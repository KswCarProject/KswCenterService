package android.renderscript;

/* loaded from: classes3.dex */
public class Byte3 {

    /* renamed from: x */
    public byte f174x;

    /* renamed from: y */
    public byte f175y;

    /* renamed from: z */
    public byte f176z;

    public Byte3() {
    }

    public Byte3(byte initX, byte initY, byte initZ) {
        this.f174x = initX;
        this.f175y = initY;
        this.f176z = initZ;
    }

    public Byte3(Byte3 source) {
        this.f174x = source.f174x;
        this.f175y = source.f175y;
        this.f176z = source.f176z;
    }

    public void add(Byte3 a) {
        this.f174x = (byte) (this.f174x + a.f174x);
        this.f175y = (byte) (this.f175y + a.f175y);
        this.f176z = (byte) (this.f176z + a.f176z);
    }

    public static Byte3 add(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x + b.f174x);
        result.f175y = (byte) (a.f175y + b.f175y);
        result.f176z = (byte) (a.f176z + b.f176z);
        return result;
    }

    public void add(byte value) {
        this.f174x = (byte) (this.f174x + value);
        this.f175y = (byte) (this.f175y + value);
        this.f176z = (byte) (this.f176z + value);
    }

    public static Byte3 add(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x + b);
        result.f175y = (byte) (a.f175y + b);
        result.f176z = (byte) (a.f176z + b);
        return result;
    }

    public void sub(Byte3 a) {
        this.f174x = (byte) (this.f174x - a.f174x);
        this.f175y = (byte) (this.f175y - a.f175y);
        this.f176z = (byte) (this.f176z - a.f176z);
    }

    public static Byte3 sub(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x - b.f174x);
        result.f175y = (byte) (a.f175y - b.f175y);
        result.f176z = (byte) (a.f176z - b.f176z);
        return result;
    }

    public void sub(byte value) {
        this.f174x = (byte) (this.f174x - value);
        this.f175y = (byte) (this.f175y - value);
        this.f176z = (byte) (this.f176z - value);
    }

    public static Byte3 sub(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x - b);
        result.f175y = (byte) (a.f175y - b);
        result.f176z = (byte) (a.f176z - b);
        return result;
    }

    public void mul(Byte3 a) {
        this.f174x = (byte) (this.f174x * a.f174x);
        this.f175y = (byte) (this.f175y * a.f175y);
        this.f176z = (byte) (this.f176z * a.f176z);
    }

    public static Byte3 mul(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x * b.f174x);
        result.f175y = (byte) (a.f175y * b.f175y);
        result.f176z = (byte) (a.f176z * b.f176z);
        return result;
    }

    public void mul(byte value) {
        this.f174x = (byte) (this.f174x * value);
        this.f175y = (byte) (this.f175y * value);
        this.f176z = (byte) (this.f176z * value);
    }

    public static Byte3 mul(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x * b);
        result.f175y = (byte) (a.f175y * b);
        result.f176z = (byte) (a.f176z * b);
        return result;
    }

    public void div(Byte3 a) {
        this.f174x = (byte) (this.f174x / a.f174x);
        this.f175y = (byte) (this.f175y / a.f175y);
        this.f176z = (byte) (this.f176z / a.f176z);
    }

    public static Byte3 div(Byte3 a, Byte3 b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x / b.f174x);
        result.f175y = (byte) (a.f175y / b.f175y);
        result.f176z = (byte) (a.f176z / b.f176z);
        return result;
    }

    public void div(byte value) {
        this.f174x = (byte) (this.f174x / value);
        this.f175y = (byte) (this.f175y / value);
        this.f176z = (byte) (this.f176z / value);
    }

    public static Byte3 div(Byte3 a, byte b) {
        Byte3 result = new Byte3();
        result.f174x = (byte) (a.f174x / b);
        result.f175y = (byte) (a.f175y / b);
        result.f176z = (byte) (a.f176z / b);
        return result;
    }

    public byte length() {
        return (byte) 3;
    }

    public void negate() {
        this.f174x = (byte) (-this.f174x);
        this.f175y = (byte) (-this.f175y);
        this.f176z = (byte) (-this.f176z);
    }

    public byte dotProduct(Byte3 a) {
        return (byte) (((byte) (((byte) (this.f174x * a.f174x)) + ((byte) (this.f175y * a.f175y)))) + ((byte) (this.f176z * a.f176z)));
    }

    public static byte dotProduct(Byte3 a, Byte3 b) {
        return (byte) (((byte) (((byte) (b.f174x * a.f174x)) + ((byte) (b.f175y * a.f175y)))) + ((byte) (b.f176z * a.f176z)));
    }

    public void addMultiple(Byte3 a, byte factor) {
        this.f174x = (byte) (this.f174x + (a.f174x * factor));
        this.f175y = (byte) (this.f175y + (a.f175y * factor));
        this.f176z = (byte) (this.f176z + (a.f176z * factor));
    }

    public void set(Byte3 a) {
        this.f174x = a.f174x;
        this.f175y = a.f175y;
        this.f176z = a.f176z;
    }

    public void setValues(byte a, byte b, byte c) {
        this.f174x = a;
        this.f175y = b;
        this.f176z = c;
    }

    public byte elementSum() {
        return (byte) (this.f174x + this.f175y + this.f176z);
    }

    public byte get(int i) {
        switch (i) {
            case 0:
                return this.f174x;
            case 1:
                return this.f175y;
            case 2:
                return this.f176z;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f174x = value;
                return;
            case 1:
                this.f175y = value;
                return;
            case 2:
                this.f176z = value;
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, byte value) {
        switch (i) {
            case 0:
                this.f174x = (byte) (this.f174x + value);
                return;
            case 1:
                this.f175y = (byte) (this.f175y + value);
                return;
            case 2:
                this.f176z = (byte) (this.f176z + value);
                return;
            default:
                throw new IndexOutOfBoundsException("Index: i");
        }
    }

    public void copyTo(byte[] data, int offset) {
        data[offset] = this.f174x;
        data[offset + 1] = this.f175y;
        data[offset + 2] = this.f176z;
    }
}
