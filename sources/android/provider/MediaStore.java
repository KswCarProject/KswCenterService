package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriPermission;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.provider.Downloads;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.logging.nano.MetricsProto;
import com.ibm.icu.text.PluralRules;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public final class MediaStore {
    public static final String ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE";
    public static final String ACTION_IMAGE_CAPTURE_SECURE = "android.media.action.IMAGE_CAPTURE_SECURE";
    public static final String ACTION_REVIEW = "android.provider.action.REVIEW";
    public static final String ACTION_REVIEW_SECURE = "android.provider.action.REVIEW_SECURE";
    public static final String ACTION_VIDEO_CAPTURE = "android.media.action.VIDEO_CAPTURE";
    public static final String AUTHORITY = "media";
    public static final Uri AUTHORITY_URI = Uri.parse("content://media");
    public static final String DELETE_CONTRIBUTED_MEDIA_CALL = "delete_contributed_media";
    public static final String EXTRA_BRIGHTNESS = "android.provider.extra.BRIGHTNESS";
    public static final String EXTRA_DURATION_LIMIT = "android.intent.extra.durationLimit";
    public static final String EXTRA_FINISH_ON_COMPLETION = "android.intent.extra.finishOnCompletion";
    public static final String EXTRA_FULL_SCREEN = "android.intent.extra.fullScreen";
    public static final String EXTRA_MEDIA_ALBUM = "android.intent.extra.album";
    public static final String EXTRA_MEDIA_ARTIST = "android.intent.extra.artist";
    public static final String EXTRA_MEDIA_FOCUS = "android.intent.extra.focus";
    public static final String EXTRA_MEDIA_GENRE = "android.intent.extra.genre";
    public static final String EXTRA_MEDIA_PLAYLIST = "android.intent.extra.playlist";
    public static final String EXTRA_MEDIA_RADIO_CHANNEL = "android.intent.extra.radio_channel";
    public static final String EXTRA_MEDIA_TITLE = "android.intent.extra.title";
    public static final String EXTRA_ORIGINATED_FROM_SHELL = "android.intent.extra.originated_from_shell";
    public static final String EXTRA_OUTPUT = "output";
    public static final String EXTRA_SCREEN_ORIENTATION = "android.intent.extra.screenOrientation";
    public static final String EXTRA_SHOW_ACTION_ICONS = "android.intent.extra.showActionIcons";
    public static final String EXTRA_SIZE_LIMIT = "android.intent.extra.sizeLimit";
    public static final String EXTRA_VIDEO_QUALITY = "android.intent.extra.videoQuality";
    public static final String GET_CONTRIBUTED_MEDIA_CALL = "get_contributed_media";
    public static final String GET_DOCUMENT_URI_CALL = "get_document_uri";
    public static final String GET_MEDIA_URI_CALL = "get_media_uri";
    public static final String GET_VERSION_CALL = "get_version";
    public static final String INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH = "android.media.action.MEDIA_PLAY_FROM_SEARCH";
    public static final String INTENT_ACTION_MEDIA_SEARCH = "android.intent.action.MEDIA_SEARCH";
    @Deprecated
    public static final String INTENT_ACTION_MUSIC_PLAYER = "android.intent.action.MUSIC_PLAYER";
    public static final String INTENT_ACTION_STILL_IMAGE_CAMERA = "android.media.action.STILL_IMAGE_CAMERA";
    public static final String INTENT_ACTION_STILL_IMAGE_CAMERA_SECURE = "android.media.action.STILL_IMAGE_CAMERA_SECURE";
    public static final String INTENT_ACTION_TEXT_OPEN_FROM_SEARCH = "android.media.action.TEXT_OPEN_FROM_SEARCH";
    public static final String INTENT_ACTION_VIDEO_CAMERA = "android.media.action.VIDEO_CAMERA";
    public static final String INTENT_ACTION_VIDEO_PLAY_FROM_SEARCH = "android.media.action.VIDEO_PLAY_FROM_SEARCH";
    public static final String MEDIA_IGNORE_FILENAME = ".nomedia";
    public static final String MEDIA_SCANNER_VOLUME = "volume";
    public static final String META_DATA_STILL_IMAGE_CAMERA_PREWARM_SERVICE = "android.media.still_image_camera_preview_service";
    public static final String PARAM_DELETE_DATA = "deletedata";
    public static final String PARAM_INCLUDE_PENDING = "includePending";
    public static final String PARAM_INCLUDE_TRASHED = "includeTrashed";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_PROGRESS = "progress";
    public static final String PARAM_REQUIRE_ORIGINAL = "requireOriginal";
    public static final String RETRANSLATE_CALL = "update_titles";
    public static final String SCAN_FILE_CALL = "scan_file";
    public static final String SCAN_VOLUME_CALL = "scan_volume";
    private static final String TAG = "MediaStore";
    @Deprecated
    public static final String UNHIDE_CALL = "unhide";
    public static final String UNKNOWN_STRING = "<unknown>";
    public static final String VOLUME_EXTERNAL = "external";
    public static final String VOLUME_EXTERNAL_PRIMARY = "external_primary";
    public static final String VOLUME_INTERNAL = "internal";

    public interface DownloadColumns extends MediaColumns {
        @Deprecated
        @Column(3)
        public static final String DESCRIPTION = "description";
        @Column(3)
        public static final String DOWNLOAD_URI = "download_uri";
        @Column(3)
        public static final String REFERER_URI = "referer_uri";
    }

    public interface MediaColumns extends BaseColumns {
        @Column(readOnly = true, value = 3)
        public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
        @Column(readOnly = true, value = 1)
        public static final String BUCKET_ID = "bucket_id";
        @Deprecated
        @Column(3)
        public static final String DATA = "_data";
        @Column(readOnly = true, value = 1)
        public static final String DATE_ADDED = "date_added";
        @Column(1)
        public static final String DATE_EXPIRES = "date_expires";
        @Column(readOnly = true, value = 1)
        public static final String DATE_MODIFIED = "date_modified";
        @Column(readOnly = true, value = 1)
        public static final String DATE_TAKEN = "datetaken";
        @Column(3)
        public static final String DISPLAY_NAME = "_display_name";
        @Column(readOnly = true, value = 3)
        public static final String DOCUMENT_ID = "document_id";
        @Column(readOnly = true, value = 1)
        public static final String DURATION = "duration";
        @Deprecated
        @Column(readOnly = true, value = 1)
        public static final String GROUP_ID = "group_id";
        @Deprecated
        @Column(readOnly = true, value = 4)
        public static final String HASH = "_hash";
        @Column(readOnly = true, value = 1)
        public static final String HEIGHT = "height";
        @Column(readOnly = true, value = 3)
        public static final String INSTANCE_ID = "instance_id";
        @Deprecated
        @UnsupportedAppUsage
        @Column(1)
        public static final String IS_DRM = "is_drm";
        @Column(1)
        public static final String IS_PENDING = "is_pending";
        @Deprecated
        @Column(1)
        public static final String IS_TRASHED = "is_trashed";
        @Deprecated
        public static final String MEDIA_SCANNER_NEW_OBJECT_ID = "media_scanner_new_object_id";
        @Column(3)
        public static final String MIME_TYPE = "mime_type";
        @Column(readOnly = true, value = 1)
        public static final String ORIENTATION = "orientation";
        @Column(readOnly = true, value = 3)
        public static final String ORIGINAL_DOCUMENT_ID = "original_document_id";
        @Column(readOnly = true, value = 3)
        public static final String OWNER_PACKAGE_NAME = "owner_package_name";
        @Deprecated
        @Column(3)
        public static final String PRIMARY_DIRECTORY = "primary_directory";
        @Column(3)
        public static final String RELATIVE_PATH = "relative_path";
        @Deprecated
        @Column(3)
        public static final String SECONDARY_DIRECTORY = "secondary_directory";
        @Column(readOnly = true, value = 1)
        public static final String SIZE = "_size";
        @Column(readOnly = true, value = 3)
        public static final String TITLE = "title";
        @Column(readOnly = true, value = 3)
        public static final String VOLUME_NAME = "volume_name";
        @Column(readOnly = true, value = 1)
        public static final String WIDTH = "width";
    }

    public static class ThumbnailConstants {
        public static final int FULL_SCREEN_KIND = 2;
        public static final Point FULL_SCREEN_SIZE = new Point(1024, 786);
        public static final int MICRO_KIND = 3;
        public static final Point MICRO_SIZE = new Point(96, 96);
        public static final int MINI_KIND = 1;
        public static final Point MINI_SIZE = new Point(512, MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION);
    }

    public static Uri setIncludePending(Uri uri) {
        return setIncludePending(uri.buildUpon()).build();
    }

    public static Uri.Builder setIncludePending(Uri.Builder uriBuilder) {
        return uriBuilder.appendQueryParameter(PARAM_INCLUDE_PENDING, "1");
    }

    @Deprecated
    public static Uri setIncludeTrashed(Uri uri) {
        return uri.buildUpon().appendQueryParameter(PARAM_INCLUDE_TRASHED, "1").build();
    }

    public static Uri setRequireOriginal(Uri uri) {
        return uri.buildUpon().appendQueryParameter(PARAM_REQUIRE_ORIGINAL, "1").build();
    }

    @Deprecated
    public static Uri createPending(Context context, PendingParams params) {
        return context.getContentResolver().insert(params.insertUri, params.insertValues);
    }

    @Deprecated
    public static PendingSession openPending(Context context, Uri uri) {
        return new PendingSession(context, uri);
    }

    @Deprecated
    public static class PendingParams {
        public final Uri insertUri;
        public final ContentValues insertValues = new ContentValues();

        public PendingParams(Uri insertUri2, String displayName, String mimeType) {
            this.insertUri = (Uri) Objects.requireNonNull(insertUri2);
            long now = System.currentTimeMillis() / 1000;
            this.insertValues.put("_display_name", (String) Objects.requireNonNull(displayName));
            this.insertValues.put("mime_type", (String) Objects.requireNonNull(mimeType));
            this.insertValues.put("date_added", Long.valueOf(now));
            this.insertValues.put("date_modified", Long.valueOf(now));
            this.insertValues.put(MediaColumns.IS_PENDING, (Integer) 1);
            this.insertValues.put(MediaColumns.DATE_EXPIRES, Long.valueOf((System.currentTimeMillis() + 86400000) / 1000));
        }

        public void setPrimaryDirectory(String primaryDirectory) {
            if (primaryDirectory == null) {
                this.insertValues.remove(MediaColumns.PRIMARY_DIRECTORY);
            } else {
                this.insertValues.put(MediaColumns.PRIMARY_DIRECTORY, primaryDirectory);
            }
        }

        public void setSecondaryDirectory(String secondaryDirectory) {
            if (secondaryDirectory == null) {
                this.insertValues.remove(MediaColumns.SECONDARY_DIRECTORY);
            } else {
                this.insertValues.put(MediaColumns.SECONDARY_DIRECTORY, secondaryDirectory);
            }
        }

        public void setDownloadUri(Uri downloadUri) {
            if (downloadUri == null) {
                this.insertValues.remove(DownloadColumns.DOWNLOAD_URI);
            } else {
                this.insertValues.put(DownloadColumns.DOWNLOAD_URI, downloadUri.toString());
            }
        }

        public void setRefererUri(Uri refererUri) {
            if (refererUri == null) {
                this.insertValues.remove(DownloadColumns.REFERER_URI);
            } else {
                this.insertValues.put(DownloadColumns.REFERER_URI, refererUri.toString());
            }
        }
    }

    @Deprecated
    public static class PendingSession implements AutoCloseable {
        private final Context mContext;
        private final Uri mUri;

        public PendingSession(Context context, Uri uri) {
            this.mContext = (Context) Objects.requireNonNull(context);
            this.mUri = (Uri) Objects.requireNonNull(uri);
        }

        public ParcelFileDescriptor open() throws FileNotFoundException {
            return this.mContext.getContentResolver().openFileDescriptor(this.mUri, "rw");
        }

        public OutputStream openOutputStream() throws FileNotFoundException {
            return this.mContext.getContentResolver().openOutputStream(this.mUri);
        }

        public void notifyProgress(int progress) {
            this.mContext.getContentResolver().notifyChange(this.mUri.buildUpon().appendQueryParameter("progress", Integer.toString(progress)).build(), (ContentObserver) null, 0);
        }

        public Uri publish() {
            ContentValues values = new ContentValues();
            values.put(MediaColumns.IS_PENDING, (Integer) 0);
            values.putNull(MediaColumns.DATE_EXPIRES);
            this.mContext.getContentResolver().update(this.mUri, values, (String) null, (String[]) null);
            return this.mUri;
        }

        public void abandon() {
            this.mContext.getContentResolver().delete(this.mUri, (String) null, (String[]) null);
        }

        public void close() {
            notifyProgress(-1);
        }
    }

    @Deprecated
    public static void trash(Context context, Uri uri) {
        trash(context, uri, 172800000);
    }

    @Deprecated
    public static void trash(Context context, Uri uri, long timeoutMillis) {
        if (timeoutMillis >= 0) {
            ContentValues values = new ContentValues();
            values.put(MediaColumns.IS_TRASHED, (Integer) 1);
            values.put(MediaColumns.DATE_EXPIRES, Long.valueOf((System.currentTimeMillis() + timeoutMillis) / 1000));
            context.getContentResolver().update(uri, values, (String) null, (String[]) null);
            return;
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static void untrash(Context context, Uri uri) {
        ContentValues values = new ContentValues();
        values.put(MediaColumns.IS_TRASHED, (Integer) 0);
        values.putNull(MediaColumns.DATE_EXPIRES);
        context.getContentResolver().update(uri, values, (String) null, (String[]) null);
    }

    public static final class Files {
        public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
        public static final String TABLE = "files";

        public interface FileColumns extends MediaColumns {
            @UnsupportedAppUsage
            @Column(readOnly = true, value = 1)
            public static final String FORMAT = "format";
            @Column(readOnly = true, value = 1)
            public static final String IS_DOWNLOAD = "is_download";
            @Column(1)
            public static final String MEDIA_TYPE = "media_type";
            public static final int MEDIA_TYPE_AUDIO = 2;
            public static final int MEDIA_TYPE_IMAGE = 1;
            public static final int MEDIA_TYPE_NONE = 0;
            public static final int MEDIA_TYPE_PLAYLIST = 4;
            public static final int MEDIA_TYPE_VIDEO = 3;
            @Column(3)
            public static final String MIME_TYPE = "mime_type";
            @Column(readOnly = true, value = 1)
            public static final String PARENT = "parent";
            @Deprecated
            @UnsupportedAppUsage
            public static final String STORAGE_ID = "storage_id";
            @Column(readOnly = true, value = 3)
            public static final String TITLE = "title";
        }

        public static Uri getContentUri(String volumeName) {
            return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath(ContentResolver.SCHEME_FILE).build();
        }

        public static final Uri getContentUri(String volumeName, long rowId) {
            return ContentUris.withAppendedId(getContentUri(volumeName), rowId);
        }

        @UnsupportedAppUsage
        public static Uri getMtpObjectsUri(String volumeName) {
            return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("object").build();
        }

        @UnsupportedAppUsage
        public static final Uri getMtpObjectsUri(String volumeName, long fileId) {
            return ContentUris.withAppendedId(getMtpObjectsUri(volumeName), fileId);
        }

        @UnsupportedAppUsage
        public static final Uri getMtpReferencesUri(String volumeName, long fileId) {
            return getMtpObjectsUri(volumeName, fileId).buildUpon().appendPath("references").build();
        }

        public static final Uri getDirectoryUri(String volumeName) {
            return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("dir").build();
        }

        public static final Uri getContentUriForPath(String path) {
            return getContentUri(MediaStore.getVolumeName(new File(path)));
        }
    }

    public static final class Downloads implements DownloadColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/download";
        public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
        public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);
        private static final Pattern PATTERN_DOWNLOADS_DIRECTORY = Pattern.compile("(?i)^/storage/[^/]+/(?:[0-9]+/)?(?:Android/sandbox/[^/]+/)?Download/?");
        public static final Pattern PATTERN_DOWNLOADS_FILE = Pattern.compile("(?i)^/storage/[^/]+/(?:[0-9]+/)?(?:Android/sandbox/[^/]+/)?Download/.+");

        private Downloads() {
        }

        public static Uri getContentUri(String volumeName) {
            return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath(Downloads.Impl.AUTHORITY).build();
        }

        public static Uri getContentUri(String volumeName, long id) {
            return ContentUris.withAppendedId(getContentUri(volumeName), id);
        }

        public static Uri getContentUriForPath(String path) {
            return getContentUri(MediaStore.getVolumeName(new File(path)));
        }

        public static boolean isDownload(String path) {
            return PATTERN_DOWNLOADS_FILE.matcher(path).matches();
        }

        public static boolean isDownloadDir(String path) {
            return PATTERN_DOWNLOADS_DIRECTORY.matcher(path).matches();
        }
    }

    public static String getVolumeName(File path) {
        if (!FileUtils.contains(Environment.getStorageDirectory(), path)) {
            return VOLUME_INTERNAL;
        }
        StorageVolume sv = ((StorageManager) AppGlobals.getInitialApplication().getSystemService(StorageManager.class)).getStorageVolume(path);
        if (sv == null) {
            throw new IllegalStateException("Unknown volume at " + path);
        } else if (sv.isPrimary()) {
            return VOLUME_EXTERNAL_PRIMARY;
        } else {
            return checkArgumentVolumeName(sv.getNormalizedUuid());
        }
    }

    @Deprecated
    private static class InternalThumbnails implements BaseColumns {
        @GuardedBy({"sPending"})
        private static ArrayMap<Uri, CancellationSignal> sPending = new ArrayMap<>();

        private InternalThumbnails() {
        }

        @Deprecated
        static Bitmap getThumbnail(ContentResolver cr, Uri uri, int kind, BitmapFactory.Options opts) {
            Point size;
            CancellationSignal signal;
            if (kind == 3) {
                size = ThumbnailConstants.MICRO_SIZE;
            } else if (kind == 2) {
                size = ThumbnailConstants.FULL_SCREEN_SIZE;
            } else if (kind == 1) {
                size = ThumbnailConstants.MINI_SIZE;
            } else {
                throw new IllegalArgumentException("Unsupported kind: " + kind);
            }
            synchronized (sPending) {
                signal = sPending.get(uri);
                if (signal == null) {
                    signal = new CancellationSignal();
                    sPending.put(uri, signal);
                }
            }
            try {
                Bitmap loadThumbnail = cr.loadThumbnail(uri, Point.convert(size), signal);
                synchronized (sPending) {
                    sPending.remove(uri);
                }
                return loadThumbnail;
            } catch (IOException e) {
                try {
                    Log.w(MediaStore.TAG, "Failed to obtain thumbnail for " + uri, e);
                    synchronized (sPending) {
                        sPending.remove(uri);
                        return null;
                    }
                } catch (Throwable th) {
                    synchronized (sPending) {
                        sPending.remove(uri);
                        throw th;
                    }
                }
            }
        }

        @Deprecated
        static void cancelThumbnail(ContentResolver cr, Uri uri) {
            synchronized (sPending) {
                CancellationSignal signal = sPending.get(uri);
                if (signal != null) {
                    signal.cancel();
                }
            }
        }
    }

    public static final class Images {

        public interface ImageColumns extends MediaColumns {
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
            public static final String BUCKET_ID = "bucket_id";
            public static final String DATE_TAKEN = "datetaken";
            @Column(readOnly = true, value = 3)
            public static final String DESCRIPTION = "description";
            public static final String GROUP_ID = "group_id";
            @Column(1)
            public static final String IS_PRIVATE = "isprivate";
            @Deprecated
            @Column(readOnly = true, value = 2)
            public static final String LATITUDE = "latitude";
            @Deprecated
            @Column(readOnly = true, value = 2)
            public static final String LONGITUDE = "longitude";
            @Deprecated
            @Column(1)
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";
            public static final String ORIENTATION = "orientation";
            @Deprecated
            @Column(3)
            public static final String PICASA_ID = "picasa_id";
        }

        public static final class Media implements ImageColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/image";
            public static final String DEFAULT_SORT_ORDER = "bucket_display_name";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);

            @Deprecated
            public static final Cursor query(ContentResolver cr, Uri uri, String[] projection) {
                return cr.query(uri, projection, (String) null, (String[]) null, "bucket_display_name");
            }

            @Deprecated
            public static final Cursor query(ContentResolver cr, Uri uri, String[] projection, String where, String orderBy) {
                String str;
                if (orderBy == null) {
                    str = "bucket_display_name";
                } else {
                    str = orderBy;
                }
                return cr.query(uri, projection, where, (String[]) null, str);
            }

            @Deprecated
            public static final Cursor query(ContentResolver cr, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy) {
                String str;
                if (orderBy == null) {
                    str = "bucket_display_name";
                } else {
                    str = orderBy;
                }
                return cr.query(uri, projection, selection, selectionArgs, str);
            }

            @Deprecated
            public static final Bitmap getBitmap(ContentResolver cr, Uri url) throws FileNotFoundException, IOException {
                InputStream input = cr.openInputStream(url);
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                input.close();
                return bitmap;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:21:0x0048, code lost:
                r9 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
                r10 = null;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:0x004d, code lost:
                r10 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:27:0x004e, code lost:
                r11 = r10;
                r10 = r9;
                r9 = r11;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:32:0x0057, code lost:
                r8 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:33:0x0058, code lost:
                r9 = null;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:37:0x005c, code lost:
                r9 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:38:0x005d, code lost:
                r11 = r9;
                r9 = r8;
                r8 = r11;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x0064, code lost:
                r7 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:43:0x0065, code lost:
                r8 = null;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:47:0x0069, code lost:
                r8 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:48:0x006a, code lost:
                r11 = r8;
                r8 = r7;
                r7 = r11;
             */
            /* JADX WARNING: Unknown top exception splitter block from list: {B:39:0x0060=Splitter:B:39:0x0060, B:15:0x0037=Splitter:B:15:0x0037} */
            @java.lang.Deprecated
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public static final java.lang.String insertImage(android.content.ContentResolver r12, java.lang.String r13, java.lang.String r14, java.lang.String r15) throws java.io.FileNotFoundException {
                /*
                    java.io.File r0 = new java.io.File
                    r0.<init>(r13)
                    java.lang.String r1 = android.media.MediaFile.getMimeTypeForFile(r13)
                    boolean r2 = android.text.TextUtils.isEmpty(r14)
                    if (r2 == 0) goto L_0x0011
                    java.lang.String r14 = "Image"
                L_0x0011:
                    android.provider.MediaStore$PendingParams r2 = new android.provider.MediaStore$PendingParams
                    android.net.Uri r3 = EXTERNAL_CONTENT_URI
                    r2.<init>(r3, r14, r1)
                    android.app.Application r3 = android.app.AppGlobals.getInitialApplication()
                    android.net.Uri r4 = android.provider.MediaStore.createPending(r3, r2)
                    r5 = 0
                    android.provider.MediaStore$PendingSession r6 = android.provider.MediaStore.openPending(r3, r4)     // Catch:{ Exception -> 0x0073 }
                    java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                    r7.<init>(r0)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                    java.io.OutputStream r8 = r6.openOutputStream()     // Catch:{ Throwable -> 0x005a, all -> 0x0057 }
                    android.os.FileUtils.copy((java.io.InputStream) r7, (java.io.OutputStream) r8)     // Catch:{ Throwable -> 0x004b, all -> 0x0048 }
                    if (r8 == 0) goto L_0x0037
                    $closeResource(r5, r8)     // Catch:{ Throwable -> 0x005a, all -> 0x0057 }
                L_0x0037:
                    $closeResource(r5, r7)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                    android.net.Uri r7 = r6.publish()     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                    java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                    if (r6 == 0) goto L_0x0047
                    $closeResource(r5, r6)     // Catch:{ Exception -> 0x0073 }
                L_0x0047:
                    return r7
                L_0x0048:
                    r9 = move-exception
                    r10 = r5
                    goto L_0x0051
                L_0x004b:
                    r9 = move-exception
                    throw r9     // Catch:{ all -> 0x004d }
                L_0x004d:
                    r10 = move-exception
                    r11 = r10
                    r10 = r9
                    r9 = r11
                L_0x0051:
                    if (r8 == 0) goto L_0x0056
                    $closeResource(r10, r8)     // Catch:{ Throwable -> 0x005a, all -> 0x0057 }
                L_0x0056:
                    throw r9     // Catch:{ Throwable -> 0x005a, all -> 0x0057 }
                L_0x0057:
                    r8 = move-exception
                    r9 = r5
                    goto L_0x0060
                L_0x005a:
                    r8 = move-exception
                    throw r8     // Catch:{ all -> 0x005c }
                L_0x005c:
                    r9 = move-exception
                    r11 = r9
                    r9 = r8
                    r8 = r11
                L_0x0060:
                    $closeResource(r9, r7)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                    throw r8     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
                L_0x0064:
                    r7 = move-exception
                    r8 = r5
                    goto L_0x006d
                L_0x0067:
                    r7 = move-exception
                    throw r7     // Catch:{ all -> 0x0069 }
                L_0x0069:
                    r8 = move-exception
                    r11 = r8
                    r8 = r7
                    r7 = r11
                L_0x006d:
                    if (r6 == 0) goto L_0x0072
                    $closeResource(r8, r6)     // Catch:{ Exception -> 0x0073 }
                L_0x0072:
                    throw r7     // Catch:{ Exception -> 0x0073 }
                L_0x0073:
                    r6 = move-exception
                    java.lang.String r7 = "MediaStore"
                    java.lang.String r8 = "Failed to insert image"
                    android.util.Log.w(r7, r8, r6)
                    android.content.ContentResolver r7 = r3.getContentResolver()
                    r7.delete(r4, r5, r5)
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: android.provider.MediaStore.Images.Media.insertImage(android.content.ContentResolver, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
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

            /* JADX WARNING: Code restructure failed: missing block: B:18:0x003c, code lost:
                r6 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:19:0x003d, code lost:
                r7 = null;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x0041, code lost:
                r7 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x0042, code lost:
                r8 = r7;
                r7 = r6;
                r6 = r8;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:0x004b, code lost:
                r5 = th;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:30:0x004c, code lost:
                r6 = null;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:34:0x0050, code lost:
                r6 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:35:0x0051, code lost:
                r8 = r6;
                r6 = r5;
                r5 = r8;
             */
            @java.lang.Deprecated
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public static final java.lang.String insertImage(android.content.ContentResolver r9, android.graphics.Bitmap r10, java.lang.String r11, java.lang.String r12) {
                /*
                    boolean r0 = android.text.TextUtils.isEmpty(r11)
                    if (r0 == 0) goto L_0x0008
                    java.lang.String r11 = "Image"
                L_0x0008:
                    android.provider.MediaStore$PendingParams r0 = new android.provider.MediaStore$PendingParams
                    android.net.Uri r1 = EXTERNAL_CONTENT_URI
                    java.lang.String r2 = "image/jpeg"
                    r0.<init>(r1, r11, r2)
                    android.app.Application r1 = android.app.AppGlobals.getInitialApplication()
                    android.net.Uri r2 = android.provider.MediaStore.createPending(r1, r0)
                    r3 = 0
                    android.provider.MediaStore$PendingSession r4 = android.provider.MediaStore.openPending(r1, r2)     // Catch:{ Exception -> 0x005a }
                    java.io.OutputStream r5 = r4.openOutputStream()     // Catch:{ Throwable -> 0x004e, all -> 0x004b }
                    android.graphics.Bitmap$CompressFormat r6 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
                    r7 = 90
                    r10.compress(r6, r7, r5)     // Catch:{ Throwable -> 0x003f, all -> 0x003c }
                    if (r5 == 0) goto L_0x002e
                    $closeResource(r3, r5)     // Catch:{ Throwable -> 0x004e, all -> 0x004b }
                L_0x002e:
                    android.net.Uri r5 = r4.publish()     // Catch:{ Throwable -> 0x004e, all -> 0x004b }
                    java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x004e, all -> 0x004b }
                    if (r4 == 0) goto L_0x003b
                    $closeResource(r3, r4)     // Catch:{ Exception -> 0x005a }
                L_0x003b:
                    return r5
                L_0x003c:
                    r6 = move-exception
                    r7 = r3
                    goto L_0x0045
                L_0x003f:
                    r6 = move-exception
                    throw r6     // Catch:{ all -> 0x0041 }
                L_0x0041:
                    r7 = move-exception
                    r8 = r7
                    r7 = r6
                    r6 = r8
                L_0x0045:
                    if (r5 == 0) goto L_0x004a
                    $closeResource(r7, r5)     // Catch:{ Throwable -> 0x004e, all -> 0x004b }
                L_0x004a:
                    throw r6     // Catch:{ Throwable -> 0x004e, all -> 0x004b }
                L_0x004b:
                    r5 = move-exception
                    r6 = r3
                    goto L_0x0054
                L_0x004e:
                    r5 = move-exception
                    throw r5     // Catch:{ all -> 0x0050 }
                L_0x0050:
                    r6 = move-exception
                    r8 = r6
                    r6 = r5
                    r5 = r8
                L_0x0054:
                    if (r4 == 0) goto L_0x0059
                    $closeResource(r6, r4)     // Catch:{ Exception -> 0x005a }
                L_0x0059:
                    throw r5     // Catch:{ Exception -> 0x005a }
                L_0x005a:
                    r4 = move-exception
                    java.lang.String r5 = "MediaStore"
                    java.lang.String r6 = "Failed to insert image"
                    android.util.Log.w(r5, r6, r4)
                    android.content.ContentResolver r5 = r1.getContentResolver()
                    r5.delete(r2, r3, r3)
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: android.provider.MediaStore.Images.Media.insertImage(android.content.ContentResolver, android.graphics.Bitmap, java.lang.String, java.lang.String):java.lang.String");
            }

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("images").appendPath(MediaStore.AUTHORITY).build();
            }

            public static Uri getContentUri(String volumeName, long id) {
                return ContentUris.withAppendedId(getContentUri(volumeName), id);
            }
        }

        @Deprecated
        public static class Thumbnails implements BaseColumns {
            @Deprecated
            @Column(3)
            public static final String DATA = "_data";
            public static final String DEFAULT_SORT_ORDER = "image_id ASC";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final int FULL_SCREEN_KIND = 2;
            @Column(readOnly = true, value = 1)
            public static final String HEIGHT = "height";
            @Column(1)
            public static final String IMAGE_ID = "image_id";
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);
            @Column(1)
            public static final String KIND = "kind";
            public static final int MICRO_KIND = 3;
            public static final int MINI_KIND = 1;
            @Deprecated
            @Column(4)
            public static final String THUMB_DATA = "thumb_data";
            @Column(readOnly = true, value = 1)
            public static final String WIDTH = "width";

            @Deprecated
            public static final Cursor query(ContentResolver cr, Uri uri, String[] projection) {
                return cr.query(uri, projection, (String) null, (String[]) null, DEFAULT_SORT_ORDER);
            }

            @Deprecated
            public static final Cursor queryMiniThumbnails(ContentResolver cr, Uri uri, int kind, String[] projection) {
                return cr.query(uri, projection, "kind = " + kind, (String[]) null, DEFAULT_SORT_ORDER);
            }

            @Deprecated
            public static final Cursor queryMiniThumbnail(ContentResolver cr, long origId, int kind, String[] projection) {
                Uri uri = EXTERNAL_CONTENT_URI;
                return cr.query(uri, projection, "image_id = " + origId + " AND " + "kind" + " = " + kind, (String[]) null, (String) null);
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver cr, long origId) {
                InternalThumbnails.cancelThumbnail(cr, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, origId));
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver cr, long imageId, int kind, BitmapFactory.Options options) {
                return InternalThumbnails.getThumbnail(cr, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, imageId), kind, options);
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver cr, long origId, long groupId) {
                cancelThumbnailRequest(cr, origId);
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver cr, long imageId, long groupId, int kind, BitmapFactory.Options options) {
                return getThumbnail(cr, imageId, kind, options);
            }

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("images").appendPath("thumbnails").build();
            }
        }
    }

    public static final class Audio {

        public interface AlbumColumns {
            @Column(readOnly = true, value = 3)
            public static final String ALBUM = "album";
            @Deprecated
            @Column(3)
            public static final String ALBUM_ART = "album_art";
            @Column(readOnly = true, value = 1)
            public static final String ALBUM_ID = "album_id";
            @Column(readOnly = true, value = 3)
            public static final String ALBUM_KEY = "album_key";
            @Column(readOnly = true, value = 3)
            public static final String ARTIST = "artist";
            @Column(readOnly = true, value = 1)
            public static final String ARTIST_ID = "artist_id";
            @Column(readOnly = true, value = 1)
            public static final String FIRST_YEAR = "minyear";
            @Column(readOnly = true, value = 1)
            public static final String LAST_YEAR = "maxyear";
            @Column(readOnly = true, value = 1)
            public static final String NUMBER_OF_SONGS = "numsongs";
            @Column(readOnly = true, value = 1)
            public static final String NUMBER_OF_SONGS_FOR_ARTIST = "numsongs_by_artist";
        }

        public interface ArtistColumns {
            @Column(readOnly = true, value = 3)
            public static final String ARTIST = "artist";
            @Column(readOnly = true, value = 3)
            public static final String ARTIST_KEY = "artist_key";
            @Column(readOnly = true, value = 1)
            public static final String NUMBER_OF_ALBUMS = "number_of_albums";
            @Column(readOnly = true, value = 1)
            public static final String NUMBER_OF_TRACKS = "number_of_tracks";
        }

        public interface AudioColumns extends MediaColumns {
            @Column(readOnly = true, value = 3)
            public static final String ALBUM = "album";
            @Column(readOnly = true, value = 3)
            public static final String ALBUM_ARTIST = "album_artist";
            @Column(readOnly = true, value = 1)
            public static final String ALBUM_ID = "album_id";
            @Column(readOnly = true, value = 3)
            public static final String ALBUM_KEY = "album_key";
            @Column(readOnly = true, value = 3)
            public static final String ARTIST = "artist";
            @Column(readOnly = true, value = 1)
            public static final String ARTIST_ID = "artist_id";
            @Column(readOnly = true, value = 3)
            public static final String ARTIST_KEY = "artist_key";
            @Column(1)
            public static final String BOOKMARK = "bookmark";
            @Deprecated
            public static final String COMPILATION = "compilation";
            @Column(readOnly = true, value = 3)
            public static final String COMPOSER = "composer";
            public static final String DURATION = "duration";
            @Deprecated
            public static final String GENRE = "genre";
            @Column(readOnly = true, value = 1)
            public static final String IS_ALARM = "is_alarm";
            @Column(readOnly = true, value = 1)
            public static final String IS_AUDIOBOOK = "is_audiobook";
            @Column(readOnly = true, value = 1)
            public static final String IS_MUSIC = "is_music";
            @Column(readOnly = true, value = 1)
            public static final String IS_NOTIFICATION = "is_notification";
            @Column(readOnly = true, value = 1)
            public static final String IS_PODCAST = "is_podcast";
            @Column(readOnly = true, value = 1)
            public static final String IS_RINGTONE = "is_ringtone";
            @Column(readOnly = true, value = 3)
            public static final String TITLE_KEY = "title_key";
            @Column(readOnly = true, value = 3)
            public static final String TITLE_RESOURCE_URI = "title_resource_uri";
            @Column(readOnly = true, value = 1)
            public static final String TRACK = "track";
            @Column(readOnly = true, value = 1)
            public static final String YEAR = "year";
        }

        public interface GenresColumns {
            @Column(3)
            public static final String NAME = "name";
        }

        public interface PlaylistsColumns {
            @Deprecated
            @Column(3)
            public static final String DATA = "_data";
            @Column(readOnly = true, value = 1)
            public static final String DATE_ADDED = "date_added";
            @Column(readOnly = true, value = 1)
            public static final String DATE_MODIFIED = "date_modified";
            @Column(3)
            public static final String NAME = "name";
        }

        @Deprecated
        public static class Thumbnails implements BaseColumns {
            @Column(1)
            public static final String ALBUM_ID = "album_id";
            @Deprecated
            @Column(3)
            public static final String DATA = "_data";
        }

        public static String keyFor(String name) {
            if (name == null) {
                return null;
            }
            boolean sortfirst = false;
            if (name.equals(MediaStore.UNKNOWN_STRING)) {
                return "\u0001";
            }
            if (name.startsWith("\u0001")) {
                sortfirst = true;
            }
            String name2 = name.trim().toLowerCase();
            if (name2.startsWith("the ")) {
                name2 = name2.substring(4);
            }
            if (name2.startsWith("an ")) {
                name2 = name2.substring(3);
            }
            if (name2.startsWith("a ")) {
                name2 = name2.substring(2);
            }
            if (name2.endsWith(", the") || name2.endsWith(",the") || name2.endsWith(", an") || name2.endsWith(",an") || name2.endsWith(", a") || name2.endsWith(",a")) {
                name2 = name2.substring(0, name2.lastIndexOf(44));
            }
            String name3 = name2.replaceAll("[\\[\\]\\(\\)\"'.,?!]", "").trim();
            if (name3.length() <= 0) {
                return "";
            }
            StringBuilder b = new StringBuilder();
            b.append('.');
            int nl = name3.length();
            for (int i = 0; i < nl; i++) {
                b.append(name3.charAt(i));
                b.append('.');
            }
            String key = DatabaseUtils.getCollationKey(b.toString());
            if (!sortfirst) {
                return key;
            }
            return "\u0001" + key;
        }

        public static final class Media implements AudioColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/audio";
            public static final String DEFAULT_SORT_ORDER = "title_key";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/audio";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final String EXTRA_MAX_BYTES = "android.provider.MediaStore.extra.MAX_BYTES";
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);
            public static final String RECORD_SOUND_ACTION = "android.provider.MediaStore.RECORD_SOUND";

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("audio").appendPath(MediaStore.AUTHORITY).build();
            }

            public static Uri getContentUri(String volumeName, long id) {
                return ContentUris.withAppendedId(getContentUri(volumeName), id);
            }

            @Deprecated
            public static Uri getContentUriForPath(String path) {
                return getContentUri(MediaStore.getVolumeName(new File(path)));
            }
        }

        public static final class Genres implements BaseColumns, GenresColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/genre";
            public static final String DEFAULT_SORT_ORDER = "name";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/genre";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("audio").appendPath("genres").build();
            }

            public static Uri getContentUriForAudioId(String volumeName, int audioId) {
                return ContentUris.withAppendedId(Media.getContentUri(volumeName), (long) audioId).buildUpon().appendPath("genres").build();
            }

            public static final class Members implements AudioColumns {
                @Column(1)
                public static final String AUDIO_ID = "audio_id";
                public static final String CONTENT_DIRECTORY = "members";
                public static final String DEFAULT_SORT_ORDER = "title_key";
                @Column(1)
                public static final String GENRE_ID = "genre_id";

                public static final Uri getContentUri(String volumeName, long genreId) {
                    return ContentUris.withAppendedId(Genres.getContentUri(volumeName), genreId).buildUpon().appendPath("members").build();
                }
            }
        }

        public static final class Playlists implements BaseColumns, PlaylistsColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/playlist";
            public static final String DEFAULT_SORT_ORDER = "name";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/playlist";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("audio").appendPath("playlists").build();
            }

            public static final class Members implements AudioColumns {
                @Column(1)
                public static final String AUDIO_ID = "audio_id";
                public static final String CONTENT_DIRECTORY = "members";
                public static final String DEFAULT_SORT_ORDER = "play_order";
                @Column(1)
                public static final String PLAYLIST_ID = "playlist_id";
                @Column(1)
                public static final String PLAY_ORDER = "play_order";
                @Column(1)
                public static final String _ID = "_id";

                public static final Uri getContentUri(String volumeName, long playlistId) {
                    return ContentUris.withAppendedId(Playlists.getContentUri(volumeName), playlistId).buildUpon().appendPath("members").build();
                }

                public static final boolean moveItem(ContentResolver res, long playlistId, int from, int to) {
                    Uri uri = getContentUri(MediaStore.VOLUME_EXTERNAL, playlistId).buildUpon().appendEncodedPath(String.valueOf(from)).appendQueryParameter("move", "true").build();
                    ContentValues values = new ContentValues();
                    values.put("play_order", Integer.valueOf(to));
                    return res.update(uri, values, (String) null, (String[]) null) != 0;
                }
            }
        }

        public static final class Artists implements BaseColumns, ArtistColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/artists";
            public static final String DEFAULT_SORT_ORDER = "artist_key";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/artist";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("audio").appendPath("artists").build();
            }

            public static final class Albums implements AlbumColumns {
                public static final Uri getContentUri(String volumeName, long artistId) {
                    return ContentUris.withAppendedId(Artists.getContentUri(volumeName), artistId).buildUpon().appendPath("albums").build();
                }
            }
        }

        public static final class Albums implements BaseColumns, AlbumColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/albums";
            public static final String DEFAULT_SORT_ORDER = "album_key";
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/album";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("audio").appendPath("albums").build();
            }
        }

        public static final class Radio {
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/radio";

            private Radio() {
            }
        }
    }

    public static final class Video {
        public static final String DEFAULT_SORT_ORDER = "_display_name";

        public interface VideoColumns extends MediaColumns {
            @Column(readOnly = true, value = 3)
            public static final String ALBUM = "album";
            @Column(readOnly = true, value = 3)
            public static final String ARTIST = "artist";
            @Column(1)
            public static final String BOOKMARK = "bookmark";
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";
            public static final String BUCKET_ID = "bucket_id";
            @Column(3)
            public static final String CATEGORY = "category";
            @Column(readOnly = true, value = 1)
            public static final String COLOR_RANGE = "color_range";
            @Column(readOnly = true, value = 1)
            public static final String COLOR_STANDARD = "color_standard";
            @Column(readOnly = true, value = 1)
            public static final String COLOR_TRANSFER = "color_transfer";
            public static final String DATE_TAKEN = "datetaken";
            @Column(readOnly = true, value = 3)
            public static final String DESCRIPTION = "description";
            public static final String DURATION = "duration";
            public static final String GROUP_ID = "group_id";
            @Column(1)
            public static final String IS_PRIVATE = "isprivate";
            @Column(3)
            public static final String LANGUAGE = "language";
            @Deprecated
            @Column(readOnly = true, value = 2)
            public static final String LATITUDE = "latitude";
            @Deprecated
            @Column(readOnly = true, value = 2)
            public static final String LONGITUDE = "longitude";
            @Deprecated
            @Column(1)
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";
            @Column(readOnly = true, value = 3)
            public static final String RESOLUTION = "resolution";
            @Column(3)
            public static final String TAGS = "tags";
        }

        @Deprecated
        public static final Cursor query(ContentResolver cr, Uri uri, String[] projection) {
            return cr.query(uri, projection, (String) null, (String[]) null, "_display_name");
        }

        public static final class Media implements VideoColumns {
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/video";
            public static final String DEFAULT_SORT_ORDER = "title";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("video").appendPath(MediaStore.AUTHORITY).build();
            }

            public static Uri getContentUri(String volumeName, long id) {
                return ContentUris.withAppendedId(getContentUri(volumeName), id);
            }
        }

        @Deprecated
        public static class Thumbnails implements BaseColumns {
            @Deprecated
            @Column(3)
            public static final String DATA = "_data";
            public static final String DEFAULT_SORT_ORDER = "video_id ASC";
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_EXTERNAL);
            public static final int FULL_SCREEN_KIND = 2;
            @Column(readOnly = true, value = 1)
            public static final String HEIGHT = "height";
            public static final Uri INTERNAL_CONTENT_URI = getContentUri(MediaStore.VOLUME_INTERNAL);
            @Column(1)
            public static final String KIND = "kind";
            public static final int MICRO_KIND = 3;
            public static final int MINI_KIND = 1;
            @Column(1)
            public static final String VIDEO_ID = "video_id";
            @Column(readOnly = true, value = 1)
            public static final String WIDTH = "width";

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver cr, long origId) {
                InternalThumbnails.cancelThumbnail(cr, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, origId));
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver cr, long videoId, int kind, BitmapFactory.Options options) {
                return InternalThumbnails.getThumbnail(cr, ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, videoId), kind, options);
            }

            @Deprecated
            public static void cancelThumbnailRequest(ContentResolver cr, long videoId, long groupId) {
                cancelThumbnailRequest(cr, videoId);
            }

            @Deprecated
            public static Bitmap getThumbnail(ContentResolver cr, long videoId, long groupId, int kind, BitmapFactory.Options options) {
                return getThumbnail(cr, videoId, kind, options);
            }

            public static Uri getContentUri(String volumeName) {
                return MediaStore.AUTHORITY_URI.buildUpon().appendPath(volumeName).appendPath("video").appendPath("thumbnails").build();
            }
        }
    }

    @Deprecated
    public static Set<String> getAllVolumeNames(Context context) {
        return getExternalVolumeNames(context);
    }

    public static Set<String> getExternalVolumeNames(Context context) {
        Set<String> volumeNames = new ArraySet<>();
        for (VolumeInfo vi : ((StorageManager) context.getSystemService(StorageManager.class)).getVolumes()) {
            if (vi.isVisibleForUser(UserHandle.myUserId()) && vi.isMountedReadable()) {
                if (vi.isPrimary()) {
                    volumeNames.add(VOLUME_EXTERNAL_PRIMARY);
                } else {
                    volumeNames.add(vi.getNormalizedFsUuid());
                }
            }
        }
        return volumeNames;
    }

    public static String getVolumeName(Uri uri) {
        List<String> segments = uri.getPathSegments();
        if (uri.getAuthority().equals(AUTHORITY) && segments != null && segments.size() > 0) {
            return segments.get(0);
        }
        throw new IllegalArgumentException("Missing volume name: " + uri);
    }

    public static String checkArgumentVolumeName(String volumeName) {
        if (TextUtils.isEmpty(volumeName)) {
            throw new IllegalArgumentException();
        } else if (VOLUME_INTERNAL.equals(volumeName) || VOLUME_EXTERNAL.equals(volumeName) || VOLUME_EXTERNAL_PRIMARY.equals(volumeName)) {
            return volumeName;
        } else {
            for (int i = 0; i < volumeName.length(); i++) {
                char c = volumeName.charAt(i);
                Log.d(TAG, "checkArgumentVolumeName  c = " + c + "   volumeName.length() = " + volumeName.length());
                if (('a' > c || c > 'z') && (('0' > c || c > '9') && c != '-')) {
                    Log.e(TAG, "checkArgumentVolumeName  error  c = " + c);
                    throw new IllegalArgumentException("Invalid volume name: " + volumeName);
                }
            }
            return volumeName;
        }
    }

    public static File getVolumePath(String volumeName) throws FileNotFoundException {
        return getVolumePath(((StorageManager) AppGlobals.getInitialApplication().getSystemService(StorageManager.class)).getVolumes(), volumeName);
    }

    public static File getVolumePath(List<VolumeInfo> volumes, String volumeName) throws FileNotFoundException {
        File path;
        if (!TextUtils.isEmpty(volumeName)) {
            char c = 65535;
            int hashCode = volumeName.hashCode();
            if (hashCode != -1820761141) {
                if (hashCode == 570410685 && volumeName.equals(VOLUME_INTERNAL)) {
                    c = 0;
                }
            } else if (volumeName.equals(VOLUME_EXTERNAL)) {
                c = 1;
            }
            switch (c) {
                case 0:
                case 1:
                    throw new FileNotFoundException(volumeName + " has no associated path");
                default:
                    boolean wantPrimary = VOLUME_EXTERNAL_PRIMARY.equals(volumeName);
                    for (VolumeInfo volume : volumes) {
                        boolean matchPrimary = wantPrimary && volume.isPrimary();
                        boolean matchSecondary = !wantPrimary && Objects.equals(volume.getNormalizedFsUuid(), volumeName);
                        if ((matchPrimary || matchSecondary) && (path = volume.getPathForUser(UserHandle.myUserId())) != null) {
                            return path;
                        }
                    }
                    throw new FileNotFoundException("Failed to find path for " + volumeName);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Collection<File> getVolumeScanPaths(String volumeName) throws FileNotFoundException {
        if (!TextUtils.isEmpty(volumeName)) {
            Context context = AppGlobals.getInitialApplication();
            UserManager um = (UserManager) context.getSystemService(UserManager.class);
            ArrayList<File> res = new ArrayList<>();
            if (VOLUME_INTERNAL.equals(volumeName)) {
                addCanonicalFile(res, new File(Environment.getRootDirectory(), AUTHORITY));
                addCanonicalFile(res, new File(Environment.getOemDirectory(), AUTHORITY));
                addCanonicalFile(res, new File(Environment.getProductDirectory(), AUTHORITY));
            } else if (VOLUME_EXTERNAL.equals(volumeName)) {
                for (String exactVolume : getExternalVolumeNames(context)) {
                    addCanonicalFile(res, getVolumePath(exactVolume));
                }
                if (um.isDemoUser()) {
                    addCanonicalFile(res, Environment.getDataPreloadsMediaDirectory());
                }
            } else {
                addCanonicalFile(res, getVolumePath(volumeName));
                if (VOLUME_EXTERNAL_PRIMARY.equals(volumeName) && um.isDemoUser()) {
                    addCanonicalFile(res, Environment.getDataPreloadsMediaDirectory());
                }
            }
            return res;
        }
        throw new IllegalArgumentException();
    }

    private static void addCanonicalFile(List<File> list, File file) {
        try {
            list.add(file.getCanonicalFile());
        } catch (IOException e) {
            Log.w(TAG, "Failed to resolve " + file + PluralRules.KEYWORD_RULE_SEPARATOR + e);
            list.add(file);
        }
    }

    public static Uri getMediaScannerUri() {
        return AUTHORITY_URI.buildUpon().appendPath("none").appendPath("media_scanner").build();
    }

    public static String getVersion(Context context) {
        return getVersion(context, VOLUME_EXTERNAL_PRIMARY);
    }

    public static String getVersion(Context context, String volumeName) {
        ContentProviderClient client;
        try {
            client = context.getContentResolver().acquireContentProviderClient(AUTHORITY);
            Bundle in = new Bundle();
            in.putString(Intent.EXTRA_TEXT, volumeName);
            String string = client.call(GET_VERSION_CALL, (String) null, in).getString(Intent.EXTRA_TEXT);
            if (client != null) {
                $closeResource((Throwable) null, client);
            }
            return string;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (Throwable th) {
            if (client != null) {
                $closeResource(r2, client);
            }
            throw th;
        }
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

    public static Uri getDocumentUri(Context context, Uri mediaUri) {
        ContentProviderClient client;
        ContentResolver resolver = context.getContentResolver();
        List<UriPermission> uriPermissions = resolver.getPersistedUriPermissions();
        try {
            client = resolver.acquireContentProviderClient(AUTHORITY);
            Bundle in = new Bundle();
            in.putParcelable("uri", mediaUri);
            in.putParcelableList(DocumentsContract.EXTRA_URI_PERMISSIONS, uriPermissions);
            Uri uri = (Uri) client.call(GET_DOCUMENT_URI_CALL, (String) null, in).getParcelable("uri");
            if (client != null) {
                $closeResource((Throwable) null, client);
            }
            return uri;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (Throwable th) {
            if (client != null) {
                $closeResource(r3, client);
            }
            throw th;
        }
    }

    public static Uri getMediaUri(Context context, Uri documentUri) {
        ContentProviderClient client;
        ContentResolver resolver = context.getContentResolver();
        List<UriPermission> uriPermissions = resolver.getPersistedUriPermissions();
        try {
            client = resolver.acquireContentProviderClient(AUTHORITY);
            Bundle in = new Bundle();
            in.putParcelable("uri", documentUri);
            in.putParcelableList(DocumentsContract.EXTRA_URI_PERMISSIONS, uriPermissions);
            Uri uri = (Uri) client.call(GET_MEDIA_URI_CALL, (String) null, in).getParcelable("uri");
            if (client != null) {
                $closeResource((Throwable) null, client);
            }
            return uri;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (Throwable th) {
            if (client != null) {
                $closeResource(r3, client);
            }
            throw th;
        }
    }

    public static long getContributedMediaSize(Context context, String packageName, UserHandle user) throws IOException {
        UserManager um = (UserManager) context.getSystemService(UserManager.class);
        if (!um.isUserUnlocked(user) || !um.isUserRunning(user)) {
            throw new IOException("User " + user + " must be unlocked and running");
        }
        try {
            ContentResolver resolver = context.createPackageContextAsUser(packageName, 0, user).getContentResolver();
            Bundle in = new Bundle();
            in.putString("android.intent.extra.PACKAGE_NAME", packageName);
            return resolver.call(AUTHORITY, GET_CONTRIBUTED_MEDIA_CALL, (String) null, in).getLong(Intent.EXTRA_INDEX);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static void deleteContributedMedia(Context context, String packageName, UserHandle user) throws IOException {
        UserManager um = (UserManager) context.getSystemService(UserManager.class);
        if (!um.isUserUnlocked(user) || !um.isUserRunning(user)) {
            throw new IOException("User " + user + " must be unlocked and running");
        }
        try {
            ContentResolver resolver = context.createPackageContextAsUser(packageName, 0, user).getContentResolver();
            Bundle in = new Bundle();
            in.putString("android.intent.extra.PACKAGE_NAME", packageName);
            resolver.call(AUTHORITY, DELETE_CONTRIBUTED_MEDIA_CALL, (String) null, in);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static Uri scanFile(Context context, File file) {
        return scan(context, SCAN_FILE_CALL, file, false);
    }

    public static Uri scanFileFromShell(Context context, File file) {
        return scan(context, SCAN_FILE_CALL, file, true);
    }

    public static void scanVolume(Context context, File file) {
        scan(context, SCAN_VOLUME_CALL, file, false);
    }

    private static Uri scan(Context context, String method, File file, boolean originatedFromShell) {
        ContentProviderClient client;
        try {
            client = context.getContentResolver().acquireContentProviderClient(AUTHORITY);
            Bundle in = new Bundle();
            in.putParcelable(Intent.EXTRA_STREAM, Uri.fromFile(file));
            in.putBoolean(EXTRA_ORIGINATED_FROM_SHELL, originatedFromShell);
            Uri uri = (Uri) client.call(method, (String) null, in).getParcelable(Intent.EXTRA_STREAM);
            if (client != null) {
                $closeResource((Throwable) null, client);
            }
            return uri;
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        } catch (Throwable th) {
            if (client != null) {
                $closeResource(r2, client);
            }
            throw th;
        }
    }
}
