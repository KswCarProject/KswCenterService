package android.net;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.HexDump;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class IpSecAlgorithm implements Parcelable {
    public static final String AUTH_CRYPT_AES_GCM = "rfc4106(gcm(aes))";
    public static final String AUTH_HMAC_MD5 = "hmac(md5)";
    public static final String AUTH_HMAC_SHA1 = "hmac(sha1)";
    public static final String AUTH_HMAC_SHA256 = "hmac(sha256)";
    public static final String AUTH_HMAC_SHA384 = "hmac(sha384)";
    public static final String AUTH_HMAC_SHA512 = "hmac(sha512)";
    public static final Parcelable.Creator<IpSecAlgorithm> CREATOR = new Parcelable.Creator<IpSecAlgorithm>() {
        public IpSecAlgorithm createFromParcel(Parcel in) {
            return new IpSecAlgorithm(in.readString(), in.createByteArray(), in.readInt());
        }

        public IpSecAlgorithm[] newArray(int size) {
            return new IpSecAlgorithm[size];
        }
    };
    public static final String CRYPT_AES_CBC = "cbc(aes)";
    public static final String CRYPT_NULL = "ecb(cipher_null)";
    private static final String TAG = "IpSecAlgorithm";
    private final byte[] mKey;
    private final String mName;
    private final int mTruncLenBits;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlgorithmName {
    }

    public IpSecAlgorithm(String algorithm, byte[] key) {
        this(algorithm, key, 0);
    }

    public IpSecAlgorithm(String algorithm, byte[] key, int truncLenBits) {
        this.mName = algorithm;
        this.mKey = (byte[]) key.clone();
        this.mTruncLenBits = truncLenBits;
        checkValidOrThrow(this.mName, this.mKey.length * 8, this.mTruncLenBits);
    }

    public String getName() {
        return this.mName;
    }

    public byte[] getKey() {
        return (byte[]) this.mKey.clone();
    }

    public int getTruncationLengthBits() {
        return this.mTruncLenBits;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mName);
        out.writeByteArray(this.mKey);
        out.writeInt(this.mTruncLenBits);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void checkValidOrThrow(java.lang.String r10, int r11, int r12) {
        /*
            r0 = 1
            r1 = 1
            int r2 = r10.hashCode()
            r3 = 1
            r4 = 0
            switch(r2) {
                case -1137603038: goto L_0x0048;
                case 394796030: goto L_0x003e;
                case 559425185: goto L_0x0034;
                case 559457797: goto L_0x002a;
                case 559510590: goto L_0x0020;
                case 759177996: goto L_0x0016;
                case 2065384259: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0053
        L_0x000c:
            java.lang.String r2 = "hmac(sha1)"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = 2
            goto L_0x0054
        L_0x0016:
            java.lang.String r2 = "hmac(md5)"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = r3
            goto L_0x0054
        L_0x0020:
            java.lang.String r2 = "hmac(sha512)"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = 5
            goto L_0x0054
        L_0x002a:
            java.lang.String r2 = "hmac(sha384)"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = 4
            goto L_0x0054
        L_0x0034:
            java.lang.String r2 = "hmac(sha256)"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = 3
            goto L_0x0054
        L_0x003e:
            java.lang.String r2 = "cbc(aes)"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = r4
            goto L_0x0054
        L_0x0048:
            java.lang.String r2 = "rfc4106(gcm(aes))"
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L_0x0053
            r2 = 6
            goto L_0x0054
        L_0x0053:
            r2 = -1
        L_0x0054:
            r5 = 192(0xc0, float:2.69E-43)
            r6 = 160(0xa0, float:2.24E-43)
            r7 = 96
            r8 = 256(0x100, float:3.59E-43)
            r9 = 128(0x80, float:1.794E-43)
            switch(r2) {
                case 0: goto L_0x00e0;
                case 1: goto L_0x00d2;
                case 2: goto L_0x00c4;
                case 3: goto L_0x00b6;
                case 4: goto L_0x00a6;
                case 5: goto L_0x0095;
                case 6: goto L_0x0078;
                default: goto L_0x0061;
            }
        L_0x0061:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Couldn't find an algorithm: "
            r3.append(r4)
            r3.append(r10)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0078:
            if (r11 == r6) goto L_0x0085
            r2 = 224(0xe0, float:3.14E-43)
            if (r11 == r2) goto L_0x0085
            r2 = 288(0x120, float:4.04E-43)
            if (r11 != r2) goto L_0x0083
            goto L_0x0085
        L_0x0083:
            r2 = r4
            goto L_0x0086
        L_0x0085:
            r2 = r3
        L_0x0086:
            r0 = r2
            r2 = 64
            if (r12 == r2) goto L_0x0092
            if (r12 == r7) goto L_0x0092
            if (r12 != r9) goto L_0x0090
            goto L_0x0092
        L_0x0090:
            r3 = r4
        L_0x0092:
            r1 = r3
            goto L_0x00eb
        L_0x0095:
            r2 = 512(0x200, float:7.175E-43)
            if (r11 != r2) goto L_0x009b
            r5 = r3
            goto L_0x009c
        L_0x009b:
            r5 = r4
        L_0x009c:
            r0 = r5
            if (r12 < r8) goto L_0x00a2
            if (r12 > r2) goto L_0x00a2
            goto L_0x00a3
        L_0x00a2:
            r3 = r4
        L_0x00a3:
            r1 = r3
            goto L_0x00eb
        L_0x00a6:
            r2 = 384(0x180, float:5.38E-43)
            if (r11 != r2) goto L_0x00ac
            r6 = r3
            goto L_0x00ad
        L_0x00ac:
            r6 = r4
        L_0x00ad:
            r0 = r6
            if (r12 < r5) goto L_0x00b3
            if (r12 > r2) goto L_0x00b3
            goto L_0x00b4
        L_0x00b3:
            r3 = r4
        L_0x00b4:
            r1 = r3
            goto L_0x00eb
        L_0x00b6:
            if (r11 != r8) goto L_0x00ba
            r2 = r3
            goto L_0x00bb
        L_0x00ba:
            r2 = r4
        L_0x00bb:
            r0 = r2
            if (r12 < r7) goto L_0x00c1
            if (r12 > r8) goto L_0x00c1
            goto L_0x00c2
        L_0x00c1:
            r3 = r4
        L_0x00c2:
            r1 = r3
            goto L_0x00eb
        L_0x00c4:
            if (r11 != r6) goto L_0x00c8
            r2 = r3
            goto L_0x00c9
        L_0x00c8:
            r2 = r4
        L_0x00c9:
            r0 = r2
            if (r12 < r7) goto L_0x00cf
            if (r12 > r6) goto L_0x00cf
            goto L_0x00d0
        L_0x00cf:
            r3 = r4
        L_0x00d0:
            r1 = r3
            goto L_0x00eb
        L_0x00d2:
            if (r11 != r9) goto L_0x00d6
            r2 = r3
            goto L_0x00d7
        L_0x00d6:
            r2 = r4
        L_0x00d7:
            r0 = r2
            if (r12 < r7) goto L_0x00dd
            if (r12 > r9) goto L_0x00dd
            goto L_0x00de
        L_0x00dd:
            r3 = r4
        L_0x00de:
            r1 = r3
            goto L_0x00eb
        L_0x00e0:
            if (r11 == r9) goto L_0x00e9
            if (r11 == r5) goto L_0x00e9
            if (r11 != r8) goto L_0x00e7
            goto L_0x00e9
        L_0x00e7:
            r3 = r4
        L_0x00e9:
            r0 = r3
        L_0x00eb:
            if (r0 == 0) goto L_0x0107
            if (r1 == 0) goto L_0x00f0
            return
        L_0x00f0:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid truncation keyLength: "
            r3.append(r4)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0107:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Invalid key material keyLength: "
            r3.append(r4)
            r3.append(r11)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.IpSecAlgorithm.checkValidOrThrow(java.lang.String, int, int):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isAuthentication() {
        /*
            r4 = this;
            java.lang.String r0 = r4.getName()
            int r1 = r0.hashCode()
            r2 = 1
            r3 = 0
            switch(r1) {
                case 559425185: goto L_0x0036;
                case 559457797: goto L_0x002c;
                case 559510590: goto L_0x0022;
                case 759177996: goto L_0x0018;
                case 2065384259: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0040
        L_0x000e:
            java.lang.String r1 = "hmac(sha1)"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = r2
            goto L_0x0041
        L_0x0018:
            java.lang.String r1 = "hmac(md5)"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = r3
            goto L_0x0041
        L_0x0022:
            java.lang.String r1 = "hmac(sha512)"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 4
            goto L_0x0041
        L_0x002c:
            java.lang.String r1 = "hmac(sha384)"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 3
            goto L_0x0041
        L_0x0036:
            java.lang.String r1 = "hmac(sha256)"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0040
            r0 = 2
            goto L_0x0041
        L_0x0040:
            r0 = -1
        L_0x0041:
            switch(r0) {
                case 0: goto L_0x0045;
                case 1: goto L_0x0045;
                case 2: goto L_0x0045;
                case 3: goto L_0x0045;
                case 4: goto L_0x0045;
                default: goto L_0x0044;
            }
        L_0x0044:
            return r3
        L_0x0045:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.IpSecAlgorithm.isAuthentication():boolean");
    }

    public boolean isEncryption() {
        return getName().equals(CRYPT_AES_CBC);
    }

    public boolean isAead() {
        return getName().equals(AUTH_CRYPT_AES_GCM);
    }

    private static boolean isUnsafeBuild() {
        return Build.IS_DEBUGGABLE && Build.IS_ENG;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{mName=");
        sb.append(this.mName);
        sb.append(", mKey=");
        sb.append(isUnsafeBuild() ? HexDump.toHexString(this.mKey) : "<hidden>");
        sb.append(", mTruncLenBits=");
        sb.append(this.mTruncLenBits);
        sb.append("}");
        return sb.toString();
    }

    @VisibleForTesting
    public static boolean equals(IpSecAlgorithm lhs, IpSecAlgorithm rhs) {
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }
        if (!lhs.mName.equals(rhs.mName) || !Arrays.equals(lhs.mKey, rhs.mKey) || lhs.mTruncLenBits != rhs.mTruncLenBits) {
            return false;
        }
        return true;
    }
}
