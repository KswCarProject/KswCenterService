package android.support.p011v4.widget;

import android.p007os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

/* renamed from: android.support.v4.widget.ListViewCompat */
/* loaded from: classes3.dex */
public final class ListViewCompat {
    public static void scrollListBy(@NonNull ListView listView, int y) {
        View firstView;
        if (Build.VERSION.SDK_INT >= 19) {
            listView.scrollListBy(y);
            return;
        }
        int firstPosition = listView.getFirstVisiblePosition();
        if (firstPosition == -1 || (firstView = listView.getChildAt(0)) == null) {
            return;
        }
        int newTop = firstView.getTop() - y;
        listView.setSelectionFromTop(firstPosition, newTop);
    }

    public static boolean canScrollList(@NonNull ListView listView, int direction) {
        if (Build.VERSION.SDK_INT >= 19) {
            return listView.canScrollList(direction);
        }
        int childCount = listView.getChildCount();
        if (childCount == 0) {
            return false;
        }
        int firstPosition = listView.getFirstVisiblePosition();
        if (direction > 0) {
            int lastBottom = listView.getChildAt(childCount - 1).getBottom();
            int lastPosition = firstPosition + childCount;
            if (lastPosition >= listView.getCount() && lastBottom <= listView.getHeight() - listView.getListPaddingBottom()) {
                return false;
            }
            return true;
        }
        int firstTop = listView.getChildAt(0).getTop();
        if (firstPosition <= 0 && firstTop >= listView.getListPaddingTop()) {
            return false;
        }
        return true;
    }

    private ListViewCompat() {
    }
}