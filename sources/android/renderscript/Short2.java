package android.renderscript;

/* loaded from: classes3.dex */
public class Short2 {

    /* renamed from: x */
    public short f226x;

    /* renamed from: y */
    public short f227y;

    public Short2() {
    }

    public Short2(short i) {
        this.f227y = i;
        this.f226x = i;
    }

    public Short2(short x, short y) {
        this.f226x = x;
        this.f227y = y;
    }

    public Short2(Short2 source) {
        this.f226x = source.f226x;
        this.f227y = source.f227y;
    }

    public void add(Short2 a) {
        this.f226x = (short) (this.f226x + a.f226x);
        this.f227y = (short) (this.f227y + a.f227y);
    }

    public static Short2 add(Short2 a, Short2 b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x + b.f226x);
        result.f227y = (short) (a.f227y + b.f227y);
        return result;
    }

    public void add(short value) {
        this.f226x = (short) (this.f226x + value);
        this.f227y = (short) (this.f227y + value);
    }

    public static Short2 add(Short2 a, short b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x + b);
        result.f227y = (short) (a.f227y + b);
        return result;
    }

    public void sub(Short2 a) {
        this.f226x = (short) (this.f226x - a.f226x);
        this.f227y = (short) (this.f227y - a.f227y);
    }

    public static Short2 sub(Short2 a, Short2 b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x - b.f226x);
        result.f227y = (short) (a.f227y - b.f227y);
        return result;
    }

    public void sub(short value) {
        this.f226x = (short) (this.f226x - value);
        this.f227y = (short) (this.f227y - value);
    }

    public static Short2 sub(Short2 a, short b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x - b);
        result.f227y = (short) (a.f227y - b);
        return result;
    }

    public void mul(Short2 a) {
        this.f226x = (short) (this.f226x * a.f226x);
        this.f227y = (short) (this.f227y * a.f227y);
    }

    public static Short2 mul(Short2 a, Short2 b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x * b.f226x);
        result.f227y = (short) (a.f227y * b.f227y);
        return result;
    }

    public void mul(short value) {
        this.f226x = (short) (this.f226x * value);
        this.f227y = (short) (this.f227y * value);
    }

    public static Short2 mul(Short2 a, short b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x * b);
        result.f227y = (short) (a.f227y * b);
        return result;
    }

    public void div(Short2 a) {
        this.f226x = (short) (this.f226x / a.f226x);
        this.f227y = (short) (this.f227y / a.f227y);
    }

    public static Short2 div(Short2 a, Short2 b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x / b.f226x);
        result.f227y = (short) (a.f227y / b.f227y);
        return result;
    }

    public void div(short value) {
        this.f226x = (short) (this.f226x / value);
        this.f227y = (short) (this.f227y / value);
    }

    public static Short2 div(Short2 a, short b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x / b);
        result.f227y = (short) (a.f227y / b);
        return result;
    }

    public void mod(Short2 a) {
        this.f226x = (short) (this.f226x % a.f226x);
        this.f227y = (short) (this.f227y % a.f227y);
    }

    public static Short2 mod(Short2 a, Short2 b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x % b.f226x);
        result.f227y = (short) (a.f227y % b.f227y);
        return result;
    }

    public void mod(short value) {
        this.f226x = (short) (this.f226x % value);
        this.f227y = (short) (this.f227y % value);
    }

    public static Short2 mod(Short2 a, short b) {
        Short2 result = new Short2();
        result.f226x = (short) (a.f226x % b);
        result.f227y = (short) (a.f227y % b);
        return result;
    }

    public short length() {
        return (short) 2;
    }

    public void negate() {
        this.f226x = (short) (-this.f226x);
        this.f227y = (short) (-this.f227y);
    }

    public short dotProduct(Short2 a) {
        return (short) ((this.f226x * a.f226x) + (this.f227y * a.f227y));
    }

    public static short dotProduct(Short2 a, Short2 b) {
        return (short) ((b.f226x * a.f226x) + (b.f227y * a.f227y));
    }

    public void addMultiple(Short2 a, short factor) {
        this.f226x = (short) (this.f226x + (a.f226x * factor));
        this.f227y = (short) (this.f227y + (a.f227y * factor));
    }

    public void set(Short2 a) {
        this.f226x = a.f226x;
        this.f227y = a.f227y;
    }

    public void setValues(short a, short b) {
        this.f226x = a;
        this.f227y = b;
    }

    public short elementSum() {
        return (short) (this.f226x + this.f227y);
    }

    public short get(int i) {
        switch (i) {
            case 0:
                return this.f226x;
            case 1:
                return this.f227y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, short value) {
        switch (i) {
            case 0:
                this.f226x = value;
                return;
            case 1:
                this.f227y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short value) {
        switch (i) {
            case 0:
                this.f226x = (short) (this.f226x + value);
                return;
            case 1:
                this.f227y = (short) (this.f227y + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] data, int offset) {
        data[offset] = this.f226x;
        data[offset + 1] = this.f227y;
    }
}
