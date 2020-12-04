package com.android.internal.telephony.uicc.asn1;

import com.android.internal.telephony.uicc.IccUtils;

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

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.android.internal.telephony.uicc.asn1.Asn1Node nextNode() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        /*
            r9 = this;
            int r0 = r9.mPosition
            int r1 = r9.mEnd
            if (r0 >= r1) goto L_0x00ed
            int r0 = r9.mPosition
            r1 = r0
            byte[] r2 = r9.mSrc
            int r3 = r0 + 1
            byte r0 = r2[r0]
            r2 = r0 & 31
            r4 = 31
            if (r2 != r4) goto L_0x0026
        L_0x0015:
            int r2 = r9.mEnd
            if (r3 >= r2) goto L_0x0026
            byte[] r2 = r9.mSrc
            int r4 = r3 + 1
            byte r2 = r2[r3]
            r2 = r2 & 128(0x80, float:1.794E-43)
            if (r2 == 0) goto L_0x0025
            r3 = r4
            goto L_0x0015
        L_0x0025:
            r3 = r4
        L_0x0026:
            int r2 = r9.mEnd
            r4 = 0
            if (r3 >= r2) goto L_0x00d6
            byte[] r2 = r9.mSrc     // Catch:{ IllegalArgumentException -> 0x00be }
            int r5 = r3 - r1
            int r2 = com.android.internal.telephony.uicc.IccUtils.bytesToInt(r2, r1, r5)     // Catch:{ IllegalArgumentException -> 0x00be }
            byte[] r4 = r9.mSrc
            int r5 = r3 + 1
            byte r0 = r4[r3]
            r3 = r0 & 128(0x80, float:1.794E-43)
            if (r3 != 0) goto L_0x0041
            r3 = r0
            goto L_0x0053
        L_0x0041:
            r3 = r0 & 127(0x7f, float:1.78E-43)
            int r4 = r5 + r3
            int r6 = r9.mEnd
            if (r4 > r6) goto L_0x00a7
            byte[] r4 = r9.mSrc     // Catch:{ IllegalArgumentException -> 0x008f }
            int r4 = com.android.internal.telephony.uicc.IccUtils.bytesToInt(r4, r5, r3)     // Catch:{ IllegalArgumentException -> 0x008f }
            int r5 = r5 + r3
            r3 = r4
        L_0x0053:
            int r4 = r5 + r3
            int r6 = r9.mEnd
            if (r4 > r6) goto L_0x0065
            com.android.internal.telephony.uicc.asn1.Asn1Node r4 = new com.android.internal.telephony.uicc.asn1.Asn1Node
            byte[] r6 = r9.mSrc
            r4.<init>(r2, r6, r5, r3)
            int r6 = r5 + r3
            r9.mPosition = r6
            return r4
        L_0x0065:
            com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException r4 = new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Incomplete data at position: "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r7 = ", expected bytes: "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r7 = ", actual bytes: "
            r6.append(r7)
            int r7 = r9.mEnd
            int r7 = r7 - r5
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r4.<init>(r2, r6)
            throw r4
        L_0x008f:
            r4 = move-exception
            com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException r6 = new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Cannot parse length at position: "
            r7.append(r8)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            r6.<init>(r2, r7, r4)
            throw r6
        L_0x00a7:
            com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException r4 = new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Cannot parse length at position: "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            r4.<init>(r2, r6)
            throw r4
        L_0x00be:
            r2 = move-exception
            com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException r5 = new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Cannot parse tag at position: "
            r6.append(r7)
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.<init>(r4, r6, r2)
            throw r5
        L_0x00d6:
            com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException r2 = new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Invalid length at position: "
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            r2.<init>(r4, r5)
            throw r2
        L_0x00ed:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "No bytes to parse."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.uicc.asn1.Asn1Decoder.nextNode():com.android.internal.telephony.uicc.asn1.Asn1Node");
    }
}
