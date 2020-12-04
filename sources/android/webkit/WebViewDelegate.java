package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewRootImpl;

@SystemApi
public final class WebViewDelegate {

    public interface OnTraceEnabledChangeListener {
        void onTraceEnabledChange(boolean z);
    }

    @UnsupportedAppUsage
    WebViewDelegate() {
    }

    public void setOnTraceEnabledChangeListener(final OnTraceEnabledChangeListener listener) {
        SystemProperties.addChangeCallback(new Runnable() {
            public void run() {
                listener.onTraceEnabledChange(WebViewDelegate.this.isTraceTagEnabled());
            }
        });
    }

    public boolean isTraceTagEnabled() {
        return Trace.isTagEnabled(16);
    }

    @Deprecated
    public boolean canInvokeDrawGlFunctor(View containerView) {
        return true;
    }

    @Deprecated
    public void invokeDrawGlFunctor(View containerView, long nativeDrawGLFunctor, boolean waitForCompletion) {
        ViewRootImpl.invokeFunctor(nativeDrawGLFunctor, waitForCompletion);
    }

    @Deprecated
    public void callDrawGlFunction(Canvas canvas, long nativeDrawGLFunctor) {
        if (canvas instanceof RecordingCanvas) {
            ((RecordingCanvas) canvas).drawGLFunctor2(nativeDrawGLFunctor, (Runnable) null);
            return;
        }
        throw new IllegalArgumentException(canvas.getClass().getName() + " is not a DisplayList canvas");
    }

    @Deprecated
    public void callDrawGlFunction(Canvas canvas, long nativeDrawGLFunctor, Runnable releasedRunnable) {
        if (canvas instanceof RecordingCanvas) {
            ((RecordingCanvas) canvas).drawGLFunctor2(nativeDrawGLFunctor, releasedRunnable);
            return;
        }
        throw new IllegalArgumentException(canvas.getClass().getName() + " is not a DisplayList canvas");
    }

    public void drawWebViewFunctor(Canvas canvas, int functor) {
        if (canvas instanceof RecordingCanvas) {
            ((RecordingCanvas) canvas).drawWebViewFunctor(functor);
            return;
        }
        throw new IllegalArgumentException(canvas.getClass().getName() + " is not a RecordingCanvas canvas");
    }

    @Deprecated
    public void detachDrawGlFunctor(View containerView, long nativeDrawGLFunctor) {
        ViewRootImpl viewRootImpl = containerView.getViewRootImpl();
        if (nativeDrawGLFunctor != 0 && viewRootImpl != null) {
            viewRootImpl.detachFunctor(nativeDrawGLFunctor);
        }
    }

    public int getPackageId(Resources resources, String packageName) {
        SparseArray<String> packageIdentifiers = resources.getAssets().getAssignedPackageIdentifiers();
        for (int i = 0; i < packageIdentifiers.size(); i++) {
            if (packageName.equals(packageIdentifiers.valueAt(i))) {
                return packageIdentifiers.keyAt(i);
            }
        }
        throw new RuntimeException("Package not found: " + packageName);
    }

    public Application getApplication() {
        return ActivityThread.currentApplication();
    }

    public String getErrorString(Context context, int errorCode) {
        return LegacyErrorStrings.getString(errorCode, context);
    }

    /* JADX WARNING: type inference failed for: r6v1, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addWebViewAssetPath(android.content.Context r8) {
        /*
            r7 = this;
            android.content.pm.PackageInfo r0 = android.webkit.WebViewFactory.getLoadedPackageInfo()
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo
            java.lang.String[] r0 = r0.getAllApkPaths()
            android.content.pm.ApplicationInfo r1 = r8.getApplicationInfo()
            java.lang.String[] r2 = r1.sharedLibraryFiles
            int r3 = r0.length
            r4 = 0
        L_0x0012:
            if (r4 >= r3) goto L_0x0022
            r5 = r0[r4]
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            java.lang.Object[] r6 = com.android.internal.util.ArrayUtils.appendElement(r6, r2, r5)
            r2 = r6
            java.lang.String[] r2 = (java.lang.String[]) r2
            int r4 = r4 + 1
            goto L_0x0012
        L_0x0022:
            java.lang.String[] r3 = r1.sharedLibraryFiles
            if (r2 == r3) goto L_0x0033
            r1.sharedLibraryFiles = r2
            android.app.ResourcesManager r3 = android.app.ResourcesManager.getInstance()
            java.lang.String r4 = r1.getBaseResourcePath()
            r3.appendLibAssetsForMainAssetPath(r4, r0)
        L_0x0033:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.webkit.WebViewDelegate.addWebViewAssetPath(android.content.Context):void");
    }

    public boolean isMultiProcessEnabled() {
        try {
            return WebViewFactory.getUpdateService().isMultiProcessEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getDataDirectorySuffix() {
        return WebViewFactory.getDataDirectorySuffix();
    }
}
