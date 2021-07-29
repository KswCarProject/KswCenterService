package android.media;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.IAudioService;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.database.SortCursor;
import com.ibm.icu.text.PluralRules;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RingtoneManager {
    public static final String ACTION_RINGTONE_PICKER = "android.intent.action.RINGTONE_PICKER";
    public static final String EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS = "android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS";
    public static final String EXTRA_RINGTONE_DEFAULT_URI = "android.intent.extra.ringtone.DEFAULT_URI";
    public static final String EXTRA_RINGTONE_EXISTING_URI = "android.intent.extra.ringtone.EXISTING_URI";
    @Deprecated
    public static final String EXTRA_RINGTONE_INCLUDE_DRM = "android.intent.extra.ringtone.INCLUDE_DRM";
    public static final String EXTRA_RINGTONE_PICKED_URI = "android.intent.extra.ringtone.PICKED_URI";
    public static final String EXTRA_RINGTONE_SHOW_DEFAULT = "android.intent.extra.ringtone.SHOW_DEFAULT";
    public static final String EXTRA_RINGTONE_SHOW_SILENT = "android.intent.extra.ringtone.SHOW_SILENT";
    public static final String EXTRA_RINGTONE_TITLE = "android.intent.extra.ringtone.TITLE";
    public static final String EXTRA_RINGTONE_TYPE = "android.intent.extra.ringtone.TYPE";
    public static final int ID_COLUMN_INDEX = 0;
    private static final String[] INTERNAL_COLUMNS = {"_id", "title", "title", "title_key"};
    private static final String[] MEDIA_COLUMNS = {"_id", "title", "title", "title_key"};
    private static final String TAG = "RingtoneManager";
    public static final int TITLE_COLUMN_INDEX = 1;
    public static final int TYPE_ALARM = 4;
    public static final int TYPE_ALL = 7;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 1;
    public static final int URI_COLUMN_INDEX = 2;
    private final Activity mActivity;
    private final Context mContext;
    @UnsupportedAppUsage
    private Cursor mCursor;
    private final List<String> mFilterColumns;
    private boolean mIncludeParentRingtones;
    private Ringtone mPreviousRingtone;
    private boolean mStopPreviousRingtone;
    private int mType;

    public RingtoneManager(Activity activity) {
        this(activity, false);
    }

    public RingtoneManager(Activity activity, boolean includeParentRingtones) {
        this.mType = 1;
        this.mFilterColumns = new ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = activity;
        this.mContext = activity;
        setType(this.mType);
        this.mIncludeParentRingtones = includeParentRingtones;
    }

    public RingtoneManager(Context context) {
        this(context, false);
    }

    public RingtoneManager(Context context, boolean includeParentRingtones) {
        this.mType = 1;
        this.mFilterColumns = new ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = null;
        this.mContext = context;
        setType(this.mType);
        this.mIncludeParentRingtones = includeParentRingtones;
    }

    public void setType(int type) {
        if (this.mCursor == null) {
            this.mType = type;
            setFilterColumnsList(type);
            return;
        }
        throw new IllegalStateException("Setting filter columns should be done before querying for ringtones.");
    }

    public int inferStreamType() {
        int i = this.mType;
        if (i != 2) {
            return i != 4 ? 2 : 4;
        }
        return 5;
    }

    public void setStopPreviousRingtone(boolean stopPreviousRingtone) {
        this.mStopPreviousRingtone = stopPreviousRingtone;
    }

    public boolean getStopPreviousRingtone() {
        return this.mStopPreviousRingtone;
    }

    public void stopPreviousRingtone() {
        if (this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
    }

    @Deprecated
    public boolean getIncludeDrm() {
        return false;
    }

    @Deprecated
    public void setIncludeDrm(boolean includeDrm) {
        if (includeDrm) {
            Log.w(TAG, "setIncludeDrm no longer supported");
        }
    }

    public Cursor getCursor() {
        Cursor parentRingtonesCursor;
        if (this.mCursor != null && this.mCursor.requery()) {
            return this.mCursor;
        }
        ArrayList<Cursor> ringtoneCursors = new ArrayList<>();
        ringtoneCursors.add(getInternalRingtones());
        ringtoneCursors.add(getMediaRingtones());
        if (this.mIncludeParentRingtones && (parentRingtonesCursor = getParentProfileRingtones()) != null) {
            ringtoneCursors.add(parentRingtonesCursor);
        }
        SortCursor sortCursor = new SortCursor((Cursor[]) ringtoneCursors.toArray(new Cursor[ringtoneCursors.size()]), "title_key");
        this.mCursor = sortCursor;
        return sortCursor;
    }

    private Cursor getParentProfileRingtones() {
        Context parentContext;
        UserInfo parentInfo = UserManager.get(this.mContext).getProfileParent(this.mContext.getUserId());
        if (parentInfo == null || parentInfo.id == this.mContext.getUserId() || (parentContext = createPackageContextAsUser(this.mContext, parentInfo.id)) == null) {
            return null;
        }
        return new ExternalRingtonesCursorWrapper(getMediaRingtones(parentContext), ContentProvider.maybeAddUserId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, parentInfo.id));
    }

    public Ringtone getRingtone(int position) {
        if (this.mStopPreviousRingtone && this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
        this.mPreviousRingtone = getRingtone(this.mContext, getRingtoneUri(position), inferStreamType());
        return this.mPreviousRingtone;
    }

    public Uri getRingtoneUri(int position) {
        if (this.mCursor == null || !this.mCursor.moveToPosition(position)) {
            return null;
        }
        return getUriFromCursor(this.mContext, this.mCursor);
    }

    private static Uri getUriFromCursor(Context context, Cursor cursor) {
        return context.getContentResolver().canonicalizeOrElse(ContentUris.withAppendedId(Uri.parse(cursor.getString(2)), cursor.getLong(0)));
    }

    public int getRingtonePosition(Uri ringtoneUri) {
        if (ringtoneUri == null) {
            return -1;
        }
        long ringtoneId = ContentUris.parseId(ringtoneUri);
        Cursor cursor = getCursor();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            if (ringtoneId == cursor.getLong(0)) {
                return cursor.getPosition();
            }
        }
        return -1;
    }

    public static Uri getValidRingtoneUri(Context context) {
        RingtoneManager rm = new RingtoneManager(context);
        Uri uri = getValidRingtoneUriFromCursorAndClose(context, rm.getInternalRingtones());
        if (uri == null) {
            return getValidRingtoneUriFromCursorAndClose(context, rm.getMediaRingtones());
        }
        return uri;
    }

    private static Uri getValidRingtoneUriFromCursorAndClose(Context context, Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Uri uri = null;
        if (cursor.moveToFirst()) {
            uri = getUriFromCursor(context, cursor);
        }
        cursor.close();
        return uri;
    }

    @UnsupportedAppUsage
    private Cursor getInternalRingtones() {
        return new ExternalRingtonesCursorWrapper(query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, INTERNAL_COLUMNS, constructBooleanTrueWhereClause(this.mFilterColumns), (String[]) null, "title_key"), MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private Cursor getMediaRingtones() {
        return new ExternalRingtonesCursorWrapper(getMediaRingtones(this.mContext), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    @UnsupportedAppUsage
    private Cursor getMediaRingtones(Context context) {
        return query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MEDIA_COLUMNS, constructBooleanTrueWhereClause(this.mFilterColumns), (String[]) null, "title_key", context);
    }

    private void setFilterColumnsList(int type) {
        List<String> columns = this.mFilterColumns;
        columns.clear();
        if ((type & 1) != 0) {
            columns.add(MediaStore.Audio.AudioColumns.IS_RINGTONE);
        }
        if ((type & 2) != 0) {
            columns.add(MediaStore.Audio.AudioColumns.IS_NOTIFICATION);
        }
        if ((type & 4) != 0) {
            columns.add(MediaStore.Audio.AudioColumns.IS_ALARM);
        }
    }

    private static String constructBooleanTrueWhereClause(List<String> columns) {
        if (columns == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = columns.size() - 1; i >= 0; i--) {
            sb.append(columns.get(i));
            sb.append("=1 or ");
        }
        if (columns.size() > 0) {
            sb.setLength(sb.length() - 4);
        }
        sb.append(")");
        return sb.toString();
    }

    private Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return query(uri, projection, selection, selectionArgs, sortOrder, this.mContext);
    }

    private Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, Context context) {
        if (this.mActivity != null) {
            return this.mActivity.managedQuery(uri, projection, selection, selectionArgs, sortOrder);
        }
        return context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    public static Ringtone getRingtone(Context context, Uri ringtoneUri) {
        return getRingtone(context, ringtoneUri, -1);
    }

    public static Ringtone getRingtone(Context context, Uri ringtoneUri, VolumeShaper.Configuration volumeShaperConfig) {
        return getRingtone(context, ringtoneUri, -1, volumeShaperConfig);
    }

    @UnsupportedAppUsage
    private static Ringtone getRingtone(Context context, Uri ringtoneUri, int streamType) {
        return getRingtone(context, ringtoneUri, streamType, (VolumeShaper.Configuration) null);
    }

    @UnsupportedAppUsage
    private static Ringtone getRingtone(Context context, Uri ringtoneUri, int streamType, VolumeShaper.Configuration volumeShaperConfig) {
        try {
            Ringtone r = new Ringtone(context, true);
            if (streamType >= 0) {
                r.setStreamType(streamType);
            }
            r.setUri(ringtoneUri, volumeShaperConfig);
            return r;
        } catch (Exception ex) {
            Log.e(TAG, "Failed to open ringtone " + ringtoneUri + PluralRules.KEYWORD_RULE_SEPARATOR + ex);
            return null;
        }
    }

    public static void disableSyncFromParent(Context userContext) {
        try {
            IAudioService.Stub.asInterface(ServiceManager.getService("audio")).disableRingtoneSync(userContext.getUserId());
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to disable ringtone sync.");
        }
    }

    public static void enableSyncFromParent(Context userContext) {
        Settings.Secure.putIntForUser(userContext.getContentResolver(), Settings.Secure.SYNC_PARENT_SOUNDS, 1, userContext.getUserId());
    }

    public static Uri getActualDefaultRingtoneUri(Context context, int type) {
        String setting = getSettingForType(type);
        Uri ringtoneUri = null;
        if (setting == null) {
            return null;
        }
        String uriString = Settings.System.getStringForUser(context.getContentResolver(), setting, context.getUserId());
        if (uriString != null) {
            ringtoneUri = Uri.parse(uriString);
        }
        if (ringtoneUri == null || ContentProvider.getUserIdFromUri(ringtoneUri) != context.getUserId()) {
            return ringtoneUri;
        }
        return ContentProvider.getUriWithoutUserId(ringtoneUri);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005d, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005e, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0062, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0063, code lost:
        r8 = r7;
        r7 = r6;
        r6 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setActualDefaultRingtoneUri(android.content.Context r9, int r10, android.net.Uri r11) {
        /*
            java.lang.String r0 = getSettingForType(r10)
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            android.content.ContentResolver r1 = r9.getContentResolver()
            java.lang.String r2 = "sync_parent_sounds"
            r3 = 0
            int r4 = r9.getUserId()
            int r2 = android.provider.Settings.Secure.getIntForUser(r1, r2, r3, r4)
            r3 = 1
            if (r2 != r3) goto L_0x001d
            disableSyncFromParent(r9)
        L_0x001d:
            boolean r2 = isInternalRingtoneUri(r11)
            if (r2 != 0) goto L_0x002b
            int r2 = r9.getUserId()
            android.net.Uri r11 = android.content.ContentProvider.maybeAddUserId(r11, r2)
        L_0x002b:
            r2 = 0
            if (r11 == 0) goto L_0x0034
            java.lang.String r3 = r11.toString()
            goto L_0x0035
        L_0x0034:
            r3 = r2
        L_0x0035:
            int r4 = r9.getUserId()
            android.provider.Settings.System.putStringForUser(r1, r0, r3, r4)
            if (r11 == 0) goto L_0x008d
            int r3 = r9.getUserId()
            android.net.Uri r3 = getCacheForType(r10, r3)
            java.io.InputStream r4 = openRingtone(r9, r11)     // Catch:{ IOException -> 0x0076 }
            java.io.OutputStream r5 = r1.openOutputStream(r3)     // Catch:{ Throwable -> 0x006e }
            android.os.FileUtils.copy((java.io.InputStream) r4, (java.io.OutputStream) r5)     // Catch:{ Throwable -> 0x0060, all -> 0x005d }
            if (r5 == 0) goto L_0x0057
            $closeResource(r2, r5)     // Catch:{ Throwable -> 0x006e }
        L_0x0057:
            if (r4 == 0) goto L_0x005c
            $closeResource(r2, r4)     // Catch:{ IOException -> 0x0076 }
        L_0x005c:
            goto L_0x008d
        L_0x005d:
            r6 = move-exception
            r7 = r2
            goto L_0x0066
        L_0x0060:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0062 }
        L_0x0062:
            r7 = move-exception
            r8 = r7
            r7 = r6
            r6 = r8
        L_0x0066:
            if (r5 == 0) goto L_0x006b
            $closeResource(r7, r5)     // Catch:{ Throwable -> 0x006e }
        L_0x006b:
            throw r6     // Catch:{ Throwable -> 0x006e }
        L_0x006c:
            r5 = move-exception
            goto L_0x0070
        L_0x006e:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x006c }
        L_0x0070:
            if (r4 == 0) goto L_0x0075
            $closeResource(r2, r4)     // Catch:{ IOException -> 0x0076 }
        L_0x0075:
            throw r5     // Catch:{ IOException -> 0x0076 }
        L_0x0076:
            r2 = move-exception
            java.lang.String r4 = "RingtoneManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to cache ringtone: "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            android.util.Log.w((java.lang.String) r4, (java.lang.String) r5)
        L_0x008d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.RingtoneManager.setActualDefaultRingtoneUri(android.content.Context, int, android.net.Uri):void");
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

    private static boolean isInternalRingtoneUri(Uri uri) {
        return isRingtoneUriInStorage(uri, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private static boolean isExternalRingtoneUri(Uri uri) {
        return isRingtoneUriInStorage(uri, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private static boolean isRingtoneUriInStorage(Uri ringtone, Uri storage) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(ringtone);
        if (uriWithoutUserId == null) {
            return false;
        }
        return uriWithoutUserId.toString().startsWith(storage.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0060, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0061, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0065, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0066, code lost:
        r8 = r7;
        r7 = r6;
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006d, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        if (r3 != null) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0073, code lost:
        $closeResource(r4, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0076, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.net.Uri addCustomExternalRingtone(android.net.Uri r10, int r11) throws java.io.FileNotFoundException, java.lang.IllegalArgumentException, java.io.IOException {
        /*
            r9 = this;
            java.lang.String r0 = android.os.Environment.getExternalStorageState()
            java.lang.String r1 = "mounted"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0093
            android.content.Context r0 = r9.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r0 = r0.getType(r10)
            if (r0 == 0) goto L_0x0077
            java.lang.String r1 = "audio/"
            boolean r1 = r0.startsWith(r1)
            if (r1 != 0) goto L_0x0029
            java.lang.String r1 = "application/ogg"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0077
        L_0x0029:
            java.lang.String r1 = getExternalDirectoryForType(r11)
            android.content.Context r2 = r9.mContext
            android.content.Context r3 = r9.mContext
            java.lang.String r3 = android.media.Utils.getFileDisplayNameFromUri(r3, r10)
            java.lang.String r3 = android.os.FileUtils.buildValidFatFilename(r3)
            java.io.File r2 = android.media.Utils.getUniqueExternalFile(r2, r1, r3, r0)
            android.content.Context r3 = r9.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            java.io.InputStream r3 = r3.openInputStream(r10)
            r4 = 0
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x006f }
            r5.<init>(r2)     // Catch:{ Throwable -> 0x006f }
            android.os.FileUtils.copy((java.io.InputStream) r3, (java.io.OutputStream) r5)     // Catch:{ Throwable -> 0x0063, all -> 0x0060 }
            $closeResource(r4, r5)     // Catch:{ Throwable -> 0x006f }
            if (r3 == 0) goto L_0x0059
            $closeResource(r4, r3)
        L_0x0059:
            android.content.Context r3 = r9.mContext
            android.net.Uri r3 = android.provider.MediaStore.scanFile(r3, r2)
            return r3
        L_0x0060:
            r6 = move-exception
            r7 = r4
            goto L_0x0069
        L_0x0063:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0065 }
        L_0x0065:
            r7 = move-exception
            r8 = r7
            r7 = r6
            r6 = r8
        L_0x0069:
            $closeResource(r7, r5)     // Catch:{ Throwable -> 0x006f }
            throw r6     // Catch:{ Throwable -> 0x006f }
        L_0x006d:
            r5 = move-exception
            goto L_0x0071
        L_0x006f:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x006d }
        L_0x0071:
            if (r3 == 0) goto L_0x0076
            $closeResource(r4, r3)
        L_0x0076:
            throw r5
        L_0x0077:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Ringtone file must have MIME type \"audio/*\". Given file has MIME type \""
            r2.append(r3)
            r2.append(r0)
            java.lang.String r3 = "\""
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0093:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "External storage is not mounted. Unable to install ringtones."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.RingtoneManager.addCustomExternalRingtone(android.net.Uri, int):android.net.Uri");
    }

    private static final String getExternalDirectoryForType(int type) {
        if (type == 4) {
            return Environment.DIRECTORY_ALARMS;
        }
        switch (type) {
            case 1:
                return Environment.DIRECTORY_RINGTONES;
            case 2:
                return Environment.DIRECTORY_NOTIFICATIONS;
            default:
                throw new IllegalArgumentException("Unsupported ringtone type: " + type);
        }
    }

    private static InputStream openRingtone(Context context, Uri uri) throws IOException {
        try {
            return context.getContentResolver().openInputStream(uri);
        } catch (IOException | SecurityException e) {
            Log.w(TAG, "Failed to open directly; attempting failover: " + e);
            try {
                return new ParcelFileDescriptor.AutoCloseInputStream(((AudioManager) context.getSystemService(AudioManager.class)).getRingtonePlayer().openRingtone(uri));
            } catch (Exception e2) {
                throw new IOException(e2);
            }
        }
    }

    private static String getSettingForType(int type) {
        if ((type & 1) != 0) {
            return Settings.System.RINGTONE;
        }
        if ((type & 2) != 0) {
            return Settings.System.NOTIFICATION_SOUND;
        }
        if ((type & 4) != 0) {
            return Settings.System.ALARM_ALERT;
        }
        return null;
    }

    public static Uri getCacheForType(int type) {
        return getCacheForType(type, UserHandle.getCallingUserId());
    }

    public static Uri getCacheForType(int type, int userId) {
        if ((type & 1) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.RINGTONE_CACHE_URI, userId);
        }
        if ((type & 2) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.NOTIFICATION_SOUND_CACHE_URI, userId);
        }
        if ((type & 4) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.ALARM_ALERT_CACHE_URI, userId);
        }
        return null;
    }

    public static boolean isDefault(Uri ringtoneUri) {
        return getDefaultType(ringtoneUri) != -1;
    }

    public static int getDefaultType(Uri defaultRingtoneUri) {
        Uri defaultRingtoneUri2 = ContentProvider.getUriWithoutUserId(defaultRingtoneUri);
        if (defaultRingtoneUri2 == null) {
            return -1;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_RINGTONE_URI)) {
            return 1;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_NOTIFICATION_URI)) {
            return 2;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_ALARM_ALERT_URI)) {
            return 4;
        }
        return -1;
    }

    public static Uri getDefaultUri(int type) {
        if ((type & 1) != 0) {
            return Settings.System.DEFAULT_RINGTONE_URI;
        }
        if ((type & 2) != 0) {
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        if ((type & 4) != 0) {
            return Settings.System.DEFAULT_ALARM_ALERT_URI;
        }
        return null;
    }

    public static AssetFileDescriptor openDefaultRingtoneUri(Context context, Uri uri) throws FileNotFoundException {
        int type = getDefaultType(uri);
        Uri cacheUri = getCacheForType(type, context.getUserId());
        Uri actualUri = getActualDefaultRingtoneUri(context, type);
        ContentResolver resolver = context.getContentResolver();
        AssetFileDescriptor afd = null;
        if (cacheUri != null && (afd = resolver.openAssetFileDescriptor(cacheUri, "r")) != null) {
            return afd;
        }
        if (actualUri != null) {
            return resolver.openAssetFileDescriptor(actualUri, "r");
        }
        return afd;
    }

    public boolean hasHapticChannels(int position) {
        return hasHapticChannels(getRingtoneUri(position));
    }

    public static boolean hasHapticChannels(Uri ringtoneUri) {
        return AudioManager.hasHapticChannels(ringtoneUri);
    }

    private static Context createPackageContextAsUser(Context context, int userId) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(userId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to create package context", e);
            return null;
        }
    }
}
