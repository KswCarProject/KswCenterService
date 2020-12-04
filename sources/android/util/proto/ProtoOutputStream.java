package android.util.proto;

import android.provider.Telephony;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public final class ProtoOutputStream extends ProtoStream {
    public static final String TAG = "ProtoOutputStream";
    private EncodedBuffer mBuffer;
    private boolean mCompacted;
    private int mCopyBegin;
    private int mDepth;
    private long mExpectedObjectToken;
    private int mNextObjectId;
    private OutputStream mStream;

    public ProtoOutputStream() {
        this(0);
    }

    public ProtoOutputStream(int chunkSize) {
        this.mNextObjectId = -1;
        this.mBuffer = new EncodedBuffer(chunkSize);
    }

    public ProtoOutputStream(OutputStream stream) {
        this();
        this.mStream = stream;
    }

    public ProtoOutputStream(FileDescriptor fd) {
        this((OutputStream) new FileOutputStream(fd));
    }

    public int getRawSize() {
        if (this.mCompacted) {
            return getBytes().length;
        }
        return this.mBuffer.getSize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0045, code lost:
        writeRepeatedSInt32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004b, code lost:
        writeRepeatedSFixed64Impl(r0, (long) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0051, code lost:
        writeRepeatedSFixed32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0057, code lost:
        writeRepeatedEnumImpl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005d, code lost:
        writeRepeatedUInt32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0065, code lost:
        if (r9 == 0.0d) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0067, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
        writeRepeatedBoolImpl(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006e, code lost:
        writeRepeatedFixed32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0074, code lost:
        writeRepeatedFixed64Impl(r0, (long) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007a, code lost:
        writeRepeatedInt32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0080, code lost:
        writeRepeatedUInt64Impl(r0, (long) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0086, code lost:
        writeRepeatedInt64Impl(r0, (long) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008b, code lost:
        writeRepeatedFloatImpl(r0, (float) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0090, code lost:
        writeRepeatedDoubleImpl(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003f, code lost:
        writeRepeatedSInt64Impl(r0, (long) r9);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(long r7, double r9) {
        /*
            r6 = this;
            r6.assertNotCompacted()
            int r0 = (int) r7
            r1 = 17587891077120(0xfff00000000, double:8.689572764003E-311)
            long r1 = r1 & r7
            r3 = 32
            long r1 = r1 >> r3
            int r1 = (int) r1
            r2 = 0
            r3 = 1
            r4 = 0
            switch(r1) {
                case 257: goto L_0x00da;
                case 258: goto L_0x00d5;
                case 259: goto L_0x00d0;
                case 260: goto L_0x00cb;
                case 261: goto L_0x00c6;
                case 262: goto L_0x00c1;
                case 263: goto L_0x00bc;
                case 264: goto L_0x00b2;
                default: goto L_0x0015;
            }
        L_0x0015:
            switch(r1) {
                case 269: goto L_0x00ad;
                case 270: goto L_0x00a8;
                case 271: goto L_0x00a3;
                case 272: goto L_0x009e;
                case 273: goto L_0x0099;
                case 274: goto L_0x0094;
                default: goto L_0x0018;
            }
        L_0x0018:
            switch(r1) {
                case 513: goto L_0x0090;
                case 514: goto L_0x008b;
                case 515: goto L_0x0086;
                case 516: goto L_0x0080;
                case 517: goto L_0x007a;
                case 518: goto L_0x0074;
                case 519: goto L_0x006e;
                case 520: goto L_0x0063;
                default: goto L_0x001b;
            }
        L_0x001b:
            switch(r1) {
                case 525: goto L_0x005d;
                case 526: goto L_0x0057;
                case 527: goto L_0x0051;
                case 528: goto L_0x004b;
                case 529: goto L_0x0045;
                case 530: goto L_0x003f;
                default: goto L_0x001e;
            }
        L_0x001e:
            switch(r1) {
                case 1281: goto L_0x0090;
                case 1282: goto L_0x008b;
                case 1283: goto L_0x0086;
                case 1284: goto L_0x0080;
                case 1285: goto L_0x007a;
                case 1286: goto L_0x0074;
                case 1287: goto L_0x006e;
                case 1288: goto L_0x0063;
                default: goto L_0x0021;
            }
        L_0x0021:
            switch(r1) {
                case 1293: goto L_0x005d;
                case 1294: goto L_0x0057;
                case 1295: goto L_0x0051;
                case 1296: goto L_0x004b;
                case 1297: goto L_0x0045;
                case 1298: goto L_0x003f;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Attempt to call write(long, double) with "
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r7)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x003f:
            long r1 = (long) r9
            r6.writeRepeatedSInt64Impl(r0, r1)
            goto L_0x00de
        L_0x0045:
            int r1 = (int) r9
            r6.writeRepeatedSInt32Impl(r0, r1)
            goto L_0x00de
        L_0x004b:
            long r1 = (long) r9
            r6.writeRepeatedSFixed64Impl(r0, r1)
            goto L_0x00de
        L_0x0051:
            int r1 = (int) r9
            r6.writeRepeatedSFixed32Impl(r0, r1)
            goto L_0x00de
        L_0x0057:
            int r1 = (int) r9
            r6.writeRepeatedEnumImpl(r0, r1)
            goto L_0x00de
        L_0x005d:
            int r1 = (int) r9
            r6.writeRepeatedUInt32Impl(r0, r1)
            goto L_0x00de
        L_0x0063:
            int r1 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0069
            r2 = r3
        L_0x0069:
            r6.writeRepeatedBoolImpl(r0, r2)
            goto L_0x00de
        L_0x006e:
            int r1 = (int) r9
            r6.writeRepeatedFixed32Impl(r0, r1)
            goto L_0x00de
        L_0x0074:
            long r1 = (long) r9
            r6.writeRepeatedFixed64Impl(r0, r1)
            goto L_0x00de
        L_0x007a:
            int r1 = (int) r9
            r6.writeRepeatedInt32Impl(r0, r1)
            goto L_0x00de
        L_0x0080:
            long r1 = (long) r9
            r6.writeRepeatedUInt64Impl(r0, r1)
            goto L_0x00de
        L_0x0086:
            long r1 = (long) r9
            r6.writeRepeatedInt64Impl(r0, r1)
            goto L_0x00de
        L_0x008b:
            float r1 = (float) r9
            r6.writeRepeatedFloatImpl(r0, r1)
            goto L_0x00de
        L_0x0090:
            r6.writeRepeatedDoubleImpl(r0, r9)
            goto L_0x00de
        L_0x0094:
            long r1 = (long) r9
            r6.writeSInt64Impl(r0, r1)
            goto L_0x00de
        L_0x0099:
            int r1 = (int) r9
            r6.writeSInt32Impl(r0, r1)
            goto L_0x00de
        L_0x009e:
            long r1 = (long) r9
            r6.writeSFixed64Impl(r0, r1)
            goto L_0x00de
        L_0x00a3:
            int r1 = (int) r9
            r6.writeSFixed32Impl(r0, r1)
            goto L_0x00de
        L_0x00a8:
            int r1 = (int) r9
            r6.writeEnumImpl(r0, r1)
            goto L_0x00de
        L_0x00ad:
            int r1 = (int) r9
            r6.writeUInt32Impl(r0, r1)
            goto L_0x00de
        L_0x00b2:
            int r1 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00b8
            r2 = r3
        L_0x00b8:
            r6.writeBoolImpl(r0, r2)
            goto L_0x00de
        L_0x00bc:
            int r1 = (int) r9
            r6.writeFixed32Impl(r0, r1)
            goto L_0x00de
        L_0x00c1:
            long r1 = (long) r9
            r6.writeFixed64Impl(r0, r1)
            goto L_0x00de
        L_0x00c6:
            int r1 = (int) r9
            r6.writeInt32Impl(r0, r1)
            goto L_0x00de
        L_0x00cb:
            long r1 = (long) r9
            r6.writeUInt64Impl(r0, r1)
            goto L_0x00de
        L_0x00d0:
            long r1 = (long) r9
            r6.writeInt64Impl(r0, r1)
            goto L_0x00de
        L_0x00d5:
            float r1 = (float) r9
            r6.writeFloatImpl(r0, r1)
            goto L_0x00de
        L_0x00da:
            r6.writeDoubleImpl(r0, r9)
        L_0x00de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, double):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0044, code lost:
        writeRepeatedSInt32Impl(r0, (int) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004a, code lost:
        writeRepeatedSFixed64Impl(r0, (long) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0050, code lost:
        writeRepeatedSFixed32Impl(r0, (int) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0056, code lost:
        writeRepeatedEnumImpl(r0, (int) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005c, code lost:
        writeRepeatedUInt32Impl(r0, (int) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0064, code lost:
        if (r8 == 0.0f) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0066, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        writeRepeatedBoolImpl(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006d, code lost:
        writeRepeatedFixed32Impl(r0, (int) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0073, code lost:
        writeRepeatedFixed64Impl(r0, (long) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0079, code lost:
        writeRepeatedInt32Impl(r0, (int) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007f, code lost:
        writeRepeatedUInt64Impl(r0, (long) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0085, code lost:
        writeRepeatedInt64Impl(r0, (long) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008a, code lost:
        writeRepeatedFloatImpl(r0, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008e, code lost:
        writeRepeatedDoubleImpl(r0, (double) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003e, code lost:
        writeRepeatedSInt64Impl(r0, (long) r8);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(long r6, float r8) {
        /*
            r5 = this;
            r5.assertNotCompacted()
            int r0 = (int) r6
            r1 = 17587891077120(0xfff00000000, double:8.689572764003E-311)
            long r1 = r1 & r6
            r3 = 32
            long r1 = r1 >> r3
            int r1 = (int) r1
            r2 = 0
            r3 = 1
            r4 = 0
            switch(r1) {
                case 257: goto L_0x00d8;
                case 258: goto L_0x00d4;
                case 259: goto L_0x00cf;
                case 260: goto L_0x00ca;
                case 261: goto L_0x00c5;
                case 262: goto L_0x00c0;
                case 263: goto L_0x00bb;
                case 264: goto L_0x00b1;
                default: goto L_0x0014;
            }
        L_0x0014:
            switch(r1) {
                case 269: goto L_0x00ac;
                case 270: goto L_0x00a7;
                case 271: goto L_0x00a2;
                case 272: goto L_0x009d;
                case 273: goto L_0x0098;
                case 274: goto L_0x0093;
                default: goto L_0x0017;
            }
        L_0x0017:
            switch(r1) {
                case 513: goto L_0x008e;
                case 514: goto L_0x008a;
                case 515: goto L_0x0085;
                case 516: goto L_0x007f;
                case 517: goto L_0x0079;
                case 518: goto L_0x0073;
                case 519: goto L_0x006d;
                case 520: goto L_0x0062;
                default: goto L_0x001a;
            }
        L_0x001a:
            switch(r1) {
                case 525: goto L_0x005c;
                case 526: goto L_0x0056;
                case 527: goto L_0x0050;
                case 528: goto L_0x004a;
                case 529: goto L_0x0044;
                case 530: goto L_0x003e;
                default: goto L_0x001d;
            }
        L_0x001d:
            switch(r1) {
                case 1281: goto L_0x008e;
                case 1282: goto L_0x008a;
                case 1283: goto L_0x0085;
                case 1284: goto L_0x007f;
                case 1285: goto L_0x0079;
                case 1286: goto L_0x0073;
                case 1287: goto L_0x006d;
                case 1288: goto L_0x0062;
                default: goto L_0x0020;
            }
        L_0x0020:
            switch(r1) {
                case 1293: goto L_0x005c;
                case 1294: goto L_0x0056;
                case 1295: goto L_0x0050;
                case 1296: goto L_0x004a;
                case 1297: goto L_0x0044;
                case 1298: goto L_0x003e;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Attempt to call write(long, float) with "
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r6)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x003e:
            long r1 = (long) r8
            r5.writeRepeatedSInt64Impl(r0, r1)
            goto L_0x00dd
        L_0x0044:
            int r1 = (int) r8
            r5.writeRepeatedSInt32Impl(r0, r1)
            goto L_0x00dd
        L_0x004a:
            long r1 = (long) r8
            r5.writeRepeatedSFixed64Impl(r0, r1)
            goto L_0x00dd
        L_0x0050:
            int r1 = (int) r8
            r5.writeRepeatedSFixed32Impl(r0, r1)
            goto L_0x00dd
        L_0x0056:
            int r1 = (int) r8
            r5.writeRepeatedEnumImpl(r0, r1)
            goto L_0x00dd
        L_0x005c:
            int r1 = (int) r8
            r5.writeRepeatedUInt32Impl(r0, r1)
            goto L_0x00dd
        L_0x0062:
            int r1 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0068
            r2 = r3
        L_0x0068:
            r5.writeRepeatedBoolImpl(r0, r2)
            goto L_0x00dd
        L_0x006d:
            int r1 = (int) r8
            r5.writeRepeatedFixed32Impl(r0, r1)
            goto L_0x00dd
        L_0x0073:
            long r1 = (long) r8
            r5.writeRepeatedFixed64Impl(r0, r1)
            goto L_0x00dd
        L_0x0079:
            int r1 = (int) r8
            r5.writeRepeatedInt32Impl(r0, r1)
            goto L_0x00dd
        L_0x007f:
            long r1 = (long) r8
            r5.writeRepeatedUInt64Impl(r0, r1)
            goto L_0x00dd
        L_0x0085:
            long r1 = (long) r8
            r5.writeRepeatedInt64Impl(r0, r1)
            goto L_0x00dd
        L_0x008a:
            r5.writeRepeatedFloatImpl(r0, r8)
            goto L_0x00dd
        L_0x008e:
            double r1 = (double) r8
            r5.writeRepeatedDoubleImpl(r0, r1)
            goto L_0x00dd
        L_0x0093:
            long r1 = (long) r8
            r5.writeSInt64Impl(r0, r1)
            goto L_0x00dd
        L_0x0098:
            int r1 = (int) r8
            r5.writeSInt32Impl(r0, r1)
            goto L_0x00dd
        L_0x009d:
            long r1 = (long) r8
            r5.writeSFixed64Impl(r0, r1)
            goto L_0x00dd
        L_0x00a2:
            int r1 = (int) r8
            r5.writeSFixed32Impl(r0, r1)
            goto L_0x00dd
        L_0x00a7:
            int r1 = (int) r8
            r5.writeEnumImpl(r0, r1)
            goto L_0x00dd
        L_0x00ac:
            int r1 = (int) r8
            r5.writeUInt32Impl(r0, r1)
            goto L_0x00dd
        L_0x00b1:
            int r1 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00b7
            r2 = r3
        L_0x00b7:
            r5.writeBoolImpl(r0, r2)
            goto L_0x00dd
        L_0x00bb:
            int r1 = (int) r8
            r5.writeFixed32Impl(r0, r1)
            goto L_0x00dd
        L_0x00c0:
            long r1 = (long) r8
            r5.writeFixed64Impl(r0, r1)
            goto L_0x00dd
        L_0x00c5:
            int r1 = (int) r8
            r5.writeInt32Impl(r0, r1)
            goto L_0x00dd
        L_0x00ca:
            long r1 = (long) r8
            r5.writeUInt64Impl(r0, r1)
            goto L_0x00dd
        L_0x00cf:
            long r1 = (long) r8
            r5.writeInt64Impl(r0, r1)
            goto L_0x00dd
        L_0x00d4:
            r5.writeFloatImpl(r0, r8)
            goto L_0x00dd
        L_0x00d8:
            double r1 = (double) r8
            r5.writeDoubleImpl(r0, r1)
        L_0x00dd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, float):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0043, code lost:
        writeRepeatedSInt32Impl(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0048, code lost:
        writeRepeatedSFixed64Impl(r0, (long) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004e, code lost:
        writeRepeatedSFixed32Impl(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0053, code lost:
        writeRepeatedEnumImpl(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0058, code lost:
        writeRepeatedUInt32Impl(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005d, code lost:
        if (r7 == 0) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005f, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0061, code lost:
        writeRepeatedBoolImpl(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0066, code lost:
        writeRepeatedFixed32Impl(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
        writeRepeatedFixed64Impl(r0, (long) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0071, code lost:
        writeRepeatedInt32Impl(r0, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0076, code lost:
        writeRepeatedUInt64Impl(r0, (long) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007b, code lost:
        writeRepeatedInt64Impl(r0, (long) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0080, code lost:
        writeRepeatedFloatImpl(r0, (float) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0085, code lost:
        writeRepeatedDoubleImpl(r0, (double) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003d, code lost:
        writeRepeatedSInt64Impl(r0, (long) r7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(long r5, int r7) {
        /*
            r4 = this;
            r4.assertNotCompacted()
            int r0 = (int) r5
            r1 = 17587891077120(0xfff00000000, double:8.689572764003E-311)
            long r1 = r1 & r5
            r3 = 32
            long r1 = r1 >> r3
            int r1 = (int) r1
            r2 = 0
            r3 = 1
            switch(r1) {
                case 257: goto L_0x00c8;
                case 258: goto L_0x00c3;
                case 259: goto L_0x00be;
                case 260: goto L_0x00b9;
                case 261: goto L_0x00b5;
                case 262: goto L_0x00b0;
                case 263: goto L_0x00ac;
                case 264: goto L_0x00a4;
                default: goto L_0x0013;
            }
        L_0x0013:
            switch(r1) {
                case 269: goto L_0x00a0;
                case 270: goto L_0x009c;
                case 271: goto L_0x0098;
                case 272: goto L_0x0093;
                case 273: goto L_0x008f;
                case 274: goto L_0x008a;
                default: goto L_0x0016;
            }
        L_0x0016:
            switch(r1) {
                case 513: goto L_0x0085;
                case 514: goto L_0x0080;
                case 515: goto L_0x007b;
                case 516: goto L_0x0076;
                case 517: goto L_0x0071;
                case 518: goto L_0x006b;
                case 519: goto L_0x0066;
                case 520: goto L_0x005d;
                default: goto L_0x0019;
            }
        L_0x0019:
            switch(r1) {
                case 525: goto L_0x0058;
                case 526: goto L_0x0053;
                case 527: goto L_0x004e;
                case 528: goto L_0x0048;
                case 529: goto L_0x0043;
                case 530: goto L_0x003d;
                default: goto L_0x001c;
            }
        L_0x001c:
            switch(r1) {
                case 1281: goto L_0x0085;
                case 1282: goto L_0x0080;
                case 1283: goto L_0x007b;
                case 1284: goto L_0x0076;
                case 1285: goto L_0x0071;
                case 1286: goto L_0x006b;
                case 1287: goto L_0x0066;
                case 1288: goto L_0x005d;
                default: goto L_0x001f;
            }
        L_0x001f:
            switch(r1) {
                case 1293: goto L_0x0058;
                case 1294: goto L_0x0053;
                case 1295: goto L_0x004e;
                case 1296: goto L_0x0048;
                case 1297: goto L_0x0043;
                case 1298: goto L_0x003d;
                default: goto L_0x0022;
            }
        L_0x0022:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Attempt to call write(long, int) with "
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r5)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x003d:
            long r1 = (long) r7
            r4.writeRepeatedSInt64Impl(r0, r1)
            goto L_0x00cd
        L_0x0043:
            r4.writeRepeatedSInt32Impl(r0, r7)
            goto L_0x00cd
        L_0x0048:
            long r1 = (long) r7
            r4.writeRepeatedSFixed64Impl(r0, r1)
            goto L_0x00cd
        L_0x004e:
            r4.writeRepeatedSFixed32Impl(r0, r7)
            goto L_0x00cd
        L_0x0053:
            r4.writeRepeatedEnumImpl(r0, r7)
            goto L_0x00cd
        L_0x0058:
            r4.writeRepeatedUInt32Impl(r0, r7)
            goto L_0x00cd
        L_0x005d:
            if (r7 == 0) goto L_0x0061
            r2 = r3
        L_0x0061:
            r4.writeRepeatedBoolImpl(r0, r2)
            goto L_0x00cd
        L_0x0066:
            r4.writeRepeatedFixed32Impl(r0, r7)
            goto L_0x00cd
        L_0x006b:
            long r1 = (long) r7
            r4.writeRepeatedFixed64Impl(r0, r1)
            goto L_0x00cd
        L_0x0071:
            r4.writeRepeatedInt32Impl(r0, r7)
            goto L_0x00cd
        L_0x0076:
            long r1 = (long) r7
            r4.writeRepeatedUInt64Impl(r0, r1)
            goto L_0x00cd
        L_0x007b:
            long r1 = (long) r7
            r4.writeRepeatedInt64Impl(r0, r1)
            goto L_0x00cd
        L_0x0080:
            float r1 = (float) r7
            r4.writeRepeatedFloatImpl(r0, r1)
            goto L_0x00cd
        L_0x0085:
            double r1 = (double) r7
            r4.writeRepeatedDoubleImpl(r0, r1)
            goto L_0x00cd
        L_0x008a:
            long r1 = (long) r7
            r4.writeSInt64Impl(r0, r1)
            goto L_0x00cd
        L_0x008f:
            r4.writeSInt32Impl(r0, r7)
            goto L_0x00cd
        L_0x0093:
            long r1 = (long) r7
            r4.writeSFixed64Impl(r0, r1)
            goto L_0x00cd
        L_0x0098:
            r4.writeSFixed32Impl(r0, r7)
            goto L_0x00cd
        L_0x009c:
            r4.writeEnumImpl(r0, r7)
            goto L_0x00cd
        L_0x00a0:
            r4.writeUInt32Impl(r0, r7)
            goto L_0x00cd
        L_0x00a4:
            if (r7 == 0) goto L_0x00a8
            r2 = r3
        L_0x00a8:
            r4.writeBoolImpl(r0, r2)
            goto L_0x00cd
        L_0x00ac:
            r4.writeFixed32Impl(r0, r7)
            goto L_0x00cd
        L_0x00b0:
            long r1 = (long) r7
            r4.writeFixed64Impl(r0, r1)
            goto L_0x00cd
        L_0x00b5:
            r4.writeInt32Impl(r0, r7)
            goto L_0x00cd
        L_0x00b9:
            long r1 = (long) r7
            r4.writeUInt64Impl(r0, r1)
            goto L_0x00cd
        L_0x00be:
            long r1 = (long) r7
            r4.writeInt64Impl(r0, r1)
            goto L_0x00cd
        L_0x00c3:
            float r1 = (float) r7
            r4.writeFloatImpl(r0, r1)
            goto L_0x00cd
        L_0x00c8:
            double r1 = (double) r7
            r4.writeDoubleImpl(r0, r1)
        L_0x00cd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0044, code lost:
        writeRepeatedSInt32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004a, code lost:
        writeRepeatedSFixed64Impl(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004f, code lost:
        writeRepeatedSFixed32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0055, code lost:
        writeRepeatedEnumImpl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x005b, code lost:
        writeRepeatedUInt32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0063, code lost:
        if (r9 == 0) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0065, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0067, code lost:
        writeRepeatedBoolImpl(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006c, code lost:
        writeRepeatedFixed32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0072, code lost:
        writeRepeatedFixed64Impl(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0077, code lost:
        writeRepeatedInt32Impl(r0, (int) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007d, code lost:
        writeRepeatedUInt64Impl(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0081, code lost:
        writeRepeatedInt64Impl(r0, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0085, code lost:
        writeRepeatedFloatImpl(r0, (float) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008a, code lost:
        writeRepeatedDoubleImpl(r0, (double) r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003f, code lost:
        writeRepeatedSInt64Impl(r0, r9);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(long r7, long r9) {
        /*
            r6 = this;
            r6.assertNotCompacted()
            int r0 = (int) r7
            r1 = 17587891077120(0xfff00000000, double:8.689572764003E-311)
            long r1 = r1 & r7
            r3 = 32
            long r1 = r1 >> r3
            int r1 = (int) r1
            r2 = 0
            r3 = 1
            r4 = 0
            switch(r1) {
                case 257: goto L_0x00d0;
                case 258: goto L_0x00cb;
                case 259: goto L_0x00c7;
                case 260: goto L_0x00c3;
                case 261: goto L_0x00be;
                case 262: goto L_0x00ba;
                case 263: goto L_0x00b5;
                case 264: goto L_0x00ab;
                default: goto L_0x0015;
            }
        L_0x0015:
            switch(r1) {
                case 269: goto L_0x00a6;
                case 270: goto L_0x00a1;
                case 271: goto L_0x009c;
                case 272: goto L_0x0098;
                case 273: goto L_0x0093;
                case 274: goto L_0x008f;
                default: goto L_0x0018;
            }
        L_0x0018:
            switch(r1) {
                case 513: goto L_0x008a;
                case 514: goto L_0x0085;
                case 515: goto L_0x0081;
                case 516: goto L_0x007d;
                case 517: goto L_0x0077;
                case 518: goto L_0x0072;
                case 519: goto L_0x006c;
                case 520: goto L_0x0061;
                default: goto L_0x001b;
            }
        L_0x001b:
            switch(r1) {
                case 525: goto L_0x005b;
                case 526: goto L_0x0055;
                case 527: goto L_0x004f;
                case 528: goto L_0x004a;
                case 529: goto L_0x0044;
                case 530: goto L_0x003f;
                default: goto L_0x001e;
            }
        L_0x001e:
            switch(r1) {
                case 1281: goto L_0x008a;
                case 1282: goto L_0x0085;
                case 1283: goto L_0x0081;
                case 1284: goto L_0x007d;
                case 1285: goto L_0x0077;
                case 1286: goto L_0x0072;
                case 1287: goto L_0x006c;
                case 1288: goto L_0x0061;
                default: goto L_0x0021;
            }
        L_0x0021:
            switch(r1) {
                case 1293: goto L_0x005b;
                case 1294: goto L_0x0055;
                case 1295: goto L_0x004f;
                case 1296: goto L_0x004a;
                case 1297: goto L_0x0044;
                case 1298: goto L_0x003f;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Attempt to call write(long, long) with "
            r2.append(r3)
            java.lang.String r3 = getFieldIdString(r7)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x003f:
            r6.writeRepeatedSInt64Impl(r0, r9)
            goto L_0x00d5
        L_0x0044:
            int r1 = (int) r9
            r6.writeRepeatedSInt32Impl(r0, r1)
            goto L_0x00d5
        L_0x004a:
            r6.writeRepeatedSFixed64Impl(r0, r9)
            goto L_0x00d5
        L_0x004f:
            int r1 = (int) r9
            r6.writeRepeatedSFixed32Impl(r0, r1)
            goto L_0x00d5
        L_0x0055:
            int r1 = (int) r9
            r6.writeRepeatedEnumImpl(r0, r1)
            goto L_0x00d5
        L_0x005b:
            int r1 = (int) r9
            r6.writeRepeatedUInt32Impl(r0, r1)
            goto L_0x00d5
        L_0x0061:
            int r1 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0067
            r2 = r3
        L_0x0067:
            r6.writeRepeatedBoolImpl(r0, r2)
            goto L_0x00d5
        L_0x006c:
            int r1 = (int) r9
            r6.writeRepeatedFixed32Impl(r0, r1)
            goto L_0x00d5
        L_0x0072:
            r6.writeRepeatedFixed64Impl(r0, r9)
            goto L_0x00d5
        L_0x0077:
            int r1 = (int) r9
            r6.writeRepeatedInt32Impl(r0, r1)
            goto L_0x00d5
        L_0x007d:
            r6.writeRepeatedUInt64Impl(r0, r9)
            goto L_0x00d5
        L_0x0081:
            r6.writeRepeatedInt64Impl(r0, r9)
            goto L_0x00d5
        L_0x0085:
            float r1 = (float) r9
            r6.writeRepeatedFloatImpl(r0, r1)
            goto L_0x00d5
        L_0x008a:
            double r1 = (double) r9
            r6.writeRepeatedDoubleImpl(r0, r1)
            goto L_0x00d5
        L_0x008f:
            r6.writeSInt64Impl(r0, r9)
            goto L_0x00d5
        L_0x0093:
            int r1 = (int) r9
            r6.writeSInt32Impl(r0, r1)
            goto L_0x00d5
        L_0x0098:
            r6.writeSFixed64Impl(r0, r9)
            goto L_0x00d5
        L_0x009c:
            int r1 = (int) r9
            r6.writeSFixed32Impl(r0, r1)
            goto L_0x00d5
        L_0x00a1:
            int r1 = (int) r9
            r6.writeEnumImpl(r0, r1)
            goto L_0x00d5
        L_0x00a6:
            int r1 = (int) r9
            r6.writeUInt32Impl(r0, r1)
            goto L_0x00d5
        L_0x00ab:
            int r1 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x00b1
            r2 = r3
        L_0x00b1:
            r6.writeBoolImpl(r0, r2)
            goto L_0x00d5
        L_0x00b5:
            int r1 = (int) r9
            r6.writeFixed32Impl(r0, r1)
            goto L_0x00d5
        L_0x00ba:
            r6.writeFixed64Impl(r0, r9)
            goto L_0x00d5
        L_0x00be:
            int r1 = (int) r9
            r6.writeInt32Impl(r0, r1)
            goto L_0x00d5
        L_0x00c3:
            r6.writeUInt64Impl(r0, r9)
            goto L_0x00d5
        L_0x00c7:
            r6.writeInt64Impl(r0, r9)
            goto L_0x00d5
        L_0x00cb:
            float r1 = (float) r9
            r6.writeFloatImpl(r0, r1)
            goto L_0x00d5
        L_0x00d0:
            double r1 = (double) r9
            r6.writeDoubleImpl(r0, r1)
        L_0x00d5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.write(long, long):void");
    }

    public void write(long fieldId, boolean val) {
        assertNotCompacted();
        int id = (int) fieldId;
        int i = (int) ((17587891077120L & fieldId) >> 32);
        if (i == 264) {
            writeBoolImpl(id, val);
        } else if (i == 520 || i == 1288) {
            writeRepeatedBoolImpl(id, val);
        } else {
            throw new IllegalArgumentException("Attempt to call write(long, boolean) with " + getFieldIdString(fieldId));
        }
    }

    public void write(long fieldId, String val) {
        assertNotCompacted();
        int id = (int) fieldId;
        int i = (int) ((17587891077120L & fieldId) >> 32);
        if (i == 265) {
            writeStringImpl(id, val);
        } else if (i == 521 || i == 1289) {
            writeRepeatedStringImpl(id, val);
        } else {
            throw new IllegalArgumentException("Attempt to call write(long, String) with " + getFieldIdString(fieldId));
        }
    }

    public void write(long fieldId, byte[] val) {
        assertNotCompacted();
        int id = (int) fieldId;
        switch ((int) ((17587891077120L & fieldId) >> 32)) {
            case 267:
                writeObjectImpl(id, val);
                return;
            case 268:
                writeBytesImpl(id, val);
                return;
            case 523:
            case 1291:
                writeRepeatedObjectImpl(id, val);
                return;
            case 524:
            case 1292:
                writeRepeatedBytesImpl(id, val);
                return;
            default:
                throw new IllegalArgumentException("Attempt to call write(long, byte[]) with " + getFieldIdString(fieldId));
        }
    }

    public long start(long fieldId) {
        assertNotCompacted();
        int id = (int) fieldId;
        if ((ProtoStream.FIELD_TYPE_MASK & fieldId) == ProtoStream.FIELD_TYPE_MESSAGE) {
            long count = ProtoStream.FIELD_COUNT_MASK & fieldId;
            if (count == 1099511627776L) {
                return startObjectImpl(id, false);
            }
            if (count == ProtoStream.FIELD_COUNT_REPEATED || count == ProtoStream.FIELD_COUNT_PACKED) {
                return startObjectImpl(id, true);
            }
        }
        throw new IllegalArgumentException("Attempt to call start(long) with " + getFieldIdString(fieldId));
    }

    public void end(long token) {
        endObjectImpl(token, getRepeatedFromToken(token));
    }

    @Deprecated
    public void writeDouble(long fieldId, double val) {
        assertNotCompacted();
        writeDoubleImpl(checkFieldId(fieldId, 1103806595072L), val);
    }

    private void writeDoubleImpl(int id, double val) {
        if (val != 0.0d) {
            writeTag(id, 1);
            this.mBuffer.writeRawFixed64(Double.doubleToLongBits(val));
        }
    }

    @Deprecated
    public void writeRepeatedDouble(long fieldId, double val) {
        assertNotCompacted();
        writeRepeatedDoubleImpl(checkFieldId(fieldId, 2203318222848L), val);
    }

    private void writeRepeatedDoubleImpl(int id, double val) {
        writeTag(id, 1);
        this.mBuffer.writeRawFixed64(Double.doubleToLongBits(val));
    }

    @Deprecated
    public void writePackedDouble(long fieldId, double[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5501853106176L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N * 8);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawFixed64(Double.doubleToLongBits(val[i]));
            }
        }
    }

    @Deprecated
    public void writeFloat(long fieldId, float val) {
        assertNotCompacted();
        writeFloatImpl(checkFieldId(fieldId, 1108101562368L), val);
    }

    private void writeFloatImpl(int id, float val) {
        if (val != 0.0f) {
            writeTag(id, 5);
            this.mBuffer.writeRawFixed32(Float.floatToIntBits(val));
        }
    }

    @Deprecated
    public void writeRepeatedFloat(long fieldId, float val) {
        assertNotCompacted();
        writeRepeatedFloatImpl(checkFieldId(fieldId, 2207613190144L), val);
    }

    private void writeRepeatedFloatImpl(int id, float val) {
        writeTag(id, 5);
        this.mBuffer.writeRawFixed32(Float.floatToIntBits(val));
    }

    @Deprecated
    public void writePackedFloat(long fieldId, float[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5506148073472L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N * 4);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawFixed32(Float.floatToIntBits(val[i]));
            }
        }
    }

    private void writeUnsignedVarintFromSignedInt(int val) {
        if (val >= 0) {
            this.mBuffer.writeRawVarint32(val);
        } else {
            this.mBuffer.writeRawVarint64((long) val);
        }
    }

    @Deprecated
    public void writeInt32(long fieldId, int val) {
        assertNotCompacted();
        writeInt32Impl(checkFieldId(fieldId, 1120986464256L), val);
    }

    private void writeInt32Impl(int id, int val) {
        if (val != 0) {
            writeTag(id, 0);
            writeUnsignedVarintFromSignedInt(val);
        }
    }

    @Deprecated
    public void writeRepeatedInt32(long fieldId, int val) {
        assertNotCompacted();
        writeRepeatedInt32Impl(checkFieldId(fieldId, 2220498092032L), val);
    }

    private void writeRepeatedInt32Impl(int id, int val) {
        writeTag(id, 0);
        writeUnsignedVarintFromSignedInt(val);
    }

    @Deprecated
    public void writePackedInt32(long fieldId, int[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5519032975360L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                int v = val[i];
                size += v >= 0 ? EncodedBuffer.getRawVarint32Size(v) : 10;
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                writeUnsignedVarintFromSignedInt(val[i2]);
            }
        }
    }

    @Deprecated
    public void writeInt64(long fieldId, long val) {
        assertNotCompacted();
        writeInt64Impl(checkFieldId(fieldId, 1112396529664L), val);
    }

    private void writeInt64Impl(int id, long val) {
        if (val != 0) {
            writeTag(id, 0);
            this.mBuffer.writeRawVarint64(val);
        }
    }

    @Deprecated
    public void writeRepeatedInt64(long fieldId, long val) {
        assertNotCompacted();
        writeRepeatedInt64Impl(checkFieldId(fieldId, 2211908157440L), val);
    }

    private void writeRepeatedInt64Impl(int id, long val) {
        writeTag(id, 0);
        this.mBuffer.writeRawVarint64(val);
    }

    @Deprecated
    public void writePackedInt64(long fieldId, long[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5510443040768L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                size += EncodedBuffer.getRawVarint64Size(val[i]);
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                this.mBuffer.writeRawVarint64(val[i2]);
            }
        }
    }

    @Deprecated
    public void writeUInt32(long fieldId, int val) {
        assertNotCompacted();
        writeUInt32Impl(checkFieldId(fieldId, 1155346202624L), val);
    }

    private void writeUInt32Impl(int id, int val) {
        if (val != 0) {
            writeTag(id, 0);
            this.mBuffer.writeRawVarint32(val);
        }
    }

    @Deprecated
    public void writeRepeatedUInt32(long fieldId, int val) {
        assertNotCompacted();
        writeRepeatedUInt32Impl(checkFieldId(fieldId, 2254857830400L), val);
    }

    private void writeRepeatedUInt32Impl(int id, int val) {
        writeTag(id, 0);
        this.mBuffer.writeRawVarint32(val);
    }

    @Deprecated
    public void writePackedUInt32(long fieldId, int[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5553392713728L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                size += EncodedBuffer.getRawVarint32Size(val[i]);
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                this.mBuffer.writeRawVarint32(val[i2]);
            }
        }
    }

    @Deprecated
    public void writeUInt64(long fieldId, long val) {
        assertNotCompacted();
        writeUInt64Impl(checkFieldId(fieldId, 1116691496960L), val);
    }

    private void writeUInt64Impl(int id, long val) {
        if (val != 0) {
            writeTag(id, 0);
            this.mBuffer.writeRawVarint64(val);
        }
    }

    @Deprecated
    public void writeRepeatedUInt64(long fieldId, long val) {
        assertNotCompacted();
        writeRepeatedUInt64Impl(checkFieldId(fieldId, 2216203124736L), val);
    }

    private void writeRepeatedUInt64Impl(int id, long val) {
        writeTag(id, 0);
        this.mBuffer.writeRawVarint64(val);
    }

    @Deprecated
    public void writePackedUInt64(long fieldId, long[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5514738008064L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                size += EncodedBuffer.getRawVarint64Size(val[i]);
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                this.mBuffer.writeRawVarint64(val[i2]);
            }
        }
    }

    @Deprecated
    public void writeSInt32(long fieldId, int val) {
        assertNotCompacted();
        writeSInt32Impl(checkFieldId(fieldId, 1172526071808L), val);
    }

    private void writeSInt32Impl(int id, int val) {
        if (val != 0) {
            writeTag(id, 0);
            this.mBuffer.writeRawZigZag32(val);
        }
    }

    @Deprecated
    public void writeRepeatedSInt32(long fieldId, int val) {
        assertNotCompacted();
        writeRepeatedSInt32Impl(checkFieldId(fieldId, 2272037699584L), val);
    }

    private void writeRepeatedSInt32Impl(int id, int val) {
        writeTag(id, 0);
        this.mBuffer.writeRawZigZag32(val);
    }

    @Deprecated
    public void writePackedSInt32(long fieldId, int[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5570572582912L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                size += EncodedBuffer.getRawZigZag32Size(val[i]);
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                this.mBuffer.writeRawZigZag32(val[i2]);
            }
        }
    }

    @Deprecated
    public void writeSInt64(long fieldId, long val) {
        assertNotCompacted();
        writeSInt64Impl(checkFieldId(fieldId, 1176821039104L), val);
    }

    private void writeSInt64Impl(int id, long val) {
        if (val != 0) {
            writeTag(id, 0);
            this.mBuffer.writeRawZigZag64(val);
        }
    }

    @Deprecated
    public void writeRepeatedSInt64(long fieldId, long val) {
        assertNotCompacted();
        writeRepeatedSInt64Impl(checkFieldId(fieldId, 2276332666880L), val);
    }

    private void writeRepeatedSInt64Impl(int id, long val) {
        writeTag(id, 0);
        this.mBuffer.writeRawZigZag64(val);
    }

    @Deprecated
    public void writePackedSInt64(long fieldId, long[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5574867550208L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                size += EncodedBuffer.getRawZigZag64Size(val[i]);
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                this.mBuffer.writeRawZigZag64(val[i2]);
            }
        }
    }

    @Deprecated
    public void writeFixed32(long fieldId, int val) {
        assertNotCompacted();
        writeFixed32Impl(checkFieldId(fieldId, 1129576398848L), val);
    }

    private void writeFixed32Impl(int id, int val) {
        if (val != 0) {
            writeTag(id, 5);
            this.mBuffer.writeRawFixed32(val);
        }
    }

    @Deprecated
    public void writeRepeatedFixed32(long fieldId, int val) {
        assertNotCompacted();
        writeRepeatedFixed32Impl(checkFieldId(fieldId, 2229088026624L), val);
    }

    private void writeRepeatedFixed32Impl(int id, int val) {
        writeTag(id, 5);
        this.mBuffer.writeRawFixed32(val);
    }

    @Deprecated
    public void writePackedFixed32(long fieldId, int[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5527622909952L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N * 4);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawFixed32(val[i]);
            }
        }
    }

    @Deprecated
    public void writeFixed64(long fieldId, long val) {
        assertNotCompacted();
        writeFixed64Impl(checkFieldId(fieldId, 1125281431552L), val);
    }

    private void writeFixed64Impl(int id, long val) {
        if (val != 0) {
            writeTag(id, 1);
            this.mBuffer.writeRawFixed64(val);
        }
    }

    @Deprecated
    public void writeRepeatedFixed64(long fieldId, long val) {
        assertNotCompacted();
        writeRepeatedFixed64Impl(checkFieldId(fieldId, 2224793059328L), val);
    }

    private void writeRepeatedFixed64Impl(int id, long val) {
        writeTag(id, 1);
        this.mBuffer.writeRawFixed64(val);
    }

    @Deprecated
    public void writePackedFixed64(long fieldId, long[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5523327942656L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N * 8);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawFixed64(val[i]);
            }
        }
    }

    @Deprecated
    public void writeSFixed32(long fieldId, int val) {
        assertNotCompacted();
        writeSFixed32Impl(checkFieldId(fieldId, 1163936137216L), val);
    }

    private void writeSFixed32Impl(int id, int val) {
        if (val != 0) {
            writeTag(id, 5);
            this.mBuffer.writeRawFixed32(val);
        }
    }

    @Deprecated
    public void writeRepeatedSFixed32(long fieldId, int val) {
        assertNotCompacted();
        writeRepeatedSFixed32Impl(checkFieldId(fieldId, 2263447764992L), val);
    }

    private void writeRepeatedSFixed32Impl(int id, int val) {
        writeTag(id, 5);
        this.mBuffer.writeRawFixed32(val);
    }

    @Deprecated
    public void writePackedSFixed32(long fieldId, int[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5561982648320L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N * 4);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawFixed32(val[i]);
            }
        }
    }

    @Deprecated
    public void writeSFixed64(long fieldId, long val) {
        assertNotCompacted();
        writeSFixed64Impl(checkFieldId(fieldId, 1168231104512L), val);
    }

    private void writeSFixed64Impl(int id, long val) {
        if (val != 0) {
            writeTag(id, 1);
            this.mBuffer.writeRawFixed64(val);
        }
    }

    @Deprecated
    public void writeRepeatedSFixed64(long fieldId, long val) {
        assertNotCompacted();
        writeRepeatedSFixed64Impl(checkFieldId(fieldId, 2267742732288L), val);
    }

    private void writeRepeatedSFixed64Impl(int id, long val) {
        writeTag(id, 1);
        this.mBuffer.writeRawFixed64(val);
    }

    @Deprecated
    public void writePackedSFixed64(long fieldId, long[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5566277615616L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N * 8);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawFixed64(val[i]);
            }
        }
    }

    @Deprecated
    public void writeBool(long fieldId, boolean val) {
        assertNotCompacted();
        writeBoolImpl(checkFieldId(fieldId, 1133871366144L), val);
    }

    private void writeBoolImpl(int id, boolean val) {
        if (val) {
            writeTag(id, 0);
            this.mBuffer.writeRawByte((byte) 1);
        }
    }

    @Deprecated
    public void writeRepeatedBool(long fieldId, boolean val) {
        assertNotCompacted();
        writeRepeatedBoolImpl(checkFieldId(fieldId, 2233382993920L), val);
    }

    private void writeRepeatedBoolImpl(int id, boolean val) {
        writeTag(id, 0);
        this.mBuffer.writeRawByte(val ? (byte) 1 : 0);
    }

    @Deprecated
    public void writePackedBool(long fieldId, boolean[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5531917877248L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            writeKnownLengthHeader(id, N);
            for (int i = 0; i < N; i++) {
                this.mBuffer.writeRawByte(val[i] ? (byte) 1 : 0);
            }
        }
    }

    @Deprecated
    public void writeString(long fieldId, String val) {
        assertNotCompacted();
        writeStringImpl(checkFieldId(fieldId, 1138166333440L), val);
    }

    private void writeStringImpl(int id, String val) {
        if (val != null && val.length() > 0) {
            writeUtf8String(id, val);
        }
    }

    @Deprecated
    public void writeRepeatedString(long fieldId, String val) {
        assertNotCompacted();
        writeRepeatedStringImpl(checkFieldId(fieldId, 2237677961216L), val);
    }

    private void writeRepeatedStringImpl(int id, String val) {
        if (val == null || val.length() == 0) {
            writeKnownLengthHeader(id, 0);
        } else {
            writeUtf8String(id, val);
        }
    }

    private void writeUtf8String(int id, String val) {
        try {
            byte[] buf = val.getBytes("UTF-8");
            writeKnownLengthHeader(id, buf.length);
            this.mBuffer.writeRawBuffer(buf);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("not possible");
        }
    }

    @Deprecated
    public void writeBytes(long fieldId, byte[] val) {
        assertNotCompacted();
        writeBytesImpl(checkFieldId(fieldId, 1151051235328L), val);
    }

    private void writeBytesImpl(int id, byte[] val) {
        if (val != null && val.length > 0) {
            writeKnownLengthHeader(id, val.length);
            this.mBuffer.writeRawBuffer(val);
        }
    }

    @Deprecated
    public void writeRepeatedBytes(long fieldId, byte[] val) {
        assertNotCompacted();
        writeRepeatedBytesImpl(checkFieldId(fieldId, 2250562863104L), val);
    }

    private void writeRepeatedBytesImpl(int id, byte[] val) {
        writeKnownLengthHeader(id, val == null ? 0 : val.length);
        this.mBuffer.writeRawBuffer(val);
    }

    @Deprecated
    public void writeEnum(long fieldId, int val) {
        assertNotCompacted();
        writeEnumImpl(checkFieldId(fieldId, 1159641169920L), val);
    }

    private void writeEnumImpl(int id, int val) {
        if (val != 0) {
            writeTag(id, 0);
            writeUnsignedVarintFromSignedInt(val);
        }
    }

    @Deprecated
    public void writeRepeatedEnum(long fieldId, int val) {
        assertNotCompacted();
        writeRepeatedEnumImpl(checkFieldId(fieldId, 2259152797696L), val);
    }

    private void writeRepeatedEnumImpl(int id, int val) {
        writeTag(id, 0);
        writeUnsignedVarintFromSignedInt(val);
    }

    @Deprecated
    public void writePackedEnum(long fieldId, int[] val) {
        assertNotCompacted();
        int id = checkFieldId(fieldId, 5557687681024L);
        int N = val != null ? val.length : 0;
        if (N > 0) {
            int size = 0;
            for (int i = 0; i < N; i++) {
                int v = val[i];
                size += v >= 0 ? EncodedBuffer.getRawVarint32Size(v) : 10;
            }
            writeKnownLengthHeader(id, size);
            for (int i2 = 0; i2 < N; i2++) {
                writeUnsignedVarintFromSignedInt(val[i2]);
            }
        }
    }

    @Deprecated
    public long startObject(long fieldId) {
        assertNotCompacted();
        return startObjectImpl(checkFieldId(fieldId, 1146756268032L), false);
    }

    @Deprecated
    public void endObject(long token) {
        assertNotCompacted();
        endObjectImpl(token, false);
    }

    @Deprecated
    public long startRepeatedObject(long fieldId) {
        assertNotCompacted();
        return startObjectImpl(checkFieldId(fieldId, 2246267895808L), true);
    }

    @Deprecated
    public void endRepeatedObject(long token) {
        assertNotCompacted();
        endObjectImpl(token, true);
    }

    private long startObjectImpl(int id, boolean repeated) {
        writeTag(id, 2);
        int sizePos = this.mBuffer.getWritePos();
        this.mDepth++;
        this.mNextObjectId--;
        this.mBuffer.writeRawFixed32((int) (this.mExpectedObjectToken >> 32));
        this.mBuffer.writeRawFixed32((int) this.mExpectedObjectToken);
        long j = this.mExpectedObjectToken;
        this.mExpectedObjectToken = makeToken(getTagSize(id), repeated, this.mDepth, this.mNextObjectId, sizePos);
        return this.mExpectedObjectToken;
    }

    private void endObjectImpl(long token, boolean repeated) {
        int depth = getDepthFromToken(token);
        boolean expectedRepeated = getRepeatedFromToken(token);
        int sizePos = getOffsetFromToken(token);
        int childRawSize = (this.mBuffer.getWritePos() - sizePos) - 8;
        if (repeated != expectedRepeated) {
            if (repeated) {
                throw new IllegalArgumentException("endRepeatedObject called where endObject should have been");
            }
            throw new IllegalArgumentException("endObject called where endRepeatedObject should have been");
        } else if ((this.mDepth & 511) == depth && this.mExpectedObjectToken == token) {
            this.mExpectedObjectToken = (((long) this.mBuffer.getRawFixed32At(sizePos)) << 32) | (4294967295L & ((long) this.mBuffer.getRawFixed32At(sizePos + 4)));
            this.mDepth--;
            if (childRawSize > 0) {
                this.mBuffer.editRawFixed32(sizePos, -childRawSize);
                this.mBuffer.editRawFixed32(sizePos + 4, -1);
            } else if (repeated) {
                this.mBuffer.editRawFixed32(sizePos, 0);
                this.mBuffer.editRawFixed32(sizePos + 4, 0);
            } else {
                this.mBuffer.rewindWriteTo(sizePos - getTagSizeFromToken(token));
            }
        } else {
            throw new IllegalArgumentException("Mismatched startObject/endObject calls. Current depth " + this.mDepth + " token=" + token2String(token) + " expectedToken=" + token2String(this.mExpectedObjectToken));
        }
    }

    @Deprecated
    public void writeObject(long fieldId, byte[] value) {
        assertNotCompacted();
        writeObjectImpl(checkFieldId(fieldId, 1146756268032L), value);
    }

    /* access modifiers changed from: package-private */
    public void writeObjectImpl(int id, byte[] value) {
        if (value != null && value.length != 0) {
            writeKnownLengthHeader(id, value.length);
            this.mBuffer.writeRawBuffer(value);
        }
    }

    @Deprecated
    public void writeRepeatedObject(long fieldId, byte[] value) {
        assertNotCompacted();
        writeRepeatedObjectImpl(checkFieldId(fieldId, 2246267895808L), value);
    }

    /* access modifiers changed from: package-private */
    public void writeRepeatedObjectImpl(int id, byte[] value) {
        writeKnownLengthHeader(id, value == null ? 0 : value.length);
        this.mBuffer.writeRawBuffer(value);
    }

    public static long makeFieldId(int id, long fieldFlags) {
        return (((long) id) & 4294967295L) | fieldFlags;
    }

    public static int checkFieldId(long fieldId, long expectedFlags) {
        long j = fieldId;
        long fieldCount = j & ProtoStream.FIELD_COUNT_MASK;
        long fieldType = j & ProtoStream.FIELD_TYPE_MASK;
        long expectedCount = expectedFlags & ProtoStream.FIELD_COUNT_MASK;
        long expectedType = expectedFlags & ProtoStream.FIELD_TYPE_MASK;
        if (((int) j) == 0) {
            throw new IllegalArgumentException("Invalid proto field " + ((int) j) + " fieldId=" + Long.toHexString(fieldId));
        } else if (fieldType == expectedType && (fieldCount == expectedCount || (fieldCount == ProtoStream.FIELD_COUNT_PACKED && expectedCount == ProtoStream.FIELD_COUNT_REPEATED))) {
            return (int) j;
        } else {
            String countString = getFieldCountString(fieldCount);
            String typeString = getFieldTypeString(fieldType);
            if (typeString == null || countString == null) {
                StringBuilder sb = new StringBuilder();
                if (expectedType == ProtoStream.FIELD_TYPE_MESSAGE) {
                    sb.append(Telephony.BaseMmsColumns.START);
                } else {
                    sb.append("write");
                }
                sb.append(getFieldCountString(expectedCount));
                sb.append(getFieldTypeString(expectedType));
                sb.append(" called with an invalid fieldId: 0x");
                sb.append(Long.toHexString(fieldId));
                sb.append(". The proto field ID might be ");
                sb.append((int) j);
                sb.append('.');
                throw new IllegalArgumentException(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder();
            if (expectedType == ProtoStream.FIELD_TYPE_MESSAGE) {
                sb2.append(Telephony.BaseMmsColumns.START);
            } else {
                sb2.append("write");
            }
            sb2.append(getFieldCountString(expectedCount));
            sb2.append(getFieldTypeString(expectedType));
            sb2.append(" called for field ");
            sb2.append((int) j);
            sb2.append(" which should be used with ");
            if (fieldType == ProtoStream.FIELD_TYPE_MESSAGE) {
                sb2.append(Telephony.BaseMmsColumns.START);
            } else {
                sb2.append("write");
            }
            sb2.append(countString);
            sb2.append(typeString);
            if (fieldCount == ProtoStream.FIELD_COUNT_PACKED) {
                sb2.append(" or writeRepeated");
                sb2.append(typeString);
            }
            sb2.append('.');
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private static int getTagSize(int id) {
        return EncodedBuffer.getRawVarint32Size(id << 3);
    }

    public void writeTag(int id, int wireType) {
        this.mBuffer.writeRawVarint32((id << 3) | wireType);
    }

    private void writeKnownLengthHeader(int id, int size) {
        writeTag(id, 2);
        this.mBuffer.writeRawFixed32(size);
        this.mBuffer.writeRawFixed32(size);
    }

    private void assertNotCompacted() {
        if (this.mCompacted) {
            throw new IllegalArgumentException("write called after compact");
        }
    }

    public byte[] getBytes() {
        compactIfNecessary();
        return this.mBuffer.getBytes(this.mBuffer.getReadableSize());
    }

    private void compactIfNecessary() {
        if (this.mCompacted) {
            return;
        }
        if (this.mDepth == 0) {
            this.mBuffer.startEditing();
            int readableSize = this.mBuffer.getReadableSize();
            editEncodedSize(readableSize);
            this.mBuffer.rewindRead();
            compactSizes(readableSize);
            if (this.mCopyBegin < readableSize) {
                this.mBuffer.writeFromThisBuffer(this.mCopyBegin, readableSize - this.mCopyBegin);
            }
            this.mBuffer.startEditing();
            this.mCompacted = true;
            return;
        }
        throw new IllegalArgumentException("Trying to compact with " + this.mDepth + " missing calls to endObject");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00da, code lost:
        if ((r12.mBuffer.readRawByte() & 128) == 0) goto L_0x0009;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int editEncodedSize(int r13) {
        /*
            r12 = this;
            android.util.proto.EncodedBuffer r0 = r12.mBuffer
            int r0 = r0.getReadPos()
            int r1 = r0 + r13
            r2 = 0
        L_0x0009:
            android.util.proto.EncodedBuffer r3 = r12.mBuffer
            int r3 = r3.getReadPos()
            r4 = r3
            if (r3 >= r1) goto L_0x00e1
            int r3 = r12.readRawTag()
            int r5 = android.util.proto.EncodedBuffer.getRawVarint32Size(r3)
            int r2 = r2 + r5
            r5 = r3 & 7
            switch(r5) {
                case 0: goto L_0x00d0;
                case 1: goto L_0x00c6;
                case 2: goto L_0x0072;
                case 3: goto L_0x005b;
                case 4: goto L_0x005b;
                case 5: goto L_0x0051;
                default: goto L_0x0020;
            }
        L_0x0020:
            android.util.proto.ProtoParseException r6 = new android.util.proto.ProtoParseException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "editEncodedSize Bad tag tag=0x"
            r7.append(r8)
            java.lang.String r8 = java.lang.Integer.toHexString(r3)
            r7.append(r8)
            java.lang.String r8 = " wireType="
            r7.append(r8)
            r7.append(r5)
            java.lang.String r8 = " -- "
            r7.append(r8)
            android.util.proto.EncodedBuffer r8 = r12.mBuffer
            java.lang.String r8 = r8.getDebugString()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x0051:
            int r2 = r2 + 4
            android.util.proto.EncodedBuffer r6 = r12.mBuffer
            r7 = 4
            r6.skipRead(r7)
            goto L_0x00df
        L_0x005b:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "groups not supported at index "
            r7.append(r8)
            r7.append(r4)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L_0x0072:
            android.util.proto.EncodedBuffer r6 = r12.mBuffer
            int r6 = r6.readRawFixed32()
            android.util.proto.EncodedBuffer r7 = r12.mBuffer
            int r7 = r7.getReadPos()
            android.util.proto.EncodedBuffer r8 = r12.mBuffer
            int r8 = r8.readRawFixed32()
            if (r6 < 0) goto L_0x00b5
            if (r8 != r6) goto L_0x008e
            android.util.proto.EncodedBuffer r9 = r12.mBuffer
            r9.skipRead(r6)
            goto L_0x00bf
        L_0x008e:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Pre-computed size where the precomputed size and the raw size in the buffer don't match! childRawSize="
            r10.append(r11)
            r10.append(r6)
            java.lang.String r11 = " childEncodedSize="
            r10.append(r11)
            r10.append(r8)
            java.lang.String r11 = " childEncodedSizePos="
            r10.append(r11)
            r10.append(r7)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x00b5:
            int r9 = -r6
            int r8 = r12.editEncodedSize(r9)
            android.util.proto.EncodedBuffer r9 = r12.mBuffer
            r9.editRawFixed32(r7, r8)
        L_0x00bf:
            int r9 = android.util.proto.EncodedBuffer.getRawVarint32Size(r8)
            int r9 = r9 + r8
            int r2 = r2 + r9
            goto L_0x00df
        L_0x00c6:
            int r2 = r2 + 8
            android.util.proto.EncodedBuffer r6 = r12.mBuffer
            r7 = 8
            r6.skipRead(r7)
            goto L_0x00df
        L_0x00d0:
            int r2 = r2 + 1
        L_0x00d2:
            android.util.proto.EncodedBuffer r6 = r12.mBuffer
            byte r6 = r6.readRawByte()
            r6 = r6 & 128(0x80, float:1.794E-43)
            if (r6 == 0) goto L_0x00df
            int r2 = r2 + 1
            goto L_0x00d2
        L_0x00df:
            goto L_0x0009
        L_0x00e1:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.editEncodedSize(int):int");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void compactSizes(int r10) {
        /*
            r9 = this;
            android.util.proto.EncodedBuffer r0 = r9.mBuffer
            int r0 = r0.getReadPos()
            int r1 = r0 + r10
        L_0x0008:
            android.util.proto.EncodedBuffer r2 = r9.mBuffer
            int r2 = r2.getReadPos()
            r3 = r2
            if (r2 >= r1) goto L_0x00b4
            int r2 = r9.readRawTag()
            r4 = r2 & 7
            switch(r4) {
                case 0: goto L_0x00a7;
                case 1: goto L_0x009f;
                case 2: goto L_0x0069;
                case 3: goto L_0x0052;
                case 4: goto L_0x0052;
                case 5: goto L_0x004b;
                default: goto L_0x001a;
            }
        L_0x001a:
            android.util.proto.ProtoParseException r5 = new android.util.proto.ProtoParseException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "compactSizes Bad tag tag=0x"
            r6.append(r7)
            java.lang.String r7 = java.lang.Integer.toHexString(r2)
            r6.append(r7)
            java.lang.String r7 = " wireType="
            r6.append(r7)
            r6.append(r4)
            java.lang.String r7 = " -- "
            r6.append(r7)
            android.util.proto.EncodedBuffer r7 = r9.mBuffer
            java.lang.String r7 = r7.getDebugString()
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x004b:
            android.util.proto.EncodedBuffer r5 = r9.mBuffer
            r6 = 4
            r5.skipRead(r6)
            goto L_0x00b2
        L_0x0052:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "groups not supported at index "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x0069:
            android.util.proto.EncodedBuffer r5 = r9.mBuffer
            int r6 = r9.mCopyBegin
            android.util.proto.EncodedBuffer r7 = r9.mBuffer
            int r7 = r7.getReadPos()
            int r8 = r9.mCopyBegin
            int r7 = r7 - r8
            r5.writeFromThisBuffer(r6, r7)
            android.util.proto.EncodedBuffer r5 = r9.mBuffer
            int r5 = r5.readRawFixed32()
            android.util.proto.EncodedBuffer r6 = r9.mBuffer
            int r6 = r6.readRawFixed32()
            android.util.proto.EncodedBuffer r7 = r9.mBuffer
            r7.writeRawVarint32(r6)
            android.util.proto.EncodedBuffer r7 = r9.mBuffer
            int r7 = r7.getReadPos()
            r9.mCopyBegin = r7
            if (r5 < 0) goto L_0x009a
            android.util.proto.EncodedBuffer r7 = r9.mBuffer
            r7.skipRead(r6)
            goto L_0x00b2
        L_0x009a:
            int r7 = -r5
            r9.compactSizes(r7)
            goto L_0x00b2
        L_0x009f:
            android.util.proto.EncodedBuffer r5 = r9.mBuffer
            r6 = 8
            r5.skipRead(r6)
            goto L_0x00b2
        L_0x00a7:
            android.util.proto.EncodedBuffer r5 = r9.mBuffer
            byte r5 = r5.readRawByte()
            r5 = r5 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x00b2
            goto L_0x00a7
        L_0x00b2:
            goto L_0x0008
        L_0x00b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.proto.ProtoOutputStream.compactSizes(int):void");
    }

    public void flush() {
        if (this.mStream != null && this.mDepth == 0 && !this.mCompacted) {
            compactIfNecessary();
            try {
                this.mStream.write(this.mBuffer.getBytes(this.mBuffer.getReadableSize()));
                this.mStream.flush();
            } catch (IOException ex) {
                throw new RuntimeException("Error flushing proto to stream", ex);
            }
        }
    }

    private int readRawTag() {
        if (this.mBuffer.getReadPos() == this.mBuffer.getReadableSize()) {
            return 0;
        }
        return (int) this.mBuffer.readRawUnsigned();
    }

    public void dump(String tag) {
        Log.d(tag, this.mBuffer.getDebugString());
        this.mBuffer.dumpBuffers(tag);
    }
}
