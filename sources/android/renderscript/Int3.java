package android.renderscript;

/* loaded from: classes3.dex */
public class Int3 {

    /* renamed from: x */
    public int f201x;

    /* renamed from: y */
    public int f202y;

    /* renamed from: z */
    public int f203z;

    public Int3() {
    }

    public Int3(int i) {
        this.f203z = i;
        this.f202y = i;
        this.f201x = i;
    }

    public Int3(int x, int y, int z) {
        this.f201x = x;
        this.f202y = y;
        this.f203z = z;
    }

    public Int3(Int3 source) {
        this.f201x = source.f201x;
        this.f202y = source.f202y;
        this.f203z = source.f203z;
    }

    public void add(Int3 a) {
        this.f201x += a.f201x;
        this.f202y += a.f202y;
        this.f203z += a.f203z;
    }

    public static Int3 add(Int3 a, Int3 b) {
        Int3 result = new Int3();
        result.f201x = a.f201x + b.f201x;
        result.f202y = a.f202y + b.f202y;
        result.f203z = a.f203z + b.f203z;
        return result;
    }

    public void add(int value) {
        this.f201x += value;
        this.f202y += value;
        this.f203z += value;
    }

    public static Int3 add(Int3 a, int b) {
        Int3 result = new Int3();
        result.f201x = a.f201x + b;
        result.f202y = a.f202y + b;
        result.f203z = a.f203z + b;
        return result;
    }

    public void sub(Int3 a) {
        this.f201x -= a.f201x;
        this.f202y -= a.f202y;
        this.f203z -= a.f203z;
    }

    public static Int3 sub(Int3 a, Int3 b) {
        Int3 result = new Int3();
        result.f201x = a.f201x - b.f201x;
        result.f202y = a.f202y - b.f202y;
        result.f203z = a.f203z - b.f203z;
        return result;
    }

    public void sub(int value) {
        this.f201x -= value;
        this.f202y -= value;
        this.f203z -= value;
    }

    public static Int3 sub(Int3 a, int b) {
        Int3 result = new Int3();
        result.f201x = a.f201x - b;
        result.f202y = a.f202y - b;
        result.f203z = a.f203z - b;
        return result;
    }

    public void mul(Int3 a) {
        this.f201x *= a.f201x;
        this.f202y *= a.f202y;
        this.f203z *= a.f203z;
    }

    public static Int3 mul(Int3 a, Int3 b) {
        Int3 result = new Int3();
        result.f201x = a.f201x * b.f201x;
        result.f202y = a.f202y * b.f202y;
        result.f203z = a.f203z * b.f203z;
        return result;
    }

    public void mul(int value) {
        this.f201x *= value;
        this.f202y *= value;
        this.f203z *= value;
    }

    public static Int3 mul(Int3 a, int b) {
        Int3 result = new Int3();
        result.f201x = a.f201x * b;
        result.f202y = a.f202y * b;
        result.f203z = a.f203z * b;
        return result;
    }

    public void div(Int3 a) {
        this.f201x /= a.f201x;
        this.f202y /= a.f202y;
        this.f203z /= a.f203z;
    }

    public static Int3 div(Int3 a, Int3 b) {
        Int3 result = new Int3();
        result.f201x = a.f201x / b.f201x;
        result.f202y = a.f202y / b.f202y;
        result.f203z = a.f203z / b.f203z;
        return result;
    }

    public void div(int value) {
        this.f201x /= value;
        this.f202y /= value;
        this.f203z /= value;
    }

    public static Int3 div(Int3 a, int b) {
        Int3 result = new Int3();
        result.f201x = a.f201x / b;
        result.f202y = a.f202y / b;
        result.f203z = a.f203z / b;
        return result;
    }

    public void mod(Int3 a) {
        this.f201x %= a.f201x;
        this.f202y %= a.f202y;
        this.f203z %= a.f203z;
    }

    public static Int3 mod(Int3 a, Int3 b) {
        Int3 result = new Int3();
        result.f201x = a.f201x % b.f201x;
        result.f202y = a.f202y % b.f202y;
        result.f203z = a.f203z % b.f203z;
        return result;
    }

    public void mod(int value) {
        this.f201x %= value;
        this.f202y %= value;
        this.f203z %= value;
    }

    public static Int3 mod(Int3 a, int b) {
        Int3 result = new Int3();
        result.f201x = a.f201x % b;
        result.f202y = a.f202y % b;
        result.f203z = a.f203z % b;
        return result;
    }

    public int length() {
        return 3;
    }

    public void negate() {
        this.f201x = -this.f201x;
        this.f202y = -this.f202y;
        this.f203z = -this.f203z;
    }

    public int dotProduct(Int3 a) {
        return (this.f201x * a.f201x) + (this.f202y * a.f202y) + (this.f203z * a.f203z);
    }

    public static int dotProduct(Int3 a, Int3 b) {
        return (b.f201x * a.f201x) + (b.f202y * a.f202y) + (b.f203z * a.f203z);
    }

    public void addMultiple(Int3 a, int factor) {
        this.f201x += a.f201x * factor;
        this.f202y += a.f202y * factor;
        this.f203z += a.f203z * factor;
    }

    public void set(Int3 a) {
        this.f201x = a.f201x;
        this.f202y = a.f202y;
        this.f203z = a.f203z;
    }

    public void setValues(int a, int b, int c) {
        this.f201x = a;
        this.f202y = b;
        this.f203z = c;
    }

    public int elementSum() {
        return this.f201x + this.f202y + this.f203z;
    }

    public int get(int i) {
        switch (i) {
            case 0:
                return this.f201x;
            case 1:
                return this.f202y;
            case 2:
                return this.f203z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, int value) {
        switch (i) {
            case 0:
                this.f201x = value;
                return;
            case 1:
                this.f202y = value;
                return;
            case 2:
                this.f203z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, int value) {
        switch (i) {
            case 0:
                this.f201x += value;
                return;
            case 1:
                this.f202y += value;
                return;
            case 2:
                this.f203z += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(int[] data, int offset) {
        data[offset] = this.f201x;
        data[offset + 1] = this.f202y;
        data[offset + 2] = this.f203z;
    }
}
