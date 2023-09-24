package android.support.p011v4.widget;

import android.p007os.Build;
import android.view.View;
import android.widget.PopupMenu;

/* renamed from: android.support.v4.widget.PopupMenuCompat */
/* loaded from: classes3.dex */
public final class PopupMenuCompat {
    private PopupMenuCompat() {
    }

    public static View.OnTouchListener getDragToOpenListener(Object popupMenu) {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((PopupMenu) popupMenu).getDragToOpenListener();
        }
        return null;
    }
}
