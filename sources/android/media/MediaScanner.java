package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.drm.DrmManagerClient;
import android.graphics.BitmapFactory;
import android.mtp.MtpConstants;
import android.net.Uri;
import android.p007os.Build;
import android.p007os.Environment;
import android.p007os.RemoteException;
import android.p007os.SystemProperties;
import android.provider.MediaStore;
import android.provider.Settings;
import android.sax.Element;
import android.sax.ElementListener;
import android.sax.RootElement;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
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
/* loaded from: classes3.dex */
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
    private static final String[] FILES_PRESCAN_PROJECTION;
    private static final String[] ID3_GENRES;
    private static final int ID_PLAYLISTS_COLUMN_INDEX = 0;
    private static final String[] ID_PROJECTION;
    public static final String LAST_INTERNAL_SCAN_FINGERPRINT = "lastScanFingerprint";
    private static final String MUSIC_DIR = "/music/";
    private static final String NOTIFICATIONS_DIR = "/notifications/";
    private static final String OEM_SOUNDS_DIR;
    private static final int PATH_PLAYLISTS_COLUMN_INDEX = 1;
    private static final String[] PLAYLIST_MEMBERS_PROJECTION;
    private static final String PODCASTS_DIR = "/podcasts/";
    private static final String PRODUCT_SOUNDS_DIR;
    private static final String RINGTONES_DIR = "/ringtones/";
    public static final String SCANNED_BUILD_PREFS_NAME = "MediaScanBuild";
    private static final String SYSTEM_SOUNDS_DIR;
    private static final String TAG = "MediaScanner";
    private static HashMap<String, String> mMediaPaths;
    private static HashMap<String, String> mNoMediaPaths;
    private static String sLastInternalScanFingerprint;
    @UnsupportedAppUsage
    private final Uri mAudioUri;
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage
    private String mDefaultAlarmAlertFilename;
    private boolean mDefaultAlarmSet;
    @UnsupportedAppUsage
    private String mDefaultNotificationFilename;
    private boolean mDefaultNotificationSet;
    @UnsupportedAppUsage
    private String mDefaultRingtoneFilename;
    private boolean mDefaultRingtoneSet;
    private final Uri mFilesFullUri;
    @UnsupportedAppUsage
    private final Uri mFilesUri;
    private final Uri mImagesUri;
    @UnsupportedAppUsage
    private MediaInserter mMediaInserter;
    private final ContentProviderClient mMediaProvider;
    private int mMtpObjectHandle;
    private long mNativeContext;
    private int mOriginalCount;
    @UnsupportedAppUsage
    private final String mPackageName;
    private final Uri mPlaylistsUri;
    private final boolean mProcessGenres;
    private final boolean mProcessPlaylists;
    private final Uri mVideoUri;
    private final String mVolumeName;
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final BitmapFactory.Options mBitmapOptions = new BitmapFactory.Options();
    private final ArrayList<PlaylistEntry> mPlaylistEntries = new ArrayList<>();
    private final ArrayList<FileEntry> mPlayLists = new ArrayList<>();
    private DrmManagerClient mDrmManagerClient = null;
    @UnsupportedAppUsage
    private final MyMediaScannerClient mClient = new MyMediaScannerClient();

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup();

    private native void processDirectory(String str, MediaScannerClient mediaScannerClient);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean processFile(String str, String str2, MediaScannerClient mediaScannerClient);

    @UnsupportedAppUsage
    private native void setLocale(String str);

    public native byte[] extractAlbumArt(FileDescriptor fileDescriptor);

    static {
        System.loadLibrary("media_jni");
        native_init();
        FILES_PRESCAN_PROJECTION = new String[]{"_id", "_data", "format", "date_modified", "media_type"};
        ID_PROJECTION = new String[]{"_id"};
        PLAYLIST_MEMBERS_PROJECTION = new String[]{MediaStore.Audio.Playlists.Members.PLAYLIST_ID};
        SYSTEM_SOUNDS_DIR = Environment.getRootDirectory() + "/media/audio";
        OEM_SOUNDS_DIR = Environment.getOemDirectory() + "/media/audio";
        PRODUCT_SOUNDS_DIR = Environment.getProductDirectory() + "/media/audio";
        ID3_GENRES = new String[]{"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "Britpop", null, "Polsk Punk", "Beat", "Christian Gangsta", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "JPop", "Synthpop"};
        mNoMediaPaths = new HashMap<>();
        mMediaPaths = new HashMap<>();
    }

    /* loaded from: classes3.dex */
    private static class FileEntry {
        int mFormat;
        long mLastModified;
        @UnsupportedAppUsage
        boolean mLastModifiedChanged;
        int mMediaType;
        String mPath;
        @UnsupportedAppUsage
        long mRowId;

        @UnsupportedAppUsage
        @Deprecated
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

    /* loaded from: classes3.dex */
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
            SharedPreferences scanSettings = this.mContext.getSharedPreferences(SCANNED_BUILD_PREFS_NAME, 0);
            sLastInternalScanFingerprint = scanSettings.getString(LAST_INTERNAL_SCAN_FINGERPRINT, new String());
        }
        this.mAudioUri = MediaStore.Audio.Media.getContentUri(volumeName);
        this.mVideoUri = MediaStore.Video.Media.getContentUri(volumeName);
        this.mImagesUri = MediaStore.Images.Media.getContentUri(volumeName);
        this.mFilesUri = MediaStore.Files.getContentUri(volumeName);
        Uri filesFullUri = this.mFilesUri.buildUpon().appendQueryParameter("nonotify", "1").build();
        this.mFilesFullUri = MediaStore.setIncludeTrashed(MediaStore.setIncludePending(filesFullUri));
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

    /* JADX INFO: Access modifiers changed from: private */
    @UnsupportedAppUsage
    public boolean isDrmEnabled() {
        String prop = SystemProperties.get("drm.service.enabled");
        return prop != null && prop.equals("true");
    }

    /* loaded from: classes3.dex */
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
        @UnsupportedAppUsage
        @Deprecated
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
            boolean z;
            int i;
            boolean noMedia2;
            this.mMimeType = mimeType;
            this.mFileSize = fileSize;
            this.mIsDrm = false;
            this.mScanSuccess = true;
            if (!isDirectory) {
                if (!noMedia && MediaScanner.isNoMediaFile(path)) {
                    noMedia2 = true;
                } else {
                    noMedia2 = noMedia;
                }
                this.mNoMedia = noMedia2;
                if (this.mMimeType == null) {
                    this.mMimeType = MediaFile.getMimeTypeForFile(path);
                }
                if (MediaScanner.this.isDrmEnabled() && MediaFile.isDrmMimeType(this.mMimeType)) {
                    getMimeTypeFromDrm(path);
                }
            }
            FileEntry entry = MediaScanner.this.makeEntryFor(path);
            long delta = entry != null ? lastModified - entry.mLastModified : 0L;
            if (delta <= 1 && delta >= -1) {
                z = false;
            } else {
                z = true;
            }
            boolean wasModified = z;
            if (entry == null || wasModified) {
                if (!wasModified) {
                    i = 0;
                    entry = new FileEntry(0L, path, lastModified, isDirectory ? 12289 : 0, 0);
                } else {
                    entry.mLastModified = lastModified;
                    i = 0;
                }
                entry.mLastModifiedChanged = true;
            } else {
                i = 0;
            }
            if (MediaScanner.this.mProcessPlaylists && MediaFile.isPlayListMimeType(this.mMimeType)) {
                MediaScanner.this.mPlayLists.add(entry);
                return null;
            }
            this.mArtist = null;
            this.mAlbumArtist = null;
            this.mAlbum = null;
            this.mTitle = null;
            this.mComposer = null;
            this.mGenre = null;
            this.mTrack = i;
            this.mYear = i;
            this.mDuration = i;
            this.mPath = path;
            this.mDate = 0L;
            this.mLastModified = lastModified;
            this.mWriter = null;
            this.mCompilation = i;
            this.mWidth = i;
            this.mHeight = i;
            this.mColorStandard = -1;
            this.mColorTransfer = -1;
            this.mColorRange = -1;
            return entry;
        }

        @Override // android.media.MediaScannerClient
        @UnsupportedAppUsage
        public void scanFile(String path, long lastModified, long fileSize, boolean isDirectory, boolean noMedia) {
            doScanFile(path, null, lastModified, fileSize, isDirectory, false, noMedia);
        }

        /* JADX WARN: Removed duplicated region for block: B:104:0x019a A[Catch: RemoteException -> 0x0134, TRY_ENTER, TRY_LEAVE, TryCatch #3 {RemoteException -> 0x0134, blocks: (B:62:0x012d, B:69:0x0146, B:76:0x0156, B:83:0x0167, B:90:0x0178, B:97:0x0189, B:104:0x019a), top: B:137:0x012d }] */
        /* JADX WARN: Removed duplicated region for block: B:131:0x00cc A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:137:0x012d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @UnsupportedAppUsage
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Uri doScanFile(String path, String mimeType, long lastModified, long fileSize, boolean isDirectory, boolean scanAlways, boolean noMedia) {
            String str;
            FileEntry entry;
            boolean scanAlways2;
            String path2;
            boolean music;
            boolean z;
            try {
                entry = beginFile(path, mimeType, lastModified, fileSize, isDirectory, noMedia);
            } catch (RemoteException e) {
                e = e;
                str = path;
            }
            if (entry == null) {
                return null;
            }
            if (MediaScanner.this.mMtpObjectHandle != 0) {
                try {
                    entry.mRowId = 0L;
                } catch (RemoteException e2) {
                    e = e2;
                }
            }
            if (entry.mPath != null) {
                if ((!MediaScanner.this.mDefaultNotificationSet && doesPathHaveFilename(entry.mPath, MediaScanner.this.mDefaultNotificationFilename)) || ((!MediaScanner.this.mDefaultRingtoneSet && doesPathHaveFilename(entry.mPath, MediaScanner.this.mDefaultRingtoneFilename)) || (!MediaScanner.this.mDefaultAlarmSet && doesPathHaveFilename(entry.mPath, MediaScanner.this.mDefaultAlarmAlertFilename)))) {
                    Log.m64w(MediaScanner.TAG, "forcing rescan of " + entry.mPath + "since ringtone setting didn't finish");
                    z = true;
                } else if (MediaScanner.isSystemSoundWithMetadata(entry.mPath) && !Build.FINGERPRINT.equals(MediaScanner.sLastInternalScanFingerprint)) {
                    Log.m68i(MediaScanner.TAG, "forcing rescan of " + entry.mPath + " since build fingerprint changed");
                    z = true;
                }
                scanAlways2 = z;
                if (entry != null) {
                    try {
                    } catch (RemoteException e3) {
                        e = e3;
                        str = path;
                    }
                    if (entry.mLastModifiedChanged || scanAlways2) {
                        if (noMedia) {
                            try {
                                Uri result = endFile(entry, false, false, false, false, false, false);
                                return result;
                            } catch (RemoteException e4) {
                                e = e4;
                            }
                        } else {
                            boolean isaudio = MediaFile.isAudioMimeType(this.mMimeType);
                            boolean isvideo = MediaFile.isVideoMimeType(this.mMimeType);
                            boolean isimage = MediaFile.isImageMimeType(this.mMimeType);
                            try {
                                try {
                                    try {
                                        if (!isaudio && !isvideo && !isimage) {
                                            path2 = path;
                                            if (!isaudio || isvideo) {
                                                this.mScanSuccess = MediaScanner.this.processFile(path2, mimeType, this);
                                            }
                                            if (isimage) {
                                                try {
                                                    this.mScanSuccess = processImageFile(path2);
                                                } catch (RemoteException e5) {
                                                    e = e5;
                                                }
                                            }
                                            String lowpath = path2.toLowerCase(Locale.ROOT);
                                            boolean ringtones = !this.mScanSuccess && lowpath.indexOf(MediaScanner.RINGTONES_DIR) > 0;
                                            boolean notifications = !this.mScanSuccess && lowpath.indexOf(MediaScanner.NOTIFICATIONS_DIR) > 0;
                                            boolean alarms = !this.mScanSuccess && lowpath.indexOf(MediaScanner.ALARMS_DIR) > 0;
                                            boolean podcasts = !this.mScanSuccess && lowpath.indexOf(MediaScanner.PODCASTS_DIR) > 0;
                                            boolean audiobooks = !this.mScanSuccess && lowpath.indexOf(MediaScanner.AUDIOBOOKS_DIR) > 0;
                                            if (this.mScanSuccess) {
                                                if (lowpath.indexOf(MediaScanner.MUSIC_DIR) > 0 || (!ringtones && !notifications && !alarms && !podcasts && !audiobooks)) {
                                                    music = true;
                                                    Uri result2 = endFile(entry, ringtones, notifications, alarms, podcasts, audiobooks, music);
                                                    return result2;
                                                }
                                            }
                                            music = false;
                                            Uri result22 = endFile(entry, ringtones, notifications, alarms, podcasts, audiobooks, music);
                                            return result22;
                                        }
                                        Uri result222 = endFile(entry, ringtones, notifications, alarms, podcasts, audiobooks, music);
                                        return result222;
                                    } catch (RemoteException e6) {
                                        e = e6;
                                    }
                                    this.mScanSuccess = MediaScanner.this.processFile(path2, mimeType, this);
                                    if (isimage) {
                                    }
                                    String lowpath2 = path2.toLowerCase(Locale.ROOT);
                                    boolean ringtones2 = !this.mScanSuccess && lowpath2.indexOf(MediaScanner.RINGTONES_DIR) > 0;
                                    boolean notifications2 = !this.mScanSuccess && lowpath2.indexOf(MediaScanner.NOTIFICATIONS_DIR) > 0;
                                    boolean alarms2 = !this.mScanSuccess && lowpath2.indexOf(MediaScanner.ALARMS_DIR) > 0;
                                    boolean podcasts2 = !this.mScanSuccess && lowpath2.indexOf(MediaScanner.PODCASTS_DIR) > 0;
                                    boolean audiobooks2 = !this.mScanSuccess && lowpath2.indexOf(MediaScanner.AUDIOBOOKS_DIR) > 0;
                                    if (this.mScanSuccess) {
                                    }
                                    music = false;
                                } catch (RemoteException e7) {
                                    e = e7;
                                }
                                path2 = Environment.maybeTranslateEmulatedPathToInternal(new File(str)).getAbsolutePath();
                                if (!isaudio) {
                                }
                            } catch (RemoteException e8) {
                                e = e8;
                                Log.m69e(MediaScanner.TAG, "RemoteException in MediaScanner.scanFile()", e);
                                return null;
                            }
                            str = path;
                        }
                        Log.m69e(MediaScanner.TAG, "RemoteException in MediaScanner.scanFile()", e);
                        return null;
                    }
                }
                return null;
            }
            scanAlways2 = scanAlways;
            if (entry != null) {
            }
            return null;
        }

        private long parseDate(String date) {
            try {
                return this.mDateFormatter.parse(date).getTime();
            } catch (ParseException e) {
                return 0L;
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

        @Override // android.media.MediaScannerClient
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
            } else if (!name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.COMPOSER) && !name.startsWith("composer;")) {
                if (MediaScanner.this.mProcessGenres && (name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.GENRE) || name.startsWith("genre;"))) {
                    this.mGenre = getGenreName(value);
                    return;
                }
                if (name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.YEAR) || name.startsWith("year;")) {
                    this.mYear = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("tracknumber") || name.startsWith("tracknumber;")) {
                    int num = parseSubstring(value, 0, 0);
                    this.mTrack = ((this.mTrack / 1000) * 1000) + num;
                } else if (name.equalsIgnoreCase("discnumber") || name.equals("set") || name.startsWith("set;")) {
                    int num2 = parseSubstring(value, 0, 0);
                    this.mTrack = (num2 * 1000) + (this.mTrack % 1000);
                } else if (name.equalsIgnoreCase("duration")) {
                    this.mDuration = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("writer") || name.startsWith("writer;")) {
                    this.mWriter = value.trim();
                } else if (name.equalsIgnoreCase(MediaStore.Audio.AudioColumns.COMPILATION)) {
                    this.mCompilation = parseSubstring(value, 0, 0);
                } else if (name.equalsIgnoreCase("isdrm")) {
                    this.mIsDrm = parseSubstring(value, 0, 0) == 1;
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
                this.mComposer = value.trim();
            }
        }

        private boolean convertGenreCode(String input, String expected) {
            String output = getGenreName(input);
            if (output.equals(expected)) {
                return true;
            }
            Log.m72d(MediaScanner.TAG, "'" + input + "' -> '" + output + "', expected '" + expected + "'");
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
                    if (i == 0 && c == '(') {
                        parenthesized = true;
                    } else if (!Character.isDigit(c)) {
                        break;
                    } else {
                        number.append(c);
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
                            if (genreIndex < 255 && i + 1 < length) {
                                if (parenthesized && charAfterNumber == ')') {
                                    i++;
                                }
                                String ret = genreTagValue.substring(i).trim();
                                if (ret.length() != 0) {
                                    return ret;
                                }
                            } else {
                                return number.toString();
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
                if (this.mWidth > 0) {
                    return this.mHeight > 0;
                }
                return false;
            } catch (Throwable th) {
                return false;
            }
        }

        @Override // android.media.MediaScannerClient
        @UnsupportedAppUsage
        public void setMimeType(String mimeType) {
            if ("audio/mp4".equals(this.mMimeType) && mimeType.startsWith("video")) {
                return;
            }
            this.mMimeType = mimeType;
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

        /* JADX WARN: Code restructure failed: missing block: B:84:0x01b7, code lost:
            if (doesPathHaveFilename(r22.mPath, r21.this$0.mDefaultNotificationFilename) == false) goto L52;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v3 */
        /* JADX WARN: Type inference failed for: r5v4, types: [java.lang.String, java.lang.String[]] */
        /* JADX WARN: Type inference failed for: r5v5 */
        @UnsupportedAppUsage
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private Uri endFile(FileEntry entry, boolean ringtones, boolean notifications, boolean alarms, boolean podcasts, boolean audiobooks, boolean music) throws RemoteException {
            int degree;
            ?? r5;
            int lastSlash;
            if (this.mArtist == null || this.mArtist.length() == 0) {
                this.mArtist = this.mAlbumArtist;
            }
            ContentValues values = toValues();
            String title = values.getAsString("title");
            if (title == null || TextUtils.isEmpty(title.trim())) {
                title = MediaFile.getFileTitle(values.getAsString("_data"));
                values.put("title", title);
            }
            String album = values.getAsString("album");
            if (MediaStore.UNKNOWN_STRING.equals(album) && (lastSlash = (album = values.getAsString("_data")).lastIndexOf(47)) >= 0) {
                int previousSlash = 0;
                while (true) {
                    int idx = album.indexOf(47, previousSlash + 1);
                    if (idx < 0 || idx >= lastSlash) {
                        break;
                    }
                    previousSlash = idx;
                }
                if (previousSlash != 0) {
                    album = album.substring(previousSlash + 1, lastSlash);
                    values.put("album", album);
                }
            }
            long rowId = entry.mRowId;
            if (MediaFile.isAudioMimeType(this.mMimeType) && (rowId == 0 || MediaScanner.this.mMtpObjectHandle != 0)) {
                values.put(MediaStore.Audio.AudioColumns.IS_RINGTONE, Boolean.valueOf(ringtones));
                values.put(MediaStore.Audio.AudioColumns.IS_NOTIFICATION, Boolean.valueOf(notifications));
                values.put(MediaStore.Audio.AudioColumns.IS_ALARM, Boolean.valueOf(alarms));
                values.put(MediaStore.Audio.AudioColumns.IS_MUSIC, Boolean.valueOf(music));
                values.put(MediaStore.Audio.AudioColumns.IS_PODCAST, Boolean.valueOf(podcasts));
                values.put(MediaStore.Audio.AudioColumns.IS_AUDIOBOOK, Boolean.valueOf(audiobooks));
            } else if (MediaFile.isExifMimeType(this.mMimeType) && !this.mNoMedia) {
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(entry.mPath);
                } catch (Exception e) {
                }
                if (exif != null) {
                    long time = exif.getGpsDateTime();
                    if (time != -1) {
                        values.put("datetaken", Long.valueOf(time));
                    } else {
                        long time2 = exif.getDateTime();
                        if (time2 != -1 && Math.abs((this.mLastModified * 1000) - time2) >= 86400000) {
                            values.put("datetaken", Long.valueOf(time2));
                        }
                    }
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                    if (orientation != -1) {
                        if (orientation == 3) {
                            degree = 180;
                        } else if (orientation == 6) {
                            degree = 90;
                        } else if (orientation == 8) {
                            degree = 270;
                        } else {
                            degree = 0;
                        }
                        values.put("orientation", Integer.valueOf(degree));
                    }
                }
            }
            Uri tableUri = MediaScanner.this.mFilesUri;
            int mediaType = 0;
            MediaInserter inserter = MediaScanner.this.mMediaInserter;
            if (!this.mNoMedia) {
                if (MediaFile.isVideoMimeType(this.mMimeType)) {
                    tableUri = MediaScanner.this.mVideoUri;
                    mediaType = 3;
                } else if (MediaFile.isImageMimeType(this.mMimeType)) {
                    tableUri = MediaScanner.this.mImagesUri;
                    mediaType = 1;
                } else if (MediaFile.isAudioMimeType(this.mMimeType)) {
                    tableUri = MediaScanner.this.mAudioUri;
                    mediaType = 2;
                } else if (MediaFile.isPlayListMimeType(this.mMimeType)) {
                    tableUri = MediaScanner.this.mPlaylistsUri;
                    mediaType = 4;
                }
            }
            Uri result = null;
            boolean needToSetSettings = false;
            if (!notifications || MediaScanner.this.mDefaultNotificationSet) {
                if (ringtones && !MediaScanner.this.mDefaultRingtoneSet) {
                    if (TextUtils.isEmpty(MediaScanner.this.mDefaultRingtoneFilename) || doesPathHaveFilename(entry.mPath, MediaScanner.this.mDefaultRingtoneFilename)) {
                        needToSetSettings = true;
                    }
                } else if (alarms && !MediaScanner.this.mDefaultAlarmSet && (TextUtils.isEmpty(MediaScanner.this.mDefaultAlarmAlertFilename) || doesPathHaveFilename(entry.mPath, MediaScanner.this.mDefaultAlarmAlertFilename))) {
                    needToSetSettings = true;
                }
            } else {
                if (TextUtils.isEmpty(MediaScanner.this.mDefaultNotificationFilename)) {
                }
                needToSetSettings = true;
            }
            if (rowId == 0) {
                if (MediaScanner.this.mMtpObjectHandle != 0) {
                    values.put(MediaStore.MediaColumns.MEDIA_SCANNER_NEW_OBJECT_ID, Integer.valueOf(MediaScanner.this.mMtpObjectHandle));
                }
                if (tableUri == MediaScanner.this.mFilesUri) {
                    int format = entry.mFormat;
                    values.put("format", Integer.valueOf(format == 0 ? MediaFile.getFormatCode(entry.mPath, this.mMimeType) : format));
                }
                if (inserter == null || needToSetSettings) {
                    if (inserter != null) {
                        inserter.flushAll();
                    }
                    result = MediaScanner.this.mMediaProvider.insert(tableUri, values);
                } else if (entry.mFormat == 12289) {
                    inserter.insertwithPriority(tableUri, values);
                } else {
                    inserter.insert(tableUri, values);
                }
                if (result != null) {
                    rowId = ContentUris.parseId(result);
                    entry.mRowId = rowId;
                }
            } else {
                result = ContentUris.withAppendedId(tableUri, rowId);
                values.remove("_data");
                if (this.mNoMedia || mediaType == entry.mMediaType) {
                    r5 = 0;
                } else {
                    ContentValues mediaTypeValues = new ContentValues();
                    mediaTypeValues.put("media_type", Integer.valueOf(mediaType));
                    r5 = 0;
                    MediaScanner.this.mMediaProvider.update(ContentUris.withAppendedId(MediaScanner.this.mFilesUri, rowId), mediaTypeValues, null, null);
                }
                MediaScanner.this.mMediaProvider.update(result, values, r5, r5);
            }
            if (needToSetSettings) {
                if (notifications) {
                    setRingtoneIfNotSet(Settings.System.NOTIFICATION_SOUND, tableUri, rowId);
                    MediaScanner.this.mDefaultNotificationSet = true;
                } else if (ringtones) {
                    setRingtoneIfNotSet(Settings.System.RINGTONE, tableUri, rowId);
                    MediaScanner.this.mDefaultRingtoneSet = true;
                } else if (alarms) {
                    setRingtoneIfNotSet(Settings.System.ALARM_ALERT, tableUri, rowId);
                    MediaScanner.this.mDefaultAlarmSet = true;
                }
            }
            return result;
        }

        private boolean doesPathHaveFilename(String path, String filename) {
            int pathFilenameStart = path.lastIndexOf(File.separatorChar) + 1;
            int filenameLength = filename.length();
            return path.regionMatches(pathFilenameStart, filename, 0, filenameLength) && pathFilenameStart + filenameLength == path.length();
        }

        private void setRingtoneIfNotSet(String settingName, Uri uri, long rowId) {
            if (!MediaScanner.this.wasRingtoneAlreadySet(settingName)) {
                ContentResolver cr = MediaScanner.this.mContext.getContentResolver();
                String existingSettingValue = Settings.System.getString(cr, settingName);
                if (TextUtils.isEmpty(existingSettingValue)) {
                    Uri settingUri = Settings.System.getUriFor(settingName);
                    Uri ringtoneUri = ContentUris.withAppendedId(uri, rowId);
                    RingtoneManager.setActualDefaultRingtoneUri(MediaScanner.this.mContext, RingtoneManager.getDefaultType(settingUri), ringtoneUri);
                }
                Settings.System.putInt(cr, MediaScanner.this.settingSetIndicatorName(settingName), 1);
            }
        }

        @UnsupportedAppUsage
        @Deprecated
        private int getFileTypeFromDrm(String path) {
            return 0;
        }

        private void getMimeTypeFromDrm(String path) {
            this.mMimeType = null;
            if (MediaScanner.this.mDrmManagerClient == null) {
                MediaScanner.this.mDrmManagerClient = new DrmManagerClient(MediaScanner.this.mContext);
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

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSystemSoundWithMetadata(String path) {
        if (!path.startsWith(SYSTEM_SOUNDS_DIR + ALARMS_DIR)) {
            if (!path.startsWith(SYSTEM_SOUNDS_DIR + RINGTONES_DIR)) {
                if (!path.startsWith(SYSTEM_SOUNDS_DIR + NOTIFICATIONS_DIR)) {
                    if (!path.startsWith(OEM_SOUNDS_DIR + ALARMS_DIR)) {
                        if (!path.startsWith(OEM_SOUNDS_DIR + RINGTONES_DIR)) {
                            if (!path.startsWith(OEM_SOUNDS_DIR + NOTIFICATIONS_DIR)) {
                                if (!path.startsWith(PRODUCT_SOUNDS_DIR + ALARMS_DIR)) {
                                    if (!path.startsWith(PRODUCT_SOUNDS_DIR + RINGTONES_DIR)) {
                                        if (path.startsWith(PRODUCT_SOUNDS_DIR + NOTIFICATIONS_DIR)) {
                                            return true;
                                        }
                                        return false;
                                    }
                                    return true;
                                }
                                return true;
                            }
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String settingSetIndicatorName(String base) {
        return base + "_set";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean wasRingtoneAlreadySet(String name) {
        ContentResolver cr = this.mContext.getContentResolver();
        String indicatorName = settingSetIndicatorName(name);
        try {
            return Settings.System.getInt(cr, indicatorName) != 0;
        } catch (Settings.SettingNotFoundException e) {
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x014c  */
    @UnsupportedAppUsage
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void prescan(String filePath, boolean prescanFiles) throws RemoteException {
        String where;
        String[] selectionArgs;
        MediaBulkDeleter deleter;
        MediaBulkDeleter deleter2;
        MediaBulkDeleter deleter3;
        Cursor c = null;
        this.mPlayLists.clear();
        int i = 1;
        int i2 = 2;
        int i3 = 0;
        if (filePath != null) {
            where = "_id>? AND _data=?";
            selectionArgs = new String[]{"", filePath};
        } else {
            where = "_id>?";
            selectionArgs = new String[]{""};
        }
        String[] selectionArgs2 = selectionArgs;
        String where2 = where;
        this.mDefaultRingtoneSet = wasRingtoneAlreadySet(Settings.System.RINGTONE);
        this.mDefaultNotificationSet = wasRingtoneAlreadySet(Settings.System.NOTIFICATION_SOUND);
        this.mDefaultAlarmSet = wasRingtoneAlreadySet(Settings.System.ALARM_ALERT);
        Uri.Builder builder = this.mFilesUri.buildUpon();
        builder.appendQueryParameter(MediaStore.PARAM_DELETE_DATA, "false");
        MediaBulkDeleter deleter4 = new MediaBulkDeleter(this.mMediaProvider, builder.build());
        if (prescanFiles) {
            try {
                Uri limitUri = this.mFilesUri.buildUpon().appendQueryParameter("limit", "1000").build();
                long lastId = Long.MIN_VALUE;
                while (true) {
                    selectionArgs2[i3] = "" + lastId;
                    if (c != null) {
                        try {
                            c.close();
                            c = null;
                        } catch (Throwable th) {
                            th = th;
                            deleter2 = deleter4;
                            if (c != null) {
                                c.close();
                            }
                            deleter2.flush();
                            throw th;
                        }
                    }
                    long lastId2 = lastId;
                    deleter3 = deleter4;
                    Uri.Builder builder2 = builder;
                    try {
                        c = this.mMediaProvider.query(limitUri, FILES_PRESCAN_PROJECTION, where2, selectionArgs2, "_id", null);
                        if (c == null) {
                            break;
                        }
                        int num = c.getCount();
                        if (num == 0) {
                            break;
                        }
                        lastId = lastId2;
                        while (c.moveToNext()) {
                            long rowId = c.getLong(i3);
                            String path = c.getString(i);
                            int format = c.getInt(i2);
                            c.getLong(3);
                            lastId = rowId;
                            if (path != null && path.startsWith("/")) {
                                int i4 = i3;
                                try {
                                    i4 = Os.access(path, OsConstants.F_OK) ? 1 : 0;
                                } catch (ErrnoException e) {
                                } catch (Throwable th2) {
                                    th = th2;
                                    deleter2 = deleter3;
                                    if (c != null) {
                                    }
                                    deleter2.flush();
                                    throw th;
                                }
                                if (i4 == 0 && !MtpConstants.isAbstractObject(format)) {
                                    String mimeType = MediaFile.getMimeTypeForFile(path);
                                    if (!MediaFile.isPlayListMimeType(mimeType)) {
                                        deleter2 = deleter3;
                                        try {
                                            deleter2.delete(rowId);
                                            if (path.toLowerCase(Locale.US).endsWith("/.nomedia")) {
                                                deleter2.flush();
                                                String parent = new File(path).getParent();
                                                this.mMediaProvider.call(MediaStore.UNHIDE_CALL, parent, null);
                                            }
                                            deleter3 = deleter2;
                                            i = 1;
                                            i2 = 2;
                                            i3 = 0;
                                        } catch (Throwable th3) {
                                            th = th3;
                                            if (c != null) {
                                            }
                                            deleter2.flush();
                                            throw th;
                                        }
                                    }
                                }
                            }
                            deleter2 = deleter3;
                            deleter3 = deleter2;
                            i = 1;
                            i2 = 2;
                            i3 = 0;
                        }
                        deleter4 = deleter3;
                        builder = builder2;
                        i = 1;
                        i2 = 2;
                        i3 = 0;
                    } catch (Throwable th4) {
                        th = th4;
                        deleter2 = deleter3;
                    }
                }
                deleter = deleter3;
            } catch (Throwable th5) {
                th = th5;
                deleter2 = deleter4;
            }
        } else {
            deleter = deleter4;
        }
        if (c != null) {
            c.close();
        }
        deleter.flush();
        this.mOriginalCount = 0;
        Cursor c2 = this.mMediaProvider.query(this.mImagesUri, ID_PROJECTION, null, null, null, null);
        if (c2 != null) {
            this.mOriginalCount = c2.getCount();
            c2.close();
        }
    }

    /* loaded from: classes3.dex */
    static class MediaBulkDeleter {
        final Uri mBaseUri;
        final ContentProviderClient mProvider;
        StringBuilder whereClause = new StringBuilder();
        ArrayList<String> whereArgs = new ArrayList<>(100);

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
                String[] foo = new String[size];
                String[] foo2 = (String[]) this.whereArgs.toArray(foo);
                ContentProviderClient contentProviderClient = this.mProvider;
                Uri uri = this.mBaseUri;
                contentProviderClient.delete(uri, "_id IN (" + this.whereClause.toString() + ")", foo2);
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
            try {
                try {
                    System.currentTimeMillis();
                    prescan(null, true);
                    System.currentTimeMillis();
                    this.mMediaInserter = new MediaInserter(this.mMediaProvider, 500);
                    for (String str : directories) {
                        processDirectory(str, this.mClient);
                    }
                    this.mMediaInserter.flushAll();
                    this.mMediaInserter = null;
                    System.currentTimeMillis();
                    postscan(directories);
                    System.currentTimeMillis();
                } catch (SQLException e) {
                    Log.m69e(TAG, "SQLException in MediaScanner.scan()", e);
                } catch (UnsupportedOperationException e2) {
                    Log.m69e(TAG, "UnsupportedOperationException in MediaScanner.scan()", e2);
                }
            } catch (RemoteException e3) {
                Log.m69e(TAG, "RemoteException in MediaScanner.scan()", e3);
            }
        } finally {
            releaseResources();
        }
    }

    @UnsupportedAppUsage
    public Uri scanSingleFile(String path, String mimeType) {
        try {
            prescan(path, true);
            File file = new File(path);
            if (file.exists() && file.canRead()) {
                long lastModifiedSeconds = file.lastModified() / 1000;
                return this.mClient.doScanFile(path, mimeType, lastModifiedSeconds, file.length(), false, true, isNoMediaPath(path));
            }
            return null;
        } catch (RemoteException e) {
            Log.m69e(TAG, "RemoteException in MediaScanner.scanFile()", e);
            return null;
        } finally {
            releaseResources();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNoMediaFile(String path) {
        int lastSlash;
        File file = new File(path);
        if (!file.isDirectory() && (lastSlash = path.lastIndexOf(47)) >= 0 && lastSlash + 2 < path.length()) {
            if (path.regionMatches(lastSlash + 1, "._", 0, 2)) {
                return true;
            }
            if (path.regionMatches(true, path.length() - 4, ".jpg", 0, 4)) {
                if (path.regionMatches(true, lastSlash + 1, "AlbumArt_{", 0, 10) || path.regionMatches(true, lastSlash + 1, "AlbumArt.", 0, 9)) {
                    return true;
                }
                int length = (path.length() - lastSlash) - 1;
                if ((length == 17 && path.regionMatches(true, lastSlash + 1, "AlbumArtSmall", 0, 13)) || (length == 10 && path.regionMatches(true, lastSlash + 1, "Folder", 0, 6))) {
                    return true;
                }
            }
        }
        return false;
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

    @UnsupportedAppUsage
    public static boolean isNoMediaPath(String path) {
        if (path == null) {
            return false;
        }
        if (path.indexOf("/.") >= 0) {
            return true;
        }
        int firstSlash = path.lastIndexOf(47);
        if (firstSlash <= 0) {
            return false;
        }
        String parent = path.substring(0, firstSlash);
        synchronized (MediaScanner.class) {
            if (mNoMediaPaths.containsKey(parent)) {
                return true;
            }
            if (!mMediaPaths.containsKey(parent)) {
                int offset = 1;
                while (offset >= 0) {
                    int slashIndex = path.indexOf(47, offset);
                    if (slashIndex > offset) {
                        slashIndex++;
                        File file = new File(path.substring(0, slashIndex) + MediaStore.MEDIA_IGNORE_FILENAME);
                        if (file.exists()) {
                            mNoMediaPaths.put(parent, "");
                            return true;
                        }
                    }
                    offset = slashIndex;
                }
                mMediaPaths.put(parent, "");
            }
            return isNoMediaFile(path);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ce, code lost:
        if (r18 != null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e1, code lost:
        if (r18 != null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e3, code lost:
        r18.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e6, code lost:
        releaseResources();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ea, code lost:
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void scanMtpFile(String path, int objectHandle, int format) {
        int i;
        String mimeType = MediaFile.getMimeType(path, format);
        File file = new File(path);
        long lastModifiedSeconds = file.lastModified() / 1000;
        if (!MediaFile.isAudioMimeType(mimeType) && !MediaFile.isVideoMimeType(mimeType) && !MediaFile.isImageMimeType(mimeType) && !MediaFile.isPlayListMimeType(mimeType) && !MediaFile.isDrmMimeType(mimeType)) {
            ContentValues values = new ContentValues();
            values.put("_size", Long.valueOf(file.length()));
            values.put("date_modified", Long.valueOf(lastModifiedSeconds));
            try {
                String[] whereArgs = {Integer.toString(objectHandle)};
                this.mMediaProvider.update(MediaStore.Files.getMtpObjectsUri(this.mVolumeName), values, "_id=?", whereArgs);
                return;
            } catch (RemoteException e) {
                Log.m69e(TAG, "RemoteException in scanMtpFile", e);
                return;
            }
        }
        this.mMtpObjectHandle = objectHandle;
        Cursor fileList = null;
        try {
            if (MediaFile.isPlayListMimeType(mimeType)) {
                prescan(null, true);
                FileEntry entry = makeEntryFor(path);
                if (entry != null) {
                    Cursor fileList2 = this.mMediaProvider.query(this.mFilesUri, FILES_PRESCAN_PROJECTION, null, null, null, null);
                    try {
                        processPlayList(entry, fileList2);
                        fileList = fileList2;
                    } catch (RemoteException e2) {
                        e = e2;
                        fileList = fileList2;
                        i = 0;
                        Log.m69e(TAG, "RemoteException in MediaScanner.scanFile()", e);
                        this.mMtpObjectHandle = i;
                    } catch (Throwable th) {
                        th = th;
                        fileList = fileList2;
                        i = 0;
                        this.mMtpObjectHandle = i;
                        if (fileList != null) {
                        }
                        releaseResources();
                        throw th;
                    }
                }
                i = 0;
            } else {
                prescan(path, false);
                i = 0;
                try {
                    try {
                        this.mClient.doScanFile(path, mimeType, lastModifiedSeconds, file.length(), format == 12289, true, isNoMediaPath(path));
                    } catch (Throwable th2) {
                        th = th2;
                        this.mMtpObjectHandle = i;
                        if (fileList != null) {
                            fileList.close();
                        }
                        releaseResources();
                        throw th;
                    }
                } catch (RemoteException e3) {
                    e = e3;
                    Log.m69e(TAG, "RemoteException in MediaScanner.scanFile()", e);
                    this.mMtpObjectHandle = i;
                }
            }
            this.mMtpObjectHandle = i;
        } catch (RemoteException e4) {
            e = e4;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0042, code lost:
        if (r3 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0044, code lost:
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:
        if (r3 == null) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
        return null;
     */
    @UnsupportedAppUsage
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    FileEntry makeEntryFor(String path) {
        Cursor c = null;
        try {
            String[] selectionArgs = {path};
            c = this.mMediaProvider.query(this.mFilesFullUri, FILES_PRESCAN_PROJECTION, "_data=?", selectionArgs, null, null);
            if (c != null && c.moveToFirst()) {
                long rowId = c.getLong(0);
                long lastModified = c.getLong(3);
                int format = c.getInt(2);
                int mediaType = c.getInt(4);
                FileEntry fileEntry = new FileEntry(rowId, path, lastModified, format, mediaType);
                if (c != null) {
                    c.close();
                }
                return fileEntry;
            }
        } catch (RemoteException e) {
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    private int matchPaths(String path1, String path2) {
        int start2;
        int end1 = path1.length();
        int end2 = path2.length();
        int result = 0;
        int end12 = end1;
        while (true) {
            int end22 = end2;
            if (end12 <= 0 || end22 <= 0) {
                break;
            }
            int slash1 = path1.lastIndexOf(47, end12 - 1);
            int slash2 = path2.lastIndexOf(47, end22 - 1);
            int backSlash1 = path1.lastIndexOf(92, end12 - 1);
            int backSlash2 = path2.lastIndexOf(92, end22 - 1);
            int start1 = slash1 > backSlash1 ? slash1 : backSlash1;
            int start22 = slash2 > backSlash2 ? slash2 : backSlash2;
            int start12 = start1 < 0 ? 0 : start1 + 1;
            if (start22 >= 0) {
                start2 = start22 + 1;
            } else {
                start2 = 0;
            }
            int length = end12 - start12;
            if (end22 - start2 != length || !path1.regionMatches(true, start12, path2, start2, length)) {
                break;
            }
            result++;
            end12 = start12 - 1;
            end2 = start2 - 1;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void cachePlaylistEntry(String line, String playListDirectory) {
        PlaylistEntry entry = new PlaylistEntry();
        int entryLength = line.length();
        while (entryLength > 0 && Character.isWhitespace(line.charAt(entryLength - 1))) {
            entryLength--;
        }
        if (entryLength < 3) {
            return;
        }
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

    private void processCachedPlaylist(Cursor fileList, ContentValues values, Uri playlistUri) {
        int i;
        long rowId;
        String data;
        fileList.moveToPosition(-1);
        do {
            if (!fileList.moveToNext()) {
                break;
            }
            rowId = fileList.getLong(0);
            data = fileList.getString(1);
        } while (!matchEntries(rowId, data));
        int len = this.mPlaylistEntries.size();
        int index = 0;
        for (i = 0; i < len; i++) {
            PlaylistEntry entry = this.mPlaylistEntries.get(i);
            if (entry.bestmatchlevel > 0) {
                try {
                    values.clear();
                    values.put("play_order", Integer.valueOf(index));
                    values.put("audio_id", Long.valueOf(entry.bestmatchid));
                    this.mMediaProvider.insert(playlistUri, values);
                    index++;
                } catch (RemoteException e) {
                    Log.m69e(TAG, "RemoteException in MediaScanner.processCachedPlaylist()", e);
                    return;
                }
            }
        }
        this.mPlaylistEntries.clear();
    }

    private void processM3uPlayList(String path, String playListDirectory, Uri uri, ContentValues values, Cursor fileList) {
        BufferedReader reader = null;
        try {
            try {
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
                        reader.close();
                    }
                } catch (IOException e) {
                    Log.m69e(TAG, "IOException in MediaScanner.processM3uPlayList()", e);
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (IOException e2) {
                Log.m69e(TAG, "IOException in MediaScanner.processM3uPlayList()", e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    Log.m69e(TAG, "IOException in MediaScanner.processM3uPlayList()", e3);
                }
            }
            throw th;
        }
    }

    private void processPlsPlayList(String path, String playListDirectory, Uri uri, ContentValues values, Cursor fileList) {
        int equals;
        BufferedReader reader = null;
        try {
            try {
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
                        reader.close();
                    }
                } catch (IOException e) {
                    Log.m69e(TAG, "IOException in MediaScanner.processPlsPlayList()", e);
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (IOException e2) {
                Log.m69e(TAG, "IOException in MediaScanner.processPlsPlayList()", e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    reader.close();
                } catch (IOException e3) {
                    Log.m69e(TAG, "IOException in MediaScanner.processPlsPlayList()", e3);
                }
            }
            throw th;
        }
    }

    /* loaded from: classes3.dex */
    class WplHandler implements ElementListener {
        final ContentHandler handler;
        String playListDirectory;

        public WplHandler(String playListDirectory, Uri uri, Cursor fileList) {
            this.playListDirectory = playListDirectory;
            RootElement root = new RootElement("smil");
            Element body = root.getChild("body");
            Element seq = body.getChild("seq");
            Element media = seq.getChild(MediaStore.AUTHORITY);
            media.setElementListener(this);
            this.handler = root.getContentHandler();
        }

        @Override // android.sax.StartElementListener
        public void start(Attributes attributes) {
            String path = attributes.getValue("", "src");
            if (path != null) {
                MediaScanner.this.cachePlaylistEntry(path, this.playListDirectory);
            }
        }

        @Override // android.sax.EndElementListener
        public void end() {
        }

        ContentHandler getContentHandler() {
            return this.handler;
        }
    }

    private void processWplPlayList(String path, String playListDirectory, Uri uri, ContentValues values, Cursor fileList) {
        FileInputStream fis = null;
        try {
            try {
                try {
                    try {
                        File f = new File(path);
                        if (f.exists()) {
                            fis = new FileInputStream(f);
                            this.mPlaylistEntries.clear();
                            Xml.parse(fis, Xml.findEncodingByName("UTF-8"), new WplHandler(playListDirectory, uri, fileList).getContentHandler());
                            processCachedPlaylist(fileList, values, uri);
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (fis != null) {
                            fis.close();
                        }
                    }
                } catch (SAXException e2) {
                    e2.printStackTrace();
                    if (fis != null) {
                        fis.close();
                    }
                }
            } catch (IOException e3) {
                Log.m69e(TAG, "IOException in MediaScanner.processWplPlayList()", e3);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fis.close();
                } catch (IOException e4) {
                    Log.m69e(TAG, "IOException in MediaScanner.processWplPlayList()", e4);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b1, code lost:
        if (r5.equals("application/vnd.ms-wpl") == false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void processPlayList(FileEntry entry, Cursor fileList) throws RemoteException {
        Uri uri;
        Uri membersUri;
        String path = entry.mPath;
        ContentValues values = new ContentValues();
        int lastSlash = path.lastIndexOf(47);
        if (lastSlash < 0) {
            throw new IllegalArgumentException("bad path " + path);
        }
        long rowId = entry.mRowId;
        String name = values.getAsString("name");
        if (name == null && (name = values.getAsString("title")) == null) {
            int lastDot = path.lastIndexOf(46);
            name = lastDot < 0 ? path.substring(lastSlash + 1) : path.substring(lastSlash + 1, lastDot);
        }
        values.put("name", name);
        values.put("date_modified", Long.valueOf(entry.mLastModified));
        if (rowId == 0) {
            values.put("_data", path);
            uri = this.mMediaProvider.insert(this.mPlaylistsUri, values);
            rowId = ContentUris.parseId(uri);
            membersUri = Uri.withAppendedPath(uri, "members");
        } else {
            uri = ContentUris.withAppendedId(this.mPlaylistsUri, rowId);
            this.mMediaProvider.update(uri, values, null, null);
            membersUri = Uri.withAppendedPath(uri, "members");
            this.mMediaProvider.delete(membersUri, null, null);
        }
        Uri membersUri2 = membersUri;
        char c = 0;
        String playListDirectory = path.substring(0, lastSlash + 1);
        String mimeType = MediaFile.getMimeTypeForFile(path);
        int hashCode = mimeType.hashCode();
        if (hashCode == -1165508903) {
            if (mimeType.equals("audio/x-scpls")) {
                c = 2;
            }
            c = '\uffff';
        } else if (hashCode != 264230524) {
            if (hashCode == 1872259501) {
            }
            c = '\uffff';
        } else {
            if (mimeType.equals("audio/x-mpegurl")) {
                c = 1;
            }
            c = '\uffff';
        }
        switch (c) {
            case 0:
                processWplPlayList(path, playListDirectory, membersUri2, values, fileList);
                return;
            case 1:
                processM3uPlayList(path, playListDirectory, membersUri2, values, fileList);
                return;
            case 2:
                processPlsPlayList(path, playListDirectory, membersUri2, values, fileList);
                return;
            default:
                return;
        }
    }

    private void processPlayLists() throws RemoteException {
        Iterator<FileEntry> iterator = this.mPlayLists.iterator();
        Cursor fileList = null;
        try {
            fileList = this.mMediaProvider.query(this.mFilesUri, FILES_PRESCAN_PROJECTION, "media_type=2", null, null, null);
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

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            this.mMediaProvider.close();
            native_finalize();
        }
    }

    protected void finalize() throws Throwable {
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
