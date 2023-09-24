package android.support.p011v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.p007os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

/* renamed from: android.support.v4.widget.ImageViewCompat */
/* loaded from: classes3.dex */
public class ImageViewCompat {
    static final ImageViewCompatImpl IMPL;

    /* renamed from: android.support.v4.widget.ImageViewCompat$ImageViewCompatImpl */
    /* loaded from: classes3.dex */
    interface ImageViewCompatImpl {
        ColorStateList getImageTintList(ImageView imageView);

        PorterDuff.Mode getImageTintMode(ImageView imageView);

        void setImageTintList(ImageView imageView, ColorStateList colorStateList);

        void setImageTintMode(ImageView imageView, PorterDuff.Mode mode);
    }

    /* renamed from: android.support.v4.widget.ImageViewCompat$BaseViewCompatImpl */
    /* loaded from: classes3.dex */
    static class BaseViewCompatImpl implements ImageViewCompatImpl {
        BaseViewCompatImpl() {
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public ColorStateList getImageTintList(ImageView view) {
            if (view instanceof TintableImageSourceView) {
                return ((TintableImageSourceView) view).getSupportImageTintList();
            }
            return null;
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public void setImageTintList(ImageView view, ColorStateList tintList) {
            if (view instanceof TintableImageSourceView) {
                ((TintableImageSourceView) view).setSupportImageTintList(tintList);
            }
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public void setImageTintMode(ImageView view, PorterDuff.Mode mode) {
            if (view instanceof TintableImageSourceView) {
                ((TintableImageSourceView) view).setSupportImageTintMode(mode);
            }
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public PorterDuff.Mode getImageTintMode(ImageView view) {
            if (view instanceof TintableImageSourceView) {
                return ((TintableImageSourceView) view).getSupportImageTintMode();
            }
            return null;
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.widget.ImageViewCompat$LollipopViewCompatImpl */
    /* loaded from: classes3.dex */
    static class LollipopViewCompatImpl extends BaseViewCompatImpl {
        LollipopViewCompatImpl() {
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.BaseViewCompatImpl, android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public ColorStateList getImageTintList(ImageView view) {
            return view.getImageTintList();
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.BaseViewCompatImpl, android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public void setImageTintList(ImageView view, ColorStateList tintList) {
            view.setImageTintList(tintList);
            if (Build.VERSION.SDK_INT == 21) {
                Drawable imageViewDrawable = view.getDrawable();
                boolean hasTint = (view.getImageTintList() == null || view.getImageTintMode() == null) ? false : true;
                if (imageViewDrawable != null && hasTint) {
                    if (imageViewDrawable.isStateful()) {
                        imageViewDrawable.setState(view.getDrawableState());
                    }
                    view.setImageDrawable(imageViewDrawable);
                }
            }
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.BaseViewCompatImpl, android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public void setImageTintMode(ImageView view, PorterDuff.Mode mode) {
            view.setImageTintMode(mode);
            if (Build.VERSION.SDK_INT == 21) {
                Drawable imageViewDrawable = view.getDrawable();
                boolean hasTint = (view.getImageTintList() == null || view.getImageTintMode() == null) ? false : true;
                if (imageViewDrawable != null && hasTint) {
                    if (imageViewDrawable.isStateful()) {
                        imageViewDrawable.setState(view.getDrawableState());
                    }
                    view.setImageDrawable(imageViewDrawable);
                }
            }
        }

        @Override // android.support.p011v4.widget.ImageViewCompat.BaseViewCompatImpl, android.support.p011v4.widget.ImageViewCompat.ImageViewCompatImpl
        public PorterDuff.Mode getImageTintMode(ImageView view) {
            return view.getImageTintMode();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new LollipopViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    public static ColorStateList getImageTintList(ImageView view) {
        return IMPL.getImageTintList(view);
    }

    public static void setImageTintList(ImageView view, ColorStateList tintList) {
        IMPL.setImageTintList(view, tintList);
    }

    public static PorterDuff.Mode getImageTintMode(ImageView view) {
        return IMPL.getImageTintMode(view);
    }

    public static void setImageTintMode(ImageView view, PorterDuff.Mode mode) {
        IMPL.setImageTintMode(view, mode);
    }

    private ImageViewCompat() {
    }
}
