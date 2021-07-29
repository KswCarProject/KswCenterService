package android.app.backup;

import android.app.IBackupAgent;
import android.app.QueuedWork;
import android.app.backup.FullBackup;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import libcore.io.IoUtils;

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
    private final IBinder mBinder = new BackupServiceBinder().asBinder();
    Handler mHandler = null;
    private UserHandle mUser;

    public abstract void onBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) throws IOException;

    public abstract void onRestore(BackupDataInput backupDataInput, int i, ParcelFileDescriptor parcelFileDescriptor) throws IOException;

    /* access modifiers changed from: package-private */
    public Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    class SharedPrefsSynchronizer implements Runnable {
        public final CountDownLatch mLatch = new CountDownLatch(1);

        SharedPrefsSynchronizer() {
        }

        public void run() {
            QueuedWork.waitToFinish();
            this.mLatch.countDown();
        }
    }

    /* access modifiers changed from: private */
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
        super((Context) null);
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

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        */
    public void onFullBackup(android.app.backup.FullBackupDataOutput r36) throws java.io.IOException {
        /*
            r35 = this;
            android.app.backup.FullBackup$BackupScheme r1 = android.app.backup.FullBackup.getBackupScheme(r35)
            boolean r0 = r1.isFullBackupContentEnabled()
            if (r0 != 0) goto L_0x000b
            return
        L_0x000b:
            java.util.Map r5 = r1.maybeParseAndGetCanonicalIncludePaths()     // Catch:{ IOException | XmlPullParserException -> 0x01bd }
            android.util.ArraySet r6 = r1.maybeParseAndGetCanonicalExcludePaths()     // Catch:{ IOException | XmlPullParserException -> 0x01bd }
            java.lang.String r0 = r35.getPackageName()
            android.content.pm.ApplicationInfo r14 = r35.getApplicationInfo()
            android.content.Context r15 = r35.createCredentialProtectedStorageContext()
            java.io.File r2 = r15.getDataDir()
            java.lang.String r13 = r2.getCanonicalPath()
            java.io.File r2 = r15.getFilesDir()
            java.lang.String r12 = r2.getCanonicalPath()
            java.io.File r2 = r15.getNoBackupFilesDir()
            java.lang.String r11 = r2.getCanonicalPath()
            java.lang.String r2 = "foo"
            java.io.File r2 = r15.getDatabasePath(r2)
            java.io.File r2 = r2.getParentFile()
            java.lang.String r10 = r2.getCanonicalPath()
            java.lang.String r2 = "foo"
            java.io.File r2 = r15.getSharedPreferencesPath(r2)
            java.io.File r2 = r2.getParentFile()
            java.lang.String r9 = r2.getCanonicalPath()
            java.io.File r2 = r15.getCacheDir()
            java.lang.String r8 = r2.getCanonicalPath()
            java.io.File r2 = r15.getCodeCacheDir()
            java.lang.String r7 = r2.getCanonicalPath()
            android.content.Context r4 = r35.createDeviceProtectedStorageContext()
            java.io.File r2 = r4.getDataDir()
            java.lang.String r3 = r2.getCanonicalPath()
            java.io.File r2 = r4.getFilesDir()
            java.lang.String r2 = r2.getCanonicalPath()
            java.io.File r16 = r4.getNoBackupFilesDir()
            r17 = r1
            java.lang.String r1 = r16.getCanonicalPath()
            r18 = r3
            java.lang.String r3 = "foo"
            java.io.File r3 = r4.getDatabasePath(r3)
            java.io.File r3 = r3.getParentFile()
            java.lang.String r3 = r3.getCanonicalPath()
            r19 = r15
            java.lang.String r15 = "foo"
            java.io.File r15 = r4.getSharedPreferencesPath(r15)
            java.io.File r15 = r15.getParentFile()
            java.lang.String r15 = r15.getCanonicalPath()
            java.io.File r16 = r4.getCacheDir()
            r20 = r13
            java.lang.String r13 = r16.getCanonicalPath()
            java.io.File r16 = r4.getCodeCacheDir()
            r21 = r5
            java.lang.String r5 = r16.getCanonicalPath()
            r22 = r4
            java.lang.String r4 = r14.nativeLibraryDir
            r23 = r6
            if (r4 == 0) goto L_0x00cc
            java.io.File r4 = new java.io.File
            java.lang.String r6 = r14.nativeLibraryDir
            r4.<init>(r6)
            java.lang.String r6 = r4.getCanonicalPath()
            goto L_0x00cd
        L_0x00cc:
            r6 = 0
        L_0x00cd:
            android.util.ArraySet r4 = new android.util.ArraySet
            r4.<init>()
            r4.add(r12)
            r4.add(r11)
            r4.add(r10)
            r4.add(r9)
            r4.add(r8)
            r4.add(r7)
            r4.add(r2)
            r4.add(r1)
            r4.add(r3)
            r4.add(r15)
            r4.add(r13)
            r4.add(r5)
            if (r6 == 0) goto L_0x00fb
            r4.add(r6)
        L_0x00fb:
            java.lang.String r16 = "r"
            r25 = r1
            r1 = r2
            r2 = r35
            r26 = r14
            r27 = r15
            r14 = r18
            r15 = r3
            r3 = r0
            r28 = r4
            r18 = r22
            r4 = r16
            r16 = r7
            r7 = r28
            r22 = r8
            r8 = r36
            r33 = r21
            r21 = r5
            r5 = r33
            r34 = r23
            r23 = r6
            r6 = r34
            r2.applyXmlFiltersAndDoFullBackupForDomain(r3, r4, r5, r6, r7, r8)
            r2 = r20
            r3 = r28
            r3.add(r2)
            java.lang.String r4 = "d_r"
            r7 = r35
            r8 = r0
            r29 = r2
            r2 = r9
            r9 = r4
            r4 = r10
            r10 = r5
            r20 = r11
            r11 = r6
            r30 = r2
            r2 = r12
            r12 = r3
            r28 = r13
            r24 = r29
            r13 = r36
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r14)
            r3.remove(r2)
            java.lang.String r9 = "f"
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r2)
            r3.remove(r1)
            java.lang.String r9 = "d_f"
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r1)
            r3.remove(r4)
            java.lang.String r9 = "db"
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r4)
            r3.remove(r15)
            java.lang.String r9 = "d_db"
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r15)
            r13 = r30
            r3.remove(r13)
            java.lang.String r9 = "sp"
            r31 = r1
            r1 = r13
            r13 = r36
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r1)
            r13 = r27
            r3.remove(r13)
            java.lang.String r9 = "d_sp"
            r32 = r1
            r1 = r13
            r13 = r36
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
            r3.add(r1)
            int r7 = android.os.Process.myUid()
            r8 = 1000(0x3e8, float:1.401E-42)
            if (r7 == r8) goto L_0x01bc
            r7 = 0
            r13 = r35
            java.io.File r27 = r13.getExternalFilesDir(r7)
            if (r27 == 0) goto L_0x01bc
            java.lang.String r9 = "ef"
            r7 = r35
            r8 = r0
            r10 = r5
            r11 = r6
            r12 = r3
            r13 = r36
            r7.applyXmlFiltersAndDoFullBackupForDomain(r8, r9, r10, r11, r12, r13)
        L_0x01bc:
            return
        L_0x01bd:
            r0 = move-exception
            r17 = r1
            java.lang.String r1 = "BackupXmlParserLogging"
            r2 = 2
            boolean r1 = android.util.Log.isLoggable(r1, r2)
            if (r1 == 0) goto L_0x01d0
            java.lang.String r1 = "BackupXmlParserLogging"
            java.lang.String r2 = "Exception trying to parse fullBackupContent xml file! Aborting full backup."
            android.util.Log.v(r1, r2, r0)
        L_0x01d0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.onFullBackup(android.app.backup.FullBackupDataOutput):void");
    }

    public void onQuotaExceeded(long backupDataBytes, long quotaBytes) {
    }

    /* access modifiers changed from: private */
    public int getBackupUserId() {
        return this.mUser == null ? super.getUserId() : this.mUser.getIdentifier();
    }

    private void applyXmlFiltersAndDoFullBackupForDomain(String packageName, String domainToken, Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> includeMap, ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> filterSet, ArraySet<String> traversalExcludeSet, FullBackupDataOutput data) throws IOException {
        String str = domainToken;
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

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01bf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void fullBackupFile(java.io.File r35, android.app.backup.FullBackupDataOutput r36) {
        /*
            r34 = this;
            r1 = 0
            android.content.pm.ApplicationInfo r0 = r34.getApplicationInfo()
            r2 = r0
            android.content.Context r0 = r34.createCredentialProtectedStorageContext()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r3 = r0.getDataDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r3 = r3.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r4 = r0.getFilesDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r4 = r4.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r5 = r0.getNoBackupFilesDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r5 = r5.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r6 = "foo"
            java.io.File r6 = r0.getDatabasePath(r6)     // Catch:{ IOException -> 0x01d9 }
            java.io.File r6 = r6.getParentFile()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r6 = r6.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r7 = "foo"
            java.io.File r7 = r0.getSharedPreferencesPath(r7)     // Catch:{ IOException -> 0x01d9 }
            java.io.File r7 = r7.getParentFile()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r7 = r7.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r8 = r0.getCacheDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r8 = r8.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r9 = r0.getCodeCacheDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r9 = r9.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            android.content.Context r10 = r34.createDeviceProtectedStorageContext()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r11 = r10.getDataDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r11 = r11.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r12 = r10.getFilesDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r12 = r12.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r13 = r10.getNoBackupFilesDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r13 = r13.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r14 = "foo"
            java.io.File r14 = r10.getDatabasePath(r14)     // Catch:{ IOException -> 0x01d9 }
            java.io.File r14 = r14.getParentFile()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r14 = r14.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r15 = "foo"
            java.io.File r15 = r10.getSharedPreferencesPath(r15)     // Catch:{ IOException -> 0x01d9 }
            java.io.File r15 = r15.getParentFile()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r15 = r15.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r16 = r10.getCacheDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r16 = r16.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            java.io.File r17 = r10.getCodeCacheDir()     // Catch:{ IOException -> 0x01d9 }
            java.lang.String r17 = r17.getCanonicalPath()     // Catch:{ IOException -> 0x01d9 }
            r18 = r0
            java.lang.String r0 = r2.nativeLibraryDir     // Catch:{ IOException -> 0x01d9 }
            r19 = r1
            if (r0 != 0) goto L_0x00a1
            r1 = 0
            goto L_0x00ac
        L_0x00a1:
            java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x01d5 }
            java.lang.String r1 = r2.nativeLibraryDir     // Catch:{ IOException -> 0x01d5 }
            r0.<init>(r1)     // Catch:{ IOException -> 0x01d5 }
            java.lang.String r1 = r0.getCanonicalPath()     // Catch:{ IOException -> 0x01d5 }
        L_0x00ac:
            r0 = r1
            int r1 = android.os.Process.myUid()     // Catch:{ IOException -> 0x01d5 }
            r21 = r2
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r1 == r2) goto L_0x00ca
            r2 = 0
            r1 = r34
            java.io.File r2 = r1.getExternalFilesDir(r2)     // Catch:{ IOException -> 0x00c7 }
            if (r2 == 0) goto L_0x00cc
            java.lang.String r20 = r2.getCanonicalPath()     // Catch:{ IOException -> 0x00c7 }
            r2 = r20
            goto L_0x00ce
        L_0x00c7:
            r0 = move-exception
            goto L_0x01de
        L_0x00ca:
            r1 = r34
        L_0x00cc:
            r2 = r19
        L_0x00ce:
            java.lang.String r19 = r35.getCanonicalPath()     // Catch:{ IOException -> 0x01cf }
            r10 = r19
            r28 = r16
            r29 = r17
            boolean r16 = r10.startsWith(r8)
            if (r16 != 0) goto L_0x01bf
            boolean r16 = r10.startsWith(r9)
            if (r16 != 0) goto L_0x01bf
            boolean r16 = r10.startsWith(r5)
            if (r16 != 0) goto L_0x01bf
            r1 = r28
            boolean r16 = r10.startsWith(r1)
            if (r16 != 0) goto L_0x01b6
            r30 = r1
            r1 = r29
            boolean r16 = r10.startsWith(r1)
            if (r16 != 0) goto L_0x01af
            boolean r16 = r10.startsWith(r13)
            if (r16 != 0) goto L_0x01af
            boolean r16 = r10.startsWith(r0)
            if (r16 == 0) goto L_0x0111
            r31 = r0
            r32 = r1
            r33 = r2
            goto L_0x01c7
        L_0x0111:
            r16 = 0
            boolean r17 = r10.startsWith(r6)
            if (r17 == 0) goto L_0x0120
            java.lang.String r17 = "db"
            r16 = r6
        L_0x011d:
            r23 = r17
            goto L_0x017c
        L_0x0120:
            boolean r17 = r10.startsWith(r7)
            if (r17 == 0) goto L_0x012c
            java.lang.String r17 = "sp"
            r16 = r7
            goto L_0x011d
        L_0x012c:
            boolean r17 = r10.startsWith(r4)
            if (r17 == 0) goto L_0x0137
            java.lang.String r17 = "f"
            r16 = r4
            goto L_0x011d
        L_0x0137:
            boolean r17 = r10.startsWith(r3)
            if (r17 == 0) goto L_0x0143
            java.lang.String r17 = "r"
            r16 = r3
            goto L_0x011d
        L_0x0143:
            boolean r17 = r10.startsWith(r14)
            if (r17 == 0) goto L_0x014e
            java.lang.String r17 = "d_db"
            r16 = r14
            goto L_0x011d
        L_0x014e:
            boolean r17 = r10.startsWith(r15)
            if (r17 == 0) goto L_0x0159
            java.lang.String r17 = "d_sp"
            r16 = r15
            goto L_0x011d
        L_0x0159:
            boolean r17 = r10.startsWith(r12)
            if (r17 == 0) goto L_0x0164
            java.lang.String r17 = "d_f"
            r16 = r12
            goto L_0x011d
        L_0x0164:
            boolean r17 = r10.startsWith(r11)
            if (r17 == 0) goto L_0x016f
            java.lang.String r17 = "d_r"
            r16 = r11
            goto L_0x011d
        L_0x016f:
            if (r2 == 0) goto L_0x018d
            boolean r17 = r10.startsWith(r2)
            if (r17 == 0) goto L_0x018d
            java.lang.String r17 = "ef"
            r16 = r2
            goto L_0x011d
        L_0x017c:
            java.lang.String r22 = r34.getPackageName()
            r24 = 0
            r25 = r16
            r26 = r10
            r27 = r36
            android.app.backup.FullBackup.backupToTar(r22, r23, r24, r25, r26, r27)
            return
        L_0x018d:
            r31 = r0
            java.lang.String r0 = "BackupAgent"
            r32 = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r33 = r2
            java.lang.String r2 = "File "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r2 = " is in an unsupported location; skipping"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r1)
            return
        L_0x01af:
            r31 = r0
            r32 = r1
            r33 = r2
            goto L_0x01c7
        L_0x01b6:
            r31 = r0
            r30 = r1
            r33 = r2
            r32 = r29
            goto L_0x01c7
        L_0x01bf:
            r31 = r0
            r33 = r2
            r30 = r28
            r32 = r29
        L_0x01c7:
            java.lang.String r0 = "BackupAgent"
            java.lang.String r1 = "lib, cache, code_cache, and no_backup files are not backed up"
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r1)
            return
        L_0x01cf:
            r0 = move-exception
            r33 = r2
            r19 = r33
            goto L_0x01de
        L_0x01d5:
            r0 = move-exception
            r21 = r2
            goto L_0x01de
        L_0x01d9:
            r0 = move-exception
            r19 = r1
            r21 = r2
        L_0x01de:
            java.lang.String r1 = "BackupAgent"
            java.lang.String r2 = "Unable to obtain canonical paths"
            android.util.Log.w((java.lang.String) r1, (java.lang.String) r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.fullBackupFile(java.io.File, android.app.backup.FullBackupDataOutput):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0028 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0028 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void fullBackupFileTree(java.lang.String r19, java.lang.String r20, java.lang.String r21, android.util.ArraySet<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> r22, android.util.ArraySet<java.lang.String> r23, android.app.backup.FullBackupDataOutput r24) {
        /*
            r18 = this;
            r1 = r22
            r2 = r23
            android.app.backup.FullBackup$BackupScheme r0 = android.app.backup.FullBackup.getBackupScheme(r18)
            r9 = r20
            java.lang.String r10 = r0.tokenToDirectoryPath(r9)
            if (r10 != 0) goto L_0x0011
            return
        L_0x0011:
            java.io.File r0 = new java.io.File
            r11 = r21
            r0.<init>(r11)
            r12 = r0
            boolean r0 = r12.exists()
            if (r0 == 0) goto L_0x00eb
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            r13 = r0
            r13.add(r12)
        L_0x0028:
            int r0 = r13.size()
            if (r0 <= 0) goto L_0x00eb
            r0 = 0
            java.lang.Object r3 = r13.remove(r0)
            java.io.File r3 = (java.io.File) r3
            r14 = r3
            java.lang.String r4 = r14.getPath()     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            android.system.StructStat r4 = android.system.Os.lstat(r4)     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            int r5 = r4.st_mode     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            boolean r5 = android.system.OsConstants.S_ISREG(r5)     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            if (r5 != 0) goto L_0x004f
            int r5 = r4.st_mode     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            boolean r5 = android.system.OsConstants.S_ISDIR(r5)     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            if (r5 != 0) goto L_0x004f
            goto L_0x0028
        L_0x004f:
            java.lang.String r5 = r14.getCanonicalPath()     // Catch:{ IOException -> 0x00c7, ErrnoException -> 0x009b }
            r7 = r5
            if (r1 == 0) goto L_0x0064
            r15 = r18
            boolean r5 = r15.manifestExcludesContainFilePath(r1, r7)     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            if (r5 == 0) goto L_0x0066
            goto L_0x0028
        L_0x005f:
            r0 = move-exception
            goto L_0x009e
        L_0x0061:
            r0 = move-exception
            goto L_0x00ca
        L_0x0064:
            r15 = r18
        L_0x0066:
            if (r2 == 0) goto L_0x006f
            boolean r5 = r2.contains(r7)     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            if (r5 == 0) goto L_0x006f
            goto L_0x0028
        L_0x006f:
            int r5 = r4.st_mode     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            boolean r5 = android.system.OsConstants.S_ISDIR(r5)     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            if (r5 == 0) goto L_0x008d
            java.io.File[] r5 = r14.listFiles()     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            if (r5 == 0) goto L_0x008d
            int r6 = r5.length     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            r8 = r0
        L_0x007f:
            if (r8 >= r6) goto L_0x008d
            r16 = r5[r8]     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            r17 = r16
            r3 = r17
            r13.add(r0, r3)     // Catch:{ IOException -> 0x0061, ErrnoException -> 0x005f }
            int r8 = r8 + 1
            goto L_0x007f
        L_0x008d:
            r5 = 0
            r3 = r19
            r4 = r20
            r6 = r10
            r8 = r24
            android.app.backup.FullBackup.backupToTar(r3, r4, r5, r6, r7, r8)
            goto L_0x0028
        L_0x009b:
            r0 = move-exception
            r15 = r18
        L_0x009e:
            java.lang.String r3 = "BackupXmlParserLogging"
            r4 = 2
            boolean r3 = android.util.Log.isLoggable(r3, r4)
            if (r3 == 0) goto L_0x00c5
            java.lang.String r3 = "BackupXmlParserLogging"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Error scanning file "
            r4.append(r5)
            r4.append(r14)
            java.lang.String r5 = " : "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            android.util.Log.v(r3, r4)
        L_0x00c5:
            goto L_0x0028
        L_0x00c7:
            r0 = move-exception
            r15 = r18
        L_0x00ca:
            java.lang.String r3 = "BackupXmlParserLogging"
            r4 = 2
            boolean r3 = android.util.Log.isLoggable(r3, r4)
            if (r3 == 0) goto L_0x00e9
            java.lang.String r3 = "BackupXmlParserLogging"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Error canonicalizing path of "
            r4.append(r5)
            r4.append(r14)
            java.lang.String r4 = r4.toString()
            android.util.Log.v(r3, r4)
        L_0x00e9:
            goto L_0x0028
        L_0x00eb:
            r15 = r18
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.fullBackupFileTree(java.lang.String, java.lang.String, java.lang.String, android.util.ArraySet, android.util.ArraySet, android.app.backup.FullBackupDataOutput):void");
    }

    private boolean manifestExcludesContainFilePath(ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> manifestExcludes, String filePath) {
        Iterator<FullBackup.BackupScheme.PathWithRequiredFlags> it = manifestExcludes.iterator();
        while (it.hasNext()) {
            String excludePath = it.next().getPath();
            if (excludePath != null && excludePath.equals(filePath)) {
                return true;
            }
        }
        return false;
    }

    public void onRestoreFile(ParcelFileDescriptor data, long size, File destination, int type, long mode, long mtime) throws IOException {
        File file = destination;
        FullBackup.restoreFile(data, size, type, mode, mtime, isFileEligibleForRestore(file) ? file : null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x008b A[LOOP:0: B:21:0x008b->B:24:0x009d, LOOP_START, PHI: r6 
      PHI: (r6v5 'explicitlyIncluded' boolean) = (r6v4 'explicitlyIncluded' boolean), (r6v7 'explicitlyIncluded' boolean) binds: [B:20:0x0082, B:24:0x009d] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isFileEligibleForRestore(java.io.File r11) throws java.io.IOException {
        /*
            r10 = this;
            android.app.backup.FullBackup$BackupScheme r0 = android.app.backup.FullBackup.getBackupScheme(r10)
            boolean r1 = r0.isFullBackupContentEnabled()
            r2 = 0
            r3 = 2
            if (r1 != 0) goto L_0x003c
            java.lang.String r1 = "BackupXmlParserLogging"
            boolean r1 = android.util.Log.isLoggable(r1, r3)
            if (r1 == 0) goto L_0x003b
            java.lang.String r1 = "BackupXmlParserLogging"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "onRestoreFile \""
            r3.append(r4)
            java.lang.String r4 = r11.getCanonicalPath()
            r3.append(r4)
            java.lang.String r4 = "\" : fullBackupContent not enabled for "
            r3.append(r4)
            java.lang.String r4 = r10.getPackageName()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.v(r1, r3)
        L_0x003b:
            return r2
        L_0x003c:
            r1 = 0
            r4 = 0
            java.lang.String r5 = r11.getCanonicalPath()
            java.util.Map r6 = r0.maybeParseAndGetCanonicalIncludePaths()     // Catch:{ XmlPullParserException -> 0x00ca }
            r1 = r6
            android.util.ArraySet r6 = r0.maybeParseAndGetCanonicalExcludePaths()     // Catch:{ XmlPullParserException -> 0x00ca }
            r4 = r6
            if (r4 == 0) goto L_0x007a
            boolean r6 = android.app.backup.BackupUtils.isFileSpecifiedInPathList(r11, r4)
            if (r6 == 0) goto L_0x007a
            java.lang.String r6 = "BackupXmlParserLogging"
            boolean r3 = android.util.Log.isLoggable(r6, r3)
            if (r3 == 0) goto L_0x0079
            java.lang.String r3 = "BackupXmlParserLogging"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "onRestoreFile: \""
            r6.append(r7)
            r6.append(r5)
            java.lang.String r7 = "\": listed in excludes; skipping."
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            android.util.Log.v(r3, r6)
        L_0x0079:
            return r2
        L_0x007a:
            if (r1 == 0) goto L_0x00c8
            boolean r6 = r1.isEmpty()
            if (r6 != 0) goto L_0x00c8
            r6 = 0
            java.util.Collection r7 = r1.values()
            java.util.Iterator r7 = r7.iterator()
        L_0x008b:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x00a1
            java.lang.Object r8 = r7.next()
            java.util.Set r8 = (java.util.Set) r8
            boolean r9 = android.app.backup.BackupUtils.isFileSpecifiedInPathList(r11, r8)
            r6 = r6 | r9
            if (r6 == 0) goto L_0x00a0
            goto L_0x00a1
        L_0x00a0:
            goto L_0x008b
        L_0x00a1:
            if (r6 != 0) goto L_0x00c8
            java.lang.String r7 = "BackupXmlParserLogging"
            boolean r3 = android.util.Log.isLoggable(r7, r3)
            if (r3 == 0) goto L_0x00c7
            java.lang.String r3 = "BackupXmlParserLogging"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "onRestoreFile: Trying to restore \""
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = "\" but it isn't specified in the included files; skipping."
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.v(r3, r7)
        L_0x00c7:
            return r2
        L_0x00c8:
            r2 = 1
            return r2
        L_0x00ca:
            r6 = move-exception
            java.lang.String r7 = "BackupXmlParserLogging"
            boolean r3 = android.util.Log.isLoggable(r7, r3)
            if (r3 == 0) goto L_0x00ef
            java.lang.String r3 = "BackupXmlParserLogging"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "onRestoreFile \""
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = "\" : Exception trying to parse fullBackupContent xml file! Aborting onRestoreFile."
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.v(r3, r7, r6)
        L_0x00ef:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.isFileEligibleForRestore(java.io.File):boolean");
    }

    /* access modifiers changed from: protected */
    public void onRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime) throws IOException {
        long mode2;
        String str = domain;
        String basePath = FullBackup.getBackupScheme(this).tokenToDirectoryPath(str);
        if (str.equals(FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)) {
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
        } else {
            String str2 = path;
        }
        FullBackup.restoreFile(data, size, type, mode2, mtime, (File) null);
    }

    public void onRestoreFinished() {
    }

    public final IBinder onBind() {
        return this.mBinder;
    }

    public void attach(Context context) {
        attachBaseContext(context);
    }

    private class BackupServiceBinder extends IBackupAgent.Stub {
        private static final String TAG = "BackupServiceBinder";

        private BackupServiceBinder() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:35:0x00c5  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void doBackup(android.os.ParcelFileDescriptor r17, android.os.ParcelFileDescriptor r18, android.os.ParcelFileDescriptor r19, long r20, android.app.backup.IBackupCallback r22, int r23) throws android.os.RemoteException {
            /*
                r16 = this;
                r1 = r16
                r2 = r22
                long r3 = android.os.Binder.clearCallingIdentity()
                android.app.backup.BackupDataOutput r0 = new android.app.backup.BackupDataOutput
                java.io.FileDescriptor r5 = r18.getFileDescriptor()
                r6 = r20
                r8 = r23
                r0.<init>(r5, r6, r8)
                r5 = r0
                r9 = -1
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this     // Catch:{ IOException -> 0x007d, RuntimeException -> 0x0051, all -> 0x004a }
                r11 = r17
                r12 = r19
                r0.onBackup(r11, r5, r12)     // Catch:{ IOException -> 0x0048, RuntimeException -> 0x0046 }
                r9 = 0
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                r0.waitForSharedPrefs()
                android.os.Binder.restoreCallingIdentity(r3)
                r2.operationComplete(r9)     // Catch:{ RemoteException -> 0x002f }
                goto L_0x0030
            L_0x002f:
                r0 = move-exception
            L_0x0030:
                int r0 = android.os.Binder.getCallingPid()
                int r13 = android.os.Process.myPid()
                if (r0 == r13) goto L_0x0043
                libcore.io.IoUtils.closeQuietly(r17)
                libcore.io.IoUtils.closeQuietly(r18)
                libcore.io.IoUtils.closeQuietly(r19)
            L_0x0043:
                return
            L_0x0044:
                r0 = move-exception
                goto L_0x004f
            L_0x0046:
                r0 = move-exception
                goto L_0x0056
            L_0x0048:
                r0 = move-exception
                goto L_0x0082
            L_0x004a:
                r0 = move-exception
                r11 = r17
                r12 = r19
            L_0x004f:
                r13 = r0
                goto L_0x00ae
            L_0x0051:
                r0 = move-exception
                r11 = r17
                r12 = r19
            L_0x0056:
                java.lang.String r13 = "BackupServiceBinder"
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0044 }
                r14.<init>()     // Catch:{ all -> 0x0044 }
                java.lang.String r15 = "onBackup ("
                r14.append(r15)     // Catch:{ all -> 0x0044 }
                android.app.backup.BackupAgent r15 = android.app.backup.BackupAgent.this     // Catch:{ all -> 0x0044 }
                java.lang.Class r15 = r15.getClass()     // Catch:{ all -> 0x0044 }
                java.lang.String r15 = r15.getName()     // Catch:{ all -> 0x0044 }
                r14.append(r15)     // Catch:{ all -> 0x0044 }
                java.lang.String r15 = ") threw"
                r14.append(r15)     // Catch:{ all -> 0x0044 }
                java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x0044 }
                android.util.Log.d(r13, r14, r0)     // Catch:{ all -> 0x0044 }
                throw r0     // Catch:{ all -> 0x0044 }
            L_0x007d:
                r0 = move-exception
                r11 = r17
                r12 = r19
            L_0x0082:
                java.lang.String r13 = "BackupServiceBinder"
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0044 }
                r14.<init>()     // Catch:{ all -> 0x0044 }
                java.lang.String r15 = "onBackup ("
                r14.append(r15)     // Catch:{ all -> 0x0044 }
                android.app.backup.BackupAgent r15 = android.app.backup.BackupAgent.this     // Catch:{ all -> 0x0044 }
                java.lang.Class r15 = r15.getClass()     // Catch:{ all -> 0x0044 }
                java.lang.String r15 = r15.getName()     // Catch:{ all -> 0x0044 }
                r14.append(r15)     // Catch:{ all -> 0x0044 }
                java.lang.String r15 = ") threw"
                r14.append(r15)     // Catch:{ all -> 0x0044 }
                java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x0044 }
                android.util.Log.d(r13, r14, r0)     // Catch:{ all -> 0x0044 }
                java.lang.RuntimeException r13 = new java.lang.RuntimeException     // Catch:{ all -> 0x0044 }
                r13.<init>(r0)     // Catch:{ all -> 0x0044 }
                throw r13     // Catch:{ all -> 0x0044 }
            L_0x00ae:
                android.app.backup.BackupAgent r0 = android.app.backup.BackupAgent.this
                r0.waitForSharedPrefs()
                android.os.Binder.restoreCallingIdentity(r3)
                r2.operationComplete(r9)     // Catch:{ RemoteException -> 0x00ba }
                goto L_0x00bb
            L_0x00ba:
                r0 = move-exception
            L_0x00bb:
                int r0 = android.os.Binder.getCallingPid()
                int r14 = android.os.Process.myPid()
                if (r0 == r14) goto L_0x00ce
                libcore.io.IoUtils.closeQuietly(r17)
                libcore.io.IoUtils.closeQuietly(r18)
                libcore.io.IoUtils.closeQuietly(r19)
            L_0x00ce:
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.backup.BackupAgent.BackupServiceBinder.doBackup(android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor, long, android.app.backup.IBackupCallback, int):void");
        }

        public void doRestore(ParcelFileDescriptor data, long appVersionCode, ParcelFileDescriptor newState, int token, IBackupManager callbackBinder) throws RemoteException {
            long ident = Binder.clearCallingIdentity();
            BackupAgent.this.waitForSharedPrefs();
            try {
                BackupAgent.this.onRestore(new BackupDataInput(data.getFileDescriptor()), appVersionCode, newState);
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0);
                } catch (RemoteException e) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                    IoUtils.closeQuietly(newState);
                }
            } catch (IOException ex) {
                Log.d(TAG, "onRestore (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                throw new RuntimeException(ex);
            } catch (RuntimeException ex2) {
                Log.d(TAG, "onRestore (" + BackupAgent.this.getClass().getName() + ") threw", ex2);
                throw ex2;
            } catch (Throwable th) {
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0);
                } catch (RemoteException e2) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                    IoUtils.closeQuietly(newState);
                }
                throw th;
            }
        }

        public void doFullBackup(ParcelFileDescriptor data, long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) {
            long ident = Binder.clearCallingIdentity();
            BackupAgent.this.waitForSharedPrefs();
            try {
                BackupAgent.this.onFullBackup(new FullBackupDataOutput(data, quotaBytes, transportFlags));
                BackupAgent.this.waitForSharedPrefs();
                try {
                    new FileOutputStream(data.getFileDescriptor()).write(new byte[4]);
                } catch (IOException e) {
                    Log.e(TAG, "Unable to finalize backup stream!");
                }
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0);
                } catch (RemoteException e2) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                }
            } catch (IOException ex) {
                Log.d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                throw new RuntimeException(ex);
            } catch (RuntimeException ex2) {
                Log.d(TAG, "onFullBackup (" + BackupAgent.this.getClass().getName() + ") threw", ex2);
                throw ex2;
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                try {
                    new FileOutputStream(data.getFileDescriptor()).write(new byte[4]);
                } catch (IOException e3) {
                    Log.e(TAG, "Unable to finalize backup stream!");
                }
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0);
                } catch (RemoteException e4) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                }
                throw th;
            }
        }

        public void doMeasureFullBackup(long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags) {
            long ident = Binder.clearCallingIdentity();
            FullBackupDataOutput measureOutput = new FullBackupDataOutput(quotaBytes, transportFlags);
            BackupAgent.this.waitForSharedPrefs();
            try {
                BackupAgent.this.onFullBackup(measureOutput);
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, measureOutput.getSize());
                } catch (RemoteException e) {
                }
            } catch (IOException ex) {
                Log.d(TAG, "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw", ex);
                throw new RuntimeException(ex);
            } catch (RuntimeException ex2) {
                Log.d(TAG, "onFullBackup[M] (" + BackupAgent.this.getClass().getName() + ") threw", ex2);
                throw ex2;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, measureOutput.getSize());
                } catch (RemoteException e2) {
                }
                throw th;
            }
        }

        public void doRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime, int token, IBackupManager callbackBinder) throws RemoteException {
            int i = token;
            IBackupManager iBackupManager = callbackBinder;
            long ident = Binder.clearCallingIdentity();
            try {
                BackupAgent.this.onRestoreFile(data, size, type, domain, path, mode, mtime);
                BackupAgent.this.waitForSharedPrefs();
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(ident);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0);
                } catch (RemoteException e) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                }
            } catch (IOException e2) {
                Log.d(TAG, "onRestoreFile (" + BackupAgent.this.getClass().getName() + ") threw", e2);
                throw new RuntimeException(e2);
            } catch (Throwable th) {
                Throwable th2 = th;
                BackupAgent.this.waitForSharedPrefs();
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(ident);
                try {
                    iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), i, 0);
                } catch (RemoteException e3) {
                }
                if (Binder.getCallingPid() != Process.myPid()) {
                    IoUtils.closeQuietly(data);
                }
                throw th2;
            }
        }

        public void doRestoreFinished(int token, IBackupManager callbackBinder) {
            long ident = Binder.clearCallingIdentity();
            try {
                BackupAgent.this.onRestoreFinished();
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0);
                } catch (RemoteException e) {
                }
            } catch (Exception e2) {
                Log.d(TAG, "onRestoreFinished (" + BackupAgent.this.getClass().getName() + ") threw", e2);
                throw e2;
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.opCompleteForUser(BackupAgent.this.getBackupUserId(), token, 0);
                } catch (RemoteException e3) {
                }
                throw th;
            }
        }

        public void fail(String message) {
            BackupAgent.this.getHandler().post(new FailRunnable(message));
        }

        public void doQuotaExceeded(long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder) {
            long ident = Binder.clearCallingIdentity();
            try {
                BackupAgent.this.onQuotaExceeded(backupDataBytes, quotaBytes);
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.operationComplete(0);
                } catch (RemoteException e) {
                }
            } catch (Exception e2) {
                Log.d(TAG, "onQuotaExceeded(" + BackupAgent.this.getClass().getName() + ") threw", e2);
                throw e2;
            } catch (Throwable th) {
                BackupAgent.this.waitForSharedPrefs();
                Binder.restoreCallingIdentity(ident);
                try {
                    callbackBinder.operationComplete(-1);
                } catch (RemoteException e3) {
                }
                throw th;
            }
        }
    }

    static class FailRunnable implements Runnable {
        private String mMessage;

        FailRunnable(String message) {
            this.mMessage = message;
        }

        public void run() {
            throw new IllegalStateException(this.mMessage);
        }
    }
}
