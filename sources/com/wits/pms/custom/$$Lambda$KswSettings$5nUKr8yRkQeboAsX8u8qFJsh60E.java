package com.wits.pms.custom;

import com.wits.pms.mirror.SystemProperties;

/* renamed from: com.wits.pms.custom.-$$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E implements Runnable {
    public static final /* synthetic */ $$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E INSTANCE = new $$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E();

    private /* synthetic */ $$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E() {
    }

    public final void run() {
        SystemProperties.set("sys.powerctl", "reboot");
    }
}
