package android.renderscript;

/* loaded from: classes3.dex */
public class Byte4 {

    /* renamed from: w */
    public byte f177w;

    /* renamed from: x */
    public byte f178x;

    /* renamed from: y */
    public byte f179y;

    /* renamed from: z */
    public byte f180z;

    public Byte4() {
    }

    public Byte4(byte initX, byte initY, byte initZ, byte initW) {
        this.f178x = initX;
        this.f179y = initY;
        this.f180z = initZ;
        this.f177w = initW;
    }

    public Byte4(Byte4 source) {
        this.f178x = source.f178x;
        this.f179y = source.f179y;
        this.f180z = source.f180z;
        this.f177w = source.f177w;
    }

    public void add(Byte4 a) {
        this.f178x = (byte) (this.f178x + a.f178x);
        this.f179y = (byte) (this.f179y + a.f179y);
        this.f180z = (byte) (this.f180z + a.f180z);
        this.f177w = (byte) (this.f177w + a.f177w);
    }

    public static Byte4 add(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x + b.f178x);
        result.f179y = (byte) (a.f179y + b.f179y);
        result.f180z = (byte) (a.f180z + b.f180z);
        result.f177w = (byte) (a.f177w + b.f177w);
        return result;
    }

    public void add(byte value) {
        this.f178x = (byte) (this.f178x + value);
        this.f179y = (byte) (this.f179y + value);
        this.f180z = (byte) (this.f180z + value);
        this.f177w = (byte) (this.f177w + value);
    }

    public static Byte4 add(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x + b);
        result.f179y = (byte) (a.f179y + b);
        result.f180z = (byte) (a.f180z + b);
        result.f177w = (byte) (a.f177w + b);
        return result;
    }

    public void sub(Byte4 a) {
        this.f178x = (byte) (this.f178x - a.f178x);
        this.f179y = (byte) (this.f179y - a.f179y);
        this.f180z = (byte) (this.f180z - a.f180z);
        this.f177w = (byte) (this.f177w - a.f177w);
    }

    public static Byte4 sub(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x - b.f178x);
        result.f179y = (byte) (a.f179y - b.f179y);
        result.f180z = (byte) (a.f180z - b.f180z);
        result.f177w = (byte) (a.f177w - b.f177w);
        return result;
    }

    public void sub(byte value) {
        this.f178x = (byte) (this.f178x - value);
        this.f179y = (byte) (this.f179y - value);
        this.f180z = (byte) (this.f180z - value);
        this.f177w = (byte) (this.f177w - value);
    }

    public static Byte4 sub(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x - b);
        result.f179y = (byte) (a.f179y - b);
        result.f180z = (byte) (a.f180z - b);
        result.f177w = (byte) (a.f177w - b);
        return result;
    }

    public void mul(Byte4 a) {
        this.f178x = (byte) (this.f178x * a.f178x);
        this.f179y = (byte) (this.f179y * a.f179y);
        this.f180z = (byte) (this.f180z * a.f180z);
        this.f177w = (byte) (this.f177w * a.f177w);
    }

    public static Byte4 mul(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x * b.f178x);
        result.f179y = (byte) (a.f179y * b.f179y);
        result.f180z = (byte) (a.f180z * b.f180z);
        result.f177w = (byte) (a.f177w * b.f177w);
        return result;
    }

    public void mul(byte value) {
        this.f178x = (byte) (this.f178x * value);
        this.f179y = (byte) (this.f179y * value);
        this.f180z = (byte) (this.f180z * value);
        this.f177w = (byte) (this.f177w * value);
    }

    public static Byte4 mul(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x * b);
        result.f179y = (byte) (a.f179y * b);
        result.f180z = (byte) (a.f180z * b);
        result.f177w = (byte) (a.f177w * b);
        return result;
    }

    public void div(Byte4 a) {
        this.f178x = (byte) (this.f178x / a.f178x);
        this.f179y = (byte) (this.f179y / a.f179y);
        this.f180z = (byte) (this.f180z / a.f180z);
        this.f177w = (byte) (this.f177w / a.f177w);
    }

    public static Byte4 div(Byte4 a, Byte4 b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x / b.f178x);
        result.f179y = (byte) (a.f179y / b.f179y);
        result.f180z = (byte) (a.f180z / b.f180z);
        result.f177w = (byte) (a.f177w / b.f177w);
        return result;
    }

    public void div(byte value) {
        this.f178x = (byte) (this.f178x / value);
        this.f179y = (byte) (this.f179y / value);
        this.f180z = (byte) (this.f180z / value);
        this.f177w = (byte) (this.f177w / value);
    }

    public static Byte4 div(Byte4 a, byte b) {
        Byte4 result = new Byte4();
        result.f178x = (byte) (a.f178x / b);
        result.f179y = (byte) (a.f179y / b);
        result.f180z = (byte) (a.f180z / b);
        result.f177w = (byte) (a.f177w / b);
        return result;
    }

    public byte length() {
        return (byte) 4;
    }

    public void negate() {
        this.f178x = (byte) (-this.f178x);
        this.f179y = (byte) (-this.f179y);
        this.f180z = (byte) (-this.f180z);
        this.f177w = (byte) (-this.f177w);
    }

    public byte dotProduct(Byte4 a) {
        return (byte) ((this.f178x * a.f178x) + (this.f179y * a.f179y) + (this.f180z * a.f180z) + (this.f177w * a.f177w));
    }

    public static byte dotProduct(Byte4 a, Byte4 b) {
        return (byte) ((b.f178x * a.f178x) + (b.f179y * a.f179y) + (b.f180z * a.f180z) + (b.f177w * a.f177w));
    }

    public void addMultiple(Byte4 a, byte factor) {
        this.f178x = (byte) (this.f178x + (a.f178x * factor));
        this.f179y = (byte) (this.f179y + (a.f179y * factor));
        this.f180z = (byte) (this.f180z + (a.f180z * factor));
        this.f177w = (byte) (this.f177w + (a.f177w * factor));
    }

    public void set(Byte4 a) {
        this.f178x = a.f178x;
        this.f179y = a.f179y;
        this.f180z = a.f180z;
        this.f177w = a.f177w;
    }

    public void setValues(byte a, byte b, byte c, byte d) {
        this.f178x = a;
        this.f179y = b;
        this.f180z = c;
        this.f177w = d;
    }

    public byte elementSum() {
        return (byte) (this.f178x + this.f179y + this.f180z + this.f177w);
    }

    public byte get(int i) {
        switch (i) {
            case 0:
                return this.f178x;
            case 1:
                return this.f179y;
            case 2:
                return this.f180z;
            case 3:
                return this.f177w;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f178x = value;
                return;
            case 1:
                this.f179y = value;
                return;
            case 2:
                this.f180z = value;
                return;
            case 3:
                this.f177w = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f178x = (byte) (this.f178x + value);
                return;
            case 1:
                this.f179y = (byte) (this.f179y + value);
                return;
            case 2:
                this.f180z = (byte) (this.f180z + value);
                return;
            case 3:
                this.f177w = (byte) (this.f177w + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] data, int offset) {
        data[offset] = this.f178x;
        data[offset + 1] = this.f179y;
        data[offset + 2] = this.f180z;
        data[offset + 3] = this.f177w;
    }
}
