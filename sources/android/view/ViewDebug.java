package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.Picture;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.Debug;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.RemoteException;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import com.ibm.icu.text.PluralRules;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/* loaded from: classes4.dex */
public class ViewDebug {
    private static final int CAPTURE_TIMEOUT = 4000;
    public static final boolean DEBUG_DRAG = false;
    public static final boolean DEBUG_POSITIONING = false;
    private static final String REMOTE_COMMAND_CAPTURE = "CAPTURE";
    private static final String REMOTE_COMMAND_CAPTURE_LAYERS = "CAPTURE_LAYERS";
    private static final String REMOTE_COMMAND_DUMP = "DUMP";
    private static final String REMOTE_COMMAND_DUMP_THEME = "DUMP_THEME";
    private static final String REMOTE_COMMAND_INVALIDATE = "INVALIDATE";
    private static final String REMOTE_COMMAND_OUTPUT_DISPLAYLIST = "OUTPUT_DISPLAYLIST";
    private static final String REMOTE_COMMAND_REQUEST_LAYOUT = "REQUEST_LAYOUT";
    private static final String REMOTE_PROFILE = "PROFILE";
    @Deprecated
    public static final boolean TRACE_HIERARCHY = false;
    @Deprecated
    public static final boolean TRACE_RECYCLER = false;
    private static HashMap<AccessibleObject, ExportedProperty> sAnnotations;
    private static HashMap<Class<?>, Field[]> sFieldsForClasses;
    private static HashMap<Class<?>, Method[]> sMethodsForClasses;
    private static HashMap<Class<?>, Method[]> mCapturedViewMethodsForClasses = null;
    private static HashMap<Class<?>, Field[]> mCapturedViewFieldsForClasses = null;

    /* loaded from: classes4.dex */
    public interface CanvasProvider {
        Bitmap createBitmap();

        Canvas getCanvas(View view, int i, int i2);
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: classes4.dex */
    public @interface CapturedViewProperty {
        boolean retrieveReturn() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: classes4.dex */
    public @interface ExportedProperty {
        String category() default "";

        boolean deepExport() default false;

        FlagToString[] flagMapping() default {};

        boolean formatToHexString() default false;

        boolean hasAdjacentMapping() default false;

        IntToString[] indexMapping() default {};

        IntToString[] mapping() default {};

        String prefix() default "";

        boolean resolveId() default false;
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: classes4.dex */
    public @interface FlagToString {
        int equals();

        int mask();

        String name();

        boolean outputIf() default true;
    }

    /* loaded from: classes4.dex */
    public interface HierarchyHandler {
        void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int i);

        View findHierarchyView(String str, int i);
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public enum HierarchyTraceType {
        INVALIDATE,
        INVALIDATE_CHILD,
        INVALIDATE_CHILD_IN_PARENT,
        REQUEST_LAYOUT,
        ON_LAYOUT,
        ON_MEASURE,
        DRAW,
        BUILD_CACHE
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: classes4.dex */
    public @interface IntToString {
        int from();

        /* renamed from: to */
        String m46to();
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public enum RecyclerTraceType {
        NEW_VIEW,
        BIND_VIEW,
        RECYCLE_FROM_ACTIVE_HEAP,
        RECYCLE_FROM_SCRAP_HEAP,
        MOVE_TO_SCRAP_HEAP,
        MOVE_FROM_ACTIVE_TO_SCRAP_HEAP
    }

    @UnsupportedAppUsage
    public static long getViewInstanceCount() {
        return Debug.countInstancesOfClass(View.class);
    }

    @UnsupportedAppUsage
    public static long getViewRootImplCount() {
        return Debug.countInstancesOfClass(ViewRootImpl.class);
    }

    @Deprecated
    public static void trace(View view, RecyclerTraceType type, int... parameters) {
    }

    @Deprecated
    public static void startRecyclerTracing(String prefix, View view) {
    }

    @Deprecated
    public static void stopRecyclerTracing() {
    }

    @Deprecated
    public static void trace(View view, HierarchyTraceType type) {
    }

    @Deprecated
    public static void startHierarchyTracing(String prefix, View view) {
    }

    @Deprecated
    public static void stopHierarchyTracing() {
    }

    @UnsupportedAppUsage
    static void dispatchCommand(View view, String command, String parameters, OutputStream clientStream) throws IOException {
        View view2 = view.getRootView();
        if (REMOTE_COMMAND_DUMP.equalsIgnoreCase(command)) {
            dump(view2, false, true, clientStream);
        } else if (REMOTE_COMMAND_DUMP_THEME.equalsIgnoreCase(command)) {
            dumpTheme(view2, clientStream);
        } else if (REMOTE_COMMAND_CAPTURE_LAYERS.equalsIgnoreCase(command)) {
            captureLayers(view2, new DataOutputStream(clientStream));
        } else {
            String[] params = parameters.split(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            if (REMOTE_COMMAND_CAPTURE.equalsIgnoreCase(command)) {
                capture(view2, clientStream, params[0]);
            } else if (REMOTE_COMMAND_OUTPUT_DISPLAYLIST.equalsIgnoreCase(command)) {
                outputDisplayList(view2, params[0]);
            } else if (REMOTE_COMMAND_INVALIDATE.equalsIgnoreCase(command)) {
                invalidate(view2, params[0]);
            } else if (REMOTE_COMMAND_REQUEST_LAYOUT.equalsIgnoreCase(command)) {
                requestLayout(view2, params[0]);
            } else if (REMOTE_PROFILE.equalsIgnoreCase(command)) {
                profile(view2, clientStream, params[0]);
            }
        }
    }

    public static View findView(View root, String parameter) {
        if (parameter.indexOf(64) != -1) {
            String[] ids = parameter.split("@");
            String className = ids[0];
            int hashCode = (int) Long.parseLong(ids[1], 16);
            View view = root.getRootView();
            if (view instanceof ViewGroup) {
                return findView((ViewGroup) view, className, hashCode);
            }
            return null;
        }
        int id = root.getResources().getIdentifier(parameter, null, null);
        return root.getRootView().findViewById(id);
    }

    private static void invalidate(View root, String parameter) {
        View view = findView(root, parameter);
        if (view != null) {
            view.postInvalidate();
        }
    }

    private static void requestLayout(View root, String parameter) {
        final View view = findView(root, parameter);
        if (view != null) {
            root.post(new Runnable() { // from class: android.view.ViewDebug.1
                @Override // java.lang.Runnable
                public void run() {
                    View.this.requestLayout();
                }
            });
        }
    }

    private static void profile(View root, OutputStream clientStream, String parameter) throws IOException {
        View view = findView(root, parameter);
        BufferedWriter out = null;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(clientStream), 32768);
                if (view != null) {
                    profileViewAndChildren(view, out);
                } else {
                    out.write("-1 -1 -1");
                    out.newLine();
                }
                out.write("DONE.");
                out.newLine();
            } catch (Exception e) {
                Log.m63w("View", "Problem profiling the view:", e);
                if (out == null) {
                    return;
                }
            }
            out.close();
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
    }

    public static void profileViewAndChildren(View view, BufferedWriter out) throws IOException {
        RenderNode node = RenderNode.create("ViewDebug", null);
        profileViewAndChildren(view, node, out, true);
    }

    private static void profileViewAndChildren(View view, RenderNode node, BufferedWriter out, boolean root) throws IOException {
        long durationDraw = 0;
        long durationMeasure = (root || (view.mPrivateFlags & 2048) != 0) ? profileViewMeasure(view) : 0L;
        long durationLayout = (root || (view.mPrivateFlags & 8192) != 0) ? profileViewLayout(view) : 0L;
        if (root || !view.willNotDraw() || (view.mPrivateFlags & 32) != 0) {
            durationDraw = profileViewDraw(view, node);
        }
        out.write(String.valueOf(durationMeasure));
        out.write(32);
        out.write(String.valueOf(durationLayout));
        out.write(32);
        out.write(String.valueOf(durationDraw));
        out.newLine();
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                profileViewAndChildren(group.getChildAt(i), node, out, false);
            }
        }
    }

    private static long profileViewMeasure(final View view) {
        return profileViewOperation(view, new ViewOperation() { // from class: android.view.ViewDebug.2
            @Override // android.view.ViewDebug.ViewOperation
            public void pre() {
                forceLayout(View.this);
            }

            private void forceLayout(View view2) {
                view2.forceLayout();
                if (view2 instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view2;
                    int count = group.getChildCount();
                    for (int i = 0; i < count; i++) {
                        forceLayout(group.getChildAt(i));
                    }
                }
            }

            @Override // android.view.ViewDebug.ViewOperation
            public void run() {
                View.this.measure(View.this.mOldWidthMeasureSpec, View.this.mOldHeightMeasureSpec);
            }
        });
    }

    private static long profileViewLayout(final View view) {
        return profileViewOperation(view, new ViewOperation() { // from class: android.view.-$$Lambda$ViewDebug$inOytI2zZEgp1DJv8Cu4GjQVNiE
            @Override // android.view.ViewDebug.ViewOperation
            public final void run() {
                r0.layout(r0.mLeft, r0.mTop, r0.mRight, View.this.mBottom);
            }
        });
    }

    private static long profileViewDraw(final View view, RenderNode node) {
        DisplayMetrics dm = view.getResources().getDisplayMetrics();
        if (dm == null) {
            return 0L;
        }
        if (view.isHardwareAccelerated()) {
            final RecordingCanvas canvas = node.beginRecording(dm.widthPixels, dm.heightPixels);
            try {
                return profileViewOperation(view, new ViewOperation() { // from class: android.view.-$$Lambda$ViewDebug$flFXZc7_CjFXx7_tYT59WSbUNjI
                    @Override // android.view.ViewDebug.ViewOperation
                    public final void run() {
                        View.this.draw(canvas);
                    }
                });
            } finally {
                node.endRecording();
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(dm, dm.widthPixels, dm.heightPixels, Bitmap.Config.RGB_565);
        final Canvas canvas2 = new Canvas(bitmap);
        try {
            return profileViewOperation(view, new ViewOperation() { // from class: android.view.-$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw
                @Override // android.view.ViewDebug.ViewOperation
                public final void run() {
                    View.this.draw(canvas2);
                }
            });
        } finally {
            canvas2.setBitmap(null);
            bitmap.recycle();
        }
    }

    /* loaded from: classes4.dex */
    interface ViewOperation {
        void run();

        default void pre() {
        }
    }

    private static long profileViewOperation(View view, final ViewOperation operation) {
        final CountDownLatch latch = new CountDownLatch(1);
        final long[] duration = new long[1];
        view.post(new Runnable() { // from class: android.view.-$$Lambda$ViewDebug$5rTN0pemwbr3I3IL2E-xDBeDTDg
            @Override // java.lang.Runnable
            public final void run() {
                ViewDebug.lambda$profileViewOperation$3(ViewDebug.ViewOperation.this, duration, latch);
            }
        });
        try {
            if (!latch.await(4000L, TimeUnit.MILLISECONDS)) {
                Log.m64w("View", "Could not complete the profiling of the view " + view);
                return -1L;
            }
            return duration[0];
        } catch (InterruptedException e) {
            Log.m64w("View", "Could not complete the profiling of the view " + view);
            Thread.currentThread().interrupt();
            return -1L;
        }
    }

    static /* synthetic */ void lambda$profileViewOperation$3(ViewOperation operation, long[] duration, CountDownLatch latch) {
        try {
            operation.pre();
            long start = Debug.threadCpuTimeNanos();
            operation.run();
            duration[0] = Debug.threadCpuTimeNanos() - start;
        } finally {
            latch.countDown();
        }
    }

    public static void captureLayers(View root, DataOutputStream clientStream) throws IOException {
        try {
            Rect outRect = new Rect();
            try {
                root.mAttachInfo.mSession.getDisplayFrame(root.mAttachInfo.mWindow, outRect);
            } catch (RemoteException e) {
            }
            clientStream.writeInt(outRect.width());
            clientStream.writeInt(outRect.height());
            captureViewLayer(root, clientStream, true);
            clientStream.write(2);
        } finally {
            clientStream.close();
        }
    }

    private static void captureViewLayer(View view, DataOutputStream clientStream, boolean visible) throws IOException {
        boolean localVisible = view.getVisibility() == 0 && visible;
        if ((view.mPrivateFlags & 128) != 128) {
            int id = view.getId();
            String name = view.getClass().getSimpleName();
            if (id != -1) {
                name = resolveId(view.getContext(), id).toString();
            }
            clientStream.write(1);
            clientStream.writeUTF(name);
            clientStream.writeByte(localVisible ? 1 : 0);
            int[] position = new int[2];
            view.getLocationInWindow(position);
            clientStream.writeInt(position[0]);
            clientStream.writeInt(position[1]);
            clientStream.flush();
            Bitmap b = performViewCapture(view, true);
            if (b != null) {
                ByteArrayOutputStream arrayOut = new ByteArrayOutputStream(b.getWidth() * b.getHeight() * 2);
                b.compress(Bitmap.CompressFormat.PNG, 100, arrayOut);
                clientStream.writeInt(arrayOut.size());
                arrayOut.writeTo(clientStream);
            }
            clientStream.flush();
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                captureViewLayer(group.getChildAt(i), clientStream, localVisible);
            }
        }
        if (view.mOverlay != null) {
            ViewGroup overlayContainer = view.getOverlay().mOverlayViewGroup;
            captureViewLayer(overlayContainer, clientStream, localVisible);
        }
    }

    private static void outputDisplayList(View root, String parameter) throws IOException {
        View view = findView(root, parameter);
        view.getViewRootImpl().outputDisplayList(view);
    }

    public static void outputDisplayList(View root, View target) {
        root.getViewRootImpl().outputDisplayList(target);
    }

    /* loaded from: classes4.dex */
    private static class PictureCallbackHandler implements AutoCloseable, HardwareRenderer.PictureCapturedCallback, Runnable {
        private final Function<Picture, Boolean> mCallback;
        private final Executor mExecutor;
        private final ReentrantLock mLock;
        private final ArrayDeque<Picture> mQueue;
        private Thread mRenderThread;
        private final HardwareRenderer mRenderer;
        private boolean mStopListening;

        private PictureCallbackHandler(HardwareRenderer renderer, Function<Picture, Boolean> callback, Executor executor) {
            this.mLock = new ReentrantLock(false);
            this.mQueue = new ArrayDeque<>(3);
            this.mRenderer = renderer;
            this.mCallback = callback;
            this.mExecutor = executor;
            this.mRenderer.setPictureCaptureCallback(this);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback(null);
        }

        @Override // android.graphics.HardwareRenderer.PictureCapturedCallback
        public void onPictureCaptured(Picture picture) {
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback(null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = Thread.currentThread();
            }
            Picture toDestroy = null;
            if (this.mQueue.size() == 3) {
                Picture toDestroy2 = this.mQueue.removeLast();
                toDestroy = toDestroy2;
            }
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (toDestroy == null) {
                this.mExecutor.execute(this);
            } else {
                toDestroy.close();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mLock.lock();
            Picture picture = this.mQueue.poll();
            boolean isStopped = this.mStopListening;
            this.mLock.unlock();
            if (Thread.currentThread() == this.mRenderThread) {
                close();
                throw new IllegalStateException("ViewDebug#startRenderingCommandsCapture must be given an executor that invokes asynchronously");
            } else if (isStopped) {
                picture.close();
            } else {
                boolean keepReceiving = this.mCallback.apply(picture).booleanValue();
                if (!keepReceiving) {
                    close();
                }
            }
        }
    }

    @Deprecated
    public static AutoCloseable startRenderingCommandsCapture(View tree, Executor executor, Function<Picture, Boolean> callback) {
        View.AttachInfo attachInfo = tree.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalArgumentException("Given view isn't attached");
        }
        if (attachInfo.mHandler.getLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        HardwareRenderer renderer = attachInfo.mThreadedRenderer;
        if (renderer == null) {
            return null;
        }
        return new PictureCallbackHandler(renderer, callback, executor);
    }

    public static AutoCloseable startRenderingCommandsCapture(View tree, Executor executor, final Callable<OutputStream> callback) {
        View.AttachInfo attachInfo = tree.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalArgumentException("Given view isn't attached");
        }
        if (attachInfo.mHandler.getLooper() != Looper.myLooper()) {
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
        HardwareRenderer renderer = attachInfo.mThreadedRenderer;
        if (renderer == null) {
            return null;
        }
        return new PictureCallbackHandler(renderer, new Function() { // from class: android.view.-$$Lambda$ViewDebug$hyDSYptlxuUTTyRIONqWzWWVDB0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ViewDebug.lambda$startRenderingCommandsCapture$4(callback, (Picture) obj);
            }
        }, executor);
    }

    static /* synthetic */ Boolean lambda$startRenderingCommandsCapture$4(Callable callback, Picture picture) {
        try {
            OutputStream stream = (OutputStream) callback.call();
            if (stream != null) {
                picture.writeToStream(stream);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private static void capture(View root, OutputStream clientStream, String parameter) throws IOException {
        View captureView = findView(root, parameter);
        capture(root, clientStream, captureView);
    }

    public static void capture(View root, OutputStream clientStream, View captureView) throws IOException {
        Bitmap b = performViewCapture(captureView, false);
        if (b == null) {
            Log.m64w("View", "Failed to create capture bitmap!");
            b = Bitmap.createBitmap(root.getResources().getDisplayMetrics(), 1, 1, Bitmap.Config.ARGB_8888);
        }
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(clientStream, 32768);
            b.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            b.recycle();
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            b.recycle();
            throw th;
        }
    }

    private static Bitmap performViewCapture(final View captureView, final boolean skipChildren) {
        if (captureView != null) {
            final CountDownLatch latch = new CountDownLatch(1);
            final Bitmap[] cache = new Bitmap[1];
            captureView.post(new Runnable() { // from class: android.view.-$$Lambda$ViewDebug$1iDmmthcZt_8LsYI6VndkxasPEs
                @Override // java.lang.Runnable
                public final void run() {
                    ViewDebug.lambda$performViewCapture$5(View.this, cache, skipChildren, latch);
                }
            });
            try {
                latch.await(4000L, TimeUnit.MILLISECONDS);
                return cache[0];
            } catch (InterruptedException e) {
                Log.m64w("View", "Could not complete the capture of the view " + captureView);
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }

    static /* synthetic */ void lambda$performViewCapture$5(View captureView, Bitmap[] cache, boolean skipChildren, CountDownLatch latch) {
        try {
            try {
                CanvasProvider provider = captureView.isHardwareAccelerated() ? new HardwareCanvasProvider() : new SoftwareCanvasProvider();
                cache[0] = captureView.createSnapshot(provider, skipChildren);
            } catch (OutOfMemoryError e) {
                Log.m64w("View", "Out of memory for bitmap");
            }
        } finally {
            latch.countDown();
        }
    }

    @UnsupportedAppUsage
    @Deprecated
    public static void dump(View root, boolean skipChildren, boolean includeProperties, OutputStream clientStream) throws IOException {
        BufferedWriter out = null;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(clientStream, "utf-8"), 32768);
                View view = root.getRootView();
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    dumpViewHierarchy(group.getContext(), group, out, 0, skipChildren, includeProperties);
                }
                out.write("DONE.");
                out.newLine();
            } catch (Exception e) {
                Log.m63w("View", "Problem dumping the view:", e);
                if (out == null) {
                    return;
                }
            }
            out.close();
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
    }

    public static void dumpv2(final View view, ByteArrayOutputStream out) throws InterruptedException {
        final ViewHierarchyEncoder encoder = new ViewHierarchyEncoder(out);
        final CountDownLatch latch = new CountDownLatch(1);
        view.post(new Runnable() { // from class: android.view.ViewDebug.3
            @Override // java.lang.Runnable
            public void run() {
                ViewHierarchyEncoder.this.addProperty("window:left", view.mAttachInfo.mWindowLeft);
                ViewHierarchyEncoder.this.addProperty("window:top", view.mAttachInfo.mWindowTop);
                view.encode(ViewHierarchyEncoder.this);
                latch.countDown();
            }
        });
        latch.await(2L, TimeUnit.SECONDS);
        encoder.endStream();
    }

    public static void dumpTheme(View view, OutputStream clientStream) throws IOException {
        BufferedWriter out = null;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(clientStream, "utf-8"), 32768);
                String[] attributes = getStyleAttributesDump(view.getContext().getResources(), view.getContext().getTheme());
                if (attributes != null) {
                    for (int i = 0; i < attributes.length; i += 2) {
                        if (attributes[i] != null) {
                            out.write(attributes[i] + "\n");
                            out.write(attributes[i + 1] + "\n");
                        }
                    }
                }
                out.write("DONE.");
                out.newLine();
            } catch (Exception e) {
                Log.m63w("View", "Problem dumping View Theme:", e);
                if (out == null) {
                    return;
                }
            }
            out.close();
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
    }

    private static String[] getStyleAttributesDump(Resources resources, Resources.Theme theme) {
        String str;
        TypedValue outValue = new TypedValue();
        int i = 0;
        int[] attributes = theme.getAllAttributes();
        String[] data = new String[attributes.length * 2];
        for (int attributeId : attributes) {
            try {
                data[i] = resources.getResourceName(attributeId);
                int i2 = i + 1;
                if (!theme.resolveAttribute(attributeId, outValue, true)) {
                    str = "null";
                } else {
                    str = outValue.coerceToString().toString();
                }
                data[i2] = str;
                i += 2;
                if (outValue.type == 1) {
                    data[i - 1] = resources.getResourceName(outValue.resourceId);
                }
            } catch (Resources.NotFoundException e) {
            }
        }
        return data;
    }

    private static View findView(ViewGroup group, String className, int hashCode) {
        View found;
        View found2;
        if (isRequestedView(group, className, hashCode)) {
            return group;
        }
        int count = group.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            if (view instanceof ViewGroup) {
                View found3 = findView((ViewGroup) view, className, hashCode);
                if (found3 != null) {
                    return found3;
                }
            } else if (isRequestedView(view, className, hashCode)) {
                return view;
            }
            if (view.mOverlay != null && (found2 = findView(view.mOverlay.mOverlayViewGroup, className, hashCode)) != null) {
                return found2;
            }
            if ((view instanceof HierarchyHandler) && (found = ((HierarchyHandler) view).findHierarchyView(className, hashCode)) != null) {
                return found;
            }
        }
        return null;
    }

    private static boolean isRequestedView(View view, String className, int hashCode) {
        if (view.hashCode() == hashCode) {
            String viewClassName = view.getClass().getName();
            if (className.equals("ViewOverlay")) {
                return viewClassName.equals("android.view.ViewOverlay$OverlayViewGroup");
            }
            return className.equals(viewClassName);
        }
        return false;
    }

    private static void dumpViewHierarchy(Context context, ViewGroup group, BufferedWriter out, int level, boolean skipChildren, boolean includeProperties) {
        if (!dumpView(context, group, out, level, includeProperties) || skipChildren) {
            return;
        }
        int count = group.getChildCount();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= count) {
                break;
            }
            View view = group.getChildAt(i2);
            if (!(view instanceof ViewGroup)) {
                dumpView(context, view, out, level + 1, includeProperties);
            } else {
                dumpViewHierarchy(context, (ViewGroup) view, out, level + 1, skipChildren, includeProperties);
            }
            if (view.mOverlay != null) {
                ViewOverlay overlay = view.getOverlay();
                ViewGroup overlayContainer = overlay.mOverlayViewGroup;
                dumpViewHierarchy(context, overlayContainer, out, level + 2, skipChildren, includeProperties);
            }
            i = i2 + 1;
        }
        if (group instanceof HierarchyHandler) {
            ((HierarchyHandler) group).dumpViewHierarchyWithProperties(out, level + 1);
        }
    }

    private static boolean dumpView(Context context, View view, BufferedWriter out, int level, boolean includeProperties) {
        for (int i = 0; i < level; i++) {
            try {
                out.write(32);
            } catch (IOException e) {
                Log.m64w("View", "Error while dumping hierarchy tree");
                return false;
            }
        }
        String className = view.getClass().getName();
        if (className.equals("android.view.ViewOverlay$OverlayViewGroup")) {
            className = "ViewOverlay";
        }
        out.write(className);
        out.write(64);
        out.write(Integer.toHexString(view.hashCode()));
        out.write(32);
        if (includeProperties) {
            dumpViewProperties(context, view, out);
        }
        out.newLine();
        return true;
    }

    private static Field[] getExportedPropertyFields(Class<?> klass) {
        if (sFieldsForClasses == null) {
            sFieldsForClasses = new HashMap<>();
        }
        if (sAnnotations == null) {
            sAnnotations = new HashMap<>(512);
        }
        HashMap<Class<?>, Field[]> map = sFieldsForClasses;
        Field[] fields = map.get(klass);
        if (fields != null) {
            return fields;
        }
        try {
            Field[] declaredFields = klass.getDeclaredFieldsUnchecked(false);
            ArrayList<Field> foundFields = new ArrayList<>();
            for (Field field : declaredFields) {
                if (field.getType() != null && field.isAnnotationPresent(ExportedProperty.class)) {
                    field.setAccessible(true);
                    foundFields.add(field);
                    sAnnotations.put(field, (ExportedProperty) field.getAnnotation(ExportedProperty.class));
                }
            }
            Field[] fields2 = (Field[]) foundFields.toArray(new Field[foundFields.size()]);
            map.put(klass, fields2);
            return fields2;
        } catch (NoClassDefFoundError e) {
            throw new AssertionError(e);
        }
    }

    private static Method[] getExportedPropertyMethods(Class<?> klass) {
        if (sMethodsForClasses == null) {
            sMethodsForClasses = new HashMap<>(100);
        }
        if (sAnnotations == null) {
            sAnnotations = new HashMap<>(512);
        }
        HashMap<Class<?>, Method[]> map = sMethodsForClasses;
        Method[] methods = map.get(klass);
        if (methods != null) {
            return methods;
        }
        Method[] methods2 = klass.getDeclaredMethodsUnchecked(false);
        ArrayList<Method> foundMethods = new ArrayList<>();
        for (Method method : methods2) {
            try {
                method.getReturnType();
                method.getParameterTypes();
                if (method.getParameterTypes().length == 0 && method.isAnnotationPresent(ExportedProperty.class) && method.getReturnType() != Void.class) {
                    method.setAccessible(true);
                    foundMethods.add(method);
                    sAnnotations.put(method, (ExportedProperty) method.getAnnotation(ExportedProperty.class));
                }
            } catch (NoClassDefFoundError e) {
            }
        }
        Method[] methods3 = (Method[]) foundMethods.toArray(new Method[foundMethods.size()]);
        map.put(klass, methods3);
        return methods3;
    }

    private static void dumpViewProperties(Context context, Object view, BufferedWriter out) throws IOException {
        dumpViewProperties(context, view, out, "");
    }

    private static void dumpViewProperties(Context context, Object view, BufferedWriter out, String prefix) throws IOException {
        if (view == null) {
            out.write(prefix + "=4,null ");
            return;
        }
        Class<?> klass = view.getClass();
        do {
            exportFields(context, view, out, klass, prefix);
            exportMethods(context, view, out, klass, prefix);
            klass = klass.getSuperclass();
        } while (klass != Object.class);
    }

    private static Object callMethodOnAppropriateTheadBlocking(final Method method, Object object) throws IllegalAccessException, InvocationTargetException, TimeoutException {
        if (!(object instanceof View)) {
            return method.invoke(object, null);
        }
        final View view = (View) object;
        Callable<Object> callable = new Callable<Object>() { // from class: android.view.ViewDebug.4
            @Override // java.util.concurrent.Callable
            public Object call() throws IllegalAccessException, InvocationTargetException {
                return method.invoke(view, null);
            }
        };
        FutureTask<Object> future = new FutureTask<>(callable);
        Handler handler = view.getHandler();
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(future);
        while (true) {
            try {
                return future.get(4000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
            } catch (CancellationException e2) {
                throw new RuntimeException("Unexpected cancellation exception", e2);
            } catch (ExecutionException e3) {
                Throwable t = e3.getCause();
                if (t instanceof IllegalAccessException) {
                    throw ((IllegalAccessException) t);
                }
                if (t instanceof InvocationTargetException) {
                    throw ((InvocationTargetException) t);
                }
                throw new RuntimeException("Unexpected exception", t);
            }
        }
    }

    private static String formatIntToHexString(int value) {
        return "0x" + Integer.toHexString(value).toUpperCase();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:4|(4:5|6|(2:119|120)(1:8)|9)|(4:67|68|(5:74|(1:76)|77|(4:79|80|(2:81|(3:83|84|(2:87|88)(1:86))(2:106|107))|(2:90|91))(1:108)|92)(2:71|72)|73)(4:11|12|(5:52|53|54|55|56)(2:14|(4:16|(3:19|(4:22|(4:24|(1:26)(1:30)|27|28)(1:31)|29|20)|32)|33|34)(2:36|(2:40|41)))|35)|42|43|45|35) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void exportMethods(Context context, Object view, BufferedWriter out, Class<?> klass, String prefix) throws IOException {
        Method[] methods;
        int count;
        Object methodValue;
        Class<?> returnType;
        ExportedProperty property;
        String str;
        String categoryPrefix;
        String categoryPrefix2;
        Method[] methods2 = getExportedPropertyMethods(klass);
        int count2 = methods2.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= count2) {
                return;
            }
            Method method = methods2[i2];
            try {
                methodValue = callMethodOnAppropriateTheadBlocking(method, view);
                returnType = method.getReturnType();
                property = sAnnotations.get(method);
                if (property.category().length() != 0) {
                    try {
                        str = property.category() + SettingsStringUtil.DELIMITER;
                    } catch (IllegalAccessException e) {
                        methods = methods2;
                        count = count2;
                        i = i2 + 1;
                        methods2 = methods;
                        count2 = count;
                    } catch (InvocationTargetException e2) {
                        methods = methods2;
                        count = count2;
                        i = i2 + 1;
                        methods2 = methods;
                        count2 = count;
                    } catch (TimeoutException e3) {
                        methods = methods2;
                        count = count2;
                        i = i2 + 1;
                        methods2 = methods;
                        count2 = count;
                    }
                } else {
                    str = "";
                }
                categoryPrefix = str;
            } catch (IllegalAccessException e4) {
                methods = methods2;
                count = count2;
            } catch (InvocationTargetException e5) {
                methods = methods2;
                count = count2;
            } catch (TimeoutException e6) {
                methods = methods2;
                count = count2;
            }
            if (returnType == Integer.TYPE) {
                try {
                    if (!property.resolveId() || context == null) {
                        FlagToString[] flagsMapping = property.flagMapping();
                        if (flagsMapping.length > 0) {
                            String valuePrefix = categoryPrefix + prefix + method.getName() + '_';
                            exportUnrolledFlags(out, flagsMapping, ((Integer) methodValue).intValue(), valuePrefix);
                        }
                        IntToString[] mapping = property.mapping();
                        if (mapping.length > 0) {
                            int intValue = ((Integer) methodValue).intValue();
                            boolean mapped = false;
                            int mappingCount = mapping.length;
                            int j = 0;
                            while (true) {
                                int j2 = j;
                                methods = methods2;
                                if (j2 >= mappingCount) {
                                    break;
                                }
                                try {
                                    IntToString mapper = mapping[j2];
                                    int mappingCount2 = mappingCount;
                                    int mappingCount3 = mapper.from();
                                    if (mappingCount3 == intValue) {
                                        methodValue = mapper.m46to();
                                        mapped = true;
                                        break;
                                    }
                                    j = j2 + 1;
                                    methods2 = methods;
                                    mappingCount = mappingCount2;
                                } catch (IllegalAccessException e7) {
                                    count = count2;
                                    i = i2 + 1;
                                    methods2 = methods;
                                    count2 = count;
                                } catch (InvocationTargetException e8) {
                                    count = count2;
                                    i = i2 + 1;
                                    methods2 = methods;
                                    count2 = count;
                                } catch (TimeoutException e9) {
                                    count = count2;
                                    i = i2 + 1;
                                    methods2 = methods;
                                    count2 = count;
                                }
                            }
                            if (!mapped) {
                                methodValue = Integer.valueOf(intValue);
                            }
                        } else {
                            methods = methods2;
                        }
                        categoryPrefix2 = categoryPrefix;
                        count = count2;
                    } else {
                        int id = ((Integer) methodValue).intValue();
                        methodValue = resolveId(context, id);
                        methods = methods2;
                        count = count2;
                        categoryPrefix2 = categoryPrefix;
                    }
                } catch (IllegalAccessException e10) {
                    methods = methods2;
                    count = count2;
                } catch (InvocationTargetException e11) {
                    methods = methods2;
                    count = count2;
                } catch (TimeoutException e12) {
                    methods = methods2;
                    count = count2;
                }
            } else {
                methods = methods2;
                if (returnType == int[].class) {
                    try {
                        String valuePrefix2 = categoryPrefix + prefix + method.getName() + '_';
                        count = count2;
                        exportUnrolledArray(context, out, property, (int[]) methodValue, valuePrefix2, "()");
                    } catch (IllegalAccessException e13) {
                        count = count2;
                    } catch (InvocationTargetException e14) {
                        count = count2;
                    } catch (TimeoutException e15) {
                        count = count2;
                    }
                } else {
                    categoryPrefix2 = categoryPrefix;
                    count = count2;
                    if (returnType == String[].class) {
                        String[] array = (String[]) methodValue;
                        if (property.hasAdjacentMapping() && array != null) {
                            for (int j3 = 0; j3 < array.length; j3 += 2) {
                                if (array[j3] != null) {
                                    writeEntry(out, categoryPrefix2 + prefix, array[j3], "()", array[j3 + 1] == null ? "null" : array[j3 + 1]);
                                }
                            }
                        }
                    } else if (!returnType.isPrimitive() && property.deepExport()) {
                        dumpViewProperties(context, methodValue, out, prefix + property.prefix());
                    }
                }
                i = i2 + 1;
                methods2 = methods;
                count2 = count;
            }
            writeEntry(out, categoryPrefix2 + prefix, method.getName(), "()", methodValue);
            i = i2 + 1;
            methods2 = methods;
            count2 = count;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:4|(4:5|6|(2:87|88)(1:8)|9)|(2:11|(1:13)(3:52|(4:54|55|56|57)(2:58|(4:60|(3:63|(4:66|(4:68|(1:70)(1:74)|71|72)(1:75)|73|64)|76)|77|78)(2:79|(4:85|(1:20)|21|22)(2:83|84)))|23))(1:86)|14|15|(6:24|(1:26)|27|(3:29|(1:(2:31|(2:34|35)(1:33))(2:46|47))|(1:37))(1:48)|38|(2:40|(1:42)(2:43|(1:45))))(1:18)|(0)|21|22|23) */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01c0 A[Catch: IllegalAccessException -> 0x01de, TryCatch #2 {IllegalAccessException -> 0x01de, blocks: (B:47:0x0112, B:50:0x011a, B:76:0x01c0, B:77:0x01c5, B:51:0x0125, B:53:0x012c, B:54:0x014c, B:56:0x0153, B:58:0x015b, B:60:0x0165, B:64:0x0175, B:66:0x017f, B:68:0x0185, B:70:0x018e, B:71:0x019b, B:73:0x019f, B:61:0x016a, B:22:0x0092, B:25:0x00a1, B:29:0x00b0, B:31:0x00b3, B:33:0x00b7, B:37:0x00d8, B:36:0x00d4, B:38:0x00db, B:40:0x00e0, B:42:0x00e6, B:44:0x00ec), top: B:90:0x0112 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void exportFields(Context context, Object view, BufferedWriter out, Class<?> klass, String prefix) throws IOException {
        Field[] fields;
        Class<?> type;
        ExportedProperty property;
        String str;
        String categoryPrefix;
        Object fieldValue;
        String categoryPrefix2;
        Class<?> type2;
        Object fieldValue2;
        Field[] fields2 = getExportedPropertyFields(klass);
        int count = fields2.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= count) {
                return;
            }
            Field field = fields2[i2];
            try {
                type = field.getType();
                property = sAnnotations.get(field);
                if (property.category().length() != 0) {
                    try {
                        str = property.category() + SettingsStringUtil.DELIMITER;
                    } catch (IllegalAccessException e) {
                        fields = fields2;
                    }
                } else {
                    str = "";
                }
                categoryPrefix = str;
            } catch (IllegalAccessException e2) {
                fields = fields2;
            }
            if (type == Integer.TYPE) {
                fieldValue = null;
                categoryPrefix2 = categoryPrefix;
                fields = fields2;
                type2 = type;
            } else if (type == Byte.TYPE) {
                fieldValue = null;
                categoryPrefix2 = categoryPrefix;
                fields = fields2;
                type2 = type;
            } else {
                if (type == int[].class) {
                    String valuePrefix = categoryPrefix + prefix + field.getName() + '_';
                    fields = fields2;
                    exportUnrolledArray(context, out, property, (int[]) field.get(view), valuePrefix, "");
                } else {
                    categoryPrefix2 = categoryPrefix;
                    fields = fields2;
                    if (type == String[].class) {
                        String[] array = (String[]) field.get(view);
                        if (property.hasAdjacentMapping() && array != null) {
                            for (int j = 0; j < array.length; j += 2) {
                                if (array[j] != null) {
                                    writeEntry(out, categoryPrefix2 + prefix, array[j], "", array[j + 1] == null ? "null" : array[j + 1]);
                                }
                            }
                        }
                    } else if (!type.isPrimitive() && property.deepExport()) {
                        dumpViewProperties(context, field.get(view), out, prefix + property.prefix());
                    } else {
                        fieldValue2 = null;
                        if (fieldValue2 == null) {
                            fieldValue2 = field.get(view);
                        }
                        writeEntry(out, categoryPrefix2 + prefix, field.getName(), "", fieldValue2);
                    }
                }
                i = i2 + 1;
                fields2 = fields;
            }
            if (property.resolveId() && context != null) {
                int id = field.getInt(view);
                fieldValue2 = resolveId(context, id);
            } else {
                FlagToString[] flagsMapping = property.flagMapping();
                if (flagsMapping.length > 0) {
                    String valuePrefix2 = categoryPrefix2 + prefix + field.getName() + '_';
                    exportUnrolledFlags(out, flagsMapping, field.getInt(view), valuePrefix2);
                }
                IntToString[] mapping = property.mapping();
                if (mapping.length > 0) {
                    int intValue = field.getInt(view);
                    int mappingCount = mapping.length;
                    int j2 = 0;
                    while (true) {
                        if (j2 >= mappingCount) {
                            fieldValue2 = fieldValue;
                            break;
                        }
                        IntToString mapped = mapping[j2];
                        FlagToString[] flagsMapping2 = flagsMapping;
                        if (mapped.from() != intValue) {
                            j2++;
                            flagsMapping = flagsMapping2;
                        } else {
                            fieldValue2 = mapped.m46to();
                            break;
                        }
                    }
                    if (fieldValue2 == null) {
                        fieldValue2 = Integer.valueOf(intValue);
                    }
                } else {
                    fieldValue2 = fieldValue;
                }
                if (property.formatToHexString()) {
                    fieldValue2 = field.get(view);
                    if (type2 == Integer.TYPE) {
                        fieldValue2 = formatIntToHexString(((Integer) fieldValue2).intValue());
                    } else if (type2 == Byte.TYPE) {
                        fieldValue2 = "0x" + Byte.toHexString(((Byte) fieldValue2).byteValue(), true);
                    }
                }
            }
            if (fieldValue2 == null) {
            }
            writeEntry(out, categoryPrefix2 + prefix, field.getName(), "", fieldValue2);
            i = i2 + 1;
            fields2 = fields;
        }
    }

    private static void writeEntry(BufferedWriter out, String prefix, String name, String suffix, Object value) throws IOException {
        out.write(prefix);
        out.write(name);
        out.write(suffix);
        out.write("=");
        writeValue(out, value);
        out.write(32);
    }

    private static void exportUnrolledFlags(BufferedWriter out, FlagToString[] mapping, int intValue, String prefix) throws IOException {
        for (FlagToString flagMapping : mapping) {
            boolean ifTrue = flagMapping.outputIf();
            int maskResult = flagMapping.mask() & intValue;
            boolean test = maskResult == flagMapping.equals();
            if ((test && ifTrue) || (!test && !ifTrue)) {
                String name = flagMapping.name();
                String value = formatIntToHexString(maskResult);
                writeEntry(out, prefix, name, "", value);
            }
        }
    }

    public static String intToString(Class<?> clazz, String field, int integer) {
        IntToString[] mapping = getMapping(clazz, field);
        if (mapping == null) {
            return Integer.toString(integer);
        }
        for (IntToString map : mapping) {
            if (map.from() == integer) {
                return map.m46to();
            }
        }
        return Integer.toString(integer);
    }

    public static String flagsToString(Class<?> clazz, String field, int flags) {
        FlagToString[] mapping = getFlagMapping(clazz, field);
        if (mapping == null) {
            return Integer.toHexString(flags);
        }
        StringBuilder result = new StringBuilder();
        int count = mapping.length;
        int j = 0;
        while (true) {
            if (j >= count) {
                break;
            }
            FlagToString flagMapping = mapping[j];
            boolean ifTrue = flagMapping.outputIf();
            int maskResult = flagMapping.mask() & flags;
            boolean test = maskResult == flagMapping.equals();
            if (test && ifTrue) {
                String name = flagMapping.name();
                result.append(name);
                result.append(' ');
            }
            j++;
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }

    private static FlagToString[] getFlagMapping(Class<?> clazz, String field) {
        try {
            return ((ExportedProperty) clazz.getDeclaredField(field).getAnnotation(ExportedProperty.class)).flagMapping();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static IntToString[] getMapping(Class<?> clazz, String field) {
        try {
            return ((ExportedProperty) clazz.getDeclaredField(field).getAnnotation(ExportedProperty.class)).mapping();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static void exportUnrolledArray(Context context, BufferedWriter out, ExportedProperty property, int[] array, String prefix, String suffix) throws IOException {
        IntToString[] indexMapping = property.indexMapping();
        boolean resolveId = true;
        boolean hasIndexMapping = indexMapping.length > 0;
        IntToString[] mapping = property.mapping();
        boolean hasMapping = mapping.length > 0;
        resolveId = (!property.resolveId() || context == null) ? false : false;
        int valuesCount = array.length;
        for (int j = 0; j < valuesCount; j++) {
            String value = null;
            int intValue = array[j];
            String name = String.valueOf(j);
            if (hasIndexMapping) {
                int mappingCount = indexMapping.length;
                int k = 0;
                while (true) {
                    if (k >= mappingCount) {
                        break;
                    }
                    IntToString mapped = indexMapping[k];
                    if (mapped.from() != j) {
                        k++;
                    } else {
                        name = mapped.m46to();
                        break;
                    }
                }
            }
            if (hasMapping) {
                int mappingCount2 = mapping.length;
                int k2 = 0;
                while (true) {
                    if (k2 >= mappingCount2) {
                        break;
                    }
                    IntToString mapped2 = mapping[k2];
                    if (mapped2.from() != intValue) {
                        k2++;
                    } else {
                        value = mapped2.m46to();
                        break;
                    }
                }
            }
            if (resolveId) {
                if (value == null) {
                    value = (String) resolveId(context, intValue);
                }
            } else {
                value = String.valueOf(intValue);
            }
            writeEntry(out, prefix, name, suffix, value);
        }
    }

    static Object resolveId(Context context, int id) {
        Resources resources = context.getResources();
        if (id >= 0) {
            try {
                Object fieldValue = resources.getResourceTypeName(id) + '/' + resources.getResourceEntryName(id);
                return fieldValue;
            } catch (Resources.NotFoundException e) {
                Object fieldValue2 = "id/" + formatIntToHexString(id);
                return fieldValue2;
            }
        }
        return "NO_ID";
    }

    private static void writeValue(BufferedWriter out, Object value) throws IOException {
        if (value != null) {
            String output = "[EXCEPTION]";
            try {
                output = value.toString().replace("\n", "\\n");
                return;
            } finally {
                out.write(String.valueOf(output.length()));
                out.write(SmsManager.REGEX_PREFIX_DELIMITER);
                out.write(output);
            }
        }
        out.write("4,null");
    }

    private static Field[] capturedViewGetPropertyFields(Class<?> klass) {
        if (mCapturedViewFieldsForClasses == null) {
            mCapturedViewFieldsForClasses = new HashMap<>();
        }
        HashMap<Class<?>, Field[]> map = mCapturedViewFieldsForClasses;
        Field[] fields = map.get(klass);
        if (fields != null) {
            return fields;
        }
        ArrayList<Field> foundFields = new ArrayList<>();
        Field[] fields2 = klass.getFields();
        for (Field field : fields2) {
            if (field.isAnnotationPresent(CapturedViewProperty.class)) {
                field.setAccessible(true);
                foundFields.add(field);
            }
        }
        int i = foundFields.size();
        Field[] fields3 = (Field[]) foundFields.toArray(new Field[i]);
        map.put(klass, fields3);
        return fields3;
    }

    private static Method[] capturedViewGetPropertyMethods(Class<?> klass) {
        if (mCapturedViewMethodsForClasses == null) {
            mCapturedViewMethodsForClasses = new HashMap<>();
        }
        HashMap<Class<?>, Method[]> map = mCapturedViewMethodsForClasses;
        Method[] methods = map.get(klass);
        if (methods != null) {
            return methods;
        }
        ArrayList<Method> foundMethods = new ArrayList<>();
        Method[] methods2 = klass.getMethods();
        for (Method method : methods2) {
            if (method.getParameterTypes().length == 0 && method.isAnnotationPresent(CapturedViewProperty.class) && method.getReturnType() != Void.class) {
                method.setAccessible(true);
                foundMethods.add(method);
            }
        }
        int i = foundMethods.size();
        Method[] methods3 = (Method[]) foundMethods.toArray(new Method[i]);
        map.put(klass, methods3);
        return methods3;
    }

    private static String capturedViewExportMethods(Object obj, Class<?> klass, String prefix) {
        if (obj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Method[] methods = capturedViewGetPropertyMethods(klass);
        for (Method method : methods) {
            try {
                Object methodValue = method.invoke(obj, null);
                Class<?> returnType = method.getReturnType();
                CapturedViewProperty property = (CapturedViewProperty) method.getAnnotation(CapturedViewProperty.class);
                if (property.retrieveReturn()) {
                    sb.append(capturedViewExportMethods(methodValue, returnType, method.getName() + "#"));
                } else {
                    sb.append(prefix);
                    sb.append(method.getName());
                    sb.append("()=");
                    if (methodValue != null) {
                        String value = methodValue.toString().replace("\n", "\\n");
                        sb.append(value);
                    } else {
                        sb.append("null");
                    }
                    sb.append("; ");
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e2) {
            }
        }
        return sb.toString();
    }

    private static String capturedViewExportFields(Object obj, Class<?> klass, String prefix) {
        if (obj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        Field[] fields = capturedViewGetPropertyFields(klass);
        for (Field field : fields) {
            try {
                Object fieldValue = field.get(obj);
                sb.append(prefix);
                sb.append(field.getName());
                sb.append("=");
                if (fieldValue != null) {
                    String value = fieldValue.toString().replace("\n", "\\n");
                    sb.append(value);
                } else {
                    sb.append("null");
                }
                sb.append(' ');
            } catch (IllegalAccessException e) {
            }
        }
        return sb.toString();
    }

    public static void dumpCapturedView(String tag, Object view) {
        Class<?> klass = view.getClass();
        StringBuilder sb = new StringBuilder(klass.getName() + PluralRules.KEYWORD_RULE_SEPARATOR);
        sb.append(capturedViewExportFields(view, klass, ""));
        sb.append(capturedViewExportMethods(view, klass, ""));
        Log.m72d(tag, sb.toString());
    }

    public static Object invokeViewMethod(final View view, final Method method, final Object[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Object> result = new AtomicReference<>();
        final AtomicReference<Throwable> exception = new AtomicReference<>();
        view.post(new Runnable() { // from class: android.view.ViewDebug.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    result.set(method.invoke(view, args));
                } catch (InvocationTargetException e) {
                    exception.set(e.getCause());
                } catch (Exception e2) {
                    exception.set(e2);
                }
                latch.countDown();
            }
        });
        try {
            latch.await();
            if (exception.get() != null) {
                throw new RuntimeException(exception.get());
            }
            return result.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLayoutParameter(final View view, String param, int value) throws NoSuchFieldException, IllegalAccessException {
        final ViewGroup.LayoutParams p = view.getLayoutParams();
        Field f = p.getClass().getField(param);
        if (f.getType() != Integer.TYPE) {
            throw new RuntimeException("Only integer layout parameters can be set. Field " + param + " is of type " + f.getType().getSimpleName());
        }
        f.set(p, Integer.valueOf(value));
        view.post(new Runnable() { // from class: android.view.ViewDebug.6
            @Override // java.lang.Runnable
            public void run() {
                View.this.setLayoutParams(p);
            }
        });
    }

    /* loaded from: classes4.dex */
    public static class SoftwareCanvasProvider implements CanvasProvider {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private boolean mEnabledHwBitmapsInSwMode;

        @Override // android.view.ViewDebug.CanvasProvider
        public Canvas getCanvas(View view, int width, int height) {
            this.mBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), width, height, Bitmap.Config.ARGB_8888);
            if (this.mBitmap == null) {
                throw new OutOfMemoryError();
            }
            this.mBitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
            if (view.mAttachInfo != null) {
                this.mCanvas = view.mAttachInfo.mCanvas;
            }
            if (this.mCanvas == null) {
                this.mCanvas = new Canvas();
            }
            this.mEnabledHwBitmapsInSwMode = this.mCanvas.isHwBitmapsInSwModeEnabled();
            this.mCanvas.setBitmap(this.mBitmap);
            return this.mCanvas;
        }

        @Override // android.view.ViewDebug.CanvasProvider
        public Bitmap createBitmap() {
            this.mCanvas.setBitmap(null);
            this.mCanvas.setHwBitmapsInSwModeEnabled(this.mEnabledHwBitmapsInSwMode);
            return this.mBitmap;
        }
    }

    /* loaded from: classes4.dex */
    public static class HardwareCanvasProvider implements CanvasProvider {
        private Picture mPicture;

        @Override // android.view.ViewDebug.CanvasProvider
        public Canvas getCanvas(View view, int width, int height) {
            this.mPicture = new Picture();
            return this.mPicture.beginRecording(width, height);
        }

        @Override // android.view.ViewDebug.CanvasProvider
        public Bitmap createBitmap() {
            this.mPicture.endRecording();
            return Bitmap.createBitmap(this.mPicture);
        }
    }
}
