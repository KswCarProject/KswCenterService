package android.renderscript;

/* loaded from: classes3.dex */
public class Float3 {

    /* renamed from: x */
    public float f192x;

    /* renamed from: y */
    public float f193y;

    /* renamed from: z */
    public float f194z;

    public Float3() {
    }

    public Float3(Float3 data) {
        this.f192x = data.f192x;
        this.f193y = data.f193y;
        this.f194z = data.f194z;
    }

    public Float3(float x, float y, float z) {
        this.f192x = x;
        this.f193y = y;
        this.f194z = z;
    }

    public static Float3 add(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f192x = a.f192x + b.f192x;
        res.f193y = a.f193y + b.f193y;
        res.f194z = a.f194z + b.f194z;
        return res;
    }

    public void add(Float3 value) {
        this.f192x += value.f192x;
        this.f193y += value.f193y;
        this.f194z += value.f194z;
    }

    public void add(float value) {
        this.f192x += value;
        this.f193y += value;
        this.f194z += value;
    }

    public static Float3 add(Float3 a, float b) {
        Float3 res = new Float3();
        res.f192x = a.f192x + b;
        res.f193y = a.f193y + b;
        res.f194z = a.f194z + b;
        return res;
    }

    public void sub(Float3 value) {
        this.f192x -= value.f192x;
        this.f193y -= value.f193y;
        this.f194z -= value.f194z;
    }

    public static Float3 sub(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f192x = a.f192x - b.f192x;
        res.f193y = a.f193y - b.f193y;
        res.f194z = a.f194z - b.f194z;
        return res;
    }

    public void sub(float value) {
        this.f192x -= value;
        this.f193y -= value;
        this.f194z -= value;
    }

    public static Float3 sub(Float3 a, float b) {
        Float3 res = new Float3();
        res.f192x = a.f192x - b;
        res.f193y = a.f193y - b;
        res.f194z = a.f194z - b;
        return res;
    }

    public void mul(Float3 value) {
        this.f192x *= value.f192x;
        this.f193y *= value.f193y;
        this.f194z *= value.f194z;
    }

    public static Float3 mul(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f192x = a.f192x * b.f192x;
        res.f193y = a.f193y * b.f193y;
        res.f194z = a.f194z * b.f194z;
        return res;
    }

    public void mul(float value) {
        this.f192x *= value;
        this.f193y *= value;
        this.f194z *= value;
    }

    public static Float3 mul(Float3 a, float b) {
        Float3 res = new Float3();
        res.f192x = a.f192x * b;
        res.f193y = a.f193y * b;
        res.f194z = a.f194z * b;
        return res;
    }

    public void div(Float3 value) {
        this.f192x /= value.f192x;
        this.f193y /= value.f193y;
        this.f194z /= value.f194z;
    }

    public static Float3 div(Float3 a, Float3 b) {
        Float3 res = new Float3();
        res.f192x = a.f192x / b.f192x;
        res.f193y = a.f193y / b.f193y;
        res.f194z = a.f194z / b.f194z;
        return res;
    }

    public void div(float value) {
        this.f192x /= value;
        this.f193y /= value;
        this.f194z /= value;
    }

    public static Float3 div(Float3 a, float b) {
        Float3 res = new Float3();
        res.f192x = a.f192x / b;
        res.f193y = a.f193y / b;
        res.f194z = a.f194z / b;
        return res;
    }

    public Float dotProduct(Float3 a) {
        return new Float((this.f192x * a.f192x) + (this.f193y * a.f193y) + (this.f194z * a.f194z));
    }

    public static Float dotProduct(Float3 a, Float3 b) {
        return new Float((b.f192x * a.f192x) + (b.f193y * a.f193y) + (b.f194z * a.f194z));
    }

    public void addMultiple(Float3 a, float factor) {
        this.f192x += a.f192x * factor;
        this.f193y += a.f193y * factor;
        this.f194z += a.f194z * factor;
    }

    public void set(Float3 a) {
        this.f192x = a.f192x;
        this.f193y = a.f193y;
        this.f194z = a.f194z;
    }

    public void negate() {
        this.f192x = -this.f192x;
        this.f193y = -this.f193y;
        this.f194z = -this.f194z;
    }

    public int length() {
        return 3;
    }

    public Float elementSum() {
        return new Float(this.f192x + this.f193y + this.f194z);
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.f192x;
            case 1:
                return this.f193y;
            case 2:
                return this.f194z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, float value) {
        switch (i) {
            case 0:
                this.f192x = value;
                return;
            case 1:
                this.f193y = value;
                return;
            case 2:
                this.f194z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float value) {
        switch (i) {
            case 0:
                this.f192x += value;
                return;
            case 1:
                this.f193y += value;
                return;
            case 2:
                this.f194z += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float x, float y, float z) {
        this.f192x = x;
        this.f193y = y;
        this.f194z = z;
    }

    public void copyTo(float[] data, int offset) {
        data[offset] = this.f192x;
        data[offset + 1] = this.f193y;
        data[offset + 2] = this.f194z;
    }
}
