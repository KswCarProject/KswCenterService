package android.app.role;

import android.annotation.SystemApi;
import android.p007os.UserHandle;

@SystemApi
/* loaded from: classes.dex */
public interface OnRoleHoldersChangedListener {
    void onRoleHoldersChanged(String str, UserHandle userHandle);
}
