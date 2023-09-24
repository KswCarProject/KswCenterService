package android.renderscript;

/* loaded from: classes3.dex */
public class Double4 {

    /* renamed from: w */
    public double f186w;

    /* renamed from: x */
    public double f187x;

    /* renamed from: y */
    public double f188y;

    /* renamed from: z */
    public double f189z;

    public Double4() {
    }

    public Double4(Double4 data) {
        this.f187x = data.f187x;
        this.f188y = data.f188y;
        this.f189z = data.f189z;
        this.f186w = data.f186w;
    }

    public Double4(double x, double y, double z, double w) {
        this.f187x = x;
        this.f188y = y;
        this.f189z = z;
        this.f186w = w;
    }

    public static Double4 add(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f187x = a.f187x + b.f187x;
        res.f188y = a.f188y + b.f188y;
        res.f189z = a.f189z + b.f189z;
        res.f186w = a.f186w + b.f186w;
        return res;
    }

    public void add(Double4 value) {
        this.f187x += value.f187x;
        this.f188y += value.f188y;
        this.f189z += value.f189z;
        this.f186w += value.f186w;
    }

    public void add(double value) {
        this.f187x += value;
        this.f188y += value;
        this.f189z += value;
        this.f186w += value;
    }

    public static Double4 add(Double4 a, double b) {
        Double4 res = new Double4();
        res.f187x = a.f187x + b;
        res.f188y = a.f188y + b;
        res.f189z = a.f189z + b;
        res.f186w = a.f186w + b;
        return res;
    }

    public void sub(Double4 value) {
        this.f187x -= value.f187x;
        this.f188y -= value.f188y;
        this.f189z -= value.f189z;
        this.f186w -= value.f186w;
    }

    public void sub(double value) {
        this.f187x -= value;
        this.f188y -= value;
        this.f189z -= value;
        this.f186w -= value;
    }

    public static Double4 sub(Double4 a, double b) {
        Double4 res = new Double4();
        res.f187x = a.f187x - b;
        res.f188y = a.f188y - b;
        res.f189z = a.f189z - b;
        res.f186w = a.f186w - b;
        return res;
    }

    public static Double4 sub(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f187x = a.f187x - b.f187x;
        res.f188y = a.f188y - b.f188y;
        res.f189z = a.f189z - b.f189z;
        res.f186w = a.f186w - b.f186w;
        return res;
    }

    public void mul(Double4 value) {
        this.f187x *= value.f187x;
        this.f188y *= value.f188y;
        this.f189z *= value.f189z;
        this.f186w *= value.f186w;
    }

    public void mul(double value) {
        this.f187x *= value;
        this.f188y *= value;
        this.f189z *= value;
        this.f186w *= value;
    }

    public static Double4 mul(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f187x = a.f187x * b.f187x;
        res.f188y = a.f188y * b.f188y;
        res.f189z = a.f189z * b.f189z;
        res.f186w = a.f186w * b.f186w;
        return res;
    }

    public static Double4 mul(Double4 a, double b) {
        Double4 res = new Double4();
        res.f187x = a.f187x * b;
        res.f188y = a.f188y * b;
        res.f189z = a.f189z * b;
        res.f186w = a.f186w * b;
        return res;
    }

    public void div(Double4 value) {
        this.f187x /= value.f187x;
        this.f188y /= value.f188y;
        this.f189z /= value.f189z;
        this.f186w /= value.f186w;
    }

    public void div(double value) {
        this.f187x /= value;
        this.f188y /= value;
        this.f189z /= value;
        this.f186w /= value;
    }

    public static Double4 div(Double4 a, double b) {
        Double4 res = new Double4();
        res.f187x = a.f187x / b;
        res.f188y = a.f188y / b;
        res.f189z = a.f189z / b;
        res.f186w = a.f186w / b;
        return res;
    }

    public static Double4 div(Double4 a, Double4 b) {
        Double4 res = new Double4();
        res.f187x = a.f187x / b.f187x;
        res.f188y = a.f188y / b.f188y;
        res.f189z = a.f189z / b.f189z;
        res.f186w = a.f186w / b.f186w;
        return res;
    }

    public double dotProduct(Double4 a) {
        return (this.f187x * a.f187x) + (this.f188y * a.f188y) + (this.f189z * a.f189z) + (this.f186w * a.f186w);
    }

    public static double dotProduct(Double4 a, Double4 b) {
        return (b.f187x * a.f187x) + (b.f188y * a.f188y) + (b.f189z * a.f189z) + (b.f186w * a.f186w);
    }

    public void addMultiple(Double4 a, double factor) {
        this.f187x += a.f187x * factor;
        this.f188y += a.f188y * factor;
        this.f189z += a.f189z * factor;
        this.f186w += a.f186w * factor;
    }

    public void set(Double4 a) {
        this.f187x = a.f187x;
        this.f188y = a.f188y;
        this.f189z = a.f189z;
        this.f186w = a.f186w;
    }

    public void negate() {
        this.f187x = -this.f187x;
        this.f188y = -this.f188y;
        this.f189z = -this.f189z;
        this.f186w = -this.f186w;
    }

    public int length() {
        return 4;
    }

    public double elementSum() {
        return this.f187x + this.f188y + this.f189z + this.f186w;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.f187x;
            case 1:
                return this.f188y;
            case 2:
                return this.f189z;
            case 3:
                return this.f186w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, double value) {
        switch (i) {
            case 0:
                this.f187x = value;
                return;
            case 1:
                this.f188y = value;
                return;
            case 2:
                this.f189z = value;
                return;
            case 3:
                this.f186w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double value) {
        switch (i) {
            case 0:
                this.f187x += value;
                return;
            case 1:
                this.f188y += value;
                return;
            case 2:
                this.f189z += value;
                return;
            case 3:
                this.f186w += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double x, double y, double z, double w) {
        this.f187x = x;
        this.f188y = y;
        this.f189z = z;
        this.f186w = w;
    }

    public void copyTo(double[] data, int offset) {
        data[offset] = this.f187x;
        data[offset + 1] = this.f188y;
        data[offset + 2] = this.f189z;
        data[offset + 3] = this.f186w;
    }
}
