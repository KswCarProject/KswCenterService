package android.support.p014v7.app;

import android.app.UiModeManager;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.p014v7.app.AppCompatDelegateImplV14;
import android.view.ActionMode;
import android.view.Window;

@RequiresApi(23)
/* renamed from: android.support.v7.app.AppCompatDelegateImplV23 */
/* loaded from: classes3.dex */
class AppCompatDelegateImplV23 extends AppCompatDelegateImplV14 {
    private final UiModeManager mUiModeManager;

    AppCompatDelegateImplV23(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
        this.mUiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
    }

    @Override // android.support.p014v7.app.AppCompatDelegateImplV14, android.support.p014v7.app.AppCompatDelegateImplBase
    Window.Callback wrapWindowCallback(Window.Callback callback) {
        return new AppCompatWindowCallbackV23(callback);
    }

    @Override // android.support.p014v7.app.AppCompatDelegateImplV14
    int mapNightMode(int mode) {
        if (mode == 0 && this.mUiModeManager.getNightMode() == 0) {
            return -1;
        }
        return super.mapNightMode(mode);
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV23$AppCompatWindowCallbackV23 */
    /* loaded from: classes3.dex */
    class AppCompatWindowCallbackV23 extends AppCompatDelegateImplV14.AppCompatWindowCallbackV14 {
        AppCompatWindowCallbackV23(Window.Callback callback) {
            super(callback);
        }

        @Override // android.support.p014v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
            if (AppCompatDelegateImplV23.this.isHandleNativeActionModesEnabled() && type == 0) {
                return startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback, type);
        }

        @Override // android.support.p014v7.app.AppCompatDelegateImplV14.AppCompatWindowCallbackV14, android.support.p014v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }
    }
}
