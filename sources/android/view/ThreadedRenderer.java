package android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.HardwareRenderer;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.p007os.SystemProperties;
import android.p007os.Trace;
import android.util.TimeUtils;
import android.view.Surface;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.android.internal.C3132R;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public final class ThreadedRenderer extends HardwareRenderer {
    public static final String DEBUG_DIRTY_REGIONS_PROPERTY = "debug.hwui.show_dirty_regions";
    public static final String DEBUG_FORCE_DARK = "debug.hwui.force_dark";
    public static final String DEBUG_FPS_DIVISOR = "debug.hwui.fps_divisor";
    public static final String DEBUG_OVERDRAW_PROPERTY = "debug.hwui.overdraw";
    public static final String DEBUG_SHOW_LAYERS_UPDATES_PROPERTY = "debug.hwui.show_layers_updates";
    public static final String DEBUG_SHOW_NON_RECTANGULAR_CLIP_PROPERTY = "debug.hwui.show_non_rect_clip";
    public static final String OVERDRAW_PROPERTY_SHOW = "show";
    static final String PRINT_CONFIG_PROPERTY = "debug.hwui.print_config";
    static final String PROFILE_MAXFRAMES_PROPERTY = "debug.hwui.profile.maxframes";
    public static final String PROFILE_PROPERTY = "debug.hwui.profile";
    public static final String PROFILE_PROPERTY_VISUALIZE_BARS = "visual_bars";
    private static final String[] VISUALIZERS;
    public static boolean sRendererDisabled;
    private static Boolean sSupportsOpenGL;
    public static boolean sSystemRendererDisabled;
    public static boolean sTrimForeground;
    private boolean mEnabled;
    private boolean mHasInsets;
    private int mHeight;
    private int mInsetLeft;
    private int mInsetTop;
    private final float mLightRadius;
    private final float mLightY;
    private final float mLightZ;
    private HardwareRenderer.FrameDrawingCallback mNextRtFrameCallback;
    private boolean mRootNodeNeedsUpdate;
    private int mSurfaceHeight;
    private int mSurfaceWidth;
    private int mWidth;
    public static int EGL_CONTEXT_PRIORITY_HIGH_IMG = 12545;
    public static int EGL_CONTEXT_PRIORITY_MEDIUM_IMG = 12546;
    public static int EGL_CONTEXT_PRIORITY_LOW_IMG = 12547;
    private boolean mInitialized = false;
    private boolean mRequested = true;

    /* loaded from: classes4.dex */
    interface DrawCallbacks {
        void onPostDraw(RecordingCanvas recordingCanvas);

        void onPreDraw(RecordingCanvas recordingCanvas);
    }

    static {
        isAvailable();
        sRendererDisabled = false;
        sSystemRendererDisabled = false;
        sTrimForeground = false;
        VISUALIZERS = new String[]{PROFILE_PROPERTY_VISUALIZE_BARS};
    }

    public static void disable(boolean system) {
        sRendererDisabled = true;
        if (system) {
            sSystemRendererDisabled = true;
        }
    }

    public static void enableForegroundTrimming() {
        sTrimForeground = true;
    }

    public static boolean isAvailable() {
        if (sSupportsOpenGL != null) {
            return sSupportsOpenGL.booleanValue();
        }
        if (SystemProperties.getInt("ro.kernel.qemu", 0) == 0) {
            sSupportsOpenGL = true;
            return true;
        }
        int qemu_gles = SystemProperties.getInt("qemu.gles", -1);
        if (qemu_gles == -1) {
            return false;
        }
        sSupportsOpenGL = Boolean.valueOf(qemu_gles > 0);
        return sSupportsOpenGL.booleanValue();
    }

    public static ThreadedRenderer create(Context context, boolean translucent, String name) {
        if (!isAvailable()) {
            return null;
        }
        ThreadedRenderer renderer = new ThreadedRenderer(context, translucent, name);
        return renderer;
    }

    ThreadedRenderer(Context context, boolean translucent, String name) {
        setName(name);
        setOpaque(!translucent);
        TypedArray a = context.obtainStyledAttributes(null, C3132R.styleable.Lighting, 0, 0);
        this.mLightY = a.getDimension(3, 0.0f);
        this.mLightZ = a.getDimension(4, 0.0f);
        this.mLightRadius = a.getDimension(2, 0.0f);
        float ambientShadowAlpha = a.getFloat(0, 0.0f);
        float spotShadowAlpha = a.getFloat(1, 0.0f);
        a.recycle();
        setLightSourceAlpha(ambientShadowAlpha, spotShadowAlpha);
    }

    @Override // android.graphics.HardwareRenderer
    public void destroy() {
        this.mInitialized = false;
        updateEnabledState(null);
        super.destroy();
    }

    boolean isEnabled() {
        return this.mEnabled;
    }

    void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    boolean isRequested() {
        return this.mRequested;
    }

    void setRequested(boolean requested) {
        this.mRequested = requested;
    }

    private void updateEnabledState(Surface surface) {
        if (surface == null || !surface.isValid()) {
            setEnabled(false);
        } else {
            setEnabled(this.mInitialized);
        }
    }

    boolean initialize(Surface surface) throws Surface.OutOfResourcesException {
        boolean status = !this.mInitialized;
        this.mInitialized = true;
        updateEnabledState(surface);
        setSurface(surface);
        return status;
    }

    boolean initializeIfNeeded(int width, int height, View.AttachInfo attachInfo, Surface surface, Rect surfaceInsets) throws Surface.OutOfResourcesException {
        if (isRequested() && !isEnabled() && initialize(surface)) {
            setup(width, height, attachInfo, surfaceInsets);
            return true;
        }
        return false;
    }

    void updateSurface(Surface surface) throws Surface.OutOfResourcesException {
        updateEnabledState(surface);
        setSurface(surface);
    }

    @Override // android.graphics.HardwareRenderer
    public void setSurface(Surface surface) {
        if (surface != null && surface.isValid()) {
            super.setSurface(surface);
        } else {
            super.setSurface(null);
        }
    }

    void registerRtFrameCallback(HardwareRenderer.FrameDrawingCallback callback) {
        this.mNextRtFrameCallback = callback;
    }

    void destroyHardwareResources(View view) {
        destroyResources(view);
        clearContent();
    }

    private static void destroyResources(View view) {
        view.destroyHardwareResources();
    }

    void setup(int width, int height, View.AttachInfo attachInfo, Rect surfaceInsets) {
        this.mWidth = width;
        this.mHeight = height;
        if (surfaceInsets != null && (surfaceInsets.left != 0 || surfaceInsets.right != 0 || surfaceInsets.top != 0 || surfaceInsets.bottom != 0)) {
            this.mHasInsets = true;
            this.mInsetLeft = surfaceInsets.left;
            this.mInsetTop = surfaceInsets.top;
            this.mSurfaceWidth = this.mInsetLeft + width + surfaceInsets.right;
            this.mSurfaceHeight = this.mInsetTop + height + surfaceInsets.bottom;
            setOpaque(false);
        } else {
            this.mHasInsets = false;
            this.mInsetLeft = 0;
            this.mInsetTop = 0;
            this.mSurfaceWidth = width;
            this.mSurfaceHeight = height;
        }
        this.mRootNode.setLeftTopRightBottom(-this.mInsetLeft, -this.mInsetTop, this.mSurfaceWidth, this.mSurfaceHeight);
        setLightCenter(attachInfo);
    }

    void setLightCenter(View.AttachInfo attachInfo) {
        Point displaySize = attachInfo.mPoint;
        attachInfo.mDisplay.getRealSize(displaySize);
        float lightX = (displaySize.f59x / 2.0f) - attachInfo.mWindowLeft;
        float lightY = this.mLightY - attachInfo.mWindowTop;
        setLightSourceGeometry(lightX, lightY, this.mLightZ, this.mLightRadius);
    }

    int getWidth() {
        return this.mWidth;
    }

    int getHeight() {
        return this.mHeight;
    }

    void dumpGfxInfo(PrintWriter pw, FileDescriptor fd, String[] args) {
        char c;
        pw.flush();
        int flags = (args == null || args.length == 0) ? 1 : 0;
        int flags2 = flags;
        for (String str : args) {
            int hashCode = str.hashCode();
            if (hashCode == -252053678) {
                if (str.equals("framestats")) {
                    c = 0;
                }
                c = '\uffff';
            } else if (hashCode != 1492) {
                if (hashCode == 108404047 && str.equals("reset")) {
                    c = 1;
                }
                c = '\uffff';
            } else {
                if (str.equals("-a")) {
                    c = 2;
                }
                c = '\uffff';
            }
            switch (c) {
                case 0:
                    flags2 |= 1;
                    break;
                case 1:
                    flags2 |= 2;
                    break;
                case 2:
                    flags2 = 1;
                    break;
            }
        }
        dumpProfileInfo(fd, flags2);
    }

    Picture captureRenderingCommands() {
        return null;
    }

    @Override // android.graphics.HardwareRenderer
    public boolean loadSystemProperties() {
        boolean changed = super.loadSystemProperties();
        if (changed) {
            invalidateRoot();
        }
        return changed;
    }

    private void updateViewTreeDisplayList(View view) {
        view.mPrivateFlags |= 32;
        view.mRecreateDisplayList = (view.mPrivateFlags & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        view.mPrivateFlags &= Integer.MAX_VALUE;
        view.updateDisplayListIfDirty();
        view.mRecreateDisplayList = false;
    }

    private void updateRootDisplayList(View view, DrawCallbacks callbacks) {
        Trace.traceBegin(8L, "Record View#draw()");
        updateViewTreeDisplayList(view);
        HardwareRenderer.FrameDrawingCallback callback = this.mNextRtFrameCallback;
        this.mNextRtFrameCallback = null;
        if (callback != null) {
            setFrameCallback(callback);
        }
        if (this.mRootNodeNeedsUpdate || !this.mRootNode.hasDisplayList()) {
            RecordingCanvas canvas = this.mRootNode.beginRecording(this.mSurfaceWidth, this.mSurfaceHeight);
            try {
                int saveCount = canvas.save();
                canvas.translate(this.mInsetLeft, this.mInsetTop);
                callbacks.onPreDraw(canvas);
                canvas.enableZ();
                canvas.drawRenderNode(view.updateDisplayListIfDirty());
                canvas.disableZ();
                callbacks.onPostDraw(canvas);
                canvas.restoreToCount(saveCount);
                this.mRootNodeNeedsUpdate = false;
            } finally {
                this.mRootNode.endRecording();
            }
        }
        Trace.traceEnd(8L);
    }

    void invalidateRoot() {
        this.mRootNodeNeedsUpdate = true;
    }

    void draw(View view, View.AttachInfo attachInfo, DrawCallbacks callbacks) {
        Choreographer choreographer = attachInfo.mViewRootImpl.mChoreographer;
        choreographer.mFrameInfo.markDrawStart();
        updateRootDisplayList(view, callbacks);
        if (attachInfo.mPendingAnimatingRenderNodes != null) {
            int count = attachInfo.mPendingAnimatingRenderNodes.size();
            for (int i = 0; i < count; i++) {
                registerAnimatingRenderNode(attachInfo.mPendingAnimatingRenderNodes.get(i));
            }
            attachInfo.mPendingAnimatingRenderNodes.clear();
            attachInfo.mPendingAnimatingRenderNodes = null;
        }
        int syncResult = syncAndDrawFrame(choreographer.mFrameInfo);
        if ((syncResult & 2) != 0) {
            setEnabled(false);
            attachInfo.mViewRootImpl.mSurface.release();
            attachInfo.mViewRootImpl.invalidate();
        }
        if ((syncResult & 1) != 0) {
            attachInfo.mViewRootImpl.invalidate();
        }
    }

    public RenderNode getRootNode() {
        return this.mRootNode;
    }

    /* loaded from: classes4.dex */
    public static class SimpleRenderer extends HardwareRenderer {
        private final float mLightRadius;
        private final float mLightY;
        private final float mLightZ;

        public SimpleRenderer(Context context, String name, Surface surface) {
            setName(name);
            setOpaque(false);
            setSurface(surface);
            TypedArray a = context.obtainStyledAttributes(null, C3132R.styleable.Lighting, 0, 0);
            this.mLightY = a.getDimension(3, 0.0f);
            this.mLightZ = a.getDimension(4, 0.0f);
            this.mLightRadius = a.getDimension(2, 0.0f);
            float ambientShadowAlpha = a.getFloat(0, 0.0f);
            float spotShadowAlpha = a.getFloat(1, 0.0f);
            a.recycle();
            setLightSourceAlpha(ambientShadowAlpha, spotShadowAlpha);
        }

        public void setLightCenter(Display display, int windowLeft, int windowTop) {
            Point displaySize = new Point();
            display.getRealSize(displaySize);
            float lightX = (displaySize.f59x / 2.0f) - windowLeft;
            float lightY = this.mLightY - windowTop;
            setLightSourceGeometry(lightX, lightY, this.mLightZ, this.mLightRadius);
        }

        public RenderNode getRootNode() {
            return this.mRootNode;
        }

        public void draw(HardwareRenderer.FrameDrawingCallback callback) {
            long vsync = AnimationUtils.currentAnimationTimeMillis() * TimeUtils.NANOS_PER_MS;
            if (callback != null) {
                setFrameCallback(callback);
            }
            createRenderRequest().setVsyncTime(vsync).syncAndDraw();
        }
    }
}
