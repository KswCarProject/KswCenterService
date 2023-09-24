package com.android.server;

import android.annotation.UnsupportedAppUsage;
import android.p007os.ConditionVariable;
import android.p007os.SystemClock;

/* loaded from: classes4.dex */
abstract class ResettableTimeout {
    @UnsupportedAppUsage
    private ConditionVariable mLock = new ConditionVariable();
    @UnsupportedAppUsage
    private volatile long mOffAt;
    private volatile boolean mOffCalled;
    private Thread mThread;

    public abstract void off();

    /* renamed from: on */
    public abstract void m21on(boolean z);

    ResettableTimeout() {
    }

    /* renamed from: go */
    public void m22go(long milliseconds) {
        boolean alreadyOn;
        synchronized (this) {
            this.mOffAt = SystemClock.uptimeMillis() + milliseconds;
            if (this.mThread == null) {
                alreadyOn = false;
                this.mLock.close();
                this.mThread = new C3412T();
                this.mThread.start();
                this.mLock.block();
                this.mOffCalled = false;
            } else {
                alreadyOn = true;
                this.mThread.interrupt();
            }
            m21on(alreadyOn);
        }
    }

    public void cancel() {
        synchronized (this) {
            this.mOffAt = 0L;
            if (this.mThread != null) {
                this.mThread.interrupt();
                this.mThread = null;
            }
            if (!this.mOffCalled) {
                this.mOffCalled = true;
                off();
            }
        }
    }

    /* renamed from: com.android.server.ResettableTimeout$T */
    /* loaded from: classes4.dex */
    private class C3412T extends Thread {
        private C3412T() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long diff;
            ResettableTimeout.this.mLock.open();
            while (true) {
                synchronized (this) {
                    diff = ResettableTimeout.this.mOffAt - SystemClock.uptimeMillis();
                    if (diff <= 0) {
                        ResettableTimeout.this.mOffCalled = true;
                        ResettableTimeout.this.off();
                        ResettableTimeout.this.mThread = null;
                        return;
                    }
                }
                try {
                    sleep(diff);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
