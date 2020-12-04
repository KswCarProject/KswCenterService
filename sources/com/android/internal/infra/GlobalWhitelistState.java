package com.android.internal.infra;

import android.content.ComponentName;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import java.io.PrintWriter;
import java.util.List;

public class GlobalWhitelistState {
    protected final Object mGlobalWhitelistStateLock = new Object();
    @GuardedBy({"mGlobalWhitelistStateLock"})
    protected SparseArray<WhitelistHelper> mWhitelisterHelpers;

    public void setWhitelist(int userId, List<String> packageNames, List<ComponentName> components) {
        synchronized (this.mGlobalWhitelistStateLock) {
            if (this.mWhitelisterHelpers == null) {
                this.mWhitelisterHelpers = new SparseArray<>(1);
            }
            WhitelistHelper helper = this.mWhitelisterHelpers.get(userId);
            if (helper == null) {
                helper = new WhitelistHelper();
                this.mWhitelisterHelpers.put(userId, helper);
            }
            helper.setWhitelist(packageNames, components);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isWhitelisted(int r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mGlobalWhitelistStateLock
            monitor-enter(r0)
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r3.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            r2 = 0
            if (r1 != 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return r2
        L_0x000a:
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r3.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ all -> 0x001b }
            com.android.internal.infra.WhitelistHelper r1 = (com.android.internal.infra.WhitelistHelper) r1     // Catch:{ all -> 0x001b }
            if (r1 != 0) goto L_0x0015
            goto L_0x0019
        L_0x0015:
            boolean r2 = r1.isWhitelisted((java.lang.String) r5)     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return r2
        L_0x001b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.infra.GlobalWhitelistState.isWhitelisted(int, java.lang.String):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isWhitelisted(int r4, android.content.ComponentName r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mGlobalWhitelistStateLock
            monitor-enter(r0)
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r3.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            r2 = 0
            if (r1 != 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return r2
        L_0x000a:
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r3.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ all -> 0x001b }
            com.android.internal.infra.WhitelistHelper r1 = (com.android.internal.infra.WhitelistHelper) r1     // Catch:{ all -> 0x001b }
            if (r1 != 0) goto L_0x0015
            goto L_0x0019
        L_0x0015:
            boolean r2 = r1.isWhitelisted((android.content.ComponentName) r5)     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return r2
        L_0x001b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.infra.GlobalWhitelistState.isWhitelisted(int, android.content.ComponentName):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.util.ArraySet<android.content.ComponentName> getWhitelistedComponents(int r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mGlobalWhitelistStateLock
            monitor-enter(r0)
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r3.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            r2 = 0
            if (r1 != 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return r2
        L_0x000a:
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r3.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ all -> 0x001b }
            com.android.internal.infra.WhitelistHelper r1 = (com.android.internal.infra.WhitelistHelper) r1     // Catch:{ all -> 0x001b }
            if (r1 != 0) goto L_0x0015
            goto L_0x0019
        L_0x0015:
            android.util.ArraySet r2 = r1.getWhitelistedComponents(r5)     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return r2
        L_0x001b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.infra.GlobalWhitelistState.getWhitelistedComponents(int, java.lang.String):android.util.ArraySet");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void resetWhitelist(int r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.mGlobalWhitelistStateLock
            monitor-enter(r0)
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r2.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return
        L_0x0009:
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r2.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            r1.remove(r3)     // Catch:{ all -> 0x001b }
            android.util.SparseArray<com.android.internal.infra.WhitelistHelper> r1 = r2.mWhitelisterHelpers     // Catch:{ all -> 0x001b }
            int r1 = r1.size()     // Catch:{ all -> 0x001b }
            if (r1 != 0) goto L_0x0019
            r1 = 0
            r2.mWhitelisterHelpers = r1     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            return
        L_0x001b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.infra.GlobalWhitelistState.resetWhitelist(int):void");
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.print(prefix);
        pw.print("State: ");
        synchronized (this.mGlobalWhitelistStateLock) {
            if (this.mWhitelisterHelpers == null) {
                pw.println("empty");
                return;
            }
            pw.print(this.mWhitelisterHelpers.size());
            pw.println(" services");
            String prefix2 = prefix + "  ";
            for (int i = 0; i < this.mWhitelisterHelpers.size(); i++) {
                this.mWhitelisterHelpers.valueAt(i).dump(prefix2, "Whitelist for userId " + this.mWhitelisterHelpers.keyAt(i), pw);
            }
        }
    }
}
