package android.renderscript;

/* loaded from: classes3.dex */
public class Long3 {

    /* renamed from: x */
    public long f210x;

    /* renamed from: y */
    public long f211y;

    /* renamed from: z */
    public long f212z;

    public Long3() {
    }

    public Long3(long i) {
        this.f212z = i;
        this.f211y = i;
        this.f210x = i;
    }

    public Long3(long x, long y, long z) {
        this.f210x = x;
        this.f211y = y;
        this.f212z = z;
    }

    public Long3(Long3 source) {
        this.f210x = source.f210x;
        this.f211y = source.f211y;
        this.f212z = source.f212z;
    }

    public void add(Long3 a) {
        this.f210x += a.f210x;
        this.f211y += a.f211y;
        this.f212z += a.f212z;
    }

    public static Long3 add(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f210x = a.f210x + b.f210x;
        result.f211y = a.f211y + b.f211y;
        result.f212z = a.f212z + b.f212z;
        return result;
    }

    public void add(long value) {
        this.f210x += value;
        this.f211y += value;
        this.f212z += value;
    }

    public static Long3 add(Long3 a, long b) {
        Long3 result = new Long3();
        result.f210x = a.f210x + b;
        result.f211y = a.f211y + b;
        result.f212z = a.f212z + b;
        return result;
    }

    public void sub(Long3 a) {
        this.f210x -= a.f210x;
        this.f211y -= a.f211y;
        this.f212z -= a.f212z;
    }

    public static Long3 sub(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f210x = a.f210x - b.f210x;
        result.f211y = a.f211y - b.f211y;
        result.f212z = a.f212z - b.f212z;
        return result;
    }

    public void sub(long value) {
        this.f210x -= value;
        this.f211y -= value;
        this.f212z -= value;
    }

    public static Long3 sub(Long3 a, long b) {
        Long3 result = new Long3();
        result.f210x = a.f210x - b;
        result.f211y = a.f211y - b;
        result.f212z = a.f212z - b;
        return result;
    }

    public void mul(Long3 a) {
        this.f210x *= a.f210x;
        this.f211y *= a.f211y;
        this.f212z *= a.f212z;
    }

    public static Long3 mul(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f210x = a.f210x * b.f210x;
        result.f211y = a.f211y * b.f211y;
        result.f212z = a.f212z * b.f212z;
        return result;
    }

    public void mul(long value) {
        this.f210x *= value;
        this.f211y *= value;
        this.f212z *= value;
    }

    public static Long3 mul(Long3 a, long b) {
        Long3 result = new Long3();
        result.f210x = a.f210x * b;
        result.f211y = a.f211y * b;
        result.f212z = a.f212z * b;
        return result;
    }

    public void div(Long3 a) {
        this.f210x /= a.f210x;
        this.f211y /= a.f211y;
        this.f212z /= a.f212z;
    }

    public static Long3 div(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f210x = a.f210x / b.f210x;
        result.f211y = a.f211y / b.f211y;
        result.f212z = a.f212z / b.f212z;
        return result;
    }

    public void div(long value) {
        this.f210x /= value;
        this.f211y /= value;
        this.f212z /= value;
    }

    public static Long3 div(Long3 a, long b) {
        Long3 result = new Long3();
        result.f210x = a.f210x / b;
        result.f211y = a.f211y / b;
        result.f212z = a.f212z / b;
        return result;
    }

    public void mod(Long3 a) {
        this.f210x %= a.f210x;
        this.f211y %= a.f211y;
        this.f212z %= a.f212z;
    }

    public static Long3 mod(Long3 a, Long3 b) {
        Long3 result = new Long3();
        result.f210x = a.f210x % b.f210x;
        result.f211y = a.f211y % b.f211y;
        result.f212z = a.f212z % b.f212z;
        return result;
    }

    public void mod(long value) {
        this.f210x %= value;
        this.f211y %= value;
        this.f212z %= value;
    }

    public static Long3 mod(Long3 a, long b) {
        Long3 result = new Long3();
        result.f210x = a.f210x % b;
        result.f211y = a.f211y % b;
        result.f212z = a.f212z % b;
        return result;
    }

    public long length() {
        return 3L;
    }

    public void negate() {
        this.f210x = -this.f210x;
        this.f211y = -this.f211y;
        this.f212z = -this.f212z;
    }

    public long dotProduct(Long3 a) {
        return (this.f210x * a.f210x) + (this.f211y * a.f211y) + (this.f212z * a.f212z);
    }

    public static long dotProduct(Long3 a, Long3 b) {
        return (b.f210x * a.f210x) + (b.f211y * a.f211y) + (b.f212z * a.f212z);
    }

    public void addMultiple(Long3 a, long factor) {
        this.f210x += a.f210x * factor;
        this.f211y += a.f211y * factor;
        this.f212z += a.f212z * factor;
    }

    public void set(Long3 a) {
        this.f210x = a.f210x;
        this.f211y = a.f211y;
        this.f212z = a.f212z;
    }

    public void setValues(long a, long b, long c) {
        this.f210x = a;
        this.f211y = b;
        this.f212z = c;
    }

    public long elementSum() {
        return this.f210x + this.f211y + this.f212z;
    }

    public long get(int i) {
        switch (i) {
            case 0:
                return this.f210x;
            case 1:
                return this.f211y;
            case 2:
                return this.f212z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, long value) {
        switch (i) {
            case 0:
                this.f210x = value;
                return;
            case 1:
                this.f211y = value;
                return;
            case 2:
                this.f212z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long value) {
        switch (i) {
            case 0:
                this.f210x += value;
                return;
            case 1:
                this.f211y += value;
                return;
            case 2:
                this.f212z += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] data, int offset) {
        data[offset] = this.f210x;
        data[offset + 1] = this.f211y;
        data[offset + 2] = this.f212z;
    }
}
