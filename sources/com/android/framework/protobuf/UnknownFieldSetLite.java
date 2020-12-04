package com.android.framework.protobuf;

import java.io.IOException;
import java.util.Arrays;

public final class UnknownFieldSetLite {
    private static final UnknownFieldSetLite DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    private static final int MIN_CAPACITY = 8;
    private int count;
    private boolean isMutable;
    private int memoizedSerializedSize;
    private Object[] objects;
    private int[] tags;

    public static UnknownFieldSetLite getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    static UnknownFieldSetLite newInstance() {
        return new UnknownFieldSetLite();
    }

    static UnknownFieldSetLite mutableCopyOf(UnknownFieldSetLite first, UnknownFieldSetLite second) {
        int count2 = first.count + second.count;
        int[] tags2 = Arrays.copyOf(first.tags, count2);
        System.arraycopy(second.tags, 0, tags2, first.count, second.count);
        Object[] objects2 = Arrays.copyOf(first.objects, count2);
        System.arraycopy(second.objects, 0, objects2, first.count, second.count);
        return new UnknownFieldSetLite(count2, tags2, objects2, true);
    }

    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }

    private UnknownFieldSetLite(int count2, int[] tags2, Object[] objects2, boolean isMutable2) {
        this.memoizedSerializedSize = -1;
        this.count = count2;
        this.tags = tags2;
        this.objects = objects2;
        this.isMutable = isMutable2;
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    /* access modifiers changed from: package-private */
    public void checkMutable() {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.count; i++) {
            int tag = this.tags[i];
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        output.writeUInt64(fieldNumber, ((Long) this.objects[i]).longValue());
                        break;
                    case 1:
                        output.writeFixed64(fieldNumber, ((Long) this.objects[i]).longValue());
                        break;
                    case 2:
                        output.writeBytes(fieldNumber, (ByteString) this.objects[i]);
                        break;
                    case 3:
                        output.writeTag(fieldNumber, 3);
                        ((UnknownFieldSetLite) this.objects[i]).writeTo(output);
                        output.writeTag(fieldNumber, 4);
                        break;
                    default:
                        throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                output.writeFixed32(fieldNumber, ((Integer) this.objects[i]).intValue());
            }
        }
    }

    public int getSerializedSize() {
        int i;
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i2 = 0; i2 < this.count; i2++) {
            int tag = this.tags[i2];
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        i = CodedOutputStream.computeUInt64Size(fieldNumber, ((Long) this.objects[i2]).longValue());
                        break;
                    case 1:
                        i = CodedOutputStream.computeFixed64Size(fieldNumber, ((Long) this.objects[i2]).longValue());
                        break;
                    case 2:
                        i = CodedOutputStream.computeBytesSize(fieldNumber, (ByteString) this.objects[i2]);
                        break;
                    case 3:
                        i = (CodedOutputStream.computeTagSize(fieldNumber) * 2) + ((UnknownFieldSetLite) this.objects[i2]).getSerializedSize();
                        break;
                    default:
                        throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                }
            } else {
                i = CodedOutputStream.computeFixed32Size(fieldNumber, ((Integer) this.objects[i2]).intValue());
            }
            size2 += i;
        }
        this.memoizedSerializedSize = size2;
        return size2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnknownFieldSetLite)) {
            return false;
        }
        UnknownFieldSetLite other = (UnknownFieldSetLite) obj;
        if (this.count != other.count || !Arrays.equals(this.tags, other.tags) || !Arrays.deepEquals(this.objects, other.objects)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((17 * 31) + this.count) * 31) + Arrays.hashCode(this.tags)) * 31) + Arrays.deepHashCode(this.objects);
    }

    /* access modifiers changed from: package-private */
    public final void printWithIndent(StringBuilder buffer, int indent) {
        for (int i = 0; i < this.count; i++) {
            MessageLiteToString.printField(buffer, indent, String.valueOf(WireFormat.getTagFieldNumber(this.tags[i])), this.objects[i]);
        }
    }

    private void storeField(int tag, Object value) {
        ensureCapacity();
        this.tags[this.count] = tag;
        this.objects[this.count] = value;
        this.count++;
    }

    private void ensureCapacity() {
        if (this.count == this.tags.length) {
            int newLength = this.count + (this.count < 4 ? 8 : this.count >> 1);
            this.tags = Arrays.copyOf(this.tags, newLength);
            this.objects = Arrays.copyOf(this.objects, newLength);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean mergeFieldFrom(int tag, CodedInputStream input) throws IOException {
        checkMutable();
        int fieldNumber = WireFormat.getTagFieldNumber(tag);
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                storeField(tag, Long.valueOf(input.readInt64()));
                return true;
            case 1:
                storeField(tag, Long.valueOf(input.readFixed64()));
                return true;
            case 2:
                storeField(tag, input.readBytes());
                return true;
            case 3:
                UnknownFieldSetLite subFieldSet = new UnknownFieldSetLite();
                subFieldSet.mergeFrom(input);
                input.checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
                storeField(tag, subFieldSet);
                return true;
            case 4:
                return false;
            case 5:
                storeField(tag, Integer.valueOf(input.readFixed32()));
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    /* access modifiers changed from: package-private */
    public UnknownFieldSetLite mergeVarintField(int fieldNumber, int value) {
        checkMutable();
        if (fieldNumber != 0) {
            storeField(WireFormat.makeTag(fieldNumber, 0), Long.valueOf((long) value));
            return this;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    /* access modifiers changed from: package-private */
    public UnknownFieldSetLite mergeLengthDelimitedField(int fieldNumber, ByteString value) {
        checkMutable();
        if (fieldNumber != 0) {
            storeField(WireFormat.makeTag(fieldNumber, 2), value);
            return this;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private com.android.framework.protobuf.UnknownFieldSetLite mergeFrom(com.android.framework.protobuf.CodedInputStream r3) throws java.io.IOException {
        /*
            r2 = this;
        L_0x0000:
            int r0 = r3.readTag()
            if (r0 == 0) goto L_0x000e
            boolean r1 = r2.mergeFieldFrom(r0, r3)
            if (r1 != 0) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            goto L_0x0000
        L_0x000e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.UnknownFieldSetLite.mergeFrom(com.android.framework.protobuf.CodedInputStream):com.android.framework.protobuf.UnknownFieldSetLite");
    }
}
