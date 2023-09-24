package android.support.p014v7.widget;

import android.graphics.Rect;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.FitWindowsViewGroup */
/* loaded from: classes3.dex */
public interface FitWindowsViewGroup {

    /* renamed from: android.support.v7.widget.FitWindowsViewGroup$OnFitSystemWindowsListener */
    /* loaded from: classes3.dex */
    public interface OnFitSystemWindowsListener {
        void onFitSystemWindows(Rect rect);
    }

    void setOnFitSystemWindowsListener(OnFitSystemWindowsListener onFitSystemWindowsListener);
}
