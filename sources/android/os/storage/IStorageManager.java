package android.os.storage;

import android.content.pm.IPackageMoveObserver;
import android.content.res.ObbInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IVoldTaskListener;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.storage.IObbActionListener;
import android.os.storage.IStorageEventListener;
import android.os.storage.IStorageShutdownObserver;
import com.android.internal.os.AppFuseMount;

public interface IStorageManager extends IInterface {
    void abortChanges(String str, boolean z) throws RemoteException;

    void abortIdleMaintenance() throws RemoteException;

    void addUserKeyAuth(int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void allocateBytes(String str, long j, int i, String str2) throws RemoteException;

    void benchmark(String str, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    int changeEncryptionPassword(int i, String str) throws RemoteException;

    void clearPassword() throws RemoteException;

    void clearUserKeyAuth(int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void commitChanges() throws RemoteException;

    void createUserKey(int i, int i2, boolean z) throws RemoteException;

    int decryptStorage(String str) throws RemoteException;

    void destroyUserKey(int i) throws RemoteException;

    void destroyUserStorage(String str, int i, int i2) throws RemoteException;

    int encryptStorage(int i, String str) throws RemoteException;

    void fixateNewestUserKeyAuth(int i) throws RemoteException;

    void forgetAllVolumes() throws RemoteException;

    void forgetVolume(String str) throws RemoteException;

    void format(String str) throws RemoteException;

    void fstrim(int i, IVoldTaskListener iVoldTaskListener) throws RemoteException;

    long getAllocatableBytes(String str, int i, String str2) throws RemoteException;

    long getCacheQuotaBytes(String str, int i) throws RemoteException;

    long getCacheSizeBytes(String str, int i) throws RemoteException;

    DiskInfo[] getDisks() throws RemoteException;

    int getEncryptionState() throws RemoteException;

    String getField(String str) throws RemoteException;

    String getMountedObbPath(String str) throws RemoteException;

    String getPassword() throws RemoteException;

    int getPasswordType() throws RemoteException;

    String getPrimaryStorageUuid() throws RemoteException;

    StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException;

    VolumeRecord[] getVolumeRecords(int i) throws RemoteException;

    VolumeInfo[] getVolumes(int i) throws RemoteException;

    boolean isConvertibleToFBE() throws RemoteException;

    boolean isObbMounted(String str) throws RemoteException;

    boolean isUserKeyUnlocked(int i) throws RemoteException;

    long lastMaintenance() throws RemoteException;

    void lockUserKey(int i) throws RemoteException;

    void mkdirs(String str, String str2) throws RemoteException;

    void mount(String str) throws RemoteException;

    void mountObb(String str, String str2, String str3, IObbActionListener iObbActionListener, int i, ObbInfo obbInfo) throws RemoteException;

    AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException;

    boolean needsCheckpoint() throws RemoteException;

    ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws RemoteException;

    void partitionMixed(String str, int i) throws RemoteException;

    void partitionPrivate(String str) throws RemoteException;

    void partitionPublic(String str) throws RemoteException;

    void prepareUserStorage(String str, int i, int i2, int i3) throws RemoteException;

    void registerListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    void runIdleMaintenance() throws RemoteException;

    void runMaintenance() throws RemoteException;

    void setDebugFlags(int i, int i2) throws RemoteException;

    void setField(String str, String str2) throws RemoteException;

    void setPrimaryStorageUuid(String str, IPackageMoveObserver iPackageMoveObserver) throws RemoteException;

    void setVolumeNickname(String str, String str2) throws RemoteException;

    void setVolumeUserFlags(String str, int i, int i2) throws RemoteException;

    void shutdown(IStorageShutdownObserver iStorageShutdownObserver) throws RemoteException;

    void startCheckpoint(int i) throws RemoteException;

    boolean supportsCheckpoint() throws RemoteException;

    void unlockUserKey(int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void unmount(String str) throws RemoteException;

    void unmountObb(String str, boolean z, IObbActionListener iObbActionListener, int i) throws RemoteException;

    void unregisterListener(IStorageEventListener iStorageEventListener) throws RemoteException;

    int verifyEncryptionPassword(String str) throws RemoteException;

    public static class Default implements IStorageManager {
        public void registerListener(IStorageEventListener listener) throws RemoteException {
        }

        public void unregisterListener(IStorageEventListener listener) throws RemoteException {
        }

        public void shutdown(IStorageShutdownObserver observer) throws RemoteException {
        }

        public void mountObb(String rawPath, String canonicalPath, String key, IObbActionListener token, int nonce, ObbInfo obbInfo) throws RemoteException {
        }

        public void unmountObb(String rawPath, boolean force, IObbActionListener token, int nonce) throws RemoteException {
        }

        public boolean isObbMounted(String rawPath) throws RemoteException {
            return false;
        }

        public String getMountedObbPath(String rawPath) throws RemoteException {
            return null;
        }

        public int decryptStorage(String password) throws RemoteException {
            return 0;
        }

        public int encryptStorage(int type, String password) throws RemoteException {
            return 0;
        }

        public int changeEncryptionPassword(int type, String password) throws RemoteException {
            return 0;
        }

        public StorageVolume[] getVolumeList(int uid, String packageName, int flags) throws RemoteException {
            return null;
        }

        public int getEncryptionState() throws RemoteException {
            return 0;
        }

        public int verifyEncryptionPassword(String password) throws RemoteException {
            return 0;
        }

        public void mkdirs(String callingPkg, String path) throws RemoteException {
        }

        public int getPasswordType() throws RemoteException {
            return 0;
        }

        public String getPassword() throws RemoteException {
            return null;
        }

        public void clearPassword() throws RemoteException {
        }

        public void setField(String field, String contents) throws RemoteException {
        }

        public String getField(String field) throws RemoteException {
            return null;
        }

        public long lastMaintenance() throws RemoteException {
            return 0;
        }

        public void runMaintenance() throws RemoteException {
        }

        public DiskInfo[] getDisks() throws RemoteException {
            return null;
        }

        public VolumeInfo[] getVolumes(int flags) throws RemoteException {
            return null;
        }

        public VolumeRecord[] getVolumeRecords(int flags) throws RemoteException {
            return null;
        }

        public void mount(String volId) throws RemoteException {
        }

        public void unmount(String volId) throws RemoteException {
        }

        public void format(String volId) throws RemoteException {
        }

        public void partitionPublic(String diskId) throws RemoteException {
        }

        public void partitionPrivate(String diskId) throws RemoteException {
        }

        public void partitionMixed(String diskId, int ratio) throws RemoteException {
        }

        public void setVolumeNickname(String fsUuid, String nickname) throws RemoteException {
        }

        public void setVolumeUserFlags(String fsUuid, int flags, int mask) throws RemoteException {
        }

        public void forgetVolume(String fsUuid) throws RemoteException {
        }

        public void forgetAllVolumes() throws RemoteException {
        }

        public String getPrimaryStorageUuid() throws RemoteException {
            return null;
        }

        public void setPrimaryStorageUuid(String volumeUuid, IPackageMoveObserver callback) throws RemoteException {
        }

        public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {
        }

        public void setDebugFlags(int flags, int mask) throws RemoteException {
        }

        public void createUserKey(int userId, int serialNumber, boolean ephemeral) throws RemoteException {
        }

        public void destroyUserKey(int userId) throws RemoteException {
        }

        public void unlockUserKey(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
        }

        public void lockUserKey(int userId) throws RemoteException {
        }

        public boolean isUserKeyUnlocked(int userId) throws RemoteException {
            return false;
        }

        public void prepareUserStorage(String volumeUuid, int userId, int serialNumber, int flags) throws RemoteException {
        }

        public void destroyUserStorage(String volumeUuid, int userId, int flags) throws RemoteException {
        }

        public boolean isConvertibleToFBE() throws RemoteException {
            return false;
        }

        public void addUserKeyAuth(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
        }

        public void fixateNewestUserKeyAuth(int userId) throws RemoteException {
        }

        public void fstrim(int flags, IVoldTaskListener listener) throws RemoteException {
        }

        public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor openProxyFileDescriptor(int mountPointId, int fileId, int mode) throws RemoteException {
            return null;
        }

        public long getCacheQuotaBytes(String volumeUuid, int uid) throws RemoteException {
            return 0;
        }

        public long getCacheSizeBytes(String volumeUuid, int uid) throws RemoteException {
            return 0;
        }

        public long getAllocatableBytes(String volumeUuid, int flags, String callingPackage) throws RemoteException {
            return 0;
        }

        public void allocateBytes(String volumeUuid, long bytes, int flags, String callingPackage) throws RemoteException {
        }

        public void runIdleMaintenance() throws RemoteException {
        }

        public void abortIdleMaintenance() throws RemoteException {
        }

        public void commitChanges() throws RemoteException {
        }

        public boolean supportsCheckpoint() throws RemoteException {
            return false;
        }

        public void startCheckpoint(int numTries) throws RemoteException {
        }

        public boolean needsCheckpoint() throws RemoteException {
            return false;
        }

        public void abortChanges(String message, boolean retry) throws RemoteException {
        }

        public void clearUserKeyAuth(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStorageManager {
        private static final String DESCRIPTOR = "android.os.storage.IStorageManager";
        static final int TRANSACTION_abortChanges = 88;
        static final int TRANSACTION_abortIdleMaintenance = 81;
        static final int TRANSACTION_addUserKeyAuth = 71;
        static final int TRANSACTION_allocateBytes = 79;
        static final int TRANSACTION_benchmark = 60;
        static final int TRANSACTION_changeEncryptionPassword = 29;
        static final int TRANSACTION_clearPassword = 38;
        static final int TRANSACTION_clearUserKeyAuth = 89;
        static final int TRANSACTION_commitChanges = 84;
        static final int TRANSACTION_createUserKey = 62;
        static final int TRANSACTION_decryptStorage = 27;
        static final int TRANSACTION_destroyUserKey = 63;
        static final int TRANSACTION_destroyUserStorage = 68;
        static final int TRANSACTION_encryptStorage = 28;
        static final int TRANSACTION_fixateNewestUserKeyAuth = 72;
        static final int TRANSACTION_forgetAllVolumes = 57;
        static final int TRANSACTION_forgetVolume = 56;
        static final int TRANSACTION_format = 50;
        static final int TRANSACTION_fstrim = 73;
        static final int TRANSACTION_getAllocatableBytes = 78;
        static final int TRANSACTION_getCacheQuotaBytes = 76;
        static final int TRANSACTION_getCacheSizeBytes = 77;
        static final int TRANSACTION_getDisks = 45;
        static final int TRANSACTION_getEncryptionState = 32;
        static final int TRANSACTION_getField = 40;
        static final int TRANSACTION_getMountedObbPath = 25;
        static final int TRANSACTION_getPassword = 37;
        static final int TRANSACTION_getPasswordType = 36;
        static final int TRANSACTION_getPrimaryStorageUuid = 58;
        static final int TRANSACTION_getVolumeList = 30;
        static final int TRANSACTION_getVolumeRecords = 47;
        static final int TRANSACTION_getVolumes = 46;
        static final int TRANSACTION_isConvertibleToFBE = 69;
        static final int TRANSACTION_isObbMounted = 24;
        static final int TRANSACTION_isUserKeyUnlocked = 66;
        static final int TRANSACTION_lastMaintenance = 42;
        static final int TRANSACTION_lockUserKey = 65;
        static final int TRANSACTION_mkdirs = 35;
        static final int TRANSACTION_mount = 48;
        static final int TRANSACTION_mountObb = 22;
        static final int TRANSACTION_mountProxyFileDescriptorBridge = 74;
        static final int TRANSACTION_needsCheckpoint = 87;
        static final int TRANSACTION_openProxyFileDescriptor = 75;
        static final int TRANSACTION_partitionMixed = 53;
        static final int TRANSACTION_partitionPrivate = 52;
        static final int TRANSACTION_partitionPublic = 51;
        static final int TRANSACTION_prepareUserStorage = 67;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_runIdleMaintenance = 80;
        static final int TRANSACTION_runMaintenance = 43;
        static final int TRANSACTION_setDebugFlags = 61;
        static final int TRANSACTION_setField = 39;
        static final int TRANSACTION_setPrimaryStorageUuid = 59;
        static final int TRANSACTION_setVolumeNickname = 54;
        static final int TRANSACTION_setVolumeUserFlags = 55;
        static final int TRANSACTION_shutdown = 20;
        static final int TRANSACTION_startCheckpoint = 86;
        static final int TRANSACTION_supportsCheckpoint = 85;
        static final int TRANSACTION_unlockUserKey = 64;
        static final int TRANSACTION_unmount = 49;
        static final int TRANSACTION_unmountObb = 23;
        static final int TRANSACTION_unregisterListener = 2;
        static final int TRANSACTION_verifyEncryptionPassword = 33;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStorageManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IStorageManager)) {
                return new Proxy(obj);
            }
            return (IStorageManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "registerListener";
                case 2:
                    return "unregisterListener";
                case 20:
                    return "shutdown";
                case 22:
                    return "mountObb";
                case 23:
                    return "unmountObb";
                case 24:
                    return "isObbMounted";
                case 25:
                    return "getMountedObbPath";
                case 27:
                    return "decryptStorage";
                case 28:
                    return "encryptStorage";
                case 29:
                    return "changeEncryptionPassword";
                case 30:
                    return "getVolumeList";
                case 32:
                    return "getEncryptionState";
                case 33:
                    return "verifyEncryptionPassword";
                case 35:
                    return "mkdirs";
                case 36:
                    return "getPasswordType";
                case 37:
                    return "getPassword";
                case 38:
                    return "clearPassword";
                case 39:
                    return "setField";
                case 40:
                    return "getField";
                case 42:
                    return "lastMaintenance";
                case 43:
                    return "runMaintenance";
                case 45:
                    return "getDisks";
                case 46:
                    return "getVolumes";
                case 47:
                    return "getVolumeRecords";
                case 48:
                    return "mount";
                case 49:
                    return "unmount";
                case 50:
                    return "format";
                case 51:
                    return "partitionPublic";
                case 52:
                    return "partitionPrivate";
                case 53:
                    return "partitionMixed";
                case 54:
                    return "setVolumeNickname";
                case 55:
                    return "setVolumeUserFlags";
                case 56:
                    return "forgetVolume";
                case 57:
                    return "forgetAllVolumes";
                case 58:
                    return "getPrimaryStorageUuid";
                case 59:
                    return "setPrimaryStorageUuid";
                case 60:
                    return "benchmark";
                case 61:
                    return "setDebugFlags";
                case 62:
                    return "createUserKey";
                case 63:
                    return "destroyUserKey";
                case 64:
                    return "unlockUserKey";
                case 65:
                    return "lockUserKey";
                case 66:
                    return "isUserKeyUnlocked";
                case 67:
                    return "prepareUserStorage";
                case 68:
                    return "destroyUserStorage";
                case 69:
                    return "isConvertibleToFBE";
                case 71:
                    return "addUserKeyAuth";
                case 72:
                    return "fixateNewestUserKeyAuth";
                case 73:
                    return "fstrim";
                case 74:
                    return "mountProxyFileDescriptorBridge";
                case 75:
                    return "openProxyFileDescriptor";
                case 76:
                    return "getCacheQuotaBytes";
                case 77:
                    return "getCacheSizeBytes";
                case 78:
                    return "getAllocatableBytes";
                case 79:
                    return "allocateBytes";
                case 80:
                    return "runIdleMaintenance";
                case 81:
                    return "abortIdleMaintenance";
                case 84:
                    return "commitChanges";
                case 85:
                    return "supportsCheckpoint";
                case 86:
                    return "startCheckpoint";
                case 87:
                    return "needsCheckpoint";
                case 88:
                    return "abortChanges";
                case 89:
                    return "clearUserKeyAuth";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ObbInfo _arg5;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            switch (code) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    registerListener(IStorageEventListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    unregisterListener(IStorageEventListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                default:
                    boolean _arg1 = false;
                    switch (code) {
                        case 22:
                            parcel.enforceInterface(DESCRIPTOR);
                            String _arg0 = data.readString();
                            String _arg12 = data.readString();
                            String _arg2 = data.readString();
                            IObbActionListener _arg3 = IObbActionListener.Stub.asInterface(data.readStrongBinder());
                            int _arg4 = data.readInt();
                            if (data.readInt() != 0) {
                                _arg5 = ObbInfo.CREATOR.createFromParcel(parcel);
                            } else {
                                _arg5 = null;
                            }
                            mountObb(_arg0, _arg12, _arg2, _arg3, _arg4, _arg5);
                            reply.writeNoException();
                            return true;
                        case 23:
                            parcel.enforceInterface(DESCRIPTOR);
                            String _arg02 = data.readString();
                            if (data.readInt() != 0) {
                                _arg1 = true;
                            }
                            unmountObb(_arg02, _arg1, IObbActionListener.Stub.asInterface(data.readStrongBinder()), data.readInt());
                            reply.writeNoException();
                            return true;
                        case 24:
                            parcel.enforceInterface(DESCRIPTOR);
                            boolean _result = isObbMounted(data.readString());
                            reply.writeNoException();
                            parcel2.writeInt(_result);
                            return true;
                        case 25:
                            parcel.enforceInterface(DESCRIPTOR);
                            String _result2 = getMountedObbPath(data.readString());
                            reply.writeNoException();
                            parcel2.writeString(_result2);
                            return true;
                        default:
                            switch (code) {
                                case 27:
                                    parcel.enforceInterface(DESCRIPTOR);
                                    int _result3 = decryptStorage(data.readString());
                                    reply.writeNoException();
                                    parcel2.writeInt(_result3);
                                    return true;
                                case 28:
                                    parcel.enforceInterface(DESCRIPTOR);
                                    int _result4 = encryptStorage(data.readInt(), data.readString());
                                    reply.writeNoException();
                                    parcel2.writeInt(_result4);
                                    return true;
                                case 29:
                                    parcel.enforceInterface(DESCRIPTOR);
                                    int _result5 = changeEncryptionPassword(data.readInt(), data.readString());
                                    reply.writeNoException();
                                    parcel2.writeInt(_result5);
                                    return true;
                                case 30:
                                    parcel.enforceInterface(DESCRIPTOR);
                                    StorageVolume[] _result6 = getVolumeList(data.readInt(), data.readString(), data.readInt());
                                    reply.writeNoException();
                                    parcel2.writeTypedArray(_result6, 1);
                                    return true;
                                default:
                                    switch (code) {
                                        case 32:
                                            parcel.enforceInterface(DESCRIPTOR);
                                            int _result7 = getEncryptionState();
                                            reply.writeNoException();
                                            parcel2.writeInt(_result7);
                                            return true;
                                        case 33:
                                            parcel.enforceInterface(DESCRIPTOR);
                                            int _result8 = verifyEncryptionPassword(data.readString());
                                            reply.writeNoException();
                                            parcel2.writeInt(_result8);
                                            return true;
                                        default:
                                            switch (code) {
                                                case 35:
                                                    parcel.enforceInterface(DESCRIPTOR);
                                                    mkdirs(data.readString(), data.readString());
                                                    reply.writeNoException();
                                                    return true;
                                                case 36:
                                                    parcel.enforceInterface(DESCRIPTOR);
                                                    int _result9 = getPasswordType();
                                                    reply.writeNoException();
                                                    parcel2.writeInt(_result9);
                                                    return true;
                                                case 37:
                                                    parcel.enforceInterface(DESCRIPTOR);
                                                    String _result10 = getPassword();
                                                    reply.writeNoException();
                                                    parcel2.writeString(_result10);
                                                    return true;
                                                case 38:
                                                    parcel.enforceInterface(DESCRIPTOR);
                                                    clearPassword();
                                                    return true;
                                                case 39:
                                                    parcel.enforceInterface(DESCRIPTOR);
                                                    setField(data.readString(), data.readString());
                                                    return true;
                                                case 40:
                                                    parcel.enforceInterface(DESCRIPTOR);
                                                    String _result11 = getField(data.readString());
                                                    reply.writeNoException();
                                                    parcel2.writeString(_result11);
                                                    return true;
                                                default:
                                                    switch (code) {
                                                        case 42:
                                                            parcel.enforceInterface(DESCRIPTOR);
                                                            long _result12 = lastMaintenance();
                                                            reply.writeNoException();
                                                            parcel2.writeLong(_result12);
                                                            return true;
                                                        case 43:
                                                            parcel.enforceInterface(DESCRIPTOR);
                                                            runMaintenance();
                                                            reply.writeNoException();
                                                            return true;
                                                        default:
                                                            switch (code) {
                                                                case 45:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    DiskInfo[] _result13 = getDisks();
                                                                    reply.writeNoException();
                                                                    parcel2.writeTypedArray(_result13, 1);
                                                                    return true;
                                                                case 46:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    VolumeInfo[] _result14 = getVolumes(data.readInt());
                                                                    reply.writeNoException();
                                                                    parcel2.writeTypedArray(_result14, 1);
                                                                    return true;
                                                                case 47:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    VolumeRecord[] _result15 = getVolumeRecords(data.readInt());
                                                                    reply.writeNoException();
                                                                    parcel2.writeTypedArray(_result15, 1);
                                                                    return true;
                                                                case 48:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    mount(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 49:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    unmount(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 50:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    format(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 51:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    partitionPublic(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 52:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    partitionPrivate(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 53:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    partitionMixed(data.readString(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 54:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    setVolumeNickname(data.readString(), data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 55:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    setVolumeUserFlags(data.readString(), data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 56:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    forgetVolume(data.readString());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 57:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    forgetAllVolumes();
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 58:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    String _result16 = getPrimaryStorageUuid();
                                                                    reply.writeNoException();
                                                                    parcel2.writeString(_result16);
                                                                    return true;
                                                                case 59:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    setPrimaryStorageUuid(data.readString(), IPackageMoveObserver.Stub.asInterface(data.readStrongBinder()));
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 60:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    benchmark(data.readString(), IVoldTaskListener.Stub.asInterface(data.readStrongBinder()));
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 61:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    setDebugFlags(data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 62:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    int _arg03 = data.readInt();
                                                                    int _arg13 = data.readInt();
                                                                    if (data.readInt() != 0) {
                                                                        _arg1 = true;
                                                                    }
                                                                    createUserKey(_arg03, _arg13, _arg1);
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 63:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    destroyUserKey(data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 64:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    unlockUserKey(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 65:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    lockUserKey(data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 66:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    boolean _result17 = isUserKeyUnlocked(data.readInt());
                                                                    reply.writeNoException();
                                                                    parcel2.writeInt(_result17);
                                                                    return true;
                                                                case 67:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    prepareUserStorage(data.readString(), data.readInt(), data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 68:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    destroyUserStorage(data.readString(), data.readInt(), data.readInt());
                                                                    reply.writeNoException();
                                                                    return true;
                                                                case 69:
                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                    boolean _result18 = isConvertibleToFBE();
                                                                    reply.writeNoException();
                                                                    parcel2.writeInt(_result18);
                                                                    return true;
                                                                default:
                                                                    switch (code) {
                                                                        case 71:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            addUserKeyAuth(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray());
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 72:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            fixateNewestUserKeyAuth(data.readInt());
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 73:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            fstrim(data.readInt(), IVoldTaskListener.Stub.asInterface(data.readStrongBinder()));
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 74:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            AppFuseMount _result19 = mountProxyFileDescriptorBridge();
                                                                            reply.writeNoException();
                                                                            if (_result19 != null) {
                                                                                parcel2.writeInt(1);
                                                                                _result19.writeToParcel(parcel2, 1);
                                                                            } else {
                                                                                parcel2.writeInt(0);
                                                                            }
                                                                            return true;
                                                                        case 75:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            ParcelFileDescriptor _result20 = openProxyFileDescriptor(data.readInt(), data.readInt(), data.readInt());
                                                                            reply.writeNoException();
                                                                            if (_result20 != null) {
                                                                                parcel2.writeInt(1);
                                                                                _result20.writeToParcel(parcel2, 1);
                                                                            } else {
                                                                                parcel2.writeInt(0);
                                                                            }
                                                                            return true;
                                                                        case 76:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            long _result21 = getCacheQuotaBytes(data.readString(), data.readInt());
                                                                            reply.writeNoException();
                                                                            parcel2.writeLong(_result21);
                                                                            return true;
                                                                        case 77:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            long _result22 = getCacheSizeBytes(data.readString(), data.readInt());
                                                                            reply.writeNoException();
                                                                            parcel2.writeLong(_result22);
                                                                            return true;
                                                                        case 78:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            long _result23 = getAllocatableBytes(data.readString(), data.readInt(), data.readString());
                                                                            reply.writeNoException();
                                                                            parcel2.writeLong(_result23);
                                                                            return true;
                                                                        case 79:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            allocateBytes(data.readString(), data.readLong(), data.readInt(), data.readString());
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 80:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            runIdleMaintenance();
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        case 81:
                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                            abortIdleMaintenance();
                                                                            reply.writeNoException();
                                                                            return true;
                                                                        default:
                                                                            switch (code) {
                                                                                case 84:
                                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                                    commitChanges();
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 85:
                                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                                    boolean _result24 = supportsCheckpoint();
                                                                                    reply.writeNoException();
                                                                                    parcel2.writeInt(_result24);
                                                                                    return true;
                                                                                case 86:
                                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                                    startCheckpoint(data.readInt());
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 87:
                                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                                    boolean _result25 = needsCheckpoint();
                                                                                    reply.writeNoException();
                                                                                    parcel2.writeInt(_result25);
                                                                                    return true;
                                                                                case 88:
                                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                                    String _arg04 = data.readString();
                                                                                    if (data.readInt() != 0) {
                                                                                        _arg1 = true;
                                                                                    }
                                                                                    abortChanges(_arg04, _arg1);
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                case 89:
                                                                                    parcel.enforceInterface(DESCRIPTOR);
                                                                                    clearUserKeyAuth(data.readInt(), data.readInt(), data.createByteArray(), data.createByteArray());
                                                                                    reply.writeNoException();
                                                                                    return true;
                                                                                default:
                                                                                    switch (code) {
                                                                                        case 20:
                                                                                            parcel.enforceInterface(DESCRIPTOR);
                                                                                            shutdown(IStorageShutdownObserver.Stub.asInterface(data.readStrongBinder()));
                                                                                            reply.writeNoException();
                                                                                            return true;
                                                                                        case IBinder.INTERFACE_TRANSACTION:
                                                                                            parcel2.writeString(DESCRIPTOR);
                                                                                            return true;
                                                                                        default:
                                                                                            return super.onTransact(code, data, reply, flags);
                                                                                    }
                                                                            }
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }

        private static class Proxy implements IStorageManager {
            public static IStorageManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void registerListener(IStorageEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterListener(IStorageEventListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdown(IStorageShutdownObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().shutdown(observer);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void mountObb(String rawPath, String canonicalPath, String key, IObbActionListener token, int nonce, ObbInfo obbInfo) throws RemoteException {
                ObbInfo obbInfo2 = obbInfo;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(rawPath);
                        try {
                            _data.writeString(canonicalPath);
                        } catch (Throwable th) {
                            th = th;
                            String str = key;
                            int i = nonce;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(key);
                            _data.writeStrongBinder(token != null ? token.asBinder() : null);
                        } catch (Throwable th2) {
                            th = th2;
                            int i2 = nonce;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(nonce);
                            if (obbInfo2 != null) {
                                _data.writeInt(1);
                                obbInfo2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().mountObb(rawPath, canonicalPath, key, token, nonce, obbInfo);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str2 = canonicalPath;
                        String str3 = key;
                        int i22 = nonce;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str4 = rawPath;
                    String str22 = canonicalPath;
                    String str32 = key;
                    int i222 = nonce;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unmountObb(String rawPath, boolean force, IObbActionListener token, int nonce) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    _data.writeInt(force);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(nonce);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unmountObb(rawPath, force, token, nonce);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isObbMounted(String rawPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isObbMounted(rawPath);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getMountedObbPath(String rawPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rawPath);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMountedObbPath(rawPath);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int decryptStorage(String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().decryptStorage(password);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int encryptStorage(int type, String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(password);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().encryptStorage(type, password);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int changeEncryptionPassword(int type, String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(password);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().changeEncryptionPassword(type, password);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StorageVolume[] getVolumeList(int uid, String packageName, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(packageName);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolumeList(uid, packageName, flags);
                    }
                    _reply.readException();
                    StorageVolume[] _result = (StorageVolume[]) _reply.createTypedArray(StorageVolume.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getEncryptionState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEncryptionState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int verifyEncryptionPassword(String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(password);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().verifyEncryptionPassword(password);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void mkdirs(String callingPkg, String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(path);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().mkdirs(callingPkg, path);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPasswordType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPasswordType();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPassword();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearPassword() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(38, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clearPassword();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setField(String field, String contents) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(field);
                    _data.writeString(contents);
                    if (this.mRemote.transact(39, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setField(field, contents);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public String getField(String field) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(field);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getField(field);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long lastMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().lastMaintenance();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void runMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().runMaintenance();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public DiskInfo[] getDisks() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisks();
                    }
                    _reply.readException();
                    DiskInfo[] _result = (DiskInfo[]) _reply.createTypedArray(DiskInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VolumeInfo[] getVolumes(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolumes(flags);
                    }
                    _reply.readException();
                    VolumeInfo[] _result = (VolumeInfo[]) _reply.createTypedArray(VolumeInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VolumeRecord[] getVolumeRecords(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolumeRecords(flags);
                    }
                    _reply.readException();
                    VolumeRecord[] _result = (VolumeRecord[]) _reply.createTypedArray(VolumeRecord.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void mount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().mount(volId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unmount(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unmount(volId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void format(String volId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().format(volId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void partitionPublic(String diskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().partitionPublic(diskId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void partitionPrivate(String diskId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().partitionPrivate(diskId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void partitionMixed(String diskId, int ratio) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(diskId);
                    _data.writeInt(ratio);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().partitionMixed(diskId, ratio);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeNickname(String fsUuid, String nickname) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    _data.writeString(nickname);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolumeNickname(fsUuid, nickname);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeUserFlags(String fsUuid, int flags, int mask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    _data.writeInt(flags);
                    _data.writeInt(mask);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolumeUserFlags(fsUuid, flags, mask);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forgetVolume(String fsUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(fsUuid);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forgetVolume(fsUuid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forgetAllVolumes() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forgetAllVolumes();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPrimaryStorageUuid() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrimaryStorageUuid();
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrimaryStorageUuid(String volumeUuid, IPackageMoveObserver callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPrimaryStorageUuid(volumeUuid, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void benchmark(String volId, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volId);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().benchmark(volId, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDebugFlags(int flags, int mask) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeInt(mask);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDebugFlags(flags, mask);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void createUserKey(int userId, int serialNumber, boolean ephemeral) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(ephemeral);
                    if (this.mRemote.transact(62, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().createUserKey(userId, serialNumber, ephemeral);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyUserKey(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().destroyUserKey(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unlockUserKey(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unlockUserKey(userId, serialNumber, token, secret);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void lockUserKey(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().lockUserKey(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUserKeyUnlocked(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserKeyUnlocked(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void prepareUserStorage(String volumeUuid, int userId, int serialNumber, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepareUserStorage(volumeUuid, userId, serialNumber, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void destroyUserStorage(String volumeUuid, int userId, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(userId);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().destroyUserStorage(volumeUuid, userId, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConvertibleToFBE() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConvertibleToFBE();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addUserKeyAuth(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addUserKeyAuth(userId, serialNumber, token, secret);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fixateNewestUserKeyAuth(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(72, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().fixateNewestUserKeyAuth(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void fstrim(int flags, IVoldTaskListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().fstrim(flags, listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AppFuseMount mountProxyFileDescriptorBridge() throws RemoteException {
                AppFuseMount _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().mountProxyFileDescriptorBridge();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AppFuseMount.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AppFuseMount _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor openProxyFileDescriptor(int mountPointId, int fileId, int mode) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mountPointId);
                    _data.writeInt(fileId);
                    _data.writeInt(mode);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openProxyFileDescriptor(mountPointId, fileId, mode);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCacheQuotaBytes(String volumeUuid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCacheQuotaBytes(volumeUuid, uid);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getCacheSizeBytes(String volumeUuid, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(77, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCacheSizeBytes(volumeUuid, uid);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getAllocatableBytes(String volumeUuid, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(78, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllocatableBytes(volumeUuid, flags, callingPackage);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void allocateBytes(String volumeUuid, long bytes, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(volumeUuid);
                    _data.writeLong(bytes);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().allocateBytes(volumeUuid, bytes, flags, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void runIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().runIdleMaintenance();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abortIdleMaintenance() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().abortIdleMaintenance();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void commitChanges() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(84, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().commitChanges();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supportsCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supportsCheckpoint();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startCheckpoint(int numTries) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(numTries);
                    if (this.mRemote.transact(86, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startCheckpoint(numTries);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean needsCheckpoint() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(87, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().needsCheckpoint();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abortChanges(String message, boolean retry) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    _data.writeInt(retry);
                    if (this.mRemote.transact(88, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().abortChanges(message, retry);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearUserKeyAuth(int userId, int serialNumber, byte[] token, byte[] secret) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(serialNumber);
                    _data.writeByteArray(token);
                    _data.writeByteArray(secret);
                    if (this.mRemote.transact(89, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearUserKeyAuth(userId, serialNumber, token, secret);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IStorageManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IStorageManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
