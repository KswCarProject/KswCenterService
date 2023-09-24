package android.content.p002pm;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import android.p007os.UserManager;
import com.android.internal.C3132R;
import com.android.internal.util.UserIcons;
import java.util.List;

/* renamed from: android.content.pm.CrossProfileApps */
/* loaded from: classes.dex */
public class CrossProfileApps {
    private final Context mContext;
    private final Resources mResources;
    private final ICrossProfileApps mService;
    private final UserManager mUserManager;

    public CrossProfileApps(Context context, ICrossProfileApps service) {
        this.mContext = context;
        this.mService = service;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mResources = context.getResources();
    }

    public void startMainActivity(ComponentName component, UserHandle targetUser) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), component, targetUser.getIdentifier(), true);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startActivity(ComponentName component, UserHandle targetUser) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), component, targetUser.getIdentifier(), false);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getTargetUserProfiles() {
        try {
            return this.mService.getTargetUserProfiles(this.mContext.getPackageName());
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public CharSequence getProfileSwitchingLabel(UserHandle userHandle) {
        int stringRes;
        verifyCanAccessUser(userHandle);
        if (this.mUserManager.isManagedProfile(userHandle.getIdentifier())) {
            stringRes = C3132R.string.managed_profile_label;
        } else {
            stringRes = C3132R.string.user_owner_label;
        }
        return this.mResources.getString(stringRes);
    }

    public Drawable getProfileSwitchingIconDrawable(UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        boolean isManagedProfile = this.mUserManager.isManagedProfile(userHandle.getIdentifier());
        if (isManagedProfile) {
            return this.mResources.getDrawable(C3132R.C3133drawable.ic_corp_badge, null);
        }
        return UserIcons.getDefaultUserIcon(this.mResources, 0, true);
    }

    private void verifyCanAccessUser(UserHandle userHandle) {
        if (!getTargetUserProfiles().contains(userHandle)) {
            throw new SecurityException("Not allowed to access " + userHandle);
        }
    }
}
