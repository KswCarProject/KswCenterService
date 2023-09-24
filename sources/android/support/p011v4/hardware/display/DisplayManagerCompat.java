package android.support.p011v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.p007os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

/* renamed from: android.support.v4.hardware.display.DisplayManagerCompat */
/* loaded from: classes3.dex */
public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<>();

    public abstract Display getDisplay(int i);

    public abstract Display[] getDisplays();

    public abstract Display[] getDisplays(String str);

    DisplayManagerCompat() {
    }

    public static DisplayManagerCompat getInstance(Context context) {
        DisplayManagerCompat instance;
        synchronized (sInstances) {
            instance = sInstances.get(context);
            if (instance == null) {
                if (Build.VERSION.SDK_INT >= 17) {
                    instance = new DisplayManagerCompatApi17Impl(context);
                } else {
                    instance = new DisplayManagerCompatApi14Impl(context);
                }
                sInstances.put(context, instance);
            }
        }
        return instance;
    }

    /* renamed from: android.support.v4.hardware.display.DisplayManagerCompat$DisplayManagerCompatApi14Impl */
    /* loaded from: classes3.dex */
    private static class DisplayManagerCompatApi14Impl extends DisplayManagerCompat {
        private final WindowManager mWindowManager;

        DisplayManagerCompatApi14Impl(Context context) {
            this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }

        @Override // android.support.p011v4.hardware.display.DisplayManagerCompat
        public Display getDisplay(int displayId) {
            Display display = this.mWindowManager.getDefaultDisplay();
            if (display.getDisplayId() == displayId) {
                return display;
            }
            return null;
        }

        @Override // android.support.p011v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays() {
            return new Display[]{this.mWindowManager.getDefaultDisplay()};
        }

        @Override // android.support.p011v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays(String category) {
            return category == null ? getDisplays() : new Display[0];
        }
    }

    @RequiresApi(17)
    /* renamed from: android.support.v4.hardware.display.DisplayManagerCompat$DisplayManagerCompatApi17Impl */
    /* loaded from: classes3.dex */
    private static class DisplayManagerCompatApi17Impl extends DisplayManagerCompat {
        private final DisplayManager mDisplayManager;

        DisplayManagerCompatApi17Impl(Context context) {
            this.mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        }

        @Override // android.support.p011v4.hardware.display.DisplayManagerCompat
        public Display getDisplay(int displayId) {
            return this.mDisplayManager.getDisplay(displayId);
        }

        @Override // android.support.p011v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays() {
            return this.mDisplayManager.getDisplays();
        }

        @Override // android.support.p011v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays(String category) {
            return this.mDisplayManager.getDisplays(category);
        }
    }
}
