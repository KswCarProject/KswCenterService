package com.android.internal.p016os;

import java.util.HashMap;

/* renamed from: com.android.internal.os.KernelWakelockStats */
/* loaded from: classes4.dex */
public class KernelWakelockStats extends HashMap<String, Entry> {
    int kernelWakelockVersion;

    /* renamed from: com.android.internal.os.KernelWakelockStats$Entry */
    /* loaded from: classes4.dex */
    public static class Entry {
        public int mCount;
        public long mTotalTime;
        public int mVersion;

        Entry(int count, long totalTime, int version) {
            this.mCount = count;
            this.mTotalTime = totalTime;
            this.mVersion = version;
        }
    }
}
