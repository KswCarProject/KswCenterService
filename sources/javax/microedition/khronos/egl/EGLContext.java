package javax.microedition.khronos.egl;

import com.google.android.gles_jni.EGLImpl;
import javax.microedition.khronos.opengles.InterfaceC3683GL;

/* loaded from: classes5.dex */
public abstract class EGLContext {
    private static final EGL EGL_INSTANCE = new EGLImpl();

    public abstract InterfaceC3683GL getGL();

    public static EGL getEGL() {
        return EGL_INSTANCE;
    }
}
