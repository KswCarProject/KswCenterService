package android.p007os;

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

/* renamed from: android.os.ShellCommand */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0071, code lost:
        if (r12.mResultReceiver == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ab, code lost:
        if (r12.mResultReceiver == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ae, code lost:
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int exec(Binder target, FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, ResultReceiver resultReceiver) {
        int start;
        String cmd;
        if (args == null || args.length <= 0) {
            start = 0;
            cmd = null;
        } else {
            String cmd2 = args[0];
            cmd = cmd2;
            start = 1;
        }
        init(target, in, out, err, args, callback, start);
        this.mCmd = cmd;
        this.mResultReceiver = resultReceiver;
        int res = -1;
        try {
            try {
                res = onCommand(this.mCmd);
            } catch (SecurityException e) {
                PrintWriter eout = getErrPrintWriter();
                eout.println("Security exception: " + e.getMessage());
                eout.println();
                e.printStackTrace(eout);
                if (this.mOutPrintWriter != null) {
                    this.mOutPrintWriter.flush();
                }
                if (this.mErrPrintWriter != null) {
                    this.mErrPrintWriter.flush();
                }
            } catch (Throwable e2) {
                PrintWriter eout2 = getErrPrintWriter();
                eout2.println();
                eout2.println("Exception occurred while executing:");
                e2.printStackTrace(eout2);
                if (this.mOutPrintWriter != null) {
                    this.mOutPrintWriter.flush();
                }
                if (this.mErrPrintWriter != null) {
                    this.mErrPrintWriter.flush();
                }
            }
        } finally {
            if (this.mOutPrintWriter != null) {
                this.mOutPrintWriter.flush();
            }
            if (this.mErrPrintWriter != null) {
                this.mErrPrintWriter.flush();
            }
            if (this.mResultReceiver != null) {
                this.mResultReceiver.send(-1, null);
            }
        }
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
        if (path == null || !path.startsWith("/data/local/tmp/")) {
            PrintWriter errPrintWriter3 = getErrPrintWriter();
            errPrintWriter3.println("Consider using a file under /data/local/tmp/");
            return null;
        }
        return null;
    }

    public String getNextOption() {
        if (this.mCurArgData != null) {
            String prev = this.mArgs[this.mArgPos - 1];
            throw new IllegalArgumentException("No argument expected after \"" + prev + "\"");
        } else if (this.mArgPos >= this.mArgs.length) {
            return null;
        } else {
            String arg = this.mArgs[this.mArgPos];
            if (arg.startsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                this.mArgPos++;
                if (arg.equals("--")) {
                    return null;
                }
                if (arg.length() > 1 && arg.charAt(1) != '-') {
                    if (arg.length() > 2) {
                        this.mCurArgData = arg.substring(2);
                        return arg.substring(0, 2);
                    }
                    this.mCurArgData = null;
                    return arg;
                }
                this.mCurArgData = null;
                return arg;
            }
            return null;
        }
    }

    public String getNextArg() {
        if (this.mCurArgData != null) {
            String arg = this.mCurArgData;
            this.mCurArgData = null;
            return arg;
        } else if (this.mArgPos < this.mArgs.length) {
            String[] strArr = this.mArgs;
            int i = this.mArgPos;
            this.mArgPos = i + 1;
            return strArr[i];
        } else {
            return null;
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
        if (arg == null) {
            String prev = this.mArgs[this.mArgPos - 1];
            throw new IllegalArgumentException("Argument expected after \"" + prev + "\"");
        }
        return arg;
    }

    public ShellCallback getShellCallback() {
        return this.mShellCallback;
    }

    public int handleDefaultCommands(String cmd) {
        if ("dump".equals(cmd)) {
            String[] newArgs = new String[this.mArgs.length - 1];
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
