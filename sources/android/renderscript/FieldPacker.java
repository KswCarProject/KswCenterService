package android.renderscript;

import android.util.Log;
import java.util.BitSet;

/* loaded from: classes3.dex */
public class FieldPacker {
    private BitSet mAlignment;
    private byte[] mData;
    private int mLen;
    private int mPos;

    public FieldPacker(int len) {
        this.mPos = 0;
        this.mLen = len;
        this.mData = new byte[len];
        this.mAlignment = new BitSet();
    }

    public FieldPacker(byte[] data) {
        this.mPos = data.length;
        this.mLen = data.length;
        this.mData = data;
        this.mAlignment = new BitSet();
    }

    static FieldPacker createFromArray(Object[] args) {
        FieldPacker fp = new FieldPacker(RenderScript.sPointerSize * 8);
        for (Object arg : args) {
            fp.addSafely(arg);
        }
        fp.resize(fp.mPos);
        return fp;
    }

    public void align(int v) {
        if (v <= 0 || ((v - 1) & v) != 0) {
            throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + v);
        }
        while ((this.mPos & (v - 1)) != 0) {
            this.mAlignment.flip(this.mPos);
            byte[] bArr = this.mData;
            int i = this.mPos;
            this.mPos = i + 1;
            bArr[i] = 0;
        }
    }

    public void subalign(int v) {
        if (((v - 1) & v) != 0) {
            throw new RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + v);
        }
        while ((this.mPos & (v - 1)) != 0) {
            this.mPos--;
        }
        if (this.mPos > 0) {
            while (this.mAlignment.get(this.mPos - 1)) {
                this.mPos--;
                this.mAlignment.flip(this.mPos);
            }
        }
    }

    public void reset() {
        this.mPos = 0;
    }

    public void reset(int i) {
        if (i < 0 || i > this.mLen) {
            throw new RSIllegalArgumentException("out of range argument: " + i);
        }
        this.mPos = i;
    }

    public void skip(int i) {
        int res = this.mPos + i;
        if (res < 0 || res > this.mLen) {
            throw new RSIllegalArgumentException("out of range argument: " + i);
        }
        this.mPos = res;
    }

    public void addI8(byte v) {
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = v;
    }

    public byte subI8() {
        subalign(1);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        return bArr[i];
    }

    public void addI16(short v) {
        align(2);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (v & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) (v >> 8);
    }

    public short subI16() {
        subalign(2);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        short v = (short) ((bArr[i] & 255) << 8);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos - 1;
        this.mPos = i2;
        return (short) (((short) (bArr2[i2] & 255)) | v);
    }

    public void addI32(int v) {
        align(4);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (v & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((v >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((v >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) ((v >> 24) & 255);
    }

    public int subI32() {
        subalign(4);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        int v = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mData;
        int i2 = this.mPos - 1;
        this.mPos = i2;
        int v2 = v | ((bArr2[i2] & 255) << 16);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos - 1;
        this.mPos = i3;
        int v3 = v2 | ((bArr3[i3] & 255) << 8);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos - 1;
        this.mPos = i4;
        return v3 | (bArr4[i4] & 255);
    }

    public void addI64(long v) {
        align(8);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (v & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((v >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((v >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) ((v >> 24) & 255);
        byte[] bArr5 = this.mData;
        int i5 = this.mPos;
        this.mPos = i5 + 1;
        bArr5[i5] = (byte) ((v >> 32) & 255);
        byte[] bArr6 = this.mData;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        bArr6[i6] = (byte) ((v >> 40) & 255);
        byte[] bArr7 = this.mData;
        int i7 = this.mPos;
        this.mPos = i7 + 1;
        bArr7[i7] = (byte) ((v >> 48) & 255);
        byte[] bArr8 = this.mData;
        int i8 = this.mPos;
        this.mPos = i8 + 1;
        bArr8[i8] = (byte) ((v >> 56) & 255);
    }

    public long subI64() {
        subalign(8);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        byte x = bArr[i];
        long v = 0 | ((x & 255) << 56);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos - 1;
        this.mPos = i2;
        byte x2 = bArr2[i2];
        long v2 = v | ((x2 & 255) << 48);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos - 1;
        this.mPos = i3;
        byte x3 = bArr3[i3];
        long v3 = v2 | ((x3 & 255) << 40);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos - 1;
        this.mPos = i4;
        byte x4 = bArr4[i4];
        long v4 = v3 | ((x4 & 255) << 32);
        byte[] bArr5 = this.mData;
        int i5 = this.mPos - 1;
        this.mPos = i5;
        byte x5 = bArr5[i5];
        long v5 = v4 | ((x5 & 255) << 24);
        byte[] bArr6 = this.mData;
        int i6 = this.mPos - 1;
        this.mPos = i6;
        byte x6 = bArr6[i6];
        long v6 = v5 | ((x6 & 255) << 16);
        byte[] bArr7 = this.mData;
        int i7 = this.mPos - 1;
        this.mPos = i7;
        byte x7 = bArr7[i7];
        long v7 = v6 | ((x7 & 255) << 8);
        byte[] bArr8 = this.mData;
        int i8 = this.mPos - 1;
        this.mPos = i8;
        byte x8 = bArr8[i8];
        return v7 | (x8 & 255);
    }

    public void addU8(short v) {
        if (v < 0 || v > 255) {
            Log.m70e("rs", "FieldPacker.addU8( " + ((int) v) + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) v;
    }

    public void addU16(int v) {
        if (v < 0 || v > 65535) {
            Log.m70e("rs", "FieldPacker.addU16( " + v + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        align(2);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (v & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) (v >> 8);
    }

    public void addU32(long v) {
        if (v < 0 || v > 4294967295L) {
            Log.m70e("rs", "FieldPacker.addU32( " + v + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        align(4);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (v & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((v >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((v >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) (255 & (v >> 24));
    }

    public void addU64(long v) {
        if (v < 0) {
            Log.m70e("rs", "FieldPacker.addU64( " + v + " )");
            throw new IllegalArgumentException("Saving value out of range for type");
        }
        align(8);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (v & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((v >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((v >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) ((v >> 24) & 255);
        byte[] bArr5 = this.mData;
        int i5 = this.mPos;
        this.mPos = i5 + 1;
        bArr5[i5] = (byte) ((v >> 32) & 255);
        byte[] bArr6 = this.mData;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        bArr6[i6] = (byte) ((v >> 40) & 255);
        byte[] bArr7 = this.mData;
        int i7 = this.mPos;
        this.mPos = i7 + 1;
        bArr7[i7] = (byte) ((v >> 48) & 255);
        byte[] bArr8 = this.mData;
        int i8 = this.mPos;
        this.mPos = i8 + 1;
        bArr8[i8] = (byte) ((v >> 56) & 255);
    }

    public void addF32(float v) {
        addI32(Float.floatToRawIntBits(v));
    }

    public float subF32() {
        return Float.intBitsToFloat(subI32());
    }

    public void addF64(double v) {
        addI64(Double.doubleToRawLongBits(v));
    }

    public double subF64() {
        return Double.longBitsToDouble(subI64());
    }

    public void addObj(BaseObj obj) {
        if (obj != null) {
            if (RenderScript.sPointerSize == 8) {
                addI64(obj.getID(null));
                addI64(0L);
                addI64(0L);
                addI64(0L);
                return;
            }
            addI32((int) obj.getID(null));
        } else if (RenderScript.sPointerSize == 8) {
            addI64(0L);
            addI64(0L);
            addI64(0L);
            addI64(0L);
        } else {
            addI32(0);
        }
    }

    public void addF32(Float2 v) {
        addF32(v.f190x);
        addF32(v.f191y);
    }

    public void addF32(Float3 v) {
        addF32(v.f192x);
        addF32(v.f193y);
        addF32(v.f194z);
    }

    public void addF32(Float4 v) {
        addF32(v.f196x);
        addF32(v.f197y);
        addF32(v.f198z);
        addF32(v.f195w);
    }

    public void addF64(Double2 v) {
        addF64(v.f181x);
        addF64(v.f182y);
    }

    public void addF64(Double3 v) {
        addF64(v.f183x);
        addF64(v.f184y);
        addF64(v.f185z);
    }

    public void addF64(Double4 v) {
        addF64(v.f187x);
        addF64(v.f188y);
        addF64(v.f189z);
        addF64(v.f186w);
    }

    public void addI8(Byte2 v) {
        addI8(v.f172x);
        addI8(v.f173y);
    }

    public void addI8(Byte3 v) {
        addI8(v.f174x);
        addI8(v.f175y);
        addI8(v.f176z);
    }

    public void addI8(Byte4 v) {
        addI8(v.f178x);
        addI8(v.f179y);
        addI8(v.f180z);
        addI8(v.f177w);
    }

    public void addU8(Short2 v) {
        addU8(v.f226x);
        addU8(v.f227y);
    }

    public void addU8(Short3 v) {
        addU8(v.f228x);
        addU8(v.f229y);
        addU8(v.f230z);
    }

    public void addU8(Short4 v) {
        addU8(v.f232x);
        addU8(v.f233y);
        addU8(v.f234z);
        addU8(v.f231w);
    }

    public void addI16(Short2 v) {
        addI16(v.f226x);
        addI16(v.f227y);
    }

    public void addI16(Short3 v) {
        addI16(v.f228x);
        addI16(v.f229y);
        addI16(v.f230z);
    }

    public void addI16(Short4 v) {
        addI16(v.f232x);
        addI16(v.f233y);
        addI16(v.f234z);
        addI16(v.f231w);
    }

    public void addU16(Int2 v) {
        addU16(v.f199x);
        addU16(v.f200y);
    }

    public void addU16(Int3 v) {
        addU16(v.f201x);
        addU16(v.f202y);
        addU16(v.f203z);
    }

    public void addU16(Int4 v) {
        addU16(v.f205x);
        addU16(v.f206y);
        addU16(v.f207z);
        addU16(v.f204w);
    }

    public void addI32(Int2 v) {
        addI32(v.f199x);
        addI32(v.f200y);
    }

    public void addI32(Int3 v) {
        addI32(v.f201x);
        addI32(v.f202y);
        addI32(v.f203z);
    }

    public void addI32(Int4 v) {
        addI32(v.f205x);
        addI32(v.f206y);
        addI32(v.f207z);
        addI32(v.f204w);
    }

    public void addU32(Long2 v) {
        addU32(v.f208x);
        addU32(v.f209y);
    }

    public void addU32(Long3 v) {
        addU32(v.f210x);
        addU32(v.f211y);
        addU32(v.f212z);
    }

    public void addU32(Long4 v) {
        addU32(v.f214x);
        addU32(v.f215y);
        addU32(v.f216z);
        addU32(v.f213w);
    }

    public void addI64(Long2 v) {
        addI64(v.f208x);
        addI64(v.f209y);
    }

    public void addI64(Long3 v) {
        addI64(v.f210x);
        addI64(v.f211y);
        addI64(v.f212z);
    }

    public void addI64(Long4 v) {
        addI64(v.f214x);
        addI64(v.f215y);
        addI64(v.f216z);
        addI64(v.f213w);
    }

    public void addU64(Long2 v) {
        addU64(v.f208x);
        addU64(v.f209y);
    }

    public void addU64(Long3 v) {
        addU64(v.f210x);
        addU64(v.f211y);
        addU64(v.f212z);
    }

    public void addU64(Long4 v) {
        addU64(v.f214x);
        addU64(v.f215y);
        addU64(v.f216z);
        addU64(v.f213w);
    }

    public Float2 subFloat2() {
        Float2 v = new Float2();
        v.f191y = subF32();
        v.f190x = subF32();
        return v;
    }

    public Float3 subFloat3() {
        Float3 v = new Float3();
        v.f194z = subF32();
        v.f193y = subF32();
        v.f192x = subF32();
        return v;
    }

    public Float4 subFloat4() {
        Float4 v = new Float4();
        v.f195w = subF32();
        v.f198z = subF32();
        v.f197y = subF32();
        v.f196x = subF32();
        return v;
    }

    public Double2 subDouble2() {
        Double2 v = new Double2();
        v.f182y = subF64();
        v.f181x = subF64();
        return v;
    }

    public Double3 subDouble3() {
        Double3 v = new Double3();
        v.f185z = subF64();
        v.f184y = subF64();
        v.f183x = subF64();
        return v;
    }

    public Double4 subDouble4() {
        Double4 v = new Double4();
        v.f186w = subF64();
        v.f189z = subF64();
        v.f188y = subF64();
        v.f187x = subF64();
        return v;
    }

    public Byte2 subByte2() {
        Byte2 v = new Byte2();
        v.f173y = subI8();
        v.f172x = subI8();
        return v;
    }

    public Byte3 subByte3() {
        Byte3 v = new Byte3();
        v.f176z = subI8();
        v.f175y = subI8();
        v.f174x = subI8();
        return v;
    }

    public Byte4 subByte4() {
        Byte4 v = new Byte4();
        v.f177w = subI8();
        v.f180z = subI8();
        v.f179y = subI8();
        v.f178x = subI8();
        return v;
    }

    public Short2 subShort2() {
        Short2 v = new Short2();
        v.f227y = subI16();
        v.f226x = subI16();
        return v;
    }

    public Short3 subShort3() {
        Short3 v = new Short3();
        v.f230z = subI16();
        v.f229y = subI16();
        v.f228x = subI16();
        return v;
    }

    public Short4 subShort4() {
        Short4 v = new Short4();
        v.f231w = subI16();
        v.f234z = subI16();
        v.f233y = subI16();
        v.f232x = subI16();
        return v;
    }

    public Int2 subInt2() {
        Int2 v = new Int2();
        v.f200y = subI32();
        v.f199x = subI32();
        return v;
    }

    public Int3 subInt3() {
        Int3 v = new Int3();
        v.f203z = subI32();
        v.f202y = subI32();
        v.f201x = subI32();
        return v;
    }

    public Int4 subInt4() {
        Int4 v = new Int4();
        v.f204w = subI32();
        v.f207z = subI32();
        v.f206y = subI32();
        v.f205x = subI32();
        return v;
    }

    public Long2 subLong2() {
        Long2 v = new Long2();
        v.f209y = subI64();
        v.f208x = subI64();
        return v;
    }

    public Long3 subLong3() {
        Long3 v = new Long3();
        v.f212z = subI64();
        v.f211y = subI64();
        v.f210x = subI64();
        return v;
    }

    public Long4 subLong4() {
        Long4 v = new Long4();
        v.f213w = subI64();
        v.f216z = subI64();
        v.f215y = subI64();
        v.f214x = subI64();
        return v;
    }

    public void addMatrix(Matrix4f v) {
        for (int i = 0; i < v.mMat.length; i++) {
            addF32(v.mMat[i]);
        }
    }

    public Matrix4f subMatrix4f() {
        Matrix4f v = new Matrix4f();
        for (int i = v.mMat.length - 1; i >= 0; i--) {
            v.mMat[i] = subF32();
        }
        return v;
    }

    public void addMatrix(Matrix3f v) {
        for (int i = 0; i < v.mMat.length; i++) {
            addF32(v.mMat[i]);
        }
    }

    public Matrix3f subMatrix3f() {
        Matrix3f v = new Matrix3f();
        for (int i = v.mMat.length - 1; i >= 0; i--) {
            v.mMat[i] = subF32();
        }
        return v;
    }

    public void addMatrix(Matrix2f v) {
        for (int i = 0; i < v.mMat.length; i++) {
            addF32(v.mMat[i]);
        }
    }

    public Matrix2f subMatrix2f() {
        Matrix2f v = new Matrix2f();
        for (int i = v.mMat.length - 1; i >= 0; i--) {
            v.mMat[i] = subF32();
        }
        return v;
    }

    public void addBoolean(boolean v) {
        addI8(v ? (byte) 1 : (byte) 0);
    }

    public boolean subBoolean() {
        byte v = subI8();
        if (v == 1) {
            return true;
        }
        return false;
    }

    public final byte[] getData() {
        return this.mData;
    }

    public int getPos() {
        return this.mPos;
    }

    private void add(Object obj) {
        if (obj instanceof Boolean) {
            addBoolean(((Boolean) obj).booleanValue());
        } else if (obj instanceof Byte) {
            addI8(((Byte) obj).byteValue());
        } else if (obj instanceof Short) {
            addI16(((Short) obj).shortValue());
        } else if (obj instanceof Integer) {
            addI32(((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            addI64(((Long) obj).longValue());
        } else if (obj instanceof Float) {
            addF32(((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            addF64(((Double) obj).doubleValue());
        } else if (obj instanceof Byte2) {
            addI8((Byte2) obj);
        } else if (obj instanceof Byte3) {
            addI8((Byte3) obj);
        } else if (obj instanceof Byte4) {
            addI8((Byte4) obj);
        } else if (obj instanceof Short2) {
            addI16((Short2) obj);
        } else if (obj instanceof Short3) {
            addI16((Short3) obj);
        } else if (obj instanceof Short4) {
            addI16((Short4) obj);
        } else if (obj instanceof Int2) {
            addI32((Int2) obj);
        } else if (obj instanceof Int3) {
            addI32((Int3) obj);
        } else if (obj instanceof Int4) {
            addI32((Int4) obj);
        } else if (obj instanceof Long2) {
            addI64((Long2) obj);
        } else if (obj instanceof Long3) {
            addI64((Long3) obj);
        } else if (obj instanceof Long4) {
            addI64((Long4) obj);
        } else if (obj instanceof Float2) {
            addF32((Float2) obj);
        } else if (obj instanceof Float3) {
            addF32((Float3) obj);
        } else if (obj instanceof Float4) {
            addF32((Float4) obj);
        } else if (obj instanceof Double2) {
            addF64((Double2) obj);
        } else if (obj instanceof Double3) {
            addF64((Double3) obj);
        } else if (obj instanceof Double4) {
            addF64((Double4) obj);
        } else if (obj instanceof Matrix2f) {
            addMatrix((Matrix2f) obj);
        } else if (obj instanceof Matrix3f) {
            addMatrix((Matrix3f) obj);
        } else if (obj instanceof Matrix4f) {
            addMatrix((Matrix4f) obj);
        } else if (obj instanceof BaseObj) {
            addObj((BaseObj) obj);
        }
    }

    private boolean resize(int newSize) {
        if (newSize == this.mLen) {
            return false;
        }
        byte[] newData = new byte[newSize];
        System.arraycopy(this.mData, 0, newData, 0, this.mPos);
        this.mData = newData;
        this.mLen = newSize;
        return true;
    }

    private void addSafely(Object obj) {
        boolean retry;
        int oldPos = this.mPos;
        do {
            retry = false;
            try {
                add(obj);
                continue;
            } catch (ArrayIndexOutOfBoundsException e) {
                this.mPos = oldPos;
                resize(this.mLen * 2);
                retry = true;
                continue;
            }
        } while (retry);
    }
}
