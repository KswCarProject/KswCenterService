package android.content;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.CancellationSignal;
import android.p007os.ParcelFileDescriptor;
import android.p007os.RemoteException;
import android.util.Log;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class LoggingContentInterface implements ContentInterface {
    private final ContentInterface delegate;
    private final String tag;

    public LoggingContentInterface(String tag, ContentInterface delegate) {
        this.tag = tag;
        this.delegate = delegate;
    }

    /* loaded from: classes.dex */
    private class Logger implements AutoCloseable {

        /* renamed from: sb */
        private final StringBuilder f26sb = new StringBuilder();

        public Logger(String method, Object... args) {
            for (Object arg : args) {
                if (arg instanceof Bundle) {
                    ((Bundle) arg).size();
                }
            }
            StringBuilder sb = this.f26sb;
            sb.append("callingUid=");
            sb.append(Binder.getCallingUid());
            sb.append(' ');
            this.f26sb.append(method);
            StringBuilder sb2 = this.f26sb;
            sb2.append('(');
            sb2.append(deepToString(args));
            sb2.append(')');
        }

        private String deepToString(Object value) {
            if (value != null && value.getClass().isArray()) {
                return Arrays.deepToString((Object[]) value);
            }
            return String.valueOf(value);
        }

        public <T> T setResult(T res) {
            if (res instanceof Cursor) {
                this.f26sb.append('\n');
                DatabaseUtils.dumpCursor((Cursor) res, this.f26sb);
            } else {
                StringBuilder sb = this.f26sb;
                sb.append(" = ");
                sb.append(deepToString(res));
            }
            return res;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            Log.m66v(LoggingContentInterface.this.tag, this.f26sb.toString());
        }
    }

    @Override // android.content.ContentInterface
    public Cursor query(Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) throws RemoteException {
        Logger l = new Logger("query", uri, projection, queryArgs, cancellationSignal);
        try {
            try {
                return (Cursor) l.setResult(this.delegate.query(uri, projection, queryArgs, cancellationSignal));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
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

    @Override // android.content.ContentInterface
    public String getType(Uri uri) throws RemoteException {
        Logger l = new Logger("getType", uri);
        try {
            try {
                return (String) l.setResult(this.delegate.getType(uri));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) throws RemoteException {
        Logger l = new Logger("getStreamTypes", uri, mimeTypeFilter);
        try {
            try {
                return (String[]) l.setResult(this.delegate.getStreamTypes(uri, mimeTypeFilter));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public Uri canonicalize(Uri uri) throws RemoteException {
        Logger l = new Logger("canonicalize", uri);
        try {
            try {
                return (Uri) l.setResult(this.delegate.canonicalize(uri));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public Uri uncanonicalize(Uri uri) throws RemoteException {
        Logger l = new Logger("uncanonicalize", uri);
        try {
            try {
                return (Uri) l.setResult(this.delegate.uncanonicalize(uri));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public boolean refresh(Uri uri, Bundle args, CancellationSignal cancellationSignal) throws RemoteException {
        Logger l = new Logger("refresh", uri, args, cancellationSignal);
        try {
            try {
                return ((Boolean) l.setResult(Boolean.valueOf(this.delegate.refresh(uri, args, cancellationSignal)))).booleanValue();
            } catch (Exception res) {
                l.setResult(res);
                throw res;
            }
        } finally {
            $closeResource(null, l);
        }
    }

    @Override // android.content.ContentInterface
    public Uri insert(Uri uri, ContentValues initialValues) throws RemoteException {
        Logger l = new Logger("insert", uri, initialValues);
        try {
            try {
                return (Uri) l.setResult(this.delegate.insert(uri, initialValues));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(Uri uri, ContentValues[] initialValues) throws RemoteException {
        Logger l = new Logger("bulkInsert", uri, initialValues);
        try {
            try {
                return ((Integer) l.setResult(Integer.valueOf(this.delegate.bulkInsert(uri, initialValues)))).intValue();
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public int delete(Uri uri, String selection, String[] selectionArgs) throws RemoteException {
        Logger l = new Logger("delete", uri, selection, selectionArgs);
        try {
            try {
                return ((Integer) l.setResult(Integer.valueOf(this.delegate.delete(uri, selection, selectionArgs)))).intValue();
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) throws RemoteException {
        Logger l = new Logger("update", uri, values, selection, selectionArgs);
        try {
            try {
                return ((Integer) l.setResult(Integer.valueOf(this.delegate.update(uri, values, selection, selectionArgs)))).intValue();
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public ParcelFileDescriptor openFile(Uri uri, String mode, CancellationSignal signal) throws RemoteException, FileNotFoundException {
        Logger l = new Logger("openFile", uri, mode, signal);
        try {
            try {
                return (ParcelFileDescriptor) l.setResult(this.delegate.openFile(uri, mode, signal));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openAssetFile(Uri uri, String mode, CancellationSignal signal) throws RemoteException, FileNotFoundException {
        Logger l = new Logger("openAssetFile", uri, mode, signal);
        try {
            try {
                return (AssetFileDescriptor) l.setResult(this.delegate.openAssetFile(uri, mode, signal));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts, CancellationSignal signal) throws RemoteException, FileNotFoundException {
        Logger l = new Logger("openTypedAssetFile", uri, mimeTypeFilter, opts, signal);
        try {
            try {
                return (AssetFileDescriptor) l.setResult(this.delegate.openTypedAssetFile(uri, mimeTypeFilter, opts, signal));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public ContentProviderResult[] applyBatch(String authority, ArrayList<ContentProviderOperation> operations) throws RemoteException, OperationApplicationException {
        Logger l = new Logger("applyBatch", authority, operations);
        try {
            try {
                return (ContentProviderResult[]) l.setResult(this.delegate.applyBatch(authority, operations));
            } finally {
                $closeResource(null, l);
            }
        } catch (Exception res) {
            l.setResult(res);
            throw res;
        }
    }

    @Override // android.content.ContentInterface
    public Bundle call(String authority, String method, String arg, Bundle extras) throws RemoteException {
        Logger l = new Logger("call", authority, method, arg, extras);
        try {
            try {
                return (Bundle) l.setResult(this.delegate.call(authority, method, arg, extras));
            } catch (Exception res) {
                l.setResult(res);
                throw res;
            }
        } finally {
            $closeResource(null, l);
        }
    }
}
