package android.renderscript;

/* loaded from: classes3.dex */
public class Double3 {

    /* renamed from: x */
    public double f183x;

    /* renamed from: y */
    public double f184y;

    /* renamed from: z */
    public double f185z;

    public Double3() {
    }

    public Double3(Double3 data) {
        this.f183x = data.f183x;
        this.f184y = data.f184y;
        this.f185z = data.f185z;
    }

    public Double3(double x, double y, double z) {
        this.f183x = x;
        this.f184y = y;
        this.f185z = z;
    }

    public static Double3 add(Double3 a, Double3 b) {
        Double3 res = new Double3();
        res.f183x = a.f183x + b.f183x;
        res.f184y = a.f184y + b.f184y;
        res.f185z = a.f185z + b.f185z;
        return res;
    }

    public void add(Double3 value) {
        this.f183x += value.f183x;
        this.f184y += value.f184y;
        this.f185z += value.f185z;
    }

    public void add(double value) {
        this.f183x += value;
        this.f184y += value;
        this.f185z += value;
    }

    public static Double3 add(Double3 a, double b) {
        Double3 res = new Double3();
        res.f183x = a.f183x + b;
        res.f184y = a.f184y + b;
        res.f185z = a.f185z + b;
        return res;
    }

    public void sub(Double3 value) {
        this.f183x -= value.f183x;
        this.f184y -= value.f184y;
        this.f185z -= value.f185z;
    }

    public static Double3 sub(Double3 a, Double3 b) {
        Double3 res = new Double3();
        res.f183x = a.f183x - b.f183x;
        res.f184y = a.f184y - b.f184y;
        res.f185z = a.f185z - b.f185z;
        return res;
    }

    public void sub(double value) {
        this.f183x -= value;
        this.f184y -= value;
        this.f185z -= value;
    }

    public static Double3 sub(Double3 a, double b) {
        Double3 res = new Double3();
        res.f183x = a.f183x - b;
        res.f184y = a.f184y - b;
        res.f185z = a.f185z - b;
        return res;
    }

    public void mul(Double3 value) {
        this.f183x *= value.f183x;
        this.f184y *= value.f184y;
        this.f185z *= value.f185z;
    }

    public static Double3 mul(Double3 a, Double3 b) {
        Double3 res = new Double3();
        res.f183x = a.f183x * b.f183x;
        res.f184y = a.f184y * b.f184y;
        res.f185z = a.f185z * b.f185z;
        return res;
    }

    public void mul(double value) {
        this.f183x *= value;
        this.f184y *= value;
        this.f185z *= value;
    }

    public static Double3 mul(Double3 a, double b) {
        Double3 res = new Double3();
        res.f183x = a.f183x * b;
        res.f184y = a.f184y * b;
        res.f185z = a.f185z * b;
        return res;
    }

    public void div(Double3 value) {
        this.f183x /= value.f183x;
        this.f184y /= value.f184y;
        this.f185z /= value.f185z;
    }

    public static Double3 div(Double3 a, Double3 b) {
        Double3 res = new Double3();
        res.f183x = a.f183x / b.f183x;
        res.f184y = a.f184y / b.f184y;
        res.f185z = a.f185z / b.f185z;
        return res;
    }

    public void div(double value) {
        this.f183x /= value;
        this.f184y /= value;
        this.f185z /= value;
    }

    public static Double3 div(Double3 a, double b) {
        Double3 res = new Double3();
        res.f183x = a.f183x / b;
        res.f184y = a.f184y / b;
        res.f185z = a.f185z / b;
        return res;
    }

    public double dotProduct(Double3 a) {
        return (this.f183x * a.f183x) + (this.f184y * a.f184y) + (this.f185z * a.f185z);
    }

    public static double dotProduct(Double3 a, Double3 b) {
        return (b.f183x * a.f183x) + (b.f184y * a.f184y) + (b.f185z * a.f185z);
    }

    public void addMultiple(Double3 a, double factor) {
        this.f183x += a.f183x * factor;
        this.f184y += a.f184y * factor;
        this.f185z += a.f185z * factor;
    }

    public void set(Double3 a) {
        this.f183x = a.f183x;
        this.f184y = a.f184y;
        this.f185z = a.f185z;
    }

    public void negate() {
        this.f183x = -this.f183x;
        this.f184y = -this.f184y;
        this.f185z = -this.f185z;
    }

    public int length() {
        return 3;
    }

    public double elementSum() {
        return this.f183x + this.f184y + this.f185z;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.f183x;
            case 1:
                return this.f184y;
            case 2:
                return this.f185z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, double value) {
        switch (i) {
            case 0:
                this.f183x = value;
                return;
            case 1:
                this.f184y = value;
                return;
            case 2:
                this.f185z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double value) {
        switch (i) {
            case 0:
                this.f183x += value;
                return;
            case 1:
                this.f184y += value;
                return;
            case 2:
                this.f185z += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double x, double y, double z) {
        this.f183x = x;
        this.f184y = y;
        this.f185z = z;
    }

    public void copyTo(double[] data, int offset) {
        data[offset] = this.f183x;
        data[offset + 1] = this.f184y;
        data[offset + 2] = this.f185z;
    }
}
