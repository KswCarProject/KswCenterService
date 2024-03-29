package android.support.p011v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.p007os.Bundle;
import android.p007os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.p011v4.app.NotificationCompatBase;
import android.support.p011v4.app.RemoteInputCompatBase;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(24)
/* renamed from: android.support.v4.app.NotificationCompatApi24 */
/* loaded from: classes3.dex */
class NotificationCompatApi24 {
    NotificationCompatApi24() {
    }

    /* renamed from: android.support.v4.app.NotificationCompatApi24$Builder */
    /* loaded from: classes3.dex */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private Notification.Builder f263b;
        private int mGroupAlertBehavior;

        public Builder(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate, boolean showWhen, boolean useChronometer, int priority, CharSequence subText, boolean localOnly, String category, ArrayList<String> people, Bundle extras, int color, int visibility, Notification publicVersion, String groupKey, boolean groupSummary, String sortKey, CharSequence[] remoteInputHistory, RemoteViews contentView, RemoteViews bigContentView, RemoteViews headsUpContentView, int groupAlertBehavior) {
            this.f263b = new Notification.Builder(context).setWhen(n.when).setShowWhen(showWhen).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, tickerView).setSound(n.sound, n.audioStreamType).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(fullScreenIntent, (n.flags & 128) != 0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate).setLocalOnly(localOnly).setExtras(extras).setGroup(groupKey).setGroupSummary(groupSummary).setSortKey(sortKey).setCategory(category).setColor(color).setVisibility(visibility).setPublicVersion(publicVersion).setRemoteInputHistory(remoteInputHistory);
            if (contentView != null) {
                this.f263b.setCustomContentView(contentView);
            }
            if (bigContentView != null) {
                this.f263b.setCustomBigContentView(bigContentView);
            }
            if (headsUpContentView != null) {
                this.f263b.setCustomHeadsUpContentView(headsUpContentView);
            }
            Iterator<String> it = people.iterator();
            while (it.hasNext()) {
                String person = it.next();
                this.f263b.addPerson(person);
            }
            this.mGroupAlertBehavior = groupAlertBehavior;
        }

        @Override // android.support.p011v4.app.NotificationBuilderWithActions
        public void addAction(NotificationCompatBase.Action action) {
            NotificationCompatApi24.addAction(this.f263b, action);
        }

        @Override // android.support.p011v4.app.NotificationBuilderWithBuilderAccessor
        public Notification.Builder getBuilder() {
            return this.f263b;
        }

        @Override // android.support.p011v4.app.NotificationBuilderWithBuilderAccessor
        public Notification build() {
            Notification notification = this.f263b.build();
            if (this.mGroupAlertBehavior != 0) {
                if (notification.getGroup() != null && (notification.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                    removeSoundAndVibration(notification);
                }
                if (notification.getGroup() != null && (notification.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notification);
                }
            }
            return notification;
        }

        private void removeSoundAndVibration(Notification notification) {
            notification.sound = null;
            notification.vibrate = null;
            notification.defaults &= -2;
            notification.defaults &= -3;
        }
    }

    public static void addMessagingStyle(NotificationBuilderWithBuilderAccessor b, CharSequence userDisplayName, CharSequence conversationTitle, List<CharSequence> texts, List<Long> timestamps, List<CharSequence> senders, List<String> dataMimeTypes, List<Uri> dataUris) {
        Notification.MessagingStyle style = new Notification.MessagingStyle(userDisplayName).setConversationTitle(conversationTitle);
        for (int i = 0; i < texts.size(); i++) {
            Notification.MessagingStyle.Message message = new Notification.MessagingStyle.Message(texts.get(i), timestamps.get(i).longValue(), senders.get(i));
            if (dataMimeTypes.get(i) != null) {
                message.setData(dataMimeTypes.get(i), dataUris.get(i));
            }
            style.addMessage(message);
        }
        style.setBuilder(b.getBuilder());
    }

    public static void addAction(Notification.Builder b, NotificationCompatBase.Action action) {
        Bundle actionExtras;
        RemoteInput[] fromCompat;
        Notification.Action.Builder actionBuilder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (RemoteInput remoteInput : RemoteInputCompatApi20.fromCompat(action.getRemoteInputs())) {
                actionBuilder.addRemoteInput(remoteInput);
            }
        }
        if (action.getExtras() != null) {
            actionExtras = new Bundle(action.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        actionBuilder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
        actionBuilder.addExtras(actionExtras);
        b.addAction(actionBuilder.build());
    }

    public static NotificationCompatBase.Action getAction(Notification notif, int actionIndex, NotificationCompatBase.Action.Factory actionFactory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
        return getActionCompatFromAction(notif.actions[actionIndex], actionFactory, remoteInputFactory);
    }

    private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action action, NotificationCompatBase.Action.Factory actionFactory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
        RemoteInputCompatBase.RemoteInput[] remoteInputs = RemoteInputCompatApi20.toCompat(action.getRemoteInputs(), remoteInputFactory);
        boolean allowGeneratedReplies = action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies();
        return actionFactory.build(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputs, null, allowGeneratedReplies);
    }

    private static Notification.Action getActionFromActionCompat(NotificationCompatBase.Action actionCompat) {
        Bundle actionExtras;
        Notification.Action.Builder actionBuilder = new Notification.Action.Builder(actionCompat.getIcon(), actionCompat.getTitle(), actionCompat.getActionIntent());
        if (actionCompat.getExtras() != null) {
            actionExtras = new Bundle(actionCompat.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", actionCompat.getAllowGeneratedReplies());
        actionBuilder.setAllowGeneratedReplies(actionCompat.getAllowGeneratedReplies());
        actionBuilder.addExtras(actionExtras);
        RemoteInputCompatBase.RemoteInput[] remoteInputCompats = actionCompat.getRemoteInputs();
        if (remoteInputCompats != null) {
            RemoteInput[] remoteInputs = RemoteInputCompatApi20.fromCompat(remoteInputCompats);
            for (RemoteInput remoteInput : remoteInputs) {
                actionBuilder.addRemoteInput(remoteInput);
            }
        }
        return actionBuilder.build();
    }

    public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> parcelables, NotificationCompatBase.Action.Factory actionFactory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
        if (parcelables == null) {
            return null;
        }
        NotificationCompatBase.Action[] actions = actionFactory.newArray(parcelables.size());
        for (int i = 0; i < actions.length; i++) {
            Notification.Action action = (Notification.Action) parcelables.get(i);
            actions[i] = getActionCompatFromAction(action, actionFactory, remoteInputFactory);
        }
        return actions;
    }

    public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] actions) {
        if (actions == null) {
            return null;
        }
        ArrayList<Parcelable> parcelables = new ArrayList<>(actions.length);
        for (NotificationCompatBase.Action action : actions) {
            parcelables.add(getActionFromActionCompat(action));
        }
        return parcelables;
    }
}
