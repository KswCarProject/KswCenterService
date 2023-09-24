package android.support.p011v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.p007os.Build;
import android.p007os.OperationCanceledException;
import android.support.p011v4.p013os.CancellationSignal;

/* renamed from: android.support.v4.content.ContentResolverCompat */
/* loaded from: classes3.dex */
public final class ContentResolverCompat {
    private ContentResolverCompat() {
    }

    public static Cursor query(ContentResolver resolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        Object cancellationSignalObject;
        if (Build.VERSION.SDK_INT >= 16) {
            if (cancellationSignal != null) {
                try {
                    cancellationSignalObject = cancellationSignal.getCancellationSignalObject();
                } catch (Exception e) {
                    if (e instanceof OperationCanceledException) {
                        throw new android.support.p011v4.p013os.OperationCanceledException();
                    }
                    throw e;
                }
            } else {
                cancellationSignalObject = null;
            }
            android.p007os.CancellationSignal cancellationSignalObj = (android.p007os.CancellationSignal) cancellationSignalObject;
            return resolver.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignalObj);
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }
}
