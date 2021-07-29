package android.mtp;

import android.media.MediaFile;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.FileObserver;
import android.os.storage.StorageVolume;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MtpStorageManager {
    private static final int IN_IGNORED = 32768;
    private static final int IN_ISDIR = 1073741824;
    private static final int IN_ONLYDIR = 16777216;
    private static final int IN_Q_OVERFLOW = 16384;
    /* access modifiers changed from: private */
    public static final String TAG = MtpStorageManager.class.getSimpleName();
    public static boolean sDebug = false;
    private volatile boolean mCheckConsistency = false;
    private Thread mConsistencyThread = new Thread(new Runnable() {
        public final void run() {
            MtpStorageManager.lambda$new$0(MtpStorageManager.this);
        }
    });
    private MtpNotifier mMtpNotifier;
    private int mNextObjectId = 1;
    private int mNextStorageId = 1;
    private HashMap<Integer, MtpObject> mObjects = new HashMap<>();
    private HashMap<Integer, MtpObject> mRoots = new HashMap<>();
    private Set<String> mSubdirectories;

    public static abstract class MtpNotifier {
        public abstract void sendObjectAdded(int i);

        public abstract void sendObjectInfoChanged(int i);

        public abstract void sendObjectRemoved(int i);
    }

    private enum MtpObjectState {
        NORMAL,
        FROZEN,
        FROZEN_ADDED,
        FROZEN_REMOVED,
        FROZEN_ONESHOT_ADD,
        FROZEN_ONESHOT_DEL
    }

    private enum MtpOperation {
        NONE,
        ADD,
        RENAME,
        COPY,
        DELETE
    }

    private class MtpObjectObserver extends FileObserver {
        MtpObject mObject;

        MtpObjectObserver(MtpObject object) {
            super(object.getPath().toString(), 16778184);
            this.mObject = object;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0147, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onEvent(int r6, java.lang.String r7) {
            /*
                r5 = this;
                android.mtp.MtpStorageManager r0 = android.mtp.MtpStorageManager.this
                monitor-enter(r0)
                r1 = r6 & 16384(0x4000, float:2.2959E-41)
                if (r1 == 0) goto L_0x0014
                java.lang.String r1 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.String r2 = "Received Inotify overflow event!"
                android.util.Log.e(r1, r2)     // Catch:{ all -> 0x0011 }
                goto L_0x0014
            L_0x0011:
                r1 = move-exception
                goto L_0x0148
            L_0x0014:
                android.mtp.MtpStorageManager$MtpObject r1 = r5.mObject     // Catch:{ all -> 0x0011 }
                android.mtp.MtpStorageManager$MtpObject r1 = r1.getChild(r7)     // Catch:{ all -> 0x0011 }
                r2 = r6 & 128(0x80, float:1.794E-43)
                if (r2 != 0) goto L_0x0113
                r2 = r6 & 256(0x100, float:3.59E-43)
                if (r2 == 0) goto L_0x0024
                goto L_0x0113
            L_0x0024:
                r2 = r6 & 64
                if (r2 != 0) goto L_0x00cd
                r2 = r6 & 512(0x200, float:7.175E-43)
                if (r2 == 0) goto L_0x002e
                goto L_0x00cd
            L_0x002e:
                r2 = 32768(0x8000, float:4.5918E-41)
                r2 = r2 & r6
                if (r2 == 0) goto L_0x0074
                boolean r2 = android.mtp.MtpStorageManager.sDebug     // Catch:{ all -> 0x0011 }
                if (r2 == 0) goto L_0x005b
                java.lang.String r2 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0011 }
                r3.<init>()     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = "inotify for "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                android.mtp.MtpStorageManager$MtpObject r4 = r5.mObject     // Catch:{ all -> 0x0011 }
                java.nio.file.Path r4 = r4.getPath()     // Catch:{ all -> 0x0011 }
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = " deleted"
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0011 }
                android.util.Log.i(r2, r3)     // Catch:{ all -> 0x0011 }
            L_0x005b:
                android.mtp.MtpStorageManager$MtpObject r2 = r5.mObject     // Catch:{ all -> 0x0011 }
                android.os.FileObserver r2 = r2.mObserver     // Catch:{ all -> 0x0011 }
                if (r2 == 0) goto L_0x006c
                android.mtp.MtpStorageManager$MtpObject r2 = r5.mObject     // Catch:{ all -> 0x0011 }
                android.os.FileObserver r2 = r2.mObserver     // Catch:{ all -> 0x0011 }
                r2.stopWatching()     // Catch:{ all -> 0x0011 }
            L_0x006c:
                android.mtp.MtpStorageManager$MtpObject r2 = r5.mObject     // Catch:{ all -> 0x0011 }
                r3 = 0
                android.os.FileObserver unused = r2.mObserver = r3     // Catch:{ all -> 0x0011 }
                goto L_0x0146
            L_0x0074:
                r2 = r6 & 8
                if (r2 == 0) goto L_0x00ab
                boolean r2 = android.mtp.MtpStorageManager.sDebug     // Catch:{ all -> 0x0011 }
                if (r2 == 0) goto L_0x00a2
                java.lang.String r2 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0011 }
                r3.<init>()     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = "inotify for "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                android.mtp.MtpStorageManager$MtpObject r4 = r5.mObject     // Catch:{ all -> 0x0011 }
                java.nio.file.Path r4 = r4.getPath()     // Catch:{ all -> 0x0011 }
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = " CLOSE_WRITE: "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r7)     // Catch:{ all -> 0x0011 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0011 }
                android.util.Log.i(r2, r3)     // Catch:{ all -> 0x0011 }
            L_0x00a2:
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch:{ all -> 0x0011 }
                android.mtp.MtpStorageManager$MtpObject r3 = r5.mObject     // Catch:{ all -> 0x0011 }
                r2.handleChangedObject(r3, r7)     // Catch:{ all -> 0x0011 }
                goto L_0x0146
            L_0x00ab:
                java.lang.String r2 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0011 }
                r3.<init>()     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = "Got unrecognized event "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r7)     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = " "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r6)     // Catch:{ all -> 0x0011 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0011 }
                android.util.Log.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x0011 }
                goto L_0x0146
            L_0x00cd:
                if (r1 != 0) goto L_0x00e9
                java.lang.String r2 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0011 }
                r3.<init>()     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = "Object was null in event "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r7)     // Catch:{ all -> 0x0011 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0011 }
                android.util.Log.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x0011 }
                monitor-exit(r0)     // Catch:{ all -> 0x0011 }
                return
            L_0x00e9:
                boolean r2 = android.mtp.MtpStorageManager.sDebug     // Catch:{ all -> 0x0011 }
                if (r2 == 0) goto L_0x010d
                java.lang.String r2 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0011 }
                r3.<init>()     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = "Got inotify removed event for "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r7)     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = " "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r6)     // Catch:{ all -> 0x0011 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0011 }
                android.util.Log.i(r2, r3)     // Catch:{ all -> 0x0011 }
            L_0x010d:
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch:{ all -> 0x0011 }
                r2.handleRemovedObject(r1)     // Catch:{ all -> 0x0011 }
                goto L_0x0146
            L_0x0113:
                boolean r2 = android.mtp.MtpStorageManager.sDebug     // Catch:{ all -> 0x0011 }
                if (r2 == 0) goto L_0x0137
                java.lang.String r2 = android.mtp.MtpStorageManager.TAG     // Catch:{ all -> 0x0011 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0011 }
                r3.<init>()     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = "Got inotify added event for "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r7)     // Catch:{ all -> 0x0011 }
                java.lang.String r4 = " "
                r3.append(r4)     // Catch:{ all -> 0x0011 }
                r3.append(r6)     // Catch:{ all -> 0x0011 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0011 }
                android.util.Log.i(r2, r3)     // Catch:{ all -> 0x0011 }
            L_0x0137:
                android.mtp.MtpStorageManager r2 = android.mtp.MtpStorageManager.this     // Catch:{ all -> 0x0011 }
                android.mtp.MtpStorageManager$MtpObject r3 = r5.mObject     // Catch:{ all -> 0x0011 }
                r4 = 1073741824(0x40000000, float:2.0)
                r4 = r4 & r6
                if (r4 == 0) goto L_0x0142
                r4 = 1
                goto L_0x0143
            L_0x0142:
                r4 = 0
            L_0x0143:
                r2.handleAddedObject(r3, r7, r4)     // Catch:{ all -> 0x0011 }
            L_0x0146:
                monitor-exit(r0)     // Catch:{ all -> 0x0011 }
                return
            L_0x0148:
                monitor-exit(r0)     // Catch:{ all -> 0x0011 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.MtpObjectObserver.onEvent(int, java.lang.String):void");
        }

        public void finalize() {
        }
    }

    public static class MtpObject {
        /* access modifiers changed from: private */
        public HashMap<String, MtpObject> mChildren;
        private int mId;
        private boolean mIsDir;
        private String mName;
        /* access modifiers changed from: private */
        public FileObserver mObserver = null;
        private MtpOperation mOp;
        private MtpObject mParent;
        private MtpObjectState mState = MtpObjectState.NORMAL;
        /* access modifiers changed from: private */
        public MtpStorage mStorage;
        private boolean mVisited = false;

        MtpObject(String name, int id, MtpStorage storage, MtpObject parent, boolean isDir) {
            this.mId = id;
            this.mName = name;
            this.mStorage = (MtpStorage) Preconditions.checkNotNull(storage);
            this.mParent = parent;
            HashMap<String, MtpObject> hashMap = null;
            this.mIsDir = isDir;
            this.mOp = MtpOperation.NONE;
            this.mChildren = this.mIsDir ? new HashMap<>() : hashMap;
        }

        public String getName() {
            return this.mName;
        }

        public int getId() {
            return this.mId;
        }

        public boolean isDir() {
            return this.mIsDir;
        }

        public int getFormat() {
            if (this.mIsDir) {
                return 12289;
            }
            return MediaFile.getFormatCode(this.mName, (String) null);
        }

        public int getStorageId() {
            return getRoot().getId();
        }

        public long getModifiedTime() {
            return getPath().toFile().lastModified() / 1000;
        }

        public MtpObject getParent() {
            return this.mParent;
        }

        public MtpObject getRoot() {
            return isRoot() ? this : this.mParent.getRoot();
        }

        public long getSize() {
            if (this.mIsDir) {
                return 0;
            }
            return getPath().toFile().length();
        }

        public Path getPath() {
            return isRoot() ? Paths.get(this.mName, new String[0]) : this.mParent.getPath().resolve(this.mName);
        }

        public boolean isRoot() {
            return this.mParent == null;
        }

        public String getVolumeName() {
            return this.mStorage.getVolumeName();
        }

        /* access modifiers changed from: private */
        public void setName(String name) {
            this.mName = name;
        }

        /* access modifiers changed from: private */
        public void setId(int id) {
            this.mId = id;
        }

        /* access modifiers changed from: private */
        public boolean isVisited() {
            return this.mVisited;
        }

        /* access modifiers changed from: private */
        public void setParent(MtpObject parent) {
            this.mParent = parent;
        }

        /* access modifiers changed from: private */
        public void setDir(boolean dir) {
            if (dir != this.mIsDir) {
                this.mIsDir = dir;
                this.mChildren = this.mIsDir ? new HashMap<>() : null;
            }
        }

        /* access modifiers changed from: private */
        public void setVisited(boolean visited) {
            this.mVisited = visited;
        }

        /* access modifiers changed from: private */
        public MtpObjectState getState() {
            return this.mState;
        }

        /* access modifiers changed from: private */
        public void setState(MtpObjectState state) {
            this.mState = state;
            if (this.mState == MtpObjectState.NORMAL) {
                this.mOp = MtpOperation.NONE;
            }
        }

        /* access modifiers changed from: private */
        public MtpOperation getOperation() {
            return this.mOp;
        }

        /* access modifiers changed from: private */
        public void setOperation(MtpOperation op) {
            this.mOp = op;
        }

        /* access modifiers changed from: private */
        public FileObserver getObserver() {
            return this.mObserver;
        }

        /* access modifiers changed from: private */
        public void setObserver(FileObserver observer) {
            this.mObserver = observer;
        }

        /* access modifiers changed from: private */
        public void addChild(MtpObject child) {
            this.mChildren.put(child.getName(), child);
        }

        /* access modifiers changed from: private */
        public MtpObject getChild(String name) {
            return this.mChildren.get(name);
        }

        /* access modifiers changed from: private */
        public Collection<MtpObject> getChildren() {
            return this.mChildren.values();
        }

        /* access modifiers changed from: private */
        public boolean exists() {
            return getPath().toFile().exists();
        }

        /* access modifiers changed from: private */
        public MtpObject copy(boolean recursive) {
            MtpObject copy = new MtpObject(this.mName, this.mId, this.mStorage, this.mParent, this.mIsDir);
            copy.mIsDir = this.mIsDir;
            copy.mVisited = this.mVisited;
            copy.mState = this.mState;
            copy.mChildren = this.mIsDir ? new HashMap<>() : null;
            if (recursive && this.mIsDir) {
                for (MtpObject child : this.mChildren.values()) {
                    MtpObject childCopy = child.copy(true);
                    childCopy.setParent(copy);
                    copy.addChild(childCopy);
                }
            }
            return copy;
        }
    }

    public MtpStorageManager(MtpNotifier notifier, Set<String> subdirectories) {
        this.mMtpNotifier = notifier;
        this.mSubdirectories = subdirectories;
        if (this.mCheckConsistency) {
            this.mConsistencyThread.start();
        }
    }

    public static /* synthetic */ void lambda$new$0(MtpStorageManager mtpStorageManager) {
        while (mtpStorageManager.mCheckConsistency) {
            try {
                Thread.sleep(15000);
                if (mtpStorageManager.checkConsistency()) {
                    Log.v(TAG, "Cache is consistent");
                } else {
                    Log.w(TAG, "Cache is not consistent");
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public synchronized void close() {
        for (MtpObject obj : this.mObjects.values()) {
            if (obj.getObserver() != null) {
                obj.getObserver().stopWatching();
                obj.setObserver((FileObserver) null);
            }
        }
        for (MtpObject obj2 : this.mRoots.values()) {
            if (obj2.getObserver() != null) {
                obj2.getObserver().stopWatching();
                obj2.setObserver((FileObserver) null);
            }
        }
        if (this.mCheckConsistency) {
            this.mCheckConsistency = false;
            this.mConsistencyThread.interrupt();
            try {
                this.mConsistencyThread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void setSubdirectories(Set<String> subDirs) {
        this.mSubdirectories = subDirs;
    }

    public synchronized MtpStorage addMtpStorage(StorageVolume volume) {
        MtpStorage storage;
        int storageId = ((getNextStorageId() & 65535) << 16) + 1;
        storage = new MtpStorage(volume, storageId);
        this.mRoots.put(Integer.valueOf(storageId), new MtpObject(storage.getPath(), storageId, storage, (MtpObject) null, true));
        return storage;
    }

    public synchronized void removeMtpStorage(MtpStorage storage) {
        removeObjectFromCache(getStorageRoot(storage.getStorageId()), true, true);
    }

    private synchronized boolean isSpecialSubDir(MtpObject obj) {
        return obj.getParent().isRoot() && this.mSubdirectories != null && !this.mSubdirectories.contains(obj.getName());
    }

    public synchronized MtpObject getByPath(String path) {
        MtpObject obj = null;
        for (MtpObject root : this.mRoots.values()) {
            if (path.startsWith(root.getName())) {
                obj = root;
                path = path.substring(root.getName().length());
            }
        }
        String[] split = path.split("/");
        int length = split.length;
        int i = 0;
        while (i < length) {
            String name = split[i];
            if (obj != null) {
                if (obj.isDir()) {
                    if (!"".equals(name)) {
                        if (!obj.isVisited()) {
                            getChildren(obj);
                        }
                        obj = obj.getChild(name);
                    }
                    i++;
                }
            }
            return null;
        }
        return obj;
    }

    public synchronized MtpObject getObject(int id) {
        if (id == 0 || id == -1) {
            Log.w(TAG, "Can't get root storages with getObject()");
            return null;
        } else if (!this.mObjects.containsKey(Integer.valueOf(id))) {
            String str = TAG;
            Log.w(str, "Id " + id + " doesn't exist");
            return null;
        } else {
            return this.mObjects.get(Integer.valueOf(id));
        }
    }

    public MtpObject getStorageRoot(int id) {
        if (this.mRoots.containsKey(Integer.valueOf(id))) {
            return this.mRoots.get(Integer.valueOf(id));
        }
        String str = TAG;
        Log.w(str, "StorageId " + id + " doesn't exist");
        return null;
    }

    private int getNextObjectId() {
        int ret = this.mNextObjectId;
        this.mNextObjectId = (int) (((long) this.mNextObjectId) + 1);
        return ret;
    }

    private int getNextStorageId() {
        int i = this.mNextStorageId;
        this.mNextStorageId = i + 1;
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0036, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0050, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<android.mtp.MtpStorageManager.MtpObject> getObjects(int r8, int r9, int r10) {
        /*
            r7 = this;
            monitor-enter(r7)
            if (r8 != 0) goto L_0x0005
            r0 = 1
            goto L_0x0006
        L_0x0005:
            r0 = 0
        L_0x0006:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0051 }
            r1.<init>()     // Catch:{ all -> 0x0051 }
            r2 = 1
            r3 = -1
            if (r8 != r3) goto L_0x0010
            r8 = 0
        L_0x0010:
            r4 = 0
            if (r10 != r3) goto L_0x0037
            if (r8 != 0) goto L_0x0037
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r7.mRoots     // Catch:{ all -> 0x0051 }
            java.util.Collection r3 = r3.values()     // Catch:{ all -> 0x0051 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0051 }
        L_0x001f:
            boolean r5 = r3.hasNext()     // Catch:{ all -> 0x0051 }
            if (r5 == 0) goto L_0x0031
            java.lang.Object r5 = r3.next()     // Catch:{ all -> 0x0051 }
            android.mtp.MtpStorageManager$MtpObject r5 = (android.mtp.MtpStorageManager.MtpObject) r5     // Catch:{ all -> 0x0051 }
            boolean r6 = r7.getObjects(r1, r5, r9, r0)     // Catch:{ all -> 0x0051 }
            r2 = r2 & r6
            goto L_0x001f
        L_0x0031:
            if (r2 == 0) goto L_0x0035
            r4 = r1
        L_0x0035:
            monitor-exit(r7)
            return r4
        L_0x0037:
            if (r8 != 0) goto L_0x003e
            android.mtp.MtpStorageManager$MtpObject r3 = r7.getStorageRoot(r10)     // Catch:{ all -> 0x0051 }
            goto L_0x0042
        L_0x003e:
            android.mtp.MtpStorageManager$MtpObject r3 = r7.getObject(r8)     // Catch:{ all -> 0x0051 }
        L_0x0042:
            if (r3 != 0) goto L_0x0046
            monitor-exit(r7)
            return r4
        L_0x0046:
            boolean r5 = r7.getObjects(r1, r3, r9, r0)     // Catch:{ all -> 0x0051 }
            r2 = r5
            if (r2 == 0) goto L_0x004f
            r4 = r1
        L_0x004f:
            monitor-exit(r7)
            return r4
        L_0x0051:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.getObjects(int, int, int):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean getObjects(java.util.List<android.mtp.MtpStorageManager.MtpObject> r6, android.mtp.MtpStorageManager.MtpObject r7, int r8, boolean r9) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.util.Collection r0 = r5.getChildren(r7)     // Catch:{ all -> 0x0048 }
            if (r0 != 0) goto L_0x000a
            r1 = 0
            monitor-exit(r5)
            return r1
        L_0x000a:
            java.util.Iterator r1 = r0.iterator()     // Catch:{ all -> 0x0048 }
        L_0x000e:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0026
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0048 }
            android.mtp.MtpStorageManager$MtpObject r2 = (android.mtp.MtpStorageManager.MtpObject) r2     // Catch:{ all -> 0x0048 }
            if (r8 == 0) goto L_0x0022
            int r3 = r2.getFormat()     // Catch:{ all -> 0x0048 }
            if (r3 != r8) goto L_0x0025
        L_0x0022:
            r6.add(r2)     // Catch:{ all -> 0x0048 }
        L_0x0025:
            goto L_0x000e
        L_0x0026:
            r1 = 1
            if (r9 == 0) goto L_0x0046
            java.util.Iterator r2 = r0.iterator()     // Catch:{ all -> 0x0048 }
        L_0x002d:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0048 }
            if (r3 == 0) goto L_0x0046
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0048 }
            android.mtp.MtpStorageManager$MtpObject r3 = (android.mtp.MtpStorageManager.MtpObject) r3     // Catch:{ all -> 0x0048 }
            boolean r4 = r3.isDir()     // Catch:{ all -> 0x0048 }
            if (r4 == 0) goto L_0x0045
            r4 = 1
            boolean r4 = r5.getObjects(r6, r3, r8, r4)     // Catch:{ all -> 0x0048 }
            r1 = r1 & r4
        L_0x0045:
            goto L_0x002d
        L_0x0046:
            monitor-exit(r5)
            return r1
        L_0x0048:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.getObjects(java.util.List, android.mtp.MtpStorageManager$MtpObject, int, boolean):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0065, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0066, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006a, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006b, code lost:
        r7 = r4;
        r4 = r3;
        r3 = r7;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:47:0x0092=Splitter:B:47:0x0092, B:42:0x008a=Splitter:B:42:0x008a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.Collection<android.mtp.MtpStorageManager.MtpObject> getChildren(android.mtp.MtpStorageManager.MtpObject r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            if (r9 == 0) goto L_0x0092
            boolean r1 = r9.isDir()     // Catch:{ all -> 0x0090 }
            if (r1 != 0) goto L_0x000c
            goto L_0x0092
        L_0x000c:
            boolean r1 = r9.isVisited()     // Catch:{ all -> 0x0090 }
            if (r1 != 0) goto L_0x008a
            java.nio.file.Path r1 = r9.getPath()     // Catch:{ all -> 0x0090 }
            android.os.FileObserver r2 = r9.getObserver()     // Catch:{ all -> 0x0090 }
            if (r2 == 0) goto L_0x0023
            java.lang.String r2 = TAG     // Catch:{ all -> 0x0090 }
            java.lang.String r3 = "Observer is not null!"
            android.util.Log.e(r2, r3)     // Catch:{ all -> 0x0090 }
        L_0x0023:
            android.mtp.MtpStorageManager$MtpObjectObserver r2 = new android.mtp.MtpStorageManager$MtpObjectObserver     // Catch:{ all -> 0x0090 }
            r2.<init>(r9)     // Catch:{ all -> 0x0090 }
            r9.setObserver(r2)     // Catch:{ all -> 0x0090 }
            android.os.FileObserver r2 = r9.getObserver()     // Catch:{ all -> 0x0090 }
            r2.startWatching()     // Catch:{ all -> 0x0090 }
            java.nio.file.DirectoryStream r2 = java.nio.file.Files.newDirectoryStream(r1)     // Catch:{ IOException | DirectoryIteratorException -> 0x0074 }
            java.util.Iterator r3 = r2.iterator()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
        L_0x003a:
            boolean r4 = r3.hasNext()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            if (r4 == 0) goto L_0x005a
            java.lang.Object r4 = r3.next()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            java.nio.file.Path r4 = (java.nio.file.Path) r4     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            java.nio.file.Path r5 = r4.getFileName()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            java.io.File r6 = r4.toFile()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            boolean r6 = r6.isDirectory()     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            r8.addObjectToCache(r9, r5, r6)     // Catch:{ Throwable -> 0x0068, all -> 0x0065 }
            goto L_0x003a
        L_0x005a:
            if (r2 == 0) goto L_0x005f
            $closeResource(r0, r2)     // Catch:{ IOException | DirectoryIteratorException -> 0x0074 }
        L_0x005f:
            r0 = 1
            r9.setVisited(r0)     // Catch:{ all -> 0x0090 }
            goto L_0x008a
        L_0x0065:
            r3 = move-exception
            r4 = r0
            goto L_0x006e
        L_0x0068:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x006a }
        L_0x006a:
            r4 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
        L_0x006e:
            if (r2 == 0) goto L_0x0073
            $closeResource(r4, r2)     // Catch:{ IOException | DirectoryIteratorException -> 0x0074 }
        L_0x0073:
            throw r3     // Catch:{ IOException | DirectoryIteratorException -> 0x0074 }
        L_0x0074:
            r2 = move-exception
            java.lang.String r3 = TAG     // Catch:{ all -> 0x0090 }
            java.lang.String r4 = r2.toString()     // Catch:{ all -> 0x0090 }
            android.util.Log.e(r3, r4)     // Catch:{ all -> 0x0090 }
            android.os.FileObserver r3 = r9.getObserver()     // Catch:{ all -> 0x0090 }
            r3.stopWatching()     // Catch:{ all -> 0x0090 }
            r9.setObserver(r0)     // Catch:{ all -> 0x0090 }
            monitor-exit(r8)
            return r0
        L_0x008a:
            java.util.Collection r0 = r9.getChildren()     // Catch:{ all -> 0x0090 }
            monitor-exit(r8)
            return r0
        L_0x0090:
            r9 = move-exception
            goto L_0x00b8
        L_0x0092:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x0090 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0090 }
            r2.<init>()     // Catch:{ all -> 0x0090 }
            java.lang.String r3 = "Can't find children of "
            r2.append(r3)     // Catch:{ all -> 0x0090 }
            if (r9 != 0) goto L_0x00a4
            java.lang.String r3 = "null"
            goto L_0x00ac
        L_0x00a4:
            int r3 = r9.getId()     // Catch:{ all -> 0x0090 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0090 }
        L_0x00ac:
            r2.append(r3)     // Catch:{ all -> 0x0090 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0090 }
            android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)     // Catch:{ all -> 0x0090 }
            monitor-exit(r8)
            return r0
        L_0x00b8:
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.getChildren(android.mtp.MtpStorageManager$MtpObject):java.util.Collection");
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

    private synchronized MtpObject addObjectToCache(MtpObject parent, String newName, boolean isDir) {
        if (!parent.isRoot() && getObject(parent.getId()) != parent) {
            return null;
        }
        if (parent.getChild(newName) != null) {
            return null;
        }
        if (this.mSubdirectories != null && parent.isRoot() && !this.mSubdirectories.contains(newName)) {
            return null;
        }
        MtpObject obj = new MtpObject(newName, getNextObjectId(), parent.mStorage, parent, isDir);
        this.mObjects.put(Integer.valueOf(obj.getId()), obj);
        parent.addChild(obj);
        return obj;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean removeObjectFromCache(android.mtp.MtpStorageManager.MtpObject r8, boolean r9, boolean r10) {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r8.isRoot()     // Catch:{ all -> 0x00d5 }
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001e
            android.mtp.MtpStorageManager$MtpObject r0 = r8.getParent()     // Catch:{ all -> 0x00d5 }
            java.util.HashMap r0 = r0.mChildren     // Catch:{ all -> 0x00d5 }
            java.lang.String r3 = r8.getName()     // Catch:{ all -> 0x00d5 }
            boolean r0 = r0.remove(r3, r8)     // Catch:{ all -> 0x00d5 }
            if (r0 == 0) goto L_0x001c
            goto L_0x001e
        L_0x001c:
            r0 = r1
            goto L_0x001f
        L_0x001e:
            r0 = r2
        L_0x001f:
            if (r0 != 0) goto L_0x003f
            boolean r3 = sDebug     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x003f
            java.lang.String r3 = TAG     // Catch:{ all -> 0x00d5 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d5 }
            r4.<init>()     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = "Failed to remove from parent "
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.nio.file.Path r5 = r8.getPath()     // Catch:{ all -> 0x00d5 }
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00d5 }
            android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x00d5 }
        L_0x003f:
            boolean r3 = r8.isRoot()     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x005c
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r7.mRoots     // Catch:{ all -> 0x00d5 }
            int r4 = r8.getId()     // Catch:{ all -> 0x00d5 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x00d5 }
            boolean r3 = r3.remove(r4, r8)     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x0059
            if (r0 == 0) goto L_0x0059
            r3 = r2
            goto L_0x005a
        L_0x0059:
            r3 = r1
        L_0x005a:
            r0 = r3
            goto L_0x0074
        L_0x005c:
            if (r9 == 0) goto L_0x0074
            java.util.HashMap<java.lang.Integer, android.mtp.MtpStorageManager$MtpObject> r3 = r7.mObjects     // Catch:{ all -> 0x00d5 }
            int r4 = r8.getId()     // Catch:{ all -> 0x00d5 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x00d5 }
            boolean r3 = r3.remove(r4, r8)     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x0072
            if (r0 == 0) goto L_0x0072
            r3 = r2
            goto L_0x0073
        L_0x0072:
            r3 = r1
        L_0x0073:
            r0 = r3
        L_0x0074:
            if (r0 != 0) goto L_0x0094
            boolean r3 = sDebug     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x0094
            java.lang.String r3 = TAG     // Catch:{ all -> 0x00d5 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d5 }
            r4.<init>()     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = "Failed to remove from global cache "
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.nio.file.Path r5 = r8.getPath()     // Catch:{ all -> 0x00d5 }
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00d5 }
            android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x00d5 }
        L_0x0094:
            android.os.FileObserver r3 = r8.getObserver()     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x00a5
            android.os.FileObserver r3 = r8.getObserver()     // Catch:{ all -> 0x00d5 }
            r3.stopWatching()     // Catch:{ all -> 0x00d5 }
            r3 = 0
            r8.setObserver(r3)     // Catch:{ all -> 0x00d5 }
        L_0x00a5:
            boolean r3 = r8.isDir()     // Catch:{ all -> 0x00d5 }
            if (r3 == 0) goto L_0x00d3
            if (r10 == 0) goto L_0x00d3
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x00d5 }
            java.util.Collection r4 = r8.getChildren()     // Catch:{ all -> 0x00d5 }
            r3.<init>(r4)     // Catch:{ all -> 0x00d5 }
            java.util.Iterator r4 = r3.iterator()     // Catch:{ all -> 0x00d5 }
        L_0x00ba:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x00d5 }
            if (r5 == 0) goto L_0x00d3
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x00d5 }
            android.mtp.MtpStorageManager$MtpObject r5 = (android.mtp.MtpStorageManager.MtpObject) r5     // Catch:{ all -> 0x00d5 }
            boolean r6 = r7.removeObjectFromCache(r5, r9, r2)     // Catch:{ all -> 0x00d5 }
            if (r6 == 0) goto L_0x00d0
            if (r0 == 0) goto L_0x00d0
            r6 = r2
            goto L_0x00d1
        L_0x00d0:
            r6 = r1
        L_0x00d1:
            r0 = r6
            goto L_0x00ba
        L_0x00d3:
            monitor-exit(r7)
            return r0
        L_0x00d5:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.removeObjectFromCache(android.mtp.MtpStorageManager$MtpObject, boolean, boolean):boolean");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0136, code lost:
        r4 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0137, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x013b, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x013c, code lost:
        r9 = r5;
        r5 = r4;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x017c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void handleAddedObject(android.mtp.MtpStorageManager.MtpObject r11, java.lang.String r12, boolean r13) {
        /*
            r10 = this;
            monitor-enter(r10)
            android.mtp.MtpStorageManager$MtpOperation r0 = android.mtp.MtpStorageManager.MtpOperation.NONE     // Catch:{ all -> 0x017d }
            android.mtp.MtpStorageManager$MtpObject r1 = r11.getChild(r12)     // Catch:{ all -> 0x017d }
            if (r1 == 0) goto L_0x009b
            android.mtp.MtpStorageManager$MtpObjectState r2 = r1.getState()     // Catch:{ all -> 0x017d }
            android.mtp.MtpStorageManager$MtpOperation r3 = r1.getOperation()     // Catch:{ all -> 0x017d }
            r0 = r3
            boolean r3 = r1.isDir()     // Catch:{ all -> 0x017d }
            if (r3 == r13) goto L_0x0036
            android.mtp.MtpStorageManager$MtpObjectState r3 = android.mtp.MtpStorageManager.MtpObjectState.FROZEN_REMOVED     // Catch:{ all -> 0x017d }
            if (r2 == r3) goto L_0x0036
            java.lang.String r3 = TAG     // Catch:{ all -> 0x017d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x017d }
            r4.<init>()     // Catch:{ all -> 0x017d }
            java.lang.String r5 = "Inconsistent directory info! "
            r4.append(r5)     // Catch:{ all -> 0x017d }
            java.nio.file.Path r5 = r1.getPath()     // Catch:{ all -> 0x017d }
            r4.append(r5)     // Catch:{ all -> 0x017d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x017d }
            android.util.Log.d(r3, r4)     // Catch:{ all -> 0x017d }
        L_0x0036:
            r1.setDir(r13)     // Catch:{ all -> 0x017d }
            int[] r3 = android.mtp.MtpStorageManager.AnonymousClass1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState     // Catch:{ all -> 0x017d }
            int r4 = r2.ordinal()     // Catch:{ all -> 0x017d }
            r3 = r3[r4]     // Catch:{ all -> 0x017d }
            switch(r3) {
                case 1: goto L_0x004f;
                case 2: goto L_0x004f;
                case 3: goto L_0x0049;
                case 4: goto L_0x0047;
                case 5: goto L_0x0047;
                default: goto L_0x0044;
            }     // Catch:{ all -> 0x017d }
        L_0x0044:
            java.lang.String r3 = TAG     // Catch:{ all -> 0x017d }
            goto L_0x0055
        L_0x0047:
            monitor-exit(r10)
            return
        L_0x0049:
            android.mtp.MtpStorageManager$MtpObjectState r3 = android.mtp.MtpStorageManager.MtpObjectState.NORMAL     // Catch:{ all -> 0x017d }
            r1.setState(r3)     // Catch:{ all -> 0x017d }
            goto L_0x0071
        L_0x004f:
            android.mtp.MtpStorageManager$MtpObjectState r3 = android.mtp.MtpStorageManager.MtpObjectState.FROZEN_ADDED     // Catch:{ all -> 0x017d }
            r1.setState(r3)     // Catch:{ all -> 0x017d }
            goto L_0x0071
        L_0x0055:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x017d }
            r4.<init>()     // Catch:{ all -> 0x017d }
            java.lang.String r5 = "Unexpected state in add "
            r4.append(r5)     // Catch:{ all -> 0x017d }
            r4.append(r12)     // Catch:{ all -> 0x017d }
            java.lang.String r5 = " "
            r4.append(r5)     // Catch:{ all -> 0x017d }
            r4.append(r2)     // Catch:{ all -> 0x017d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x017d }
            android.util.Log.w((java.lang.String) r3, (java.lang.String) r4)     // Catch:{ all -> 0x017d }
        L_0x0071:
            boolean r3 = sDebug     // Catch:{ all -> 0x017d }
            if (r3 == 0) goto L_0x009a
            java.lang.String r3 = TAG     // Catch:{ all -> 0x017d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x017d }
            r4.<init>()     // Catch:{ all -> 0x017d }
            r4.append(r2)     // Catch:{ all -> 0x017d }
            java.lang.String r5 = " transitioned to "
            r4.append(r5)     // Catch:{ all -> 0x017d }
            android.mtp.MtpStorageManager$MtpObjectState r5 = r1.getState()     // Catch:{ all -> 0x017d }
            r4.append(r5)     // Catch:{ all -> 0x017d }
            java.lang.String r5 = " in op "
            r4.append(r5)     // Catch:{ all -> 0x017d }
            r4.append(r0)     // Catch:{ all -> 0x017d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x017d }
            android.util.Log.i(r3, r4)     // Catch:{ all -> 0x017d }
        L_0x009a:
            goto L_0x00ab
        L_0x009b:
            android.mtp.MtpStorageManager$MtpObject r2 = r10.addObjectToCache(r11, r12, r13)     // Catch:{ all -> 0x017d }
            r1 = r2
            if (r1 == 0) goto L_0x015b
            android.mtp.MtpStorageManager$MtpNotifier r2 = r10.mMtpNotifier     // Catch:{ all -> 0x017d }
            int r3 = r1.getId()     // Catch:{ all -> 0x017d }
            r2.sendObjectAdded(r3)     // Catch:{ all -> 0x017d }
        L_0x00ab:
            if (r13 == 0) goto L_0x0159
            android.mtp.MtpStorageManager$MtpOperation r2 = android.mtp.MtpStorageManager.MtpOperation.RENAME     // Catch:{ all -> 0x017d }
            if (r0 != r2) goto L_0x00b3
            monitor-exit(r10)
            return
        L_0x00b3:
            android.mtp.MtpStorageManager$MtpOperation r2 = android.mtp.MtpStorageManager.MtpOperation.COPY     // Catch:{ all -> 0x017d }
            if (r0 != r2) goto L_0x00bf
            boolean r2 = r1.isVisited()     // Catch:{ all -> 0x017d }
            if (r2 != 0) goto L_0x00bf
            monitor-exit(r10)
            return
        L_0x00bf:
            android.os.FileObserver r2 = r1.getObserver()     // Catch:{ all -> 0x017d }
            if (r2 == 0) goto L_0x00ce
            java.lang.String r2 = TAG     // Catch:{ all -> 0x017d }
            java.lang.String r3 = "Observer is not null!"
            android.util.Log.e(r2, r3)     // Catch:{ all -> 0x017d }
            monitor-exit(r10)
            return
        L_0x00ce:
            android.mtp.MtpStorageManager$MtpObjectObserver r2 = new android.mtp.MtpStorageManager$MtpObjectObserver     // Catch:{ all -> 0x017d }
            r2.<init>(r1)     // Catch:{ all -> 0x017d }
            r1.setObserver(r2)     // Catch:{ all -> 0x017d }
            android.os.FileObserver r2 = r1.getObserver()     // Catch:{ all -> 0x017d }
            r2.startWatching()     // Catch:{ all -> 0x017d }
            r2 = 1
            r1.setVisited(r2)     // Catch:{ all -> 0x017d }
            r2 = 0
            java.nio.file.Path r3 = r1.getPath()     // Catch:{ IOException | DirectoryIteratorException -> 0x0145 }
            java.nio.file.DirectoryStream r3 = java.nio.file.Files.newDirectoryStream(r3)     // Catch:{ IOException | DirectoryIteratorException -> 0x0145 }
            java.util.Iterator r4 = r3.iterator()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
        L_0x00ee:
            boolean r5 = r4.hasNext()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            if (r5 == 0) goto L_0x0130
            java.lang.Object r5 = r4.next()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.nio.file.Path r5 = (java.nio.file.Path) r5     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            boolean r6 = sDebug     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            if (r6 == 0) goto L_0x011c
            java.lang.String r6 = TAG     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            r7.<init>()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.lang.String r8 = "Manually handling event for "
            r7.append(r8)     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.nio.file.Path r8 = r5.getFileName()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            android.util.Log.i(r6, r7)     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
        L_0x011c:
            java.nio.file.Path r6 = r5.getFileName()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            java.io.File r7 = r5.toFile()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            boolean r7 = r7.isDirectory()     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            r10.handleAddedObject(r1, r6, r7)     // Catch:{ Throwable -> 0x0139, all -> 0x0136 }
            goto L_0x00ee
        L_0x0130:
            if (r3 == 0) goto L_0x0135
            $closeResource(r2, r3)     // Catch:{ IOException | DirectoryIteratorException -> 0x0145 }
        L_0x0135:
            goto L_0x0159
        L_0x0136:
            r4 = move-exception
            r5 = r2
            goto L_0x013f
        L_0x0139:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x013b }
        L_0x013b:
            r5 = move-exception
            r9 = r5
            r5 = r4
            r4 = r9
        L_0x013f:
            if (r3 == 0) goto L_0x0144
            $closeResource(r5, r3)     // Catch:{ IOException | DirectoryIteratorException -> 0x0145 }
        L_0x0144:
            throw r4     // Catch:{ IOException | DirectoryIteratorException -> 0x0145 }
        L_0x0145:
            r3 = move-exception
            java.lang.String r4 = TAG     // Catch:{ all -> 0x017d }
            java.lang.String r5 = r3.toString()     // Catch:{ all -> 0x017d }
            android.util.Log.e(r4, r5)     // Catch:{ all -> 0x017d }
            android.os.FileObserver r4 = r1.getObserver()     // Catch:{ all -> 0x017d }
            r4.stopWatching()     // Catch:{ all -> 0x017d }
            r1.setObserver(r2)     // Catch:{ all -> 0x017d }
        L_0x0159:
            monitor-exit(r10)
            return
        L_0x015b:
            boolean r2 = sDebug     // Catch:{ all -> 0x017d }
            if (r2 == 0) goto L_0x017b
            java.lang.String r2 = TAG     // Catch:{ all -> 0x017d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x017d }
            r3.<init>()     // Catch:{ all -> 0x017d }
            java.lang.String r4 = "object "
            r3.append(r4)     // Catch:{ all -> 0x017d }
            r3.append(r12)     // Catch:{ all -> 0x017d }
            java.lang.String r4 = " already exists"
            r3.append(r4)     // Catch:{ all -> 0x017d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x017d }
            android.util.Log.w((java.lang.String) r2, (java.lang.String) r3)     // Catch:{ all -> 0x017d }
        L_0x017b:
            monitor-exit(r10)
            return
        L_0x017d:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.handleAddedObject(android.mtp.MtpStorageManager$MtpObject, java.lang.String, boolean):void");
    }

    /* access modifiers changed from: private */
    public synchronized void handleRemovedObject(MtpObject obj) {
        MtpObjectState state = obj.getState();
        MtpOperation op = obj.getOperation();
        int i = AnonymousClass1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[state.ordinal()];
        boolean z = true;
        if (i != 1) {
            switch (i) {
                case 4:
                    if (removeObjectFromCache(obj, true, true)) {
                        this.mMtpNotifier.sendObjectRemoved(obj.getId());
                        break;
                    }
                    break;
                case 5:
                    obj.setState(MtpObjectState.FROZEN_REMOVED);
                    break;
                case 6:
                    if (op == MtpOperation.RENAME) {
                        z = false;
                    }
                    removeObjectFromCache(obj, z, false);
                    break;
                default:
                    Log.e(TAG, "Got unexpected object remove for " + obj.getName());
                    break;
            }
        } else {
            obj.setState(MtpObjectState.FROZEN_REMOVED);
        }
        if (sDebug) {
            Log.i(TAG, state + " transitioned to " + obj.getState() + " in op " + op);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void handleChangedObject(MtpObject parent, String path) {
        MtpOperation mtpOperation = MtpOperation.NONE;
        MtpObject obj = parent.getChild(path);
        if (obj != null) {
            if (!obj.isDir() && obj.getSize() > 0) {
                MtpObjectState access$1400 = obj.getState();
                MtpOperation op = obj.getOperation();
                this.mMtpNotifier.sendObjectInfoChanged(obj.getId());
                if (sDebug) {
                    String str = TAG;
                    Log.d(str, "sendObjectInfoChanged: id=" + obj.getId() + ",size=" + obj.getSize());
                }
            }
        } else if (sDebug) {
            String str2 = TAG;
            Log.w(str2, "object " + path + " null");
        }
    }

    public void flushEvents() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void dump() {
        for (Integer intValue : this.mObjects.keySet()) {
            int key = intValue.intValue();
            MtpObject obj = this.mObjects.get(Integer.valueOf(key));
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append(" | ");
            sb.append(obj.getParent() == null ? Integer.valueOf(obj.getParent().getId()) : "null");
            sb.append(" | ");
            sb.append(obj.getName());
            sb.append(" | ");
            sb.append(obj.isDir() ? "dir" : "obj");
            sb.append(" | ");
            sb.append(obj.isVisited() ? "v" : "nv");
            sb.append(" | ");
            sb.append(obj.getState());
            Log.i(str, sb.toString());
        }
    }

    public synchronized boolean checkConsistency() {
        boolean ret;
        DirectoryStream<Path> stream;
        List<MtpObject> objs = new ArrayList<>();
        objs.addAll(this.mRoots.values());
        objs.addAll(this.mObjects.values());
        ret = true;
        for (MtpObject obj : objs) {
            if (!obj.exists()) {
                String str = TAG;
                Log.w(str, "Object doesn't exist " + obj.getPath() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + obj.getId());
                ret = false;
            }
            if (obj.getState() != MtpObjectState.NORMAL) {
                String str2 = TAG;
                Log.w(str2, "Object " + obj.getPath() + " in state " + obj.getState());
                ret = false;
            }
            if (obj.getOperation() != MtpOperation.NONE) {
                String str3 = TAG;
                Log.w(str3, "Object " + obj.getPath() + " in operation " + obj.getOperation());
                ret = false;
            }
            if (!obj.isRoot() && this.mObjects.get(Integer.valueOf(obj.getId())) != obj) {
                String str4 = TAG;
                Log.w(str4, "Object " + obj.getPath() + " is not in map correctly");
                ret = false;
            }
            if (obj.getParent() != null) {
                if (obj.getParent().isRoot() && obj.getParent() != this.mRoots.get(Integer.valueOf(obj.getParent().getId()))) {
                    String str5 = TAG;
                    Log.w(str5, "Root parent is not in root mapping " + obj.getPath());
                    ret = false;
                }
                if (!obj.getParent().isRoot() && obj.getParent() != this.mObjects.get(Integer.valueOf(obj.getParent().getId()))) {
                    String str6 = TAG;
                    Log.w(str6, "Parent is not in object mapping " + obj.getPath());
                    ret = false;
                }
                if (obj.getParent().getChild(obj.getName()) != obj) {
                    String str7 = TAG;
                    Log.w(str7, "Child does not exist in parent " + obj.getPath());
                    ret = false;
                }
            }
            if (obj.isDir()) {
                if (obj.isVisited() == (obj.getObserver() == null)) {
                    String str8 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append(obj.getPath());
                    sb.append(" is ");
                    sb.append(obj.isVisited() ? "" : "not ");
                    sb.append(" visited but observer is ");
                    sb.append(obj.getObserver());
                    Log.w(str8, sb.toString());
                    ret = false;
                }
                if (!obj.isVisited() && obj.getChildren().size() > 0) {
                    String str9 = TAG;
                    Log.w(str9, obj.getPath() + " is not visited but has children");
                    ret = false;
                }
                try {
                    stream = Files.newDirectoryStream(obj.getPath());
                    Set<String> files = new HashSet<>();
                    for (Path file : stream) {
                        if (obj.isVisited() && obj.getChild(file.getFileName().toString()) == null && (this.mSubdirectories == null || !obj.isRoot() || this.mSubdirectories.contains(file.getFileName().toString()))) {
                            String str10 = TAG;
                            Log.w(str10, "File exists in fs but not in children " + file);
                            ret = false;
                        }
                        files.add(file.toString());
                    }
                    for (MtpObject child : obj.getChildren()) {
                        if (!files.contains(child.getPath().toString())) {
                            String str11 = TAG;
                            Log.w(str11, "File in children doesn't exist in fs " + child.getPath());
                            ret = false;
                        }
                        if (child != this.mObjects.get(Integer.valueOf(child.getId()))) {
                            String str12 = TAG;
                            Log.w(str12, "Child is not in object map " + child.getPath());
                            ret = false;
                        }
                    }
                    if (stream != null) {
                        $closeResource((Throwable) null, stream);
                    }
                } catch (IOException | DirectoryIteratorException e) {
                    Log.w(TAG, e.toString());
                    ret = false;
                } catch (Throwable th) {
                    if (stream != null) {
                        $closeResource(r5, stream);
                    }
                    throw th;
                }
            }
        }
        return ret;
    }

    public synchronized int beginSendObject(MtpObject parent, String name, int format) {
        if (sDebug) {
            String str = TAG;
            Log.v(str, "beginSendObject " + name);
        }
        if (!parent.isDir()) {
            return -1;
        }
        if (parent.isRoot() && this.mSubdirectories != null && !this.mSubdirectories.contains(name)) {
            return -1;
        }
        getChildren(parent);
        MtpObject obj = addObjectToCache(parent, name, format == 12289);
        if (obj == null) {
            return -1;
        }
        obj.setState(MtpObjectState.FROZEN);
        obj.setOperation(MtpOperation.ADD);
        return obj.getId();
    }

    public synchronized boolean endSendObject(MtpObject obj, boolean succeeded) {
        if (sDebug) {
            String str = TAG;
            Log.v(str, "endSendObject " + succeeded);
        }
        return generalEndAddObject(obj, succeeded, true);
    }

    public synchronized boolean beginRenameObject(MtpObject obj, String newName) {
        if (sDebug) {
            String str = TAG;
            Log.v(str, "beginRenameObject " + obj.getName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + newName);
        }
        if (obj.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(obj)) {
            return false;
        }
        if (obj.getParent().getChild(newName) != null) {
            return false;
        }
        MtpObject oldObj = obj.copy(false);
        obj.setName(newName);
        obj.getParent().addChild(obj);
        oldObj.getParent().addChild(oldObj);
        return generalBeginRenameObject(oldObj, obj);
    }

    public synchronized boolean endRenameObject(MtpObject obj, String oldName, boolean success) {
        MtpObject oldObj;
        if (sDebug) {
            String str = TAG;
            Log.v(str, "endRenameObject " + success);
        }
        MtpObject parent = obj.getParent();
        oldObj = parent.getChild(oldName);
        if (!success) {
            MtpObject temp = oldObj;
            MtpObjectState oldState = oldObj.getState();
            temp.setName(obj.getName());
            temp.setState(obj.getState());
            oldObj = obj;
            oldObj.setName(oldName);
            oldObj.setState(oldState);
            obj = temp;
            parent.addChild(obj);
            parent.addChild(oldObj);
        }
        return generalEndRenameObject(oldObj, obj, success);
    }

    public synchronized boolean beginRemoveObject(MtpObject obj) {
        if (sDebug) {
            String str = TAG;
            Log.v(str, "beginRemoveObject " + obj.getName());
        }
        return !obj.isRoot() && !isSpecialSubDir(obj) && generalBeginRemoveObject(obj, MtpOperation.DELETE);
    }

    public synchronized boolean endRemoveObject(MtpObject obj, boolean success) {
        boolean z;
        if (sDebug) {
            Log.v(TAG, "endRemoveObject " + success);
        }
        boolean ret = true;
        z = false;
        if (obj.isDir()) {
            Iterator it = new ArrayList(obj.getChildren()).iterator();
            while (it.hasNext()) {
                MtpObject child = (MtpObject) it.next();
                if (child.getOperation() == MtpOperation.DELETE) {
                    ret = endRemoveObject(child, success) && ret;
                }
            }
        }
        if (generalEndRemoveObject(obj, success, true) && ret) {
            z = true;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean beginMoveObject(android.mtp.MtpStorageManager.MtpObject r5, android.mtp.MtpStorageManager.MtpObject r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = sDebug     // Catch:{ all -> 0x0081 }
            if (r0 == 0) goto L_0x001f
            java.lang.String r0 = TAG     // Catch:{ all -> 0x0081 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0081 }
            r1.<init>()     // Catch:{ all -> 0x0081 }
            java.lang.String r2 = "beginMoveObject "
            r1.append(r2)     // Catch:{ all -> 0x0081 }
            java.nio.file.Path r2 = r6.getPath()     // Catch:{ all -> 0x0081 }
            r1.append(r2)     // Catch:{ all -> 0x0081 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0081 }
            android.util.Log.v(r0, r1)     // Catch:{ all -> 0x0081 }
        L_0x001f:
            boolean r0 = r5.isRoot()     // Catch:{ all -> 0x0081 }
            r1 = 0
            if (r0 == 0) goto L_0x0028
            monitor-exit(r4)
            return r1
        L_0x0028:
            boolean r0 = r4.isSpecialSubDir(r5)     // Catch:{ all -> 0x0081 }
            if (r0 == 0) goto L_0x0030
            monitor-exit(r4)
            return r1
        L_0x0030:
            r4.getChildren(r6)     // Catch:{ all -> 0x0081 }
            java.lang.String r0 = r5.getName()     // Catch:{ all -> 0x0081 }
            android.mtp.MtpStorageManager$MtpObject r0 = r6.getChild(r0)     // Catch:{ all -> 0x0081 }
            if (r0 == 0) goto L_0x003f
            monitor-exit(r4)
            return r1
        L_0x003f:
            int r0 = r5.getStorageId()     // Catch:{ all -> 0x0081 }
            int r2 = r6.getStorageId()     // Catch:{ all -> 0x0081 }
            if (r0 == r2) goto L_0x0066
            r0 = 1
            android.mtp.MtpStorageManager$MtpObject r2 = r5.copy(r0)     // Catch:{ all -> 0x0081 }
            r2.setParent(r6)     // Catch:{ all -> 0x0081 }
            r6.addChild(r2)     // Catch:{ all -> 0x0081 }
            android.mtp.MtpStorageManager$MtpOperation r3 = android.mtp.MtpStorageManager.MtpOperation.RENAME     // Catch:{ all -> 0x0081 }
            boolean r3 = r4.generalBeginRemoveObject(r5, r3)     // Catch:{ all -> 0x0081 }
            if (r3 == 0) goto L_0x0063
            boolean r3 = r4.generalBeginCopyObject(r2, r1)     // Catch:{ all -> 0x0081 }
            if (r3 == 0) goto L_0x0063
            goto L_0x0064
        L_0x0063:
            r0 = r1
        L_0x0064:
            monitor-exit(r4)
            return r0
        L_0x0066:
            android.mtp.MtpStorageManager$MtpObject r0 = r5.copy(r1)     // Catch:{ all -> 0x0081 }
            r5.setParent(r6)     // Catch:{ all -> 0x0081 }
            android.mtp.MtpStorageManager$MtpObject r1 = r0.getParent()     // Catch:{ all -> 0x0081 }
            r1.addChild(r0)     // Catch:{ all -> 0x0081 }
            android.mtp.MtpStorageManager$MtpObject r1 = r5.getParent()     // Catch:{ all -> 0x0081 }
            r1.addChild(r5)     // Catch:{ all -> 0x0081 }
            boolean r1 = r4.generalBeginRenameObject(r0, r5)     // Catch:{ all -> 0x0081 }
            monitor-exit(r4)
            return r1
        L_0x0081:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.beginMoveObject(android.mtp.MtpStorageManager$MtpObject, android.mtp.MtpStorageManager$MtpObject):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean endMoveObject(android.mtp.MtpStorageManager.MtpObject r7, android.mtp.MtpStorageManager.MtpObject r8, java.lang.String r9, boolean r10) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = sDebug     // Catch:{ all -> 0x0073 }
            if (r0 == 0) goto L_0x001b
            java.lang.String r0 = TAG     // Catch:{ all -> 0x0073 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
            r1.<init>()     // Catch:{ all -> 0x0073 }
            java.lang.String r2 = "endMoveObject "
            r1.append(r2)     // Catch:{ all -> 0x0073 }
            r1.append(r10)     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0073 }
            android.util.Log.v(r0, r1)     // Catch:{ all -> 0x0073 }
        L_0x001b:
            android.mtp.MtpStorageManager$MtpObject r0 = r7.getChild(r9)     // Catch:{ all -> 0x0073 }
            android.mtp.MtpStorageManager$MtpObject r1 = r8.getChild(r9)     // Catch:{ all -> 0x0073 }
            r2 = 0
            if (r0 == 0) goto L_0x0071
            if (r1 != 0) goto L_0x0029
            goto L_0x0071
        L_0x0029:
            int r3 = r7.getStorageId()     // Catch:{ all -> 0x0073 }
            int r4 = r1.getStorageId()     // Catch:{ all -> 0x0073 }
            if (r3 == r4) goto L_0x0044
            boolean r3 = r6.endRemoveObject(r0, r10)     // Catch:{ all -> 0x0073 }
            r4 = 1
            boolean r5 = r6.generalEndCopyObject(r1, r10, r4)     // Catch:{ all -> 0x0073 }
            if (r5 == 0) goto L_0x0042
            if (r3 == 0) goto L_0x0042
            r2 = r4
        L_0x0042:
            monitor-exit(r6)
            return r2
        L_0x0044:
            if (r10 != 0) goto L_0x006b
            r2 = r0
            android.mtp.MtpStorageManager$MtpObjectState r3 = r0.getState()     // Catch:{ all -> 0x0073 }
            android.mtp.MtpStorageManager$MtpObject r4 = r1.getParent()     // Catch:{ all -> 0x0073 }
            r2.setParent(r4)     // Catch:{ all -> 0x0073 }
            android.mtp.MtpStorageManager$MtpObjectState r4 = r1.getState()     // Catch:{ all -> 0x0073 }
            r2.setState(r4)     // Catch:{ all -> 0x0073 }
            r0 = r1
            r0.setParent(r7)     // Catch:{ all -> 0x0073 }
            r0.setState(r3)     // Catch:{ all -> 0x0073 }
            r1 = r2
            android.mtp.MtpStorageManager$MtpObject r4 = r1.getParent()     // Catch:{ all -> 0x0073 }
            r4.addChild(r1)     // Catch:{ all -> 0x0073 }
            r7.addChild(r0)     // Catch:{ all -> 0x0073 }
        L_0x006b:
            boolean r2 = r6.generalEndRenameObject(r0, r1, r10)     // Catch:{ all -> 0x0073 }
            monitor-exit(r6)
            return r2
        L_0x0071:
            monitor-exit(r6)
            return r2
        L_0x0073:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpStorageManager.endMoveObject(android.mtp.MtpStorageManager$MtpObject, android.mtp.MtpStorageManager$MtpObject, java.lang.String, boolean):boolean");
    }

    public synchronized int beginCopyObject(MtpObject object, MtpObject newParent) {
        if (sDebug) {
            String str = TAG;
            Log.v(str, "beginCopyObject " + object.getName() + " to " + newParent.getPath());
        }
        String name = object.getName();
        if (!newParent.isDir()) {
            return -1;
        }
        if (newParent.isRoot() && this.mSubdirectories != null && !this.mSubdirectories.contains(name)) {
            return -1;
        }
        getChildren(newParent);
        if (newParent.getChild(name) != null) {
            return -1;
        }
        MtpObject newObj = object.copy(object.isDir());
        newParent.addChild(newObj);
        newObj.setParent(newParent);
        if (!generalBeginCopyObject(newObj, true)) {
            return -1;
        }
        return newObj.getId();
    }

    public synchronized boolean endCopyObject(MtpObject object, boolean success) {
        if (sDebug) {
            String str = TAG;
            Log.v(str, "endCopyObject " + object.getName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + success);
        }
        return generalEndCopyObject(object, success, false);
    }

    private synchronized boolean generalEndAddObject(MtpObject obj, boolean succeeded, boolean removeGlobal) {
        int i = AnonymousClass1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[obj.getState().ordinal()];
        if (i != 5) {
            switch (i) {
                case 1:
                    if (succeeded) {
                        obj.setState(MtpObjectState.FROZEN_ONESHOT_ADD);
                        break;
                    } else if (!removeObjectFromCache(obj, removeGlobal, false)) {
                        return false;
                    }
                    break;
                case 2:
                    if (removeObjectFromCache(obj, removeGlobal, false)) {
                        if (succeeded) {
                            this.mMtpNotifier.sendObjectRemoved(obj.getId());
                            break;
                        }
                    } else {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        } else {
            obj.setState(MtpObjectState.NORMAL);
            if (!succeeded) {
                MtpObject parent = obj.getParent();
                if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                handleAddedObject(parent, obj.getName(), obj.isDir());
            }
        }
        return true;
    }

    private synchronized boolean generalEndRemoveObject(MtpObject obj, boolean success, boolean removeGlobal) {
        int i = AnonymousClass1.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[obj.getState().ordinal()];
        if (i != 5) {
            switch (i) {
                case 1:
                    if (!success) {
                        obj.setState(MtpObjectState.NORMAL);
                        break;
                    } else {
                        obj.setState(MtpObjectState.FROZEN_ONESHOT_DEL);
                        break;
                    }
                case 2:
                    if (removeObjectFromCache(obj, removeGlobal, false)) {
                        if (!success) {
                            this.mMtpNotifier.sendObjectRemoved(obj.getId());
                            break;
                        }
                    } else {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        } else {
            obj.setState(MtpObjectState.NORMAL);
            if (success) {
                MtpObject parent = obj.getParent();
                if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                handleAddedObject(parent, obj.getName(), obj.isDir());
            }
        }
        return true;
    }

    private synchronized boolean generalBeginRenameObject(MtpObject fromObj, MtpObject toObj) {
        fromObj.setState(MtpObjectState.FROZEN);
        toObj.setState(MtpObjectState.FROZEN);
        fromObj.setOperation(MtpOperation.RENAME);
        toObj.setOperation(MtpOperation.RENAME);
        return true;
    }

    private synchronized boolean generalEndRenameObject(MtpObject fromObj, MtpObject toObj, boolean success) {
        return generalEndAddObject(toObj, success, success) && generalEndRemoveObject(fromObj, success, success ^ true);
    }

    private synchronized boolean generalBeginRemoveObject(MtpObject obj, MtpOperation op) {
        obj.setState(MtpObjectState.FROZEN);
        obj.setOperation(op);
        if (obj.isDir()) {
            for (MtpObject child : obj.getChildren()) {
                generalBeginRemoveObject(child, op);
            }
        }
        return true;
    }

    private synchronized boolean generalBeginCopyObject(MtpObject obj, boolean newId) {
        obj.setState(MtpObjectState.FROZEN);
        obj.setOperation(MtpOperation.COPY);
        if (newId) {
            obj.setId(getNextObjectId());
            this.mObjects.put(Integer.valueOf(obj.getId()), obj);
        }
        if (obj.isDir()) {
            for (MtpObject child : obj.getChildren()) {
                if (!generalBeginCopyObject(child, newId)) {
                    return false;
                }
            }
        }
        return true;
    }

    private synchronized boolean generalEndCopyObject(MtpObject obj, boolean success, boolean addGlobal) {
        boolean ret;
        boolean z;
        if (success && addGlobal) {
            this.mObjects.put(Integer.valueOf(obj.getId()), obj);
        }
        boolean ret2 = true;
        ret = false;
        if (obj.isDir()) {
            Iterator it = new ArrayList(obj.getChildren()).iterator();
            while (it.hasNext()) {
                MtpObject child = (MtpObject) it.next();
                if (child.getOperation() == MtpOperation.COPY) {
                    ret2 = generalEndCopyObject(child, success, addGlobal) && ret2;
                }
            }
        }
        if (!success) {
            if (addGlobal) {
                z = false;
                if (generalEndAddObject(obj, success, z) && ret2) {
                    ret = true;
                }
            }
        }
        z = true;
        ret = true;
        return ret;
    }
}
