package android.os;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.FastPrintWriter;
import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public abstract class ShellCommand {
    static final boolean DEBUG = false;
    static final String TAG = "ShellCommand";
    private int mArgPos;
    private String[] mArgs;
    private String mCmd;
    private String mCurArgData;
    private FileDescriptor mErr;
    private FastPrintWriter mErrPrintWriter;
    private FileOutputStream mFileErr;
    private FileInputStream mFileIn;
    private FileOutputStream mFileOut;
    private FileDescriptor mIn;
    private InputStream mInputStream;
    private FileDescriptor mOut;
    private FastPrintWriter mOutPrintWriter;
    private ResultReceiver mResultReceiver;
    private ShellCallback mShellCallback;
    private Binder mTarget;

    public abstract int onCommand(String str);

    public abstract void onHelp();

    public void init(Binder target, FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, int firstArgPos) {
        this.mTarget = target;
        this.mIn = in;
        this.mOut = out;
        this.mErr = err;
        this.mArgs = args;
        this.mShellCallback = callback;
        this.mResultReceiver = null;
        this.mCmd = null;
        this.mArgPos = firstArgPos;
        this.mCurArgData = null;
        this.mFileIn = null;
        this.mFileOut = null;
        this.mFileErr = null;
        this.mOutPrintWriter = null;
        this.mErrPrintWriter = null;
        this.mInputStream = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        if (r9.mResultReceiver != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0045, code lost:
        r9.mResultReceiver.send(r2, (android.os.Bundle) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        if (r9.mResultReceiver == null) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ab, code lost:
        if (r9.mResultReceiver == null) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ae, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int exec(android.os.Binder r13, java.io.FileDescriptor r14, java.io.FileDescriptor r15, java.io.FileDescriptor r16, java.lang.String[] r17, android.os.ShellCallback r18, android.os.ResultReceiver r19) {
        /*
            r12 = this;
            r9 = r12
            r10 = r17
            r0 = 0
            if (r10 == 0) goto L_0x000f
            int r1 = r10.length
            if (r1 <= 0) goto L_0x000f
            r0 = r10[r0]
            r1 = 1
            r11 = r0
            r8 = r1
            goto L_0x0012
        L_0x000f:
            r1 = 0
            r8 = r0
            r11 = r1
        L_0x0012:
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r7 = r18
            r1.init(r2, r3, r4, r5, r6, r7, r8)
            r9.mCmd = r11
            r1 = r19
            r9.mResultReceiver = r1
            r0 = -1
            r2 = r0
            r3 = 0
            java.lang.String r0 = r9.mCmd     // Catch:{ SecurityException -> 0x0074, Throwable -> 0x004d }
            int r0 = r12.onCommand(r0)     // Catch:{ SecurityException -> 0x0074, Throwable -> 0x004d }
            r2 = r0
            com.android.internal.util.FastPrintWriter r0 = r9.mOutPrintWriter
            if (r0 == 0) goto L_0x0038
            com.android.internal.util.FastPrintWriter r0 = r9.mOutPrintWriter
            r0.flush()
        L_0x0038:
            com.android.internal.util.FastPrintWriter r0 = r9.mErrPrintWriter
            if (r0 == 0) goto L_0x0041
            com.android.internal.util.FastPrintWriter r0 = r9.mErrPrintWriter
            r0.flush()
        L_0x0041:
            android.os.ResultReceiver r0 = r9.mResultReceiver
            if (r0 == 0) goto L_0x00ae
        L_0x0045:
            android.os.ResultReceiver r0 = r9.mResultReceiver
            r0.send(r2, r3)
            goto L_0x00ae
        L_0x004b:
            r0 = move-exception
            goto L_0x00af
        L_0x004d:
            r0 = move-exception
            java.io.PrintWriter r4 = r12.getErrPrintWriter()     // Catch:{ all -> 0x004b }
            r4.println()     // Catch:{ all -> 0x004b }
            java.lang.String r5 = "Exception occurred while executing:"
            r4.println(r5)     // Catch:{ all -> 0x004b }
            r0.printStackTrace(r4)     // Catch:{ all -> 0x004b }
            com.android.internal.util.FastPrintWriter r0 = r9.mOutPrintWriter
            if (r0 == 0) goto L_0x0066
            com.android.internal.util.FastPrintWriter r0 = r9.mOutPrintWriter
            r0.flush()
        L_0x0066:
            com.android.internal.util.FastPrintWriter r0 = r9.mErrPrintWriter
            if (r0 == 0) goto L_0x006f
            com.android.internal.util.FastPrintWriter r0 = r9.mErrPrintWriter
            r0.flush()
        L_0x006f:
            android.os.ResultReceiver r0 = r9.mResultReceiver
            if (r0 == 0) goto L_0x00ae
            goto L_0x0045
        L_0x0074:
            r0 = move-exception
            java.io.PrintWriter r4 = r12.getErrPrintWriter()     // Catch:{ all -> 0x004b }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
            r5.<init>()     // Catch:{ all -> 0x004b }
            java.lang.String r6 = "Security exception: "
            r5.append(r6)     // Catch:{ all -> 0x004b }
            java.lang.String r6 = r0.getMessage()     // Catch:{ all -> 0x004b }
            r5.append(r6)     // Catch:{ all -> 0x004b }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x004b }
            r4.println(r5)     // Catch:{ all -> 0x004b }
            r4.println()     // Catch:{ all -> 0x004b }
            r0.printStackTrace(r4)     // Catch:{ all -> 0x004b }
            com.android.internal.util.FastPrintWriter r0 = r9.mOutPrintWriter
            if (r0 == 0) goto L_0x00a0
            com.android.internal.util.FastPrintWriter r0 = r9.mOutPrintWriter
            r0.flush()
        L_0x00a0:
            com.android.internal.util.FastPrintWriter r0 = r9.mErrPrintWriter
            if (r0 == 0) goto L_0x00a9
            com.android.internal.util.FastPrintWriter r0 = r9.mErrPrintWriter
            r0.flush()
        L_0x00a9:
            android.os.ResultReceiver r0 = r9.mResultReceiver
            if (r0 == 0) goto L_0x00ae
            goto L_0x0045
        L_0x00ae:
            return r2
        L_0x00af:
            com.android.internal.util.FastPrintWriter r4 = r9.mOutPrintWriter
            if (r4 == 0) goto L_0x00b9
            com.android.internal.util.FastPrintWriter r4 = r9.mOutPrintWriter
            r4.flush()
        L_0x00b9:
            com.android.internal.util.FastPrintWriter r4 = r9.mErrPrintWriter
            if (r4 == 0) goto L_0x00c2
            com.android.internal.util.FastPrintWriter r4 = r9.mErrPrintWriter
            r4.flush()
        L_0x00c2:
            android.os.ResultReceiver r4 = r9.mResultReceiver
            if (r4 == 0) goto L_0x00cb
            android.os.ResultReceiver r4 = r9.mResultReceiver
            r4.send(r2, r3)
        L_0x00cb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.ShellCommand.exec(android.os.Binder, java.io.FileDescriptor, java.io.FileDescriptor, java.io.FileDescriptor, java.lang.String[], android.os.ShellCallback, android.os.ResultReceiver):int");
    }

    public ResultReceiver adoptResultReceiver() {
        ResultReceiver rr = this.mResultReceiver;
        this.mResultReceiver = null;
        return rr;
    }

    public FileDescriptor getOutFileDescriptor() {
        return this.mOut;
    }

    public OutputStream getRawOutputStream() {
        if (this.mFileOut == null) {
            this.mFileOut = new FileOutputStream(this.mOut);
        }
        return this.mFileOut;
    }

    public PrintWriter getOutPrintWriter() {
        if (this.mOutPrintWriter == null) {
            this.mOutPrintWriter = new FastPrintWriter(getRawOutputStream());
        }
        return this.mOutPrintWriter;
    }

    public FileDescriptor getErrFileDescriptor() {
        return this.mErr;
    }

    public OutputStream getRawErrorStream() {
        if (this.mFileErr == null) {
            this.mFileErr = new FileOutputStream(this.mErr);
        }
        return this.mFileErr;
    }

    public PrintWriter getErrPrintWriter() {
        if (this.mErr == null) {
            return getOutPrintWriter();
        }
        if (this.mErrPrintWriter == null) {
            this.mErrPrintWriter = new FastPrintWriter(getRawErrorStream());
        }
        return this.mErrPrintWriter;
    }

    public FileDescriptor getInFileDescriptor() {
        return this.mIn;
    }

    public InputStream getRawInputStream() {
        if (this.mFileIn == null) {
            this.mFileIn = new FileInputStream(this.mIn);
        }
        return this.mFileIn;
    }

    public InputStream getBufferedInputStream() {
        if (this.mInputStream == null) {
            this.mInputStream = new BufferedInputStream(getRawInputStream());
        }
        return this.mInputStream;
    }

    public ParcelFileDescriptor openFileForSystem(String path, String mode) {
        try {
            ParcelFileDescriptor pfd = getShellCallback().openFile(path, "u:r:system_server:s0", mode);
            if (pfd != null) {
                return pfd;
            }
        } catch (RuntimeException e) {
            PrintWriter errPrintWriter = getErrPrintWriter();
            errPrintWriter.println("Failure opening file: " + e.getMessage());
        }
        PrintWriter errPrintWriter2 = getErrPrintWriter();
        errPrintWriter2.println("Error: Unable to open file: " + path);
        if (path != null && path.startsWith("/data/local/tmp/")) {
            return null;
        }
        PrintWriter errPrintWriter3 = getErrPrintWriter();
        errPrintWriter3.println("Consider using a file under " + "/data/local/tmp/");
        return null;
    }

    public String getNextOption() {
        if (this.mCurArgData != null) {
            throw new IllegalArgumentException("No argument expected after \"" + this.mArgs[this.mArgPos - 1] + "\"");
        } else if (this.mArgPos >= this.mArgs.length) {
            return null;
        } else {
            String arg = this.mArgs[this.mArgPos];
            if (!arg.startsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                return null;
            }
            this.mArgPos++;
            if (arg.equals("--")) {
                return null;
            }
            if (arg.length() <= 1 || arg.charAt(1) == '-') {
                this.mCurArgData = null;
                return arg;
            } else if (arg.length() > 2) {
                this.mCurArgData = arg.substring(2);
                return arg.substring(0, 2);
            } else {
                this.mCurArgData = null;
                return arg;
            }
        }
    }

    public String getNextArg() {
        if (this.mCurArgData != null) {
            String arg = this.mCurArgData;
            this.mCurArgData = null;
            return arg;
        } else if (this.mArgPos >= this.mArgs.length) {
            return null;
        } else {
            String[] strArr = this.mArgs;
            int i = this.mArgPos;
            this.mArgPos = i + 1;
            return strArr[i];
        }
    }

    @UnsupportedAppUsage
    public String peekNextArg() {
        if (this.mCurArgData != null) {
            return this.mCurArgData;
        }
        if (this.mArgPos < this.mArgs.length) {
            return this.mArgs[this.mArgPos];
        }
        return null;
    }

    public String getNextArgRequired() {
        String arg = getNextArg();
        if (arg != null) {
            return arg;
        }
        String prev = this.mArgs[this.mArgPos - 1];
        throw new IllegalArgumentException("Argument expected after \"" + prev + "\"");
    }

    public ShellCallback getShellCallback() {
        return this.mShellCallback;
    }

    public int handleDefaultCommands(String cmd) {
        if ("dump".equals(cmd)) {
            String[] newArgs = new String[(this.mArgs.length - 1)];
            System.arraycopy(this.mArgs, 1, newArgs, 0, this.mArgs.length - 1);
            this.mTarget.doDump(this.mOut, getOutPrintWriter(), newArgs);
            return 0;
        } else if (cmd == null || "help".equals(cmd) || "-h".equals(cmd)) {
            onHelp();
            return -1;
        } else {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Unknown command: " + cmd);
            return -1;
        }
    }
}
