package android.renderscript;

/* loaded from: classes3.dex */
public class Int2 {

    /* renamed from: x */
    public int f199x;

    /* renamed from: y */
    public int f200y;

    public Int2() {
    }

    public Int2(int i) {
        this.f200y = i;
        this.f199x = i;
    }

    public Int2(int x, int y) {
        this.f199x = x;
        this.f200y = y;
    }

    public Int2(Int2 source) {
        this.f199x = source.f199x;
        this.f200y = source.f200y;
    }

    public void add(Int2 a) {
        this.f199x += a.f199x;
        this.f200y += a.f200y;
    }

    public static Int2 add(Int2 a, Int2 b) {
        Int2 result = new Int2();
        result.f199x = a.f199x + b.f199x;
        result.f200y = a.f200y + b.f200y;
        return result;
    }

    public void add(int value) {
        this.f199x += value;
        this.f200y += value;
    }

    public static Int2 add(Int2 a, int b) {
        Int2 result = new Int2();
        result.f199x = a.f199x + b;
        result.f200y = a.f200y + b;
        return result;
    }

    public void sub(Int2 a) {
        this.f199x -= a.f199x;
        this.f200y -= a.f200y;
    }

    public static Int2 sub(Int2 a, Int2 b) {
        Int2 result = new Int2();
        result.f199x = a.f199x - b.f199x;
        result.f200y = a.f200y - b.f200y;
        return result;
    }

    public void sub(int value) {
        this.f199x -= value;
        this.f200y -= value;
    }

    public static Int2 sub(Int2 a, int b) {
        Int2 result = new Int2();
        result.f199x = a.f199x - b;
        result.f200y = a.f200y - b;
        return result;
    }

    public void mul(Int2 a) {
        this.f199x *= a.f199x;
        this.f200y *= a.f200y;
    }

    public static Int2 mul(Int2 a, Int2 b) {
        Int2 result = new Int2();
        result.f199x = a.f199x * b.f199x;
        result.f200y = a.f200y * b.f200y;
        return result;
    }

    public void mul(int value) {
        this.f199x *= value;
        this.f200y *= value;
    }

    public static Int2 mul(Int2 a, int b) {
        Int2 result = new Int2();
        result.f199x = a.f199x * b;
        result.f200y = a.f200y * b;
        return result;
    }

    public void div(Int2 a) {
        this.f199x /= a.f199x;
        this.f200y /= a.f200y;
    }

    public static Int2 div(Int2 a, Int2 b) {
        Int2 result = new Int2();
        result.f199x = a.f199x / b.f199x;
        result.f200y = a.f200y / b.f200y;
        return result;
    }

    public void div(int value) {
        this.f199x /= value;
        this.f200y /= value;
    }

    public static Int2 div(Int2 a, int b) {
        Int2 result = new Int2();
        result.f199x = a.f199x / b;
        result.f200y = a.f200y / b;
        return result;
    }

    public void mod(Int2 a) {
        this.f199x %= a.f199x;
        this.f200y %= a.f200y;
    }

    public static Int2 mod(Int2 a, Int2 b) {
        Int2 result = new Int2();
        result.f199x = a.f199x % b.f199x;
        result.f200y = a.f200y % b.f200y;
        return result;
    }

    public void mod(int value) {
        this.f199x %= value;
        this.f200y %= value;
    }

    public static Int2 mod(Int2 a, int b) {
        Int2 result = new Int2();
        result.f199x = a.f199x % b;
        result.f200y = a.f200y % b;
        return result;
    }

    public int length() {
        return 2;
    }

    public void negate() {
        this.f199x = -this.f199x;
        this.f200y = -this.f200y;
    }

    public int dotProduct(Int2 a) {
        return (this.f199x * a.f199x) + (this.f200y * a.f200y);
    }

    public static int dotProduct(Int2 a, Int2 b) {
        return (b.f199x * a.f199x) + (b.f200y * a.f200y);
    }

    public void addMultiple(Int2 a, int factor) {
        this.f199x += a.f199x * factor;
        this.f200y += a.f200y * factor;
    }

    public void set(Int2 a) {
        this.f199x = a.f199x;
        this.f200y = a.f200y;
    }

    public void setValues(int a, int b) {
        this.f199x = a;
        this.f200y = b;
    }

    public int elementSum() {
        return this.f199x + this.f200y;
    }

    public int get(int i) {
        switch (i) {
            case 0:
                return this.f199x;
            case 1:
                return this.f200y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, int value) {
        switch (i) {
            case 0:
                this.f199x = value;
                return;
            case 1:
                this.f200y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, int value) {
        switch (i) {
            case 0:
                this.f199x += value;
                return;
            case 1:
                this.f200y += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] data, int offset) {
        data[offset] = this.f199x;
        data[offset + 1] = this.f200y;
    }
}
