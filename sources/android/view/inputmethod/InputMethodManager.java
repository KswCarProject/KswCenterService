package android.view.inputmethod;

import android.Manifest;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.provider.SettingsStringUtil;
import android.text.style.SuggestionSpan;
import android.util.Log;
import android.util.Pools;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.util.SparseArray;
import android.view.ImeInsetsSourceConsumer;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventSender;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.autofill.AutofillManager;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry;
import com.android.internal.os.SomeArgs;
import com.android.internal.view.IInputConnectionWrapper;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputMethodClient;
import com.android.internal.view.IInputMethodManager;
import com.android.internal.view.IInputMethodSession;
import com.android.internal.view.InputBindResult;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class InputMethodManager {
    static final boolean DEBUG = false;
    public static final int DISPATCH_HANDLED = 1;
    public static final int DISPATCH_IN_PROGRESS = -1;
    public static final int DISPATCH_NOT_HANDLED = 0;
    public static final int HIDE_IMPLICIT_ONLY = 1;
    public static final int HIDE_NOT_ALWAYS = 2;
    static final long INPUT_METHOD_NOT_RESPONDING_TIMEOUT = 2500;
    static final int MSG_APPLY_IME_VISIBILITY = 20;
    static final int MSG_BIND = 2;
    static final int MSG_DUMP = 1;
    static final int MSG_FLUSH_INPUT_EVENT = 7;
    static final int MSG_REPORT_FULLSCREEN_MODE = 10;
    static final int MSG_REPORT_PRE_RENDERED = 15;
    static final int MSG_SEND_INPUT_EVENT = 5;
    static final int MSG_SET_ACTIVE = 4;
    static final int MSG_TIMEOUT_INPUT_EVENT = 6;
    static final int MSG_UNBIND = 3;
    static final int MSG_UPDATE_ACTIVITY_VIEW_TO_SCREEN_MATRIX = 30;
    private static final int NOT_A_SUBTYPE_ID = -1;
    static final String PENDING_EVENT_COUNTER = "aq:imm";
    private static final int REQUEST_UPDATE_CURSOR_ANCHOR_INFO_NONE = 0;
    public static final int RESULT_HIDDEN = 3;
    public static final int RESULT_SHOWN = 2;
    public static final int RESULT_UNCHANGED_HIDDEN = 1;
    public static final int RESULT_UNCHANGED_SHOWN = 0;
    public static final int SHOW_FORCED = 2;
    public static final int SHOW_IMPLICIT = 1;
    public static final int SHOW_IM_PICKER_MODE_AUTO = 0;
    public static final int SHOW_IM_PICKER_MODE_EXCLUDE_AUXILIARY_SUBTYPES = 2;
    public static final int SHOW_IM_PICKER_MODE_INCLUDE_AUXILIARY_SUBTYPES = 1;
    private static final String SUBTYPE_MODE_VOICE = "voice";
    static final String TAG = "InputMethodManager";
    @GuardedBy({"sLock"})
    @Deprecated
    @UnsupportedAppUsage
    static InputMethodManager sInstance;
    @GuardedBy({"sLock"})
    private static final SparseArray<InputMethodManager> sInstanceMap = new SparseArray<>();
    private static final Object sLock = new Object();
    boolean mActive = false;
    /* access modifiers changed from: private */
    public Matrix mActivityViewToScreenMatrix = null;
    int mBindSequence = -1;
    final IInputMethodClient.Stub mClient = new IInputMethodClient.Stub() {
        /* access modifiers changed from: protected */
        public void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
            CountDownLatch latch = new CountDownLatch(1);
            SomeArgs sargs = SomeArgs.obtain();
            sargs.arg1 = fd;
            sargs.arg2 = fout;
            sargs.arg3 = args;
            sargs.arg4 = latch;
            InputMethodManager.this.mH.sendMessage(InputMethodManager.this.mH.obtainMessage(1, sargs));
            try {
                if (!latch.await(5, TimeUnit.SECONDS)) {
                    fout.println("Timeout waiting for dump");
                }
            } catch (InterruptedException e) {
                fout.println("Interrupted waiting for dump");
            }
        }

        public void onBindMethod(InputBindResult res) {
            InputMethodManager.this.mH.obtainMessage(2, res).sendToTarget();
        }

        public void onUnbindMethod(int sequence, int unbindReason) {
            InputMethodManager.this.mH.obtainMessage(3, sequence, unbindReason).sendToTarget();
        }

        public void setActive(boolean active, boolean fullscreen) {
            InputMethodManager.this.mH.obtainMessage(4, active, fullscreen).sendToTarget();
        }

        public void reportFullscreenMode(boolean fullscreen) {
            InputMethodManager.this.mH.obtainMessage(10, fullscreen, 0).sendToTarget();
        }

        public void reportPreRendered(EditorInfo info) {
            InputMethodManager.this.mH.obtainMessage(15, 0, 0, info).sendToTarget();
        }

        public void applyImeVisibility(boolean setVisible) {
            InputMethodManager.this.mH.obtainMessage(20, setVisible, 0).sendToTarget();
        }

        public void updateActivityViewToScreenMatrix(int bindSequence, float[] matrixValues) {
            InputMethodManager.this.mH.obtainMessage(30, bindSequence, 0, matrixValues).sendToTarget();
        }
    };
    CompletionInfo[] mCompletions;
    InputChannel mCurChannel;
    @UnsupportedAppUsage
    String mCurId;
    @UnsupportedAppUsage
    IInputMethodSession mCurMethod;
    @UnsupportedAppUsage
    View mCurRootView;
    ImeInputEventSender mCurSender;
    EditorInfo mCurrentTextBoxAttribute;
    /* access modifiers changed from: private */
    public CursorAnchorInfo mCursorAnchorInfo = null;
    int mCursorCandEnd;
    int mCursorCandStart;
    @UnsupportedAppUsage
    Rect mCursorRect = new Rect();
    int mCursorSelEnd;
    int mCursorSelStart;
    private final int mDisplayId;
    final InputConnection mDummyInputConnection = new BaseInputConnection(this, false);
    boolean mFullscreenMode;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    final H mH;
    final IInputContext mIInputContext;
    /* access modifiers changed from: private */
    public ImeInsetsSourceConsumer mImeInsetsConsumer;
    final Looper mMainLooper;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    View mNextServedView;
    final Pools.Pool<PendingEvent> mPendingEventPool = new Pools.SimplePool(20);
    final SparseArray<PendingEvent> mPendingEvents = new SparseArray<>(20);
    /* access modifiers changed from: private */
    public int mRequestUpdateCursorAnchorInfoMonitorMode = 0;
    boolean mRestartOnNextWindowFocus = true;
    boolean mServedConnecting;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    ControlledInputConnectionWrapper mServedInputConnectionWrapper;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    View mServedView;
    @UnsupportedAppUsage
    final IInputMethodManager mService;
    @UnsupportedAppUsage
    Rect mTmpCursorRect = new Rect();

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(Object obj, boolean z);
    }

    public static void ensureDefaultInstanceForDefaultDisplayIfNecessary() {
        forContextInternal(0, Looper.getMainLooper());
    }

    private static boolean isAutofillUIShowing(View servedView) {
        AutofillManager afm = (AutofillManager) servedView.getContext().getSystemService(AutofillManager.class);
        return afm != null && afm.isAutofillUiShowing();
    }

    private InputMethodManager getFallbackInputMethodManagerIfNecessary(View view) {
        ViewRootImpl viewRootImpl;
        int viewRootDisplayId;
        if (view == null || (viewRootImpl = view.getViewRootImpl()) == null || (viewRootDisplayId = viewRootImpl.getDisplayId()) == this.mDisplayId) {
            return null;
        }
        InputMethodManager fallbackImm = (InputMethodManager) viewRootImpl.mContext.getSystemService(InputMethodManager.class);
        if (fallbackImm == null) {
            Log.e(TAG, "b/117267690: Failed to get non-null fallback IMM. view=" + view);
            return null;
        } else if (fallbackImm.mDisplayId != viewRootDisplayId) {
            Log.e(TAG, "b/117267690: Failed to get fallback IMM with expected displayId=" + viewRootDisplayId + " actual IMM#displayId=" + fallbackImm.mDisplayId + " view=" + view);
            return null;
        } else {
            Log.w(TAG, "b/117267690: Display ID mismatch found. ViewRootImpl displayId=" + viewRootDisplayId + " InputMethodManager displayId=" + this.mDisplayId + ". Use the right InputMethodManager instance to avoid performance overhead.", new Throwable());
            return fallbackImm;
        }
    }

    /* access modifiers changed from: private */
    public static boolean canStartInput(View servedView) {
        return servedView.hasWindowFocus() || isAutofillUIShowing(servedView);
    }

    class H extends Handler {
        H(Looper looper) {
            super(looper, (Handler.Callback) null, true);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:147:0x0229, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:202:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:203:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x00bd, code lost:
            if (r2 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x00bf, code lost:
            r11.this$0.startInputInner(6, (android.os.IBinder) null, 0, 0, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x014e, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r12) {
            /*
                r11 = this;
                int r0 = r12.what
                r1 = 10
                r2 = 1
                r3 = 0
                if (r0 == r1) goto L_0x026d
                r1 = 15
                if (r0 == r1) goto L_0x024e
                r1 = 20
                if (r0 == r1) goto L_0x022d
                r1 = 30
                if (r0 == r1) goto L_0x0193
                switch(r0) {
                    case 1: goto L_0x0152;
                    case 2: goto L_0x00cd;
                    case 3: goto L_0x008e;
                    case 4: goto L_0x0032;
                    case 5: goto L_0x0028;
                    case 6: goto L_0x0020;
                    case 7: goto L_0x0018;
                    default: goto L_0x0017;
                }
            L_0x0017:
                return
            L_0x0018:
                android.view.inputmethod.InputMethodManager r0 = android.view.inputmethod.InputMethodManager.this
                int r1 = r12.arg1
                r0.finishedInputEvent(r1, r3, r3)
                return
            L_0x0020:
                android.view.inputmethod.InputMethodManager r0 = android.view.inputmethod.InputMethodManager.this
                int r1 = r12.arg1
                r0.finishedInputEvent(r1, r3, r2)
                return
            L_0x0028:
                android.view.inputmethod.InputMethodManager r0 = android.view.inputmethod.InputMethodManager.this
                java.lang.Object r1 = r12.obj
                android.view.inputmethod.InputMethodManager$PendingEvent r1 = (android.view.inputmethod.InputMethodManager.PendingEvent) r1
                r0.sendInputEventAndReportResultOnMainLooper(r1)
                return
            L_0x0032:
                int r0 = r12.arg1
                if (r0 == 0) goto L_0x0038
                r0 = r2
                goto L_0x0039
            L_0x0038:
                r0 = r3
            L_0x0039:
                int r1 = r12.arg2
                if (r1 == 0) goto L_0x003f
                r3 = r2
            L_0x003f:
                r1 = r3
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r3 = r3.mH
                monitor-enter(r3)
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                r4.mActive = r0     // Catch:{ all -> 0x008b }
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                r4.mFullscreenMode = r1     // Catch:{ all -> 0x008b }
                if (r0 != 0) goto L_0x005c
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                r4.mRestartOnNextWindowFocus = r2     // Catch:{ all -> 0x008b }
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ RemoteException -> 0x005b }
                com.android.internal.view.IInputContext r2 = r2.mIInputContext     // Catch:{ RemoteException -> 0x005b }
                r2.finishComposingText()     // Catch:{ RemoteException -> 0x005b }
                goto L_0x005c
            L_0x005b:
                r2 = move-exception
            L_0x005c:
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                android.view.View r2 = r2.mServedView     // Catch:{ all -> 0x008b }
                if (r2 == 0) goto L_0x0089
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                android.view.View r2 = r2.mServedView     // Catch:{ all -> 0x008b }
                boolean r2 = android.view.inputmethod.InputMethodManager.canStartInput(r2)     // Catch:{ all -> 0x008b }
                if (r2 == 0) goto L_0x0089
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                boolean r4 = r4.mRestartOnNextWindowFocus     // Catch:{ all -> 0x008b }
                boolean r2 = r2.checkFocusNoStartInput(r4)     // Catch:{ all -> 0x008b }
                if (r2 == 0) goto L_0x0089
                if (r0 == 0) goto L_0x007d
                r2 = 7
            L_0x007b:
                r5 = r2
                goto L_0x0080
            L_0x007d:
                r2 = 8
                goto L_0x007b
            L_0x0080:
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x008b }
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
                r4.startInputInner(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x008b }
            L_0x0089:
                monitor-exit(r3)     // Catch:{ all -> 0x008b }
                return
            L_0x008b:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x008b }
                throw r2
            L_0x008e:
                int r0 = r12.arg1
                int r1 = r12.arg2
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r4 = r3.mH
                monitor-enter(r4)
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x00ca }
                int r3 = r3.mBindSequence     // Catch:{ all -> 0x00ca }
                if (r3 == r0) goto L_0x009f
                monitor-exit(r4)     // Catch:{ all -> 0x00ca }
                return
            L_0x009f:
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x00ca }
                r3.clearBindingLocked()     // Catch:{ all -> 0x00ca }
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x00ca }
                android.view.View r3 = r3.mServedView     // Catch:{ all -> 0x00ca }
                if (r3 == 0) goto L_0x00b8
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x00ca }
                android.view.View r3 = r3.mServedView     // Catch:{ all -> 0x00ca }
                boolean r3 = r3.isFocused()     // Catch:{ all -> 0x00ca }
                if (r3 == 0) goto L_0x00b8
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x00ca }
                r3.mServedConnecting = r2     // Catch:{ all -> 0x00ca }
            L_0x00b8:
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x00ca }
                boolean r2 = r2.mActive     // Catch:{ all -> 0x00ca }
                monitor-exit(r4)     // Catch:{ all -> 0x00ca }
                if (r2 == 0) goto L_0x00c9
                android.view.inputmethod.InputMethodManager r5 = android.view.inputmethod.InputMethodManager.this
                r6 = 6
                r7 = 0
                r8 = 0
                r9 = 0
                r10 = 0
                r5.startInputInner(r6, r7, r8, r9, r10)
            L_0x00c9:
                return
            L_0x00ca:
                r2 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x00ca }
                throw r2
            L_0x00cd:
                java.lang.Object r0 = r12.obj
                com.android.internal.view.InputBindResult r0 = (com.android.internal.view.InputBindResult) r0
                android.view.inputmethod.InputMethodManager r1 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r1 = r1.mH
                monitor-enter(r1)
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                int r2 = r2.mBindSequence     // Catch:{ all -> 0x014f }
                if (r2 < 0) goto L_0x0118
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                int r2 = r2.mBindSequence     // Catch:{ all -> 0x014f }
                int r4 = r0.sequence     // Catch:{ all -> 0x014f }
                if (r2 == r4) goto L_0x00e5
                goto L_0x0118
            L_0x00e5:
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                int unused = r2.mRequestUpdateCursorAnchorInfoMonitorMode = r3     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                android.view.InputChannel r3 = r0.channel     // Catch:{ all -> 0x014f }
                r2.setInputChannelLocked(r3)     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                com.android.internal.view.IInputMethodSession r3 = r0.method     // Catch:{ all -> 0x014f }
                r2.mCurMethod = r3     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                java.lang.String r3 = r0.id     // Catch:{ all -> 0x014f }
                r2.mCurId = r3     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                int r3 = r0.sequence     // Catch:{ all -> 0x014f }
                r2.mBindSequence = r3     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                android.graphics.Matrix r3 = r0.getActivityViewToScreenMatrix()     // Catch:{ all -> 0x014f }
                android.graphics.Matrix unused = r2.mActivityViewToScreenMatrix = r3     // Catch:{ all -> 0x014f }
                monitor-exit(r1)     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this
                r5 = 5
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
                r4.startInputInner(r5, r6, r7, r8, r9)
                return
            L_0x0118:
                java.lang.String r2 = "InputMethodManager"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x014f }
                r3.<init>()     // Catch:{ all -> 0x014f }
                java.lang.String r4 = "Ignoring onBind: cur seq="
                r3.append(r4)     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                int r4 = r4.mBindSequence     // Catch:{ all -> 0x014f }
                r3.append(r4)     // Catch:{ all -> 0x014f }
                java.lang.String r4 = ", given seq="
                r3.append(r4)     // Catch:{ all -> 0x014f }
                int r4 = r0.sequence     // Catch:{ all -> 0x014f }
                r3.append(r4)     // Catch:{ all -> 0x014f }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x014f }
                android.util.Log.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x014f }
                android.view.InputChannel r2 = r0.channel     // Catch:{ all -> 0x014f }
                if (r2 == 0) goto L_0x014d
                android.view.InputChannel r2 = r0.channel     // Catch:{ all -> 0x014f }
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x014f }
                android.view.InputChannel r3 = r3.mCurChannel     // Catch:{ all -> 0x014f }
                if (r2 == r3) goto L_0x014d
                android.view.InputChannel r2 = r0.channel     // Catch:{ all -> 0x014f }
                r2.dispose()     // Catch:{ all -> 0x014f }
            L_0x014d:
                monitor-exit(r1)     // Catch:{ all -> 0x014f }
                return
            L_0x014f:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x014f }
                throw r2
            L_0x0152:
                java.lang.Object r0 = r12.obj
                com.android.internal.os.SomeArgs r0 = (com.android.internal.os.SomeArgs) r0
                android.view.inputmethod.InputMethodManager r1 = android.view.inputmethod.InputMethodManager.this     // Catch:{ RuntimeException -> 0x0168 }
                java.lang.Object r2 = r0.arg1     // Catch:{ RuntimeException -> 0x0168 }
                java.io.FileDescriptor r2 = (java.io.FileDescriptor) r2     // Catch:{ RuntimeException -> 0x0168 }
                java.lang.Object r3 = r0.arg2     // Catch:{ RuntimeException -> 0x0168 }
                java.io.PrintWriter r3 = (java.io.PrintWriter) r3     // Catch:{ RuntimeException -> 0x0168 }
                java.lang.Object r4 = r0.arg3     // Catch:{ RuntimeException -> 0x0168 }
                java.lang.String[] r4 = (java.lang.String[]) r4     // Catch:{ RuntimeException -> 0x0168 }
                r1.doDump(r2, r3, r4)     // Catch:{ RuntimeException -> 0x0168 }
                goto L_0x0181
            L_0x0168:
                r1 = move-exception
                java.lang.Object r2 = r0.arg2
                java.io.PrintWriter r2 = (java.io.PrintWriter) r2
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Exception: "
                r3.append(r4)
                r3.append(r1)
                java.lang.String r3 = r3.toString()
                r2.println(r3)
            L_0x0181:
                java.lang.Object r1 = r0.arg4
                monitor-enter(r1)
                java.lang.Object r2 = r0.arg4     // Catch:{ all -> 0x0190 }
                java.util.concurrent.CountDownLatch r2 = (java.util.concurrent.CountDownLatch) r2     // Catch:{ all -> 0x0190 }
                r2.countDown()     // Catch:{ all -> 0x0190 }
                monitor-exit(r1)     // Catch:{ all -> 0x0190 }
                r0.recycle()
                return
            L_0x0190:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0190 }
                throw r2
            L_0x0193:
                java.lang.Object r0 = r12.obj
                float[] r0 = (float[]) r0
                int r1 = r12.arg1
                android.view.inputmethod.InputMethodManager r4 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r4 = r4.mH
                monitor-enter(r4)
                android.view.inputmethod.InputMethodManager r5 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                int r5 = r5.mBindSequence     // Catch:{ all -> 0x022a }
                if (r5 == r1) goto L_0x01a6
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                return
            L_0x01a6:
                if (r0 != 0) goto L_0x01b0
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                r3 = 0
                android.graphics.Matrix unused = r2.mActivityViewToScreenMatrix = r3     // Catch:{ all -> 0x022a }
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                return
            L_0x01b0:
                r5 = 9
                float[] r5 = new float[r5]     // Catch:{ all -> 0x022a }
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                android.graphics.Matrix r6 = r6.mActivityViewToScreenMatrix     // Catch:{ all -> 0x022a }
                r6.getValues(r5)     // Catch:{ all -> 0x022a }
                boolean r6 = java.util.Arrays.equals(r5, r0)     // Catch:{ all -> 0x022a }
                if (r6 == 0) goto L_0x01c5
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                return
            L_0x01c5:
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                android.graphics.Matrix r6 = r6.mActivityViewToScreenMatrix     // Catch:{ all -> 0x022a }
                r6.setValues(r0)     // Catch:{ all -> 0x022a }
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                android.view.inputmethod.CursorAnchorInfo r6 = r6.mCursorAnchorInfo     // Catch:{ all -> 0x022a }
                if (r6 == 0) goto L_0x0228
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                com.android.internal.view.IInputMethodSession r6 = r6.mCurMethod     // Catch:{ all -> 0x022a }
                if (r6 == 0) goto L_0x0228
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r6 = r6.mServedInputConnectionWrapper     // Catch:{ all -> 0x022a }
                if (r6 != 0) goto L_0x01e3
                goto L_0x0228
            L_0x01e3:
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                int r6 = r6.mRequestUpdateCursorAnchorInfoMonitorMode     // Catch:{ all -> 0x022a }
                r6 = r6 & 2
                if (r6 == 0) goto L_0x01ee
                goto L_0x01ef
            L_0x01ee:
                r2 = r3
            L_0x01ef:
                if (r2 != 0) goto L_0x01f3
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                return
            L_0x01f3:
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ RemoteException -> 0x020b }
                com.android.internal.view.IInputMethodSession r3 = r3.mCurMethod     // Catch:{ RemoteException -> 0x020b }
                android.view.inputmethod.InputMethodManager r6 = android.view.inputmethod.InputMethodManager.this     // Catch:{ RemoteException -> 0x020b }
                android.view.inputmethod.CursorAnchorInfo r6 = r6.mCursorAnchorInfo     // Catch:{ RemoteException -> 0x020b }
                android.view.inputmethod.InputMethodManager r7 = android.view.inputmethod.InputMethodManager.this     // Catch:{ RemoteException -> 0x020b }
                android.graphics.Matrix r7 = r7.mActivityViewToScreenMatrix     // Catch:{ RemoteException -> 0x020b }
                android.view.inputmethod.CursorAnchorInfo r6 = android.view.inputmethod.CursorAnchorInfo.createForAdditionalParentMatrix(r6, r7)     // Catch:{ RemoteException -> 0x020b }
                r3.updateCursorAnchorInfo(r6)     // Catch:{ RemoteException -> 0x020b }
                goto L_0x0226
            L_0x020b:
                r3 = move-exception
                java.lang.String r6 = "InputMethodManager"
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x022a }
                r7.<init>()     // Catch:{ all -> 0x022a }
                java.lang.String r8 = "IME died: "
                r7.append(r8)     // Catch:{ all -> 0x022a }
                android.view.inputmethod.InputMethodManager r8 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x022a }
                java.lang.String r8 = r8.mCurId     // Catch:{ all -> 0x022a }
                r7.append(r8)     // Catch:{ all -> 0x022a }
                java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x022a }
                android.util.Log.w(r6, r7, r3)     // Catch:{ all -> 0x022a }
            L_0x0226:
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                return
            L_0x0228:
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                return
            L_0x022a:
                r2 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x022a }
                throw r2
            L_0x022d:
                android.view.inputmethod.InputMethodManager r0 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r0 = r0.mH
                monitor-enter(r0)
                android.view.inputmethod.InputMethodManager r1 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x024b }
                android.view.ImeInsetsSourceConsumer r1 = r1.mImeInsetsConsumer     // Catch:{ all -> 0x024b }
                if (r1 == 0) goto L_0x0249
                android.view.inputmethod.InputMethodManager r1 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x024b }
                android.view.ImeInsetsSourceConsumer r1 = r1.mImeInsetsConsumer     // Catch:{ all -> 0x024b }
                int r4 = r12.arg1     // Catch:{ all -> 0x024b }
                if (r4 == 0) goto L_0x0245
                goto L_0x0246
            L_0x0245:
                r2 = r3
            L_0x0246:
                r1.applyImeVisibility(r2)     // Catch:{ all -> 0x024b }
            L_0x0249:
                monitor-exit(r0)     // Catch:{ all -> 0x024b }
                return
            L_0x024b:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x024b }
                throw r1
            L_0x024e:
                android.view.inputmethod.InputMethodManager r0 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r0 = r0.mH
                monitor-enter(r0)
                android.view.inputmethod.InputMethodManager r1 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x026a }
                android.view.ImeInsetsSourceConsumer r1 = r1.mImeInsetsConsumer     // Catch:{ all -> 0x026a }
                if (r1 == 0) goto L_0x0268
                android.view.inputmethod.InputMethodManager r1 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x026a }
                android.view.ImeInsetsSourceConsumer r1 = r1.mImeInsetsConsumer     // Catch:{ all -> 0x026a }
                java.lang.Object r2 = r12.obj     // Catch:{ all -> 0x026a }
                android.view.inputmethod.EditorInfo r2 = (android.view.inputmethod.EditorInfo) r2     // Catch:{ all -> 0x026a }
                r1.onPreRendered(r2)     // Catch:{ all -> 0x026a }
            L_0x0268:
                monitor-exit(r0)     // Catch:{ all -> 0x026a }
                return
            L_0x026a:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x026a }
                throw r1
            L_0x026d:
                int r0 = r12.arg1
                if (r0 == 0) goto L_0x0272
                goto L_0x0273
            L_0x0272:
                r2 = r3
            L_0x0273:
                r0 = r2
                r1 = 0
                android.view.inputmethod.InputMethodManager r2 = android.view.inputmethod.InputMethodManager.this
                android.view.inputmethod.InputMethodManager$H r2 = r2.mH
                monitor-enter(r2)
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x0294 }
                r3.mFullscreenMode = r0     // Catch:{ all -> 0x0294 }
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x0294 }
                android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r3 = r3.mServedInputConnectionWrapper     // Catch:{ all -> 0x0294 }
                if (r3 == 0) goto L_0x028d
                android.view.inputmethod.InputMethodManager r3 = android.view.inputmethod.InputMethodManager.this     // Catch:{ all -> 0x0294 }
                android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r3 = r3.mServedInputConnectionWrapper     // Catch:{ all -> 0x0294 }
                android.view.inputmethod.InputConnection r3 = r3.getInputConnection()     // Catch:{ all -> 0x0294 }
                r1 = r3
            L_0x028d:
                monitor-exit(r2)     // Catch:{ all -> 0x0294 }
                if (r1 == 0) goto L_0x0293
                r1.reportFullscreenMode(r0)
            L_0x0293:
                return
            L_0x0294:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0294 }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.H.handleMessage(android.os.Message):void");
        }
    }

    private static class ControlledInputConnectionWrapper extends IInputConnectionWrapper {
        private final InputMethodManager mParentInputMethodManager;

        public ControlledInputConnectionWrapper(Looper mainLooper, InputConnection conn, InputMethodManager inputMethodManager) {
            super(mainLooper, conn);
            this.mParentInputMethodManager = inputMethodManager;
        }

        public boolean isActive() {
            return this.mParentInputMethodManager.mActive && !isFinished();
        }

        /* access modifiers changed from: package-private */
        public void deactivate() {
            if (!isFinished()) {
                closeConnection();
            }
        }

        public String toString() {
            return "ControlledInputConnectionWrapper{connection=" + getInputConnection() + " finished=" + isFinished() + " mParentInputMethodManager.mActive=" + this.mParentInputMethodManager.mActive + "}";
        }
    }

    static void tearDownEditMode() {
        if (isInEditMode()) {
            synchronized (sLock) {
                sInstance = null;
            }
            return;
        }
        throw new UnsupportedOperationException("This method must be called only from layoutlib");
    }

    private static boolean isInEditMode() {
        return false;
    }

    private static InputMethodManager createInstance(int displayId, Looper looper) {
        if (isInEditMode()) {
            return createStubInstance(displayId, looper);
        }
        return createRealInstance(displayId, looper);
    }

    private static InputMethodManager createRealInstance(int displayId, Looper looper) {
        try {
            IInputMethodManager service = IInputMethodManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.INPUT_METHOD_SERVICE));
            InputMethodManager imm = new InputMethodManager(service, displayId, looper);
            long identity = Binder.clearCallingIdentity();
            try {
                service.addClient(imm.mClient, imm.mIInputContext, displayId);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(identity);
                throw th;
            }
            Binder.restoreCallingIdentity(identity);
            return imm;
        } catch (ServiceManager.ServiceNotFoundException e2) {
            throw new IllegalStateException(e2);
        }
    }

    private static InputMethodManager createStubInstance(int displayId, Looper looper) {
        Class<IInputMethodManager> c = IInputMethodManager.class;
        return new InputMethodManager((IInputMethodManager) Proxy.newProxyInstance(c.getClassLoader(), new Class[]{c}, $$Lambda$InputMethodManager$iDWn3IGSUFqIcs8Py42UhfrshxI.INSTANCE), displayId, looper);
    }

    static /* synthetic */ Object lambda$createStubInstance$0(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if (returnType == Boolean.TYPE) {
            return false;
        }
        if (returnType == Integer.TYPE) {
            return 0;
        }
        if (returnType == Long.TYPE) {
            return 0L;
        }
        if (returnType == Short.TYPE) {
            return 0;
        }
        if (returnType == Character.TYPE) {
            return 0;
        }
        if (returnType == Byte.TYPE) {
            return 0;
        }
        if (returnType == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (returnType == Double.TYPE) {
            return Double.valueOf(0.0d);
        }
        return null;
    }

    private InputMethodManager(IInputMethodManager service, int displayId, Looper looper) {
        this.mService = service;
        this.mMainLooper = looper;
        this.mH = new H(looper);
        this.mDisplayId = displayId;
        this.mIInputContext = new ControlledInputConnectionWrapper(looper, this.mDummyInputConnection, this);
    }

    public static InputMethodManager forContext(Context context) {
        int displayId = context.getDisplayId();
        return forContextInternal(displayId, displayId == 0 ? Looper.getMainLooper() : context.getMainLooper());
    }

    private static InputMethodManager forContextInternal(int displayId, Looper looper) {
        boolean isDefaultDisplay = displayId == 0;
        synchronized (sLock) {
            InputMethodManager instance = sInstanceMap.get(displayId);
            if (instance != null) {
                return instance;
            }
            InputMethodManager instance2 = createInstance(displayId, looper);
            if (sInstance == null && isDefaultDisplay) {
                sInstance = instance2;
            }
            sInstanceMap.put(displayId, instance2);
            return instance2;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static InputMethodManager getInstance() {
        Log.w(TAG, "InputMethodManager.getInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        ensureDefaultInstanceForDefaultDisplayIfNecessary();
        return peekInstance();
    }

    @Deprecated
    @UnsupportedAppUsage
    public static InputMethodManager peekInstance() {
        InputMethodManager inputMethodManager;
        Log.w(TAG, "InputMethodManager.peekInstance() is deprecated because it cannot be compatible with multi-display. Use context.getSystemService(InputMethodManager.class) instead.", new Throwable());
        synchronized (sLock) {
            inputMethodManager = sInstance;
        }
        return inputMethodManager;
    }

    @UnsupportedAppUsage
    public IInputMethodClient getClient() {
        return this.mClient;
    }

    @UnsupportedAppUsage
    public IInputContext getInputContext() {
        return this.mIInputContext;
    }

    public List<InputMethodInfo> getInputMethodList() {
        try {
            return this.mService.getInputMethodList(UserHandle.myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<InputMethodInfo> getInputMethodListAsUser(int userId) {
        try {
            return this.mService.getInputMethodList(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<InputMethodInfo> getEnabledInputMethodList() {
        try {
            return this.mService.getEnabledInputMethodList(UserHandle.myUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<InputMethodInfo> getEnabledInputMethodListAsUser(int userId) {
        try {
            return this.mService.getEnabledInputMethodList(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(InputMethodInfo imi, boolean allowsImplicitlySelectedSubtypes) {
        try {
            return this.mService.getEnabledInputMethodSubtypeList(imi == null ? null : imi.getId(), allowsImplicitlySelectedSubtypes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void showStatusIcon(IBinder imeToken, String packageName, int iconId) {
        InputMethodPrivilegedOperationsRegistry.get(imeToken).updateStatusIcon(packageName, iconId);
    }

    @Deprecated
    public void hideStatusIcon(IBinder imeToken) {
        InputMethodPrivilegedOperationsRegistry.get(imeToken).updateStatusIcon((String) null, 0);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void registerSuggestionSpansForNotification(SuggestionSpan[] spans) {
        Log.w(TAG, "registerSuggestionSpansForNotification() is deprecated.  Does nothing.");
    }

    @Deprecated
    @UnsupportedAppUsage
    public void notifySuggestionPicked(SuggestionSpan span, String originalString, int index) {
        Log.w(TAG, "notifySuggestionPicked() is deprecated.  Does nothing.");
    }

    public boolean isFullscreenMode() {
        boolean z;
        synchronized (this.mH) {
            z = this.mFullscreenMode;
        }
        return z;
    }

    public boolean isActive(View view) {
        boolean z;
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            return fallbackImm.isActive(view);
        }
        checkFocus();
        synchronized (this.mH) {
            z = (this.mServedView == view || (this.mServedView != null && this.mServedView.checkInputConnectionProxy(view))) && this.mCurrentTextBoxAttribute != null;
        }
        return z;
    }

    public boolean isActive() {
        boolean z;
        checkFocus();
        synchronized (this.mH) {
            z = (this.mServedView == null || this.mCurrentTextBoxAttribute == null) ? false : true;
        }
        return z;
    }

    public boolean isAcceptingText() {
        checkFocus();
        return (this.mServedInputConnectionWrapper == null || this.mServedInputConnectionWrapper.getInputConnection() == null) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public void clearBindingLocked() {
        clearConnectionLocked();
        setInputChannelLocked((InputChannel) null);
        this.mBindSequence = -1;
        this.mCurId = null;
        this.mCurMethod = null;
    }

    /* access modifiers changed from: package-private */
    public void setInputChannelLocked(InputChannel channel) {
        if (this.mCurChannel != channel) {
            if (this.mCurSender != null) {
                flushPendingEventsLocked();
                this.mCurSender.dispose();
                this.mCurSender = null;
            }
            if (this.mCurChannel != null) {
                this.mCurChannel.dispose();
            }
            this.mCurChannel = channel;
        }
    }

    /* access modifiers changed from: package-private */
    public void clearConnectionLocked() {
        this.mCurrentTextBoxAttribute = null;
        if (this.mServedInputConnectionWrapper != null) {
            this.mServedInputConnectionWrapper.deactivate();
            this.mServedInputConnectionWrapper = null;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void finishInputLocked() {
        this.mNextServedView = null;
        this.mActivityViewToScreenMatrix = null;
        if (this.mServedView != null) {
            this.mServedView = null;
            this.mCompletions = null;
            this.mServedConnecting = false;
            clearConnectionLocked();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void displayCompletions(android.view.View r5, android.view.inputmethod.CompletionInfo[] r6) {
        /*
            r4 = this;
            android.view.inputmethod.InputMethodManager r0 = r4.getFallbackInputMethodManagerIfNecessary(r5)
            if (r0 == 0) goto L_0x000a
            r0.displayCompletions(r5, r6)
            return
        L_0x000a:
            r4.checkFocus()
            android.view.inputmethod.InputMethodManager$H r1 = r4.mH
            monitor-enter(r1)
            android.view.View r2 = r4.mServedView     // Catch:{ all -> 0x0033 }
            if (r2 == r5) goto L_0x0022
            android.view.View r2 = r4.mServedView     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0020
            android.view.View r2 = r4.mServedView     // Catch:{ all -> 0x0033 }
            boolean r2 = r2.checkInputConnectionProxy(r5)     // Catch:{ all -> 0x0033 }
            if (r2 != 0) goto L_0x0022
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x0033 }
            return
        L_0x0022:
            r4.mCompletions = r6     // Catch:{ all -> 0x0033 }
            com.android.internal.view.IInputMethodSession r2 = r4.mCurMethod     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0031
            com.android.internal.view.IInputMethodSession r2 = r4.mCurMethod     // Catch:{ RemoteException -> 0x0030 }
            android.view.inputmethod.CompletionInfo[] r3 = r4.mCompletions     // Catch:{ RemoteException -> 0x0030 }
            r2.displayCompletions(r3)     // Catch:{ RemoteException -> 0x0030 }
            goto L_0x0031
        L_0x0030:
            r2 = move-exception
        L_0x0031:
            monitor-exit(r1)     // Catch:{ all -> 0x0033 }
            return
        L_0x0033:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0033 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.displayCompletions(android.view.View, android.view.inputmethod.CompletionInfo[]):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateExtractedText(android.view.View r4, int r5, android.view.inputmethod.ExtractedText r6) {
        /*
            r3 = this;
            android.view.inputmethod.InputMethodManager r0 = r3.getFallbackInputMethodManagerIfNecessary(r4)
            if (r0 == 0) goto L_0x000a
            r0.updateExtractedText(r4, r5, r6)
            return
        L_0x000a:
            r3.checkFocus()
            android.view.inputmethod.InputMethodManager$H r1 = r3.mH
            monitor-enter(r1)
            android.view.View r2 = r3.mServedView     // Catch:{ all -> 0x002f }
            if (r2 == r4) goto L_0x0022
            android.view.View r2 = r3.mServedView     // Catch:{ all -> 0x002f }
            if (r2 == 0) goto L_0x0020
            android.view.View r2 = r3.mServedView     // Catch:{ all -> 0x002f }
            boolean r2 = r2.checkInputConnectionProxy(r4)     // Catch:{ all -> 0x002f }
            if (r2 != 0) goto L_0x0022
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            return
        L_0x0022:
            com.android.internal.view.IInputMethodSession r2 = r3.mCurMethod     // Catch:{ all -> 0x002f }
            if (r2 == 0) goto L_0x002d
            com.android.internal.view.IInputMethodSession r2 = r3.mCurMethod     // Catch:{ RemoteException -> 0x002c }
            r2.updateExtractedText(r5, r6)     // Catch:{ RemoteException -> 0x002c }
            goto L_0x002d
        L_0x002c:
            r2 = move-exception
        L_0x002d:
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            return
        L_0x002f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.updateExtractedText(android.view.View, int, android.view.inputmethod.ExtractedText):void");
    }

    public boolean showSoftInput(View view, int flags) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            return fallbackImm.showSoftInput(view, flags);
        }
        return showSoftInput(view, flags, (ResultReceiver) null);
    }

    public boolean showSoftInput(View view, int flags, ResultReceiver resultReceiver) {
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(view);
        if (fallbackImm != null) {
            return fallbackImm.showSoftInput(view, flags, resultReceiver);
        }
        checkFocus();
        synchronized (this.mH) {
            if (this.mServedView != view && (this.mServedView == null || !this.mServedView.checkInputConnectionProxy(view))) {
                return false;
            }
            try {
                boolean showSoftInput = this.mService.showSoftInput(this.mClient, flags, resultReceiver);
                return showSoftInput;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768499)
    public void showSoftInputUnchecked(int flags, ResultReceiver resultReceiver) {
        try {
            Log.w(TAG, "showSoftInputUnchecked() is a hidden method, which will be removed soon. If you are using android.support.v7.widget.SearchView, please update to version 26.0 or newer version.");
            this.mService.showSoftInput(this.mClient, flags, resultReceiver);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hideSoftInputFromWindow(IBinder windowToken, int flags) {
        return hideSoftInputFromWindow(windowToken, flags, (ResultReceiver) null);
    }

    public boolean hideSoftInputFromWindow(IBinder windowToken, int flags, ResultReceiver resultReceiver) {
        checkFocus();
        synchronized (this.mH) {
            if (this.mServedView == null || this.mServedView.getWindowToken() != windowToken) {
                return false;
            }
            try {
                boolean hideSoftInput = this.mService.hideSoftInput(this.mClient, flags, resultReceiver);
                return hideSoftInput;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void toggleSoftInputFromWindow(android.os.IBinder r3, int r4, int r5) {
        /*
            r2 = this;
            android.view.inputmethod.InputMethodManager$H r0 = r2.mH
            monitor-enter(r0)
            android.view.View r1 = r2.mServedView     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x001d
            android.view.View r1 = r2.mServedView     // Catch:{ all -> 0x001f }
            android.os.IBinder r1 = r1.getWindowToken()     // Catch:{ all -> 0x001f }
            if (r1 == r3) goto L_0x0010
            goto L_0x001d
        L_0x0010:
            com.android.internal.view.IInputMethodSession r1 = r2.mCurMethod     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x001b
            com.android.internal.view.IInputMethodSession r1 = r2.mCurMethod     // Catch:{ RemoteException -> 0x001a }
            r1.toggleSoftInput(r4, r5)     // Catch:{ RemoteException -> 0x001a }
            goto L_0x001b
        L_0x001a:
            r1 = move-exception
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return
        L_0x001d:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return
        L_0x001f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.toggleSoftInputFromWindow(android.os.IBinder, int, int):void");
    }

    public void toggleSoftInput(int showFlags, int hideFlags) {
        if (this.mCurMethod != null) {
            try {
                this.mCurMethod.toggleSoftInput(showFlags, hideFlags);
            } catch (RemoteException e) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void restartInput(android.view.View r10) {
        /*
            r9 = this;
            android.view.inputmethod.InputMethodManager r0 = r9.getFallbackInputMethodManagerIfNecessary(r10)
            if (r0 == 0) goto L_0x000a
            r0.restartInput(r10)
            return
        L_0x000a:
            r9.checkFocus()
            android.view.inputmethod.InputMethodManager$H r1 = r9.mH
            monitor-enter(r1)
            android.view.View r2 = r9.mServedView     // Catch:{ all -> 0x0030 }
            if (r2 == r10) goto L_0x0022
            android.view.View r2 = r9.mServedView     // Catch:{ all -> 0x0030 }
            if (r2 == 0) goto L_0x0020
            android.view.View r2 = r9.mServedView     // Catch:{ all -> 0x0030 }
            boolean r2 = r2.checkInputConnectionProxy(r10)     // Catch:{ all -> 0x0030 }
            if (r2 != 0) goto L_0x0022
        L_0x0020:
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            return
        L_0x0022:
            r2 = 1
            r9.mServedConnecting = r2     // Catch:{ all -> 0x0030 }
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            r4 = 3
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r3 = r9
            r3.startInputInner(r4, r5, r6, r7, r8)
            return
        L_0x0030:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0030 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.restartInput(android.view.View):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01c6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01c7, code lost:
        r20 = r2;
        r22 = r13;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01cd, code lost:
        monitor-exit(r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01ce, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01cf, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        if (r2 != null) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        android.util.Log.e(TAG, "ABORT input: ServedView must be attached to a Window");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        r4 = r27 | 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0024, code lost:
        if (r3.onCheckIsTextEditor() == false) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
        r4 = r4 | 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        r18 = r3.getViewRootImpl().mWindowAttributes.softInputMode;
        r19 = r3.getViewRootImpl().mWindowAttributes.flags;
        r6 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        r6 = r26;
        r4 = r27;
        r18 = r28;
        r19 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        r2 = r3.getHandler();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004a, code lost:
        if (r2 != null) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004c, code lost:
        closeCurrentInput();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004f, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0058, code lost:
        if (r2.getLooper() == android.os.Looper.myLooper()) goto L_0x0065;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005a, code lost:
        r2.post(new android.view.inputmethod.$$Lambda$InputMethodManager$dfnCauFoZCfHfXs1QavrkwWDf0(r1, r25));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0064, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0065, code lost:
        r15 = r25;
        r5 = new android.view.inputmethod.EditorInfo();
        r5.packageName = r3.getContext().getOpPackageName();
        r5.fieldId = r3.getId();
        r14 = r3.onCreateInputConnection(r5);
        r13 = r1.mH;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0082, code lost:
        monitor-enter(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0085, code lost:
        if (r1.mServedView != r3) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0089, code lost:
        if (r1.mServedConnecting != false) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008b, code lost:
        r20 = r2;
        r22 = r13;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0095, code lost:
        if (r1.mCurrentTextBoxAttribute != null) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0097, code lost:
        r4 = r4 | 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0099, code lost:
        r1.mCurrentTextBoxAttribute = r5;
        maybeCallServedViewChangedLocked(r5);
        r1.mServedConnecting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a3, code lost:
        if (r1.mServedInputConnectionWrapper == null) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r1.mServedInputConnectionWrapper.deactivate();
        r1.mServedInputConnectionWrapper = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ad, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ae, code lost:
        r20 = r2;
        r22 = r13;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b6, code lost:
        if (r14 == null) goto L_0x00f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b8, code lost:
        r1.mCursorSelStart = r5.initialSelStart;
        r1.mCursorSelEnd = r5.initialSelEnd;
        r1.mCursorCandStart = -1;
        r1.mCursorCandEnd = -1;
        r1.mCursorRect.setEmpty();
        r1.mCursorAnchorInfo = null;
        r7 = android.view.inputmethod.InputConnectionInspector.getMissingMethodFlags(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d2, code lost:
        if ((r7 & 32) == 0) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d4, code lost:
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d6, code lost:
        r8 = r14.getHandler();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00dc, code lost:
        if (r8 == null) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00de, code lost:
        r10 = r8.getLooper();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e3, code lost:
        r10 = r2.getLooper();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ea, code lost:
        r16 = r7;
        r12 = new android.view.inputmethod.InputMethodManager.ControlledInputConnectionWrapper(r10, r14, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f0, code lost:
        r16 = 0;
        r12 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        r1.mServedInputConnectionWrapper = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0105, code lost:
        r20 = r2;
        r2 = true;
        r22 = r13;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r7 = r1.mService.startInputOrWindowGainedFocus(r25, r1.mClient, r6, r4, r18, r19, r5, r12, r16, r3.getContext().getApplicationInfo().targetSdkVersion);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x011f, code lost:
        if (r7 != null) goto L_0x0152;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0121, code lost:
        android.util.Log.wtf(TAG, "startInputOrWindowGainedFocus must not return null. startInputReason=" + com.android.internal.inputmethod.InputMethodDebug.startInputReasonToString(r25) + " editorInfo=" + r5 + " startInputFlags=" + com.android.internal.inputmethod.InputMethodDebug.startInputFlagsToString(r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        monitor-exit(r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0151, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        r1.mActivityViewToScreenMatrix = r7.getActivityViewToScreenMatrix();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x015a, code lost:
        if (r7.id == null) goto L_0x016e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x015c, code lost:
        setInputChannelLocked(r7.channel);
        r1.mBindSequence = r7.sequence;
        r1.mCurMethod = r7.method;
        r1.mCurId = r7.id;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0170, code lost:
        if (r7.channel == null) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0176, code lost:
        if (r7.channel == r1.mCurChannel) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0178, code lost:
        r7.channel.dispose();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0181, code lost:
        if (r7.result == 11) goto L_0x0184;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0184, code lost:
        r1.mRestartOnNextWindowFocus = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0188, code lost:
        if (r1.mCurMethod == null) goto L_0x01bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x018c, code lost:
        if (r1.mCompletions == null) goto L_0x01bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        r1.mCurMethod.displayCompletions(r1.mCompletions);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0198, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        if (r26 != null) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x019a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x019b, code lost:
        r20 = r2;
        r2 = true;
        r21 = r12;
        r22 = r13;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        android.util.Log.w(TAG, "IME died: " + r1.mCurId, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01bc, code lost:
        monitor-exit(r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01bd, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01be, code lost:
        r20 = r2;
        r22 = r13;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01c4, code lost:
        monitor-exit(r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01c5, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r2 = r3.getWindowToken();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean startInputInner(int r25, android.os.IBinder r26, int r27, int r28, int r29) {
        /*
            r24 = this;
            r1 = r24
            android.view.inputmethod.InputMethodManager$H r2 = r1.mH
            monitor-enter(r2)
            android.view.View r0 = r1.mServedView     // Catch:{ all -> 0x01d1 }
            r3 = r0
            r0 = 0
            if (r3 != 0) goto L_0x000d
            monitor-exit(r2)     // Catch:{ all -> 0x01d1 }
            return r0
        L_0x000d:
            monitor-exit(r2)     // Catch:{ all -> 0x01d1 }
            if (r26 != 0) goto L_0x003e
            android.os.IBinder r2 = r3.getWindowToken()
            if (r2 != 0) goto L_0x001e
            java.lang.String r4 = "InputMethodManager"
            java.lang.String r5 = "ABORT input: ServedView must be attached to a Window"
            android.util.Log.e(r4, r5)
            return r0
        L_0x001e:
            r4 = r27 | 1
            boolean r5 = r3.onCheckIsTextEditor()
            if (r5 == 0) goto L_0x0028
            r4 = r4 | 2
        L_0x0028:
            android.view.ViewRootImpl r5 = r3.getViewRootImpl()
            android.view.WindowManager$LayoutParams r5 = r5.mWindowAttributes
            int r5 = r5.softInputMode
            android.view.ViewRootImpl r6 = r3.getViewRootImpl()
            android.view.WindowManager$LayoutParams r6 = r6.mWindowAttributes
            int r6 = r6.flags
            r18 = r5
            r19 = r6
            r6 = r2
            goto L_0x0046
        L_0x003e:
            r6 = r26
            r4 = r27
            r18 = r28
            r19 = r29
        L_0x0046:
            android.os.Handler r2 = r3.getHandler()
            if (r2 != 0) goto L_0x0050
            r24.closeCurrentInput()
            return r0
        L_0x0050:
            android.os.Looper r5 = r2.getLooper()
            android.os.Looper r7 = android.os.Looper.myLooper()
            if (r5 == r7) goto L_0x0065
            android.view.inputmethod.-$$Lambda$InputMethodManager$dfnCauFoZCf-HfXs1QavrkwWDf0 r5 = new android.view.inputmethod.-$$Lambda$InputMethodManager$dfnCauFoZCf-HfXs1QavrkwWDf0
            r15 = r25
            r5.<init>(r15)
            r2.post(r5)
            return r0
        L_0x0065:
            r15 = r25
            android.view.inputmethod.EditorInfo r5 = new android.view.inputmethod.EditorInfo
            r5.<init>()
            android.content.Context r7 = r3.getContext()
            java.lang.String r7 = r7.getOpPackageName()
            r5.packageName = r7
            int r7 = r3.getId()
            r5.fieldId = r7
            android.view.inputmethod.InputConnection r14 = r3.onCreateInputConnection(r5)
            android.view.inputmethod.InputMethodManager$H r13 = r1.mH
            monitor-enter(r13)
            android.view.View r7 = r1.mServedView     // Catch:{ all -> 0x01c6 }
            if (r7 != r3) goto L_0x01be
            boolean r7 = r1.mServedConnecting     // Catch:{ all -> 0x01c6 }
            if (r7 != 0) goto L_0x0093
            r20 = r2
            r22 = r13
            r23 = r14
            goto L_0x01c4
        L_0x0093:
            android.view.inputmethod.EditorInfo r7 = r1.mCurrentTextBoxAttribute     // Catch:{ all -> 0x01c6 }
            if (r7 != 0) goto L_0x0099
            r4 = r4 | 8
        L_0x0099:
            r1.mCurrentTextBoxAttribute = r5     // Catch:{ all -> 0x01c6 }
            r1.maybeCallServedViewChangedLocked(r5)     // Catch:{ all -> 0x01c6 }
            r1.mServedConnecting = r0     // Catch:{ all -> 0x01c6 }
            android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r7 = r1.mServedInputConnectionWrapper     // Catch:{ all -> 0x01c6 }
            r8 = 0
            if (r7 == 0) goto L_0x00b6
            android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r7 = r1.mServedInputConnectionWrapper     // Catch:{ all -> 0x00ad }
            r7.deactivate()     // Catch:{ all -> 0x00ad }
            r1.mServedInputConnectionWrapper = r8     // Catch:{ all -> 0x00ad }
            goto L_0x00b6
        L_0x00ad:
            r0 = move-exception
            r20 = r2
            r22 = r13
            r23 = r14
            goto L_0x01cd
        L_0x00b6:
            if (r14 == 0) goto L_0x00f0
            int r7 = r5.initialSelStart     // Catch:{ all -> 0x00ad }
            r1.mCursorSelStart = r7     // Catch:{ all -> 0x00ad }
            int r7 = r5.initialSelEnd     // Catch:{ all -> 0x00ad }
            r1.mCursorSelEnd = r7     // Catch:{ all -> 0x00ad }
            r7 = -1
            r1.mCursorCandStart = r7     // Catch:{ all -> 0x00ad }
            r1.mCursorCandEnd = r7     // Catch:{ all -> 0x00ad }
            android.graphics.Rect r7 = r1.mCursorRect     // Catch:{ all -> 0x00ad }
            r7.setEmpty()     // Catch:{ all -> 0x00ad }
            r1.mCursorAnchorInfo = r8     // Catch:{ all -> 0x00ad }
            int r7 = android.view.inputmethod.InputConnectionInspector.getMissingMethodFlags(r14)     // Catch:{ all -> 0x00ad }
            r8 = r7 & 32
            if (r8 == 0) goto L_0x00d6
            r8 = 0
            goto L_0x00da
        L_0x00d6:
            android.os.Handler r8 = r14.getHandler()     // Catch:{ all -> 0x00ad }
        L_0x00da:
            android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r9 = new android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper     // Catch:{ all -> 0x00ad }
            if (r8 == 0) goto L_0x00e3
            android.os.Looper r10 = r8.getLooper()     // Catch:{ all -> 0x00ad }
            goto L_0x00e7
        L_0x00e3:
            android.os.Looper r10 = r2.getLooper()     // Catch:{ all -> 0x00ad }
        L_0x00e7:
            r9.<init>(r10, r14, r1)     // Catch:{ all -> 0x00ad }
            r8 = r9
            r16 = r7
            r12 = r8
            goto L_0x00f4
        L_0x00f0:
            r7 = 0
            r16 = r0
            r12 = r7
        L_0x00f4:
            r1.mServedInputConnectionWrapper = r12     // Catch:{ all -> 0x01c6 }
            r11 = 1
            com.android.internal.view.IInputMethodManager r7 = r1.mService     // Catch:{ RemoteException -> 0x019a }
            com.android.internal.view.IInputMethodClient$Stub r9 = r1.mClient     // Catch:{ RemoteException -> 0x019a }
            android.content.Context r8 = r3.getContext()     // Catch:{ RemoteException -> 0x019a }
            android.content.pm.ApplicationInfo r8 = r8.getApplicationInfo()     // Catch:{ RemoteException -> 0x019a }
            int r10 = r8.targetSdkVersion     // Catch:{ RemoteException -> 0x019a }
            r8 = r25
            r17 = r10
            r10 = r6
            r20 = r2
            r2 = r11
            r11 = r4
            r21 = r12
            r12 = r18
            r22 = r13
            r13 = r19
            r23 = r14
            r14 = r5
            r15 = r21
            com.android.internal.view.InputBindResult r7 = r7.startInputOrWindowGainedFocus(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)     // Catch:{ RemoteException -> 0x0198 }
            if (r7 != 0) goto L_0x0152
            java.lang.String r8 = "InputMethodManager"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0198 }
            r9.<init>()     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r10 = "startInputOrWindowGainedFocus must not return null. startInputReason="
            r9.append(r10)     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r10 = com.android.internal.inputmethod.InputMethodDebug.startInputReasonToString(r25)     // Catch:{ RemoteException -> 0x0198 }
            r9.append(r10)     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r10 = " editorInfo="
            r9.append(r10)     // Catch:{ RemoteException -> 0x0198 }
            r9.append(r5)     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r10 = " startInputFlags="
            r9.append(r10)     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r10 = com.android.internal.inputmethod.InputMethodDebug.startInputFlagsToString(r4)     // Catch:{ RemoteException -> 0x0198 }
            r9.append(r10)     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r9 = r9.toString()     // Catch:{ RemoteException -> 0x0198 }
            android.util.Log.wtf((java.lang.String) r8, (java.lang.String) r9)     // Catch:{ RemoteException -> 0x0198 }
            monitor-exit(r22)     // Catch:{ all -> 0x01cf }
            return r0
        L_0x0152:
            android.graphics.Matrix r0 = r7.getActivityViewToScreenMatrix()     // Catch:{ RemoteException -> 0x0198 }
            r1.mActivityViewToScreenMatrix = r0     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r0 = r7.id     // Catch:{ RemoteException -> 0x0198 }
            if (r0 == 0) goto L_0x016e
            android.view.InputChannel r0 = r7.channel     // Catch:{ RemoteException -> 0x0198 }
            r1.setInputChannelLocked(r0)     // Catch:{ RemoteException -> 0x0198 }
            int r0 = r7.sequence     // Catch:{ RemoteException -> 0x0198 }
            r1.mBindSequence = r0     // Catch:{ RemoteException -> 0x0198 }
            com.android.internal.view.IInputMethodSession r0 = r7.method     // Catch:{ RemoteException -> 0x0198 }
            r1.mCurMethod = r0     // Catch:{ RemoteException -> 0x0198 }
            java.lang.String r0 = r7.id     // Catch:{ RemoteException -> 0x0198 }
            r1.mCurId = r0     // Catch:{ RemoteException -> 0x0198 }
            goto L_0x017d
        L_0x016e:
            android.view.InputChannel r0 = r7.channel     // Catch:{ RemoteException -> 0x0198 }
            if (r0 == 0) goto L_0x017d
            android.view.InputChannel r0 = r7.channel     // Catch:{ RemoteException -> 0x0198 }
            android.view.InputChannel r8 = r1.mCurChannel     // Catch:{ RemoteException -> 0x0198 }
            if (r0 == r8) goto L_0x017d
            android.view.InputChannel r0 = r7.channel     // Catch:{ RemoteException -> 0x0198 }
            r0.dispose()     // Catch:{ RemoteException -> 0x0198 }
        L_0x017d:
            int r0 = r7.result     // Catch:{ RemoteException -> 0x0198 }
            r8 = 11
            if (r0 == r8) goto L_0x0184
            goto L_0x0186
        L_0x0184:
            r1.mRestartOnNextWindowFocus = r2     // Catch:{ RemoteException -> 0x0198 }
        L_0x0186:
            com.android.internal.view.IInputMethodSession r0 = r1.mCurMethod     // Catch:{ RemoteException -> 0x0198 }
            if (r0 == 0) goto L_0x0197
            android.view.inputmethod.CompletionInfo[] r0 = r1.mCompletions     // Catch:{ RemoteException -> 0x0198 }
            if (r0 == 0) goto L_0x0197
            com.android.internal.view.IInputMethodSession r0 = r1.mCurMethod     // Catch:{ RemoteException -> 0x0196 }
            android.view.inputmethod.CompletionInfo[] r8 = r1.mCompletions     // Catch:{ RemoteException -> 0x0196 }
            r0.displayCompletions(r8)     // Catch:{ RemoteException -> 0x0196 }
            goto L_0x0197
        L_0x0196:
            r0 = move-exception
        L_0x0197:
            goto L_0x01bc
        L_0x0198:
            r0 = move-exception
            goto L_0x01a4
        L_0x019a:
            r0 = move-exception
            r20 = r2
            r2 = r11
            r21 = r12
            r22 = r13
            r23 = r14
        L_0x01a4:
            java.lang.String r7 = "InputMethodManager"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cf }
            r8.<init>()     // Catch:{ all -> 0x01cf }
            java.lang.String r9 = "IME died: "
            r8.append(r9)     // Catch:{ all -> 0x01cf }
            java.lang.String r9 = r1.mCurId     // Catch:{ all -> 0x01cf }
            r8.append(r9)     // Catch:{ all -> 0x01cf }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x01cf }
            android.util.Log.w(r7, r8, r0)     // Catch:{ all -> 0x01cf }
        L_0x01bc:
            monitor-exit(r22)     // Catch:{ all -> 0x01cf }
            return r2
        L_0x01be:
            r20 = r2
            r22 = r13
            r23 = r14
        L_0x01c4:
            monitor-exit(r22)     // Catch:{ all -> 0x01cf }
            return r0
        L_0x01c6:
            r0 = move-exception
            r20 = r2
            r22 = r13
            r23 = r14
        L_0x01cd:
            monitor-exit(r22)     // Catch:{ all -> 0x01cf }
            throw r0
        L_0x01cf:
            r0 = move-exception
            goto L_0x01cd
        L_0x01d1:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x01d1 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.startInputInner(int, android.os.IBinder, int, int, int):boolean");
    }

    @UnsupportedAppUsage
    public void windowDismissed(IBinder appWindowToken) {
        checkFocus();
        synchronized (this.mH) {
            if (this.mServedView != null && this.mServedView.getWindowToken() == appWindowToken) {
                finishInputLocked();
            }
            if (this.mCurRootView != null && this.mCurRootView.getWindowToken() == appWindowToken) {
                this.mCurRootView = null;
            }
        }
    }

    @UnsupportedAppUsage
    public void focusIn(View view) {
        synchronized (this.mH) {
            focusInLocked(view);
        }
    }

    /* access modifiers changed from: package-private */
    public void focusInLocked(View view) {
        if ((view == null || !view.isTemporarilyDetached()) && this.mCurRootView == view.getRootView()) {
            this.mNextServedView = view;
            scheduleCheckFocusLocked(view);
        }
    }

    @UnsupportedAppUsage
    public void focusOut(View view) {
        synchronized (this.mH) {
            View view2 = this.mServedView;
        }
    }

    public void onViewDetachedFromWindow(View view) {
        synchronized (this.mH) {
            if (this.mServedView == view) {
                this.mNextServedView = null;
                scheduleCheckFocusLocked(view);
            }
        }
    }

    static void scheduleCheckFocusLocked(View view) {
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.dispatchCheckFocus();
        }
    }

    @UnsupportedAppUsage
    public void checkFocus() {
        if (checkFocusNoStartInput(false)) {
            startInputInner(4, (IBinder) null, 0, 0, 0);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        if (r1 == null) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003f, code lost:
        r1.finishComposingText();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkFocusNoStartInput(boolean r6) {
        /*
            r5 = this;
            android.view.View r0 = r5.mServedView
            android.view.View r1 = r5.mNextServedView
            r2 = 0
            if (r0 != r1) goto L_0x000a
            if (r6 != 0) goto L_0x000a
            return r2
        L_0x000a:
            android.view.inputmethod.InputMethodManager$H r0 = r5.mH
            monitor-enter(r0)
            android.view.View r1 = r5.mServedView     // Catch:{ all -> 0x0043 }
            android.view.View r3 = r5.mNextServedView     // Catch:{ all -> 0x0043 }
            if (r1 != r3) goto L_0x0017
            if (r6 != 0) goto L_0x0017
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return r2
        L_0x0017:
            android.view.View r1 = r5.mNextServedView     // Catch:{ all -> 0x0043 }
            if (r1 != 0) goto L_0x0023
            r5.finishInputLocked()     // Catch:{ all -> 0x0043 }
            r5.closeCurrentInput()     // Catch:{ all -> 0x0043 }
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            return r2
        L_0x0023:
            android.view.inputmethod.InputMethodManager$ControlledInputConnectionWrapper r1 = r5.mServedInputConnectionWrapper     // Catch:{ all -> 0x0043 }
            android.view.View r2 = r5.mNextServedView     // Catch:{ all -> 0x0043 }
            r5.mServedView = r2     // Catch:{ all -> 0x0043 }
            r2 = 0
            r5.mCurrentTextBoxAttribute = r2     // Catch:{ all -> 0x0043 }
            r5.mCompletions = r2     // Catch:{ all -> 0x0043 }
            r3 = 1
            r5.mServedConnecting = r3     // Catch:{ all -> 0x0043 }
            android.view.View r4 = r5.mServedView     // Catch:{ all -> 0x0043 }
            boolean r4 = r4.onCheckIsTextEditor()     // Catch:{ all -> 0x0043 }
            if (r4 != 0) goto L_0x003c
            r5.maybeCallServedViewChangedLocked(r2)     // Catch:{ all -> 0x0043 }
        L_0x003c:
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x0042
            r1.finishComposingText()
        L_0x0042:
            return r3
        L_0x0043:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0043 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.checkFocusNoStartInput(boolean):boolean");
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void closeCurrentInput() {
        try {
            this.mService.hideSoftInput(this.mClient, 2, (ResultReceiver) null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001d, code lost:
        if (r23 == null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x001f, code lost:
        r0 = 0 | 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
        if (r23.onCheckIsTextEditor() == false) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        r0 = r0 | 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
        if (r25 == false) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002b, code lost:
        r0 = r0 | 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002d, code lost:
        r20 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0033, code lost:
        if (checkFocusNoStartInput(r8) == false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0046, code lost:
        if (startInputInner(1, r22.getWindowToken(), r20, r24, r26) == false) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0048, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0049, code lost:
        r1 = r7.mH;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004b, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r7.mService.startInputOrWindowGainedFocus(2, r7.mClient, r22.getWindowToken(), r20, r24, r26, (android.view.inputmethod.EditorInfo) null, (com.android.internal.view.IInputContext) null, 0, r22.getContext().getApplicationInfo().targetSdkVersion);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0072, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0073, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0075, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007a, code lost:
        throw r0.rethrowFromSystemServer();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007c, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPostWindowFocus(android.view.View r22, android.view.View r23, int r24, boolean r25, int r26) {
        /*
            r21 = this;
            r7 = r21
            r1 = 0
            android.view.inputmethod.InputMethodManager$H r2 = r7.mH
            monitor-enter(r2)
            boolean r0 = r7.mRestartOnNextWindowFocus     // Catch:{ all -> 0x0080 }
            if (r0 == 0) goto L_0x0010
            r0 = 0
            r7.mRestartOnNextWindowFocus = r0     // Catch:{ all -> 0x0080 }
            r0 = 1
            r8 = r0
            goto L_0x0011
        L_0x0010:
            r8 = r1
        L_0x0011:
            if (r23 == 0) goto L_0x0016
            r0 = r23
            goto L_0x0018
        L_0x0016:
            r0 = r22
        L_0x0018:
            r7.focusInLocked(r0)     // Catch:{ all -> 0x007d }
            monitor-exit(r2)     // Catch:{ all -> 0x007d }
            r0 = 0
            if (r23 == 0) goto L_0x0029
            r0 = r0 | 1
            boolean r1 = r23.onCheckIsTextEditor()
            if (r1 == 0) goto L_0x0029
            r0 = r0 | 2
        L_0x0029:
            if (r25 == 0) goto L_0x002d
            r0 = r0 | 4
        L_0x002d:
            r20 = r0
            boolean r0 = r7.checkFocusNoStartInput(r8)
            if (r0 == 0) goto L_0x0049
            r2 = 1
            android.os.IBinder r3 = r22.getWindowToken()
            r1 = r21
            r4 = r20
            r5 = r24
            r6 = r26
            boolean r0 = r1.startInputInner(r2, r3, r4, r5, r6)
            if (r0 == 0) goto L_0x0049
            return
        L_0x0049:
            android.view.inputmethod.InputMethodManager$H r1 = r7.mH
            monitor-enter(r1)
            com.android.internal.view.IInputMethodManager r9 = r7.mService     // Catch:{ RemoteException -> 0x0075 }
            r10 = 2
            com.android.internal.view.IInputMethodClient$Stub r11 = r7.mClient     // Catch:{ RemoteException -> 0x0075 }
            android.os.IBinder r12 = r22.getWindowToken()     // Catch:{ RemoteException -> 0x0075 }
            r16 = 0
            r17 = 0
            r18 = 0
            android.content.Context r0 = r22.getContext()     // Catch:{ RemoteException -> 0x0075 }
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo()     // Catch:{ RemoteException -> 0x0075 }
            int r0 = r0.targetSdkVersion     // Catch:{ RemoteException -> 0x0075 }
            r13 = r20
            r14 = r24
            r15 = r26
            r19 = r0
            r9.startInputOrWindowGainedFocus(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ RemoteException -> 0x0075 }
            monitor-exit(r1)     // Catch:{ all -> 0x0073 }
            return
        L_0x0073:
            r0 = move-exception
            goto L_0x007b
        L_0x0075:
            r0 = move-exception
            java.lang.RuntimeException r2 = r0.rethrowFromSystemServer()     // Catch:{ all -> 0x0073 }
            throw r2     // Catch:{ all -> 0x0073 }
        L_0x007b:
            monitor-exit(r1)     // Catch:{ all -> 0x0073 }
            throw r0
        L_0x007d:
            r0 = move-exception
            r1 = r8
            goto L_0x0081
        L_0x0080:
            r0 = move-exception
        L_0x0081:
            monitor-exit(r2)     // Catch:{ all -> 0x0080 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.onPostWindowFocus(android.view.View, android.view.View, int, boolean, int):void");
    }

    @UnsupportedAppUsage
    public void onPreWindowFocus(View rootView, boolean hasWindowFocus) {
        synchronized (this.mH) {
            if (rootView == null) {
                try {
                    this.mCurRootView = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (hasWindowFocus) {
                this.mCurRootView = rootView;
            } else if (rootView == this.mCurRootView) {
                this.mCurRootView = null;
            }
        }
    }

    public void registerImeConsumer(ImeInsetsSourceConsumer imeInsetsConsumer) {
        if (imeInsetsConsumer != null) {
            synchronized (this.mH) {
                this.mImeInsetsConsumer = imeInsetsConsumer;
            }
            return;
        }
        throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
    }

    public void unregisterImeConsumer(ImeInsetsSourceConsumer imeInsetsConsumer) {
        if (imeInsetsConsumer != null) {
            synchronized (this.mH) {
                if (this.mImeInsetsConsumer == imeInsetsConsumer) {
                    this.mImeInsetsConsumer = null;
                }
            }
            return;
        }
        throw new IllegalStateException("ImeInsetsSourceConsumer cannot be null.");
    }

    public boolean requestImeShow(ResultReceiver resultReceiver) {
        synchronized (this.mH) {
            if (this.mServedView == null) {
                return false;
            }
            showSoftInput(this.mServedView, 0, resultReceiver);
            return true;
        }
    }

    public void notifyImeHidden() {
        synchronized (this.mH) {
            try {
                if (this.mCurMethod != null) {
                    this.mCurMethod.notifyImeHidden();
                }
            } catch (RemoteException e) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0087, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateSelection(android.view.View r18, int r19, int r20, int r21, int r22) {
        /*
            r17 = this;
            r1 = r17
            r8 = r18
            r15 = r19
            r14 = r20
            r13 = r21
            r12 = r22
            android.view.inputmethod.InputMethodManager r16 = r17.getFallbackInputMethodManagerIfNecessary(r18)
            if (r16 == 0) goto L_0x0022
            r2 = r16
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            r7 = r22
            r2.updateSelection(r3, r4, r5, r6, r7)
            return
        L_0x0022:
            r17.checkFocus()
            android.view.inputmethod.InputMethodManager$H r2 = r1.mH
            monitor-enter(r2)
            android.view.View r0 = r1.mServedView     // Catch:{ all -> 0x0088 }
            if (r0 == r8) goto L_0x0038
            android.view.View r0 = r1.mServedView     // Catch:{ all -> 0x0088 }
            if (r0 == 0) goto L_0x0086
            android.view.View r0 = r1.mServedView     // Catch:{ all -> 0x0088 }
            boolean r0 = r0.checkInputConnectionProxy(r8)     // Catch:{ all -> 0x0088 }
            if (r0 == 0) goto L_0x0086
        L_0x0038:
            android.view.inputmethod.EditorInfo r0 = r1.mCurrentTextBoxAttribute     // Catch:{ all -> 0x0088 }
            if (r0 == 0) goto L_0x0086
            com.android.internal.view.IInputMethodSession r0 = r1.mCurMethod     // Catch:{ all -> 0x0088 }
            if (r0 != 0) goto L_0x0041
            goto L_0x0086
        L_0x0041:
            int r0 = r1.mCursorSelStart     // Catch:{ all -> 0x0088 }
            if (r0 != r15) goto L_0x0051
            int r0 = r1.mCursorSelEnd     // Catch:{ all -> 0x0088 }
            if (r0 != r14) goto L_0x0051
            int r0 = r1.mCursorCandStart     // Catch:{ all -> 0x0088 }
            if (r0 != r13) goto L_0x0051
            int r0 = r1.mCursorCandEnd     // Catch:{ all -> 0x0088 }
            if (r0 == r12) goto L_0x0084
        L_0x0051:
            int r10 = r1.mCursorSelStart     // Catch:{ RemoteException -> 0x006b }
            int r11 = r1.mCursorSelEnd     // Catch:{ RemoteException -> 0x006b }
            r1.mCursorSelStart = r15     // Catch:{ RemoteException -> 0x006b }
            r1.mCursorSelEnd = r14     // Catch:{ RemoteException -> 0x006b }
            r1.mCursorCandStart = r13     // Catch:{ RemoteException -> 0x006b }
            r1.mCursorCandEnd = r12     // Catch:{ RemoteException -> 0x006b }
            com.android.internal.view.IInputMethodSession r9 = r1.mCurMethod     // Catch:{ RemoteException -> 0x006b }
            r12 = r19
            r13 = r20
            r14 = r21
            r15 = r22
            r9.updateSelection(r10, r11, r12, r13, r14, r15)     // Catch:{ RemoteException -> 0x006b }
            goto L_0x0084
        L_0x006b:
            r0 = move-exception
            java.lang.String r3 = "InputMethodManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0088 }
            r4.<init>()     // Catch:{ all -> 0x0088 }
            java.lang.String r5 = "IME died: "
            r4.append(r5)     // Catch:{ all -> 0x0088 }
            java.lang.String r5 = r1.mCurId     // Catch:{ all -> 0x0088 }
            r4.append(r5)     // Catch:{ all -> 0x0088 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0088 }
            android.util.Log.w(r3, r4, r0)     // Catch:{ all -> 0x0088 }
        L_0x0084:
            monitor-exit(r2)     // Catch:{ all -> 0x0088 }
            return
        L_0x0086:
            monitor-exit(r2)     // Catch:{ all -> 0x0088 }
            return
        L_0x0088:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0088 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.updateSelection(android.view.View, int, int, int, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0054, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void viewClicked(android.view.View r8) {
        /*
            r7 = this;
            android.view.inputmethod.InputMethodManager r0 = r7.getFallbackInputMethodManagerIfNecessary(r8)
            if (r0 == 0) goto L_0x000a
            r0.viewClicked(r8)
            return
        L_0x000a:
            android.view.View r1 = r7.mServedView
            android.view.View r2 = r7.mNextServedView
            if (r1 == r2) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            r7.checkFocus()
            android.view.inputmethod.InputMethodManager$H r2 = r7.mH
            monitor-enter(r2)
            android.view.View r3 = r7.mServedView     // Catch:{ all -> 0x0055 }
            if (r3 == r8) goto L_0x0029
            android.view.View r3 = r7.mServedView     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0053
            android.view.View r3 = r7.mServedView     // Catch:{ all -> 0x0055 }
            boolean r3 = r3.checkInputConnectionProxy(r8)     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0053
        L_0x0029:
            android.view.inputmethod.EditorInfo r3 = r7.mCurrentTextBoxAttribute     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0053
            com.android.internal.view.IInputMethodSession r3 = r7.mCurMethod     // Catch:{ all -> 0x0055 }
            if (r3 != 0) goto L_0x0032
            goto L_0x0053
        L_0x0032:
            com.android.internal.view.IInputMethodSession r3 = r7.mCurMethod     // Catch:{ RemoteException -> 0x0038 }
            r3.viewClicked(r1)     // Catch:{ RemoteException -> 0x0038 }
            goto L_0x0051
        L_0x0038:
            r3 = move-exception
            java.lang.String r4 = "InputMethodManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0055 }
            r5.<init>()     // Catch:{ all -> 0x0055 }
            java.lang.String r6 = "IME died: "
            r5.append(r6)     // Catch:{ all -> 0x0055 }
            java.lang.String r6 = r7.mCurId     // Catch:{ all -> 0x0055 }
            r5.append(r6)     // Catch:{ all -> 0x0055 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0055 }
            android.util.Log.w(r4, r5, r3)     // Catch:{ all -> 0x0055 }
        L_0x0051:
            monitor-exit(r2)     // Catch:{ all -> 0x0055 }
            return
        L_0x0053:
            monitor-exit(r2)     // Catch:{ all -> 0x0055 }
            return
        L_0x0055:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0055 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.viewClicked(android.view.View):void");
    }

    @Deprecated
    public boolean isWatchingCursor(View view) {
        return false;
    }

    @UnsupportedAppUsage
    public boolean isCursorAnchorInfoEnabled() {
        boolean z;
        synchronized (this.mH) {
            z = true;
            boolean isImmediate = (this.mRequestUpdateCursorAnchorInfoMonitorMode & 1) != 0;
            boolean isMonitoring = (this.mRequestUpdateCursorAnchorInfoMonitorMode & 2) != 0;
            if (!isImmediate) {
                if (!isMonitoring) {
                    z = false;
                }
            }
        }
        return z;
    }

    @UnsupportedAppUsage
    public void setUpdateCursorAnchorInfoMode(int flags) {
        synchronized (this.mH) {
            this.mRequestUpdateCursorAnchorInfoMonitorMode = flags;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateCursor(android.view.View r8, int r9, int r10, int r11, int r12) {
        /*
            r7 = this;
            android.view.inputmethod.InputMethodManager r6 = r7.getFallbackInputMethodManagerIfNecessary(r8)
            if (r6 == 0) goto L_0x0010
            r0 = r6
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r0.updateCursor(r1, r2, r3, r4, r5)
            return
        L_0x0010:
            r7.checkFocus()
            android.view.inputmethod.InputMethodManager$H r0 = r7.mH
            monitor-enter(r0)
            android.view.View r1 = r7.mServedView     // Catch:{ all -> 0x006a }
            if (r1 == r8) goto L_0x0026
            android.view.View r1 = r7.mServedView     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0068
            android.view.View r1 = r7.mServedView     // Catch:{ all -> 0x006a }
            boolean r1 = r1.checkInputConnectionProxy(r8)     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0068
        L_0x0026:
            android.view.inputmethod.EditorInfo r1 = r7.mCurrentTextBoxAttribute     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0068
            com.android.internal.view.IInputMethodSession r1 = r7.mCurMethod     // Catch:{ all -> 0x006a }
            if (r1 != 0) goto L_0x002f
            goto L_0x0068
        L_0x002f:
            android.graphics.Rect r1 = r7.mTmpCursorRect     // Catch:{ all -> 0x006a }
            r1.set(r9, r10, r11, r12)     // Catch:{ all -> 0x006a }
            android.graphics.Rect r1 = r7.mCursorRect     // Catch:{ all -> 0x006a }
            android.graphics.Rect r2 = r7.mTmpCursorRect     // Catch:{ all -> 0x006a }
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x006a }
            if (r1 != 0) goto L_0x0066
            com.android.internal.view.IInputMethodSession r1 = r7.mCurMethod     // Catch:{ RemoteException -> 0x004d }
            android.graphics.Rect r2 = r7.mTmpCursorRect     // Catch:{ RemoteException -> 0x004d }
            r1.updateCursor(r2)     // Catch:{ RemoteException -> 0x004d }
            android.graphics.Rect r1 = r7.mCursorRect     // Catch:{ RemoteException -> 0x004d }
            android.graphics.Rect r2 = r7.mTmpCursorRect     // Catch:{ RemoteException -> 0x004d }
            r1.set(r2)     // Catch:{ RemoteException -> 0x004d }
            goto L_0x0066
        L_0x004d:
            r1 = move-exception
            java.lang.String r2 = "InputMethodManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x006a }
            r3.<init>()     // Catch:{ all -> 0x006a }
            java.lang.String r4 = "IME died: "
            r3.append(r4)     // Catch:{ all -> 0x006a }
            java.lang.String r4 = r7.mCurId     // Catch:{ all -> 0x006a }
            r3.append(r4)     // Catch:{ all -> 0x006a }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x006a }
            android.util.Log.w(r2, r3, r1)     // Catch:{ all -> 0x006a }
        L_0x0066:
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            return
        L_0x0068:
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            return
        L_0x006a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.updateCursor(android.view.View, int, int, int, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x007e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateCursorAnchorInfo(android.view.View r8, android.view.inputmethod.CursorAnchorInfo r9) {
        /*
            r7 = this;
            if (r8 == 0) goto L_0x0082
            if (r9 != 0) goto L_0x0006
            goto L_0x0082
        L_0x0006:
            android.view.inputmethod.InputMethodManager r0 = r7.getFallbackInputMethodManagerIfNecessary(r8)
            if (r0 == 0) goto L_0x0010
            r0.updateCursorAnchorInfo(r8, r9)
            return
        L_0x0010:
            r7.checkFocus()
            android.view.inputmethod.InputMethodManager$H r1 = r7.mH
            monitor-enter(r1)
            android.view.View r2 = r7.mServedView     // Catch:{ all -> 0x007f }
            if (r2 == r8) goto L_0x0026
            android.view.View r2 = r7.mServedView     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x007d
            android.view.View r2 = r7.mServedView     // Catch:{ all -> 0x007f }
            boolean r2 = r2.checkInputConnectionProxy(r8)     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x007d
        L_0x0026:
            android.view.inputmethod.EditorInfo r2 = r7.mCurrentTextBoxAttribute     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x007d
            com.android.internal.view.IInputMethodSession r2 = r7.mCurMethod     // Catch:{ all -> 0x007f }
            if (r2 != 0) goto L_0x002f
            goto L_0x007d
        L_0x002f:
            int r2 = r7.mRequestUpdateCursorAnchorInfoMonitorMode     // Catch:{ all -> 0x007f }
            r3 = 1
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0036
            goto L_0x0037
        L_0x0036:
            r3 = 0
        L_0x0037:
            r2 = r3
            if (r2 != 0) goto L_0x0044
            android.view.inputmethod.CursorAnchorInfo r3 = r7.mCursorAnchorInfo     // Catch:{ all -> 0x007f }
            boolean r3 = java.util.Objects.equals(r3, r9)     // Catch:{ all -> 0x007f }
            if (r3 == 0) goto L_0x0044
            monitor-exit(r1)     // Catch:{ all -> 0x007f }
            return
        L_0x0044:
            android.graphics.Matrix r3 = r7.mActivityViewToScreenMatrix     // Catch:{ RemoteException -> 0x0062 }
            if (r3 == 0) goto L_0x0054
            com.android.internal.view.IInputMethodSession r3 = r7.mCurMethod     // Catch:{ RemoteException -> 0x0062 }
            android.graphics.Matrix r4 = r7.mActivityViewToScreenMatrix     // Catch:{ RemoteException -> 0x0062 }
            android.view.inputmethod.CursorAnchorInfo r4 = android.view.inputmethod.CursorAnchorInfo.createForAdditionalParentMatrix(r9, r4)     // Catch:{ RemoteException -> 0x0062 }
            r3.updateCursorAnchorInfo(r4)     // Catch:{ RemoteException -> 0x0062 }
            goto L_0x0059
        L_0x0054:
            com.android.internal.view.IInputMethodSession r3 = r7.mCurMethod     // Catch:{ RemoteException -> 0x0062 }
            r3.updateCursorAnchorInfo(r9)     // Catch:{ RemoteException -> 0x0062 }
        L_0x0059:
            r7.mCursorAnchorInfo = r9     // Catch:{ RemoteException -> 0x0062 }
            int r3 = r7.mRequestUpdateCursorAnchorInfoMonitorMode     // Catch:{ RemoteException -> 0x0062 }
            r3 = r3 & -2
            r7.mRequestUpdateCursorAnchorInfoMonitorMode = r3     // Catch:{ RemoteException -> 0x0062 }
            goto L_0x007b
        L_0x0062:
            r3 = move-exception
            java.lang.String r4 = "InputMethodManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x007f }
            r5.<init>()     // Catch:{ all -> 0x007f }
            java.lang.String r6 = "IME died: "
            r5.append(r6)     // Catch:{ all -> 0x007f }
            java.lang.String r6 = r7.mCurId     // Catch:{ all -> 0x007f }
            r5.append(r6)     // Catch:{ all -> 0x007f }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x007f }
            android.util.Log.w(r4, r5, r3)     // Catch:{ all -> 0x007f }
        L_0x007b:
            monitor-exit(r1)     // Catch:{ all -> 0x007f }
            return
        L_0x007d:
            monitor-exit(r1)     // Catch:{ all -> 0x007f }
            return
        L_0x007f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x007f }
            throw r2
        L_0x0082:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.updateCursorAnchorInfo(android.view.View, android.view.inputmethod.CursorAnchorInfo):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendAppPrivateCommand(android.view.View r7, java.lang.String r8, android.os.Bundle r9) {
        /*
            r6 = this;
            android.view.inputmethod.InputMethodManager r0 = r6.getFallbackInputMethodManagerIfNecessary(r7)
            if (r0 == 0) goto L_0x000a
            r0.sendAppPrivateCommand(r7, r8, r9)
            return
        L_0x000a:
            r6.checkFocus()
            android.view.inputmethod.InputMethodManager$H r1 = r6.mH
            monitor-enter(r1)
            android.view.View r2 = r6.mServedView     // Catch:{ all -> 0x004c }
            if (r2 == r7) goto L_0x0020
            android.view.View r2 = r6.mServedView     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x004a
            android.view.View r2 = r6.mServedView     // Catch:{ all -> 0x004c }
            boolean r2 = r2.checkInputConnectionProxy(r7)     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x004a
        L_0x0020:
            android.view.inputmethod.EditorInfo r2 = r6.mCurrentTextBoxAttribute     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x004a
            com.android.internal.view.IInputMethodSession r2 = r6.mCurMethod     // Catch:{ all -> 0x004c }
            if (r2 != 0) goto L_0x0029
            goto L_0x004a
        L_0x0029:
            com.android.internal.view.IInputMethodSession r2 = r6.mCurMethod     // Catch:{ RemoteException -> 0x002f }
            r2.appPrivateCommand(r8, r9)     // Catch:{ RemoteException -> 0x002f }
            goto L_0x0048
        L_0x002f:
            r2 = move-exception
            java.lang.String r3 = "InputMethodManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x004c }
            r4.<init>()     // Catch:{ all -> 0x004c }
            java.lang.String r5 = "IME died: "
            r4.append(r5)     // Catch:{ all -> 0x004c }
            java.lang.String r5 = r6.mCurId     // Catch:{ all -> 0x004c }
            r4.append(r5)     // Catch:{ all -> 0x004c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x004c }
            android.util.Log.w(r3, r4, r2)     // Catch:{ all -> 0x004c }
        L_0x0048:
            monitor-exit(r1)     // Catch:{ all -> 0x004c }
            return
        L_0x004a:
            monitor-exit(r1)     // Catch:{ all -> 0x004c }
            return
        L_0x004c:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004c }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.sendAppPrivateCommand(android.view.View, java.lang.String, android.os.Bundle):void");
    }

    @Deprecated
    public void setInputMethod(IBinder token, String id) {
        if (token != null) {
            InputMethodPrivilegedOperationsRegistry.get(token).setInputMethod(id);
        } else if (id != null) {
            if (Process.myUid() == 1000) {
                Log.w(TAG, "System process should not be calling setInputMethod() because almost always it is a bug under multi-user / multi-profile environment. Consider interacting with InputMethodManagerService directly via LocalServices.");
                return;
            }
            Context fallbackContext = ActivityThread.currentApplication();
            if (fallbackContext != null && fallbackContext.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) == 0) {
                List<InputMethodInfo> imis = getEnabledInputMethodList();
                int numImis = imis.size();
                boolean found = false;
                int i = 0;
                while (true) {
                    if (i >= numImis) {
                        break;
                    } else if (id.equals(imis.get(i).getId())) {
                        found = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!found) {
                    Log.e(TAG, "Ignoring setInputMethod(null, " + id + ") because the specified id not found in enabled IMEs.");
                    return;
                }
                Log.w(TAG, "The undocumented behavior that setInputMethod() accepts null token when the caller has WRITE_SECURE_SETTINGS is deprecated. This behavior may be completely removed in a future version.  Update secure settings directly instead.");
                ContentResolver resolver = fallbackContext.getContentResolver();
                Settings.Secure.putInt(resolver, Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, -1);
                Settings.Secure.putString(resolver, Settings.Secure.DEFAULT_INPUT_METHOD, id);
            }
        }
    }

    @Deprecated
    public void setInputMethodAndSubtype(IBinder token, String id, InputMethodSubtype subtype) {
        if (token == null) {
            Log.e(TAG, "setInputMethodAndSubtype() does not accept null token on Android Q and later.");
        } else {
            InputMethodPrivilegedOperationsRegistry.get(token).setInputMethodAndSubtype(id, subtype);
        }
    }

    @Deprecated
    public void hideSoftInputFromInputMethod(IBinder token, int flags) {
        InputMethodPrivilegedOperationsRegistry.get(token).hideMySoftInput(flags);
    }

    @Deprecated
    public void showSoftInputFromInputMethod(IBinder token, int flags) {
        InputMethodPrivilegedOperationsRegistry.get(token).showMySoftInput(flags);
    }

    public int dispatchInputEvent(InputEvent event, Object token, FinishedInputEventCallback callback, Handler handler) {
        synchronized (this.mH) {
            if (this.mCurMethod == null) {
                return 0;
            }
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                if (keyEvent.getAction() == 0 && keyEvent.getKeyCode() == 63 && keyEvent.getRepeatCount() == 0) {
                    showInputMethodPickerLocked();
                    return 1;
                }
            }
            PendingEvent p = obtainPendingEventLocked(event, token, this.mCurId, callback, handler);
            if (this.mMainLooper.isCurrentThread()) {
                int sendInputEventOnMainLooperLocked = sendInputEventOnMainLooperLocked(p);
                return sendInputEventOnMainLooperLocked;
            }
            Message msg = this.mH.obtainMessage(5, p);
            msg.setAsynchronous(true);
            this.mH.sendMessage(msg);
            return -1;
        }
    }

    public void dispatchKeyEventFromInputMethod(View targetView, KeyEvent event) {
        ViewRootImpl viewRootImpl;
        InputMethodManager fallbackImm = getFallbackInputMethodManagerIfNecessary(targetView);
        if (fallbackImm != null) {
            fallbackImm.dispatchKeyEventFromInputMethod(targetView, event);
            return;
        }
        synchronized (this.mH) {
            if (targetView != null) {
                try {
                    viewRootImpl = targetView.getViewRootImpl();
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                viewRootImpl = null;
            }
            if (viewRootImpl == null && this.mServedView != null) {
                viewRootImpl = this.mServedView.getViewRootImpl();
            }
            if (viewRootImpl != null) {
                viewRootImpl.dispatchKeyFromIme(event);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void sendInputEventAndReportResultOnMainLooper(PendingEvent p) {
        synchronized (this.mH) {
            int result = sendInputEventOnMainLooperLocked(p);
            if (result != -1) {
                boolean z = true;
                if (result != 1) {
                    z = false;
                }
                boolean handled = z;
                invokeFinishedInputEventCallback(p, handled);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int sendInputEventOnMainLooperLocked(PendingEvent p) {
        if (this.mCurChannel != null) {
            if (this.mCurSender == null) {
                this.mCurSender = new ImeInputEventSender(this.mCurChannel, this.mH.getLooper());
            }
            InputEvent event = p.mEvent;
            int seq = event.getSequenceNumber();
            if (this.mCurSender.sendInputEvent(seq, event)) {
                this.mPendingEvents.put(seq, p);
                Trace.traceCounter(4, PENDING_EVENT_COUNTER, this.mPendingEvents.size());
                Message msg = this.mH.obtainMessage(6, seq, 0, p);
                msg.setAsynchronous(true);
                this.mH.sendMessageDelayed(msg, INPUT_METHOD_NOT_RESPONDING_TIMEOUT);
                return -1;
            }
            Log.w(TAG, "Unable to send input event to IME: " + this.mCurId + " dropping: " + event);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0049, code lost:
        invokeFinishedInputEventCallback(r2, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finishedInputEvent(int r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            android.view.inputmethod.InputMethodManager$H r0 = r7.mH
            monitor-enter(r0)
            android.util.SparseArray<android.view.inputmethod.InputMethodManager$PendingEvent> r1 = r7.mPendingEvents     // Catch:{ all -> 0x004e }
            int r1 = r1.indexOfKey(r8)     // Catch:{ all -> 0x004e }
            if (r1 >= 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            return
        L_0x000d:
            android.util.SparseArray<android.view.inputmethod.InputMethodManager$PendingEvent> r2 = r7.mPendingEvents     // Catch:{ all -> 0x004e }
            java.lang.Object r2 = r2.valueAt(r1)     // Catch:{ all -> 0x004e }
            android.view.inputmethod.InputMethodManager$PendingEvent r2 = (android.view.inputmethod.InputMethodManager.PendingEvent) r2     // Catch:{ all -> 0x004e }
            android.util.SparseArray<android.view.inputmethod.InputMethodManager$PendingEvent> r3 = r7.mPendingEvents     // Catch:{ all -> 0x004e }
            r3.removeAt(r1)     // Catch:{ all -> 0x004e }
            r3 = 4
            java.lang.String r5 = "aq:imm"
            android.util.SparseArray<android.view.inputmethod.InputMethodManager$PendingEvent> r6 = r7.mPendingEvents     // Catch:{ all -> 0x004e }
            int r6 = r6.size()     // Catch:{ all -> 0x004e }
            android.os.Trace.traceCounter(r3, r5, r6)     // Catch:{ all -> 0x004e }
            if (r10 == 0) goto L_0x0042
            java.lang.String r3 = "InputMethodManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x004e }
            r4.<init>()     // Catch:{ all -> 0x004e }
            java.lang.String r5 = "Timeout waiting for IME to handle input event after 2500 ms: "
            r4.append(r5)     // Catch:{ all -> 0x004e }
            java.lang.String r5 = r2.mInputMethodId     // Catch:{ all -> 0x004e }
            r4.append(r5)     // Catch:{ all -> 0x004e }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x004e }
            android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x004e }
            goto L_0x0048
        L_0x0042:
            android.view.inputmethod.InputMethodManager$H r3 = r7.mH     // Catch:{ all -> 0x004e }
            r4 = 6
            r3.removeMessages(r4, r2)     // Catch:{ all -> 0x004e }
        L_0x0048:
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            r0 = r2
            r7.invokeFinishedInputEventCallback(r0, r9)
            return
        L_0x004e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.inputmethod.InputMethodManager.finishedInputEvent(int, boolean, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public void invokeFinishedInputEventCallback(PendingEvent p, boolean handled) {
        p.mHandled = handled;
        if (p.mHandler.getLooper().isCurrentThread()) {
            p.run();
            return;
        }
        Message msg = Message.obtain(p.mHandler, (Runnable) p);
        msg.setAsynchronous(true);
        msg.sendToTarget();
    }

    private void flushPendingEventsLocked() {
        this.mH.removeMessages(7);
        int count = this.mPendingEvents.size();
        for (int i = 0; i < count; i++) {
            Message msg = this.mH.obtainMessage(7, this.mPendingEvents.keyAt(i), 0);
            msg.setAsynchronous(true);
            msg.sendToTarget();
        }
    }

    private PendingEvent obtainPendingEventLocked(InputEvent event, Object token, String inputMethodId, FinishedInputEventCallback callback, Handler handler) {
        PendingEvent p = this.mPendingEventPool.acquire();
        if (p == null) {
            p = new PendingEvent();
        }
        p.mEvent = event;
        p.mToken = token;
        p.mInputMethodId = inputMethodId;
        p.mCallback = callback;
        p.mHandler = handler;
        return p;
    }

    /* access modifiers changed from: private */
    public void recyclePendingEventLocked(PendingEvent p) {
        p.recycle();
        this.mPendingEventPool.release(p);
    }

    public void showInputMethodPicker() {
        synchronized (this.mH) {
            showInputMethodPickerLocked();
        }
    }

    public void showInputMethodPickerFromSystem(boolean showAuxiliarySubtypes, int displayId) {
        int mode;
        if (showAuxiliarySubtypes) {
            mode = 1;
        } else {
            mode = 2;
        }
        try {
            this.mService.showInputMethodPickerFromSystem(this.mClient, mode, displayId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void showInputMethodPickerLocked() {
        try {
            this.mService.showInputMethodPickerFromClient(this.mClient, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInputMethodPickerShown() {
        try {
            return this.mService.isInputMethodPickerShownForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void showInputMethodAndSubtypeEnabler(String imiId) {
        try {
            this.mService.showInputMethodAndSubtypeEnablerFromClient(this.mClient, imiId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public InputMethodSubtype getCurrentInputMethodSubtype() {
        try {
            return this.mService.getCurrentInputMethodSubtype();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean setCurrentInputMethodSubtype(InputMethodSubtype subtype) {
        Context fallbackContext;
        if (Process.myUid() == 1000) {
            Log.w(TAG, "System process should not call setCurrentInputMethodSubtype() because almost always it is a bug under multi-user / multi-profile environment. Consider directly interacting with InputMethodManagerService via LocalServices.");
            return false;
        } else if (subtype == null || (fallbackContext = ActivityThread.currentApplication()) == null || fallbackContext.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS) != 0) {
            return false;
        } else {
            ContentResolver contentResolver = fallbackContext.getContentResolver();
            String imeId = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD);
            if (ComponentName.unflattenFromString(imeId) == null) {
                return false;
            }
            try {
                List<InputMethodSubtype> enabledSubtypes = this.mService.getEnabledInputMethodSubtypeList(imeId, true);
                int numSubtypes = enabledSubtypes.size();
                for (int i = 0; i < numSubtypes; i++) {
                    InputMethodSubtype enabledSubtype = enabledSubtypes.get(i);
                    if (enabledSubtype.equals(subtype)) {
                        Settings.Secure.putInt(contentResolver, Settings.Secure.SELECTED_INPUT_METHOD_SUBTYPE, enabledSubtype.hashCode());
                        return true;
                    }
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 114740982)
    public void notifyUserAction() {
        Log.w(TAG, "notifyUserAction() is a hidden method, which is now just a stub method that does nothing.  Leave comments in b.android.com/114740982 if your  application still depends on the previous behavior of this method.");
    }

    public Map<InputMethodInfo, List<InputMethodSubtype>> getShortcutInputMethodsAndSubtypes() {
        List<InputMethodInfo> enabledImes = getEnabledInputMethodList();
        enabledImes.sort(Comparator.comparingInt($$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w.INSTANCE));
        int numEnabledImes = enabledImes.size();
        for (int imiIndex = 0; imiIndex < numEnabledImes; imiIndex++) {
            InputMethodInfo imi = enabledImes.get(imiIndex);
            int subtypeCount = getEnabledInputMethodSubtypeList(imi, true).size();
            for (int subtypeIndex = 0; subtypeIndex < subtypeCount; subtypeIndex++) {
                InputMethodSubtype subtype = imi.getSubtypeAt(subtypeIndex);
                if (SUBTYPE_MODE_VOICE.equals(subtype.getMode())) {
                    return Collections.singletonMap(imi, Collections.singletonList(subtype));
                }
            }
        }
        return Collections.emptyMap();
    }

    static /* synthetic */ int lambda$getShortcutInputMethodsAndSubtypes$2(InputMethodInfo imi) {
        return imi.isSystem() ^ true ? 1 : 0;
    }

    @UnsupportedAppUsage
    public int getInputMethodWindowVisibleHeight() {
        try {
            return this.mService.getInputMethodWindowVisibleHeight();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportActivityView(int childDisplayId, Matrix matrix) {
        float[] matrixValues;
        if (matrix == null) {
            matrixValues = null;
        } else {
            try {
                matrixValues = new float[9];
                matrix.getValues(matrixValues);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mService.reportActivityView(this.mClient, childDisplayId, matrixValues);
    }

    @Deprecated
    public boolean switchToLastInputMethod(IBinder imeToken) {
        return InputMethodPrivilegedOperationsRegistry.get(imeToken).switchToPreviousInputMethod();
    }

    @Deprecated
    public boolean switchToNextInputMethod(IBinder imeToken, boolean onlyCurrentIme) {
        return InputMethodPrivilegedOperationsRegistry.get(imeToken).switchToNextInputMethod(onlyCurrentIme);
    }

    @Deprecated
    public boolean shouldOfferSwitchingToNextInputMethod(IBinder imeToken) {
        return InputMethodPrivilegedOperationsRegistry.get(imeToken).shouldOfferSwitchingToNextInputMethod();
    }

    @Deprecated
    public void setAdditionalInputMethodSubtypes(String imiId, InputMethodSubtype[] subtypes) {
        try {
            this.mService.setAdditionalInputMethodSubtypes(imiId, subtypes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public InputMethodSubtype getLastInputMethodSubtype() {
        try {
            return this.mService.getLastInputMethodSubtype();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void maybeCallServedViewChangedLocked(EditorInfo tba) {
        if (this.mImeInsetsConsumer != null) {
            this.mImeInsetsConsumer.onServedEditorChanged(tba);
        }
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    /* access modifiers changed from: package-private */
    public void doDump(FileDescriptor fd, PrintWriter fout, String[] args) {
        Printer p = new PrintWriterPrinter(fout);
        p.println("Input method client state for " + this + SettingsStringUtil.DELIMITER);
        StringBuilder sb = new StringBuilder();
        sb.append("  mService=");
        sb.append(this.mService);
        p.println(sb.toString());
        p.println("  mMainLooper=" + this.mMainLooper);
        p.println("  mIInputContext=" + this.mIInputContext);
        p.println("  mActive=" + this.mActive + " mRestartOnNextWindowFocus=" + this.mRestartOnNextWindowFocus + " mBindSequence=" + this.mBindSequence + " mCurId=" + this.mCurId);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  mFullscreenMode=");
        sb2.append(this.mFullscreenMode);
        p.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("  mCurMethod=");
        sb3.append(this.mCurMethod);
        p.println(sb3.toString());
        p.println("  mCurRootView=" + this.mCurRootView);
        p.println("  mServedView=" + this.mServedView);
        p.println("  mNextServedView=" + this.mNextServedView);
        p.println("  mServedConnecting=" + this.mServedConnecting);
        if (this.mCurrentTextBoxAttribute != null) {
            p.println("  mCurrentTextBoxAttribute:");
            this.mCurrentTextBoxAttribute.dump(p, "    ");
        } else {
            p.println("  mCurrentTextBoxAttribute: null");
        }
        p.println("  mServedInputConnectionWrapper=" + this.mServedInputConnectionWrapper);
        p.println("  mCompletions=" + Arrays.toString(this.mCompletions));
        p.println("  mCursorRect=" + this.mCursorRect);
        p.println("  mCursorSelStart=" + this.mCursorSelStart + " mCursorSelEnd=" + this.mCursorSelEnd + " mCursorCandStart=" + this.mCursorCandStart + " mCursorCandEnd=" + this.mCursorCandEnd);
    }

    private final class ImeInputEventSender extends InputEventSender {
        public ImeInputEventSender(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        public void onInputEventFinished(int seq, boolean handled) {
            InputMethodManager.this.finishedInputEvent(seq, handled, false);
        }
    }

    private final class PendingEvent implements Runnable {
        public FinishedInputEventCallback mCallback;
        public InputEvent mEvent;
        public boolean mHandled;
        public Handler mHandler;
        public String mInputMethodId;
        public Object mToken;

        private PendingEvent() {
        }

        public void recycle() {
            this.mEvent = null;
            this.mToken = null;
            this.mInputMethodId = null;
            this.mCallback = null;
            this.mHandler = null;
            this.mHandled = false;
        }

        public void run() {
            this.mCallback.onFinishedInputEvent(this.mToken, this.mHandled);
            synchronized (InputMethodManager.this.mH) {
                InputMethodManager.this.recyclePendingEventLocked(this);
            }
        }
    }

    private static String dumpViewInfo(View view) {
        if (view == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(view);
        sb.append(",focus=" + view.hasFocus());
        sb.append(",windowFocus=" + view.hasWindowFocus());
        sb.append(",autofillUiShowing=" + isAutofillUIShowing(view));
        sb.append(",window=" + view.getWindowToken());
        sb.append(",displayId=" + view.getContext().getDisplayId());
        sb.append(",temporaryDetach=" + view.isTemporarilyDetached());
        return sb.toString();
    }
}
