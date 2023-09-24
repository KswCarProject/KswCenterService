package android.support.p011v4.app;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ReportFragment;
import android.p007os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p011v4.util.SimpleArrayMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.app.SupportActivity */
/* loaded from: classes3.dex */
public class SupportActivity extends Activity implements LifecycleOwner {
    private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap<>();
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* renamed from: android.support.v4.app.SupportActivity$ExtraData */
    /* loaded from: classes3.dex */
    public static class ExtraData {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void putExtraData(ExtraData extraData) {
        this.mExtraDataMap.put(extraData.getClass(), extraData);
    }

    @Override // android.app.Activity
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReportFragment.injectIfNeededIn(this);
    }

    @Override // android.app.Activity
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        this.mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(outState);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public <T extends ExtraData> T getExtraData(Class<T> extraDataClass) {
        return (T) this.mExtraDataMap.get(extraDataClass);
    }

    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }
}
