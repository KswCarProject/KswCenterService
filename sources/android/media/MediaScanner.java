package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.drm.DrmManagerClient;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.MediaStore;
import android.provider.Settings;
import android.sax.ElementListener;
import android.sax.RootElement;
import android.telecom.Logging.Session;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.Xml;
import dalvik.system.CloseGuard;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

@Deprecated
public class MediaScanner implements AutoCloseable {
    private static final String ALARMS_DIR = "/alarms/";
    private static final String AUDIOBOOKS_DIR = "/audiobooks/";
    private static final int DATE_MODIFIED_PLAYLISTS_COLUMN_INDEX = 2;
    private static final String DEFAULT_RINGTONE_PROPERTY_PREFIX = "ro.config.";
    private static final boolean ENABLE_BULK_INSERTS = true;
    private static final int FILES_PRESCAN_DATE_MODIFIED_COLUMN_INDEX = 3;
    private static final int FILES_PRESCAN_FORMAT_COLUMN_INDEX = 2;
    private static final int FILES_PRESCAN_ID_COLUMN_INDEX = 0;
    private static final int FILES_PRESCAN_MEDIA_TYPE_COLUMN_INDEX = 4;
    private static final int FILES_PRESCAN_PATH_COLUMN_INDEX = 1;
    @UnsupportedAppUsage
    private static final String[] FILES_PRESCAN_PROJECTION = {"_id", "_data", "format", "date_modified", "media_type"};
    /* access modifiers changed from: private */
    public static final String[] ID3_GENRES = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "Britpop", null, "Polsk Punk", "Beat", "Christian Gangsta", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "JPop", "Synthpop"};
    private static final int ID_PLAYLISTS_COLUMN_INDEX = 0;
    private static final String[] ID_PROJECTION = {"_id"};
    public static final String LAST_INTERNAL_SCAN_FINGERPRINT = "lastScanFingerprint";
    private static final String MUSIC_DIR = "/music/";
    private static final String NOTIFICATIONS_DIR = "/notifications/";
    private static final String OEM_SOUNDS_DIR = (Environment.getOemDirectory() + "/media/audio");
    private static final int PATH_PLAYLISTS_COLUMN_INDEX = 1;
    private static final String[] PLAYLIST_MEMBERS_PROJECTION = {MediaStore.Audio.Playlists.Members.PLAYLIST_ID};
    private static final String PODCASTS_DIR = "/podcasts/";
    private static final String PRODUCT_SOUNDS_DIR = (Environment.getProductDirectory() + "/media/audio");
    private static final String RINGTONES_DIR = "/ringtones/";
    public static final String SCANNED_BUILD_PREFS_NAME = "MediaScanBuild";
    private static final String SYSTEM_SOUNDS_DIR = (Environment.getRootDirectory() + "/media/audio");
    private static final String TAG = "MediaScanner";
    private static HashMap<String, String> mMediaPaths = new HashMap<>();
    private static HashMap<String, String> mNoMediaPaths = new HashMap<>();
    /* access modifiers changed from: private */
    public static String sLastInternalScanFingerprint;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public final Uri mAudioUri;
    /* access modifiers changed from: private */
    public final BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
    @UnsupportedAppUsage
    private final MyMediaScannerClient mClient = new MyMediaScannerClient();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public final Context mContext;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public String mDefaultAlarmAlertFilename;
    /* access modifiers changed from: private */
    public boolean mDefaultAlarmSet;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public String mDefaultNotificationFilename;
    /* access modifiers changed from: private */
    public boolean mDefaultNotificationSet;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public String mDefaultRingtoneFilename;
    /* access modifiers changed from: private */
    public boolean mDefaultRingtoneSet;
    /* access modifiers changed from: private */
    public DrmManagerClient mDrmManagerClient = null;
    private final Uri mFilesFullUri;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public final Uri mFilesUri;
    /* access modifiers changed from: private */
    public final Uri mImagesUri;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public MediaInserter mMediaInserter;
    /* access modifiers changed from: private */
    public final ContentProviderClient mMediaProvider;
    /* access modifiers changed from: private */
    public int mMtpObjectHandle;
    private long mNativeContext;
    private int mOriginalCount;
    @UnsupportedAppUsage
    private final String mPackageName;
    /* access modifiers changed from: private */
    public final ArrayList<FileEntry> mPlayLists = new ArrayList<>();
    private final ArrayList<PlaylistEntry> mPlaylistEntries = new ArrayList<>();
    /* access modifiers changed from: private */
    public final Uri mPlaylistsUri;
    /* access modifiers changed from: private */
    public final boolean mProcessGenres;
    /* access modifiers changed from: private */
    public final boolean mProcessPlaylists;
    /* access modifiers changed from: private */
    public final Uri mVideoUri;
    private final String mVolumeName;

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup();

    private native void processDirectory(String str, MediaScannerClient mediaScannerClient);

    /* access modifiers changed from: private */
    public native boolean processFile(String str, String str2, MediaScannerClient mediaScannerClient);

    @UnsupportedAppUsage
    private native void setLocale(String str);

    public native byte[] extractAlbumArt(FileDescriptor fileDescriptor);

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    private static class FileEntry {
        int mFormat;
        long mLastModified;
        @UnsupportedAppUsage
        boolean mLastModifiedChanged;
        int mMediaType;
        String mPath;
        @UnsupportedAppUsage
        long mRowId;

        @Deprecated
        @UnsupportedAppUsage
        FileEntry(long rowId, String path, long lastModified, int format) {
            this(rowId, path, lastModified, format, 0);
        }

        FileEntry(long rowId, String path, long lastModified, int format, int mediaType) {
            this.mRowId = rowId;
            this.mPath = path;
            this.mLastModified = lastModified;
            this.mFormat = format;
            this.mMediaType = mediaType;
            this.mLastModifiedChanged = false;
        }

        public String toString() {
            return this.mPath + " mRowId: " + this.mRowId;
        }
    }

    private static class PlaylistEntry {
        long bestmatchid;
        int bestmatchlevel;
        String path;

        private PlaylistEntry() {
        }
    }

    @UnsupportedAppUsage
    public MediaScanner(Context c, String volumeName) {
        native_setup();
        this.mContext = c;
        this.mPackageName = c.getPackageName();
        this.mVolumeName = volumeName;
        this.mBitmapOptions.inSampleSize = 1;
        this.mBitmapOptions.inJustDecodeBounds = true;
        setDefaultRingtoneFileNames();
        this.mMediaProvider = this.mContext.getContentResolver().acquireContentProviderClient(MediaStore.AUTHORITY);
        if (sLastInternalScanFingerprint == null) {
            sLastInternalScanFingerprint = this.mContext.getSharedPreferences(SCANNED_BUILD_PREFS_NAME, 0).getString(LAST_INTERNAL_SCAN_FINGERPRINT, new String());
        }
        this.mAudioUri = MediaStore.Audio.Media.getContentUri(volumeName);
        this.mVideoUri = MediaStore.Video.Media.getContentUri(volumeName);
        this.mImagesUri = MediaStore.Images.Media.getContentUri(volumeName);
        this.mFilesUri = MediaStore.Files.getContentUri(volumeName);
        this.mFilesFullUri = MediaStore.setIncludeTrashed(MediaStore.setIncludePending(this.mFilesUri.buildUpon().appendQueryParameter("nonotify", "1").build()));
        if (!volumeName.equals(MediaStore.VOLUME_INTERNAL)) {
            this.mProcessPlaylists = true;
            this.mProcessGenres = true;
            this.mPlaylistsUri = MediaStore.Audio.Playlists.getContentUri(volumeName);
        } else {
            this.mProcessPlaylists = false;
            this.mProcessGenres = false;
            this.mPlaylistsUri = null;
        }
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        if (locale != null) {
            String language = locale.getLanguage();
            String country = locale.getCountry();
            if (language != null) {
                if (country != null) {
                    setLocale(language + Session.SESSION_SEPARATION_CHAR_CHILD + country);
                } else {
                    setLocale(language);
                }
            }
        }
        this.mCloseGuard.open("close");
    }

    private void setDefaultRingtoneFileNames() {
        this.mDefaultRingtoneFilename = SystemProperties.get("ro.config.ringtone");
        this.mDefaultNotificationFilename = SystemProperties.get("ro.config.notification_sound");
        this.mDefaultAlarmAlertFilename = SystemProperties.get("ro.config.alarm_alert");
    }

    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public boolean isDrmEnabled() {
        String prop = SystemProperties.get("drm.service.enabled");
        return prop != null && prop.equals("true");
    }

    private class MyMediaScannerClient implements MediaScannerClient {
        private String mAlbum;
        private String mAlbumArtist;
        private String mArtist;
        private int mColorRange;
        private int mColorStandard;
        private int mColorTransfer;
        private int mCompilation;
        private String mComposer;
        private long mDate;
        private final SimpleDateFormat mDateFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        private int mDuration;
        private long mFileSize;
        @Deprecated
        @UnsupportedAppUsage
        private int mFileType;
        private String mGenre;
        private int mHeight;
        @UnsupportedAppUsage
        private boolean mIsDrm;
        private long mLastModified;
        @UnsupportedAppUsage
        private String mMimeType;
        @UnsupportedAppUsage
        private boolean mNoMedia;
        @UnsupportedAppUsage
        private String mPath;
        private boolean mScanSuccess;
        private String mTitle;
        private int mTrack;
        private int mWidth;
        private String mWriter;
        private int mYear;

        public MyMediaScannerClient() {
            this.mDateFormatter.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        }

        @UnsupportedAppUsage
        public FileEntry beginFile(String path, String mimeType, long lastModified, long fileSize, boolean isDirectory, boolean noMedia) {
            int i;
            boolean noMedia2;
            String str = path;
            long j = lastModified;
            this.mMimeType = mimeType;
            this.mFileSize = fileSize;
            this.mIsDrm = false;
            this.mScanSuccess = true;
            if (!isDirectory) {
                if (noMedia || !MediaScanner.isNoMediaFile(path)) {
                    noMedia2 = noMedia;
                } else {
                    noMedia2 = true;
                }
                this.mNoMedia = noMedia2;
                if (this.mMimeType == null) {
                    this.mMimeType = MediaFile.getMimeTypeForFile(path);
                }
                if (MediaScanner.this.isDrmEnabled() && MediaFile.isDrmMimeType(this.mMimeType)) {
                    getMimeTypeFromDrm(path);
                }
                boolean z = noMedia2;
            } else {
                boolean z2 = noMedia;
            }
            FileEntry entry = MediaScanner.this.makeEntryFor(str);
            long delta = entry != null ? j - entry.mLastModified : 0;
            boolean wasModified = delta > 1 || delta < -1;
            if (entry == null || wasModified) {
                if (wasModified) {
                    entry.mLastModified = j;
                    i = 0;
                } else {
                    FileEntry fileEntry = entry;
                    i = 0;
                    entry = new FileEntry(0, path, lastModified, isDirectory ? 12289 : 0, 0);
                }
                entry.mLastModifiedChanged = true;
            } else {
                i = 0;
            }
            if (!MediaScanner.this.mProcessPlaylists || !MediaFile.isPlayListMimeType(this.mMimeType)) {
                this.mArtist = null;
                this.mAlbumArtist = null;
                this.mAlbum = null;
                this.mTitle = null;
                this.mComposer = null;
                this.mGenre = null;
                this.mTrack = i;
                this.mYear = i;
                this.mDuration = i;
                this.mPath = str;
                this.mDate = 0;
                this.mLastModified = j;
                this.mWriter = null;
                this.mCompilation = i;
                this.mWidth = i;
                this.mHeight = i;
                this.mColorStandard = -1;
                this.mColorTransfer = -1;
                this.mColorRange = -1;
                return entry;
            }
            MediaScanner.this.mPlayLists.add(entry);
            return null;
        }

        @UnsupportedAppUsage
        public void scanFile(String path, long lastModified, long fileSize, boolean isDirectory, boolean noMedia) {
            doScanFile(path, (String) null, lastModified, fileSize, isDirectory, false, noMedia);
        }

        /* JADX WARNING: Removed duplicated region for block: B:105:0x0178  */
        /* JADX WARNING: Removed duplicated region for block: B:115:0x0189  */
        /* JADX WARNING: Removed duplicated region for block: B:125:0x019a  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00cc A[SYNTHETIC, Splitter:B:38:0x00cc] */
        /* JADX WARNING: Removed duplicated region for block: B:67:0x012d A[SYNTHETIC, Splitter:B:67:0x012d] */
        /* JADX WARNING: Removed duplicated region for block: B:75:0x0146  */
        /* JADX WARNING: Removed duplicated region for block: B:85:0x0156  */
        /* JADX WARNING: Removed duplicated region for block: B:95:0x0167  */
        @android.annotation.UnsupportedAppUsage
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.net.Uri doScanFile(java.lang.String r23, java.lang.String r24, long r25, long r27, boolean r29, boolean r30, boolean r31) {
            /*
                r22 = this;
                r10 = r22
                r0 = 0
                r11 = r0
                r1 = r22
                r2 = r23
                r3 = r24
                r4 = r25
                r6 = r27
                r8 = r29
                r9 = r31
                android.media.MediaScanner$FileEntry r1 = r1.beginFile(r2, r3, r4, r6, r8, r9)     // Catch:{ RemoteException -> 0x01d6 }
                r9 = r1
                if (r9 != 0) goto L_0x001a
                return r0
            L_0x001a:
                android.media.MediaScanner r0 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x01d6 }
                int r0 = r0.mMtpObjectHandle     // Catch:{ RemoteException -> 0x01d6 }
                if (r0 == 0) goto L_0x002e
                r0 = 0
                r9.mRowId = r0     // Catch:{ RemoteException -> 0x0027 }
                goto L_0x002e
            L_0x0027:
                r0 = move-exception
                r21 = r23
                r12 = r30
                goto L_0x01dd
            L_0x002e:
                java.lang.String r0 = r9.mPath     // Catch:{ RemoteException -> 0x01d6 }
                if (r0 == 0) goto L_0x00c8
                android.media.MediaScanner r0 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r0.mDefaultNotificationSet     // Catch:{ RemoteException -> 0x0027 }
                if (r0 != 0) goto L_0x0048
                java.lang.String r0 = r9.mPath     // Catch:{ RemoteException -> 0x0027 }
                android.media.MediaScanner r1 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r1 = r1.mDefaultNotificationFilename     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r10.doesPathHaveFilename(r0, r1)     // Catch:{ RemoteException -> 0x0027 }
                if (r0 != 0) goto L_0x0074
            L_0x0048:
                android.media.MediaScanner r0 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r0.mDefaultRingtoneSet     // Catch:{ RemoteException -> 0x0027 }
                if (r0 != 0) goto L_0x005e
                java.lang.String r0 = r9.mPath     // Catch:{ RemoteException -> 0x0027 }
                android.media.MediaScanner r1 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r1 = r1.mDefaultRingtoneFilename     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r10.doesPathHaveFilename(r0, r1)     // Catch:{ RemoteException -> 0x0027 }
                if (r0 != 0) goto L_0x0074
            L_0x005e:
                android.media.MediaScanner r0 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r0.mDefaultAlarmSet     // Catch:{ RemoteException -> 0x0027 }
                if (r0 != 0) goto L_0x0095
                java.lang.String r0 = r9.mPath     // Catch:{ RemoteException -> 0x0027 }
                android.media.MediaScanner r1 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r1 = r1.mDefaultAlarmAlertFilename     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r10.doesPathHaveFilename(r0, r1)     // Catch:{ RemoteException -> 0x0027 }
                if (r0 == 0) goto L_0x0095
            L_0x0074:
                java.lang.String r0 = "MediaScanner"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0027 }
                r1.<init>()     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r2 = "forcing rescan of "
                r1.append(r2)     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r2 = r9.mPath     // Catch:{ RemoteException -> 0x0027 }
                r1.append(r2)     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r2 = "since ringtone setting didn't finish"
                r1.append(r2)     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r1 = r1.toString()     // Catch:{ RemoteException -> 0x0027 }
                android.util.Log.w((java.lang.String) r0, (java.lang.String) r1)     // Catch:{ RemoteException -> 0x0027 }
                r0 = 1
            L_0x0093:
                r12 = r0
                goto L_0x00ca
            L_0x0095:
                java.lang.String r0 = r9.mPath     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = android.media.MediaScanner.isSystemSoundWithMetadata(r0)     // Catch:{ RemoteException -> 0x0027 }
                if (r0 == 0) goto L_0x00c8
                java.lang.String r0 = android.os.Build.FINGERPRINT     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r1 = android.media.MediaScanner.sLastInternalScanFingerprint     // Catch:{ RemoteException -> 0x0027 }
                boolean r0 = r0.equals(r1)     // Catch:{ RemoteException -> 0x0027 }
                if (r0 != 0) goto L_0x00c8
                java.lang.String r0 = "MediaScanner"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x0027 }
                r1.<init>()     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r2 = "forcing rescan of "
                r1.append(r2)     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r2 = r9.mPath     // Catch:{ RemoteException -> 0x0027 }
                r1.append(r2)     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r2 = " since build fingerprint changed"
                r1.append(r2)     // Catch:{ RemoteException -> 0x0027 }
                java.lang.String r1 = r1.toString()     // Catch:{ RemoteException -> 0x0027 }
                android.util.Log.i(r0, r1)     // Catch:{ RemoteException -> 0x0027 }
                r0 = 1
                goto L_0x0093
            L_0x00c8:
                r12 = r30
            L_0x00ca:
                if (r9 == 0) goto L_0x01d1
                boolean r0 = r9.mLastModifiedChanged     // Catch:{ RemoteException -> 0x01cd }
                if (r0 != 0) goto L_0x00d2
                if (r12 == 0) goto L_0x01d1
            L_0x00d2:
                if (r31 == 0) goto L_0x00eb
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                r7 = 0
                r8 = 0
                r1 = r22
                r2 = r9
                android.net.Uri r0 = r1.endFile(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ RemoteException -> 0x00e6 }
                r11 = r0
                r21 = r23
                goto L_0x01d5
            L_0x00e6:
                r0 = move-exception
                r21 = r23
                goto L_0x01dd
            L_0x00eb:
                java.lang.String r0 = r10.mMimeType     // Catch:{ RemoteException -> 0x01cd }
                boolean r0 = android.media.MediaFile.isAudioMimeType(r0)     // Catch:{ RemoteException -> 0x01cd }
                java.lang.String r1 = r10.mMimeType     // Catch:{ RemoteException -> 0x01cd }
                boolean r1 = android.media.MediaFile.isVideoMimeType(r1)     // Catch:{ RemoteException -> 0x01cd }
                r13 = r1
                java.lang.String r1 = r10.mMimeType     // Catch:{ RemoteException -> 0x01cd }
                boolean r1 = android.media.MediaFile.isImageMimeType(r1)     // Catch:{ RemoteException -> 0x01cd }
                r14 = r1
                if (r0 != 0) goto L_0x0109
                if (r13 != 0) goto L_0x0109
                if (r14 == 0) goto L_0x0106
                goto L_0x0109
            L_0x0106:
                r7 = r23
                goto L_0x0119
            L_0x0109:
                java.io.File r1 = new java.io.File     // Catch:{ RemoteException -> 0x01cd }
                r2 = r23
                r1.<init>(r2)     // Catch:{ RemoteException -> 0x01cb }
                java.io.File r1 = android.os.Environment.maybeTranslateEmulatedPathToInternal(r1)     // Catch:{ RemoteException -> 0x01cb }
                java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ RemoteException -> 0x01cb }
                r7 = r1
            L_0x0119:
                if (r0 != 0) goto L_0x0121
                if (r13 == 0) goto L_0x011e
                goto L_0x0121
            L_0x011e:
                r6 = r24
                goto L_0x012b
            L_0x0121:
                android.media.MediaScanner r1 = android.media.MediaScanner.this     // Catch:{ RemoteException -> 0x01c7 }
                r6 = r24
                boolean r1 = r1.processFile(r7, r6, r10)     // Catch:{ RemoteException -> 0x01c7 }
                r10.mScanSuccess = r1     // Catch:{ RemoteException -> 0x01c7 }
            L_0x012b:
                if (r14 == 0) goto L_0x0139
                boolean r1 = r10.processImageFile(r7)     // Catch:{ RemoteException -> 0x0134 }
                r10.mScanSuccess = r1     // Catch:{ RemoteException -> 0x0134 }
                goto L_0x0139
            L_0x0134:
                r0 = move-exception
                r21 = r7
                goto L_0x01dd
            L_0x0139:
                java.util.Locale r1 = java.util.Locale.ROOT     // Catch:{ RemoteException -> 0x01c7 }
                java.lang.String r1 = r7.toLowerCase(r1)     // Catch:{ RemoteException -> 0x01c7 }
                r5 = r1
                boolean r1 = r10.mScanSuccess     // Catch:{ RemoteException -> 0x01c7 }
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L_0x0150
                java.lang.String r1 = "/ringtones/"
                int r1 = r5.indexOf(r1)     // Catch:{ RemoteException -> 0x0134 }
                if (r1 <= 0) goto L_0x0150
                r1 = r2
                goto L_0x0151
            L_0x0150:
                r1 = r3
            L_0x0151:
                r15 = r1
                boolean r1 = r10.mScanSuccess     // Catch:{ RemoteException -> 0x01c7 }
                if (r1 == 0) goto L_0x0160
                java.lang.String r1 = "/notifications/"
                int r1 = r5.indexOf(r1)     // Catch:{ RemoteException -> 0x0134 }
                if (r1 <= 0) goto L_0x0160
                r1 = r2
                goto L_0x0161
            L_0x0160:
                r1 = r3
            L_0x0161:
                r16 = r1
                boolean r1 = r10.mScanSuccess     // Catch:{ RemoteException -> 0x01c7 }
                if (r1 == 0) goto L_0x0171
                java.lang.String r1 = "/alarms/"
                int r1 = r5.indexOf(r1)     // Catch:{ RemoteException -> 0x0134 }
                if (r1 <= 0) goto L_0x0171
                r1 = r2
                goto L_0x0172
            L_0x0171:
                r1 = r3
            L_0x0172:
                r17 = r1
                boolean r1 = r10.mScanSuccess     // Catch:{ RemoteException -> 0x01c7 }
                if (r1 == 0) goto L_0x0182
                java.lang.String r1 = "/podcasts/"
                int r1 = r5.indexOf(r1)     // Catch:{ RemoteException -> 0x0134 }
                if (r1 <= 0) goto L_0x0182
                r1 = r2
                goto L_0x0183
            L_0x0182:
                r1 = r3
            L_0x0183:
                r18 = r1
                boolean r1 = r10.mScanSuccess     // Catch:{ RemoteException -> 0x01c7 }
                if (r1 == 0) goto L_0x0193
                java.lang.String r1 = "/audiobooks/"
                int r1 = r5.indexOf(r1)     // Catch:{ RemoteException -> 0x0134 }
                if (r1 <= 0) goto L_0x0193
                r1 = r2
                goto L_0x0194
            L_0x0193:
                r1 = r3
            L_0x0194:
                r19 = r1
                boolean r1 = r10.mScanSuccess     // Catch:{ RemoteException -> 0x01c7 }
                if (r1 == 0) goto L_0x01ae
                java.lang.String r1 = "/music/"
                int r1 = r5.indexOf(r1)     // Catch:{ RemoteException -> 0x0134 }
                if (r1 > 0) goto L_0x01ac
                if (r15 != 0) goto L_0x01ae
                if (r16 != 0) goto L_0x01ae
                if (r17 != 0) goto L_0x01ae
                if (r18 != 0) goto L_0x01ae
                if (r19 != 0) goto L_0x01ae
            L_0x01ac:
                r8 = r2
                goto L_0x01af
            L_0x01ae:
                r8 = r3
            L_0x01af:
                r1 = r22
                r2 = r9
                r3 = r15
                r4 = r16
                r20 = r5
                r5 = r17
                r6 = r18
                r21 = r7
                r7 = r19
                android.net.Uri r1 = r1.endFile(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ RemoteException -> 0x01c5 }
                r11 = r1
                goto L_0x01d5
            L_0x01c5:
                r0 = move-exception
                goto L_0x01dd
            L_0x01c7:
                r0 = move-exception
                r21 = r7
                goto L_0x01dd
            L_0x01cb:
                r0 = move-exception
                goto L_0x01db
            L_0x01cd:
                r0 = move-exception
                r2 = r23
                goto L_0x01db
            L_0x01d1:
                r2 = r23
                r21 = r2
            L_0x01d5:
                goto L_0x01e4
            L_0x01d6:
                r0 = move-exception
                r2 = r23
                r12 = r30
            L_0x01db:
                r21 = r2
            L_0x01dd:
                java.lang.String r1 = "MediaScanner"
                java.lang.String r2 = "RemoteException in MediaScanner.scanFile()"
                android.util.Log.e(r1, r2, r0)
            L_0x01e4:
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.MyMediaScannerClient.doScanFile(java.lang.String, java.lang.String, long, long, boolean, boolean, boolean):android.net.Uri");
        }

        private long parseDate(String date) {
            try {
                return this.mDateFormatter.parse(date).getTime();
            } catch (ParseException e) {
                return 0;
            }
        }

        private int parseSubstring(String s, int start, int defaultValue) {
            int length = s.length();
            if (start == length) {
                return defaultValue;
            }
            int start2 = start + 1;
            int start3 = s.charAt(start);
            if (start3 < 48 || start3 > 57) {
                return defaultValue;
            }
            int result = start3 - 48;
            while (start2 < length) {
                int start4 = start2 + 1;
                char ch = s.charAt(start2);
                if (ch < '0' || ch > '9') {
                    return result;
                }
                result = (result * 10) + (ch - '0');
                start2 = start4;
            }
            return result;
        }

        @UnsupportedAppUsage
        public void handleStringTag(String name, String value) {
            if (name.equalsIgnoreCase("title") || name.startsWith("title;")) {
                this.mTitle = value;
            } else if (name.equalsIgnoreCase("artist") || name.startsWith("artist;")) {
                this.mArtist = value.trim();
            } else if (name.equalsIgnoreCase("albumartist") || name.startsWith("albumartist;") || name.equalsIgnoreCase("band") || name.startsWith("band;")) {
                this.mAlbumArtist = value.trim();
            } else if (name.equalsIgnoreCase("album") || name.startsWith("album;")) {
                this.mAlbum = value.trim();
            } else if (name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.COMPOSER) || name.startsWith("composer;")) {
                this.mComposer = value.trim();
            } else if (!MediaScanner.this.mProcessGenres || (!name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.GENRE) && !name.startsWith("genre;"))) {
                boolean z = false;
                if (name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.YEAR) || name.startsWith("year;")) {
                    this.mYear = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("tracknumber") || name.startsWith("tracknumber;")) {
                    this.mTrack = ((this.mTrack / 1000) * 1000) + parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("discnumber") || name.equals("set") || name.startsWith("set;")) {
                    this.mTrack = (parseSubstring(value, 0, 0) * 1000) + (this.mTrack % 1000);
                } else if (name.equalsIgnoreCase("duration")) {
                    this.mDuration = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("writer") || name.startsWith("writer;")) {
                    this.mWriter = value.trim();
                } else if (name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.COMPILATION)) {
                    this.mCompilation = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("isdrm")) {
                    if (parseSubstring(value, 0, 0) == 1) {
                        z = true;
                    }
                    this.mIsDrm = z;
                } else if (name.equalsIgnoreCase("date")) {
                    this.mDate = parseDate(value);
                } else if (name.equalsIgnoreCase("width")) {
                    this.mWidth = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("height")) {
                    this.mHeight = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("colorstandard")) {
                    this.mColorStandard = parseSubstring(value, 0, -1);
                } else if (name.equalsIgnoreCase("colortransfer")) {
                    this.mColorTransfer = parseSubstring(value, 0, -1);
                } else if (name.equalsIgnoreCase("colorrange")) {
                    this.mColorRange = parseSubstring(value, 0, -1);
                }
            } else {
                this.mGenre = getGenreName(value);
            }
        }

        private boolean convertGenreCode(String input, String expected) {
            String output = getGenreName(input);
            if (output.equals(expected)) {
                return true;
            }
            Log.d(MediaScanner.TAG, "'" + input + "' -> '" + output + "', expected '" + expected + "'");
            return false;
        }

        private void testGenreNameConverter() {
            convertGenreCode("2", "Country");
            convertGenreCode("(2)", "Country");
            convertGenreCode("(2", "(2");
            convertGenreCode("2 Foo", "Country");
            convertGenreCode("(2) Foo", "Country");
            convertGenreCode("(2 Foo", "(2 Foo");
            convertGenreCode("2Foo", "2Foo");
            convertGenreCode("(2)Foo", "Country");
            convertGenreCode("200 Foo", "Foo");
            convertGenreCode("(200) Foo", "Foo");
            convertGenreCode("200Foo", "200Foo");
            convertGenreCode("(200)Foo", "Foo");
            convertGenreCode("200)Foo", "200)Foo");
            convertGenreCode("200) Foo", "200) Foo");
        }

        public String getGenreName(String genreTagValue) {
            if (genreTagValue == null) {
                return null;
            }
            int length = genreTagValue.length();
            if (length > 0) {
                boolean parenthesized = false;
                StringBuffer number = new StringBuffer();
                int i = 0;
                while (i < length) {
                    char c = genreTagValue.charAt(i);
                    if (i != 0 || c != '(') {
                        if (!Character.isDigit(c)) {
                            break;
                        }
                        number.append(c);
                    } else {
                        parenthesized = true;
                    }
                    i++;
                }
                char charAfterNumber = i < length ? genreTagValue.charAt(i) : ' ';
                if ((parenthesized && charAfterNumber == ')') || (!parenthesized && Character.isWhitespace(charAfterNumber))) {
                    try {
                        short genreIndex = Short.parseShort(number.toString());
                        if (genreIndex >= 0) {
                            if (genreIndex < MediaScanner.ID3_GENRES.length && MediaScanner.ID3_GENRES[genreIndex] != null) {
                                return MediaScanner.ID3_GENRES[genreIndex];
                            }
                            if (genreIndex == 255) {
                                return null;
                            }
                            if (genreIndex >= 255 || i + 1 >= length) {
                                return number.toString();
                            }
                            if (parenthesized && charAfterNumber == ')') {
                                i++;
                            }
                            String ret = genreTagValue.substring(i).trim();
                            if (ret.length() != 0) {
                                return ret;
                            }
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return genreTagValue;
        }

        private boolean processImageFile(String path) {
            try {
                MediaScanner.this.mBitmapOptions.outWidth = 0;
                MediaScanner.this.mBitmapOptions.outHeight = 0;
                BitmapFactory.decodeFile(path, MediaScanner.this.mBitmapOptions);
                this.mWidth = MediaScanner.this.mBitmapOptions.outWidth;
                this.mHeight = MediaScanner.this.mBitmapOptions.outHeight;
                if (this.mWidth <= 0 || this.mHeight <= 0) {
                    return false;
                }
                return true;
            } catch (Throwable th) {
                return false;
            }
        }

        @UnsupportedAppUsage
        public void setMimeType(String mimeType) {
            if (!"audio/mp4".equals(this.mMimeType) || !mimeType.startsWith("video")) {
                this.mMimeType = mimeType;
            }
        }

        @UnsupportedAppUsage
        private ContentValues toValues() {
            ContentValues map = new ContentValues();
            map.put("_data", this.mPath);
            map.put("title", this.mTitle);
            map.put("date_modified", Long.valueOf(this.mLastModified));
            map.put("_size", Long.valueOf(this.mFileSize));
            map.put("mime_type", this.mMimeType);
            map.put(MediaStore.MediaColumns.IS_DRM, Boolean.valueOf(this.mIsDrm));
            map.putNull(MediaStore.MediaColumns.HASH);
            String resolution = null;
            if (this.mWidth > 0 && this.mHeight > 0) {
                map.put("width", Integer.valueOf(this.mWidth));
                map.put("height", Integer.valueOf(this.mHeight));
                resolution = this.mWidth + "x" + this.mHeight;
            }
            if (!this.mNoMedia) {
                if (MediaFile.isVideoMimeType(this.mMimeType)) {
                    map.put("artist", (this.mArtist == null || this.mArtist.length() <= 0) ? MediaStore.UNKNOWN_STRING : this.mArtist);
                    map.put("album", (this.mAlbum == null || this.mAlbum.length() <= 0) ? MediaStore.UNKNOWN_STRING : this.mAlbum);
                    map.put("duration", Integer.valueOf(this.mDuration));
                    if (resolution != null) {
                        map.put(MediaStore.Video.VideoColumns.RESOLUTION, resolution);
                    }
                    if (this.mColorStandard >= 0) {
                        map.put(MediaStore.Video.VideoColumns.COLOR_STANDARD, Integer.valueOf(this.mColorStandard));
                    }
                    if (this.mColorTransfer >= 0) {
                        map.put(MediaStore.Video.VideoColumns.COLOR_TRANSFER, Integer.valueOf(this.mColorTransfer));
                    }
                    if (this.mColorRange >= 0) {
                        map.put(MediaStore.Video.VideoColumns.COLOR_RANGE, Integer.valueOf(this.mColorRange));
                    }
                    if (this.mDate > 0) {
                        map.put("datetaken", Long.valueOf(this.mDate));
                    }
                } else if (!MediaFile.isImageMimeType(this.mMimeType) && MediaFile.isAudioMimeType(this.mMimeType)) {
                    map.put("artist", (this.mArtist == null || this.mArtist.length() <= 0) ? MediaStore.UNKNOWN_STRING : this.mArtist);
                    map.put(MediaStore.Audio.AudioColumns.ALBUM_ARTIST, (this.mAlbumArtist == null || this.mAlbumArtist.length() <= 0) ? null : this.mAlbumArtist);
                    map.put("album", (this.mAlbum == null || this.mAlbum.length() <= 0) ? MediaStore.UNKNOWN_STRING : this.mAlbum);
                    map.put(MediaStore.Audio.AudioColumns.COMPOSER, this.mComposer);
                    map.put(MediaStore.Audio.AudioColumns.GENRE, this.mGenre);
                    if (this.mYear != 0) {
                        map.put(MediaStore.Audio.AudioColumns.YEAR, Integer.valueOf(this.mYear));
                    }
                    map.put(MediaStore.Audio.AudioColumns.TRACK, Integer.valueOf(this.mTrack));
                    map.put("duration", Integer.valueOf(this.mDuration));
                    map.put(MediaStore.Audio.AudioColumns.COMPILATION, Integer.valueOf(this.mCompilation));
                }
            }
            return map;
        }

        /* JADX WARNING: type inference failed for: r5v3, types: [java.lang.String, java.lang.String[]] */
        /* JADX WARNING: type inference failed for: r5v4 */
        /* JADX WARNING: type inference failed for: r5v5 */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
            r0 = r3.getAsString("_data");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x01b7, code lost:
            if (doesPathHaveFilename(r2.mPath, android.media.MediaScanner.access$600(r1.this$0)) != false) goto L_0x01bc;
         */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x006b  */
        @android.annotation.UnsupportedAppUsage
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.net.Uri endFile(android.media.MediaScanner.FileEntry r22, boolean r23, boolean r24, boolean r25, boolean r26, boolean r27, boolean r28) throws android.os.RemoteException {
            /*
                r21 = this;
                r1 = r21
                r2 = r22
                java.lang.String r0 = r1.mArtist
                if (r0 == 0) goto L_0x0010
                java.lang.String r0 = r1.mArtist
                int r0 = r0.length()
                if (r0 != 0) goto L_0x0014
            L_0x0010:
                java.lang.String r0 = r1.mAlbumArtist
                r1.mArtist = r0
            L_0x0014:
                android.content.ContentValues r3 = r21.toValues()
                java.lang.String r0 = "title"
                java.lang.String r0 = r3.getAsString(r0)
                if (r0 == 0) goto L_0x002e
                java.lang.String r4 = r0.trim()
                boolean r4 = android.text.TextUtils.isEmpty(r4)
                if (r4 == 0) goto L_0x002c
                goto L_0x002e
            L_0x002c:
                r4 = r0
                goto L_0x003f
            L_0x002e:
                java.lang.String r4 = "_data"
                java.lang.String r4 = r3.getAsString(r4)
                java.lang.String r0 = android.media.MediaFile.getFileTitle(r4)
                java.lang.String r4 = "title"
                r3.put((java.lang.String) r4, (java.lang.String) r0)
                goto L_0x002c
            L_0x003f:
                java.lang.String r0 = "album"
                java.lang.String r0 = r3.getAsString(r0)
                java.lang.String r5 = "<unknown>"
                boolean r5 = r5.equals(r0)
                if (r5 == 0) goto L_0x0076
                java.lang.String r5 = "_data"
                java.lang.String r0 = r3.getAsString(r5)
                r5 = 47
                int r7 = r0.lastIndexOf(r5)
                if (r7 < 0) goto L_0x0076
                r8 = 0
            L_0x005c:
                int r9 = r8 + 1
                int r9 = r0.indexOf(r5, r9)
                if (r9 < 0) goto L_0x0069
                if (r9 < r7) goto L_0x0067
                goto L_0x0069
            L_0x0067:
                r8 = r9
                goto L_0x005c
            L_0x0069:
                if (r8 == 0) goto L_0x0076
                int r5 = r8 + 1
                java.lang.String r0 = r0.substring(r5, r7)
                java.lang.String r5 = "album"
                r3.put((java.lang.String) r5, (java.lang.String) r0)
            L_0x0076:
                r5 = r0
                long r7 = r2.mRowId
                java.lang.String r0 = r1.mMimeType
                boolean r0 = android.media.MediaFile.isAudioMimeType(r0)
                r9 = 0
                r11 = 0
                if (r0 == 0) goto L_0x00c8
                int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r0 == 0) goto L_0x0090
                android.media.MediaScanner r0 = android.media.MediaScanner.this
                int r0 = r0.mMtpObjectHandle
                if (r0 == 0) goto L_0x00c8
            L_0x0090:
                java.lang.String r0 = "is_ringtone"
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r23)
                r3.put((java.lang.String) r0, (java.lang.Boolean) r6)
                java.lang.String r0 = "is_notification"
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r24)
                r3.put((java.lang.String) r0, (java.lang.Boolean) r6)
                java.lang.String r0 = "is_alarm"
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r25)
                r3.put((java.lang.String) r0, (java.lang.Boolean) r6)
                java.lang.String r0 = "is_music"
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r28)
                r3.put((java.lang.String) r0, (java.lang.Boolean) r6)
                java.lang.String r0 = "is_podcast"
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r26)
                r3.put((java.lang.String) r0, (java.lang.Boolean) r6)
                java.lang.String r0 = "is_audiobook"
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r27)
                r3.put((java.lang.String) r0, (java.lang.Boolean) r6)
                goto L_0x0141
            L_0x00c8:
                java.lang.String r0 = r1.mMimeType
                boolean r0 = android.media.MediaFile.isExifMimeType(r0)
                if (r0 == 0) goto L_0x0141
                boolean r0 = r1.mNoMedia
                if (r0 != 0) goto L_0x0141
                r12 = r11
                android.media.ExifInterface r0 = new android.media.ExifInterface     // Catch:{ Exception -> 0x00de }
                java.lang.String r6 = r2.mPath     // Catch:{ Exception -> 0x00de }
                r0.<init>((java.lang.String) r6)     // Catch:{ Exception -> 0x00de }
                r12 = r0
                goto L_0x00df
            L_0x00de:
                r0 = move-exception
            L_0x00df:
                if (r12 == 0) goto L_0x0141
                long r14 = r12.getGpsDateTime()
                r16 = -1
                int r0 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r0 == 0) goto L_0x00f5
                java.lang.String r0 = "datetaken"
                java.lang.Long r6 = java.lang.Long.valueOf(r14)
                r3.put((java.lang.String) r0, (java.lang.Long) r6)
                goto L_0x0118
            L_0x00f5:
                long r14 = r12.getDateTime()
                int r0 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
                if (r0 == 0) goto L_0x0118
                long r9 = r1.mLastModified
                r16 = 1000(0x3e8, double:4.94E-321)
                long r9 = r9 * r16
                long r9 = r9 - r14
                long r9 = java.lang.Math.abs(r9)
                r16 = 86400000(0x5265c00, double:4.2687272E-316)
                int r0 = (r9 > r16 ? 1 : (r9 == r16 ? 0 : -1))
                if (r0 < 0) goto L_0x0118
                java.lang.String r0 = "datetaken"
                java.lang.Long r6 = java.lang.Long.valueOf(r14)
                r3.put((java.lang.String) r0, (java.lang.Long) r6)
            L_0x0118:
                java.lang.String r0 = "Orientation"
                r6 = -1
                int r0 = r12.getAttributeInt(r0, r6)
                if (r0 == r6) goto L_0x0141
                r6 = 3
                if (r0 == r6) goto L_0x0133
                r6 = 6
                if (r0 == r6) goto L_0x0130
                r6 = 8
                if (r0 == r6) goto L_0x012d
                r6 = 0
                goto L_0x0136
            L_0x012d:
                r6 = 270(0x10e, float:3.78E-43)
                goto L_0x0136
            L_0x0130:
                r6 = 90
                goto L_0x0136
            L_0x0133:
                r6 = 180(0xb4, float:2.52E-43)
            L_0x0136:
                java.lang.String r9 = "orientation"
                java.lang.Integer r10 = java.lang.Integer.valueOf(r6)
                r3.put((java.lang.String) r9, (java.lang.Integer) r10)
            L_0x0141:
                android.media.MediaScanner r0 = android.media.MediaScanner.this
                android.net.Uri r0 = r0.mFilesUri
                r6 = 0
                android.media.MediaScanner r9 = android.media.MediaScanner.this
                android.media.MediaInserter r9 = r9.mMediaInserter
                boolean r10 = r1.mNoMedia
                if (r10 != 0) goto L_0x0191
                java.lang.String r10 = r1.mMimeType
                boolean r10 = android.media.MediaFile.isVideoMimeType(r10)
                if (r10 == 0) goto L_0x0162
                android.media.MediaScanner r10 = android.media.MediaScanner.this
                android.net.Uri r0 = r10.mVideoUri
                r6 = 3
                goto L_0x0191
            L_0x0162:
                java.lang.String r10 = r1.mMimeType
                boolean r10 = android.media.MediaFile.isImageMimeType(r10)
                if (r10 == 0) goto L_0x0172
                android.media.MediaScanner r10 = android.media.MediaScanner.this
                android.net.Uri r0 = r10.mImagesUri
                r6 = 1
                goto L_0x0191
            L_0x0172:
                java.lang.String r10 = r1.mMimeType
                boolean r10 = android.media.MediaFile.isAudioMimeType(r10)
                if (r10 == 0) goto L_0x0182
                android.media.MediaScanner r10 = android.media.MediaScanner.this
                android.net.Uri r0 = r10.mAudioUri
                r6 = 2
                goto L_0x0191
            L_0x0182:
                java.lang.String r10 = r1.mMimeType
                boolean r10 = android.media.MediaFile.isPlayListMimeType(r10)
                if (r10 == 0) goto L_0x0191
                android.media.MediaScanner r10 = android.media.MediaScanner.this
                android.net.Uri r0 = r10.mPlaylistsUri
                r6 = 4
            L_0x0191:
                r10 = 0
                r12 = 0
                if (r24 == 0) goto L_0x01be
                android.media.MediaScanner r11 = android.media.MediaScanner.this
                boolean r11 = r11.mDefaultNotificationSet
                if (r11 != 0) goto L_0x01be
                android.media.MediaScanner r11 = android.media.MediaScanner.this
                java.lang.String r11 = r11.mDefaultNotificationFilename
                boolean r11 = android.text.TextUtils.isEmpty(r11)
                if (r11 != 0) goto L_0x01ba
                java.lang.String r11 = r2.mPath
                r18 = r4
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                java.lang.String r4 = r4.mDefaultNotificationFilename
                boolean r4 = r1.doesPathHaveFilename(r11, r4)
                if (r4 == 0) goto L_0x020b
                goto L_0x01bc
            L_0x01ba:
                r18 = r4
            L_0x01bc:
                r12 = 1
                goto L_0x020b
            L_0x01be:
                r18 = r4
                if (r23 == 0) goto L_0x01e6
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                boolean r4 = r4.mDefaultRingtoneSet
                if (r4 != 0) goto L_0x01e6
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                java.lang.String r4 = r4.mDefaultRingtoneFilename
                boolean r4 = android.text.TextUtils.isEmpty(r4)
                if (r4 != 0) goto L_0x01e4
                java.lang.String r4 = r2.mPath
                android.media.MediaScanner r11 = android.media.MediaScanner.this
                java.lang.String r11 = r11.mDefaultRingtoneFilename
                boolean r4 = r1.doesPathHaveFilename(r4, r11)
                if (r4 == 0) goto L_0x020b
            L_0x01e4:
                r12 = 1
                goto L_0x020b
            L_0x01e6:
                if (r25 == 0) goto L_0x020b
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                boolean r4 = r4.mDefaultAlarmSet
                if (r4 != 0) goto L_0x020b
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                java.lang.String r4 = r4.mDefaultAlarmAlertFilename
                boolean r4 = android.text.TextUtils.isEmpty(r4)
                if (r4 != 0) goto L_0x020a
                java.lang.String r4 = r2.mPath
                android.media.MediaScanner r11 = android.media.MediaScanner.this
                java.lang.String r11 = r11.mDefaultAlarmAlertFilename
                boolean r4 = r1.doesPathHaveFilename(r4, r11)
                if (r4 == 0) goto L_0x020b
            L_0x020a:
                r12 = 1
            L_0x020b:
                r13 = 0
                int r4 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
                if (r4 != 0) goto L_0x027a
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                int r4 = r4.mMtpObjectHandle
                if (r4 == 0) goto L_0x0229
                java.lang.String r4 = "media_scanner_new_object_id"
                android.media.MediaScanner r11 = android.media.MediaScanner.this
                int r11 = r11.mMtpObjectHandle
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                r3.put((java.lang.String) r4, (java.lang.Integer) r11)
            L_0x0229:
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                android.net.Uri r4 = r4.mFilesUri
                if (r0 != r4) goto L_0x024d
                int r4 = r2.mFormat
                if (r4 != 0) goto L_0x0242
                java.lang.String r11 = r2.mPath
                r19 = r4
                java.lang.String r4 = r1.mMimeType
                int r4 = android.media.MediaFile.getFormatCode(r11, r4)
                r19 = r4
                goto L_0x0244
            L_0x0242:
                r19 = r4
            L_0x0244:
                java.lang.String r4 = "format"
                java.lang.Integer r11 = java.lang.Integer.valueOf(r19)
                r3.put((java.lang.String) r4, (java.lang.Integer) r11)
            L_0x024d:
                if (r9 == 0) goto L_0x0260
                if (r12 == 0) goto L_0x0252
                goto L_0x0260
            L_0x0252:
                int r4 = r2.mFormat
                r11 = 12289(0x3001, float:1.722E-41)
                if (r4 != r11) goto L_0x025c
                r9.insertwithPriority(r0, r3)
                goto L_0x026f
            L_0x025c:
                r9.insert(r0, r3)
                goto L_0x026f
            L_0x0260:
                if (r9 == 0) goto L_0x0265
                r9.flushAll()
            L_0x0265:
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                android.content.ContentProviderClient r4 = r4.mMediaProvider
                android.net.Uri r10 = r4.insert(r0, r3)
            L_0x026f:
                if (r10 == 0) goto L_0x0277
                long r7 = android.content.ContentUris.parseId(r10)
                r2.mRowId = r7
            L_0x0277:
                r20 = r5
                goto L_0x02bd
            L_0x027a:
                android.net.Uri r10 = android.content.ContentUris.withAppendedId(r0, r7)
                java.lang.String r4 = "_data"
                r3.remove(r4)
                boolean r4 = r1.mNoMedia
                if (r4 != 0) goto L_0x02b1
                int r4 = r2.mMediaType
                if (r6 == r4) goto L_0x02b1
                android.content.ContentValues r4 = new android.content.ContentValues
                r4.<init>()
                java.lang.String r11 = "media_type"
                java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
                r4.put((java.lang.String) r11, (java.lang.Integer) r2)
                android.media.MediaScanner r2 = android.media.MediaScanner.this
                android.content.ContentProviderClient r2 = r2.mMediaProvider
                android.media.MediaScanner r11 = android.media.MediaScanner.this
                android.net.Uri r11 = r11.mFilesUri
                android.net.Uri r11 = android.content.ContentUris.withAppendedId(r11, r7)
                r20 = r5
                r5 = 0
                r2.update(r11, r4, r5, r5)
                goto L_0x02b4
            L_0x02b1:
                r20 = r5
                r5 = 0
            L_0x02b4:
                android.media.MediaScanner r2 = android.media.MediaScanner.this
                android.content.ContentProviderClient r2 = r2.mMediaProvider
                r2.update(r10, r3, r5, r5)
            L_0x02bd:
                if (r12 == 0) goto L_0x02e8
                r2 = 1
                if (r24 == 0) goto L_0x02ce
                java.lang.String r4 = "notification_sound"
                r1.setRingtoneIfNotSet(r4, r0, r7)
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                boolean unused = r4.mDefaultNotificationSet = r2
                goto L_0x02e8
            L_0x02ce:
                if (r23 == 0) goto L_0x02dc
                java.lang.String r4 = "ringtone"
                r1.setRingtoneIfNotSet(r4, r0, r7)
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                boolean unused = r4.mDefaultRingtoneSet = r2
                goto L_0x02e8
            L_0x02dc:
                if (r25 == 0) goto L_0x02e8
                java.lang.String r4 = "alarm_alert"
                r1.setRingtoneIfNotSet(r4, r0, r7)
                android.media.MediaScanner r4 = android.media.MediaScanner.this
                boolean unused = r4.mDefaultAlarmSet = r2
            L_0x02e8:
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.MyMediaScannerClient.endFile(android.media.MediaScanner$FileEntry, boolean, boolean, boolean, boolean, boolean, boolean):android.net.Uri");
        }

        private boolean doesPathHaveFilename(String path, String filename) {
            int pathFilenameStart = path.lastIndexOf(File.separatorChar) + 1;
            int filenameLength = filename.length();
            if (!path.regionMatches(pathFilenameStart, filename, 0, filenameLength) || pathFilenameStart + filenameLength != path.length()) {
                return false;
            }
            return true;
        }

        private void setRingtoneIfNotSet(String settingName, Uri uri, long rowId) {
            if (!MediaScanner.this.wasRingtoneAlreadySet(settingName)) {
                ContentResolver cr = MediaScanner.this.mContext.getContentResolver();
                if (TextUtils.isEmpty(Settings.System.getString(cr, settingName))) {
                    Uri settingUri = Settings.System.getUriFor(settingName);
                    RingtoneManager.setActualDefaultRingtoneUri(MediaScanner.this.mContext, RingtoneManager.getDefaultType(settingUri), ContentUris.withAppendedId(uri, rowId));
                }
                Settings.System.putInt(cr, MediaScanner.this.settingSetIndicatorName(settingName), 1);
            }
        }

        @Deprecated
        @UnsupportedAppUsage
        private int getFileTypeFromDrm(String path) {
            return 0;
        }

        private void getMimeTypeFromDrm(String path) {
            this.mMimeType = null;
            if (MediaScanner.this.mDrmManagerClient == null) {
                DrmManagerClient unused = MediaScanner.this.mDrmManagerClient = new DrmManagerClient(MediaScanner.this.mContext);
            }
            if (MediaScanner.this.mDrmManagerClient.canHandle(path, (String) null)) {
                this.mIsDrm = true;
                this.mMimeType = MediaScanner.this.mDrmManagerClient.getOriginalMimeType(path);
            }
            if (this.mMimeType == null) {
                this.mMimeType = ContentResolver.MIME_TYPE_DEFAULT;
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean isSystemSoundWithMetadata(String path) {
        if (path.startsWith(SYSTEM_SOUNDS_DIR + ALARMS_DIR)) {
            return true;
        }
        if (path.startsWith(SYSTEM_SOUNDS_DIR + RINGTONES_DIR)) {
            return true;
        }
        if (path.startsWith(SYSTEM_SOUNDS_DIR + NOTIFICATIONS_DIR)) {
            return true;
        }
        if (path.startsWith(OEM_SOUNDS_DIR + ALARMS_DIR)) {
            return true;
        }
        if (path.startsWith(OEM_SOUNDS_DIR + RINGTONES_DIR)) {
            return true;
        }
        if (path.startsWith(OEM_SOUNDS_DIR + NOTIFICATIONS_DIR)) {
            return true;
        }
        if (path.startsWith(PRODUCT_SOUNDS_DIR + ALARMS_DIR)) {
            return true;
        }
        if (path.startsWith(PRODUCT_SOUNDS_DIR + RINGTONES_DIR)) {
            return true;
        }
        if (path.startsWith(PRODUCT_SOUNDS_DIR + NOTIFICATIONS_DIR)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public String settingSetIndicatorName(String base) {
        return base + "_set";
    }

    /* access modifiers changed from: private */
    public boolean wasRingtoneAlreadySet(String name) {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), settingSetIndicatorName(name)) != 0;
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x014c  */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void prescan(java.lang.String r25, boolean r26) throws android.os.RemoteException {
        /*
            r24 = this;
            r1 = r24
            r3 = 0
            r0 = 0
            r4 = 0
            java.util.ArrayList<android.media.MediaScanner$FileEntry> r5 = r1.mPlayLists
            r5.clear()
            r5 = 1
            r6 = 2
            r7 = 0
            if (r25 == 0) goto L_0x001d
            java.lang.String r0 = "_id>? AND _data=?"
            java.lang.String[] r8 = new java.lang.String[r6]
            java.lang.String r9 = ""
            r8[r7] = r9
            r8[r5] = r25
            r4 = r8
        L_0x001a:
            r15 = r4
            r4 = r0
            goto L_0x0027
        L_0x001d:
            java.lang.String r0 = "_id>?"
            java.lang.String r8 = ""
            java.lang.String[] r8 = new java.lang.String[]{r8}
            r4 = r8
            goto L_0x001a
        L_0x0027:
            java.lang.String r0 = "ringtone"
            boolean r0 = r1.wasRingtoneAlreadySet(r0)
            r1.mDefaultRingtoneSet = r0
            java.lang.String r0 = "notification_sound"
            boolean r0 = r1.wasRingtoneAlreadySet(r0)
            r1.mDefaultNotificationSet = r0
            java.lang.String r0 = "alarm_alert"
            boolean r0 = r1.wasRingtoneAlreadySet(r0)
            r1.mDefaultAlarmSet = r0
            android.net.Uri r0 = r1.mFilesUri
            android.net.Uri$Builder r14 = r0.buildUpon()
            java.lang.String r0 = "deletedata"
            java.lang.String r8 = "false"
            r14.appendQueryParameter(r0, r8)
            android.media.MediaScanner$MediaBulkDeleter r0 = new android.media.MediaScanner$MediaBulkDeleter
            android.content.ContentProviderClient r8 = r1.mMediaProvider
            android.net.Uri r9 = r14.build()
            r0.<init>(r8, r9)
            r13 = r0
            if (r26 == 0) goto L_0x0153
            r8 = -9223372036854775808
            android.net.Uri r0 = r1.mFilesUri     // Catch:{ all -> 0x0146 }
            android.net.Uri$Builder r0 = r0.buildUpon()     // Catch:{ all -> 0x0146 }
            java.lang.String r10 = "limit"
            java.lang.String r11 = "1000"
            android.net.Uri$Builder r0 = r0.appendQueryParameter(r10, r11)     // Catch:{ all -> 0x0146 }
            android.net.Uri r0 = r0.build()     // Catch:{ all -> 0x0146 }
            r11 = r8
            r9 = r0
        L_0x0072:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0146 }
            r0.<init>()     // Catch:{ all -> 0x0146 }
            java.lang.String r8 = ""
            r0.append(r8)     // Catch:{ all -> 0x0146 }
            r0.append(r11)     // Catch:{ all -> 0x0146 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0146 }
            r15[r7] = r0     // Catch:{ all -> 0x0146 }
            if (r3 == 0) goto L_0x0093
            r3.close()     // Catch:{ all -> 0x008d }
            r0 = 0
            r3 = r0
            goto L_0x0093
        L_0x008d:
            r0 = move-exception
            r5 = r13
            r20 = r14
            goto L_0x014a
        L_0x0093:
            android.content.ContentProviderClient r8 = r1.mMediaProvider     // Catch:{ all -> 0x0146 }
            java.lang.String[] r10 = FILES_PRESCAN_PROJECTION     // Catch:{ all -> 0x0146 }
            java.lang.String r0 = "_id"
            r16 = 0
            r17 = r11
            r11 = r4
            r12 = r15
            r19 = r13
            r13 = r0
            r20 = r14
            r14 = r16
            android.database.Cursor r0 = r8.query(r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x0142 }
            r3 = r0
            if (r3 != 0) goto L_0x00ae
            goto L_0x00b6
        L_0x00ae:
            int r0 = r3.getCount()     // Catch:{ all -> 0x0142 }
            r8 = r0
            if (r8 != 0) goto L_0x00ba
        L_0x00b6:
            r5 = r19
            goto L_0x0156
        L_0x00ba:
            r11 = r17
        L_0x00bc:
            boolean r0 = r3.moveToNext()     // Catch:{ all -> 0x0142 }
            if (r0 == 0) goto L_0x0138
            long r13 = r3.getLong(r7)     // Catch:{ all -> 0x0142 }
            java.lang.String r0 = r3.getString(r5)     // Catch:{ all -> 0x0142 }
            r10 = r0
            int r0 = r3.getInt(r6)     // Catch:{ all -> 0x0142 }
            r16 = r0
            r0 = 3
            long r17 = r3.getLong(r0)     // Catch:{ all -> 0x0142 }
            r11 = r13
            if (r10 == 0) goto L_0x0130
            java.lang.String r0 = "/"
            boolean r0 = r10.startsWith(r0)     // Catch:{ all -> 0x0142 }
            if (r0 == 0) goto L_0x0130
            r21 = r7
            int r0 = android.system.OsConstants.F_OK     // Catch:{ ErrnoException -> 0x00f0, all -> 0x00ec }
            boolean r0 = android.system.Os.access(r10, r0)     // Catch:{ ErrnoException -> 0x00f0, all -> 0x00ec }
            r21 = r0
            goto L_0x00f1
        L_0x00ec:
            r0 = move-exception
            r5 = r19
            goto L_0x014a
        L_0x00f0:
            r0 = move-exception
        L_0x00f1:
            if (r21 != 0) goto L_0x0130
            boolean r0 = android.mtp.MtpConstants.isAbstractObject(r16)     // Catch:{ all -> 0x0142 }
            if (r0 != 0) goto L_0x0130
            java.lang.String r0 = android.media.MediaFile.getMimeTypeForFile(r10)     // Catch:{ all -> 0x0142 }
            boolean r22 = android.media.MediaFile.isPlayListMimeType(r0)     // Catch:{ all -> 0x0142 }
            if (r22 != 0) goto L_0x0130
            r5 = r19
            r5.delete(r13)     // Catch:{ all -> 0x012e }
            java.util.Locale r6 = java.util.Locale.US     // Catch:{ all -> 0x012e }
            java.lang.String r6 = r10.toLowerCase(r6)     // Catch:{ all -> 0x012e }
            java.lang.String r7 = "/.nomedia"
            boolean r6 = r6.endsWith(r7)     // Catch:{ all -> 0x012e }
            if (r6 == 0) goto L_0x0132
            r5.flush()     // Catch:{ all -> 0x012e }
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x012e }
            r6.<init>(r10)     // Catch:{ all -> 0x012e }
            java.lang.String r6 = r6.getParent()     // Catch:{ all -> 0x012e }
            android.content.ContentProviderClient r7 = r1.mMediaProvider     // Catch:{ all -> 0x012e }
            r23 = r0
            java.lang.String r0 = "unhide"
            r2 = 0
            r7.call(r0, r6, r2)     // Catch:{ all -> 0x012e }
            goto L_0x0132
        L_0x012e:
            r0 = move-exception
            goto L_0x014a
        L_0x0130:
            r5 = r19
        L_0x0132:
            r19 = r5
            r5 = 1
            r6 = 2
            r7 = 0
            goto L_0x00bc
        L_0x0138:
            r5 = r19
            r13 = r5
            r14 = r20
            r5 = 1
            r6 = 2
            r7 = 0
            goto L_0x0072
        L_0x0142:
            r0 = move-exception
            r5 = r19
            goto L_0x014a
        L_0x0146:
            r0 = move-exception
            r5 = r13
            r20 = r14
        L_0x014a:
            if (r3 == 0) goto L_0x014f
            r3.close()
        L_0x014f:
            r5.flush()
            throw r0
        L_0x0153:
            r5 = r13
            r20 = r14
        L_0x0156:
            if (r3 == 0) goto L_0x015b
            r3.close()
        L_0x015b:
            r5.flush()
            r2 = 0
            r1.mOriginalCount = r2
            android.content.ContentProviderClient r6 = r1.mMediaProvider
            android.net.Uri r7 = r1.mImagesUri
            java.lang.String[] r8 = ID_PROJECTION
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            android.database.Cursor r0 = r6.query(r7, r8, r9, r10, r11, r12)
            if (r0 == 0) goto L_0x017b
            int r2 = r0.getCount()
            r1.mOriginalCount = r2
            r0.close()
        L_0x017b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.prescan(java.lang.String, boolean):void");
    }

    static class MediaBulkDeleter {
        final Uri mBaseUri;
        final ContentProviderClient mProvider;
        ArrayList<String> whereArgs = new ArrayList<>(100);
        StringBuilder whereClause = new StringBuilder();

        public MediaBulkDeleter(ContentProviderClient provider, Uri baseUri) {
            this.mProvider = provider;
            this.mBaseUri = baseUri;
        }

        public void delete(long id) throws RemoteException {
            if (this.whereClause.length() != 0) {
                this.whereClause.append(SmsManager.REGEX_PREFIX_DELIMITER);
            }
            this.whereClause.append("?");
            ArrayList<String> arrayList = this.whereArgs;
            arrayList.add("" + id);
            if (this.whereArgs.size() > 100) {
                flush();
            }
        }

        public void flush() throws RemoteException {
            int size = this.whereArgs.size();
            if (size > 0) {
                ArrayList<String> arrayList = this.whereArgs;
                ContentProviderClient contentProviderClient = this.mProvider;
                Uri uri = this.mBaseUri;
                int delete = contentProviderClient.delete(uri, "_id IN (" + this.whereClause.toString() + ")", (String[]) arrayList.toArray(new String[size]));
                this.whereClause.setLength(0);
                this.whereArgs.clear();
            }
        }
    }

    @UnsupportedAppUsage
    private void postscan(String[] directories) throws RemoteException {
        if (this.mProcessPlaylists) {
            processPlayLists();
        }
        this.mPlayLists.clear();
    }

    private void releaseResources() {
        if (this.mDrmManagerClient != null) {
            this.mDrmManagerClient.close();
            this.mDrmManagerClient = null;
        }
    }

    public void scanDirectories(String[] directories) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            prescan((String) null, true);
            long currentTimeMillis2 = System.currentTimeMillis();
            this.mMediaInserter = new MediaInserter(this.mMediaProvider, 500);
            for (String processDirectory : directories) {
                processDirectory(processDirectory, this.mClient);
            }
            this.mMediaInserter.flushAll();
            this.mMediaInserter = null;
            long currentTimeMillis3 = System.currentTimeMillis();
            postscan(directories);
            System.currentTimeMillis();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException in MediaScanner.scan()", e);
        } catch (UnsupportedOperationException e2) {
            Log.e(TAG, "UnsupportedOperationException in MediaScanner.scan()", e2);
        } catch (RemoteException e3) {
            Log.e(TAG, "RemoteException in MediaScanner.scan()", e3);
        } catch (Throwable th) {
            releaseResources();
            throw th;
        }
        releaseResources();
    }

    @UnsupportedAppUsage
    public Uri scanSingleFile(String path, String mimeType) {
        String str = path;
        try {
            prescan(path, true);
            File file = new File(path);
            if (file.exists()) {
                if (file.canRead()) {
                    String str2 = path;
                    String str3 = mimeType;
                    Uri doScanFile = this.mClient.doScanFile(str2, str3, file.lastModified() / 1000, file.length(), false, true, isNoMediaPath(path));
                    releaseResources();
                    return doScanFile;
                }
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in MediaScanner.scanFile()", e);
            return null;
        } finally {
            releaseResources();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0072, code lost:
        if (r12.regionMatches(true, r1 + 1, "AlbumArtSmall", 0, 13) == false) goto L_0x0074;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isNoMediaFile(java.lang.String r12) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r12)
            boolean r1 = r0.isDirectory()
            r2 = 0
            if (r1 == 0) goto L_0x000d
            return r2
        L_0x000d:
            r1 = 47
            int r1 = r12.lastIndexOf(r1)
            if (r1 < 0) goto L_0x0088
            int r3 = r1 + 2
            int r4 = r12.length()
            if (r3 >= r4) goto L_0x0088
            int r3 = r1 + 1
            java.lang.String r4 = "._"
            r5 = 2
            boolean r3 = r12.regionMatches(r3, r4, r2, r5)
            r4 = 1
            if (r3 == 0) goto L_0x002a
            return r4
        L_0x002a:
            r6 = 1
            int r3 = r12.length()
            int r7 = r3 + -4
            java.lang.String r8 = ".jpg"
            r9 = 0
            r10 = 4
            r5 = r12
            boolean r3 = r5.regionMatches(r6, r7, r8, r9, r10)
            if (r3 == 0) goto L_0x0088
            r6 = 1
            int r7 = r1 + 1
            java.lang.String r8 = "AlbumArt_{"
            r9 = 0
            r10 = 10
            r5 = r12
            boolean r3 = r5.regionMatches(r6, r7, r8, r9, r10)
            if (r3 != 0) goto L_0x0087
            r6 = 1
            int r7 = r1 + 1
            java.lang.String r8 = "AlbumArt."
            r9 = 0
            r10 = 9
            r5 = r12
            boolean r3 = r5.regionMatches(r6, r7, r8, r9, r10)
            if (r3 == 0) goto L_0x005b
            goto L_0x0087
        L_0x005b:
            int r3 = r12.length()
            int r3 = r3 - r1
            int r3 = r3 - r4
            r5 = 17
            if (r3 != r5) goto L_0x0074
            r7 = 1
            int r8 = r1 + 1
            java.lang.String r9 = "AlbumArtSmall"
            r10 = 0
            r11 = 13
            r6 = r12
            boolean r5 = r6.regionMatches(r7, r8, r9, r10, r11)
            if (r5 != 0) goto L_0x0086
        L_0x0074:
            r5 = 10
            if (r3 != r5) goto L_0x0088
            r7 = 1
            int r8 = r1 + 1
            java.lang.String r9 = "Folder"
            r10 = 0
            r11 = 6
            r6 = r12
            boolean r5 = r6.regionMatches(r7, r8, r9, r10, r11)
            if (r5 == 0) goto L_0x0088
        L_0x0086:
            return r4
        L_0x0087:
            return r4
        L_0x0088:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.isNoMediaFile(java.lang.String):boolean");
    }

    public static void clearMediaPathCache(boolean clearMediaPaths, boolean clearNoMediaPaths) {
        synchronized (MediaScanner.class) {
            if (clearMediaPaths) {
                try {
                    mMediaPaths.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (clearNoMediaPaths) {
                mNoMediaPaths.clear();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0072, code lost:
        return isNoMediaFile(r11);
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isNoMediaPath(java.lang.String r11) {
        /*
            r0 = 0
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = "/."
            int r1 = r11.indexOf(r1)
            r2 = 1
            if (r1 < 0) goto L_0x000e
            return r2
        L_0x000e:
            r1 = 47
            int r3 = r11.lastIndexOf(r1)
            if (r3 > 0) goto L_0x0017
            return r0
        L_0x0017:
            java.lang.String r4 = r11.substring(r0, r3)
            java.lang.Class<android.media.MediaScanner> r5 = android.media.MediaScanner.class
            monitor-enter(r5)
            java.util.HashMap<java.lang.String, java.lang.String> r6 = mNoMediaPaths     // Catch:{ all -> 0x0073 }
            boolean r6 = r6.containsKey(r4)     // Catch:{ all -> 0x0073 }
            if (r6 == 0) goto L_0x0028
            monitor-exit(r5)     // Catch:{ all -> 0x0073 }
            return r2
        L_0x0028:
            java.util.HashMap<java.lang.String, java.lang.String> r6 = mMediaPaths     // Catch:{ all -> 0x0073 }
            boolean r6 = r6.containsKey(r4)     // Catch:{ all -> 0x0073 }
            if (r6 != 0) goto L_0x006d
            r6 = r2
        L_0x0031:
            if (r6 < 0) goto L_0x0066
            int r7 = r11.indexOf(r1, r6)     // Catch:{ all -> 0x0073 }
            if (r7 <= r6) goto L_0x0064
            int r7 = r7 + 1
            java.io.File r8 = new java.io.File     // Catch:{ all -> 0x0073 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r9.<init>()     // Catch:{ all -> 0x0073 }
            java.lang.String r10 = r11.substring(r0, r7)     // Catch:{ all -> 0x0073 }
            r9.append(r10)     // Catch:{ all -> 0x0073 }
            java.lang.String r10 = ".nomedia"
            r9.append(r10)     // Catch:{ all -> 0x0073 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0073 }
            r8.<init>(r9)     // Catch:{ all -> 0x0073 }
            boolean r9 = r8.exists()     // Catch:{ all -> 0x0073 }
            if (r9 == 0) goto L_0x0064
            java.util.HashMap<java.lang.String, java.lang.String> r0 = mNoMediaPaths     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = ""
            r0.put(r4, r1)     // Catch:{ all -> 0x0073 }
            monitor-exit(r5)     // Catch:{ all -> 0x0073 }
            return r2
        L_0x0064:
            r6 = r7
            goto L_0x0031
        L_0x0066:
            java.util.HashMap<java.lang.String, java.lang.String> r0 = mMediaPaths     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = ""
            r0.put(r4, r1)     // Catch:{ all -> 0x0073 }
        L_0x006d:
            monitor-exit(r5)     // Catch:{ all -> 0x0073 }
            boolean r0 = isNoMediaFile(r11)
            return r0
        L_0x0073:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0073 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.isNoMediaPath(java.lang.String):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ce, code lost:
        if (r18 != null) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e1, code lost:
        if (r18 == null) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e3, code lost:
        r18.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e6, code lost:
        releaseResources();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ea, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void scanMtpFile(java.lang.String r22, int r23, int r24) {
        /*
            r21 = this;
            r1 = r21
            r12 = r22
            r13 = r24
            java.lang.String r14 = android.media.MediaFile.getMimeType(r12, r13)
            java.io.File r0 = new java.io.File
            r0.<init>(r12)
            r15 = r0
            long r2 = r15.lastModified()
            r4 = 1000(0x3e8, double:4.94E-321)
            long r16 = r2 / r4
            boolean r0 = android.media.MediaFile.isAudioMimeType(r14)
            r2 = 1
            r11 = 0
            if (r0 != 0) goto L_0x0073
            boolean r0 = android.media.MediaFile.isVideoMimeType(r14)
            if (r0 != 0) goto L_0x0073
            boolean r0 = android.media.MediaFile.isImageMimeType(r14)
            if (r0 != 0) goto L_0x0073
            boolean r0 = android.media.MediaFile.isPlayListMimeType(r14)
            if (r0 != 0) goto L_0x0073
            boolean r0 = android.media.MediaFile.isDrmMimeType(r14)
            if (r0 != 0) goto L_0x0073
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            r3 = r0
            java.lang.String r0 = "_size"
            long r4 = r15.length()
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r3.put((java.lang.String) r0, (java.lang.Long) r4)
            java.lang.String r0 = "date_modified"
            java.lang.Long r4 = java.lang.Long.valueOf(r16)
            r3.put((java.lang.String) r0, (java.lang.Long) r4)
            java.lang.String[] r0 = new java.lang.String[r2]     // Catch:{ RemoteException -> 0x006a }
            java.lang.String r2 = java.lang.Integer.toString(r23)     // Catch:{ RemoteException -> 0x006a }
            r0[r11] = r2     // Catch:{ RemoteException -> 0x006a }
            android.content.ContentProviderClient r2 = r1.mMediaProvider     // Catch:{ RemoteException -> 0x006a }
            java.lang.String r4 = r1.mVolumeName     // Catch:{ RemoteException -> 0x006a }
            android.net.Uri r4 = android.provider.MediaStore.Files.getMtpObjectsUri(r4)     // Catch:{ RemoteException -> 0x006a }
            java.lang.String r5 = "_id=?"
            r2.update(r4, r3, r5, r0)     // Catch:{ RemoteException -> 0x006a }
            goto L_0x0072
        L_0x006a:
            r0 = move-exception
            java.lang.String r2 = "MediaScanner"
            java.lang.String r4 = "RemoteException in scanMtpFile"
            android.util.Log.e(r2, r4, r0)
        L_0x0072:
            return
        L_0x0073:
            r10 = r23
            r1.mMtpObjectHandle = r10
            r0 = 0
            r18 = r0
            boolean r3 = android.media.MediaFile.isPlayListMimeType(r14)     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            if (r3 == 0) goto L_0x00a8
            r1.prescan(r0, r2)     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            android.media.MediaScanner$FileEntry r0 = r21.makeEntryFor(r22)     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            if (r0 == 0) goto L_0x00a5
            android.content.ContentProviderClient r2 = r1.mMediaProvider     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            android.net.Uri r3 = r1.mFilesUri     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            java.lang.String[] r4 = FILES_PRESCAN_PROJECTION     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8)     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            r1.processPlayList(r0, r2)     // Catch:{ RemoteException -> 0x00a1, all -> 0x009d }
            r18 = r2
            goto L_0x00a5
        L_0x009d:
            r0 = move-exception
            r18 = r2
            goto L_0x00d4
        L_0x00a1:
            r0 = move-exception
            r18 = r2
            goto L_0x00d7
        L_0x00a5:
            r12 = r11
            goto L_0x00cc
        L_0x00a8:
            r1.prescan(r12, r11)     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            android.media.MediaScanner$MyMediaScannerClient r0 = r1.mClient     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            long r7 = r15.length()     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            r3 = 12289(0x3001, float:1.722E-41)
            if (r13 != r3) goto L_0x00b7
            r9 = r2
            goto L_0x00b8
        L_0x00b7:
            r9 = r11
        L_0x00b8:
            r19 = 1
            boolean r20 = isNoMediaPath(r22)     // Catch:{ RemoteException -> 0x00d6, all -> 0x00d3 }
            r2 = r0
            r3 = r22
            r4 = r14
            r5 = r16
            r10 = r19
            r12 = r11
            r11 = r20
            r2.doScanFile(r3, r4, r5, r7, r9, r10, r11)     // Catch:{ RemoteException -> 0x00d1 }
        L_0x00cc:
            r1.mMtpObjectHandle = r12
            if (r18 == 0) goto L_0x00e6
            goto L_0x00e3
        L_0x00d1:
            r0 = move-exception
            goto L_0x00d8
        L_0x00d3:
            r0 = move-exception
        L_0x00d4:
            r12 = r11
            goto L_0x00ec
        L_0x00d6:
            r0 = move-exception
        L_0x00d7:
            r12 = r11
        L_0x00d8:
            java.lang.String r2 = "MediaScanner"
            java.lang.String r3 = "RemoteException in MediaScanner.scanFile()"
            android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x00eb }
            r1.mMtpObjectHandle = r12
            if (r18 == 0) goto L_0x00e6
        L_0x00e3:
            r18.close()
        L_0x00e6:
            r21.releaseResources()
            return
        L_0x00eb:
            r0 = move-exception
        L_0x00ec:
            r1.mMtpObjectHandle = r12
            if (r18 == 0) goto L_0x00f3
            r18.close()
        L_0x00f3:
            r21.releaseResources()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.scanMtpFile(java.lang.String, int, int):void");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0042, code lost:
        if (r3 != null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0044, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
        if (r3 == null) goto L_0x0053;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0053, code lost:
        return null;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.media.MediaScanner.FileEntry makeEntryFor(java.lang.String r18) {
        /*
            r17 = this;
            r1 = r17
            r2 = 0
            r3 = r2
            java.lang.String r7 = "_data=?"
            r0 = 1
            java.lang.String[] r8 = new java.lang.String[r0]     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r0 = 0
            r8[r0] = r18     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            android.content.ContentProviderClient r4 = r1.mMediaProvider     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            android.net.Uri r5 = r1.mFilesFullUri     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            java.lang.String[] r6 = FILES_PRESCAN_PROJECTION     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r9 = 0
            r10 = 0
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10)     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r3 = r4
            if (r3 == 0) goto L_0x0042
            boolean r4 = r3.moveToFirst()     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            if (r4 == 0) goto L_0x0042
            long r10 = r3.getLong(r0)     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r0 = 3
            long r13 = r3.getLong(r0)     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r0 = 2
            int r15 = r3.getInt(r0)     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r0 = 4
            int r16 = r3.getInt(r0)     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            android.media.MediaScanner$FileEntry r0 = new android.media.MediaScanner$FileEntry     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            r9 = r0
            r12 = r18
            r9.<init>(r10, r12, r13, r15, r16)     // Catch:{ RemoteException -> 0x004f, all -> 0x0048 }
            if (r3 == 0) goto L_0x0041
            r3.close()
        L_0x0041:
            return r0
        L_0x0042:
            if (r3 == 0) goto L_0x0053
        L_0x0044:
            r3.close()
            goto L_0x0053
        L_0x0048:
            r0 = move-exception
            if (r3 == 0) goto L_0x004e
            r3.close()
        L_0x004e:
            throw r0
        L_0x004f:
            r0 = move-exception
            if (r3 == 0) goto L_0x0053
            goto L_0x0044
        L_0x0053:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.makeEntryFor(java.lang.String):android.media.MediaScanner$FileEntry");
    }

    private int matchPaths(String path1, String path2) {
        int start2;
        String str = path1;
        String str2 = path2;
        int end1 = path1.length();
        int end2 = path2.length();
        int result = 0;
        int end12 = end1;
        while (true) {
            int end22 = end2;
            if (end12 <= 0 || end22 <= 0) {
                break;
            }
            int slash1 = str.lastIndexOf(47, end12 - 1);
            int slash2 = str2.lastIndexOf(47, end22 - 1);
            int backSlash1 = str.lastIndexOf(92, end12 - 1);
            int backSlash2 = str2.lastIndexOf(92, end22 - 1);
            int start1 = slash1 > backSlash1 ? slash1 : backSlash1;
            int start22 = slash2 > backSlash2 ? slash2 : backSlash2;
            int start12 = start1 < 0 ? 0 : start1 + 1;
            if (start22 < 0) {
                start2 = 0;
            } else {
                start2 = start22 + 1;
            }
            int length = end12 - start12;
            if (end22 - start2 == length) {
                int i = length;
                if (!path1.regionMatches(true, start12, path2, start2, length)) {
                    break;
                }
                result++;
                end12 = start12 - 1;
                end2 = start2 - 1;
            } else {
                break;
            }
        }
        return result;
    }

    private boolean matchEntries(long rowId, String data) {
        int len = this.mPlaylistEntries.size();
        boolean done = true;
        for (int i = 0; i < len; i++) {
            PlaylistEntry entry = this.mPlaylistEntries.get(i);
            if (entry.bestmatchlevel != Integer.MAX_VALUE) {
                done = false;
                if (data.equalsIgnoreCase(entry.path)) {
                    entry.bestmatchid = rowId;
                    entry.bestmatchlevel = Integer.MAX_VALUE;
                } else {
                    int matchLength = matchPaths(data, entry.path);
                    if (matchLength > entry.bestmatchlevel) {
                        entry.bestmatchid = rowId;
                        entry.bestmatchlevel = matchLength;
                    }
                }
            }
        }
        return done;
    }

    /* access modifiers changed from: private */
    public void cachePlaylistEntry(String line, String playListDirectory) {
        PlaylistEntry entry = new PlaylistEntry();
        int entryLength = line.length();
        while (entryLength > 0 && Character.isWhitespace(line.charAt(entryLength - 1))) {
            entryLength--;
        }
        if (entryLength >= 3) {
            boolean fullPath = false;
            if (entryLength < line.length()) {
                line = line.substring(0, entryLength);
            }
            char ch1 = line.charAt(0);
            if (ch1 == '/' || (Character.isLetter(ch1) && line.charAt(1) == ':' && line.charAt(2) == '\\')) {
                fullPath = true;
            }
            if (!fullPath) {
                line = playListDirectory + line;
            }
            entry.path = line;
            this.mPlaylistEntries.add(entry);
        }
    }

    private void processCachedPlaylist(Cursor fileList, ContentValues values, Uri playlistUri) {
        int i;
        fileList.moveToPosition(-1);
        do {
            if (!fileList.moveToNext() || matchEntries(fileList.getLong(0), fileList.getString(1))) {
                int len = this.mPlaylistEntries.size();
                int index = 0;
            }
            break;
        } while (matchEntries(fileList.getLong(0), fileList.getString(1)));
        int len2 = this.mPlaylistEntries.size();
        int index2 = 0;
        for (i = 0; i < len2; i++) {
            PlaylistEntry entry = this.mPlaylistEntries.get(i);
            if (entry.bestmatchlevel > 0) {
                try {
                    values.clear();
                    values.put("play_order", Integer.valueOf(index2));
                    values.put("audio_id", Long.valueOf(entry.bestmatchid));
                    this.mMediaProvider.insert(playlistUri, values);
                    index2++;
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException in MediaScanner.processCachedPlaylist()", e);
                    return;
                }
            }
        }
        this.mPlaylistEntries.clear();
    }

    private void processM3uPlayList(String path, String playListDirectory, Uri uri, ContentValues values, Cursor fileList) {
        BufferedReader reader = null;
        try {
            File f = new File(path);
            if (f.exists()) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)), 8192);
                this.mPlaylistEntries.clear();
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    if (line.length() > 0 && line.charAt(0) != '#') {
                        cachePlaylistEntry(line, playListDirectory);
                    }
                }
                processCachedPlaylist(fileList, values, uri);
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException in MediaScanner.processM3uPlayList()", e);
                }
            }
        } catch (IOException e2) {
            Log.e(TAG, "IOException in MediaScanner.processM3uPlayList()", e2);
            if (reader != null) {
                reader.close();
            }
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    Log.e(TAG, "IOException in MediaScanner.processM3uPlayList()", e3);
                }
            }
            throw th;
        }
    }

    private void processPlsPlayList(String path, String playListDirectory, Uri uri, ContentValues values, Cursor fileList) {
        int equals;
        BufferedReader reader = null;
        try {
            File f = new File(path);
            if (f.exists()) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)), 8192);
                this.mPlaylistEntries.clear();
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    if (line.startsWith("File") && (equals = line.indexOf(61)) > 0) {
                        cachePlaylistEntry(line.substring(equals + 1), playListDirectory);
                    }
                }
                processCachedPlaylist(fileList, values, uri);
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException in MediaScanner.processPlsPlayList()", e);
                }
            }
        } catch (IOException e2) {
            Log.e(TAG, "IOException in MediaScanner.processPlsPlayList()", e2);
            if (reader != null) {
                reader.close();
            }
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    Log.e(TAG, "IOException in MediaScanner.processPlsPlayList()", e3);
                }
            }
            throw th;
        }
    }

    class WplHandler implements ElementListener {
        final ContentHandler handler;
        String playListDirectory;

        public WplHandler(String playListDirectory2, Uri uri, Cursor fileList) {
            this.playListDirectory = playListDirectory2;
            RootElement root = new RootElement("smil");
            root.getChild("body").getChild("seq").getChild(MediaStore.AUTHORITY).setElementListener(this);
            this.handler = root.getContentHandler();
        }

        public void start(Attributes attributes) {
            String path = attributes.getValue("", "src");
            if (path != null) {
                MediaScanner.this.cachePlaylistEntry(path, this.playListDirectory);
            }
        }

        public void end() {
        }

        /* access modifiers changed from: package-private */
        public ContentHandler getContentHandler() {
            return this.handler;
        }
    }

    private void processWplPlayList(String path, String playListDirectory, Uri uri, ContentValues values, Cursor fileList) {
        FileInputStream fis = null;
        try {
            File f = new File(path);
            if (f.exists()) {
                fis = new FileInputStream(f);
                this.mPlaylistEntries.clear();
                Xml.parse(fis, Xml.findEncodingByName("UTF-8"), new WplHandler(playListDirectory, uri, fileList).getContentHandler());
                processCachedPlaylist(fileList, values, uri);
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Log.e(TAG, "IOException in MediaScanner.processWplPlayList()", e);
                }
            }
        } catch (SAXException e2) {
            e2.printStackTrace();
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            if (fis != null) {
                fis.close();
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e4) {
                    Log.e(TAG, "IOException in MediaScanner.processWplPlayList()", e4);
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b1, code lost:
        if (r5.equals("application/vnd.ms-wpl") == false) goto L_0x00c8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ed  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processPlayList(android.media.MediaScanner.FileEntry r19, android.database.Cursor r20) throws android.os.RemoteException {
        /*
            r18 = this;
            r6 = r18
            r7 = r19
            java.lang.String r8 = r7.mPath
            android.content.ContentValues r0 = new android.content.ContentValues
            r0.<init>()
            r9 = r0
            r0 = 47
            int r10 = r8.lastIndexOf(r0)
            if (r10 < 0) goto L_0x00fd
            long r0 = r7.mRowId
            java.lang.String r2 = "name"
            java.lang.String r2 = r9.getAsString(r2)
            if (r2 != 0) goto L_0x003e
            java.lang.String r3 = "title"
            java.lang.String r2 = r9.getAsString(r3)
            if (r2 != 0) goto L_0x003e
            r3 = 46
            int r3 = r8.lastIndexOf(r3)
            if (r3 >= 0) goto L_0x0037
            int r4 = r10 + 1
            java.lang.String r4 = r8.substring(r4)
            goto L_0x003d
        L_0x0037:
            int r4 = r10 + 1
            java.lang.String r4 = r8.substring(r4, r3)
        L_0x003d:
            r2 = r4
        L_0x003e:
            r11 = r2
            java.lang.String r2 = "name"
            r9.put((java.lang.String) r2, (java.lang.String) r11)
            java.lang.String r2 = "date_modified"
            long r3 = r7.mLastModified
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r9.put((java.lang.String) r2, (java.lang.Long) r3)
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0072
            java.lang.String r2 = "_data"
            r9.put((java.lang.String) r2, (java.lang.String) r8)
            android.content.ContentProviderClient r2 = r6.mMediaProvider
            android.net.Uri r3 = r6.mPlaylistsUri
            android.net.Uri r2 = r2.insert(r3, r9)
            long r0 = android.content.ContentUris.parseId(r2)
            java.lang.String r3 = "members"
            android.net.Uri r3 = android.net.Uri.withAppendedPath(r2, r3)
        L_0x006e:
            r12 = r0
            r14 = r2
            r15 = r3
            goto L_0x008b
        L_0x0072:
            android.net.Uri r2 = r6.mPlaylistsUri
            android.net.Uri r2 = android.content.ContentUris.withAppendedId(r2, r0)
            android.content.ContentProviderClient r3 = r6.mMediaProvider
            r4 = 0
            r3.update(r2, r9, r4, r4)
            java.lang.String r3 = "members"
            android.net.Uri r3 = android.net.Uri.withAppendedPath(r2, r3)
            android.content.ContentProviderClient r5 = r6.mMediaProvider
            r5.delete(r3, r4, r4)
            goto L_0x006e
        L_0x008b:
            int r0 = r10 + 1
            r1 = 0
            java.lang.String r16 = r8.substring(r1, r0)
            java.lang.String r5 = android.media.MediaFile.getMimeTypeForFile(r8)
            r0 = -1
            int r2 = r5.hashCode()
            r3 = -1165508903(0xffffffffba87bed9, float:-0.001035656)
            if (r2 == r3) goto L_0x00be
            r3 = 264230524(0xfbfd67c, float:1.891667E-29)
            if (r2 == r3) goto L_0x00b4
            r3 = 1872259501(0x6f9869ad, float:9.433895E28)
            if (r2 == r3) goto L_0x00ab
            goto L_0x00c8
        L_0x00ab:
            java.lang.String r2 = "application/vnd.ms-wpl"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x00c8
            goto L_0x00c9
        L_0x00b4:
            java.lang.String r1 = "audio/x-mpegurl"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x00c8
            r1 = 1
            goto L_0x00c9
        L_0x00be:
            java.lang.String r1 = "audio/x-scpls"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x00c8
            r1 = 2
            goto L_0x00c9
        L_0x00c8:
            r1 = r0
        L_0x00c9:
            switch(r1) {
                case 0: goto L_0x00ed;
                case 1: goto L_0x00de;
                case 2: goto L_0x00cf;
                default: goto L_0x00cc;
            }
        L_0x00cc:
            r17 = r5
            goto L_0x00fc
        L_0x00cf:
            r0 = r18
            r1 = r8
            r2 = r16
            r3 = r15
            r4 = r9
            r17 = r5
            r5 = r20
            r0.processPlsPlayList(r1, r2, r3, r4, r5)
            goto L_0x00fc
        L_0x00de:
            r17 = r5
            r0 = r18
            r1 = r8
            r2 = r16
            r3 = r15
            r4 = r9
            r5 = r20
            r0.processM3uPlayList(r1, r2, r3, r4, r5)
            goto L_0x00fc
        L_0x00ed:
            r17 = r5
            r0 = r18
            r1 = r8
            r2 = r16
            r3 = r15
            r4 = r9
            r5 = r20
            r0.processWplPlayList(r1, r2, r3, r4, r5)
        L_0x00fc:
            return
        L_0x00fd:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "bad path "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaScanner.processPlayList(android.media.MediaScanner$FileEntry, android.database.Cursor):void");
    }

    private void processPlayLists() throws RemoteException {
        Iterator<FileEntry> iterator = this.mPlayLists.iterator();
        Cursor fileList = null;
        try {
            fileList = this.mMediaProvider.query(this.mFilesUri, FILES_PRESCAN_PROJECTION, "media_type=2", (String[]) null, (String) null, (CancellationSignal) null);
            while (iterator.hasNext()) {
                FileEntry entry = iterator.next();
                if (entry.mLastModifiedChanged) {
                    processPlayList(entry, fileList);
                }
            }
            if (fileList == null) {
                return;
            }
        } catch (RemoteException e) {
            if (fileList == null) {
                return;
            }
        } catch (Throwable th) {
            if (fileList != null) {
                fileList.close();
            }
            throw th;
        }
        fileList.close();
    }

    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            this.mMediaProvider.close();
            native_finalize();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }
}
