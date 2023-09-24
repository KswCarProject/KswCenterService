package com.android.internal.telephony.uicc.asn1;

import com.android.internal.telephony.uicc.IccUtils;

/* loaded from: classes4.dex */
public final class Asn1Decoder {
    private final int mEnd;
    private int mPosition;
    private final byte[] mSrc;

    public Asn1Decoder(String hex) {
        this(IccUtils.hexStringToBytes(hex));
    }

    public Asn1Decoder(byte[] src) {
        this(src, 0, src.length);
    }

    public Asn1Decoder(byte[] bytes, int offset, int length) {
        if (offset < 0 || length < 0 || offset + length > bytes.length) {
            throw new IndexOutOfBoundsException("Out of the bounds: bytes=[" + bytes.length + "], offset=" + offset + ", length=" + length);
        }
        this.mSrc = bytes;
        this.mPosition = offset;
        this.mEnd = offset + length;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public boolean hasNextNode() {
        return this.mPosition < this.mEnd;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Asn1Node nextNode() throws InvalidAsn1DataException {
        int lenLen;
        if (this.mPosition >= this.mEnd) {
            throw new IllegalStateException("No bytes to parse.");
        }
        int offset = this.mPosition;
        int offset2 = offset + 1;
        if ((this.mSrc[offset] & 31) == 31) {
            while (true) {
                if (offset2 >= this.mEnd) {
                    break;
                }
                int offset3 = offset2 + 1;
                if ((this.mSrc[offset2] & 128) == 0) {
                    offset2 = offset3;
                    break;
                }
                offset2 = offset3;
            }
        }
        if (offset2 < this.mEnd) {
            try {
                int tag = IccUtils.bytesToInt(this.mSrc, offset, offset2 - offset);
                int offset4 = offset2 + 1;
                int b = this.mSrc[offset2];
                if ((b & 128) == 0) {
                    lenLen = b;
                } else {
                    int lenLen2 = b & 127;
                    if (offset4 + lenLen2 > this.mEnd) {
                        throw new InvalidAsn1DataException(tag, "Cannot parse length at position: " + offset4);
                    }
                    try {
                        int dataLen = IccUtils.bytesToInt(this.mSrc, offset4, lenLen2);
                        offset4 += lenLen2;
                        lenLen = dataLen;
                    } catch (IllegalArgumentException e) {
                        throw new InvalidAsn1DataException(tag, "Cannot parse length at position: " + offset4, e);
                    }
                }
                int dataLen2 = offset4 + lenLen;
                if (dataLen2 > this.mEnd) {
                    throw new InvalidAsn1DataException(tag, "Incomplete data at position: " + offset4 + ", expected bytes: " + lenLen + ", actual bytes: " + (this.mEnd - offset4));
                }
                Asn1Node root = new Asn1Node(tag, this.mSrc, offset4, lenLen);
                this.mPosition = offset4 + lenLen;
                return root;
            } catch (IllegalArgumentException e2) {
                throw new InvalidAsn1DataException(0, "Cannot parse tag at position: " + offset, e2);
            }
        }
        throw new InvalidAsn1DataException(0, "Invalid length at position: " + offset2);
    }
}
