package com.android.server.backup;

import android.app.IWallpaperManager;
import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupHelper;
import android.app.backup.FullBackup;
import android.app.backup.FullBackupDataOutput;
import android.app.backup.WallpaperBackupHelper;
import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.google.android.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SystemBackupAgent extends BackupAgentHelper {
    private static final String ACCOUNT_MANAGER_HELPER = "account_manager";
    private static final String NOTIFICATION_HELPER = "notifications";
    private static final String PERMISSION_HELPER = "permissions";
    private static final String PREFERRED_HELPER = "preferred_activities";
    private static final String SHORTCUT_MANAGER_HELPER = "shortcut_manager";
    private static final String SLICES_HELPER = "slices";
    private static final String SYNC_SETTINGS_HELPER = "account_sync_settings";
    private static final String TAG = "SystemBackupAgent";
    private static final String USAGE_STATS_HELPER = "usage_stats";
    private static final String WALLPAPER_HELPER = "wallpaper";
    public static final String WALLPAPER_IMAGE = new File(Environment.getUserSystemDirectory(0), Context.WALLPAPER_SERVICE).getAbsolutePath();
    private static final String WALLPAPER_IMAGE_DIR = Environment.getUserSystemDirectory(0).getAbsolutePath();
    private static final String WALLPAPER_IMAGE_FILENAME = "wallpaper";
    private static final String WALLPAPER_IMAGE_KEY = "/data/data/com.android.settings/files/wallpaper";
    public static final String WALLPAPER_INFO = new File(Environment.getUserSystemDirectory(0), WALLPAPER_INFO_FILENAME).getAbsolutePath();
    private static final String WALLPAPER_INFO_DIR = Environment.getUserSystemDirectory(0).getAbsolutePath();
    private static final String WALLPAPER_INFO_FILENAME = "wallpaper_info.xml";
    private static final Set<String> sEligibleForMultiUser = Sets.newArraySet(PERMISSION_HELPER, NOTIFICATION_HELPER, SYNC_SETTINGS_HELPER);
    private int mUserId = 0;

    public void onCreate(UserHandle user) {
        super.onCreate(user);
        this.mUserId = user.getIdentifier();
        addHelper(SYNC_SETTINGS_HELPER, new AccountSyncSettingsBackupHelper(this, this.mUserId));
        addHelper(PREFERRED_HELPER, new PreferredActivityBackupHelper());
        addHelper(NOTIFICATION_HELPER, new NotificationBackupHelper(this.mUserId));
        addHelper(PERMISSION_HELPER, new PermissionBackupHelper(this.mUserId));
        addHelper(USAGE_STATS_HELPER, new UsageStatsBackupHelper(this));
        addHelper(SHORTCUT_MANAGER_HELPER, new ShortcutBackupHelper());
        addHelper(ACCOUNT_MANAGER_HELPER, new AccountManagerBackupHelper());
        addHelper(SLICES_HELPER, new SliceBackupHelper(this));
    }

    public void onFullBackup(FullBackupDataOutput data) throws IOException {
    }

    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        addHelper(Context.WALLPAPER_SERVICE, new WallpaperBackupHelper(this, new String[]{"/data/data/com.android.settings/files/wallpaper"}));
        addHelper("system_files", new WallpaperBackupHelper(this, new String[]{"/data/data/com.android.settings/files/wallpaper"}));
        super.onRestore(data, appVersionCode, newState);
    }

    public void addHelper(String keyPrefix, BackupHelper helper) {
        if (this.mUserId == 0 || sEligibleForMultiUser.contains(keyPrefix)) {
            super.addHelper(keyPrefix, helper);
        }
    }

    public void onRestoreFile(ParcelFileDescriptor data, long size, int type, String domain, String path, long mode, long mtime) throws IOException {
        IWallpaperManager wallpaper;
        String str = domain;
        String str2 = path;
        Slog.i(TAG, "Restoring file domain=" + str + " path=" + str2);
        boolean restoredWallpaper = false;
        File outFile = null;
        if (str.equals("r")) {
            if (str2.equals(WALLPAPER_INFO_FILENAME)) {
                outFile = new File(WALLPAPER_INFO);
                restoredWallpaper = true;
            } else if (str2.equals(Context.WALLPAPER_SERVICE)) {
                outFile = new File(WALLPAPER_IMAGE);
                restoredWallpaper = true;
            }
        }
        boolean restoredWallpaper2 = restoredWallpaper;
        if (outFile == null) {
            try {
                Slog.w(TAG, "Skipping unrecognized system file: [ " + str + " : " + str2 + " ]");
            } catch (IOException e) {
                if (restoredWallpaper2) {
                    new File(WALLPAPER_IMAGE).delete();
                    new File(WALLPAPER_INFO).delete();
                    return;
                }
                return;
            }
        }
        FullBackup.restoreFile(data, size, type, mode, mtime, outFile);
        if (restoredWallpaper2 && (wallpaper = (IWallpaperManager) ServiceManager.getService(Context.WALLPAPER_SERVICE)) != null) {
            try {
                wallpaper.settingsRestored();
            } catch (RemoteException e2) {
                RemoteException re = e2;
                Slog.e(TAG, "Couldn't restore settings\n" + re);
            }
        }
    }
}
