package android.print;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PrintDocumentAdapter;
import com.android.internal.R;
import java.io.File;

public class PrintFileDocumentAdapter extends PrintDocumentAdapter {
    private static final String LOG_TAG = "PrintedFileDocAdapter";
    /* access modifiers changed from: private */
    public final Context mContext;
    private final PrintDocumentInfo mDocumentInfo;
    /* access modifiers changed from: private */
    public final File mFile;
    private WriteFileAsyncTask mWriteFileAsyncTask;

    public PrintFileDocumentAdapter(Context context, File file, PrintDocumentInfo documentInfo) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null!");
        } else if (documentInfo != null) {
            this.mContext = context;
            this.mFile = file;
            this.mDocumentInfo = documentInfo;
        } else {
            throw new IllegalArgumentException("documentInfo cannot be null!");
        }
    }

    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback callback, Bundle metadata) {
        callback.onLayoutFinished(this.mDocumentInfo, false);
    }

    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback callback) {
        this.mWriteFileAsyncTask = new WriteFileAsyncTask(destination, cancellationSignal, callback);
        this.mWriteFileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Params[]) null);
    }

    private final class WriteFileAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CancellationSignal mCancellationSignal;
        private final ParcelFileDescriptor mDestination;
        private final PrintDocumentAdapter.WriteResultCallback mResultCallback;

        public WriteFileAsyncTask(ParcelFileDescriptor destination, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback callback) {
            this.mDestination = destination;
            this.mResultCallback = callback;
            this.mCancellationSignal = cancellationSignal;
            this.mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(PrintFileDocumentAdapter.this) {
                public void onCancel() {
                    WriteFileAsyncTask.this.cancel(true);
                }
            });
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
            r3 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
            r4 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0029, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
            r5 = r4;
            r4 = r3;
            r3 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0031, code lost:
            r2 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0032, code lost:
            r3 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0036, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0037, code lost:
            r5 = r3;
            r3 = r2;
            r2 = r5;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Void... r7) {
            /*
                r6 = this;
                r0 = 0
                java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
                android.print.PrintFileDocumentAdapter r2 = android.print.PrintFileDocumentAdapter.this     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
                java.io.File r2 = r2.mFile     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
                r1.<init>(r2)     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
                android.os.ParcelFileDescriptor r3 = r6.mDestination     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
                java.io.FileDescriptor r3 = r3.getFileDescriptor()     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
                r2.<init>(r3)     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
                android.os.CancellationSignal r3 = r6.mCancellationSignal     // Catch:{ Throwable -> 0x0027, all -> 0x0024 }
                android.os.FileUtils.copy((java.io.InputStream) r1, (java.io.OutputStream) r2, (android.os.CancellationSignal) r3, (java.util.concurrent.Executor) r0, (android.os.FileUtils.ProgressListener) r0)     // Catch:{ Throwable -> 0x0027, all -> 0x0024 }
                $closeResource(r0, r2)     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
                $closeResource(r0, r1)     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
                goto L_0x005a
            L_0x0024:
                r3 = move-exception
                r4 = r0
                goto L_0x002d
            L_0x0027:
                r3 = move-exception
                throw r3     // Catch:{ all -> 0x0029 }
            L_0x0029:
                r4 = move-exception
                r5 = r4
                r4 = r3
                r3 = r5
            L_0x002d:
                $closeResource(r4, r2)     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
                throw r3     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            L_0x0031:
                r2 = move-exception
                r3 = r0
                goto L_0x003a
            L_0x0034:
                r2 = move-exception
                throw r2     // Catch:{ all -> 0x0036 }
            L_0x0036:
                r3 = move-exception
                r5 = r3
                r3 = r2
                r2 = r5
            L_0x003a:
                $closeResource(r3, r1)     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
                throw r2     // Catch:{ OperationCanceledException -> 0x0059, IOException -> 0x003e }
            L_0x003e:
                r1 = move-exception
                java.lang.String r2 = "PrintedFileDocAdapter"
                java.lang.String r3 = "Error writing data!"
                android.util.Log.e(r2, r3, r1)
                android.print.PrintDocumentAdapter$WriteResultCallback r2 = r6.mResultCallback
                android.print.PrintFileDocumentAdapter r3 = android.print.PrintFileDocumentAdapter.this
                android.content.Context r3 = r3.mContext
                r4 = 17041330(0x10407b2, float:2.4250092E-38)
                java.lang.String r3 = r3.getString(r4)
                r2.onWriteFailed(r3)
                goto L_0x005b
            L_0x0059:
                r1 = move-exception
            L_0x005a:
            L_0x005b:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.print.PrintFileDocumentAdapter.WriteFileAsyncTask.doInBackground(java.lang.Void[]):java.lang.Void");
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

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            this.mResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        }

        /* access modifiers changed from: protected */
        public void onCancelled(Void result) {
            this.mResultCallback.onWriteFailed(PrintFileDocumentAdapter.this.mContext.getString(R.string.write_fail_reason_cancelled));
        }
    }
}
