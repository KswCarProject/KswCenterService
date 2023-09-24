package android.support.p014v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.p007os.Build;
import android.support.p011v4.view.ActionProvider;
import android.support.p014v7.appcompat.C2132R;
import android.support.p014v7.content.res.AppCompatResources;
import android.support.p014v7.widget.ActivityChooserModel;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* renamed from: android.support.v7.widget.ShareActionProvider */
/* loaded from: classes3.dex */
public class ShareActionProvider extends ActionProvider {
    private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    final Context mContext;
    private int mMaxShownActivityCount;
    private ActivityChooserModel.OnChooseActivityListener mOnChooseActivityListener;
    private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener;
    OnShareTargetSelectedListener mOnShareTargetSelectedListener;
    String mShareHistoryFileName;

    /* renamed from: android.support.v7.widget.ShareActionProvider$OnShareTargetSelectedListener */
    /* loaded from: classes3.dex */
    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent);
    }

    public ShareActionProvider(Context context) {
        super(context);
        this.mMaxShownActivityCount = 4;
        this.mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener();
        this.mShareHistoryFileName = "share_history.xml";
        this.mContext = context;
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener listener) {
        this.mOnShareTargetSelectedListener = listener;
        setActivityChooserPolicyIfNeeded();
    }

    @Override // android.support.p011v4.view.ActionProvider
    public View onCreateActionView() {
        ActivityChooserView activityChooserView = new ActivityChooserView(this.mContext);
        if (!activityChooserView.isInEditMode()) {
            ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
            activityChooserView.setActivityChooserModel(dataModel);
        }
        TypedValue outTypedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(C2132R.attr.actionModeShareDrawable, outTypedValue, true);
        Drawable drawable = AppCompatResources.getDrawable(this.mContext, outTypedValue.resourceId);
        activityChooserView.setExpandActivityOverflowButtonDrawable(drawable);
        activityChooserView.setProvider(this);
        activityChooserView.setDefaultActionButtonContentDescription(C2132R.string.abc_shareactionprovider_share_with_application);
        activityChooserView.setExpandActivityOverflowButtonContentDescription(C2132R.string.abc_shareactionprovider_share_with);
        return activityChooserView;
    }

    @Override // android.support.p011v4.view.ActionProvider
    public boolean hasSubMenu() {
        return true;
    }

    @Override // android.support.p011v4.view.ActionProvider
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        PackageManager packageManager = this.mContext.getPackageManager();
        int expandedActivityCount = dataModel.getActivityCount();
        int collapsedActivityCount = Math.min(expandedActivityCount, this.mMaxShownActivityCount);
        for (int i = 0; i < collapsedActivityCount; i++) {
            ResolveInfo activity = dataModel.getActivity(i);
            subMenu.add(0, i, i, activity.loadLabel(packageManager)).setIcon(activity.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        }
        if (collapsedActivityCount < expandedActivityCount) {
            SubMenu expandedSubMenu = subMenu.addSubMenu(0, collapsedActivityCount, collapsedActivityCount, this.mContext.getString(C2132R.string.abc_activity_chooser_view_see_all));
            for (int i2 = 0; i2 < expandedActivityCount; i2++) {
                ResolveInfo activity2 = dataModel.getActivity(i2);
                expandedSubMenu.add(0, i2, i2, activity2.loadLabel(packageManager)).setIcon(activity2.loadIcon(packageManager)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
            }
        }
    }

    public void setShareHistoryFileName(String shareHistoryFile) {
        this.mShareHistoryFileName = shareHistoryFile;
        setActivityChooserPolicyIfNeeded();
    }

    public void setShareIntent(Intent shareIntent) {
        if (shareIntent != null) {
            String action = shareIntent.getAction();
            if (Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action)) {
                updateIntent(shareIntent);
            }
        }
        ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        dataModel.setIntent(shareIntent);
    }

    /* renamed from: android.support.v7.widget.ShareActionProvider$ShareMenuItemOnMenuItemClickListener */
    /* loaded from: classes3.dex */
    private class ShareMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        ShareMenuItemOnMenuItemClickListener() {
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem item) {
            ActivityChooserModel dataModel = ActivityChooserModel.get(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName);
            int itemId = item.getItemId();
            Intent launchIntent = dataModel.chooseActivity(itemId);
            if (launchIntent != null) {
                String action = launchIntent.getAction();
                if (Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action)) {
                    ShareActionProvider.this.updateIntent(launchIntent);
                }
                ShareActionProvider.this.mContext.startActivity(launchIntent);
                return true;
            }
            return true;
        }
    }

    private void setActivityChooserPolicyIfNeeded() {
        if (this.mOnShareTargetSelectedListener == null) {
            return;
        }
        if (this.mOnChooseActivityListener == null) {
            this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy();
        }
        ActivityChooserModel dataModel = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        dataModel.setOnChooseActivityListener(this.mOnChooseActivityListener);
    }

    /* renamed from: android.support.v7.widget.ShareActionProvider$ShareActivityChooserModelPolicy */
    /* loaded from: classes3.dex */
    private class ShareActivityChooserModelPolicy implements ActivityChooserModel.OnChooseActivityListener {
        ShareActivityChooserModelPolicy() {
        }

        @Override // android.support.p014v7.widget.ActivityChooserModel.OnChooseActivityListener
        public boolean onChooseActivity(ActivityChooserModel host, Intent intent) {
            if (ShareActionProvider.this.mOnShareTargetSelectedListener != null) {
                ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, intent);
                return false;
            }
            return false;
        }
    }

    void updateIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            intent.addFlags(134742016);
        } else {
            intent.addFlags(524288);
        }
    }
}