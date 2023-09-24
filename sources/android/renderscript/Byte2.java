package android.renderscript;

/* loaded from: classes3.dex */
public class Byte2 {

    /* renamed from: x */
    public byte f172x;

    /* renamed from: y */
    public byte f173y;

    public Byte2() {
    }

    public Byte2(byte initX, byte initY) {
        this.f172x = initX;
        this.f173y = initY;
    }

    public Byte2(Byte2 source) {
        this.f172x = source.f172x;
        this.f173y = source.f173y;
    }

    public void add(Byte2 a) {
        this.f172x = (byte) (this.f172x + a.f172x);
        this.f173y = (byte) (this.f173y + a.f173y);
    }

    public static Byte2 add(Byte2 a, Byte2 b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x + b.f172x);
        result.f173y = (byte) (a.f173y + b.f173y);
        return result;
    }

    public void add(byte value) {
        this.f172x = (byte) (this.f172x + value);
        this.f173y = (byte) (this.f173y + value);
    }

    public static Byte2 add(Byte2 a, byte b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x + b);
        result.f173y = (byte) (a.f173y + b);
        return result;
    }

    public void sub(Byte2 a) {
        this.f172x = (byte) (this.f172x - a.f172x);
        this.f173y = (byte) (this.f173y - a.f173y);
    }

    public static Byte2 sub(Byte2 a, Byte2 b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x - b.f172x);
        result.f173y = (byte) (a.f173y - b.f173y);
        return result;
    }

    public void sub(byte value) {
        this.f172x = (byte) (this.f172x - value);
        this.f173y = (byte) (this.f173y - value);
    }

    public static Byte2 sub(Byte2 a, byte b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x - b);
        result.f173y = (byte) (a.f173y - b);
        return result;
    }

    public void mul(Byte2 a) {
        this.f172x = (byte) (this.f172x * a.f172x);
        this.f173y = (byte) (this.f173y * a.f173y);
    }

    public static Byte2 mul(Byte2 a, Byte2 b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x * b.f172x);
        result.f173y = (byte) (a.f173y * b.f173y);
        return result;
    }

    public void mul(byte value) {
        this.f172x = (byte) (this.f172x * value);
        this.f173y = (byte) (this.f173y * value);
    }

    public static Byte2 mul(Byte2 a, byte b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x * b);
        result.f173y = (byte) (a.f173y * b);
        return result;
    }

    public void div(Byte2 a) {
        this.f172x = (byte) (this.f172x / a.f172x);
        this.f173y = (byte) (this.f173y / a.f173y);
    }

    public static Byte2 div(Byte2 a, Byte2 b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x / b.f172x);
        result.f173y = (byte) (a.f173y / b.f173y);
        return result;
    }

    public void div(byte value) {
        this.f172x = (byte) (this.f172x / value);
        this.f173y = (byte) (this.f173y / value);
    }

    public static Byte2 div(Byte2 a, byte b) {
        Byte2 result = new Byte2();
        result.f172x = (byte) (a.f172x / b);
        result.f173y = (byte) (a.f173y / b);
        return result;
    }

    public byte length() {
        return (byte) 2;
    }

    public void negate() {
        this.f172x = (byte) (-this.f172x);
        this.f173y = (byte) (-this.f173y);
    }

    public byte dotProduct(Byte2 a) {
        return (byte) ((this.f172x * a.f172x) + (this.f173y * a.f173y));
    }

    public static byte dotProduct(Byte2 a, Byte2 b) {
        return (byte) ((b.f172x * a.f172x) + (b.f173y * a.f173y));
    }

    public void addMultiple(Byte2 a, byte factor) {
        this.f172x = (byte) (this.f172x + (a.f172x * factor));
        this.f173y = (byte) (this.f173y + (a.f173y * factor));
    }

    public void set(Byte2 a) {
        this.f172x = a.f172x;
        this.f173y = a.f173y;
    }

    public void setValues(byte a, byte b) {
        this.f172x = a;
        this.f173y = b;
    }

    public byte elementSum() {
        return (byte) (this.f172x + this.f173y);
    }

    public byte get(int i) {
        switch (i) {
            case 0:
                return this.f172x;
            case 1:
                return this.f173y;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f172x = value;
                return;
            case 1:
                this.f173y = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f172x = (byte) (this.f172x + value);
                return;
            case 1:
                this.f173y = (byte) (this.f173y + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] data, int offset) {
        data[offset] = this.f172x;
        data[offset + 1] = this.f173y;
    }
}
