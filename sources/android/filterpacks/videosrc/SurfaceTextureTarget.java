package android.filterpacks.videosrc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import android.graphics.SurfaceTexture;
import android.util.Log;

public class SurfaceTextureTarget extends Filter {
    private static final String TAG = "SurfaceTextureTarget";
    private final int RENDERMODE_CUSTOMIZE = 3;
    private final int RENDERMODE_FILL_CROP = 2;
    private final int RENDERMODE_FIT = 1;
    private final int RENDERMODE_STRETCH = 0;
    private float mAspectRatio = 1.0f;
    private boolean mLogVerbose = Log.isLoggable(TAG, 2);
    private ShaderProgram mProgram;
    private int mRenderMode = 1;
    @GenerateFieldPort(hasDefault = true, name = "renderMode")
    private String mRenderModeString;
    private GLFrame mScreen;
    @GenerateFinalPort(name = "height")
    private int mScreenHeight;
    @GenerateFinalPort(name = "width")
    private int mScreenWidth;
    @GenerateFieldPort(hasDefault = true, name = "sourceQuad")
    private Quad mSourceQuad = new Quad(new Point(0.0f, 1.0f), new Point(1.0f, 1.0f), new Point(0.0f, 0.0f), new Point(1.0f, 0.0f));
    private int mSurfaceId;
    @GenerateFinalPort(name = "surfaceTexture")
    private SurfaceTexture mSurfaceTexture;
    @GenerateFieldPort(hasDefault = true, name = "targetQuad")
    private Quad mTargetQuad = new Quad(new Point(0.0f, 0.0f), new Point(1.0f, 0.0f), new Point(0.0f, 1.0f), new Point(1.0f, 1.0f));

    public SurfaceTextureTarget(String name) {
        super(name);
    }

    public synchronized void setupPorts() {
        if (this.mSurfaceTexture != null) {
            addMaskedInputPort("frame", ImageFormat.create(3));
        } else {
            throw new RuntimeException("Null SurfaceTexture passed to SurfaceTextureTarget");
        }
    }

    public void updateRenderMode() {
        if (this.mLogVerbose) {
            Log.v(TAG, "updateRenderMode. Thread: " + Thread.currentThread());
        }
        if (this.mRenderModeString != null) {
            if (this.mRenderModeString.equals("stretch")) {
                this.mRenderMode = 0;
            } else if (this.mRenderModeString.equals("fit")) {
                this.mRenderMode = 1;
            } else if (this.mRenderModeString.equals("fill_crop")) {
                this.mRenderMode = 2;
            } else if (this.mRenderModeString.equals("customize")) {
                this.mRenderMode = 3;
            } else {
                throw new RuntimeException("Unknown render mode '" + this.mRenderModeString + "'!");
            }
        }
        updateTargetRect();
    }

    public void prepare(FilterContext context) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Prepare. Thread: " + Thread.currentThread());
        }
        this.mProgram = ShaderProgram.createIdentity(context);
        this.mProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        updateRenderMode();
        MutableFrameFormat screenFormat = new MutableFrameFormat(2, 3);
        screenFormat.setBytesPerSample(4);
        screenFormat.setDimensions(this.mScreenWidth, this.mScreenHeight);
        this.mScreen = (GLFrame) context.getFrameManager().newBoundFrame(screenFormat, 101, 0);
    }

    public synchronized void open(FilterContext context) {
        if (this.mSurfaceTexture != null) {
            this.mSurfaceId = context.getGLEnvironment().registerSurfaceTexture(this.mSurfaceTexture, this.mScreenWidth, this.mScreenHeight);
            if (this.mSurfaceId <= 0) {
                throw new RuntimeException("Could not register SurfaceTexture: " + this.mSurfaceTexture);
            }
        } else {
            Log.e(TAG, "SurfaceTexture is null!!");
            throw new RuntimeException("Could not register SurfaceTexture: " + this.mSurfaceTexture);
        }
    }

    public synchronized void close(FilterContext context) {
        if (this.mSurfaceId > 0) {
            context.getGLEnvironment().unregisterSurfaceId(this.mSurfaceId);
            this.mSurfaceId = -1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void disconnect(android.filterfw.core.FilterContext r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.mLogVerbose     // Catch:{ all -> 0x002e }
            if (r0 == 0) goto L_0x000c
            java.lang.String r0 = "SurfaceTextureTarget"
            java.lang.String r1 = "disconnect"
            android.util.Log.v(r0, r1)     // Catch:{ all -> 0x002e }
        L_0x000c:
            android.graphics.SurfaceTexture r0 = r2.mSurfaceTexture     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x0019
            java.lang.String r0 = "SurfaceTextureTarget"
            java.lang.String r1 = "SurfaceTexture is already null. Nothing to disconnect."
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x002e }
            monitor-exit(r2)
            return
        L_0x0019:
            r0 = 0
            r2.mSurfaceTexture = r0     // Catch:{ all -> 0x002e }
            int r0 = r2.mSurfaceId     // Catch:{ all -> 0x002e }
            if (r0 <= 0) goto L_0x002c
            android.filterfw.core.GLEnvironment r0 = r3.getGLEnvironment()     // Catch:{ all -> 0x002e }
            int r1 = r2.mSurfaceId     // Catch:{ all -> 0x002e }
            r0.unregisterSurfaceId(r1)     // Catch:{ all -> 0x002e }
            r0 = -1
            r2.mSurfaceId = r0     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r2)
            return
        L_0x002e:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.filterpacks.videosrc.SurfaceTextureTarget.disconnect(android.filterfw.core.FilterContext):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0095, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void process(android.filterfw.core.FilterContext r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            int r0 = r8.mSurfaceId     // Catch:{ all -> 0x0096 }
            if (r0 > 0) goto L_0x0007
            monitor-exit(r8)
            return
        L_0x0007:
            android.filterfw.core.GLEnvironment r0 = r9.getGLEnvironment()     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = "frame"
            android.filterfw.core.Frame r1 = r8.pullInput(r1)     // Catch:{ all -> 0x0096 }
            r2 = 0
            android.filterfw.core.FrameFormat r3 = r1.getFormat()     // Catch:{ all -> 0x0096 }
            int r3 = r3.getWidth()     // Catch:{ all -> 0x0096 }
            float r3 = (float) r3     // Catch:{ all -> 0x0096 }
            android.filterfw.core.FrameFormat r4 = r1.getFormat()     // Catch:{ all -> 0x0096 }
            int r4 = r4.getHeight()     // Catch:{ all -> 0x0096 }
            float r4 = (float) r4     // Catch:{ all -> 0x0096 }
            float r3 = r3 / r4
            float r4 = r8.mAspectRatio     // Catch:{ all -> 0x0096 }
            int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r4 == 0) goto L_0x0061
            boolean r4 = r8.mLogVerbose     // Catch:{ all -> 0x0096 }
            if (r4 == 0) goto L_0x005c
            java.lang.String r4 = "SurfaceTextureTarget"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            r5.<init>()     // Catch:{ all -> 0x0096 }
            java.lang.String r6 = "Process. New aspect ratio: "
            r5.append(r6)     // Catch:{ all -> 0x0096 }
            r5.append(r3)     // Catch:{ all -> 0x0096 }
            java.lang.String r6 = ", previously: "
            r5.append(r6)     // Catch:{ all -> 0x0096 }
            float r6 = r8.mAspectRatio     // Catch:{ all -> 0x0096 }
            r5.append(r6)     // Catch:{ all -> 0x0096 }
            java.lang.String r6 = ". Thread: "
            r5.append(r6)     // Catch:{ all -> 0x0096 }
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0096 }
            r5.append(r6)     // Catch:{ all -> 0x0096 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0096 }
            android.util.Log.v(r4, r5)     // Catch:{ all -> 0x0096 }
        L_0x005c:
            r8.mAspectRatio = r3     // Catch:{ all -> 0x0096 }
            r8.updateTargetRect()     // Catch:{ all -> 0x0096 }
        L_0x0061:
            r4 = 0
            android.filterfw.core.FrameFormat r5 = r1.getFormat()     // Catch:{ all -> 0x0096 }
            int r5 = r5.getTarget()     // Catch:{ all -> 0x0096 }
            r6 = 3
            if (r5 == r6) goto L_0x0078
            android.filterfw.core.FrameManager r7 = r9.getFrameManager()     // Catch:{ all -> 0x0096 }
            android.filterfw.core.Frame r6 = r7.duplicateFrameToTarget(r1, r6)     // Catch:{ all -> 0x0096 }
            r4 = r6
            r2 = 1
            goto L_0x0079
        L_0x0078:
            r4 = r1
        L_0x0079:
            int r6 = r8.mSurfaceId     // Catch:{ all -> 0x0096 }
            r0.activateSurfaceWithId(r6)     // Catch:{ all -> 0x0096 }
            android.filterfw.core.ShaderProgram r6 = r8.mProgram     // Catch:{ all -> 0x0096 }
            android.filterfw.core.GLFrame r7 = r8.mScreen     // Catch:{ all -> 0x0096 }
            r6.process((android.filterfw.core.Frame) r4, (android.filterfw.core.Frame) r7)     // Catch:{ all -> 0x0096 }
            long r6 = r1.getTimestamp()     // Catch:{ all -> 0x0096 }
            r0.setSurfaceTimestamp(r6)     // Catch:{ all -> 0x0096 }
            r0.swapBuffers()     // Catch:{ all -> 0x0096 }
            if (r2 == 0) goto L_0x0094
            r4.release()     // Catch:{ all -> 0x0096 }
        L_0x0094:
            monitor-exit(r8)
            return
        L_0x0096:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.filterpacks.videosrc.SurfaceTextureTarget.process(android.filterfw.core.FilterContext):void");
    }

    public void fieldPortValueUpdated(String name, FilterContext context) {
        if (this.mLogVerbose) {
            Log.v(TAG, "FPVU. Thread: " + Thread.currentThread());
        }
        updateRenderMode();
    }

    public void tearDown(FilterContext context) {
        if (this.mScreen != null) {
            this.mScreen.release();
        }
    }

    private void updateTargetRect() {
        if (this.mLogVerbose) {
            Log.v(TAG, "updateTargetRect. Thread: " + Thread.currentThread());
        }
        if (this.mScreenWidth > 0 && this.mScreenHeight > 0 && this.mProgram != null) {
            float screenAspectRatio = ((float) this.mScreenWidth) / ((float) this.mScreenHeight);
            float relativeAspectRatio = screenAspectRatio / this.mAspectRatio;
            if (this.mLogVerbose) {
                Log.v(TAG, "UTR. screen w = " + ((float) this.mScreenWidth) + " x screen h = " + ((float) this.mScreenHeight) + " Screen AR: " + screenAspectRatio + ", frame AR: " + this.mAspectRatio + ", relative AR: " + relativeAspectRatio);
            }
            if (relativeAspectRatio != 1.0f || this.mRenderMode == 3) {
                switch (this.mRenderMode) {
                    case 0:
                        this.mTargetQuad.p0.set(0.0f, 0.0f);
                        this.mTargetQuad.p1.set(1.0f, 0.0f);
                        this.mTargetQuad.p2.set(0.0f, 1.0f);
                        this.mTargetQuad.p3.set(1.0f, 1.0f);
                        this.mProgram.setClearsOutput(false);
                        break;
                    case 1:
                        if (relativeAspectRatio > 1.0f) {
                            this.mTargetQuad.p0.set(0.5f - (0.5f / relativeAspectRatio), 0.0f);
                            this.mTargetQuad.p1.set((0.5f / relativeAspectRatio) + 0.5f, 0.0f);
                            this.mTargetQuad.p2.set(0.5f - (0.5f / relativeAspectRatio), 1.0f);
                            this.mTargetQuad.p3.set((0.5f / relativeAspectRatio) + 0.5f, 1.0f);
                        } else {
                            this.mTargetQuad.p0.set(0.0f, 0.5f - (relativeAspectRatio * 0.5f));
                            this.mTargetQuad.p1.set(1.0f, 0.5f - (relativeAspectRatio * 0.5f));
                            this.mTargetQuad.p2.set(0.0f, (relativeAspectRatio * 0.5f) + 0.5f);
                            this.mTargetQuad.p3.set(1.0f, (relativeAspectRatio * 0.5f) + 0.5f);
                        }
                        this.mProgram.setClearsOutput(true);
                        break;
                    case 2:
                        if (relativeAspectRatio > 1.0f) {
                            this.mTargetQuad.p0.set(0.0f, 0.5f - (relativeAspectRatio * 0.5f));
                            this.mTargetQuad.p1.set(1.0f, 0.5f - (relativeAspectRatio * 0.5f));
                            this.mTargetQuad.p2.set(0.0f, (relativeAspectRatio * 0.5f) + 0.5f);
                            this.mTargetQuad.p3.set(1.0f, (relativeAspectRatio * 0.5f) + 0.5f);
                        } else {
                            this.mTargetQuad.p0.set(0.5f - (0.5f / relativeAspectRatio), 0.0f);
                            this.mTargetQuad.p1.set((0.5f / relativeAspectRatio) + 0.5f, 0.0f);
                            this.mTargetQuad.p2.set(0.5f - (0.5f / relativeAspectRatio), 1.0f);
                            this.mTargetQuad.p3.set((0.5f / relativeAspectRatio) + 0.5f, 1.0f);
                        }
                        this.mProgram.setClearsOutput(true);
                        break;
                    case 3:
                        this.mProgram.setSourceRegion(this.mSourceQuad);
                        break;
                }
                if (this.mLogVerbose) {
                    Log.v(TAG, "UTR. quad: " + this.mTargetQuad);
                }
                this.mProgram.setTargetRegion(this.mTargetQuad);
                return;
            }
            this.mProgram.setTargetRect(0.0f, 0.0f, 1.0f, 1.0f);
            this.mProgram.setClearsOutput(false);
        }
    }
}
