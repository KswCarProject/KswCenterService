package android.renderscript;

/* loaded from: classes3.dex */
public class Long4 {

    /* renamed from: w */
    public long f213w;

    /* renamed from: x */
    public long f214x;

    /* renamed from: y */
    public long f215y;

    /* renamed from: z */
    public long f216z;

    public Long4() {
    }

    public Long4(long i) {
        this.f213w = i;
        this.f216z = i;
        this.f215y = i;
        this.f214x = i;
    }

    public Long4(long x, long y, long z, long w) {
        this.f214x = x;
        this.f215y = y;
        this.f216z = z;
        this.f213w = w;
    }

    public Long4(Long4 source) {
        this.f214x = source.f214x;
        this.f215y = source.f215y;
        this.f216z = source.f216z;
        this.f213w = source.f213w;
    }

    public void add(Long4 a) {
        this.f214x += a.f214x;
        this.f215y += a.f215y;
        this.f216z += a.f216z;
        this.f213w += a.f213w;
    }

    public static Long4 add(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f214x = a.f214x + b.f214x;
        result.f215y = a.f215y + b.f215y;
        result.f216z = a.f216z + b.f216z;
        result.f213w = a.f213w + b.f213w;
        return result;
    }

    public void add(long value) {
        this.f214x += value;
        this.f215y += value;
        this.f216z += value;
        this.f213w += value;
    }

    public static Long4 add(Long4 a, long b) {
        Long4 result = new Long4();
        result.f214x = a.f214x + b;
        result.f215y = a.f215y + b;
        result.f216z = a.f216z + b;
        result.f213w = a.f213w + b;
        return result;
    }

    public void sub(Long4 a) {
        this.f214x -= a.f214x;
        this.f215y -= a.f215y;
        this.f216z -= a.f216z;
        this.f213w -= a.f213w;
    }

    public static Long4 sub(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f214x = a.f214x - b.f214x;
        result.f215y = a.f215y - b.f215y;
        result.f216z = a.f216z - b.f216z;
        result.f213w = a.f213w - b.f213w;
        return result;
    }

    public void sub(long value) {
        this.f214x -= value;
        this.f215y -= value;
        this.f216z -= value;
        this.f213w -= value;
    }

    public static Long4 sub(Long4 a, long b) {
        Long4 result = new Long4();
        result.f214x = a.f214x - b;
        result.f215y = a.f215y - b;
        result.f216z = a.f216z - b;
        result.f213w = a.f213w - b;
        return result;
    }

    public void mul(Long4 a) {
        this.f214x *= a.f214x;
        this.f215y *= a.f215y;
        this.f216z *= a.f216z;
        this.f213w *= a.f213w;
    }

    public static Long4 mul(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f214x = a.f214x * b.f214x;
        result.f215y = a.f215y * b.f215y;
        result.f216z = a.f216z * b.f216z;
        result.f213w = a.f213w * b.f213w;
        return result;
    }

    public void mul(long value) {
        this.f214x *= value;
        this.f215y *= value;
        this.f216z *= value;
        this.f213w *= value;
    }

    public static Long4 mul(Long4 a, long b) {
        Long4 result = new Long4();
        result.f214x = a.f214x * b;
        result.f215y = a.f215y * b;
        result.f216z = a.f216z * b;
        result.f213w = a.f213w * b;
        return result;
    }

    public void div(Long4 a) {
        this.f214x /= a.f214x;
        this.f215y /= a.f215y;
        this.f216z /= a.f216z;
        this.f213w /= a.f213w;
    }

    public static Long4 div(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f214x = a.f214x / b.f214x;
        result.f215y = a.f215y / b.f215y;
        result.f216z = a.f216z / b.f216z;
        result.f213w = a.f213w / b.f213w;
        return result;
    }

    public void div(long value) {
        this.f214x /= value;
        this.f215y /= value;
        this.f216z /= value;
        this.f213w /= value;
    }

    public static Long4 div(Long4 a, long b) {
        Long4 result = new Long4();
        result.f214x = a.f214x / b;
        result.f215y = a.f215y / b;
        result.f216z = a.f216z / b;
        result.f213w = a.f213w / b;
        return result;
    }

    public void mod(Long4 a) {
        this.f214x %= a.f214x;
        this.f215y %= a.f215y;
        this.f216z %= a.f216z;
        this.f213w %= a.f213w;
    }

    public static Long4 mod(Long4 a, Long4 b) {
        Long4 result = new Long4();
        result.f214x = a.f214x % b.f214x;
        result.f215y = a.f215y % b.f215y;
        result.f216z = a.f216z % b.f216z;
        result.f213w = a.f213w % b.f213w;
        return result;
    }

    public void mod(long value) {
        this.f214x %= value;
        this.f215y %= value;
        this.f216z %= value;
        this.f213w %= value;
    }

    public static Long4 mod(Long4 a, long b) {
        Long4 result = new Long4();
        result.f214x = a.f214x % b;
        result.f215y = a.f215y % b;
        result.f216z = a.f216z % b;
        result.f213w = a.f213w % b;
        return result;
    }

    public long length() {
        return 4L;
    }

    public void negate() {
        this.f214x = -this.f214x;
        this.f215y = -this.f215y;
        this.f216z = -this.f216z;
        this.f213w = -this.f213w;
    }

    public long dotProduct(Long4 a) {
        return (this.f214x * a.f214x) + (this.f215y * a.f215y) + (this.f216z * a.f216z) + (this.f213w * a.f213w);
    }

    public static long dotProduct(Long4 a, Long4 b) {
        return (b.f214x * a.f214x) + (b.f215y * a.f215y) + (b.f216z * a.f216z) + (b.f213w * a.f213w);
    }

    public void addMultiple(Long4 a, long factor) {
        this.f214x += a.f214x * factor;
        this.f215y += a.f215y * factor;
        this.f216z += a.f216z * factor;
        this.f213w += a.f213w * factor;
    }

    public void set(Long4 a) {
        this.f214x = a.f214x;
        this.f215y = a.f215y;
        this.f216z = a.f216z;
        this.f213w = a.f213w;
    }

    public void setValues(long a, long b, long c, long d) {
        this.f214x = a;
        this.f215y = b;
        this.f216z = c;
        this.f213w = d;
    }

    public long elementSum() {
        return this.f214x + this.f215y + this.f216z + this.f213w;
    }

    public long get(int i) {
        switch (i) {
            case 0:
                return this.f214x;
            case 1:
                return this.f215y;
            case 2:
                return this.f216z;
            case 3:
                return this.f213w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, long value) {
        switch (i) {
            case 0:
                this.f214x = value;
                return;
            case 1:
                this.f215y = value;
                return;
            case 2:
                this.f216z = value;
                return;
            case 3:
                this.f213w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long value) {
        switch (i) {
            case 0:
                this.f214x += value;
                return;
            case 1:
                this.f215y += value;
                return;
            case 2:
                this.f216z += value;
                return;
            case 3:
                this.f213w += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] data, int offset) {
        data[offset] = this.f214x;
        data[offset + 1] = this.f215y;
        data[offset + 2] = this.f216z;
        data[offset + 3] = this.f213w;
    }
}
