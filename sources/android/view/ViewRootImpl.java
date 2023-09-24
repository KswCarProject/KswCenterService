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
import android.graphics.PixelFormat;
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
import android.media.TtmlUtils;
import android.net.TrafficStats;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Debug;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.ParcelFileDescriptor;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.SystemClock;
import android.p007os.SystemProperties;
import android.p007os.Trace;
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
import com.android.internal.C3132R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.p016os.IResultReceiver;
import com.android.internal.p016os.SomeArgs;
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

/* loaded from: classes4.dex */
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
    private static boolean sAlwaysAssignFocus;
    View mAccessibilityFocusedHost;
    AccessibilityNodeInfo mAccessibilityFocusedVirtualView;
    AccessibilityInteractionController mAccessibilityInteractionController;
    final AccessibilityManager mAccessibilityManager;
    private ActivityConfigCallback mActivityConfigCallback;
    private boolean mActivityRelaunched;
    @UnsupportedAppUsage
    boolean mAdded;
    boolean mAddedTouchMode;
    private boolean mAppVisibilityChanged;
    boolean mApplyInsetsRequested;
    @UnsupportedAppUsage
    final View.AttachInfo mAttachInfo;
    AudioManager mAudioManager;
    final String mBasePackageName;
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
    private final int mDensity;
    @UnsupportedAppUsage
    Rect mDirty;
    Display mDisplay;
    private final DisplayManager.DisplayListener mDisplayListener;
    final DisplayManager mDisplayManager;
    ClipDescription mDragDescription;
    private boolean mDragResizing;
    boolean mDrawingAllowed;
    int mDrawsNeededToReport;
    @UnsupportedAppUsage
    FallbackEventHandler mFallbackEventHandler;
    boolean mFirst;
    InputStage mFirstInputStage;
    InputStage mFirstPostImeInputStage;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private int mFpsNumFrames;
    boolean mFullRedrawNeeded;
    private final GestureExclusionTracker mGestureExclusionTracker;
    boolean mHadWindowFocus;
    final ViewRootHandler mHandler;
    int mHardwareXOffset;
    int mHardwareYOffset;
    boolean mHasHadWindowFocus;
    boolean mHaveMoveEvent;
    @UnsupportedAppUsage
    int mHeight;
    final HighContrastTextManager mHighContrastTextManager;
    InputChannel mInputChannel;
    private final InputEventCompatProcessor mInputCompatProcessor;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    WindowInputEventReceiver mInputEventReceiver;
    InputQueue mInputQueue;
    InputQueue.Callback mInputQueueCallback;
    private final InsetsController mInsetsController;
    final InvalidateOnAnimationRunnable mInvalidateOnAnimationRunnable;
    private boolean mInvalidateRootRequested;
    public boolean mIsAnimating;
    boolean mIsCreating;
    boolean mIsDrawing;
    boolean mIsInTraversal;
    boolean mLastOverscanRequested;
    @UnsupportedAppUsage
    WeakReference<View> mLastScrolledFocus;
    int mLastSystemUiVisibility;
    int mLastTouchSource;
    boolean mLastWasImTarget;
    private WindowInsets mLastWindowInsets;
    boolean mLayoutRequested;
    volatile Object mLocalDragState;
    final WindowLeaked mLocation;
    boolean mLostWindowFocus;
    private boolean mNeedsRendererSetup;
    boolean mNewSurfaceNeeded;
    private final int mNoncompatDensity;
    boolean mPendingAlwaysConsumeSystemBars;
    int mPendingInputEventCount;
    QueuedInputEvent mPendingInputEventHead;
    QueuedInputEvent mPendingInputEventTail;
    private ArrayList<LayoutTransition> mPendingTransitions;
    boolean mPointerCapture;
    final Region mPreviousTransparentRegion;
    boolean mProcessInputEventsScheduled;
    private boolean mProfile;
    private boolean mProfileRendering;
    private QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private boolean mRemoved;
    private Choreographer.FrameCallback mRenderProfiler;
    private boolean mRenderProfilingEnabled;
    boolean mReportNextDraw;
    private int mResizeMode;
    boolean mScrollMayChange;
    int mScrollY;
    Scroller mScroller;
    SendWindowContentChangedAccessibilityEvent mSendWindowContentChangedAccessibilityEvent;
    int mSeq;
    int mSoftInputMode;
    BaseSurfaceHolder mSurfaceHolder;
    SurfaceHolder.Callback2 mSurfaceHolderCallback;
    private SurfaceSession mSurfaceSession;
    InputStage mSyntheticInputStage;
    private String mTag;
    final int mTargetSdkVersion;
    HashSet<View> mTempHashSet;
    final Rect mTempRect;
    final Thread mThread;
    CompatibilityInfo.Translator mTranslator;
    final Region mTransparentRegion;
    int mTraversalBarrier;
    final TraversalRunnable mTraversalRunnable;
    public boolean mTraversalScheduled;
    boolean mUnbufferedInputDispatch;
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
    final BinderC2750W mWindow;
    CountDownLatch mWindowDrawCountDown;
    @GuardedBy({"this"})
    boolean mWindowFocusChanged;
    @UnsupportedAppUsage
    final IWindowSession mWindowSession;
    private final ArrayList<WindowStoppedCallback> mWindowStoppedCallbacks;
    private static final String USE_NEW_INSETS_PROPERTY = "persist.wm.new_insets";
    public static int sNewInsetsMode = SystemProperties.getInt(USE_NEW_INSETS_PROPERTY, 0);
    @UnsupportedAppUsage
    static final ThreadLocal<HandlerActionQueue> sRunQueues = new ThreadLocal<>();
    static final ArrayList<Runnable> sFirstDrawHandlers = new ArrayList<>();
    static boolean sFirstDrawComplete = false;
    private static final ArrayList<ConfigChangedCallback> sConfigCallbacks = new ArrayList<>();
    private static boolean sCompatibilityDone = false;
    static final Interpolator mResizeInterpolator = new AccelerateDecelerateInterpolator();
    @GuardedBy({"mWindowCallbacks"})
    final ArrayList<WindowCallbacks> mWindowCallbacks = new ArrayList<>();
    final int[] mTmpLocation = new int[2];
    final TypedValue mTmpValue = new TypedValue();
    public final WindowManager.LayoutParams mWindowAttributes = new WindowManager.LayoutParams();
    boolean mAppVisible = true;
    private boolean mForceDecorViewVisibility = false;
    int mOrigWindowType = -1;
    @UnsupportedAppUsage
    boolean mStopped = false;
    boolean mIsAmbientMode = false;
    boolean mPausedForTransition = false;
    boolean mLastInCompatMode = false;
    private final Rect mTempBoundsRect = new Rect();
    String mPendingInputEventQueueLengthCounterName = "pq";
    private final UnhandledKeyManager mUnhandledKeyManager = new UnhandledKeyManager();
    boolean mWindowAttributesChanged = false;
    int mWindowAttributesChangesFlag = 0;
    @UnsupportedAppUsage
    public final Surface mSurface = new Surface();
    private final SurfaceControl mSurfaceControl = new SurfaceControl();
    public final Surface mBoundsSurface = new Surface();
    private final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    final Rect mTmpFrame = new Rect();
    final Rect mPendingOverscanInsets = new Rect();
    final Rect mPendingVisibleInsets = new Rect();
    final Rect mPendingStableInsets = new Rect();
    final Rect mPendingContentInsets = new Rect();
    final Rect mPendingOutsets = new Rect();
    final Rect mPendingBackDropFrame = new Rect();
    final DisplayCutout.ParcelableWrapper mPendingDisplayCutout = new DisplayCutout.ParcelableWrapper(DisplayCutout.NO_CUTOUT);
    private InsetsState mTempInsets = new InsetsState();
    final ViewTreeObserver.InternalInsetsInfo mLastGivenInsets = new ViewTreeObserver.InternalInsetsInfo();
    final Rect mDispatchContentInsets = new Rect();
    final Rect mDispatchStableInsets = new Rect();
    DisplayCutout mDispatchDisplayCutout = DisplayCutout.NO_CUTOUT;
    private final Configuration mLastConfigurationFromResources = new Configuration();
    private final MergedConfiguration mLastReportedMergedConfiguration = new MergedConfiguration();
    private final MergedConfiguration mPendingMergedConfiguration = new MergedConfiguration();
    final PointF mDragPoint = new PointF();
    final PointF mLastTouchPoint = new PointF();
    private long mFpsStartTime = -1;
    private long mFpsPrevTime = -1;
    private int mPointerIconType = 1;
    private PointerIcon mCustomPointerIcon = null;
    final AccessibilityInteractionConnectionManager mAccessibilityInteractionConnectionManager = new AccessibilityInteractionConnectionManager();
    private boolean mInLayout = false;
    ArrayList<View> mLayoutRequesters = new ArrayList<>();
    boolean mHandlingLayoutInLayoutRequest = false;

    /* loaded from: classes4.dex */
    public interface ActivityConfigCallback {
        void onConfigurationChanged(Configuration configuration, int i);
    }

    /* loaded from: classes4.dex */
    public interface ConfigChangedCallback {
        void onConfigurationChanged(Configuration configuration);
    }

    /* loaded from: classes4.dex */
    interface WindowStoppedCallback {
        void windowStopped(boolean z);
    }

    /* loaded from: classes4.dex */
    static final class SystemUiVisibilityInfo {
        int globalVisibility;
        int localChanges;
        int localValue;
        int seq;

        SystemUiVisibilityInfo() {
        }
    }

    public ViewRootImpl(Context context, Display display) {
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mInsetsController = new InsetsController(this);
        this.mGestureExclusionTracker = new GestureExclusionTracker();
        this.mTag = TAG;
        this.mHaveMoveEvent = false;
        this.mProfile = false;
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: android.view.ViewRootImpl.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
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

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int displayId) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int displayId) {
            }

            private int toViewScreenState(int displayState) {
                if (displayState != 1) {
                    return 1;
                }
                return 0;
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
        this.mLocation = new WindowLeaked(null);
        this.mLocation.fillInStackTrace();
        this.mWidth = -1;
        this.mHeight = -1;
        this.mDirty = new Rect();
        this.mTempRect = new Rect();
        this.mVisRect = new Rect();
        this.mWinFrame = new Rect();
        this.mWindow = new BinderC2750W(this);
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
        String processorOverrideName = context.getResources().getString(C3132R.string.config_inputEventCompatProcessorOverrideClassName);
        if (processorOverrideName.isEmpty()) {
            this.mInputCompatProcessor = new InputEventCompatProcessor(context);
        } else {
            InputEventCompatProcessor compatProcessor = null;
            try {
                try {
                    compatProcessor = (InputEventCompatProcessor) Class.forName(processorOverrideName).getConstructor(Context.class).newInstance(context);
                } catch (Exception e) {
                    Log.m69e(TAG, "Unable to create the InputEventCompatProcessor. ", e);
                }
            } finally {
                this.mInputCompatProcessor = compatProcessor;
            }
        }
        if (!sCompatibilityDone) {
            sAlwaysAssignFocus = this.mTargetSdkVersion < 28;
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
        if (windowSession != null) {
            try {
                return windowSession.getInTouchMode();
            } catch (RemoteException e) {
                return false;
            }
        }
        return false;
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

    public void setView(View view, WindowManager.LayoutParams attrs, View panelParentView) {
        synchronized (this) {
            try {
                try {
                    if (this.mView == null) {
                        this.mView = view;
                        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
                        this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mHandler);
                        this.mViewLayoutDirectionInitial = this.mView.getRawLayoutDirection();
                        this.mFallbackEventHandler.setView(view);
                        this.mWindowAttributes.copyFrom(attrs);
                        if (this.mWindowAttributes.packageName == null) {
                            this.mWindowAttributes.packageName = this.mBasePackageName;
                        }
                        WindowManager.LayoutParams attrs2 = this.mWindowAttributes;
                        setTag();
                        this.mClientWindowLayoutFlags = attrs2.flags;
                        setAccessibilityFocus(null, null);
                        if (view instanceof RootViewSurfaceTaker) {
                            this.mSurfaceHolderCallback = ((RootViewSurfaceTaker) view).willYouTakeTheSurface();
                            if (this.mSurfaceHolderCallback != null) {
                                this.mSurfaceHolder = new TakenSurfaceHolder();
                                this.mSurfaceHolder.setFormat(0);
                                this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
                            }
                        }
                        if (!attrs2.hasManualSurfaceInsets) {
                            attrs2.setSurfaceInsets(view, false, true);
                        }
                        CompatibilityInfo compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
                        this.mTranslator = compatibilityInfo.getTranslator();
                        if (this.mSurfaceHolder == null) {
                            enableHardwareAcceleration(attrs2);
                            boolean useMTRenderer = this.mAttachInfo.mThreadedRenderer != null;
                            if (this.mUseMTRenderer != useMTRenderer) {
                                endDragResizing();
                                this.mUseMTRenderer = useMTRenderer;
                            }
                        }
                        boolean restore = false;
                        if (this.mTranslator != null) {
                            this.mSurface.setCompatibilityTranslator(this.mTranslator);
                            restore = true;
                            attrs2.backup();
                            this.mTranslator.translateWindowLayout(attrs2);
                        }
                        boolean restore2 = restore;
                        boolean restore3 = compatibilityInfo.supportsScreen();
                        if (!restore3) {
                            attrs2.privateFlags |= 128;
                            this.mLastInCompatMode = true;
                        }
                        this.mSoftInputMode = attrs2.softInputMode;
                        this.mWindowAttributesChanged = true;
                        this.mWindowAttributesChangesFlag = -1;
                        this.mAttachInfo.mRootView = view;
                        this.mAttachInfo.mScalingRequired = this.mTranslator != null;
                        this.mAttachInfo.mApplicationScale = this.mTranslator == null ? 1.0f : this.mTranslator.applicationScale;
                        if (panelParentView != null) {
                            this.mAttachInfo.mPanelParentWindowToken = panelParentView.getApplicationWindowToken();
                        }
                        this.mAdded = true;
                        requestLayout();
                        if ((this.mWindowAttributes.inputFeatures & 2) == 0) {
                            this.mInputChannel = new InputChannel();
                        }
                        this.mForceDecorViewVisibility = (this.mWindowAttributes.privateFlags & 16384) != 0;
                        try {
                            this.mOrigWindowType = this.mWindowAttributes.type;
                            this.mAttachInfo.mRecomputeGlobalAttributes = true;
                            collectViewAttributes();
                            try {
                                try {
                                    try {
                                        int res = this.mWindowSession.addToDisplay(this.mWindow, this.mSeq, this.mWindowAttributes, getHostVisibility(), this.mDisplay.getDisplayId(), this.mTmpFrame, this.mAttachInfo.mContentInsets, this.mAttachInfo.mStableInsets, this.mAttachInfo.mOutsets, this.mAttachInfo.mDisplayCutout, this.mInputChannel, this.mTempInsets);
                                        setFrame(this.mTmpFrame);
                                        if (restore2) {
                                            try {
                                                attrs2.restore();
                                            } catch (Throwable th) {
                                                th = th;
                                                throw th;
                                            }
                                        }
                                        if (this.mTranslator != null) {
                                            this.mTranslator.translateRectInScreenToAppWindow(this.mAttachInfo.mContentInsets);
                                        }
                                        this.mPendingOverscanInsets.set(0, 0, 0, 0);
                                        this.mPendingContentInsets.set(this.mAttachInfo.mContentInsets);
                                        this.mPendingStableInsets.set(this.mAttachInfo.mStableInsets);
                                        this.mPendingDisplayCutout.set(this.mAttachInfo.mDisplayCutout);
                                        this.mPendingVisibleInsets.set(0, 0, 0, 0);
                                        this.mAttachInfo.mAlwaysConsumeSystemBars = (res & 4) != 0;
                                        this.mPendingAlwaysConsumeSystemBars = this.mAttachInfo.mAlwaysConsumeSystemBars;
                                        this.mInsetsController.onStateChanged(this.mTempInsets);
                                        if (res < 0) {
                                            this.mAttachInfo.mRootView = null;
                                            this.mAdded = false;
                                            this.mFallbackEventHandler.setView(null);
                                            unscheduleTraversals();
                                            setAccessibilityFocus(null, null);
                                            switch (res) {
                                                case -10:
                                                    throw new WindowManager.InvalidDisplayException("Unable to add window " + this.mWindow + " -- the specified window type " + this.mWindowAttributes.type + " is not valid");
                                                case -9:
                                                    throw new WindowManager.InvalidDisplayException("Unable to add window " + this.mWindow + " -- the specified display can not be found");
                                                case -8:
                                                    throw new WindowManager.BadTokenException("Unable to add window " + this.mWindow + " -- permission denied for window type " + this.mWindowAttributes.type);
                                                case -7:
                                                    throw new WindowManager.BadTokenException("Unable to add window " + this.mWindow + " -- another window of type " + this.mWindowAttributes.type + " already exists");
                                                case -6:
                                                    return;
                                                case -5:
                                                    throw new WindowManager.BadTokenException("Unable to add window -- window " + this.mWindow + " has already been added");
                                                case -4:
                                                    throw new WindowManager.BadTokenException("Unable to add window -- app for token " + attrs2.token + " is exiting");
                                                case -3:
                                                    throw new WindowManager.BadTokenException("Unable to add window -- token " + attrs2.token + " is not for an application");
                                                case -2:
                                                case -1:
                                                    throw new WindowManager.BadTokenException("Unable to add window -- token " + attrs2.token + " is not valid; is your activity running?");
                                                default:
                                                    throw new RuntimeException("Unable to add window -- unknown error code " + res);
                                            }
                                        }
                                        if (view instanceof RootViewSurfaceTaker) {
                                            this.mInputQueueCallback = ((RootViewSurfaceTaker) view).willYouTakeTheInputQueue();
                                        }
                                        if (this.mInputChannel != null) {
                                            if (this.mInputQueueCallback != null) {
                                                this.mInputQueue = new InputQueue();
                                                this.mInputQueueCallback.onInputQueueCreated(this.mInputQueue);
                                            }
                                            this.mInputEventReceiver = new WindowInputEventReceiver(this.mInputChannel, Looper.myLooper());
                                        }
                                        view.assignParent(this);
                                        this.mAddedTouchMode = (res & 1) != 0;
                                        this.mAppVisible = (res & 2) != 0;
                                        if (this.mAccessibilityManager.isEnabled()) {
                                            this.mAccessibilityInteractionConnectionManager.ensureConnection();
                                        }
                                        if (view.getImportantForAccessibility() == 0) {
                                            view.setImportantForAccessibility(1);
                                        }
                                        CharSequence counterSuffix = attrs2.getTitle();
                                        this.mSyntheticInputStage = new SyntheticInputStage();
                                        InputStage viewPostImeStage = new ViewPostImeInputStage(this.mSyntheticInputStage);
                                        InputStage nativePostImeStage = new NativePostImeInputStage(viewPostImeStage, "aq:native-post-ime:" + ((Object) counterSuffix));
                                        InputStage earlyPostImeStage = new EarlyPostImeInputStage(nativePostImeStage);
                                        InputStage imeStage = new ImeInputStage(earlyPostImeStage, "aq:ime:" + ((Object) counterSuffix));
                                        InputStage viewPreImeStage = new ViewPreImeInputStage(imeStage);
                                        InputStage nativePreImeStage = new NativePreImeInputStage(viewPreImeStage, "aq:native-pre-ime:" + ((Object) counterSuffix));
                                        this.mFirstInputStage = nativePreImeStage;
                                        this.mFirstPostImeInputStage = earlyPostImeStage;
                                        this.mPendingInputEventQueueLengthCounterName = "aq:pending:" + ((Object) counterSuffix);
                                    } catch (RemoteException e) {
                                        e = e;
                                        this.mAdded = false;
                                        this.mView = null;
                                        this.mAttachInfo.mRootView = null;
                                        this.mInputChannel = null;
                                        this.mFallbackEventHandler.setView(null);
                                        unscheduleTraversals();
                                        setAccessibilityFocus(null, null);
                                        throw new RuntimeException("Adding window failed", e);
                                    } catch (Throwable th2) {
                                        e = th2;
                                        if (restore2) {
                                            attrs2.restore();
                                        }
                                        throw e;
                                    }
                                } catch (Throwable th3) {
                                    e = th3;
                                }
                            } catch (RemoteException e2) {
                                e = e2;
                            }
                        } catch (RemoteException e3) {
                            e = e3;
                        } catch (Throwable th4) {
                            e = th4;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        }
    }

    private void setTag() {
        String[] split = this.mWindowAttributes.getTitle().toString().split("\\.");
        if (split.length > 0) {
            this.mTag = "ViewRootImpl[" + split[split.length - 1] + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    void destroyHardwareResources() {
        ThreadedRenderer renderer = this.mAttachInfo.mThreadedRenderer;
        if (renderer != null) {
            if (Looper.myLooper() != this.mAttachInfo.mHandler.getLooper()) {
                this.mAttachInfo.mHandler.postAtFrontOfQueue(new Runnable() { // from class: android.view.-$$Lambda$dj1hfDQd0iEp_uBDBPEUMMYJJwk
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewRootImpl.this.destroyHardwareResources();
                    }
                });
                return;
            }
            renderer.destroyHardwareResources(this.mView);
            renderer.destroy();
        }
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

    public void registerRtFrameCallback(final HardwareRenderer.FrameDrawingCallback callback) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: android.view.-$$Lambda$ViewRootImpl$IReiNMSbDakZSGbIZuL_ifaFWn8
                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
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
            Log.m69e(TAG, "Exception while executing onFrameDraw", e);
        }
    }

    @UnsupportedAppUsage
    private void enableHardwareAcceleration(WindowManager.LayoutParams attrs) {
        boolean wideGamut = false;
        this.mAttachInfo.mHardwareAccelerated = false;
        this.mAttachInfo.mHardwareAccelerationRequested = false;
        if (this.mTranslator != null) {
            return;
        }
        boolean hardwareAccelerated = (attrs.flags & 16777216) != 0;
        if (!hardwareAccelerated || !ThreadedRenderer.isAvailable()) {
            return;
        }
        boolean fakeHwAccelerated = (attrs.privateFlags & 1) != 0;
        boolean forceHwAccelerated = (attrs.privateFlags & 2) != 0;
        if (fakeHwAccelerated) {
            this.mAttachInfo.mHardwareAccelerationRequested = true;
        } else if (!ThreadedRenderer.sRendererDisabled || (ThreadedRenderer.sSystemRendererDisabled && forceHwAccelerated)) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.destroy();
            }
            Rect insets = attrs.surfaceInsets;
            boolean hasSurfaceInsets = (insets.left == 0 && insets.right == 0 && insets.top == 0 && insets.bottom == 0) ? false : true;
            boolean translucent = attrs.format != -1 || hasSurfaceInsets;
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

    private int getNightMode() {
        return this.mContext.getResources().getConfiguration().uiMode & 48;
    }

    private void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        boolean z = false;
        boolean useAutoDark = getNightMode() == 32;
        if (useAutoDark) {
            boolean forceDarkAllowedDefault = SystemProperties.getBoolean(ThreadedRenderer.DEBUG_FORCE_DARK, false);
            TypedArray a = this.mContext.obtainStyledAttributes(C3132R.styleable.Theme);
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

    @UnsupportedAppUsage
    public View getView() {
        return this.mView;
    }

    final WindowLeaked getLocation() {
        return this.mLocation;
    }

    void setLayoutParams(WindowManager.LayoutParams attrs, boolean newView) {
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
            } else if (this.mWindowAttributes.surfaceInsets.left != oldInsetLeft || this.mWindowAttributes.surfaceInsets.top != oldInsetTop || this.mWindowAttributes.surfaceInsets.right != oldInsetRight || this.mWindowAttributes.surfaceInsets.bottom != oldInsetBottom) {
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

    void handleAppVisibility(boolean visible) {
        if (this.mAppVisible != visible) {
            this.mAppVisible = visible;
            this.mAppVisibilityChanged = true;
            scheduleTraversals();
            if (!this.mAppVisible) {
                WindowManagerGlobal.trimForeground();
            }
        }
    }

    void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        scheduleTraversals();
    }

    public void onMovedToDisplay(int displayId, Configuration config) {
        if (this.mDisplay.getDisplayId() == displayId) {
            return;
        }
        updateInternalDisplay(displayId, this.mView.getResources());
        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
        this.mView.dispatchMovedToDisplay(this.mDisplay, config);
    }

    private void updateInternalDisplay(int displayId, Resources resources) {
        Display preferredDisplay = ResourcesManager.getInstance().getAdjustedDisplay(displayId, resources);
        if (preferredDisplay == null) {
            Slog.m50w(TAG, "Cannot get desired display with Id: " + displayId);
            this.mDisplay = ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = preferredDisplay;
        }
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    void pokeDrawLockIfNeeded() {
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

    @Override // android.view.ViewParent
    public void requestFitSystemWindows() {
        checkThread();
        this.mApplyInsetsRequested = true;
        scheduleTraversals();
    }

    void notifyInsetsChanged() {
        if (sNewInsetsMode == 0) {
            return;
        }
        this.mApplyInsetsRequested = true;
        if (!this.mIsInTraversal) {
            scheduleTraversals();
        }
    }

    @Override // android.view.ViewParent
    public void requestLayout() {
        if (!this.mHandlingLayoutInLayoutRequest) {
            checkThread();
            this.mLayoutRequested = true;
            scheduleTraversals();
        }
    }

    @Override // android.view.ViewParent
    public boolean isLayoutRequested() {
        return this.mLayoutRequested;
    }

    @Override // android.view.ViewParent
    public void onDescendantInvalidated(View child, View descendant) {
        if ((descendant.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        invalidate();
    }

    @UnsupportedAppUsage
    void invalidate() {
        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
        if (!this.mWillDrawSoon) {
            scheduleTraversals();
        }
    }

    void invalidateWorld(View view) {
        view.invalidate();
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            for (int i = 0; i < parent.getChildCount(); i++) {
                invalidateWorld(parent.getChildAt(i));
            }
        }
    }

    @Override // android.view.ViewParent
    public void invalidateChild(View child, Rect dirty) {
        invalidateChildInParent(null, dirty);
    }

    @Override // android.view.ViewParent
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        checkThread();
        if (dirty == null) {
            invalidate();
            return null;
        } else if (dirty.isEmpty() && !this.mIsAnimating) {
            return null;
        } else {
            if (this.mCurScrollY != 0 || this.mTranslator != null) {
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
        boolean intersected = localDirty.intersect(0, 0, (int) ((this.mWidth * appScale) + 0.5f), (int) ((this.mHeight * appScale) + 0.5f));
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

    void addWindowStoppedCallback(WindowStoppedCallback c) {
        this.mWindowStoppedCallbacks.add(c);
    }

    void removeWindowStoppedCallback(WindowStoppedCallback c) {
        this.mWindowStoppedCallbacks.remove(c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
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
            if (this.mStopped) {
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
        if (this.mBoundsSurfaceControl != null && this.mBoundsSurface.isValid()) {
            return;
        }
        SurfaceControl.Builder builder = new SurfaceControl.Builder(this.mSurfaceSession);
        this.mBoundsSurfaceControl = builder.setName("Bounds for - " + getTitle().toString()).setParent(this.mSurfaceControl).build();
        setBoundsSurfaceCrop();
        this.mTransaction.setLayer(this.mBoundsSurfaceControl, zOrderLayer).show(this.mBoundsSurfaceControl).apply();
        this.mBoundsSurface.copyFrom(this.mBoundsSurfaceControl);
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

    @Override // android.view.ViewParent
    public ViewParent getParent() {
        return null;
    }

    @Override // android.view.ViewParent
    public boolean getChildVisibleRect(View child, Rect r, Point offset) {
        if (child != this.mView) {
            throw new RuntimeException("child is not mine, honest!");
        }
        return r.intersect(0, 0, this.mWidth, this.mHeight);
    }

    @Override // android.view.ViewParent
    public void bringChildToFront(View child) {
    }

    int getHostVisibility() {
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

    void notifyRendererOfFramePending() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyFramePending();
        }
    }

    @UnsupportedAppUsage
    void scheduleTraversals() {
        if (!this.mTraversalScheduled) {
            this.mTraversalScheduled = true;
            this.mTraversalBarrier = this.mHandler.getLooper().getQueue().postSyncBarrier();
            this.mChoreographer.postCallback(3, this.mTraversalRunnable, null);
            if (!this.mUnbufferedInputDispatch) {
                scheduleConsumeBatchedInput();
            }
            notifyRendererOfFramePending();
            pokeDrawLockIfNeeded();
        }
    }

    void unscheduleTraversals() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            this.mChoreographer.removeCallbacks(3, this.mTraversalRunnable, null);
        }
    }

    void doTraversal() {
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
            params.flags = (params.flags & (-129)) | (this.mClientWindowLayoutFlags & 128);
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
            if (this.mAttachInfo.mKeepScreenOn != oldScreenOn || this.mAttachInfo.mSystemUiVisibility != params.subtreeSystemUiVisibility || this.mAttachInfo.mHasSystemUiListeners != params.hasSystemUiListeners) {
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
            res.getValue(C3132R.dimen.config_prefDialogWidth, this.mTmpValue, true);
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
                    int baseSize2 = (baseSize + desiredWindowWidth) / 2;
                    int baseSize3 = lp.width;
                    int childWidthMeasureSpec2 = getRootMeasureSpec(baseSize2, baseSize3);
                    performMeasure(childWidthMeasureSpec2, childHeightMeasureSpec);
                    if ((host.getMeasuredWidthAndState() & 16777216) == 0) {
                        goodMeasure = true;
                    }
                }
            }
        }
        if (goodMeasure) {
            return false;
        }
        int childWidthMeasureSpec3 = getRootMeasureSpec(desiredWindowWidth, lp.width);
        performMeasure(childWidthMeasureSpec3, getRootMeasureSpec(desiredWindowHeight, lp.height));
        if (this.mWidth == host.getMeasuredWidth() && this.mHeight == host.getMeasuredHeight()) {
            return false;
        }
        return true;
    }

    void transformMatrixToGlobal(Matrix m) {
        m.preTranslate(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    void transformMatrixToLocal(Matrix m) {
        m.postTranslate(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
    }

    WindowInsets getWindowInsets(boolean forceConstruct) {
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
            this.mLastWindowInsets = this.mInsetsController.calculateInsets(this.mContext.getResources().getConfiguration().isScreenRound(), this.mAttachInfo.mAlwaysConsumeSystemBars, displayCutout2, ensureInsetsNonNegative(contentInsets, "content"), ensureInsetsNonNegative(stableInsets, "stable"), this.mWindowAttributes.softInputMode);
        }
        return this.mLastWindowInsets;
    }

    private Rect ensureInsetsNonNegative(Rect insets, String kind) {
        if (insets.left < 0 || insets.top < 0 || insets.right < 0 || insets.bottom < 0) {
            String str = this.mTag;
            Log.wtf(str, "Negative " + kind + "Insets: " + insets + ", mFirst=" + this.mFirst);
            return new Rect(Math.max(0, insets.left), Math.max(0, insets.top), Math.max(0, insets.right), Math.max(0, insets.bottom));
        }
        return insets;
    }

    void dispatchApplyInsets(View host) {
        Trace.traceBegin(8L, "dispatchApplyInsets");
        WindowInsets insets = getWindowInsets(true);
        boolean dispatchCutout = this.mWindowAttributes.layoutInDisplayCutoutMode == 1;
        if (!dispatchCutout) {
            insets = insets.consumeDisplayCutout();
        }
        host.dispatchApplyWindowInsets(insets);
        Trace.traceEnd(8L);
    }

    InsetsController getInsetsController() {
        return this.mInsetsController;
    }

    private static boolean shouldUseDisplaySize(WindowManager.LayoutParams lp) {
        return lp.type == 2014 || lp.type == 2011 || lp.type == 2020;
    }

    private int dipToPx(int dip) {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        return (int) ((displayMetrics.density * dip) + 0.5f);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(149:5|(1:650)(1:13)|14|(4:16|(1:18)(1:648)|(1:20)(1:647)|(142:22|23|(1:25)|26|(2:28|(1:30)(1:31))|32|(5:34|(1:36)(1:641)|37|(1:39)|40)(2:642|(1:646))|(5:42|(2:(1:45)(1:47)|46)|(1:54)|51|(1:53))|55|(1:57)|58|(1:640)(1:64)|65|(4:67|(1:69)(16:610|(1:612)|613|(1:615)|616|(1:618)|619|(1:621)|622|(1:624)|625|(1:627)|628|(1:630)|631|(2:635|(1:637)(1:638)))|70|71)(1:639)|72|(1:74)|75|(1:77)|78|(2:594|(6:596|(3:598|(2:600|601)(1:603)|602)|604|(1:606)|607|(1:609)))|82|(5:84|(1:88)|89|(1:91)(1:93)|92)|94|(2:96|(118:98|99|(1:101)|(115:104|(1:591)(2:108|(2:584|(99:586|(1:588)|590|116|(1:583)(1:120)|121|(1:582)(1:125)|126|(1:128)(1:581)|129|(1:580)(2:134|(2:136|(1:138)(36:578|(1:266)|(1:403)(1:272)|273|(1:402)(1:277)|278|(2:280|(25:282|(1:284)|285|(3:287|288|289)|(1:293)|(2:295|(5:299|(1:301)(1:307)|302|303|304))|308|(2:310|(2:319|(1:323))(2:314|(1:318)))|(1:400)(1:327)|328|(1:398)(1:331)|(1:397)(1:335)|(1:337)(1:(1:396))|(3:385|(1:392)(1:387)|(1:389))|340|(2:346|(9:349|350|(1:352)|353|(1:356)|357|(3:359|(4:363|(2:366|364)|367|368)|369)(1:(1:373)(2:374|(4:378|(2:381|379)|382|383)))|370|371))|384|350|(0)|353|(1:356)|357|(0)(0)|370|371))|401|(0)|(0)|308|(0)|(1:325)|400|328|(0)|398|(1:333)|397|(0)(0)|(0)|385|(14:390|392|(0)|340|(4:342|344|346|(9:349|350|(0)|353|(0)|357|(0)(0)|370|371))|384|350|(0)|353|(0)|357|(0)(0)|370|371)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371))(1:579))|139|(2:(1:576)(1:145)|146)(1:577)|147|(1:149)(1:575)|150|151|152|(4:545|546|(10:548|549|550|551|552|553|554|555|556|557)(1:569)|558)(1:154)|155|156|157|158|159|(3:537|538|539)|161|(1:163)(1:536)|164|(1:166)(1:535)|167|168|(1:170)|(1:172)|(1:174)|(1:176)|(1:178)|(2:533|534)|(1:188)|(3:192|(1:194)(1:196)|195)|(2:198|(3:200|201|(5:203|204|205|(3:208|209|(1:211))|207)))(2:499|(8:501|(1:503)|504|(1:506)|507|(1:509)|510|(1:514))(2:515|(3:524|525|526)))|444|(1:446)(1:498)|447|(1:449)(1:497)|450|(1:496)(1:453)|454|455|456|(1:(14:(1:460)(1:492)|461|(1:491)(1:465)|466|(1:468)(1:490)|469|470|471|472|473|474|475|476|477)(1:493))(1:494)|478|(1:(1:481)(1:482))|223|(1:443)|227|(4:229|(1:231)|232|(3:(3:235|(3:237|(1:239)|240)|241)|(3:425|(3:427|(1:429)|430)|431)(1:245)|246)(2:432|(4:434|435|436|437)))(1:442)|247|(1:258)|259|(4:404|(1:406)(1:424)|407|(44:415|(1:417)|418|(1:420)|(1:422)|423|264|(0)|(2:268|270)|403|273|(1:275)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371))|263|264|(0)|(0)|403|273|(0)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371))(1:114))|115|116|(1:118)|583|121|(1:123)|582|126|(0)(0)|129|(0)|580|139|(0)(0)|147|(0)(0)|150|151|152|(0)(0)|155|156|157|158|159|(0)|161|(0)(0)|164|(0)(0)|167|168|(0)|(0)|(0)|(0)|(0)|(1:180)|533|534|(0)|(4:190|192|(0)(0)|195)|(0)(0)|444|(0)(0)|447|(0)(0)|450|(0)|496|454|455|456|(0)(0)|478|(0)|223|(1:225)|443|227|(0)(0)|247|(2:249|258)|259|(1:261)|404|(0)(0)|407|(1:409)|415|(0)|418|(0)|(0)|423|264|(0)|(0)|403|273|(0)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371)|592|590|116|(0)|583|121|(0)|582|126|(0)(0)|129|(0)|580|139|(0)(0)|147|(0)(0)|150|151|152|(0)(0)|155|156|157|158|159|(0)|161|(0)(0)|164|(0)(0)|167|168|(0)|(0)|(0)|(0)|(0)|(0)|533|534|(0)|(0)|(0)(0)|444|(0)(0)|447|(0)(0)|450|(0)|496|454|455|456|(0)(0)|478|(0)|223|(0)|443|227|(0)(0)|247|(0)|259|(0)|404|(0)(0)|407|(0)|415|(0)|418|(0)|(0)|423|264|(0)|(0)|403|273|(0)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371))|593|99|(0)|(116:104|(1:106)|591|115|116|(0)|583|121|(0)|582|126|(0)(0)|129|(0)|580|139|(0)(0)|147|(0)(0)|150|151|152|(0)(0)|155|156|157|158|159|(0)|161|(0)(0)|164|(0)(0)|167|168|(0)|(0)|(0)|(0)|(0)|(0)|533|534|(0)|(0)|(0)(0)|444|(0)(0)|447|(0)(0)|450|(0)|496|454|455|456|(0)(0)|478|(0)|223|(0)|443|227|(0)(0)|247|(0)|259|(0)|404|(0)(0)|407|(0)|415|(0)|418|(0)|(0)|423|264|(0)|(0)|403|273|(0)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371)|592|590|116|(0)|583|121|(0)|582|126|(0)(0)|129|(0)|580|139|(0)(0)|147|(0)(0)|150|151|152|(0)(0)|155|156|157|158|159|(0)|161|(0)(0)|164|(0)(0)|167|168|(0)|(0)|(0)|(0)|(0)|(0)|533|534|(0)|(0)|(0)(0)|444|(0)(0)|447|(0)(0)|450|(0)|496|454|455|456|(0)(0)|478|(0)|223|(0)|443|227|(0)(0)|247|(0)|259|(0)|404|(0)(0)|407|(0)|415|(0)|418|(0)|(0)|423|264|(0)|(0)|403|273|(0)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371))|649|23|(0)|26|(0)|32|(0)(0)|(0)|55|(0)|58|(2:60|62)|640|65|(0)(0)|72|(0)|75|(0)|78|(1:80)|594|(0)|82|(0)|94|(0)|593|99|(0)|(0)|592|590|116|(0)|583|121|(0)|582|126|(0)(0)|129|(0)|580|139|(0)(0)|147|(0)(0)|150|151|152|(0)(0)|155|156|157|158|159|(0)|161|(0)(0)|164|(0)(0)|167|168|(0)|(0)|(0)|(0)|(0)|(0)|533|534|(0)|(0)|(0)(0)|444|(0)(0)|447|(0)(0)|450|(0)|496|454|455|456|(0)(0)|478|(0)|223|(0)|443|227|(0)(0)|247|(0)|259|(0)|404|(0)(0)|407|(0)|415|(0)|418|(0)|(0)|423|264|(0)|(0)|403|273|(0)|402|278|(0)|401|(0)|(0)|308|(0)|(0)|400|328|(0)|398|(0)|397|(0)(0)|(0)|385|(0)|387|(0)|340|(0)|384|350|(0)|353|(0)|357|(0)(0)|370|371) */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x030a, code lost:
        if (r23.height() != r58.mHeight) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x06e1, code lost:
        r44 = r1;
        r46 = r12;
        r12 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x06ec, code lost:
        r44 = r1;
        r12 = r6;
        r46 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x06f7, code lost:
        r44 = r1;
        r30 = false;
        r31 = false;
        r12 = r6;
        r46 = 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02d0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0345  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0351 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04c0  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x04c2  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04d1 A[Catch: RemoteException -> 0x044d, TRY_ENTER, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x04dc A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x04ea A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:278:0x04f8 A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0506 A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0511 A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0547 A[Catch: RemoteException -> 0x044d, TRY_ENTER, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0552 A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0563  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x056c A[Catch: RemoteException -> 0x044d, TryCatch #11 {RemoteException -> 0x044d, blocks: (B:256:0x043f, B:272:0x04d1, B:274:0x04dc, B:276:0x04ea, B:278:0x04f8, B:280:0x0506, B:282:0x0511, B:284:0x0519, B:286:0x051d, B:292:0x0547, B:294:0x0552, B:296:0x0558, B:300:0x0567, B:302:0x056c, B:304:0x0574, B:306:0x0582, B:377:0x0659, B:324:0x05ba, B:326:0x05be, B:327:0x05c3, B:329:0x05ce, B:330:0x05d7, B:332:0x05db, B:333:0x05e0, B:335:0x05e6, B:337:0x05f0, B:346:0x060a, B:348:0x0610, B:349:0x0613, B:352:0x061e), top: B:653:0x043f, inners: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x05b2 A[Catch: RemoteException -> 0x06e0, TRY_ENTER, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x06e0, blocks: (B:254:0x0435, B:261:0x0459, B:265:0x04b4, B:269:0x04c3, B:369:0x063b, B:375:0x0647, B:381:0x066a, B:385:0x0673, B:322:0x05b2, B:339:0x05f8, B:344:0x0606, B:289:0x0527), top: B:633:0x0435 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0626  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0628  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x062d  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x062f  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0634 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:371:0x063f  */
    /* JADX WARN: Removed duplicated region for block: B:397:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x06c9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x0720  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0738  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x07f3  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x07fd  */
    /* JADX WARN: Removed duplicated region for block: B:466:0x082d  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0835  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0837  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x083e  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x087f  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0896  */
    /* JADX WARN: Removed duplicated region for block: B:489:0x08a9  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:493:0x08b5  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x08ba  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x08c8  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x08d5  */
    /* JADX WARN: Removed duplicated region for block: B:524:0x0945  */
    /* JADX WARN: Removed duplicated region for block: B:526:0x0953  */
    /* JADX WARN: Removed duplicated region for block: B:540:0x09bb  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x09f2  */
    /* JADX WARN: Removed duplicated region for block: B:563:0x0a01 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:567:0x0a08  */
    /* JADX WARN: Removed duplicated region for block: B:572:0x0a11  */
    /* JADX WARN: Removed duplicated region for block: B:573:0x0a15  */
    /* JADX WARN: Removed duplicated region for block: B:578:0x0a20 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:582:0x0a28  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0a35  */
    /* JADX WARN: Removed duplicated region for block: B:590:0x0a49  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x0aa0  */
    /* JADX WARN: Removed duplicated region for block: B:604:0x0aad A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:609:0x0ab7  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x0ae3  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x043f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:658:0x03c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x014b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void performTraversals() {
        boolean z;
        CompatibilityInfo compatibilityInfo;
        int desiredWindowWidth;
        int desiredWindowHeight;
        boolean layoutRequested;
        int i;
        Rect frame;
        boolean insetsChanged;
        int desiredWindowHeight2;
        int desiredWindowWidth2;
        int resizeMode;
        WindowManager.LayoutParams params;
        WindowManager.LayoutParams params2;
        int desiredWindowHeight3;
        int desiredWindowHeight4;
        boolean windowShouldResize;
        boolean computesInternalInsets;
        int relayoutResult;
        boolean isViewVisible;
        boolean insetsPending;
        Rect frame2;
        WindowManager.LayoutParams params3;
        boolean surfaceSizeChanged;
        boolean didLayout;
        boolean triggerGlobalLayoutListener;
        boolean changedVisibility;
        boolean hasWindowFocus;
        boolean regainedFocus;
        boolean isToast;
        boolean z2;
        boolean cancelDraw;
        boolean imTarget;
        InputMethodManager imm;
        Rect contentInsets;
        Rect visibleInsets;
        Region region;
        boolean updatedConfiguration;
        boolean contentInsetsChanged;
        boolean hadSurface;
        boolean insetsPending2;
        boolean hwInitialized;
        boolean surfaceSizeChanged2;
        int relayoutResult2;
        int relayoutResult3;
        int relayoutResult4;
        ThreadedRenderer threadedRenderer;
        boolean focusChangedDueToTouchMode;
        boolean measureAgain;
        int relayoutResult5;
        boolean overscanInsetsChanged;
        boolean visibleInsetsChanged;
        boolean stableInsetsChanged;
        boolean cutoutChanged;
        boolean alwaysConsumeSystemBarsChanged;
        boolean colorModeChanged;
        boolean dragResizing;
        boolean dragResizing2;
        View host = this.mView;
        if (host == null || !this.mAdded) {
            return;
        }
        this.mIsInTraversal = true;
        this.mWillDrawSoon = true;
        boolean windowSizeMayChange = false;
        boolean surfaceChanged = false;
        WindowManager.LayoutParams lp = this.mWindowAttributes;
        int viewVisibility = getHostVisibility();
        boolean viewVisibilityChanged = !this.mFirst && (this.mViewVisibility != viewVisibility || this.mNewSurfaceNeeded || this.mAppVisibilityChanged);
        this.mAppVisibilityChanged = false;
        if (!this.mFirst) {
            if ((this.mViewVisibility == 0) != (viewVisibility == 0)) {
                z = true;
                boolean viewUserVisibilityChanged = z;
                WindowManager.LayoutParams params4 = null;
                if (this.mWindowAttributesChanged) {
                    this.mWindowAttributesChanged = false;
                    surfaceChanged = true;
                    params4 = lp;
                }
                boolean surfaceChanged2 = surfaceChanged;
                compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
                if (compatibilityInfo.supportsScreen() == this.mLastInCompatMode) {
                    params4 = lp;
                    this.mFullRedrawNeeded = true;
                    this.mLayoutRequested = true;
                    if (this.mLastInCompatMode) {
                        params4.privateFlags &= -129;
                        this.mLastInCompatMode = false;
                    } else {
                        params4.privateFlags |= 128;
                        this.mLastInCompatMode = true;
                    }
                }
                WindowManager.LayoutParams params5 = params4;
                this.mWindowAttributesChangesFlag = 0;
                Rect frame3 = this.mWinFrame;
                if (this.mFirst) {
                    desiredWindowWidth = frame3.width();
                    desiredWindowHeight = frame3.height();
                    if (desiredWindowWidth != this.mWidth || desiredWindowHeight != this.mHeight) {
                        this.mFullRedrawNeeded = true;
                        this.mLayoutRequested = true;
                        windowSizeMayChange = true;
                    }
                } else {
                    this.mFullRedrawNeeded = true;
                    this.mLayoutRequested = true;
                    Configuration config = this.mContext.getResources().getConfiguration();
                    if (shouldUseDisplaySize(lp)) {
                        Point size = new Point();
                        this.mDisplay.getRealSize(size);
                        desiredWindowWidth = size.f59x;
                        desiredWindowHeight = size.f60y;
                    } else {
                        desiredWindowWidth = this.mWinFrame.width();
                        desiredWindowHeight = this.mWinFrame.height();
                    }
                    this.mAttachInfo.mUse32BitDrawingCache = true;
                    this.mAttachInfo.mWindowVisibility = viewVisibility;
                    this.mAttachInfo.mRecomputeGlobalAttributes = false;
                    this.mLastConfigurationFromResources.setTo(config);
                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                    if (this.mViewLayoutDirectionInitial == 2) {
                        host.setLayoutDirection(config.getLayoutDirection());
                    }
                    host.dispatchAttachedToWindow(this.mAttachInfo, 0);
                    this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(true);
                    dispatchApplyInsets(host);
                }
                if (viewVisibilityChanged) {
                    this.mAttachInfo.mWindowVisibility = viewVisibility;
                    host.dispatchWindowVisibilityChanged(viewVisibility);
                    if (viewUserVisibilityChanged) {
                        host.dispatchVisibilityAggregated(viewVisibility == 0);
                    }
                    if (viewVisibility != 0 || this.mNewSurfaceNeeded) {
                        endDragResizing();
                        destroyHardwareResources();
                    }
                    if (viewVisibility == 8) {
                        this.mHasHadWindowFocus = false;
                    }
                }
                if (this.mAttachInfo.mWindowVisibility != 0) {
                    host.clearAccessibilityFocus();
                }
                getRunQueue().executeActions(this.mAttachInfo.mHandler);
                layoutRequested = !this.mLayoutRequested && (!this.mStopped || this.mReportNextDraw);
                if (layoutRequested) {
                    i = -2;
                    frame = frame3;
                    insetsChanged = false;
                    desiredWindowHeight2 = desiredWindowHeight;
                    desiredWindowWidth2 = desiredWindowWidth;
                } else {
                    Resources res = this.mView.getContext().getResources();
                    if (this.mFirst) {
                        this.mAttachInfo.mInTouchMode = !this.mAddedTouchMode;
                        ensureTouchModeLocally(this.mAddedTouchMode);
                    } else {
                        insetsChanged = this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets) ? false : true;
                        if (!this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets)) {
                            insetsChanged = true;
                        }
                        if (!this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets)) {
                            insetsChanged = true;
                        }
                        if (!this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout)) {
                            insetsChanged = true;
                        }
                        if (!this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets)) {
                            this.mAttachInfo.mVisibleInsets.set(this.mPendingVisibleInsets);
                        }
                        if (!this.mPendingOutsets.equals(this.mAttachInfo.mOutsets)) {
                            insetsChanged = true;
                        }
                        if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
                            insetsChanged = true;
                        }
                        if (lp.width == -2 || lp.height == -2) {
                            windowSizeMayChange = true;
                            if (shouldUseDisplaySize(lp)) {
                                Point size2 = new Point();
                                this.mDisplay.getRealSize(size2);
                                desiredWindowWidth = size2.f59x;
                                desiredWindowHeight = size2.f60y;
                            } else {
                                Configuration config2 = res.getConfiguration();
                                desiredWindowWidth = dipToPx(config2.screenWidthDp);
                                desiredWindowHeight = dipToPx(config2.screenHeightDp);
                            }
                        }
                    }
                    insetsChanged = insetsChanged;
                    desiredWindowWidth2 = desiredWindowWidth;
                    i = -2;
                    frame = frame3;
                    desiredWindowHeight2 = desiredWindowHeight;
                    windowSizeMayChange |= measureHierarchy(host, lp, res, desiredWindowWidth2, desiredWindowHeight2);
                }
                if (collectViewAttributes()) {
                    params5 = lp;
                }
                if (this.mAttachInfo.mForceReportNewAttributes) {
                    this.mAttachInfo.mForceReportNewAttributes = false;
                    params5 = lp;
                }
                if (!this.mFirst || this.mAttachInfo.mViewVisibilityChanged) {
                    this.mAttachInfo.mViewVisibilityChanged = false;
                    resizeMode = this.mSoftInputMode & 240;
                    if (resizeMode == 0) {
                        int N = this.mAttachInfo.mScrollContainers.size();
                        int resizeMode2 = resizeMode;
                        for (int resizeMode3 = 0; resizeMode3 < N; resizeMode3++) {
                            if (this.mAttachInfo.mScrollContainers.get(resizeMode3).isShown()) {
                                resizeMode2 = 16;
                            }
                        }
                        if (resizeMode2 == 0) {
                            resizeMode2 = 32;
                        }
                        if ((lp.softInputMode & 240) != resizeMode2) {
                            lp.softInputMode = (lp.softInputMode & TrafficStats.TAG_SYSTEM_IMPERSONATION_RANGE_END) | resizeMode2;
                            params5 = lp;
                        }
                    }
                }
                params = params5;
                if (params != null) {
                    if ((host.mPrivateFlags & 512) != 0 && !PixelFormat.formatHasAlpha(params.format)) {
                        params.format = -3;
                    }
                    this.mAttachInfo.mOverscanRequested = (params.flags & 33554432) != 0;
                }
                if (this.mApplyInsetsRequested) {
                    this.mApplyInsetsRequested = false;
                    this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                    dispatchApplyInsets(host);
                    if (this.mLayoutRequested) {
                        params2 = params;
                        desiredWindowHeight3 = desiredWindowHeight2;
                        windowSizeMayChange |= measureHierarchy(host, lp, this.mView.getContext().getResources(), desiredWindowWidth2, desiredWindowHeight2);
                        boolean windowSizeMayChange2 = windowSizeMayChange;
                        if (layoutRequested) {
                            this.mLayoutRequested = false;
                        }
                        if (layoutRequested && windowSizeMayChange2) {
                            if (this.mWidth == host.getMeasuredWidth() || this.mHeight != host.getMeasuredHeight()) {
                                desiredWindowHeight4 = desiredWindowHeight3;
                            } else if (lp.width == i && frame.width() < desiredWindowWidth2 && frame.width() != this.mWidth) {
                                desiredWindowHeight4 = desiredWindowHeight3;
                            } else if (lp.height == i) {
                                desiredWindowHeight4 = desiredWindowHeight3;
                                if (frame.height() < desiredWindowHeight4) {
                                }
                                windowShouldResize = false;
                                boolean windowShouldResize2 = windowShouldResize | (!this.mDragResizing && this.mResizeMode == 0) | this.mActivityRelaunched;
                                computesInternalInsets = !this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
                                relayoutResult = 0;
                                boolean updatedConfiguration2 = false;
                                int surfaceGenerationId = this.mSurface.getGenerationId();
                                isViewVisible = viewVisibility != 0;
                                boolean windowRelayoutWasForced = this.mForceNextWindowRelayout;
                                if (!this.mFirst || windowShouldResize2 || insetsChanged || viewVisibilityChanged) {
                                    insetsPending = false;
                                    frame2 = frame;
                                    params3 = params2;
                                } else {
                                    params3 = params2;
                                    if (params3 == null) {
                                        insetsPending = false;
                                        boolean insetsPending3 = this.mForceNextWindowRelayout;
                                        if (!insetsPending3) {
                                            maybeHandleWindowMove(frame);
                                            surfaceSizeChanged = false;
                                            if (surfaceSizeChanged) {
                                                updateBoundsSurface();
                                            }
                                            didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                                            triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                                            if (didLayout) {
                                                performLayout(lp, this.mWidth, this.mHeight);
                                                if ((host.mPrivateFlags & 512) != 0) {
                                                    host.getLocationInWindow(this.mTmpLocation);
                                                    this.mTransparentRegion.set(this.mTmpLocation[0], this.mTmpLocation[1], (this.mTmpLocation[0] + host.mRight) - host.mLeft, (this.mTmpLocation[1] + host.mBottom) - host.mTop);
                                                    host.gatherTransparentRegion(this.mTransparentRegion);
                                                    if (this.mTranslator != null) {
                                                        this.mTranslator.translateRegionInWindowToScreen(this.mTransparentRegion);
                                                    }
                                                    if (!this.mTransparentRegion.equals(this.mPreviousTransparentRegion)) {
                                                        this.mPreviousTransparentRegion.set(this.mTransparentRegion);
                                                        this.mFullRedrawNeeded = true;
                                                        try {
                                                            this.mWindowSession.setTransparentRegion(this.mWindow, this.mTransparentRegion);
                                                        } catch (RemoteException e) {
                                                        }
                                                    }
                                                    if (triggerGlobalLayoutListener) {
                                                        this.mAttachInfo.mRecomputeGlobalAttributes = false;
                                                        this.mAttachInfo.mTreeObserver.dispatchOnGlobalLayout();
                                                    }
                                                    if (computesInternalInsets) {
                                                        ViewTreeObserver.InternalInsetsInfo insets = this.mAttachInfo.mGivenInternalInsets;
                                                        insets.reset();
                                                        this.mAttachInfo.mTreeObserver.dispatchOnComputeInternalInsets(insets);
                                                        this.mAttachInfo.mHasNonEmptyGivenInternalInsets = !insets.isEmpty();
                                                        if (insetsPending || !this.mLastGivenInsets.equals(insets)) {
                                                            this.mLastGivenInsets.set(insets);
                                                            if (this.mTranslator != null) {
                                                                contentInsets = this.mTranslator.getTranslatedContentInsets(insets.contentInsets);
                                                                visibleInsets = this.mTranslator.getTranslatedVisibleInsets(insets.visibleInsets);
                                                                region = this.mTranslator.getTranslatedTouchableArea(insets.touchableRegion);
                                                            } else {
                                                                contentInsets = insets.contentInsets;
                                                                visibleInsets = insets.visibleInsets;
                                                                region = insets.touchableRegion;
                                                            }
                                                            Region touchableRegion = region;
                                                            try {
                                                                this.mWindowSession.setInsets(this.mWindow, insets.mTouchableInsets, contentInsets, visibleInsets, touchableRegion);
                                                            } catch (RemoteException e2) {
                                                            }
                                                        }
                                                    }
                                                    if (this.mFirst) {
                                                        if (!sAlwaysAssignFocus && isInTouchMode()) {
                                                            View focused = this.mView.findFocus();
                                                            if ((focused instanceof ViewGroup) && ((ViewGroup) focused).getDescendantFocusability() == 262144) {
                                                                focused.restoreDefaultFocus();
                                                            }
                                                        } else if (this.mView != null && !this.mView.hasFocus()) {
                                                            this.mView.restoreDefaultFocus();
                                                        }
                                                    }
                                                    changedVisibility = (!viewVisibilityChanged || this.mFirst) && isViewVisible;
                                                    hasWindowFocus = !this.mAttachInfo.mHasWindowFocus && isViewVisible;
                                                    regainedFocus = !hasWindowFocus && this.mLostWindowFocus;
                                                    if (regainedFocus) {
                                                        this.mLostWindowFocus = false;
                                                    } else if (!hasWindowFocus && this.mHadWindowFocus) {
                                                        this.mLostWindowFocus = true;
                                                    }
                                                    if (!changedVisibility || regainedFocus) {
                                                        isToast = this.mWindowAttributes != null && this.mWindowAttributes.type == 2005;
                                                        if (!isToast) {
                                                            host.sendAccessibilityEvent(32);
                                                        }
                                                    }
                                                    this.mFirst = false;
                                                    this.mWillDrawSoon = false;
                                                    this.mNewSurfaceNeeded = false;
                                                    this.mActivityRelaunched = false;
                                                    this.mViewVisibility = viewVisibility;
                                                    this.mHadWindowFocus = hasWindowFocus;
                                                    if (hasWindowFocus && !isInLocalFocusMode() && (imTarget = WindowManager.LayoutParams.mayUseInputMethod(this.mWindowAttributes.flags)) != this.mLastWasImTarget) {
                                                        this.mLastWasImTarget = imTarget;
                                                        imm = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                                                        if (imm != null && imTarget) {
                                                            imm.onPreWindowFocus(this.mView, hasWindowFocus);
                                                            z2 = true;
                                                            imm.onPostWindowFocus(this.mView, this.mView.findFocus(), this.mWindowAttributes.softInputMode, !this.mHasHadWindowFocus, this.mWindowAttributes.flags);
                                                            if ((relayoutResult & 2) != 0) {
                                                                reportNextDraw();
                                                            }
                                                            if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw() && isViewVisible) {
                                                                z2 = false;
                                                            }
                                                            cancelDraw = z2;
                                                            if (cancelDraw) {
                                                                if (this.mPendingTransitions != null && this.mPendingTransitions.size() > 0) {
                                                                    for (int i2 = 0; i2 < this.mPendingTransitions.size(); i2++) {
                                                                        this.mPendingTransitions.get(i2).startChangingAnimations();
                                                                    }
                                                                    this.mPendingTransitions.clear();
                                                                }
                                                                performDraw();
                                                            } else if (isViewVisible) {
                                                                scheduleTraversals();
                                                            } else if (this.mPendingTransitions != null && this.mPendingTransitions.size() > 0) {
                                                                for (int i3 = 0; i3 < this.mPendingTransitions.size(); i3++) {
                                                                    this.mPendingTransitions.get(i3).endChangingAnimations();
                                                                }
                                                                this.mPendingTransitions.clear();
                                                            }
                                                            this.mIsInTraversal = false;
                                                        }
                                                    }
                                                    z2 = true;
                                                    if ((relayoutResult & 2) != 0) {
                                                    }
                                                    if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                                        z2 = false;
                                                    }
                                                    cancelDraw = z2;
                                                    if (cancelDraw) {
                                                    }
                                                    this.mIsInTraversal = false;
                                                }
                                            }
                                            if (triggerGlobalLayoutListener) {
                                            }
                                            if (computesInternalInsets) {
                                            }
                                            if (this.mFirst) {
                                            }
                                            if (viewVisibilityChanged) {
                                            }
                                            if (this.mAttachInfo.mHasWindowFocus) {
                                            }
                                            if (hasWindowFocus) {
                                            }
                                            if (regainedFocus) {
                                            }
                                            if (!changedVisibility) {
                                            }
                                            if (this.mWindowAttributes != null) {
                                                if (!isToast) {
                                                }
                                                this.mFirst = false;
                                                this.mWillDrawSoon = false;
                                                this.mNewSurfaceNeeded = false;
                                                this.mActivityRelaunched = false;
                                                this.mViewVisibility = viewVisibility;
                                                this.mHadWindowFocus = hasWindowFocus;
                                                if (hasWindowFocus) {
                                                    this.mLastWasImTarget = imTarget;
                                                    imm = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                                                    if (imm != null) {
                                                        imm.onPreWindowFocus(this.mView, hasWindowFocus);
                                                        z2 = true;
                                                        imm.onPostWindowFocus(this.mView, this.mView.findFocus(), this.mWindowAttributes.softInputMode, !this.mHasHadWindowFocus, this.mWindowAttributes.flags);
                                                        if ((relayoutResult & 2) != 0) {
                                                        }
                                                        if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                                        }
                                                        cancelDraw = z2;
                                                        if (cancelDraw) {
                                                        }
                                                        this.mIsInTraversal = false;
                                                    }
                                                }
                                                z2 = true;
                                                if ((relayoutResult & 2) != 0) {
                                                }
                                                if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                                }
                                                cancelDraw = z2;
                                                if (cancelDraw) {
                                                }
                                                this.mIsInTraversal = false;
                                            }
                                            if (!isToast) {
                                            }
                                            this.mFirst = false;
                                            this.mWillDrawSoon = false;
                                            this.mNewSurfaceNeeded = false;
                                            this.mActivityRelaunched = false;
                                            this.mViewVisibility = viewVisibility;
                                            this.mHadWindowFocus = hasWindowFocus;
                                            if (hasWindowFocus) {
                                            }
                                            z2 = true;
                                            if ((relayoutResult & 2) != 0) {
                                            }
                                            if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                            }
                                            cancelDraw = z2;
                                            if (cancelDraw) {
                                            }
                                            this.mIsInTraversal = false;
                                        }
                                        frame2 = frame;
                                    } else {
                                        insetsPending = false;
                                        frame2 = frame;
                                    }
                                }
                                this.mForceNextWindowRelayout = false;
                                boolean insetsPending4 = isViewVisible ? insetsPending : computesInternalInsets && (this.mFirst || viewVisibilityChanged);
                                if (this.mSurfaceHolder == null) {
                                    this.mSurfaceHolder.mSurfaceLock.lock();
                                    updatedConfiguration = false;
                                    this.mDrawingAllowed = true;
                                } else {
                                    updatedConfiguration = false;
                                }
                                contentInsetsChanged = false;
                                hadSurface = this.mSurface.isValid();
                                if (this.mAttachInfo.mThreadedRenderer == null) {
                                    try {
                                        if (this.mAttachInfo.mThreadedRenderer.pause()) {
                                            hwInitialized = false;
                                            try {
                                                surfaceSizeChanged2 = false;
                                                try {
                                                    try {
                                                        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
                                                    } catch (RemoteException e3) {
                                                        insetsPending2 = insetsPending4;
                                                        relayoutResult2 = surfaceGenerationId;
                                                        relayoutResult3 = 0;
                                                    }
                                                } catch (RemoteException e4) {
                                                    insetsPending2 = insetsPending4;
                                                    relayoutResult2 = surfaceGenerationId;
                                                    relayoutResult3 = 0;
                                                }
                                            } catch (RemoteException e5) {
                                                surfaceSizeChanged2 = false;
                                                insetsPending2 = insetsPending4;
                                                relayoutResult2 = surfaceGenerationId;
                                                relayoutResult3 = 0;
                                            }
                                        } else {
                                            hwInitialized = false;
                                            surfaceSizeChanged2 = false;
                                        }
                                        this.mChoreographer.mFrameInfo.addFlags(1L);
                                    } catch (RemoteException e6) {
                                        hwInitialized = false;
                                        surfaceSizeChanged2 = false;
                                        insetsPending2 = insetsPending4;
                                        relayoutResult2 = surfaceGenerationId;
                                        relayoutResult3 = 0;
                                    }
                                } else {
                                    hwInitialized = false;
                                    surfaceSizeChanged2 = false;
                                }
                                relayoutResult2 = relayoutWindow(params3, viewVisibility, insetsPending4);
                                if (!this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                                    try {
                                        performConfigurationChange(this.mPendingMergedConfiguration, !this.mFirst, -1);
                                        updatedConfiguration = true;
                                    } catch (RemoteException e7) {
                                        insetsPending2 = insetsPending4;
                                        relayoutResult3 = relayoutResult2;
                                        relayoutResult2 = surfaceGenerationId;
                                        updatedConfiguration2 = updatedConfiguration;
                                        boolean surfaceSizeChanged3 = surfaceSizeChanged2;
                                        relayoutResult4 = relayoutResult3;
                                        this.mAttachInfo.mWindowLeft = frame2.left;
                                        this.mAttachInfo.mWindowTop = frame2.top;
                                        if (this.mWidth == frame2.width()) {
                                        }
                                        this.mWidth = frame2.width();
                                        this.mHeight = frame2.height();
                                        if (this.mSurfaceHolder == null) {
                                        }
                                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                        if (threadedRenderer != null) {
                                        }
                                        if (this.mStopped) {
                                        }
                                        focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                                        if (!focusChangedDueToTouchMode) {
                                        }
                                        int childWidthMeasureSpec = getRootMeasureSpec(this.mWidth, lp.width);
                                        int childHeightMeasureSpec = getRootMeasureSpec(this.mHeight, lp.height);
                                        performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
                                        int width = host.getMeasuredWidth();
                                        int height = host.getMeasuredHeight();
                                        measureAgain = false;
                                        relayoutResult5 = relayoutResult4;
                                        if (lp.horizontalWeight > 0.0f) {
                                        }
                                        if (lp.verticalWeight > 0.0f) {
                                        }
                                        if (measureAgain) {
                                        }
                                        layoutRequested = true;
                                        insetsPending = insetsPending2;
                                        relayoutResult = relayoutResult5;
                                        if (surfaceSizeChanged) {
                                        }
                                        didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                                        triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                                        if (didLayout) {
                                        }
                                        if (triggerGlobalLayoutListener) {
                                        }
                                        if (computesInternalInsets) {
                                        }
                                        if (this.mFirst) {
                                        }
                                        if (viewVisibilityChanged) {
                                        }
                                        if (this.mAttachInfo.mHasWindowFocus) {
                                        }
                                        if (hasWindowFocus) {
                                        }
                                        if (regainedFocus) {
                                        }
                                        if (!changedVisibility) {
                                        }
                                        if (this.mWindowAttributes != null) {
                                        }
                                        if (!isToast) {
                                        }
                                        this.mFirst = false;
                                        this.mWillDrawSoon = false;
                                        this.mNewSurfaceNeeded = false;
                                        this.mActivityRelaunched = false;
                                        this.mViewVisibility = viewVisibility;
                                        this.mHadWindowFocus = hasWindowFocus;
                                        if (hasWindowFocus) {
                                        }
                                        z2 = true;
                                        if ((relayoutResult & 2) != 0) {
                                        }
                                        if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                        }
                                        cancelDraw = z2;
                                        if (cancelDraw) {
                                        }
                                        this.mIsInTraversal = false;
                                    }
                                }
                                overscanInsetsChanged = !this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets);
                                contentInsetsChanged = !this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets);
                                visibleInsetsChanged = !this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets);
                                stableInsetsChanged = !this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets);
                                cutoutChanged = !this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout);
                                boolean outsetsChanged = !this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
                                surfaceSizeChanged2 = (relayoutResult2 & 32) == 0;
                                surfaceChanged2 |= surfaceSizeChanged2;
                                alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars == this.mAttachInfo.mAlwaysConsumeSystemBars;
                                colorModeChanged = hasColorModeChanged(lp.getColorMode());
                                if (contentInsetsChanged) {
                                    this.mAttachInfo.mContentInsets.set(this.mPendingContentInsets);
                                }
                                if (overscanInsetsChanged) {
                                    this.mAttachInfo.mOverscanInsets.set(this.mPendingOverscanInsets);
                                    contentInsetsChanged = true;
                                }
                                if (stableInsetsChanged) {
                                    this.mAttachInfo.mStableInsets.set(this.mPendingStableInsets);
                                    contentInsetsChanged = true;
                                }
                                if (cutoutChanged) {
                                    this.mAttachInfo.mDisplayCutout.set(this.mPendingDisplayCutout);
                                    contentInsetsChanged = true;
                                }
                                if (alwaysConsumeSystemBarsChanged) {
                                    this.mAttachInfo.mAlwaysConsumeSystemBars = this.mPendingAlwaysConsumeSystemBars;
                                    contentInsetsChanged = true;
                                }
                                if (!contentInsetsChanged || this.mLastSystemUiVisibility != this.mAttachInfo.mSystemUiVisibility || this.mApplyInsetsRequested || this.mLastOverscanRequested != this.mAttachInfo.mOverscanRequested || outsetsChanged) {
                                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                    this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                                    this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
                                    this.mApplyInsetsRequested = false;
                                    dispatchApplyInsets(host);
                                    contentInsetsChanged = true;
                                }
                                if (visibleInsetsChanged) {
                                    this.mAttachInfo.mVisibleInsets.set(this.mPendingVisibleInsets);
                                }
                                if (colorModeChanged && this.mAttachInfo.mThreadedRenderer != null) {
                                    this.mAttachInfo.mThreadedRenderer.setWideGamut(lp.getColorMode() != 1);
                                }
                                if (hadSurface) {
                                    if (this.mSurface.isValid()) {
                                        this.mFullRedrawNeeded = true;
                                        this.mPreviousTransparentRegion.setEmpty();
                                        try {
                                            if (this.mAttachInfo.mThreadedRenderer != null) {
                                                try {
                                                    boolean hwInitialized2 = this.mAttachInfo.mThreadedRenderer.initialize(this.mSurface);
                                                    if (hwInitialized2) {
                                                        try {
                                                            if ((host.mPrivateFlags & 512) == 0) {
                                                                this.mAttachInfo.mThreadedRenderer.allocateBuffers();
                                                            }
                                                        } catch (Surface.OutOfResourcesException e8) {
                                                            e = e8;
                                                            handleOutOfResourcesException(e);
                                                            return;
                                                        }
                                                    }
                                                    hwInitialized = hwInitialized2;
                                                } catch (Surface.OutOfResourcesException e9) {
                                                    e = e9;
                                                }
                                            }
                                        } catch (RemoteException e10) {
                                            insetsPending2 = insetsPending4;
                                            hwInitialized = true;
                                            relayoutResult3 = relayoutResult2;
                                            relayoutResult2 = surfaceGenerationId;
                                            updatedConfiguration2 = updatedConfiguration;
                                            boolean surfaceSizeChanged32 = surfaceSizeChanged2;
                                            relayoutResult4 = relayoutResult3;
                                            this.mAttachInfo.mWindowLeft = frame2.left;
                                            this.mAttachInfo.mWindowTop = frame2.top;
                                            if (this.mWidth == frame2.width()) {
                                            }
                                            this.mWidth = frame2.width();
                                            this.mHeight = frame2.height();
                                            if (this.mSurfaceHolder == null) {
                                            }
                                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                            if (threadedRenderer != null) {
                                            }
                                            if (this.mStopped) {
                                            }
                                            focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                                            if (!focusChangedDueToTouchMode) {
                                            }
                                            int childWidthMeasureSpec2 = getRootMeasureSpec(this.mWidth, lp.width);
                                            int childHeightMeasureSpec2 = getRootMeasureSpec(this.mHeight, lp.height);
                                            performMeasure(childWidthMeasureSpec2, childHeightMeasureSpec2);
                                            int width2 = host.getMeasuredWidth();
                                            int height2 = host.getMeasuredHeight();
                                            measureAgain = false;
                                            relayoutResult5 = relayoutResult4;
                                            if (lp.horizontalWeight > 0.0f) {
                                            }
                                            if (lp.verticalWeight > 0.0f) {
                                            }
                                            if (measureAgain) {
                                            }
                                            layoutRequested = true;
                                            insetsPending = insetsPending2;
                                            relayoutResult = relayoutResult5;
                                            if (surfaceSizeChanged) {
                                            }
                                            didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                                            triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                                            if (didLayout) {
                                            }
                                            if (triggerGlobalLayoutListener) {
                                            }
                                            if (computesInternalInsets) {
                                            }
                                            if (this.mFirst) {
                                            }
                                            if (viewVisibilityChanged) {
                                            }
                                            if (this.mAttachInfo.mHasWindowFocus) {
                                            }
                                            if (hasWindowFocus) {
                                            }
                                            if (regainedFocus) {
                                            }
                                            if (!changedVisibility) {
                                            }
                                            if (this.mWindowAttributes != null) {
                                            }
                                            if (!isToast) {
                                            }
                                            this.mFirst = false;
                                            this.mWillDrawSoon = false;
                                            this.mNewSurfaceNeeded = false;
                                            this.mActivityRelaunched = false;
                                            this.mViewVisibility = viewVisibility;
                                            this.mHadWindowFocus = hasWindowFocus;
                                            if (hasWindowFocus) {
                                            }
                                            z2 = true;
                                            if ((relayoutResult & 2) != 0) {
                                            }
                                            if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                            }
                                            cancelDraw = z2;
                                            if (cancelDraw) {
                                            }
                                            this.mIsInTraversal = false;
                                        }
                                    }
                                } else if (!this.mSurface.isValid()) {
                                    if (this.mLastScrolledFocus != null) {
                                        this.mLastScrolledFocus.clear();
                                    }
                                    this.mCurScrollY = 0;
                                    this.mScrollY = 0;
                                    if (this.mView instanceof RootViewSurfaceTaker) {
                                        ((RootViewSurfaceTaker) this.mView).onRootViewScrollYChanged(this.mCurScrollY);
                                    }
                                    if (this.mScroller != null) {
                                        this.mScroller.abortAnimation();
                                    }
                                    if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.isEnabled()) {
                                        this.mAttachInfo.mThreadedRenderer.destroy();
                                    }
                                } else if ((surfaceGenerationId != this.mSurface.getGenerationId() || surfaceSizeChanged2 || windowRelayoutWasForced || colorModeChanged) && this.mSurfaceHolder == null && this.mAttachInfo.mThreadedRenderer != null) {
                                    this.mFullRedrawNeeded = true;
                                    try {
                                        this.mAttachInfo.mThreadedRenderer.updateSurface(this.mSurface);
                                    } catch (Surface.OutOfResourcesException e11) {
                                        handleOutOfResourcesException(e11);
                                        return;
                                    }
                                }
                                boolean freeformResizing = (relayoutResult2 & 16) == 0;
                                boolean dockedResizing = (relayoutResult2 & 8) == 0;
                                dragResizing = !freeformResizing || dockedResizing;
                                if (this.mDragResizing != dragResizing) {
                                    insetsPending2 = insetsPending4;
                                    dragResizing2 = dragResizing;
                                    relayoutResult3 = relayoutResult2;
                                    relayoutResult2 = surfaceGenerationId;
                                } else if (dragResizing) {
                                    this.mResizeMode = freeformResizing ? 0 : 1;
                                    boolean backdropSizeMatchesFrame = this.mWinFrame.width() == this.mPendingBackDropFrame.width() && this.mWinFrame.height() == this.mPendingBackDropFrame.height();
                                    try {
                                        try {
                                            insetsPending2 = insetsPending4;
                                            dragResizing2 = dragResizing;
                                            relayoutResult3 = relayoutResult2;
                                            relayoutResult2 = surfaceGenerationId;
                                            startDragResizing(this.mPendingBackDropFrame, !backdropSizeMatchesFrame, this.mPendingVisibleInsets, this.mPendingStableInsets, this.mResizeMode);
                                        } catch (RemoteException e12) {
                                            insetsPending2 = insetsPending4;
                                            relayoutResult3 = relayoutResult2;
                                            relayoutResult2 = surfaceGenerationId;
                                        }
                                    } catch (RemoteException e13) {
                                        insetsPending2 = insetsPending4;
                                        relayoutResult3 = relayoutResult2;
                                        relayoutResult2 = surfaceGenerationId;
                                    }
                                } else {
                                    insetsPending2 = insetsPending4;
                                    dragResizing2 = dragResizing;
                                    relayoutResult3 = relayoutResult2;
                                    relayoutResult2 = surfaceGenerationId;
                                    endDragResizing();
                                }
                                if (!this.mUseMTRenderer) {
                                    if (dragResizing2) {
                                        this.mCanvasOffsetX = this.mWinFrame.left;
                                        this.mCanvasOffsetY = this.mWinFrame.top;
                                    } else {
                                        this.mCanvasOffsetY = 0;
                                        this.mCanvasOffsetX = 0;
                                    }
                                }
                                updatedConfiguration2 = updatedConfiguration;
                                boolean surfaceSizeChanged322 = surfaceSizeChanged2;
                                relayoutResult4 = relayoutResult3;
                                this.mAttachInfo.mWindowLeft = frame2.left;
                                this.mAttachInfo.mWindowTop = frame2.top;
                                if (this.mWidth == frame2.width() || this.mHeight != frame2.height()) {
                                    this.mWidth = frame2.width();
                                    this.mHeight = frame2.height();
                                }
                                if (this.mSurfaceHolder == null) {
                                    if (this.mSurface.isValid()) {
                                        this.mSurfaceHolder.mSurface = this.mSurface;
                                    }
                                    this.mSurfaceHolder.setSurfaceFrameSize(this.mWidth, this.mHeight);
                                    this.mSurfaceHolder.mSurfaceLock.unlock();
                                    if (this.mSurface.isValid()) {
                                        if (!hadSurface) {
                                            this.mSurfaceHolder.ungetCallbacks();
                                            this.mIsCreating = true;
                                            SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                                            if (callbacks != null) {
                                                int length = callbacks.length;
                                                int i4 = 0;
                                                while (i4 < length) {
                                                    SurfaceHolder.Callback c = callbacks[i4];
                                                    c.surfaceCreated(this.mSurfaceHolder);
                                                    i4++;
                                                    callbacks = callbacks;
                                                }
                                            }
                                            surfaceChanged2 = true;
                                        }
                                        if (surfaceChanged2 || relayoutResult2 != this.mSurface.getGenerationId()) {
                                            SurfaceHolder.Callback[] callbacks2 = this.mSurfaceHolder.getCallbacks();
                                            if (callbacks2 != null) {
                                                int length2 = callbacks2.length;
                                                int i5 = 0;
                                                while (i5 < length2) {
                                                    SurfaceHolder.Callback c2 = callbacks2[i5];
                                                    c2.surfaceChanged(this.mSurfaceHolder, lp.format, this.mWidth, this.mHeight);
                                                    i5++;
                                                    callbacks2 = callbacks2;
                                                    surfaceSizeChanged322 = surfaceSizeChanged322;
                                                    length2 = length2;
                                                    frame2 = frame2;
                                                }
                                            }
                                            surfaceSizeChanged = surfaceSizeChanged322;
                                        } else {
                                            surfaceSizeChanged = surfaceSizeChanged322;
                                        }
                                        this.mIsCreating = false;
                                    } else {
                                        surfaceSizeChanged = surfaceSizeChanged322;
                                        if (hadSurface) {
                                            notifySurfaceDestroyed();
                                            this.mSurfaceHolder.mSurfaceLock.lock();
                                            try {
                                                this.mSurfaceHolder.mSurface = new Surface();
                                            } finally {
                                                this.mSurfaceHolder.mSurfaceLock.unlock();
                                            }
                                        }
                                    }
                                } else {
                                    surfaceSizeChanged = surfaceSizeChanged322;
                                }
                                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                                if (threadedRenderer != null && threadedRenderer.isEnabled() && (hwInitialized || this.mWidth != threadedRenderer.getWidth() || this.mHeight != threadedRenderer.getHeight() || this.mNeedsRendererSetup)) {
                                    threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                                    this.mNeedsRendererSetup = false;
                                }
                                if (this.mStopped || this.mReportNextDraw) {
                                    focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                                    if (!focusChangedDueToTouchMode || this.mWidth != host.getMeasuredWidth() || this.mHeight != host.getMeasuredHeight() || contentInsetsChanged || updatedConfiguration2) {
                                        int childWidthMeasureSpec22 = getRootMeasureSpec(this.mWidth, lp.width);
                                        int childHeightMeasureSpec22 = getRootMeasureSpec(this.mHeight, lp.height);
                                        performMeasure(childWidthMeasureSpec22, childHeightMeasureSpec22);
                                        int width22 = host.getMeasuredWidth();
                                        int height22 = host.getMeasuredHeight();
                                        measureAgain = false;
                                        relayoutResult5 = relayoutResult4;
                                        if (lp.horizontalWeight > 0.0f) {
                                            childWidthMeasureSpec22 = View.MeasureSpec.makeMeasureSpec(width22 + ((int) ((this.mWidth - width22) * lp.horizontalWeight)), 1073741824);
                                            measureAgain = true;
                                        }
                                        if (lp.verticalWeight > 0.0f) {
                                            childHeightMeasureSpec22 = View.MeasureSpec.makeMeasureSpec(height22 + ((int) ((this.mHeight - height22) * lp.verticalWeight)), 1073741824);
                                            measureAgain = true;
                                        }
                                        if (measureAgain) {
                                            performMeasure(childWidthMeasureSpec22, childHeightMeasureSpec22);
                                        }
                                        layoutRequested = true;
                                        insetsPending = insetsPending2;
                                        relayoutResult = relayoutResult5;
                                        if (surfaceSizeChanged) {
                                        }
                                        didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                                        triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                                        if (didLayout) {
                                        }
                                        if (triggerGlobalLayoutListener) {
                                        }
                                        if (computesInternalInsets) {
                                        }
                                        if (this.mFirst) {
                                        }
                                        if (viewVisibilityChanged) {
                                        }
                                        if (this.mAttachInfo.mHasWindowFocus) {
                                        }
                                        if (hasWindowFocus) {
                                        }
                                        if (regainedFocus) {
                                        }
                                        if (!changedVisibility) {
                                        }
                                        if (this.mWindowAttributes != null) {
                                        }
                                        if (!isToast) {
                                        }
                                        this.mFirst = false;
                                        this.mWillDrawSoon = false;
                                        this.mNewSurfaceNeeded = false;
                                        this.mActivityRelaunched = false;
                                        this.mViewVisibility = viewVisibility;
                                        this.mHadWindowFocus = hasWindowFocus;
                                        if (hasWindowFocus) {
                                        }
                                        z2 = true;
                                        if ((relayoutResult & 2) != 0) {
                                        }
                                        if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                        }
                                        cancelDraw = z2;
                                        if (cancelDraw) {
                                        }
                                        this.mIsInTraversal = false;
                                    }
                                }
                                relayoutResult5 = relayoutResult4;
                                insetsPending = insetsPending2;
                                relayoutResult = relayoutResult5;
                                if (surfaceSizeChanged) {
                                }
                                didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                                triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                                if (didLayout) {
                                }
                                if (triggerGlobalLayoutListener) {
                                }
                                if (computesInternalInsets) {
                                }
                                if (this.mFirst) {
                                }
                                if (viewVisibilityChanged) {
                                }
                                if (this.mAttachInfo.mHasWindowFocus) {
                                }
                                if (hasWindowFocus) {
                                }
                                if (regainedFocus) {
                                }
                                if (!changedVisibility) {
                                }
                                if (this.mWindowAttributes != null) {
                                }
                                if (!isToast) {
                                }
                                this.mFirst = false;
                                this.mWillDrawSoon = false;
                                this.mNewSurfaceNeeded = false;
                                this.mActivityRelaunched = false;
                                this.mViewVisibility = viewVisibility;
                                this.mHadWindowFocus = hasWindowFocus;
                                if (hasWindowFocus) {
                                }
                                z2 = true;
                                if ((relayoutResult & 2) != 0) {
                                }
                                if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                                }
                                cancelDraw = z2;
                                if (cancelDraw) {
                                }
                                this.mIsInTraversal = false;
                            }
                            windowShouldResize = true;
                            boolean windowShouldResize22 = windowShouldResize | (!this.mDragResizing && this.mResizeMode == 0) | this.mActivityRelaunched;
                            computesInternalInsets = !this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
                            relayoutResult = 0;
                            boolean updatedConfiguration22 = false;
                            int surfaceGenerationId2 = this.mSurface.getGenerationId();
                            isViewVisible = viewVisibility != 0;
                            boolean windowRelayoutWasForced2 = this.mForceNextWindowRelayout;
                            if (this.mFirst) {
                            }
                            insetsPending = false;
                            frame2 = frame;
                            params3 = params2;
                            this.mForceNextWindowRelayout = false;
                            if (isViewVisible) {
                            }
                            if (this.mSurfaceHolder == null) {
                            }
                            contentInsetsChanged = false;
                            hadSurface = this.mSurface.isValid();
                            if (this.mAttachInfo.mThreadedRenderer == null) {
                            }
                            relayoutResult2 = relayoutWindow(params3, viewVisibility, insetsPending4);
                            if (!this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                            }
                            overscanInsetsChanged = !this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets);
                            contentInsetsChanged = !this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets);
                            visibleInsetsChanged = !this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets);
                            stableInsetsChanged = !this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets);
                            cutoutChanged = !this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout);
                            boolean outsetsChanged2 = !this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
                            surfaceSizeChanged2 = (relayoutResult2 & 32) == 0;
                            surfaceChanged2 |= surfaceSizeChanged2;
                            alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars == this.mAttachInfo.mAlwaysConsumeSystemBars;
                            colorModeChanged = hasColorModeChanged(lp.getColorMode());
                            if (contentInsetsChanged) {
                            }
                            if (overscanInsetsChanged) {
                            }
                            if (stableInsetsChanged) {
                            }
                            if (cutoutChanged) {
                            }
                            if (alwaysConsumeSystemBarsChanged) {
                            }
                            if (!contentInsetsChanged) {
                            }
                            this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                            this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                            this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
                            this.mApplyInsetsRequested = false;
                            dispatchApplyInsets(host);
                            contentInsetsChanged = true;
                            if (visibleInsetsChanged) {
                            }
                            if (colorModeChanged) {
                                this.mAttachInfo.mThreadedRenderer.setWideGamut(lp.getColorMode() != 1);
                            }
                            if (hadSurface) {
                            }
                            if ((relayoutResult2 & 16) == 0) {
                            }
                            boolean dockedResizing2 = (relayoutResult2 & 8) == 0;
                            dragResizing = !freeformResizing || dockedResizing2;
                            if (this.mDragResizing != dragResizing) {
                            }
                            if (!this.mUseMTRenderer) {
                            }
                            updatedConfiguration22 = updatedConfiguration;
                            boolean surfaceSizeChanged3222 = surfaceSizeChanged2;
                            relayoutResult4 = relayoutResult3;
                            this.mAttachInfo.mWindowLeft = frame2.left;
                            this.mAttachInfo.mWindowTop = frame2.top;
                            if (this.mWidth == frame2.width()) {
                            }
                            this.mWidth = frame2.width();
                            this.mHeight = frame2.height();
                            if (this.mSurfaceHolder == null) {
                            }
                            threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                            if (threadedRenderer != null) {
                                threadedRenderer.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                                this.mNeedsRendererSetup = false;
                            }
                            if (this.mStopped) {
                            }
                            focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                            if (!focusChangedDueToTouchMode) {
                            }
                            int childWidthMeasureSpec222 = getRootMeasureSpec(this.mWidth, lp.width);
                            int childHeightMeasureSpec222 = getRootMeasureSpec(this.mHeight, lp.height);
                            performMeasure(childWidthMeasureSpec222, childHeightMeasureSpec222);
                            int width222 = host.getMeasuredWidth();
                            int height222 = host.getMeasuredHeight();
                            measureAgain = false;
                            relayoutResult5 = relayoutResult4;
                            if (lp.horizontalWeight > 0.0f) {
                            }
                            if (lp.verticalWeight > 0.0f) {
                            }
                            if (measureAgain) {
                            }
                            layoutRequested = true;
                            insetsPending = insetsPending2;
                            relayoutResult = relayoutResult5;
                            if (surfaceSizeChanged) {
                            }
                            didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                            triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                            if (didLayout) {
                            }
                            if (triggerGlobalLayoutListener) {
                            }
                            if (computesInternalInsets) {
                            }
                            if (this.mFirst) {
                            }
                            if (viewVisibilityChanged) {
                            }
                            if (this.mAttachInfo.mHasWindowFocus) {
                            }
                            if (hasWindowFocus) {
                            }
                            if (regainedFocus) {
                            }
                            if (!changedVisibility) {
                            }
                            if (this.mWindowAttributes != null) {
                            }
                            if (!isToast) {
                            }
                            this.mFirst = false;
                            this.mWillDrawSoon = false;
                            this.mNewSurfaceNeeded = false;
                            this.mActivityRelaunched = false;
                            this.mViewVisibility = viewVisibility;
                            this.mHadWindowFocus = hasWindowFocus;
                            if (hasWindowFocus) {
                            }
                            z2 = true;
                            if ((relayoutResult & 2) != 0) {
                            }
                            if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                            }
                            cancelDraw = z2;
                            if (cancelDraw) {
                            }
                            this.mIsInTraversal = false;
                        }
                        desiredWindowHeight4 = desiredWindowHeight3;
                        windowShouldResize = false;
                        boolean windowShouldResize222 = windowShouldResize | (!this.mDragResizing && this.mResizeMode == 0) | this.mActivityRelaunched;
                        computesInternalInsets = !this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
                        relayoutResult = 0;
                        boolean updatedConfiguration222 = false;
                        int surfaceGenerationId22 = this.mSurface.getGenerationId();
                        isViewVisible = viewVisibility != 0;
                        boolean windowRelayoutWasForced22 = this.mForceNextWindowRelayout;
                        if (this.mFirst) {
                        }
                        insetsPending = false;
                        frame2 = frame;
                        params3 = params2;
                        this.mForceNextWindowRelayout = false;
                        if (isViewVisible) {
                        }
                        if (this.mSurfaceHolder == null) {
                        }
                        contentInsetsChanged = false;
                        hadSurface = this.mSurface.isValid();
                        if (this.mAttachInfo.mThreadedRenderer == null) {
                        }
                        relayoutResult2 = relayoutWindow(params3, viewVisibility, insetsPending4);
                        if (!this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                        }
                        overscanInsetsChanged = !this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets);
                        contentInsetsChanged = !this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets);
                        visibleInsetsChanged = !this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets);
                        stableInsetsChanged = !this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets);
                        cutoutChanged = !this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout);
                        boolean outsetsChanged22 = !this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
                        surfaceSizeChanged2 = (relayoutResult2 & 32) == 0;
                        surfaceChanged2 |= surfaceSizeChanged2;
                        alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars == this.mAttachInfo.mAlwaysConsumeSystemBars;
                        colorModeChanged = hasColorModeChanged(lp.getColorMode());
                        if (contentInsetsChanged) {
                        }
                        if (overscanInsetsChanged) {
                        }
                        if (stableInsetsChanged) {
                        }
                        if (cutoutChanged) {
                        }
                        if (alwaysConsumeSystemBarsChanged) {
                        }
                        if (!contentInsetsChanged) {
                        }
                        this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                        this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                        this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
                        this.mApplyInsetsRequested = false;
                        dispatchApplyInsets(host);
                        contentInsetsChanged = true;
                        if (visibleInsetsChanged) {
                        }
                        if (colorModeChanged) {
                        }
                        if (hadSurface) {
                        }
                        if ((relayoutResult2 & 16) == 0) {
                        }
                        boolean dockedResizing22 = (relayoutResult2 & 8) == 0;
                        dragResizing = !freeformResizing || dockedResizing22;
                        if (this.mDragResizing != dragResizing) {
                        }
                        if (!this.mUseMTRenderer) {
                        }
                        updatedConfiguration222 = updatedConfiguration;
                        boolean surfaceSizeChanged32222 = surfaceSizeChanged2;
                        relayoutResult4 = relayoutResult3;
                        this.mAttachInfo.mWindowLeft = frame2.left;
                        this.mAttachInfo.mWindowTop = frame2.top;
                        if (this.mWidth == frame2.width()) {
                        }
                        this.mWidth = frame2.width();
                        this.mHeight = frame2.height();
                        if (this.mSurfaceHolder == null) {
                        }
                        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                        if (threadedRenderer != null) {
                        }
                        if (this.mStopped) {
                        }
                        focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                        if (!focusChangedDueToTouchMode) {
                        }
                        int childWidthMeasureSpec2222 = getRootMeasureSpec(this.mWidth, lp.width);
                        int childHeightMeasureSpec2222 = getRootMeasureSpec(this.mHeight, lp.height);
                        performMeasure(childWidthMeasureSpec2222, childHeightMeasureSpec2222);
                        int width2222 = host.getMeasuredWidth();
                        int height2222 = host.getMeasuredHeight();
                        measureAgain = false;
                        relayoutResult5 = relayoutResult4;
                        if (lp.horizontalWeight > 0.0f) {
                        }
                        if (lp.verticalWeight > 0.0f) {
                        }
                        if (measureAgain) {
                        }
                        layoutRequested = true;
                        insetsPending = insetsPending2;
                        relayoutResult = relayoutResult5;
                        if (surfaceSizeChanged) {
                        }
                        didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                        triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                        if (didLayout) {
                        }
                        if (triggerGlobalLayoutListener) {
                        }
                        if (computesInternalInsets) {
                        }
                        if (this.mFirst) {
                        }
                        if (viewVisibilityChanged) {
                        }
                        if (this.mAttachInfo.mHasWindowFocus) {
                        }
                        if (hasWindowFocus) {
                        }
                        if (regainedFocus) {
                        }
                        if (!changedVisibility) {
                        }
                        if (this.mWindowAttributes != null) {
                        }
                        if (!isToast) {
                        }
                        this.mFirst = false;
                        this.mWillDrawSoon = false;
                        this.mNewSurfaceNeeded = false;
                        this.mActivityRelaunched = false;
                        this.mViewVisibility = viewVisibility;
                        this.mHadWindowFocus = hasWindowFocus;
                        if (hasWindowFocus) {
                        }
                        z2 = true;
                        if ((relayoutResult & 2) != 0) {
                        }
                        if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                        }
                        cancelDraw = z2;
                        if (cancelDraw) {
                        }
                        this.mIsInTraversal = false;
                    }
                }
                params2 = params;
                desiredWindowHeight3 = desiredWindowHeight2;
                boolean windowSizeMayChange22 = windowSizeMayChange;
                if (layoutRequested) {
                }
                if (layoutRequested) {
                    if (this.mWidth == host.getMeasuredWidth()) {
                    }
                    desiredWindowHeight4 = desiredWindowHeight3;
                    windowShouldResize = true;
                    boolean windowShouldResize2222 = windowShouldResize | (!this.mDragResizing && this.mResizeMode == 0) | this.mActivityRelaunched;
                    computesInternalInsets = !this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
                    relayoutResult = 0;
                    boolean updatedConfiguration2222 = false;
                    int surfaceGenerationId222 = this.mSurface.getGenerationId();
                    isViewVisible = viewVisibility != 0;
                    boolean windowRelayoutWasForced222 = this.mForceNextWindowRelayout;
                    if (this.mFirst) {
                    }
                    insetsPending = false;
                    frame2 = frame;
                    params3 = params2;
                    this.mForceNextWindowRelayout = false;
                    if (isViewVisible) {
                    }
                    if (this.mSurfaceHolder == null) {
                    }
                    contentInsetsChanged = false;
                    hadSurface = this.mSurface.isValid();
                    if (this.mAttachInfo.mThreadedRenderer == null) {
                    }
                    relayoutResult2 = relayoutWindow(params3, viewVisibility, insetsPending4);
                    if (!this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                    }
                    overscanInsetsChanged = !this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets);
                    contentInsetsChanged = !this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets);
                    visibleInsetsChanged = !this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets);
                    stableInsetsChanged = !this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets);
                    cutoutChanged = !this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout);
                    boolean outsetsChanged222 = !this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
                    surfaceSizeChanged2 = (relayoutResult2 & 32) == 0;
                    surfaceChanged2 |= surfaceSizeChanged2;
                    alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars == this.mAttachInfo.mAlwaysConsumeSystemBars;
                    colorModeChanged = hasColorModeChanged(lp.getColorMode());
                    if (contentInsetsChanged) {
                    }
                    if (overscanInsetsChanged) {
                    }
                    if (stableInsetsChanged) {
                    }
                    if (cutoutChanged) {
                    }
                    if (alwaysConsumeSystemBarsChanged) {
                    }
                    if (!contentInsetsChanged) {
                    }
                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                    this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                    this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
                    this.mApplyInsetsRequested = false;
                    dispatchApplyInsets(host);
                    contentInsetsChanged = true;
                    if (visibleInsetsChanged) {
                    }
                    if (colorModeChanged) {
                    }
                    if (hadSurface) {
                    }
                    if ((relayoutResult2 & 16) == 0) {
                    }
                    boolean dockedResizing222 = (relayoutResult2 & 8) == 0;
                    dragResizing = !freeformResizing || dockedResizing222;
                    if (this.mDragResizing != dragResizing) {
                    }
                    if (!this.mUseMTRenderer) {
                    }
                    updatedConfiguration2222 = updatedConfiguration;
                    boolean surfaceSizeChanged322222 = surfaceSizeChanged2;
                    relayoutResult4 = relayoutResult3;
                    this.mAttachInfo.mWindowLeft = frame2.left;
                    this.mAttachInfo.mWindowTop = frame2.top;
                    if (this.mWidth == frame2.width()) {
                    }
                    this.mWidth = frame2.width();
                    this.mHeight = frame2.height();
                    if (this.mSurfaceHolder == null) {
                    }
                    threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                    if (threadedRenderer != null) {
                    }
                    if (this.mStopped) {
                    }
                    focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                    if (!focusChangedDueToTouchMode) {
                    }
                    int childWidthMeasureSpec22222 = getRootMeasureSpec(this.mWidth, lp.width);
                    int childHeightMeasureSpec22222 = getRootMeasureSpec(this.mHeight, lp.height);
                    performMeasure(childWidthMeasureSpec22222, childHeightMeasureSpec22222);
                    int width22222 = host.getMeasuredWidth();
                    int height22222 = host.getMeasuredHeight();
                    measureAgain = false;
                    relayoutResult5 = relayoutResult4;
                    if (lp.horizontalWeight > 0.0f) {
                    }
                    if (lp.verticalWeight > 0.0f) {
                    }
                    if (measureAgain) {
                    }
                    layoutRequested = true;
                    insetsPending = insetsPending2;
                    relayoutResult = relayoutResult5;
                    if (surfaceSizeChanged) {
                    }
                    didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                    triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                    if (didLayout) {
                    }
                    if (triggerGlobalLayoutListener) {
                    }
                    if (computesInternalInsets) {
                    }
                    if (this.mFirst) {
                    }
                    if (viewVisibilityChanged) {
                    }
                    if (this.mAttachInfo.mHasWindowFocus) {
                    }
                    if (hasWindowFocus) {
                    }
                    if (regainedFocus) {
                    }
                    if (!changedVisibility) {
                    }
                    if (this.mWindowAttributes != null) {
                    }
                    if (!isToast) {
                    }
                    this.mFirst = false;
                    this.mWillDrawSoon = false;
                    this.mNewSurfaceNeeded = false;
                    this.mActivityRelaunched = false;
                    this.mViewVisibility = viewVisibility;
                    this.mHadWindowFocus = hasWindowFocus;
                    if (hasWindowFocus) {
                    }
                    z2 = true;
                    if ((relayoutResult & 2) != 0) {
                    }
                    if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                    }
                    cancelDraw = z2;
                    if (cancelDraw) {
                    }
                    this.mIsInTraversal = false;
                }
                desiredWindowHeight4 = desiredWindowHeight3;
                windowShouldResize = false;
                boolean windowShouldResize22222 = windowShouldResize | (!this.mDragResizing && this.mResizeMode == 0) | this.mActivityRelaunched;
                computesInternalInsets = !this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
                relayoutResult = 0;
                boolean updatedConfiguration22222 = false;
                int surfaceGenerationId2222 = this.mSurface.getGenerationId();
                isViewVisible = viewVisibility != 0;
                boolean windowRelayoutWasForced2222 = this.mForceNextWindowRelayout;
                if (this.mFirst) {
                }
                insetsPending = false;
                frame2 = frame;
                params3 = params2;
                this.mForceNextWindowRelayout = false;
                if (isViewVisible) {
                }
                if (this.mSurfaceHolder == null) {
                }
                contentInsetsChanged = false;
                hadSurface = this.mSurface.isValid();
                if (this.mAttachInfo.mThreadedRenderer == null) {
                }
                relayoutResult2 = relayoutWindow(params3, viewVisibility, insetsPending4);
                if (!this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
                }
                overscanInsetsChanged = !this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets);
                contentInsetsChanged = !this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets);
                visibleInsetsChanged = !this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets);
                stableInsetsChanged = !this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets);
                cutoutChanged = !this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout);
                boolean outsetsChanged2222 = !this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
                surfaceSizeChanged2 = (relayoutResult2 & 32) == 0;
                surfaceChanged2 |= surfaceSizeChanged2;
                alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars == this.mAttachInfo.mAlwaysConsumeSystemBars;
                colorModeChanged = hasColorModeChanged(lp.getColorMode());
                if (contentInsetsChanged) {
                }
                if (overscanInsetsChanged) {
                }
                if (stableInsetsChanged) {
                }
                if (cutoutChanged) {
                }
                if (alwaysConsumeSystemBarsChanged) {
                }
                if (!contentInsetsChanged) {
                }
                this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
                this.mApplyInsetsRequested = false;
                dispatchApplyInsets(host);
                contentInsetsChanged = true;
                if (visibleInsetsChanged) {
                }
                if (colorModeChanged) {
                }
                if (hadSurface) {
                }
                if ((relayoutResult2 & 16) == 0) {
                }
                boolean dockedResizing2222 = (relayoutResult2 & 8) == 0;
                dragResizing = !freeformResizing || dockedResizing2222;
                if (this.mDragResizing != dragResizing) {
                }
                if (!this.mUseMTRenderer) {
                }
                updatedConfiguration22222 = updatedConfiguration;
                boolean surfaceSizeChanged3222222 = surfaceSizeChanged2;
                relayoutResult4 = relayoutResult3;
                this.mAttachInfo.mWindowLeft = frame2.left;
                this.mAttachInfo.mWindowTop = frame2.top;
                if (this.mWidth == frame2.width()) {
                }
                this.mWidth = frame2.width();
                this.mHeight = frame2.height();
                if (this.mSurfaceHolder == null) {
                }
                threadedRenderer = this.mAttachInfo.mThreadedRenderer;
                if (threadedRenderer != null) {
                }
                if (this.mStopped) {
                }
                focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
                if (!focusChangedDueToTouchMode) {
                }
                int childWidthMeasureSpec222222 = getRootMeasureSpec(this.mWidth, lp.width);
                int childHeightMeasureSpec222222 = getRootMeasureSpec(this.mHeight, lp.height);
                performMeasure(childWidthMeasureSpec222222, childHeightMeasureSpec222222);
                int width222222 = host.getMeasuredWidth();
                int height222222 = host.getMeasuredHeight();
                measureAgain = false;
                relayoutResult5 = relayoutResult4;
                if (lp.horizontalWeight > 0.0f) {
                }
                if (lp.verticalWeight > 0.0f) {
                }
                if (measureAgain) {
                }
                layoutRequested = true;
                insetsPending = insetsPending2;
                relayoutResult = relayoutResult5;
                if (surfaceSizeChanged) {
                }
                didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
                triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
                if (didLayout) {
                }
                if (triggerGlobalLayoutListener) {
                }
                if (computesInternalInsets) {
                }
                if (this.mFirst) {
                }
                if (viewVisibilityChanged) {
                }
                if (this.mAttachInfo.mHasWindowFocus) {
                }
                if (hasWindowFocus) {
                }
                if (regainedFocus) {
                }
                if (!changedVisibility) {
                }
                if (this.mWindowAttributes != null) {
                }
                if (!isToast) {
                }
                this.mFirst = false;
                this.mWillDrawSoon = false;
                this.mNewSurfaceNeeded = false;
                this.mActivityRelaunched = false;
                this.mViewVisibility = viewVisibility;
                this.mHadWindowFocus = hasWindowFocus;
                if (hasWindowFocus) {
                }
                z2 = true;
                if ((relayoutResult & 2) != 0) {
                }
                if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
                }
                cancelDraw = z2;
                if (cancelDraw) {
                }
                this.mIsInTraversal = false;
            }
        }
        z = false;
        boolean viewUserVisibilityChanged2 = z;
        WindowManager.LayoutParams params42 = null;
        if (this.mWindowAttributesChanged) {
        }
        boolean surfaceChanged22 = surfaceChanged;
        compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        if (compatibilityInfo.supportsScreen() == this.mLastInCompatMode) {
        }
        WindowManager.LayoutParams params52 = params42;
        this.mWindowAttributesChangesFlag = 0;
        Rect frame32 = this.mWinFrame;
        if (this.mFirst) {
        }
        if (viewVisibilityChanged) {
        }
        if (this.mAttachInfo.mWindowVisibility != 0) {
        }
        getRunQueue().executeActions(this.mAttachInfo.mHandler);
        layoutRequested = !this.mLayoutRequested && (!this.mStopped || this.mReportNextDraw);
        if (layoutRequested) {
        }
        if (collectViewAttributes()) {
        }
        if (this.mAttachInfo.mForceReportNewAttributes) {
        }
        if (!this.mFirst) {
        }
        this.mAttachInfo.mViewVisibilityChanged = false;
        resizeMode = this.mSoftInputMode & 240;
        if (resizeMode == 0) {
        }
        params = params52;
        if (params != null) {
        }
        if (this.mApplyInsetsRequested) {
        }
        params2 = params;
        desiredWindowHeight3 = desiredWindowHeight2;
        boolean windowSizeMayChange222 = windowSizeMayChange;
        if (layoutRequested) {
        }
        if (layoutRequested) {
        }
        desiredWindowHeight4 = desiredWindowHeight3;
        windowShouldResize = false;
        boolean windowShouldResize222222 = windowShouldResize | (!this.mDragResizing && this.mResizeMode == 0) | this.mActivityRelaunched;
        computesInternalInsets = !this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
        relayoutResult = 0;
        boolean updatedConfiguration222222 = false;
        int surfaceGenerationId22222 = this.mSurface.getGenerationId();
        isViewVisible = viewVisibility != 0;
        boolean windowRelayoutWasForced22222 = this.mForceNextWindowRelayout;
        if (this.mFirst) {
        }
        insetsPending = false;
        frame2 = frame;
        params3 = params2;
        this.mForceNextWindowRelayout = false;
        if (isViewVisible) {
        }
        if (this.mSurfaceHolder == null) {
        }
        contentInsetsChanged = false;
        hadSurface = this.mSurface.isValid();
        if (this.mAttachInfo.mThreadedRenderer == null) {
        }
        relayoutResult2 = relayoutWindow(params3, viewVisibility, insetsPending4);
        if (!this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration)) {
        }
        overscanInsetsChanged = !this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets);
        contentInsetsChanged = !this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets);
        visibleInsetsChanged = !this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets);
        stableInsetsChanged = !this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets);
        cutoutChanged = !this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout);
        boolean outsetsChanged22222 = !this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
        surfaceSizeChanged2 = (relayoutResult2 & 32) == 0;
        surfaceChanged22 |= surfaceSizeChanged2;
        alwaysConsumeSystemBarsChanged = this.mPendingAlwaysConsumeSystemBars == this.mAttachInfo.mAlwaysConsumeSystemBars;
        colorModeChanged = hasColorModeChanged(lp.getColorMode());
        if (contentInsetsChanged) {
        }
        if (overscanInsetsChanged) {
        }
        if (stableInsetsChanged) {
        }
        if (cutoutChanged) {
        }
        if (alwaysConsumeSystemBarsChanged) {
        }
        if (!contentInsetsChanged) {
        }
        this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
        this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
        this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
        this.mApplyInsetsRequested = false;
        dispatchApplyInsets(host);
        contentInsetsChanged = true;
        if (visibleInsetsChanged) {
        }
        if (colorModeChanged) {
        }
        if (hadSurface) {
        }
        if ((relayoutResult2 & 16) == 0) {
        }
        boolean dockedResizing22222 = (relayoutResult2 & 8) == 0;
        dragResizing = !freeformResizing || dockedResizing22222;
        if (this.mDragResizing != dragResizing) {
        }
        if (!this.mUseMTRenderer) {
        }
        updatedConfiguration222222 = updatedConfiguration;
        boolean surfaceSizeChanged32222222 = surfaceSizeChanged2;
        relayoutResult4 = relayoutResult3;
        this.mAttachInfo.mWindowLeft = frame2.left;
        this.mAttachInfo.mWindowTop = frame2.top;
        if (this.mWidth == frame2.width()) {
        }
        this.mWidth = frame2.width();
        this.mHeight = frame2.height();
        if (this.mSurfaceHolder == null) {
        }
        threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
        }
        if (this.mStopped) {
        }
        focusChangedDueToTouchMode = ensureTouchModeLocally((relayoutResult4 & 1) == 0);
        if (!focusChangedDueToTouchMode) {
        }
        int childWidthMeasureSpec2222222 = getRootMeasureSpec(this.mWidth, lp.width);
        int childHeightMeasureSpec2222222 = getRootMeasureSpec(this.mHeight, lp.height);
        performMeasure(childWidthMeasureSpec2222222, childHeightMeasureSpec2222222);
        int width2222222 = host.getMeasuredWidth();
        int height2222222 = host.getMeasuredHeight();
        measureAgain = false;
        relayoutResult5 = relayoutResult4;
        if (lp.horizontalWeight > 0.0f) {
        }
        if (lp.verticalWeight > 0.0f) {
        }
        if (measureAgain) {
        }
        layoutRequested = true;
        insetsPending = insetsPending2;
        relayoutResult = relayoutResult5;
        if (surfaceSizeChanged) {
        }
        didLayout = !layoutRequested && (!this.mStopped || this.mReportNextDraw);
        triggerGlobalLayoutListener = !didLayout || this.mAttachInfo.mRecomputeGlobalAttributes;
        if (didLayout) {
        }
        if (triggerGlobalLayoutListener) {
        }
        if (computesInternalInsets) {
        }
        if (this.mFirst) {
        }
        if (viewVisibilityChanged) {
        }
        if (this.mAttachInfo.mHasWindowFocus) {
        }
        if (hasWindowFocus) {
        }
        if (regainedFocus) {
        }
        if (!changedVisibility) {
        }
        if (this.mWindowAttributes != null) {
        }
        if (!isToast) {
        }
        this.mFirst = false;
        this.mWillDrawSoon = false;
        this.mNewSurfaceNeeded = false;
        this.mActivityRelaunched = false;
        this.mViewVisibility = viewVisibility;
        this.mHadWindowFocus = hasWindowFocus;
        if (hasWindowFocus) {
        }
        z2 = true;
        if ((relayoutResult & 2) != 0) {
        }
        if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
        }
        cancelDraw = z2;
        if (cancelDraw) {
        }
        this.mIsInTraversal = false;
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

    /* JADX INFO: Access modifiers changed from: private */
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

    /* JADX INFO: Access modifiers changed from: private */
    public void handleWindowFocusChanged() {
        synchronized (this) {
            if (this.mWindowFocusChanged) {
                this.mWindowFocusChanged = false;
                boolean hasWindowFocus = this.mUpcomingWindowFocus;
                boolean inTouchMode = this.mUpcomingInTouchMode;
                if (sNewInsetsMode != 0) {
                    if (hasWindowFocus) {
                        this.mInsetsController.onWindowFocusGained();
                    } else {
                        this.mInsetsController.onWindowFocusLost();
                    }
                }
                if (this.mAdded) {
                    profileRendering(hasWindowFocus);
                    if (hasWindowFocus) {
                        ensureTouchModeLocally(inTouchMode);
                        if (this.mAttachInfo.mThreadedRenderer != null && this.mSurface.isValid()) {
                            this.mFullRedrawNeeded = true;
                            try {
                                WindowManager.LayoutParams lp = this.mWindowAttributes;
                                Rect surfaceInsets = lp != null ? lp.surfaceInsets : null;
                                this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, surfaceInsets);
                            } catch (Surface.OutOfResourcesException e) {
                                Log.m69e(this.mTag, "OutOfResourcesException locking surface", e);
                                try {
                                    if (!this.mWindowSession.outOfMemory(this.mWindow)) {
                                        Slog.m50w(this.mTag, "No processes killed for memory; killing self");
                                        Process.killProcess(Process.myPid());
                                    }
                                } catch (RemoteException e2) {
                                }
                                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(6), 500L);
                                return;
                            }
                        }
                    }
                    this.mAttachInfo.mHasWindowFocus = hasWindowFocus;
                    this.mLastWasImTarget = WindowManager.LayoutParams.mayUseInputMethod(this.mWindowAttributes.flags);
                    InputMethodManager imm = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                    if (imm != null && this.mLastWasImTarget && !isInLocalFocusMode()) {
                        imm.onPreWindowFocus(this.mView, hasWindowFocus);
                    }
                    if (this.mView != null) {
                        this.mAttachInfo.mKeyDispatchState.reset();
                        this.mView.dispatchWindowFocusChanged(hasWindowFocus);
                        this.mAttachInfo.mTreeObserver.dispatchOnWindowFocusChange(hasWindowFocus);
                        if (this.mAttachInfo.mTooltipHost != null) {
                            this.mAttachInfo.mTooltipHost.hideTooltip();
                        }
                    }
                    if (hasWindowFocus) {
                        if (imm != null && this.mLastWasImTarget && !isInLocalFocusMode()) {
                            imm.onPostWindowFocus(this.mView, this.mView.findFocus(), this.mWindowAttributes.softInputMode, !this.mHasHadWindowFocus, this.mWindowAttributes.flags);
                        }
                        this.mWindowAttributes.softInputMode &= TrafficStats.TAG_NETWORK_STACK_RANGE_END;
                        ((WindowManager.LayoutParams) this.mView.getLayoutParams()).softInputMode &= TrafficStats.TAG_NETWORK_STACK_RANGE_END;
                        this.mHasHadWindowFocus = true;
                        fireAccessibilityFocusEventIfHasFocusedNode();
                    } else if (this.mPointerCapture) {
                        handlePointerCaptureChanged(false);
                    }
                }
                this.mFirstInputStage.onWindowFocusChanged(hasWindowFocus);
            }
        }
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        View focusedView;
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || (focusedView = this.mView.findFocus()) == null) {
            return;
        }
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

    private AccessibilityNodeInfo findFocusedVirtualNode(AccessibilityNodeProvider provider) {
        AccessibilityNodeInfo focusedNode = provider.findFocus(1);
        if (focusedNode != null) {
            return focusedNode;
        }
        if (this.mContext.isAutofillCompatibilityEnabled()) {
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
                        int virtualId = AccessibilityNodeInfo.getVirtualDescendantId(childNodeIds.get(i));
                        AccessibilityNodeInfo child = provider.createAccessibilityNodeInfo(virtualId);
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
        return null;
    }

    private void handleOutOfResourcesException(Surface.OutOfResourcesException e) {
        Log.m69e(this.mTag, "OutOfResourcesException initializing HW surface", e);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && Process.myUid() != 1000) {
                Slog.m50w(this.mTag, "No processes killed for memory; killing self");
                Process.killProcess(Process.myPid());
            }
        } catch (RemoteException e2) {
        }
        this.mLayoutRequested = true;
    }

    private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
        if (this.mView == null) {
            return;
        }
        Trace.traceBegin(8L, "measure");
        try {
            this.mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } finally {
            Trace.traceEnd(8L);
        }
    }

    boolean isInLayout() {
        return this.mInLayout;
    }

    boolean requestLayoutDuringLayout(View view) {
        if (view.mParent == null || view.mAttachInfo == null) {
            return true;
        }
        if (!this.mLayoutRequesters.contains(view)) {
            this.mLayoutRequesters.add(view);
        }
        return !this.mHandlingLayoutInLayoutRequest;
    }

    private void performLayout(WindowManager.LayoutParams lp, int desiredWindowWidth, int desiredWindowHeight) {
        ArrayList<View> validLayoutRequesters;
        this.mLayoutRequested = false;
        this.mScrollMayChange = true;
        this.mInLayout = true;
        View host = this.mView;
        if (host == null) {
            return;
        }
        Trace.traceBegin(8L, TtmlUtils.TAG_LAYOUT);
        try {
            host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
            this.mInLayout = false;
            int numViewsRequestingLayout = this.mLayoutRequesters.size();
            if (numViewsRequestingLayout > 0 && (validLayoutRequesters = getValidLayoutRequesters(this.mLayoutRequesters, false)) != null) {
                this.mHandlingLayoutInLayoutRequest = true;
                int numValidRequests = validLayoutRequesters.size();
                for (int i = 0; i < numValidRequests; i++) {
                    View view = validLayoutRequesters.get(i);
                    Log.m64w("View", "requestLayout() improperly called by " + view + " during layout: running second layout pass");
                    view.requestLayout();
                }
                measureHierarchy(host, lp, this.mView.getContext().getResources(), desiredWindowWidth, desiredWindowHeight);
                this.mInLayout = true;
                host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
                this.mHandlingLayoutInLayoutRequest = false;
                final ArrayList<View> validLayoutRequesters2 = getValidLayoutRequesters(this.mLayoutRequesters, true);
                if (validLayoutRequesters2 != null) {
                    getRunQueue().post(new Runnable() { // from class: android.view.ViewRootImpl.2
                        @Override // java.lang.Runnable
                        public void run() {
                            int numValidRequests2 = validLayoutRequesters2.size();
                            for (int i2 = 0; i2 < numValidRequests2; i2++) {
                                View view2 = (View) validLayoutRequesters2.get(i2);
                                Log.m64w("View", "requestLayout() improperly called by " + view2 + " during second layout pass: posting in next frame");
                                view2.requestLayout();
                            }
                        }
                    });
                }
            }
            Trace.traceEnd(8L);
            this.mInLayout = false;
        } catch (Throwable th) {
            Trace.traceEnd(8L);
            throw th;
        }
    }

    private ArrayList<View> getValidLayoutRequesters(ArrayList<View> layoutRequesters, boolean secondLayoutRequests) {
        int numViewsRequestingLayout = layoutRequesters.size();
        int i = 0;
        ArrayList<View> validLayoutRequesters = null;
        for (int i2 = 0; i2 < numViewsRequestingLayout; i2++) {
            View view = layoutRequesters.get(i2);
            if (view != null && view.mAttachInfo != null && view.mParent != null && (secondLayoutRequests || (view.mPrivateFlags & 4096) == 4096)) {
                boolean gone = false;
                View parent = view;
                while (true) {
                    if (parent == null) {
                        break;
                    } else if ((parent.mViewFlags & 12) == 8) {
                        gone = true;
                        break;
                    } else if (parent.mParent instanceof View) {
                        parent = (View) parent.mParent;
                    } else {
                        parent = null;
                    }
                }
                if (!gone) {
                    if (validLayoutRequesters == null) {
                        validLayoutRequesters = new ArrayList<>();
                    }
                    validLayoutRequesters.add(view);
                }
            }
        }
        if (!secondLayoutRequests) {
            while (true) {
                int i3 = i;
                if (i3 >= numViewsRequestingLayout) {
                    break;
                }
                View view2 = layoutRequesters.get(i3);
                while (view2 != null && (view2.mPrivateFlags & 4096) != 0) {
                    view2.mPrivateFlags &= -4097;
                    if (view2.mParent instanceof View) {
                        view2 = (View) view2.mParent;
                    } else {
                        view2 = null;
                    }
                }
                i = i3 + 1;
            }
        }
        layoutRequesters.clear();
        return validLayoutRequesters;
    }

    @Override // android.view.ViewParent
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
                int measureSpec = View.MeasureSpec.makeMeasureSpec(windowSize, Integer.MIN_VALUE);
                return measureSpec;
            case -1:
                int measureSpec2 = View.MeasureSpec.makeMeasureSpec(windowSize, 1073741824);
                return measureSpec2;
            default:
                int measureSpec3 = View.MeasureSpec.makeMeasureSpec(rootDimension, 1073741824);
                return measureSpec3;
        }
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPreDraw(RecordingCanvas canvas) {
        if (this.mCurScrollY != 0 && this.mHardwareYOffset != 0 && this.mAttachInfo.mThreadedRenderer.isOpaque()) {
            canvas.drawColor(-16777216);
        }
        canvas.translate(-this.mHardwareXOffset, -this.mHardwareYOffset);
    }

    @Override // android.view.ThreadedRenderer.DrawCallbacks
    public void onPostDraw(RecordingCanvas canvas) {
        drawAccessibilityFocusedDrawableIfNeeded(canvas);
        if (this.mUseMTRenderer) {
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                this.mWindowCallbacks.get(i).onPostDraw(canvas);
            }
        }
    }

    void outputDisplayList(View view) {
        view.mRenderNode.output();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void profileRendering(boolean enabled) {
        if (this.mProfileRendering) {
            this.mRenderProfilingEnabled = enabled;
            if (this.mRenderProfiler != null) {
                this.mChoreographer.removeFrameCallback(this.mRenderProfiler);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler = new Choreographer.FrameCallback() { // from class: android.view.ViewRootImpl.3
                        @Override // android.view.Choreographer.FrameCallback
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
        long frameTime = nowTime - this.mFpsPrevTime;
        long totalTime = nowTime - this.mFpsStartTime;
        Log.m66v(this.mTag, "0x" + thisHash + "\tFrame time:\t" + frameTime);
        this.mFpsPrevTime = nowTime;
        if (totalTime > 1000) {
            float fps = (this.mFpsNumFrames * 1000.0f) / ((float) totalTime);
            Log.m66v(this.mTag, "0x" + thisHash + "\tFPS:\t" + fps);
            this.mFpsStartTime = nowTime;
            this.mFpsNumFrames = 0;
        }
    }

    void drawPending() {
        this.mDrawsNeededToReport++;
    }

    void pendingDrawFinished() {
        if (this.mDrawsNeededToReport == 0) {
            throw new RuntimeException("Unbalanced drawPending/pendingDrawFinished calls");
        }
        this.mDrawsNeededToReport--;
        if (this.mDrawsNeededToReport == 0) {
            reportDrawFinished();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    private void performDraw() {
        if ((this.mAttachInfo.mDisplayState == 1 && !this.mReportNextDraw) || this.mView == null) {
            return;
        }
        boolean fullRedrawNeeded = this.mFullRedrawNeeded || this.mReportNextDraw;
        this.mFullRedrawNeeded = false;
        this.mIsDrawing = true;
        Trace.traceBegin(8L, "draw");
        boolean usingAsyncReport = false;
        if (this.mAttachInfo.mThreadedRenderer != null && this.mAttachInfo.mThreadedRenderer.isEnabled()) {
            final ArrayList<Runnable> commitCallbacks = this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks();
            if (this.mReportNextDraw) {
                usingAsyncReport = true;
                final Handler handler = this.mAttachInfo.mHandler;
                this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(new HardwareRenderer.FrameCompleteCallback() { // from class: android.view.-$$Lambda$ViewRootImpl$YBiqAhbCbXVPSKdbE3K4rH2gpxI
                    @Override // android.graphics.HardwareRenderer.FrameCompleteCallback
                    public final void onFrameComplete(long j) {
                        handler.postAtFrontOfQueue(new Runnable() { // from class: android.view.-$$Lambda$ViewRootImpl$7A_3tkr_Kw4TZAeIUGVlOoTcZhg
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.lambda$performDraw$1(ViewRootImpl.this, r2);
                            }
                        });
                    }
                });
            } else if (commitCallbacks != null && commitCallbacks.size() > 0) {
                final Handler handler2 = this.mAttachInfo.mHandler;
                this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(new HardwareRenderer.FrameCompleteCallback() { // from class: android.view.-$$Lambda$ViewRootImpl$zlBUjCwDtoAWMNaHI62DIq-eKFY
                    @Override // android.graphics.HardwareRenderer.FrameCompleteCallback
                    public final void onFrameComplete(long j) {
                        Handler.this.postAtFrontOfQueue(new Runnable() { // from class: android.view.-$$Lambda$ViewRootImpl$-dgEKMWLAJVMlaVy41safRlNQBo
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.lambda$performDraw$3(r1);
                            }
                        });
                    }
                });
            }
        }
        try {
            boolean canUseAsync = draw(fullRedrawNeeded);
            if (usingAsyncReport && !canUseAsync) {
                this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(null);
                usingAsyncReport = false;
            }
            this.mIsDrawing = false;
            Trace.traceEnd(8L);
            if (this.mAttachInfo.mPendingAnimatingRenderNodes != null) {
                int count = this.mAttachInfo.mPendingAnimatingRenderNodes.size();
                for (int i = 0; i < count; i++) {
                    this.mAttachInfo.mPendingAnimatingRenderNodes.get(i).endAllAnimators();
                }
                this.mAttachInfo.mPendingAnimatingRenderNodes.clear();
            }
            if (this.mReportNextDraw) {
                this.mReportNextDraw = false;
                if (this.mWindowDrawCountDown != null) {
                    try {
                        this.mWindowDrawCountDown.await();
                    } catch (InterruptedException e) {
                        Log.m70e(this.mTag, "Window redraw count down interrupted!");
                    }
                    this.mWindowDrawCountDown = null;
                }
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
                }
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    SurfaceCallbackHelper sch = new SurfaceCallbackHelper(new Runnable() { // from class: android.view.-$$Lambda$ViewRootImpl$dznxCZGM2R1fsBljsJKomLjBRoM
                        @Override // java.lang.Runnable
                        public final void run() {
                            ViewRootImpl.this.postDrawFinished();
                        }
                    });
                    SurfaceHolder.Callback[] callbacks = this.mSurfaceHolder.getCallbacks();
                    sch.dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, callbacks);
                } else if (!usingAsyncReport) {
                    if (this.mAttachInfo.mThreadedRenderer != null) {
                        this.mAttachInfo.mThreadedRenderer.fence();
                    }
                    pendingDrawFinished();
                }
            }
        } catch (Throwable th) {
            this.mIsDrawing = false;
            Trace.traceEnd(8L);
            throw th;
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
        if (surface.isValid()) {
            if (!sFirstDrawComplete) {
                synchronized (sFirstDrawHandlers) {
                    sFirstDrawComplete = true;
                    int count = sFirstDrawHandlers.size();
                    for (int i = 0; i < count; i++) {
                        this.mHandler.post(sFirstDrawHandlers.get(i));
                    }
                }
            }
            scrollToRectOrFocus(null, false);
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
            int curScrollY3 = this.mCurScrollY;
            if (curScrollY3 != curScrollY2) {
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
                dirty.set(0, 0, (int) ((this.mWidth * appScale) + 0.5f), (int) ((this.mHeight * appScale) + 0.5f));
            }
            this.mAttachInfo.mTreeObserver.dispatchOnDraw();
            int xOffset = -this.mCanvasOffsetX;
            int yOffset = (-this.mCanvasOffsetY) + curScrollY2;
            WindowManager.LayoutParams params = this.mWindowAttributes;
            Rect surfaceInsets = params != null ? params.surfaceInsets : null;
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
                boolean hasFocus = getAccessibilityFocusedRect(bounds);
                if (!hasFocus) {
                    bounds.setEmpty();
                }
                if (!bounds.equals(drawable2.getBounds())) {
                    accessibilityFocusDirty = true;
                }
            }
            boolean accessibilityFocusDirty2 = accessibilityFocusDirty;
            this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / TimeUtils.NANOS_PER_MS;
            boolean useAsyncReport = false;
            if (!dirty.isEmpty() || this.mIsAnimating || accessibilityFocusDirty2) {
                if (this.mAttachInfo.mThreadedRenderer == null || !this.mAttachInfo.mThreadedRenderer.isEnabled()) {
                    if (this.mAttachInfo.mThreadedRenderer != null && !this.mAttachInfo.mThreadedRenderer.isEnabled() && this.mAttachInfo.mThreadedRenderer.isRequested() && this.mSurface.isValid()) {
                        try {
                        } catch (Surface.OutOfResourcesException e) {
                            e = e;
                        }
                        try {
                            this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, surfaceInsets);
                            this.mFullRedrawNeeded = true;
                            scheduleTraversals();
                            return false;
                        } catch (Surface.OutOfResourcesException e2) {
                            e = e2;
                            handleOutOfResourcesException(e);
                            return false;
                        }
                    } else if (!drawSoftware(surface, this.mAttachInfo, xOffset2, yOffset2, scalingRequired, dirty, surfaceInsets)) {
                        return false;
                    }
                } else {
                    boolean invalidateRoot = accessibilityFocusDirty2 || this.mInvalidateRootRequested;
                    this.mInvalidateRootRequested = false;
                    this.mIsAnimating = false;
                    if (this.mHardwareYOffset != yOffset2 || this.mHardwareXOffset != xOffset2) {
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
                    this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
                }
            }
            if (animating) {
                this.mFullRedrawNeeded = true;
                scheduleTraversals();
            }
            return useAsyncReport;
        }
        return false;
    }

    private boolean drawSoftware(Surface surface, View.AttachInfo attachInfo, int xoff, int yoff, boolean scalingRequired, Rect dirty, Rect surfaceInsets) {
        int dirtyXOffset = xoff;
        int dirtyYOffset = yoff;
        if (surfaceInsets != null) {
            dirtyXOffset += surfaceInsets.left;
            dirtyYOffset += surfaceInsets.top;
        }
        int dirtyYOffset2 = dirtyYOffset;
        int dirtyXOffset2 = dirtyXOffset;
        try {
            dirty.offset(-dirtyXOffset2, -dirtyYOffset2);
            int i = dirty.left;
            int i2 = dirty.top;
            int i3 = dirty.right;
            int i4 = dirty.bottom;
            Canvas canvas = this.mSurface.lockCanvas(dirty);
            canvas.setDensity(this.mDensity);
            try {
                if (!canvas.isOpaque() || yoff != 0 || xoff != 0) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                }
                dirty.setEmpty();
                this.mIsAnimating = false;
                this.mView.mPrivateFlags |= 32;
                canvas.translate(-xoff, -yoff);
                if (this.mTranslator != null) {
                    this.mTranslator.translateCanvas(canvas);
                }
                canvas.setScreenDensity(scalingRequired ? this.mNoncompatDensity : 0);
                this.mView.draw(canvas);
                drawAccessibilityFocusedDrawableIfNeeded(canvas);
                surface.unlockCanvasAndPost(canvas);
                return true;
            } catch (IllegalArgumentException e) {
                Log.m69e(this.mTag, "Could not unlock surface", e);
                this.mLayoutRequested = true;
                return false;
            }
        } catch (Surface.OutOfResourcesException e2) {
            handleOutOfResourcesException(e2);
            return false;
        } catch (IllegalArgumentException e3) {
            Log.m69e(this.mTag, "Could not lock surface", e3);
            this.mLayoutRequested = true;
            return false;
        } finally {
            dirty.offset(dirtyXOffset2, dirtyYOffset2);
        }
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(Canvas canvas) {
        Rect bounds = this.mAttachInfo.mTmpInvalRect;
        if (!getAccessibilityFocusedRect(bounds)) {
            if (this.mAttachInfo.mAccessibilityFocusDrawable != null) {
                this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
                return;
            }
            return;
        }
        Drawable drawable = getAccessibilityFocusedDrawable();
        if (drawable != null) {
            drawable.setBounds(bounds);
            drawable.draw(canvas);
        }
    }

    private boolean getAccessibilityFocusedRect(Rect bounds) {
        View host;
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mView.mContext);
        if (!manager.isEnabled() || !manager.isTouchExplorationEnabled() || (host = this.mAccessibilityFocusedHost) == null || host.mAttachInfo == null) {
            return false;
        }
        AccessibilityNodeProvider provider = host.getAccessibilityNodeProvider();
        if (provider == null) {
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
            boolean resolved = this.mView.mContext.getTheme().resolveAttribute(C3132R.attr.accessibilityFocusedDrawable, value, true);
            if (resolved) {
                this.mAttachInfo.mAccessibilityFocusDrawable = this.mView.mContext.getDrawable(value.resourceId);
            }
        }
        return this.mAttachInfo.mAccessibilityFocusDrawable;
    }

    void updateSystemGestureExclusionRectsForView(View view) {
        this.mGestureExclusionTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(32);
    }

    void systemGestureExclusionChanged() {
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

    boolean scrollToRectOrFocus(Rect rectangle, boolean immediate) {
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
            View lastScrolledFocus = this.mLastScrolledFocus != null ? this.mLastScrolledFocus.get() : null;
            if (focus != lastScrolledFocus) {
                rectangle = null;
            }
            if (focus != lastScrolledFocus || this.mScrollMayChange || rectangle != null) {
                this.mLastScrolledFocus = new WeakReference<>(focus);
                this.mScrollMayChange = false;
                if (focus.getGlobalVisibleRect(this.mVisRect, null)) {
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

    void setAccessibilityFocus(View view, AccessibilityNodeInfo node) {
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
                int virtualNodeId = AccessibilityNodeInfo.getVirtualDescendantId(focusNode.getSourceNodeId());
                provider.performAction(virtualNodeId, 128, null);
            }
            focusNode.recycle();
        }
        if (this.mAccessibilityFocusedHost != null && this.mAccessibilityFocusedHost != view) {
            this.mAccessibilityFocusedHost.clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = node;
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.invalidateRoot();
        }
    }

    boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    void requestPointerCapture(boolean enabled) {
        if (this.mPointerCapture == enabled) {
            return;
        }
        InputManager.getInstance().requestPointerCapture(this.mAttachInfo.mWindowToken, enabled);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePointerCaptureChanged(boolean hasCapture) {
        if (this.mPointerCapture == hasCapture) {
            return;
        }
        this.mPointerCapture = hasCapture;
        if (this.mView != null) {
            this.mView.dispatchPointerCaptureChanged(hasCapture);
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
        return !isWideGamut || this.mContext.getResources().getConfiguration().isScreenWideColorGamut();
    }

    @Override // android.view.ViewParent
    public void requestChildFocus(View child, View focused) {
        checkThread();
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public void clearChildFocus(View child) {
        checkThread();
        scheduleTraversals();
    }

    @Override // android.view.ViewParent
    public ViewParent getParentForAccessibility() {
        return null;
    }

    @Override // android.view.ViewParent
    public void focusableViewAvailable(View v) {
        checkThread();
        if (this.mView != null) {
            if (!this.mView.hasFocus()) {
                if (sAlwaysAssignFocus || !this.mAttachInfo.mInTouchMode) {
                    v.requestFocus();
                    return;
                }
                return;
            }
            View focused = this.mView.findFocus();
            if (focused instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) focused;
                if (group.getDescendantFocusability() == 262144 && isViewDescendantOf(v, focused)) {
                    v.requestFocus();
                }
            }
        }
    }

    @Override // android.view.ViewParent
    public void recomputeViewAttributes(View child) {
        checkThread();
        if (this.mView == child) {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
            if (!this.mWillDrawSoon) {
                scheduleTraversals();
            }
        }
    }

    void dispatchDetachedFromWindow() {
        this.mFirstInputStage.onDetachedFromWindow();
        if (this.mView != null && this.mView.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(false);
            this.mView.dispatchDetachedFromWindow();
        }
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(this.mHighContrastTextManager);
        removeSendWindowContentChangedCallback();
        destroyHardwareRenderer();
        setAccessibilityFocus(null, null);
        this.mView.assignParent(null);
        this.mView = null;
        this.mAttachInfo.mRootView = null;
        destroySurface();
        if (this.mInputQueueCallback != null && this.mInputQueue != null) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void performConfigurationChange(MergedConfiguration mergedConfiguration, boolean force, int newDisplayId) {
        if (mergedConfiguration == null) {
            throw new IllegalArgumentException("No merged config provided.");
        }
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
    }

    public void updateConfiguration(int newDisplayId) {
        if (this.mView == null) {
            return;
        }
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

    public static boolean isViewDescendantOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        ViewParent theParent = child.getParent();
        return (theParent instanceof ViewGroup) && isViewDescendantOf((View) theParent, parent);
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    /* loaded from: classes4.dex */
    final class ViewRootHandler extends Handler {
        ViewRootHandler() {
        }

        @Override // android.p007os.Handler
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
                case 10:
                case 20:
                case 22:
                case 26:
                default:
                    return super.getMessageName(message);
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
            }
        }

        @Override // android.p007os.Handler
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            if (msg.what == 26 && msg.obj == null) {
                throw new NullPointerException("Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
            }
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
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
                    break;
                case 5:
                    break;
                case 6:
                    ViewRootImpl.this.handleWindowFocusChanged();
                    return;
                case 7:
                    SomeArgs args2 = (SomeArgs) msg.obj;
                    InputEventReceiver receiver = (InputEventReceiver) args2.arg2;
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) args2.arg1, receiver, 0, true);
                    args2.recycle();
                    return;
                case 8:
                    ViewRootImpl.this.handleAppVisibility(msg.arg1 != 0);
                    return;
                case 9:
                    ViewRootImpl.this.handleGetNewSurface();
                    return;
                case 10:
                case 20:
                default:
                    return;
                case 11:
                    KeyEvent event = (KeyEvent) msg.obj;
                    if ((event.getFlags() & 8) != 0) {
                        event = KeyEvent.changeFlags(event, event.getFlags() & (-9));
                    }
                    ViewRootImpl.this.enqueueInputEvent(event, null, 1, true);
                    return;
                case 12:
                    ViewRootImpl.this.enqueueInputEvent((KeyEvent) msg.obj, null, 0, true);
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
                    ViewRootImpl.this.setAccessibilityFocus(null, null);
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
                    ViewRootImpl.this.enqueueInputEvent((InputEvent) msg.obj, null, 32, true);
                    return;
                case 25:
                    ViewRootImpl.this.handleDispatchWindowShown();
                    return;
                case 26:
                    IResultReceiver receiver2 = (IResultReceiver) msg.obj;
                    int deviceId = msg.arg1;
                    ViewRootImpl.this.handleRequestKeyboardShortcuts(receiver2, deviceId);
                    return;
                case 27:
                    ViewRootImpl.this.resetPointerIcon((MotionEvent) msg.obj);
                    return;
                case 28:
                    boolean hasCapture = msg.arg1 != 0;
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
            }
            if (ViewRootImpl.this.mAdded) {
                SomeArgs args4 = (SomeArgs) msg.obj;
                int displayId = args4.argi3;
                MergedConfiguration mergedConfiguration = (MergedConfiguration) args4.arg4;
                boolean displayChanged = ViewRootImpl.this.mDisplay.getDisplayId() != displayId;
                boolean configChanged = false;
                if (!ViewRootImpl.this.mLastReportedMergedConfiguration.equals(mergedConfiguration)) {
                    ViewRootImpl.this.performConfigurationChange(mergedConfiguration, false, displayChanged ? displayId : -1);
                    configChanged = true;
                } else if (displayChanged) {
                    ViewRootImpl.this.onMovedToDisplay(displayId, ViewRootImpl.this.mLastConfigurationFromResources);
                }
                boolean framesChanged = (ViewRootImpl.this.mWinFrame.equals(args4.arg1) && ViewRootImpl.this.mPendingOverscanInsets.equals(args4.arg5) && ViewRootImpl.this.mPendingContentInsets.equals(args4.arg2) && ViewRootImpl.this.mPendingStableInsets.equals(args4.arg6) && ViewRootImpl.this.mPendingDisplayCutout.get().equals(args4.arg9) && ViewRootImpl.this.mPendingVisibleInsets.equals(args4.arg3) && ViewRootImpl.this.mPendingOutsets.equals(args4.arg7)) ? false : true;
                ViewRootImpl.this.setFrame((Rect) args4.arg1);
                ViewRootImpl.this.mPendingOverscanInsets.set((Rect) args4.arg5);
                ViewRootImpl.this.mPendingContentInsets.set((Rect) args4.arg2);
                ViewRootImpl.this.mPendingStableInsets.set((Rect) args4.arg6);
                ViewRootImpl.this.mPendingDisplayCutout.set((DisplayCutout) args4.arg9);
                ViewRootImpl.this.mPendingVisibleInsets.set((Rect) args4.arg3);
                ViewRootImpl.this.mPendingOutsets.set((Rect) args4.arg7);
                ViewRootImpl.this.mPendingBackDropFrame.set((Rect) args4.arg8);
                ViewRootImpl.this.mForceNextWindowRelayout = args4.argi1 != 0;
                ViewRootImpl.this.mPendingAlwaysConsumeSystemBars = args4.argi2 != 0;
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

    @UnsupportedAppUsage
    boolean ensureTouchMode(boolean inTouchMode) {
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
        focused.clearFocusInternal(null, true, false);
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
        if (this.mView != null) {
            if (this.mView.hasFocus()) {
                View focusedView = this.mView.findFocus();
                if (!(focusedView instanceof ViewGroup) || ((ViewGroup) focusedView).getDescendantFocusability() != 262144) {
                    return false;
                }
            }
            return this.mView.restoreDefaultFocus();
        }
        return false;
    }

    /* loaded from: classes4.dex */
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

        protected void finish(QueuedInputEvent q, boolean handled) {
            q.mFlags |= 4;
            if (handled) {
                q.mFlags |= 8;
            }
            forward(q);
        }

        protected void forward(QueuedInputEvent q) {
            onDeliverToNext(q);
        }

        protected void apply(QueuedInputEvent q, int result) {
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

        protected int onProcess(QueuedInputEvent q) {
            return 0;
        }

        protected void onDeliverToNext(QueuedInputEvent q) {
            if (this.mNext == null) {
                ViewRootImpl.this.finishInputEvent(q);
            } else {
                this.mNext.deliver(q);
            }
        }

        protected void onWindowFocusChanged(boolean hasWindowFocus) {
            if (this.mNext != null) {
                this.mNext.onWindowFocusChanged(hasWindowFocus);
            }
        }

        protected void onDetachedFromWindow() {
            if (this.mNext != null) {
                this.mNext.onDetachedFromWindow();
            }
        }

        protected boolean shouldDropInputEvent(QueuedInputEvent q) {
            if (ViewRootImpl.this.mView == null || !ViewRootImpl.this.mAdded) {
                String str = ViewRootImpl.this.mTag;
                Slog.m50w(str, "Dropping event due to root view being removed: " + q.mEvent);
                return true;
            } else if ((ViewRootImpl.this.mAttachInfo.mHasWindowFocus || q.mEvent.isFromSource(2) || ViewRootImpl.this.isAutofillUiShowing()) && !ViewRootImpl.this.mStopped && ((!ViewRootImpl.this.mIsAmbientMode || q.mEvent.isFromSource(1)) && (!ViewRootImpl.this.mPausedForTransition || isBack(q.mEvent)))) {
                return false;
            } else {
                if (!ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
                    String str2 = ViewRootImpl.this.mTag;
                    Slog.m50w(str2, "Dropping event due to no window focus: " + q.mEvent);
                    return true;
                }
                q.mEvent.cancel();
                String str3 = ViewRootImpl.this.mTag;
                Slog.m50w(str3, "Cancelling event due to no window focus: " + q.mEvent);
                return false;
            }
        }

        void dump(String prefix, PrintWriter writer) {
            if (this.mNext != null) {
                this.mNext.dump(prefix, writer);
            }
        }

        private boolean isBack(InputEvent event) {
            return (event instanceof KeyEvent) && ((KeyEvent) event).getKeyCode() == 4;
        }
    }

    /* loaded from: classes4.dex */
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

        protected void defer(QueuedInputEvent q) {
            q.mFlags |= 2;
            enqueue(q);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void forward(QueuedInputEvent q) {
            q.mFlags &= -3;
            QueuedInputEvent curr = this.mQueueHead;
            if (curr == null) {
                super.forward(q);
                return;
            }
            int deviceId = q.mEvent.getDeviceId();
            QueuedInputEvent prev = null;
            boolean blocked = false;
            while (curr != null && curr != q) {
                if (!blocked && deviceId == curr.mEvent.getDeviceId()) {
                    blocked = true;
                }
                prev = curr;
                curr = curr.mNext;
            }
            if (blocked) {
                if (curr == null) {
                    enqueue(q);
                    return;
                }
                return;
            }
            if (curr != null) {
                curr = curr.mNext;
                dequeue(q, prev);
            }
            super.forward(q);
            while (curr != null) {
                if (deviceId == curr.mEvent.getDeviceId()) {
                    if ((curr.mFlags & 2) == 0) {
                        QueuedInputEvent next = curr.mNext;
                        dequeue(curr, prev);
                        super.forward(curr);
                        curr = next;
                    } else {
                        return;
                    }
                } else {
                    prev = curr;
                    curr = curr.mNext;
                }
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void apply(QueuedInputEvent q, int result) {
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
            Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
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
            Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
        }

        @Override // android.view.ViewRootImpl.InputStage
        void dump(String prefix, PrintWriter writer) {
            writer.print(prefix);
            writer.print(getClass().getName());
            writer.print(": mQueueLength=");
            writer.println(this.mQueueLength);
            super.dump(prefix, writer);
        }
    }

    /* loaded from: classes4.dex */
    final class NativePreImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (ViewRootImpl.this.mInputQueue != null && (q.mEvent instanceof KeyEvent)) {
                ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, true, this);
                return 3;
            }
            return 0;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    /* loaded from: classes4.dex */
    final class ViewPreImeInputStage extends InputStage {
        public ViewPreImeInputStage(InputStage next) {
            super(next);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (q.mEvent instanceof KeyEvent) {
                return processKeyEvent(q);
            }
            return 0;
        }

        private int processKeyEvent(QueuedInputEvent q) {
            KeyEvent event = (KeyEvent) q.mEvent;
            if (ViewRootImpl.this.mView.dispatchKeyEventPreIme(event)) {
                return 1;
            }
            return 0;
        }
    }

    /* loaded from: classes4.dex */
    final class ImeInputStage extends AsyncInputStage implements InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            InputMethodManager imm;
            if (!ViewRootImpl.this.mLastWasImTarget || ViewRootImpl.this.isInLocalFocusMode() || (imm = (InputMethodManager) ViewRootImpl.this.mContext.getSystemService(InputMethodManager.class)) == null) {
                return 0;
            }
            InputEvent event = q.mEvent;
            int result = imm.dispatchInputEvent(event, q, this, ViewRootImpl.this.mHandler);
            if (result == 1) {
                return 1;
            }
            return result == 0 ? 0 : 3;
        }

        @Override // android.view.inputmethod.InputMethodManager.FinishedInputEventCallback
        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    /* loaded from: classes4.dex */
    final class EarlyPostImeInputStage extends InputStage {
        public EarlyPostImeInputStage(InputStage next) {
            super(next);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
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
                event.offsetLocation(0.0f, ViewRootImpl.this.mCurScrollY);
            }
            if (event.isTouchEvent()) {
                ViewRootImpl.this.mLastTouchPoint.f61x = event.getRawX();
                ViewRootImpl.this.mLastTouchPoint.f62y = event.getRawY();
                ViewRootImpl.this.mLastTouchSource = event.getSource();
                return 0;
            }
            return 0;
        }
    }

    /* loaded from: classes4.dex */
    final class NativePostImeInputStage extends AsyncInputStage implements InputQueue.FinishedInputEventCallback {
        public NativePostImeInputStage(InputStage next, String traceCounter) {
            super(next, traceCounter);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            if (ViewRootImpl.this.mInputQueue != null) {
                ViewRootImpl.this.mInputQueue.sendInputEvent(q.mEvent, q, false, this);
                return 3;
            }
            return 0;
        }

        @Override // android.view.InputQueue.FinishedInputEventCallback
        public void onFinishedInputEvent(Object token, boolean handled) {
            QueuedInputEvent q = (QueuedInputEvent) token;
            if (handled) {
                finish(q, true);
            } else {
                forward(q);
            }
        }
    }

    /* loaded from: classes4.dex */
    final class ViewPostImeInputStage extends InputStage {
        public ViewPostImeInputStage(InputStage next) {
            super(next);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
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

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(QueuedInputEvent q) {
            if (ViewRootImpl.this.mUnbufferedInputDispatch && (q.mEvent instanceof MotionEvent) && ((MotionEvent) q.mEvent).isTouchEvent() && ViewRootImpl.isTerminalInputEvent(q.mEvent)) {
                ViewRootImpl.this.mUnbufferedInputDispatch = false;
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
            super.onDeliverToNext(q);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x002c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean performFocusNavigation(KeyEvent event) {
            int direction = 0;
            int keyCode = event.getKeyCode();
            if (keyCode != 61) {
                switch (keyCode) {
                    case 19:
                        if (event.hasNoModifiers()) {
                            direction = 33;
                            break;
                        }
                        break;
                    case 20:
                        if (event.hasNoModifiers()) {
                            direction = 130;
                            break;
                        }
                        break;
                    case 21:
                        if (event.hasNoModifiers()) {
                            direction = 17;
                            break;
                        }
                        break;
                    case 22:
                        if (event.hasNoModifiers()) {
                            direction = 66;
                            break;
                        }
                        break;
                    default:
                        switch (keyCode) {
                        }
                }
            } else if (event.hasNoModifiers()) {
                direction = 2;
            } else if (event.hasModifiers(1)) {
                direction = 1;
            }
            if (direction != 0) {
                View focused = ViewRootImpl.this.mView.findFocus();
                if (focused == null) {
                    return ViewRootImpl.this.mView.restoreDefaultFocus();
                }
                View v = focused.focusSearch(direction);
                if (v != null && v != focused) {
                    focused.getFocusedRect(ViewRootImpl.this.mTempRect);
                    if (ViewRootImpl.this.mView instanceof ViewGroup) {
                        ((ViewGroup) ViewRootImpl.this.mView).offsetDescendantRectToMyCoords(focused, ViewRootImpl.this.mTempRect);
                        ((ViewGroup) ViewRootImpl.this.mView).offsetRectIntoDescendantCoords(v, ViewRootImpl.this.mTempRect);
                    }
                    if (v.requestFocus(direction, ViewRootImpl.this.mTempRect)) {
                        ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                        return true;
                    }
                }
                return ViewRootImpl.this.mView.dispatchUnhandledMove(focused, direction);
            }
            return false;
        }

        private boolean performKeyboardGroupNavigation(int direction) {
            View cluster;
            View focused = ViewRootImpl.this.mView.findFocus();
            if (focused == null && ViewRootImpl.this.mView.restoreDefaultFocus()) {
                return true;
            }
            if (focused == null) {
                cluster = ViewRootImpl.this.keyboardNavigationClusterSearch(null, direction);
            } else {
                cluster = focused.keyboardNavigationClusterSearch(null, direction);
            }
            int realDirection = direction;
            realDirection = (direction == 2 || direction == 1) ? 130 : 130;
            if (cluster != null && cluster.isRootNamespace()) {
                if (!cluster.restoreFocusNotInCluster()) {
                    cluster = ViewRootImpl.this.keyboardNavigationClusterSearch(null, direction);
                } else {
                    ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                    return true;
                }
            }
            if (cluster != null && cluster.restoreFocusInCluster(realDirection)) {
                ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
                return true;
            }
            return false;
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
            if (event.getAction() == 0) {
                return groupNavigationDirection != 0 ? performKeyboardGroupNavigation(groupNavigationDirection) ? 1 : 0 : performFocusNavigation(event) ? 1 : 0;
            }
            return 0;
        }

        private int processPointerEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested = false;
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = true;
            boolean dispatchPointerEvent = ViewRootImpl.this.mView.dispatchPointerEvent(event);
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
            return dispatchPointerEvent ? 1 : 0;
        }

        private void maybeUpdatePointerIcon(MotionEvent event) {
            if (event.getPointerCount() == 1 && event.isFromSource(8194)) {
                if (event.getActionMasked() == 9 || event.getActionMasked() == 10) {
                    ViewRootImpl.this.mPointerIconType = 1;
                }
                if (event.getActionMasked() != 10 && !ViewRootImpl.this.updatePointerIcon(event) && event.getActionMasked() == 7) {
                    ViewRootImpl.this.mPointerIconType = 1;
                }
            }
        }

        private int processTrackballEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            return ((!event.isFromSource(InputDevice.SOURCE_MOUSE_RELATIVE) || (ViewRootImpl.this.hasPointerCapture() && !ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event))) && !ViewRootImpl.this.mView.dispatchTrackballEvent(event)) ? 0 : 1;
        }

        private int processGenericMotionEvent(QueuedInputEvent q) {
            MotionEvent event = (MotionEvent) q.mEvent;
            return ((event.isFromSource(1048584) && ViewRootImpl.this.hasPointerCapture() && ViewRootImpl.this.mView.dispatchCapturedPointerEvent(event)) || ViewRootImpl.this.mView.dispatchGenericMotionEvent(event)) ? 1 : 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPointerIcon(MotionEvent event) {
        this.mPointerIconType = 1;
        updatePointerIcon(event);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updatePointerIcon(MotionEvent event) {
        float x = event.getX(0);
        float y = event.getY(0);
        if (this.mView == null) {
            Slog.m58d(this.mTag, "updatePointerIcon called after view was removed");
            return false;
        } else if (x >= 0.0f && x < this.mView.getWidth() && y >= 0.0f && y < this.mView.getHeight()) {
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
        } else {
            Slog.m58d(this.mTag, "updatePointerIcon called with position out of bounds");
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeUpdateTooltip(MotionEvent event) {
        if (event.getPointerCount() != 1) {
            return;
        }
        int action = event.getActionMasked();
        if (action != 9 && action != 7 && action != 10) {
            return;
        }
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (manager.isEnabled() && manager.isTouchExplorationEnabled()) {
            return;
        }
        if (this.mView == null) {
            Slog.m58d(this.mTag, "maybeUpdateTooltip called after view was removed");
        } else {
            this.mView.dispatchTooltipHoverEvent(event);
        }
    }

    /* loaded from: classes4.dex */
    final class SyntheticInputStage extends InputStage {
        private final SyntheticJoystickHandler mJoystick;
        private final SyntheticKeyboardHandler mKeyboard;
        private final SyntheticTouchNavigationHandler mTouchNavigation;
        private final SyntheticTrackballHandler mTrackball;

        public SyntheticInputStage() {
            super(null);
            this.mTrackball = new SyntheticTrackballHandler();
            this.mJoystick = new SyntheticJoystickHandler();
            this.mTouchNavigation = new SyntheticTouchNavigationHandler();
            this.mKeyboard = new SyntheticKeyboardHandler();
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected int onProcess(QueuedInputEvent q) {
            q.mFlags |= 16;
            if (!(q.mEvent instanceof MotionEvent)) {
                if ((q.mFlags & 32) != 0) {
                    this.mKeyboard.process((KeyEvent) q.mEvent);
                    return 1;
                }
                return 0;
            }
            MotionEvent event = (MotionEvent) q.mEvent;
            int source = event.getSource();
            if ((source & 4) != 0) {
                this.mTrackball.process(event);
                return 1;
            } else if ((source & 16) != 0) {
                this.mJoystick.process(event);
                return 1;
            } else if ((source & 2097152) == 2097152) {
                this.mTouchNavigation.process(event);
                return 1;
            } else {
                return 0;
            }
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDeliverToNext(QueuedInputEvent q) {
            if ((q.mFlags & 16) == 0 && (q.mEvent instanceof MotionEvent)) {
                MotionEvent event = (MotionEvent) q.mEvent;
                int source = event.getSource();
                if ((source & 4) != 0) {
                    this.mTrackball.cancel();
                } else if ((source & 16) == 0) {
                    if ((source & 2097152) == 2097152) {
                        this.mTouchNavigation.cancel(event);
                    }
                } else {
                    this.mJoystick.cancel();
                }
            }
            super.onDeliverToNext(q);
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onWindowFocusChanged(boolean hasWindowFocus) {
            if (hasWindowFocus) {
                return;
            }
            this.mJoystick.cancel();
        }

        @Override // android.view.ViewRootImpl.InputStage
        protected void onDetachedFromWindow() {
            this.mJoystick.cancel();
        }
    }

    /* loaded from: classes4.dex */
    final class SyntheticTrackballHandler {
        private long mLastTime;

        /* renamed from: mX */
        private final TrackballAxis f2421mX = new TrackballAxis();

        /* renamed from: mY */
        private final TrackballAxis f2422mY = new TrackballAxis();

        SyntheticTrackballHandler() {
        }

        public void process(MotionEvent event) {
            long curTime;
            long curTime2;
            long curTime3 = SystemClock.uptimeMillis();
            if (this.mLastTime + 250 < curTime3) {
                this.f2421mX.reset(0);
                this.f2422mY.reset(0);
                this.mLastTime = curTime3;
            }
            int action = event.getAction();
            int metaState = event.getMetaState();
            switch (action) {
                case 0:
                    curTime = curTime3;
                    this.f2421mX.reset(2);
                    this.f2422mY.reset(2);
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime, curTime, 0, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                case 1:
                    this.f2421mX.reset(2);
                    this.f2422mY.reset(2);
                    curTime = curTime3;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime3, curTime3, 1, 23, 0, metaState, -1, 0, 1024, 257));
                    break;
                default:
                    curTime = curTime3;
                    break;
            }
            float xOff = this.f2421mX.collect(event.getX(), event.getEventTime(), "X");
            float yOff = this.f2422mY.collect(event.getY(), event.getEventTime(), "Y");
            int keycode = 0;
            int movement = 0;
            float accel = 1.0f;
            if (xOff > yOff) {
                movement = this.f2421mX.generate();
                if (movement != 0) {
                    keycode = movement > 0 ? 22 : 21;
                    accel = this.f2421mX.acceleration;
                    this.f2422mY.reset(2);
                }
            } else if (yOff > 0.0f && (movement = this.f2422mY.generate()) != 0) {
                keycode = movement > 0 ? 20 : 19;
                accel = this.f2422mY.acceleration;
                this.f2421mX.reset(2);
            }
            int keycode2 = keycode;
            float accel2 = accel;
            if (keycode2 != 0) {
                if (movement < 0) {
                    movement = -movement;
                }
                int accelMovement = (int) (movement * accel2);
                if (accelMovement > movement) {
                    int movement2 = movement - 1;
                    int repeatCount = accelMovement - movement2;
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime, curTime, 2, keycode2, repeatCount, metaState, -1, 0, 1024, 257));
                    movement = movement2;
                    curTime2 = curTime;
                } else {
                    curTime2 = curTime;
                }
                while (movement > 0) {
                    long curTime4 = SystemClock.uptimeMillis();
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime4, curTime4, 0, keycode2, 0, metaState, -1, 0, 1024, 257));
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(curTime4, curTime4, 1, keycode2, 0, metaState, -1, 0, 1024, 257));
                    movement--;
                    curTime2 = curTime4;
                    xOff = xOff;
                    yOff = yOff;
                }
                this.mLastTime = curTime2;
            }
        }

        public void cancel() {
            this.mLastTime = -2147483648L;
            if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mAdded) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
        }
    }

    /* loaded from: classes4.dex */
    static final class TrackballAxis {
        static final float ACCEL_MOVE_SCALING_FACTOR = 0.025f;
        static final long FAST_MOVE_TIME = 150;
        static final float FIRST_MOVEMENT_THRESHOLD = 0.5f;
        static final float MAX_ACCELERATION = 20.0f;
        static final float SECOND_CUMULATIVE_MOVEMENT_THRESHOLD = 2.0f;
        static final float SUBSEQUENT_INCREMENTAL_MOVEMENT_THRESHOLD = 1.0f;
        int dir;
        int nonAccelMovement;
        float position;
        int step;
        float acceleration = 1.0f;
        long lastMoveTime = 0;

        TrackballAxis() {
        }

        void reset(int _step) {
            this.position = 0.0f;
            this.acceleration = 1.0f;
            this.lastMoveTime = 0L;
            this.step = _step;
            this.dir = 0;
        }

        float collect(float off, long time, String axis) {
            long normTime;
            if (off > 0.0f) {
                normTime = 150.0f * off;
                if (this.dir < 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = 1;
            } else if (off < 0.0f) {
                normTime = (-off) * 150.0f;
                if (this.dir > 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
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
                    float f = MAX_ACCELERATION;
                    if (acc < MAX_ACCELERATION) {
                        f = acc;
                    }
                    this.acceleration = f;
                } else {
                    float scale2 = ((float) (delta - normTime2)) * ACCEL_MOVE_SCALING_FACTOR;
                    if (scale2 > 1.0f) {
                        acc /= scale2;
                    }
                    this.acceleration = acc > 1.0f ? acc : 1.0f;
                }
            }
            this.position += off;
            return Math.abs(this.position);
        }

        int generate() {
            int movement = 0;
            this.nonAccelMovement = 0;
            while (true) {
                int dir = this.position >= 0.0f ? 1 : -1;
                switch (this.step) {
                    case 0:
                        if (Math.abs(this.position) < 0.5f) {
                            return movement;
                        }
                        movement += dir;
                        this.nonAccelMovement += dir;
                        this.step = 1;
                        break;
                    case 1:
                        if (Math.abs(this.position) < SECOND_CUMULATIVE_MOVEMENT_THRESHOLD) {
                            return movement;
                        }
                        movement += dir;
                        this.nonAccelMovement += dir;
                        this.position -= dir * SECOND_CUMULATIVE_MOVEMENT_THRESHOLD;
                        this.step = 2;
                        break;
                    default:
                        if (Math.abs(this.position) < 1.0f) {
                            return movement;
                        }
                        movement += dir;
                        this.position -= dir * 1.0f;
                        float acc = this.acceleration * 1.1f;
                        this.acceleration = acc < MAX_ACCELERATION ? acc : this.acceleration;
                        break;
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    final class SyntheticJoystickHandler extends Handler {
        private static final int MSG_ENQUEUE_X_AXIS_KEY_REPEAT = 1;
        private static final int MSG_ENQUEUE_Y_AXIS_KEY_REPEAT = 2;
        private final SparseArray<KeyEvent> mDeviceKeyEvents;
        private final JoystickAxesState mJoystickAxesState;

        public SyntheticJoystickHandler() {
            super(true);
            this.mJoystickAxesState = new JoystickAxesState();
            this.mDeviceKeyEvents = new SparseArray<>();
        }

        @Override // android.p007os.Handler
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
                        sendMessageDelayed(m, ViewConfiguration.getKeyRepeatDelay());
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
                    String str = ViewRootImpl.this.mTag;
                    Log.m64w(str, "Unexpected action: " + event.getActionMasked());
                    return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
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
                long time = event.getHistoricalEventTime(h);
                this.mJoystickAxesState.updateStateForAxis(event, time, 0, event.getHistoricalAxisValue(0, 0, h));
                this.mJoystickAxesState.updateStateForAxis(event, time, 1, event.getHistoricalAxisValue(1, 0, h));
                this.mJoystickAxesState.updateStateForAxis(event, time, 15, event.getHistoricalAxisValue(15, 0, h));
                this.mJoystickAxesState.updateStateForAxis(event, time, 16, event.getHistoricalAxisValue(16, 0, h));
            }
            long time2 = event.getEventTime();
            this.mJoystickAxesState.updateStateForAxis(event, time2, 0, event.getAxisValue(0));
            this.mJoystickAxesState.updateStateForAxis(event, time2, 1, event.getAxisValue(1));
            this.mJoystickAxesState.updateStateForAxis(event, time2, 15, event.getAxisValue(15));
            this.mJoystickAxesState.updateStateForAxis(event, time2, 16, event.getAxisValue(16));
        }

        /* loaded from: classes4.dex */
        final class JoystickAxesState {
            private static final int STATE_DOWN_OR_RIGHT = 1;
            private static final int STATE_NEUTRAL = 0;
            private static final int STATE_UP_OR_LEFT = -1;
            final int[] mAxisStatesHat = {0, 0};
            final int[] mAxisStatesStick = {0, 0};

            JoystickAxesState() {
            }

            void resetState() {
                this.mAxisStatesHat[0] = 0;
                this.mAxisStatesHat[1] = 0;
                this.mAxisStatesStick[0] = 0;
                this.mAxisStatesStick[1] = 0;
            }

            void updateStateForAxis(MotionEvent event, long time, int axis, float value) {
                int axisStateIndex;
                int repeatMessage;
                int currentState;
                int keyCode;
                if (isXAxis(axis)) {
                    axisStateIndex = 0;
                    repeatMessage = 1;
                } else if (!isYAxis(axis)) {
                    String str = ViewRootImpl.this.mTag;
                    Log.m70e(str, "Unexpected axis " + axis + " in updateStateForAxis!");
                    return;
                } else {
                    axisStateIndex = 1;
                    repeatMessage = 2;
                }
                int newState = joystickAxisValueToState(value);
                if (axis == 0 || axis == 1) {
                    currentState = this.mAxisStatesStick[axisStateIndex];
                } else {
                    currentState = this.mAxisStatesHat[axisStateIndex];
                }
                if (currentState == newState) {
                    return;
                }
                int metaState = event.getMetaState();
                int deviceId = event.getDeviceId();
                int source = event.getSource();
                if (currentState == 1 || currentState == -1) {
                    int keyCode2 = joystickAxisAndStateToKeycode(axis, currentState);
                    if (keyCode2 != 0) {
                        ViewRootImpl.this.enqueueInputEvent(new KeyEvent(time, time, 1, keyCode2, 0, metaState, deviceId, 0, 1024, source));
                        deviceId = deviceId;
                        SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId, null);
                    }
                    SyntheticJoystickHandler.this.removeMessages(repeatMessage);
                }
                if ((newState == 1 || newState == -1) && (keyCode = joystickAxisAndStateToKeycode(axis, newState)) != 0) {
                    int deviceId2 = deviceId;
                    KeyEvent keyEvent = new KeyEvent(time, time, 0, keyCode, 0, metaState, deviceId2, 0, 1024, source);
                    ViewRootImpl.this.enqueueInputEvent(keyEvent);
                    Message m = SyntheticJoystickHandler.this.obtainMessage(repeatMessage, keyEvent);
                    m.setAsynchronous(true);
                    SyntheticJoystickHandler.this.sendMessageDelayed(m, ViewConfiguration.getKeyRepeatTimeout());
                    SyntheticJoystickHandler.this.mDeviceKeyEvents.put(deviceId2, new KeyEvent(time, time, 1, keyCode, 0, metaState, deviceId2, 0, 1056, source));
                }
                if (axis == 0 || axis == 1) {
                    this.mAxisStatesStick[axisStateIndex] = newState;
                } else {
                    this.mAxisStatesHat[axisStateIndex] = newState;
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
                if (!isYAxis(axis) || state != 1) {
                    String str = ViewRootImpl.this.mTag;
                    Log.m70e(str, "Unknown axis " + axis + " or direction " + state);
                    return 0;
                }
                return 20;
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

    /* loaded from: classes4.dex */
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
        private int mActivePointerId;
        private float mConfigMaxFlingVelocity;
        private float mConfigMinFlingVelocity;
        private float mConfigTickDistance;
        private boolean mConsumedMovement;
        private int mCurrentDeviceId;
        private boolean mCurrentDeviceSupported;
        private int mCurrentSource;
        private final Runnable mFlingRunnable;
        private float mFlingVelocity;
        private boolean mFlinging;
        private float mLastX;
        private float mLastY;
        private int mPendingKeyCode;
        private long mPendingKeyDownTime;
        private int mPendingKeyMetaState;
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
            this.mCurrentDeviceId = -1;
            this.mActivePointerId = -1;
            this.mPendingKeyCode = 0;
            this.mFlingRunnable = new Runnable() { // from class: android.view.ViewRootImpl.SyntheticTouchNavigationHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    long time = SystemClock.uptimeMillis();
                    SyntheticTouchNavigationHandler.this.sendKeyDownOrRepeat(time, SyntheticTouchNavigationHandler.this.mPendingKeyCode, SyntheticTouchNavigationHandler.this.mPendingKeyMetaState);
                    SyntheticTouchNavigationHandler.access$3132(SyntheticTouchNavigationHandler.this, SyntheticTouchNavigationHandler.FLING_TICK_DECAY);
                    if (!SyntheticTouchNavigationHandler.this.postFling(time)) {
                        SyntheticTouchNavigationHandler.this.mFlinging = false;
                        SyntheticTouchNavigationHandler.this.finishKeys(time);
                    }
                }
            };
        }

        public void process(MotionEvent event) {
            long time = event.getEventTime();
            int deviceId = event.getDeviceId();
            int source = event.getSource();
            if (this.mCurrentDeviceId != deviceId || this.mCurrentSource != source) {
                finishKeys(time);
                finishTracking(time);
                this.mCurrentDeviceId = deviceId;
                this.mCurrentSource = source;
                this.mCurrentDeviceSupported = false;
                InputDevice device = event.getDevice();
                if (device != null) {
                    InputDevice.MotionRange xRange = device.getMotionRange(0);
                    InputDevice.MotionRange yRange = device.getMotionRange(1);
                    if (xRange != null && yRange != null) {
                        this.mCurrentDeviceSupported = true;
                        float xRes = xRange.getResolution();
                        if (xRes <= 0.0f) {
                            xRes = xRange.getRange() / 48.0f;
                        }
                        float yRes = yRange.getResolution();
                        if (yRes <= 0.0f) {
                            yRes = yRange.getRange() / 48.0f;
                        }
                        float nominalRes = (xRes + yRes) * 0.5f;
                        this.mConfigTickDistance = 12.0f * nominalRes;
                        this.mConfigMinFlingVelocity = this.mConfigTickDistance * 6.0f;
                        this.mConfigMaxFlingVelocity = this.mConfigTickDistance * MAX_FLING_VELOCITY_TICKS_PER_SECOND;
                    }
                }
            }
            if (!this.mCurrentDeviceSupported) {
                return;
            }
            int action = event.getActionMasked();
            switch (action) {
                case 0:
                    boolean caughtFling = this.mFlinging;
                    finishKeys(time);
                    finishTracking(time);
                    this.mActivePointerId = event.getPointerId(0);
                    this.mVelocityTracker = VelocityTracker.obtain();
                    this.mVelocityTracker.addMovement(event);
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
                        int index = event.findPointerIndex(this.mActivePointerId);
                        if (index < 0) {
                            finishKeys(time);
                            finishTracking(time);
                            return;
                        }
                        this.mVelocityTracker.addMovement(event);
                        float x = event.getX(index);
                        float y = event.getY(index);
                        this.mAccumulatedX += x - this.mLastX;
                        this.mAccumulatedY += y - this.mLastY;
                        this.mLastX = x;
                        this.mLastY = y;
                        int metaState = event.getMetaState();
                        consumeAccumulatedMovement(time, metaState);
                        if (action == 1) {
                            if (this.mConsumedMovement && this.mPendingKeyCode != 0) {
                                this.mVelocityTracker.computeCurrentVelocity(1000, this.mConfigMaxFlingVelocity);
                                float vx = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
                                float vy = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                                if (!startFling(time, vx, vy)) {
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

        public void cancel(MotionEvent event) {
            if (this.mCurrentDeviceId == event.getDeviceId() && this.mCurrentSource == event.getSource()) {
                long time = event.getEventTime();
                finishKeys(time);
                finishTracking(time);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
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

        /* JADX INFO: Access modifiers changed from: private */
        public void sendKeyDownOrRepeat(long time, int keyCode, int metaState) {
            if (this.mPendingKeyCode == keyCode) {
                this.mPendingKeyRepeatCount++;
            } else {
                sendKeyUp(time);
                this.mPendingKeyDownTime = time;
                this.mPendingKeyCode = keyCode;
                this.mPendingKeyRepeatCount = 0;
            }
            this.mPendingKeyMetaState = metaState;
            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(this.mPendingKeyDownTime, time, 0, this.mPendingKeyCode, this.mPendingKeyRepeatCount, this.mPendingKeyMetaState, this.mCurrentDeviceId, 1024, this.mCurrentSource));
        }

        private void sendKeyUp(long time) {
            if (this.mPendingKeyCode != 0) {
                ViewRootImpl.this.enqueueInputEvent(new KeyEvent(this.mPendingKeyDownTime, time, 1, this.mPendingKeyCode, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
                this.mPendingKeyCode = 0;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0048  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean startFling(long time, float vx, float vy) {
            int i = this.mPendingKeyCode;
            switch (i) {
                case 19:
                    if ((-vy) < this.mConfigMinFlingVelocity || Math.abs(vx) >= this.mConfigMinFlingVelocity) {
                        return false;
                    }
                    this.mFlingVelocity = -vy;
                    this.mFlinging = postFling(time);
                    return this.mFlinging;
                case 20:
                    if (vy < this.mConfigMinFlingVelocity || Math.abs(vx) >= this.mConfigMinFlingVelocity) {
                        return false;
                    }
                    this.mFlingVelocity = vy;
                    this.mFlinging = postFling(time);
                    return this.mFlinging;
                case 21:
                    if ((-vx) < this.mConfigMinFlingVelocity || Math.abs(vy) >= this.mConfigMinFlingVelocity) {
                        return false;
                    }
                    this.mFlingVelocity = -vx;
                    this.mFlinging = postFling(time);
                    return this.mFlinging;
                case 22:
                    if (vx < this.mConfigMinFlingVelocity || Math.abs(vy) >= this.mConfigMinFlingVelocity) {
                        return false;
                    }
                    this.mFlingVelocity = vx;
                    this.mFlinging = postFling(time);
                    return this.mFlinging;
                default:
                    switch (i) {
                        case 691:
                            break;
                        case 692:
                            break;
                        default:
                            this.mFlinging = postFling(time);
                            return this.mFlinging;
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean postFling(long time) {
            if (this.mFlingVelocity >= this.mConfigMinFlingVelocity) {
                long delay = (this.mConfigTickDistance / this.mFlingVelocity) * 1000.0f;
                postAtTime(this.mFlingRunnable, time + delay);
                return true;
            }
            return false;
        }

        private void cancelFling() {
            if (this.mFlinging) {
                removeCallbacks(this.mFlingRunnable);
                this.mFlinging = false;
            }
        }
    }

    /* loaded from: classes4.dex */
    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {
        }

        public void process(KeyEvent event) {
            if ((event.getFlags() & 1024) != 0) {
                return;
            }
            KeyCharacterMap kcm = event.getKeyCharacterMap();
            int keyCode = event.getKeyCode();
            int metaState = event.getMetaState();
            KeyCharacterMap.FallbackAction fallbackAction = kcm.getFallbackAction(keyCode, metaState);
            if (fallbackAction != null) {
                int flags = event.getFlags() | 1024;
                KeyEvent fallbackEvent = KeyEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), fallbackAction.keyCode, event.getRepeatCount(), fallbackAction.metaState, event.getDeviceId(), event.getScanCode(), flags, event.getSource(), null);
                fallbackAction.recycle();
                ViewRootImpl.this.enqueueInputEvent(fallbackEvent);
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkForLeavingTouchModeAndConsume(KeyEvent event) {
        if (this.mAttachInfo.mInTouchMode) {
            int action = event.getAction();
            if ((action == 0 || action == 2) && (event.getFlags() & 4) == 0) {
                if (isNavigationKey(event)) {
                    return ensureTouchMode(false);
                }
                if (isTypingKey(event)) {
                    ensureTouchMode(false);
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @UnsupportedAppUsage
    void setLocalDragState(Object obj) {
        this.mLocalDragState = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
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
                setDragFocus(null, event);
            } else {
                if (what == 2 || what == 3) {
                    this.mDragPoint.set(event.f2398mX, event.f2399mY);
                    if (this.mTranslator != null) {
                        this.mTranslator.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    if (this.mCurScrollY != 0) {
                        this.mDragPoint.offset(0.0f, this.mCurScrollY);
                    }
                    event.f2398mX = this.mDragPoint.f61x;
                    event.f2399mY = this.mDragPoint.f62y;
                }
                View prevDragView = this.mCurrentDragView;
                if (what == 3 && event.mClipData != null) {
                    event.mClipData.prepareToEnterProcess();
                }
                boolean result = this.mView.dispatchDragEvent(event);
                if (what == 2 && !event.mEventHandlerWasCalled) {
                    setDragFocus(null, event);
                }
                if (prevDragView != this.mCurrentDragView) {
                    if (prevDragView != null) {
                        try {
                            this.mWindowSession.dragRecipientExited(this.mWindow);
                        } catch (RemoteException e) {
                            Slog.m56e(this.mTag, "Unable to note drag target change");
                        }
                    }
                    if (this.mCurrentDragView != null) {
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                }
                if (what == 3) {
                    try {
                        String str = this.mTag;
                        Log.m68i(str, "Reporting drop result: " + result);
                        this.mWindowSession.reportDropResult(this.mWindow, result);
                    } catch (RemoteException e2) {
                        Log.m70e(this.mTag, "Unable to report drop result");
                    }
                }
                if (what == 4) {
                    this.mCurrentDragView = null;
                    setLocalDragState(null);
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
        if (this.mView == null) {
            return;
        }
        if (args.localChanges != 0) {
            this.mView.updateLocalSystemUiVisibility(args.localValue, args.localChanges);
        }
        int visibility = args.globalVisibility & 7;
        if (visibility != this.mAttachInfo.mGlobalSystemUiVisibility) {
            this.mAttachInfo.mGlobalSystemUiVisibility = visibility;
            this.mView.dispatchSystemUiVisibilityChanged(visibility);
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
        outLocation.f59x = (int) this.mLastTouchPoint.f61x;
        outLocation.f60y = (int) this.mLastTouchPoint.f62y;
    }

    public int getLastTouchSource() {
        return this.mLastTouchSource;
    }

    public void setDragFocus(View newDragTarget, DragEvent event) {
        if (this.mCurrentDragView != newDragTarget && !View.sCascadedDragDrop) {
            float tx = event.f2398mX;
            float ty = event.f2399mY;
            int action = event.mAction;
            ClipData td = event.mClipData;
            event.f2398mX = 0.0f;
            event.f2399mY = 0.0f;
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
            event.f2398mX = tx;
            event.f2399mY = ty;
            event.mClipData = td;
        }
        this.mCurrentDragView = newDragTarget;
    }

    private AudioManager getAudioManager() {
        if (this.mView == null) {
            throw new IllegalStateException("getAudioManager called when there is no mView");
        }
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mView.getContext().getSystemService("audio");
        }
        return this.mAudioManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AutofillManager getAutofillManager() {
        if (this.mView instanceof ViewGroup) {
            ViewGroup decorView = (ViewGroup) this.mView;
            if (decorView.getChildCount() > 0) {
                return (AutofillManager) decorView.getChildAt(0).getContext().getSystemService(AutofillManager.class);
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAutofillUiShowing() {
        AutofillManager afm = getAutofillManager();
        if (afm == null) {
            return false;
        }
        return afm.isAutofillUiShowing();
    }

    public AccessibilityInteractionController getAccessibilityInteractionController() {
        if (this.mView == null) {
            throw new IllegalStateException("getAccessibilityInteractionController called when there is no mView");
        }
        if (this.mAccessibilityInteractionController == null) {
            this.mAccessibilityInteractionController = new AccessibilityInteractionController(this);
        }
        return this.mAccessibilityInteractionController;
    }

    private int relayoutWindow(WindowManager.LayoutParams params, int viewVisibility, boolean insetsPending) throws RemoteException {
        float appScale = this.mAttachInfo.mApplicationScale;
        boolean restore = false;
        if (params != null && this.mTranslator != null) {
            restore = true;
            params.backup();
            this.mTranslator.translateWindowLayout(params);
        }
        boolean restore2 = restore;
        if (params != null && this.mOrigWindowType != params.type && this.mTargetSdkVersion < 14) {
            String str = this.mTag;
            Slog.m50w(str, "Window type can not be changed after the window is added; ignoring change of " + this.mView);
            params.type = this.mOrigWindowType;
        }
        long frameNumber = -1;
        if (this.mSurface.isValid()) {
            frameNumber = this.mSurface.getNextFrameNumber();
        }
        int relayoutResult = this.mWindowSession.relayout(this.mWindow, this.mSeq, params, (int) ((this.mView.getMeasuredWidth() * appScale) + 0.5f), (int) ((this.mView.getMeasuredHeight() * appScale) + 0.5f), viewVisibility, insetsPending ? 1 : 0, frameNumber, this.mTmpFrame, this.mPendingOverscanInsets, this.mPendingContentInsets, this.mPendingVisibleInsets, this.mPendingStableInsets, this.mPendingOutsets, this.mPendingBackDropFrame, this.mPendingDisplayCutout, this.mPendingMergedConfiguration, this.mSurfaceControl, this.mTempInsets);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setFrame(Rect frame) {
        this.mWinFrame.set(frame);
        this.mInsetsController.onFrameChanged(frame);
    }

    @Override // android.view.View.AttachInfo.Callbacks
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
            Log.m70e(str, "FATAL EXCEPTION when attempting to play sound effect: " + e);
            e.printStackTrace();
        }
    }

    @Override // android.view.View.AttachInfo.Callbacks
    public boolean performHapticFeedback(int effectId, boolean always) {
        try {
            return this.mWindowSession.performHapticFeedback(effectId, always);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.view.ViewParent
    public View focusSearch(View focused, int direction) {
        checkThread();
        if (!(this.mView instanceof ViewGroup)) {
            return null;
        }
        return FocusFinder.getInstance().findNextFocus((ViewGroup) this.mView, focused, direction);
    }

    @Override // android.view.ViewParent
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
        if (!(view instanceof ViewGroup) || (N = (grp = (ViewGroup) view).getChildCount()) <= 0) {
            return;
        }
        String prefix2 = prefix + "  ";
        for (int i = 0; i < N; i++) {
            dumpViewHierarchy(prefix2, writer, grp.getChildAt(i));
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

    boolean die(boolean immediate) {
        if (immediate && !this.mIsInTraversal) {
            doDie();
            return false;
        }
        if (!this.mIsDrawing) {
            destroyHardwareRenderer();
        } else {
            String str = this.mTag;
            Log.m70e(str, "Attempting to destroy the window while drawing!\n  window=" + this + ", title=" + ((Object) this.mWindowAttributes.getTitle()));
        }
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    void doDie() {
        checkThread();
        synchronized (this) {
            if (this.mRemoved) {
                return;
            }
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

    public void requestUpdateConfiguration(Configuration config) {
        Message msg = this.mHandler.obtainMessage(18, config);
        this.mHandler.sendMessage(msg);
    }

    public void loadSystemProperties() {
        this.mHandler.post(new Runnable() { // from class: android.view.ViewRootImpl.4
            @Override // java.lang.Runnable
            public void run() {
                ViewRootImpl.this.mProfileRendering = SystemProperties.getBoolean(ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                ViewRootImpl.this.profileRendering(ViewRootImpl.this.mAttachInfo.mHasWindowFocus);
                if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null && ViewRootImpl.this.mAttachInfo.mThreadedRenderer.loadSystemProperties()) {
                    ViewRootImpl.this.invalidate();
                }
                boolean layout = DisplayProperties.debug_layout().orElse(false).booleanValue();
                if (layout != ViewRootImpl.this.mAttachInfo.mDebugLayout) {
                    ViewRootImpl.this.mAttachInfo.mDebugLayout = layout;
                    if (!ViewRootImpl.this.mHandler.hasMessages(22)) {
                        ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(22, 200L);
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

    /* JADX INFO: Access modifiers changed from: private */
    @UnsupportedAppUsage
    public void dispatchResized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration mergedConfiguration, Rect backDropFrame, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) {
        if (this.mDragResizing && this.mUseMTRenderer) {
            boolean fullscreen = frame.equals(backDropFrame);
            synchronized (this.mWindowCallbacks) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
                    this.mWindowCallbacks.get(i).onWindowSizeIsChanging(backDropFrame, fullscreen, visibleInsets, stableInsets);
                }
            }
        }
        Message msg = this.mHandler.obtainMessage(reportDraw ? 5 : 4);
        if (this.mTranslator != null) {
            this.mTranslator.translateRectInScreenToAppWindow(frame);
            this.mTranslator.translateRectInScreenToAppWindow(overscanInsets);
            this.mTranslator.translateRectInScreenToAppWindow(contentInsets);
            this.mTranslator.translateRectInScreenToAppWindow(visibleInsets);
        }
        SomeArgs args = SomeArgs.obtain();
        boolean sameProcessCall = Binder.getCallingPid() == Process.myPid();
        args.arg1 = sameProcessCall ? new Rect(frame) : frame;
        args.arg2 = sameProcessCall ? new Rect(contentInsets) : contentInsets;
        args.arg3 = sameProcessCall ? new Rect(visibleInsets) : visibleInsets;
        args.arg4 = (!sameProcessCall || mergedConfiguration == null) ? mergedConfiguration : new MergedConfiguration(mergedConfiguration);
        args.arg5 = sameProcessCall ? new Rect(overscanInsets) : overscanInsets;
        args.arg6 = sameProcessCall ? new Rect(stableInsets) : stableInsets;
        args.arg7 = sameProcessCall ? new Rect(outsets) : outsets;
        args.arg8 = sameProcessCall ? new Rect(backDropFrame) : backDropFrame;
        args.arg9 = displayCutout.get();
        args.argi1 = forceLayout ? 1 : 0;
        args.argi2 = alwaysConsumeSystemBars ? 1 : 0;
        args.argi3 = displayId;
        msg.obj = args;
        this.mHandler.sendMessage(msg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInsetsChanged(InsetsState insetsState) {
        this.mHandler.obtainMessage(30, insetsState).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInsetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) {
        SomeArgs args = SomeArgs.obtain();
        args.arg1 = insetsState;
        args.arg2 = activeControls;
        this.mHandler.obtainMessage(31, args).sendToTarget();
    }

    public void dispatchMoved(int newX, int newY) {
        if (this.mTranslator != null) {
            PointF point = new PointF(newX, newY);
            this.mTranslator.translatePointInScreenToAppWindow(point);
            newX = (int) (point.f61x + 0.5d);
            newY = (int) (point.f62y + 0.5d);
        }
        Message msg = this.mHandler.obtainMessage(23, newX, newY);
        this.mHandler.sendMessage(msg);
    }

    /* loaded from: classes4.dex */
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
            return (this.mEvent instanceof MotionEvent) && (this.mEvent.isFromSource(2) || this.mEvent.isFromSource(4194304));
        }

        public boolean shouldSendToSynthesizer() {
            if ((this.mFlags & 32) != 0) {
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("QueuedInputEvent{flags=");
            boolean hasPrevious = flagToString("DELIVER_POST_IME", 1, false, sb);
            if (!flagToString("UNHANDLED", 32, flagToString("RESYNTHESIZED", 16, flagToString("FINISHED_HANDLED", 8, flagToString("FINISHED", 4, flagToString("DEFERRED", 2, hasPrevious, sb), sb), sb), sb), sb)) {
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
            if ((this.mFlags & flag) != 0) {
                if (hasPrevious) {
                    sb.append("|");
                }
                sb.append(name);
                return true;
            }
            return hasPrevious;
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

    @UnsupportedAppUsage
    void enqueueInputEvent(InputEvent event) {
        enqueueInputEvent(event, null, 0, false);
    }

    @UnsupportedAppUsage
    void enqueueInputEvent(InputEvent event, InputEventReceiver receiver, int flags, boolean processImmediately) {
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
        Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
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

    void doProcessInputEvents() {
        while (this.mPendingInputEventHead != null) {
            QueuedInputEvent q = this.mPendingInputEventHead;
            this.mPendingInputEventHead = q.mNext;
            if (this.mPendingInputEventHead == null) {
                this.mPendingInputEventTail = null;
            }
            q.mNext = null;
            this.mPendingInputEventCount--;
            Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
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
        Trace.asyncTraceBegin(8L, "deliverInputEvent", q.mEvent.getSequenceNumber());
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

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputEvent(QueuedInputEvent q) {
        Trace.asyncTraceEnd(8L, "deliverInputEvent", q.mEvent.getSequenceNumber());
        if (q.mReceiver != null) {
            boolean handled = (q.mFlags & 8) != 0;
            boolean modified = (q.mFlags & 64) != 0;
            if (modified) {
                Trace.traceBegin(8L, "processInputEventBeforeFinish");
                try {
                    InputEvent processedEvent = this.mInputCompatProcessor.processInputEventBeforeFinish(q.mEvent);
                    if (processedEvent != null) {
                        q.mReceiver.finishInputEvent(processedEvent, handled);
                    }
                } finally {
                    Trace.traceEnd(8L);
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
        if (event instanceof KeyEvent) {
            KeyEvent keyEvent = (KeyEvent) event;
            return keyEvent.getAction() == 1;
        }
        MotionEvent motionEvent = (MotionEvent) event;
        int action = motionEvent.getAction();
        return action == 1 || action == 3 || action == 10;
    }

    void scheduleConsumeBatchedInput() {
        if (!this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = true;
            this.mChoreographer.postCallback(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void unscheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void scheduleConsumeBatchedInputImmediately() {
        if (!this.mConsumeBatchedInputImmediatelyScheduled) {
            unscheduleConsumeBatchedInput();
            this.mConsumeBatchedInputImmediatelyScheduled = true;
            this.mHandler.post(this.mConsumeBatchedInputImmediatelyRunnable);
        }
    }

    void doConsumeBatchedInput(long frameTimeNanos) {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            if (this.mInputEventReceiver != null && this.mInputEventReceiver.consumeBatchedInputEvents(frameTimeNanos) && frameTimeNanos != -1) {
                scheduleConsumeBatchedInput();
            }
            doProcessInputEvents();
        }
    }

    /* loaded from: classes4.dex */
    final class TraversalRunnable implements Runnable {
        TraversalRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewRootImpl.this.doTraversal();
        }
    }

    /* loaded from: classes4.dex */
    final class WindowInputEventReceiver extends InputEventReceiver {
        public WindowInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override // android.view.InputEventReceiver
        public void onInputEvent(InputEvent event) {
            Trace.traceBegin(8L, "processInputEventForCompatibility");
            try {
                List<InputEvent> processedEvents = ViewRootImpl.this.mInputCompatProcessor.processInputEventForCompatibility(event);
                Trace.traceEnd(8L);
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
                Trace.traceEnd(8L);
                throw th;
            }
        }

        @Override // android.view.InputEventReceiver
        public void onBatchedInputEventPending() {
            if (ViewRootImpl.this.mUnbufferedInputDispatch) {
                super.onBatchedInputEventPending();
            } else {
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
        }

        @Override // android.view.InputEventReceiver
        public void dispose() {
            ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }
    }

    /* loaded from: classes4.dex */
    final class ConsumeBatchedInputRunnable implements Runnable {
        ConsumeBatchedInputRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewRootImpl.this.doConsumeBatchedInput(ViewRootImpl.this.mChoreographer.getFrameTimeNanos());
        }
    }

    /* loaded from: classes4.dex */
    final class ConsumeBatchedInputImmediatelyRunnable implements Runnable {
        ConsumeBatchedInputImmediatelyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewRootImpl.this.doConsumeBatchedInput(-1L);
        }
    }

    /* loaded from: classes4.dex */
    final class InvalidateOnAnimationRunnable implements Runnable {
        private boolean mPosted;
        private View.AttachInfo.InvalidateInfo[] mTempViewRects;
        private View[] mTempViews;
        private final ArrayList<View> mViews = new ArrayList<>();
        private final ArrayList<View.AttachInfo.InvalidateInfo> mViewRects = new ArrayList<>();

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
                    ViewRootImpl.this.mChoreographer.removeCallbacks(1, this, null);
                    this.mPosted = false;
                }
            }
        }

        @Override // java.lang.Runnable
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
                ViewRootImpl.this.mChoreographer.postCallback(1, this, null);
                this.mPosted = true;
            }
        }
    }

    public void dispatchInvalidateDelayed(View view, long delayMilliseconds) {
        Message msg = this.mHandler.obtainMessage(1, view);
        this.mHandler.sendMessageDelayed(msg, delayMilliseconds);
    }

    public void dispatchInvalidateRectDelayed(View.AttachInfo.InvalidateInfo info, long delayMilliseconds) {
        Message msg = this.mHandler.obtainMessage(2, info);
        this.mHandler.sendMessageDelayed(msg, delayMilliseconds);
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
        dispatchInputEvent(event, null);
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
        msg.arg1 = visible ? 1 : 0;
        this.mHandler.sendMessage(msg);
    }

    public void dispatchGetNewSurface() {
        Message msg = this.mHandler.obtainMessage(9);
        this.mHandler.sendMessage(msg);
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
        Message msg = this.mHandler.obtainMessage(what, event);
        this.mHandler.sendMessage(msg);
    }

    public void updatePointerIcon(float x, float y) {
        this.mHandler.removeMessages(27);
        long now = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(0L, now, 7, x, y, 0);
        Message msg = this.mHandler.obtainMessage(27, event);
        this.mHandler.sendMessage(msg);
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
        msg.arg1 = on ? 1 : 0;
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

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View originalView) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean showContextMenuForChild(View originalView, float x, float y) {
        return false;
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewParent
    public ActionMode startActionModeForChild(View originalView, ActionMode.Callback callback, int type) {
        return null;
    }

    @Override // android.view.ViewParent
    public void createContextMenu(ContextMenu menu) {
    }

    @Override // android.view.ViewParent
    public void childDrawableStateChanged(View child) {
    }

    @Override // android.view.ViewParent
    public boolean requestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        AccessibilityNodeProvider provider;
        if (this.mView == null || this.mStopped || this.mPausedForTransition) {
            return false;
        }
        if (event.getEventType() != 2048 && this.mSendWindowContentChangedAccessibilityEvent != null && this.mSendWindowContentChangedAccessibilityEvent.mSource != null) {
            this.mSendWindowContentChangedAccessibilityEvent.removeCallbacksAndRun();
        }
        int eventType = event.getEventType();
        View source = getSourceForAccessibilityEvent(event);
        if (eventType == 2048) {
            handleWindowContentChangedEvent(event);
        } else if (eventType == 32768) {
            if (source != null && (provider = source.getAccessibilityNodeProvider()) != null) {
                int virtualNodeId = AccessibilityNodeInfo.getVirtualDescendantId(event.getSourceNodeId());
                AccessibilityNodeInfo node = provider.createAccessibilityNodeInfo(virtualNodeId);
                setAccessibilityFocus(source, node);
            }
        } else if (eventType == 65536 && source != null && source.getAccessibilityNodeProvider() != null) {
            setAccessibilityFocus(null, null);
        }
        this.mAccessibilityManager.sendAccessibilityEvent(event);
        return true;
    }

    private View getSourceForAccessibilityEvent(AccessibilityEvent event) {
        long sourceNodeId = event.getSourceNodeId();
        int accessibilityViewId = AccessibilityNodeInfo.getAccessibilityViewId(sourceNodeId);
        return AccessibilityNodeIdManager.getInstance().findView(accessibilityViewId);
    }

    private void handleWindowContentChangedEvent(AccessibilityEvent event) {
        View focusedHost = this.mAccessibilityFocusedHost;
        if (focusedHost == null || this.mAccessibilityFocusedVirtualView == null) {
            return;
        }
        AccessibilityNodeProvider provider = focusedHost.getAccessibilityNodeProvider();
        if (provider == null) {
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            focusedHost.clearAccessibilityFocusNoCallbacks(0);
            return;
        }
        int changes = event.getContentChangeTypes();
        if ((changes & 1) == 0 && changes != 0) {
            return;
        }
        long eventSourceNodeId = event.getSourceNodeId();
        int changedViewId = AccessibilityNodeInfo.getAccessibilityViewId(eventSourceNodeId);
        boolean hostInSubtree = false;
        View root = this.mAccessibilityFocusedHost;
        while (root != null && !hostInSubtree) {
            if (changedViewId == root.getAccessibilityViewId()) {
                hostInSubtree = true;
            } else {
                ViewParent parent = root.getParent();
                if (parent instanceof View) {
                    root = (View) parent;
                } else {
                    root = null;
                }
            }
        }
        if (!hostInSubtree) {
            return;
        }
        long focusedSourceNodeId = this.mAccessibilityFocusedVirtualView.getSourceNodeId();
        int focusedChildId = AccessibilityNodeInfo.getVirtualDescendantId(focusedSourceNodeId);
        Rect oldBounds = this.mTempRect;
        this.mAccessibilityFocusedVirtualView.getBoundsInScreen(oldBounds);
        this.mAccessibilityFocusedVirtualView = provider.createAccessibilityNodeInfo(focusedChildId);
        if (this.mAccessibilityFocusedVirtualView == null) {
            this.mAccessibilityFocusedHost = null;
            focusedHost.clearAccessibilityFocusNoCallbacks(0);
            provider.performAction(focusedChildId, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
            invalidateRectOnScreen(oldBounds);
            return;
        }
        Rect newBounds = this.mAccessibilityFocusedVirtualView.getBoundsInScreen();
        if (!oldBounds.equals(newBounds)) {
            oldBounds.union(newBounds);
            invalidateRectOnScreen(oldBounds);
        }
    }

    @Override // android.view.ViewParent
    public void notifySubtreeAccessibilityStateChanged(View child, View source, int changeType) {
        postSendWindowContentChangedCallback((View) Preconditions.checkNotNull(source), changeType);
    }

    @Override // android.view.ViewParent
    public boolean canResolveLayoutDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isLayoutDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getLayoutDirection() {
        return 0;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextDirection() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextDirectionResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getTextDirection() {
        return 1;
    }

    @Override // android.view.ViewParent
    public boolean canResolveTextAlignment() {
        return true;
    }

    @Override // android.view.ViewParent
    public boolean isTextAlignmentResolved() {
        return true;
    }

    @Override // android.view.ViewParent
    public int getTextAlignment() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getCommonPredecessor(View first, View second) {
        if (this.mTempHashSet == null) {
            this.mTempHashSet = new HashSet<>();
        }
        HashSet<View> seen = this.mTempHashSet;
        seen.clear();
        View firstCurrent = first;
        while (firstCurrent != null) {
            seen.add(firstCurrent);
            ViewParent firstCurrentParent = firstCurrent.mParent;
            if (firstCurrentParent instanceof View) {
                firstCurrent = (View) firstCurrentParent;
            } else {
                firstCurrent = null;
            }
        }
        View secondCurrent = second;
        while (secondCurrent != null) {
            if (seen.contains(secondCurrent)) {
                seen.clear();
                return secondCurrent;
            }
            ViewParent secondCurrentParent = secondCurrent.mParent;
            if (secondCurrentParent instanceof View) {
                secondCurrent = (View) secondCurrentParent;
            } else {
                secondCurrent = null;
            }
        }
        seen.clear();
        return null;
    }

    void checkThread() {
        if (this.mThread != Thread.currentThread()) {
            throw new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views.");
        }
    }

    @Override // android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override // android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
        if (rectangle == null) {
            return scrollToRectOrFocus(null, immediate);
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

    @Override // android.view.ViewParent
    public void childHasTransientStateChanged(View child, boolean hasTransientState) {
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public void onStopNestedScroll(View target) {
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override // android.view.ViewParent, android.support.p011v4.view.NestedScrollingParent
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override // android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
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

    void changeCanvasOpacity(boolean opaque) {
        String str = this.mTag;
        Log.m72d(str, "changeCanvasOpacity: opaque=" + opaque);
        boolean opaque2 = opaque & ((this.mView.mPrivateFlags & 512) == 0);
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(opaque2);
        }
    }

    public boolean dispatchUnhandledKeyEvent(KeyEvent event) {
        return this.mUnhandledKeyManager.dispatch(this.mView, event);
    }

    /* loaded from: classes4.dex */
    class TakenSurfaceHolder extends BaseSurfaceHolder {
        TakenSurfaceHolder() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public boolean onAllowLockCanvas() {
            return ViewRootImpl.this.mDrawingAllowed;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onRelayoutContainer() {
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFormat(int format) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceFormat(format);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setType(int type) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceType(type);
        }

        @Override // com.android.internal.view.BaseSurfaceHolder
        public void onUpdateSurface() {
            throw new IllegalStateException("Shouldn't be here");
        }

        @Override // android.view.SurfaceHolder
        public boolean isCreating() {
            return ViewRootImpl.this.mIsCreating;
        }

        @Override // com.android.internal.view.BaseSurfaceHolder, android.view.SurfaceHolder
        public void setFixedSize(int width, int height) {
            throw new UnsupportedOperationException("Currently only support sizing from layout");
        }

        @Override // android.view.SurfaceHolder
        public void setKeepScreenOn(boolean screenOn) {
            ((RootViewSurfaceTaker) ViewRootImpl.this.mView).setSurfaceKeepScreenOn(screenOn);
        }
    }

    /* renamed from: android.view.ViewRootImpl$W */
    /* loaded from: classes4.dex */
    static class BinderC2750W extends IWindow.Stub {
        private final WeakReference<ViewRootImpl> mViewAncestor;
        private final IWindowSession mWindowSession;

        BinderC2750W(ViewRootImpl viewAncestor) {
            this.mViewAncestor = new WeakReference<>(viewAncestor);
            this.mWindowSession = viewAncestor.mWindowSession;
        }

        @Override // android.view.IWindow
        public void resized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration mergedConfiguration, Rect backDropFrame, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchResized(frame, overscanInsets, contentInsets, visibleInsets, stableInsets, outsets, reportDraw, mergedConfiguration, backDropFrame, forceLayout, alwaysConsumeSystemBars, displayId, displayCutout);
            }
        }

        @Override // android.view.IWindow
        public void insetsChanged(InsetsState insetsState) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchInsetsChanged(insetsState);
            }
        }

        @Override // android.view.IWindow
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchInsetsControlChanged(insetsState, activeControls);
            }
        }

        @Override // android.view.IWindow
        public void moved(int newX, int newY) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchMoved(newX, newY);
            }
        }

        @Override // android.view.IWindow
        public void dispatchAppVisibility(boolean visible) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchAppVisibility(visible);
            }
        }

        @Override // android.view.IWindow
        public void dispatchGetNewSurface() {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchGetNewSurface();
            }
        }

        @Override // android.view.IWindow
        public void windowFocusChanged(boolean hasFocus, boolean inTouchMode) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
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

        @Override // android.view.IWindow
        public void executeCommand(String command, String parameters, ParcelFileDescriptor out) {
            View view;
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null && (view = viewAncestor.mView) != null) {
                if (checkCallingPermission(Manifest.C0000permission.DUMP) != 0) {
                    throw new SecurityException("Insufficient permissions to invoke executeCommand() from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
                }
                OutputStream clientStream = null;
                try {
                    try {
                        try {
                            clientStream = new ParcelFileDescriptor.AutoCloseOutputStream(out);
                            ViewDebug.dispatchCommand(view, command, parameters, clientStream);
                            clientStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            if (clientStream != null) {
                                clientStream.close();
                            }
                        }
                    } catch (Throwable th) {
                        if (clientStream != null) {
                            try {
                                clientStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }

        @Override // android.view.IWindow
        public void closeSystemDialogs(String reason) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchCloseSystemDialogs(reason);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(asBinder());
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) {
            if (sync) {
                try {
                    this.mWindowSession.wallpaperCommandComplete(asBinder(), null);
                } catch (RemoteException e) {
                }
            }
        }

        @Override // android.view.IWindow
        public void dispatchDragEvent(DragEvent event) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchDragEvent(event);
            }
        }

        @Override // android.view.IWindow
        public void updatePointerIcon(float x, float y) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.updatePointerIcon(x, y);
            }
        }

        @Override // android.view.IWindow
        public void dispatchSystemUiVisibilityChanged(int seq, int globalVisibility, int localValue, int localChanges) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchSystemUiVisibilityChanged(seq, globalVisibility, localValue, localChanges);
            }
        }

        @Override // android.view.IWindow
        public void dispatchWindowShown() {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchWindowShown();
            }
        }

        @Override // android.view.IWindow
        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchRequestKeyboardShortcuts(receiver, deviceId);
            }
        }

        @Override // android.view.IWindow
        public void dispatchPointerCaptureChanged(boolean hasCapture) {
            ViewRootImpl viewAncestor = this.mViewAncestor.get();
            if (viewAncestor != null) {
                viewAncestor.dispatchPointerCaptureChanged(hasCapture);
            }
        }
    }

    /* loaded from: classes4.dex */
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
        return updated | ((this.mDragResizing && this.mReportNextDraw) ? false : false);
    }

    private void requestDrawWindow() {
        if (!this.mUseMTRenderer) {
            return;
        }
        this.mWindowDrawCountDown = new CountDownLatch(this.mWindowCallbacks.size());
        for (int i = this.mWindowCallbacks.size() - 1; i >= 0; i--) {
            this.mWindowCallbacks.get(i).onRequestDraw(this.mReportNextDraw);
        }
    }

    public void reportActivityRelaunched() {
        this.mActivityRelaunched = true;
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    /* loaded from: classes4.dex */
    final class AccessibilityInteractionConnectionManager implements AccessibilityManager.AccessibilityStateChangeListener {
        AccessibilityInteractionConnectionManager() {
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
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
            boolean registered = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (!registered) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = ViewRootImpl.this.mAccessibilityManager.addAccessibilityInteractionConnection(ViewRootImpl.this.mWindow, ViewRootImpl.this.mContext.getPackageName(), new AccessibilityInteractionConnection(ViewRootImpl.this));
            }
        }

        public void ensureNoConnection() {
            boolean registered = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (registered) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = -1;
                ViewRootImpl.this.mAccessibilityManager.removeAccessibilityInteractionConnection(ViewRootImpl.this.mWindow);
            }
        }
    }

    /* loaded from: classes4.dex */
    final class HighContrastTextManager implements AccessibilityManager.HighTextContrastChangeListener {
        HighContrastTextManager() {
            ThreadedRenderer.setHighContrastText(ViewRootImpl.this.mAccessibilityManager.isHighTextContrastEnabled());
        }

        @Override // android.view.accessibility.AccessibilityManager.HighTextContrastChangeListener
        public void onHighTextContrastStateChanged(boolean enabled) {
            ThreadedRenderer.setHighContrastText(enabled);
            ViewRootImpl.this.destroyHardwareResources();
            ViewRootImpl.this.invalidate();
        }
    }

    /* loaded from: classes4.dex */
    static final class AccessibilityInteractionConnection extends IAccessibilityInteractionConnection.Stub {
        private final WeakReference<ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new WeakReference<>(viewRootImpl);
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfoByAccessibilityId(long accessibilityNodeId, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, Bundle args) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfoByAccessibilityIdClientThread(accessibilityNodeId, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, args);
                return;
            }
            try {
                callback.setFindAccessibilityNodeInfosResult(null, interactionId);
            } catch (RemoteException e) {
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void performAccessibilityAction(long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().performAccessibilityActionClientThread(accessibilityNodeId, action, arguments, interactionId, callback, flags, interrogatingPid, interrogatingTid);
                return;
            }
            try {
                callback.setPerformAccessibilityActionResult(false, interactionId);
            } catch (RemoteException e) {
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByViewId(long accessibilityNodeId, String viewId, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByViewIdClientThread(accessibilityNodeId, viewId, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                return;
            }
            try {
                callback.setFindAccessibilityNodeInfoResult(null, interactionId);
            } catch (RemoteException e) {
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findAccessibilityNodeInfosByText(long accessibilityNodeId, String text, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByTextClientThread(accessibilityNodeId, text, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                return;
            }
            try {
                callback.setFindAccessibilityNodeInfosResult(null, interactionId);
            } catch (RemoteException e) {
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void findFocus(long accessibilityNodeId, int focusType, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findFocusClientThread(accessibilityNodeId, focusType, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                return;
            }
            try {
                callback.setFindAccessibilityNodeInfoResult(null, interactionId);
            } catch (RemoteException e) {
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void focusSearch(long accessibilityNodeId, int direction, Region interactiveRegion, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().focusSearchClientThread(accessibilityNodeId, direction, interactiveRegion, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                return;
            }
            try {
                callback.setFindAccessibilityNodeInfoResult(null, interactionId);
            } catch (RemoteException e) {
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void clearAccessibilityFocus() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().clearAccessibilityFocusClientThread();
            }
        }

        @Override // android.view.accessibility.IAccessibilityInteractionConnection
        public void notifyOutsideTouch() {
            ViewRootImpl viewRootImpl = this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().notifyOutsideTouchClientThread();
            }
        }
    }

    /* loaded from: classes4.dex */
    private class SendWindowContentChangedAccessibilityEvent implements Runnable {
        private int mChangeTypes;
        public long mLastEventTimeMillis;
        public StackTraceElement[] mOrigin;
        public View mSource;

        private SendWindowContentChangedAccessibilityEvent() {
            this.mChangeTypes = 0;
        }

        @Override // java.lang.Runnable
        public void run() {
            View source = this.mSource;
            this.mSource = null;
            if (source == null) {
                Log.m70e(ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (AccessibilityManager.getInstance(ViewRootImpl.this.mContext).isEnabled()) {
                this.mLastEventTimeMillis = SystemClock.uptimeMillis();
                AccessibilityEvent event = AccessibilityEvent.obtain();
                event.setEventType(2048);
                event.setContentChangeTypes(this.mChangeTypes);
                source.sendAccessibilityEventUnchecked(event);
            } else {
                this.mLastEventTimeMillis = 0L;
            }
            source.resetSubtreeAccessibilityStateChanged();
            this.mChangeTypes = 0;
        }

        public void runOrPost(View source, int changeType) {
            if (ViewRootImpl.this.mHandler.getLooper() != Looper.myLooper()) {
                CalledFromWrongThreadException e = new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views.");
                Log.m69e(ViewRootImpl.TAG, "Accessibility content change on non-UI thread. Future Android versions will throw an exception.", e);
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

    /* loaded from: classes4.dex */
    private static class UnhandledKeyManager {
        private final SparseArray<WeakReference<View>> mCapturedKeys;
        private WeakReference<View> mCurrentReceiver;
        private boolean mDispatched;

        private UnhandledKeyManager() {
            this.mDispatched = true;
            this.mCapturedKeys = new SparseArray<>();
            this.mCurrentReceiver = null;
        }

        boolean dispatch(View root, KeyEvent event) {
            if (this.mDispatched) {
                return false;
            }
            try {
                Trace.traceBegin(8L, "UnhandledKeyEvent dispatch");
                this.mDispatched = true;
                View consumer = root.dispatchUnhandledKeyEvent(event);
                if (event.getAction() == 0) {
                    int keycode = event.getKeyCode();
                    if (consumer != null && !KeyEvent.isModifierKey(keycode)) {
                        this.mCapturedKeys.put(keycode, new WeakReference<>(consumer));
                    }
                }
                return consumer != null;
            } finally {
                Trace.traceEnd(8L);
            }
        }

        void preDispatch(KeyEvent event) {
            int idx;
            this.mCurrentReceiver = null;
            if (event.getAction() == 1 && (idx = this.mCapturedKeys.indexOfKey(event.getKeyCode())) >= 0) {
                this.mCurrentReceiver = this.mCapturedKeys.valueAt(idx);
                this.mCapturedKeys.removeAt(idx);
            }
        }

        boolean preViewDispatch(KeyEvent event) {
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(event.getKeyCode());
            }
            if (this.mCurrentReceiver == null) {
                return false;
            }
            View target = this.mCurrentReceiver.get();
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
