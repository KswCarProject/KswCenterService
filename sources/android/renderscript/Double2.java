package android.renderscript;

/* loaded from: classes3.dex */
public class Double2 {

    /* renamed from: x */
    public double f181x;

    /* renamed from: y */
    public double f182y;

    public Double2() {
    }

    public Double2(Double2 data) {
        this.f181x = data.f181x;
        this.f182y = data.f182y;
    }

    public Double2(double x, double y) {
        this.f181x = x;
        this.f182y = y;
    }

    public static Double2 add(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f181x = a.f181x + b.f181x;
        res.f182y = a.f182y + b.f182y;
        return res;
    }

    public void add(Double2 value) {
        this.f181x += value.f181x;
        this.f182y += value.f182y;
    }

    public void add(double value) {
        this.f181x += value;
        this.f182y += value;
    }

    public static Double2 add(Double2 a, double b) {
        Double2 res = new Double2();
        res.f181x = a.f181x + b;
        res.f182y = a.f182y + b;
        return res;
    }

    public void sub(Double2 value) {
        this.f181x -= value.f181x;
        this.f182y -= value.f182y;
    }

    public static Double2 sub(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f181x = a.f181x - b.f181x;
        res.f182y = a.f182y - b.f182y;
        return res;
    }

    public void sub(double value) {
        this.f181x -= value;
        this.f182y -= value;
    }

    public static Double2 sub(Double2 a, double b) {
        Double2 res = new Double2();
        res.f181x = a.f181x - b;
        res.f182y = a.f182y - b;
        return res;
    }

    public void mul(Double2 value) {
        this.f181x *= value.f181x;
        this.f182y *= value.f182y;
    }

    public static Double2 mul(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f181x = a.f181x * b.f181x;
        res.f182y = a.f182y * b.f182y;
        return res;
    }

    public void mul(double value) {
        this.f181x *= value;
        this.f182y *= value;
    }

    public static Double2 mul(Double2 a, double b) {
        Double2 res = new Double2();
        res.f181x = a.f181x * b;
        res.f182y = a.f182y * b;
        return res;
    }

    public void div(Double2 value) {
        this.f181x /= value.f181x;
        this.f182y /= value.f182y;
    }

    public static Double2 div(Double2 a, Double2 b) {
        Double2 res = new Double2();
        res.f181x = a.f181x / b.f181x;
        res.f182y = a.f182y / b.f182y;
        return res;
    }

    public void div(double value) {
        this.f181x /= value;
        this.f182y /= value;
    }

    public static Double2 div(Double2 a, double b) {
        Double2 res = new Double2();
        res.f181x = a.f181x / b;
        res.f182y = a.f182y / b;
        return res;
    }

    public double dotProduct(Double2 a) {
        return (this.f181x * a.f181x) + (this.f182y * a.f182y);
    }

    public static Double dotProduct(Double2 a, Double2 b) {
        return Double.valueOf((b.f181x * a.f181x) + (b.f182y * a.f182y));
    }

    public void addMultiple(Double2 a, double factor) {
        this.f181x += a.f181x * factor;
        this.f182y += a.f182y * factor;
    }

    public void set(Double2 a) {
        this.f181x = a.f181x;
        this.f182y = a.f182y;
    }

    public void negate() {
        this.f181x = -this.f181x;
        this.f182y = -this.f182y;
    }

    public int length() {
        return 2;
    }

    public double elementSum() {
        return this.f181x + this.f182y;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.f181x;
            case 1:
                return this.f182y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, double value) {
        switch (i) {
            case 0:
                this.f181x = value;
                return;
            case 1:
                this.f182y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double value) {
        switch (i) {
            case 0:
                this.f181x += value;
                return;
            case 1:
                this.f182y += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double x, double y) {
        this.f181x = x;
        this.f182y = y;
    }

    public void copyTo(double[] data, int offset) {
        data[offset] = this.f181x;
        data[offset + 1] = this.f182y;
    }
}
