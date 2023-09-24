package android.media;

import android.graphics.ImageFormat;
import android.hardware.HardwareBuffer;
import android.media.Image;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.view.Surface;
import dalvik.system.VMRuntime;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class ImageReader implements AutoCloseable {
    private static final int ACQUIRE_MAX_IMAGES = 2;
    private static final int ACQUIRE_NO_BUFS = 1;
    private static final int ACQUIRE_SUCCESS = 0;
    private int mEstimatedNativeAllocBytes;
    private final int mFormat;
    private final int mHeight;
    private boolean mIsReaderValid;
    private OnImageAvailableListener mListener;
    private ListenerHandler mListenerHandler;
    private final int mMaxImages;
    private long mNativeContext;
    private final int mNumPlanes;
    private final Surface mSurface;
    private final int mWidth;
    private final Object mListenerLock = new Object();
    private final Object mCloseLock = new Object();
    private List<Image> mAcquiredImages = new CopyOnWriteArrayList();

    /* loaded from: classes3.dex */
    public interface OnImageAvailableListener {
        void onImageAvailable(ImageReader imageReader);
    }

    private static native void nativeClassInit();

    private native synchronized void nativeClose();

    private native synchronized int nativeDetachImage(Image image);

    private native synchronized void nativeDiscardFreeBuffers();

    private native synchronized Surface nativeGetSurface();

    private native synchronized int nativeImageSetup(Image image);

    private native synchronized void nativeInit(Object obj, int i, int i2, int i3, int i4, long j);

    private native synchronized void nativeReleaseImage(Image image);

    public static ImageReader newInstance(int width, int height, int format, int maxImages) {
        return new ImageReader(width, height, format, maxImages, format == 34 ? 0L : 3L);
    }

    public static ImageReader newInstance(int width, int height, int format, int maxImages, long usage) {
        return new ImageReader(width, height, format, maxImages, usage);
    }

    protected ImageReader(int width, int height, int format, int maxImages, long usage) {
        this.mIsReaderValid = false;
        this.mWidth = width;
        this.mHeight = height;
        this.mFormat = format;
        this.mMaxImages = maxImages;
        if (width >= 1 && height >= 1) {
            if (this.mMaxImages < 1) {
                throw new IllegalArgumentException("Maximum outstanding image count must be at least 1");
            }
            if (format != 17) {
                this.mNumPlanes = ImageUtils.getNumPlanesForFormat(this.mFormat);
                nativeInit(new WeakReference(this), width, height, format, maxImages, usage);
                this.mSurface = nativeGetSurface();
                this.mIsReaderValid = true;
                this.mEstimatedNativeAllocBytes = ImageUtils.getEstimatedNativeAllocBytes(width, height, format, 1);
                VMRuntime.getRuntime().registerNativeAllocation(this.mEstimatedNativeAllocBytes);
                return;
            }
            throw new IllegalArgumentException("NV21 format is not supported");
        }
        throw new IllegalArgumentException("The image dimensions must be positive");
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getImageFormat() {
        return this.mFormat;
    }

    public int getMaxImages() {
        return this.mMaxImages;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public Image acquireLatestImage() {
        Image image = acquireNextImage();
        if (image == null) {
            return null;
        }
        while (true) {
            try {
                Image next = acquireNextImageNoThrowISE();
                if (next == null) {
                    break;
                }
                image.close();
                image = next;
            } finally {
                if (image != null) {
                    image.close();
                }
            }
        }
        Image result = image;
        image = null;
        return result;
    }

    public Image acquireNextImageNoThrowISE() {
        SurfaceImage si = new SurfaceImage(this.mFormat);
        if (acquireNextSurfaceImage(si) == 0) {
            return si;
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int acquireNextSurfaceImage(SurfaceImage si) {
        int status;
        synchronized (this.mCloseLock) {
            status = 1;
            if (this.mIsReaderValid) {
                status = nativeImageSetup(si);
            }
            switch (status) {
                case 0:
                    si.mIsImageValid = true;
                    break;
                case 1:
                case 2:
                    break;
                default:
                    throw new AssertionError("Unknown nativeImageSetup return code " + status);
            }
            if (status == 0) {
                this.mAcquiredImages.add(si);
            }
        }
        return status;
    }

    public Image acquireNextImage() {
        SurfaceImage si = new SurfaceImage(this.mFormat);
        int status = acquireNextSurfaceImage(si);
        switch (status) {
            case 0:
                return si;
            case 1:
                return null;
            case 2:
                throw new IllegalStateException(String.format("maxImages (%d) has already been acquired, call #close before acquiring more.", Integer.valueOf(this.mMaxImages)));
            default:
                throw new AssertionError("Unknown nativeImageSetup return code " + status);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseImage(Image i) {
        if (!(i instanceof SurfaceImage)) {
            throw new IllegalArgumentException("This image was not produced by an ImageReader");
        }
        SurfaceImage si = (SurfaceImage) i;
        if (!si.mIsImageValid) {
            return;
        }
        if (si.getReader() != this || !this.mAcquiredImages.contains(i)) {
            throw new IllegalArgumentException("This image was not produced by this ImageReader");
        }
        si.clearSurfacePlanes();
        nativeReleaseImage(i);
        si.mIsImageValid = false;
        this.mAcquiredImages.remove(i);
    }

    public void setOnImageAvailableListener(OnImageAvailableListener listener, Handler handler) {
        synchronized (this.mListenerLock) {
            if (listener != null) {
                Looper looper = handler != null ? handler.getLooper() : Looper.myLooper();
                if (looper == null) {
                    throw new IllegalArgumentException("handler is null but the current thread is not a looper");
                }
                if (this.mListenerHandler == null || this.mListenerHandler.getLooper() != looper) {
                    this.mListenerHandler = new ListenerHandler(looper);
                }
                this.mListener = listener;
            } else {
                this.mListener = null;
                this.mListenerHandler = null;
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        setOnImageAvailableListener(null, null);
        if (this.mSurface != null) {
            this.mSurface.release();
        }
        synchronized (this.mCloseLock) {
            this.mIsReaderValid = false;
            for (Image image : this.mAcquiredImages) {
                image.close();
            }
            this.mAcquiredImages.clear();
            nativeClose();
            if (this.mEstimatedNativeAllocBytes > 0) {
                VMRuntime.getRuntime().registerNativeFree(this.mEstimatedNativeAllocBytes);
                this.mEstimatedNativeAllocBytes = 0;
            }
        }
    }

    public void discardFreeBuffers() {
        synchronized (this.mCloseLock) {
            nativeDiscardFreeBuffers();
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    void detachImage(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("input image must not be null");
        }
        if (!isImageOwnedbyMe(image)) {
            throw new IllegalArgumentException("Trying to detach an image that is not owned by this ImageReader");
        }
        SurfaceImage si = (SurfaceImage) image;
        si.throwISEIfImageIsInvalid();
        if (si.isAttachable()) {
            throw new IllegalStateException("Image was already detached from this ImageReader");
        }
        nativeDetachImage(image);
        si.clearSurfacePlanes();
        si.mPlanes = null;
        si.setDetached(true);
    }

    private boolean isImageOwnedbyMe(Image image) {
        if (image instanceof SurfaceImage) {
            SurfaceImage si = (SurfaceImage) image;
            return si.getReader() == this;
        }
        return false;
    }

    private static void postEventFromNative(Object selfRef) {
        Handler handler;
        WeakReference<ImageReader> weakSelf = (WeakReference) selfRef;
        ImageReader ir = weakSelf.get();
        if (ir == null) {
            return;
        }
        synchronized (ir.mListenerLock) {
            handler = ir.mListenerHandler;
        }
        if (handler != null) {
            handler.sendEmptyMessage(0);
        }
    }

    /* loaded from: classes3.dex */
    private final class ListenerHandler extends Handler {
        public ListenerHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            OnImageAvailableListener listener;
            boolean isReaderValid;
            synchronized (ImageReader.this.mListenerLock) {
                listener = ImageReader.this.mListener;
            }
            synchronized (ImageReader.this.mCloseLock) {
                isReaderValid = ImageReader.this.mIsReaderValid;
            }
            if (listener != null && isReaderValid) {
                listener.onImageAvailable(ImageReader.this);
            }
        }
    }

    /* loaded from: classes3.dex */
    private class SurfaceImage extends Image {
        private int mFormat;
        private AtomicBoolean mIsDetached = new AtomicBoolean(false);
        private long mNativeBuffer;
        private SurfacePlane[] mPlanes;
        private int mScalingMode;
        private long mTimestamp;
        private int mTransform;

        private native synchronized SurfacePlane[] nativeCreatePlanes(int i, int i2);

        private native synchronized int nativeGetFormat(int i);

        private native synchronized HardwareBuffer nativeGetHardwareBuffer();

        private native synchronized int nativeGetHeight();

        private native synchronized int nativeGetWidth();

        public SurfaceImage(int format) {
            this.mFormat = 0;
            this.mFormat = format;
        }

        @Override // android.media.Image, java.lang.AutoCloseable
        public void close() {
            ImageReader.this.releaseImage(this);
        }

        public ImageReader getReader() {
            return ImageReader.this;
        }

        @Override // android.media.Image
        public int getFormat() {
            throwISEIfImageIsInvalid();
            int readerFormat = ImageReader.this.getImageFormat();
            this.mFormat = readerFormat == 34 ? readerFormat : nativeGetFormat(readerFormat);
            return this.mFormat;
        }

        @Override // android.media.Image
        public int getWidth() {
            throwISEIfImageIsInvalid();
            switch (getFormat()) {
                case 36:
                case 256:
                case 257:
                case ImageFormat.HEIC /* 1212500294 */:
                case ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    int width = ImageReader.this.getWidth();
                    return width;
                default:
                    int width2 = nativeGetWidth();
                    return width2;
            }
        }

        @Override // android.media.Image
        public int getHeight() {
            throwISEIfImageIsInvalid();
            switch (getFormat()) {
                case 36:
                case 256:
                case 257:
                case ImageFormat.HEIC /* 1212500294 */:
                case ImageFormat.DEPTH_JPEG /* 1768253795 */:
                    int height = ImageReader.this.getHeight();
                    return height;
                default:
                    int height2 = nativeGetHeight();
                    return height2;
            }
        }

        @Override // android.media.Image
        public long getTimestamp() {
            throwISEIfImageIsInvalid();
            return this.mTimestamp;
        }

        @Override // android.media.Image
        public int getTransform() {
            throwISEIfImageIsInvalid();
            return this.mTransform;
        }

        @Override // android.media.Image
        public int getScalingMode() {
            throwISEIfImageIsInvalid();
            return this.mScalingMode;
        }

        @Override // android.media.Image
        public HardwareBuffer getHardwareBuffer() {
            throwISEIfImageIsInvalid();
            return nativeGetHardwareBuffer();
        }

        @Override // android.media.Image
        public void setTimestamp(long timestampNs) {
            throwISEIfImageIsInvalid();
            this.mTimestamp = timestampNs;
        }

        @Override // android.media.Image
        public Image.Plane[] getPlanes() {
            throwISEIfImageIsInvalid();
            if (this.mPlanes == null) {
                this.mPlanes = nativeCreatePlanes(ImageReader.this.mNumPlanes, ImageReader.this.mFormat);
            }
            return (Image.Plane[]) this.mPlanes.clone();
        }

        protected final void finalize() throws Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        @Override // android.media.Image
        boolean isAttachable() {
            throwISEIfImageIsInvalid();
            return this.mIsDetached.get();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // android.media.Image
        public ImageReader getOwner() {
            throwISEIfImageIsInvalid();
            return ImageReader.this;
        }

        @Override // android.media.Image
        long getNativeContext() {
            throwISEIfImageIsInvalid();
            return this.mNativeBuffer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDetached(boolean detached) {
            throwISEIfImageIsInvalid();
            this.mIsDetached.getAndSet(detached);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSurfacePlanes() {
            if (this.mIsImageValid && this.mPlanes != null) {
                for (int i = 0; i < this.mPlanes.length; i++) {
                    if (this.mPlanes[i] != null) {
                        this.mPlanes[i].clearBuffer();
                        this.mPlanes[i] = null;
                    }
                }
            }
        }

        /* loaded from: classes3.dex */
        private class SurfacePlane extends Image.Plane {
            private ByteBuffer mBuffer;
            private final int mPixelStride;
            private final int mRowStride;

            private SurfacePlane(int rowStride, int pixelStride, ByteBuffer buffer) {
                this.mRowStride = rowStride;
                this.mPixelStride = pixelStride;
                this.mBuffer = buffer;
                this.mBuffer.order(ByteOrder.nativeOrder());
            }

            @Override // android.media.Image.Plane
            public ByteBuffer getBuffer() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                return this.mBuffer;
            }

            @Override // android.media.Image.Plane
            public int getPixelStride() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                if (ImageReader.this.mFormat == 36) {
                    throw new UnsupportedOperationException("getPixelStride is not supported for RAW_PRIVATE plane");
                }
                return this.mPixelStride;
            }

            @Override // android.media.Image.Plane
            public int getRowStride() {
                SurfaceImage.this.throwISEIfImageIsInvalid();
                if (ImageReader.this.mFormat == 36) {
                    throw new UnsupportedOperationException("getRowStride is not supported for RAW_PRIVATE plane");
                }
                return this.mRowStride;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void clearBuffer() {
                if (this.mBuffer == null) {
                    return;
                }
                if (this.mBuffer.isDirect()) {
                    NioUtils.freeDirectBuffer(this.mBuffer);
                }
                this.mBuffer = null;
            }
        }
    }

    static {
        System.loadLibrary("media_jni");
        nativeClassInit();
    }
}
