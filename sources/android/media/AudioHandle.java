package android.media;

import android.annotation.UnsupportedAppUsage;

/* loaded from: classes3.dex */
class AudioHandle {
    @UnsupportedAppUsage
    private final int mId;

    @UnsupportedAppUsage
    AudioHandle(int id) {
        this.mId = id;
    }

    /* renamed from: id */
    int m116id() {
        return this.mId;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof AudioHandle)) {
            return false;
        }
        AudioHandle ah = (AudioHandle) o;
        return this.mId == ah.m116id();
    }

    public int hashCode() {
        return this.mId;
    }

    public String toString() {
        return Integer.toString(this.mId);
    }
}
