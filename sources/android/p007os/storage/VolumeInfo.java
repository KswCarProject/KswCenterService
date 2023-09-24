package android.p007os.storage;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.p007os.Environment;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.UserHandle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.C3132R;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.io.CharArrayWriter;
import java.io.File;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/* renamed from: android.os.storage.VolumeInfo */
/* loaded from: classes3.dex */
public class VolumeInfo implements Parcelable {
    public static final String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    @UnsupportedAppUsage
    public static final Parcelable.Creator<VolumeInfo> CREATOR;
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
    @UnsupportedAppUsage
    public final DiskInfo disk;
    @UnsupportedAppUsage
    public String fsLabel;
    public String fsType;
    @UnsupportedAppUsage
    public String fsUuid;

    /* renamed from: id */
    public final String f158id;
    @UnsupportedAppUsage
    public String internalPath;
    public int mountFlags;
    public int mountUserId;
    public final String partGuid;
    @UnsupportedAppUsage
    public String path;
    @UnsupportedAppUsage
    public int state;
    @UnsupportedAppUsage
    public final int type;
    private static SparseArray<String> sStateToEnvironment = new SparseArray<>();
    private static ArrayMap<String, String> sEnvironmentToBroadcast = new ArrayMap<>();
    private static SparseIntArray sStateToDescrip = new SparseIntArray();
    private static final Comparator<VolumeInfo> sDescriptionComparator = new Comparator<VolumeInfo>() { // from class: android.os.storage.VolumeInfo.1
        @Override // java.util.Comparator
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
        sStateToDescrip.put(0, C3132R.string.ext_media_status_unmounted);
        sStateToDescrip.put(1, C3132R.string.ext_media_status_checking);
        sStateToDescrip.put(2, C3132R.string.ext_media_status_mounted);
        sStateToDescrip.put(3, C3132R.string.ext_media_status_mounted_ro);
        sStateToDescrip.put(4, C3132R.string.ext_media_status_formatting);
        sStateToDescrip.put(5, C3132R.string.ext_media_status_ejecting);
        sStateToDescrip.put(6, C3132R.string.ext_media_status_unmountable);
        sStateToDescrip.put(7, C3132R.string.ext_media_status_removed);
        sStateToDescrip.put(8, C3132R.string.ext_media_status_bad_removal);
        CREATOR = new Parcelable.Creator<VolumeInfo>() { // from class: android.os.storage.VolumeInfo.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public VolumeInfo createFromParcel(Parcel in) {
                return new VolumeInfo(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public VolumeInfo[] newArray(int size) {
                return new VolumeInfo[size];
            }
        };
    }

    public VolumeInfo(String id, int type, DiskInfo disk, String partGuid) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.f158id = (String) Preconditions.checkNotNull(id);
        this.type = type;
        this.disk = disk;
        this.partGuid = partGuid;
    }

    @UnsupportedAppUsage
    public VolumeInfo(Parcel parcel) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.f158id = parcel.readString();
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
    public static String getEnvironmentForState(int state) {
        String envState = sStateToEnvironment.get(state);
        if (envState != null) {
            return envState;
        }
        return "unknown";
    }

    public static String getBroadcastForEnvironment(String envState) {
        return sEnvironmentToBroadcast.get(envState);
    }

    public static String getBroadcastForState(int state) {
        return getBroadcastForEnvironment(getEnvironmentForState(state));
    }

    public static Comparator<VolumeInfo> getDescriptionComparator() {
        return sDescriptionComparator;
    }

    @UnsupportedAppUsage
    public String getId() {
        return this.f158id;
    }

    @UnsupportedAppUsage
    public DiskInfo getDisk() {
        return this.disk;
    }

    @UnsupportedAppUsage
    public String getDiskId() {
        if (this.disk != null) {
            return this.disk.f157id;
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
        if (ID_PRIVATE_INTERNAL.equals(this.f158id) || ID_EMULATED_INTERNAL.equals(this.f158id)) {
            return Resources.getSystem().getString(C3132R.string.storage_internal);
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
        String environmentForState;
        String derivedFsUuid;
        long maxFileSize;
        boolean emulated;
        boolean removable;
        StorageManager storage = (StorageManager) context.getSystemService(StorageManager.class);
        if (!reportUnmounted) {
            environmentForState = getEnvironmentForState(this.state);
        } else {
            environmentForState = Environment.MEDIA_UNMOUNTED;
        }
        String envState = environmentForState;
        File userPath = getPathForUser(userId);
        if (userPath == null) {
            userPath = new File("/dev/null");
        }
        File internalPath = getInternalPathForUser(userId);
        if (internalPath == null) {
            internalPath = new File("/dev/null");
        }
        File internalPath2 = internalPath;
        String description = null;
        String derivedFsUuid2 = this.fsUuid;
        long maxFileSize2 = 0;
        if (this.type == 2) {
            VolumeInfo privateVol = storage.findPrivateForEmulated(this);
            if (privateVol != null) {
                description = storage.getBestVolumeDescription(privateVol);
                derivedFsUuid2 = privateVol.fsUuid;
            }
            if (ID_EMULATED_INTERNAL.equals(this.f158id)) {
                removable = false;
            } else {
                removable = true;
            }
            derivedFsUuid = derivedFsUuid2;
            maxFileSize = 0;
            emulated = true;
        } else if (this.type != 0 && this.type != 5) {
            throw new IllegalStateException("Unexpected volume type " + this.type);
        } else {
            description = storage.getBestVolumeDescription(this);
            if ("vfat".equals(this.fsType)) {
                maxFileSize2 = 4294967295L;
            }
            derivedFsUuid = derivedFsUuid2;
            maxFileSize = maxFileSize2;
            emulated = false;
            removable = true;
        }
        if (description == null) {
            description = context.getString(17039374);
        }
        return new StorageVolume(this.f158id, userPath, internalPath2, description, isPrimary(), removable, emulated, false, maxFileSize, new UserHandle(userId), derivedFsUuid, envState);
    }

    @UnsupportedAppUsage
    public static int buildStableMtpStorageId(String fsUuid) {
        if (TextUtils.isEmpty(fsUuid)) {
            return 0;
        }
        int hash = 0;
        for (int i = 0; i < fsUuid.length(); i++) {
            hash = (hash * 31) + fsUuid.charAt(i);
        }
        int i2 = hash << 16;
        int hash2 = (i2 ^ hash) & (-65536);
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
        } else if (this.type == 2 && isPrimary()) {
            uri = DocumentsContract.buildRootUri("com.android.externalstorage.documents", "primary");
        } else {
            return null;
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
        pw.println("VolumeInfo{" + this.f158id + "}:");
        pw.increaseIndent();
        pw.printPair("type", DebugUtils.valueToString(getClass(), "TYPE_", this.type));
        pw.printPair("diskId", getDiskId());
        pw.printPair("partGuid", this.partGuid);
        pw.printPair("mountFlags", DebugUtils.flagsToString(getClass(), "MOUNT_FLAG_", this.mountFlags));
        pw.printPair("mountUserId", Integer.valueOf(this.mountUserId));
        pw.printPair("state", DebugUtils.valueToString(getClass(), "STATE_", this.state));
        pw.println();
        pw.printPair("fsType", this.fsType);
        pw.printPair("fsUuid", this.fsUuid);
        pw.printPair("fsLabel", this.fsLabel);
        pw.println();
        pw.printPair("path", this.path);
        pw.printPair("internalPath", this.internalPath);
        pw.decreaseIndent();
        pw.println();
    }

    /* renamed from: clone */
    public VolumeInfo m165clone() {
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
            return Objects.equals(this.f158id, ((VolumeInfo) o).f158id);
        }
        return false;
    }

    public int hashCode() {
        return this.f158id.hashCode();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.f158id);
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
