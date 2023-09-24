package android.support.p014v7.widget;

import android.graphics.Rect;
import android.p007os.Build;
import android.support.annotation.RestrictTo;
import android.support.p011v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.ViewUtils */
/* loaded from: classes3.dex */
public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Method sComputeFitSystemWindowsMethod;

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                sComputeFitSystemWindowsMethod = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
                if (!sComputeFitSystemWindowsMethod.isAccessible()) {
                    sComputeFitSystemWindowsMethod.setAccessible(true);
                }
            } catch (NoSuchMethodException e) {
                Log.m72d(TAG, "Could not find method computeFitSystemWindows. Oh well.");
            }
        }
    }

    private ViewUtils() {
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static void computeFitSystemWindows(View view, Rect inoutInsets, Rect outLocalInsets) {
        if (sComputeFitSystemWindowsMethod != null) {
            try {
                sComputeFitSystemWindowsMethod.invoke(view, inoutInsets, outLocalInsets);
            } catch (Exception e) {
                Log.m71d(TAG, "Could not invoke computeFitSystemWindows", e);
            }
        }
    }

    public static void makeOptionalFitsSystemWindows(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                method.invoke(view, new Object[0]);
            } catch (IllegalAccessException e) {
                Log.m71d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e);
            } catch (NoSuchMethodException e2) {
                Log.m72d(TAG, "Could not find method makeOptionalFitsSystemWindows. Oh well...");
            } catch (InvocationTargetException e3) {
                Log.m71d(TAG, "Could not invoke makeOptionalFitsSystemWindows", e3);
            }
        }
    }
}
