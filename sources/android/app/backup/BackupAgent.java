package android.app.backup;

import android.app.IBackupAgent;
import android.app.QueuedWork;
import android.app.backup.FullBackup;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.p002pm.ApplicationInfo;
import android.p007os.Binder;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.ParcelFileDescriptor;
import android.p007os.Process;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.ArraySet;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class BackupAgent extends ContextWrapper {
    private static final boolean DEBUG = false;
    public static final int FLAG_CLIENT_SIDE_ENCRYPTION_ENABLED = 1;
    public static final int FLAG_DEVICE_TO_DEVICE_TRANSFER = 2;
    public static final int FLAG_FAKE_CLIENT_SIDE_ENCRYPTION_ENABLED = Integer.MIN_VALUE;
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "BackupAgent";
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_EOF = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_SYMLINK = 3;
    private final IBinder mBinder;
    Handler mHandler;
    private UserHandle mUser;

    public abstract void onBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException;

    public abstract void onRestore(BackupDataInput backupDataInput, int i, ParcelFileDescriptor parcelFileDescriptor) throws IOException;

    Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    /* loaded from: classes.dex */
    class SharedPrefsSynchronizer implements Runnable {
        public final CountDownLatch mLatch = new CountDownLatch(1);

        SharedPrefsSynchronizer() {
        }

        @Override // java.lang.Runnable
        public void run() {
            QueuedWork.waitToFinish();
            this.mLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForSharedPrefs() {
        Handler h = getHandler();
        SharedPrefsSynchronizer s = new SharedPrefsSynchronizer();
        h.postAtFrontOfQueue(s);
        try {
            s.mLatch.await();
        } catch (InterruptedException e) {
        }
    }

    public BackupAgent() {
        super(null);
        this.mHandler = null;
        this.mBinder = new BackupServiceBinder().asBinder();
    }

    public void onCreate() {
    }

    public void onCreate(UserHandle user) {
        onCreate();
        this.mUser = user;
    }

    public void onDestroy() {
    }

    public void onRestore(BackupDataInput data, long appVersionCode, ParcelFileDescriptor newState) throws IOException {
        onRestore(data, (int) appVersionCode, newState);
    }

    public void onFullBackup(FullBackupDataOutput data) throws IOException {
        String libDir;
        FullBackup.BackupScheme backupScheme = FullBackup.getBackupScheme(this);
        if (!backupScheme.isFullBackupContentEnabled()) {
            return;
        }
        try {
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> manifestIncludeMap = backupScheme.maybeParseAndGetCanonicalIncludePaths();
            ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludeSet = backupScheme.maybeParseAndGetCanonicalExcludePaths();
            String packageName = getPackageName();
            ApplicationInfo appInfo = getApplicationInfo();
            Context ceContext = createCredentialProtectedStorageContext();
            String rootDir = ceContext.getDataDir().getCanonicalPath();
            String filesDir = ceContext.getFilesDir().getCanonicalPath();
            String noBackupDir = ceContext.getNoBackupFilesDir().getCanonicalPath();
            String databaseDir = ceContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String sharedPrefsDir = ceContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String cacheDir = ceContext.getCacheDir().getCanonicalPath();
            String codeCacheDir = ceContext.getCodeCacheDir().getCanonicalPath();
            Context deContext = createDeviceProtectedStorageContext();
            String deviceRootDir = deContext.getDataDir().getCanonicalPath();
            String deviceFilesDir = deContext.getFilesDir().getCanonicalPath();
            String deviceNoBackupDir = deContext.getNoBackupFilesDir().getCanonicalPath();
            String deviceDatabaseDir = deContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String deviceSharedPrefsDir = deContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String deviceCacheDir = deContext.getCacheDir().getCanonicalPath();
            String deviceCodeCacheDir = deContext.getCodeCacheDir().getCanonicalPath();
            if (appInfo.nativeLibraryDir != null) {
                libDir = new File(appInfo.nativeLibraryDir).getCanonicalPath();
            } else {
                libDir = null;
            }
            ArraySet<String> traversalExcludeSet = new ArraySet<>();
            traversalExcludeSet.add(filesDir);
            traversalExcludeSet.add(noBackupDir);
            traversalExcludeSet.add(databaseDir);
            traversalExcludeSet.add(sharedPrefsDir);
            traversalExcludeSet.add(cacheDir);
            traversalExcludeSet.add(codeCacheDir);
            traversalExcludeSet.add(deviceFilesDir);
            traversalExcludeSet.add(deviceNoBackupDir);
            traversalExcludeSet.add(deviceDatabaseDir);
            traversalExcludeSet.add(deviceSharedPrefsDir);
            traversalExcludeSet.add(deviceCacheDir);
            traversalExcludeSet.add(deviceCodeCacheDir);
            if (libDir != null) {
                traversalExcludeSet.add(libDir);
            }
            applyXmlFiltersAndDoFullBackupForDomain(packageName, "r", manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(rootDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_ROOT_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(deviceRootDir);
            traversalExcludeSet.remove(filesDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.FILES_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(filesDir);
            traversalExcludeSet.remove(deviceFilesDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_FILES_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(deviceFilesDir);
            traversalExcludeSet.remove(databaseDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DATABASE_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(databaseDir);
            traversalExcludeSet.remove(deviceDatabaseDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_DATABASE_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(deviceDatabaseDir);
            traversalExcludeSet.remove(sharedPrefsDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.SHAREDPREFS_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(sharedPrefsDir);
            traversalExcludeSet.remove(deviceSharedPrefsDir);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
            traversalExcludeSet.add(deviceSharedPrefsDir);
            if (Process.myUid() != 1000) {
                File efLocation = getExternalFilesDir(null);
                if (efLocation != null) {
                    applyXmlFiltersAndDoFullBackupForDomain(packageName, FullBackup.MANAGED_EXTERNAL_TREE_TOKEN, manifestIncludeMap, manifestExcludeSet, traversalExcludeSet, data);
                }
            }
        } catch (IOException | XmlPullParserException e) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.m65v("BackupXmlParserLogging", "Exception trying to parse fullBackupContent xml file! Aborting full backup.", e);
            }
        }
    }

    public void onQuotaExceeded(long backupDataBytes, long quotaBytes) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBackupUserId() {
        return this.mUser == null ? super.getUserId() : this.mUser.getIdentifier();
    }

    private void applyXmlFiltersAndDoFullBackupForDomain(String packageName, String domainToken, Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> includeMap, ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> filterSet, ArraySet<String> traversalExcludeSet, FullBackupDataOutput data) throws IOException {
        if (includeMap == null || includeMap.size() == 0) {
            fullBackupFileTree(packageName, domainToken, FullBackup.getBackupScheme(this).tokenToDirectoryPath(domainToken), filterSet, traversalExcludeSet, data);
            return;
        }
        if (includeMap.get(domainToken) != null) {
            for (FullBackup.BackupScheme.PathWithRequiredFlags includeFile : includeMap.get(domainToken)) {
                if (areIncludeRequiredTransportFlagsSatisfied(includeFile.getRequiredFlags(), data.getTransportFlags())) {
                    fullBackupFileTree(packageName, domainToken, includeFile.getPath(), filterSet, traversalExcludeSet, data);
                }
            }
        }
    }

    private boolean areIncludeRequiredTransportFlagsSatisfied(int includeFlags, int transportFlags) {
        return (transportFlags & includeFlags) == includeFlags;
    }

    public final void fullBackupFile(File file, FullBackupDataOutput output) {
        String canonicalPath;
        String efDir;
        String filePath;
        String domain;
        String rootpath;
        ApplicationInfo appInfo = getApplicationInfo();
        try {
            Context ceContext = createCredentialProtectedStorageContext();
            String rootDir = ceContext.getDataDir().getCanonicalPath();
            String filesDir = ceContext.getFilesDir().getCanonicalPath();
            String nbFilesDir = ceContext.getNoBackupFilesDir().getCanonicalPath();
            String dbDir = ceContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String spDir = ceContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String cacheDir = ceContext.getCacheDir().getCanonicalPath();
            String codeCacheDir = ceContext.getCodeCacheDir().getCanonicalPath();
            Context deContext = createDeviceProtectedStorageContext();
            String deviceRootDir = deContext.getDataDir().getCanonicalPath();
            String deviceFilesDir = deContext.getFilesDir().getCanonicalPath();
            String deviceNbFilesDir = deContext.getNoBackupFilesDir().getCanonicalPath();
            String deviceDbDir = deContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            String deviceSpDir = deContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            String deviceCacheDir = deContext.getCacheDir().getCanonicalPath();
            String deviceCodeCacheDir = deContext.getCodeCacheDir().getCanonicalPath();
            if (appInfo.nativeLibraryDir == null) {
                canonicalPath = null;
            } else {
                try {
                    canonicalPath = new File(appInfo.nativeLibraryDir).getCanonicalPath();
                } catch (IOException e) {
                    Log.m64w(TAG, "Unable to obtain canonical paths");
                    return;
                }
            }
            String libDir = canonicalPath;
            try {
                if (Process.myUid() != 1000) {
                    try {
                        File efLocation = getExternalFilesDir(null);
                        if (efLocation != null) {
                            efDir = efLocation.getCanonicalPath();
                            filePath = file.getCanonicalPath();
                            if (filePath.startsWith(cacheDir) && !filePath.startsWith(codeCacheDir) && !filePath.startsWith(nbFilesDir)) {
                                if (!filePath.startsWith(deviceCacheDir)) {
                                    if (!filePath.startsWith(deviceCodeCacheDir) && !filePath.startsWith(deviceNbFilesDir)) {
                                        if (!filePath.startsWith(libDir)) {
                                            if (filePath.startsWith(dbDir)) {
                                                domain = FullBackup.DATABASE_TREE_TOKEN;
                                                rootpath = dbDir;
                                            } else if (filePath.startsWith(spDir)) {
                                                domain = FullBackup.SHAREDPREFS_TREE_TOKEN;
                                                rootpath = spDir;
                                            } else if (filePath.startsWith(filesDir)) {
                                                domain = FullBackup.FILES_TREE_TOKEN;
                                                rootpath = filesDir;
                                            } else if (filePath.startsWith(rootDir)) {
                                                domain = "r";
                                                rootpath = rootDir;
                                            } else if (filePath.startsWith(deviceDbDir)) {
                                                domain = FullBackup.DEVICE_DATABASE_TREE_TOKEN;
                                                rootpath = deviceDbDir;
                                            } else if (filePath.startsWith(deviceSpDir)) {
                                                domain = FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
                                                rootpath = deviceSpDir;
                                            } else if (filePath.startsWith(deviceFilesDir)) {
                                                domain = FullBackup.DEVICE_FILES_TREE_TOKEN;
                                                rootpath = deviceFilesDir;
                                            } else if (filePath.startsWith(deviceRootDir)) {
                                                domain = FullBackup.DEVICE_ROOT_TREE_TOKEN;
                                                rootpath = deviceRootDir;
                                            } else if (efDir != null && filePath.startsWith(efDir)) {
                                                domain = FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
                                                rootpath = efDir;
                                            } else {
                                                Log.m64w(TAG, "File " + filePath + " is in an unsupported location; skipping");
                                                return;
                                            }
                                            FullBackup.backupToTar(getPackageName(), domain, null, rootpath, filePath, output);
                                            return;
                                        }
                                    }
                                }
                            }
                            Log.m64w(TAG, "lib, cache, code_cache, and no_backup files are not backed up");
                            return;
                        }
                    } catch (IOException e2) {
                        Log.m64w(TAG, "Unable to obtain canonical paths");
                        return;
                    }
                }
                filePath = file.getCanonicalPath();
                if (filePath.startsWith(cacheDir)) {
                }
                Log.m64w(TAG, "lib, cache, code_cache, and no_backup files are not backed up");
                return;
            } catch (IOException e3) {
                Log.m64w(TAG, "Unable to obtain canonical paths");
                return;
            }
            efDir = null;
        } catch (IOException e4) {
        }
    }

    protected final void fullBackupFileTree(String packageName, String domain, String startingPath, ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludes, ArraySet<String> systemExcludes, FullBackupDataOutput output) {
        File[] contents;
        String domainPath = FullBackup.getBackupScheme(this).tokenToDirectoryPath(domain);
        if (domainPath == null) {
            return;
        }
        File rootFile = new File(startingPath);
        if (rootFile.exists()) {
            LinkedList<File> scanQueue = new LinkedList<>();
            scanQueue.add(rootFile);
            while (scanQueue.size() > 0) {
                File file = scanQueue.remove(0);
                try {
                    StructStat stat = Os.lstat(file.getPath());
                    if (OsConstants.S_ISREG(stat.st_mode) || OsConstants.S_ISDIR(stat.st_mode)) {
                        String filePath = file.getCanonicalPath();
                        if (manifestExcludes != null) {
                            try {
                                if (manifestExcludesContainFilePath(manifestExcludes, filePath)) {
                                }
                            } catch (ErrnoException e) {
                                e = e;
                                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                                    Log.m66v("BackupXmlParserLogging", "Error scanning file " + file + " : " + e);
                                }
                            } catch (IOException e2) {
                                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                                    Log.m66v("BackupXmlParserLogging", "Error canonicalizing path of " + file);
                                }
                            }
                        }
                        if (systemExcludes == null || !systemExcludes.contains(filePath)) {
                            if (OsConstants.S_ISDIR(stat.st_mode) && (contents = file.listFiles()) != null) {
                                for (File entry : contents) {
                                    scanQueue.add(0, entry);
                                }
                            }
                            FullBackup.backupToTar(packageName, domain, null, domainPath, filePath, output);
                        }
                    }
                } catch (ErrnoException e3) {
                    e = e3;
                } catch (IOException e4) {
                }
            }
        }
    }

    private boolean manifestExcludesContainFilePath(ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludes, String filePath) {
        Iterator<FullBackup.BackupScheme.PathWithRequiredFlags> it = manifestExcludes.iterator();
        while (it.hasNext()) {
            FullBackup.BackupScheme.PathWithRequiredFlags exclude = it.next();
            String excludePath = exclude.getPath();
            if (excludePath != null && excludePath.equals(filePath)) {
                return true;
            }
        }
        return false;
    }

    public void onRestoreFile(ParcelFileDescriptor data, long size, File destination, int type, long mode, long mtime) throws IOException {
        boolean accept = isFileEligibleForRestore(destination);
        FullBackup.restoreFile(data, size, type, mode, mtime, accept ? destination : null);
    }

    private boolean isFileEligibleForRestore(File destination) throws IOException {
        FullBackup.BackupScheme bs = FullBackup.getBackupScheme(this);
        if (!bs.isFullBackupContentEnabled()) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.m66v("BackupXmlParserLogging", "onRestoreFile \"" + destination.getCanonicalPath() + "\" : fullBackupContent not enabled for " + getPackageName());
            }
            return false;
        }
        String destinationCanonicalPath = destination.getCanonicalPath();
        try {
            Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> includes = bs.maybeParseAndGetCanonicalIncludePaths();
            ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> excludes = bs.maybeParseAndGetCanonicalExcludePaths();
            if (excludes != null && BackupUtils.isFileSpecifiedInPathList(destination, excludes)) {
                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                    Log.m66v("BackupXmlParserLogging", "onRestoreFile: \"" + destinationCanonicalPath + "\": listed in excludes; skipping.");
                }
                return false;
            } else if (includes != null && !includes.isEmpty()) {
                boolean explicitlyIncluded = false;
                for (Set<FullBackup.BackupScheme.PathWithRequiredFlags> domainIncludes : includes.values()) {
                    explicitlyIncluded |= BackupUtils.isFileSpecifiedInPathList(destination, domainIncludes);
                    if (explicitlyIncluded) {
                        break;
                    }
                }
                if (!explicitlyIncluded) {
                    if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                        Log.m66v("BackupXmlParserLogging", "onRestoreFile: Trying to restore \"" + destinationCanonicalPath + "\" but it isn't specified in the included files; skipping.");
                    }
                    return false;
                }
                return true;
            } else {
                return true;
            }
        } catch (XmlPullParserException e) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.m65v("BackupXmlParserLogging", "onRestoreFile \"" + destinationCanonicalPath + "\" : Exception trying to parse fullBackupContent xml file! Aborting onRestoreFile.", e);
            }
            return false;
        }
    }

    protected void onRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime) throws IOException {
        long mode2;
        String basePath = FullBackup.getBackupScheme(this).tokenToDirectoryPath(domain);
        if (domain.equals(FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)) {
            mode2 = -1;
        } else {
            mode2 = mode;
        }
        if (basePath != null) {
            File outFile = new File(basePath, path);
            String outPath = outFile.getCanonicalPath();
            if (outPath.startsWith(basePath + File.separatorChar)) {
                onRestoreFile(data, size, outFile, type, mode2, mtime);
                return;
            }
        }
        FullBackup.restoreFile(data, size, type, mode2, mtime, null);
    }

    public void onRestoreFinished() {
    }

    public final IBinder onBind() {
        return this.mBinder;
    }

    public void attach(Context context) {
        attachBaseContext(context);
    }

    /* loaded from: classes.dex */
    private class BackupServiceBinder extends IBackupAgent.Stub {
        private static final String TAG = "BackupServiceBinder";

        private BackupServiceBinder() {
        }

        /* JADX WARN: Removed duplicated region for block: B:37:0x00c5  */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doBackup(ParcelFileDescriptor oldState, ParcelFileDescriptor data, ParcelFileDescriptor newState, long quotaBytes, IBackupCallback callbackBinder, int transportFlags) throws RemoteException {
            long ident = Binder.clearCallingIdentity();
            BackupDataOutput output = new BackupDataOutput(data.getFileDescriptor(), quotaBytes, transportFlags);
            try {
                try {
                } catch (IOException e) {
                    ex = e;
                } catch (RuntimeException e2) {
                    ex = e2;
                } catch (Throwable th) {
                    th = th;
                    Throwable th2 = th;
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.operationComplete(-1L);
                    } catch (RemoteException e3) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                    }
                    throw th2;
                }
                try {
                    BackupAgent.this.onBackup(oldState, output, newState);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.operationComplete(0L);
                    } catch (RemoteException e4) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(oldState);
                        IoUtils.closeQuietly(data);
                        IoUtils.closeQuietly(newState);
                    }
                } catch (IOException e5) {
                    ex = e5;
                    Log.m71d(TAG, "onBackup (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                    throw new RuntimeException(ex);
                } catch (RuntimeException e6) {
                    ex = e6;
                    Log.m71d(TAG, "onBackup (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                    throw ex;
                }
            } catch (Throwable th3) {
                th = th3;
                Throwable th22 = th;
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                callbackBinder.operationComplete(-1L);
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(oldState);
                    IoUtils.closeQuietly(data);
                    IoUtils.closeQuietly(newState);
                }
                throw th22;
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestore(ParcelFileDescriptor data, long appVersionCode, ParcelFileDescriptor newState, int token, IBackupManager callbackBinder) throws RemoteException {
            long ident = Binder.clearCallingIdentity();
            BackupAgent.this.waitForSharedPrefs();
            BackupDataInput input = new BackupDataInput(data.getFileDescriptor());
            try {
                try {
                    BackupAgent.this.onRestore(input, appVersionCode, newState);
                } catch (IOException ex) {
                    Log.m71d(TAG, "onRestore (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                    throw new RuntimeException(ex);
                } catch (RuntimeException ex2) {
                    Log.m71d(TAG, "onRestore (" + BackupAgent.this.getClass().getName() + ") threw", ex2);
                    throw ex2;
                }
            } finally {
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                } catch (RemoteException e) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                    IoUtils.closeQuietly(newState);
                }
            }
        }

        @Override // android.app.IBackupAgent
        public void doFullBackup(ParcelFileDescriptor data, long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) {
            long ident = Binder.clearCallingIdentity();
            BackupAgent.this.waitForSharedPrefs();
            try {
                try {
                    BackupAgent.this.onFullBackup(new FullBackupDataOutput(data, quotaBytes, transportFlags));
                    BackupAgent.this.waitForSharedPrefs();
                    try {
                        FileOutputStream out = new FileOutputStream(data.getFileDescriptor());
                        byte[] buf = new byte[4];
                        out.write(buf);
                    } catch (IOException e) {
                        Log.m70e(TAG, "Unable to finalize backup stream!");
                    }
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                    } catch (RemoteException e2) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(data);
                    }
                } catch (IOException ex) {
                    Log.m71d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                    throw new RuntimeException(ex);
                } catch (RuntimeException ex2) {
                    Log.m71d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", ex2);
                    throw ex2;
                }
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                try {
                    FileOutputStream out2 = new FileOutputStream(data.getFileDescriptor());
                    byte[] buf2 = new byte[4];
                    out2.write(buf2);
                } catch (IOException e3) {
                    Log.m70e(TAG, "Unable to finalize backup stream!");
                }
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                } catch (RemoteException e4) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) {
            long ident = Binder.clearCallingIdentity();
            FullBackupDataOutput measureOutput = new FullBackupDataOutput(quotaBytes, transportFlags);
            BackupAgent.this.waitForSharedPrefs();
            try {
                try {
                    try {
                        BackupAgent.this.onFullBackup(measureOutput);
                        Binder.restoreCallingIdentity(ident);
                        try {
                            callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, measureOutput.getSize());
                        } catch (RemoteException e) {
                        }
                    } catch (RuntimeException ex) {
                        Log.m71d(TAG, "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                        throw ex;
                    }
                } catch (IOException ex2) {
                    Log.m71d(TAG, "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw", ex2);
                    throw new RuntimeException(ex2);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, measureOutput.getSize());
                } catch (RemoteException e2) {
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime, int token, IBackupManager callbackBinder) throws RemoteException {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFile(data, size, type, domain, path, mode, mtime);
                    BackupAgent.this.waitForSharedPrefs();
                    BackupAgent.this.reloadSharedPreferences();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                    } catch (RemoteException e) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(data);
                    }
                } catch (Throwable th) {
                    BackupAgent.this.waitForSharedPrefs();
                    BackupAgent.this.reloadSharedPreferences();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                    } catch (RemoteException e2) {
                    }
                    if (Binder.getCallingPid() != Process.myPid()) {
                        IoUtils.closeQuietly(data);
                    }
                    throw th;
                }
            } catch (IOException e3) {
                Log.m71d(TAG, "onRestoreFile (" + BackupAgent.this.getClass().getName() + ") threw", e3);
                throw new RuntimeException(e3);
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int token, IBackupManager callbackBinder) {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onRestoreFinished();
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                    } catch (RemoteException e) {
                    }
                } catch (Exception e2) {
                    Log.m71d(TAG, "onRestoreFinished (" + BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw e2;
                }
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0L);
                } catch (RemoteException e3) {
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void fail(String message) {
            BackupAgent.this.getHandler().post(new FailRunnable(message));
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder) {
            long ident = Binder.clearCallingIdentity();
            try {
                try {
                    BackupAgent.this.onQuotaExceeded(backupDataBytes, quotaBytes);
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.operationComplete(0L);
                    } catch (RemoteException e) {
                    }
                } catch (Throwable th) {
                    BackupAgent.this.waitForSharedPrefs();
                    Binder.restoreCallingIdentity(ident);
                    try {
                        callbackBinder.operationComplete(-1L);
                    } catch (RemoteException e2) {
                    }
                    throw th;
                }
            } catch (Exception e3) {
                Log.m71d(TAG, "onQuotaExceeded(" + BackupAgent.this.getClass().getName() + ") threw", e3);
                throw e3;
            }
        }
    }

    /* loaded from: classes.dex */
    static class FailRunnable implements Runnable {
        private String mMessage;

        FailRunnable(String message) {
            this.mMessage = message;
        }

        @Override // java.lang.Runnable
        public void run() {
            throw new IllegalStateException(this.mMessage);
        }
    }
}
