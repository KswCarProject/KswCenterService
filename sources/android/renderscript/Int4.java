package android.renderscript;

/* loaded from: classes3.dex */
public class Int4 {

    /* renamed from: w */
    public int f204w;

    /* renamed from: x */
    public int f205x;

    /* renamed from: y */
    public int f206y;

    /* renamed from: z */
    public int f207z;

    public Int4() {
    }

    public Int4(int i) {
        this.f204w = i;
        this.f207z = i;
        this.f206y = i;
        this.f205x = i;
    }

    public Int4(int x, int y, int z, int w) {
        this.f205x = x;
        this.f206y = y;
        this.f207z = z;
        this.f204w = w;
    }

    public Int4(Int4 source) {
        this.f205x = source.f205x;
        this.f206y = source.f206y;
        this.f207z = source.f207z;
        this.f204w = source.f204w;
    }

    public void add(Int4 a) {
        this.f205x += a.f205x;
        this.f206y += a.f206y;
        this.f207z += a.f207z;
        this.f204w += a.f204w;
    }

    public static Int4 add(Int4 a, Int4 b) {
        Int4 result = new Int4();
        result.f205x = a.f205x + b.f205x;
        result.f206y = a.f206y + b.f206y;
        result.f207z = a.f207z + b.f207z;
        result.f204w = a.f204w + b.f204w;
        return result;
    }

    public void add(int value) {
        this.f205x += value;
        this.f206y += value;
        this.f207z += value;
        this.f204w += value;
    }

    public static Int4 add(Int4 a, int b) {
        Int4 result = new Int4();
        result.f205x = a.f205x + b;
        result.f206y = a.f206y + b;
        result.f207z = a.f207z + b;
        result.f204w = a.f204w + b;
        return result;
    }

    public void sub(Int4 a) {
        this.f205x -= a.f205x;
        this.f206y -= a.f206y;
        this.f207z -= a.f207z;
        this.f204w -= a.f204w;
    }

    public static Int4 sub(Int4 a, Int4 b) {
        Int4 result = new Int4();
        result.f205x = a.f205x - b.f205x;
        result.f206y = a.f206y - b.f206y;
        result.f207z = a.f207z - b.f207z;
        result.f204w = a.f204w - b.f204w;
        return result;
    }

    public void sub(int value) {
        this.f205x -= value;
        this.f206y -= value;
        this.f207z -= value;
        this.f204w -= value;
    }

    public static Int4 sub(Int4 a, int b) {
        Int4 result = new Int4();
        result.f205x = a.f205x - b;
        result.f206y = a.f206y - b;
        result.f207z = a.f207z - b;
        result.f204w = a.f204w - b;
        return result;
    }

    public void mul(Int4 a) {
        this.f205x *= a.f205x;
        this.f206y *= a.f206y;
        this.f207z *= a.f207z;
        this.f204w *= a.f204w;
    }

    public static Int4 mul(Int4 a, Int4 b) {
        Int4 result = new Int4();
        result.f205x = a.f205x * b.f205x;
        result.f206y = a.f206y * b.f206y;
        result.f207z = a.f207z * b.f207z;
        result.f204w = a.f204w * b.f204w;
        return result;
    }

    public void mul(int value) {
        this.f205x *= value;
        this.f206y *= value;
        this.f207z *= value;
        this.f204w *= value;
    }

    public static Int4 mul(Int4 a, int b) {
        Int4 result = new Int4();
        result.f205x = a.f205x * b;
        result.f206y = a.f206y * b;
        result.f207z = a.f207z * b;
        result.f204w = a.f204w * b;
        return result;
    }

    public void div(Int4 a) {
        this.f205x /= a.f205x;
        this.f206y /= a.f206y;
        this.f207z /= a.f207z;
        this.f204w /= a.f204w;
    }

    public static Int4 div(Int4 a, Int4 b) {
        Int4 result = new Int4();
        result.f205x = a.f205x / b.f205x;
        result.f206y = a.f206y / b.f206y;
        result.f207z = a.f207z / b.f207z;
        result.f204w = a.f204w / b.f204w;
        return result;
    }

    public void div(int value) {
        this.f205x /= value;
        this.f206y /= value;
        this.f207z /= value;
        this.f204w /= value;
    }

    public static Int4 div(Int4 a, int b) {
        Int4 result = new Int4();
        result.f205x = a.f205x / b;
        result.f206y = a.f206y / b;
        result.f207z = a.f207z / b;
        result.f204w = a.f204w / b;
        return result;
    }

    public void mod(Int4 a) {
        this.f205x %= a.f205x;
        this.f206y %= a.f206y;
        this.f207z %= a.f207z;
        this.f204w %= a.f204w;
    }

    public static Int4 mod(Int4 a, Int4 b) {
        Int4 result = new Int4();
        result.f205x = a.f205x % b.f205x;
        result.f206y = a.f206y % b.f206y;
        result.f207z = a.f207z % b.f207z;
        result.f204w = a.f204w % b.f204w;
        return result;
    }

    public void mod(int value) {
        this.f205x %= value;
        this.f206y %= value;
        this.f207z %= value;
        this.f204w %= value;
    }

    public static Int4 mod(Int4 a, int b) {
        Int4 result = new Int4();
        result.f205x = a.f205x % b;
        result.f206y = a.f206y % b;
        result.f207z = a.f207z % b;
        result.f204w = a.f204w % b;
        return result;
    }

    public int length() {
        return 4;
    }

    public void negate() {
        this.f205x = -this.f205x;
        this.f206y = -this.f206y;
        this.f207z = -this.f207z;
        this.f204w = -this.f204w;
    }

    public int dotProduct(Int4 a) {
        return (this.f205x * a.f205x) + (this.f206y * a.f206y) + (this.f207z * a.f207z) + (this.f204w * a.f204w);
    }

    public static int dotProduct(Int4 a, Int4 b) {
        return (b.f205x * a.f205x) + (b.f206y * a.f206y) + (b.f207z * a.f207z) + (b.f204w * a.f204w);
    }

    public void addMultiple(Int4 a, int factor) {
        this.f205x += a.f205x * factor;
        this.f206y += a.f206y * factor;
        this.f207z += a.f207z * factor;
        this.f204w += a.f204w * factor;
    }

    public void set(Int4 a) {
        this.f205x = a.f205x;
        this.f206y = a.f206y;
        this.f207z = a.f207z;
        this.f204w = a.f204w;
    }

    public void setValues(int a, int b, int c, int d) {
        this.f205x = a;
        this.f206y = b;
        this.f207z = c;
        this.f204w = d;
    }

    public int elementSum() {
        return this.f205x + this.f206y + this.f207z + this.f204w;
    }

    public int get(int i) {
        switch (i) {
            case 0:
                return this.f205x;
            case 1:
                return this.f206y;
            case 2:
                return this.f207z;
            case 3:
                return this.f204w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, int value) {
        switch (i) {
            case 0:
                this.f205x = value;
                return;
            case 1:
                this.f206y = value;
                return;
            case 2:
                this.f207z = value;
                return;
            case 3:
                this.f204w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, int value) {
        switch (i) {
            case 0:
                this.f205x += value;
                return;
            case 1:
                this.f206y += value;
                return;
            case 2:
                this.f207z += value;
                return;
            case 3:
                this.f204w += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] data, int offset) {
        data[offset] = this.f205x;
        data[offset + 1] = this.f206y;
        data[offset + 2] = this.f207z;
        data[offset + 3] = this.f204w;
    }
}
