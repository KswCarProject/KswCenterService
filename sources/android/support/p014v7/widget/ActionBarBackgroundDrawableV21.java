package android.support.p014v7.widget;

import android.graphics.Outline;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
/* renamed from: android.support.v7.widget.ActionBarBackgroundDrawableV21 */
/* loaded from: classes3.dex */
class ActionBarBackgroundDrawableV21 extends ActionBarBackgroundDrawable {
    public ActionBarBackgroundDrawableV21(ActionBarContainer container) {
        super(container);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(@NonNull Outline outline) {
        if (this.mContainer.mIsSplit) {
            if (this.mContainer.mSplitBackground != null) {
                this.mContainer.mSplitBackground.getOutline(outline);
            }
        } else if (this.mContainer.mBackground != null) {
            this.mContainer.mBackground.getOutline(outline);
        }
    }
}
