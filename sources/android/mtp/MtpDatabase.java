package android.mtp;

import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.mtp.MtpStorageManager;
import android.net.Uri;
import android.p007os.BatteryManager;
import android.p007os.RemoteException;
import android.p007os.SystemProperties;
import android.p007os.storage.StorageVolume;
import android.provider.MediaStore;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.android.internal.annotations.VisibleForNative;
import com.google.android.collect.Sets;
import dalvik.system.CloseGuard;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/* loaded from: classes3.dex */
public class MtpDatabase implements AutoCloseable {
    private static final int[] AUDIO_PROPERTIES;
    private static final int[] DEVICE_PROPERTIES;
    private static final int[] FILE_PROPERTIES;
    private static final int[] IMAGE_PROPERTIES;
    private static final String NO_MEDIA = ".nomedia";
    private static final String PATH_WHERE = "_data=?";
    private static final int[] PLAYBACK_FORMATS;
    private static final int[] VIDEO_PROPERTIES;
    private int mBatteryLevel;
    private int mBatteryScale;
    private final Context mContext;
    private SharedPreferences mDeviceProperties;
    private int mDeviceType;
    private MtpStorageManager mManager;
    private final ContentProviderClient mMediaProvider;
    @VisibleForNative
    private long mNativeContext;
    private MtpServer mServer;
    private static final String TAG = MtpDatabase.class.getSimpleName();
    private static final String[] ID_PROJECTION = {"_id"};
    private static final String[] PATH_PROJECTION = {"_data"};
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final HashMap<String, MtpStorage> mStorageMap = new HashMap<>();
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByProperty = new SparseArray<>();
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByFormat = new SparseArray<>();
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() { // from class: android.mtp.MtpDatabase.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                MtpDatabase.this.mBatteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int newLevel = intent.getIntExtra("level", 0);
                if (newLevel != MtpDatabase.this.mBatteryLevel) {
                    MtpDatabase.this.mBatteryLevel = newLevel;
                    if (MtpDatabase.this.mServer != null) {
                        MtpDatabase.this.mServer.sendDevicePropertyChanged(MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL);
                    }
                }
            }
        }
    };

    private final native void native_finalize();

    private final native void native_setup();

    static {
        System.loadLibrary("media_jni");
        PLAYBACK_FORMATS = new int[]{12288, 12289, 12292, 12293, 12296, 12297, 12299, MtpConstants.FORMAT_EXIF_JPEG, MtpConstants.FORMAT_TIFF_EP, MtpConstants.FORMAT_BMP, MtpConstants.FORMAT_GIF, MtpConstants.FORMAT_JFIF, MtpConstants.FORMAT_PNG, MtpConstants.FORMAT_TIFF, MtpConstants.FORMAT_WMA, MtpConstants.FORMAT_OGG, MtpConstants.FORMAT_AAC, MtpConstants.FORMAT_MP4_CONTAINER, MtpConstants.FORMAT_MP2, MtpConstants.FORMAT_3GP_CONTAINER, MtpConstants.FORMAT_ABSTRACT_AV_PLAYLIST, MtpConstants.FORMAT_WPL_PLAYLIST, MtpConstants.FORMAT_M3U_PLAYLIST, MtpConstants.FORMAT_PLS_PLAYLIST, MtpConstants.FORMAT_XML_DOCUMENT, MtpConstants.FORMAT_FLAC, MtpConstants.FORMAT_DNG, MtpConstants.FORMAT_HEIF};
        FILE_PROPERTIES = new int[]{MtpConstants.PROPERTY_STORAGE_ID, MtpConstants.PROPERTY_OBJECT_FORMAT, MtpConstants.PROPERTY_PROTECTION_STATUS, MtpConstants.PROPERTY_OBJECT_SIZE, MtpConstants.PROPERTY_OBJECT_FILE_NAME, MtpConstants.PROPERTY_DATE_MODIFIED, MtpConstants.PROPERTY_PERSISTENT_UID, MtpConstants.PROPERTY_PARENT_OBJECT, MtpConstants.PROPERTY_NAME, MtpConstants.PROPERTY_DISPLAY_NAME, MtpConstants.PROPERTY_DATE_ADDED};
        AUDIO_PROPERTIES = new int[]{MtpConstants.PROPERTY_ARTIST, MtpConstants.PROPERTY_ALBUM_NAME, MtpConstants.PROPERTY_ALBUM_ARTIST, MtpConstants.PROPERTY_TRACK, MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE, MtpConstants.PROPERTY_DURATION, MtpConstants.PROPERTY_COMPOSER, MtpConstants.PROPERTY_AUDIO_WAVE_CODEC, MtpConstants.PROPERTY_BITRATE_TYPE, MtpConstants.PROPERTY_AUDIO_BITRATE, MtpConstants.PROPERTY_NUMBER_OF_CHANNELS, MtpConstants.PROPERTY_SAMPLE_RATE};
        VIDEO_PROPERTIES = new int[]{MtpConstants.PROPERTY_ARTIST, MtpConstants.PROPERTY_ALBUM_NAME, MtpConstants.PROPERTY_DURATION, MtpConstants.PROPERTY_DESCRIPTION};
        IMAGE_PROPERTIES = new int[]{MtpConstants.PROPERTY_DESCRIPTION};
        DEVICE_PROPERTIES = new int[]{MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER, MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME, MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE, MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL, MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE};
    }

    @VisibleForNative
    private int[] getSupportedObjectProperties(int format) {
        switch (format) {
            case 12296:
            case 12297:
            case MtpConstants.FORMAT_WMA /* 47361 */:
            case MtpConstants.FORMAT_OGG /* 47362 */:
            case MtpConstants.FORMAT_AAC /* 47363 */:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(AUDIO_PROPERTIES)).toArray();
            case 12299:
            case MtpConstants.FORMAT_WMV /* 47489 */:
            case MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(VIDEO_PROPERTIES)).toArray();
            case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case MtpConstants.FORMAT_BMP /* 14340 */:
            case MtpConstants.FORMAT_GIF /* 14343 */:
            case MtpConstants.FORMAT_PNG /* 14347 */:
            case MtpConstants.FORMAT_DNG /* 14353 */:
            case MtpConstants.FORMAT_HEIF /* 14354 */:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(IMAGE_PROPERTIES)).toArray();
            default:
                return FILE_PROPERTIES;
        }
    }

    public static Uri getObjectPropertiesUri(int format, String volumeName) {
        switch (format) {
            case 12296:
            case 12297:
            case MtpConstants.FORMAT_WMA /* 47361 */:
            case MtpConstants.FORMAT_OGG /* 47362 */:
            case MtpConstants.FORMAT_AAC /* 47363 */:
                return MediaStore.Audio.Media.getContentUri(volumeName);
            case 12299:
            case MtpConstants.FORMAT_WMV /* 47489 */:
            case MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                return MediaStore.Video.Media.getContentUri(volumeName);
            case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case MtpConstants.FORMAT_BMP /* 14340 */:
            case MtpConstants.FORMAT_GIF /* 14343 */:
            case MtpConstants.FORMAT_PNG /* 14347 */:
            case MtpConstants.FORMAT_DNG /* 14353 */:
            case MtpConstants.FORMAT_HEIF /* 14354 */:
                return MediaStore.Images.Media.getContentUri(volumeName);
            default:
                return MediaStore.Files.getContentUri(volumeName);
        }
    }

    @VisibleForNative
    private int[] getSupportedDeviceProperties() {
        return DEVICE_PROPERTIES;
    }

    @VisibleForNative
    private int[] getSupportedPlaybackFormats() {
        return PLAYBACK_FORMATS;
    }

    @VisibleForNative
    private int[] getSupportedCaptureFormats() {
        return null;
    }

    public MtpDatabase(Context context, String[] subDirectories) {
        native_setup();
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mMediaProvider = context.getContentResolver().acquireContentProviderClient(MediaStore.AUTHORITY);
        this.mManager = new MtpStorageManager(new MtpStorageManager.MtpNotifier() { // from class: android.mtp.MtpDatabase.2
            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectAdded(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectAdded(id);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectRemoved(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectRemoved(id);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectInfoChanged(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectInfoChanged(id);
                }
            }
        }, subDirectories == null ? null : Sets.newHashSet(subDirectories));
        initDeviceProperties(context);
        this.mDeviceType = SystemProperties.getInt("sys.usb.mtp.device_type", 0);
        this.mCloseGuard.open("close");
    }

    public void setServer(MtpServer server) {
        this.mServer = server;
        try {
            this.mContext.unregisterReceiver(this.mBatteryReceiver);
        } catch (IllegalArgumentException e) {
        }
        if (server != null) {
            this.mContext.registerReceiver(this.mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mManager.close();
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            if (this.mMediaProvider != null) {
                this.mMediaProvider.close();
            }
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

    public void addStorage(StorageVolume storage) {
        MtpStorage mtpStorage = this.mManager.addMtpStorage(storage);
        this.mStorageMap.put(storage.getPath(), mtpStorage);
        if (this.mServer != null) {
            this.mServer.addStorage(mtpStorage);
        }
    }

    public void removeStorage(StorageVolume storage) {
        MtpStorage mtpStorage = this.mStorageMap.get(storage.getPath());
        if (mtpStorage == null) {
            return;
        }
        if (this.mServer != null) {
            this.mServer.removeStorage(mtpStorage);
        }
        this.mManager.removeMtpStorage(mtpStorage);
        this.mStorageMap.remove(storage.getPath());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.String] */
    private void initDeviceProperties(Context context) {
        SQLiteDatabase db;
        boolean moveToNext;
        this.mDeviceProperties = context.getSharedPreferences("device-properties", 0);
        File databaseFile = context.getDatabasePath("device-properties");
        if (!databaseFile.exists()) {
            return;
        }
        SQLiteDatabase db2 = null;
        db2 = null;
        db2 = null;
        Cursor c = null;
        try {
            try {
                db = context.openOrCreateDatabase("device-properties", 0, null);
                if (db != null) {
                    try {
                        c = db.query("properties", new String[]{"_id", "code", "value"}, null, null, null, null, null);
                        db2 = "code";
                        if (c != null) {
                            SharedPreferences.Editor e = this.mDeviceProperties.edit();
                            while (true) {
                                moveToNext = c.moveToNext();
                                if (!moveToNext) {
                                    break;
                                }
                                String name = c.getString(1);
                                String value = c.getString(2);
                                e.putString(name, value);
                            }
                            e.commit();
                            db2 = moveToNext;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        db2 = db;
                        Log.m69e(TAG, "failed to migrate device properties", e);
                        if (c != null) {
                            c.close();
                        }
                        if (db2 != null) {
                            db2.close();
                        }
                        context.deleteDatabase("device-properties");
                    } catch (Throwable th) {
                        th = th;
                        if (c != null) {
                            c.close();
                        }
                        if (db != null) {
                            db.close();
                        }
                        throw th;
                    }
                }
                if (c != null) {
                    c.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e3) {
                e = e3;
            }
            context.deleteDatabase("device-properties");
        } catch (Throwable th2) {
            th = th2;
            db = db2;
        }
    }

    @VisibleForNative
    private int beginSendObject(String path, int format, int parent, int storageId) {
        MtpStorageManager.MtpObject parentObj = parent == 0 ? this.mManager.getStorageRoot(storageId) : this.mManager.getObject(parent);
        if (parentObj == null) {
            return -1;
        }
        Path objPath = Paths.get(path, new String[0]);
        return this.mManager.beginSendObject(parentObj, objPath.getFileName().toString(), format);
    }

    @VisibleForNative
    private void endSendObject(int handle, boolean succeeded) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || !this.mManager.endSendObject(obj, succeeded)) {
            Log.m70e(TAG, "Failed to successfully end send object");
        } else if (succeeded) {
            MediaStore.scanFile(this.mContext, obj.getPath().toFile());
        }
    }

    @VisibleForNative
    private void rescanFile(String path, int handle, int format) {
        MediaStore.scanFile(this.mContext, new File(path));
    }

    @VisibleForNative
    private int[] getObjectList(int storageID, int format, int parent) {
        List<MtpStorageManager.MtpObject> objs = this.mManager.getObjects(parent, format, storageID);
        if (objs == null) {
            return null;
        }
        int[] ret = new int[objs.size()];
        for (int i = 0; i < objs.size(); i++) {
            ret[i] = objs.get(i).getId();
        }
        return ret;
    }

    @VisibleForNative
    private int getNumObjects(int storageID, int format, int parent) {
        List<MtpStorageManager.MtpObject> objs = this.mManager.getObjects(parent, format, storageID);
        if (objs == null) {
            return -1;
        }
        return objs.size();
    }

    @VisibleForNative
    private MtpPropertyList getObjectPropertyList(int handle, int format, int property, int groupCode, int depth) {
        MtpPropertyGroup propertyGroup;
        int handle2 = handle;
        int format2 = format;
        if (property == 0) {
            if (groupCode == 0) {
                return new MtpPropertyList(8198);
            }
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_GROUP_UNSUPPORTED);
        }
        int i = -1;
        int depth2 = depth;
        if (depth2 == -1 && (handle2 == 0 || handle2 == -1)) {
            handle2 = -1;
            depth2 = 0;
        }
        if (depth2 != 0 && depth2 != 1) {
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_DEPTH_UNSUPPORTED);
        }
        List<MtpStorageManager.MtpObject> objs = null;
        MtpStorageManager.MtpObject thisObj = null;
        if (handle2 == -1) {
            objs = this.mManager.getObjects(0, format2, -1);
            if (objs == null) {
                return new MtpPropertyList(8201);
            }
        } else if (handle2 != 0) {
            MtpStorageManager.MtpObject obj = this.mManager.getObject(handle2);
            if (obj == null) {
                return new MtpPropertyList(8201);
            }
            if (obj.getFormat() == format2 || format2 == 0) {
                thisObj = obj;
            }
        }
        if (handle2 == 0 || depth2 == 1) {
            if (handle2 == 0) {
                handle2 = -1;
            }
            objs = this.mManager.getObjects(handle2, format2, -1);
            if (objs == null) {
                return new MtpPropertyList(8201);
            }
        }
        if (objs == null) {
            objs = new ArrayList<>();
        }
        if (thisObj != null) {
            objs.add(thisObj);
        }
        MtpPropertyList ret = new MtpPropertyList(8193);
        for (MtpStorageManager.MtpObject obj2 : objs) {
            if (property == i) {
                if (format2 == 0 && handle2 != 0 && handle2 != i) {
                    format2 = obj2.getFormat();
                }
                propertyGroup = this.mPropertyGroupsByFormat.get(format2);
                if (propertyGroup == null) {
                    propertyGroup = new MtpPropertyGroup(getSupportedObjectProperties(format2));
                    this.mPropertyGroupsByFormat.put(format2, propertyGroup);
                }
            } else {
                propertyGroup = this.mPropertyGroupsByProperty.get(property);
                if (propertyGroup == null) {
                    int[] propertyList = {property};
                    propertyGroup = new MtpPropertyGroup(propertyList);
                    this.mPropertyGroupsByProperty.put(property, propertyGroup);
                }
            }
            int err = propertyGroup.getPropertyList(this.mMediaProvider, obj2.getVolumeName(), obj2, ret);
            if (err == 8193) {
                i = -1;
            } else {
                return new MtpPropertyList(err);
            }
        }
        return ret;
    }

    private int renameFile(int handle, String newName) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        Path oldPath = obj.getPath();
        if (this.mManager.beginRenameObject(obj, newName)) {
            Path newPath = obj.getPath();
            boolean success = oldPath.toFile().renameTo(newPath.toFile());
            try {
                Os.access(oldPath.toString(), OsConstants.F_OK);
                Os.access(newPath.toString(), OsConstants.F_OK);
            } catch (ErrnoException e) {
            }
            if (!this.mManager.endRenameObject(obj, oldPath.getFileName().toString(), success)) {
                Log.m70e(TAG, "Failed to end rename object");
            }
            if (success) {
                ContentValues values = new ContentValues();
                values.put("_data", newPath.toString());
                String[] whereArgs = {oldPath.toString()};
                try {
                    Uri objectsUri = MediaStore.Files.getMtpObjectsUri(obj.getVolumeName());
                    this.mMediaProvider.update(objectsUri, values, PATH_WHERE, whereArgs);
                } catch (RemoteException e2) {
                    Log.m69e(TAG, "RemoteException in mMediaProvider.update", e2);
                }
                if (obj.isDir()) {
                    if (oldPath.getFileName().startsWith(".") && !newPath.startsWith(".")) {
                        MediaStore.scanFile(this.mContext, newPath.toFile());
                        return 8193;
                    }
                    return 8193;
                } else if (oldPath.getFileName().toString().toLowerCase(Locale.US).equals(".nomedia") && !newPath.getFileName().toString().toLowerCase(Locale.US).equals(".nomedia")) {
                    MediaStore.scanFile(this.mContext, newPath.getParent().toFile());
                    return 8193;
                } else {
                    return 8193;
                }
            }
            return 8194;
        }
        return 8194;
    }

    @VisibleForNative
    private int beginMoveObject(int handle, int newParent, int newStorage) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        MtpStorageManager.MtpObject parent = newParent == 0 ? this.mManager.getStorageRoot(newStorage) : this.mManager.getObject(newParent);
        if (obj == null || parent == null) {
            return 8201;
        }
        boolean allowed = this.mManager.beginMoveObject(obj, parent);
        return allowed ? 8193 : 8194;
    }

    @VisibleForNative
    private void endMoveObject(int oldParent, int newParent, int oldStorage, int newStorage, int objId, boolean success) {
        MtpStorageManager.MtpObject object;
        MtpStorageManager.MtpObject object2;
        if (oldParent != 0) {
            object = this.mManager.getObject(oldParent);
        } else {
            object = this.mManager.getStorageRoot(oldStorage);
        }
        MtpStorageManager.MtpObject oldParentObj = object;
        if (newParent != 0) {
            object2 = this.mManager.getObject(newParent);
        } else {
            object2 = this.mManager.getStorageRoot(newStorage);
        }
        MtpStorageManager.MtpObject newParentObj = object2;
        String name = this.mManager.getObject(objId).getName();
        if (newParentObj == null || oldParentObj == null || !this.mManager.endMoveObject(oldParentObj, newParentObj, name, success)) {
            Log.m70e(TAG, "Failed to end move object");
            return;
        }
        MtpStorageManager.MtpObject obj = this.mManager.getObject(objId);
        if (!success || obj == null) {
            return;
        }
        ContentValues values = new ContentValues();
        Path path = newParentObj.getPath().resolve(name);
        Path oldPath = oldParentObj.getPath().resolve(name);
        values.put("_data", path.toString());
        if (!obj.getParent().isRoot()) {
            int parentId = findInMedia(newParentObj, path.getParent());
            if (parentId == -1) {
                deleteFromMedia(obj, oldPath, obj.isDir());
                return;
            }
            values.put("parent", Integer.valueOf(parentId));
        } else {
            values.put("parent", (Integer) 0);
        }
        String[] whereArgs = {oldPath.toString()};
        int parentId2 = -1;
        try {
            if (!oldParentObj.isRoot()) {
                try {
                    parentId2 = findInMedia(oldParentObj, oldPath.getParent());
                } catch (RemoteException e) {
                    e = e;
                    Log.m69e(TAG, "RemoteException in mMediaProvider.update", e);
                }
            }
        } catch (RemoteException e2) {
            e = e2;
        }
        try {
            if (!oldParentObj.isRoot() && parentId2 == -1) {
                MediaStore.scanFile(this.mContext, path.toFile());
            }
            Uri objectsUri = MediaStore.Files.getMtpObjectsUri(obj.getVolumeName());
            this.mMediaProvider.update(objectsUri, values, PATH_WHERE, whereArgs);
        } catch (RemoteException e3) {
            e = e3;
            Log.m69e(TAG, "RemoteException in mMediaProvider.update", e);
        }
    }

    @VisibleForNative
    private int beginCopyObject(int handle, int newParent, int newStorage) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        MtpStorageManager.MtpObject parent = newParent == 0 ? this.mManager.getStorageRoot(newStorage) : this.mManager.getObject(newParent);
        if (obj == null || parent == null) {
            return 8201;
        }
        return this.mManager.beginCopyObject(obj, parent);
    }

    @VisibleForNative
    private void endCopyObject(int handle, boolean success) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || !this.mManager.endCopyObject(obj, success)) {
            Log.m70e(TAG, "Failed to end copy object");
        } else if (!success) {
        } else {
            MediaStore.scanFile(this.mContext, obj.getPath().toFile());
        }
    }

    @VisibleForNative
    private int setObjectProperty(int handle, int property, long intValue, String stringValue) {
        if (property == 56327) {
            return renameFile(handle, stringValue);
        }
        return MtpConstants.RESPONSE_OBJECT_PROP_NOT_SUPPORTED;
    }

    @VisibleForNative
    private int getDeviceProperty(int property, long[] outIntValue, char[] outStringValue) {
        if (property == 20481) {
            outIntValue[0] = this.mBatteryLevel;
            outIntValue[1] = this.mBatteryScale;
            return 8193;
        } else if (property == 20483) {
            Display display = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int width = display.getMaximumSizeDimension();
            int height = display.getMaximumSizeDimension();
            String imageSize = Integer.toString(width) + "x" + Integer.toString(height);
            imageSize.getChars(0, imageSize.length(), outStringValue, 0);
            outStringValue[imageSize.length()] = 0;
            return 8193;
        } else if (property != 54279) {
            switch (property) {
                case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
                case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                    String value = this.mDeviceProperties.getString(Integer.toString(property), "");
                    int length = value.length();
                    if (length > 255) {
                        length = 255;
                    }
                    value.getChars(0, length, outStringValue, 0);
                    outStringValue[length] = 0;
                    return 8193;
                default:
                    return 8202;
            }
        } else {
            outIntValue[0] = this.mDeviceType;
            return 8193;
        }
    }

    @VisibleForNative
    private int setDeviceProperty(int property, long intValue, String stringValue) {
        switch (property) {
            case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
            case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                SharedPreferences.Editor e = this.mDeviceProperties.edit();
                e.putString(Integer.toString(property), stringValue);
                return e.commit() ? 8193 : 8194;
            default:
                return 8202;
        }
    }

    @VisibleForNative
    private boolean getObjectInfo(int handle, int[] outStorageFormatParent, char[] outName, long[] outCreatedModified) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return false;
        }
        outStorageFormatParent[0] = obj.getStorageId();
        outStorageFormatParent[1] = obj.getFormat();
        outStorageFormatParent[2] = obj.getParent().isRoot() ? 0 : obj.getParent().getId();
        int nameLen = Integer.min(obj.getName().length(), 255);
        obj.getName().getChars(0, nameLen, outName, 0);
        outName[nameLen] = 0;
        outCreatedModified[0] = obj.getModifiedTime();
        outCreatedModified[1] = obj.getModifiedTime();
        return true;
    }

    @VisibleForNative
    private int getObjectFilePath(int handle, char[] outFilePath, long[] outFileLengthFormat) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        String path = obj.getPath().toString();
        int pathLen = Integer.min(path.length(), 4096);
        path.getChars(0, pathLen, outFilePath, 0);
        outFilePath[pathLen] = 0;
        outFileLengthFormat[0] = obj.getSize();
        outFileLengthFormat[1] = obj.getFormat();
        return 8193;
    }

    private int getObjectFormat(int handle) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return -1;
        }
        return obj.getFormat();
    }

    @VisibleForNative
    private int beginDeleteObject(int handle) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        if (!this.mManager.beginRemoveObject(obj)) {
            return 8194;
        }
        return 8193;
    }

    @VisibleForNative
    private void endDeleteObject(int handle, boolean success) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return;
        }
        if (!this.mManager.endRemoveObject(obj, success)) {
            Log.m70e(TAG, "Failed to end remove object");
        }
        if (success) {
            deleteFromMedia(obj, obj.getPath(), obj.isDir());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0054, code lost:
        if (r9 == null) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0057, code lost:
        return r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int findInMedia(MtpStorageManager.MtpObject obj, Path path) {
        Uri objectsUri = MediaStore.Files.getMtpObjectsUri(obj.getVolumeName());
        int ret = -1;
        Cursor c = null;
        try {
            try {
                c = this.mMediaProvider.query(objectsUri, ID_PROJECTION, PATH_WHERE, new String[]{path.toString()}, null, null);
                if (c != null && c.moveToNext()) {
                    ret = c.getInt(0);
                }
            } catch (RemoteException e) {
                Log.m70e(TAG, "Error finding " + path + " in MediaProvider");
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private void deleteFromMedia(MtpStorageManager.MtpObject obj, Path path, boolean isDir) {
        Uri objectsUri = MediaStore.Files.getMtpObjectsUri(obj.getVolumeName());
        if (isDir) {
            try {
                ContentProviderClient contentProviderClient = this.mMediaProvider;
                contentProviderClient.delete(objectsUri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{path + "/%", Integer.toString(path.toString().length() + 1), path.toString() + "/"});
            } catch (Exception e) {
                String str = TAG;
                Log.m72d(str, "Failed to delete " + path + " from MediaProvider");
                return;
            }
        }
        String[] whereArgs = {path.toString()};
        if (this.mMediaProvider.delete(objectsUri, PATH_WHERE, whereArgs) > 0) {
            if (!isDir && path.toString().toLowerCase(Locale.US).endsWith(".nomedia")) {
                MediaStore.scanFile(this.mContext, path.getParent().toFile());
                return;
            }
            return;
        }
        String str2 = TAG;
        Log.m68i(str2, "Mediaprovider didn't delete " + path);
    }

    @VisibleForNative
    private int[] getObjectReferences(int handle) {
        int handle2;
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || (handle2 = findInMedia(obj, obj.getPath())) == -1) {
            return null;
        }
        Uri uri = MediaStore.Files.getMtpReferencesUri(obj.getVolumeName(), handle2);
        Cursor c = null;
        try {
            try {
                c = this.mMediaProvider.query(uri, PATH_PROJECTION, null, null, null, null);
                if (c == null) {
                    if (c != null) {
                        c.close();
                    }
                    return null;
                }
                ArrayList<Integer> result = new ArrayList<>();
                while (c.moveToNext()) {
                    String refPath = c.getString(0);
                    MtpStorageManager.MtpObject refObj = this.mManager.getByPath(refPath);
                    if (refObj != null) {
                        result.add(Integer.valueOf(refObj.getId()));
                    }
                }
                int[] array = result.stream().mapToInt($$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
                if (c != null) {
                    c.close();
                }
                return array;
            } catch (RemoteException e) {
                Log.m69e(TAG, "RemoteException in getObjectList", e);
                if (c != null) {
                    c.close();
                }
                return null;
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    @VisibleForNative
    private int setObjectReferences(int handle, int[] references) {
        int refHandle;
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj != null) {
            int handle2 = findInMedia(obj, obj.getPath());
            int i = -1;
            if (handle2 == -1) {
                return 8194;
            }
            Uri uri = MediaStore.Files.getMtpReferencesUri(obj.getVolumeName(), handle2);
            ArrayList<ContentValues> valuesList = new ArrayList<>();
            int length = references.length;
            int i2 = 0;
            while (i2 < length) {
                int id = references[i2];
                MtpStorageManager.MtpObject refObj = this.mManager.getObject(id);
                if (refObj != null && (refHandle = findInMedia(refObj, refObj.getPath())) != i) {
                    ContentValues values = new ContentValues();
                    values.put("_id", Integer.valueOf(refHandle));
                    valuesList.add(values);
                }
                i2++;
                i = -1;
            }
            try {
            } catch (RemoteException e) {
                Log.m69e(TAG, "RemoteException in setObjectReferences", e);
            }
            if (this.mMediaProvider.bulkInsert(uri, (ContentValues[]) valuesList.toArray(new ContentValues[0])) <= 0) {
                return 8194;
            }
            return 8193;
        }
        return 8201;
    }
}
