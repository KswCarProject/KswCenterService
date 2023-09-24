package android.transition;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public final class Scene {
    private Context mContext;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    Runnable mEnterAction;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    Runnable mExitAction;
    private View mLayout;
    private int mLayoutId;
    private ViewGroup mSceneRoot;

    public static Scene getSceneForLayout(ViewGroup sceneRoot, int layoutId, Context context) {
        SparseArray<Scene> scenes = (SparseArray) sceneRoot.getTag(C3132R.C3134id.scene_layoutid_cache);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTagInternal(C3132R.C3134id.scene_layoutid_cache, scenes);
        }
        Scene scene = scenes.get(layoutId);
        if (scene != null) {
            return scene;
        }
        Scene scene2 = new Scene(sceneRoot, layoutId, context);
        scenes.put(layoutId, scene2);
        return scene2;
    }

    public Scene(ViewGroup sceneRoot) {
        this.mLayoutId = -1;
        this.mSceneRoot = sceneRoot;
    }

    private Scene(ViewGroup sceneRoot, int layoutId, Context context) {
        this.mLayoutId = -1;
        this.mContext = context;
        this.mSceneRoot = sceneRoot;
        this.mLayoutId = layoutId;
    }

    public Scene(ViewGroup sceneRoot, View layout) {
        this.mLayoutId = -1;
        this.mSceneRoot = sceneRoot;
        this.mLayout = layout;
    }

    @Deprecated
    public Scene(ViewGroup sceneRoot, ViewGroup layout) {
        this.mLayoutId = -1;
        this.mSceneRoot = sceneRoot;
        this.mLayout = layout;
    }

    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public void exit() {
        if (getCurrentScene(this.mSceneRoot) == this && this.mExitAction != null) {
            this.mExitAction.run();
        }
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        if (this.mEnterAction != null) {
            this.mEnterAction.run();
        }
        setCurrentScene(this.mSceneRoot, this);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    static void setCurrentScene(ViewGroup sceneRoot, Scene scene) {
        sceneRoot.setTagInternal(C3132R.C3134id.current_scene, scene);
    }

    public static Scene getCurrentScene(ViewGroup sceneRoot) {
        return (Scene) sceneRoot.getTag(C3132R.C3134id.current_scene);
    }

    public void setEnterAction(Runnable action) {
        this.mEnterAction = action;
    }

    public void setExitAction(Runnable action) {
        this.mExitAction = action;
    }

    boolean isCreatedFromLayoutResource() {
        return this.mLayoutId > 0;
    }
}
