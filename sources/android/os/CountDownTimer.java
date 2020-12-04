package android.os;

public abstract class CountDownTimer {
    private static final int MSG = 1;
    /* access modifiers changed from: private */
    public boolean mCancelled = false;
    /* access modifiers changed from: private */
    public final long mCountdownInterval;
    private Handler mHandler = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0061, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r14) {
            /*
                r13 = this;
                android.os.CountDownTimer r0 = android.os.CountDownTimer.this
                monitor-enter(r0)
                android.os.CountDownTimer r1 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                boolean r1 = r1.mCancelled     // Catch:{ all -> 0x0062 }
                if (r1 == 0) goto L_0x000d
                monitor-exit(r0)     // Catch:{ all -> 0x0062 }
                return
            L_0x000d:
                android.os.CountDownTimer r1 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                long r1 = r1.mStopTimeInFuture     // Catch:{ all -> 0x0062 }
                long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0062 }
                long r1 = r1 - r3
                r3 = 0
                int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r5 > 0) goto L_0x0024
                android.os.CountDownTimer r3 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                r3.onFinish()     // Catch:{ all -> 0x0062 }
                goto L_0x0060
            L_0x0024:
                long r5 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0062 }
                android.os.CountDownTimer r7 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                r7.onTick(r1)     // Catch:{ all -> 0x0062 }
                long r7 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0062 }
                long r7 = r7 - r5
                android.os.CountDownTimer r9 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                long r9 = r9.mCountdownInterval     // Catch:{ all -> 0x0062 }
                int r9 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
                if (r9 >= 0) goto L_0x0045
                long r9 = r1 - r7
                int r3 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r3 >= 0) goto L_0x0058
                r9 = 0
                goto L_0x0058
            L_0x0045:
                android.os.CountDownTimer r9 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                long r9 = r9.mCountdownInterval     // Catch:{ all -> 0x0062 }
                long r9 = r9 - r7
            L_0x004c:
                int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r11 >= 0) goto L_0x0058
                android.os.CountDownTimer r11 = android.os.CountDownTimer.this     // Catch:{ all -> 0x0062 }
                long r11 = r11.mCountdownInterval     // Catch:{ all -> 0x0062 }
                long r9 = r9 + r11
                goto L_0x004c
            L_0x0058:
                r3 = 1
                android.os.Message r3 = r13.obtainMessage(r3)     // Catch:{ all -> 0x0062 }
                r13.sendMessageDelayed(r3, r9)     // Catch:{ all -> 0x0062 }
            L_0x0060:
                monitor-exit(r0)     // Catch:{ all -> 0x0062 }
                return
            L_0x0062:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0062 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.CountDownTimer.AnonymousClass1.handleMessage(android.os.Message):void");
        }
    };
    private final long mMillisInFuture;
    /* access modifiers changed from: private */
    public long mStopTimeInFuture;

    public abstract void onFinish();

    public abstract void onTick(long j);

    public CountDownTimer(long millisInFuture, long countDownInterval) {
        this.mMillisInFuture = millisInFuture;
        this.mCountdownInterval = countDownInterval;
    }

    public final synchronized void cancel() {
        this.mCancelled = true;
        this.mHandler.removeMessages(1);
    }

    public final synchronized CountDownTimer start() {
        this.mCancelled = false;
        if (this.mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        this.mStopTimeInFuture = SystemClock.elapsedRealtime() + this.mMillisInFuture;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
        return this;
    }
}
