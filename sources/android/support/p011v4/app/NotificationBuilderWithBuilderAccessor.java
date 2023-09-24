package android.support.p011v4.app;

import android.app.Notification;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.app.NotificationBuilderWithBuilderAccessor */
/* loaded from: classes3.dex */
public interface NotificationBuilderWithBuilderAccessor {
    Notification build();

    Notification.Builder getBuilder();
}
