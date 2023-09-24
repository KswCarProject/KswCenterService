package android.renderscript;

/* loaded from: classes3.dex */
public class Short4 {

    /* renamed from: w */
    public short f231w;

    /* renamed from: x */
    public short f232x;

    /* renamed from: y */
    public short f233y;

    /* renamed from: z */
    public short f234z;

    public Short4() {
    }

    public Short4(short i) {
        this.f231w = i;
        this.f234z = i;
        this.f233y = i;
        this.f232x = i;
    }

    public Short4(short x, short y, short z, short w) {
        this.f232x = x;
        this.f233y = y;
        this.f234z = z;
        this.f231w = w;
    }

    public Short4(Short4 source) {
        this.f232x = source.f232x;
        this.f233y = source.f233y;
        this.f234z = source.f234z;
        this.f231w = source.f231w;
    }

    public void add(Short4 a) {
        this.f232x = (short) (this.f232x + a.f232x);
        this.f233y = (short) (this.f233y + a.f233y);
        this.f234z = (short) (this.f234z + a.f234z);
        this.f231w = (short) (this.f231w + a.f231w);
    }

    public static Short4 add(Short4 a, Short4 b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x + b.f232x);
        result.f233y = (short) (a.f233y + b.f233y);
        result.f234z = (short) (a.f234z + b.f234z);
        result.f231w = (short) (a.f231w + b.f231w);
        return result;
    }

    public void add(short value) {
        this.f232x = (short) (this.f232x + value);
        this.f233y = (short) (this.f233y + value);
        this.f234z = (short) (this.f234z + value);
        this.f231w = (short) (this.f231w + value);
    }

    public static Short4 add(Short4 a, short b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x + b);
        result.f233y = (short) (a.f233y + b);
        result.f234z = (short) (a.f234z + b);
        result.f231w = (short) (a.f231w + b);
        return result;
    }

    public void sub(Short4 a) {
        this.f232x = (short) (this.f232x - a.f232x);
        this.f233y = (short) (this.f233y - a.f233y);
        this.f234z = (short) (this.f234z - a.f234z);
        this.f231w = (short) (this.f231w - a.f231w);
    }

    public static Short4 sub(Short4 a, Short4 b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x - b.f232x);
        result.f233y = (short) (a.f233y - b.f233y);
        result.f234z = (short) (a.f234z - b.f234z);
        result.f231w = (short) (a.f231w - b.f231w);
        return result;
    }

    public void sub(short value) {
        this.f232x = (short) (this.f232x - value);
        this.f233y = (short) (this.f233y - value);
        this.f234z = (short) (this.f234z - value);
        this.f231w = (short) (this.f231w - value);
    }

    public static Short4 sub(Short4 a, short b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x - b);
        result.f233y = (short) (a.f233y - b);
        result.f234z = (short) (a.f234z - b);
        result.f231w = (short) (a.f231w - b);
        return result;
    }

    public void mul(Short4 a) {
        this.f232x = (short) (this.f232x * a.f232x);
        this.f233y = (short) (this.f233y * a.f233y);
        this.f234z = (short) (this.f234z * a.f234z);
        this.f231w = (short) (this.f231w * a.f231w);
    }

    public static Short4 mul(Short4 a, Short4 b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x * b.f232x);
        result.f233y = (short) (a.f233y * b.f233y);
        result.f234z = (short) (a.f234z * b.f234z);
        result.f231w = (short) (a.f231w * b.f231w);
        return result;
    }

    public void mul(short value) {
        this.f232x = (short) (this.f232x * value);
        this.f233y = (short) (this.f233y * value);
        this.f234z = (short) (this.f234z * value);
        this.f231w = (short) (this.f231w * value);
    }

    public static Short4 mul(Short4 a, short b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x * b);
        result.f233y = (short) (a.f233y * b);
        result.f234z = (short) (a.f234z * b);
        result.f231w = (short) (a.f231w * b);
        return result;
    }

    public void div(Short4 a) {
        this.f232x = (short) (this.f232x / a.f232x);
        this.f233y = (short) (this.f233y / a.f233y);
        this.f234z = (short) (this.f234z / a.f234z);
        this.f231w = (short) (this.f231w / a.f231w);
    }

    public static Short4 div(Short4 a, Short4 b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x / b.f232x);
        result.f233y = (short) (a.f233y / b.f233y);
        result.f234z = (short) (a.f234z / b.f234z);
        result.f231w = (short) (a.f231w / b.f231w);
        return result;
    }

    public void div(short value) {
        this.f232x = (short) (this.f232x / value);
        this.f233y = (short) (this.f233y / value);
        this.f234z = (short) (this.f234z / value);
        this.f231w = (short) (this.f231w / value);
    }

    public static Short4 div(Short4 a, short b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x / b);
        result.f233y = (short) (a.f233y / b);
        result.f234z = (short) (a.f234z / b);
        result.f231w = (short) (a.f231w / b);
        return result;
    }

    public void mod(Short4 a) {
        this.f232x = (short) (this.f232x % a.f232x);
        this.f233y = (short) (this.f233y % a.f233y);
        this.f234z = (short) (this.f234z % a.f234z);
        this.f231w = (short) (this.f231w % a.f231w);
    }

    public static Short4 mod(Short4 a, Short4 b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x % b.f232x);
        result.f233y = (short) (a.f233y % b.f233y);
        result.f234z = (short) (a.f234z % b.f234z);
        result.f231w = (short) (a.f231w % b.f231w);
        return result;
    }

    public void mod(short value) {
        this.f232x = (short) (this.f232x % value);
        this.f233y = (short) (this.f233y % value);
        this.f234z = (short) (this.f234z % value);
        this.f231w = (short) (this.f231w % value);
    }

    public static Short4 mod(Short4 a, short b) {
        Short4 result = new Short4();
        result.f232x = (short) (a.f232x % b);
        result.f233y = (short) (a.f233y % b);
        result.f234z = (short) (a.f234z % b);
        result.f231w = (short) (a.f231w % b);
        return result;
    }

    public short length() {
        return (short) 4;
    }

    public void negate() {
        this.f232x = (short) (-this.f232x);
        this.f233y = (short) (-this.f233y);
        this.f234z = (short) (-this.f234z);
        this.f231w = (short) (-this.f231w);
    }

    public short dotProduct(Short4 a) {
        return (short) ((this.f232x * a.f232x) + (this.f233y * a.f233y) + (this.f234z * a.f234z) + (this.f231w * a.f231w));
    }

    public static short dotProduct(Short4 a, Short4 b) {
        return (short) ((b.f232x * a.f232x) + (b.f233y * a.f233y) + (b.f234z * a.f234z) + (b.f231w * a.f231w));
    }

    public void addMultiple(Short4 a, short factor) {
        this.f232x = (short) (this.f232x + (a.f232x * factor));
        this.f233y = (short) (this.f233y + (a.f233y * factor));
        this.f234z = (short) (this.f234z + (a.f234z * factor));
        this.f231w = (short) (this.f231w + (a.f231w * factor));
    }

    public void set(Short4 a) {
        this.f232x = a.f232x;
        this.f233y = a.f233y;
        this.f234z = a.f234z;
        this.f231w = a.f231w;
    }

    public void setValues(short a, short b, short c, short d) {
        this.f232x = a;
        this.f233y = b;
        this.f234z = c;
        this.f231w = d;
    }

    public short elementSum() {
        return (short) (this.f232x + this.f233y + this.f234z + this.f231w);
    }

    public short get(int i) {
        switch (i) {
            case 0:
                return this.f232x;
            case 1:
                return this.f233y;
            case 2:
                return this.f234z;
            case 3:
                return this.f231w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, short value) {
        switch (i) {
            case 0:
                this.f232x = value;
                return;
            case 1:
                this.f233y = value;
                return;
            case 2:
                this.f234z = value;
                return;
            case 3:
                this.f231w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, short value) {
        switch (i) {
            case 0:
                this.f232x = (short) (this.f232x + value);
                return;
            case 1:
                this.f233y = (short) (this.f233y + value);
                return;
            case 2:
                this.f234z = (short) (this.f234z + value);
                return;
            case 3:
                this.f231w = (short) (this.f231w + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(short[] data, int offset) {
        data[offset] = this.f232x;
        data[offset + 1] = this.f233y;
        data[offset + 2] = this.f234z;
        data[offset + 3] = this.f231w;
    }
}
