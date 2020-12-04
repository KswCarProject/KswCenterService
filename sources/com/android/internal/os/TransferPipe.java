package com.android.internal.os;

import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.SystemClock;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    interface Caller {
        void go(IInterface iInterface, FileDescriptor fileDescriptor, String str, String[] strArr) throws RemoteException;
    }

    public TransferPipe() throws IOException {
        this((String) null);
    }

    public TransferPipe(String bufferPrefix) throws IOException {
        this(bufferPrefix, TAG);
    }

    protected TransferPipe(String bufferPrefix, String threadName) throws IOException {
        this.mThread = new Thread(this, threadName);
        this.mFds = ParcelFileDescriptor.createPipe();
        this.mBufferPrefix = bufferPrefix;
    }

    /* access modifiers changed from: package-private */
    public ParcelFileDescriptor getReadFd() {
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

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004c, code lost:
        r7 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004d, code lost:
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0052, code lost:
        r9 = r8;
        r8 = r7;
        r7 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] dumpAsync(android.os.IBinder r10, java.lang.String... r11) throws java.io.IOException, android.os.RemoteException {
        /*
            android.os.ParcelFileDescriptor[] r0 = android.os.ParcelFileDescriptor.createPipe()
            r1 = 0
            r2 = 1
            r3 = r0[r2]     // Catch:{ all -> 0x0061 }
            java.io.FileDescriptor r3 = r3.getFileDescriptor()     // Catch:{ all -> 0x0061 }
            dumpAsync(r10, r3, r11)     // Catch:{ all -> 0x0061 }
            r3 = r0[r2]     // Catch:{ all -> 0x0061 }
            r3.close()     // Catch:{ all -> 0x0061 }
            r3 = 0
            r0[r2] = r3     // Catch:{ all -> 0x0061 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0061 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0061 }
            r5.<init>()     // Catch:{ all -> 0x0061 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x005b }
            r7 = r0[r1]     // Catch:{ Throwable -> 0x005b }
            java.io.FileDescriptor r7 = r7.getFileDescriptor()     // Catch:{ Throwable -> 0x005b }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x005b }
        L_0x002b:
            int r7 = r6.read(r4)     // Catch:{ Throwable -> 0x004f, all -> 0x004c }
            r8 = -1
            if (r7 != r8) goto L_0x0048
            $closeResource(r3, r6)     // Catch:{ Throwable -> 0x005b }
            byte[] r6 = r5.toByteArray()     // Catch:{ Throwable -> 0x005b }
            $closeResource(r3, r5)     // Catch:{ all -> 0x0061 }
            r1 = r0[r1]
            r1.close()
            r1 = r0[r2]
            libcore.io.IoUtils.closeQuietly(r1)
            return r6
        L_0x0048:
            r5.write(r4, r1, r7)     // Catch:{ Throwable -> 0x004f, all -> 0x004c }
            goto L_0x002b
        L_0x004c:
            r7 = move-exception
            r8 = r3
            goto L_0x0055
        L_0x004f:
            r7 = move-exception
            throw r7     // Catch:{ all -> 0x0051 }
        L_0x0051:
            r8 = move-exception
            r9 = r8
            r8 = r7
            r7 = r9
        L_0x0055:
            $closeResource(r8, r6)     // Catch:{ Throwable -> 0x005b }
            throw r7     // Catch:{ Throwable -> 0x005b }
        L_0x0059:
            r6 = move-exception
            goto L_0x005d
        L_0x005b:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0059 }
        L_0x005d:
            $closeResource(r3, r5)     // Catch:{ all -> 0x0061 }
            throw r6     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r3 = move-exception
            r1 = r0[r1]
            r1.close()
            r1 = r0[r2]
            libcore.io.IoUtils.closeQuietly(r1)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.TransferPipe.dumpAsync(android.os.IBinder, java.lang.String[]):byte[]");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    static void go(Caller caller, IInterface iface, FileDescriptor out, String prefix, String[] args) throws IOException, RemoteException {
        go(caller, iface, out, prefix, args, 5000);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void go(com.android.internal.os.TransferPipe.Caller r3, android.os.IInterface r4, java.io.FileDescriptor r5, java.lang.String r6, java.lang.String[] r7, long r8) throws java.io.IOException, android.os.RemoteException {
        /*
            android.os.IBinder r0 = r4.asBinder()
            boolean r0 = r0 instanceof android.os.Binder
            if (r0 == 0) goto L_0x000e
            r3.go(r4, r5, r6, r7)     // Catch:{ RemoteException -> 0x000c }
            goto L_0x000d
        L_0x000c:
            r0 = move-exception
        L_0x000d:
            return
        L_0x000e:
            com.android.internal.os.TransferPipe r0 = new com.android.internal.os.TransferPipe
            r0.<init>()
            r1 = 0
            android.os.ParcelFileDescriptor r2 = r0.getWriteFd()     // Catch:{ Throwable -> 0x0028 }
            java.io.FileDescriptor r2 = r2.getFileDescriptor()     // Catch:{ Throwable -> 0x0028 }
            r3.go(r4, r2, r6, r7)     // Catch:{ Throwable -> 0x0028 }
            r0.go(r5, r8)     // Catch:{ Throwable -> 0x0028 }
            $closeResource(r1, r0)
            return
        L_0x0026:
            r2 = move-exception
            goto L_0x002a
        L_0x0028:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0026 }
        L_0x002a:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.TransferPipe.go(com.android.internal.os.TransferPipe$Caller, android.os.IInterface, java.io.FileDescriptor, java.lang.String, java.lang.String[], long):void");
    }

    static void goDump(IBinder binder, FileDescriptor out, String[] args) throws IOException, RemoteException {
        goDump(binder, out, args, 5000);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void goDump(android.os.IBinder r3, java.io.FileDescriptor r4, java.lang.String[] r5, long r6) throws java.io.IOException, android.os.RemoteException {
        /*
            boolean r0 = r3 instanceof android.os.Binder
            if (r0 == 0) goto L_0x000a
            r3.dump(r4, r5)     // Catch:{ RemoteException -> 0x0008 }
            goto L_0x0009
        L_0x0008:
            r0 = move-exception
        L_0x0009:
            return
        L_0x000a:
            com.android.internal.os.TransferPipe r0 = new com.android.internal.os.TransferPipe
            r0.<init>()
            r1 = 0
            android.os.ParcelFileDescriptor r2 = r0.getWriteFd()     // Catch:{ Throwable -> 0x0024 }
            java.io.FileDescriptor r2 = r2.getFileDescriptor()     // Catch:{ Throwable -> 0x0024 }
            r3.dumpAsync(r2, r5)     // Catch:{ Throwable -> 0x0024 }
            r0.go(r4, r6)     // Catch:{ Throwable -> 0x0024 }
            $closeResource(r1, r0)
            return
        L_0x0022:
            r2 = move-exception
            goto L_0x0026
        L_0x0024:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0022 }
        L_0x0026:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.TransferPipe.goDump(android.os.IBinder, java.io.FileDescriptor, java.lang.String[], long):void");
    }

    public void go(FileDescriptor out) throws IOException {
        go(out, 5000);
    }

    public void go(FileDescriptor out, long timeout) throws IOException {
        try {
            synchronized (this) {
                this.mOutFd = out;
                this.mEndTime = SystemClock.uptimeMillis() + timeout;
                closeFd(1);
                this.mThread.start();
                while (this.mFailure == null && !this.mComplete) {
                    long waitTime = this.mEndTime - SystemClock.uptimeMillis();
                    if (waitTime > 0) {
                        try {
                            wait(waitTime);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        this.mThread.interrupt();
                        throw new IOException("Timeout");
                    }
                }
                if (this.mFailure != null) {
                    throw new IOException(this.mFailure);
                }
            }
            kill();
        } catch (Throwable th) {
            kill();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void closeFd(int num) {
        if (this.mFds[num] != null) {
            try {
                this.mFds[num].close();
            } catch (IOException e) {
            }
            this.mFds[num] = null;
        }
    }

    public void close() {
        kill();
    }

    public void kill() {
        synchronized (this) {
            closeFd(0);
            closeFd(1);
        }
    }

    /* access modifiers changed from: protected */
    public OutputStream getNewOutputStream() {
        return new FileOutputStream(this.mOutFd);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        r3 = null;
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        if (r11.mBufferPrefix == null) goto L_0x002f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        r3 = r11.mBufferPrefix.getBytes();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r5 = r2.read(r0);
        r6 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r5 <= 0) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0038, code lost:
        if (r3 != null) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        r1.write(r0, 0, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
        r8 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0040, code lost:
        if (r5 >= r6) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        if (r0[r5] == 10) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        if (r5 <= r8) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004a, code lost:
        r1.write(r0, r8, r5 - r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004f, code lost:
        r8 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0050, code lost:
        if (r4 == false) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0052, code lost:
        r1.write(r3);
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0057, code lost:
        if (r5 >= r6) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x005b, code lost:
        if (r0[r5] != 10) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005d, code lost:
        if (r5 >= r6) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005f, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0060, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0062, code lost:
        if (r6 <= r8) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0064, code lost:
        r1.write(r0, r8, r6 - r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006a, code lost:
        r11.mThread.isInterrupted();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0071, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r11.mComplete = true;
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0077, code lost:
        monitor-exit(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0078, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x007c, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x007d, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        r11.mFailure = r5.toString();
        notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0088, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r11 = this;
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            monitor-enter(r11)
            android.os.ParcelFileDescriptor r1 = r11.getReadFd()     // Catch:{ all -> 0x008c }
            if (r1 != 0) goto L_0x0014
            java.lang.String r2 = "TransferPipe"
            java.lang.String r3 = "Pipe has been closed..."
            android.util.Slog.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x008c }
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            return
        L_0x0014:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x008c }
            java.io.FileDescriptor r3 = r1.getFileDescriptor()     // Catch:{ all -> 0x008c }
            r2.<init>(r3)     // Catch:{ all -> 0x008c }
            java.io.OutputStream r3 = r11.getNewOutputStream()     // Catch:{ all -> 0x008c }
            r1 = r3
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            r3 = 0
            r4 = 1
            java.lang.String r5 = r11.mBufferPrefix
            if (r5 == 0) goto L_0x002f
            java.lang.String r5 = r11.mBufferPrefix
            byte[] r3 = r5.getBytes()
        L_0x002f:
            int r5 = r2.read(r0)     // Catch:{ IOException -> 0x007c }
            r6 = r5
            r7 = 1
            if (r5 <= 0) goto L_0x006a
            r5 = 0
            if (r3 != 0) goto L_0x003e
            r1.write(r0, r5, r6)     // Catch:{ IOException -> 0x007c }
            goto L_0x002f
        L_0x003e:
            r8 = 0
        L_0x0040:
            if (r5 >= r6) goto L_0x0062
            byte r9 = r0[r5]     // Catch:{ IOException -> 0x007c }
            r10 = 10
            if (r9 == r10) goto L_0x0060
            if (r5 <= r8) goto L_0x004f
            int r9 = r5 - r8
            r1.write(r0, r8, r9)     // Catch:{ IOException -> 0x007c }
        L_0x004f:
            r8 = r5
            if (r4 == 0) goto L_0x0056
            r1.write(r3)     // Catch:{ IOException -> 0x007c }
            r4 = 0
        L_0x0056:
            int r5 = r5 + r7
            if (r5 >= r6) goto L_0x005d
            byte r9 = r0[r5]     // Catch:{ IOException -> 0x007c }
            if (r9 != r10) goto L_0x0056
        L_0x005d:
            if (r5 >= r6) goto L_0x0060
            r4 = 1
        L_0x0060:
            int r5 = r5 + r7
            goto L_0x0040
        L_0x0062:
            if (r6 <= r8) goto L_0x0069
            int r5 = r6 - r8
            r1.write(r0, r8, r5)     // Catch:{ IOException -> 0x007c }
        L_0x0069:
            goto L_0x002f
        L_0x006a:
            java.lang.Thread r5 = r11.mThread     // Catch:{ IOException -> 0x007c }
            r5.isInterrupted()     // Catch:{ IOException -> 0x007c }
            monitor-enter(r11)
            r11.mComplete = r7     // Catch:{ all -> 0x0079 }
            r11.notifyAll()     // Catch:{ all -> 0x0079 }
            monitor-exit(r11)     // Catch:{ all -> 0x0079 }
            return
        L_0x0079:
            r5 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0079 }
            throw r5
        L_0x007c:
            r5 = move-exception
            monitor-enter(r11)
            java.lang.String r6 = r5.toString()     // Catch:{ all -> 0x0089 }
            r11.mFailure = r6     // Catch:{ all -> 0x0089 }
            r11.notifyAll()     // Catch:{ all -> 0x0089 }
            monitor-exit(r11)     // Catch:{ all -> 0x0089 }
            return
        L_0x0089:
            r6 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0089 }
            throw r6
        L_0x008c:
            r1 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.TransferPipe.run():void");
    }
}
