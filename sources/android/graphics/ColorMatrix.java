package android.graphics;

import java.util.Arrays;

/* loaded from: classes.dex */
public class ColorMatrix {
    private final float[] mArray;

    public ColorMatrix() {
        this.mArray = new float[20];
        reset();
    }

    public ColorMatrix(float[] src) {
        this.mArray = new float[20];
        System.arraycopy(src, 0, this.mArray, 0, 20);
    }

    public ColorMatrix(ColorMatrix src) {
        this.mArray = new float[20];
        System.arraycopy(src.mArray, 0, this.mArray, 0, 20);
    }

    public final float[] getArray() {
        return this.mArray;
    }

    public void reset() {
        float[] a = this.mArray;
        Arrays.fill(a, 0.0f);
        a[18] = 1.0f;
        a[12] = 1.0f;
        a[6] = 1.0f;
        a[0] = 1.0f;
    }

    public void set(ColorMatrix src) {
        System.arraycopy(src.mArray, 0, this.mArray, 0, 20);
    }

    public void set(float[] src) {
        System.arraycopy(src, 0, this.mArray, 0, 20);
    }

    public void setScale(float rScale, float gScale, float bScale, float aScale) {
        float[] a = this.mArray;
        for (int i = 19; i > 0; i--) {
            a[i] = 0.0f;
        }
        a[0] = rScale;
        a[6] = gScale;
        a[12] = bScale;
        a[18] = aScale;
    }

    public void setRotate(int axis, float degrees) {
        reset();
        double radians = (degrees * 3.141592653589793d) / 180.0d;
        float cosine = (float) Math.cos(radians);
        float sine = (float) Math.sin(radians);
        switch (axis) {
            case 0:
                float[] fArr = this.mArray;
                this.mArray[12] = cosine;
                fArr[6] = cosine;
                this.mArray[7] = sine;
                this.mArray[11] = -sine;
                return;
            case 1:
                float[] fArr2 = this.mArray;
                this.mArray[12] = cosine;
                fArr2[0] = cosine;
                this.mArray[2] = -sine;
                this.mArray[10] = sine;
                return;
            case 2:
                float[] fArr3 = this.mArray;
                this.mArray[6] = cosine;
                fArr3[0] = cosine;
                this.mArray[1] = sine;
                this.mArray[5] = -sine;
                return;
            default:
                throw new RuntimeException();
        }
    }

    public void setConcat(ColorMatrix matA, ColorMatrix matB) {
        float[] tmp;
        if (matA == this || matB == this) {
            tmp = new float[20];
        } else {
            tmp = this.mArray;
        }
        float[] a = matA.mArray;
        float[] b = matB.mArray;
        int i = 0;
        for (int index = 0; index < 20; index += 5) {
            int index2 = i;
            int index3 = 0;
            while (index3 < 4) {
                tmp[index2] = (a[index + 0] * b[index3 + 0]) + (a[index + 1] * b[index3 + 5]) + (a[index + 2] * b[index3 + 10]) + (a[index + 3] * b[index3 + 15]);
                index3++;
                index2++;
            }
            i = index2 + 1;
            tmp[index2] = (a[index + 0] * b[4]) + (a[index + 1] * b[9]) + (a[index + 2] * b[14]) + (a[index + 3] * b[19]) + a[index + 4];
        }
        if (tmp != this.mArray) {
            System.arraycopy(tmp, 0, this.mArray, 0, 20);
        }
    }

    public void preConcat(ColorMatrix prematrix) {
        setConcat(this, prematrix);
    }

    public void postConcat(ColorMatrix postmatrix) {
        setConcat(postmatrix, this);
    }

    public void setSaturation(float sat) {
        reset();
        float[] m = this.mArray;
        float invSat = 1.0f - sat;
        float R = 0.213f * invSat;
        float G = 0.715f * invSat;
        float B = 0.072f * invSat;
        m[0] = R + sat;
        m[1] = G;
        m[2] = B;
        m[5] = R;
        m[6] = G + sat;
        m[7] = B;
        m[10] = R;
        m[11] = G;
        m[12] = B + sat;
    }

    public void setRGB2YUV() {
        reset();
        float[] m = this.mArray;
        m[0] = 0.299f;
        m[1] = 0.587f;
        m[2] = 0.114f;
        m[5] = -0.16874f;
        m[6] = -0.33126f;
        m[7] = 0.5f;
        m[10] = 0.5f;
        m[11] = -0.41869f;
        m[12] = -0.08131f;
    }

    public void setYUV2RGB() {
        reset();
        float[] m = this.mArray;
        m[2] = 1.402f;
        m[5] = 1.0f;
        m[6] = -0.34414f;
        m[7] = -0.71414f;
        m[10] = 1.0f;
        m[11] = 1.772f;
        m[12] = 0.0f;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ColorMatrix) {
            float[] other = ((ColorMatrix) obj).mArray;
            for (int i = 0; i < 20; i++) {
                if (other[i] != this.mArray[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
