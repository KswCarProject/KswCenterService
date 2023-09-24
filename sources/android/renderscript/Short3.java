package android.renderscript;

/* loaded from: classes3.dex */
public class Short3 {

    /* renamed from: x */
    public short f228x;

    /* renamed from: y */
    public short f229y;

    /* renamed from: z */
    public short f230z;

    public Short3() {
    }

    public Short3(short i) {
        this.f230z = i;
        this.f229y = i;
        this.f228x = i;
    }

    public Short3(short x, short y, short z) {
        this.f228x = x;
        this.f229y = y;
        this.f230z = z;
    }

    public Short3(Short3 source) {
        this.f228x = source.f228x;
        this.f229y = source.f229y;
        this.f230z = source.f230z;
    }

    public void add(Short3 a) {
        this.f228x = (short) (this.f228x + a.f228x);
        this.f229y = (short) (this.f229y + a.f229y);
        this.f230z = (short) (this.f230z + a.f230z);
    }

    public static Short3 add(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x + b.f228x);
        result.f229y = (short) (a.f229y + b.f229y);
        result.f230z = (short) (a.f230z + b.f230z);
        return result;
    }

    public void add(short value) {
        this.f228x = (short) (this.f228x + value);
        this.f229y = (short) (this.f229y + value);
        this.f230z = (short) (this.f230z + value);
    }

    public static Short3 add(Short3 a, short b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x + b);
        result.f229y = (short) (a.f229y + b);
        result.f230z = (short) (a.f230z + b);
        return result;
    }

    public void sub(Short3 a) {
        this.f228x = (short) (this.f228x - a.f228x);
        this.f229y = (short) (this.f229y - a.f229y);
        this.f230z = (short) (this.f230z - a.f230z);
    }

    public static Short3 sub(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x - b.f228x);
        result.f229y = (short) (a.f229y - b.f229y);
        result.f230z = (short) (a.f230z - b.f230z);
        return result;
    }

    public void sub(short value) {
        this.f228x = (short) (this.f228x - value);
        this.f229y = (short) (this.f229y - value);
        this.f230z = (short) (this.f230z - value);
    }

    public static Short3 sub(Short3 a, short b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x - b);
        result.f229y = (short) (a.f229y - b);
        result.f230z = (short) (a.f230z - b);
        return result;
    }

    public void mul(Short3 a) {
        this.f228x = (short) (this.f228x * a.f228x);
        this.f229y = (short) (this.f229y * a.f229y);
        this.f230z = (short) (this.f230z * a.f230z);
    }

    public static Short3 mul(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x * b.f228x);
        result.f229y = (short) (a.f229y * b.f229y);
        result.f230z = (short) (a.f230z * b.f230z);
        return result;
    }

    public void mul(short value) {
        this.f228x = (short) (this.f228x * value);
        this.f229y = (short) (this.f229y * value);
        this.f230z = (short) (this.f230z * value);
    }

    public static Short3 mul(Short3 a, short b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x * b);
        result.f229y = (short) (a.f229y * b);
        result.f230z = (short) (a.f230z * b);
        return result;
    }

    public void div(Short3 a) {
        this.f228x = (short) (this.f228x / a.f228x);
        this.f229y = (short) (this.f229y / a.f229y);
        this.f230z = (short) (this.f230z / a.f230z);
    }

    public static Short3 div(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x / b.f228x);
        result.f229y = (short) (a.f229y / b.f229y);
        result.f230z = (short) (a.f230z / b.f230z);
        return result;
    }

    public void div(short value) {
        this.f228x = (short) (this.f228x / value);
        this.f229y = (short) (this.f229y / value);
        this.f230z = (short) (this.f230z / value);
    }

    public static Short3 div(Short3 a, short b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x / b);
        result.f229y = (short) (a.f229y / b);
        result.f230z = (short) (a.f230z / b);
        return result;
    }

    public void mod(Short3 a) {
        this.f228x = (short) (this.f228x % a.f228x);
        this.f229y = (short) (this.f229y % a.f229y);
        this.f230z = (short) (this.f230z % a.f230z);
    }

    public static Short3 mod(Short3 a, Short3 b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x % b.f228x);
        result.f229y = (short) (a.f229y % b.f229y);
        result.f230z = (short) (a.f230z % b.f230z);
        return result;
    }

    public void mod(short value) {
        this.f228x = (short) (this.f228x % value);
        this.f229y = (short) (this.f229y % value);
        this.f230z = (short) (this.f230z % value);
    }

    public static Short3 mod(Short3 a, short b) {
        Short3 result = new Short3();
        result.f228x = (short) (a.f228x % b);
        result.f229y = (short) (a.f229y % b);
        result.f230z = (short) (a.f230z % b);
        return result;
    }

    public short length() {
        return (short) 3;
    }

    public void negate() {
        this.f228x = (short) (-this.f228x);
        this.f229y = (short) (-this.f229y);
        this.f230z = (short) (-this.f230z);
    }

    public short dotProduct(Short3 a) {
        return (short) ((this.f228x * a.f228x) + (this.f229y * a.f229y) + (this.f230z * a.f230z));
    }

    public static short dotProduct(Short3 a, Short3 b) {
        return (short) ((b.f228x * a.f228x) + (b.f229y * a.f229y) + (b.f230z * a.f230z));
    }

    public void addMultiple(Short3 a, short factor) {
        this.f228x = (short) (this.f228x + (a.f228x * factor));
        this.f229y = (short) (this.f229y + (a.f229y * factor));
        this.f230z = (short) (this.f230z + (a.f230z * factor));
    }

    public void set(Short3 a) {
        this.f228x = a.f228x;
        this.f229y = a.f229y;
        this.f230z = a.f230z;
    }

    public void setValues(short a, short b, short c) {
        this.f228x = a;
        this.f229y = b;
        this.f230z = c;
    }

    public short elementSum() {
        return (short) (this.f228x + this.f229y + this.f230z);
    }

    public short get(int i) {
        switch (i) {
            case 0:
                return this.f228x;
            case 1:
                return this.f229y;
            case 2:
                return this.f230z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, short value) {
        switch (i) {
            case 0:
                this.f228x = value;
                return;
            case 1:
                this.f229y = value;
                return;
            case 2:
                this.f230z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short value) {
        switch (i) {
            case 0:
                this.f228x = (short) (this.f228x + value);
                return;
            case 1:
                this.f229y = (short) (this.f229y + value);
                return;
            case 2:
                this.f230z = (short) (this.f230z + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] data, int offset) {
        data[offset] = this.f228x;
        data[offset + 1] = this.f229y;
        data[offset + 2] = this.f230z;
    }
}
