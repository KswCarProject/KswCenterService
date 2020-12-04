package com.google.android.gles_jni;

import javax.microedition.khronos.egl.EGLDisplay;

public class EGLDisplayImpl extends EGLDisplay {
    long mEGLDisplay;

    public EGLDisplayImpl(long dpy) {
        this.mEGLDisplay = dpy;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.mEGLDisplay == ((EGLDisplayImpl) o).mEGLDisplay) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (17 * 31) + ((int) (this.mEGLDisplay ^ (this.mEGLDisplay >>> 32)));
    }
}
