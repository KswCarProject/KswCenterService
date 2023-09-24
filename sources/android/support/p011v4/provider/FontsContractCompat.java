package android.support.p011v4.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.p002pm.PackageInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ProviderInfo;
import android.content.p002pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.p007os.Build;
import android.p007os.CancellationSignal;
import android.p007os.Handler;
import android.provider.BaseColumns;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.p011v4.content.res.FontResourcesParserCompat;
import android.support.p011v4.graphics.TypefaceCompat;
import android.support.p011v4.graphics.TypefaceCompatUtil;
import android.support.p011v4.provider.SelfDestructiveThread;
import android.support.p011v4.util.LruCache;
import android.support.p011v4.util.Preconditions;
import android.support.p011v4.util.SimpleArrayMap;
import android.widget.TextView;
import com.android.internal.content.NativeLibraryHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* renamed from: android.support.v4.provider.FontsContractCompat */
/* loaded from: classes3.dex */
public class FontsContractCompat {
    private static final int BACKGROUND_THREAD_KEEP_ALIVE_DURATION_MS = 10000;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String PARCEL_FONT_RESULTS = "font_results";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int RESULT_CODE_PROVIDER_NOT_FOUND = -1;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final int RESULT_CODE_WRONG_CERTIFICATES = -2;
    private static final String TAG = "FontsContractCompat";
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    private static final SelfDestructiveThread sBackgroundThread = new SelfDestructiveThread("fonts", 10, 10000);
    private static final Object sLock = new Object();
    @GuardedBy("sLock")
    private static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>>> sPendingReplies = new SimpleArrayMap<>();
    private static final Comparator<byte[]> sByteArrayComparator = new Comparator<byte[]>() { // from class: android.support.v4.provider.FontsContractCompat.5
        @Override // java.util.Comparator
        public int compare(byte[] l, byte[] r) {
            if (l.length != r.length) {
                return l.length - r.length;
            }
            for (int i = 0; i < l.length; i++) {
                if (l[i] != r[i]) {
                    return l[i] - r[i];
                }
            }
            return 0;
        }
    };

    /* renamed from: android.support.v4.provider.FontsContractCompat$Columns */
    /* loaded from: classes3.dex */
    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    private FontsContractCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface getFontInternal(Context context, FontRequest request, int style) {
        try {
            FontFamilyResult result = fetchFonts(context, null, request);
            if (result.getStatusCode() != 0) {
                return null;
            }
            return TypefaceCompat.createFromFontInfo(context, null, result.getFonts(), style);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Typeface getFontSync(final Context context, final FontRequest request, @Nullable final TextView targetView, int strategy, int timeout, final int style) {
        final String id = request.getIdentifier() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + style;
        Typeface cached = sTypefaceCache.get(id);
        if (cached != null) {
            return cached;
        }
        boolean isBlockingFetch = strategy == 0;
        if (isBlockingFetch && timeout == -1) {
            return getFontInternal(context, request, style);
        }
        Callable<Typeface> fetcher = new Callable<Typeface>() { // from class: android.support.v4.provider.FontsContractCompat.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Typeface call() throws Exception {
                Typeface typeface = FontsContractCompat.getFontInternal(Context.this, request, style);
                if (typeface != null) {
                    FontsContractCompat.sTypefaceCache.put(id, typeface);
                }
                return typeface;
            }
        };
        if (isBlockingFetch) {
            try {
                return (Typeface) sBackgroundThread.postAndWait(fetcher, timeout);
            } catch (InterruptedException e) {
                return null;
            }
        }
        final WeakReference<TextView> textViewWeak = new WeakReference<>(targetView);
        SelfDestructiveThread.ReplyCallback<Typeface> reply = new SelfDestructiveThread.ReplyCallback<Typeface>() { // from class: android.support.v4.provider.FontsContractCompat.2
            @Override // android.support.p011v4.provider.SelfDestructiveThread.ReplyCallback
            public void onReply(Typeface typeface) {
                TextView textView = (TextView) textViewWeak.get();
                if (textView != null) {
                    targetView.setTypeface(typeface, style);
                }
            }
        };
        synchronized (sLock) {
            if (sPendingReplies.containsKey(id)) {
                sPendingReplies.get(id).add(reply);
                return null;
            }
            ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>> pendingReplies = new ArrayList<>();
            pendingReplies.add(reply);
            sPendingReplies.put(id, pendingReplies);
            sBackgroundThread.postAndReply(fetcher, new SelfDestructiveThread.ReplyCallback<Typeface>() { // from class: android.support.v4.provider.FontsContractCompat.3
                @Override // android.support.p011v4.provider.SelfDestructiveThread.ReplyCallback
                public void onReply(Typeface typeface) {
                    ArrayList<SelfDestructiveThread.ReplyCallback<Typeface>> replies;
                    synchronized (FontsContractCompat.sLock) {
                        replies = (ArrayList) FontsContractCompat.sPendingReplies.get(id);
                        FontsContractCompat.sPendingReplies.remove(id);
                    }
                    for (int i = 0; i < replies.size(); i++) {
                        replies.get(i).onReply(typeface);
                    }
                }
            });
            return null;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontInfo */
    /* loaded from: classes3.dex */
    public static class FontInfo {
        private final boolean mItalic;
        private final int mResultCode;
        private final int mTtcIndex;
        private final Uri mUri;
        private final int mWeight;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public FontInfo(@NonNull Uri uri, @IntRange(from = 0) int ttcIndex, @IntRange(from = 1, m104to = 1000) int weight, boolean italic, int resultCode) {
            this.mUri = (Uri) Preconditions.checkNotNull(uri);
            this.mTtcIndex = ttcIndex;
            this.mWeight = weight;
            this.mItalic = italic;
            this.mResultCode = resultCode;
        }

        @NonNull
        public Uri getUri() {
            return this.mUri;
        }

        @IntRange(from = 0)
        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        @IntRange(from = 1, m104to = 1000)
        public int getWeight() {
            return this.mWeight;
        }

        public boolean isItalic() {
            return this.mItalic;
        }

        public int getResultCode() {
            return this.mResultCode;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontFamilyResult */
    /* loaded from: classes3.dex */
    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        private final FontInfo[] mFonts;
        private final int mStatusCode;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* renamed from: android.support.v4.provider.FontsContractCompat$FontFamilyResult$FontResultStatus */
        /* loaded from: classes3.dex */
        @interface FontResultStatus {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public FontFamilyResult(int statusCode, @Nullable FontInfo[] fonts) {
            this.mStatusCode = statusCode;
            this.mFonts = fonts;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public FontInfo[] getFonts() {
            return this.mFonts;
        }
    }

    /* renamed from: android.support.v4.provider.FontsContractCompat$FontRequestCallback */
    /* loaded from: classes3.dex */
    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        /* renamed from: android.support.v4.provider.FontsContractCompat$FontRequestCallback$FontRequestFailReason */
        /* loaded from: classes3.dex */
        @interface FontRequestFailReason {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }

        public void onTypefaceRequestFailed(int reason) {
        }
    }

    public static void requestFont(@NonNull final Context context, @NonNull final FontRequest request, @NonNull final FontRequestCallback callback, @NonNull Handler handler) {
        final Handler callerThreadHandler = new Handler();
        handler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FontFamilyResult result = FontsContractCompat.fetchFonts(Context.this, null, request);
                    if (result.getStatusCode() != 0) {
                        switch (result.getStatusCode()) {
                            case 1:
                                callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-2);
                                    }
                                });
                                return;
                            case 2:
                                callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.3
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-3);
                                    }
                                });
                                return;
                            default:
                                callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.4
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-3);
                                    }
                                });
                                return;
                        }
                    }
                    FontInfo[] fonts = result.getFonts();
                    if (fonts == null || fonts.length == 0) {
                        callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.5
                            @Override // java.lang.Runnable
                            public void run() {
                                callback.onTypefaceRequestFailed(1);
                            }
                        });
                        return;
                    }
                    for (FontInfo font : fonts) {
                        if (font.getResultCode() != 0) {
                            final int resultCode = font.getResultCode();
                            if (resultCode < 0) {
                                callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.6
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        callback.onTypefaceRequestFailed(-3);
                                    }
                                });
                                return;
                            } else {
                                callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.7
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        callback.onTypefaceRequestFailed(resultCode);
                                    }
                                });
                                return;
                            }
                        }
                    }
                    final Typeface typeface = FontsContractCompat.buildTypeface(Context.this, null, fonts);
                    if (typeface == null) {
                        callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.8
                            @Override // java.lang.Runnable
                            public void run() {
                                callback.onTypefaceRequestFailed(-3);
                            }
                        });
                    } else {
                        callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.9
                            @Override // java.lang.Runnable
                            public void run() {
                                callback.onTypefaceRetrieved(typeface);
                            }
                        });
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    callerThreadHandler.post(new Runnable() { // from class: android.support.v4.provider.FontsContractCompat.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            callback.onTypefaceRequestFailed(-1);
                        }
                    });
                }
            }
        });
    }

    public static Typeface buildTypeface(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fonts) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fonts, 0);
    }

    @RequiresApi(19)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fonts, CancellationSignal cancellationSignal) {
        HashMap<Uri, ByteBuffer> out = new HashMap<>();
        for (FontInfo font : fonts) {
            if (font.getResultCode() == 0) {
                Uri uri = font.getUri();
                if (!out.containsKey(uri)) {
                    ByteBuffer buffer = TypefaceCompatUtil.mmap(context, cancellationSignal, uri);
                    out.put(uri, buffer);
                }
            }
        }
        return Collections.unmodifiableMap(out);
    }

    @NonNull
    public static FontFamilyResult fetchFonts(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontRequest request) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return new FontFamilyResult(1, null);
        }
        FontInfo[] fonts = getFontFromProvider(context, request, providerInfo.authority, cancellationSignal);
        return new FontFamilyResult(0, fonts);
    }

    @VisibleForTesting
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static ProviderInfo getProvider(@NonNull PackageManager packageManager, @NonNull FontRequest request, @Nullable Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (!info.packageName.equals(request.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        } else {
            PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, 64);
            List<byte[]> signatures = convertToByteArrayList(packageInfo.signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        }
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        int resourceId = request.getCertificatesArrayResId();
        return FontResourcesParserCompat.readCerts(resources, resourceId);
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shas = new ArrayList<>();
        for (Signature signature : signatures) {
            shas.add(signature.toByteArray());
        }
        return shas;
    }

    @VisibleForTesting
    @NonNull
    static FontInfo[] getFontFromProvider(Context context, FontRequest request, String authority, CancellationSignal cancellationSignal) {
        Cursor query;
        Uri fileUri;
        ArrayList<FontInfo> result = new ArrayList<>();
        Uri uri = new Uri.Builder().scheme("content").authority(authority).build();
        Uri fileBaseUri = new Uri.Builder().scheme("content").authority(authority).appendPath(ContentResolver.SCHEME_FILE).build();
        Cursor cursor = null;
        try {
            int i = 0;
            if (Build.VERSION.SDK_INT > 16) {
                query = context.getContentResolver().query(uri, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{request.getQuery()}, null, cancellationSignal);
            } else {
                query = context.getContentResolver().query(uri, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{request.getQuery()}, null);
            }
            cursor = query;
            if (cursor != null && cursor.getCount() > 0) {
                int resultCodeColumnIndex = cursor.getColumnIndex("result_code");
                result = new ArrayList<>();
                int idColumnIndex = cursor.getColumnIndex("_id");
                int fileIdColumnIndex = cursor.getColumnIndex("file_id");
                int ttcIndexColumnIndex = cursor.getColumnIndex("font_ttc_index");
                int weightColumnIndex = cursor.getColumnIndex("font_weight");
                int italicColumnIndex = cursor.getColumnIndex("font_italic");
                while (cursor.moveToNext()) {
                    int resultCode = resultCodeColumnIndex != -1 ? cursor.getInt(resultCodeColumnIndex) : i;
                    int ttcIndex = ttcIndexColumnIndex != -1 ? cursor.getInt(ttcIndexColumnIndex) : i;
                    if (fileIdColumnIndex == -1) {
                        long id = cursor.getLong(idColumnIndex);
                        fileUri = ContentUris.withAppendedId(uri, id);
                    } else {
                        long id2 = cursor.getLong(fileIdColumnIndex);
                        fileUri = ContentUris.withAppendedId(fileBaseUri, id2);
                    }
                    Uri fileUri2 = fileUri;
                    int weight = weightColumnIndex != -1 ? cursor.getInt(weightColumnIndex) : 400;
                    boolean italic = italicColumnIndex != -1 && cursor.getInt(italicColumnIndex) == 1;
                    result.add(new FontInfo(fileUri2, ttcIndex, weight, italic, resultCode));
                    i = 0;
                }
            }
            return (FontInfo[]) result.toArray(new FontInfo[0]);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
