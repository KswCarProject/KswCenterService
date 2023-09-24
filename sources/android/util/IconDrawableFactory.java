package android.util;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageItemInfo;
import android.content.p002pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.p007os.UserHandle;
import android.p007os.UserManager;
import com.android.internal.C3132R;
import com.android.internal.annotations.VisibleForTesting;

/* loaded from: classes4.dex */
public class IconDrawableFactory {
    @VisibleForTesting
    public static final int[] CORP_BADGE_COLORS = {C3132R.color.profile_badge_1, C3132R.color.profile_badge_2, C3132R.color.profile_badge_3};
    protected final Context mContext;
    protected final boolean mEmbedShadow;
    protected final LauncherIcons mLauncherIcons;
    protected final PackageManager mPm;
    protected final UserManager mUm;

    private IconDrawableFactory(Context context, boolean embedShadow) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mUm = (UserManager) context.getSystemService(UserManager.class);
        this.mLauncherIcons = new LauncherIcons(context);
        this.mEmbedShadow = embedShadow;
    }

    protected boolean needsBadging(ApplicationInfo appInfo, int userId) {
        return appInfo.isInstantApp() || this.mUm.isManagedProfile(userId);
    }

    @UnsupportedAppUsage
    public Drawable getBadgedIcon(ApplicationInfo appInfo) {
        return getBadgedIcon(appInfo, UserHandle.getUserId(appInfo.uid));
    }

    public Drawable getBadgedIcon(ApplicationInfo appInfo, int userId) {
        return getBadgedIcon(appInfo, appInfo, userId);
    }

    @UnsupportedAppUsage
    public Drawable getBadgedIcon(PackageItemInfo itemInfo, ApplicationInfo appInfo, int userId) {
        Drawable icon = this.mPm.loadUnbadgedItemIcon(itemInfo, appInfo);
        if (!this.mEmbedShadow && !needsBadging(appInfo, userId)) {
            return icon;
        }
        Drawable icon2 = getShadowedIcon(icon);
        if (appInfo.isInstantApp()) {
            int badgeColor = Resources.getSystem().getColor(C3132R.color.instant_app_badge, null);
            icon2 = this.mLauncherIcons.getBadgedDrawable(icon2, C3132R.C3133drawable.ic_instant_icon_badge_bolt, badgeColor);
        }
        if (this.mUm.isManagedProfile(userId)) {
            return this.mLauncherIcons.getBadgedDrawable(icon2, C3132R.C3133drawable.ic_corp_icon_badge_case, getUserBadgeColor(this.mUm, userId));
        }
        return icon2;
    }

    public Drawable getShadowedIcon(Drawable icon) {
        return this.mLauncherIcons.wrapIconDrawableWithShadow(icon);
    }

    public static int getUserBadgeColor(UserManager um, int userId) {
        int badge = um.getManagedProfileBadge(userId);
        if (badge < 0) {
            badge = 0;
        }
        int resourceId = CORP_BADGE_COLORS[badge % CORP_BADGE_COLORS.length];
        return Resources.getSystem().getColor(resourceId, null);
    }

    @UnsupportedAppUsage
    public static IconDrawableFactory newInstance(Context context) {
        return new IconDrawableFactory(context, true);
    }

    public static IconDrawableFactory newInstance(Context context, boolean embedShadow) {
        return new IconDrawableFactory(context, embedShadow);
    }
}
