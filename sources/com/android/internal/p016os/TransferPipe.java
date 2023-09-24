package com.android.internal.p016os;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.p007os.SystemClock;
import android.util.Slog;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import libcore.io.IoUtils;

/* renamed from: com.android.internal.os.TransferPipe */
/* loaded from: classes4.dex */
public class TransferPipe implements Runnable, Closeable {
    static final boolean DEBUG = false;
    static final long DEFAULT_TIMEOUT = 5000;
    static final String TAG = "TransferPipe";
    String mBufferPrefix;
    boolean mComplete;
    long mEndTime;
    String mFailure;
    final ParcelFileDescriptor[] mFds;
    FileDescriptor mOutFd;
    final Thread mThread;

    /* renamed from: com.android.internal.os.TransferPipe$Caller */
    /* loaded from: classes4.dex */
    interface Caller {
        /* renamed from: go */
        void m29go(IInterface iInterface, FileDescriptor fileDescriptor, String str, String[] strArr) throws RemoteException;
    }

    public TransferPipe() throws IOException {
        this(null);
    }

    public TransferPipe(String bufferPrefix) throws IOException {
        this(bufferPrefix, TAG);
    }

    protected TransferPipe(String bufferPrefix, String threadName) throws IOException {
        this.mThread = new Thread(this, threadName);
        this.mFds = ParcelFileDescriptor.createPipe();
        this.mBufferPrefix = bufferPrefix;
    }

    ParcelFileDescriptor getReadFd() {
        return this.mFds[0];
    }

    public ParcelFileDescriptor getWriteFd() {
        return this.mFds[1];
    }

    public void setBufferPrefix(String prefix) {
        this.mBufferPrefix = prefix;
    }

    public static void dumpAsync(IBinder binder, FileDescriptor out, String[] args) throws IOException, RemoteException {
        goDump(binder, out, args);
    }

    public static byte[] dumpAsync(IBinder binder, String... args) throws IOException, RemoteException {
        ParcelFileDescriptor[] pipe = ParcelFileDescriptor.createPipe();
        try {
            dumpAsync(binder, pipe[1].getFileDescriptor(), args);
            pipe[1].close();
            pipe[1] = null;
            byte[] buffer = new byte[4096];
            ByteArrayOutputStream combinedBuffer = new ByteArrayOutputStream();
            FileInputStream is = new FileInputStream(pipe[0].getFileDescriptor());
            while (true) {
                int numRead = is.read(buffer);
                if (numRead != -1) {
                    combinedBuffer.write(buffer, 0, numRead);
                } else {
                    $closeResource(null, is);
                    byte[] byteArray = combinedBuffer.toByteArray();
                    $closeResource(null, combinedBuffer);
                    return byteArray;
                }
            }
        } finally {
            pipe[0].close();
            IoUtils.closeQuietly(pipe[1]);
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    /* renamed from: go */
    static void m33go(Caller caller, IInterface iface, FileDescriptor out, String prefix, String[] args) throws IOException, RemoteException {
        m32go(caller, iface, out, prefix, args, 5000L);
    }

    /* renamed from: go */
    static void m32go(Caller caller, IInterface iface, FileDescriptor out, String prefix, String[] args, long timeout) throws IOException, RemoteException {
        if (iface.asBinder() instanceof Binder) {
            try {
                caller.m29go(iface, out, prefix, args);
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        TransferPipe tp = new TransferPipe();
        try {
            caller.m29go(iface, tp.getWriteFd().getFileDescriptor(), prefix, args);
            tp.m30go(out, timeout);
            $closeResource(null, tp);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                $closeResource(th, tp);
                throw th2;
            }
        }
    }

    static void goDump(IBinder binder, FileDescriptor out, String[] args) throws IOException, RemoteException {
        goDump(binder, out, args, 5000L);
    }

    static void goDump(IBinder binder, FileDescriptor out, String[] args, long timeout) throws IOException, RemoteException {
        if (binder instanceof Binder) {
            try {
                binder.dump(out, args);
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        TransferPipe tp = new TransferPipe();
        try {
            binder.dumpAsync(tp.getWriteFd().getFileDescriptor(), args);
            tp.m30go(out, timeout);
            $closeResource(null, tp);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                $closeResource(th, tp);
                throw th2;
            }
        }
    }

    /* renamed from: go */
    public void m31go(FileDescriptor out) throws IOException {
        m30go(out, 5000L);
    }

    /* renamed from: go */
    public void m30go(FileDescriptor out, long timeout) throws IOException {
        try {
            synchronized (this) {
                this.mOutFd = out;
                this.mEndTime = SystemClock.uptimeMillis() + timeout;
                closeFd(1);
                this.mThread.start();
                while (this.mFailure == null && !this.mComplete) {
                    long waitTime = this.mEndTime - SystemClock.uptimeMillis();
                    if (waitTime <= 0) {
                        this.mThread.interrupt();
                        throw new IOException("Timeout");
                    }
                    try {
                        wait(waitTime);
                    } catch (InterruptedException e) {
                    }
                }
                if (this.mFailure != null) {
                    throw new IOException(this.mFailure);
                }
            }
        } finally {
            kill();
        }
    }

    void closeFd(int num) {
        if (this.mFds[num] != null) {
            try {
                this.mFds[num].close();
            } catch (IOException e) {
            }
            this.mFds[num] = null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        kill();
    }

    public void kill() {
        synchronized (this) {
            closeFd(0);
            closeFd(1);
        }
    }

    protected OutputStream getNewOutputStream() {
        return new FileOutputStream(this.mOutFd);
    }

    @Override // java.lang.Runnable
    public void run() {
        byte[] buffer = new byte[1024];
        synchronized (this) {
            ParcelFileDescriptor readFd = getReadFd();
            if (readFd == null) {
                Slog.m50w(TAG, "Pipe has been closed...");
                return;
            }
            FileInputStream fis = new FileInputStream(readFd.getFileDescriptor());
            OutputStream fos = getNewOutputStream();
            byte[] bufferPrefix = null;
            boolean needPrefix = true;
            if (this.mBufferPrefix != null) {
                bufferPrefix = this.mBufferPrefix.getBytes();
            }
            while (true) {
                try {
                    int size = fis.read(buffer);
                    if (size > 0) {
                        int i = 0;
                        if (bufferPrefix == null) {
                            fos.write(buffer, 0, size);
                        } else {
                            int start = 0;
                            while (i < size) {
                                if (buffer[i] != 10) {
                                    if (i > start) {
                                        fos.write(buffer, start, i - start);
                                    }
                                    start = i;
                                    if (needPrefix) {
                                        fos.write(bufferPrefix);
                                        needPrefix = false;
                                    }
                                    do {
                                        i++;
                                        if (i >= size) {
                                            break;
                                        }
                                    } while (buffer[i] != 10);
                                    if (i < size) {
                                        needPrefix = true;
                                    }
                                }
                                i++;
                            }
                            if (size > start) {
                                fos.write(buffer, start, size - start);
                            }
                        }
                    } else {
                        this.mThread.isInterrupted();
                        synchronized (this) {
                            this.mComplete = true;
                            notifyAll();
                        }
                        return;
                    }
                } catch (IOException e) {
                    synchronized (this) {
                        this.mFailure = e.toString();
                        notifyAll();
                        return;
                    }
                }
            }
        }
    }
}
