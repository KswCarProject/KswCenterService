package android.support.p014v7.app;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

@RequiresApi(14)
/* renamed from: android.support.v7.app.AppCompatDelegateImplV11 */
/* loaded from: classes3.dex */
class AppCompatDelegateImplV11 extends AppCompatDelegateImplV9 {
    AppCompatDelegateImplV11(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
    }

    @Override // android.support.p014v7.app.AppCompatDelegateImplV9, android.support.p014v7.app.AppCompatDelegate
    public boolean hasWindowFeature(int featureId) {
        return super.hasWindowFeature(featureId) || this.mWindow.hasFeature(featureId);
    }

    @Override // android.support.p014v7.app.AppCompatDelegateImplV9
    View callActivityOnCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return null;
    }
}
