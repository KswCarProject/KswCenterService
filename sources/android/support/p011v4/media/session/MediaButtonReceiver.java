package android.support.p011v4.media.session;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.p007os.Build;
import android.p007os.RemoteException;
import android.support.p011v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

/* renamed from: android.support.v4.media.session.MediaButtonReceiver */
/* loaded from: classes3.dex */
public class MediaButtonReceiver extends BroadcastReceiver {
    private static final String TAG = "MediaButtonReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction()) || !intent.hasExtra(Intent.EXTRA_KEY_EVENT)) {
            Log.m72d(TAG, "Ignore unsupported intent: " + intent);
            return;
        }
        ComponentName mediaButtonServiceComponentName = getServiceComponentByAction(context, Intent.ACTION_MEDIA_BUTTON);
        if (mediaButtonServiceComponentName != null) {
            intent.setComponent(mediaButtonServiceComponentName);
            startForegroundService(context, intent);
            return;
        }
        ComponentName mediaBrowserServiceComponentName = getServiceComponentByAction(context, "android.media.browse.MediaBrowserService");
        if (mediaBrowserServiceComponentName != null) {
            BroadcastReceiver.PendingResult pendingResult = goAsync();
            Context applicationContext = context.getApplicationContext();
            MediaButtonConnectionCallback connectionCallback = new MediaButtonConnectionCallback(applicationContext, intent, pendingResult);
            MediaBrowserCompat mediaBrowser = new MediaBrowserCompat(applicationContext, mediaBrowserServiceComponentName, connectionCallback, null);
            connectionCallback.setMediaBrowser(mediaBrowser);
            mediaBrowser.connect();
            return;
        }
        throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
    }

    /* renamed from: android.support.v4.media.session.MediaButtonReceiver$MediaButtonConnectionCallback */
    /* loaded from: classes3.dex */
    private static class MediaButtonConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        private final Context mContext;
        private final Intent mIntent;
        private MediaBrowserCompat mMediaBrowser;
        private final BroadcastReceiver.PendingResult mPendingResult;

        MediaButtonConnectionCallback(Context context, Intent intent, BroadcastReceiver.PendingResult pendingResult) {
            this.mContext = context;
            this.mIntent = intent;
            this.mPendingResult = pendingResult;
        }

        void setMediaBrowser(MediaBrowserCompat mediaBrowser) {
            this.mMediaBrowser = mediaBrowser;
        }

        @Override // android.support.p011v4.media.MediaBrowserCompat.ConnectionCallback
        public void onConnected() {
            try {
                MediaControllerCompat mediaController = new MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken());
                KeyEvent ke = (KeyEvent) this.mIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                mediaController.dispatchMediaButtonEvent(ke);
            } catch (RemoteException e) {
                Log.m69e(MediaButtonReceiver.TAG, "Failed to create a media controller", e);
            }
            finish();
        }

        @Override // android.support.p011v4.media.MediaBrowserCompat.ConnectionCallback
        public void onConnectionSuspended() {
            finish();
        }

        @Override // android.support.p011v4.media.MediaBrowserCompat.ConnectionCallback
        public void onConnectionFailed() {
            finish();
        }

        private void finish() {
            this.mMediaBrowser.disconnect();
            this.mPendingResult.finish();
        }
    }

    public static KeyEvent handleIntent(MediaSessionCompat mediaSessionCompat, Intent intent) {
        if (mediaSessionCompat == null || intent == null || !Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction()) || !intent.hasExtra(Intent.EXTRA_KEY_EVENT)) {
            return null;
        }
        KeyEvent ke = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        MediaControllerCompat mediaController = mediaSessionCompat.getController();
        mediaController.dispatchMediaButtonEvent(ke);
        return ke;
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context context, long action) {
        ComponentName mbrComponent = getMediaButtonReceiverComponent(context);
        if (mbrComponent == null) {
            Log.m64w(TAG, "A unique media button receiver could not be found in the given context, so couldn't build a pending intent.");
            return null;
        }
        return buildMediaButtonPendingIntent(context, mbrComponent, action);
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context context, ComponentName mbrComponent, long action) {
        if (mbrComponent == null) {
            Log.m64w(TAG, "The component name of media button receiver should be provided.");
            return null;
        }
        int keyCode = PlaybackStateCompat.toKeyCode(action);
        if (keyCode == 0) {
            Log.m64w(TAG, "Cannot build a media button pending intent with the given action: " + action);
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(mbrComponent);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(0, keyCode));
        return PendingIntent.getBroadcast(context, keyCode, intent, 0);
    }

    static ComponentName getMediaButtonReceiverComponent(Context context) {
        Intent queryIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        queryIntent.setPackage(context.getPackageName());
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(queryIntent, 0);
        if (resolveInfos.size() == 1) {
            ResolveInfo resolveInfo = resolveInfos.get(0);
            return new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        } else if (resolveInfos.size() > 1) {
            Log.m64w(TAG, "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.");
            return null;
        } else {
            return null;
        }
    }

    private static void startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    private static ComponentName getServiceComponentByAction(Context context, String action) {
        PackageManager pm = context.getPackageManager();
        Intent queryIntent = new Intent(action);
        queryIntent.setPackage(context.getPackageName());
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(queryIntent, 0);
        if (resolveInfos.size() == 1) {
            ResolveInfo resolveInfo = resolveInfos.get(0);
            return new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        } else if (resolveInfos.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Expected 1 service that handles " + action + ", found " + resolveInfos.size());
        }
    }
}
