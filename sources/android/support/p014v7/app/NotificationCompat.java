package android.support.p014v7.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.p007os.Build;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.support.p011v4.app.BundleCompat;
import android.support.p011v4.app.NotificationCompat;
import android.support.p011v4.media.app.NotificationCompat;
import android.support.p011v4.media.session.MediaSessionCompat;

@Deprecated
/* renamed from: android.support.v7.app.NotificationCompat */
/* loaded from: classes3.dex */
public class NotificationCompat extends android.support.p011v4.app.NotificationCompat {

    @Deprecated
    /* renamed from: android.support.v7.app.NotificationCompat$DecoratedCustomViewStyle */
    /* loaded from: classes3.dex */
    public static class DecoratedCustomViewStyle extends NotificationCompat.DecoratedCustomViewStyle {
    }

    @Deprecated
    /* renamed from: android.support.v7.app.NotificationCompat$DecoratedMediaCustomViewStyle */
    /* loaded from: classes3.dex */
    public static class DecoratedMediaCustomViewStyle extends NotificationCompat.DecoratedMediaCustomViewStyle {
    }

    @Deprecated
    public static MediaSessionCompat.Token getMediaSession(Notification notification) {
        Bundle extras = getExtras(notification);
        if (extras != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                Object tokenInner = extras.getParcelable("android.mediaSession");
                if (tokenInner != null) {
                    return MediaSessionCompat.Token.fromToken(tokenInner);
                }
                return null;
            }
            IBinder tokenInner2 = BundleCompat.getBinder(extras, "android.mediaSession");
            if (tokenInner2 != null) {
                Parcel p = Parcel.obtain();
                p.writeStrongBinder(tokenInner2);
                p.setDataPosition(0);
                MediaSessionCompat.Token token = MediaSessionCompat.Token.CREATOR.createFromParcel(p);
                p.recycle();
                return token;
            }
            return null;
        }
        return null;
    }

    @Deprecated
    /* renamed from: android.support.v7.app.NotificationCompat$Builder */
    /* loaded from: classes3.dex */
    public static class Builder extends NotificationCompat.Builder {
        @Deprecated
        public Builder(Context context) {
            super(context);
        }
    }

    @Deprecated
    /* renamed from: android.support.v7.app.NotificationCompat$MediaStyle */
    /* loaded from: classes3.dex */
    public static class MediaStyle extends NotificationCompat.MediaStyle {
        @Deprecated
        public MediaStyle() {
        }

        @Deprecated
        public MediaStyle(NotificationCompat.Builder builder) {
            super(builder);
        }

        @Override // android.support.p011v4.media.app.NotificationCompat.MediaStyle
        @Deprecated
        public MediaStyle setShowActionsInCompactView(int... actions) {
            return (MediaStyle) super.setShowActionsInCompactView(actions);
        }

        @Override // android.support.p011v4.media.app.NotificationCompat.MediaStyle
        @Deprecated
        public MediaStyle setMediaSession(MediaSessionCompat.Token token) {
            return (MediaStyle) super.setMediaSession(token);
        }

        @Override // android.support.p011v4.media.app.NotificationCompat.MediaStyle
        @Deprecated
        public MediaStyle setShowCancelButton(boolean show) {
            return (MediaStyle) super.setShowCancelButton(show);
        }

        @Override // android.support.p011v4.media.app.NotificationCompat.MediaStyle
        @Deprecated
        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            return (MediaStyle) super.setCancelButtonIntent(pendingIntent);
        }
    }
}
