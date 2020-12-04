package android.os;

import java.io.PrintStream;

public class Broadcaster {
    private Registration mReg;

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009b, code lost:
        return;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void request(int r9, android.os.Handler r10, int r11) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            android.os.Broadcaster$Registration r1 = r8.mReg     // Catch:{ all -> 0x009c }
            r2 = 0
            r3 = 0
            r4 = 1
            if (r1 != 0) goto L_0x0029
            android.os.Broadcaster$Registration r1 = new android.os.Broadcaster$Registration     // Catch:{ all -> 0x009c }
            r1.<init>()     // Catch:{ all -> 0x009c }
            r0 = r1
            r0.senderWhat = r9     // Catch:{ all -> 0x009c }
            android.os.Handler[] r1 = new android.os.Handler[r4]     // Catch:{ all -> 0x009c }
            r0.targets = r1     // Catch:{ all -> 0x009c }
            int[] r1 = new int[r4]     // Catch:{ all -> 0x009c }
            r0.targetWhats = r1     // Catch:{ all -> 0x009c }
            android.os.Handler[] r1 = r0.targets     // Catch:{ all -> 0x009c }
            r1[r3] = r10     // Catch:{ all -> 0x009c }
            int[] r1 = r0.targetWhats     // Catch:{ all -> 0x009c }
            r1[r3] = r11     // Catch:{ all -> 0x009c }
            r8.mReg = r0     // Catch:{ all -> 0x009c }
            r0.next = r0     // Catch:{ all -> 0x009c }
            r0.prev = r0     // Catch:{ all -> 0x009c }
            goto L_0x009a
        L_0x0029:
            android.os.Broadcaster$Registration r1 = r8.mReg     // Catch:{ all -> 0x009c }
            r0 = r1
        L_0x002c:
            int r5 = r0.senderWhat     // Catch:{ all -> 0x009c }
            if (r5 < r9) goto L_0x0031
            goto L_0x0036
        L_0x0031:
            android.os.Broadcaster$Registration r5 = r0.next     // Catch:{ all -> 0x009c }
            r0 = r5
            if (r0 != r1) goto L_0x002c
        L_0x0036:
            int r5 = r0.senderWhat     // Catch:{ all -> 0x009c }
            if (r5 == r9) goto L_0x0065
            android.os.Broadcaster$Registration r3 = new android.os.Broadcaster$Registration     // Catch:{ all -> 0x009c }
            r3.<init>()     // Catch:{ all -> 0x009c }
            r2 = r3
            r2.senderWhat = r9     // Catch:{ all -> 0x009c }
            android.os.Handler[] r3 = new android.os.Handler[r4]     // Catch:{ all -> 0x009c }
            r2.targets = r3     // Catch:{ all -> 0x009c }
            int[] r3 = new int[r4]     // Catch:{ all -> 0x009c }
            r2.targetWhats = r3     // Catch:{ all -> 0x009c }
            r2.next = r0     // Catch:{ all -> 0x009c }
            android.os.Broadcaster$Registration r3 = r0.prev     // Catch:{ all -> 0x009c }
            r2.prev = r3     // Catch:{ all -> 0x009c }
            android.os.Broadcaster$Registration r3 = r0.prev     // Catch:{ all -> 0x009c }
            r3.next = r2     // Catch:{ all -> 0x009c }
            r0.prev = r2     // Catch:{ all -> 0x009c }
            android.os.Broadcaster$Registration r3 = r8.mReg     // Catch:{ all -> 0x009c }
            if (r0 != r3) goto L_0x0062
            int r3 = r0.senderWhat     // Catch:{ all -> 0x009c }
            int r4 = r2.senderWhat     // Catch:{ all -> 0x009c }
            if (r3 <= r4) goto L_0x0062
            r8.mReg = r2     // Catch:{ all -> 0x009c }
        L_0x0062:
            r0 = r2
            r2 = 0
            goto L_0x0092
        L_0x0065:
            android.os.Handler[] r2 = r0.targets     // Catch:{ all -> 0x009c }
            int r2 = r2.length     // Catch:{ all -> 0x009c }
            android.os.Handler[] r4 = r0.targets     // Catch:{ all -> 0x009c }
            int[] r5 = r0.targetWhats     // Catch:{ all -> 0x009c }
            r6 = r3
        L_0x006d:
            if (r6 >= r2) goto L_0x007c
            r7 = r4[r6]     // Catch:{ all -> 0x009c }
            if (r7 != r10) goto L_0x0079
            r7 = r5[r6]     // Catch:{ all -> 0x009c }
            if (r7 != r11) goto L_0x0079
            monitor-exit(r8)     // Catch:{ all -> 0x009c }
            return
        L_0x0079:
            int r6 = r6 + 1
            goto L_0x006d
        L_0x007c:
            int r6 = r2 + 1
            android.os.Handler[] r6 = new android.os.Handler[r6]     // Catch:{ all -> 0x009c }
            r0.targets = r6     // Catch:{ all -> 0x009c }
            android.os.Handler[] r6 = r0.targets     // Catch:{ all -> 0x009c }
            java.lang.System.arraycopy(r4, r3, r6, r3, r2)     // Catch:{ all -> 0x009c }
            int r6 = r2 + 1
            int[] r6 = new int[r6]     // Catch:{ all -> 0x009c }
            r0.targetWhats = r6     // Catch:{ all -> 0x009c }
            int[] r6 = r0.targetWhats     // Catch:{ all -> 0x009c }
            java.lang.System.arraycopy(r5, r3, r6, r3, r2)     // Catch:{ all -> 0x009c }
        L_0x0092:
            android.os.Handler[] r3 = r0.targets     // Catch:{ all -> 0x009c }
            r3[r2] = r10     // Catch:{ all -> 0x009c }
            int[] r3 = r0.targetWhats     // Catch:{ all -> 0x009c }
            r3[r2] = r11     // Catch:{ all -> 0x009c }
        L_0x009a:
            monitor-exit(r8)     // Catch:{ all -> 0x009c }
            return
        L_0x009c:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x009c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.Broadcaster.request(int, android.os.Handler, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0058, code lost:
        return;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancelRequest(int r10, android.os.Handler r11, int r12) {
        /*
            r9 = this;
            monitor-enter(r9)
            android.os.Broadcaster$Registration r0 = r9.mReg     // Catch:{ all -> 0x0059 }
            r1 = r0
            if (r1 != 0) goto L_0x0008
            monitor-exit(r9)     // Catch:{ all -> 0x0059 }
            return
        L_0x0008:
            int r2 = r1.senderWhat     // Catch:{ all -> 0x0059 }
            if (r2 < r10) goto L_0x000d
            goto L_0x0012
        L_0x000d:
            android.os.Broadcaster$Registration r2 = r1.next     // Catch:{ all -> 0x0059 }
            r1 = r2
            if (r1 != r0) goto L_0x0008
        L_0x0012:
            int r2 = r1.senderWhat     // Catch:{ all -> 0x0059 }
            if (r2 != r10) goto L_0x0057
            android.os.Handler[] r2 = r1.targets     // Catch:{ all -> 0x0059 }
            int[] r3 = r1.targetWhats     // Catch:{ all -> 0x0059 }
            int r4 = r2.length     // Catch:{ all -> 0x0059 }
            r5 = 0
            r6 = r5
        L_0x001d:
            if (r6 >= r4) goto L_0x0057
            r7 = r2[r6]     // Catch:{ all -> 0x0059 }
            if (r7 != r11) goto L_0x0054
            r7 = r3[r6]     // Catch:{ all -> 0x0059 }
            if (r7 != r12) goto L_0x0054
            int r7 = r4 + -1
            android.os.Handler[] r7 = new android.os.Handler[r7]     // Catch:{ all -> 0x0059 }
            r1.targets = r7     // Catch:{ all -> 0x0059 }
            int r7 = r4 + -1
            int[] r7 = new int[r7]     // Catch:{ all -> 0x0059 }
            r1.targetWhats = r7     // Catch:{ all -> 0x0059 }
            if (r6 <= 0) goto L_0x003f
            android.os.Handler[] r7 = r1.targets     // Catch:{ all -> 0x0059 }
            java.lang.System.arraycopy(r2, r5, r7, r5, r6)     // Catch:{ all -> 0x0059 }
            int[] r7 = r1.targetWhats     // Catch:{ all -> 0x0059 }
            java.lang.System.arraycopy(r3, r5, r7, r5, r6)     // Catch:{ all -> 0x0059 }
        L_0x003f:
            int r5 = r4 - r6
            int r5 = r5 + -1
            if (r5 == 0) goto L_0x0057
            int r7 = r6 + 1
            android.os.Handler[] r8 = r1.targets     // Catch:{ all -> 0x0059 }
            java.lang.System.arraycopy(r2, r7, r8, r6, r5)     // Catch:{ all -> 0x0059 }
            int r7 = r6 + 1
            int[] r8 = r1.targetWhats     // Catch:{ all -> 0x0059 }
            java.lang.System.arraycopy(r3, r7, r8, r6, r5)     // Catch:{ all -> 0x0059 }
            goto L_0x0057
        L_0x0054:
            int r6 = r6 + 1
            goto L_0x001d
        L_0x0057:
            monitor-exit(r9)     // Catch:{ all -> 0x0059 }
            return
        L_0x0059:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0059 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.Broadcaster.cancelRequest(int, android.os.Handler, int):void");
    }

    public void dumpRegistrations() {
        synchronized (this) {
            Registration start = this.mReg;
            PrintStream printStream = System.out;
            printStream.println("Broadcaster " + this + " {");
            if (start != null) {
                Registration r = start;
                do {
                    PrintStream printStream2 = System.out;
                    printStream2.println("    senderWhat=" + r.senderWhat);
                    int n = r.targets.length;
                    for (int i = 0; i < n; i++) {
                        PrintStream printStream3 = System.out;
                        printStream3.println("        [" + r.targetWhats[i] + "] " + r.targets[i]);
                    }
                    r = r.next;
                } while (r != start);
            }
            System.out.println("}");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0036, code lost:
        return;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void broadcast(android.os.Message r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            android.os.Broadcaster$Registration r0 = r10.mReg     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r10)     // Catch:{ all -> 0x0037 }
            return
        L_0x0007:
            int r0 = r11.what     // Catch:{ all -> 0x0037 }
            android.os.Broadcaster$Registration r1 = r10.mReg     // Catch:{ all -> 0x0037 }
            r2 = r1
        L_0x000c:
            int r3 = r2.senderWhat     // Catch:{ all -> 0x0037 }
            if (r3 < r0) goto L_0x0011
            goto L_0x0016
        L_0x0011:
            android.os.Broadcaster$Registration r3 = r2.next     // Catch:{ all -> 0x0037 }
            r2 = r3
            if (r2 != r1) goto L_0x000c
        L_0x0016:
            int r3 = r2.senderWhat     // Catch:{ all -> 0x0037 }
            if (r3 != r0) goto L_0x0035
            android.os.Handler[] r3 = r2.targets     // Catch:{ all -> 0x0037 }
            int[] r4 = r2.targetWhats     // Catch:{ all -> 0x0037 }
            int r5 = r3.length     // Catch:{ all -> 0x0037 }
            r6 = 0
        L_0x0020:
            if (r6 >= r5) goto L_0x0035
            r7 = r3[r6]     // Catch:{ all -> 0x0037 }
            android.os.Message r8 = android.os.Message.obtain()     // Catch:{ all -> 0x0037 }
            r8.copyFrom(r11)     // Catch:{ all -> 0x0037 }
            r9 = r4[r6]     // Catch:{ all -> 0x0037 }
            r8.what = r9     // Catch:{ all -> 0x0037 }
            r7.sendMessage(r8)     // Catch:{ all -> 0x0037 }
            int r6 = r6 + 1
            goto L_0x0020
        L_0x0035:
            monitor-exit(r10)     // Catch:{ all -> 0x0037 }
            return
        L_0x0037:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0037 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.Broadcaster.broadcast(android.os.Message):void");
    }

    private class Registration {
        Registration next;
        Registration prev;
        int senderWhat;
        int[] targetWhats;
        Handler[] targets;

        private Registration() {
        }
    }
}
