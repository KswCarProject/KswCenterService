package android.support.p014v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;

/* renamed from: android.support.v7.widget.TintInfo */
/* loaded from: classes3.dex */
class TintInfo {
    public boolean mHasTintList;
    public boolean mHasTintMode;
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;

    TintInfo() {
    }

    void clear() {
        this.mTintList = null;
        this.mHasTintList = false;
        this.mTintMode = null;
        this.mHasTintMode = false;
    }
}
