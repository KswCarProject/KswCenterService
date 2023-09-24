package com.android.server.backup;

import android.app.backup.BlobBackupHelper;
import android.p007os.UserHandle;
import android.permission.PermissionManagerInternal;
import android.util.Slog;
import com.android.server.LocalServices;

/* loaded from: classes4.dex */
public class PermissionBackupHelper extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_PERMISSIONS = "permissions";
    private static final int STATE_VERSION = 1;
    private static final String TAG = "PermissionBackup";
    private final PermissionManagerInternal mPermissionManager;
    private final UserHandle mUser;

    public PermissionBackupHelper(int userId) {
        super(1, KEY_PERMISSIONS);
        this.mUser = UserHandle.m110of(userId);
        this.mPermissionManager = (PermissionManagerInternal) LocalServices.getService(PermissionManagerInternal.class);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
        android.util.Slog.m50w(com.android.server.backup.PermissionBackupHelper.TAG, "Unexpected backup key " + r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
        return null;
     */
    @Override // android.app.backup.BlobBackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected byte[] getBackupPayload(String key) {
        char c = '\uffff';
        try {
            if (key.hashCode() == 1133704324 && key.equals(KEY_PERMISSIONS)) {
                c = 0;
            }
            return this.mPermissionManager.backupRuntimePermissions(this.mUser);
        } catch (Exception e) {
            Slog.m56e(TAG, "Unable to store payload " + key);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
        android.util.Slog.m50w(com.android.server.backup.PermissionBackupHelper.TAG, "Unexpected restore key " + r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    @Override // android.app.backup.BlobBackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void applyRestoredPayload(String key, byte[] payload) {
        char c = '\uffff';
        try {
            if (key.hashCode() == 1133704324 && key.equals(KEY_PERMISSIONS)) {
                c = 0;
            }
            this.mPermissionManager.restoreRuntimePermissions(payload, this.mUser);
        } catch (Exception e) {
            Slog.m50w(TAG, "Unable to restore key " + key);
        }
    }
}
