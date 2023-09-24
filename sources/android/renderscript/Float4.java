package android.renderscript;

/* loaded from: classes3.dex */
public class Float4 {

    /* renamed from: w */
    public float f195w;

    /* renamed from: x */
    public float f196x;

    /* renamed from: y */
    public float f197y;

    /* renamed from: z */
    public float f198z;

    public Float4() {
    }

    public Float4(Float4 data) {
        this.f196x = data.f196x;
        this.f197y = data.f197y;
        this.f198z = data.f198z;
        this.f195w = data.f195w;
    }

    public Float4(float x, float y, float z, float w) {
        this.f196x = x;
        this.f197y = y;
        this.f198z = z;
        this.f195w = w;
    }

    public static Float4 add(Float4 a, Float4 b) {
        Float4 res = new Float4();
        res.f196x = a.f196x + b.f196x;
        res.f197y = a.f197y + b.f197y;
        res.f198z = a.f198z + b.f198z;
        res.f195w = a.f195w + b.f195w;
        return res;
    }

    public void add(Float4 value) {
        this.f196x += value.f196x;
        this.f197y += value.f197y;
        this.f198z += value.f198z;
        this.f195w += value.f195w;
    }

    public void add(float value) {
        this.f196x += value;
        this.f197y += value;
        this.f198z += value;
        this.f195w += value;
    }

    public static Float4 add(Float4 a, float b) {
        Float4 res = new Float4();
        res.f196x = a.f196x + b;
        res.f197y = a.f197y + b;
        res.f198z = a.f198z + b;
        res.f195w = a.f195w + b;
        return res;
    }

    public void sub(Float4 value) {
        this.f196x -= value.f196x;
        this.f197y -= value.f197y;
        this.f198z -= value.f198z;
        this.f195w -= value.f195w;
    }

    public void sub(float value) {
        this.f196x -= value;
        this.f197y -= value;
        this.f198z -= value;
        this.f195w -= value;
    }

    public static Float4 sub(Float4 a, float b) {
        Float4 res = new Float4();
        res.f196x = a.f196x - b;
        res.f197y = a.f197y - b;
        res.f198z = a.f198z - b;
        res.f195w = a.f195w - b;
        return res;
    }

    public static Float4 sub(Float4 a, Float4 b) {
        Float4 res = new Float4();
        res.f196x = a.f196x - b.f196x;
        res.f197y = a.f197y - b.f197y;
        res.f198z = a.f198z - b.f198z;
        res.f195w = a.f195w - b.f195w;
        return res;
    }

    public void mul(Float4 value) {
        this.f196x *= value.f196x;
        this.f197y *= value.f197y;
        this.f198z *= value.f198z;
        this.f195w *= value.f195w;
    }

    public void mul(float value) {
        this.f196x *= value;
        this.f197y *= value;
        this.f198z *= value;
        this.f195w *= value;
    }

    public static Float4 mul(Float4 a, Float4 b) {
        Float4 res = new Float4();
        res.f196x = a.f196x * b.f196x;
        res.f197y = a.f197y * b.f197y;
        res.f198z = a.f198z * b.f198z;
        res.f195w = a.f195w * b.f195w;
        return res;
    }

    public static Float4 mul(Float4 a, float b) {
        Float4 res = new Float4();
        res.f196x = a.f196x * b;
        res.f197y = a.f197y * b;
        res.f198z = a.f198z * b;
        res.f195w = a.f195w * b;
        return res;
    }

    public void div(Float4 value) {
        this.f196x /= value.f196x;
        this.f197y /= value.f197y;
        this.f198z /= value.f198z;
        this.f195w /= value.f195w;
    }

    public void div(float value) {
        this.f196x /= value;
        this.f197y /= value;
        this.f198z /= value;
        this.f195w /= value;
    }

    public static Float4 div(Float4 a, float b) {
        Float4 res = new Float4();
        res.f196x = a.f196x / b;
        res.f197y = a.f197y / b;
        res.f198z = a.f198z / b;
        res.f195w = a.f195w / b;
        return res;
    }

    public static Float4 div(Float4 a, Float4 b) {
        Float4 res = new Float4();
        res.f196x = a.f196x / b.f196x;
        res.f197y = a.f197y / b.f197y;
        res.f198z = a.f198z / b.f198z;
        res.f195w = a.f195w / b.f195w;
        return res;
    }

    public float dotProduct(Float4 a) {
        return (this.f196x * a.f196x) + (this.f197y * a.f197y) + (this.f198z * a.f198z) + (this.f195w * a.f195w);
    }

    public static float dotProduct(Float4 a, Float4 b) {
        return (b.f196x * a.f196x) + (b.f197y * a.f197y) + (b.f198z * a.f198z) + (b.f195w * a.f195w);
    }

    public void addMultiple(Float4 a, float factor) {
        this.f196x += a.f196x * factor;
        this.f197y += a.f197y * factor;
        this.f198z += a.f198z * factor;
        this.f195w += a.f195w * factor;
    }

    public void set(Float4 a) {
        this.f196x = a.f196x;
        this.f197y = a.f197y;
        this.f198z = a.f198z;
        this.f195w = a.f195w;
    }

    public void negate() {
        this.f196x = -this.f196x;
        this.f197y = -this.f197y;
        this.f198z = -this.f198z;
        this.f195w = -this.f195w;
    }

    public int length() {
        return 4;
    }

    public float elementSum() {
        return this.f196x + this.f197y + this.f198z + this.f195w;
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.f196x;
            case 1:
                return this.f197y;
            case 2:
                return this.f198z;
            case 3:
                return this.f195w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, float value) {
        switch (i) {
            case 0:
                this.f196x = value;
                return;
            case 1:
                this.f197y = value;
                return;
            case 2:
                this.f198z = value;
                return;
            case 3:
                this.f195w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float value) {
        switch (i) {
            case 0:
                this.f196x += value;
                return;
            case 1:
                this.f197y += value;
                return;
            case 2:
                this.f198z += value;
                return;
            case 3:
                this.f195w += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float x, float y, float z, float w) {
        this.f196x = x;
        this.f197y = y;
        this.f198z = z;
        this.f195w = w;
    }

    public void copyTo(float[] data, int offset) {
        data[offset] = this.f196x;
        data[offset + 1] = this.f197y;
        data[offset + 2] = this.f198z;
        data[offset + 3] = this.f195w;
    }
}
