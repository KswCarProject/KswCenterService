package android.mtp;

import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.mtp.MtpStorageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.storage.StorageVolume;
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

public class MtpDatabase implements AutoCloseable {
    private static final int[] AUDIO_PROPERTIES = {MtpConstants.PROPERTY_ARTIST, MtpConstants.PROPERTY_ALBUM_NAME, MtpConstants.PROPERTY_ALBUM_ARTIST, MtpConstants.PROPERTY_TRACK, MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE, MtpConstants.PROPERTY_DURATION, MtpConstants.PROPERTY_COMPOSER, MtpConstants.PROPERTY_AUDIO_WAVE_CODEC, MtpConstants.PROPERTY_BITRATE_TYPE, MtpConstants.PROPERTY_AUDIO_BITRATE, MtpConstants.PROPERTY_NUMBER_OF_CHANNELS, MtpConstants.PROPERTY_SAMPLE_RATE};
    private static final int[] DEVICE_PROPERTIES = {MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER, MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME, MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE, MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL, MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE};
    private static final int[] FILE_PROPERTIES = {MtpConstants.PROPERTY_STORAGE_ID, MtpConstants.PROPERTY_OBJECT_FORMAT, MtpConstants.PROPERTY_PROTECTION_STATUS, MtpConstants.PROPERTY_OBJECT_SIZE, MtpConstants.PROPERTY_OBJECT_FILE_NAME, MtpConstants.PROPERTY_DATE_MODIFIED, MtpConstants.PROPERTY_PERSISTENT_UID, MtpConstants.PROPERTY_PARENT_OBJECT, MtpConstants.PROPERTY_NAME, MtpConstants.PROPERTY_DISPLAY_NAME, MtpConstants.PROPERTY_DATE_ADDED};
    private static final String[] ID_PROJECTION = {"_id"};
    private static final int[] IMAGE_PROPERTIES = {56392};
    private static final String NO_MEDIA = ".nomedia";
    private static final String[] PATH_PROJECTION = {"_data"};
    private static final String PATH_WHERE = "_data=?";
    private static final int[] PLAYBACK_FORMATS = {12288, 12289, 12292, 12293, 12296, 12297, 12299, MtpConstants.FORMAT_EXIF_JPEG, MtpConstants.FORMAT_TIFF_EP, MtpConstants.FORMAT_BMP, MtpConstants.FORMAT_GIF, MtpConstants.FORMAT_JFIF, MtpConstants.FORMAT_PNG, MtpConstants.FORMAT_TIFF, MtpConstants.FORMAT_WMA, MtpConstants.FORMAT_OGG, MtpConstants.FORMAT_AAC, MtpConstants.FORMAT_MP4_CONTAINER, MtpConstants.FORMAT_MP2, MtpConstants.FORMAT_3GP_CONTAINER, MtpConstants.FORMAT_ABSTRACT_AV_PLAYLIST, MtpConstants.FORMAT_WPL_PLAYLIST, MtpConstants.FORMAT_M3U_PLAYLIST, MtpConstants.FORMAT_PLS_PLAYLIST, MtpConstants.FORMAT_XML_DOCUMENT, MtpConstants.FORMAT_FLAC, MtpConstants.FORMAT_DNG, MtpConstants.FORMAT_HEIF};
    private static final String TAG = MtpDatabase.class.getSimpleName();
    private static final int[] VIDEO_PROPERTIES = {MtpConstants.PROPERTY_ARTIST, MtpConstants.PROPERTY_ALBUM_NAME, MtpConstants.PROPERTY_DURATION, MtpConstants.PROPERTY_DESCRIPTION};
    /* access modifiers changed from: private */
    public int mBatteryLevel;
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int unused = MtpDatabase.this.mBatteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int newLevel = intent.getIntExtra("level", 0);
                if (newLevel != MtpDatabase.this.mBatteryLevel) {
                    int unused2 = MtpDatabase.this.mBatteryLevel = newLevel;
                    if (MtpDatabase.this.mServer != null) {
                        MtpDatabase.this.mServer.sendDevicePropertyChanged(MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL);
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public int mBatteryScale;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final Context mContext;
    private SharedPreferences mDeviceProperties;
    private int mDeviceType;
    private MtpStorageManager mManager;
    private final ContentProviderClient mMediaProvider;
    @VisibleForNative
    private long mNativeContext;
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByFormat = new SparseArray<>();
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByProperty = new SparseArray<>();
    /* access modifiers changed from: private */
    public MtpServer mServer;
    private final HashMap<String, MtpStorage> mStorageMap = new HashMap<>();

    private final native void native_finalize();

    private final native void native_setup();

    static {
        System.loadLibrary("media_jni");
    }

    @VisibleForNative
    private int[] getSupportedObjectProperties(int format) {
        switch (format) {
            case 12296:
            case 12297:
            case MtpConstants.FORMAT_WMA:
            case MtpConstants.FORMAT_OGG:
            case MtpConstants.FORMAT_AAC:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(AUDIO_PROPERTIES)).toArray();
            case 12299:
            case MtpConstants.FORMAT_WMV:
            case MtpConstants.FORMAT_3GP_CONTAINER:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(VIDEO_PROPERTIES)).toArray();
            case MtpConstants.FORMAT_EXIF_JPEG:
            case MtpConstants.FORMAT_BMP:
            case MtpConstants.FORMAT_GIF:
            case MtpConstants.FORMAT_PNG:
            case MtpConstants.FORMAT_DNG:
            case MtpConstants.FORMAT_HEIF:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(IMAGE_PROPERTIES)).toArray();
            default:
                return FILE_PROPERTIES;
        }
    }

    public static Uri getObjectPropertiesUri(int format, String volumeName) {
        switch (format) {
            case 12296:
            case 12297:
            case MtpConstants.FORMAT_WMA:
            case MtpConstants.FORMAT_OGG:
            case MtpConstants.FORMAT_AAC:
                return MediaStore.Audio.Media.getContentUri(volumeName);
            case 12299:
            case MtpConstants.FORMAT_WMV:
            case MtpConstants.FORMAT_3GP_CONTAINER:
                return MediaStore.Video.Media.getContentUri(volumeName);
            case MtpConstants.FORMAT_EXIF_JPEG:
            case MtpConstants.FORMAT_BMP:
            case MtpConstants.FORMAT_GIF:
            case MtpConstants.FORMAT_PNG:
            case MtpConstants.FORMAT_DNG:
            case MtpConstants.FORMAT_HEIF:
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
        this.mManager = new MtpStorageManager(new MtpStorageManager.MtpNotifier() {
            public void sendObjectAdded(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectAdded(id);
                }
            }

            public void sendObjectRemoved(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectRemoved(id);
                }
            }

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

    public void addStorage(StorageVolume storage) {
        MtpStorage mtpStorage = this.mManager.addMtpStorage(storage);
        this.mStorageMap.put(storage.getPath(), mtpStorage);
        if (this.mServer != null) {
            this.mServer.addStorage(mtpStorage);
        }
    }

    public void removeStorage(StorageVolume storage) {
        MtpStorage mtpStorage = this.mStorageMap.get(storage.getPath());
        if (mtpStorage != null) {
            if (this.mServer != null) {
                this.mServer.removeStorage(mtpStorage);
            }
            this.mManager.removeMtpStorage(mtpStorage);
            this.mStorageMap.remove(storage.getPath());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initDeviceProperties(android.content.Context r17) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            java.lang.String r3 = "device-properties"
            java.lang.String r0 = "device-properties"
            r4 = 0
            android.content.SharedPreferences r0 = r2.getSharedPreferences((java.lang.String) r0, (int) r4)
            r1.mDeviceProperties = r0
            java.lang.String r0 = "device-properties"
            java.io.File r5 = r2.getDatabasePath(r0)
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x0097
            r6 = 0
            r0 = 0
            r7 = r0
            java.lang.String r8 = "device-properties"
            android.database.sqlite.SQLiteDatabase r0 = r2.openOrCreateDatabase(r8, r4, r0)     // Catch:{ Exception -> 0x0073 }
            r4 = r0
            if (r4 == 0) goto L_0x0065
            java.lang.String r9 = "properties"
            java.lang.String r0 = "_id"
            java.lang.String r6 = "code"
            java.lang.String r8 = "value"
            java.lang.String[] r10 = new java.lang.String[]{r0, r6, r8}     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r8 = r4
            android.database.Cursor r0 = r8.query(r9, r10, r11, r12, r13, r14, r15)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r7 = r0
            if (r7 == 0) goto L_0x0065
            android.content.SharedPreferences r0 = r1.mDeviceProperties     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
        L_0x0048:
            boolean r6 = r7.moveToNext()     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            if (r6 == 0) goto L_0x005c
            r6 = 1
            java.lang.String r6 = r7.getString(r6)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r8 = 2
            java.lang.String r8 = r7.getString(r8)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            r0.putString(r6, r8)     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            goto L_0x0048
        L_0x005c:
            r0.commit()     // Catch:{ Exception -> 0x0062, all -> 0x0060 }
            goto L_0x0065
        L_0x0060:
            r0 = move-exception
            goto L_0x008c
        L_0x0062:
            r0 = move-exception
            r6 = r4
            goto L_0x0074
        L_0x0065:
            if (r7 == 0) goto L_0x006a
            r7.close()
        L_0x006a:
            if (r4 == 0) goto L_0x0086
            r4.close()
            goto L_0x0086
        L_0x0070:
            r0 = move-exception
            r4 = r6
            goto L_0x008c
        L_0x0073:
            r0 = move-exception
        L_0x0074:
            java.lang.String r4 = TAG     // Catch:{ all -> 0x0070 }
            java.lang.String r8 = "failed to migrate device properties"
            android.util.Log.e(r4, r8, r0)     // Catch:{ all -> 0x0070 }
            if (r7 == 0) goto L_0x0080
            r7.close()
        L_0x0080:
            if (r6 == 0) goto L_0x0085
            r6.close()
        L_0x0085:
            r4 = r6
        L_0x0086:
            java.lang.String r0 = "device-properties"
            r2.deleteDatabase(r0)
            goto L_0x0097
        L_0x008c:
            if (r7 == 0) goto L_0x0091
            r7.close()
        L_0x0091:
            if (r4 == 0) goto L_0x0096
            r4.close()
        L_0x0096:
            throw r0
        L_0x0097:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.initDeviceProperties(android.content.Context):void");
    }

    @VisibleForNative
    private int beginSendObject(String path, int format, int parent, int storageId) {
        MtpStorageManager.MtpObject parentObj = parent == 0 ? this.mManager.getStorageRoot(storageId) : this.mManager.getObject(parent);
        if (parentObj == null) {
            return -1;
        }
        return this.mManager.beginSendObject(parentObj, Paths.get(path, new String[0]).getFileName().toString(), format);
    }

    @VisibleForNative
    private void endSendObject(int handle, boolean succeeded) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || !this.mManager.endSendObject(obj, succeeded)) {
            Log.e(TAG, "Failed to successfully end send object");
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
        int i = property;
        if (i != 0) {
            int i2 = -1;
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
                if (i == i2) {
                    if (!(format2 != 0 || handle2 == 0 || handle2 == i2)) {
                        format2 = obj2.getFormat();
                    }
                    propertyGroup = this.mPropertyGroupsByFormat.get(format2);
                    if (propertyGroup == null) {
                        propertyGroup = new MtpPropertyGroup(getSupportedObjectProperties(format2));
                        this.mPropertyGroupsByFormat.put(format2, propertyGroup);
                    }
                } else {
                    propertyGroup = this.mPropertyGroupsByProperty.get(i);
                    if (propertyGroup == null) {
                        propertyGroup = new MtpPropertyGroup(new int[]{i});
                        this.mPropertyGroupsByProperty.put(i, propertyGroup);
                    }
                }
                int err = propertyGroup.getPropertyList(this.mMediaProvider, obj2.getVolumeName(), obj2, ret);
                if (err != 8193) {
                    return new MtpPropertyList(err);
                }
                i2 = -1;
            }
            return ret;
        } else if (groupCode == 0) {
            return new MtpPropertyList(8198);
        } else {
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_GROUP_UNSUPPORTED);
        }
    }

    private int renameFile(int handle, String newName) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        Path oldPath = obj.getPath();
        if (!this.mManager.beginRenameObject(obj, newName)) {
            return 8194;
        }
        Path newPath = obj.getPath();
        boolean success = oldPath.toFile().renameTo(newPath.toFile());
        try {
            Os.access(oldPath.toString(), OsConstants.F_OK);
            Os.access(newPath.toString(), OsConstants.F_OK);
        } catch (ErrnoException e) {
        }
        if (!this.mManager.endRenameObject(obj, oldPath.getFileName().toString(), success)) {
            Log.e(TAG, "Failed to end rename object");
        }
        if (!success) {
            return 8194;
        }
        ContentValues values = new ContentValues();
        values.put("_data", newPath.toString());
        String[] whereArgs = {oldPath.toString()};
        try {
            this.mMediaProvider.update(MediaStore.Files.getMtpObjectsUri(obj.getVolumeName()), values, PATH_WHERE, whereArgs);
        } catch (RemoteException e2) {
            Log.e(TAG, "RemoteException in mMediaProvider.update", e2);
        }
        if (obj.isDir()) {
            if (!oldPath.getFileName().startsWith(".") || newPath.startsWith(".")) {
                return 8193;
            }
            MediaStore.scanFile(this.mContext, newPath.toFile());
            return 8193;
        } else if (!oldPath.getFileName().toString().toLowerCase(Locale.US).equals(".nomedia") || newPath.getFileName().toString().toLowerCase(Locale.US).equals(".nomedia")) {
            return 8193;
        } else {
            MediaStore.scanFile(this.mContext, newPath.getParent().toFile());
            return 8193;
        }
    }

    @VisibleForNative
    private int beginMoveObject(int handle, int newParent, int newStorage) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        MtpStorageManager.MtpObject parent = newParent == 0 ? this.mManager.getStorageRoot(newStorage) : this.mManager.getObject(newParent);
        if (obj == null || parent == null) {
            return 8201;
        }
        return this.mManager.beginMoveObject(obj, parent) ? 8193 : 8194;
    }

    @VisibleForNative
    private void endMoveObject(int oldParent, int newParent, int oldStorage, int newStorage, int objId, boolean success) {
        MtpStorageManager.MtpObject mtpObject;
        MtpStorageManager.MtpObject mtpObject2;
        int i = oldParent;
        int i2 = newParent;
        int i3 = objId;
        boolean z = success;
        if (i == 0) {
            mtpObject = this.mManager.getStorageRoot(oldStorage);
        } else {
            int i4 = oldStorage;
            mtpObject = this.mManager.getObject(i);
        }
        MtpStorageManager.MtpObject oldParentObj = mtpObject;
        if (i2 == 0) {
            mtpObject2 = this.mManager.getStorageRoot(newStorage);
        } else {
            int i5 = newStorage;
            mtpObject2 = this.mManager.getObject(i2);
        }
        MtpStorageManager.MtpObject newParentObj = mtpObject2;
        String name = this.mManager.getObject(i3).getName();
        if (newParentObj == null || oldParentObj == null || !this.mManager.endMoveObject(oldParentObj, newParentObj, name, z)) {
            Log.e(TAG, "Failed to end move object");
            return;
        }
        MtpStorageManager.MtpObject obj = this.mManager.getObject(i3);
        if (z && obj != null) {
            ContentValues values = new ContentValues();
            Path path = newParentObj.getPath().resolve(name);
            Path oldPath = oldParentObj.getPath().resolve(name);
            values.put("_data", path.toString());
            if (obj.getParent().isRoot()) {
                values.put("parent", (Integer) 0);
            } else {
                int parentId = findInMedia(newParentObj, path.getParent());
                if (parentId != -1) {
                    values.put("parent", Integer.valueOf(parentId));
                } else {
                    deleteFromMedia(obj, oldPath, obj.isDir());
                    return;
                }
            }
            String[] whereArgs = {oldPath.toString()};
            int parentId2 = -1;
            try {
                if (!oldParentObj.isRoot()) {
                    try {
                        parentId2 = findInMedia(oldParentObj, oldPath.getParent());
                    } catch (RemoteException e) {
                        e = e;
                        Log.e(TAG, "RemoteException in mMediaProvider.update", e);
                    }
                }
                if (oldParentObj.isRoot()) {
                } else if (parentId2 != -1) {
                    int i6 = parentId2;
                } else {
                    try {
                        int i7 = parentId2;
                        MediaStore.scanFile(this.mContext, path.toFile());
                        return;
                    } catch (RemoteException e2) {
                        e = e2;
                        Log.e(TAG, "RemoteException in mMediaProvider.update", e);
                    }
                }
                this.mMediaProvider.update(MediaStore.Files.getMtpObjectsUri(obj.getVolumeName()), values, PATH_WHERE, whereArgs);
            } catch (RemoteException e3) {
                e = e3;
                Log.e(TAG, "RemoteException in mMediaProvider.update", e);
            }
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
            Log.e(TAG, "Failed to end copy object");
        } else if (success) {
            MediaStore.scanFile(this.mContext, obj.getPath().toFile());
        }
    }

    @VisibleForNative
    private int setObjectProperty(int handle, int property, long intValue, String stringValue) {
        if (property != 56327) {
            return MtpConstants.RESPONSE_OBJECT_PROP_NOT_SUPPORTED;
        }
        return renameFile(handle, stringValue);
    }

    @VisibleForNative
    private int getDeviceProperty(int property, long[] outIntValue, char[] outStringValue) {
        if (property == 20481) {
            outIntValue[0] = (long) this.mBatteryLevel;
            outIntValue[1] = (long) this.mBatteryScale;
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
                case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER:
                case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME:
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
            outIntValue[0] = (long) this.mDeviceType;
            return 8193;
        }
    }

    @VisibleForNative
    private int setDeviceProperty(int property, long intValue, String stringValue) {
        switch (property) {
            case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER:
            case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME:
                SharedPreferences.Editor e = this.mDeviceProperties.edit();
                e.putString(Integer.toString(property), stringValue);
                if (e.commit()) {
                    return 8193;
                }
                return 8194;
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
        outFileLengthFormat[1] = (long) obj.getFormat();
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
        if (obj != null) {
            if (!this.mManager.endRemoveObject(obj, success)) {
                Log.e(TAG, "Failed to end remove object");
            }
            if (success) {
                deleteFromMedia(obj, obj.getPath(), obj.isDir());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0054, code lost:
        if (r9 == null) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0057, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0030, code lost:
        if (r9 != null) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0032, code lost:
        r9.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int findInMedia(android.mtp.MtpStorageManager.MtpObject r12, java.nio.file.Path r13) {
        /*
            r11 = this;
            java.lang.String r0 = r12.getVolumeName()
            android.net.Uri r0 = android.provider.MediaStore.Files.getMtpObjectsUri(r0)
            r8 = -1
            r1 = 0
            r9 = r1
            android.content.ContentProviderClient r1 = r11.mMediaProvider     // Catch:{ RemoteException -> 0x0038 }
            java.lang.String[] r3 = ID_PROJECTION     // Catch:{ RemoteException -> 0x0038 }
            java.lang.String r4 = "_data=?"
            r2 = 1
            java.lang.String[] r5 = new java.lang.String[r2]     // Catch:{ RemoteException -> 0x0038 }
            java.lang.String r2 = r13.toString()     // Catch:{ RemoteException -> 0x0038 }
            r10 = 0
            r5[r10] = r2     // Catch:{ RemoteException -> 0x0038 }
            r6 = 0
            r7 = 0
            r2 = r0
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7)     // Catch:{ RemoteException -> 0x0038 }
            r9 = r1
            if (r9 == 0) goto L_0x0030
            boolean r1 = r9.moveToNext()     // Catch:{ RemoteException -> 0x0038 }
            if (r1 == 0) goto L_0x0030
            int r1 = r9.getInt(r10)     // Catch:{ RemoteException -> 0x0038 }
            r8 = r1
        L_0x0030:
            if (r9 == 0) goto L_0x0057
        L_0x0032:
            r9.close()
            goto L_0x0057
        L_0x0036:
            r1 = move-exception
            goto L_0x0058
        L_0x0038:
            r1 = move-exception
            java.lang.String r2 = TAG     // Catch:{ all -> 0x0036 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0036 }
            r3.<init>()     // Catch:{ all -> 0x0036 }
            java.lang.String r4 = "Error finding "
            r3.append(r4)     // Catch:{ all -> 0x0036 }
            r3.append(r13)     // Catch:{ all -> 0x0036 }
            java.lang.String r4 = " in MediaProvider"
            r3.append(r4)     // Catch:{ all -> 0x0036 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0036 }
            android.util.Log.e(r2, r3)     // Catch:{ all -> 0x0036 }
            if (r9 == 0) goto L_0x0057
            goto L_0x0032
        L_0x0057:
            return r8
        L_0x0058:
            if (r9 == 0) goto L_0x005d
            r9.close()
        L_0x005d:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.findInMedia(android.mtp.MtpStorageManager$MtpObject, java.nio.file.Path):int");
    }

    private void deleteFromMedia(MtpStorageManager.MtpObject obj, Path path, boolean isDir) {
        Uri objectsUri = MediaStore.Files.getMtpObjectsUri(obj.getVolumeName());
        if (isDir) {
            try {
                ContentProviderClient contentProviderClient = this.mMediaProvider;
                contentProviderClient.delete(objectsUri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{path + "/%", Integer.toString(path.toString().length() + 1), path.toString() + "/"});
            } catch (Exception e) {
                String str = TAG;
                Log.d(str, "Failed to delete " + path + " from MediaProvider");
                return;
            }
        }
        if (this.mMediaProvider.delete(objectsUri, PATH_WHERE, new String[]{path.toString()}) <= 0) {
            String str2 = TAG;
            Log.i(str2, "Mediaprovider didn't delete " + path);
        } else if (!isDir && path.toString().toLowerCase(Locale.US).endsWith(".nomedia")) {
            MediaStore.scanFile(this.mContext, path.getParent().toFile());
        }
    }

    @VisibleForNative
    private int[] getObjectReferences(int handle) {
        int handle2;
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || (handle2 = findInMedia(obj, obj.getPath())) == -1) {
            return null;
        }
        Cursor c = null;
        try {
            c = this.mMediaProvider.query(MediaStore.Files.getMtpReferencesUri(obj.getVolumeName(), (long) handle2), PATH_PROJECTION, (String) null, (String[]) null, (String) null, (CancellationSignal) null);
            if (c == null) {
                if (c != null) {
                    c.close();
                }
                return null;
            }
            ArrayList<Integer> result = new ArrayList<>();
            while (c.moveToNext()) {
                MtpStorageManager.MtpObject refObj = this.mManager.getByPath(c.getString(0));
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
            Log.e(TAG, "RemoteException in getObjectList", e);
            if (c != null) {
                c.close();
            }
            return null;
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
        int[] iArr = references;
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        int handle2 = findInMedia(obj, obj.getPath());
        int i = -1;
        if (handle2 == -1) {
            return 8194;
        }
        Uri uri = MediaStore.Files.getMtpReferencesUri(obj.getVolumeName(), (long) handle2);
        ArrayList<ContentValues> valuesList = new ArrayList<>();
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            MtpStorageManager.MtpObject refObj = this.mManager.getObject(iArr[i2]);
            if (!(refObj == null || (refHandle = findInMedia(refObj, refObj.getPath())) == i)) {
                ContentValues values = new ContentValues();
                values.put("_id", Integer.valueOf(refHandle));
                valuesList.add(values);
            }
            i2++;
            i = -1;
        }
        try {
            if (this.mMediaProvider.bulkInsert(uri, (ContentValues[]) valuesList.toArray(new ContentValues[0])) > 0) {
                return 8193;
            }
            return 8194;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in setObjectReferences", e);
        }
    }
}
