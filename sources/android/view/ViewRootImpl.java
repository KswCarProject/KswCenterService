package android.view;

import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ResourcesManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.sysprop.DisplayProperties;
import android.util.AndroidRuntimeException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongArray;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Choreographer;
import android.view.DisplayCutout;
import android.view.IWindow;
import android.view.InputDevice;
import android.view.InputQueue;
import android.view.KeyCharacterMap;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.autofill.AutofillManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Scroller;
import com.android.internal.R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.IResultReceiver;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.PhoneFallbackEventHandler;
import com.android.internal.util.Preconditions;
import com.android.internal.view.BaseSurfaceHolder;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.SurfaceCallbackHelper;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public final class ViewRootImpl implements ViewParent, View.AttachInfo.Callbacks, ThreadedRenderer.DrawCallbacks {
    private static final boolean DBG = false;
    private static final boolean DEBUG_CONFIGURATION = false;
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    private static final boolean DEBUG_DIALOG = false;
    private static final boolean DEBUG_DRAW = false;
    private static final boolean DEBUG_FPS = false;
    private static final boolean DEBUG_IMF = false;
    private static final boolean DEBUG_INPUT_RESIZE = false;
    private static final boolean DEBUG_INPUT_STAGES = false;
    private static final boolean DEBUG_KEEP_SCREEN_ON = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final boolean DEBUG_ORIENTATION = false;
    private static final boolean DEBUG_TRACKBALL = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MAX_QUEUED_INPUT_EVENT_POOL_SIZE = 10;
    static final int MAX_TRACKBALL_DELAY = 250;
    private static final int MSG_CHECK_FOCUS = 13;
    private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST = 21;
    private static final int MSG_CLOSE_SYSTEM_DIALOGS = 14;
    private static final int MSG_DIE = 3;
    private static final int MSG_DISPATCH_APP_VISIBILITY = 8;
    private static final int MSG_DISPATCH_DRAG_EVENT = 15;
    private static final int MSG_DISPATCH_DRAG_LOCATION_EVENT = 16;
    private static final int MSG_DISPATCH_GET_NEW_SURFACE = 9;
    private static final int MSG_DISPATCH_INPUT_EVENT = 7;
    private static final int MSG_DISPATCH_KEY_FROM_AUTOFILL = 12;
    private static final int MSG_DISPATCH_KEY_FROM_IME = 11;
    private static final int MSG_DISPATCH_SYSTEM_UI_VISIBILITY = 17;
    private static final int MSG_DISPATCH_WINDOW_SHOWN = 25;
    private static final int MSG_DRAW_FINISHED = 29;
    private static final int MSG_INSETS_CHANGED = 30;
    private static final int MSG_INSETS_CONTROL_CHANGED = 31;
    private static final int MSG_INVALIDATE = 1;
    private static final int MSG_INVALIDATE_RECT = 2;
    private static final int MSG_INVALIDATE_WORLD = 22;
    private static final int MSG_POINTER_CAPTURE_CHANGED = 28;
    private static final int MSG_PROCESS_INPUT_EVENTS = 19;
    private static final int MSG_REQUEST_KEYBOARD_SHORTCUTS = 26;
    private static final int MSG_RESIZED = 4;
    private static final int MSG_RESIZED_REPORT = 5;
    private static final int MSG_SYNTHESIZE_INPUT_EVENT = 24;
    private static final int MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED = 32;
    private static final int MSG_UPDATE_CONFIGURATION = 18;
    private static final int MSG_UPDATE_POINTER_ICON = 27;
    private static final int MSG_WINDOW_FOCUS_CHANGED = 6;
    private static final int MSG_WINDOW_MOVED = 23;
    private static final boolean MT_RENDERER_AVAILABLE = true;
    public static final int NEW_INSETS_MODE_FULL = 2;
    public static final int NEW_INSETS_MODE_IME = 1;
    public static final int NEW_INSETS_MODE_NONE = 0;
    public static final String PROPERTY_EMULATOR_WIN_OUTSET_BOTTOM_PX = "ro.emu.win_outset_bottom_px";
    private static final String PROPERTY_PROFILE_RENDERING = "viewroot.profile_rendering";
    private static final String TAG = "ViewRootImpl";
    private static final String USE_NEW_INSETS_PROPERTY = "persist.wm.new_insets";
    static final Interpolator mResizeInterpolator = new AccelerateDecelerateInterpolator();
    private static boolean sAlwaysAssignFocus;
    private static boolean sCompatibilityDone = false;
    private static final ArrayList<ConfigChangedCallback> sConfigCallbacks = new ArrayList<>();
    static boolean sFirstDrawComplete = false;
    static final ArrayList<Runnable> sFirstDrawHandlers = new ArrayList<>();
    public static int sNewInsetsMode = SystemProperties.getInt(USE_NEW_INSETS_PROPERTY, 0);
    @UnsupportedAppUsage
    static final ThreadLocal<HandlerActionQueue> sRunQueues = new ThreadLocal<>();
    View mAccessibilityFocusedHost;
    AccessibilityNodeInfo mAccessibilityFocusedVirtualView;
    final AccessibilityInteractionConnectionManager mAccessibilityInteractionConnectionManager;
    AccessibilityInteractionController mAccessibilityInteractionController;
    final AccessibilityManager mAccessibilityManager;
    private ActivityConfigCallback mActivityConfigCallback;
    private boolean mActivityRelaunched;
    @UnsupportedAppUsage
    boolean mAdded;
    boolean mAddedTouchMode;
    private boolean mAppVisibilityChanged;
    boolean mAppVisible = true;
    boolean mApplyInsetsRequested;
    @UnsupportedAppUsage
    final View.AttachInfo mAttachInfo;
    AudioManager mAudioManager;
    final String mBasePackageName;
    public final Surface mBoundsSurface;
    private SurfaceControl mBoundsSurfaceControl;
    private int mCanvasOffsetX;
    private int mCanvasOffsetY;
    Choreographer mChoreographer;
    int mClientWindowLayoutFlags;
    final ConsumeBatchedInputImmediatelyRunnable mConsumeBatchedInputImmediatelyRunnable;
    boolean mConsumeBatchedInputImmediatelyScheduled;
    boolean mConsumeBatchedInputScheduled;
    final ConsumeBatchedInputRunnable mConsumedBatchedInputRunnable;
    @UnsupportedAppUsage
    public final Context mContext;
    int mCurScrollY;
    View mCurrentDragView;
    private PointerIcon mCustomPointerIcon;
    private final int mDensity;
    @UnsupportedAppUsage
    Rect mDirty;
    final Rect mDispatchContentInsets;
    DisplayCutout mDispatchDisplayCutout;
    final Rect mDispatchStableInsets;
    Display mDisplay;
    private final DisplayManager.DisplayListener mDisplayListener;
    final DisplayManager mDisplayManager;
    ClipDescription mDragDescription;
    final PointF mDragPoint;
    private boolean mDragResizing;
    boolean mDrawingAllowed;
    int mDrawsNeededToReport;
    @UnsupportedAppUsage
    FallbackEventHandler mFallbackEventHandler;
    boolean mFirst;
    InputStage mFirstInputStage;
    InputStage mFirstPostImeInputStage;
    private boolean mForceDecorViewVisibility;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private int mFpsNumFrames;
    private long mFpsPrevTime;
    private long mFpsStartTime;
    boolean mFullRedrawNeeded;
    private final GestureExclusionTracker mGestureExclusionTracker;
    boolean mHadWindowFocus;
    final ViewRootHandler mHandler;
    boolean mHandlingLayoutInLayoutRequest;
    int mHardwareXOffset;
    int mHardwareYOffset;
    boolean mHasHadWindowFocus;
    boolean mHaveMoveEvent;
    @UnsupportedAppUsage
    int mHeight;
    final HighContrastTextManager mHighContrastTextManager;
    private boolean mInLayout;
    InputChannel mInputChannel;
    /* access modifiers changed from: private */
    public final InputEventCompatProcessor mInputCompatProcessor;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    WindowInputEventReceiver mInputEventReceiver;
    InputQueue mInputQueue;
    InputQueue.Callback mInputQueueCallback;
    /* access modifiers changed from: private */
    public final InsetsController mInsetsController;
    final InvalidateOnAnimationRunnable mInvalidateOnAnimationRunnable;
    private boolean mInvalidateRootRequested;
    boolean mIsAmbientMode;
    public boolean mIsAnimating;
    boolean mIsCreating;
    boolean mIsDrawing;
    boolean mIsInTraversal;
    /* access modifiers changed from: private */
    public final Configuration mLastConfigurationFromResources;
    final ViewTreeObserver.InternalInsetsInfo mLastGivenInsets;
    boolean mLastInCompatMode;
    boolean mLastOverscanRequested;
    /* access modifiers changed from: private */
    public final MergedConfiguration mLastReportedMergedConfiguration;
    @UnsupportedAppUsage
    WeakReference<View> mLastScrolledFocus;
    int mLastSystemUiVisibility;
    final PointF mLastTouchPoint;
    int mLastTouchSource;
    boolean mLastWasImTarget;
    private WindowInsets mLastWindowInsets;
    boolean mLayoutRequested;
    ArrayList<View> mLayoutRequesters;
    volatile Object mLocalDragState;
    final WindowLeaked mLocation;
    boolean mLostWindowFocus;
    private boolean mNeedsRendererSetup;
    boolean mNewSurfaceNeeded;
    private final int mNoncompatDensity;
    int mOrigWindowType;
    boolean mPausedForTransition;
    boolean mPendingAlwaysConsumeSystemBars;
    final Rect mPendingBackDropFrame;
    final Rect mPendingContentInsets;
    final DisplayCutout.ParcelableWrapper mPendingDisplayCutout;
    int mPendingInputEventCount;
    QueuedInputEvent mPendingInputEventHead;
    String mPendingInputEventQueueLengthCounterName;
    QueuedInputEvent mPendingInputEventTail;
    /* access modifiers changed from: private */
    public final MergedConfiguration mPendingMergedConfiguration;
    final Rect mPendingOutsets;
    final Rect mPendingOverscanInsets;
    final Rect mPendingStableInsets;
    private ArrayList<LayoutTransition> mPendingTransitions;
    final Rect mPendingVisibleInsets;
    boolean mPointerCapture;
    /* access modifiers changed from: private */
    public int mPointerIconType;
    final Region mPreviousTransparentRegion;
    boolean mProcessInputEventsScheduled;
    private boolean mProfile;
    /* access modifiers changed from: private */
    public boolean mProfileRendering;
    private QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private boolean mRemoved;
    /* access modifiers changed from: private */
    public Choreographer.FrameCallback mRenderProfiler;
    /* access modifiers changed from: private */
    public boolean mRenderProfilingEnabled;
    boolean mReportNextDraw;
    private int mResizeMode;
    boolean mScrollMayChange;
    int mScrollY;
    Scroller mScroller;
    SendWindowContentChangedAccessibilityEvent mSendWindowContentChangedAccessibilityEvent;
    int mSeq;
    int mSoftInputMode;
    @UnsupportedAppUsage
    boolean mStopped;
    @UnsupportedAppUsage
    public final Surface mSurface;
    private final SurfaceControl mSurfaceControl;
    BaseSurfaceHolder mSurfaceHolder;
    SurfaceHolder.Callback2 mSurfaceHolderCallback;
    private SurfaceSession mSurfaceSession;
    InputStage mSyntheticInputStage;
    /* access modifiers changed from: private */
    public String mTag;
    final int mTargetSdkVersion;
    private final Rect mTempBoundsRect;
    HashSet<View> mTempHashSet;
    private InsetsState mTempInsets;
    final Rect mTempRect;
    final Thread mThread;
    final Rect mTmpFrame;
    final int[] mTmpLocation = new int[2];
    final TypedValue mTmpValue = new TypedValue();
    private final SurfaceControl.Transaction mTransaction;
    CompatibilityInfo.Translator mTranslator;
    final Region mTransparentRegion;
    int mTraversalBarrier;
    final TraversalRunnable mTraversalRunnable;
    public boolean mTraversalScheduled;
    boolean mUnbufferedInputDispatch;
    /* access modifiers changed from: private */
    public final UnhandledKeyManager mUnhandledKeyManager;
    @GuardedBy({"this"})
    boolean mUpcomingInTouchMode;
    @GuardedBy({"this"})
    boolean mUpcomingWindowFocus;
    private boolean mUseMTRenderer;
    @UnsupportedAppUsage
    View mView;
    final ViewConfiguration mViewConfiguration;
    private int mViewLayoutDirectionInitial;
    int mViewVisibility;
    final Rect mVisRect;
    @UnsupportedAppUsage
    int mWidth;
    boolean mWillDrawSoon;
    final Rect mWinFrame;
    final W mWindow;
    public final WindowManager.LayoutParams mWindowAttributes = new WindowManager.LayoutParams();
    boolean mWindowAttributesChanged;
    int mWindowAttributesChangesFlag;
    @GuardedBy({"mWindowCallbacks"})
    final ArrayList<WindowCallbacks> mWindowCallbacks = new ArrayList<>();
    CountDownLatch mWindowDrawCountDown;
    @GuardedBy({"this"})
    boolean mWindowFocusChanged;
    @UnsupportedAppUsage
    final IWindowSession mWindowSession;
    private final ArrayList<WindowStoppedCallback> mWindowStoppedCallbacks;

    public interface ActivityConfigCallback {
        void onConfigurationChanged(Configuration configuration, int i);
    }

    public interface ConfigChangedCallback {
        void onConfigurationChanged(Configuration configuration);
    }

    interface WindowStoppedCallback {
        void windowStopped(boolean z);
    }

    static final class SystemUiVisibilityInfo {
        int globalVisibility;
        int localChanges;
        int localValue;
        int seq;

        SystemUiVisibilityInfo() {
        }
    }

    public ViewRootImpl(Context context, Display display) {
        boolean z = false;
        this.mForceDecorViewVisibility = false;
        this.mOrigWindowType = -1;
        this.mStopped = false;
        this.mIsAmbientMode = false;
        this.mPausedForTransition = false;
        this.mLastInCompatMode = false;
        this.mTempBoundsRect = new Rect();
        this.mPendingInputEventQueueLengthCounterName = "pq";
        this.mUnhandledKeyManager = new UnhandledKeyManager();
        this.mWindowAttributesChanged = false;
        this.mWindowAttributesChangesFlag = 0;
        this.mSurface = new Surface();
        this.mSurfaceControl = new SurfaceControl();
        this.mBoundsSurface = new Surface();
        this.mTransaction = new SurfaceControl.Transaction();
        this.mTmpFrame = new Rect();
        this.mPendingOverscanInsets = new Rect();
        this.mPendingVisibleInsets = new Rect();
        this.mPendingStableInsets = new Rect();
        this.mPendingContentInsets = new Rect();
        this.mPendingOutsets = new Rect();
        this.mPendingBackDropFrame = new Rect();
        this.mPendingDisplayCutout = new DisplayCutout.ParcelableWrapper(DisplayCutout.NO_CUTOUT);
        this.mTempInsets = new InsetsState();
        this.mLastGivenInsets = new ViewTreeObserver.InternalInsetsInfo();
        this.mDispatchContentInsets = new Rect();
        this.mDispatchStableInsets = new Rect();
        this.mDispatchDisplayCutout = DisplayCutout.NO_CUTOUT;
        this.mLastConfigurationFromResources = new Configuration();
        this.mLastReportedMergedConfiguration = new MergedConfiguration();
        this.mPendingMergedConfiguration = new MergedConfiguration();
        this.mDragPoint = new PointF();
        this.mLastTouchPoint = new PointF();
        this.mFpsStartTime = -1;
        this.mFpsPrevTime = -1;
        this.mPointerIconType = 1;
        this.mCustomPointerIcon = null;
        this.mAccessibilityInteractionConnectionManager = new AccessibilityInteractionConnectionManager();
        this.mInLayout = false;
        this.mLayoutRequesters = new ArrayList<>();
        this.mHandlingLayoutInLayoutRequest = false;
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mInsetsController = new InsetsController(this);
        this.mGestureExclusionTracker = new GestureExclusionTracker();
        this.mTag = TAG;
        this.mHaveMoveEvent = false;
        this.mProfile = false;
        this.mDisplayListener = new DisplayManager.DisplayListener() {
            public void onDisplayChanged(int displayId) {
                int oldDisplayState;
                int newDisplayState;
                if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mDisplay.getDisplayId() == displayId && (oldDisplayState = ViewRootImpl.this.mAttachInfo.mDisplayState) != (newDisplayState = ViewRootImpl.this.mDisplay.getState())) {
                    ViewRootImpl.this.mAttachInfo.mDisplayState = newDisplayState;
                    ViewRootImpl.this.pokeDrawLockIfNeeded();
                    if (oldDisplayState != 0) {
                        int oldScreenState = toViewScreenState(oldDisplayState);
                        int newScreenState = toViewScreenState(newDisplayState);
                        if (oldScreenState != newScreenState) {
                            ViewRootImpl.this.mView.dispatchScreenStateChanged(newScreenState);
                        }
                        if (oldDisplayState == 1) {
                            ViewRootImpl.this.mFullRedrawNeeded = true;
                            ViewRootImpl.this.scheduleTraversals();
                        }
                    }
                }
            }

            public void onDisplayRemoved(int displayId) {
            }

            public void onDisplayAdded(int displayId) {
            }

            private int toViewScreenState(int displayState) {
                if (displayState == 1) {
                    return 0;
                }
                return 1;
            }
        };
        this.mWindowStoppedCallbacks = new ArrayList<>();
        this.mDrawsNeededToReport = 0;
        this.mHandler = new ViewRootHandler();
        this.mTraversalRunnable = new TraversalRunnable();
        this.mConsumedBatchedInputRunnable = new ConsumeBatchedInputRunnable();
        this.mConsumeBatchedInputImmediatelyRunnable = new ConsumeBatchedInputImmediatelyRunnable();
        this.mInvalidateOnAnimationRunnable = new InvalidateOnAnimationRunnable();
        this.mContext = context;
        this.mWindowSession = WindowManagerGlobal.getWindowSession();
        this.mDisplay = display;
        this.mBasePackageName = context.getBasePackageName();
        this.mThread = Thread.currentThread();
        this.mLocation = new WindowLeaked((String) null);
        this.mLocation.fillInStackTrace();
        this.mWidth = -1;
        this.mHeight = -1;
        this.mDirty = new Rect();
        this.mTempRect = new Rect();
        this.mVisRect = new Rect();
        this.mWinFrame = new Rect();
        this.mWindow = new W(this);
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mViewVisibility = 8;
        this.mTransparentRegion = new Region();
        this.mPreviousTransparentRegion = new Region();
        this.mFirst = true;
        this.mAdded = false;
        this.mAttachInfo = new View.AttachInfo(this.mWindowSession, this.mWindow, display, this, this.mHandler, this, context);
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager, this.mHandler);
        this.mHighContrastTextManager = new HighContrastTextManager();
        this.mAccessibilityManager.addHighTextContrastStateChangeListener(this.mHighContrastTextManager, this.mHandler);
        this.mViewConfiguration = ViewConfiguration.get(context);
        this.mDensity = context.getResources().getDisplayMetrics().densityDpi;
        this.mNoncompatDensity = context.getResources().getDisplayMetrics().noncompatDensityDpi;
        this.mFallbackEventHandler = new PhoneFallbackEventHandler(context);
        this.mChoreographer = Choreographer.getInstance();
        this.mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        String processorOverrideName = context.getResources().getString(R.string.config_inputEventCompatProcessorOverrideClassName);
        if (processorOverrideName.isEmpty()) {
            this.mInputCompatProcessor = new InputEventCompatProcessor(context);
        } else {
            InputEventCompatProcessor compatProcessor = null;
            try {
                compatProcessor = (InputEventCompatProcessor) Class.forName(processorOverrideName).getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
            } catch (Exception e) {
                Log.e(TAG, "Unable to create the InputEventCompatProcessor. ", e);
            } catch (Throwable th) {
                this.mInputCompatProcessor = null;
                throw th;
            }
            this.mInputCompatProcessor = compatProcessor;
        }
        if (!sCompatibilityDone) {
            sAlwaysAssignFocus = this.mTargetSdkVersion < 28 ? true : z;
            sCompatibilityDone = true;
        }
        loadSystemProperties();
    }

    public static void addFirstDrawHandler(Runnable callback) {
        synchronized (sFirstDrawHandlers) {
            if (!sFirstDrawComplete) {
                sFirstDrawHandlers.add(callback);
            }
        }
    }

    @UnsupportedAppUsage
    public static void addConfigCallback(ConfigChangedCallback callback) {
        synchronized (sConfigCallbacks) {
            sConfigCallbacks.add(callback);
        }
    }

    public void setActivityConfigCallback(ActivityConfigCallback callback) {
        this.mActivityConfigCallback = callback;
    }

    public void addWindowCallbacks(WindowCallbacks callback) {
        synchronized (this.mWindowCallbacks) {
            this.mWindowCallbacks.add(callback);
        }
    }

    public void removeWindowCallbacks(WindowCallbacks callback) {
        synchronized (this.mWindowCallbacks) {
            this.mWindowCallbacks.remove(callback);
        }
    }

    public void reportDrawFinish() {
        if (this.mWindowDrawCountDown != null) {
            this.mWindowDrawCountDown.countDown();
        }
    }

    public void profile() {
        this.mProfile = true;
    }

    static boolean isInTouchMode() {
        IWindowSession windowSession = WindowManagerGlobal.peekWindowSession();
        if (windowSession == null) {
            return false;
        }
        try {
            return windowSession.getInTouchMode();
        } catch (RemoteException e) {
            return false;
        }
    }

    public void notifyChildRebuilt() {
        if (this.mView instanceof RootViewSurfaceTaker) {
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder.removeCallback(this.mSurfaceHolderCallback);
            }
            this.mSurfaceHolderCallback = ((RootViewSurfaceTaker) this.mView).willYouTakeTheSurface();
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder = new TakenSurfaceHolder();
                this.mSurfaceHolder.setFormat(0);
                this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
            } else {
                this.mSurfaceHolder = null;
            }
            this.mInputQueueCallback = ((RootViewSurfaceTaker) this.mView).willYouTakeTheInputQueue();
            if (this.mInputQueueCallback != null) {
                this.mInputQueueCallback.onInputQueueCreated(this.mInputQueue);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:143:0x040f, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0408 A[SYNTHETIC, Splitter:B:138:0x0408] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setView(android.view.View r25, android.view.WindowManager.LayoutParams r26, android.view.View r27) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            monitor-enter(r24)
            android.view.View r0 = r1.mView     // Catch:{ all -> 0x0410 }
            if (r0 != 0) goto L_0x040c
            r1.mView = r2     // Catch:{ all -> 0x0410 }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ all -> 0x0410 }
            android.view.Display r3 = r1.mDisplay     // Catch:{ all -> 0x0410 }
            int r3 = r3.getState()     // Catch:{ all -> 0x0410 }
            r0.mDisplayState = r3     // Catch:{ all -> 0x0410 }
            android.hardware.display.DisplayManager r0 = r1.mDisplayManager     // Catch:{ all -> 0x0410 }
            android.hardware.display.DisplayManager$DisplayListener r3 = r1.mDisplayListener     // Catch:{ all -> 0x0410 }
            android.view.ViewRootImpl$ViewRootHandler r4 = r1.mHandler     // Catch:{ all -> 0x0410 }
            r0.registerDisplayListener(r3, r4)     // Catch:{ all -> 0x0410 }
            android.view.View r0 = r1.mView     // Catch:{ all -> 0x0410 }
            int r0 = r0.getRawLayoutDirection()     // Catch:{ all -> 0x0410 }
            r1.mViewLayoutDirectionInitial = r0     // Catch:{ all -> 0x0410 }
            android.view.FallbackEventHandler r0 = r1.mFallbackEventHandler     // Catch:{ all -> 0x0410 }
            r0.setView(r2)     // Catch:{ all -> 0x0410 }
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ all -> 0x0410 }
            r3 = r26
            r0.copyFrom(r3)     // Catch:{ all -> 0x0415 }
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ all -> 0x0415 }
            java.lang.String r0 = r0.packageName     // Catch:{ all -> 0x0415 }
            if (r0 != 0) goto L_0x003e
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ all -> 0x0415 }
            java.lang.String r4 = r1.mBasePackageName     // Catch:{ all -> 0x0415 }
            r0.packageName = r4     // Catch:{ all -> 0x0415 }
        L_0x003e:
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ all -> 0x0415 }
            r3 = r0
            r24.setTag()     // Catch:{ all -> 0x0415 }
            int r0 = r3.flags     // Catch:{ all -> 0x0415 }
            r1.mClientWindowLayoutFlags = r0     // Catch:{ all -> 0x0415 }
            r4 = 0
            r1.setAccessibilityFocus(r4, r4)     // Catch:{ all -> 0x0415 }
            boolean r0 = r2 instanceof com.android.internal.view.RootViewSurfaceTaker     // Catch:{ all -> 0x0415 }
            r5 = 0
            if (r0 == 0) goto L_0x0071
            r0 = r2
            com.android.internal.view.RootViewSurfaceTaker r0 = (com.android.internal.view.RootViewSurfaceTaker) r0     // Catch:{ all -> 0x0415 }
            android.view.SurfaceHolder$Callback2 r0 = r0.willYouTakeTheSurface()     // Catch:{ all -> 0x0415 }
            r1.mSurfaceHolderCallback = r0     // Catch:{ all -> 0x0415 }
            android.view.SurfaceHolder$Callback2 r0 = r1.mSurfaceHolderCallback     // Catch:{ all -> 0x0415 }
            if (r0 == 0) goto L_0x0071
            android.view.ViewRootImpl$TakenSurfaceHolder r0 = new android.view.ViewRootImpl$TakenSurfaceHolder     // Catch:{ all -> 0x0415 }
            r0.<init>()     // Catch:{ all -> 0x0415 }
            r1.mSurfaceHolder = r0     // Catch:{ all -> 0x0415 }
            com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x0415 }
            r0.setFormat(r5)     // Catch:{ all -> 0x0415 }
            com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x0415 }
            android.view.SurfaceHolder$Callback2 r6 = r1.mSurfaceHolderCallback     // Catch:{ all -> 0x0415 }
            r0.addCallback(r6)     // Catch:{ all -> 0x0415 }
        L_0x0071:
            boolean r0 = r3.hasManualSurfaceInsets     // Catch:{ all -> 0x0415 }
            r6 = 1
            if (r0 != 0) goto L_0x0079
            r3.setSurfaceInsets(r2, r5, r6)     // Catch:{ all -> 0x0415 }
        L_0x0079:
            android.view.Display r0 = r1.mDisplay     // Catch:{ all -> 0x0415 }
            android.view.DisplayAdjustments r0 = r0.getDisplayAdjustments()     // Catch:{ all -> 0x0415 }
            android.content.res.CompatibilityInfo r0 = r0.getCompatibilityInfo()     // Catch:{ all -> 0x0415 }
            r7 = r0
            android.content.res.CompatibilityInfo$Translator r0 = r7.getTranslator()     // Catch:{ all -> 0x0415 }
            r1.mTranslator = r0     // Catch:{ all -> 0x0415 }
            com.android.internal.view.BaseSurfaceHolder r0 = r1.mSurfaceHolder     // Catch:{ all -> 0x0415 }
            if (r0 != 0) goto L_0x00a3
            r1.enableHardwareAcceleration(r3)     // Catch:{ all -> 0x0415 }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ all -> 0x0415 }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ all -> 0x0415 }
            if (r0 == 0) goto L_0x0099
            r0 = r6
            goto L_0x009a
        L_0x0099:
            r0 = r5
        L_0x009a:
            boolean r8 = r1.mUseMTRenderer     // Catch:{ all -> 0x0415 }
            if (r8 == r0) goto L_0x00a3
            r24.endDragResizing()     // Catch:{ all -> 0x0415 }
            r1.mUseMTRenderer = r0     // Catch:{ all -> 0x0415 }
        L_0x00a3:
            r0 = 0
            android.content.res.CompatibilityInfo$Translator r8 = r1.mTranslator     // Catch:{ all -> 0x0415 }
            if (r8 == 0) goto L_0x00b8
            android.view.Surface r8 = r1.mSurface     // Catch:{ all -> 0x0415 }
            android.content.res.CompatibilityInfo$Translator r9 = r1.mTranslator     // Catch:{ all -> 0x0415 }
            r8.setCompatibilityTranslator(r9)     // Catch:{ all -> 0x0415 }
            r0 = 1
            r3.backup()     // Catch:{ all -> 0x0415 }
            android.content.res.CompatibilityInfo$Translator r8 = r1.mTranslator     // Catch:{ all -> 0x0415 }
            r8.translateWindowLayout(r3)     // Catch:{ all -> 0x0415 }
        L_0x00b8:
            r8 = r0
            boolean r0 = r7.supportsScreen()     // Catch:{ all -> 0x0415 }
            if (r0 != 0) goto L_0x00c7
            int r0 = r3.privateFlags     // Catch:{ all -> 0x0415 }
            r0 = r0 | 128(0x80, float:1.794E-43)
            r3.privateFlags = r0     // Catch:{ all -> 0x0415 }
            r1.mLastInCompatMode = r6     // Catch:{ all -> 0x0415 }
        L_0x00c7:
            int r0 = r3.softInputMode     // Catch:{ all -> 0x0415 }
            r1.mSoftInputMode = r0     // Catch:{ all -> 0x0415 }
            r1.mWindowAttributesChanged = r6     // Catch:{ all -> 0x0415 }
            r0 = -1
            r1.mWindowAttributesChangesFlag = r0     // Catch:{ all -> 0x0415 }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ all -> 0x0415 }
            r0.mRootView = r2     // Catch:{ all -> 0x0415 }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ all -> 0x0415 }
            android.content.res.CompatibilityInfo$Translator r9 = r1.mTranslator     // Catch:{ all -> 0x0415 }
            if (r9 == 0) goto L_0x00dc
            r9 = r6
            goto L_0x00dd
        L_0x00dc:
            r9 = r5
        L_0x00dd:
            r0.mScalingRequired = r9     // Catch:{ all -> 0x0415 }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ all -> 0x0415 }
            android.content.res.CompatibilityInfo$Translator r9 = r1.mTranslator     // Catch:{ all -> 0x0415 }
            if (r9 != 0) goto L_0x00e8
            r9 = 1065353216(0x3f800000, float:1.0)
            goto L_0x00ec
        L_0x00e8:
            android.content.res.CompatibilityInfo$Translator r9 = r1.mTranslator     // Catch:{ all -> 0x0415 }
            float r9 = r9.applicationScale     // Catch:{ all -> 0x0415 }
        L_0x00ec:
            r0.mApplicationScale = r9     // Catch:{ all -> 0x0415 }
            if (r27 == 0) goto L_0x00f8
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ all -> 0x0415 }
            android.os.IBinder r10 = r27.getApplicationWindowToken()     // Catch:{ all -> 0x0415 }
            r0.mPanelParentWindowToken = r10     // Catch:{ all -> 0x0415 }
        L_0x00f8:
            r1.mAdded = r6     // Catch:{ all -> 0x0415 }
            r24.requestLayout()     // Catch:{ all -> 0x0415 }
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ all -> 0x0415 }
            int r0 = r0.inputFeatures     // Catch:{ all -> 0x0415 }
            r0 = r0 & 2
            if (r0 != 0) goto L_0x010c
            android.view.InputChannel r0 = new android.view.InputChannel     // Catch:{ all -> 0x0415 }
            r0.<init>()     // Catch:{ all -> 0x0415 }
            r1.mInputChannel = r0     // Catch:{ all -> 0x0415 }
        L_0x010c:
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ all -> 0x0415 }
            int r0 = r0.privateFlags     // Catch:{ all -> 0x0415 }
            r0 = r0 & 16384(0x4000, float:2.2959E-41)
            if (r0 == 0) goto L_0x0116
            r0 = r6
            goto L_0x0117
        L_0x0116:
            r0 = r5
        L_0x0117:
            r1.mForceDecorViewVisibility = r0     // Catch:{ all -> 0x0415 }
            android.view.WindowManager$LayoutParams r0 = r1.mWindowAttributes     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            int r0 = r0.type     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            r1.mOrigWindowType = r0     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            r0.mRecomputeGlobalAttributes = r6     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            r24.collectViewAttributes()     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.IWindowSession r10 = r1.mWindowSession     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.ViewRootImpl$W r11 = r1.mWindow     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            int r12 = r1.mSeq     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.WindowManager$LayoutParams r13 = r1.mWindowAttributes     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            int r14 = r24.getHostVisibility()     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.Display r0 = r1.mDisplay     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            int r15 = r0.getDisplayId()     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.graphics.Rect r0 = r1.mTmpFrame     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.View$AttachInfo r6 = r1.mAttachInfo     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.graphics.Rect r6 = r6.mContentInsets     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.View$AttachInfo r4 = r1.mAttachInfo     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.graphics.Rect r4 = r4.mStableInsets     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.view.View$AttachInfo r5 = r1.mAttachInfo     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            android.graphics.Rect r5 = r5.mOutsets     // Catch:{ RemoteException -> 0x03e3, all -> 0x03df }
            r23 = r7
            android.view.View$AttachInfo r7 = r1.mAttachInfo     // Catch:{ RemoteException -> 0x03dd }
            android.view.DisplayCutout$ParcelableWrapper r7 = r7.mDisplayCutout     // Catch:{ RemoteException -> 0x03dd }
            android.view.InputChannel r9 = r1.mInputChannel     // Catch:{ RemoteException -> 0x03dd }
            android.view.InsetsState r2 = r1.mTempInsets     // Catch:{ RemoteException -> 0x03d9, all -> 0x03d5 }
            r16 = r0
            r17 = r6
            r18 = r4
            r19 = r5
            r20 = r7
            r21 = r9
            r22 = r2
            int r0 = r10.addToDisplay(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)     // Catch:{ RemoteException -> 0x03d9, all -> 0x03d5 }
            android.graphics.Rect r2 = r1.mTmpFrame     // Catch:{ RemoteException -> 0x03d9, all -> 0x03d5 }
            r1.setFrame(r2)     // Catch:{ RemoteException -> 0x03d9, all -> 0x03d5 }
            if (r8 == 0) goto L_0x0172
            r3.restore()     // Catch:{ all -> 0x016d }
            goto L_0x0172
        L_0x016d:
            r0 = move-exception
            r2 = r25
            goto L_0x0413
        L_0x0172:
            android.content.res.CompatibilityInfo$Translator r2 = r1.mTranslator     // Catch:{ all -> 0x016d }
            if (r2 == 0) goto L_0x0180
            android.content.res.CompatibilityInfo$Translator r2 = r1.mTranslator     // Catch:{ all -> 0x016d }
            android.view.View$AttachInfo r4 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            android.graphics.Rect r4 = r4.mContentInsets     // Catch:{ all -> 0x016d }
            r2.translateRectInScreenToAppWindow(r4)     // Catch:{ all -> 0x016d }
        L_0x0180:
            android.graphics.Rect r2 = r1.mPendingOverscanInsets     // Catch:{ all -> 0x016d }
            r4 = 0
            r2.set(r4, r4, r4, r4)     // Catch:{ all -> 0x016d }
            android.graphics.Rect r2 = r1.mPendingContentInsets     // Catch:{ all -> 0x016d }
            android.view.View$AttachInfo r4 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            android.graphics.Rect r4 = r4.mContentInsets     // Catch:{ all -> 0x016d }
            r2.set(r4)     // Catch:{ all -> 0x016d }
            android.graphics.Rect r2 = r1.mPendingStableInsets     // Catch:{ all -> 0x016d }
            android.view.View$AttachInfo r4 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            android.graphics.Rect r4 = r4.mStableInsets     // Catch:{ all -> 0x016d }
            r2.set(r4)     // Catch:{ all -> 0x016d }
            android.view.DisplayCutout$ParcelableWrapper r2 = r1.mPendingDisplayCutout     // Catch:{ all -> 0x016d }
            android.view.View$AttachInfo r4 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            android.view.DisplayCutout$ParcelableWrapper r4 = r4.mDisplayCutout     // Catch:{ all -> 0x016d }
            r2.set((android.view.DisplayCutout.ParcelableWrapper) r4)     // Catch:{ all -> 0x016d }
            android.graphics.Rect r2 = r1.mPendingVisibleInsets     // Catch:{ all -> 0x016d }
            r4 = 0
            r2.set(r4, r4, r4, r4)     // Catch:{ all -> 0x016d }
            android.view.View$AttachInfo r2 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            r4 = r0 & 4
            if (r4 == 0) goto L_0x01af
            r4 = 1
            goto L_0x01b0
        L_0x01af:
            r4 = 0
        L_0x01b0:
            r2.mAlwaysConsumeSystemBars = r4     // Catch:{ all -> 0x016d }
            android.view.View$AttachInfo r2 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            boolean r2 = r2.mAlwaysConsumeSystemBars     // Catch:{ all -> 0x016d }
            r1.mPendingAlwaysConsumeSystemBars = r2     // Catch:{ all -> 0x016d }
            android.view.InsetsController r2 = r1.mInsetsController     // Catch:{ all -> 0x016d }
            android.view.InsetsState r4 = r1.mTempInsets     // Catch:{ all -> 0x016d }
            r2.onStateChanged(r4)     // Catch:{ all -> 0x016d }
            if (r0 >= 0) goto L_0x0301
            android.view.View$AttachInfo r2 = r1.mAttachInfo     // Catch:{ all -> 0x016d }
            r4 = 0
            r2.mRootView = r4     // Catch:{ all -> 0x016d }
            r2 = 0
            r1.mAdded = r2     // Catch:{ all -> 0x016d }
            android.view.FallbackEventHandler r2 = r1.mFallbackEventHandler     // Catch:{ all -> 0x016d }
            r2.setView(r4)     // Catch:{ all -> 0x016d }
            r24.unscheduleTraversals()     // Catch:{ all -> 0x016d }
            r1.setAccessibilityFocus(r4, r4)     // Catch:{ all -> 0x016d }
            switch(r0) {
                case -10: goto L_0x02c2;
                case -9: goto L_0x02a4;
                case -8: goto L_0x027f;
                case -7: goto L_0x0255;
                case -6: goto L_0x0253;
                case -5: goto L_0x0235;
                case -4: goto L_0x0217;
                case -3: goto L_0x01f9;
                case -2: goto L_0x01db;
                case -1: goto L_0x01db;
                default: goto L_0x01d7;
            }     // Catch:{ all -> 0x016d }
        L_0x01d7:
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x016d }
            goto L_0x02ec
        L_0x01db:
            android.view.WindowManager$BadTokenException r2 = new android.view.WindowManager$BadTokenException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window -- token "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.os.IBinder r5 = r3.token     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " is not valid; is your activity running?"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x01f9:
            android.view.WindowManager$BadTokenException r2 = new android.view.WindowManager$BadTokenException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window -- token "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.os.IBinder r5 = r3.token     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " is not for an application"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x0217:
            android.view.WindowManager$BadTokenException r2 = new android.view.WindowManager$BadTokenException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window -- app for token "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.os.IBinder r5 = r3.token     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " is exiting"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x0235:
            android.view.WindowManager$BadTokenException r2 = new android.view.WindowManager$BadTokenException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window -- window "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.ViewRootImpl$W r5 = r1.mWindow     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " has already been added"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x0253:
            monitor-exit(r24)     // Catch:{ all -> 0x016d }
            return
        L_0x0255:
            android.view.WindowManager$BadTokenException r2 = new android.view.WindowManager$BadTokenException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.ViewRootImpl$W r5 = r1.mWindow     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " -- another window of type "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.WindowManager$LayoutParams r5 = r1.mWindowAttributes     // Catch:{ all -> 0x016d }
            int r5 = r5.type     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " already exists"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x027f:
            android.view.WindowManager$BadTokenException r2 = new android.view.WindowManager$BadTokenException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.ViewRootImpl$W r5 = r1.mWindow     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " -- permission denied for window type "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.WindowManager$LayoutParams r5 = r1.mWindowAttributes     // Catch:{ all -> 0x016d }
            int r5 = r5.type     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x02a4:
            android.view.WindowManager$InvalidDisplayException r2 = new android.view.WindowManager$InvalidDisplayException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.ViewRootImpl$W r5 = r1.mWindow     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " -- the specified display can not be found"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x02c2:
            android.view.WindowManager$InvalidDisplayException r2 = new android.view.WindowManager$InvalidDisplayException     // Catch:{ all -> 0x016d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.ViewRootImpl$W r5 = r1.mWindow     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " -- the specified window type "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            android.view.WindowManager$LayoutParams r5 = r1.mWindowAttributes     // Catch:{ all -> 0x016d }
            int r5 = r5.type     // Catch:{ all -> 0x016d }
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r5 = " is not valid"
            r4.append(r5)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x02ec:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x016d }
            r4.<init>()     // Catch:{ all -> 0x016d }
            java.lang.String r5 = "Unable to add window -- unknown error code "
            r4.append(r5)     // Catch:{ all -> 0x016d }
            r4.append(r0)     // Catch:{ all -> 0x016d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x016d }
            r2.<init>(r4)     // Catch:{ all -> 0x016d }
            throw r2     // Catch:{ all -> 0x016d }
        L_0x0301:
            r2 = r25
            boolean r4 = r2 instanceof com.android.internal.view.RootViewSurfaceTaker     // Catch:{ all -> 0x0415 }
            if (r4 == 0) goto L_0x0310
            r4 = r2
            com.android.internal.view.RootViewSurfaceTaker r4 = (com.android.internal.view.RootViewSurfaceTaker) r4     // Catch:{ all -> 0x0415 }
            android.view.InputQueue$Callback r4 = r4.willYouTakeTheInputQueue()     // Catch:{ all -> 0x0415 }
            r1.mInputQueueCallback = r4     // Catch:{ all -> 0x0415 }
        L_0x0310:
            android.view.InputChannel r4 = r1.mInputChannel     // Catch:{ all -> 0x0415 }
            if (r4 == 0) goto L_0x0333
            android.view.InputQueue$Callback r4 = r1.mInputQueueCallback     // Catch:{ all -> 0x0415 }
            if (r4 == 0) goto L_0x0326
            android.view.InputQueue r4 = new android.view.InputQueue     // Catch:{ all -> 0x0415 }
            r4.<init>()     // Catch:{ all -> 0x0415 }
            r1.mInputQueue = r4     // Catch:{ all -> 0x0415 }
            android.view.InputQueue$Callback r4 = r1.mInputQueueCallback     // Catch:{ all -> 0x0415 }
            android.view.InputQueue r5 = r1.mInputQueue     // Catch:{ all -> 0x0415 }
            r4.onInputQueueCreated(r5)     // Catch:{ all -> 0x0415 }
        L_0x0326:
            android.view.ViewRootImpl$WindowInputEventReceiver r4 = new android.view.ViewRootImpl$WindowInputEventReceiver     // Catch:{ all -> 0x0415 }
            android.view.InputChannel r5 = r1.mInputChannel     // Catch:{ all -> 0x0415 }
            android.os.Looper r6 = android.os.Looper.myLooper()     // Catch:{ all -> 0x0415 }
            r4.<init>(r5, r6)     // Catch:{ all -> 0x0415 }
            r1.mInputEventReceiver = r4     // Catch:{ all -> 0x0415 }
        L_0x0333:
            r2.assignParent(r1)     // Catch:{ all -> 0x0415 }
            r4 = r0 & 1
            if (r4 == 0) goto L_0x033c
            r4 = 1
            goto L_0x033d
        L_0x033c:
            r4 = 0
        L_0x033d:
            r1.mAddedTouchMode = r4     // Catch:{ all -> 0x0415 }
            r4 = r0 & 2
            if (r4 == 0) goto L_0x0345
            r4 = 1
            goto L_0x0346
        L_0x0345:
            r4 = 0
        L_0x0346:
            r1.mAppVisible = r4     // Catch:{ all -> 0x0415 }
            android.view.accessibility.AccessibilityManager r4 = r1.mAccessibilityManager     // Catch:{ all -> 0x0415 }
            boolean r4 = r4.isEnabled()     // Catch:{ all -> 0x0415 }
            if (r4 == 0) goto L_0x0355
            android.view.ViewRootImpl$AccessibilityInteractionConnectionManager r4 = r1.mAccessibilityInteractionConnectionManager     // Catch:{ all -> 0x0415 }
            r4.ensureConnection()     // Catch:{ all -> 0x0415 }
        L_0x0355:
            int r4 = r25.getImportantForAccessibility()     // Catch:{ all -> 0x0415 }
            if (r4 != 0) goto L_0x035f
            r4 = 1
            r2.setImportantForAccessibility(r4)     // Catch:{ all -> 0x0415 }
        L_0x035f:
            java.lang.CharSequence r4 = r3.getTitle()     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$SyntheticInputStage r5 = new android.view.ViewRootImpl$SyntheticInputStage     // Catch:{ all -> 0x0415 }
            r5.<init>()     // Catch:{ all -> 0x0415 }
            r1.mSyntheticInputStage = r5     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$ViewPostImeInputStage r5 = new android.view.ViewRootImpl$ViewPostImeInputStage     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$InputStage r6 = r1.mSyntheticInputStage     // Catch:{ all -> 0x0415 }
            r5.<init>(r6)     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$NativePostImeInputStage r6 = new android.view.ViewRootImpl$NativePostImeInputStage     // Catch:{ all -> 0x0415 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0415 }
            r7.<init>()     // Catch:{ all -> 0x0415 }
            java.lang.String r9 = "aq:native-post-ime:"
            r7.append(r9)     // Catch:{ all -> 0x0415 }
            r7.append(r4)     // Catch:{ all -> 0x0415 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0415 }
            r6.<init>(r5, r7)     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$EarlyPostImeInputStage r7 = new android.view.ViewRootImpl$EarlyPostImeInputStage     // Catch:{ all -> 0x0415 }
            r7.<init>(r6)     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$ImeInputStage r9 = new android.view.ViewRootImpl$ImeInputStage     // Catch:{ all -> 0x0415 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0415 }
            r10.<init>()     // Catch:{ all -> 0x0415 }
            java.lang.String r11 = "aq:ime:"
            r10.append(r11)     // Catch:{ all -> 0x0415 }
            r10.append(r4)     // Catch:{ all -> 0x0415 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0415 }
            r9.<init>(r7, r10)     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$ViewPreImeInputStage r10 = new android.view.ViewRootImpl$ViewPreImeInputStage     // Catch:{ all -> 0x0415 }
            r10.<init>(r9)     // Catch:{ all -> 0x0415 }
            android.view.ViewRootImpl$NativePreImeInputStage r11 = new android.view.ViewRootImpl$NativePreImeInputStage     // Catch:{ all -> 0x0415 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0415 }
            r12.<init>()     // Catch:{ all -> 0x0415 }
            java.lang.String r13 = "aq:native-pre-ime:"
            r12.append(r13)     // Catch:{ all -> 0x0415 }
            r12.append(r4)     // Catch:{ all -> 0x0415 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0415 }
            r11.<init>(r10, r12)     // Catch:{ all -> 0x0415 }
            r1.mFirstInputStage = r11     // Catch:{ all -> 0x0415 }
            r1.mFirstPostImeInputStage = r7     // Catch:{ all -> 0x0415 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0415 }
            r12.<init>()     // Catch:{ all -> 0x0415 }
            java.lang.String r13 = "aq:pending:"
            r12.append(r13)     // Catch:{ all -> 0x0415 }
            r12.append(r4)     // Catch:{ all -> 0x0415 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0415 }
            r1.mPendingInputEventQueueLengthCounterName = r12     // Catch:{ all -> 0x0415 }
            goto L_0x040e
        L_0x03d5:
            r0 = move-exception
            r2 = r25
            goto L_0x0406
        L_0x03d9:
            r0 = move-exception
            r2 = r25
            goto L_0x03e6
        L_0x03dd:
            r0 = move-exception
            goto L_0x03e6
        L_0x03df:
            r0 = move-exception
            r23 = r7
            goto L_0x0406
        L_0x03e3:
            r0 = move-exception
            r23 = r7
        L_0x03e6:
            r4 = 0
            r1.mAdded = r4     // Catch:{ all -> 0x0405 }
            r4 = 0
            r1.mView = r4     // Catch:{ all -> 0x0405 }
            android.view.View$AttachInfo r5 = r1.mAttachInfo     // Catch:{ all -> 0x0405 }
            r5.mRootView = r4     // Catch:{ all -> 0x0405 }
            r1.mInputChannel = r4     // Catch:{ all -> 0x0405 }
            android.view.FallbackEventHandler r5 = r1.mFallbackEventHandler     // Catch:{ all -> 0x0405 }
            r5.setView(r4)     // Catch:{ all -> 0x0405 }
            r24.unscheduleTraversals()     // Catch:{ all -> 0x0405 }
            r1.setAccessibilityFocus(r4, r4)     // Catch:{ all -> 0x0405 }
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0405 }
            java.lang.String r5 = "Adding window failed"
            r4.<init>(r5, r0)     // Catch:{ all -> 0x0405 }
            throw r4     // Catch:{ all -> 0x0405 }
        L_0x0405:
            r0 = move-exception
        L_0x0406:
            if (r8 == 0) goto L_0x040b
            r3.restore()     // Catch:{ all -> 0x0415 }
        L_0x040b:
            throw r0     // Catch:{ all -> 0x0415 }
        L_0x040c:
            r3 = r26
        L_0x040e:
            monitor-exit(r24)     // Catch:{ all -> 0x0415 }
            return
        L_0x0410:
            r0 = move-exception
            r3 = r26
        L_0x0413:
            monitor-exit(r24)     // Catch:{ all -> 0x0415 }
            throw r0
        L_0x0415:
            r0 = move-exception
            goto L_0x0413
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.setView(android.view.View, android.view.WindowManager$LayoutParams, android.view.View):void");
    }

    private void setTag() {
        String[] split = this.mWindowAttributes.getTitle().toString().split("\\.");
        if (split.length > 0) {
            this.mTag = "ViewRootImpl[" + split[split.length - 1] + "]";
        }
    }

    /* access modifiers changed from: private */
    public boolean isInLocalFocusMode() {
        return (this.mWindowAttributes.flags & 268435456) != 0;
    }

    @UnsupportedAppUsage
    public int getWindowFlags() {
        return this.mWindowAttributes.flags;
    }

    public int getDisplayId() {
        return this.mDisplay.getDisplayId();
    }

    public CharSequence getTitle() {
        return this.mWindowAttributes.getTitle();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    /* access modifiers changed from: package-private */
    public void destroyHardwareResources() {
        ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
        if (renderer == null) {
            return;
        }
        if (Looper.myLooper() != this.mAttachInfo.mHandler.getLooper()) {
            this.mAttachInfo.mHandler.postAtFrontOfQueue(new Runnable() {
                public final void run() {
                    ViewRootImpl.this.destroyHardwareResources();
                }
            });
            return;
        }
        renderer.destroyHardwareResources(this.mView);
        renderer.destroy();
    }

    @UnsupportedAppUsage
    public void detachFunctor(long functor) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.stopDrawing();
        }
    }

    @UnsupportedAppUsage
    public static void invokeFunctor(long functor, boolean waitForCompletion) {
        ThreadedRenderer.invokeFunctor(functor, waitForCompletion);
    }

    public void registerAnimatingRenderNode(RenderNode animator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerAnimatingRenderNode(animator);
            return;
        }
        if (this.mAttachInfo.mPendingAnimatingRenderNodes == null) {
            this.mAttachInfo.mPendingAnimatingRenderNodes = new ArrayList();
        }
        this.mAttachInfo.mPendingAnimatingRenderNodes.add(animator);
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator animator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerVectorDrawableAnimator(animator);
        }
    }

    public void registerRtFrameCallback(HardwareRenderer.FrameDrawingCallback callback) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() {
                public final void onFrameDraw(long j) {
                    ViewRootImpl.lambda$registerRtFrameCallback$0(HardwareRenderer.FrameDrawingCallback.this, j);
                }
            });
        }
    }

    static /* synthetic */ void lambda$registerRtFrameCallback$0(HardwareRenderer.FrameDrawingCallback callback, long frame) {
        try {
            callback.onFrameDraw(frame);
        } catch (Exception e) {
            Log.e(TAG, "Exception while executing onFrameDraw", e);
        }
    }

    @UnsupportedAppUsage
    private void enableHardwareAcceleration(WindowManager.LayoutParams attrs) {
        boolean wideGamut = false;
        this.mAttachInfo.mHardwareAccelerated = false;
        this.mAttachInfo.mHardwareAccelerationRequested = false;
        if (this.mTranslator == null) {
            if (((attrs.flags & 16777216) != 0) && ThreadedRenderer.isAvailable()) {
                boolean fakeHwAccelerated = (attrs.privateFlags & 1) != 0;
                boolean forceHwAccelerated = (attrs.privateFlags & 2) != 0;
                if (fakeHwAccelerated) {
                    this.mAttachInfo.mHardwareAccelerationRequested = true;
                } else if (!ThreadedRenderer.sRendererDisabled || (ThreadedRenderer.sSystemRendererDisabled && forceHwAccelerated)) {
                    if (this.mAttachInfo.mThreadedRenderer != null) {
                        this.mAttachInfo.mThreadedRenderer.destroy();
                    }
                    Rect insets = attrs.surfaceInsets;
                    boolean translucent = attrs.format != -1 || (insets.left != 0 || insets.right != 0 || insets.top != 0 || insets.bottom != 0);
                    if (this.mContext.getResources().getConfiguration().isScreenWideColorGamut() && attrs.getColorMode() == 1) {
                        wideGamut = true;
                    }
                    this.mAttachInfo.mThreadedRenderer = ThreadedRenderer.create(this.mContext, translucent, attrs.getTitle().toString());
                    this.mAttachInfo.mThreadedRenderer.setWideGamut(wideGamut);
                    updateForceDarkMode();
                    if (this.mAttachInfo.mThreadedRenderer != null) {
                        View.AttachInfo attachInfo = this.mAttachInfo;
                        this.mAttachInfo.mHardwareAccelerationRequested = true;
                        attachInfo.mHardwareAccelerated = true;
                    }
                }
            }
        }
    }

    private int getNightMode() {
        return this.mContext.getResources().getConfiguration().uiMode & 48;
    }

    private void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            boolean z = false;
            boolean useAutoDark = getNightMode() == 32;
            if (useAutoDark) {
                boolean forceDarkAllowedDefault = SystemProperties.getBoolean(ThreadedRenderer.DEBUG_FORCE_DARK, false);
                TypedArray a = this.mContext.obtainStyledAttributes(R.styleable.Theme);
                if (a.getBoolean(279, true) && a.getBoolean(278, forceDarkAllowedDefault)) {
                    z = true;
                }
                useAutoDark = z;
                a.recycle();
            }
            if (this.mAttachInfo.mThreadedRenderer.setForceDark(useAutoDark)) {
                invalidateWorld(this.mView);
            }
        }
    }

    @UnsupportedAppUsage
    public View getView() {
        return this.mView;
    }

    /* access modifiers changed from: package-private */
    public final WindowLeaked getLocation() {
        return this.mLocation;
    }

    /* access modifiers changed from: package-private */
    public void setLayoutParams(WindowManager.LayoutParams attrs, boolean newView) {
        synchronized (this) {
            int oldInsetLeft = this.mWindowAttributes.surfaceInsets.left;
            int oldInsetTop = this.mWindowAttributes.surfaceInsets.top;
            int oldInsetRight = this.mWindowAttributes.surfaceInsets.right;
            int oldInsetBottom = this.mWindowAttributes.surfaceInsets.bottom;
            int oldSoftInputMode = this.mWindowAttributes.softInputMode;
            boolean oldHasManualSurfaceInsets = this.mWindowAttributes.hasManualSurfaceInsets;
            this.mClientWindowLayoutFlags = attrs.flags;
            int compatibleWindowFlag = this.mWindowAttributes.privateFlags & 128;
            attrs.systemUiVisibility = this.mWindowAttributes.systemUiVisibility;
            attrs.subtreeSystemUiVisibility = this.mWindowAttributes.subtreeSystemUiVisibility;
            this.mWindowAttributesChangesFlag = this.mWindowAttributes.copyFrom(attrs);
            if ((this.mWindowAttributesChangesFlag & 524288) != 0) {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if ((this.mWindowAttributesChangesFlag & 1) != 0) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            if (this.mWindowAttributes.packageName == null) {
                this.mWindowAttributes.packageName = this.mBasePackageName;
            }
            this.mWindowAttributes.privateFlags |= compatibleWindowFlag;
            if (this.mWindowAttributes.preservePreviousSurfaceInsets) {
                this.mWindowAttributes.surfaceInsets.set(oldInsetLeft, oldInsetTop, oldInsetRight, oldInsetBottom);
                this.mWindowAttributes.hasManualSurfaceInsets = oldHasManualSurfaceInsets;
            } else if (!(this.mWindowAttributes.surfaceInsets.left == oldInsetLeft && this.mWindowAttributes.surfaceInsets.top == oldInsetTop && this.mWindowAttributes.surfaceInsets.right == oldInsetRight && this.mWindowAttributes.surfaceInsets.bottom == oldInsetBottom)) {
                this.mNeedsRendererSetup = true;
            }
            applyKeepScreenOnFlag(this.mWindowAttributes);
            if (newView) {
                this.mSoftInputMode = attrs.softInputMode;
                requestLayout();
            }
            if ((attrs.softInputMode & 240) == 0) {
                this.mWindowAttributes.softInputMode = (this.mWindowAttributes.softInputMode & TrafficStats.TAG_SYSTEM_IMPERSONATION_RANGE_END) | (oldSoftInputMode & 240);
            }
            this.mWindowAttributesChanged = true;
            scheduleTraversals();
        }
    }

    /* access modifiers changed from: package-private */
    public void handleAppVisibility(boolean visible) {
        if (this.mAppVisible != visible) {
            this.mAppVisible = visible;
            this.mAppVisibilityChanged = true;
            scheduleTraversals();
            if (!this.mAppVisible) {
                WindowManagerGlobal.trimForeground();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        scheduleTraversals();
    }

    public void onMovedToDisplay(int displayId, Configuration config) {
        if (this.mDisplay.getDisplayId() != displayId) {
            updateInternalDisplay(displayId, this.mView.getResources());
            this.mAttachInfo.mDisplayState = this.mDisplay.getState();
            this.mView.dispatchMovedToDisplay(this.mDisplay, config);
        }
    }

    private void updateInternalDisplay(int displayId, Resources resources) {
        Display preferredDisplay = ResourcesManager.getInstance().getAdjustedDisplay(displayId, resources);
        if (preferredDisplay == null) {
            Slog.w(TAG, "Cannot get desired display with Id: " + displayId);
            this.mDisplay = ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = preferredDisplay;
        }
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    /* access modifiers changed from: package-private */
    public void pokeDrawLockIfNeeded() {
        int displayState = this.mAttachInfo.mDisplayState;
        if (this.mView != null && this.mAdded && this.mTraversalScheduled) {
            if (displayState == 3 || displayState == 4) {
                try {
                    this.mWindowSession.pokeDrawLock(this.mWindow);
                } catch (RemoteException e) {
                }
            }
        }
    }

    public void requestFitSystemWindows() {
        checkThread();
        this.mApplyInsetsRequested = true;
        scheduleTraversals();
    }

    /* access modifiers changed from: package-private */
    public void notifyInsetsChanged() {
        if (sNewInsetsMode != 0) {
            this.mApplyInsetsRequested = true;
            if (!this.mIsInTraversal) {
                scheduleTraversals();
            }
        }
    }

    public void requestLayout() {
        if (!this.mHandlingLayoutInLayoutRequest) {
            checkThread();
            this.mLayoutRequested = true;
            scheduleTraversals();
        }
    }

    public boolean isLayoutRequested() {
        return this.mLayoutRequested;
    }

    public void onDescendantInvalidated(View child, View descendant) {
        if ((descendant.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        invalidate();
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void invalidate() {
        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
        if (!this.mWillDrawSoon) {
            scheduleTraversals();
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateWorld(View view) {
        view.invalidate();
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                invalidateWorld(parent.getChildAt(i));
            }
        }
    }

    public void invalidateChild(View child, Rect dirty) {
        invalidateChildInParent((int[]) null, dirty);
    }

    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        checkThread();
        if (dirty == null) {
            invalidate();
            return null;
        } else if (dirty.isEmpty() && !this.mIsAnimating) {
            return null;
        } else {
            if (!(this.mCurScrollY == 0 && this.mTranslator == null)) {
                this.mTempRect.set(dirty);
                dirty = this.mTempRect;
                if (this.mCurScrollY != 0) {
                    dirty.offset(0, -this.mCurScrollY);
                }
                if (this.mTranslator != null) {
                    this.mTranslator.translateRectInAppWindowToScreen(dirty);
                }
                if (this.mAttachInfo.mScalingRequired) {
                    dirty.inset(-1, -1);
                }
            }
            invalidateRectOnScreen(dirty);
            return null;
        }
    }

    private void invalidateRectOnScreen(Rect dirty) {
        Rect localDirty = this.mDirty;
        localDirty.union(dirty.left, dirty.top, dirty.right, dirty.bottom);
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean intersected = localDirty.intersect(0, 0, (int) ((((float) this.mWidth) * appScale) + 0.5f), (int) ((((float) this.mHeight) * appScale) + 0.5f));
        if (!intersected) {
            localDirty.setEmpty();
        }
        if (this.mWillDrawSoon) {
            return;
        }
        if (intersected || this.mIsAnimating) {
            scheduleTraversals();
        }
    }

    public void setIsAmbientMode(boolean ambient) {
        this.mIsAmbientMode = ambient;
    }

    /* access modifiers changed from: package-private */
    public void addWindowStoppedCallback(WindowStoppedCallback c) {
        this.mWindowStoppedCallbacks.add(c);
    }

    /* access modifiers changed from: package-private */
    public void removeWindowStoppedCallback(WindowStoppedCallback c) {
        this.mWindowStoppedCallbacks.remove(c);
    }

    /* access modifiers changed from: package-private */
    public void setWindowStopped(boolean stopped) {
        checkThread();
        if (this.mStopped != stopped) {
            this.mStopped = stopped;
            ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
            if (renderer != null) {
                renderer.setStopped(this.mStopped);
            }
            if (!this.mStopped) {
                this.mNewSurfaceNeeded = true;
                scheduleTraversals();
            } else if (renderer != null) {
                renderer.destroyHardwareResources(this.mView);
            }
            for (int i = 0; i < this.mWindowStoppedCallbacks.size(); i++) {
                this.mWindowStoppedCallbacks.get(i).windowStopped(stopped);
            }
            if (this.mStopped != 0) {
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    notifySurfaceDestroyed();
                }
                destroySurface();
            }
        }
    }

    public void createBoundsSurface(int zOrderLayer) {
        if (this.mSurfaceSession == null) {
            this.mSurfaceSession = new SurfaceSession();
        }
        if (this.mBoundsSurfaceControl == null || !this.mBoundsSurface.isValid()) {
            SurfaceControl.Builder builder = new SurfaceControl.Builder(this.mSurfaceSession);
            this.mBoundsSurfaceControl = builder.setName("Bounds for - " + getTitle().toString()).setParent(this.mSurfaceControl).build();
            setBoundsSurfaceCrop();
            this.mTransaction.setLayer(this.mBoundsSurfaceControl, zOrderLayer).show(this.mBoundsSurfaceControl).apply();
            this.mBoundsSurface.copyFrom(this.mBoundsSurfaceControl);
        }
    }

    private void setBoundsSurfaceCrop() {
        this.mTempBoundsRect.set(this.mWinFrame);
        this.mTempBoundsRect.offsetTo(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top);
        this.mTransaction.setWindowCrop(this.mBoundsSurfaceControl, this.mTempBoundsRect);
    }

    private void updateBoundsSurface() {
        if (this.mBoundsSurfaceControl != null && this.mSurface.isValid()) {
            setBoundsSurfaceCrop();
            this.mTransaction.deferTransactionUntilSurface(this.mBoundsSurfaceControl, this.mSurface, this.mSurface.getNextFrameNumber()).apply();
        }
    }

    private void destroySurface() {
        this.mSurface.release();
        this.mSurfaceControl.release();
        this.mSurfaceSession = null;
        if (this.mBoundsSurfaceControl != null) {
            this.mBoundsSurfaceControl.remove();
            this.mBoundsSurface.release();
            this.mBoundsSurfaceControl = null;
        }
    }

    public void setPausedForTransition(boolean paused) {
        this.mPausedForTransition = paused;
    }

    public ViewParent getParent() {
        return null;
    }

    public boolean getChildVisibleRect(View child, Rect r, Point offset) {
        if (child == this.mView) {
            return r.intersect(0, 0, this.mWidth, this.mHeight);
        }
        throw new RuntimeException("child is not mine, honest!");
    }

    public void bringChildToFront(View child) {
    }

    /* access modifiers changed from: package-private */
    public int getHostVisibility() {
        if (this.mAppVisible || this.mForceDecorViewVisibility) {
            return this.mView.getVisibility();
        }
        return 8;
    }

    public void requestTransitionStart(LayoutTransition transition) {
        if (this.mPendingTransitions == null || !this.mPendingTransitions.contains(transition)) {
            if (this.mPendingTransitions == null) {
                this.mPendingTransitions = new ArrayList<>();
            }
            this.mPendingTransitions.add(transition);
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyRendererOfFramePending() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyFramePending();
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void scheduleTraversals() {
        if (!this.mTraversalScheduled) {
            this.mTraversalScheduled = true;
            this.mTraversalBarrier = this.mHandler.getLooper().getQueue().postSyncBarrier();
            this.mChoreographer.postCallback(3, this.mTraversalRunnable, (Object) null);
            if (!this.mUnbufferedInputDispatch) {
                scheduleConsumeBatchedInput();
            }
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }

    /* access modifiers changed from: package-private */
    public void unscheduleTraversals() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            this.mChoreographer.removeCallbacks(3, this.mTraversalRunnable, (Object) null);
        }
    }

    /* access modifiers changed from: package-private */
    public void doTraversal() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            if (this.mProfile) {
                Debug.startMethodTracing("ViewAncestor");
            }
            performTraversals();
            if (this.mProfile) {
                Debug.stopMethodTracing();
                this.mProfile = false;
            }
        }
    }

    private void applyKeepScreenOnFlag(WindowManager.LayoutParams params) {
        if (this.mAttachInfo.mKeepScreenOn) {
            params.flags |= 128;
        } else {
            params.flags = (params.flags & -129) | (this.mClientWindowLayoutFlags & 128);
        }
    }

    private boolean collectViewAttributes() {
        if (this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mAttachInfo.mRecomputeGlobalAttributes = false;
            boolean oldScreenOn = this.mAttachInfo.mKeepScreenOn;
            this.mAttachInfo.mKeepScreenOn = false;
            this.mAttachInfo.mSystemUiVisibility = 0;
            this.mAttachInfo.mHasSystemUiListeners = false;
            this.mView.dispatchCollectViewAttributes(this.mAttachInfo, 0);
            this.mAttachInfo.mSystemUiVisibility &= ~this.mAttachInfo.mDisabledSystemUiVisibility;
            WindowManager.LayoutParams params = this.mWindowAttributes;
            this.mAttachInfo.mSystemUiVisibility |= getImpliedSystemUiVisibility(params);
            if (!(this.mAttachInfo.mKeepScreenOn == oldScreenOn && this.mAttachInfo.mSystemUiVisibility == params.subtreeSystemUiVisibility && this.mAttachInfo.mHasSystemUiListeners == params.hasSystemUiListeners)) {
                applyKeepScreenOnFlag(params);
                params.subtreeSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                params.hasSystemUiListeners = this.mAttachInfo.mHasSystemUiListeners;
                this.mView.dispatchWindowSystemUiVisiblityChanged(this.mAttachInfo.mSystemUiVisibility);
                return true;
            }
        }
        return false;
    }

    private int getImpliedSystemUiVisibility(WindowManager.LayoutParams params) {
        int vis = 0;
        if ((params.flags & 67108864) != 0) {
            vis = 0 | 1280;
        }
        if ((params.flags & 134217728) != 0) {
            return vis | 768;
        }
        return vis;
    }

    private boolean measureHierarchy(View host, WindowManager.LayoutParams lp, Resources res, int desiredWindowWidth, int desiredWindowHeight) {
        boolean goodMeasure = false;
        if (lp.width == -2) {
            DisplayMetrics packageMetrics = res.getDisplayMetrics();
            res.getValue((int) R.dimen.config_prefDialogWidth, this.mTmpValue, true);
            int baseSize = 0;
            if (this.mTmpValue.type == 5) {
                baseSize = (int) this.mTmpValue.getDimension(packageMetrics);
            }
            if (baseSize != 0 && desiredWindowWidth > baseSize) {
                int childWidthMeasureSpec = getRootMeasureSpec(baseSize, lp.width);
                int childHeightMeasureSpec = getRootMeasureSpec(desiredWindowHeight, lp.height);
                performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
                if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                    goodMeasure = true;
                } else {
                    performMeasure(getRootMeasureSpec((baseSize + desiredWindowWidth) / 2, lp.width), childHeightMeasureSpec);
                    if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                        goodMeasure = true;
                    }
                }
            }
        }
        if (goodMeasure) {
            return false;
        }
        performMeasure(getRootMeasureSpec(desiredWindowWidth, lp.width), getRootMeasureSpec(desiredWindowHeight, lp.height));
        if (this.mWidth == host.getMeasuredWidth() && this.mHeight == host.getMeasuredHeight()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void transformMatrixToGlobal(Matrix m) {
        m.preTranslate((float) this.mAttachInfo.mWindowLeft, (float) this.mAttachInfo.mWindowTop);
    }

    /* access modifiers changed from: package-private */
    public void transformMatrixToLocal(Matrix m) {
        m.postTranslate((float) (-this.mAttachInfo.mWindowLeft), (float) (-this.mAttachInfo.mWindowTop));
    }

    /* access modifiers changed from: package-private */
    public WindowInsets getWindowInsets(boolean forceConstruct) {
        if (this.mLastWindowInsets == null || forceConstruct) {
            this.mDispatchContentInsets.set(this.mAttachInfo.mContentInsets);
            this.mDispatchStableInsets.set(this.mAttachInfo.mStableInsets);
            this.mDispatchDisplayCutout = this.mAttachInfo.mDisplayCutout.get();
            Rect contentInsets = this.mDispatchContentInsets;
            Rect stableInsets = this.mDispatchStableInsets;
            DisplayCutout displayCutout = this.mDispatchDisplayCutout;
            if (!forceConstruct && (!this.mPendingContentInsets.equals(contentInsets) || !this.mPendingStableInsets.equals(stableInsets) || !this.mPendingDisplayCutout.get().equals(displayCutout))) {
                contentInsets = this.mPendingContentInsets;
                stableInsets = this.mPendingStableInsets;
                displayCutout = this.mPendingDisplayCutout.get();
            }
            DisplayCutout displayCutout2 = displayCutout;
            Rect outsets = this.mAttachInfo.mOutsets;
            if (outsets.left > 0 || outsets.top > 0 || outsets.right > 0 || outsets.bottom > 0) {
                contentInsets = new Rect(contentInsets.left + outsets.left, contentInsets.top + outsets.top, contentInsets.right + outsets.right, contentInsets.bottom + outsets.bottom);
            }
            Rect contentInsets2 = ensureInsetsNonNegative(contentInsets, "content");
            Rect stableInsets2 = ensureInsetsNonNegative(stableInsets, "stable");
            this.mLastWindowInsets = this.mInsetsController.calculateInsets(this.mContext.getResources().getConfiguration().isScreenRound(), this.mAttachInfo.mAlwaysConsumeSystemBars, displayCutout2, contentInsets2, stableInsets2, this.mWindowAttributes.softInputMode);
        }
        return this.mLastWindowInsets;
    }

    private Rect ensureInsetsNonNegative(Rect insets, String kind) {
        if (insets.left >= 0 && insets.top >= 0 && insets.right >= 0 && insets.bottom >= 0) {
            return insets;
        }
        String str = this.mTag;
        Log.wtf(str, "Negative " + kind + "Insets: " + insets + ", mFirst=" + this.mFirst);
        return new Rect(Math.max(0, insets.left), Math.max(0, insets.top), Math.max(0, insets.right), Math.max(0, insets.bottom));
    }

    /* access modifiers changed from: package-private */
    public void dispatchApplyInsets(View host) {
        Trace.traceBegin(8, "dispatchApplyInsets");
        boolean dispatchCutout = true;
        WindowInsets insets = getWindowInsets(true);
        if (this.mWindowAttributes.layoutInDisplayCutoutMode != 1) {
            dispatchCutout = false;
        }
        if (!dispatchCutout) {
            insets = insets.consumeDisplayCutout();
        }
        host.dispatchApplyWindowInsets(insets);
        Trace.traceEnd(8);
    }

    /* access modifiers changed from: package-private */
    public InsetsController getInsetsController() {
        return this.mInsetsController;
    }

    private static boolean shouldUseDisplaySize(WindowManager.LayoutParams lp) {
        return lp.type == 2014 || lp.type == 2011 || lp.type == 2020;
    }

    private int dipToPx(int dip) {
        return (int) ((this.mContext.getResources().getDisplayMetrics().density * ((float) dip)) + 0.5f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:174:0x030a, code lost:
        if (r23.height() != r7.mHeight) goto L_0x030f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x020e  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x021b  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0223  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x023f  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0277  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x029a  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02cb  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02e2  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x030d  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x031c  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x031e  */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x0335  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x0337  */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x0345  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x0351 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:210:0x0390  */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x039d  */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x03a3  */
    /* JADX WARNING: Removed duplicated region for block: B:221:0x03b0  */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x03c3 A[SYNTHETIC, Splitter:B:226:0x03c3] */
    /* JADX WARNING: Removed duplicated region for block: B:249:0x042a  */
    /* JADX WARNING: Removed duplicated region for block: B:256:0x043f A[SYNTHETIC, Splitter:B:256:0x043f] */
    /* JADX WARNING: Removed duplicated region for block: B:265:0x04b1 A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x04b3 A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x04c0 A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:270:0x04c2 A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:274:0x04d1 A[SYNTHETIC, Splitter:B:274:0x04d1] */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x04dc A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:279:0x04ea A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:281:0x04f8 A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:283:0x0506 A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:296:0x0547 A[SYNTHETIC, Splitter:B:296:0x0547] */
    /* JADX WARNING: Removed duplicated region for block: B:303:0x0563 A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0565 A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x056c A[Catch:{ RemoteException -> 0x044d }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:328:0x05b2 A[SYNTHETIC, Splitter:B:328:0x05b2] */
    /* JADX WARNING: Removed duplicated region for block: B:366:0x0626  */
    /* JADX WARNING: Removed duplicated region for block: B:367:0x0628  */
    /* JADX WARNING: Removed duplicated region for block: B:370:0x062d  */
    /* JADX WARNING: Removed duplicated region for block: B:371:0x062f  */
    /* JADX WARNING: Removed duplicated region for block: B:374:0x0634 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:381:0x063f A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:395:0x066e A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:396:0x0671 A[Catch:{ RemoteException -> 0x06e0 }] */
    /* JADX WARNING: Removed duplicated region for block: B:412:0x06b8 A[Catch:{ RemoteException -> 0x06de }] */
    /* JADX WARNING: Removed duplicated region for block: B:415:0x06c9 A[Catch:{ RemoteException -> 0x06de }] */
    /* JADX WARNING: Removed duplicated region for block: B:432:0x0738  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:464:0x07f3  */
    /* JADX WARNING: Removed duplicated region for block: B:483:0x0835  */
    /* JADX WARNING: Removed duplicated region for block: B:484:0x0837  */
    /* JADX WARNING: Removed duplicated region for block: B:496:0x087f  */
    /* JADX WARNING: Removed duplicated region for block: B:499:0x0896  */
    /* JADX WARNING: Removed duplicated region for block: B:501:0x08a9  */
    /* JADX WARNING: Removed duplicated region for block: B:505:0x08b5  */
    /* JADX WARNING: Removed duplicated region for block: B:511:0x08c2  */
    /* JADX WARNING: Removed duplicated region for block: B:512:0x08c4  */
    /* JADX WARNING: Removed duplicated region for block: B:517:0x08cf  */
    /* JADX WARNING: Removed duplicated region for block: B:518:0x08d1  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:521:0x08d5  */
    /* JADX WARNING: Removed duplicated region for block: B:534:0x0945  */
    /* JADX WARNING: Removed duplicated region for block: B:536:0x0953  */
    /* JADX WARNING: Removed duplicated region for block: B:550:0x09bb  */
    /* JADX WARNING: Removed duplicated region for block: B:572:0x0a01 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:578:0x0a0c  */
    /* JADX WARNING: Removed duplicated region for block: B:579:0x0a0e  */
    /* JADX WARNING: Removed duplicated region for block: B:581:0x0a11  */
    /* JADX WARNING: Removed duplicated region for block: B:582:0x0a15 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:590:0x0a26  */
    /* JADX WARNING: Removed duplicated region for block: B:593:0x0a30  */
    /* JADX WARNING: Removed duplicated region for block: B:595:0x0a35  */
    /* JADX WARNING: Removed duplicated region for block: B:609:0x0aa0  */
    /* JADX WARNING: Removed duplicated region for block: B:616:0x0ab7  */
    /* JADX WARNING: Removed duplicated region for block: B:626:0x0ae3  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x014b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void performTraversals() {
        /*
            r58 = this;
            r7 = r58
            android.view.View r8 = r7.mView
            if (r8 == 0) goto L_0x0b15
            boolean r0 = r7.mAdded
            if (r0 != 0) goto L_0x000c
            goto L_0x0b15
        L_0x000c:
            r9 = 1
            r7.mIsInTraversal = r9
            r7.mWillDrawSoon = r9
            r0 = 0
            r1 = 0
            android.view.WindowManager$LayoutParams r10 = r7.mWindowAttributes
            int r11 = r58.getHostVisibility()
            boolean r2 = r7.mFirst
            r12 = 0
            if (r2 != 0) goto L_0x002c
            int r2 = r7.mViewVisibility
            if (r2 != r11) goto L_0x002a
            boolean r2 = r7.mNewSurfaceNeeded
            if (r2 != 0) goto L_0x002a
            boolean r2 = r7.mAppVisibilityChanged
            if (r2 == 0) goto L_0x002c
        L_0x002a:
            r2 = r9
            goto L_0x002d
        L_0x002c:
            r2 = r12
        L_0x002d:
            r13 = r2
            r7.mAppVisibilityChanged = r12
            boolean r2 = r7.mFirst
            if (r2 != 0) goto L_0x0044
            int r2 = r7.mViewVisibility
            if (r2 != 0) goto L_0x003a
            r2 = r9
            goto L_0x003b
        L_0x003a:
            r2 = r12
        L_0x003b:
            if (r11 != 0) goto L_0x003f
            r3 = r9
            goto L_0x0040
        L_0x003f:
            r3 = r12
        L_0x0040:
            if (r2 == r3) goto L_0x0044
            r2 = r9
            goto L_0x0045
        L_0x0044:
            r2 = r12
        L_0x0045:
            r14 = r2
            r2 = 0
            boolean r3 = r7.mWindowAttributesChanged
            if (r3 == 0) goto L_0x004f
            r7.mWindowAttributesChanged = r12
            r1 = 1
            r2 = r10
        L_0x004f:
            r15 = r1
            android.view.Display r1 = r7.mDisplay
            android.view.DisplayAdjustments r1 = r1.getDisplayAdjustments()
            android.content.res.CompatibilityInfo r16 = r1.getCompatibilityInfo()
            boolean r1 = r16.supportsScreen()
            boolean r3 = r7.mLastInCompatMode
            if (r1 != r3) goto L_0x007c
            r2 = r10
            r7.mFullRedrawNeeded = r9
            r7.mLayoutRequested = r9
            boolean r1 = r7.mLastInCompatMode
            if (r1 == 0) goto L_0x0074
            int r1 = r2.privateFlags
            r1 = r1 & -129(0xffffffffffffff7f, float:NaN)
            r2.privateFlags = r1
            r7.mLastInCompatMode = r12
            goto L_0x007c
        L_0x0074:
            int r1 = r2.privateFlags
            r1 = r1 | 128(0x80, float:1.794E-43)
            r2.privateFlags = r1
            r7.mLastInCompatMode = r9
        L_0x007c:
            r17 = r2
            r7.mWindowAttributesChangesFlag = r12
            android.graphics.Rect r6 = r7.mWinFrame
            boolean r1 = r7.mFirst
            if (r1 == 0) goto L_0x00e8
            r7.mFullRedrawNeeded = r9
            r7.mLayoutRequested = r9
            android.content.Context r1 = r7.mContext
            android.content.res.Resources r1 = r1.getResources()
            android.content.res.Configuration r1 = r1.getConfiguration()
            boolean r2 = shouldUseDisplaySize(r10)
            if (r2 == 0) goto L_0x00a9
            android.graphics.Point r2 = new android.graphics.Point
            r2.<init>()
            android.view.Display r3 = r7.mDisplay
            r3.getRealSize(r2)
            int r3 = r2.x
            int r2 = r2.y
            goto L_0x00b5
        L_0x00a9:
            android.graphics.Rect r2 = r7.mWinFrame
            int r3 = r2.width()
            android.graphics.Rect r2 = r7.mWinFrame
            int r2 = r2.height()
        L_0x00b5:
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            r4.mUse32BitDrawingCache = r9
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            r4.mWindowVisibility = r11
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            r4.mRecomputeGlobalAttributes = r12
            android.content.res.Configuration r4 = r7.mLastConfigurationFromResources
            r4.setTo(r1)
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            int r4 = r4.mSystemUiVisibility
            r7.mLastSystemUiVisibility = r4
            int r4 = r7.mViewLayoutDirectionInitial
            r5 = 2
            if (r4 != r5) goto L_0x00d8
            int r4 = r1.getLayoutDirection()
            r8.setLayoutDirection(r4)
        L_0x00d8:
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            r8.dispatchAttachedToWindow(r4, r12)
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            android.view.ViewTreeObserver r4 = r4.mTreeObserver
            r4.dispatchOnWindowAttachedChange(r9)
            r7.dispatchApplyInsets(r8)
            goto L_0x00fd
        L_0x00e8:
            int r3 = r6.width()
            int r2 = r6.height()
            int r1 = r7.mWidth
            if (r3 != r1) goto L_0x00f8
            int r1 = r7.mHeight
            if (r2 == r1) goto L_0x00fd
        L_0x00f8:
            r7.mFullRedrawNeeded = r9
            r7.mLayoutRequested = r9
            r0 = 1
        L_0x00fd:
            if (r13 == 0) goto L_0x0122
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            r1.mWindowVisibility = r11
            r8.dispatchWindowVisibilityChanged(r11)
            if (r14 == 0) goto L_0x0110
            if (r11 != 0) goto L_0x010c
            r1 = r9
            goto L_0x010d
        L_0x010c:
            r1 = r12
        L_0x010d:
            r8.dispatchVisibilityAggregated(r1)
        L_0x0110:
            if (r11 != 0) goto L_0x0116
            boolean r1 = r7.mNewSurfaceNeeded
            if (r1 == 0) goto L_0x011c
        L_0x0116:
            r58.endDragResizing()
            r58.destroyHardwareResources()
        L_0x011c:
            r1 = 8
            if (r11 != r1) goto L_0x0122
            r7.mHasHadWindowFocus = r12
        L_0x0122:
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            int r1 = r1.mWindowVisibility
            if (r1 == 0) goto L_0x012b
            r8.clearAccessibilityFocus()
        L_0x012b:
            android.view.HandlerActionQueue r1 = getRunQueue()
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            android.os.Handler r4 = r4.mHandler
            r1.executeActions(r4)
            r1 = 0
            boolean r4 = r7.mLayoutRequested
            if (r4 == 0) goto L_0x0145
            boolean r4 = r7.mStopped
            if (r4 == 0) goto L_0x0143
            boolean r4 = r7.mReportNextDraw
            if (r4 == 0) goto L_0x0145
        L_0x0143:
            r4 = r9
            goto L_0x0146
        L_0x0145:
            r4 = r12
        L_0x0146:
            r18 = r4
            r5 = -2
            if (r18 == 0) goto L_0x020e
            android.view.View r4 = r7.mView
            android.content.Context r4 = r4.getContext()
            android.content.res.Resources r19 = r4.getResources()
            boolean r4 = r7.mFirst
            if (r4 == 0) goto L_0x016c
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            boolean r12 = r7.mAddedTouchMode
            r12 = r12 ^ r9
            r4.mInTouchMode = r12
            boolean r4 = r7.mAddedTouchMode
            r7.ensureTouchModeLocally(r4)
        L_0x0165:
            r21 = r1
            r20 = r2
            r12 = r3
            goto L_0x01fc
        L_0x016c:
            android.graphics.Rect r4 = r7.mPendingOverscanInsets
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            android.graphics.Rect r12 = r12.mOverscanInsets
            boolean r4 = r4.equals(r12)
            if (r4 != 0) goto L_0x0179
            r1 = 1
        L_0x0179:
            android.graphics.Rect r4 = r7.mPendingContentInsets
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            android.graphics.Rect r12 = r12.mContentInsets
            boolean r4 = r4.equals(r12)
            if (r4 != 0) goto L_0x0186
            r1 = 1
        L_0x0186:
            android.graphics.Rect r4 = r7.mPendingStableInsets
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            android.graphics.Rect r12 = r12.mStableInsets
            boolean r4 = r4.equals(r12)
            if (r4 != 0) goto L_0x0193
            r1 = 1
        L_0x0193:
            android.view.DisplayCutout$ParcelableWrapper r4 = r7.mPendingDisplayCutout
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            android.view.DisplayCutout$ParcelableWrapper r12 = r12.mDisplayCutout
            boolean r4 = r4.equals(r12)
            if (r4 != 0) goto L_0x01a0
            r1 = 1
        L_0x01a0:
            android.graphics.Rect r4 = r7.mPendingVisibleInsets
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            android.graphics.Rect r12 = r12.mVisibleInsets
            boolean r4 = r4.equals(r12)
            if (r4 != 0) goto L_0x01b5
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            android.graphics.Rect r4 = r4.mVisibleInsets
            android.graphics.Rect r12 = r7.mPendingVisibleInsets
            r4.set(r12)
        L_0x01b5:
            android.graphics.Rect r4 = r7.mPendingOutsets
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            android.graphics.Rect r12 = r12.mOutsets
            boolean r4 = r4.equals(r12)
            if (r4 != 0) goto L_0x01c2
            r1 = 1
        L_0x01c2:
            boolean r4 = r7.mPendingAlwaysConsumeSystemBars
            android.view.View$AttachInfo r12 = r7.mAttachInfo
            boolean r12 = r12.mAlwaysConsumeSystemBars
            if (r4 == r12) goto L_0x01cb
            r1 = 1
        L_0x01cb:
            int r4 = r10.width
            if (r4 == r5) goto L_0x01d3
            int r4 = r10.height
            if (r4 != r5) goto L_0x0165
        L_0x01d3:
            r0 = 1
            boolean r4 = shouldUseDisplaySize(r10)
            if (r4 == 0) goto L_0x01ea
            android.graphics.Point r4 = new android.graphics.Point
            r4.<init>()
            android.view.Display r12 = r7.mDisplay
            r12.getRealSize(r4)
            int r3 = r4.x
            int r2 = r4.y
            goto L_0x0165
        L_0x01ea:
            android.content.res.Configuration r4 = r19.getConfiguration()
            int r12 = r4.screenWidthDp
            int r3 = r7.dipToPx(r12)
            int r12 = r4.screenHeightDp
            int r2 = r7.dipToPx(r12)
            goto L_0x0165
        L_0x01fc:
            r1 = r58
            r2 = r8
            r3 = r10
            r4 = r19
            r9 = r5
            r5 = r12
            r23 = r6
            r6 = r20
            boolean r1 = r1.measureHierarchy(r2, r3, r4, r5, r6)
            r0 = r0 | r1
            goto L_0x0215
        L_0x020e:
            r9 = r5
            r23 = r6
            r21 = r1
            r6 = r2
            r12 = r3
        L_0x0215:
            boolean r1 = r58.collectViewAttributes()
            if (r1 == 0) goto L_0x021d
            r17 = r10
        L_0x021d:
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            boolean r1 = r1.mForceReportNewAttributes
            if (r1 == 0) goto L_0x022a
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            r2 = 0
            r1.mForceReportNewAttributes = r2
            r17 = r10
        L_0x022a:
            boolean r1 = r7.mFirst
            if (r1 != 0) goto L_0x0234
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            boolean r1 = r1.mViewVisibilityChanged
            if (r1 == 0) goto L_0x0273
        L_0x0234:
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            r2 = 0
            r1.mViewVisibilityChanged = r2
            int r1 = r7.mSoftInputMode
            r1 = r1 & 240(0xf0, float:3.36E-43)
            if (r1 != 0) goto L_0x0273
            android.view.View$AttachInfo r2 = r7.mAttachInfo
            java.util.ArrayList<android.view.View> r2 = r2.mScrollContainers
            int r2 = r2.size()
            r3 = r1
            r1 = 0
        L_0x0249:
            if (r1 >= r2) goto L_0x0260
            android.view.View$AttachInfo r4 = r7.mAttachInfo
            java.util.ArrayList<android.view.View> r4 = r4.mScrollContainers
            java.lang.Object r4 = r4.get(r1)
            android.view.View r4 = (android.view.View) r4
            boolean r4 = r4.isShown()
            if (r4 == 0) goto L_0x025d
            r3 = 16
        L_0x025d:
            int r1 = r1 + 1
            goto L_0x0249
        L_0x0260:
            if (r3 != 0) goto L_0x0264
            r3 = 32
        L_0x0264:
            int r1 = r10.softInputMode
            r1 = r1 & 240(0xf0, float:3.36E-43)
            if (r1 == r3) goto L_0x0273
            int r1 = r10.softInputMode
            r1 = r1 & -241(0xffffffffffffff0f, float:NaN)
            r1 = r1 | r3
            r10.softInputMode = r1
            r17 = r10
        L_0x0273:
            r5 = r17
            if (r5 == 0) goto L_0x0296
            int r1 = r8.mPrivateFlags
            r1 = r1 & 512(0x200, float:7.175E-43)
            if (r1 == 0) goto L_0x0288
            int r1 = r5.format
            boolean r1 = android.graphics.PixelFormat.formatHasAlpha(r1)
            if (r1 != 0) goto L_0x0288
            r1 = -3
            r5.format = r1
        L_0x0288:
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            int r2 = r5.flags
            r3 = 33554432(0x2000000, float:9.403955E-38)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0293
            r2 = 1
            goto L_0x0294
        L_0x0293:
            r2 = 0
        L_0x0294:
            r1.mOverscanRequested = r2
        L_0x0296:
            boolean r1 = r7.mApplyInsetsRequested
            if (r1 == 0) goto L_0x02c3
            r1 = 0
            r7.mApplyInsetsRequested = r1
            android.view.View$AttachInfo r1 = r7.mAttachInfo
            boolean r1 = r1.mOverscanRequested
            r7.mLastOverscanRequested = r1
            r7.dispatchApplyInsets(r8)
            boolean r1 = r7.mLayoutRequested
            if (r1 == 0) goto L_0x02c3
            android.view.View r1 = r7.mView
            android.content.Context r1 = r1.getContext()
            android.content.res.Resources r4 = r1.getResources()
            r1 = r58
            r2 = r8
            r3 = r10
            r24 = r5
            r5 = r12
            r25 = r6
            boolean r1 = r1.measureHierarchy(r2, r3, r4, r5, r6)
            r0 = r0 | r1
            goto L_0x02c7
        L_0x02c3:
            r24 = r5
            r25 = r6
        L_0x02c7:
            r17 = r0
            if (r18 == 0) goto L_0x02ce
            r1 = 0
            r7.mLayoutRequested = r1
        L_0x02ce:
            if (r18 == 0) goto L_0x0311
            if (r17 == 0) goto L_0x0311
            int r0 = r7.mWidth
            int r1 = r8.getMeasuredWidth()
            if (r0 != r1) goto L_0x030d
            int r0 = r7.mHeight
            int r1 = r8.getMeasuredHeight()
            if (r0 != r1) goto L_0x030d
            int r0 = r10.width
            if (r0 != r9) goto L_0x02f8
            int r0 = r23.width()
            if (r0 >= r12) goto L_0x02f8
            int r0 = r23.width()
            int r1 = r7.mWidth
            if (r0 != r1) goto L_0x02f5
            goto L_0x02f8
        L_0x02f5:
            r9 = r25
            goto L_0x030f
        L_0x02f8:
            int r0 = r10.height
            if (r0 != r9) goto L_0x0311
            int r0 = r23.height()
            r9 = r25
            if (r0 >= r9) goto L_0x0313
            int r0 = r23.height()
            int r1 = r7.mHeight
            if (r0 == r1) goto L_0x0313
            goto L_0x030f
        L_0x030d:
            r9 = r25
        L_0x030f:
            r0 = 1
            goto L_0x0314
        L_0x0311:
            r9 = r25
        L_0x0313:
            r0 = 0
        L_0x0314:
            boolean r1 = r7.mDragResizing
            if (r1 == 0) goto L_0x031e
            int r1 = r7.mResizeMode
            if (r1 != 0) goto L_0x031e
            r1 = 1
            goto L_0x031f
        L_0x031e:
            r1 = 0
        L_0x031f:
            r0 = r0 | r1
            boolean r1 = r7.mActivityRelaunched
            r19 = r0 | r1
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            android.view.ViewTreeObserver r0 = r0.mTreeObserver
            boolean r0 = r0.hasComputeInternalInsetsListeners()
            if (r0 != 0) goto L_0x0337
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            boolean r0 = r0.mHasNonEmptyGivenInternalInsets
            if (r0 == 0) goto L_0x0335
            goto L_0x0337
        L_0x0335:
            r0 = 0
            goto L_0x0338
        L_0x0337:
            r0 = 1
        L_0x0338:
            r20 = r0
            r0 = 0
            r1 = 0
            r2 = 0
            android.view.Surface r3 = r7.mSurface
            int r6 = r3.getGenerationId()
            if (r11 != 0) goto L_0x0347
            r3 = 1
            goto L_0x0348
        L_0x0347:
            r3 = 0
        L_0x0348:
            r25 = r3
            boolean r5 = r7.mForceNextWindowRelayout
            r3 = 0
            boolean r4 = r7.mFirst
            if (r4 != 0) goto L_0x0381
            if (r19 != 0) goto L_0x0381
            if (r21 != 0) goto L_0x0381
            if (r13 != 0) goto L_0x0381
            r4 = r24
            if (r4 != 0) goto L_0x037a
            r26 = r0
            boolean r0 = r7.mForceNextWindowRelayout
            if (r0 == 0) goto L_0x0366
            r27 = r9
            r9 = r23
            goto L_0x0389
        L_0x0366:
            r27 = r9
            r9 = r23
            r7.maybeHandleWindowMove(r9)
            r49 = r3
            r40 = r4
            r42 = r5
            r51 = r9
            r32 = r12
            r12 = r6
            goto L_0x08b3
        L_0x037a:
            r26 = r0
            r27 = r9
            r9 = r23
            goto L_0x0389
        L_0x0381:
            r26 = r0
            r27 = r9
            r9 = r23
            r4 = r24
        L_0x0389:
            r28 = r1
            r1 = 0
            r7.mForceNextWindowRelayout = r1
            if (r25 == 0) goto L_0x039d
            if (r20 == 0) goto L_0x039a
            boolean r0 = r7.mFirst
            if (r0 != 0) goto L_0x0398
            if (r13 == 0) goto L_0x039a
        L_0x0398:
            r0 = 1
            goto L_0x039b
        L_0x039a:
            r0 = 0
        L_0x039b:
            r1 = r0
            goto L_0x039f
        L_0x039d:
            r1 = r26
        L_0x039f:
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            if (r0 == 0) goto L_0x03b0
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock
            r0.lock()
            r29 = r2
            r2 = 1
            r7.mDrawingAllowed = r2
            goto L_0x03b2
        L_0x03b0:
            r29 = r2
        L_0x03b2:
            r2 = 0
            r23 = 0
            android.view.Surface r0 = r7.mSurface
            boolean r0 = r0.isValid()
            r24 = r0
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06f6 }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x06f6 }
            if (r0 == 0) goto L_0x042a
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x0418 }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x0418 }
            boolean r0 = r0.pause()     // Catch:{ RemoteException -> 0x0418 }
            if (r0 == 0) goto L_0x03fc
            android.graphics.Rect r0 = r7.mDirty     // Catch:{ RemoteException -> 0x0418 }
            r30 = r2
            int r2 = r7.mWidth     // Catch:{ RemoteException -> 0x03ec }
            r31 = r3
            int r3 = r7.mHeight     // Catch:{ RemoteException -> 0x03de }
            r32 = r12
            r12 = 0
            r0.set(r12, r12, r2, r3)     // Catch:{ RemoteException -> 0x040c }
            goto L_0x0402
        L_0x03de:
            r0 = move-exception
            r32 = r12
            r44 = r1
            r40 = r4
            r42 = r5
            r12 = r6
            r46 = r28
            goto L_0x0706
        L_0x03ec:
            r0 = move-exception
            r31 = r3
            r32 = r12
            r44 = r1
            r40 = r4
            r42 = r5
            r12 = r6
            r46 = r28
            goto L_0x0706
        L_0x03fc:
            r30 = r2
            r31 = r3
            r32 = r12
        L_0x0402:
            android.view.Choreographer r0 = r7.mChoreographer     // Catch:{ RemoteException -> 0x040c }
            android.graphics.FrameInfo r0 = r0.mFrameInfo     // Catch:{ RemoteException -> 0x040c }
            r2 = 1
            r0.addFlags(r2)     // Catch:{ RemoteException -> 0x040c }
            goto L_0x0430
        L_0x040c:
            r0 = move-exception
            r44 = r1
            r40 = r4
            r42 = r5
            r12 = r6
            r46 = r28
            goto L_0x0706
        L_0x0418:
            r0 = move-exception
            r30 = r2
            r31 = r3
            r32 = r12
            r44 = r1
            r40 = r4
            r42 = r5
            r12 = r6
            r46 = r28
            goto L_0x0706
        L_0x042a:
            r30 = r2
            r31 = r3
            r32 = r12
        L_0x0430:
            int r0 = r7.relayoutWindow(r4, r11, r1)     // Catch:{ RemoteException -> 0x06eb }
            r12 = r0
            android.util.MergedConfiguration r0 = r7.mPendingMergedConfiguration     // Catch:{ RemoteException -> 0x06e0 }
            android.util.MergedConfiguration r2 = r7.mLastReportedMergedConfiguration     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            if (r0 != 0) goto L_0x0459
            android.util.MergedConfiguration r0 = r7.mPendingMergedConfiguration     // Catch:{ RemoteException -> 0x044d }
            boolean r2 = r7.mFirst     // Catch:{ RemoteException -> 0x044d }
            r3 = 1
            r2 = r2 ^ r3
            r3 = -1
            r7.performConfigurationChange(r0, r2, r3)     // Catch:{ RemoteException -> 0x044d }
            r2 = 1
            r29 = r2
            goto L_0x0459
        L_0x044d:
            r0 = move-exception
            r44 = r1
        L_0x0450:
            r40 = r4
            r42 = r5
            r46 = r12
            r12 = r6
            goto L_0x0706
        L_0x0459:
            android.graphics.Rect r0 = r7.mPendingOverscanInsets     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r2.mOverscanInsets     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 1
            r0 = r0 ^ r2
            r26 = r0
            android.graphics.Rect r0 = r7.mPendingContentInsets     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r2.mContentInsets     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 1
            r0 = r0 ^ r2
            r23 = r0
            android.graphics.Rect r0 = r7.mPendingVisibleInsets     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r2.mVisibleInsets     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 1
            r0 = r0 ^ r2
            r28 = r0
            android.graphics.Rect r0 = r7.mPendingStableInsets     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r2.mStableInsets     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 1
            r0 = r0 ^ r2
            r33 = r0
            android.view.DisplayCutout$ParcelableWrapper r0 = r7.mPendingDisplayCutout     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.view.DisplayCutout$ParcelableWrapper r2 = r2.mDisplayCutout     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 1
            r0 = r0 ^ r2
            r34 = r0
            android.graphics.Rect r0 = r7.mPendingOutsets     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r2.mOutsets     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 1
            r0 = r0 ^ r2
            r35 = r0
            r0 = r12 & 32
            if (r0 == 0) goto L_0x04b3
            r0 = 1
            goto L_0x04b4
        L_0x04b3:
            r0 = 0
        L_0x04b4:
            r31 = r0
            r15 = r15 | r31
            boolean r0 = r7.mPendingAlwaysConsumeSystemBars     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            boolean r2 = r2.mAlwaysConsumeSystemBars     // Catch:{ RemoteException -> 0x06e0 }
            if (r0 == r2) goto L_0x04c2
            r0 = 1
            goto L_0x04c3
        L_0x04c2:
            r0 = 0
        L_0x04c3:
            r36 = r0
            int r0 = r10.getColorMode()     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r7.hasColorModeChanged(r0)     // Catch:{ RemoteException -> 0x06e0 }
            r37 = r0
            if (r23 == 0) goto L_0x04da
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r0 = r0.mContentInsets     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r2 = r7.mPendingContentInsets     // Catch:{ RemoteException -> 0x044d }
            r0.set(r2)     // Catch:{ RemoteException -> 0x044d }
        L_0x04da:
            if (r26 == 0) goto L_0x04e8
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r0 = r0.mOverscanInsets     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r2 = r7.mPendingOverscanInsets     // Catch:{ RemoteException -> 0x044d }
            r0.set(r2)     // Catch:{ RemoteException -> 0x044d }
            r0 = 1
            r23 = r0
        L_0x04e8:
            if (r33 == 0) goto L_0x04f6
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r0 = r0.mStableInsets     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r2 = r7.mPendingStableInsets     // Catch:{ RemoteException -> 0x044d }
            r0.set(r2)     // Catch:{ RemoteException -> 0x044d }
            r0 = 1
            r23 = r0
        L_0x04f6:
            if (r34 == 0) goto L_0x0504
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.DisplayCutout$ParcelableWrapper r0 = r0.mDisplayCutout     // Catch:{ RemoteException -> 0x044d }
            android.view.DisplayCutout$ParcelableWrapper r2 = r7.mPendingDisplayCutout     // Catch:{ RemoteException -> 0x044d }
            r0.set((android.view.DisplayCutout.ParcelableWrapper) r2)     // Catch:{ RemoteException -> 0x044d }
            r0 = 1
            r23 = r0
        L_0x0504:
            if (r36 == 0) goto L_0x050f
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            boolean r2 = r7.mPendingAlwaysConsumeSystemBars     // Catch:{ RemoteException -> 0x044d }
            r0.mAlwaysConsumeSystemBars = r2     // Catch:{ RemoteException -> 0x044d }
            r0 = 1
            r23 = r0
        L_0x050f:
            if (r23 != 0) goto L_0x0527
            int r0 = r7.mLastSystemUiVisibility     // Catch:{ RemoteException -> 0x044d }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            int r2 = r2.mSystemUiVisibility     // Catch:{ RemoteException -> 0x044d }
            if (r0 != r2) goto L_0x0527
            boolean r0 = r7.mApplyInsetsRequested     // Catch:{ RemoteException -> 0x044d }
            if (r0 != 0) goto L_0x0527
            boolean r0 = r7.mLastOverscanRequested     // Catch:{ RemoteException -> 0x044d }
            android.view.View$AttachInfo r2 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            boolean r2 = r2.mOverscanRequested     // Catch:{ RemoteException -> 0x044d }
            if (r0 != r2) goto L_0x0527
            if (r35 == 0) goto L_0x0545
        L_0x0527:
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            int r0 = r0.mSystemUiVisibility     // Catch:{ RemoteException -> 0x06e0 }
            r7.mLastSystemUiVisibility = r0     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.mOverscanRequested     // Catch:{ RemoteException -> 0x06e0 }
            r7.mLastOverscanRequested = r0     // Catch:{ RemoteException -> 0x06e0 }
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r0 = r0.mOutsets     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r7.mPendingOutsets     // Catch:{ RemoteException -> 0x06e0 }
            r0.set(r2)     // Catch:{ RemoteException -> 0x06e0 }
            r2 = 0
            r7.mApplyInsetsRequested = r2     // Catch:{ RemoteException -> 0x06e0 }
            r7.dispatchApplyInsets(r8)     // Catch:{ RemoteException -> 0x06e0 }
            r0 = 1
            r23 = r0
        L_0x0545:
            if (r28 == 0) goto L_0x0550
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r0 = r0.mVisibleInsets     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r2 = r7.mPendingVisibleInsets     // Catch:{ RemoteException -> 0x044d }
            r0.set(r2)     // Catch:{ RemoteException -> 0x044d }
        L_0x0550:
            if (r37 == 0) goto L_0x056a
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x056a
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            int r2 = r10.getColorMode()     // Catch:{ RemoteException -> 0x044d }
            r3 = 1
            if (r2 != r3) goto L_0x0565
            r2 = 1
            goto L_0x0567
        L_0x0565:
            r2 = 0
        L_0x0567:
            r0.setWideGamut(r2)     // Catch:{ RemoteException -> 0x044d }
        L_0x056a:
            if (r24 != 0) goto L_0x05b2
            android.view.Surface r0 = r7.mSurface     // Catch:{ RemoteException -> 0x044d }
            boolean r0 = r0.isValid()     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x0622
            r2 = 1
            r7.mFullRedrawNeeded = r2     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Region r0 = r7.mPreviousTransparentRegion     // Catch:{ RemoteException -> 0x044d }
            r0.setEmpty()     // Catch:{ RemoteException -> 0x044d }
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x0622
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ OutOfResourcesException -> 0x05a4 }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ OutOfResourcesException -> 0x05a4 }
            android.view.Surface r2 = r7.mSurface     // Catch:{ OutOfResourcesException -> 0x05a4 }
            boolean r0 = r0.initialize(r2)     // Catch:{ OutOfResourcesException -> 0x05a4 }
            r2 = r0
            if (r2 == 0) goto L_0x059f
            int r0 = r8.mPrivateFlags     // Catch:{ OutOfResourcesException -> 0x059d }
            r0 = r0 & 512(0x200, float:7.175E-43)
            if (r0 != 0) goto L_0x059f
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ OutOfResourcesException -> 0x059d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ OutOfResourcesException -> 0x059d }
            r0.allocateBuffers()     // Catch:{ OutOfResourcesException -> 0x059d }
            goto L_0x059f
        L_0x059d:
            r0 = move-exception
            goto L_0x05a7
        L_0x059f:
            r30 = r2
            goto L_0x0622
        L_0x05a4:
            r0 = move-exception
            r2 = r30
        L_0x05a7:
            r7.handleOutOfResourcesException(r0)     // Catch:{ RemoteException -> 0x05ab }
            return
        L_0x05ab:
            r0 = move-exception
            r44 = r1
            r30 = r2
            goto L_0x0450
        L_0x05b2:
            android.view.Surface r0 = r7.mSurface     // Catch:{ RemoteException -> 0x06e0 }
            boolean r0 = r0.isValid()     // Catch:{ RemoteException -> 0x06e0 }
            if (r0 != 0) goto L_0x05f8
            java.lang.ref.WeakReference<android.view.View> r0 = r7.mLastScrolledFocus     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x05c3
            java.lang.ref.WeakReference<android.view.View> r0 = r7.mLastScrolledFocus     // Catch:{ RemoteException -> 0x044d }
            r0.clear()     // Catch:{ RemoteException -> 0x044d }
        L_0x05c3:
            r2 = 0
            r7.mCurScrollY = r2     // Catch:{ RemoteException -> 0x044d }
            r7.mScrollY = r2     // Catch:{ RemoteException -> 0x044d }
            android.view.View r0 = r7.mView     // Catch:{ RemoteException -> 0x044d }
            boolean r0 = r0 instanceof com.android.internal.view.RootViewSurfaceTaker     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x05d7
            android.view.View r0 = r7.mView     // Catch:{ RemoteException -> 0x044d }
            com.android.internal.view.RootViewSurfaceTaker r0 = (com.android.internal.view.RootViewSurfaceTaker) r0     // Catch:{ RemoteException -> 0x044d }
            int r2 = r7.mCurScrollY     // Catch:{ RemoteException -> 0x044d }
            r0.onRootViewScrollYChanged(r2)     // Catch:{ RemoteException -> 0x044d }
        L_0x05d7:
            android.widget.Scroller r0 = r7.mScroller     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x05e0
            android.widget.Scroller r0 = r7.mScroller     // Catch:{ RemoteException -> 0x044d }
            r0.abortAnimation()     // Catch:{ RemoteException -> 0x044d }
        L_0x05e0:
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x0622
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            boolean r0 = r0.isEnabled()     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x0622
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            r0.destroy()     // Catch:{ RemoteException -> 0x044d }
            goto L_0x0622
        L_0x05f8:
            android.view.Surface r0 = r7.mSurface     // Catch:{ RemoteException -> 0x06e0 }
            int r0 = r0.getGenerationId()     // Catch:{ RemoteException -> 0x06e0 }
            if (r6 != r0) goto L_0x0606
            if (r31 != 0) goto L_0x0606
            if (r5 != 0) goto L_0x0606
            if (r37 == 0) goto L_0x0622
        L_0x0606:
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder     // Catch:{ RemoteException -> 0x06e0 }
            if (r0 != 0) goto L_0x0622
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ RemoteException -> 0x044d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ RemoteException -> 0x044d }
            if (r0 == 0) goto L_0x0622
            r2 = 1
            r7.mFullRedrawNeeded = r2     // Catch:{ RemoteException -> 0x044d }
            android.view.View$AttachInfo r0 = r7.mAttachInfo     // Catch:{ OutOfResourcesException -> 0x061d }
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer     // Catch:{ OutOfResourcesException -> 0x061d }
            android.view.Surface r2 = r7.mSurface     // Catch:{ OutOfResourcesException -> 0x061d }
            r0.updateSurface(r2)     // Catch:{ OutOfResourcesException -> 0x061d }
            goto L_0x0622
        L_0x061d:
            r0 = move-exception
            r7.handleOutOfResourcesException(r0)     // Catch:{ RemoteException -> 0x044d }
            return
        L_0x0622:
            r0 = r12 & 16
            if (r0 == 0) goto L_0x0628
            r0 = 1
            goto L_0x0629
        L_0x0628:
            r0 = 0
        L_0x0629:
            r2 = r12 & 8
            if (r2 == 0) goto L_0x062f
            r2 = 1
            goto L_0x0630
        L_0x062f:
            r2 = 0
        L_0x0630:
            r38 = r2
            if (r0 != 0) goto L_0x0639
            if (r38 == 0) goto L_0x0637
            goto L_0x0639
        L_0x0637:
            r2 = 0
            goto L_0x063a
        L_0x0639:
            r2 = 1
        L_0x063a:
            r3 = r2
            boolean r2 = r7.mDragResizing     // Catch:{ RemoteException -> 0x06e0 }
            if (r2 == r3) goto L_0x06b8
            if (r3 == 0) goto L_0x06a7
            if (r0 == 0) goto L_0x0646
            r2 = 0
            goto L_0x0647
        L_0x0646:
            r2 = 1
        L_0x0647:
            r7.mResizeMode = r2     // Catch:{ RemoteException -> 0x06e0 }
            android.graphics.Rect r2 = r7.mWinFrame     // Catch:{ RemoteException -> 0x06e0 }
            int r2 = r2.width()     // Catch:{ RemoteException -> 0x06e0 }
            r39 = r0
            android.graphics.Rect r0 = r7.mPendingBackDropFrame     // Catch:{ RemoteException -> 0x06e0 }
            int r0 = r0.width()     // Catch:{ RemoteException -> 0x06e0 }
            if (r2 != r0) goto L_0x0669
            android.graphics.Rect r0 = r7.mWinFrame     // Catch:{ RemoteException -> 0x044d }
            int r0 = r0.height()     // Catch:{ RemoteException -> 0x044d }
            android.graphics.Rect r2 = r7.mPendingBackDropFrame     // Catch:{ RemoteException -> 0x044d }
            int r2 = r2.height()     // Catch:{ RemoteException -> 0x044d }
            if (r0 != r2) goto L_0x0669
            r0 = 1
            goto L_0x066a
        L_0x0669:
            r0 = 0
        L_0x066a:
            android.graphics.Rect r2 = r7.mPendingBackDropFrame     // Catch:{ RemoteException -> 0x06e0 }
            if (r0 != 0) goto L_0x0671
            r40 = 1
            goto L_0x0673
        L_0x0671:
            r40 = 0
        L_0x0673:
            r41 = r0
            android.graphics.Rect r0 = r7.mPendingVisibleInsets     // Catch:{ RemoteException -> 0x06e0 }
            r42 = r5
            android.graphics.Rect r5 = r7.mPendingStableInsets     // Catch:{ RemoteException -> 0x069d }
            r43 = r6
            int r6 = r7.mResizeMode     // Catch:{ RemoteException -> 0x0692 }
            r44 = r1
            r1 = r58
            r45 = r3
            r3 = r40
            r40 = r4
            r4 = r0
            r46 = r12
            r12 = r43
            r1.startDragResizing(r2, r3, r4, r5, r6)     // Catch:{ RemoteException -> 0x06de }
            goto L_0x06c5
        L_0x0692:
            r0 = move-exception
            r44 = r1
            r40 = r4
            r46 = r12
            r12 = r43
            goto L_0x0706
        L_0x069d:
            r0 = move-exception
            r44 = r1
            r40 = r4
            r46 = r12
            r12 = r6
            goto L_0x0706
        L_0x06a7:
            r39 = r0
            r44 = r1
            r45 = r3
            r40 = r4
            r42 = r5
            r46 = r12
            r12 = r6
            r58.endDragResizing()     // Catch:{ RemoteException -> 0x06de }
            goto L_0x06c5
        L_0x06b8:
            r39 = r0
            r44 = r1
            r45 = r3
            r40 = r4
            r42 = r5
            r46 = r12
            r12 = r6
        L_0x06c5:
            boolean r0 = r7.mUseMTRenderer     // Catch:{ RemoteException -> 0x06de }
            if (r0 != 0) goto L_0x06dd
            if (r45 == 0) goto L_0x06d8
            android.graphics.Rect r0 = r7.mWinFrame     // Catch:{ RemoteException -> 0x06de }
            int r0 = r0.left     // Catch:{ RemoteException -> 0x06de }
            r7.mCanvasOffsetX = r0     // Catch:{ RemoteException -> 0x06de }
            android.graphics.Rect r0 = r7.mWinFrame     // Catch:{ RemoteException -> 0x06de }
            int r0 = r0.top     // Catch:{ RemoteException -> 0x06de }
            r7.mCanvasOffsetY = r0     // Catch:{ RemoteException -> 0x06de }
            goto L_0x06dd
        L_0x06d8:
            r1 = 0
            r7.mCanvasOffsetY = r1     // Catch:{ RemoteException -> 0x06de }
            r7.mCanvasOffsetX = r1     // Catch:{ RemoteException -> 0x06de }
        L_0x06dd:
            goto L_0x0706
        L_0x06de:
            r0 = move-exception
            goto L_0x0706
        L_0x06e0:
            r0 = move-exception
            r44 = r1
            r40 = r4
            r42 = r5
            r46 = r12
            r12 = r6
            goto L_0x0706
        L_0x06eb:
            r0 = move-exception
            r44 = r1
            r40 = r4
            r42 = r5
            r12 = r6
            r46 = r28
            goto L_0x0706
        L_0x06f6:
            r0 = move-exception
            r44 = r1
            r30 = r2
            r31 = r3
            r40 = r4
            r42 = r5
            r32 = r12
            r12 = r6
            r46 = r28
        L_0x0706:
            r2 = r29
            r3 = r31
            r1 = r46
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            int r4 = r9.left
            r0.mWindowLeft = r4
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            int r4 = r9.top
            r0.mWindowTop = r4
            int r0 = r7.mWidth
            int r4 = r9.width()
            if (r0 != r4) goto L_0x0728
            int r0 = r7.mHeight
            int r4 = r9.height()
            if (r0 == r4) goto L_0x0734
        L_0x0728:
            int r0 = r9.width()
            r7.mWidth = r0
            int r0 = r9.height()
            r7.mHeight = r0
        L_0x0734:
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            if (r0 == 0) goto L_0x07f3
            android.view.Surface r0 = r7.mSurface
            boolean r0 = r0.isValid()
            if (r0 == 0) goto L_0x0746
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            android.view.Surface r4 = r7.mSurface
            r0.mSurface = r4
        L_0x0746:
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            int r4 = r7.mWidth
            int r5 = r7.mHeight
            r0.setSurfaceFrameSize(r4, r5)
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock
            r0.unlock()
            android.view.Surface r0 = r7.mSurface
            boolean r0 = r0.isValid()
            if (r0 == 0) goto L_0x07c9
            if (r24 != 0) goto L_0x0785
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            r0.ungetCallbacks()
            r4 = 1
            r7.mIsCreating = r4
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            android.view.SurfaceHolder$Callback[] r0 = r0.getCallbacks()
            if (r0 == 0) goto L_0x0782
            int r4 = r0.length
            r5 = 0
        L_0x0772:
            if (r5 >= r4) goto L_0x0782
            r6 = r0[r5]
            r47 = r0
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            r6.surfaceCreated(r0)
            int r5 = r5 + 1
            r0 = r47
            goto L_0x0772
        L_0x0782:
            r47 = r0
            r15 = 1
        L_0x0785:
            if (r15 != 0) goto L_0x0795
            android.view.Surface r0 = r7.mSurface
            int r0 = r0.getGenerationId()
            if (r12 == r0) goto L_0x0790
            goto L_0x0795
        L_0x0790:
            r49 = r3
            r51 = r9
            goto L_0x07c5
        L_0x0795:
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            android.view.SurfaceHolder$Callback[] r0 = r0.getCallbacks()
            if (r0 == 0) goto L_0x07c1
            int r4 = r0.length
            r5 = 0
        L_0x079f:
            if (r5 >= r4) goto L_0x07c1
            r6 = r0[r5]
            r48 = r0
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            r49 = r3
            int r3 = r10.format
            r50 = r4
            int r4 = r7.mWidth
            r51 = r9
            int r9 = r7.mHeight
            r6.surfaceChanged(r0, r3, r4, r9)
            int r5 = r5 + 1
            r0 = r48
            r3 = r49
            r4 = r50
            r9 = r51
            goto L_0x079f
        L_0x07c1:
            r49 = r3
            r51 = r9
        L_0x07c5:
            r3 = 0
            r7.mIsCreating = r3
            goto L_0x07f7
        L_0x07c9:
            r49 = r3
            r51 = r9
            if (r24 == 0) goto L_0x07f7
            r58.notifySurfaceDestroyed()
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock
            r0.lock()
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder     // Catch:{ all -> 0x07ea }
            android.view.Surface r3 = new android.view.Surface     // Catch:{ all -> 0x07ea }
            r3.<init>()     // Catch:{ all -> 0x07ea }
            r0.mSurface = r3     // Catch:{ all -> 0x07ea }
            com.android.internal.view.BaseSurfaceHolder r0 = r7.mSurfaceHolder
            java.util.concurrent.locks.ReentrantLock r0 = r0.mSurfaceLock
            r0.unlock()
            goto L_0x07f7
        L_0x07ea:
            r0 = move-exception
            com.android.internal.view.BaseSurfaceHolder r3 = r7.mSurfaceHolder
            java.util.concurrent.locks.ReentrantLock r3 = r3.mSurfaceLock
            r3.unlock()
            throw r0
        L_0x07f3:
            r49 = r3
            r51 = r9
        L_0x07f7:
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            android.view.ThreadedRenderer r0 = r0.mThreadedRenderer
            if (r0 == 0) goto L_0x0829
            boolean r3 = r0.isEnabled()
            if (r3 == 0) goto L_0x0829
            if (r30 != 0) goto L_0x0819
            int r3 = r7.mWidth
            int r4 = r0.getWidth()
            if (r3 != r4) goto L_0x0819
            int r3 = r7.mHeight
            int r4 = r0.getHeight()
            if (r3 != r4) goto L_0x0819
            boolean r3 = r7.mNeedsRendererSetup
            if (r3 == 0) goto L_0x0829
        L_0x0819:
            int r3 = r7.mWidth
            int r4 = r7.mHeight
            android.view.View$AttachInfo r5 = r7.mAttachInfo
            android.view.WindowManager$LayoutParams r6 = r7.mWindowAttributes
            android.graphics.Rect r6 = r6.surfaceInsets
            r0.setup(r3, r4, r5, r6)
            r3 = 0
            r7.mNeedsRendererSetup = r3
        L_0x0829:
            boolean r3 = r7.mStopped
            if (r3 == 0) goto L_0x0831
            boolean r3 = r7.mReportNextDraw
            if (r3 == 0) goto L_0x0853
        L_0x0831:
            r3 = r1 & 1
            if (r3 == 0) goto L_0x0837
            r3 = 1
            goto L_0x0838
        L_0x0837:
            r3 = 0
        L_0x0838:
            boolean r3 = r7.ensureTouchModeLocally(r3)
            if (r3 != 0) goto L_0x0856
            int r4 = r7.mWidth
            int r5 = r8.getMeasuredWidth()
            if (r4 != r5) goto L_0x0856
            int r4 = r7.mHeight
            int r5 = r8.getMeasuredHeight()
            if (r4 != r5) goto L_0x0856
            if (r23 != 0) goto L_0x0856
            if (r2 == 0) goto L_0x0853
            goto L_0x0856
        L_0x0853:
            r53 = r1
            goto L_0x08af
        L_0x0856:
            int r4 = r7.mWidth
            int r5 = r10.width
            int r4 = getRootMeasureSpec(r4, r5)
            int r5 = r7.mHeight
            int r6 = r10.height
            int r5 = getRootMeasureSpec(r5, r6)
            r7.performMeasure(r4, r5)
            int r6 = r8.getMeasuredWidth()
            int r9 = r8.getMeasuredHeight()
            r26 = 0
            r52 = r0
            float r0 = r10.horizontalWeight
            r28 = 0
            int r0 = (r0 > r28 ? 1 : (r0 == r28 ? 0 : -1))
            r53 = r1
            if (r0 <= 0) goto L_0x0890
            int r0 = r7.mWidth
            int r0 = r0 - r6
            float r0 = (float) r0
            float r1 = r10.horizontalWeight
            float r0 = r0 * r1
            int r0 = (int) r0
            int r6 = r6 + r0
            r0 = 1073741824(0x40000000, float:2.0)
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r0)
            r26 = 1
        L_0x0890:
            float r0 = r10.verticalWeight
            int r0 = (r0 > r28 ? 1 : (r0 == r28 ? 0 : -1))
            if (r0 <= 0) goto L_0x08a7
            int r0 = r7.mHeight
            int r0 = r0 - r9
            float r0 = (float) r0
            float r1 = r10.verticalWeight
            float r0 = r0 * r1
            int r0 = (int) r0
            int r9 = r9 + r0
            r0 = 1073741824(0x40000000, float:2.0)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r0)
            r26 = 1
        L_0x08a7:
            if (r26 == 0) goto L_0x08ac
            r7.performMeasure(r4, r5)
        L_0x08ac:
            r0 = 1
            r18 = r0
        L_0x08af:
            r26 = r44
            r1 = r53
        L_0x08b3:
            if (r49 == 0) goto L_0x08b8
            r58.updateBoundsSurface()
        L_0x08b8:
            if (r18 == 0) goto L_0x08c4
            boolean r0 = r7.mStopped
            if (r0 == 0) goto L_0x08c2
            boolean r0 = r7.mReportNextDraw
            if (r0 == 0) goto L_0x08c4
        L_0x08c2:
            r0 = 1
            goto L_0x08c5
        L_0x08c4:
            r0 = 0
        L_0x08c5:
            r3 = r0
            if (r3 != 0) goto L_0x08d1
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            boolean r0 = r0.mRecomputeGlobalAttributes
            if (r0 == 0) goto L_0x08cf
            goto L_0x08d1
        L_0x08cf:
            r0 = 0
            goto L_0x08d2
        L_0x08d1:
            r0 = 1
        L_0x08d2:
            r4 = r0
            if (r3 == 0) goto L_0x093f
            int r0 = r7.mWidth
            int r5 = r7.mHeight
            r7.performLayout(r10, r0, r5)
            int r0 = r8.mPrivateFlags
            r0 = r0 & 512(0x200, float:7.175E-43)
            if (r0 == 0) goto L_0x093f
            int[] r0 = r7.mTmpLocation
            r8.getLocationInWindow(r0)
            android.graphics.Region r0 = r7.mTransparentRegion
            int[] r5 = r7.mTmpLocation
            r6 = 0
            r5 = r5[r6]
            int[] r9 = r7.mTmpLocation
            r22 = 1
            r9 = r9[r22]
            r54 = r2
            int[] r2 = r7.mTmpLocation
            r2 = r2[r6]
            int r6 = r8.mRight
            int r2 = r2 + r6
            int r6 = r8.mLeft
            int r2 = r2 - r6
            int[] r6 = r7.mTmpLocation
            r6 = r6[r22]
            r55 = r3
            int r3 = r8.mBottom
            int r6 = r6 + r3
            int r3 = r8.mTop
            int r6 = r6 - r3
            r0.set(r5, r9, r2, r6)
            android.graphics.Region r0 = r7.mTransparentRegion
            r8.gatherTransparentRegion(r0)
            android.content.res.CompatibilityInfo$Translator r0 = r7.mTranslator
            if (r0 == 0) goto L_0x091f
            android.content.res.CompatibilityInfo$Translator r0 = r7.mTranslator
            android.graphics.Region r2 = r7.mTransparentRegion
            r0.translateRegionInWindowToScreen(r2)
        L_0x091f:
            android.graphics.Region r0 = r7.mTransparentRegion
            android.graphics.Region r2 = r7.mPreviousTransparentRegion
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0943
            android.graphics.Region r0 = r7.mPreviousTransparentRegion
            android.graphics.Region r2 = r7.mTransparentRegion
            r0.set((android.graphics.Region) r2)
            r2 = 1
            r7.mFullRedrawNeeded = r2
            android.view.IWindowSession r0 = r7.mWindowSession     // Catch:{ RemoteException -> 0x093d }
            android.view.ViewRootImpl$W r2 = r7.mWindow     // Catch:{ RemoteException -> 0x093d }
            android.graphics.Region r3 = r7.mTransparentRegion     // Catch:{ RemoteException -> 0x093d }
            r0.setTransparentRegion(r2, r3)     // Catch:{ RemoteException -> 0x093d }
            goto L_0x0943
        L_0x093d:
            r0 = move-exception
            goto L_0x0943
        L_0x093f:
            r54 = r2
            r55 = r3
        L_0x0943:
            if (r4 == 0) goto L_0x0951
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            r2 = 0
            r0.mRecomputeGlobalAttributes = r2
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            android.view.ViewTreeObserver r0 = r0.mTreeObserver
            r0.dispatchOnGlobalLayout()
        L_0x0951:
            if (r20 == 0) goto L_0x09b7
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            android.view.ViewTreeObserver$InternalInsetsInfo r2 = r0.mGivenInternalInsets
            r2.reset()
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            android.view.ViewTreeObserver r0 = r0.mTreeObserver
            r0.dispatchOnComputeInternalInsets(r2)
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            boolean r3 = r2.isEmpty()
            r5 = 1
            r3 = r3 ^ r5
            r0.mHasNonEmptyGivenInternalInsets = r3
            if (r26 != 0) goto L_0x0975
            android.view.ViewTreeObserver$InternalInsetsInfo r0 = r7.mLastGivenInsets
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x09b7
        L_0x0975:
            android.view.ViewTreeObserver$InternalInsetsInfo r0 = r7.mLastGivenInsets
            r0.set(r2)
            android.content.res.CompatibilityInfo$Translator r0 = r7.mTranslator
            if (r0 == 0) goto L_0x099b
            android.content.res.CompatibilityInfo$Translator r0 = r7.mTranslator
            android.graphics.Rect r3 = r2.contentInsets
            android.graphics.Rect r0 = r0.getTranslatedContentInsets(r3)
            android.content.res.CompatibilityInfo$Translator r3 = r7.mTranslator
            android.graphics.Rect r5 = r2.visibleInsets
            android.graphics.Rect r3 = r3.getTranslatedVisibleInsets(r5)
            android.content.res.CompatibilityInfo$Translator r5 = r7.mTranslator
            android.graphics.Region r6 = r2.touchableRegion
            android.graphics.Region r5 = r5.getTranslatedTouchableArea(r6)
        L_0x0996:
            r38 = r5
            r5 = r3
            r3 = r0
            goto L_0x09a2
        L_0x099b:
            android.graphics.Rect r0 = r2.contentInsets
            android.graphics.Rect r3 = r2.visibleInsets
            android.graphics.Region r5 = r2.touchableRegion
            goto L_0x0996
        L_0x09a2:
            android.view.IWindowSession r0 = r7.mWindowSession     // Catch:{ RemoteException -> 0x09b6 }
            android.view.ViewRootImpl$W r6 = r7.mWindow     // Catch:{ RemoteException -> 0x09b6 }
            int r9 = r2.mTouchableInsets     // Catch:{ RemoteException -> 0x09b6 }
            r33 = r0
            r34 = r6
            r35 = r9
            r36 = r3
            r37 = r5
            r33.setInsets(r34, r35, r36, r37, r38)     // Catch:{ RemoteException -> 0x09b6 }
            goto L_0x09b7
        L_0x09b6:
            r0 = move-exception
        L_0x09b7:
            boolean r0 = r7.mFirst
            if (r0 == 0) goto L_0x09f0
            boolean r0 = sAlwaysAssignFocus
            if (r0 != 0) goto L_0x09df
            boolean r0 = isInTouchMode()
            if (r0 != 0) goto L_0x09c6
            goto L_0x09df
        L_0x09c6:
            android.view.View r0 = r7.mView
            android.view.View r0 = r0.findFocus()
            boolean r2 = r0 instanceof android.view.ViewGroup
            if (r2 == 0) goto L_0x09f0
            r2 = r0
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
            int r2 = r2.getDescendantFocusability()
            r3 = 262144(0x40000, float:3.67342E-40)
            if (r2 != r3) goto L_0x09f0
            r0.restoreDefaultFocus()
            goto L_0x09f0
        L_0x09df:
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x09f0
            android.view.View r0 = r7.mView
            boolean r0 = r0.hasFocus()
            if (r0 != 0) goto L_0x09f0
            android.view.View r0 = r7.mView
            r0.restoreDefaultFocus()
        L_0x09f0:
            if (r13 != 0) goto L_0x09f6
            boolean r0 = r7.mFirst
            if (r0 == 0) goto L_0x09fa
        L_0x09f6:
            if (r25 == 0) goto L_0x09fa
            r0 = 1
            goto L_0x09fb
        L_0x09fa:
            r0 = 0
        L_0x09fb:
            android.view.View$AttachInfo r2 = r7.mAttachInfo
            boolean r2 = r2.mHasWindowFocus
            if (r2 == 0) goto L_0x0a05
            if (r25 == 0) goto L_0x0a05
            r2 = 1
            goto L_0x0a06
        L_0x0a05:
            r2 = 0
        L_0x0a06:
            if (r2 == 0) goto L_0x0a0e
            boolean r3 = r7.mLostWindowFocus
            if (r3 == 0) goto L_0x0a0e
            r3 = 1
            goto L_0x0a0f
        L_0x0a0e:
            r3 = 0
        L_0x0a0f:
            if (r3 == 0) goto L_0x0a15
            r5 = 0
            r7.mLostWindowFocus = r5
            goto L_0x0a1e
        L_0x0a15:
            if (r2 != 0) goto L_0x0a1e
            boolean r5 = r7.mHadWindowFocus
            if (r5 == 0) goto L_0x0a1e
            r5 = 1
            r7.mLostWindowFocus = r5
        L_0x0a1e:
            if (r0 != 0) goto L_0x0a22
            if (r3 == 0) goto L_0x0a3a
        L_0x0a22:
            android.view.WindowManager$LayoutParams r5 = r7.mWindowAttributes
            if (r5 != 0) goto L_0x0a28
        L_0x0a26:
            r5 = 0
            goto L_0x0a33
        L_0x0a28:
            android.view.WindowManager$LayoutParams r5 = r7.mWindowAttributes
            int r5 = r5.type
            r6 = 2005(0x7d5, float:2.81E-42)
            if (r5 != r6) goto L_0x0a32
            r5 = 1
            goto L_0x0a33
        L_0x0a32:
            goto L_0x0a26
        L_0x0a33:
            if (r5 != 0) goto L_0x0a3a
            r6 = 32
            r8.sendAccessibilityEvent(r6)
        L_0x0a3a:
            r5 = 0
            r7.mFirst = r5
            r7.mWillDrawSoon = r5
            r7.mNewSurfaceNeeded = r5
            r7.mActivityRelaunched = r5
            r7.mViewVisibility = r11
            r7.mHadWindowFocus = r2
            if (r2 == 0) goto L_0x0a96
            boolean r5 = r58.isInLocalFocusMode()
            if (r5 != 0) goto L_0x0a96
            android.view.WindowManager$LayoutParams r5 = r7.mWindowAttributes
            int r5 = r5.flags
            boolean r5 = android.view.WindowManager.LayoutParams.mayUseInputMethod(r5)
            boolean r6 = r7.mLastWasImTarget
            if (r5 == r6) goto L_0x0a96
            r7.mLastWasImTarget = r5
            android.content.Context r6 = r7.mContext
            java.lang.Class<android.view.inputmethod.InputMethodManager> r9 = android.view.inputmethod.InputMethodManager.class
            java.lang.Object r6 = r6.getSystemService(r9)
            android.view.inputmethod.InputMethodManager r6 = (android.view.inputmethod.InputMethodManager) r6
            if (r6 == 0) goto L_0x0a96
            if (r5 == 0) goto L_0x0a96
            android.view.View r9 = r7.mView
            r6.onPreWindowFocus(r9, r2)
            android.view.View r9 = r7.mView
            r56 = r0
            android.view.View r0 = r7.mView
            android.view.View r35 = r0.findFocus()
            android.view.WindowManager$LayoutParams r0 = r7.mWindowAttributes
            int r0 = r0.softInputMode
            r57 = r2
            boolean r2 = r7.mHasHadWindowFocus
            r22 = 1
            r37 = r2 ^ 1
            android.view.WindowManager$LayoutParams r2 = r7.mWindowAttributes
            int r2 = r2.flags
            r33 = r6
            r34 = r9
            r36 = r0
            r38 = r2
            r33.onPostWindowFocus(r34, r35, r36, r37, r38)
            goto L_0x0a9c
        L_0x0a96:
            r56 = r0
            r57 = r2
            r22 = 1
        L_0x0a9c:
            r0 = r1 & 2
            if (r0 == 0) goto L_0x0aa3
            r58.reportNextDraw()
        L_0x0aa3:
            android.view.View$AttachInfo r0 = r7.mAttachInfo
            android.view.ViewTreeObserver r0 = r0.mTreeObserver
            boolean r0 = r0.dispatchOnPreDraw()
            if (r0 != 0) goto L_0x0ab3
            if (r25 != 0) goto L_0x0ab0
            goto L_0x0ab3
        L_0x0ab0:
            r22 = 0
        L_0x0ab3:
            r0 = r22
            if (r0 != 0) goto L_0x0ae3
            java.util.ArrayList<android.animation.LayoutTransition> r2 = r7.mPendingTransitions
            if (r2 == 0) goto L_0x0adf
            java.util.ArrayList<android.animation.LayoutTransition> r2 = r7.mPendingTransitions
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x0adf
            r2 = 0
        L_0x0ac4:
            java.util.ArrayList<android.animation.LayoutTransition> r5 = r7.mPendingTransitions
            int r5 = r5.size()
            if (r2 >= r5) goto L_0x0ada
            java.util.ArrayList<android.animation.LayoutTransition> r5 = r7.mPendingTransitions
            java.lang.Object r5 = r5.get(r2)
            android.animation.LayoutTransition r5 = (android.animation.LayoutTransition) r5
            r5.startChangingAnimations()
            int r2 = r2 + 1
            goto L_0x0ac4
        L_0x0ada:
            java.util.ArrayList<android.animation.LayoutTransition> r2 = r7.mPendingTransitions
            r2.clear()
        L_0x0adf:
            r58.performDraw()
            goto L_0x0b11
        L_0x0ae3:
            if (r25 == 0) goto L_0x0ae9
            r58.scheduleTraversals()
            goto L_0x0b11
        L_0x0ae9:
            java.util.ArrayList<android.animation.LayoutTransition> r2 = r7.mPendingTransitions
            if (r2 == 0) goto L_0x0b11
            java.util.ArrayList<android.animation.LayoutTransition> r2 = r7.mPendingTransitions
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x0b11
            r2 = 0
        L_0x0af6:
            java.util.ArrayList<android.animation.LayoutTransition> r5 = r7.mPendingTransitions
            int r5 = r5.size()
            if (r2 >= r5) goto L_0x0b0c
            java.util.ArrayList<android.animation.LayoutTransition> r5 = r7.mPendingTransitions
            java.lang.Object r5 = r5.get(r2)
            android.animation.LayoutTransition r5 = (android.animation.LayoutTransition) r5
            r5.endChangingAnimations()
            int r2 = r2 + 1
            goto L_0x0af6
        L_0x0b0c:
            java.util.ArrayList<android.animation.LayoutTransition> r2 = r7.mPendingTransitions
            r2.clear()
        L_0x0b11:
            r2 = 0
            r7.mIsInTraversal = r2
            return
        L_0x0b15:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.performTraversals():void");
    }

    private void notifySurfaceDestroyed() {
        this.mSurfaceHolder.ungetCallbacks();
        SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
        if (callbacks != null) {
            for (SurfaceHolder.Callback c : callbacks) {
                c.surfaceDestroyed(this.mSurfaceHolder);
            }
        }
    }

    /* access modifiers changed from: private */
    public void maybeHandleWindowMove(Rect frame) {
        boolean windowMoved = (this.mAttachInfo.mWindowLeft == frame.left && this.mAttachInfo.mWindowTop == frame.top) ? false : true;
        if (windowMoved) {
            if (this.mTranslator != null) {
                this.mTranslator.translateRectInScreenToAppWinFrame(frame);
            }
            this.mAttachInfo.mWindowLeft = frame.left;
            this.mAttachInfo.mWindowTop = frame.top;
        }
        if (windowMoved || this.mAttachInfo.mNeedsUpdateLightCenter) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo);
            }
            this.mAttachInfo.mNeedsUpdateLightCenter = false;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        if (r1 == false) goto L_0x001b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        r12.mInsetsController.onWindowFocusGained();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        r12.mInsetsController.onWindowFocusLost();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        if (r12.mAdded == false) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0024, code lost:
        profileRendering(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        if (r1 == false) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        ensureTouchModeLocally(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0031, code lost:
        if (r12.mAttachInfo.mThreadedRenderer == null) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        if (r12.mSurface.isValid() == false) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003b, code lost:
        r12.mFullRedrawNeeded = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4 = r12.mWindowAttributes;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003f, code lost:
        if (r4 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0041, code lost:
        r5 = r4.surfaceInsets;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0044, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0045, code lost:
        r12.mAttachInfo.mThreadedRenderer.initializeIfNeeded(r12.mWidth, r12.mHeight, r12.mAttachInfo, r12.mSurface, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0057, code lost:
        android.util.Log.e(r12.mTag, "OutOfResourcesException locking surface", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0066, code lost:
        if (r12.mWindowSession.outOfMemory(r12.mWindow) == false) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0068, code lost:
        android.util.Slog.w(r12.mTag, "No processes killed for memory; killing self");
        android.os.Process.killProcess(android.os.Process.myPid());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0078, code lost:
        r12.mHandler.sendMessageDelayed(r12.mHandler.obtainMessage(6), 500);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0086, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0087, code lost:
        r12.mAttachInfo.mHasWindowFocus = r1;
        r12.mLastWasImTarget = android.view.WindowManager.LayoutParams.mayUseInputMethod(r12.mWindowAttributes.flags);
        r4 = (android.view.inputmethod.InputMethodManager) r12.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009f, code lost:
        if (r4 == null) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a3, code lost:
        if (r12.mLastWasImTarget == false) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a9, code lost:
        if (isInLocalFocusMode() != false) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ab, code lost:
        r4.onPreWindowFocus(r12.mView, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b2, code lost:
        if (r12.mView == null) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b4, code lost:
        r12.mAttachInfo.mKeyDispatchState.reset();
        r12.mView.dispatchWindowFocusChanged(r1);
        r12.mAttachInfo.mTreeObserver.dispatchOnWindowFocusChange(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00cb, code lost:
        if (r12.mAttachInfo.mTooltipHost == null) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00cd, code lost:
        r12.mAttachInfo.mTooltipHost.hideTooltip();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00d4, code lost:
        if (r1 == false) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00d6, code lost:
        if (r4 == null) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00da, code lost:
        if (r12.mLastWasImTarget == false) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e0, code lost:
        if (isInLocalFocusMode() != false) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e2, code lost:
        r4.onPostWindowFocus(r12.mView, r12.mView.findFocus(), r12.mWindowAttributes.softInputMode, !r12.mHasHadWindowFocus, r12.mWindowAttributes.flags);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00fa, code lost:
        r12.mWindowAttributes.softInputMode &= android.net.TrafficStats.TAG_NETWORK_STACK_RANGE_END;
        ((android.view.WindowManager.LayoutParams) r12.mView.getLayoutParams()).softInputMode &= android.net.TrafficStats.TAG_NETWORK_STACK_RANGE_END;
        r12.mHasHadWindowFocus = true;
        fireAccessibilityFocusEventIfHasFocusedNode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0118, code lost:
        if (r12.mPointerCapture == false) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x011a, code lost:
        handlePointerCaptureChanged(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x011d, code lost:
        r12.mFirstInputStage.onWindowFocusChanged(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0122, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        if (sNewInsetsMode == 0) goto L_0x0020;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleWindowFocusChanged() {
        /*
            r12 = this;
            monitor-enter(r12)
            boolean r0 = r12.mWindowFocusChanged     // Catch:{ all -> 0x0123 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r12)     // Catch:{ all -> 0x0123 }
            return
        L_0x0007:
            r0 = 0
            r12.mWindowFocusChanged = r0     // Catch:{ all -> 0x0123 }
            boolean r1 = r12.mUpcomingWindowFocus     // Catch:{ all -> 0x0123 }
            boolean r2 = r12.mUpcomingInTouchMode     // Catch:{ all -> 0x0123 }
            monitor-exit(r12)     // Catch:{ all -> 0x0123 }
            int r3 = sNewInsetsMode
            if (r3 == 0) goto L_0x0020
            if (r1 == 0) goto L_0x001b
            android.view.InsetsController r3 = r12.mInsetsController
            r3.onWindowFocusGained()
            goto L_0x0020
        L_0x001b:
            android.view.InsetsController r3 = r12.mInsetsController
            r3.onWindowFocusLost()
        L_0x0020:
            boolean r3 = r12.mAdded
            if (r3 == 0) goto L_0x011d
            r12.profileRendering(r1)
            r3 = 1
            if (r1 == 0) goto L_0x0087
            r12.ensureTouchModeLocally(r2)
            android.view.View$AttachInfo r4 = r12.mAttachInfo
            android.view.ThreadedRenderer r4 = r4.mThreadedRenderer
            if (r4 == 0) goto L_0x0087
            android.view.Surface r4 = r12.mSurface
            boolean r4 = r4.isValid()
            if (r4 == 0) goto L_0x0087
            r12.mFullRedrawNeeded = r3
            android.view.WindowManager$LayoutParams r4 = r12.mWindowAttributes     // Catch:{ OutOfResourcesException -> 0x0056 }
            if (r4 == 0) goto L_0x0044
            android.graphics.Rect r5 = r4.surfaceInsets     // Catch:{ OutOfResourcesException -> 0x0056 }
            goto L_0x0045
        L_0x0044:
            r5 = 0
        L_0x0045:
            r11 = r5
            android.view.View$AttachInfo r5 = r12.mAttachInfo     // Catch:{ OutOfResourcesException -> 0x0056 }
            android.view.ThreadedRenderer r6 = r5.mThreadedRenderer     // Catch:{ OutOfResourcesException -> 0x0056 }
            int r7 = r12.mWidth     // Catch:{ OutOfResourcesException -> 0x0056 }
            int r8 = r12.mHeight     // Catch:{ OutOfResourcesException -> 0x0056 }
            android.view.View$AttachInfo r9 = r12.mAttachInfo     // Catch:{ OutOfResourcesException -> 0x0056 }
            android.view.Surface r10 = r12.mSurface     // Catch:{ OutOfResourcesException -> 0x0056 }
            r6.initializeIfNeeded(r7, r8, r9, r10, r11)     // Catch:{ OutOfResourcesException -> 0x0056 }
            goto L_0x0087
        L_0x0056:
            r0 = move-exception
            java.lang.String r3 = r12.mTag
            java.lang.String r4 = "OutOfResourcesException locking surface"
            android.util.Log.e(r3, r4, r0)
            android.view.IWindowSession r3 = r12.mWindowSession     // Catch:{ RemoteException -> 0x0077 }
            android.view.ViewRootImpl$W r4 = r12.mWindow     // Catch:{ RemoteException -> 0x0077 }
            boolean r3 = r3.outOfMemory(r4)     // Catch:{ RemoteException -> 0x0077 }
            if (r3 != 0) goto L_0x0076
            java.lang.String r3 = r12.mTag     // Catch:{ RemoteException -> 0x0077 }
            java.lang.String r4 = "No processes killed for memory; killing self"
            android.util.Slog.w((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ RemoteException -> 0x0077 }
            int r3 = android.os.Process.myPid()     // Catch:{ RemoteException -> 0x0077 }
            android.os.Process.killProcess(r3)     // Catch:{ RemoteException -> 0x0077 }
        L_0x0076:
            goto L_0x0078
        L_0x0077:
            r3 = move-exception
        L_0x0078:
            android.view.ViewRootImpl$ViewRootHandler r3 = r12.mHandler
            android.view.ViewRootImpl$ViewRootHandler r4 = r12.mHandler
            r5 = 6
            android.os.Message r4 = r4.obtainMessage(r5)
            r5 = 500(0x1f4, double:2.47E-321)
            r3.sendMessageDelayed(r4, r5)
            return
        L_0x0087:
            android.view.View$AttachInfo r4 = r12.mAttachInfo
            r4.mHasWindowFocus = r1
            android.view.WindowManager$LayoutParams r4 = r12.mWindowAttributes
            int r4 = r4.flags
            boolean r4 = android.view.WindowManager.LayoutParams.mayUseInputMethod(r4)
            r12.mLastWasImTarget = r4
            android.content.Context r4 = r12.mContext
            java.lang.Class<android.view.inputmethod.InputMethodManager> r5 = android.view.inputmethod.InputMethodManager.class
            java.lang.Object r4 = r4.getSystemService(r5)
            android.view.inputmethod.InputMethodManager r4 = (android.view.inputmethod.InputMethodManager) r4
            if (r4 == 0) goto L_0x00b0
            boolean r5 = r12.mLastWasImTarget
            if (r5 == 0) goto L_0x00b0
            boolean r5 = r12.isInLocalFocusMode()
            if (r5 != 0) goto L_0x00b0
            android.view.View r5 = r12.mView
            r4.onPreWindowFocus(r5, r1)
        L_0x00b0:
            android.view.View r5 = r12.mView
            if (r5 == 0) goto L_0x00d4
            android.view.View$AttachInfo r5 = r12.mAttachInfo
            android.view.KeyEvent$DispatcherState r5 = r5.mKeyDispatchState
            r5.reset()
            android.view.View r5 = r12.mView
            r5.dispatchWindowFocusChanged(r1)
            android.view.View$AttachInfo r5 = r12.mAttachInfo
            android.view.ViewTreeObserver r5 = r5.mTreeObserver
            r5.dispatchOnWindowFocusChange(r1)
            android.view.View$AttachInfo r5 = r12.mAttachInfo
            android.view.View r5 = r5.mTooltipHost
            if (r5 == 0) goto L_0x00d4
            android.view.View$AttachInfo r5 = r12.mAttachInfo
            android.view.View r5 = r5.mTooltipHost
            r5.hideTooltip()
        L_0x00d4:
            if (r1 == 0) goto L_0x0116
            if (r4 == 0) goto L_0x00fa
            boolean r0 = r12.mLastWasImTarget
            if (r0 == 0) goto L_0x00fa
            boolean r0 = r12.isInLocalFocusMode()
            if (r0 != 0) goto L_0x00fa
            android.view.View r6 = r12.mView
            android.view.View r0 = r12.mView
            android.view.View r7 = r0.findFocus()
            android.view.WindowManager$LayoutParams r0 = r12.mWindowAttributes
            int r8 = r0.softInputMode
            boolean r0 = r12.mHasHadWindowFocus
            r9 = r0 ^ 1
            android.view.WindowManager$LayoutParams r0 = r12.mWindowAttributes
            int r10 = r0.flags
            r5 = r4
            r5.onPostWindowFocus(r6, r7, r8, r9, r10)
        L_0x00fa:
            android.view.WindowManager$LayoutParams r0 = r12.mWindowAttributes
            int r5 = r0.softInputMode
            r5 = r5 & -257(0xfffffffffffffeff, float:NaN)
            r0.softInputMode = r5
            android.view.View r0 = r12.mView
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            int r5 = r0.softInputMode
            r5 = r5 & -257(0xfffffffffffffeff, float:NaN)
            r0.softInputMode = r5
            r12.mHasHadWindowFocus = r3
            r12.fireAccessibilityFocusEventIfHasFocusedNode()
            goto L_0x011d
        L_0x0116:
            boolean r3 = r12.mPointerCapture
            if (r3 == 0) goto L_0x011d
            r12.handlePointerCaptureChanged(r0)
        L_0x011d:
            android.view.ViewRootImpl$InputStage r0 = r12.mFirstInputStage
            r0.onWindowFocusChanged(r1)
            return
        L_0x0123:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x0123 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.handleWindowFocusChanged():void");
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        View focusedView;
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && (focusedView = this.mView.findFocus()) != null) {
            AccessibilityNodeProvider provider = focusedView.getAccessibilityNodeProvider();
            if (provider == null) {
                focusedView.sendAccessibilityEvent(8);
                return;
            }
            AccessibilityNodeInfo focusedNode = findFocusedVirtualNode(provider);
            if (focusedNode != null) {
                int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(focusedNode.getSourceNodeId());
                AccessibilityEvent event = AccessibilityEvent.obtain(8);
                event.setSource(focusedView, virtualId);
                event.setPackageName(focusedNode.getPackageName());
                event.setChecked(focusedNode.isChecked());
                event.setContentDescription(focusedNode.getContentDescription());
                event.setPassword(focusedNode.isPassword());
                event.getText().add(focusedNode.getText());
                event.setEnabled(focusedNode.isEnabled());
                focusedView.getParent().requestSendAccessibilityEvent(focusedView, event);
                focusedNode.recycle();
            }
        }
    }

    private AccessibilityNodeInfo findFocusedVirtualNode(AccessibilityNodeProvider provider) {
        AccessibilityNodeInfo focusedNode = provider.findFocus(1);
        if (focusedNode != null) {
            return focusedNode;
        }
        if (!this.mContext.isAutofillCompatibilityEnabled()) {
            return null;
        }
        AccessibilityNodeInfo current = provider.createAccessibilityNodeInfo(-1);
        if (current.isFocused()) {
            return current;
        }
        Queue<AccessibilityNodeInfo> fringe = new LinkedList<>();
        fringe.offer(current);
        while (!fringe.isEmpty()) {
            AccessibilityNodeInfo current2 = fringe.poll();
            LongArray childNodeIds = current2.getChildNodeIds();
            if (childNodeIds != null && childNodeIds.size() > 0) {
                int childCount = childNodeIds.size();
                for (int i = 0; i < childCount; i++) {
                    AccessibilityNodeInfo child = provider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(childNodeIds.get(i)));
                    if (child != null) {
                        if (child.isFocused()) {
                            return child;
                        }
                        fringe.offer(child);
                    }
                }
                current2.recycle();
            }
        }
        return null;
    }

    private void handleOutOfResourcesException(Surface.OutOfResourcesException e) {
        Log.e(this.mTag, "OutOfResourcesException initializing HW surface", e);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && Process.myUid() != 1000) {
                Slog.w(this.mTag, "No processes killed for memory; killing self");
                Process.killProcess(Process.myPid());
            }
        } catch (RemoteException e2) {
        }
        this.mLayoutRequested = true;
    }

    private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        if (this.mView != null) {
            Trace.traceBegin(8, "measure");
            try {
                this.mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            } finally {
                Trace.traceEnd(8);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInLayout() {
        return this.mInLayout;
    }

    /* access modifiers changed from: package-private */
    public boolean requestLayoutDuringLayout(View view) {
        if (view.mParent == null || view.mAttachInfo == null) {
            return true;
        }
        if (!this.mLayoutRequesters.contains(view)) {
            this.mLayoutRequesters.add(view);
        }
        if (!this.mHandlingLayoutInLayoutRequest) {
            return true;
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    private void performLayout(WindowManager.LayoutParams lp, int desiredWindowWidth, int desiredWindowHeight) {
        ArrayList<View> validLayoutRequesters;
        this.mLayoutRequested = false;
        this.mScrollMayChange = true;
        this.mInLayout = true;
        View host = this.mView;
        if (host != null) {
            Trace.traceBegin(8, TtmlUtils.TAG_LAYOUT);
            try {
                host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
                this.mInLayout = false;
                if (this.mLayoutRequesters.size() > 0 && (validLayoutRequesters = getValidLayoutRequesters(this.mLayoutRequesters, false)) != null) {
                    this.mHandlingLayoutInLayoutRequest = true;
                    int numValidRequests = validLayoutRequesters.size();
                    for (int i = 0; i < numValidRequests; i++) {
                        View view = validLayoutRequesters.get(i);
                        Log.w("View", "requestLayout() improperly called by " + view + " during layout: running second layout pass");
                        view.requestLayout();
                    }
                    measureHierarchy(host, lp, this.mView.getContext().getResources(), desiredWindowWidth, desiredWindowHeight);
                    this.mInLayout = true;
                    host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
                    this.mHandlingLayoutInLayoutRequest = false;
                    ArrayList<View> validLayoutRequesters2 = getValidLayoutRequesters(this.mLayoutRequesters, true);
                    if (validLayoutRequesters2 != null) {
                        final ArrayList<View> finalRequesters = validLayoutRequesters2;
                        getRunQueue().post(new Runnable() {
                            public void run() {
                                int numValidRequests = finalRequesters.size();
                                for (int i = 0; i < numValidRequests; i++) {
                                    View view = (View) finalRequesters.get(i);
                                    Log.w("View", "requestLayout() improperly called by " + view + " during second layout pass: posting in next frame");
                                    view.requestLayout();
                                }
                            }
                        });
                    }
                }
                Trace.traceEnd(8);
                this.mInLayout = false;
            } catch (Throwable th) {
                Trace.traceEnd(8);
                throw th;
            }
        }
    }

    /* JADX WARNING: type inference failed for: r5v6, types: [android.view.ViewParent] */
    /* JADX WARNING: type inference failed for: r7v5, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.ArrayList<android.view.View> getValidLayoutRequesters(java.util.ArrayList<android.view.View> r10, boolean r11) {
        /*
            r9 = this;
            int r0 = r10.size()
            r1 = 0
            r2 = 0
            r3 = r1
            r1 = r2
        L_0x0008:
            r4 = 4096(0x1000, float:5.74E-42)
            if (r1 >= r0) goto L_0x004f
            java.lang.Object r5 = r10.get(r1)
            android.view.View r5 = (android.view.View) r5
            if (r5 == 0) goto L_0x004c
            android.view.View$AttachInfo r6 = r5.mAttachInfo
            if (r6 == 0) goto L_0x004c
            android.view.ViewParent r6 = r5.mParent
            if (r6 == 0) goto L_0x004c
            if (r11 != 0) goto L_0x0023
            int r6 = r5.mPrivateFlags
            r6 = r6 & r4
            if (r6 != r4) goto L_0x004c
        L_0x0023:
            r4 = 0
            r6 = r5
        L_0x0025:
            if (r6 == 0) goto L_0x003f
            int r7 = r6.mViewFlags
            r7 = r7 & 12
            r8 = 8
            if (r7 != r8) goto L_0x0031
            r4 = 1
            goto L_0x003f
        L_0x0031:
            android.view.ViewParent r7 = r6.mParent
            boolean r7 = r7 instanceof android.view.View
            if (r7 == 0) goto L_0x003d
            android.view.ViewParent r7 = r6.mParent
            r6 = r7
            android.view.View r6 = (android.view.View) r6
            goto L_0x0025
        L_0x003d:
            r6 = 0
            goto L_0x0025
        L_0x003f:
            if (r4 != 0) goto L_0x004c
            if (r3 != 0) goto L_0x0049
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r3 = r7
        L_0x0049:
            r3.add(r5)
        L_0x004c:
            int r1 = r1 + 1
            goto L_0x0008
        L_0x004f:
            if (r11 != 0) goto L_0x0079
        L_0x0052:
            r1 = r2
            if (r1 >= r0) goto L_0x0079
            java.lang.Object r2 = r10.get(r1)
            android.view.View r2 = (android.view.View) r2
        L_0x005b:
            if (r2 == 0) goto L_0x0076
            int r5 = r2.mPrivateFlags
            r5 = r5 & r4
            if (r5 == 0) goto L_0x0076
            int r5 = r2.mPrivateFlags
            r5 = r5 & -4097(0xffffffffffffefff, float:NaN)
            r2.mPrivateFlags = r5
            android.view.ViewParent r5 = r2.mParent
            boolean r5 = r5 instanceof android.view.View
            if (r5 == 0) goto L_0x0074
            android.view.ViewParent r5 = r2.mParent
            r2 = r5
            android.view.View r2 = (android.view.View) r2
            goto L_0x005b
        L_0x0074:
            r2 = 0
            goto L_0x005b
        L_0x0076:
            int r2 = r1 + 1
            goto L_0x0052
        L_0x0079:
            r10.clear()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.getValidLayoutRequesters(java.util.ArrayList, boolean):java.util.ArrayList");
    }

    public void requestTransparentRegion(View child) {
        checkThread();
        if (this.mView == child) {
            this.mView.mPrivateFlags |= 512;
            this.mWindowAttributesChanged = true;
            this.mWindowAttributesChangesFlag = 0;
            requestLayout();
        }
    }

    private static int getRootMeasureSpec(int windowSize, int rootDimension) {
        switch (rootDimension) {
            case -2:
                return View.MeasureSpec.makeMeasureSpec(windowSize, Integer.MIN_VALUE);
            case -1:
                return View.MeasureSpec.makeMeasureSpec(windowSize, 1073741824);
            default:
                return View.MeasureSpec.makeMeasureSpec(rootDimension, 1073741824);
        }
    }

    public void onPreDraw(RecordingCanvas canvas) {
        if (!(this.mCurScrollY == 0 || this.mHardwareYOffset == 0 || !this.mAttachInfo.mThreadedRenderer.isOpaque())) {
            canvas.drawColor(-16777216);
        }
        canvas.translate((float) (-this.mHardwareXOffset), (float) (-this.mHardwareYOffset));
    }

    public void onPostDraw(RecordingCanvas canvas) {
        drawAccessibilityFocusedDrawableIfNeeded(canvas);
        if (this.mUseMTRenderer) {
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                this.mWindowCallbacks.get(i).onPostDraw(canvas);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void outputDisplayList(View view) {
        view.mRenderNode.output();
    }

    /* access modifiers changed from: private */
    public void profileRendering(boolean enabled) {
        if (this.mProfileRendering) {
            this.mRenderProfilingEnabled = enabled;
            if (this.mRenderProfiler != null) {
                this.mChoreographer.removeFrameCallback(this.mRenderProfiler);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler = new Choreographer.FrameCallback() {
                        public void doFrame(long frameTimeNanos) {
                            ViewRootImpl.this.mDirty.set(0, 0, ViewRootImpl.this.mWidth, ViewRootImpl.this.mHeight);
                            ViewRootImpl.this.scheduleTraversals();
                            if (ViewRootImpl.this.mRenderProfilingEnabled) {
                                ViewRootImpl.this.mChoreographer.postFrameCallback(ViewRootImpl.this.mRenderProfiler);
                            }
                        }
                    };
                }
                this.mChoreographer.postFrameCallback(this.mRenderProfiler);
                return;
            }
            this.mRenderProfiler = null;
        }
    }

    private void trackFPS() {
        long nowTime = System.currentTimeMillis();
        if (this.mFpsStartTime < 0) {
            this.mFpsPrevTime = nowTime;
            this.mFpsStartTime = nowTime;
            this.mFpsNumFrames = 0;
            return;
        }
        this.mFpsNumFrames++;
        String thisHash = Integer.toHexString(System.identityHashCode(this));
        long totalTime = nowTime - this.mFpsStartTime;
        Log.v(this.mTag, "0x" + thisHash + "\tFrame time:\t" + (nowTime - this.mFpsPrevTime));
        this.mFpsPrevTime = nowTime;
        if (totalTime > 1000) {
            float fps = (((float) this.mFpsNumFrames) * 1000.0f) / ((float) totalTime);
            Log.v(this.mTag, "0x" + thisHash + "\tFPS:\t" + fps);
            this.mFpsStartTime = nowTime;
            this.mFpsNumFrames = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void drawPending() {
        this.mDrawsNeededToReport++;
    }

    /* access modifiers changed from: package-private */
    public void pendingDrawFinished() {
        if (this.mDrawsNeededToReport != 0) {
            this.mDrawsNeededToReport--;
            if (this.mDrawsNeededToReport == 0) {
                reportDrawFinished();
                return;
            }
            return;
        }
        throw new RuntimeException("Unbalanced drawPending/pendingDrawFinished calls");
    }

    /* access modifiers changed from: private */
    public void postDrawFinished() {
        this.mHandler.sendEmptyMessage(29);
    }

    private void reportDrawFinished() {
        try {
            this.mDrawsNeededToReport = 0;
            this.mWindowSession.finishDrawing(this.mWindow);
        } catch (RemoteException e) {
        }
    }

    /* JADX INFO: finally extract failed */
    private void performDraw() {
        if ((this.mAttachInfo.mDisplayState != 1 || this.mReportNextDraw) && this.mView != null) {
            boolean fullRedrawNeeded = this.mFullRedrawNeeded || this.mReportNextDraw;
            this.mFullRedrawNeeded = false;
            this.mIsDrawing = true;
            Trace.traceBegin(8, "draw");
            boolean usingAsyncReport = false;
            if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.isEnabled()) {
                ArrayList<Runnable> commitCallbacks = this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks();
                if (this.mReportNextDraw) {
                    usingAsyncReport = true;
                    this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(new HardwareRenderer.FrameCompleteCallback(this.mAttachInfo.mHandler, commitCallbacks) {
                        private final /* synthetic */ Handler f$1;
                        private final /* synthetic */ ArrayList f$2;

                        {
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void onFrameComplete(long j) {
                            this.f$1.postAtFrontOfQueue(new Runnable(this.f$2) {
                                private final /* synthetic */ ArrayList f$1;

                                {
                                    this.f$1 = r2;
                                }

                                public final void run() {
                                    ViewRootImpl.lambda$performDraw$1(ViewRootImpl.this, this.f$1);
                                }
                            });
                        }
                    });
                } else if (commitCallbacks != null && commitCallbacks.size() > 0) {
                    this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(new HardwareRenderer.FrameCompleteCallback(commitCallbacks) {
                        private final /* synthetic */ ArrayList f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void onFrameComplete(long j) {
                            Handler.this.postAtFrontOfQueue(new Runnable(this.f$1) {
                                private final /* synthetic */ ArrayList f$0;

                                {
                                    this.f$0 = r1;
                                }

                                public final void run() {
                                    ViewRootImpl.lambda$performDraw$3(this.f$0);
                                }
                            });
                        }
                    });
                }
            }
            try {
                boolean canUseAsync = draw(fullRedrawNeeded);
                if (usingAsyncReport && !canUseAsync) {
                    this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback((HardwareRenderer.FrameCompleteCallback) null);
                    usingAsyncReport = false;
                }
                this.mIsDrawing = false;
                Trace.traceEnd(8);
                if (this.mAttachInfo.mPendingAnimatingRenderNodes != null) {
                    int count = this.mAttachInfo.mPendingAnimatingRenderNodes.size();
                    for (int i = 0; i < count; i++) {
                        this.mAttachInfo.mPendingAnimatingRenderNodes.get(i).endAllAnimators();
                    }
                    this.mAttachInfo.mPendingAnimatingRenderNodes.clear();
                }
                if (this.mReportNextDraw != 0) {
                    this.mReportNextDraw = false;
                    if (this.mWindowDrawCountDown != null) {
                        try {
                            this.mWindowDrawCountDown.await();
                        } catch (InterruptedException e) {
                            Log.e(this.mTag, "Window redraw count down interrupted!");
                        }
                        this.mWindowDrawCountDown = null;
                    }
                    if (this.mAttachInfo.mThreadedRenderer != null) {
                        this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
                    }
                    if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                        new SurfaceCallbackHelper(new Runnable() {
                            public final void run() {
                                ViewRootImpl.this.postDrawFinished();
                            }
                        }).dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, this.mSurfaceHolder.getCallbacks());
                    } else if (!usingAsyncReport) {
                        if (this.mAttachInfo.mThreadedRenderer != null) {
                            this.mAttachInfo.mThreadedRenderer.fence();
                        }
                        pendingDrawFinished();
                    }
                }
            } catch (Throwable th) {
                this.mIsDrawing = false;
                Trace.traceEnd(8);
                throw th;
            }
        }
    }

    public static /* synthetic */ void lambda$performDraw$1(ViewRootImpl viewRootImpl, ArrayList commitCallbacks) {
        viewRootImpl.pendingDrawFinished();
        if (commitCallbacks != null) {
            for (int i = 0; i < commitCallbacks.size(); i++) {
                ((Runnable) commitCallbacks.get(i)).run();
            }
        }
    }

    static /* synthetic */ void lambda$performDraw$3(ArrayList commitCallbacks) {
        for (int i = 0; i < commitCallbacks.size(); i++) {
            ((Runnable) commitCallbacks.get(i)).run();
        }
    }

    private boolean draw(boolean fullRedrawNeeded) {
        int curScrollY;
        boolean fullRedrawNeeded2;
        Drawable drawable;
        Surface surface = this.mSurface;
        if (!surface.isValid()) {
            return false;
        }
        if (!sFirstDrawComplete) {
            synchronized (sFirstDrawHandlers) {
                sFirstDrawComplete = true;
                int count = sFirstDrawHandlers.size();
                for (int i = 0; i < count; i++) {
                    this.mHandler.post(sFirstDrawHandlers.get(i));
                }
            }
        }
        Rect rect = null;
        scrollToRectOrFocus((Rect) null, false);
        if (this.mAttachInfo.mViewScrollChanged) {
            this.mAttachInfo.mViewScrollChanged = false;
            this.mAttachInfo.mTreeObserver.dispatchOnScrollChanged();
        }
        boolean animating = this.mScroller != null && this.mScroller.computeScrollOffset();
        if (animating) {
            curScrollY = this.mScroller.getCurrY();
        } else {
            curScrollY = this.mScrollY;
        }
        int curScrollY2 = curScrollY;
        if (this.mCurScrollY != curScrollY2) {
            this.mCurScrollY = curScrollY2;
            if (this.mView instanceof RootViewSurfaceTaker) {
                ((RootViewSurfaceTaker) this.mView).onRootViewScrollYChanged(this.mCurScrollY);
            }
            fullRedrawNeeded2 = true;
        } else {
            fullRedrawNeeded2 = fullRedrawNeeded;
        }
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean scalingRequired = this.mAttachInfo.mScalingRequired;
        Rect dirty = this.mDirty;
        if (this.mSurfaceHolder != null) {
            dirty.setEmpty();
            if (animating && this.mScroller != null) {
                this.mScroller.abortAnimation();
            }
            return false;
        }
        if (fullRedrawNeeded2) {
            dirty.set(0, 0, (int) ((((float) this.mWidth) * appScale) + 0.5f), (int) ((((float) this.mHeight) * appScale) + 0.5f));
        }
        this.mAttachInfo.mTreeObserver.dispatchOnDraw();
        int xOffset = -this.mCanvasOffsetX;
        int yOffset = (-this.mCanvasOffsetY) + curScrollY2;
        WindowManager.LayoutParams params = this.mWindowAttributes;
        if (params != null) {
            rect = params.surfaceInsets;
        }
        Rect surfaceInsets = rect;
        if (surfaceInsets != null) {
            xOffset -= surfaceInsets.left;
            yOffset -= surfaceInsets.top;
            dirty.offset(surfaceInsets.left, surfaceInsets.right);
        }
        int yOffset2 = yOffset;
        int xOffset2 = xOffset;
        boolean accessibilityFocusDirty = false;
        Drawable drawable2 = this.mAttachInfo.mAccessibilityFocusDrawable;
        if (drawable2 != null) {
            Rect bounds = this.mAttachInfo.mTmpInvalRect;
            if (!getAccessibilityFocusedRect(bounds)) {
                bounds.setEmpty();
            }
            if (!bounds.equals(drawable2.getBounds())) {
                accessibilityFocusDirty = true;
            }
        }
        boolean accessibilityFocusDirty2 = accessibilityFocusDirty;
        int i2 = curScrollY2;
        boolean z = fullRedrawNeeded2;
        this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / TimeUtils.NANOS_PER_MS;
        boolean useAsyncReport = false;
        if (dirty.isEmpty() && !this.mIsAnimating && !accessibilityFocusDirty2) {
            Drawable drawable3 = drawable2;
            int i3 = xOffset2;
            int i4 = yOffset2;
            Rect rect2 = surfaceInsets;
            WindowManager.LayoutParams layoutParams = params;
            Rect rect3 = dirty;
            boolean z2 = scalingRequired;
            float f = appScale;
        } else if (this.mAttachInfo.mThreadedRenderer == null || !this.mAttachInfo.mThreadedRenderer.isEnabled()) {
            Drawable drawable4 = drawable2;
            if (this.mAttachInfo.mThreadedRenderer == null || this.mAttachInfo.mThreadedRenderer.isEnabled() || !this.mAttachInfo.mThreadedRenderer.isRequested() || !this.mSurface.isValid()) {
                Drawable drawable5 = drawable4;
                WindowManager.LayoutParams layoutParams2 = params;
                boolean z3 = scalingRequired;
                float f2 = appScale;
                if (!drawSoftware(surface, this.mAttachInfo, xOffset2, yOffset2, scalingRequired, dirty, surfaceInsets)) {
                    return false;
                }
            } else {
                try {
                    int i5 = xOffset2;
                    try {
                        this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, surfaceInsets);
                        this.mFullRedrawNeeded = true;
                        scheduleTraversals();
                        return false;
                    } catch (Surface.OutOfResourcesException e) {
                        e = e;
                        handleOutOfResourcesException(e);
                        return false;
                    }
                } catch (Surface.OutOfResourcesException e2) {
                    e = e2;
                    int i6 = xOffset2;
                    handleOutOfResourcesException(e);
                    return false;
                }
            }
        } else {
            boolean invalidateRoot = accessibilityFocusDirty2 || this.mInvalidateRootRequested;
            this.mInvalidateRootRequested = false;
            this.mIsAnimating = false;
            if (!(this.mHardwareYOffset == yOffset2 && this.mHardwareXOffset == xOffset2)) {
                this.mHardwareYOffset = yOffset2;
                this.mHardwareXOffset = xOffset2;
                invalidateRoot = true;
            }
            if (invalidateRoot) {
                this.mAttachInfo.mThreadedRenderer.invalidateRoot();
            }
            dirty.setEmpty();
            boolean updated = updateContentDrawBounds();
            if (this.mReportNextDraw) {
                drawable = drawable2;
                this.mAttachInfo.mThreadedRenderer.setStopped(false);
            } else {
                drawable = drawable2;
            }
            if (updated) {
                requestDrawWindow();
            }
            useAsyncReport = true;
            boolean z4 = invalidateRoot;
            this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
            int i7 = xOffset2;
            int i8 = yOffset2;
            Rect rect4 = surfaceInsets;
            WindowManager.LayoutParams layoutParams3 = params;
            Rect rect5 = dirty;
            boolean z5 = scalingRequired;
            float f3 = appScale;
            Drawable drawable6 = drawable;
        }
        if (animating) {
            this.mFullRedrawNeeded = true;
            scheduleTraversals();
        }
        return useAsyncReport;
    }

    /* Debug info: failed to restart local var, previous not found, register: 16 */
    private boolean drawSoftware(Surface surface, View.AttachInfo attachInfo, int xoff, int yoff, boolean scalingRequired, Rect dirty, Rect surfaceInsets) {
        Surface surface2 = surface;
        int i = xoff;
        int i2 = yoff;
        Rect rect = dirty;
        Rect rect2 = surfaceInsets;
        int dirtyXOffset = xoff;
        int dirtyYOffset = yoff;
        if (rect2 != null) {
            dirtyXOffset += rect2.left;
            dirtyYOffset += rect2.top;
        }
        int dirtyYOffset2 = dirtyYOffset;
        int dirtyXOffset2 = dirtyXOffset;
        try {
            rect.offset(-dirtyXOffset2, -dirtyYOffset2);
            int i3 = rect.left;
            int i4 = rect.top;
            int i5 = rect.right;
            int i6 = rect.bottom;
            Canvas canvas = this.mSurface.lockCanvas(rect);
            canvas.setDensity(this.mDensity);
            try {
                if (!(canvas.isOpaque() && i2 == 0 && i == 0)) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                }
                dirty.setEmpty();
                this.mIsAnimating = false;
                this.mView.mPrivateFlags |= 32;
                canvas.translate((float) (-i), (float) (-i2));
                if (this.mTranslator != null) {
                    this.mTranslator.translateCanvas(canvas);
                }
                canvas.setScreenDensity(scalingRequired ? this.mNoncompatDensity : 0);
                this.mView.draw(canvas);
                drawAccessibilityFocusedDrawableIfNeeded(canvas);
                try {
                    return true;
                } catch (IllegalArgumentException e) {
                    Log.e(this.mTag, "Could not unlock surface", e);
                    this.mLayoutRequested = true;
                    return false;
                }
            } finally {
                surface2.unlockCanvasAndPost(canvas);
            }
        } catch (Surface.OutOfResourcesException e2) {
            handleOutOfResourcesException(e2);
            return false;
        } catch (IllegalArgumentException e3) {
            Log.e(this.mTag, "Could not lock surface", e3);
            this.mLayoutRequested = true;
            return false;
        } finally {
            rect.offset(dirtyXOffset2, dirtyYOffset2);
        }
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(Canvas canvas) {
        Rect bounds = this.mAttachInfo.mTmpInvalRect;
        if (getAccessibilityFocusedRect(bounds)) {
            Drawable drawable = getAccessibilityFocusedDrawable();
            if (drawable != null) {
                drawable.setBounds(bounds);
                drawable.draw(canvas);
            }
        } else if (this.mAttachInfo.mAccessibilityFocusDrawable != null) {
            this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
        }
    }

    private boolean getAccessibilityFocusedRect(Rect bounds) {
        View host;
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mView.mContext);
        if (!manager.isEnabled() || !manager.isTouchExplorationEnabled() || (host = this.mAccessibilityFocusedHost) == null || host.mAttachInfo == null) {
            return false;
        }
        if (host.getAccessibilityNodeProvider() == null) {
            host.getBoundsOnScreen(bounds, true);
        } else if (this.mAccessibilityFocusedVirtualView == null) {
            return false;
        } else {
            this.mAccessibilityFocusedVirtualView.getBoundsInScreen(bounds);
        }
        View.AttachInfo attachInfo = this.mAttachInfo;
        bounds.offset(0, attachInfo.mViewRootImpl.mScrollY);
        bounds.offset(-attachInfo.mWindowLeft, -attachInfo.mWindowTop);
        if (!bounds.intersect(0, 0, attachInfo.mViewRootImpl.mWidth, attachInfo.mViewRootImpl.mHeight)) {
            bounds.setEmpty();
        }
        return !bounds.isEmpty();
    }

    private Drawable getAccessibilityFocusedDrawable() {
        if (this.mAttachInfo.mAccessibilityFocusDrawable == null) {
            TypedValue value = new TypedValue();
            if (this.mView.mContext.getTheme().resolveAttribute(R.attr.accessibilityFocusedDrawable, value, true)) {
                this.mAttachInfo.mAccessibilityFocusDrawable = this.mView.mContext.getDrawable(value.resourceId);
            }
        }
        return this.mAttachInfo.mAccessibilityFocusDrawable;
    }

    /* access modifiers changed from: package-private */
    public void updateSystemGestureExclusionRectsForView(View view) {
        this.mGestureExclusionTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(32);
    }

    /* access modifiers changed from: package-private */
    public void systemGestureExclusionChanged() {
        List<Rect> rectsForWindowManager = this.mGestureExclusionTracker.computeChangedRects();
        if (rectsForWindowManager != null && this.mView != null) {
            try {
                this.mWindowSession.reportSystemGestureExclusionChanged(this.mWindow, rectsForWindowManager);
                this.mAttachInfo.mTreeObserver.dispatchOnSystemGestureExclusionRectsChanged(rectsForWindowManager);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setRootSystemGestureExclusionRects(List<Rect> rects) {
        this.mGestureExclusionTracker.setRootSystemGestureExclusionRects(rects);
        this.mHandler.sendEmptyMessage(32);
    }

    public List<Rect> getRootSystemGestureExclusionRects() {
        return this.mGestureExclusionTracker.getRootSystemGestureExclusionRects();
    }

    public void requestInvalidateRootRenderNode() {
        this.mInvalidateRootRequested = true;
    }

    /* access modifiers changed from: package-private */
    public boolean scrollToRectOrFocus(Rect rectangle, boolean immediate) {
        int height;
        Rect ci = this.mAttachInfo.mContentInsets;
        Rect vi = this.mAttachInfo.mVisibleInsets;
        int scrollY = 0;
        boolean handled = false;
        if (vi.left > ci.left || vi.top > ci.top || vi.right > ci.right || vi.bottom > ci.bottom) {
            scrollY = this.mScrollY;
            View focus = this.mView.findFocus();
            if (focus == null) {
                return false;
            }
            View lastScrolledFocus = this.mLastScrolledFocus != null ? (View) this.mLastScrolledFocus.get() : null;
            if (focus != lastScrolledFocus) {
                rectangle = null;
            }
            if (!(focus == lastScrolledFocus && !this.mScrollMayChange && rectangle == null)) {
                this.mLastScrolledFocus = new WeakReference<>(focus);
                this.mScrollMayChange = false;
                if (focus.getGlobalVisibleRect(this.mVisRect, (Point) null)) {
                    if (rectangle == null) {
                        focus.getFocusedRect(this.mTempRect);
                        if (this.mView instanceof ViewGroup) {
                            ((ViewGroup) this.mView).offsetDescendantRectToMyCoords(focus, this.mTempRect);
                        }
                    } else {
                        this.mTempRect.set(rectangle);
                    }
                    if (this.mTempRect.intersect(this.mVisRect)) {
                        if (this.mTempRect.height() <= (this.mView.getHeight() - vi.top) - vi.bottom) {
                            if (this.mTempRect.top < vi.top) {
                                height = this.mTempRect.top - vi.top;
                            } else if (this.mTempRect.bottom > this.mView.getHeight() - vi.bottom) {
                                height = this.mTempRect.bottom - (this.mView.getHeight() - vi.bottom);
                            } else {
                                scrollY = 0;
                            }
                            scrollY = height;
                        }
                        handled = true;
                    }
                }
            }
        }
        if (scrollY != this.mScrollY) {
            if (!immediate) {
                if (this.mScroller == null) {
                    this.mScroller = new Scroller(this.mView.getContext());
                }
                this.mScroller.startScroll(0, this.mScrollY, 0, scrollY - this.mScrollY);
            } else if (this.mScroller != null) {
                this.mScroller.abortAnimation();
            }
            this.mScrollY = scrollY;
        }
        return handled;
    }

    @UnsupportedAppUsage
    public View getAccessibilityFocusedHost() {
        return this.mAccessibilityFocusedHost;
    }

    @UnsupportedAppUsage
    public AccessibilityNodeInfo getAccessibilityFocusedVirtualView() {
        return this.mAccessibilityFocusedVirtualView;
    }

    /* access modifiers changed from: package-private */
    public void setAccessibilityFocus(View view, AccessibilityNodeInfo node) {
        if (this.mAccessibilityFocusedVirtualView != null) {
            AccessibilityNodeInfo focusNode = this.mAccessibilityFocusedVirtualView;
            View focusHost = this.mAccessibilityFocusedHost;
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            focusHost.clearAccessibilityFocusNoCallbacks(64);
            AccessibilityNodeProvider provider = focusHost.getAccessibilityNodeProvider();
            if (provider != null) {
                focusNode.getBoundsInParent(this.mTempRect);
                focusHost.invalidate(this.mTempRect);
                provider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(focusNode.getSourceNodeId()), 128, (Bundle) null);
            }
            focusNode.recycle();
        }
        if (!(this.mAccessibilityFocusedHost == null || this.mAccessibilityFocusedHost == view)) {
            this.mAccessibilityFocusedHost.clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = node;
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.invalidateRoot();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    /* access modifiers changed from: package-private */
    public void requestPointerCapture(boolean enabled) {
        if (this.mPointerCapture != enabled) {
            InputManager.getInstance().requestPointerCapture(this.mAttachInfo.mWindowToken, enabled);
        }
    }

    /* access modifiers changed from: private */
    public void handlePointerCaptureChanged(boolean hasCapture) {
        if (this.mPointerCapture != hasCapture) {
            this.mPointerCapture = hasCapture;
            if (this.mView != null) {
                this.mView.dispatchPointerCaptureChanged(hasCapture);
            }
        }
    }

    private boolean hasColorModeChanged(int colorMode) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return false;
        }
        boolean isWideGamut = colorMode == 1;
        if (this.mAttachInfo.mThreadedRenderer.isWideGamut() == isWideGamut) {
            return false;
        }
        if (!isWideGamut || this.mContext.getResources().getConfiguration().isScreenWideColorGamut()) {
            return true;
        }
        return false;
    }

    public void requestChildFocus(View child, View focused) {
        checkThread();
        scheduleTraversals();
    }

    public void clearChildFocus(View child) {
        checkThread();
        scheduleTraversals();
    }

    public ViewParent getParentForAccessibility() {
        return null;
    }

    public void focusableViewAvailable(View v) {
        checkThread();
        if (this.mView == null) {
            return;
        }
        if (this.mView.hasFocus()) {
            View focused = this.mView.findFocus();
            if ((focused instanceof ViewGroup) && ((ViewGroup) focused).getDescendantFocusability() == 262144 && isViewDescendantOf(v, focused)) {
                v.requestFocus();
            }
        } else if (sAlwaysAssignFocus || !this.mAttachInfo.mInTouchMode) {
            v.requestFocus();
        }
    }

    public void recomputeViewAttributes(View child) {
        checkThread();
        if (this.mView == child) {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
            if (!this.mWillDrawSoon) {
                scheduleTraversals();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchDetachedFromWindow() {
        this.mFirstInputStage.onDetachedFromWindow();
        if (!(this.mView == null || this.mView.mAttachInfo == null)) {
            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(false);
            this.mView.dispatchDetachedFromWindow();
        }
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(this.mHighContrastTextManager);
        removeSendWindowContentChangedCallback();
        destroyHardwareRenderer();
        setAccessibilityFocus((View) null, (AccessibilityNodeInfo) null);
        this.mView.assignParent((ViewParent) null);
        this.mView = null;
        this.mAttachInfo.mRootView = null;
        destroySurface();
        if (!(this.mInputQueueCallback == null || this.mInputQueue == null)) {
            this.mInputQueueCallback.onInputQueueDestroyed(this.mInputQueue);
            this.mInputQueue.dispose();
            this.mInputQueueCallback = null;
            this.mInputQueue = null;
        }
        if (this.mInputEventReceiver != null) {
            this.mInputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        try {
            this.mWindowSession.remove(this.mWindow);
        } catch (RemoteException e) {
        }
        if (this.mInputChannel != null) {
            this.mInputChannel.dispose();
            this.mInputChannel = null;
        }
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        unscheduleTraversals();
    }

    /* access modifiers changed from: private */
    public void performConfigurationChange(MergedConfiguration mergedConfiguration, boolean force, int newDisplayId) {
        if (mergedConfiguration != null) {
            Configuration globalConfig = mergedConfiguration.getGlobalConfiguration();
            Configuration overrideConfig = mergedConfiguration.getOverrideConfiguration();
            CompatibilityInfo ci = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
            if (!ci.equals(CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
                globalConfig = new Configuration(globalConfig);
                ci.applyToConfiguration(this.mNoncompatDensity, globalConfig);
            }
            synchronized (sConfigCallbacks) {
                for (int i = sConfigCallbacks.size() - 1; i >= 0; i--) {
                    sConfigCallbacks.get(i).onConfigurationChanged(globalConfig);
                }
            }
            this.mLastReportedMergedConfiguration.setConfiguration(globalConfig, overrideConfig);
            this.mForceNextConfigUpdate = force;
            if (this.mActivityConfigCallback != null) {
                this.mActivityConfigCallback.onConfigurationChanged(overrideConfig, newDisplayId);
            } else {
                updateConfiguration(newDisplayId);
            }
            this.mForceNextConfigUpdate = false;
            return;
        }
        throw new IllegalArgumentException("No merged config provided.");
    }

    public void updateConfiguration(int newDisplayId) {
        if (this.mView != null) {
            Resources localResources = this.mView.getResources();
            Configuration config = localResources.getConfiguration();
            if (newDisplayId != -1) {
                onMovedToDisplay(newDisplayId, config);
            }
            if (this.mForceNextConfigUpdate || this.mLastConfigurationFromResources.diff(config) != 0) {
                updateInternalDisplay(this.mDisplay.getDisplayId(), localResources);
                int lastLayoutDirection = this.mLastConfigurationFromResources.getLayoutDirection();
                int currentLayoutDirection = config.getLayoutDirection();
                this.mLastConfigurationFromResources.setTo(config);
                if (lastLayoutDirection != currentLayoutDirection && this.mViewLayoutDirectionInitial == 2) {
                    this.mView.setLayoutDirection(currentLayoutDirection);
                }
                this.mView.dispatchConfigurationChanged(config);
                this.mForceNextWindowRelayout = true;
                requestLayout();
            }
            updateForceDarkMode();
        }
    }

    public static boolean isViewDescendantOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        ViewParent theParent = child.getParent();
        if (!(theParent instanceof ViewGroup) || !isViewDescendantOf((View) theParent, parent)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void forceLayout(View view) {
        view.forceLayout();
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                forceLayout(group.getChildAt(i));
            }
        }
    }

    final class ViewRootHandler extends Handler {
        ViewRootHandler() {
        }

        public String getMessageName(Message message) {
            switch (message.what) {
                case 1:
                    return "MSG_INVALIDATE";
                case 2:
                    return "MSG_INVALIDATE_RECT";
                case 3:
                    return "MSG_DIE";
                case 4:
                    return "MSG_RESIZED";
                case 5:
                    return "MSG_RESIZED_REPORT";
                case 6:
                    return "MSG_WINDOW_FOCUS_CHANGED";
                case 7:
                    return "MSG_DISPATCH_INPUT_EVENT";
                case 8:
                    return "MSG_DISPATCH_APP_VISIBILITY";
                case 9:
                    return "MSG_DISPATCH_GET_NEW_SURFACE";
                case 11:
                    return "MSG_DISPATCH_KEY_FROM_IME";
                case 12:
                    return "MSG_DISPATCH_KEY_FROM_AUTOFILL";
                case 13:
                    return "MSG_CHECK_FOCUS";
                case 14:
                    return "MSG_CLOSE_SYSTEM_DIALOGS";
                case 15:
                    return "MSG_DISPATCH_DRAG_EVENT";
                case 16:
                    return "MSG_DISPATCH_DRAG_LOCATION_EVENT";
                case 17:
                    return "MSG_DISPATCH_SYSTEM_UI_VISIBILITY";
                case 18:
                    return "MSG_UPDATE_CONFIGURATION";
                case 19:
                    return "MSG_PROCESS_INPUT_EVENTS";
                case 21:
                    return "MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST";
                case 23:
                    return "MSG_WINDOW_MOVED";
                case 24:
                    return "MSG_SYNTHESIZE_INPUT_EVENT";
                case 25:
                    return "MSG_DISPATCH_WINDOW_SHOWN";
                case 27:
                    return "MSG_UPDATE_POINTER_ICON";
                case 28:
                    return "MSG_POINTER_CAPTURE_CHANGED";
                case 29:
                    return "MSG_DRAW_FINISHED";
                case 30:
                    return "MSG_INSETS_CHANGED";
                case 31:
                    return "MSG_INSETS_CONTROL_CHANGED";
                case 32:
                    return "MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED";
                default:
                    return super.getMessageName(message);
            }
        }

        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            if (msg.what != 26 || msg.obj != null) {
                return super.sendMessageAtTime(msg, uptimeMillis);
            }
            throw new NullPointerException("Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
        }

        public void handleMessage(Message msg) {
            int i = -1;
            boolean hasCapture = true;
            switch (msg.what) {
                case 1:
                    ((View) msg.obj).invalidate();
                    return;
                case 2:
                    View.AttachInfo.InvalidateInfo info = (View.AttachInfo.InvalidateInfo) msg.obj;
                    info.target.invalidate(info.left, info.top, info.right, info.bottom);
                    info.recycle();
                    return;
                case 3:
                    ViewRootImpl.this.doDie();
                    return;
                case 4:
                    SomeArgs args = (SomeArgs) msg.obj;
                    if (ViewRootImpl.this.mWinFrame.equals(args.arg1) && ViewRootImpl.this.mPendingOverscanInsets.equals(args.arg5) && ViewRootImpl.this.mPendingContentInsets.equals(args.arg2) && ViewRootImpl.this.mPendingStableInsets.equals(args.arg6) && ViewRootImpl.this.mPendingDisplayCutout.get().equals(args.arg9) && ViewRootImpl.this.mPendingVisibleInsets.equals(args.arg3) && ViewRootImpl.this.mPendingOutsets.equals(args.arg7) && ViewRootImpl.this.mPendingBackDropFrame.equals(args.arg8) && args.arg4 == null && args.argi1 == 0 && ViewRootImpl.this.mDisplay.getDisplayId() == args.argi3) {
                        return;
                    }
                case 5:
                    break;
                case 6:
                    ViewRootImpl.this.handleWindowFocusChanged();
                    return;
                case 7:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) args2.arg1, (InputEventReceiver) args2.arg2, 0, true);
                    args2.recycle();
                    return;
                case 8:
                    ViewRootImpl viewRootImpl = ViewRootImpl.this;
                    if (msg.arg1 == 0) {
                        hasCapture = false;
                    }
                    viewRootImpl.handleAppVisibility(hasCapture);
                    return;
                case 9:
                    ViewRootImpl.this.handleGetNewSurface();
                    return;
                case 11:
                    KeyEvent event = (KeyEvent) msg.obj;
                    if ((event.getFlags() & 8) != 0) {
                        event = KeyEvent.changeFlags(event, event.getFlags() & -9);
                    }
                    ViewRootImpl.this.enqueueInputEvent(event, (InputEventReceiver) null, 1, true);
                    return;
                case 12:
                    ViewRootImpl.this.enqueueInputEvent((KeyEvent) msg.obj, (InputEventReceiver) null, 0, true);
                    return;
                case 13:
                    InputMethodManager imm = (InputMethodManager) ViewRootImpl.this.mContext.getSystemService(InputMethodManager.class);
                    if (imm != null) {
                        imm.checkFocus();
                        return;
                    }
                    return;
                case 14:
                    if (ViewRootImpl.this.mView != null) {
                        ViewRootImpl.this.mView.onCloseSystemDialogs((String) msg.obj);
                        return;
                    }
                    return;
                case 15:
                case 16:
                    DragEvent event2 = (DragEvent) msg.obj;
                    event2.mLocalState = ViewRootImpl.this.mLocalDragState;
                    ViewRootImpl.this.handleDragEvent(event2);
                    return;
                case 17:
                    ViewRootImpl.this.handleDispatchSystemUiVisibilityChanged((SystemUiVisibilityInfo) msg.obj);
                    return;
                case 18:
                    Configuration config = (Configuration) msg.obj;
                    if (config.isOtherSeqNewer(ViewRootImpl.this.mLastReportedMergedConfiguration.getMergedConfiguration())) {
                        config = ViewRootImpl.this.mLastReportedMergedConfiguration.getGlobalConfiguration();
                    }
                    ViewRootImpl.this.mPendingMergedConfiguration.setConfiguration(config, ViewRootImpl.this.mLastReportedMergedConfiguration.getOverrideConfiguration());
                    ViewRootImpl.this.performConfigurationChange(ViewRootImpl.this.mPendingMergedConfiguration, false, -1);
                    return;
                case 19:
                    ViewRootImpl.this.mProcessInputEventsScheduled = false;
                    ViewRootImpl.this.doProcessInputEvents();
                    return;
                case 21:
                    ViewRootImpl.this.setAccessibilityFocus((View) null, (AccessibilityNodeInfo) null);
                    return;
                case 22:
                    if (ViewRootImpl.this.mView != null) {
                        ViewRootImpl.this.invalidateWorld(ViewRootImpl.this.mView);
                        return;
                    }
                    return;
                case 23:
                    if (ViewRootImpl.this.mAdded) {
                        int w = ViewRootImpl.this.mWinFrame.width();
                        int h = ViewRootImpl.this.mWinFrame.height();
                        int l = msg.arg1;
                        int t = msg.arg2;
                        ViewRootImpl.this.mTmpFrame.left = l;
                        ViewRootImpl.this.mTmpFrame.right = l + w;
                        ViewRootImpl.this.mTmpFrame.top = t;
                        ViewRootImpl.this.mTmpFrame.bottom = t + h;
                        ViewRootImpl.this.setFrame(ViewRootImpl.this.mTmpFrame);
                        ViewRootImpl.this.mPendingBackDropFrame.set(ViewRootImpl.this.mWinFrame);
                        ViewRootImpl.this.maybeHandleWindowMove(ViewRootImpl.this.mWinFrame);
                        return;
                    }
                    return;
                case 24:
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) msg.obj, (InputEventReceiver) null, 32, true);
                    return;
                case 25:
                    ViewRootImpl.this.handleDispatchWindowShown();
                    return;
                case 26:
                    ViewRootImpl.this.handleRequestKeyboardShortcuts((IResultReceiver) msg.obj, msg.arg1);
                    return;
                case 27:
                    ViewRootImpl.this.resetPointerIcon((MotionEvent) msg.obj);
                    return;
                case 28:
                    if (msg.arg1 == 0) {
                        hasCapture = false;
                    }
                    ViewRootImpl.this.handlePointerCaptureChanged(hasCapture);
                    return;
                case 29:
                    ViewRootImpl.this.pendingDrawFinished();
                    return;
                case 30:
                    ViewRootImpl.this.mInsetsController.onStateChanged((InsetsState) msg.obj);
                    return;
                case 31:
                    SomeArgs args3 = (SomeArgs) msg.obj;
                    ViewRootImpl.this.mInsetsController.onControlsChanged((InsetsSourceControl[]) args3.arg2);
                    ViewRootImpl.this.mInsetsController.onStateChanged((InsetsState) args3.arg1);
                    return;
                case 32:
                    ViewRootImpl.this.systemGestureExclusionChanged();
                    return;
                default:
                    return;
            }
            if (ViewRootImpl.this.mAdded) {
                SomeArgs args4 = (SomeArgs) msg.obj;
                int displayId = args4.argi3;
                MergedConfiguration mergedConfiguration = (MergedConfiguration) args4.arg4;
                boolean displayChanged = ViewRootImpl.this.mDisplay.getDisplayId() != displayId;
                boolean configChanged = false;
                if (!ViewRootImpl.this.mLastReportedMergedConfiguration.equals(mergedConfiguration)) {
                    ViewRootImpl viewRootImpl2 = ViewRootImpl.this;
                    if (displayChanged) {
                        i = displayId;
                    }
                    viewRootImpl2.performConfigurationChange(mergedConfiguration, false, i);
                    configChanged = true;
                } else if (displayChanged) {
                    ViewRootImpl.this.onMovedToDisplay(displayId, ViewRootImpl.this.mLastConfigurationFromResources);
                }
                boolean framesChanged = !ViewRootImpl.this.mWinFrame.equals(args4.arg1) || !ViewRootImpl.this.mPendingOverscanInsets.equals(args4.arg5) || !ViewRootImpl.this.mPendingContentInsets.equals(args4.arg2) || !ViewRootImpl.this.mPendingStableInsets.equals(args4.arg6) || !ViewRootImpl.this.mPendingDisplayCutout.get().equals(args4.arg9) || !ViewRootImpl.this.mPendingVisibleInsets.equals(args4.arg3) || !ViewRootImpl.this.mPendingOutsets.equals(args4.arg7);
                ViewRootImpl.this.setFrame((Rect) args4.arg1);
                ViewRootImpl.this.mPendingOverscanInsets.set((Rect) args4.arg5);
                ViewRootImpl.this.mPendingContentInsets.set((Rect) args4.arg2);
                ViewRootImpl.this.mPendingStableInsets.set((Rect) args4.arg6);
                ViewRootImpl.this.mPendingDisplayCutout.set((DisplayCutout) args4.arg9);
                ViewRootImpl.this.mPendingVisibleInsets.set((Rect) args4.arg3);
                ViewRootImpl.this.mPendingOutsets.set((Rect) args4.arg7);
                ViewRootImpl.this.mPendingBackDropFrame.set((Rect) args4.arg8);
                ViewRootImpl.this.mForceNextWindowRelayout = args4.argi1 != 0;
                ViewRootImpl viewRootImpl3 = ViewRootImpl.this;
                if (args4.argi2 == 0) {
                    hasCapture = false;
                }
                viewRootImpl3.mPendingAlwaysConsumeSystemBars = hasCapture;
                args4.recycle();
                if (msg.what == 5) {
                    ViewRootImpl.this.reportNextDraw();
                }
                if (ViewRootImpl.this.mView != null && (framesChanged || configChanged)) {
                    ViewRootImpl.forceLayout(ViewRootImpl.this.mView);
                }
                ViewRootImpl.this.requestLayout();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean ensureTouchMode(boolean inTouchMode) {
        if (this.mAttachInfo.mInTouchMode == inTouchMode) {
            return false;
        }
        try {
            this.mWindowSession.setInTouchMode(inTouchMode);
            return ensureTouchModeLocally(inTouchMode);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean ensureTouchModeLocally(boolean inTouchMode) {
        if (this.mAttachInfo.mInTouchMode == inTouchMode) {
            return false;
        }
        this.mAttachInfo.mInTouchMode = inTouchMode;
        this.mAttachInfo.mTreeObserver.dispatchOnTouchModeChanged(inTouchMode);
        return inTouchMode ? enterTouchMode() : leaveTouchMode();
    }

    private boolean enterTouchMode() {
        View focused;
        if (this.mView == null || !this.mView.hasFocus() || (focused = this.mView.findFocus()) == null || focused.isFocusableInTouchMode()) {
            return false;
        }
        ViewGroup ancestorToTakeFocus = findAncestorToTakeFocusInTouchMode(focused);
        if (ancestorToTakeFocus != null) {
            return ancestorToTakeFocus.requestFocus();
        }
        focused.clearFocusInternal((View) null, true, false);
        return true;
    }

    private static ViewGroup findAncestorToTakeFocusInTouchMode(View focused) {
        ViewParent parent = focused.getParent();
        while (parent instanceof ViewGroup) {
            ViewGroup vgParent = (ViewGroup) parent;
            if (vgParent.getDescendantFocusability() == 262144 && vgParent.isFocusableInTouchMode()) {
                return vgParent;
            }
            if (vgParent.isRootNamespace()) {
                return null;
            }
            parent = vgParent.getParent();
        }
        return null;
    }

    private boolean leaveTouchMode() {
        if (this.mView == null) {
            return false;
        }
        if (this.mView.hasFocus()) {
            View focusedView = this.mView.findFocus();
            if (!(focusedView instanceof ViewGroup) || ((ViewGroup) focusedView).getDescendantFocusability() != 262144) {
                return false;
            }
        }
        return this.mView.restoreDefaultFocus();
    }

    abstract class InputStage {
        protected static final int FINISH_HANDLED = 1;
        protected static final int FINISH_NOT_HANDLED = 2;
        protected static final int FORWARD = 0;
        private final InputStage mNext;

        public InputStage(InputStage next) {
            this.mNext = next;
        }

        public final void deliver(QueuedInputEvent q) {
            if ((q.mFlags & 4) != 0) {
                forward(q);
            } else if (shouldDropInputEvent(q)) {
                finish(q, false);
            } else {
                apply(q, onProcess(q));
            }
        }

        /* access modifiers changed from: protected */
        public void finish(QueuedInputEvent q, boolean handled) {
            q.mFlags |= 4;
            if (handled) {
                q.mFlags |= 8;
            }
            forward(q);
        }

        /* access modifiers changed from: protected */
        public void forward(QueuedInputEvent q) {
            onDeliverToNext(q);
        }

        /* access modifiers changed from: protected */
        public void apply(QueuedInputEvent q, int result) {
            if (result == 0) {
                forward(q);
            } else if (result == 1) {
                finish(q, true);
            } else if (result == 2) {
                finish(q, false);
            } else {
                throw new IllegalArgumentException("Invalid result: " + result);
            }
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            return 0;
        }

        /* access modifiers changed from: protected */
        public void onDeliverToNext(QueuedInputEvent q) {
            if (this.mNext != null) {
                this.mNext.deliver(q);
            } else {
                ViewRootImpl.this.finishInputEvent(q);
            }
        }

        /* access modifiers changed from: protected */
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            if (this.mNext != null) {
                this.mNext.onWindowFocusChanged(hasWindowFocus);
            }
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            if (this.mNext != null) {
                this.mNext.onDetachedFromWindow();
            }
        }

        /* access modifiers changed from: protected */
        public boolean shouldDropInputEvent(QueuedInputEvent q) {
            if (ViewRootImpl.this.mView == null || !ViewRootImpl.this.mAdded) {
                String access$1700 = ViewRootImpl.this.mTag;
                Slog.w(access$1700, "Dropping event due to root view being removed: " + q.mEvent);
                return true;
            } else if ((ViewRootImpl.this.mAttachInfo.mHasWindowFocus || q.mEvent.isFromSource(2) || ViewRootImpl.this.isAutofillUiShowing()) && !ViewRootImpl.this.mStopped && ((!ViewRootImpl.this.mIsAmbientMode || q.mEvent.isFromSource(1)) && (!ViewRootImpl.this.mPausedForTransition || isBack(q.mEvent)))) {
                return false;
            } else {
                if (ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
                    q.mEvent.cancel();
                    String access$17002 = ViewRootImpl.this.mTag;
                    Slog.w(access$17002, "Cancelling event due to no window focus: " + q.mEvent);
                    return false;
                }
                String access$17003 = ViewRootImpl.this.mTag;
                Slog.w(access$17003, "Dropping event due to no window focus: " + q.mEvent);
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, PrintWriter writer) {
            if (this.mNext != null) {
                this.mNext.dump(prefix, writer);
            }
        }

        private boolean isBack(InputEvent event) {
            if (!(event instanceof KeyEvent) || ((KeyEvent) event).getKeyCode() != 4) {
                return false;
            }
            return true;
        }
    }

    abstract class AsyncInputStage extends InputStage {
        protected static final int DEFER = 3;
        private QueuedInputEvent mQueueHead;
        private int mQueueLength;
        private QueuedInputEvent mQueueTail;
        private final String mTraceCounter;

        public AsyncInputStage(InputStage next, String traceCounter) {
            super(next);
            this.mTraceCounter = traceCounter;
        }

        /* access modifiers changed from: protected */
        public void defer(QueuedInputEvent q) {
            q.mFlags |= 2;
            enqueue(q);
        }

        /* access modifiers changed from: protected */
        public void forward(QueuedInputEvent q) {
            QueuedInputEvent curr;
            q.mFlags &= -3;
            QueuedInputEvent curr2 = this.mQueueHead;
            if (curr2 == null) {
                super.forward(q);
                return;
            }
            int deviceId = q.mEvent.getDeviceId();
            QueuedInputEvent prev = null;
            boolean blocked = false;
            while (curr2 != null && curr2 != q) {
                if (!blocked && deviceId == curr2.mEvent.getDeviceId()) {
                    blocked = true;
                }
                prev = curr2;
                curr2 = curr2.mNext;
            }
            if (!blocked) {
                if (curr2 != null) {
                    curr2 = curr2.mNext;
                    dequeue(q, prev);
                }
                super.forward(q);
                while (curr != null) {
                    if (deviceId != curr.mEvent.getDeviceId()) {
                        prev = curr;
                        curr = curr.mNext;
                    } else if ((curr.mFlags & 2) == 0) {
                        QueuedInputEvent next = curr.mNext;
                        dequeue(curr, prev);
                        super.forward(curr);
                        curr = next;
                    } else {
                        return;
                    }
                }
            } else if (curr2 == null) {
                enqueue(q);
            }
        }

        /* access modifiers changed from: protected */
        public void apply(QueuedInputEvent q, int result) {
            if (result == 3) {
                defer(q);
            } else {
                super.apply(q, result);
            }
        }

        private void enqueue(QueuedInputEvent q) {
            if (this.mQueueTail == null) {
                this.mQueueHead = q;
                this.mQueueTail = q;
            } else {
                this.mQueueTail.mNext = q;
                this.mQueueTail = q;
            }
            this.mQueueLength++;
            Trace.traceCounter(4, this.mTraceCounter, this.mQueueLength);
        }

        private void dequeue(QueuedInputEvent q, QueuedInputEvent prev) {
            if (prev == null) {
                this.mQueueHead = q.mNext;
            } else {
                prev.mNext = q.mNext;
            }
            if (this.mQueueTail == q) {
                this.mQueueTail = prev;
            }
            q.mNext = null;
            this.mQueueLength--;
            Trace.traceCounter(4, this.mTraceCounter, this.mQueueLength);
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, PrintWriter writer) {
            writer.print(prefix);
            writer.print(getClass().getName());
            writer.print(": mQueueLength=");
            writer.println(this.mQueueLength);
            super.dump(prefix, writer);
        }
    }

    final class NativePreImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            if (ViewRootImpl.this.mInputQueue == null || !(q.mEvent instanceof KeyEvent)) {
                return 0;
            }
            ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, true, this);
            return 3;
        }

        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    final class ViewPreImeInputStage extends InputStage {
        public ViewPreImeInputStage(InputStage next) {
            super(next);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            if (ViewRootImpl.this.mView.dispatchKeyEventPreIme((KeyEvent) q.mEvent)) {
                return 1;
            }
            return 0;
        }
    }

    final class ImeInputStage extends AsyncInputStage implements InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            InputMethodManager imm;
            if (!ViewRootImpl.this.mLastWasImTarget || ViewRootImpl.this.isInLocalFocusMode() || (imm = (InputMethodManager) ViewRootImpl.this.mContext.getSystemService(InputMethodManager.class)) == null) {
                return 0;
            }
            int result = imm.dispatchInputEvent(q.mEvent, q, this, ViewRootImpl.this.mHandler);
            if (result == 1) {
                return 1;
            }
            if (result == 0) {
                return 0;
            }
            return 3;
        }

        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    final class EarlyPostImeInputStage extends InputStage {
        public EarlyPostImeInputStage(InputStage next) {
            super(next);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            if (q.mEvent instanceof MotionEvent) {
                return processMotionEvent(q);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            KeyEvent event = (KeyEvent) q.mEvent;
            if (ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.handleTooltipKey(event);
            }
            if (ViewRootImpl.this.checkForLeavingTouchModeAndConsume(event)) {
                return 1;
            }
            ViewRootImpl.this.mFallbackEventHandler.preDispatchKeyEvent(event);
            return 0;
        }

        private int processMotionEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            if (event.isFromSource(2)) {
                return processPointerEvent(q);
            }
            int action = event.getActionMasked();
            if ((action == 0 || action == 8) && event.isFromSource(8)) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
            return 0;
        }

        private int processPointerEvent(QueuedInputEvent q) {
            AutofillManager afm;
            MotionEvent event = (MotionEvent) q.mEvent;
            if (ViewRootImpl.this.mTranslator != null) {
                ViewRootImpl.this.mTranslator.translateEventInScreenToAppWindow(event);
            }
            int action = event.getAction();
            if (action == 0 || action == 8) {
                ViewRootImpl.this.ensureTouchMode(event.isFromSource(4098));
            }
            if (action == 0 && (afm = ViewRootImpl.this.getAutofillManager()) != null) {
                afm.requestHideFillUi();
            }
            if (action == 0 && ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.hideTooltip();
            }
            if (ViewRootImpl.this.mCurScrollY != 0) {
                event.offsetLocation(0.0f, (float) ViewRootImpl.this.mCurScrollY);
            }
            if (!event.isTouchEvent()) {
                return 0;
            }
            ViewRootImpl.this.mLastTouchPoint.x = event.getRawX();
            ViewRootImpl.this.mLastTouchPoint.y = event.getRawY();
            ViewRootImpl.this.mLastTouchSource = event.getSource();
            return 0;
        }
    }

    final class NativePostImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePostImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            if (ViewRootImpl.this.mInputQueue == null) {
                return 0;
            }
            ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, false, this);
            return 3;
        }

        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    final class ViewPostImeInputStage extends InputStage {
        public ViewPostImeInputStage(InputStage next) {
            super(next);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            int source = q.mEvent.getSource();
            if ((source & 2) != 0) {
                return processPointerEvent(q);
            }
            if ((source & 4) != 0) {
                return processTrackballEvent(q);
            }
            return processGenericMotionEvent(q);
        }

        /* access modifiers changed from: protected */
        public void onDeliverToNext(QueuedInputEvent q) {
            if (ViewRootImpl.this.mUnbufferedInputDispatch && (q.mEvent instanceof MotionEvent) && ((MotionEvent) q.mEvent).isTouchEvent() && ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
                ViewRootImpl.this.mUnbufferedInputDispatch = false;
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
            super.onDeliverToNext(q);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
            if (r7.hasNoModifiers() == false) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
            r0 = 130;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0030, code lost:
            if (r7.hasNoModifiers() == false) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0032, code lost:
            r0 = 33;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean performFocusNavigation(android.view.KeyEvent r7) {
            /*
                r6 = this;
                r0 = 0
                int r1 = r7.getKeyCode()
                r2 = 61
                r3 = 1
                if (r1 == r2) goto L_0x0035
                switch(r1) {
                    case 19: goto L_0x002c;
                    case 20: goto L_0x0023;
                    case 21: goto L_0x001a;
                    case 22: goto L_0x0011;
                    default: goto L_0x000d;
                }
            L_0x000d:
                switch(r1) {
                    case 691: goto L_0x002c;
                    case 692: goto L_0x0023;
                    default: goto L_0x0010;
                }
            L_0x0010:
                goto L_0x0044
            L_0x0011:
                boolean r1 = r7.hasNoModifiers()
                if (r1 == 0) goto L_0x0044
                r0 = 66
                goto L_0x0044
            L_0x001a:
                boolean r1 = r7.hasNoModifiers()
                if (r1 == 0) goto L_0x0044
                r0 = 17
                goto L_0x0044
            L_0x0023:
                boolean r1 = r7.hasNoModifiers()
                if (r1 == 0) goto L_0x0044
                r0 = 130(0x82, float:1.82E-43)
                goto L_0x0044
            L_0x002c:
                boolean r1 = r7.hasNoModifiers()
                if (r1 == 0) goto L_0x0044
                r0 = 33
                goto L_0x0044
            L_0x0035:
                boolean r1 = r7.hasNoModifiers()
                if (r1 == 0) goto L_0x003d
                r0 = 2
                goto L_0x0044
            L_0x003d:
                boolean r1 = r7.hasModifiers(r3)
                if (r1 == 0) goto L_0x0044
                r0 = 1
            L_0x0044:
                if (r0 == 0) goto L_0x00ac
                android.view.ViewRootImpl r1 = android.view.ViewRootImpl.this
                android.view.View r1 = r1.mView
                android.view.View r1 = r1.findFocus()
                if (r1 == 0) goto L_0x00a1
                android.view.View r2 = r1.focusSearch(r0)
                if (r2 == 0) goto L_0x0095
                if (r2 == r1) goto L_0x0095
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                android.graphics.Rect r4 = r4.mTempRect
                r1.getFocusedRect(r4)
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                android.view.View r4 = r4.mView
                boolean r4 = r4 instanceof android.view.ViewGroup
                if (r4 == 0) goto L_0x0081
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                android.view.View r4 = r4.mView
                android.view.ViewGroup r4 = (android.view.ViewGroup) r4
                android.view.ViewRootImpl r5 = android.view.ViewRootImpl.this
                android.graphics.Rect r5 = r5.mTempRect
                r4.offsetDescendantRectToMyCoords(r1, r5)
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                android.view.View r4 = r4.mView
                android.view.ViewGroup r4 = (android.view.ViewGroup) r4
                android.view.ViewRootImpl r5 = android.view.ViewRootImpl.this
                android.graphics.Rect r5 = r5.mTempRect
                r4.offsetRectIntoDescendantCoords(r2, r5)
            L_0x0081:
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                android.graphics.Rect r4 = r4.mTempRect
                boolean r4 = r2.requestFocus(r0, r4)
                if (r4 == 0) goto L_0x0095
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                int r5 = android.view.SoundEffectConstants.getContantForFocusDirection(r0)
                r4.playSoundEffect(r5)
                return r3
            L_0x0095:
                android.view.ViewRootImpl r4 = android.view.ViewRootImpl.this
                android.view.View r4 = r4.mView
                boolean r4 = r4.dispatchUnhandledMove(r1, r0)
                if (r4 == 0) goto L_0x00a0
                return r3
            L_0x00a0:
                goto L_0x00ac
            L_0x00a1:
                android.view.ViewRootImpl r2 = android.view.ViewRootImpl.this
                android.view.View r2 = r2.mView
                boolean r2 = r2.restoreDefaultFocus()
                if (r2 == 0) goto L_0x00ac
                return r3
            L_0x00ac:
                r1 = 0
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.ViewPostImeInputStage.performFocusNavigation(android.view.KeyEvent):boolean");
        }

        private boolean performKeyboardGroupNavigation(int direction) {
            View cluster;
            View focused = ViewRootImpl.this.mView.findFocus();
            if (focused == null && ViewRootImpl.this.mView.restoreDefaultFocus()) {
                return true;
            }
            if (focused == null) {
                cluster = ViewRootImpl.this.keyboardNavigationClusterSearch((View) null, direction);
            } else {
                cluster = focused.keyboardNavigationClusterSearch((View) null, direction);
            }
            int realDirection = direction;
            if (direction == 2 || direction == 1) {
                realDirection = 130;
            }
            if (cluster != null && cluster.isRootNamespace()) {
                if (cluster.restoreFocusNotInCluster()) {
                    ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    return true;
                }
                cluster = ViewRootImpl.this.keyboardNavigationClusterSearch((View) null, direction);
            }
            if (cluster == null || !cluster.restoreFocusInCluster(realDirection)) {
                return false;
            }
            ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            return true;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            KeyEvent event = (KeyEvent) q.mEvent;
            if (ViewRootImpl.this.mUnhandledKeyManager.preViewDispatch(event) || ViewRootImpl.this.mView.dispatchKeyEvent(event)) {
                return 1;
            }
            if (shouldDropInputEvent(q)) {
                return 2;
            }
            if (ViewRootImpl.this.mUnhandledKeyManager.dispatch(ViewRootImpl.this.mView, event)) {
                return 1;
            }
            int groupNavigationDirection = 0;
            if (event.getAction() == 0 && event.getKeyCode() == 61) {
                if (KeyEvent.metaStateHasModifiers(event.getMetaState(), 65536)) {
                    groupNavigationDirection = 2;
                } else if (KeyEvent.metaStateHasModifiers(event.getMetaState(), 65537)) {
                    groupNavigationDirection = 1;
                }
            }
            if (event.getAction() == 0 && !KeyEvent.metaStateHasNoModifiers(event.getMetaState()) && event.getRepeatCount() == 0 && !KeyEvent.isModifierKey(event.getKeyCode()) && groupNavigationDirection == 0) {
                if (ViewRootImpl.this.mView.dispatchKeyShortcutEvent(event)) {
                    return 1;
                }
                if (shouldDropInputEvent(q)) {
                    return 2;
                }
            }
            if (ViewRootImpl.this.mFallbackEventHandler.dispatchKeyEvent(event)) {
                return 1;
            }
            if (shouldDropInputEvent(q)) {
                return 2;
            }
            if (event.getAction() != 0) {
                return 0;
            }
            if (groupNavigationDirection != 0) {
                if (performKeyboardGroupNavigation(groupNavigationDirection)) {
                    return 1;
                }
                return 0;
            } else if (performFocusNavigation(event)) {
                return 1;
            } else {
                return 0;
            }
        }

        private int processPointerEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested = false;
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = true;
            boolean handled = ViewRootImpl.this.mView.dispatchPointerEvent(event);
            int action = event.getActionMasked();
            if (action == 2) {
                ViewRootImpl.this.mHaveMoveEvent = true;
            } else if (action == 1) {
                ViewRootImpl.this.mHaveMoveEvent = false;
            }
            maybeUpdatePointerIcon(event);
            ViewRootImpl.this.maybeUpdateTooltip(event);
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = false;
            if (ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested && !ViewRootImpl.this.mUnbufferedInputDispatch) {
                ViewRootImpl.this.mUnbufferedInputDispatch = true;
                if (ViewRootImpl.this.mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.scheduleConsumeBatchedInputImmediately();
                }
            }
            return handled;
        }

        private void maybeUpdatePointerIcon(MotionEvent event) {
            if (event.getPointerCount() == 1 && event.isFromSource(8194)) {
                if (event.getActionMasked() == 9 || event.getActionMasked() == 10) {
                    int unused = ViewRootImpl.this.mPointerIconType = 1;
                }
                if (event.getActionMasked() != 10 && !ViewRootImpl.this.updatePointerIcon(event) && event.getActionMasked() == 7) {
                    int unused2 = ViewRootImpl.this.mPointerIconType = 1;
                }
            }
        }

        private int processTrackballEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            if ((!event.isFromSource(InputDevice.SOURCE_MOUSE_RELATIVE) || (ViewRootImpl.this.hasPointerCapture() && !ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event))) && !ViewRootImpl.this.mView.dispatchTrackballEvent(event)) {
                return 0;
            }
            return 1;
        }

        private int processGenericMotionEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            if ((!event.isFromSource(1048584) || !ViewRootImpl.this.hasPointerCapture() || !ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event)) && !ViewRootImpl.this.mView.dispatchGenericMotionEvent(event)) {
                return 0;
            }
            return 1;
        }
    }

    /* access modifiers changed from: private */
    public void resetPointerIcon(MotionEvent event) {
        this.mPointerIconType = 1;
        updatePointerIcon(event);
    }

    /* access modifiers changed from: private */
    public boolean updatePointerIcon(MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        if (this.mView == null) {
            Slog.d(this.mTag, "updatePointerIcon called after view was removed");
            return false;
        } else if (x < 0.0f || x >= ((float) this.mView.getWidth()) || y < 0.0f || y >= ((float) this.mView.getHeight())) {
            Slog.d(this.mTag, "updatePointerIcon called with position out of bounds");
            return false;
        } else {
            PointerIcon pointerIcon = this.mView.onResolvePointerIcon(event, 0);
            int pointerType = pointerIcon != null ? pointerIcon.getType() : 1000;
            if (this.mPointerIconType != pointerType) {
                this.mPointerIconType = pointerType;
                this.mCustomPointerIcon = null;
                if (this.mPointerIconType != -1) {
                    InputManager.getInstance().setPointerIconType(pointerType);
                    return true;
                }
            }
            if (this.mPointerIconType == -1 && !pointerIcon.equals(this.mCustomPointerIcon)) {
                this.mCustomPointerIcon = pointerIcon;
                InputManager.getInstance().setCustomPointerIcon(this.mCustomPointerIcon);
            }
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void maybeUpdateTooltip(MotionEvent event) {
        if (event.getPointerCount() == 1) {
            int action = event.getActionMasked();
            if (action == 9 || action == 7 || action == 10) {
                AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
                if (manager.isEnabled() && manager.isTouchExplorationEnabled()) {
                    return;
                }
                if (this.mView == null) {
                    Slog.d(this.mTag, "maybeUpdateTooltip called after view was removed");
                } else {
                    this.mView.dispatchTooltipHoverEvent(event);
                }
            }
        }
    }

    final class SyntheticInputStage extends InputStage {
        private final SyntheticJoystickHandler mJoystick = new SyntheticJoystickHandler();
        private final SyntheticKeyboardHandler mKeyboard = new SyntheticKeyboardHandler();
        private final SyntheticTouchNavigationHandler mTouchNavigation = new SyntheticTouchNavigationHandler();
        private final SyntheticTrackballHandler mTrackball = new SyntheticTrackballHandler();

        public SyntheticInputStage() {
            super((InputStage) null);
        }

        /* access modifiers changed from: protected */
        public int onProcess(QueuedInputEvent q) {
            q.mFlags |= 16;
            if (q.mEvent instanceof MotionEvent) {
                MotionEvent event = (MotionEvent) q.mEvent;
                int source = event.getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.process(event);
                    return 1;
                } else if ((source & 16) != 0) {
                    this.mJoystick.process(event);
                    return 1;
                } else if ((source & 2097152) != 2097152) {
                    return 0;
                } else {
                    this.mTouchNavigation.process(event);
                    return 1;
                }
            } else if ((q.mFlags & 32) == 0) {
                return 0;
            } else {
                this.mKeyboard.process((KeyEvent) q.mEvent);
                return 1;
            }
        }

        /* access modifiers changed from: protected */
        public void onDeliverToNext(QueuedInputEvent q) {
            if ((q.mFlags & 16) == 0 && (q.mEvent instanceof MotionEvent)) {
                MotionEvent event = (MotionEvent) q.mEvent;
                int source = event.getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.cancel();
                } else if ((source & 16) != 0) {
                    this.mJoystick.cancel();
                } else if ((source & 2097152) == 2097152) {
                    this.mTouchNavigation.cancel(event);
                }
            }
            super.onDeliverToNext(q);
        }

        /* access modifiers changed from: protected */
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            if (!hasWindowFocus) {
                this.mJoystick.cancel();
            }
        }

        /* access modifiers changed from: protected */
        public void onDetachedFromWindow() {
            this.mJoystick.cancel();
        }
    }

    final class SyntheticTrackballHandler {
        private long mLastTime;
        private final TrackballAxis mX = new TrackballAxis();
        private final TrackballAxis mY = new TrackballAxis();

        SyntheticTrackballHandler() {
        }

        public void process(MotionEvent event) {
            long curTime;
            int i;
            long curTime2;
            int i2;
            long curTime3 = SystemClock.uptimeMillis();
            if (this.mLastTime + 250 < curTime3) {
                this.mX.reset(0);
                this.mY.reset(0);
                this.mLastTime = curTime3;
            }
            int action = event.getAction();
            int metaState = event.getMetaState();
            switch (action) {
                case 0:
                    curTime = curTime3;
                    this.mX.reset(2);
                    this.mY.reset(2);
                    ViewRootImpl viewRootImpl = ViewRootImpl.this;
                    KeyEvent keyEvent = r1;
                    KeyEvent keyEvent2 = new KeyEvent(curTime, curTime, 0, 23, 0, metaState, -1, 0, 1024, 257);
                    viewRootImpl.enqueueInputEvent(keyEvent);
                    break;
                case 1:
                    this.mX.reset(2);
                    this.mY.reset(2);
                    KeyEvent keyEvent3 = r1;
                    curTime = curTime3;
                    KeyEvent keyEvent4 = new KeyEvent(curTime3, curTime3, 1, 23, 0, metaState, -1, 0, 1024, 257);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent3);
                    break;
                default:
                    curTime = curTime3;
                    break;
            }
            float xOff = this.mX.collect(event.getX(), event.getEventTime(), "X");
            float yOff = this.mY.collect(event.getY(), event.getEventTime(), "Y");
            int keycode = 0;
            int movement = 0;
            float accel = 1.0f;
            if (xOff > yOff) {
                movement = this.mX.generate();
                if (movement != 0) {
                    if (movement > 0) {
                        i2 = 22;
                    } else {
                        i2 = 21;
                    }
                    keycode = i2;
                    accel = this.mX.acceleration;
                    this.mY.reset(2);
                }
            } else if (yOff > 0.0f && (movement = this.mY.generate()) != 0) {
                if (movement > 0) {
                    i = 20;
                } else {
                    i = 19;
                }
                keycode = i;
                accel = this.mY.acceleration;
                this.mX.reset(2);
            }
            int keycode2 = keycode;
            float accel2 = accel;
            if (keycode2 != 0) {
                if (movement < 0) {
                    movement = -movement;
                }
                int accelMovement = (int) (((float) movement) * accel2);
                if (accelMovement > movement) {
                    int movement2 = movement - 1;
                    KeyEvent keyEvent5 = r1;
                    int i3 = accelMovement;
                    KeyEvent keyEvent6 = new KeyEvent(curTime, curTime, 2, keycode2, accelMovement - movement2, metaState, -1, 0, 1024, 257);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent5);
                    movement = movement2;
                    curTime2 = curTime;
                } else {
                    curTime2 = curTime;
                }
                while (movement > 0) {
                    int movement3 = movement - 1;
                    long curTime4 = SystemClock.uptimeMillis();
                    long j = curTime4;
                    long j2 = curTime4;
                    int i4 = keycode2;
                    int i5 = metaState;
                    KeyEvent keyEvent7 = r1;
                    float xOff2 = xOff;
                    KeyEvent keyEvent8 = new KeyEvent(j, j2, 0, i4, 0, i5, -1, 0, 1024, 257);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent7);
                    ViewRootImpl viewRootImpl2 = ViewRootImpl.this;
                    float yOff2 = yOff;
                    KeyEvent keyEvent9 = r1;
                    KeyEvent keyEvent10 = new KeyEvent(j, j2, 1, i4, 0, i5, -1, 0, 1024, 257);
                    viewRootImpl2.enqueueInputEvent(keyEvent9);
                    movement = movement3;
                    curTime2 = curTime4;
                    xOff = xOff2;
                    yOff = yOff2;
                }
                float f = yOff;
                this.mLastTime = curTime2;
                return;
            }
            float f2 = yOff;
            long j3 = curTime;
        }

        public void cancel() {
            this.mLastTime = -2147483648L;
            if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mAdded) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
        }
    }

    static final class TrackballAxis {
        static final float ACCEL_MOVE_SCALING_FACTOR = 0.025f;
        static final long FAST_MOVE_TIME = 150;
        static final float FIRST_MOVEMENT_THRESHOLD = 0.5f;
        static final float MAX_ACCELERATION = 20.0f;
        static final float SECOND_CUMULATIVE_MOVEMENT_THRESHOLD = 2.0f;
        static final float SUBSEQUENT_INCREMENTAL_MOVEMENT_THRESHOLD = 1.0f;
        float acceleration = 1.0f;
        int dir;
        long lastMoveTime = 0;
        int nonAccelMovement;
        float position;
        int step;

        TrackballAxis() {
        }

        /* access modifiers changed from: package-private */
        public void reset(int _step) {
            this.position = 0.0f;
            this.acceleration = 1.0f;
            this.lastMoveTime = 0;
            this.step = _step;
            this.dir = 0;
        }

        /* access modifiers changed from: package-private */
        public float collect(float off, long time, String axis) {
            long normTime;
            float f = 1.0f;
            if (off > 0.0f) {
                normTime = (long) (150.0f * off);
                if (this.dir < 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0;
                }
                this.dir = 1;
            } else if (off < 0.0f) {
                normTime = (long) ((-off) * 150.0f);
                if (this.dir > 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0;
                }
                this.dir = -1;
            } else {
                normTime = 0;
            }
            long normTime2 = normTime;
            if (normTime2 > 0) {
                long delta = time - this.lastMoveTime;
                this.lastMoveTime = time;
                float acc = this.acceleration;
                if (delta < normTime2) {
                    float scale = ((float) (normTime2 - delta)) * ACCEL_MOVE_SCALING_FACTOR;
                    if (scale > 1.0f) {
                        acc *= scale;
                    }
                    float f2 = MAX_ACCELERATION;
                    if (acc < MAX_ACCELERATION) {
                        f2 = acc;
                    }
                    this.acceleration = f2;
                } else {
                    float scale2 = ((float) (delta - normTime2)) * ACCEL_MOVE_SCALING_FACTOR;
                    if (scale2 > 1.0f) {
                        acc /= scale2;
                    }
                    if (acc > 1.0f) {
                        f = acc;
                    }
                    this.acceleration = f;
                }
            }
            this.position += off;
            return Math.abs(this.position);
        }

        /* access modifiers changed from: package-private */
        public int generate() {
            int movement = 0;
            this.nonAccelMovement = 0;
            while (true) {
                int dir2 = this.position >= 0.0f ? 1 : -1;
                switch (this.step) {
                    case 0:
                        if (Math.abs(this.position) >= FIRST_MOVEMENT_THRESHOLD) {
                            movement += dir2;
                            this.nonAccelMovement += dir2;
                            this.step = 1;
                            break;
                        } else {
                            return movement;
                        }
                    case 1:
                        if (Math.abs(this.position) >= SECOND_CUMULATIVE_MOVEMENT_THRESHOLD) {
                            movement += dir2;
                            this.nonAccelMovement += dir2;
                            this.position -= ((float) dir2) * SECOND_CUMULATIVE_MOVEMENT_THRESHOLD;
                            this.step = 2;
                            break;
                        } else {
                            return movement;
                        }
                    default:
                        if (Math.abs(this.position) >= 1.0f) {
                            movement += dir2;
                            this.position -= ((float) dir2) * 1.0f;
                            float acc = this.acceleration * 1.1f;
                            this.acceleration = acc < MAX_ACCELERATION ? acc : this.acceleration;
                            break;
                        } else {
                            return movement;
                        }
                }
            }
        }
    }

    final class SyntheticJoystickHandler extends Handler {
        private static final int MSG_ENQUEUE_X_AXIS_KEY_REPEAT = 1;
        private static final int MSG_ENQUEUE_Y_AXIS_KEY_REPEAT = 2;
        /* access modifiers changed from: private */
        public final SparseArray<KeyEvent> mDeviceKeyEvents = new SparseArray<>();
        private final JoystickAxesState mJoystickAxesState = new JoystickAxesState();

        public SyntheticJoystickHandler() {
            super(true);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                case 2:
                    if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus) {
                        KeyEvent oldEvent = (KeyEvent) msg.obj;
                        KeyEvent e = KeyEvent.changeTimeRepeat(oldEvent, SystemClock.uptimeMillis(), oldEvent.getRepeatCount() + 1);
                        ViewRootImpl.this.enqueueInputEvent(e);
                        Message m = obtainMessage(msg.what, e);
                        m.setAsynchronous(true);
                        sendMessageDelayed(m, (long) ViewConfiguration.getKeyRepeatDelay());
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public void process(MotionEvent event) {
            switch (event.getActionMasked()) {
                case 2:
                    update(event);
                    return;
                case 3:
                    cancel();
                    return;
                default:
                    String access$1700 = ViewRootImpl.this.mTag;
                    Log.w(access$1700, "Unexpected action: " + event.getActionMasked());
                    return;
            }
        }

        /* access modifiers changed from: private */
        public void cancel() {
            removeMessages(1);
            removeMessages(2);
            for (int i = 0; i < this.mDeviceKeyEvents.size(); i++) {
                KeyEvent keyEvent = this.mDeviceKeyEvents.valueAt(i);
                if (keyEvent != null) {
                    ViewRootImpl.this.enqueueInputEvent(KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
                }
            }
            this.mDeviceKeyEvents.clear();
            this.mJoystickAxesState.resetState();
        }

        private void update(MotionEvent event) {
            int historySize = event.getHistorySize();
            for (int h = 0; h < historySize; h++) {
                MotionEvent motionEvent = event;
                long historicalEventTime = event.getHistoricalEventTime(h);
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 0, event.getHistoricalAxisValue(0, 0, h));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 1, event.getHistoricalAxisValue(1, 0, h));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 15, event.getHistoricalAxisValue(15, 0, h));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, historicalEventTime, 16, event.getHistoricalAxisValue(16, 0, h));
            }
            long time = event.getEventTime();
            this.mJoystickAxesState.updateStateForAxis(event, time, 0, event.getAxisValue(0));
            this.mJoystickAxesState.updateStateForAxis(event, time, 1, event.getAxisValue(1));
            this.mJoystickAxesState.updateStateForAxis(event, time, 15, event.getAxisValue(15));
            this.mJoystickAxesState.updateStateForAxis(event, time, 16, event.getAxisValue(16));
        }

        final class JoystickAxesState {
            private static final int STATE_DOWN_OR_RIGHT = 1;
            private static final int STATE_NEUTRAL = 0;
            private static final int STATE_UP_OR_LEFT = -1;
            final int[] mAxisStatesHat = {0, 0};
            final int[] mAxisStatesStick = {0, 0};

            JoystickAxesState() {
            }

            /* access modifiers changed from: package-private */
            public void resetState() {
                this.mAxisStatesHat[0] = 0;
                this.mAxisStatesHat[1] = 0;
                this.mAxisStatesStick[0] = 0;
                this.mAxisStatesStick[1] = 0;
            }

            /* access modifiers changed from: package-private */
            public void updateStateForAxis(MotionEvent event, long time, int axis, float value) {
                int repeatMessage;
                int axisStateIndex;
                int currentState;
                int i = axis;
                if (isXAxis(i)) {
                    axisStateIndex = 0;
                    repeatMessage = 1;
                } else if (isYAxis(i) != 0) {
                    axisStateIndex = 1;
                    repeatMessage = 2;
                } else {
                    float f = value;
                    String access$1700 = ViewRootImpl.this.mTag;
                    Log.e(access$1700, "Unexpected axis " + i + " in updateStateForAxis!");
                    return;
                }
                int newState = joystickAxisValueToState(value);
                if (i == 0 || i == 1) {
                    currentState = this.mAxisStatesStick[axisStateIndex];
                } else {
                    currentState = this.mAxisStatesHat[axisStateIndex];
                }
                if (currentState != newState) {
                    int metaState = event.getMetaState();
                    int deviceId = event.getDeviceId();
                    int source = event.getSource();
                    if (currentState == 1 || currentState == -1) {
                        int keyCode = joystickAxisAndStateToKeycode(i, currentState);
                        if (keyCode != 0) {
                            KeyEvent keyEvent = r8;
                            int deviceId2 = deviceId;
                            KeyEvent keyEvent2 = new KeyEvent(time, time, 1, keyCode, 0, metaState, deviceId2, 0, 1024, source);
                            ViewRootImpl.this.enqueueInputEvent(keyEvent);
                            deviceId = deviceId2;
                            SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId, null);
                        }
                        SyntheticJoystickHandler.this.removeMessages(repeatMessage);
                    }
                    if (newState == 1 || newState == -1) {
                        int keyCode2 = joystickAxisAndStateToKeycode(i, newState);
                        if (keyCode2 != 0) {
                            int deviceId3 = deviceId;
                            int i2 = source;
                            KeyEvent keyEvent3 = new KeyEvent(time, time, 0, keyCode2, 0, metaState, deviceId3, 0, 1024, i2);
                            ViewRootImpl.this.enqueueInputEvent(keyEvent3);
                            Message m = SyntheticJoystickHandler.this.obtainMessage(repeatMessage, keyEvent3);
                            m.setAsynchronous(true);
                            SyntheticJoystickHandler.this.sendMessageDelayed(m, (long) ViewConfiguration.getKeyRepeatTimeout());
                            KeyEvent keyEvent4 = r8;
                            Message message = m;
                            KeyEvent keyEvent5 = keyEvent3;
                            KeyEvent keyEvent6 = new KeyEvent(time, time, 1, keyCode2, 0, metaState, deviceId3, 0, 1056, i2);
                            SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId3, keyEvent4);
                        }
                    } else {
                        int i3 = deviceId;
                    }
                    if (i == 0 || i == 1) {
                        this.mAxisStatesStick[axisStateIndex] = newState;
                    } else {
                        this.mAxisStatesHat[axisStateIndex] = newState;
                    }
                }
            }

            private boolean isXAxis(int axis) {
                return axis == 0 || axis == 15;
            }

            private boolean isYAxis(int axis) {
                return axis == 1 || axis == 16;
            }

            private int joystickAxisAndStateToKeycode(int axis, int state) {
                if (isXAxis(axis) && state == -1) {
                    return 21;
                }
                if (isXAxis(axis) && state == 1) {
                    return 22;
                }
                if (isYAxis(axis) && state == -1) {
                    return 19;
                }
                if (isYAxis(axis) && state == 1) {
                    return 20;
                }
                String access$1700 = ViewRootImpl.this.mTag;
                Log.e(access$1700, "Unknown axis " + axis + " or direction " + state);
                return 0;
            }

            private int joystickAxisValueToState(float value) {
                if (value >= 0.5f) {
                    return 1;
                }
                if (value <= -0.5f) {
                    return -1;
                }
                return 0;
            }
        }
    }

    final class SyntheticTouchNavigationHandler extends Handler {
        private static final float DEFAULT_HEIGHT_MILLIMETERS = 48.0f;
        private static final float DEFAULT_WIDTH_MILLIMETERS = 48.0f;
        private static final float FLING_TICK_DECAY = 0.8f;
        private static final boolean LOCAL_DEBUG = false;
        private static final String LOCAL_TAG = "SyntheticTouchNavigationHandler";
        private static final float MAX_FLING_VELOCITY_TICKS_PER_SECOND = 20.0f;
        private static final float MIN_FLING_VELOCITY_TICKS_PER_SECOND = 6.0f;
        private static final int TICK_DISTANCE_MILLIMETERS = 12;
        private float mAccumulatedX;
        private float mAccumulatedY;
        private int mActivePointerId = -1;
        private float mConfigMaxFlingVelocity;
        private float mConfigMinFlingVelocity;
        private float mConfigTickDistance;
        private boolean mConsumedMovement;
        private int mCurrentDeviceId = -1;
        private boolean mCurrentDeviceSupported;
        private int mCurrentSource;
        private final Runnable mFlingRunnable = new Runnable() {
            public void run() {
                long time = SystemClock.uptimeMillis();
                SyntheticTouchNavigationHandler.this.sendKeyDownOrRepeat(time, SyntheticTouchNavigationHandler.this.mPendingKeyCode, SyntheticTouchNavigationHandler.this.mPendingKeyMetaState);
                SyntheticTouchNavigationHandler.access$3132(SyntheticTouchNavigationHandler.this, SyntheticTouchNavigationHandler.FLING_TICK_DECAY);
                if (!SyntheticTouchNavigationHandler.this.postFling(time)) {
                    boolean unused = SyntheticTouchNavigationHandler.this.mFlinging = false;
                    SyntheticTouchNavigationHandler.this.finishKeys(time);
                }
            }
        };
        private float mFlingVelocity;
        /* access modifiers changed from: private */
        public boolean mFlinging;
        private float mLastX;
        private float mLastY;
        /* access modifiers changed from: private */
        public int mPendingKeyCode = 0;
        private long mPendingKeyDownTime;
        /* access modifiers changed from: private */
        public int mPendingKeyMetaState;
        private int mPendingKeyRepeatCount;
        private float mStartX;
        private float mStartY;
        private VelocityTracker mVelocityTracker;

        static /* synthetic */ float access$3132(SyntheticTouchNavigationHandler x0, float x1) {
            float f = x0.mFlingVelocity * x1;
            x0.mFlingVelocity = f;
            return f;
        }

        public SyntheticTouchNavigationHandler() {
            super(true);
        }

        public void process(MotionEvent event) {
            MotionEvent motionEvent = event;
            long time = event.getEventTime();
            int deviceId = event.getDeviceId();
            int source = event.getSource();
            if (!(this.mCurrentDeviceId == deviceId && this.mCurrentSource == source)) {
                finishKeys(time);
                finishTracking(time);
                this.mCurrentDeviceId = deviceId;
                this.mCurrentSource = source;
                this.mCurrentDeviceSupported = false;
                InputDevice device = event.getDevice();
                if (device != null) {
                    InputDevice.MotionRange xRange = device.getMotionRange(0);
                    InputDevice.MotionRange yRange = device.getMotionRange(1);
                    if (!(xRange == null || yRange == null)) {
                        this.mCurrentDeviceSupported = true;
                        float xRes = xRange.getResolution();
                        if (xRes <= 0.0f) {
                            xRes = xRange.getRange() / 48.0f;
                        }
                        float yRes = yRange.getResolution();
                        if (yRes <= 0.0f) {
                            yRes = yRange.getRange() / 48.0f;
                        }
                        this.mConfigTickDistance = 12.0f * (xRes + yRes) * 0.5f;
                        this.mConfigMinFlingVelocity = this.mConfigTickDistance * MIN_FLING_VELOCITY_TICKS_PER_SECOND;
                        this.mConfigMaxFlingVelocity = this.mConfigTickDistance * MAX_FLING_VELOCITY_TICKS_PER_SECOND;
                    }
                }
            }
            if (this.mCurrentDeviceSupported) {
                int action = event.getActionMasked();
                switch (action) {
                    case 0:
                        boolean caughtFling = this.mFlinging;
                        finishKeys(time);
                        finishTracking(time);
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        this.mVelocityTracker = VelocityTracker.obtain();
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mStartX = event.getX();
                        this.mStartY = event.getY();
                        this.mLastX = this.mStartX;
                        this.mLastY = this.mStartY;
                        this.mAccumulatedX = 0.0f;
                        this.mAccumulatedY = 0.0f;
                        this.mConsumedMovement = caughtFling;
                        return;
                    case 1:
                    case 2:
                        if (this.mActivePointerId >= 0) {
                            int index = motionEvent.findPointerIndex(this.mActivePointerId);
                            if (index < 0) {
                                finishKeys(time);
                                finishTracking(time);
                                return;
                            }
                            this.mVelocityTracker.addMovement(motionEvent);
                            float x = motionEvent.getX(index);
                            float y = motionEvent.getY(index);
                            this.mAccumulatedX += x - this.mLastX;
                            this.mAccumulatedY += y - this.mLastY;
                            this.mLastX = x;
                            this.mLastY = y;
                            consumeAccumulatedMovement(time, event.getMetaState());
                            if (action == 1) {
                                if (this.mConsumedMovement && this.mPendingKeyCode != 0) {
                                    this.mVelocityTracker.computeCurrentVelocity(1000, this.mConfigMaxFlingVelocity);
                                    if (!startFling(time, this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mVelocityTracker.getYVelocity(this.mActivePointerId))) {
                                        finishKeys(time);
                                    }
                                }
                                finishTracking(time);
                                return;
                            }
                            return;
                        }
                        return;
                    case 3:
                        finishKeys(time);
                        finishTracking(time);
                        return;
                    default:
                        return;
                }
            }
        }

        public void cancel(MotionEvent event) {
            if (this.mCurrentDeviceId == event.getDeviceId() && this.mCurrentSource == event.getSource()) {
                long time = event.getEventTime();
                finishKeys(time);
                finishTracking(time);
            }
        }

        /* access modifiers changed from: private */
        public void finishKeys(long time) {
            cancelFling();
            sendKeyUp(time);
        }

        private void finishTracking(long time) {
            if (this.mActivePointerId >= 0) {
                this.mActivePointerId = -1;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }

        private void consumeAccumulatedMovement(long time, int metaState) {
            float absX = Math.abs(this.mAccumulatedX);
            float absY = Math.abs(this.mAccumulatedY);
            if (absX >= absY) {
                if (absX >= this.mConfigTickDistance) {
                    this.mAccumulatedX = consumeAccumulatedMovement(time, metaState, this.mAccumulatedX, 21, 22);
                    this.mAccumulatedY = 0.0f;
                    this.mConsumedMovement = true;
                }
            } else if (absY >= this.mConfigTickDistance) {
                this.mAccumulatedY = consumeAccumulatedMovement(time, metaState, this.mAccumulatedY, 691, 692);
                this.mAccumulatedX = 0.0f;
                this.mConsumedMovement = true;
            }
        }

        private float consumeAccumulatedMovement(long time, int metaState, float accumulator, int negativeKeyCode, int positiveKeyCode) {
            while (accumulator <= (-this.mConfigTickDistance)) {
                sendKeyDownOrRepeat(time, negativeKeyCode, metaState);
                accumulator += this.mConfigTickDistance;
            }
            while (accumulator >= this.mConfigTickDistance) {
                sendKeyDownOrRepeat(time, positiveKeyCode, metaState);
                accumulator -= this.mConfigTickDistance;
            }
            return accumulator;
        }

        /* access modifiers changed from: private */
        public void sendKeyDownOrRepeat(long time, int keyCode, int metaState) {
            int i = keyCode;
            if (this.mPendingKeyCode != i) {
                sendKeyUp(time);
                this.mPendingKeyDownTime = time;
                this.mPendingKeyCode = i;
                this.mPendingKeyRepeatCount = 0;
            } else {
                long j = time;
                this.mPendingKeyRepeatCount++;
            }
            this.mPendingKeyMetaState = metaState;
            ViewRootImpl viewRootImpl = ViewRootImpl.this;
            KeyEvent keyEvent = r3;
            KeyEvent keyEvent2 = new KeyEvent(this.mPendingKeyDownTime, time, 0, this.mPendingKeyCode, this.mPendingKeyRepeatCount, this.mPendingKeyMetaState, this.mCurrentDeviceId, 1024, this.mCurrentSource);
            viewRootImpl.enqueueInputEvent(keyEvent);
        }

        private void sendKeyUp(long time) {
            if (this.mPendingKeyCode != 0) {
                ViewRootImpl.this.enqueueInputEvent(new KeyEvent(this.mPendingKeyDownTime, time, 1, this.mPendingKeyCode, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
                this.mPendingKeyCode = 0;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
            if (r7 < r3.mConfigMinFlingVelocity) goto L_0x0047;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
            if (java.lang.Math.abs(r6) >= r3.mConfigMinFlingVelocity) goto L_0x0047;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0044, code lost:
            r3.mFlingVelocity = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
            return false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x004d, code lost:
            if ((-r7) < r3.mConfigMinFlingVelocity) goto L_0x0066;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0057, code lost:
            if (java.lang.Math.abs(r6) >= r3.mConfigMinFlingVelocity) goto L_0x0066;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0059, code lost:
            r3.mFlingVelocity = -r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x005d, code lost:
            r3.mFlinging = postFling(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0065, code lost:
            return r3.mFlinging;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
            return false;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean startFling(long r4, float r6, float r7) {
            /*
                r3 = this;
                int r0 = r3.mPendingKeyCode
                r1 = 0
                switch(r0) {
                    case 19: goto L_0x0048;
                    case 20: goto L_0x0034;
                    case 21: goto L_0x001e;
                    case 22: goto L_0x000a;
                    default: goto L_0x0006;
                }
            L_0x0006:
                switch(r0) {
                    case 691: goto L_0x0048;
                    case 692: goto L_0x0034;
                    default: goto L_0x0009;
                }
            L_0x0009:
                goto L_0x005d
            L_0x000a:
                float r0 = r3.mConfigMinFlingVelocity
                int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                if (r0 < 0) goto L_0x001d
                float r0 = java.lang.Math.abs(r7)
                float r2 = r3.mConfigMinFlingVelocity
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 >= 0) goto L_0x001d
                r3.mFlingVelocity = r6
                goto L_0x005d
            L_0x001d:
                return r1
            L_0x001e:
                float r0 = -r6
                float r2 = r3.mConfigMinFlingVelocity
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 < 0) goto L_0x0033
                float r0 = java.lang.Math.abs(r7)
                float r2 = r3.mConfigMinFlingVelocity
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 >= 0) goto L_0x0033
                float r0 = -r6
                r3.mFlingVelocity = r0
                goto L_0x005d
            L_0x0033:
                return r1
            L_0x0034:
                float r0 = r3.mConfigMinFlingVelocity
                int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                if (r0 < 0) goto L_0x0047
                float r0 = java.lang.Math.abs(r6)
                float r2 = r3.mConfigMinFlingVelocity
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 >= 0) goto L_0x0047
                r3.mFlingVelocity = r7
                goto L_0x005d
            L_0x0047:
                return r1
            L_0x0048:
                float r0 = -r7
                float r2 = r3.mConfigMinFlingVelocity
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 < 0) goto L_0x0066
                float r0 = java.lang.Math.abs(r6)
                float r2 = r3.mConfigMinFlingVelocity
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 >= 0) goto L_0x0066
                float r0 = -r7
                r3.mFlingVelocity = r0
            L_0x005d:
                boolean r0 = r3.postFling(r4)
                r3.mFlinging = r0
                boolean r0 = r3.mFlinging
                return r0
            L_0x0066:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.SyntheticTouchNavigationHandler.startFling(long, float, float):boolean");
        }

        /* access modifiers changed from: private */
        public boolean postFling(long time) {
            if (this.mFlingVelocity < this.mConfigMinFlingVelocity) {
                return false;
            }
            postAtTime(this.mFlingRunnable, time + ((long) ((this.mConfigTickDistance / this.mFlingVelocity) * 1000.0f)));
            return true;
        }

        private void cancelFling() {
            if (this.mFlinging) {
                removeCallbacks(this.mFlingRunnable);
                this.mFlinging = false;
            }
        }
    }

    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {
        }

        public void process(KeyEvent event) {
            if ((event.getFlags() & 1024) == 0) {
                KeyCharacterMap.FallbackAction fallbackAction = event.getKeyCharacterMap().getFallbackAction(event.getKeyCode(), event.getMetaState());
                if (fallbackAction != null) {
                    KeyEvent fallbackEvent = KeyEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), fallbackAction.keyCode, event.getRepeatCount(), fallbackAction.metaState, event.getDeviceId(), event.getScanCode(), event.getFlags() | 1024, event.getSource(), (String) null);
                    fallbackAction.recycle();
                    ViewRootImpl.this.enqueueInputEvent(fallbackEvent);
                    return;
                }
            }
        }
    }

    private static boolean isNavigationKey(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 61:
            case 62:
            case 66:
            case 92:
            case 93:
            case 122:
            case 123:
            case 691:
            case 692:
                return true;
            default:
                return false;
        }
    }

    private static boolean isTypingKey(KeyEvent keyEvent) {
        return keyEvent.getUnicodeChar() > 0;
    }

    /* access modifiers changed from: private */
    public boolean checkForLeavingTouchModeAndConsume(KeyEvent event) {
        if (!this.mAttachInfo.mInTouchMode) {
            return false;
        }
        int action = event.getAction();
        if ((action != 0 && action != 2) || (event.getFlags() & 4) != 0) {
            return false;
        }
        if (isNavigationKey(event)) {
            return ensureTouchMode(false);
        }
        if (!isTypingKey(event)) {
            return false;
        }
        ensureTouchMode(false);
        return false;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void setLocalDragState(Object obj) {
        this.mLocalDragState = obj;
    }

    /* access modifiers changed from: private */
    public void handleDragEvent(DragEvent event) {
        if (this.mView != null && this.mAdded) {
            int what = event.mAction;
            if (what == 1) {
                this.mCurrentDragView = null;
                this.mDragDescription = event.mClipDescription;
            } else {
                if (what == 4) {
                    this.mDragDescription = null;
                }
                event.mClipDescription = this.mDragDescription;
            }
            if (what == 6) {
                if (View.sCascadedDragDrop) {
                    this.mView.dispatchDragEnterExitInPreN(event);
                }
                setDragFocus((View) null, event);
            } else {
                if (what == 2 || what == 3) {
                    this.mDragPoint.set(event.mX, event.mY);
                    if (this.mTranslator != null) {
                        this.mTranslator.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    if (this.mCurScrollY != 0) {
                        this.mDragPoint.offset(0.0f, (float) this.mCurScrollY);
                    }
                    event.mX = this.mDragPoint.x;
                    event.mY = this.mDragPoint.y;
                }
                View prevDragView = this.mCurrentDragView;
                if (what == 3 && event.mClipData != null) {
                    event.mClipData.prepareToEnterProcess();
                }
                boolean result = this.mView.dispatchDragEvent(event);
                if (what == 2 && !event.mEventHandlerWasCalled) {
                    setDragFocus((View) null, event);
                }
                if (prevDragView != this.mCurrentDragView) {
                    if (prevDragView != null) {
                        try {
                            this.mWindowSession.dragRecipientExited(this.mWindow);
                        } catch (RemoteException e) {
                            Slog.e(this.mTag, "Unable to note drag target change");
                        }
                    }
                    if (this.mCurrentDragView != null) {
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                }
                if (what == 3) {
                    try {
                        String str = this.mTag;
                        Log.i(str, "Reporting drop result: " + result);
                        this.mWindowSession.reportDropResult(this.mWindow, result);
                    } catch (RemoteException e2) {
                        Log.e(this.mTag, "Unable to report drop result");
                    }
                }
                if (what == 4) {
                    this.mCurrentDragView = null;
                    setLocalDragState((Object) null);
                    this.mAttachInfo.mDragToken = null;
                    if (this.mAttachInfo.mDragSurface != null) {
                        this.mAttachInfo.mDragSurface.release();
                        this.mAttachInfo.mDragSurface = null;
                    }
                }
            }
        }
        event.recycle();
    }

    public void handleDispatchSystemUiVisibilityChanged(SystemUiVisibilityInfo args) {
        if (this.mSeq != args.seq) {
            this.mSeq = args.seq;
            this.mAttachInfo.mForceReportNewAttributes = true;
            scheduleTraversals();
        }
        if (this.mView != null) {
            if (args.localChanges != 0) {
                this.mView.updateLocalSystemUiVisibility(args.localValue, args.localChanges);
            }
            int visibility = args.globalVisibility & 7;
            if (visibility != this.mAttachInfo.mGlobalSystemUiVisibility) {
                this.mAttachInfo.mGlobalSystemUiVisibility = visibility;
                this.mView.dispatchSystemUiVisibilityChanged(visibility);
            }
        }
    }

    public void onWindowTitleChanged() {
        this.mAttachInfo.mForceReportNewAttributes = true;
    }

    public void handleDispatchWindowShown() {
        this.mAttachInfo.mTreeObserver.dispatchOnWindowShown();
    }

    public void handleRequestKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
        Bundle data = new Bundle();
        ArrayList<KeyboardShortcutGroup> list = new ArrayList<>();
        if (this.mView != null) {
            this.mView.requestKeyboardShortcuts(list, deviceId);
        }
        data.putParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, list);
        try {
            receiver.send(0, data);
        } catch (RemoteException e) {
        }
    }

    @UnsupportedAppUsage
    public void getLastTouchPoint(Point outLocation) {
        outLocation.x = (int) this.mLastTouchPoint.x;
        outLocation.y = (int) this.mLastTouchPoint.y;
    }

    public int getLastTouchSource() {
        return this.mLastTouchSource;
    }

    public void setDragFocus(View newDragTarget, DragEvent event) {
        if (this.mCurrentDragView != newDragTarget && !View.sCascadedDragDrop) {
            float tx = event.mX;
            float ty = event.mY;
            int action = event.mAction;
            ClipData td = event.mClipData;
            event.mX = 0.0f;
            event.mY = 0.0f;
            event.mClipData = null;
            if (this.mCurrentDragView != null) {
                event.mAction = 6;
                this.mCurrentDragView.callDragEventHandler(event);
            }
            if (newDragTarget != null) {
                event.mAction = 5;
                newDragTarget.callDragEventHandler(event);
            }
            event.mAction = action;
            event.mX = tx;
            event.mY = ty;
            event.mClipData = td;
        }
        this.mCurrentDragView = newDragTarget;
    }

    private AudioManager getAudioManager() {
        if (this.mView != null) {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) this.mView.getContext().getSystemService("audio");
            }
            return this.mAudioManager;
        }
        throw new IllegalStateException("getAudioManager called when there is no mView");
    }

    /* access modifiers changed from: private */
    public AutofillManager getAutofillManager() {
        if (!(this.mView instanceof ViewGroup)) {
            return null;
        }
        ViewGroup decorView = (ViewGroup) this.mView;
        if (decorView.getChildCount() > 0) {
            return (AutofillManager) decorView.getChildAt(0).getContext().getSystemService(AutofillManager.class);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public boolean isAutofillUiShowing() {
        AutofillManager afm = getAutofillManager();
        if (afm == null) {
            return false;
        }
        return afm.isAutofillUiShowing();
    }

    public AccessibilityInteractionController getAccessibilityInteractionController() {
        if (this.mView != null) {
            if (this.mAccessibilityInteractionController == null) {
                this.mAccessibilityInteractionController = new AccessibilityInteractionController(this);
            }
            return this.mAccessibilityInteractionController;
        }
        throw new IllegalStateException("getAccessibilityInteractionController called when there is no mView");
    }

    private int relayoutWindow(WindowManager.LayoutParams params, int viewVisibility, boolean insetsPending) throws RemoteException {
        WindowManager.LayoutParams layoutParams = params;
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean restore = false;
        if (!(layoutParams == null || this.mTranslator == null)) {
            restore = true;
            params.backup();
            this.mTranslator.translateWindowLayout(layoutParams);
        }
        boolean restore2 = restore;
        if (!(layoutParams == null || this.mOrigWindowType == layoutParams.type || this.mTargetSdkVersion >= 14)) {
            String str = this.mTag;
            Slog.w(str, "Window type can not be changed after the window is added; ignoring change of " + this.mView);
            layoutParams.type = this.mOrigWindowType;
        }
        long frameNumber = -1;
        if (this.mSurface.isValid()) {
            frameNumber = this.mSurface.getNextFrameNumber();
        }
        long frameNumber2 = frameNumber;
        float f = appScale;
        int relayoutResult = this.mWindowSession.relayout(this.mWindow, this.mSeq, params, (int) ((((float) this.mView.getMeasuredWidth()) * appScale) + 0.5f), (int) ((((float) this.mView.getMeasuredHeight()) * appScale) + 0.5f), viewVisibility, insetsPending ? 1 : 0, frameNumber2, this.mTmpFrame, this.mPendingOverscanInsets, this.mPendingContentInsets, this.mPendingVisibleInsets, this.mPendingStableInsets, this.mPendingOutsets, this.mPendingBackDropFrame, this.mPendingDisplayCutout, this.mPendingMergedConfiguration, this.mSurfaceControl, this.mTempInsets);
        if (this.mSurfaceControl.isValid()) {
            this.mSurface.copyFrom(this.mSurfaceControl);
        } else {
            destroySurface();
        }
        this.mPendingAlwaysConsumeSystemBars = (relayoutResult & 64) != 0;
        if (restore2) {
            params.restore();
        }
        if (this.mTranslator != null) {
            this.mTranslator.translateRectInScreenToAppWinFrame(this.mTmpFrame);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingOverscanInsets);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingContentInsets);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingVisibleInsets);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingStableInsets);
        }
        setFrame(this.mTmpFrame);
        this.mInsetsController.onStateChanged(this.mTempInsets);
        return relayoutResult;
    }

    /* access modifiers changed from: private */
    public void setFrame(Rect frame) {
        this.mWinFrame.set(frame);
        this.mInsetsController.onFrameChanged(frame);
    }

    public void playSoundEffect(int effectId) {
        checkThread();
        try {
            AudioManager audioManager = getAudioManager();
            switch (effectId) {
                case 0:
                    audioManager.playSoundEffect(0);
                    return;
                case 1:
                    audioManager.playSoundEffect(3);
                    return;
                case 2:
                    audioManager.playSoundEffect(1);
                    return;
                case 3:
                    audioManager.playSoundEffect(4);
                    return;
                case 4:
                    audioManager.playSoundEffect(2);
                    return;
                default:
                    throw new IllegalArgumentException("unknown effect id " + effectId + " not defined in " + SoundEffectConstants.class.getCanonicalName());
            }
        } catch (IllegalStateException e) {
            String str = this.mTag;
            Log.e(str, "FATAL EXCEPTION when attempting to play sound effect: " + e);
            e.printStackTrace();
        }
    }

    public boolean performHapticFeedback(int effectId, boolean always) {
        try {
            return this.mWindowSession.performHapticFeedback(effectId, always);
        } catch (RemoteException e) {
            return false;
        }
    }

    public View focusSearch(View focused, int direction) {
        checkThread();
        if (!(this.mView instanceof ViewGroup)) {
            return null;
        }
        return FocusFinder.getInstance().findNextFocus((ViewGroup) this.mView, focused, direction);
    }

    public View keyboardNavigationClusterSearch(View currentCluster, int direction) {
        checkThread();
        return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this.mView, currentCluster, direction);
    }

    public void debug() {
        this.mView.debug();
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String innerPrefix = prefix + "  ";
        writer.print(prefix);
        writer.println("ViewRoot:");
        writer.print(innerPrefix);
        writer.print("mAdded=");
        writer.print(this.mAdded);
        writer.print(" mRemoved=");
        writer.println(this.mRemoved);
        writer.print(innerPrefix);
        writer.print("mConsumeBatchedInputScheduled=");
        writer.println(this.mConsumeBatchedInputScheduled);
        writer.print(innerPrefix);
        writer.print("mConsumeBatchedInputImmediatelyScheduled=");
        writer.println(this.mConsumeBatchedInputImmediatelyScheduled);
        writer.print(innerPrefix);
        writer.print("mPendingInputEventCount=");
        writer.println(this.mPendingInputEventCount);
        writer.print(innerPrefix);
        writer.print("mProcessInputEventsScheduled=");
        writer.println(this.mProcessInputEventsScheduled);
        writer.print(innerPrefix);
        writer.print("mTraversalScheduled=");
        writer.print(this.mTraversalScheduled);
        writer.print(innerPrefix);
        writer.print("mIsAmbientMode=");
        writer.print(this.mIsAmbientMode);
        if (this.mTraversalScheduled) {
            writer.print(" (barrier=");
            writer.print(this.mTraversalBarrier);
            writer.println(")");
        } else {
            writer.println();
        }
        this.mFirstInputStage.dump(innerPrefix, writer);
        this.mChoreographer.dump(prefix, writer);
        this.mInsetsController.dump(prefix, writer);
        writer.print(prefix);
        writer.println("View Hierarchy:");
        dumpViewHierarchy(innerPrefix, writer, this.mView);
    }

    private void dumpViewHierarchy(String prefix, PrintWriter writer, View view) {
        ViewGroup grp;
        int N;
        writer.print(prefix);
        if (view == null) {
            writer.println("null");
            return;
        }
        writer.println(view.toString());
        if ((view instanceof ViewGroup) && (N = grp.getChildCount()) > 0) {
            String prefix2 = prefix + "  ";
            for (int i = 0; i < N; i++) {
                dumpViewHierarchy(prefix2, writer, (grp = (ViewGroup) view).getChildAt(i));
            }
        }
    }

    public void dumpGfxInfo(int[] info) {
        info[1] = 0;
        info[0] = 0;
        if (this.mView != null) {
            getGfxInfo(this.mView, info);
        }
    }

    private static void getGfxInfo(View view, int[] info) {
        RenderNode renderNode = view.mRenderNode;
        info[0] = info[0] + 1;
        if (renderNode != null) {
            info[1] = info[1] + ((int) renderNode.computeApproximateMemoryUsage());
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                getGfxInfo(group.getChildAt(i), info);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean die(boolean immediate) {
        if (!immediate || this.mIsInTraversal) {
            if (!this.mIsDrawing) {
                destroyHardwareRenderer();
            } else {
                String str = this.mTag;
                Log.e(str, "Attempting to destroy the window while drawing!\n  window=" + this + ", title=" + this.mWindowAttributes.getTitle());
            }
            this.mHandler.sendEmptyMessage(3);
            return true;
        }
        doDie();
        return false;
    }

    /* access modifiers changed from: package-private */
    public void doDie() {
        checkThread();
        synchronized (this) {
            if (!this.mRemoved) {
                boolean viewVisibilityChanged = true;
                this.mRemoved = true;
                if (this.mAdded) {
                    dispatchDetachedFromWindow();
                }
                if (this.mAdded && !this.mFirst) {
                    destroyHardwareRenderer();
                    if (this.mView != null) {
                        int viewVisibility = this.mView.getVisibility();
                        if (this.mViewVisibility == viewVisibility) {
                            viewVisibilityChanged = false;
                        }
                        if (this.mWindowAttributesChanged || viewVisibilityChanged) {
                            try {
                                if ((relayoutWindow(this.mWindowAttributes, viewVisibility, false) & 2) != 0) {
                                    this.mWindowSession.finishDrawing(this.mWindow);
                                }
                            } catch (RemoteException e) {
                            }
                        }
                        destroySurface();
                    }
                }
                this.mAdded = false;
                WindowManagerGlobal.getInstance().doRemoveView(this);
            }
        }
    }

    public void requestUpdateConfiguration(Configuration config) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(18, config));
    }

    public void loadSystemProperties() {
        this.mHandler.post(new Runnable() {
            public void run() {
                boolean unused = ViewRootImpl.this.mProfileRendering = SystemProperties.getBoolean(ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                ViewRootImpl.this.profileRendering(ViewRootImpl.this.mAttachInfo.mHasWindowFocus);
                if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null && ViewRootImpl.this.mAttachInfo.mThreadedRenderer.loadSystemProperties()) {
                    ViewRootImpl.this.invalidate();
                }
                boolean layout = DisplayProperties.debug_layout().orElse(false).booleanValue();
                if (layout != ViewRootImpl.this.mAttachInfo.mDebugLayout) {
                    ViewRootImpl.this.mAttachInfo.mDebugLayout = layout;
                    if (!ViewRootImpl.this.mHandler.hasMessages(22)) {
                        ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(22, 200);
                    }
                }
            }
        });
    }

    private void destroyHardwareRenderer() {
        ThreadedRenderer hardwareRenderer = this.mAttachInfo.mThreadedRenderer;
        if (hardwareRenderer != null) {
            if (this.mView != null) {
                hardwareRenderer.destroyHardwareResources(this.mView);
            }
            hardwareRenderer.destroy();
            hardwareRenderer.setRequested(false);
            this.mAttachInfo.mThreadedRenderer = null;
            this.mAttachInfo.mHardwareAccelerated = false;
        }
    }

    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public void dispatchResized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration mergedConfiguration, Rect backDropFrame, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) {
        Rect rect = frame;
        Rect rect2 = overscanInsets;
        Rect rect3 = contentInsets;
        Rect rect4 = visibleInsets;
        Rect rect5 = stableInsets;
        MergedConfiguration mergedConfiguration2 = mergedConfiguration;
        Rect rect6 = backDropFrame;
        boolean sameProcessCall = true;
        if (this.mDragResizing && this.mUseMTRenderer) {
            boolean fullscreen = rect.equals(rect6);
            synchronized (this.mWindowCallbacks) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks.get(i).onWindowSizeIsChanging(rect6, fullscreen, rect4, rect5);
                }
            }
        }
        Message msg = this.mHandler.obtainMessage(reportDraw ? 5 : 4);
        if (this.mTranslator != null) {
            this.mTranslator.translateRectInScreenToAppWindow(rect);
            this.mTranslator.translateRectInScreenToAppWindow(rect2);
            this.mTranslator.translateRectInScreenToAppWindow(rect3);
            this.mTranslator.translateRectInScreenToAppWindow(rect4);
        }
        SomeArgs args = SomeArgs.obtain();
        if (Binder.getCallingPid() != Process.myPid()) {
            sameProcessCall = false;
        }
        args.arg1 = sameProcessCall ? new Rect(rect) : rect;
        args.arg2 = sameProcessCall ? new Rect(rect3) : rect3;
        args.arg3 = sameProcessCall ? new Rect(rect4) : rect4;
        args.arg4 = (!sameProcessCall || mergedConfiguration2 == null) ? mergedConfiguration2 : new MergedConfiguration(mergedConfiguration2);
        args.arg5 = sameProcessCall ? new Rect(rect2) : rect2;
        args.arg6 = sameProcessCall ? new Rect(rect5) : rect5;
        args.arg7 = sameProcessCall ? new Rect(outsets) : outsets;
        args.arg8 = sameProcessCall ? new Rect(rect6) : rect6;
        args.arg9 = displayCutout.get();
        args.argi1 = forceLayout ? 1 : 0;
        args.argi2 = alwaysConsumeSystemBars ? 1 : 0;
        args.argi3 = displayId;
        msg.obj = args;
        this.mHandler.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    public void dispatchInsetsChanged(InsetsState insetsState) {
        this.mHandler.obtainMessage(30, insetsState).sendToTarget();
    }

    /* access modifiers changed from: private */
    public void dispatchInsetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) {
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = insetsState;
        args.arg2 = activeControls;
        this.mHandler.obtainMessage(31, args).sendToTarget();
    }

    public void dispatchMoved(int newX, int newY) {
        if (this.mTranslator != null) {
            PointF point = new PointF((float) newX, (float) newY);
            this.mTranslator.translatePointInScreenToAppWindow(point);
            newX = (int) (((double) point.x) + 0.5d);
            newY = (int) (((double) point.y) + 0.5d);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(23, newX, newY));
    }

    private static final class QueuedInputEvent {
        public static final int FLAG_DEFERRED = 2;
        public static final int FLAG_DELIVER_POST_IME = 1;
        public static final int FLAG_FINISHED = 4;
        public static final int FLAG_FINISHED_HANDLED = 8;
        public static final int FLAG_MODIFIED_FOR_COMPATIBILITY = 64;
        public static final int FLAG_RESYNTHESIZED = 16;
        public static final int FLAG_UNHANDLED = 32;
        public InputEvent mEvent;
        public int mFlags;
        public QueuedInputEvent mNext;
        public InputEventReceiver mReceiver;

        private QueuedInputEvent() {
        }

        public boolean shouldSkipIme() {
            if ((this.mFlags & 1) != 0) {
                return true;
            }
            if (!(this.mEvent instanceof MotionEvent) || (!this.mEvent.isFromSource(2) && !this.mEvent.isFromSource(4194304))) {
                return false;
            }
            return true;
        }

        public boolean shouldSendToSynthesizer() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("QueuedInputEvent{flags=");
            if (!flagToString("UNHANDLED", 32, flagToString("RESYNTHESIZED", 16, flagToString("FINISHED_HANDLED", 8, flagToString("FINISHED", 4, flagToString("DEFERRED", 2, flagToString("DELIVER_POST_IME", 1, false, sb), sb), sb), sb), sb), sb)) {
                sb.append("0");
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(", hasNextQueuedEvent=");
            sb2.append(this.mEvent != null ? "true" : "false");
            sb.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(", hasInputEventReceiver=");
            sb3.append(this.mReceiver != null ? "true" : "false");
            sb.append(sb3.toString());
            sb.append(", mEvent=" + this.mEvent + "}");
            return sb.toString();
        }

        private boolean flagToString(String name, int flag, boolean hasPrevious, StringBuilder sb) {
            if ((this.mFlags & flag) == 0) {
                return hasPrevious;
            }
            if (hasPrevious) {
                sb.append("|");
            }
            sb.append(name);
            return true;
        }
    }

    private QueuedInputEvent obtainQueuedInputEvent(InputEvent event, InputEventReceiver receiver, int flags) {
        QueuedInputEvent q = this.mQueuedInputEventPool;
        if (q != null) {
            this.mQueuedInputEventPoolSize--;
            this.mQueuedInputEventPool = q.mNext;
            q.mNext = null;
        } else {
            q = new QueuedInputEvent();
        }
        q.mEvent = event;
        q.mReceiver = receiver;
        q.mFlags = flags;
        return q;
    }

    private void recycleQueuedInputEvent(QueuedInputEvent q) {
        q.mEvent = null;
        q.mReceiver = null;
        if (this.mQueuedInputEventPoolSize < 10) {
            this.mQueuedInputEventPoolSize++;
            q.mNext = this.mQueuedInputEventPool;
            this.mQueuedInputEventPool = q;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void enqueueInputEvent(InputEvent event) {
        enqueueInputEvent(event, (InputEventReceiver) null, 0, false);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void enqueueInputEvent(InputEvent event, InputEventReceiver receiver, int flags, boolean processImmediately) {
        QueuedInputEvent q = obtainQueuedInputEvent(event, receiver, flags);
        QueuedInputEvent last = this.mPendingInputEventTail;
        if (last == null) {
            this.mPendingInputEventHead = q;
            this.mPendingInputEventTail = q;
        } else {
            last.mNext = q;
            this.mPendingInputEventTail = q;
        }
        this.mPendingInputEventCount++;
        Trace.traceCounter(4, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
        if (processImmediately) {
            doProcessInputEvents();
        } else {
            scheduleProcessInputEvents();
        }
    }

    private void scheduleProcessInputEvents() {
        if (!this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = true;
            Message msg = this.mHandler.obtainMessage(19);
            msg.setAsynchronous(true);
            this.mHandler.sendMessage(msg);
        }
    }

    /* access modifiers changed from: package-private */
    public void doProcessInputEvents() {
        while (this.mPendingInputEventHead != null) {
            QueuedInputEvent q = this.mPendingInputEventHead;
            this.mPendingInputEventHead = q.mNext;
            if (this.mPendingInputEventHead == null) {
                this.mPendingInputEventTail = null;
            }
            q.mNext = null;
            this.mPendingInputEventCount--;
            Trace.traceCounter(4, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
            long eventTime = q.mEvent.getEventTimeNano();
            long oldestEventTime = eventTime;
            if (q.mEvent instanceof MotionEvent) {
                MotionEvent me = (MotionEvent) q.mEvent;
                if (me.getHistorySize() > 0) {
                    oldestEventTime = me.getHistoricalEventTimeNano(0);
                }
            }
            this.mChoreographer.mFrameInfo.updateInputEventTime(eventTime, oldestEventTime);
            deliverInputEvent(q);
        }
        if (this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = false;
            this.mHandler.removeMessages(19);
        }
    }

    private void deliverInputEvent(QueuedInputEvent q) {
        InputStage stage;
        Trace.asyncTraceBegin(8, "deliverInputEvent", q.mEvent.getSequenceNumber());
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onInputEvent(q.mEvent, 0);
        }
        if (q.shouldSendToSynthesizer()) {
            stage = this.mSyntheticInputStage;
        } else {
            stage = q.shouldSkipIme() ? this.mFirstPostImeInputStage : this.mFirstInputStage;
        }
        if (q.mEvent instanceof KeyEvent) {
            this.mUnhandledKeyManager.preDispatch((KeyEvent) q.mEvent);
        }
        if (stage != null) {
            handleWindowFocusChanged();
            stage.deliver(q);
            return;
        }
        finishInputEvent(q);
    }

    /* access modifiers changed from: private */
    public void finishInputEvent(QueuedInputEvent q) {
        Trace.asyncTraceEnd(8, "deliverInputEvent", q.mEvent.getSequenceNumber());
        if (q.mReceiver != null) {
            boolean modified = false;
            boolean handled = (q.mFlags & 8) != 0;
            if ((q.mFlags & 64) != 0) {
                modified = true;
            }
            if (modified) {
                Trace.traceBegin(8, "processInputEventBeforeFinish");
                try {
                    InputEvent processedEvent = this.mInputCompatProcessor.processInputEventBeforeFinish(q.mEvent);
                    if (processedEvent != null) {
                        q.mReceiver.finishInputEvent(processedEvent, handled);
                    }
                } finally {
                    Trace.traceEnd(8);
                }
            } else {
                q.mReceiver.finishInputEvent(q.mEvent, handled);
            }
        } else {
            q.mEvent.recycleIfNeededAfterDispatch();
        }
        recycleQueuedInputEvent(q);
    }

    static boolean isTerminalInputEvent(InputEvent event) {
        if (!(event instanceof KeyEvent)) {
            int action = ((MotionEvent) event).getAction();
            if (action == 1 || action == 3 || action == 10) {
                return true;
            }
            return false;
        } else if (((KeyEvent) event).getAction() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void scheduleConsumeBatchedInput() {
        if (!this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = true;
            this.mChoreographer.postCallback(0, this.mConsumedBatchedInputRunnable, (Object) null);
        }
    }

    /* access modifiers changed from: package-private */
    public void unscheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mConsumedBatchedInputRunnable, (Object) null);
        }
    }

    /* access modifiers changed from: package-private */
    public void scheduleConsumeBatchedInputImmediately() {
        if (!this.mConsumeBatchedInputImmediatelyScheduled) {
            unscheduleConsumeBatchedInput();
            this.mConsumeBatchedInputImmediatelyScheduled = true;
            this.mHandler.post(this.mConsumeBatchedInputImmediatelyRunnable);
        }
    }

    /* access modifiers changed from: package-private */
    public void doConsumeBatchedInput(long frameTimeNanos) {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            if (!(this.mInputEventReceiver == null || !this.mInputEventReceiver.consumeBatchedInputEvents(frameTimeNanos) || frameTimeNanos == -1)) {
                scheduleConsumeBatchedInput();
            }
            doProcessInputEvents();
        }
    }

    final class TraversalRunnable implements Runnable {
        TraversalRunnable() {
        }

        public void run() {
            ViewRootImpl.this.doTraversal();
        }
    }

    final class WindowInputEventReceiver extends InputEventReceiver {
        public WindowInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        /* JADX INFO: finally extract failed */
        public void onInputEvent(InputEvent event) {
            Trace.traceBegin(8, "processInputEventForCompatibility");
            try {
                List<InputEvent> processedEvents = ViewRootImpl.this.mInputCompatProcessor.processInputEventForCompatibility(event);
                Trace.traceEnd(8);
                if (processedEvents == null) {
                    ViewRootImpl.this.enqueueInputEvent(event, this, 0, true);
                } else if (processedEvents.isEmpty()) {
                    finishInputEvent(event, true);
                } else {
                    for (int i = 0; i < processedEvents.size(); i++) {
                        ViewRootImpl.this.enqueueInputEvent(processedEvents.get(i), this, 64, true);
                    }
                }
            } catch (Throwable th) {
                Trace.traceEnd(8);
                throw th;
            }
        }

        public void onBatchedInputEventPending() {
            if (ViewRootImpl.this.mUnbufferedInputDispatch) {
                super.onBatchedInputEventPending();
            } else {
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
        }

        public void dispose() {
            ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }
    }

    final class ConsumeBatchedInputRunnable implements Runnable {
        ConsumeBatchedInputRunnable() {
        }

        public void run() {
            ViewRootImpl.this.doConsumeBatchedInput(ViewRootImpl.this.mChoreographer.getFrameTimeNanos());
        }
    }

    final class ConsumeBatchedInputImmediatelyRunnable implements Runnable {
        ConsumeBatchedInputImmediatelyRunnable() {
        }

        public void run() {
            ViewRootImpl.this.doConsumeBatchedInput(-1);
        }
    }

    final class InvalidateOnAnimationRunnable implements Runnable {
        private boolean mPosted;
        private View.AttachInfo.InvalidateInfo[] mTempViewRects;
        private View[] mTempViews;
        private final ArrayList<View.AttachInfo.InvalidateInfo> mViewRects = new ArrayList<>();
        private final ArrayList<View> mViews = new ArrayList<>();

        InvalidateOnAnimationRunnable() {
        }

        public void addView(View view) {
            synchronized (this) {
                this.mViews.add(view);
                postIfNeededLocked();
            }
        }

        public void addViewRect(View.AttachInfo.InvalidateInfo info) {
            synchronized (this) {
                this.mViewRects.add(info);
                postIfNeededLocked();
            }
        }

        public void removeView(View view) {
            synchronized (this) {
                this.mViews.remove(view);
                int i = this.mViewRects.size();
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    View.AttachInfo.InvalidateInfo info = this.mViewRects.get(i2);
                    if (info.target == view) {
                        this.mViewRects.remove(i2);
                        info.recycle();
                    }
                    i = i2;
                }
                if (this.mPosted && this.mViews.isEmpty() && this.mViewRects.isEmpty()) {
                    ViewRootImpl.this.mChoreographer.removeCallbacks(1, this, (Object) null);
                    this.mPosted = false;
                }
            }
        }

        public void run() {
            int i;
            int viewCount;
            int viewRectCount;
            synchronized (this) {
                this.mPosted = false;
                viewCount = this.mViews.size();
                if (viewCount != 0) {
                    this.mTempViews = (View[]) this.mViews.toArray(this.mTempViews != null ? this.mTempViews : new View[viewCount]);
                    this.mViews.clear();
                }
                viewRectCount = this.mViewRects.size();
                if (viewRectCount != 0) {
                    this.mTempViewRects = (View.AttachInfo.InvalidateInfo[]) this.mViewRects.toArray(this.mTempViewRects != null ? this.mTempViewRects : new View.AttachInfo.InvalidateInfo[viewRectCount]);
                    this.mViewRects.clear();
                }
            }
            for (int i2 = 0; i2 < viewCount; i2++) {
                this.mTempViews[i2].invalidate();
                this.mTempViews[i2] = null;
            }
            for (i = 0; i < viewRectCount; i++) {
                View.AttachInfo.InvalidateInfo info = this.mTempViewRects[i];
                info.target.invalidate(info.left, info.top, info.right, info.bottom);
                info.recycle();
            }
        }

        private void postIfNeededLocked() {
            if (!this.mPosted) {
                ViewRootImpl.this.mChoreographer.postCallback(1, this, (Object) null);
                this.mPosted = true;
            }
        }
    }

    public void dispatchInvalidateDelayed(View view, long delayMilliseconds) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, view), delayMilliseconds);
    }

    public void dispatchInvalidateRectDelayed(View.AttachInfo.InvalidateInfo info, long delayMilliseconds) {
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2, info), delayMilliseconds);
    }

    public void dispatchInvalidateOnAnimation(View view) {
        this.mInvalidateOnAnimationRunnable.addView(view);
    }

    public void dispatchInvalidateRectOnAnimation(View.AttachInfo.InvalidateInfo info) {
        this.mInvalidateOnAnimationRunnable.addViewRect(info);
    }

    @UnsupportedAppUsage
    public void cancelInvalidate(View view) {
        this.mHandler.removeMessages(1, view);
        this.mHandler.removeMessages(2, view);
        this.mInvalidateOnAnimationRunnable.removeView(view);
    }

    @UnsupportedAppUsage
    public void dispatchInputEvent(InputEvent event) {
        dispatchInputEvent(event, (InputEventReceiver) null);
    }

    @UnsupportedAppUsage
    public void dispatchInputEvent(InputEvent event, InputEventReceiver receiver) {
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = event;
        args.arg2 = receiver;
        Message msg = this.mHandler.obtainMessage(7, args);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    public void synthesizeInputEvent(InputEvent event) {
        Message msg = this.mHandler.obtainMessage(24, event);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    @UnsupportedAppUsage
    public void dispatchKeyFromIme(KeyEvent event) {
        Message msg = this.mHandler.obtainMessage(11, event);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    public void dispatchKeyFromAutofill(KeyEvent event) {
        Message msg = this.mHandler.obtainMessage(12, event);
        msg.setAsynchronous(true);
        this.mHandler.sendMessage(msg);
    }

    @UnsupportedAppUsage
    public void dispatchUnhandledInputEvent(InputEvent event) {
        if (event instanceof MotionEvent) {
            event = MotionEvent.obtain((MotionEvent) event);
        }
        synthesizeInputEvent(event);
    }

    public void dispatchAppVisibility(boolean visible) {
        Message msg = this.mHandler.obtainMessage(8);
        msg.arg1 = visible;
        this.mHandler.sendMessage(msg);
    }

    public void dispatchGetNewSurface() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9));
    }

    public void windowFocusChanged(boolean hasFocus, boolean inTouchMode) {
        synchronized (this) {
            this.mWindowFocusChanged = true;
            this.mUpcomingWindowFocus = hasFocus;
            this.mUpcomingInTouchMode = inTouchMode;
        }
        Message msg = Message.obtain();
        msg.what = 6;
        this.mHandler.sendMessage(msg);
    }

    public void dispatchWindowShown() {
        this.mHandler.sendEmptyMessage(25);
    }

    public void dispatchCloseSystemDialogs(String reason) {
        Message msg = Message.obtain();
        msg.what = 14;
        msg.obj = reason;
        this.mHandler.sendMessage(msg);
    }

    public void dispatchDragEvent(DragEvent event) {
        int what;
        if (event.getAction() == 2) {
            what = 16;
            this.mHandler.removeMessages(16);
        } else {
            what = 15;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(what, event));
    }

    public void updatePointerIcon(float x, float y) {
        this.mHandler.removeMessages(27);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(27, MotionEvent.obtain(0, SystemClock.uptimeMillis(), 7, x, y, 0)));
    }

    public void dispatchSystemUiVisibilityChanged(int seq, int globalVisibility, int localValue, int localChanges) {
        SystemUiVisibilityInfo args = new SystemUiVisibilityInfo();
        args.seq = seq;
        args.globalVisibility = globalVisibility;
        args.localValue = localValue;
        args.localChanges = localChanges;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(17, args));
    }

    public void dispatchCheckFocus() {
        if (!this.mHandler.hasMessages(13)) {
            this.mHandler.sendEmptyMessage(13);
        }
    }

    public void dispatchRequestKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
        this.mHandler.obtainMessage(26, deviceId, 0, receiver).sendToTarget();
    }

    public void dispatchPointerCaptureChanged(boolean on) {
        this.mHandler.removeMessages(28);
        Message msg = this.mHandler.obtainMessage(28);
        msg.arg1 = on;
        this.mHandler.sendMessage(msg);
    }

    private void postSendWindowContentChangedCallback(View source, int changeType) {
        if (this.mSendWindowContentChangedAccessibilityEvent == null) {
            this.mSendWindowContentChangedAccessibilityEvent = new SendWindowContentChangedAccessibilityEvent();
        }
        this.mSendWindowContentChangedAccessibilityEvent.runOrPost(source, changeType);
    }

    private void removeSendWindowContentChangedCallback() {
        if (this.mSendWindowContentChangedAccessibilityEvent != null) {
            this.mHandler.removeCallbacks(this.mSendWindowContentChangedAccessibilityEvent);
        }
    }

    public boolean showContextMenuForChild(View originalView) {
        return false;
    }

    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return false;
    }

    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return null;
    }

    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
        return null;
    }

    public void createContextMenu(ContextMenu menu) {
    }

    public void childDrawableStateChanged(View child) {
    }

    public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        AccessibilityNodeProvider provider;
        if (this.mView == null || this.mStopped || this.mPausedForTransition) {
            return false;
        }
        if (!(event.getEventType() == 2048 || this.mSendWindowContentChangedAccessibilityEvent == null || this.mSendWindowContentChangedAccessibilityEvent.mSource == null)) {
            this.mSendWindowContentChangedAccessibilityEvent.removeCallbacksAndRun();
        }
        int eventType = event.getEventType();
        View source = getSourceForAccessibilityEvent(event);
        if (eventType == 2048) {
            handleWindowContentChangedEvent(event);
        } else if (eventType != 32768) {
            if (!(eventType != 65536 || source == null || source.getAccessibilityNodeProvider() == null)) {
                setAccessibilityFocus((View) null, (AccessibilityNodeInfo) null);
            }
        } else if (!(source == null || (provider = source.getAccessibilityNodeProvider()) == null)) {
            setAccessibilityFocus(source, provider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(event.getSourceNodeId())));
        }
        this.mAccessibilityManager.sendAccessibilityEvent(event);
        return true;
    }

    private View getSourceForAccessibilityEvent(AccessibilityEvent event) {
        return AccessibilityNodeIdManager.getInstance().findView(AccessibilityNodeInfo.getAccessibilityViewId(event.getSourceNodeId()));
    }

    /* JADX WARNING: type inference failed for: r11v3, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleWindowContentChangedEvent(android.view.accessibility.AccessibilityEvent r17) {
        /*
            r16 = this;
            r0 = r16
            android.view.View r1 = r0.mAccessibilityFocusedHost
            if (r1 == 0) goto L_0x008f
            android.view.accessibility.AccessibilityNodeInfo r2 = r0.mAccessibilityFocusedVirtualView
            if (r2 != 0) goto L_0x000c
            goto L_0x008f
        L_0x000c:
            android.view.accessibility.AccessibilityNodeProvider r2 = r1.getAccessibilityNodeProvider()
            r3 = 0
            r4 = 0
            if (r2 != 0) goto L_0x001c
            r0.mAccessibilityFocusedHost = r4
            r0.mAccessibilityFocusedVirtualView = r4
            r1.clearAccessibilityFocusNoCallbacks(r3)
            return
        L_0x001c:
            int r5 = r17.getContentChangeTypes()
            r6 = r5 & 1
            if (r6 != 0) goto L_0x0027
            if (r5 == 0) goto L_0x0027
            return
        L_0x0027:
            long r6 = r17.getSourceNodeId()
            int r8 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(r6)
            r9 = 0
            android.view.View r10 = r0.mAccessibilityFocusedHost
        L_0x0032:
            if (r10 == 0) goto L_0x004c
            if (r9 != 0) goto L_0x004c
            int r11 = r10.getAccessibilityViewId()
            if (r8 != r11) goto L_0x003e
            r9 = 1
            goto L_0x0032
        L_0x003e:
            android.view.ViewParent r11 = r10.getParent()
            boolean r12 = r11 instanceof android.view.View
            if (r12 == 0) goto L_0x004a
            r10 = r11
            android.view.View r10 = (android.view.View) r10
            goto L_0x004b
        L_0x004a:
            r10 = 0
        L_0x004b:
            goto L_0x0032
        L_0x004c:
            if (r9 != 0) goto L_0x004f
            return
        L_0x004f:
            android.view.accessibility.AccessibilityNodeInfo r11 = r0.mAccessibilityFocusedVirtualView
            long r11 = r11.getSourceNodeId()
            int r13 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(r11)
            android.graphics.Rect r14 = r0.mTempRect
            android.view.accessibility.AccessibilityNodeInfo r15 = r0.mAccessibilityFocusedVirtualView
            r15.getBoundsInScreen(r14)
            android.view.accessibility.AccessibilityNodeInfo r15 = r2.createAccessibilityNodeInfo(r13)
            r0.mAccessibilityFocusedVirtualView = r15
            android.view.accessibility.AccessibilityNodeInfo r15 = r0.mAccessibilityFocusedVirtualView
            if (r15 != 0) goto L_0x007c
            r0.mAccessibilityFocusedHost = r4
            r1.clearAccessibilityFocusNoCallbacks(r3)
            android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r3 = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS
            int r3 = r3.getId()
            r2.performAction(r13, r3, r4)
            r0.invalidateRectOnScreen(r14)
            goto L_0x008e
        L_0x007c:
            android.view.accessibility.AccessibilityNodeInfo r3 = r0.mAccessibilityFocusedVirtualView
            android.graphics.Rect r3 = r3.getBoundsInScreen()
            boolean r4 = r14.equals(r3)
            if (r4 != 0) goto L_0x008e
            r14.union(r3)
            r0.invalidateRectOnScreen(r14)
        L_0x008e:
            return
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.handleWindowContentChangedEvent(android.view.accessibility.AccessibilityEvent):void");
    }

    public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
        postSendWindowContentChangedCallback((View) Preconditions.checkNotNull(source), changeType);
    }

    public boolean canResolveLayoutDirection() {
        return true;
    }

    public boolean isLayoutDirectionResolved() {
        return true;
    }

    public int getLayoutDirection() {
        return 0;
    }

    public boolean canResolveTextDirection() {
        return true;
    }

    public boolean isTextDirectionResolved() {
        return true;
    }

    public int getTextDirection() {
        return 1;
    }

    public boolean canResolveTextAlignment() {
        return true;
    }

    public boolean isTextAlignmentResolved() {
        return true;
    }

    public int getTextAlignment() {
        return 1;
    }

    /* JADX WARNING: type inference failed for: r3v2, types: [android.view.ViewParent] */
    /* JADX WARNING: type inference failed for: r2v6, types: [android.view.ViewParent] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getCommonPredecessor(android.view.View r6, android.view.View r7) {
        /*
            r5 = this;
            java.util.HashSet<android.view.View> r0 = r5.mTempHashSet
            if (r0 != 0) goto L_0x000b
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            r5.mTempHashSet = r0
        L_0x000b:
            java.util.HashSet<android.view.View> r0 = r5.mTempHashSet
            r0.clear()
            r1 = r6
        L_0x0011:
            if (r1 == 0) goto L_0x0022
            r0.add(r1)
            android.view.ViewParent r2 = r1.mParent
            boolean r3 = r2 instanceof android.view.View
            if (r3 == 0) goto L_0x0020
            r1 = r2
            android.view.View r1 = (android.view.View) r1
            goto L_0x0021
        L_0x0020:
            r1 = 0
        L_0x0021:
            goto L_0x0011
        L_0x0022:
            r2 = r7
        L_0x0023:
            if (r2 == 0) goto L_0x003b
            boolean r3 = r0.contains(r2)
            if (r3 == 0) goto L_0x002f
            r0.clear()
            return r2
        L_0x002f:
            android.view.ViewParent r3 = r2.mParent
            boolean r4 = r3 instanceof android.view.View
            if (r4 == 0) goto L_0x0039
            r2 = r3
            android.view.View r2 = (android.view.View) r2
            goto L_0x003a
        L_0x0039:
            r2 = 0
        L_0x003a:
            goto L_0x0023
        L_0x003b:
            r0.clear()
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewRootImpl.getCommonPredecessor(android.view.View, android.view.View):android.view.View");
    }

    /* access modifiers changed from: package-private */
    public void checkThread() {
        if (this.mThread != Thread.currentThread()) {
            throw new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views.");
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        if (rectangle == null) {
            return scrollToRectOrFocus((Rect) null, immediate);
        }
        rectangle.offset(child.getLeft() - child.getScrollX(), child.getTop() - child.getScrollY());
        boolean scrolled = scrollToRectOrFocus(rectangle, immediate);
        this.mTempRect.set(rectangle);
        this.mTempRect.offset(0, -this.mCurScrollY);
        this.mTempRect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
        try {
            this.mWindowSession.onRectangleOnScreenRequested(this.mWindow, this.mTempRect);
        } catch (RemoteException e) {
        }
        return scrolled;
    }

    public void childHasTransientStateChanged(View child, boolean hasTransientState) {
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    public void onStopNestedScroll(View target) {
    }

    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        return false;
    }

    /* access modifiers changed from: private */
    public void reportNextDraw() {
        if (!this.mReportNextDraw) {
            drawPending();
        }
        this.mReportNextDraw = true;
    }

    public void setReportNextDraw() {
        reportNextDraw();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void changeCanvasOpacity(boolean opaque) {
        String str = this.mTag;
        Log.d(str, "changeCanvasOpacity: opaque=" + opaque);
        boolean opaque2 = opaque & ((this.mView.mPrivateFlags & 512) == 0);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(opaque2);
        }
    }

    public boolean dispatchUnhandledKeyEvent(KeyEvent event) {
        return this.mUnhandledKeyManager.dispatch(this.mView, event);
    }

    class TakenSurfaceHolder extends BaseSurfaceHolder {
        TakenSurfaceHolder() {
        }

        public boolean onAllowLockCanvas() {
            return ViewRootImpl.this.mDrawingAllowed;
        }

        public void onRelayoutContainer() {
        }

        public void setFormat(int format) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceFormat(format);
        }

        public void setType(int type) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceType(type);
        }

        public void onUpdateSurface() {
            throw new IllegalStateException("Shouldn't be here");
        }

        public boolean isCreating() {
            return ViewRootImpl.this.mIsCreating;
        }

        public void setFixedSize(int width, int height) {
            throw new UnsupportedOperationException("Currently only support sizing from layout");
        }

        public void setKeepScreenOn(boolean screenOn) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceKeepScreenOn(screenOn);
        }
    }

    static class W extends IWindow.Stub {
        private final WeakReference<ViewRootImpl> mViewAncestor;
        private final IWindowSession mWindowSession;

        W(ViewRootImpl viewAncestor) {
            this.mViewAncestor = new WeakReference<>(viewAncestor);
            this.mWindowSession = viewAncestor.mWindowSession;
        }

        public void resized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration mergedConfiguration, Rect backDropFrame, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchResized(frame, overscanInsets, contentInsets, visibleInsets, stableInsets, outsets, reportDraw, mergedConfiguration, backDropFrame, forceLayout, alwaysConsumeSystemBars, displayId, displayCutout);
            }
        }

        public void insetsChanged(InsetsState insetsState) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchInsetsChanged(insetsState);
            }
        }

        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchInsetsControlChanged(insetsState, activeControls);
            }
        }

        public void moved(int newX, int newY) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchMoved(newX, newY);
            }
        }

        public void dispatchAppVisibility(boolean visible) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchAppVisibility(visible);
            }
        }

        public void dispatchGetNewSurface() {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchGetNewSurface();
            }
        }

        public void windowFocusChanged(boolean hasFocus, boolean inTouchMode) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.windowFocusChanged(hasFocus, inTouchMode);
            }
        }

        private static int checkCallingPermission(String permission) {
            try {
                return ActivityManager.getService().checkPermission(permission, Binder.getCallingPid(), Binder.getCallingUid());
            } catch (RemoteException e) {
                return -1;
            }
        }

        public void executeCommand(String command, String parameters, ParcelFileDescriptor out) {
            View view;
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null && (view = viewAncestor.mView) != null) {
                if (checkCallingPermission(Manifest.permission.DUMP) == 0) {
                    OutputStream clientStream = null;
                    try {
                        clientStream = new ParcelFileDescriptor.AutoCloseOutputStream(out);
                        ViewDebug.dispatchCommand(view, command, parameters, clientStream);
                        try {
                            clientStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        if (clientStream != null) {
                            clientStream.close();
                        }
                    } catch (Throwable th) {
                        if (clientStream != null) {
                            try {
                                clientStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } else {
                    throw new SecurityException("Insufficient permissions to invoke executeCommand() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
                }
            }
        }

        public void closeSystemDialogs(String reason) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchCloseSystemDialogs(reason);
            }
        }

        public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(asBinder());
                } catch (RemoteException e) {
                }
            }
        }

        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperCommandComplete(asBinder(), (Bundle) null);
                } catch (RemoteException e) {
                }
            }
        }

        public void dispatchDragEvent(DragEvent event) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchDragEvent(event);
            }
        }

        public void updatePointerIcon(float x, float y) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.updatePointerIcon(x, y);
            }
        }

        public void dispatchSystemUiVisibilityChanged(int seq, int globalVisibility, int localValue, int localChanges) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchSystemUiVisibilityChanged(seq, globalVisibility, localValue, localChanges);
            }
        }

        public void dispatchWindowShown() {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchWindowShown();
            }
        }

        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchRequestKeyboardShortcuts(receiver, deviceId);
            }
        }

        public void dispatchPointerCaptureChanged(boolean hasCapture) {
            ViewRootImpl viewAncestor = (ViewRootImpl) this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchPointerCaptureChanged(hasCapture);
            }
        }
    }

    public static final class CalledFromWrongThreadException extends AndroidRuntimeException {
        @UnsupportedAppUsage
        public CalledFromWrongThreadException(String msg) {
            super(msg);
        }
    }

    static HandlerActionQueue getRunQueue() {
        HandlerActionQueue rq = sRunQueues.get();
        if (rq != null) {
            return rq;
        }
        HandlerActionQueue rq2 = new HandlerActionQueue();
        sRunQueues.set(rq2);
        return rq2;
    }

    private void startDragResizing(Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets, int resizeMode) {
        if (!this.mDragResizing) {
            this.mDragResizing = true;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks.get(i).onWindowDragResizeStart(initialBounds, fullscreen, systemInsets, stableInsets, resizeMode);
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private void endDragResizing() {
        if (this.mDragResizing) {
            this.mDragResizing = false;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks.get(i).onWindowDragResizeEnd();
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private boolean updateContentDrawBounds() {
        boolean updated = false;
        boolean z = true;
        if (this.mUseMTRenderer) {
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                updated |= this.mWindowCallbacks.get(i).onContentDrawn(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWidth, this.mHeight);
            }
        }
        if (this.mDragResizing == 0 || !this.mReportNextDraw) {
            z = false;
        }
        return updated | z;
    }

    private void requestDrawWindow() {
        if (this.mUseMTRenderer) {
            this.mWindowDrawCountDown = new CountDownLatch(this.mWindowCallbacks.size());
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                this.mWindowCallbacks.get(i).onRequestDraw(this.mReportNextDraw);
            }
        }
    }

    public void reportActivityRelaunched() {
        this.mActivityRelaunched = true;
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    final class AccessibilityInteractionConnectionManager implements AccessibilityManager.AccessibilityStateChangeListener {
        AccessibilityInteractionConnectionManager() {
        }

        public void onAccessibilityStateChanged(boolean enabled) {
            if (enabled) {
                ensureConnection();
                if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus && ViewRootImpl.this.mView != null) {
                    ViewRootImpl.this.mView.sendAccessibilityEvent(32);
                    View focusedView = ViewRootImpl.this.mView.findFocus();
                    if (focusedView != null && focusedView != ViewRootImpl.this.mView) {
                        focusedView.sendAccessibilityEvent(8);
                        return;
                    }
                    return;
                }
                return;
            }
            ensureNoConnection();
            ViewRootImpl.this.mHandler.obtainMessage(21).sendToTarget();
        }

        public void ensureConnection() {
            if (!(ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1)) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = ViewRootImpl.this.mAccessibilityManager.addAccessibilityInteractionConnection(ViewRootImpl.this.mWindow, ViewRootImpl.this.mContext.getPackageName(), new AccessibilityInteractionConnection(ViewRootImpl.this));
            }
        }

        public void ensureNoConnection() {
            if (ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = -1;
                ViewRootImpl.this.mAccessibilityManager.removeAccessibilityInteractionConnection(ViewRootImpl.this.mWindow);
            }
        }
    }

    final class HighContrastTextManager implements AccessibilityManager.HighTextContrastChangeListener {
        HighContrastTextManager() {
            ThreadedRenderer.setHighContrastText(ViewRootImpl.this.mAccessibilityManager.isHighTextContrastEnabled());
        }

        public void onHighTextContrastStateChanged(boolean enabled) {
            ThreadedRenderer.setHighContrastText(enabled);
            ViewRootImpl.this.destroyHardwareResources();
            ViewRootImpl.this.invalidate();
        }
    }

    static final class AccessibilityInteractionConnection extends IAccessibilityInteractionConnection.Stub {
        private final WeakReference<ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new WeakReference<>(viewRootImpl);
        }

        public void findAccessibilityNodeInfoByAccessibilityId(long accessibilityNodeId, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, Bundle args) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                try {
                    callback.setFindAccessibilityNodeInfosResult((List<AccessibilityNodeInfo>) null, interactionId);
                } catch (RemoteException e) {
                }
            } else {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfoByAccessibilityIdClientThread(accessibilityNodeId, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, args);
                int i = interactionId;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = callback;
            }
        }

        public void performAccessibilityAction(long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                try {
                    callback.setPerformAccessibilityActionResult(false, interactionId);
                } catch (RemoteException e) {
                }
            } else {
                viewRootImpl.getAccessibilityInteractionController().performAccessibilityActionClientThread(accessibilityNodeId, action, arguments, interactionId, callback, flags, interrogatingPid, interrogatingTid);
                int i = interactionId;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = callback;
            }
        }

        public void findAccessibilityNodeInfosByViewId(long accessibilityNodeId, String viewId, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                try {
                    callback.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, interactionId);
                } catch (RemoteException e) {
                }
            } else {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByViewIdClientThread(accessibilityNodeId, viewId, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                int i = interactionId;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = callback;
            }
        }

        public void findAccessibilityNodeInfosByText(long accessibilityNodeId, String text, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                try {
                    callback.setFindAccessibilityNodeInfosResult((List<AccessibilityNodeInfo>) null, interactionId);
                } catch (RemoteException e) {
                }
            } else {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByTextClientThread(accessibilityNodeId, text, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                int i = interactionId;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = callback;
            }
        }

        public void findFocus(long accessibilityNodeId, int focusType, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                try {
                    callback.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, interactionId);
                } catch (RemoteException e) {
                }
            } else {
                viewRootImpl.getAccessibilityInteractionController().findFocusClientThread(accessibilityNodeId, focusType, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                int i = interactionId;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = callback;
            }
        }

        public void focusSearch(long accessibilityNodeId, int direction, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl == null || viewRootImpl.mView == null) {
                try {
                    callback.setFindAccessibilityNodeInfoResult((AccessibilityNodeInfo) null, interactionId);
                } catch (RemoteException e) {
                }
            } else {
                viewRootImpl.getAccessibilityInteractionController().focusSearchClientThread(accessibilityNodeId, direction, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                int i = interactionId;
                IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = callback;
            }
        }

        public void clearAccessibilityFocus() {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().clearAccessibilityFocusClientThread();
            }
        }

        public void notifyOutsideTouch() {
            ViewRootImpl viewRootImpl = (ViewRootImpl) this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().notifyOutsideTouchClientThread();
            }
        }
    }

    private class SendWindowContentChangedAccessibilityEvent implements Runnable {
        private int mChangeTypes;
        public long mLastEventTimeMillis;
        public StackTraceElement[] mOrigin;
        public View mSource;

        private SendWindowContentChangedAccessibilityEvent() {
            this.mChangeTypes = 0;
        }

        public void run() {
            View source = this.mSource;
            this.mSource = null;
            if (source == null) {
                Log.e(ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (AccessibilityManager.getInstance(ViewRootImpl.this.mContext).isEnabled()) {
                this.mLastEventTimeMillis = SystemClock.uptimeMillis();
                AccessibilityEvent event = AccessibilityEvent.obtain();
                event.setEventType(2048);
                event.setContentChangeTypes(this.mChangeTypes);
                source.sendAccessibilityEventUnchecked(event);
            } else {
                this.mLastEventTimeMillis = 0;
            }
            source.resetSubtreeAccessibilityStateChanged();
            this.mChangeTypes = 0;
        }

        public void runOrPost(View source, int changeType) {
            if (ViewRootImpl.this.mHandler.getLooper() != Looper.myLooper()) {
                Log.e(ViewRootImpl.TAG, "Accessibility content change on non-UI thread. Future Android versions will throw an exception.", new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views."));
                ViewRootImpl.this.mHandler.removeCallbacks(this);
                if (this.mSource != null) {
                    run();
                }
            }
            if (this.mSource != null) {
                View predecessor = ViewRootImpl.this.getCommonPredecessor(this.mSource, source);
                if (predecessor != null) {
                    predecessor = predecessor.getSelfOrParentImportantForA11y();
                }
                this.mSource = predecessor != null ? predecessor : source;
                this.mChangeTypes |= changeType;
                return;
            }
            this.mSource = source;
            this.mChangeTypes = changeType;
            long timeSinceLastMillis = SystemClock.uptimeMillis() - this.mLastEventTimeMillis;
            long minEventIntevalMillis = ViewConfiguration.getSendRecurringAccessibilityEventsInterval();
            if (timeSinceLastMillis >= minEventIntevalMillis) {
                removeCallbacksAndRun();
            } else {
                ViewRootImpl.this.mHandler.postDelayed(this, minEventIntevalMillis - timeSinceLastMillis);
            }
        }

        public void removeCallbacksAndRun() {
            ViewRootImpl.this.mHandler.removeCallbacks(this);
            run();
        }
    }

    private static class UnhandledKeyManager {
        private final SparseArray<WeakReference<View>> mCapturedKeys;
        private WeakReference<View> mCurrentReceiver;
        private boolean mDispatched;

        private UnhandledKeyManager() {
            this.mDispatched = true;
            this.mCapturedKeys = new SparseArray<>();
            this.mCurrentReceiver = null;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public boolean dispatch(View root, KeyEvent event) {
            if (this.mDispatched) {
                return false;
            }
            try {
                Trace.traceBegin(8, "UnhandledKeyEvent dispatch");
                this.mDispatched = true;
                View consumer = root.dispatchUnhandledKeyEvent(event);
                if (event.getAction() == 0) {
                    int keycode = event.getKeyCode();
                    if (consumer != null && !KeyEvent.isModifierKey(keycode)) {
                        this.mCapturedKeys.put(keycode, new WeakReference(consumer));
                    }
                }
                Trace.traceEnd(8);
                if (consumer != null) {
                    return true;
                }
                return false;
            } catch (Throwable th) {
                Trace.traceEnd(8);
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void preDispatch(KeyEvent event) {
            int idx;
            this.mCurrentReceiver = null;
            if (event.getAction() == 1 && (idx = this.mCapturedKeys.indexOfKey(event.getKeyCode())) >= 0) {
                this.mCurrentReceiver = this.mCapturedKeys.valueAt(idx);
                this.mCapturedKeys.removeAt(idx);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean preViewDispatch(KeyEvent event) {
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(event.getKeyCode());
            }
            if (this.mCurrentReceiver == null) {
                return false;
            }
            View target = (View) this.mCurrentReceiver.get();
            if (event.getAction() == 1) {
                this.mCurrentReceiver = null;
            }
            if (target != null && target.isAttachedToWindow()) {
                target.onUnhandledKeyEvent(event);
            }
            return true;
        }
    }
}
