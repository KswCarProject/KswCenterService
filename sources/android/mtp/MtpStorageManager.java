package android.mtp;

import android.media.MediaFile;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.FileObserver;
import android.p007os.storage.StorageVolume;
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

/* loaded from: classes3.dex */
public class MtpStorageManager {
    private static final int IN_IGNORED = 32768;
    private static final int IN_ISDIR = 1073741824;
    private static final int IN_ONLYDIR = 16777216;
    private static final int IN_Q_OVERFLOW = 16384;
    private static final String TAG = MtpStorageManager.class.getSimpleName();
    public static boolean sDebug = false;
    private MtpNotifier mMtpNotifier;
    private Set<String> mSubdirectories;
    private HashMap<Integer, MtpObject> mObjects = new HashMap<>();
    private HashMap<Integer, MtpObject> mRoots = new HashMap<>();
    private int mNextObjectId = 1;
    private int mNextStorageId = 1;
    private volatile boolean mCheckConsistency = false;
    private Thread mConsistencyThread = new Thread(new Runnable() { // from class: android.mtp.-$$Lambda$MtpStorageManager$HocvlaKIXOtuA3p8uOP9PA7UJtw
        @Override // java.lang.Runnable
        public final void run() {
            MtpStorageManager.lambda$new$0(MtpStorageManager.this);
        }
    });

    /* loaded from: classes3.dex */
    public static abstract class MtpNotifier {
        public abstract void sendObjectAdded(int i);

        public abstract void sendObjectInfoChanged(int i);

        public abstract void sendObjectRemoved(int i);
    }

    /* loaded from: classes3.dex */
    private enum MtpObjectState {
        NORMAL,
        FROZEN,
        FROZEN_ADDED,
        FROZEN_REMOVED,
        FROZEN_ONESHOT_ADD,
        FROZEN_ONESHOT_DEL
    }

    /* loaded from: classes3.dex */
    private enum MtpOperation {
        NONE,
        ADD,
        RENAME,
        COPY,
        DELETE
    }

    /* loaded from: classes3.dex */
    private class MtpObjectObserver extends FileObserver {
        MtpObject mObject;

        MtpObjectObserver(MtpObject object) {
            super(object.getPath().toString(), 16778184);
            this.mObject = object;
        }

        /* JADX WARN: Code restructure failed: missing block: B:36:0x00cf, code lost:
            r2 = android.mtp.MtpStorageManager.TAG;
            android.util.Log.m64w(r2, "Object was null in event " + r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00e8, code lost:
            return;
         */
        @Override // android.p007os.FileObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onEvent(int event, String path) {
            synchronized (MtpStorageManager.this) {
                if ((event & 16384) != 0) {
                    try {
                        Log.m70e(MtpStorageManager.TAG, "Received Inotify overflow event!");
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                MtpObject obj = this.mObject.getChild(path);
                if ((event & 128) == 0 && (event & 256) == 0) {
                    if ((event & 64) == 0 && (event & 512) == 0) {
                        if ((32768 & event) != 0) {
                            if (MtpStorageManager.sDebug) {
                                String str = MtpStorageManager.TAG;
                                Log.m68i(str, "inotify for " + this.mObject.getPath() + " deleted");
                            }
                            if (this.mObject.mObserver != null) {
                                this.mObject.mObserver.stopWatching();
                            }
                            this.mObject.mObserver = null;
                        } else if ((event & 8) == 0) {
                            String str2 = MtpStorageManager.TAG;
                            Log.m64w(str2, "Got unrecognized event " + path + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + event);
                        } else {
                            if (MtpStorageManager.sDebug) {
                                String str3 = MtpStorageManager.TAG;
                                Log.m68i(str3, "inotify for " + this.mObject.getPath() + " CLOSE_WRITE: " + path);
                            }
                            MtpStorageManager.this.handleChangedObject(this.mObject, path);
                        }
                    }
                    if (MtpStorageManager.sDebug) {
                        String str4 = MtpStorageManager.TAG;
                        Log.m68i(str4, "Got inotify removed event for " + path + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + event);
                    }
                    MtpStorageManager.this.handleRemovedObject(obj);
                }
                if (MtpStorageManager.sDebug) {
                    String str5 = MtpStorageManager.TAG;
                    Log.m68i(str5, "Got inotify added event for " + path + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + event);
                }
                MtpStorageManager.this.handleAddedObject(this.mObject, path, (1073741824 & event) != 0);
            }
        }

        @Override // android.p007os.FileObserver
        public void finalize() {
        }
    }

    /* loaded from: classes3.dex */
    public static class MtpObject {
        private HashMap<String, MtpObject> mChildren;
        private int mId;
        private boolean mIsDir;
        private String mName;
        private MtpObject mParent;
        private MtpStorage mStorage;
        private FileObserver mObserver = null;
        private boolean mVisited = false;
        private MtpObjectState mState = MtpObjectState.NORMAL;
        private MtpOperation mOp = MtpOperation.NONE;

        MtpObject(String name, int id, MtpStorage storage, MtpObject parent, boolean isDir) {
            this.mId = id;
            this.mName = name;
            this.mStorage = (MtpStorage) Preconditions.checkNotNull(storage);
            this.mParent = parent;
            this.mIsDir = isDir;
            this.mChildren = this.mIsDir ? new HashMap<>() : null;
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
            return MediaFile.getFormatCode(this.mName, null);
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
                return 0L;
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

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String name) {
            this.mName = name;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setId(int id) {
            this.mId = id;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isVisited() {
            return this.mVisited;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParent(MtpObject parent) {
            this.mParent = parent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDir(boolean dir) {
            if (dir != this.mIsDir) {
                this.mIsDir = dir;
                this.mChildren = this.mIsDir ? new HashMap<>() : null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVisited(boolean visited) {
            this.mVisited = visited;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObjectState getState() {
            return this.mState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(MtpObjectState state) {
            this.mState = state;
            if (this.mState == MtpObjectState.NORMAL) {
                this.mOp = MtpOperation.NONE;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpOperation getOperation() {
            return this.mOp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOperation(MtpOperation op) {
            this.mOp = op;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FileObserver getObserver() {
            return this.mObserver;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setObserver(FileObserver observer) {
            this.mObserver = observer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChild(MtpObject child) {
            this.mChildren.put(child.getName(), child);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public MtpObject getChild(String name) {
            return this.mChildren.get(name);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Collection<MtpObject> getChildren() {
            return this.mChildren.values();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean exists() {
            return getPath().toFile().exists();
        }

        /* JADX INFO: Access modifiers changed from: private */
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
                Thread.sleep(15000L);
                if (mtpStorageManager.checkConsistency()) {
                    Log.m66v(TAG, "Cache is consistent");
                } else {
                    Log.m64w(TAG, "Cache is not consistent");
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
                obj.setObserver(null);
            }
        }
        for (MtpObject obj2 : this.mRoots.values()) {
            if (obj2.getObserver() != null) {
                obj2.getObserver().stopWatching();
                obj2.setObserver(null);
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
        MtpObject root = new MtpObject(storage.getPath(), storageId, storage, null, true);
        this.mRoots.put(Integer.valueOf(storageId), root);
        return storage;
    }

    public synchronized void removeMtpStorage(MtpStorage storage) {
        removeObjectFromCache(getStorageRoot(storage.getStorageId()), true, true);
    }

    private synchronized boolean isSpecialSubDir(MtpObject obj) {
        boolean z;
        if (obj.getParent().isRoot() && this.mSubdirectories != null) {
            z = this.mSubdirectories.contains(obj.getName()) ? false : true;
        }
        return z;
    }

    public synchronized MtpObject getByPath(String path) {
        String[] split;
        MtpObject obj = null;
        for (MtpObject root : this.mRoots.values()) {
            if (path.startsWith(root.getName())) {
                obj = root;
                path = path.substring(root.getName().length());
            }
        }
        for (String name : path.split("/")) {
            if (obj != null && obj.isDir()) {
                if (!"".equals(name)) {
                    if (!obj.isVisited()) {
                        getChildren(obj);
                    }
                    obj = obj.getChild(name);
                }
            }
            return null;
        }
        return obj;
    }

    public synchronized MtpObject getObject(int id) {
        if (id == 0 || id == -1) {
            Log.m64w(TAG, "Can't get root storages with getObject()");
            return null;
        } else if (!this.mObjects.containsKey(Integer.valueOf(id))) {
            String str = TAG;
            Log.m64w(str, "Id " + id + " doesn't exist");
            return null;
        } else {
            return this.mObjects.get(Integer.valueOf(id));
        }
    }

    public MtpObject getStorageRoot(int id) {
        if (!this.mRoots.containsKey(Integer.valueOf(id))) {
            String str = TAG;
            Log.m64w(str, "StorageId " + id + " doesn't exist");
            return null;
        }
        return this.mRoots.get(Integer.valueOf(id));
    }

    private int getNextObjectId() {
        int ret = this.mNextObjectId;
        this.mNextObjectId = (int) (this.mNextObjectId + 1);
        return ret;
    }

    private int getNextStorageId() {
        int i = this.mNextStorageId;
        this.mNextStorageId = i + 1;
        return i;
    }

    public synchronized List<MtpObject> getObjects(int parent, int format, int storageId) {
        boolean recursive = parent == 0;
        ArrayList<MtpObject> objs = new ArrayList<>();
        boolean ret = true;
        if (parent == -1) {
            parent = 0;
        }
        if (storageId == -1 && parent == 0) {
            for (MtpObject root : this.mRoots.values()) {
                ret &= getObjects(objs, root, format, recursive);
            }
            return ret ? objs : null;
        }
        MtpObject obj = parent == 0 ? getStorageRoot(storageId) : getObject(parent);
        if (obj == null) {
            return null;
        }
        boolean ret2 = getObjects(objs, obj, format, recursive);
        return ret2 ? objs : null;
    }

    private synchronized boolean getObjects(List<MtpObject> toAdd, MtpObject parent, int format, boolean rec) {
        Collection<MtpObject> children = getChildren(parent);
        if (children == null) {
            return false;
        }
        for (MtpObject o : children) {
            if (format == 0 || o.getFormat() == format) {
                toAdd.add(o);
            }
        }
        boolean ret = true;
        if (rec) {
            for (MtpObject o2 : children) {
                if (o2.isDir()) {
                    ret &= getObjects(toAdd, o2, format, true);
                }
            }
        }
        return ret;
    }

    private synchronized Collection<MtpObject> getChildren(MtpObject object) {
        if (object != null) {
            if (object.isDir()) {
                if (!object.isVisited()) {
                    Path dir = object.getPath();
                    if (object.getObserver() != null) {
                        Log.m70e(TAG, "Observer is not null!");
                    }
                    object.setObserver(new MtpObjectObserver(object));
                    object.getObserver().startWatching();
                    try {
                        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
                        try {
                            for (Path file : stream) {
                                addObjectToCache(object, file.getFileName().toString(), file.toFile().isDirectory());
                            }
                            if (stream != null) {
                                $closeResource(null, stream);
                            }
                            object.setVisited(true);
                        } finally {
                        }
                    } catch (IOException | DirectoryIteratorException e) {
                        Log.m70e(TAG, e.toString());
                        object.getObserver().stopWatching();
                        object.setObserver(null);
                        return null;
                    }
                }
                return object.getChildren();
            }
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Can't find children of ");
        sb.append(object == null ? "null" : Integer.valueOf(object.getId()));
        Log.m64w(str, sb.toString());
        return null;
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    private synchronized MtpObject addObjectToCache(MtpObject parent, String newName, boolean isDir) {
        if (parent.isRoot() || getObject(parent.getId()) == parent) {
            if (parent.getChild(newName) != null) {
                return null;
            }
            if (this.mSubdirectories == null || !parent.isRoot() || this.mSubdirectories.contains(newName)) {
                MtpObject obj = new MtpObject(newName, getNextObjectId(), parent.mStorage, parent, isDir);
                this.mObjects.put(Integer.valueOf(obj.getId()), obj);
                parent.addChild(obj);
                return obj;
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0045 A[Catch: all -> 0x00d5, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:11:0x0021, B:13:0x0025, B:14:0x003f, B:16:0x0045, B:30:0x0076, B:32:0x007a, B:33:0x0094, B:35:0x009a, B:36:0x00a5, B:39:0x00ad, B:40:0x00ba, B:42:0x00c0, B:23:0x005e), top: B:53:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009a A[Catch: all -> 0x00d5, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:11:0x0021, B:13:0x0025, B:14:0x003f, B:16:0x0045, B:30:0x0076, B:32:0x007a, B:33:0x0094, B:35:0x009a, B:36:0x00a5, B:39:0x00ad, B:40:0x00ba, B:42:0x00c0, B:23:0x005e), top: B:53:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c0 A[Catch: all -> 0x00d5, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:11:0x0021, B:13:0x0025, B:14:0x003f, B:16:0x0045, B:30:0x0076, B:32:0x007a, B:33:0x0094, B:35:0x009a, B:36:0x00a5, B:39:0x00ad, B:40:0x00ba, B:42:0x00c0, B:23:0x005e), top: B:53:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized boolean removeObjectFromCache(MtpObject removed, boolean removeGlobal, boolean recursive) {
        boolean ret;
        if (!removed.isRoot() && !removed.getParent().mChildren.remove(removed.getName(), removed)) {
            ret = false;
            if (!ret && sDebug) {
                Log.m64w(TAG, "Failed to remove from parent " + removed.getPath());
            }
            if (!removed.isRoot()) {
                ret = this.mRoots.remove(Integer.valueOf(removed.getId()), removed) && ret;
            } else if (removeGlobal) {
                ret = this.mObjects.remove(Integer.valueOf(removed.getId()), removed) && ret;
            }
            if (!ret && sDebug) {
                Log.m64w(TAG, "Failed to remove from global cache " + removed.getPath());
            }
            if (removed.getObserver() != null) {
                removed.getObserver().stopWatching();
                removed.setObserver(null);
            }
            if (removed.isDir() && recursive) {
                Collection<MtpObject> children = new ArrayList<>(removed.getChildren());
                for (MtpObject child : children) {
                    ret = removeObjectFromCache(child, removeGlobal, true) && ret;
                }
            }
        }
        ret = true;
        if (!ret) {
            Log.m64w(TAG, "Failed to remove from parent " + removed.getPath());
        }
        if (!removed.isRoot()) {
        }
        if (!ret) {
            Log.m64w(TAG, "Failed to remove from global cache " + removed.getPath());
        }
        if (removed.getObserver() != null) {
        }
        if (removed.isDir()) {
            Collection<MtpObject> children2 = new ArrayList<>(removed.getChildren());
            while (r4.hasNext()) {
            }
        }
        return ret;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleAddedObject(MtpObject parent, String path, boolean isDir) {
        MtpOperation op = MtpOperation.NONE;
        MtpObject obj = parent.getChild(path);
        if (obj != null) {
            MtpObjectState state = obj.getState();
            op = obj.getOperation();
            if (obj.isDir() != isDir && state != MtpObjectState.FROZEN_REMOVED) {
                String str = TAG;
                Log.m72d(str, "Inconsistent directory info! " + obj.getPath());
            }
            obj.setDir(isDir);
            switch (state) {
                case FROZEN:
                case FROZEN_REMOVED:
                    obj.setState(MtpObjectState.FROZEN_ADDED);
                    break;
                case FROZEN_ONESHOT_ADD:
                    obj.setState(MtpObjectState.NORMAL);
                    break;
                case NORMAL:
                case FROZEN_ADDED:
                    return;
                default:
                    String str2 = TAG;
                    Log.m64w(str2, "Unexpected state in add " + path + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + state);
                    break;
            }
            if (sDebug) {
                String str3 = TAG;
                Log.m68i(str3, state + " transitioned to " + obj.getState() + " in op " + op);
            }
        } else {
            obj = addObjectToCache(parent, path, isDir);
            if (obj != null) {
                this.mMtpNotifier.sendObjectAdded(obj.getId());
            } else {
                if (sDebug) {
                    String str4 = TAG;
                    Log.m64w(str4, "object " + path + " already exists");
                }
                return;
            }
        }
        if (isDir) {
            if (op == MtpOperation.RENAME) {
                return;
            }
            if (op == MtpOperation.COPY && !obj.isVisited()) {
                return;
            }
            if (obj.getObserver() == null) {
                obj.setObserver(new MtpObjectObserver(obj));
                obj.getObserver().startWatching();
                obj.setVisited(true);
                try {
                    DirectoryStream<Path> stream = Files.newDirectoryStream(obj.getPath());
                    try {
                        for (Path file : stream) {
                            if (sDebug) {
                                String str5 = TAG;
                                Log.m68i(str5, "Manually handling event for " + file.getFileName().toString());
                            }
                            handleAddedObject(obj, file.getFileName().toString(), file.toFile().isDirectory());
                        }
                        if (stream != null) {
                            $closeResource(null, stream);
                        }
                    } finally {
                    }
                } catch (IOException | DirectoryIteratorException e) {
                    Log.m70e(TAG, e.toString());
                    obj.getObserver().stopWatching();
                    obj.setObserver(null);
                }
            } else {
                Log.m70e(TAG, "Observer is not null!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleRemovedObject(MtpObject obj) {
        MtpObjectState state = obj.getState();
        MtpOperation op = obj.getOperation();
        int i = C12531.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[state.ordinal()];
        boolean z = true;
        if (i == 1) {
            obj.setState(MtpObjectState.FROZEN_REMOVED);
        } else {
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
                    Log.m70e(TAG, "Got unexpected object remove for " + obj.getName());
                    break;
            }
        }
        if (sDebug) {
            Log.m68i(TAG, state + " transitioned to " + obj.getState() + " in op " + op);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleChangedObject(MtpObject parent, String path) {
        MtpOperation mtpOperation = MtpOperation.NONE;
        MtpObject obj = parent.getChild(path);
        if (obj != null) {
            if (!obj.isDir() && obj.getSize() > 0) {
                obj.getState();
                obj.getOperation();
                this.mMtpNotifier.sendObjectInfoChanged(obj.getId());
                if (sDebug) {
                    String str = TAG;
                    Log.m72d(str, "sendObjectInfoChanged: id=" + obj.getId() + ",size=" + obj.getSize());
                }
            }
        } else if (sDebug) {
            String str2 = TAG;
            Log.m64w(str2, "object " + path + " null");
        }
    }

    public void flushEvents() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void dump() {
        for (Integer num : this.mObjects.keySet()) {
            int key = num.intValue();
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
            Log.m68i(str, sb.toString());
        }
    }

    public synchronized boolean checkConsistency() {
        boolean ret;
        List<MtpObject> objs = new ArrayList<>();
        objs.addAll(this.mRoots.values());
        objs.addAll(this.mObjects.values());
        ret = true;
        for (MtpObject obj : objs) {
            if (!obj.exists()) {
                String str = TAG;
                Log.m64w(str, "Object doesn't exist " + obj.getPath() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + obj.getId());
                ret = false;
            }
            if (obj.getState() != MtpObjectState.NORMAL) {
                String str2 = TAG;
                Log.m64w(str2, "Object " + obj.getPath() + " in state " + obj.getState());
                ret = false;
            }
            if (obj.getOperation() != MtpOperation.NONE) {
                String str3 = TAG;
                Log.m64w(str3, "Object " + obj.getPath() + " in operation " + obj.getOperation());
                ret = false;
            }
            if (!obj.isRoot() && this.mObjects.get(Integer.valueOf(obj.getId())) != obj) {
                String str4 = TAG;
                Log.m64w(str4, "Object " + obj.getPath() + " is not in map correctly");
                ret = false;
            }
            if (obj.getParent() != null) {
                if (obj.getParent().isRoot() && obj.getParent() != this.mRoots.get(Integer.valueOf(obj.getParent().getId()))) {
                    String str5 = TAG;
                    Log.m64w(str5, "Root parent is not in root mapping " + obj.getPath());
                    ret = false;
                }
                if (!obj.getParent().isRoot() && obj.getParent() != this.mObjects.get(Integer.valueOf(obj.getParent().getId()))) {
                    String str6 = TAG;
                    Log.m64w(str6, "Parent is not in object mapping " + obj.getPath());
                    ret = false;
                }
                if (obj.getParent().getChild(obj.getName()) != obj) {
                    String str7 = TAG;
                    Log.m64w(str7, "Child does not exist in parent " + obj.getPath());
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
                    Log.m64w(str8, sb.toString());
                    ret = false;
                }
                if (!obj.isVisited() && obj.getChildren().size() > 0) {
                    String str9 = TAG;
                    Log.m64w(str9, obj.getPath() + " is not visited but has children");
                    ret = false;
                }
                try {
                    DirectoryStream<Path> stream = Files.newDirectoryStream(obj.getPath());
                    try {
                        Set<String> files = new HashSet<>();
                        for (Path file : stream) {
                            if (obj.isVisited() && obj.getChild(file.getFileName().toString()) == null && (this.mSubdirectories == null || !obj.isRoot() || this.mSubdirectories.contains(file.getFileName().toString()))) {
                                String str10 = TAG;
                                Log.m64w(str10, "File exists in fs but not in children " + file);
                                ret = false;
                            }
                            files.add(file.toString());
                        }
                        for (MtpObject child : obj.getChildren()) {
                            if (!files.contains(child.getPath().toString())) {
                                String str11 = TAG;
                                Log.m64w(str11, "File in children doesn't exist in fs " + child.getPath());
                                ret = false;
                            }
                            if (child != this.mObjects.get(Integer.valueOf(child.getId()))) {
                                String str12 = TAG;
                                Log.m64w(str12, "Child is not in object map " + child.getPath());
                                ret = false;
                            }
                        }
                        if (stream != null) {
                            $closeResource(null, stream);
                        }
                    } catch (Throwable th) {
                        try {
                            throw th;
                            break;
                        } catch (Throwable th2) {
                            if (stream != null) {
                                $closeResource(th, stream);
                            }
                            throw th2;
                            break;
                        }
                    }
                } catch (IOException | DirectoryIteratorException e) {
                    Log.m64w(TAG, e.toString());
                    ret = false;
                }
            }
        }
        return ret;
    }

    public synchronized int beginSendObject(MtpObject parent, String name, int format) {
        if (sDebug) {
            String str = TAG;
            Log.m66v(str, "beginSendObject " + name);
        }
        if (parent.isDir()) {
            if (!parent.isRoot() || this.mSubdirectories == null || this.mSubdirectories.contains(name)) {
                getChildren(parent);
                MtpObject obj = addObjectToCache(parent, name, format == 12289);
                if (obj == null) {
                    return -1;
                }
                obj.setState(MtpObjectState.FROZEN);
                obj.setOperation(MtpOperation.ADD);
                return obj.getId();
            }
            return -1;
        }
        return -1;
    }

    public synchronized boolean endSendObject(MtpObject obj, boolean succeeded) {
        if (sDebug) {
            String str = TAG;
            Log.m66v(str, "endSendObject " + succeeded);
        }
        return generalEndAddObject(obj, succeeded, true);
    }

    public synchronized boolean beginRenameObject(MtpObject obj, String newName) {
        if (sDebug) {
            String str = TAG;
            Log.m66v(str, "beginRenameObject " + obj.getName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + newName);
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
            Log.m66v(str, "endRenameObject " + success);
        }
        MtpObject parent = obj.getParent();
        oldObj = parent.getChild(oldName);
        if (!success) {
            MtpObjectState oldState = oldObj.getState();
            oldObj.setName(obj.getName());
            oldObj.setState(obj.getState());
            oldObj = obj;
            oldObj.setName(oldName);
            oldObj.setState(oldState);
            obj = oldObj;
            parent.addChild(obj);
            parent.addChild(oldObj);
        }
        return generalEndRenameObject(oldObj, obj, success);
    }

    public synchronized boolean beginRemoveObject(MtpObject obj) {
        boolean z;
        if (sDebug) {
            String str = TAG;
            Log.m66v(str, "beginRemoveObject " + obj.getName());
        }
        if (!obj.isRoot() && !isSpecialSubDir(obj)) {
            z = generalBeginRemoveObject(obj, MtpOperation.DELETE);
        }
        return z;
    }

    public synchronized boolean endRemoveObject(MtpObject obj, boolean success) {
        boolean z;
        if (sDebug) {
            Log.m66v(TAG, "endRemoveObject " + success);
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

    public synchronized boolean beginMoveObject(MtpObject obj, MtpObject newParent) {
        if (sDebug) {
            Log.m66v(TAG, "beginMoveObject " + newParent.getPath());
        }
        if (obj.isRoot()) {
            return false;
        }
        if (isSpecialSubDir(obj)) {
            return false;
        }
        getChildren(newParent);
        if (newParent.getChild(obj.getName()) != null) {
            return false;
        }
        if (obj.getStorageId() == newParent.getStorageId()) {
            MtpObject oldObj = obj.copy(false);
            obj.setParent(newParent);
            oldObj.getParent().addChild(oldObj);
            obj.getParent().addChild(obj);
            return generalBeginRenameObject(oldObj, obj);
        }
        boolean z = true;
        MtpObject newObj = obj.copy(true);
        newObj.setParent(newParent);
        newParent.addChild(newObj);
        if (!generalBeginRemoveObject(obj, MtpOperation.RENAME) || !generalBeginCopyObject(newObj, false)) {
            z = false;
        }
        return z;
    }

    public synchronized boolean endMoveObject(MtpObject oldParent, MtpObject newParent, String name, boolean success) {
        if (sDebug) {
            Log.m66v(TAG, "endMoveObject " + success);
        }
        MtpObject oldObj = oldParent.getChild(name);
        MtpObject newObj = newParent.getChild(name);
        boolean z = false;
        if (oldObj != null && newObj != null) {
            if (oldParent.getStorageId() != newObj.getStorageId()) {
                boolean ret = endRemoveObject(oldObj, success);
                if (generalEndCopyObject(newObj, success, true) && ret) {
                    z = true;
                }
                return z;
            }
            if (!success) {
                MtpObjectState oldState = oldObj.getState();
                oldObj.setParent(newObj.getParent());
                oldObj.setState(newObj.getState());
                oldObj = newObj;
                oldObj.setParent(oldParent);
                oldObj.setState(oldState);
                newObj = oldObj;
                newObj.getParent().addChild(newObj);
                oldParent.addChild(oldObj);
            }
            return generalEndRenameObject(oldObj, newObj, success);
        }
        return false;
    }

    public synchronized int beginCopyObject(MtpObject object, MtpObject newParent) {
        if (sDebug) {
            String str = TAG;
            Log.m66v(str, "beginCopyObject " + object.getName() + " to " + newParent.getPath());
        }
        String name = object.getName();
        if (newParent.isDir()) {
            if (!newParent.isRoot() || this.mSubdirectories == null || this.mSubdirectories.contains(name)) {
                getChildren(newParent);
                if (newParent.getChild(name) != null) {
                    return -1;
                }
                MtpObject newObj = object.copy(object.isDir());
                newParent.addChild(newObj);
                newObj.setParent(newParent);
                if (generalBeginCopyObject(newObj, true)) {
                    return newObj.getId();
                }
                return -1;
            }
            return -1;
        }
        return -1;
    }

    public synchronized boolean endCopyObject(MtpObject object, boolean success) {
        if (sDebug) {
            String str = TAG;
            Log.m66v(str, "endCopyObject " + object.getName() + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + success);
        }
        return generalEndCopyObject(object, success, false);
    }

    private synchronized boolean generalEndAddObject(MtpObject obj, boolean succeeded, boolean removeGlobal) {
        int i = C12531.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[obj.getState().ordinal()];
        if (i == 5) {
            obj.setState(MtpObjectState.NORMAL);
            if (!succeeded) {
                MtpObject parent = obj.getParent();
                if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                handleAddedObject(parent, obj.getName(), obj.isDir());
            }
        } else {
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
                    if (!removeObjectFromCache(obj, removeGlobal, false)) {
                        return false;
                    }
                    if (succeeded) {
                        this.mMtpNotifier.sendObjectRemoved(obj.getId());
                        break;
                    }
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private synchronized boolean generalEndRemoveObject(MtpObject obj, boolean success, boolean removeGlobal) {
        int i = C12531.$SwitchMap$android$mtp$MtpStorageManager$MtpObjectState[obj.getState().ordinal()];
        if (i == 5) {
            obj.setState(MtpObjectState.NORMAL);
            if (success) {
                MtpObject parent = obj.getParent();
                if (!removeObjectFromCache(obj, removeGlobal, false)) {
                    return false;
                }
                handleAddedObject(parent, obj.getName(), obj.isDir());
            }
        } else {
            switch (i) {
                case 1:
                    if (success) {
                        obj.setState(MtpObjectState.FROZEN_ONESHOT_DEL);
                        break;
                    } else {
                        obj.setState(MtpObjectState.NORMAL);
                        break;
                    }
                case 2:
                    if (!removeObjectFromCache(obj, removeGlobal, false)) {
                        return false;
                    }
                    if (!success) {
                        this.mMtpNotifier.sendObjectRemoved(obj.getId());
                        break;
                    }
                    break;
                default:
                    return false;
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
        boolean ret;
        ret = generalEndRemoveObject(fromObj, success, !success);
        return generalEndAddObject(toObj, success, success) && ret;
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
        if (!success && addGlobal) {
            z = false;
            if (generalEndAddObject(obj, success, z) && ret2) {
                ret = true;
            }
        }
        z = true;
        if (generalEndAddObject(obj, success, z)) {
            ret = true;
        }
        return ret;
    }
}
