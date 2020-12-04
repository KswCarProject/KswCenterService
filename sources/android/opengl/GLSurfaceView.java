package android.opengl;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceView extends SurfaceView implements SurfaceHolder.Callback2 {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceView";
    /* access modifiers changed from: private */
    public static final GLThreadManager sGLThreadManager = new GLThreadManager();
    /* access modifiers changed from: private */
    public int mDebugFlags;
    private boolean mDetached;
    /* access modifiers changed from: private */
    public EGLConfigChooser mEGLConfigChooser;
    /* access modifiers changed from: private */
    public int mEGLContextClientVersion;
    /* access modifiers changed from: private */
    public EGLContextFactory mEGLContextFactory;
    /* access modifiers changed from: private */
    public EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    @UnsupportedAppUsage
    private GLThread mGLThread;
    /* access modifiers changed from: private */
    public GLWrapper mGLWrapper;
    /* access modifiers changed from: private */
    public boolean mPreserveEGLContextOnPause;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public Renderer mRenderer;
    private final WeakReference<GLSurfaceView> mThisWeakRef = new WeakReference<>(this);

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    public interface Renderer {
        void onDrawFrame(GL10 gl10);

        void onSurfaceChanged(GL10 gl10, int i, int i2);

        void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig);
    }

    public GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public GLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public void setGLWrapper(GLWrapper glWrapper) {
        this.mGLWrapper = glWrapper;
    }

    public void setDebugFlags(int debugFlags) {
        this.mDebugFlags = debugFlags;
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
        this.mPreserveEGLContextOnPause = preserveOnPause;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory factory) {
        checkRenderThreadState();
        this.mEGLContextFactory = factory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = factory;
    }

    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        setEGLConfigChooser((EGLConfigChooser) new SimpleEGLConfigChooser(needDepth));
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        setEGLConfigChooser((EGLConfigChooser) new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setEGLContextClientVersion(int version) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = version;
    }

    public void setRenderMode(int renderMode) {
        this.mGLThread.setRenderMode(renderMode);
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.mGLThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.mGLThread.surfaceDestroyed();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        this.mGLThread.onWindowResize(w, h);
    }

    public void surfaceRedrawNeededAsync(SurfaceHolder holder, Runnable finishDrawing) {
        if (this.mGLThread != null) {
            this.mGLThread.requestRenderAndNotify(finishDrawing);
        }
    }

    @Deprecated
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable r) {
        this.mGLThread.queueEvent(r);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int renderMode = 1;
            if (this.mGLThread != null) {
                renderMode = this.mGLThread.getRenderMode();
            }
            this.mGLThread = new GLThread(this.mThisWeakRef);
            if (renderMode != 1) {
                this.mGLThread.setRenderMode(renderMode);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] iArr;
            int[] attrib_list = {this.EGL_CONTEXT_CLIENT_VERSION, GLSurfaceView.this.mEGLContextClientVersion, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (GLSurfaceView.this.mEGLContextClientVersion != 0) {
                iArr = attrib_list;
            } else {
                iArr = null;
            }
            return egl.eglCreateContext(display, config, eGLContext, iArr);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
                EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
            }
        }
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
            try {
                return egl.eglCreateWindowSurface(display, config, nativeWindow, (int[]) null);
            } catch (IllegalArgumentException e) {
                Log.e(GLSurfaceView.TAG, "eglCreateWindowSurface", e);
                return null;
            }
        }

        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
        }
    }

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        /* access modifiers changed from: package-private */
        public abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public BaseConfigChooser(int[] configSpec) {
            this.mConfigSpec = filterConfigSpec(configSpec);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[1];
            if (egl.eglChooseConfig(display, this.mConfigSpec, (EGLConfig[]) null, 0, num_config)) {
                int numConfigs = num_config[0];
                if (numConfigs > 0) {
                    EGLConfig[] configs = new EGLConfig[numConfigs];
                    if (egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
                        EGLConfig config = chooseConfig(egl, display, configs);
                        if (config != null) {
                            return config;
                        }
                        throw new IllegalArgumentException("No config chosen");
                    }
                    throw new IllegalArgumentException("eglChooseConfig#2 failed");
                }
                throw new IllegalArgumentException("No configs match configSpec");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        private int[] filterConfigSpec(int[] configSpec) {
            if (GLSurfaceView.this.mEGLContextClientVersion != 2 && GLSurfaceView.this.mEGLContextClientVersion != 3) {
                return configSpec;
            }
            int len = configSpec.length;
            int[] newConfigSpec = new int[(len + 2)];
            System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
            newConfigSpec[len - 1] = 12352;
            if (GLSurfaceView.this.mEGLContextClientVersion == 2) {
                newConfigSpec[len] = 4;
            } else {
                newConfigSpec[len] = 64;
            }
            newConfigSpec[len + 1] = 12344;
            return newConfigSpec;
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
            super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
            this.mRedSize = redSize;
            this.mGreenSize = greenSize;
            this.mBlueSize = blueSize;
            this.mAlphaSize = alphaSize;
            this.mDepthSize = depthSize;
            this.mStencilSize = stencilSize;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            for (EGLConfig config : configs) {
                EGL10 egl10 = egl;
                EGLDisplay eGLDisplay = display;
                EGLConfig eGLConfig = config;
                int d = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int s = findConfigAttrib(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (d >= this.mDepthSize && s >= this.mStencilSize) {
                    EGL10 egl102 = egl;
                    EGLDisplay eGLDisplay2 = display;
                    EGLConfig eGLConfig2 = config;
                    int r = findConfigAttrib(egl102, eGLDisplay2, eGLConfig2, 12324, 0);
                    int g = findConfigAttrib(egl102, eGLDisplay2, eGLConfig2, 12323, 0);
                    int b = findConfigAttrib(egl102, eGLDisplay2, eGLConfig2, 12322, 0);
                    int a = findConfigAttrib(egl102, eGLDisplay2, eGLConfig2, 12321, 0);
                    if (r == this.mRedSize && g == this.mGreenSize && b == this.mBlueSize && a == this.mAlphaSize) {
                        return config;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                return this.mValue[0];
            }
            return defaultValue;
        }
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SimpleEGLConfigChooser(boolean withDepthBuffer) {
            super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
        }
    }

    private static class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        @UnsupportedAppUsage
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;

        public EglHelper(WeakReference<GLSurfaceView> glSurfaceViewWeakRef) {
            this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
        }

        public void start() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay != EGL10.EGL_NO_DISPLAY) {
                if (this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                    GLSurfaceView view = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                    if (view == null) {
                        this.mEglConfig = null;
                        this.mEglContext = null;
                    } else {
                        this.mEglConfig = view.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                        this.mEglContext = view.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
                    }
                    if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                        this.mEglContext = null;
                        throwEglException("createContext");
                    }
                    this.mEglSurface = null;
                    return;
                }
                throw new RuntimeException("eglInitialize failed");
            }
            throw new RuntimeException("eglGetDisplay failed");
        }

        public boolean createSurface() {
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.mEglConfig != null) {
                destroySurfaceImp();
                GLSurfaceView view = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    this.mEglSurface = view.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, view.getHolder());
                } else {
                    this.mEglSurface = null;
                }
                if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                    if (this.mEgl.eglGetError() == 12299) {
                        Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    }
                    return false;
                } else if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                    return true;
                } else {
                    logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                    return false;
                }
            } else {
                throw new RuntimeException("mEglConfig not initialized");
            }
        }

        /* access modifiers changed from: package-private */
        public GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLSurfaceView view = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
            if (view == null) {
                return gl;
            }
            if (view.mGLWrapper != null) {
                gl = view.mGLWrapper.wrap(gl);
            }
            if ((view.mDebugFlags & 3) == 0) {
                return gl;
            }
            int configFlags = 0;
            Writer log = null;
            if ((view.mDebugFlags & 1) != 0) {
                configFlags = 0 | 1;
            }
            if ((view.mDebugFlags & 2) != 0) {
                log = new LogWriter();
            }
            return GLDebugHelper.wrap(gl, configFlags, log);
        }

        public int swap() {
            if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return this.mEgl.eglGetError();
            }
            return 12288;
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLSurfaceView view = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    view.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLSurfaceView view = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                if (view != null) {
                    view.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        private void throwEglException(String function) {
            throwEglException(function, this.mEgl.eglGetError());
        }

        public static void throwEglException(String function, int error) {
            throw new RuntimeException(formatEglError(function, error));
        }

        public static void logEglErrorAsWarning(String tag, String function, int error) {
            Log.w(tag, formatEglError(function, error));
        }

        public static String formatEglError(String function, int error) {
            return function + " failed: " + EGLLogWrapper.getErrorString(error);
        }
    }

    static class GLThread extends Thread {
        @UnsupportedAppUsage
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue = new ArrayList<>();
        /* access modifiers changed from: private */
        public boolean mExited;
        private Runnable mFinishDrawingRunnable = null;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight = 0;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode = 1;
        private boolean mRequestPaused;
        private boolean mRequestRender = true;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged = true;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private boolean mWantRenderNotification = false;
        private int mWidth = 0;

        GLThread(WeakReference<GLSurfaceView> glSurfaceViewWeakRef) {
            this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException e) {
            } catch (Throwable th) {
                GLSurfaceView.sGLThreadManager.threadExiting(this);
                throw th;
            }
            GLSurfaceView.sGLThreadManager.threadExiting(this);
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:100:0x016e, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:101:0x016f, code lost:
            r15 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x0175, code lost:
            r14 = android.opengl.GLSurfaceView.access$800();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x0179, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
            r1.mFinishedCreatingEglSurface = true;
            r1.mSurfaceIsBad = true;
            android.opengl.GLSurfaceView.access$800().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x0186, code lost:
            monitor-exit(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x018e, code lost:
            if (r5 == false) goto L_0x019b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x0190, code lost:
            r5 = false;
            r3 = (javax.microedition.khronos.opengles.GL10) r1.mEglHelper.createGL();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x019b, code lost:
            r19 = r5;
            r18 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x01a1, code lost:
            if (r4 == false) goto L_0x01ce;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x01ab, code lost:
            r14 = (android.opengl.GLSurfaceView) r1.mGLSurfaceViewWeakRef.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x01ac, code lost:
            if (r14 == null) goto L_0x01cc;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
            android.os.Trace.traceBegin(8, "onSurfaceCreated");
            android.opengl.GLSurfaceView.access$1000(r14).onSurfaceCreated(r3, r1.mEglHelper.mEglConfig);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
            android.os.Trace.traceEnd(8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:133:0x01cc, code lost:
            r4 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x01ce, code lost:
            if (r7 == false) goto L_0x0200;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x01d8, code lost:
            r5 = (android.opengl.GLSurfaceView) r1.mGLSurfaceViewWeakRef.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x01d9, code lost:
            if (r5 == null) goto L_0x01fb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x01db, code lost:
            r20 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:?, code lost:
            android.os.Trace.traceBegin(8, "onSurfaceChanged");
            android.opengl.GLSurfaceView.access$1000(r5).onSurfaceChanged(r3, r11, r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:?, code lost:
            android.os.Trace.traceEnd(8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:147:0x01fb, code lost:
            r20 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:148:0x01fd, code lost:
            r7 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x0200, code lost:
            r20 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x020a, code lost:
            r5 = (android.opengl.GLSurfaceView) r1.mGLSurfaceViewWeakRef.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:152:0x020b, code lost:
            if (r5 == null) goto L_0x0241;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:153:0x020d, code lost:
            r21 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:155:?, code lost:
            android.os.Trace.traceBegin(8, "onDrawFrame");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x0219, code lost:
            if (r1.mFinishDrawingRunnable == null) goto L_0x0222;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x021b, code lost:
            r2 = r1.mFinishDrawingRunnable;
            r1.mFinishDrawingRunnable = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:159:0x0223, code lost:
            android.opengl.GLSurfaceView.access$1000(r5).onDrawFrame(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:0x022a, code lost:
            if (r2 == null) goto L_0x0230;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x022c, code lost:
            r2.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x022f, code lost:
            r2 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:165:?, code lost:
            android.os.Trace.traceEnd(8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x0241, code lost:
            r21 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:0x0244, code lost:
            r5 = r1.mEglHelper.swap();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:172:0x024c, code lost:
            if (r5 == 12288) goto L_0x0270;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:174:0x0250, code lost:
            if (r5 == 12302) goto L_0x026d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:175:0x0252, code lost:
            android.opengl.GLSurfaceView.EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", r5);
            r6 = android.opengl.GLSurfaceView.access$800();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:176:0x025d, code lost:
            monitor-enter(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:179:?, code lost:
            r1.mSurfaceIsBad = true;
            android.opengl.GLSurfaceView.access$800().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:180:0x0268, code lost:
            monitor-exit(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:186:0x026d, code lost:
            r6 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:187:0x0270, code lost:
            r6 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:188:0x0272, code lost:
            if (r8 == false) goto L_0x0278;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:189:0x0274, code lost:
            r8 = false;
            r9 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:190:0x0278, code lost:
            r5 = r19;
            r7 = r21;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x014d, code lost:
            if (r13 == null) goto L_0x0155;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
            r13.run();
            r13 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:92:0x0155, code lost:
            if (r15 == false) goto L_0x018e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x015d, code lost:
            if (r1.mEglHelper.createSurface() == false) goto L_0x0175;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x015f, code lost:
            r14 = android.opengl.GLSurfaceView.access$800();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x0163, code lost:
            monitor-enter(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
            r1.mFinishedCreatingEglSurface = true;
            android.opengl.GLSurfaceView.access$800().notifyAll();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void guardedRun() throws java.lang.InterruptedException {
            /*
                r22 = this;
                r1 = r22
                android.opengl.GLSurfaceView$EglHelper r0 = new android.opengl.GLSurfaceView$EglHelper
                java.lang.ref.WeakReference<android.opengl.GLSurfaceView> r2 = r1.mGLSurfaceViewWeakRef
                r0.<init>(r2)
                r1.mEglHelper = r0
                r0 = 0
                r1.mHaveEglContext = r0
                r1.mHaveEglSurface = r0
                r1.mWantRenderNotification = r0
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                r7 = 0
                r8 = 0
                r9 = 0
                r10 = 0
                r11 = 0
                r12 = 0
                r13 = 0
                r15 = r4
                r4 = r3
                r3 = r2
                r2 = 0
            L_0x0022:
                android.opengl.GLSurfaceView$GLThreadManager r16 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x02a1 }
                monitor-enter(r16)     // Catch:{ all -> 0x02a1 }
            L_0x0027:
                boolean r14 = r1.mShouldExit     // Catch:{ all -> 0x029e }
                if (r14 == 0) goto L_0x003c
                monitor-exit(r16)     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView$GLThreadManager r14 = android.opengl.GLSurfaceView.sGLThreadManager
                monitor-enter(r14)
                r22.stopEglSurfaceLocked()     // Catch:{ all -> 0x0039 }
                r22.stopEglContextLocked()     // Catch:{ all -> 0x0039 }
                monitor-exit(r14)     // Catch:{ all -> 0x0039 }
                return
            L_0x0039:
                r0 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x0039 }
                throw r0
            L_0x003c:
                java.util.ArrayList<java.lang.Runnable> r14 = r1.mEventQueue     // Catch:{ all -> 0x029e }
                boolean r14 = r14.isEmpty()     // Catch:{ all -> 0x029e }
                if (r14 != 0) goto L_0x0053
                java.util.ArrayList<java.lang.Runnable> r14 = r1.mEventQueue     // Catch:{ all -> 0x029e }
                r0 = 0
                java.lang.Object r14 = r14.remove(r0)     // Catch:{ all -> 0x029e }
                java.lang.Runnable r14 = (java.lang.Runnable) r14     // Catch:{ all -> 0x029e }
                r0 = r14
                r13 = r0
                r0 = 0
                goto L_0x014c
            L_0x0053:
                r0 = 0
                boolean r14 = r1.mPaused     // Catch:{ all -> 0x029e }
                r17 = r0
                boolean r0 = r1.mRequestPaused     // Catch:{ all -> 0x029e }
                if (r14 == r0) goto L_0x006b
                boolean r0 = r1.mRequestPaused     // Catch:{ all -> 0x029e }
                boolean r14 = r1.mRequestPaused     // Catch:{ all -> 0x029e }
                r1.mPaused = r14     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView$GLThreadManager r14 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r14.notifyAll()     // Catch:{ all -> 0x029e }
                r17 = r0
            L_0x006b:
                boolean r0 = r1.mShouldReleaseEglContext     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x0079
                r22.stopEglSurfaceLocked()     // Catch:{ all -> 0x029e }
                r22.stopEglContextLocked()     // Catch:{ all -> 0x029e }
                r0 = 0
                r1.mShouldReleaseEglContext = r0     // Catch:{ all -> 0x029e }
                r10 = 1
            L_0x0079:
                if (r6 == 0) goto L_0x0083
                r22.stopEglSurfaceLocked()     // Catch:{ all -> 0x029e }
                r22.stopEglContextLocked()     // Catch:{ all -> 0x029e }
                r0 = 0
                r6 = r0
            L_0x0083:
                if (r17 == 0) goto L_0x008c
                boolean r0 = r1.mHaveEglSurface     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x008c
                r22.stopEglSurfaceLocked()     // Catch:{ all -> 0x029e }
            L_0x008c:
                if (r17 == 0) goto L_0x00a7
                boolean r0 = r1.mHaveEglContext     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x00a7
                java.lang.ref.WeakReference<android.opengl.GLSurfaceView> r0 = r1.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x029e }
                java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView r0 = (android.opengl.GLSurfaceView) r0     // Catch:{ all -> 0x029e }
                if (r0 != 0) goto L_0x009e
                r14 = 0
                goto L_0x00a2
            L_0x009e:
                boolean r14 = r0.mPreserveEGLContextOnPause     // Catch:{ all -> 0x029e }
            L_0x00a2:
                if (r14 != 0) goto L_0x00a7
                r22.stopEglContextLocked()     // Catch:{ all -> 0x029e }
            L_0x00a7:
                boolean r0 = r1.mHasSurface     // Catch:{ all -> 0x029e }
                if (r0 != 0) goto L_0x00c3
                boolean r0 = r1.mWaitingForSurface     // Catch:{ all -> 0x029e }
                if (r0 != 0) goto L_0x00c3
                boolean r0 = r1.mHaveEglSurface     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x00b6
                r22.stopEglSurfaceLocked()     // Catch:{ all -> 0x029e }
            L_0x00b6:
                r0 = 1
                r1.mWaitingForSurface = r0     // Catch:{ all -> 0x029e }
                r0 = 0
                r1.mSurfaceIsBad = r0     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r0.notifyAll()     // Catch:{ all -> 0x029e }
            L_0x00c3:
                boolean r0 = r1.mHasSurface     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x00d5
                boolean r0 = r1.mWaitingForSurface     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x00d5
                r0 = 0
                r1.mWaitingForSurface = r0     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r0.notifyAll()     // Catch:{ all -> 0x029e }
            L_0x00d5:
                if (r9 == 0) goto L_0x00e5
                r0 = 0
                r1.mWantRenderNotification = r0     // Catch:{ all -> 0x029e }
                r9 = 0
                r0 = 1
                r1.mRenderComplete = r0     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r0.notifyAll()     // Catch:{ all -> 0x029e }
            L_0x00e5:
                java.lang.Runnable r0 = r1.mFinishDrawingRunnable     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x00ef
                java.lang.Runnable r0 = r1.mFinishDrawingRunnable     // Catch:{ all -> 0x029e }
                r2 = r0
                r0 = 0
                r1.mFinishDrawingRunnable = r0     // Catch:{ all -> 0x029e }
            L_0x00ef:
                boolean r0 = r22.readyToDraw()     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x0284
                boolean r0 = r1.mHaveEglContext     // Catch:{ all -> 0x029e }
                if (r0 != 0) goto L_0x0119
                if (r10 == 0) goto L_0x00fe
                r0 = 0
                r10 = r0
                goto L_0x0119
            L_0x00fe:
                android.opengl.GLSurfaceView$EglHelper r0 = r1.mEglHelper     // Catch:{ RuntimeException -> 0x0110 }
                r0.start()     // Catch:{ RuntimeException -> 0x0110 }
                r0 = 1
                r1.mHaveEglContext = r0     // Catch:{ all -> 0x029e }
                r4 = 1
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r0.notifyAll()     // Catch:{ all -> 0x029e }
                goto L_0x0119
            L_0x0110:
                r0 = move-exception
                android.opengl.GLSurfaceView$GLThreadManager r14 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r14.releaseEglContextLocked(r1)     // Catch:{ all -> 0x029e }
                throw r0     // Catch:{ all -> 0x029e }
            L_0x0119:
                boolean r0 = r1.mHaveEglContext     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x0127
                boolean r0 = r1.mHaveEglSurface     // Catch:{ all -> 0x029e }
                if (r0 != 0) goto L_0x0127
                r0 = 1
                r1.mHaveEglSurface = r0     // Catch:{ all -> 0x029e }
                r15 = 1
                r5 = 1
                r7 = 1
            L_0x0127:
                boolean r0 = r1.mHaveEglSurface     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x0293
                boolean r0 = r1.mSizeChanged     // Catch:{ all -> 0x029e }
                if (r0 == 0) goto L_0x013d
                r7 = 1
                int r0 = r1.mWidth     // Catch:{ all -> 0x029e }
                r11 = r0
                int r0 = r1.mHeight     // Catch:{ all -> 0x029e }
                r12 = r0
                r0 = 1
                r1.mWantRenderNotification = r0     // Catch:{ all -> 0x029e }
                r15 = 1
                r0 = 0
                r1.mSizeChanged = r0     // Catch:{ all -> 0x029e }
            L_0x013d:
                r0 = 0
                r1.mRequestRender = r0     // Catch:{ all -> 0x029e }
                android.opengl.GLSurfaceView$GLThreadManager r14 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r14.notifyAll()     // Catch:{ all -> 0x029e }
                boolean r14 = r1.mWantRenderNotification     // Catch:{ all -> 0x029e }
                if (r14 == 0) goto L_0x014c
                r8 = 1
            L_0x014c:
                monitor-exit(r16)     // Catch:{ all -> 0x027e }
                if (r13 == 0) goto L_0x0155
                r13.run()     // Catch:{ all -> 0x02a1 }
                r13 = 0
                goto L_0x0022
            L_0x0155:
                if (r15 == 0) goto L_0x018e
                android.opengl.GLSurfaceView$EglHelper r14 = r1.mEglHelper     // Catch:{ all -> 0x02a1 }
                boolean r14 = r14.createSurface()     // Catch:{ all -> 0x02a1 }
                if (r14 == 0) goto L_0x0175
                android.opengl.GLSurfaceView$GLThreadManager r14 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x02a1 }
                monitor-enter(r14)     // Catch:{ all -> 0x02a1 }
                r0 = 1
                r1.mFinishedCreatingEglSurface = r0     // Catch:{ all -> 0x0172 }
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0172 }
                r0.notifyAll()     // Catch:{ all -> 0x0172 }
                monitor-exit(r14)     // Catch:{ all -> 0x0172 }
                r0 = 0
                r15 = r0
                goto L_0x018e
            L_0x0172:
                r0 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x0172 }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x0175:
                android.opengl.GLSurfaceView$GLThreadManager r14 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x02a1 }
                monitor-enter(r14)     // Catch:{ all -> 0x02a1 }
                r0 = 1
                r1.mFinishedCreatingEglSurface = r0     // Catch:{ all -> 0x018b }
                r1.mSurfaceIsBad = r0     // Catch:{ all -> 0x018b }
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x018b }
                r0.notifyAll()     // Catch:{ all -> 0x018b }
                monitor-exit(r14)     // Catch:{ all -> 0x018b }
            L_0x0188:
                r0 = 0
                goto L_0x0022
            L_0x018b:
                r0 = move-exception
                monitor-exit(r14)     // Catch:{ all -> 0x018b }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x018e:
                if (r5 == 0) goto L_0x019b
                android.opengl.GLSurfaceView$EglHelper r0 = r1.mEglHelper     // Catch:{ all -> 0x02a1 }
                javax.microedition.khronos.opengles.GL r0 = r0.createGL()     // Catch:{ all -> 0x02a1 }
                javax.microedition.khronos.opengles.GL10 r0 = (javax.microedition.khronos.opengles.GL10) r0     // Catch:{ all -> 0x02a1 }
                r3 = 0
                r5 = r3
                r3 = r0
            L_0x019b:
                r19 = r5
                r18 = r6
                r5 = 8
                if (r4 == 0) goto L_0x01ce
                java.lang.ref.WeakReference<android.opengl.GLSurfaceView> r0 = r1.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x02a1 }
                java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x02a1 }
                android.opengl.GLSurfaceView r0 = (android.opengl.GLSurfaceView) r0     // Catch:{ all -> 0x02a1 }
                r14 = r0
                if (r14 == 0) goto L_0x01cc
                java.lang.String r0 = "onSurfaceCreated"
                android.os.Trace.traceBegin(r5, r0)     // Catch:{ all -> 0x01c5 }
                android.opengl.GLSurfaceView$Renderer r0 = r14.mRenderer     // Catch:{ all -> 0x01c5 }
                android.opengl.GLSurfaceView$EglHelper r5 = r1.mEglHelper     // Catch:{ all -> 0x01c5 }
                javax.microedition.khronos.egl.EGLConfig r5 = r5.mEglConfig     // Catch:{ all -> 0x01c5 }
                r0.onSurfaceCreated(r3, r5)     // Catch:{ all -> 0x01c5 }
                r5 = 8
                android.os.Trace.traceEnd(r5)     // Catch:{ all -> 0x02a1 }
                goto L_0x01cc
            L_0x01c5:
                r0 = move-exception
                r5 = 8
                android.os.Trace.traceEnd(r5)     // Catch:{ all -> 0x02a1 }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x01cc:
                r0 = 0
                r4 = r0
            L_0x01ce:
                if (r7 == 0) goto L_0x0200
                java.lang.ref.WeakReference<android.opengl.GLSurfaceView> r0 = r1.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x02a1 }
                java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x02a1 }
                android.opengl.GLSurfaceView r0 = (android.opengl.GLSurfaceView) r0     // Catch:{ all -> 0x02a1 }
                r5 = r0
                if (r5 == 0) goto L_0x01fb
                java.lang.String r0 = "onSurfaceChanged"
                r20 = r7
                r6 = 8
                android.os.Trace.traceBegin(r6, r0)     // Catch:{ all -> 0x01f0 }
                android.opengl.GLSurfaceView$Renderer r0 = r5.mRenderer     // Catch:{ all -> 0x01f0 }
                r0.onSurfaceChanged(r3, r11, r12)     // Catch:{ all -> 0x01f0 }
                android.os.Trace.traceEnd(r6)     // Catch:{ all -> 0x02a1 }
                goto L_0x01fd
            L_0x01f0:
                r0 = move-exception
                goto L_0x01f5
            L_0x01f2:
                r0 = move-exception
                r20 = r7
            L_0x01f5:
                r6 = 8
                android.os.Trace.traceEnd(r6)     // Catch:{ all -> 0x02a1 }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x01fb:
                r20 = r7
            L_0x01fd:
                r0 = 0
                r7 = r0
                goto L_0x0202
            L_0x0200:
                r20 = r7
            L_0x0202:
                java.lang.ref.WeakReference<android.opengl.GLSurfaceView> r0 = r1.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x02a1 }
                java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x02a1 }
                android.opengl.GLSurfaceView r0 = (android.opengl.GLSurfaceView) r0     // Catch:{ all -> 0x02a1 }
                r5 = r0
                if (r5 == 0) goto L_0x0241
                java.lang.String r0 = "onDrawFrame"
                r21 = r7
                r6 = 8
                android.os.Trace.traceBegin(r6, r0)     // Catch:{ all -> 0x0236 }
                java.lang.Runnable r0 = r1.mFinishDrawingRunnable     // Catch:{ all -> 0x0236 }
                if (r0 == 0) goto L_0x0222
                java.lang.Runnable r0 = r1.mFinishDrawingRunnable     // Catch:{ all -> 0x0236 }
                r2 = r0
                r0 = 0
                r1.mFinishDrawingRunnable = r0     // Catch:{ all -> 0x0236 }
                goto L_0x0223
            L_0x0222:
                r0 = 0
            L_0x0223:
                android.opengl.GLSurfaceView$Renderer r6 = r5.mRenderer     // Catch:{ all -> 0x0236 }
                r6.onDrawFrame(r3)     // Catch:{ all -> 0x0236 }
                if (r2 == 0) goto L_0x0230
                r2.run()     // Catch:{ all -> 0x0236 }
                r2 = 0
            L_0x0230:
                r6 = 8
                android.os.Trace.traceEnd(r6)     // Catch:{ all -> 0x02a1 }
                goto L_0x0244
            L_0x0236:
                r0 = move-exception
                goto L_0x023b
            L_0x0238:
                r0 = move-exception
                r21 = r7
            L_0x023b:
                r6 = 8
                android.os.Trace.traceEnd(r6)     // Catch:{ all -> 0x02a1 }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x0241:
                r21 = r7
                r0 = 0
            L_0x0244:
                android.opengl.GLSurfaceView$EglHelper r5 = r1.mEglHelper     // Catch:{ all -> 0x02a1 }
                int r5 = r5.swap()     // Catch:{ all -> 0x02a1 }
                r6 = 12288(0x3000, float:1.7219E-41)
                if (r5 == r6) goto L_0x026f
                r6 = 12302(0x300e, float:1.7239E-41)
                if (r5 == r6) goto L_0x026d
                java.lang.String r6 = "GLThread"
                java.lang.String r7 = "eglSwapBuffers"
                android.opengl.GLSurfaceView.EglHelper.logEglErrorAsWarning(r6, r7, r5)     // Catch:{ all -> 0x02a1 }
                android.opengl.GLSurfaceView$GLThreadManager r6 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x02a1 }
                monitor-enter(r6)     // Catch:{ all -> 0x02a1 }
                r7 = 1
                r1.mSurfaceIsBad = r7     // Catch:{ all -> 0x026a }
                android.opengl.GLSurfaceView$GLThreadManager r7 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x026a }
                r7.notifyAll()     // Catch:{ all -> 0x026a }
                monitor-exit(r6)     // Catch:{ all -> 0x026a }
                goto L_0x0270
            L_0x026a:
                r0 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x026a }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x026d:
                r6 = 1
                goto L_0x0272
            L_0x026f:
            L_0x0270:
                r6 = r18
            L_0x0272:
                if (r8 == 0) goto L_0x0278
                r7 = 1
                r5 = 0
                r8 = r5
                r9 = r7
            L_0x0278:
                r5 = r19
                r7 = r21
                goto L_0x0188
            L_0x027e:
                r0 = move-exception
                r18 = r6
                r20 = r7
                goto L_0x029f
            L_0x0284:
                r0 = 0
                if (r2 == 0) goto L_0x0293
                java.lang.String r14 = "GLSurfaceView"
                java.lang.String r0 = "Warning, !readyToDraw() but waiting for draw finished! Early reporting draw finished."
                android.util.Log.w((java.lang.String) r14, (java.lang.String) r0)     // Catch:{ all -> 0x029e }
                r2.run()     // Catch:{ all -> 0x029e }
                r0 = 0
                r2 = r0
            L_0x0293:
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x029e }
                r0.wait()     // Catch:{ all -> 0x029e }
                r0 = 0
                goto L_0x0027
            L_0x029e:
                r0 = move-exception
            L_0x029f:
                monitor-exit(r16)     // Catch:{ all -> 0x029e }
                throw r0     // Catch:{ all -> 0x02a1 }
            L_0x02a1:
                r0 = move-exception
                android.opengl.GLSurfaceView$GLThreadManager r2 = android.opengl.GLSurfaceView.sGLThreadManager
                monitor-enter(r2)
                r22.stopEglSurfaceLocked()     // Catch:{ all -> 0x02af }
                r22.stopEglContextLocked()     // Catch:{ all -> 0x02af }
                monitor-exit(r2)     // Catch:{ all -> 0x02af }
                throw r0
            L_0x02af:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x02af }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.opengl.GLSurfaceView.GLThread.guardedRun():void");
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        private boolean readyToDraw() {
            return !this.mPaused && this.mHasSurface && !this.mSurfaceIsBad && this.mWidth > 0 && this.mHeight > 0 && (this.mRequestRender || this.mRenderMode == 1);
        }

        public void setRenderMode(int renderMode) {
            if (renderMode < 0 || renderMode > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRenderMode = renderMode;
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public int getRenderMode() {
            int i;
            synchronized (GLSurfaceView.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public void requestRender() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestRender = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void requestRenderAndNotify(Runnable finishDrawing) {
            synchronized (GLSurfaceView.sGLThreadManager) {
                if (Thread.currentThread() != this) {
                    this.mWantRenderNotification = true;
                    this.mRequestRender = true;
                    this.mRenderComplete = false;
                    this.mFinishDrawingRunnable = finishDrawing;
                    GLSurfaceView.sGLThreadManager.notifyAll();
                }
            }
        }

        public void surfaceCreated() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onPause() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0044, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onWindowResize(int r4, int r5) {
            /*
                r3 = this;
                android.opengl.GLSurfaceView$GLThreadManager r0 = android.opengl.GLSurfaceView.sGLThreadManager
                monitor-enter(r0)
                r3.mWidth = r4     // Catch:{ all -> 0x0045 }
                r3.mHeight = r5     // Catch:{ all -> 0x0045 }
                r1 = 1
                r3.mSizeChanged = r1     // Catch:{ all -> 0x0045 }
                r3.mRequestRender = r1     // Catch:{ all -> 0x0045 }
                r1 = 0
                r3.mRenderComplete = r1     // Catch:{ all -> 0x0045 }
                java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0045 }
                if (r1 != r3) goto L_0x0019
                monitor-exit(r0)     // Catch:{ all -> 0x0045 }
                return
            L_0x0019:
                android.opengl.GLSurfaceView$GLThreadManager r1 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0045 }
                r1.notifyAll()     // Catch:{ all -> 0x0045 }
            L_0x0020:
                boolean r1 = r3.mExited     // Catch:{ all -> 0x0045 }
                if (r1 != 0) goto L_0x0043
                boolean r1 = r3.mPaused     // Catch:{ all -> 0x0045 }
                if (r1 != 0) goto L_0x0043
                boolean r1 = r3.mRenderComplete     // Catch:{ all -> 0x0045 }
                if (r1 != 0) goto L_0x0043
                boolean r1 = r3.ableToDraw()     // Catch:{ all -> 0x0045 }
                if (r1 == 0) goto L_0x0043
                android.opengl.GLSurfaceView$GLThreadManager r1 = android.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ InterruptedException -> 0x003a }
                r1.wait()     // Catch:{ InterruptedException -> 0x003a }
            L_0x0039:
                goto L_0x0020
            L_0x003a:
                r1 = move-exception
                java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0045 }
                r2.interrupt()     // Catch:{ all -> 0x0045 }
                goto L_0x0039
            L_0x0043:
                monitor-exit(r0)     // Catch:{ all -> 0x0045 }
                return
            L_0x0045:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0045 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.opengl.GLSurfaceView.GLThread.onWindowResize(int, int):void");
        }

        public void requestExitAndWait() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mShouldExit = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLSurfaceView.sGLThreadManager.notifyAll();
        }

        public void queueEvent(Runnable r) {
            if (r != null) {
                synchronized (GLSurfaceView.sGLThreadManager) {
                    this.mEventQueue.add(r);
                    GLSurfaceView.sGLThreadManager.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("r must not be null");
        }
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() {
        }

        public void close() {
            flushBuilder();
        }

        public void flush() {
            flushBuilder();
        }

        public void write(char[] buf, int offset, int count) {
            for (int i = 0; i < count; i++) {
                char c = buf[offset + i];
                if (c == 10) {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v(GLSurfaceView.TAG, this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";

        private GLThreadManager() {
        }

        public synchronized void threadExiting(GLThread thread) {
            boolean unused = thread.mExited = true;
            notifyAll();
        }

        public void releaseEglContextLocked(GLThread thread) {
            notifyAll();
        }
    }
}
