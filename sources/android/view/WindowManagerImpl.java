package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Region;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.util.SeempLog;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.p016os.IResultReceiver;
import java.util.List;

/* loaded from: classes4.dex */
public final class WindowManagerImpl implements WindowManager {
    private final Context mContext;
    private IBinder mDefaultToken;
    @UnsupportedAppUsage
    private final WindowManagerGlobal mGlobal;
    private final Window mParentWindow;

    public WindowManagerImpl(Context context) {
        this(context, null);
    }

    private WindowManagerImpl(Context context, Window parentWindow) {
        this.mGlobal = WindowManagerGlobal.getInstance();
        this.mContext = context;
        this.mParentWindow = parentWindow;
    }

    public WindowManagerImpl createLocalWindowManager(Window parentWindow) {
        return new WindowManagerImpl(this.mContext, parentWindow);
    }

    public WindowManagerImpl createPresentationWindowManager(Context displayContext) {
        return new WindowManagerImpl(displayContext, this.mParentWindow);
    }

    public void setDefaultToken(IBinder token) {
        this.mDefaultToken = token;
    }

    @Override // android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams params) {
        SeempLog.record_vg_layout(383, params);
        applyDefaultToken(params);
        this.mGlobal.addView(view, params, this.mContext.getDisplay(), this.mParentWindow);
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
        SeempLog.record_vg_layout(MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION, params);
        applyDefaultToken(params);
        this.mGlobal.updateViewLayout(view, params);
    }

    private void applyDefaultToken(ViewGroup.LayoutParams params) {
        if (this.mDefaultToken != null && this.mParentWindow == null) {
            if (!(params instanceof WindowManager.LayoutParams)) {
                throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
            }
            WindowManager.LayoutParams wparams = (WindowManager.LayoutParams) params;
            if (wparams.token == null) {
                wparams.token = this.mDefaultToken;
            }
        }
    }

    @Override // android.view.ViewManager
    public void removeView(View view) {
        this.mGlobal.removeView(view, false);
    }

    @Override // android.view.WindowManager
    public void removeViewImmediate(View view) {
        this.mGlobal.removeView(view, true);
    }

    @Override // android.view.WindowManager
    public void requestAppKeyboardShortcuts(final WindowManager.KeyboardShortcutsReceiver receiver, int deviceId) {
        IResultReceiver resultReceiver = new IResultReceiver.Stub() { // from class: android.view.WindowManagerImpl.1
            @Override // com.android.internal.p016os.IResultReceiver
            public void send(int resultCode, Bundle resultData) throws RemoteException {
                List<KeyboardShortcutGroup> result = resultData.getParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY);
                receiver.onKeyboardShortcutsReceived(result);
            }
        };
        try {
            WindowManagerGlobal.getWindowManagerService().requestAppKeyboardShortcuts(resultReceiver, deviceId);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public Display getDefaultDisplay() {
        return this.mContext.getDisplay();
    }

    @Override // android.view.WindowManager
    public Region getCurrentImeTouchRegion() {
        try {
            return WindowManagerGlobal.getWindowManagerService().getCurrentImeTouchRegion();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowWithInsecureKeyguard(displayId, shouldShow);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowSystemDecors(int displayId, boolean shouldShow) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowSystemDecors(displayId, shouldShow);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public boolean shouldShowSystemDecors(int displayId) {
        try {
            return WindowManagerGlobal.getWindowManagerService().shouldShowSystemDecors(displayId);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowIme(int displayId, boolean shouldShow) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowIme(displayId, shouldShow);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public boolean shouldShowIme(int displayId) {
        try {
            return WindowManagerGlobal.getWindowManagerService().shouldShowIme(displayId);
        } catch (RemoteException e) {
            return false;
        }
    }
}
