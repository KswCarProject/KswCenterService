package com.android.server.backup;

import android.accounts.AccountManagerInternal;
import android.app.backup.BlobBackupHelper;
import android.util.Slog;
import com.android.server.LocalServices;

/* loaded from: classes4.dex */
public class AccountManagerBackupHelper extends BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final String KEY_ACCOUNT_ACCESS_GRANTS = "account_access_grants";
    private static final int STATE_VERSION = 1;
    private static final String TAG = "AccountsBackup";

    public AccountManagerBackupHelper() {
        super(1, KEY_ACCOUNT_ACCESS_GRANTS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
        android.util.Slog.m50w(com.android.server.backup.AccountManagerBackupHelper.TAG, "Unexpected backup key " + r7);
     */
    @Override // android.app.backup.BlobBackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected byte[] getBackupPayload(String key) {
        AccountManagerInternal am = (AccountManagerInternal) LocalServices.getService(AccountManagerInternal.class);
        char c = '\uffff';
        try {
            if (key.hashCode() == 1544100736 && key.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                c = 0;
            }
            return am.backupAccountAccessPermissions(0);
        } catch (Exception e) {
            Slog.m56e(TAG, "Unable to store payload " + key);
        }
        return new byte[0];
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
        android.util.Slog.m50w(com.android.server.backup.AccountManagerBackupHelper.TAG, "Unexpected restore key " + r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    @Override // android.app.backup.BlobBackupHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void applyRestoredPayload(String key, byte[] payload) {
        AccountManagerInternal am = (AccountManagerInternal) LocalServices.getService(AccountManagerInternal.class);
        char c = '\uffff';
        try {
            if (key.hashCode() == 1544100736 && key.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                c = 0;
            }
            am.restoreAccountAccessPermissions(payload, 0);
        } catch (Exception e) {
            Slog.m50w(TAG, "Unable to restore key " + key);
        }
    }
}
