package android.os.storage;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.R;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.CharArrayWriter;
import java.io.File;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class VolumeInfo implements Parcelable {
    public static final String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    @UnsupportedAppUsage
    public static final Parcelable.Creator<VolumeInfo> CREATOR = new Parcelable.Creator<VolumeInfo>() {
        public VolumeInfo createFromParcel(Parcel in) {
            return new VolumeInfo(in);
        }

        public VolumeInfo[] newArray(int size) {
            return new VolumeInfo[size];
        }
    };
    private static final String DOCUMENT_AUTHORITY = "com.android.externalstorage.documents";
    private static final String DOCUMENT_ROOT_PRIMARY_EMULATED = "primary";
    public static final String EXTRA_VOLUME_ID = "android.os.storage.extra.VOLUME_ID";
    public static final String EXTRA_VOLUME_STATE = "android.os.storage.extra.VOLUME_STATE";
    public static final String ID_EMULATED_INTERNAL = "emulated";
    public static final String ID_PRIVATE_INTERNAL = "private";
    public static final int MOUNT_FLAG_PRIMARY = 1;
    public static final int MOUNT_FLAG_VISIBLE = 2;
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_UNMOUNTED = 0;
    public static final int TYPE_ASEC = 3;
    @UnsupportedAppUsage
    public static final int TYPE_EMULATED = 2;
    public static final int TYPE_OBB = 4;
    public static final int TYPE_PRIVATE = 1;
    @UnsupportedAppUsage
    public static final int TYPE_PUBLIC = 0;
    public static final int TYPE_STUB = 5;
    private static final Comparator<VolumeInfo> sDescriptionComparator = new Comparator<VolumeInfo>() {
        public int compare(VolumeInfo lhs, VolumeInfo rhs) {
            if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(lhs.getId())) {
                return -1;
            }
            if (lhs.getDescription() == null) {
                return 1;
            }
            if (rhs.getDescription() == null) {
                return -1;
            }
            return lhs.getDescription().compareTo(rhs.getDescription());
        }
    };
    private static ArrayMap<String, String> sEnvironmentToBroadcast = new ArrayMap<>();
    private static SparseIntArray sStateToDescrip = new SparseIntArray();
    private static SparseArray<String> sStateToEnvironment = new SparseArray<>();
    @UnsupportedAppUsage
    public final DiskInfo disk;
    @UnsupportedAppUsage
    public String fsLabel;
    public String fsType;
    @UnsupportedAppUsage
    public String fsUuid;
    public final String id;
    @UnsupportedAppUsage
    public String internalPath;
    public int mountFlags = 0;
    public int mountUserId = -10000;
    public final String partGuid;
    @UnsupportedAppUsage
    public String path;
    @UnsupportedAppUsage
    public int state = 0;
    @UnsupportedAppUsage
    public final int type;

    static {
        sStateToEnvironment.put(0, Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(1, Environment.MEDIA_CHECKING);
        sStateToEnvironment.put(2, Environment.MEDIA_MOUNTED);
        sStateToEnvironment.put(3, Environment.MEDIA_MOUNTED_READ_ONLY);
        sStateToEnvironment.put(4, Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(5, Environment.MEDIA_EJECTING);
        sStateToEnvironment.put(6, Environment.MEDIA_UNMOUNTABLE);
        sStateToEnvironment.put(7, Environment.MEDIA_REMOVED);
        sStateToEnvironment.put(8, Environment.MEDIA_BAD_REMOVAL);
        sEnvironmentToBroadcast.put(Environment.MEDIA_UNMOUNTED, Intent.ACTION_MEDIA_UNMOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_CHECKING, Intent.ACTION_MEDIA_CHECKING);
        sEnvironmentToBroadcast.put(Environment.MEDIA_MOUNTED, Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_MOUNTED_READ_ONLY, Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_EJECTING, Intent.ACTION_MEDIA_EJECT);
        sEnvironmentToBroadcast.put(Environment.MEDIA_UNMOUNTABLE, Intent.ACTION_MEDIA_UNMOUNTABLE);
        sEnvironmentToBroadcast.put(Environment.MEDIA_REMOVED, Intent.ACTION_MEDIA_REMOVED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_BAD_REMOVAL, Intent.ACTION_MEDIA_BAD_REMOVAL);
        sStateToDescrip.put(0, R.string.ext_media_status_unmounted);
        sStateToDescrip.put(1, R.string.ext_media_status_checking);
        sStateToDescrip.put(2, R.string.ext_media_status_mounted);
        sStateToDescrip.put(3, R.string.ext_media_status_mounted_ro);
        sStateToDescrip.put(4, R.string.ext_media_status_formatting);
        sStateToDescrip.put(5, R.string.ext_media_status_ejecting);
        sStateToDescrip.put(6, R.string.ext_media_status_unmountable);
        sStateToDescrip.put(7, R.string.ext_media_status_removed);
        sStateToDescrip.put(8, R.string.ext_media_status_bad_removal);
    }

    public VolumeInfo(String id2, int type2, DiskInfo disk2, String partGuid2) {
        this.id = (String) Preconditions.checkNotNull(id2);
        this.type = type2;
        this.disk = disk2;
        this.partGuid = partGuid2;
    }

    @UnsupportedAppUsage
    public VolumeInfo(Parcel parcel) {
        this.id = parcel.readString();
        this.type = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.disk = DiskInfo.CREATOR.createFromParcel(parcel);
        } else {
            this.disk = null;
        }
        this.partGuid = parcel.readString();
        this.mountFlags = parcel.readInt();
        this.mountUserId = parcel.readInt();
        this.state = parcel.readInt();
        this.fsType = parcel.readString();
        this.fsUuid = parcel.readString();
        this.fsLabel = parcel.readString();
        this.path = parcel.readString();
        this.internalPath = parcel.readString();
    }

    @UnsupportedAppUsage
    public static String getEnvironmentForState(int state2) {
        String envState = sStateToEnvironment.get(state2);
        if (envState != null) {
            return envState;
        }
        return "unknown";
    }

    public static String getBroadcastForEnvironment(String envState) {
        return sEnvironmentToBroadcast.get(envState);
    }

    public static String getBroadcastForState(int state2) {
        return getBroadcastForEnvironment(getEnvironmentForState(state2));
    }

    public static Comparator<VolumeInfo> getDescriptionComparator() {
        return sDescriptionComparator;
    }

    @UnsupportedAppUsage
    public String getId() {
        return this.id;
    }

    @UnsupportedAppUsage
    public DiskInfo getDisk() {
        return this.disk;
    }

    @UnsupportedAppUsage
    public String getDiskId() {
        if (this.disk != null) {
            return this.disk.id;
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getType() {
        return this.type;
    }

    @UnsupportedAppUsage
    public int getState() {
        return this.state;
    }

    public int getStateDescription() {
        return sStateToDescrip.get(this.state, 0);
    }

    @UnsupportedAppUsage
    public String getFsUuid() {
        return this.fsUuid;
    }

    public String getNormalizedFsUuid() {
        if (this.fsUuid != null) {
            return this.fsUuid.toLowerCase(Locale.US);
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getMountUserId() {
        return this.mountUserId;
    }

    @UnsupportedAppUsage
    public String getDescription() {
        if (ID_PRIVATE_INTERNAL.equals(this.id) || ID_EMULATED_INTERNAL.equals(this.id)) {
            return Resources.getSystem().getString(R.string.storage_internal);
        }
        if (!TextUtils.isEmpty(this.fsLabel)) {
            return this.fsLabel;
        }
        return null;
    }

    @UnsupportedAppUsage
    public boolean isMountedReadable() {
        return this.state == 2 || this.state == 3;
    }

    @UnsupportedAppUsage
    public boolean isMountedWritable() {
        return this.state == 2;
    }

    @UnsupportedAppUsage
    public boolean isPrimary() {
        return (this.mountFlags & 1) != 0;
    }

    @UnsupportedAppUsage
    public boolean isPrimaryPhysical() {
        return isPrimary() && getType() == 0;
    }

    @UnsupportedAppUsage
    public boolean isVisible() {
        return (this.mountFlags & 2) != 0;
    }

    public boolean isVisibleForUser(int userId) {
        if ((this.type == 0 || this.type == 5) && this.mountUserId == userId) {
            return isVisible();
        }
        if (this.type == 2) {
            return isVisible();
        }
        return false;
    }

    public boolean isVisibleForRead(int userId) {
        return isVisibleForUser(userId);
    }

    @UnsupportedAppUsage
    public boolean isVisibleForWrite(int userId) {
        return isVisibleForUser(userId);
    }

    @UnsupportedAppUsage
    public File getPath() {
        if (this.path != null) {
            return new File(this.path);
        }
        return null;
    }

    @UnsupportedAppUsage
    public File getInternalPath() {
        if (this.internalPath != null) {
            return new File(this.internalPath);
        }
        return null;
    }

    @UnsupportedAppUsage
    public File getPathForUser(int userId) {
        if (this.path == null) {
            return null;
        }
        if (this.type == 0 || this.type == 5) {
            return new File(this.path);
        }
        if (this.type == 2) {
            return new File(this.path, Integer.toString(userId));
        }
        return null;
    }

    @UnsupportedAppUsage
    public File getInternalPathForUser(int userId) {
        if (this.path == null) {
            return null;
        }
        if (this.type == 0 || this.type == 5) {
            return new File(this.path.replace("/storage/", "/mnt/media_rw/"));
        }
        return getPathForUser(userId);
    }

    @UnsupportedAppUsage
    public StorageVolume buildStorageVolume(Context context, int userId, boolean reportUnmounted) {
        String derivedFsUuid;
        long maxFileSize;
        boolean emulated;
        boolean removable;
        Context context2 = context;
        int i = userId;
        StorageManager storage = (StorageManager) context2.getSystemService(StorageManager.class);
        String envState = reportUnmounted ? Environment.MEDIA_UNMOUNTED : getEnvironmentForState(this.state);
        File userPath = getPathForUser(i);
        if (userPath == null) {
            userPath = new File("/dev/null");
        }
        File internalPath2 = getInternalPathForUser(i);
        if (internalPath2 == null) {
            internalPath2 = new File("/dev/null");
        }
        File internalPath3 = internalPath2;
        String description = null;
        String derivedFsUuid2 = this.fsUuid;
        long maxFileSize2 = 0;
        if (this.type == 2) {
            VolumeInfo privateVol = storage.findPrivateForEmulated(this);
            if (privateVol != null) {
                description = storage.getBestVolumeDescription(privateVol);
                derivedFsUuid2 = privateVol.fsUuid;
            }
            if (ID_EMULATED_INTERNAL.equals(this.id)) {
                removable = false;
            } else {
                removable = true;
            }
            boolean z = removable;
            derivedFsUuid = derivedFsUuid2;
            maxFileSize = 0;
            emulated = true;
        } else if (this.type == 0 || this.type == 5) {
            description = storage.getBestVolumeDescription(this);
            if ("vfat".equals(this.fsType)) {
                maxFileSize2 = 4294967295L;
            }
            derivedFsUuid = derivedFsUuid2;
            maxFileSize = maxFileSize2;
            emulated = false;
            removable = true;
        } else {
            throw new IllegalStateException("Unexpected volume type " + this.type);
        }
        if (description == null) {
            description = context2.getString(17039374);
        }
        return new StorageVolume(this.id, userPath, internalPath3, description, isPrimary(), removable, emulated, false, maxFileSize, new UserHandle(i), derivedFsUuid, envState);
    }

    @UnsupportedAppUsage
    public static int buildStableMtpStorageId(String fsUuid2) {
        if (TextUtils.isEmpty(fsUuid2)) {
            return 0;
        }
        int hash = 0;
        for (int i = 0; i < fsUuid2.length(); i++) {
            hash = (hash * 31) + fsUuid2.charAt(i);
        }
        int hash2 = ((hash << 16) ^ hash) & -65536;
        if (hash2 == 0) {
            hash2 = 131072;
        }
        if (hash2 == 65536) {
            hash2 = 131072;
        }
        if (hash2 == -65536) {
            hash2 = -131072;
        }
        return hash2 | 1;
    }

    @UnsupportedAppUsage
    public Intent buildBrowseIntent() {
        return buildBrowseIntentForUser(UserHandle.myUserId());
    }

    public Intent buildBrowseIntentForUser(int userId) {
        Uri uri;
        if ((this.type == 0 || this.type == 5) && this.mountUserId == userId) {
            uri = DocumentsContract.buildRootUri("com.android.externalstorage.documents", this.fsUuid);
        } else if (this.type != 2 || !isPrimary()) {
            return null;
        } else {
            uri = DocumentsContract.buildRootUri("com.android.externalstorage.documents", "primary");
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(uri, DocumentsContract.Root.MIME_TYPE_ITEM);
        intent.putExtra(DocumentsContract.EXTRA_SHOW_ADVANCED, isPrimary());
        return intent;
    }

    public String toString() {
        CharArrayWriter writer = new CharArrayWriter();
        dump(new IndentingPrintWriter(writer, "    ", 80));
        return writer.toString();
    }

    public void dump(IndentingPrintWriter pw) {
        pw.println("VolumeInfo{" + this.id + "}:");
        pw.increaseIndent();
        pw.printPair("type", (Object) DebugUtils.valueToString(getClass(), "TYPE_", this.type));
        pw.printPair("diskId", (Object) getDiskId());
        pw.printPair("partGuid", (Object) this.partGuid);
        pw.printPair("mountFlags", (Object) DebugUtils.flagsToString(getClass(), "MOUNT_FLAG_", this.mountFlags));
        pw.printPair("mountUserId", (Object) Integer.valueOf(this.mountUserId));
        pw.printPair("state", (Object) DebugUtils.valueToString(getClass(), "STATE_", this.state));
        pw.println();
        pw.printPair("fsType", (Object) this.fsType);
        pw.printPair("fsUuid", (Object) this.fsUuid);
        pw.printPair("fsLabel", (Object) this.fsLabel);
        pw.println();
        pw.printPair("path", (Object) this.path);
        pw.printPair("internalPath", (Object) this.internalPath);
        pw.decreaseIndent();
        pw.println();
    }

    public VolumeInfo clone() {
        Parcel temp = Parcel.obtain();
        try {
            writeToParcel(temp, 0);
            temp.setDataPosition(0);
            return CREATOR.createFromParcel(temp);
        } finally {
            temp.recycle();
        }
    }

    public boolean equals(Object o) {
        if (o instanceof VolumeInfo) {
            return Objects.equals(this.id, ((VolumeInfo) o).id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.id);
        parcel.writeInt(this.type);
        if (this.disk != null) {
            parcel.writeInt(1);
            this.disk.writeToParcel(parcel, flags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.partGuid);
        parcel.writeInt(this.mountFlags);
        parcel.writeInt(this.mountUserId);
        parcel.writeInt(this.state);
        parcel.writeString(this.fsType);
        parcel.writeString(this.fsUuid);
        parcel.writeString(this.fsLabel);
        parcel.writeString(this.path);
        parcel.writeString(this.internalPath);
    }
}
