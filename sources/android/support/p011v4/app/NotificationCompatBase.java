package android.support.p011v4.app;

import android.app.PendingIntent;
import android.p007os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.p011v4.app.RemoteInputCompatBase;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.app.NotificationCompatBase */
/* loaded from: classes3.dex */
public class NotificationCompatBase {

    /* renamed from: android.support.v4.app.NotificationCompatBase$Action */
    /* loaded from: classes3.dex */
    public static abstract class Action {

        /* renamed from: android.support.v4.app.NotificationCompatBase$Action$Factory */
        /* loaded from: classes3.dex */
        public interface Factory {
            Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInputCompatBase.RemoteInput[] remoteInputArr, RemoteInputCompatBase.RemoteInput[] remoteInputArr2, boolean z);

            Action[] newArray(int i);
        }

        public abstract PendingIntent getActionIntent();

        public abstract boolean getAllowGeneratedReplies();

        public abstract RemoteInputCompatBase.RemoteInput[] getDataOnlyRemoteInputs();

        public abstract Bundle getExtras();

        public abstract int getIcon();

        public abstract RemoteInputCompatBase.RemoteInput[] getRemoteInputs();

        public abstract CharSequence getTitle();
    }

    /* renamed from: android.support.v4.app.NotificationCompatBase$UnreadConversation */
    /* loaded from: classes3.dex */
    public static abstract class UnreadConversation {

        /* renamed from: android.support.v4.app.NotificationCompatBase$UnreadConversation$Factory */
        /* loaded from: classes3.dex */
        public interface Factory {
            UnreadConversation build(String[] strArr, RemoteInputCompatBase.RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j);
        }

        abstract long getLatestTimestamp();

        abstract String[] getMessages();

        abstract String getParticipant();

        abstract String[] getParticipants();

        abstract PendingIntent getReadPendingIntent();

        abstract RemoteInputCompatBase.RemoteInput getRemoteInput();

        abstract PendingIntent getReplyPendingIntent();
    }
}
