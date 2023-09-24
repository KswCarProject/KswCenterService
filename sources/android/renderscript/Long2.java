package android.renderscript;

/* loaded from: classes3.dex */
public class Long2 {

    /* renamed from: x */
    public long f208x;

    /* renamed from: y */
    public long f209y;

    public Long2() {
    }

    public Long2(long i) {
        this.f209y = i;
        this.f208x = i;
    }

    public Long2(long x, long y) {
        this.f208x = x;
        this.f209y = y;
    }

    public Long2(Long2 source) {
        this.f208x = source.f208x;
        this.f209y = source.f209y;
    }

    public void add(Long2 a) {
        this.f208x += a.f208x;
        this.f209y += a.f209y;
    }

    public static Long2 add(Long2 a, Long2 b) {
        Long2 result = new Long2();
        result.f208x = a.f208x + b.f208x;
        result.f209y = a.f209y + b.f209y;
        return result;
    }

    public void add(long value) {
        this.f208x += value;
        this.f209y += value;
    }

    public static Long2 add(Long2 a, long b) {
        Long2 result = new Long2();
        result.f208x = a.f208x + b;
        result.f209y = a.f209y + b;
        return result;
    }

    public void sub(Long2 a) {
        this.f208x -= a.f208x;
        this.f209y -= a.f209y;
    }

    public static Long2 sub(Long2 a, Long2 b) {
        Long2 result = new Long2();
        result.f208x = a.f208x - b.f208x;
        result.f209y = a.f209y - b.f209y;
        return result;
    }

    public void sub(long value) {
        this.f208x -= value;
        this.f209y -= value;
    }

    public static Long2 sub(Long2 a, long b) {
        Long2 result = new Long2();
        result.f208x = a.f208x - b;
        result.f209y = a.f209y - b;
        return result;
    }

    public void mul(Long2 a) {
        this.f208x *= a.f208x;
        this.f209y *= a.f209y;
    }

    public static Long2 mul(Long2 a, Long2 b) {
        Long2 result = new Long2();
        result.f208x = a.f208x * b.f208x;
        result.f209y = a.f209y * b.f209y;
        return result;
    }

    public void mul(long value) {
        this.f208x *= value;
        this.f209y *= value;
    }

    public static Long2 mul(Long2 a, long b) {
        Long2 result = new Long2();
        result.f208x = a.f208x * b;
        result.f209y = a.f209y * b;
        return result;
    }

    public void div(Long2 a) {
        this.f208x /= a.f208x;
        this.f209y /= a.f209y;
    }

    public static Long2 div(Long2 a, Long2 b) {
        Long2 result = new Long2();
        result.f208x = a.f208x / b.f208x;
        result.f209y = a.f209y / b.f209y;
        return result;
    }

    public void div(long value) {
        this.f208x /= value;
        this.f209y /= value;
    }

    public static Long2 div(Long2 a, long b) {
        Long2 result = new Long2();
        result.f208x = a.f208x / b;
        result.f209y = a.f209y / b;
        return result;
    }

    public void mod(Long2 a) {
        this.f208x %= a.f208x;
        this.f209y %= a.f209y;
    }

    public static Long2 mod(Long2 a, Long2 b) {
        Long2 result = new Long2();
        result.f208x = a.f208x % b.f208x;
        result.f209y = a.f209y % b.f209y;
        return result;
    }

    public void mod(long value) {
        this.f208x %= value;
        this.f209y %= value;
    }

    public static Long2 mod(Long2 a, long b) {
        Long2 result = new Long2();
        result.f208x = a.f208x % b;
        result.f209y = a.f209y % b;
        return result;
    }

    public long length() {
        return 2L;
    }

    public void negate() {
        this.f208x = -this.f208x;
        this.f209y = -this.f209y;
    }

    public long dotProduct(Long2 a) {
        return (this.f208x * a.f208x) + (this.f209y * a.f209y);
    }

    public static long dotProduct(Long2 a, Long2 b) {
        return (b.f208x * a.f208x) + (b.f209y * a.f209y);
    }

    public void addMultiple(Long2 a, long factor) {
        this.f208x += a.f208x * factor;
        this.f209y += a.f209y * factor;
    }

    public void set(Long2 a) {
        this.f208x = a.f208x;
        this.f209y = a.f209y;
    }

    public void setValues(long a, long b) {
        this.f208x = a;
        this.f209y = b;
    }

    public long elementSum() {
        return this.f208x + this.f209y;
    }

    public long get(int i) {
        switch (i) {
            case 0:
                return this.f208x;
            case 1:
                return this.f209y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, long value) {
        switch (i) {
            case 0:
                this.f208x = value;
                return;
            case 1:
                this.f209y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, long value) {
        switch (i) {
            case 0:
                this.f208x += value;
                return;
            case 1:
                this.f209y += value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(long[] data, int offset) {
        data[offset] = this.f208x;
        data[offset + 1] = this.f209y;
    }
}
