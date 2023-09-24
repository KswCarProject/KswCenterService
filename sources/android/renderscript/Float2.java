package android.renderscript;

/* loaded from: classes3.dex */
public class Float2 {

    /* renamed from: x */
    public float f190x;

    /* renamed from: y */
    public float f191y;

    public Float2() {
    }

    public Float2(Float2 data) {
        this.f190x = data.f190x;
        this.f191y = data.f191y;
    }

    public Float2(float x, float y) {
        this.f190x = x;
        this.f191y = y;
    }

    public static Float2 add(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f190x = a.f190x + b.f190x;
        res.f191y = a.f191y + b.f191y;
        return res;
    }

    public void add(Float2 value) {
        this.f190x += value.f190x;
        this.f191y += value.f191y;
    }

    public void add(float value) {
        this.f190x += value;
        this.f191y += value;
    }

    public static Float2 add(Float2 a, float b) {
        Float2 res = new Float2();
        res.f190x = a.f190x + b;
        res.f191y = a.f191y + b;
        return res;
    }

    public void sub(Float2 value) {
        this.f190x -= value.f190x;
        this.f191y -= value.f191y;
    }

    public static Float2 sub(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f190x = a.f190x - b.f190x;
        res.f191y = a.f191y - b.f191y;
        return res;
    }

    public void sub(float value) {
        this.f190x -= value;
        this.f191y -= value;
    }

    public static Float2 sub(Float2 a, float b) {
        Float2 res = new Float2();
        res.f190x = a.f190x - b;
        res.f191y = a.f191y - b;
        return res;
    }

    public void mul(Float2 value) {
        this.f190x *= value.f190x;
        this.f191y *= value.f191y;
    }

    public static Float2 mul(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f190x = a.f190x * b.f190x;
        res.f191y = a.f191y * b.f191y;
        return res;
    }

    public void mul(float value) {
        this.f190x *= value;
        this.f191y *= value;
    }

    public static Float2 mul(Float2 a, float b) {
        Float2 res = new Float2();
        res.f190x = a.f190x * b;
        res.f191y = a.f191y * b;
        return res;
    }

    public void div(Float2 value) {
        this.f190x /= value.f190x;
        this.f191y /= value.f191y;
    }

    public static Float2 div(Float2 a, Float2 b) {
        Float2 res = new Float2();
        res.f190x = a.f190x / b.f190x;
        res.f191y = a.f191y / b.f191y;
        return res;
    }

    public void div(float value) {
        this.f190x /= value;
        this.f191y /= value;
    }

    public static Float2 div(Float2 a, float b) {
        Float2 res = new Float2();
        res.f190x = a.f190x / b;
        res.f191y = a.f191y / b;
        return res;
    }

    public float dotProduct(Float2 a) {
        return (this.f190x * a.f190x) + (this.f191y * a.f191y);
    }

    public static float dotProduct(Float2 a, Float2 b) {
        return (b.f190x * a.f190x) + (b.f191y * a.f191y);
    }

    public void addMultiple(Float2 a, float factor) {
        this.f190x += a.f190x * factor;
        this.f191y += a.f191y * factor;
    }

    public void set(Float2 a) {
        this.f190x = a.f190x;
        this.f191y = a.f191y;
    }

    public void negate() {
        this.f190x = -this.f190x;
        this.f191y = -this.f191y;
    }

    public int length() {
        return 2;
    }

    public float elementSum() {
        return this.f190x + this.f191y;
    }

    public float get(int i) {
        switch (i) {
            case 0:
                return this.f190x;
            case 1:
                return this.f191y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, float value) {
        switch (i) {
            case 0:
                this.f190x = value;
                return;
            case 1:
                this.f191y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, float value) {
        switch (i) {
            case 0:
                this.f190x += value;
                return;
            case 1:
                this.f191y += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(float x, float y) {
        this.f190x = x;
        this.f191y = y;
    }

    public void copyTo(float[] data, int offset) {
        data[offset] = this.f190x;
        data[offset + 1] = this.f191y;
    }
}
