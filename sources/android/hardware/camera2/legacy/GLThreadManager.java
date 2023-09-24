package android.hardware.camera2.legacy;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.legacy.RequestThreadManager;
import android.p007os.ConditionVariable;
import android.p007os.Handler;
import android.p007os.Message;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.Collection;

/* loaded from: classes.dex */
public class GLThreadManager {
    private static final boolean DEBUG = false;
    private static final int MSG_ALLOW_FRAMES = 5;
    private static final int MSG_CLEANUP = 3;
    private static final int MSG_DROP_FRAMES = 4;
    private static final int MSG_NEW_CONFIGURATION = 1;
    private static final int MSG_NEW_FRAME = 2;
    private final String TAG;
    private CaptureCollector mCaptureCollector;
    private final CameraDeviceState mDeviceState;
    private final RequestHandlerThread mGLHandlerThread;
    private final SurfaceTextureRenderer mTextureRenderer;
    private final RequestThreadManager.FpsCounter mPrevCounter = new RequestThreadManager.FpsCounter("GL Preview Producer");
    private final Handler.Callback mGLHandlerCb = new Handler.Callback() { // from class: android.hardware.camera2.legacy.GLThreadManager.1
        private boolean mCleanup = false;
        private boolean mConfigured = false;
        private boolean mDroppingFrames = false;

        @Override // android.p007os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (this.mCleanup) {
                return true;
            }
            try {
                int i = msg.what;
                if (i != -1) {
                    switch (i) {
                        case 1:
                            ConfigureHolder configure = (ConfigureHolder) msg.obj;
                            GLThreadManager.this.mTextureRenderer.cleanupEGLContext();
                            GLThreadManager.this.mTextureRenderer.configureSurfaces(configure.surfaces);
                            GLThreadManager.this.mCaptureCollector = (CaptureCollector) Preconditions.checkNotNull(configure.collector);
                            configure.condition.open();
                            this.mConfigured = true;
                            break;
                        case 2:
                            if (this.mDroppingFrames) {
                                Log.m64w(GLThreadManager.this.TAG, "Ignoring frame.");
                                break;
                            } else {
                                if (!this.mConfigured) {
                                    Log.m70e(GLThreadManager.this.TAG, "Dropping frame, EGL context not configured!");
                                }
                                GLThreadManager.this.mTextureRenderer.drawIntoSurfaces(GLThreadManager.this.mCaptureCollector);
                                break;
                            }
                        case 3:
                            GLThreadManager.this.mTextureRenderer.cleanupEGLContext();
                            this.mCleanup = true;
                            this.mConfigured = false;
                            break;
                        case 4:
                            this.mDroppingFrames = true;
                            break;
                        case 5:
                            this.mDroppingFrames = false;
                            break;
                        default:
                            String str = GLThreadManager.this.TAG;
                            Log.m70e(str, "Unhandled message " + msg.what + " on GLThread.");
                            break;
                    }
                }
            } catch (Exception e) {
                Log.m69e(GLThreadManager.this.TAG, "Received exception on GL render thread: ", e);
                GLThreadManager.this.mDeviceState.setError(1);
            }
            return true;
        }
    };

    /* loaded from: classes.dex */
    private static class ConfigureHolder {
        public final CaptureCollector collector;
        public final ConditionVariable condition;
        public final Collection<Pair<Surface, Size>> surfaces;

        public ConfigureHolder(ConditionVariable condition, Collection<Pair<Surface, Size>> surfaces, CaptureCollector collector) {
            this.condition = condition;
            this.surfaces = surfaces;
            this.collector = collector;
        }
    }

    public GLThreadManager(int cameraId, int facing, CameraDeviceState state) {
        this.mTextureRenderer = new SurfaceTextureRenderer(facing);
        this.TAG = String.format("CameraDeviceGLThread-%d", Integer.valueOf(cameraId));
        this.mGLHandlerThread = new RequestHandlerThread(this.TAG, this.mGLHandlerCb);
        this.mDeviceState = state;
    }

    public void start() {
        this.mGLHandlerThread.start();
    }

    public void waitUntilStarted() {
        this.mGLHandlerThread.waitUntilStarted();
    }

    public void quit() {
        Handler handler = this.mGLHandlerThread.getHandler();
        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(3));
        this.mGLHandlerThread.quitSafely();
        try {
            this.mGLHandlerThread.join();
        } catch (InterruptedException e) {
            Log.m70e(this.TAG, String.format("Thread %s (%d) interrupted while quitting.", this.mGLHandlerThread.getName(), Long.valueOf(this.mGLHandlerThread.getId())));
        }
    }

    public void queueNewFrame() {
        Handler handler = this.mGLHandlerThread.getHandler();
        if (!handler.hasMessages(2)) {
            handler.sendMessage(handler.obtainMessage(2));
        } else {
            Log.m70e(this.TAG, "GLThread dropping frame.  Not consuming frames quickly enough!");
        }
    }

    public void setConfigurationAndWait(Collection<Pair<Surface, Size>> surfaces, CaptureCollector collector) {
        Preconditions.checkNotNull(collector, "collector must not be null");
        Handler handler = this.mGLHandlerThread.getHandler();
        ConditionVariable condition = new ConditionVariable(false);
        ConfigureHolder configure = new ConfigureHolder(condition, surfaces, collector);
        Message m = handler.obtainMessage(1, 0, 0, configure);
        handler.sendMessage(m);
        condition.block();
    }

    public SurfaceTexture getCurrentSurfaceTexture() {
        return this.mTextureRenderer.getSurfaceTexture();
    }

    public void ignoreNewFrames() {
        this.mGLHandlerThread.getHandler().sendEmptyMessage(4);
    }

    public void waitUntilIdle() {
        this.mGLHandlerThread.waitUntilIdle();
    }

    public void allowNewFrames() {
        this.mGLHandlerThread.getHandler().sendEmptyMessage(5);
    }
}
