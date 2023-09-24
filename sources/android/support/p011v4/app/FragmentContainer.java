package android.support.p011v4.app;

import android.content.Context;
import android.p007os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

/* renamed from: android.support.v4.app.FragmentContainer */
/* loaded from: classes3.dex */
public abstract class FragmentContainer {
    @Nullable
    public abstract View onFindViewById(@IdRes int i);

    public abstract boolean onHasView();

    public Fragment instantiate(Context context, String className, Bundle arguments) {
        return Fragment.instantiate(context, className, arguments);
    }
}
