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
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.SettingsStringUtil;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewOverlay;
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
    private static HashMap<Class<?>, Field[]> mCapturedViewFieldsForClasses = null;
    private static HashMap<Class<?>, Method[]> mCapturedViewMethodsForClasses = null;
    private static HashMap<AccessibleObject, ExportedProperty> sAnnotations;
    private static HashMap<Class<?>, Field[]> sFieldsForClasses;
    private static HashMap<Class<?>, Method[]> sMethodsForClasses;

    public interface CanvasProvider {
        Bitmap createBitmap();

        Canvas getCanvas(View view, int i, int i2);
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CapturedViewProperty {
        boolean retrieveReturn() default false;
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
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
    public @interface FlagToString {
        int equals();

        int mask();

        String name();

        boolean outputIf() default true;
    }

    public interface HierarchyHandler {
        void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int i);

        View findHierarchyView(String str, int i);
    }

    @Deprecated
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
    public @interface IntToString {
        int from();

        String to();
    }

    @Deprecated
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
        return root.getRootView().findViewById(root.getResources().getIdentifier(parameter, (String) null, (String) null));
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
            root.post(new Runnable() {
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
            Log.w("View", "Problem profiling the view:", e);
            if (out == null) {
                return;
            }
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
        out.close();
    }

    public static void profileViewAndChildren(View view, BufferedWriter out) throws IOException {
        profileViewAndChildren(view, RenderNode.create("ViewDebug", (RenderNode.AnimationHost) null), out, true);
    }

    private static void profileViewAndChildren(View view, RenderNode node, BufferedWriter out, boolean root) throws IOException {
        long durationDraw = 0;
        long durationMeasure = (root || (view.mPrivateFlags & 2048) != 0) ? profileViewMeasure(view) : 0;
        long durationLayout = (root || (view.mPrivateFlags & 8192) != 0) ? profileViewLayout(view) : 0;
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
        return profileViewOperation(view, new ViewOperation() {
            public void pre() {
                forceLayout(View.this);
            }

            private void forceLayout(View view) {
                view.forceLayout();
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    int count = group.getChildCount();
                    for (int i = 0; i < count; i++) {
                        forceLayout(group.getChildAt(i));
                    }
                }
            }

            public void run() {
                View.this.measure(View.this.mOldWidthMeasureSpec, View.this.mOldHeightMeasureSpec);
            }
        });
    }

    private static long profileViewLayout(View view) {
        return profileViewOperation(view, new ViewOperation() {
            public final void run() {
                View.this.layout(View.this.mLeft, View.this.mTop, View.this.mRight, View.this.mBottom);
            }
        });
    }

    private static long profileViewDraw(View view, RenderNode node) {
        DisplayMetrics dm = view.getResources().getDisplayMetrics();
        if (dm == null) {
            return 0;
        }
        if (view.isHardwareAccelerated()) {
            try {
                return profileViewOperation(view, new ViewOperation(node.beginRecording(dm.widthPixels, dm.heightPixels)) {
                    private final /* synthetic */ RecordingCanvas f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        View.this.draw(this.f$1);
                    }
                });
            } finally {
                node.endRecording();
            }
        } else {
            Bitmap bitmap = Bitmap.createBitmap(dm, dm.widthPixels, dm.heightPixels, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            try {
                return profileViewOperation(view, new ViewOperation(canvas) {
                    private final /* synthetic */ Canvas f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        View.this.draw(this.f$1);
                    }
                });
            } finally {
                canvas.setBitmap((Bitmap) null);
                bitmap.recycle();
            }
        }
    }

    interface ViewOperation {
        void run();

        void pre() {
        }
    }

    private static long profileViewOperation(View view, ViewOperation operation) {
        CountDownLatch latch = new CountDownLatch(1);
        long[] duration = new long[1];
        view.post(new Runnable(duration, latch) {
            private final /* synthetic */ long[] f$1;
            private final /* synthetic */ CountDownLatch f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                ViewDebug.lambda$profileViewOperation$3(ViewDebug.ViewOperation.this, this.f$1, this.f$2);
            }
        });
        try {
            if (latch.await(4000, TimeUnit.MILLISECONDS)) {
                return duration[0];
            }
            Log.w("View", "Could not complete the profiling of the view " + view);
            return -1;
        } catch (InterruptedException e) {
            Log.w("View", "Could not complete the profiling of the view " + view);
            Thread.currentThread().interrupt();
            return -1;
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
            captureViewLayer(view.getOverlay().mOverlayViewGroup, clientStream, localVisible);
        }
    }

    private static void outputDisplayList(View root, String parameter) throws IOException {
        View view = findView(root, parameter);
        view.getViewRootImpl().outputDisplayList(view);
    }

    public static void outputDisplayList(View root, View target) {
        root.getViewRootImpl().outputDisplayList(target);
    }

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

        public void close() {
            this.mLock.lock();
            this.mStopListening = true;
            this.mLock.unlock();
            this.mRenderer.setPictureCaptureCallback((HardwareRenderer.PictureCapturedCallback) null);
        }

        public void onPictureCaptured(Picture picture) {
            this.mLock.lock();
            if (this.mStopListening) {
                this.mLock.unlock();
                this.mRenderer.setPictureCaptureCallback((HardwareRenderer.PictureCapturedCallback) null);
                return;
            }
            if (this.mRenderThread == null) {
                this.mRenderThread = Thread.currentThread();
            }
            Picture toDestroy = null;
            if (this.mQueue.size() == 3) {
                toDestroy = this.mQueue.removeLast();
            }
            this.mQueue.add(picture);
            this.mLock.unlock();
            if (toDestroy == null) {
                this.mExecutor.execute(this);
            } else {
                toDestroy.close();
            }
        }

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
            } else if (!this.mCallback.apply(picture).booleanValue()) {
                close();
            }
        }
    }

    @Deprecated
    public static AutoCloseable startRenderingCommandsCapture(View tree, Executor executor, Function<Picture, Boolean> callback) {
        View.AttachInfo attachInfo = tree.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalArgumentException("Given view isn't attached");
        } else if (attachInfo.mHandler.getLooper() == Looper.myLooper()) {
            HardwareRenderer renderer = attachInfo.mThreadedRenderer;
            if (renderer != null) {
                return new PictureCallbackHandler(renderer, callback, executor);
            }
            return null;
        } else {
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
    }

    public static AutoCloseable startRenderingCommandsCapture(View tree, Executor executor, Callable<OutputStream> callback) {
        View.AttachInfo attachInfo = tree.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalArgumentException("Given view isn't attached");
        } else if (attachInfo.mHandler.getLooper() == Looper.myLooper()) {
            HardwareRenderer renderer = attachInfo.mThreadedRenderer;
            if (renderer != null) {
                return new PictureCallbackHandler(renderer, new Function(callback) {
                    private final /* synthetic */ Callable f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final Object apply(Object obj) {
                        return ViewDebug.lambda$startRenderingCommandsCapture$4(this.f$0, (Picture) obj);
                    }
                }, executor);
            }
            return null;
        } else {
            throw new IllegalStateException("Called on the wrong thread. Must be called on the thread that owns the given View");
        }
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
        capture(root, clientStream, findView(root, parameter));
    }

    public static void capture(View root, OutputStream clientStream, View captureView) throws IOException {
        Bitmap b = performViewCapture(captureView, false);
        if (b == null) {
            Log.w("View", "Failed to create capture bitmap!");
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

    private static Bitmap performViewCapture(View captureView, boolean skipChildren) {
        if (captureView == null) {
            return null;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Bitmap[] cache = new Bitmap[1];
        captureView.post(new Runnable(cache, skipChildren, latch) {
            private final /* synthetic */ Bitmap[] f$1;
            private final /* synthetic */ boolean f$2;
            private final /* synthetic */ CountDownLatch f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                ViewDebug.lambda$performViewCapture$5(View.this, this.f$1, this.f$2, this.f$3);
            }
        });
        try {
            latch.await(4000, TimeUnit.MILLISECONDS);
            return cache[0];
        } catch (InterruptedException e) {
            Log.w("View", "Could not complete the capture of the view " + captureView);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    static /* synthetic */ void lambda$performViewCapture$5(View captureView, Bitmap[] cache, boolean skipChildren, CountDownLatch latch) {
        try {
            cache[0] = captureView.createSnapshot(captureView.isHardwareAccelerated() ? new HardwareCanvasProvider() : new SoftwareCanvasProvider(), skipChildren);
        } catch (OutOfMemoryError e) {
            Log.w("View", "Out of memory for bitmap");
        } catch (Throwable th) {
            latch.countDown();
            throw th;
        }
        latch.countDown();
    }

    @Deprecated
    @UnsupportedAppUsage
    public static void dump(View root, boolean skipChildren, boolean includeProperties, OutputStream clientStream) throws IOException {
        BufferedWriter out = null;
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
            Log.w("View", "Problem dumping the view:", e);
            if (out == null) {
                return;
            }
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
        out.close();
    }

    public static void dumpv2(final View view, ByteArrayOutputStream out) throws InterruptedException {
        final ViewHierarchyEncoder encoder = new ViewHierarchyEncoder(out);
        final CountDownLatch latch = new CountDownLatch(1);
        view.post(new Runnable() {
            public void run() {
                ViewHierarchyEncoder.this.addProperty("window:left", view.mAttachInfo.mWindowLeft);
                ViewHierarchyEncoder.this.addProperty("window:top", view.mAttachInfo.mWindowTop);
                view.encode(ViewHierarchyEncoder.this);
                latch.countDown();
            }
        });
        latch.await(2, TimeUnit.SECONDS);
        encoder.endStream();
    }

    public static void dumpTheme(View view, OutputStream clientStream) throws IOException {
        BufferedWriter out = null;
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
            Log.w("View", "Problem dumping View Theme:", e);
            if (out == null) {
                return;
            }
        } catch (Throwable th) {
            if (out != null) {
                out.close();
            }
            throw th;
        }
        out.close();
    }

    private static String[] getStyleAttributesDump(Resources resources, Resources.Theme theme) {
        String str;
        TypedValue outValue = new TypedValue();
        int i = 0;
        int[] attributes = theme.getAllAttributes();
        String[] data = new String[(attributes.length * 2)];
        for (int attributeId : attributes) {
            try {
                data[i] = resources.getResourceName(attributeId);
                int i2 = i + 1;
                if (theme.resolveAttribute(attributeId, outValue, true)) {
                    str = outValue.coerceToString().toString();
                } else {
                    str = "null";
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
        if (view.hashCode() != hashCode) {
            return false;
        }
        String viewClassName = view.getClass().getName();
        if (className.equals("ViewOverlay")) {
            return viewClassName.equals("android.view.ViewOverlay$OverlayViewGroup");
        }
        return className.equals(viewClassName);
    }

    private static void dumpViewHierarchy(Context context, ViewGroup group, BufferedWriter out, int level, boolean skipChildren, boolean includeProperties) {
        Context context2 = context;
        ViewGroup viewGroup = group;
        BufferedWriter bufferedWriter = out;
        int i = level;
        boolean z = includeProperties;
        if (dumpView(context2, viewGroup, bufferedWriter, i, z) && !skipChildren) {
            int count = group.getChildCount();
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= count) {
                    break;
                }
                View view = viewGroup.getChildAt(i3);
                if (view instanceof ViewGroup) {
                    dumpViewHierarchy(context, (ViewGroup) view, out, i + 1, skipChildren, includeProperties);
                } else {
                    dumpView(context2, view, bufferedWriter, i + 1, z);
                }
                if (view.mOverlay != null) {
                    ViewOverlay.OverlayViewGroup overlayViewGroup = view.getOverlay().mOverlayViewGroup;
                    ViewOverlay.OverlayViewGroup overlayViewGroup2 = overlayViewGroup;
                    dumpViewHierarchy(context, overlayViewGroup, out, i + 2, skipChildren, includeProperties);
                }
                i2 = i3 + 1;
            }
            if (viewGroup instanceof HierarchyHandler) {
                ((HierarchyHandler) viewGroup).dumpViewHierarchyWithProperties(bufferedWriter, i + 1);
            }
        }
    }

    private static boolean dumpView(Context context, View view, BufferedWriter out, int level, boolean includeProperties) {
        int i = 0;
        while (i < level) {
            try {
                out.write(32);
                i++;
            } catch (IOException e) {
                Log.w("View", "Error while dumping hierarchy tree");
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
        Class cls = view.getClass();
        do {
            exportFields(context, view, out, cls, prefix);
            exportMethods(context, view, out, cls, prefix);
            cls = cls.getSuperclass();
        } while (cls != Object.class);
    }

    private static Object callMethodOnAppropriateTheadBlocking(final Method method, Object object) throws IllegalAccessException, InvocationTargetException, TimeoutException {
        if (!(object instanceof View)) {
            return method.invoke(object, (Object[]) null);
        }
        final View view = (View) object;
        FutureTask<Object> future = new FutureTask<>(new Callable<Object>() {
            public Object call() throws IllegalAccessException, InvocationTargetException {
                return method.invoke(view, (Object[]) null);
            }
        });
        Handler handler = view.getHandler();
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(future);
        while (true) {
            try {
                return future.get(4000, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                Throwable t = e.getCause();
                if (t instanceof IllegalAccessException) {
                    throw ((IllegalAccessException) t);
                } else if (t instanceof InvocationTargetException) {
                    throw ((InvocationTargetException) t);
                } else {
                    throw new RuntimeException("Unexpected exception", t);
                }
            } catch (InterruptedException e2) {
            } catch (CancellationException e3) {
                throw new RuntimeException("Unexpected cancellation exception", e3);
            }
        }
    }

    private static String formatIntToHexString(int value) {
        return "0x" + Integer.toHexString(value).toUpperCase();
    }

    private static void exportMethods(Context context, Object view, BufferedWriter out, Class<?> klass, String prefix) throws IOException {
        int count;
        Method[] methods;
        String str;
        String categoryPrefix;
        Context context2 = context;
        BufferedWriter bufferedWriter = out;
        String str2 = prefix;
        Method[] methods2 = getExportedPropertyMethods(klass);
        int count2 = methods2.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < count2) {
                Method method = methods2[i2];
                try {
                    Object methodValue = callMethodOnAppropriateTheadBlocking(method, view);
                    Class<?> returnType = method.getReturnType();
                    ExportedProperty property = sAnnotations.get(method);
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
                    String categoryPrefix2 = str;
                    if (returnType == Integer.TYPE) {
                        try {
                            if (!property.resolveId() || context2 == null) {
                                FlagToString[] flagsMapping = property.flagMapping();
                                if (flagsMapping.length > 0) {
                                    exportUnrolledFlags(bufferedWriter, flagsMapping, ((Integer) methodValue).intValue(), categoryPrefix2 + str2 + method.getName() + '_');
                                }
                                IntToString[] mapping = property.mapping();
                                if (mapping.length > 0) {
                                    int intValue = ((Integer) methodValue).intValue();
                                    boolean mapped = false;
                                    FlagToString[] flagToStringArr = flagsMapping;
                                    int mappingCount = mapping.length;
                                    int j = 0;
                                    while (true) {
                                        methods = methods2;
                                        int j2 = j;
                                        if (j2 >= mappingCount) {
                                            int i3 = mappingCount;
                                            break;
                                        }
                                        try {
                                            IntToString mapper = mapping[j2];
                                            int mappingCount2 = mappingCount;
                                            if (mapper.from() == intValue) {
                                                methodValue = mapper.to();
                                                mapped = true;
                                                break;
                                            }
                                            j = j2 + 1;
                                            methods2 = methods;
                                            mappingCount = mappingCount2;
                                        } catch (IllegalAccessException e4) {
                                            count = count2;
                                            i = i2 + 1;
                                            methods2 = methods;
                                            count2 = count;
                                        } catch (InvocationTargetException e5) {
                                            count = count2;
                                            i = i2 + 1;
                                            methods2 = methods;
                                            count2 = count;
                                        } catch (TimeoutException e6) {
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
                                categoryPrefix = categoryPrefix2;
                                count = count2;
                            } else {
                                methodValue = resolveId(context2, ((Integer) methodValue).intValue());
                                methods = methods2;
                                count = count2;
                                categoryPrefix = categoryPrefix2;
                            }
                        } catch (IllegalAccessException e7) {
                            methods = methods2;
                            count = count2;
                        } catch (InvocationTargetException e8) {
                            methods = methods2;
                            count = count2;
                        } catch (TimeoutException e9) {
                            methods = methods2;
                            count = count2;
                        }
                    } else {
                        methods = methods2;
                        if (returnType == int[].class) {
                            try {
                                String str3 = "()";
                                String suffix = categoryPrefix2;
                                count = count2;
                                Class<?> cls = returnType;
                                try {
                                    exportUnrolledArray(context, out, property, (int[]) methodValue, categoryPrefix2 + str2 + method.getName() + '_', "()");
                                } catch (IllegalAccessException | InvocationTargetException | TimeoutException e10) {
                                }
                            } catch (IllegalAccessException e11) {
                                count = count2;
                            } catch (InvocationTargetException e12) {
                                count = count2;
                            } catch (TimeoutException e13) {
                                count = count2;
                            }
                        } else {
                            categoryPrefix = categoryPrefix2;
                            count = count2;
                            Class<?> returnType2 = returnType;
                            if (returnType2 == String[].class) {
                                String[] array = (String[]) methodValue;
                                if (property.hasAdjacentMapping() && array != null) {
                                    for (int j3 = 0; j3 < array.length; j3 += 2) {
                                        if (array[j3] != null) {
                                            writeEntry(bufferedWriter, categoryPrefix + str2, array[j3], "()", array[j3 + 1] == null ? "null" : array[j3 + 1]);
                                        }
                                    }
                                }
                            } else if (!returnType2.isPrimitive() && property.deepExport()) {
                                dumpViewProperties(context2, methodValue, bufferedWriter, str2 + property.prefix());
                            }
                        }
                        i = i2 + 1;
                        methods2 = methods;
                        count2 = count;
                    }
                    writeEntry(bufferedWriter, categoryPrefix + str2, method.getName(), "()", methodValue);
                } catch (IllegalAccessException e14) {
                    methods = methods2;
                    count = count2;
                } catch (InvocationTargetException e15) {
                    methods = methods2;
                    count = count2;
                } catch (TimeoutException e16) {
                    methods = methods2;
                    count = count2;
                }
                i = i2 + 1;
                methods2 = methods;
                count2 = count;
            } else {
                Object obj = view;
                Method[] methodArr = methods2;
                int i4 = count2;
                return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:77:0x01c0 A[Catch:{ IllegalAccessException -> 0x01de }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void exportFields(android.content.Context r20, java.lang.Object r21, java.io.BufferedWriter r22, java.lang.Class<?> r23, java.lang.String r24) throws java.io.IOException {
        /*
            r7 = r20
            r8 = r21
            r9 = r22
            r10 = r24
            java.lang.reflect.Field[] r11 = getExportedPropertyFields(r23)
            int r12 = r11.length
            r0 = 0
        L_0x000e:
            r14 = r0
            if (r14 >= r12) goto L_0x01e9
            r0 = r11[r14]
            r15 = r0
            r0 = 0
            java.lang.Class r1 = r15.getType()     // Catch:{ IllegalAccessException -> 0x01e0 }
            r6 = r1
            java.util.HashMap<java.lang.reflect.AccessibleObject, android.view.ViewDebug$ExportedProperty> r1 = sAnnotations     // Catch:{ IllegalAccessException -> 0x01e0 }
            java.lang.Object r1 = r1.get(r15)     // Catch:{ IllegalAccessException -> 0x01e0 }
            r3 = r1
            android.view.ViewDebug$ExportedProperty r3 = (android.view.ViewDebug.ExportedProperty) r3     // Catch:{ IllegalAccessException -> 0x01e0 }
            java.lang.String r1 = r3.category()     // Catch:{ IllegalAccessException -> 0x01e0 }
            int r1 = r1.length()     // Catch:{ IllegalAccessException -> 0x01e0 }
            if (r1 == 0) goto L_0x0048
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x0043 }
            r1.<init>()     // Catch:{ IllegalAccessException -> 0x0043 }
            java.lang.String r2 = r3.category()     // Catch:{ IllegalAccessException -> 0x0043 }
            r1.append(r2)     // Catch:{ IllegalAccessException -> 0x0043 }
            java.lang.String r2 = ":"
            r1.append(r2)     // Catch:{ IllegalAccessException -> 0x0043 }
            java.lang.String r1 = r1.toString()     // Catch:{ IllegalAccessException -> 0x0043 }
            goto L_0x004a
        L_0x0043:
            r0 = move-exception
            r18 = r11
            goto L_0x01e3
        L_0x0048:
            java.lang.String r1 = ""
        L_0x004a:
            r2 = r1
            java.lang.Class r1 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException -> 0x01e0 }
            r5 = 95
            if (r6 == r1) goto L_0x010c
            java.lang.Class r1 = java.lang.Byte.TYPE     // Catch:{ IllegalAccessException -> 0x01e0 }
            if (r6 != r1) goto L_0x005d
            r17 = r0
            r0 = r2
            r18 = r11
            r11 = r6
            goto L_0x0112
        L_0x005d:
            java.lang.Class<int[]> r1 = int[].class
            if (r6 != r1) goto L_0x0097
            java.lang.Object r1 = r15.get(r8)     // Catch:{ IllegalAccessException -> 0x01e0 }
            r4 = r1
            int[] r4 = (int[]) r4     // Catch:{ IllegalAccessException -> 0x01e0 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x01e0 }
            r1.<init>()     // Catch:{ IllegalAccessException -> 0x01e0 }
            r1.append(r2)     // Catch:{ IllegalAccessException -> 0x01e0 }
            r1.append(r10)     // Catch:{ IllegalAccessException -> 0x01e0 }
            java.lang.String r13 = r15.getName()     // Catch:{ IllegalAccessException -> 0x01e0 }
            r1.append(r13)     // Catch:{ IllegalAccessException -> 0x01e0 }
            r1.append(r5)     // Catch:{ IllegalAccessException -> 0x01e0 }
            java.lang.String r5 = r1.toString()     // Catch:{ IllegalAccessException -> 0x01e0 }
            java.lang.String r1 = ""
            r13 = r1
            java.lang.String r16 = ""
            r1 = r20
            r17 = r0
            r0 = r2
            r2 = r22
            r18 = r11
            r11 = r6
            r6 = r16
            exportUnrolledArray(r1, r2, r3, r4, r5, r6)     // Catch:{ IllegalAccessException -> 0x01de }
            goto L_0x01e3
        L_0x0097:
            r17 = r0
            r0 = r2
            r18 = r11
            r11 = r6
            java.lang.Class<java.lang.String[]> r1 = java.lang.String[].class
            if (r11 != r1) goto L_0x00e0
            java.lang.Object r1 = r15.get(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ IllegalAccessException -> 0x01de }
            boolean r2 = r3.hasAdjacentMapping()     // Catch:{ IllegalAccessException -> 0x01de }
            if (r2 == 0) goto L_0x00de
            if (r1 == 0) goto L_0x00de
            r2 = 0
        L_0x00b0:
            int r4 = r1.length     // Catch:{ IllegalAccessException -> 0x01de }
            if (r2 >= r4) goto L_0x00de
            r4 = r1[r2]     // Catch:{ IllegalAccessException -> 0x01de }
            if (r4 == 0) goto L_0x00db
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x01de }
            r4.<init>()     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r0)     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r10)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r4 = r4.toString()     // Catch:{ IllegalAccessException -> 0x01de }
            r5 = r1[r2]     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r6 = ""
            int r13 = r2 + 1
            r13 = r1[r13]     // Catch:{ IllegalAccessException -> 0x01de }
            if (r13 != 0) goto L_0x00d4
            java.lang.String r13 = "null"
            goto L_0x00d8
        L_0x00d4:
            int r13 = r2 + 1
            r13 = r1[r13]     // Catch:{ IllegalAccessException -> 0x01de }
        L_0x00d8:
            writeEntry(r9, r4, r5, r6, r13)     // Catch:{ IllegalAccessException -> 0x01de }
        L_0x00db:
            int r2 = r2 + 2
            goto L_0x00b0
        L_0x00de:
            goto L_0x01e3
        L_0x00e0:
            boolean r1 = r11.isPrimitive()     // Catch:{ IllegalAccessException -> 0x01de }
            if (r1 != 0) goto L_0x0108
            boolean r1 = r3.deepExport()     // Catch:{ IllegalAccessException -> 0x01de }
            if (r1 == 0) goto L_0x0108
            java.lang.Object r1 = r15.get(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x01de }
            r2.<init>()     // Catch:{ IllegalAccessException -> 0x01de }
            r2.append(r10)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r4 = r3.prefix()     // Catch:{ IllegalAccessException -> 0x01de }
            r2.append(r4)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r2 = r2.toString()     // Catch:{ IllegalAccessException -> 0x01de }
            dumpViewProperties(r7, r1, r9, r2)     // Catch:{ IllegalAccessException -> 0x01de }
            goto L_0x01e3
        L_0x0108:
            r1 = r17
            goto L_0x01be
        L_0x010c:
            r17 = r0
            r0 = r2
            r18 = r11
            r11 = r6
        L_0x0112:
            boolean r1 = r3.resolveId()     // Catch:{ IllegalAccessException -> 0x01de }
            if (r1 == 0) goto L_0x0125
            if (r7 == 0) goto L_0x0125
            int r1 = r15.getInt(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.Object r2 = resolveId(r7, r1)     // Catch:{ IllegalAccessException -> 0x01de }
            r1 = r2
            goto L_0x01be
        L_0x0125:
            android.view.ViewDebug$FlagToString[] r1 = r3.flagMapping()     // Catch:{ IllegalAccessException -> 0x01de }
            int r2 = r1.length     // Catch:{ IllegalAccessException -> 0x01de }
            if (r2 <= 0) goto L_0x014c
            int r2 = r15.getInt(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x01de }
            r4.<init>()     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r0)     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r10)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r6 = r15.getName()     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r6)     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r5)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r4 = r4.toString()     // Catch:{ IllegalAccessException -> 0x01de }
            exportUnrolledFlags(r9, r1, r2, r4)     // Catch:{ IllegalAccessException -> 0x01de }
        L_0x014c:
            android.view.ViewDebug$IntToString[] r2 = r3.mapping()     // Catch:{ IllegalAccessException -> 0x01de }
            int r4 = r2.length     // Catch:{ IllegalAccessException -> 0x01de }
            if (r4 <= 0) goto L_0x017b
            int r4 = r15.getInt(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            int r5 = r2.length     // Catch:{ IllegalAccessException -> 0x01de }
            r6 = 0
        L_0x0159:
            if (r6 >= r5) goto L_0x016f
            r13 = r2[r6]     // Catch:{ IllegalAccessException -> 0x01de }
            r19 = r1
            int r1 = r13.from()     // Catch:{ IllegalAccessException -> 0x01de }
            if (r1 != r4) goto L_0x016a
            java.lang.String r1 = r13.to()     // Catch:{ IllegalAccessException -> 0x01de }
            goto L_0x0173
        L_0x016a:
            int r6 = r6 + 1
            r1 = r19
            goto L_0x0159
        L_0x016f:
            r19 = r1
            r1 = r17
        L_0x0173:
            if (r1 != 0) goto L_0x017f
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)     // Catch:{ IllegalAccessException -> 0x01de }
            r1 = r6
            goto L_0x017f
        L_0x017b:
            r19 = r1
            r1 = r17
        L_0x017f:
            boolean r4 = r3.formatToHexString()     // Catch:{ IllegalAccessException -> 0x01de }
            if (r4 == 0) goto L_0x01bd
            java.lang.Object r4 = r15.get(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            r1 = r4
            java.lang.Class r4 = java.lang.Integer.TYPE     // Catch:{ IllegalAccessException -> 0x01de }
            if (r11 != r4) goto L_0x019b
            r4 = r1
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ IllegalAccessException -> 0x01de }
            int r4 = r4.intValue()     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r4 = formatIntToHexString(r4)     // Catch:{ IllegalAccessException -> 0x01de }
            r1 = r4
            goto L_0x01bd
        L_0x019b:
            java.lang.Class r4 = java.lang.Byte.TYPE     // Catch:{ IllegalAccessException -> 0x01de }
            if (r11 != r4) goto L_0x01bd
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x01de }
            r4.<init>()     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r5 = "0x"
            r4.append(r5)     // Catch:{ IllegalAccessException -> 0x01de }
            r5 = r1
            java.lang.Byte r5 = (java.lang.Byte) r5     // Catch:{ IllegalAccessException -> 0x01de }
            byte r5 = r5.byteValue()     // Catch:{ IllegalAccessException -> 0x01de }
            r6 = 1
            java.lang.String r5 = java.lang.Byte.toHexString(r5, r6)     // Catch:{ IllegalAccessException -> 0x01de }
            r4.append(r5)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r4 = r4.toString()     // Catch:{ IllegalAccessException -> 0x01de }
            r1 = r4
        L_0x01bd:
        L_0x01be:
            if (r1 != 0) goto L_0x01c5
            java.lang.Object r2 = r15.get(r8)     // Catch:{ IllegalAccessException -> 0x01de }
            r1 = r2
        L_0x01c5:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x01de }
            r2.<init>()     // Catch:{ IllegalAccessException -> 0x01de }
            r2.append(r0)     // Catch:{ IllegalAccessException -> 0x01de }
            r2.append(r10)     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r2 = r2.toString()     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r4 = r15.getName()     // Catch:{ IllegalAccessException -> 0x01de }
            java.lang.String r5 = ""
            writeEntry(r9, r2, r4, r5, r1)     // Catch:{ IllegalAccessException -> 0x01de }
            goto L_0x01e3
        L_0x01de:
            r0 = move-exception
            goto L_0x01e3
        L_0x01e0:
            r0 = move-exception
            r18 = r11
        L_0x01e3:
            int r0 = r14 + 1
            r11 = r18
            goto L_0x000e
        L_0x01e9:
            r18 = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewDebug.exportFields(android.content.Context, java.lang.Object, java.io.BufferedWriter, java.lang.Class, java.lang.String):void");
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
                writeEntry(out, prefix, flagMapping.name(), "", formatIntToHexString(maskResult));
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
                return map.to();
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
            boolean test = true;
            if (j >= count) {
                break;
            }
            FlagToString flagMapping = mapping[j];
            boolean ifTrue = flagMapping.outputIf();
            if ((flagMapping.mask() & flags) != flagMapping.equals()) {
                test = false;
            }
            if (test && ifTrue) {
                result.append(flagMapping.name());
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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void exportUnrolledArray(android.content.Context r16, java.io.BufferedWriter r17, android.view.ViewDebug.ExportedProperty r18, int[] r19, java.lang.String r20, java.lang.String r21) throws java.io.IOException {
        /*
            r0 = r16
            r1 = r19
            android.view.ViewDebug$IntToString[] r2 = r18.indexMapping()
            int r3 = r2.length
            r5 = 1
            if (r3 <= 0) goto L_0x000e
            r3 = r5
            goto L_0x000f
        L_0x000e:
            r3 = 0
        L_0x000f:
            android.view.ViewDebug$IntToString[] r6 = r18.mapping()
            int r7 = r6.length
            if (r7 <= 0) goto L_0x0018
            r7 = r5
            goto L_0x0019
        L_0x0018:
            r7 = 0
        L_0x0019:
            boolean r8 = r18.resolveId()
            if (r8 == 0) goto L_0x0022
            if (r0 == 0) goto L_0x0022
            goto L_0x0023
        L_0x0022:
            r5 = 0
        L_0x0023:
            int r8 = r1.length
            r9 = 0
        L_0x0025:
            if (r9 >= r8) goto L_0x0076
            r10 = 0
            r11 = r1[r9]
            java.lang.String r12 = java.lang.String.valueOf(r9)
            if (r3 == 0) goto L_0x0044
            int r13 = r2.length
            r14 = 0
        L_0x0032:
            if (r14 >= r13) goto L_0x0044
            r15 = r2[r14]
            int r4 = r15.from()
            if (r4 != r9) goto L_0x0041
            java.lang.String r12 = r15.to()
            goto L_0x0044
        L_0x0041:
            int r14 = r14 + 1
            goto L_0x0032
        L_0x0044:
            if (r7 == 0) goto L_0x005a
            int r4 = r6.length
            r13 = 0
        L_0x0048:
            if (r13 >= r4) goto L_0x005a
            r14 = r6[r13]
            int r15 = r14.from()
            if (r15 != r11) goto L_0x0057
            java.lang.String r10 = r14.to()
            goto L_0x005a
        L_0x0057:
            int r13 = r13 + 1
            goto L_0x0048
        L_0x005a:
            if (r5 == 0) goto L_0x0066
            if (r10 != 0) goto L_0x006a
            java.lang.Object r4 = resolveId(r0, r11)
            r10 = r4
            java.lang.String r10 = (java.lang.String) r10
            goto L_0x006a
        L_0x0066:
            java.lang.String r10 = java.lang.String.valueOf(r11)
        L_0x006a:
            r4 = r17
            r13 = r20
            r14 = r21
            writeEntry(r4, r13, r12, r14, r10)
            int r9 = r9 + 1
            goto L_0x0025
        L_0x0076:
            r4 = r17
            r13 = r20
            r14 = r21
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.ViewDebug.exportUnrolledArray(android.content.Context, java.io.BufferedWriter, android.view.ViewDebug$ExportedProperty, int[], java.lang.String, java.lang.String):void");
    }

    static Object resolveId(Context context, int id) {
        Resources resources = context.getResources();
        if (id < 0) {
            return "NO_ID";
        }
        try {
            return resources.getResourceTypeName(id) + '/' + resources.getResourceEntryName(id);
        } catch (Resources.NotFoundException e) {
            return "id/" + formatIntToHexString(id);
        }
    }

    private static void writeValue(BufferedWriter out, Object value) throws IOException {
        String output;
        if (value != null) {
            output = "[EXCEPTION]";
            try {
                output = value.toString().replace("\n", "\\n");
            } finally {
                out.write(String.valueOf(output.length()));
                out.write(SmsManager.REGEX_PREFIX_DELIMITER);
                out.write(output);
            }
        } else {
            out.write("4,null");
        }
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
        for (Field field : klass.getFields()) {
            if (field.isAnnotationPresent(CapturedViewProperty.class)) {
                field.setAccessible(true);
                foundFields.add(field);
            }
        }
        Field[] fields2 = (Field[]) foundFields.toArray(new Field[foundFields.size()]);
        map.put(klass, fields2);
        return fields2;
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
        for (Method method : klass.getMethods()) {
            if (method.getParameterTypes().length == 0 && method.isAnnotationPresent(CapturedViewProperty.class) && method.getReturnType() != Void.class) {
                method.setAccessible(true);
                foundMethods.add(method);
            }
        }
        Method[] methods2 = (Method[]) foundMethods.toArray(new Method[foundMethods.size()]);
        map.put(klass, methods2);
        return methods2;
    }

    private static String capturedViewExportMethods(Object obj, Class<?> klass, String prefix) {
        if (obj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (Method method : capturedViewGetPropertyMethods(klass)) {
            try {
                Object methodValue = method.invoke(obj, (Object[]) null);
                Class<?> returnType = method.getReturnType();
                if (((CapturedViewProperty) method.getAnnotation(CapturedViewProperty.class)).retrieveReturn()) {
                    sb.append(capturedViewExportMethods(methodValue, returnType, method.getName() + "#"));
                } else {
                    sb.append(prefix);
                    sb.append(method.getName());
                    sb.append("()=");
                    if (methodValue != null) {
                        sb.append(methodValue.toString().replace("\n", "\\n"));
                    } else {
                        sb.append("null");
                    }
                    sb.append("; ");
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
            }
        }
        return sb.toString();
    }

    private static String capturedViewExportFields(Object obj, Class<?> klass, String prefix) {
        if (obj == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (Field field : capturedViewGetPropertyFields(klass)) {
            try {
                Object fieldValue = field.get(obj);
                sb.append(prefix);
                sb.append(field.getName());
                sb.append("=");
                if (fieldValue != null) {
                    sb.append(fieldValue.toString().replace("\n", "\\n"));
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
        Log.d(tag, sb.toString());
    }

    public static Object invokeViewMethod(View view, Method method, Object[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Object> result = new AtomicReference<>();
        final AtomicReference<Throwable> exception = new AtomicReference<>();
        final Method method2 = method;
        final View view2 = view;
        final Object[] objArr = args;
        view.post(new Runnable() {
            public void run() {
                try {
                    result.set(method2.invoke(view2, objArr));
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
            if (exception.get() == null) {
                return result.get();
            }
            throw new RuntimeException(exception.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLayoutParameter(final View view, String param, int value) throws NoSuchFieldException, IllegalAccessException {
        final ViewGroup.LayoutParams p = view.getLayoutParams();
        Field f = p.getClass().getField(param);
        if (f.getType() == Integer.TYPE) {
            f.set(p, Integer.valueOf(value));
            view.post(new Runnable() {
                public void run() {
                    View.this.setLayoutParams(p);
                }
            });
            return;
        }
        throw new RuntimeException("Only integer layout parameters can be set. Field " + param + " is of type " + f.getType().getSimpleName());
    }

    public static class SoftwareCanvasProvider implements CanvasProvider {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private boolean mEnabledHwBitmapsInSwMode;

        public Canvas getCanvas(View view, int width, int height) {
            this.mBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), width, height, Bitmap.Config.ARGB_8888);
            if (this.mBitmap != null) {
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
            throw new OutOfMemoryError();
        }

        public Bitmap createBitmap() {
            this.mCanvas.setBitmap((Bitmap) null);
            this.mCanvas.setHwBitmapsInSwModeEnabled(this.mEnabledHwBitmapsInSwMode);
            return this.mBitmap;
        }
    }

    public static class HardwareCanvasProvider implements CanvasProvider {
        private Picture mPicture;

        public Canvas getCanvas(View view, int width, int height) {
            this.mPicture = new Picture();
            return this.mPicture.beginRecording(width, height);
        }

        public Bitmap createBitmap() {
            this.mPicture.endRecording();
            return Bitmap.createBitmap(this.mPicture);
        }
    }
}
