package com.android.framework.protobuf;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes4.dex */
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
        int count = first.count + second.count;
        int[] tags = Arrays.copyOf(first.tags, count);
        System.arraycopy(second.tags, 0, tags, first.count, second.count);
        Object[] objects = Arrays.copyOf(first.objects, count);
        System.arraycopy(second.objects, 0, objects, first.count, second.count);
        return new UnknownFieldSetLite(count, tags, objects, true);
    }

    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }

    private UnknownFieldSetLite(int count, int[] tags, Object[] objects, boolean isMutable) {
        this.memoizedSerializedSize = -1;
        this.count = count;
        this.tags = tags;
        this.objects = objects;
        this.isMutable = isMutable;
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    void checkMutable() {
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
                        continue;
                    case 1:
                        output.writeFixed64(fieldNumber, ((Long) this.objects[i]).longValue());
                        continue;
                    case 2:
                        output.writeBytes(fieldNumber, (ByteString) this.objects[i]);
                        continue;
                    case 3:
                        output.writeTag(fieldNumber, 3);
                        ((UnknownFieldSetLite) this.objects[i]).writeTo(output);
                        output.writeTag(fieldNumber, 4);
                        continue;
                    default:
                        throw InvalidProtocolBufferException.invalidWireType();
                }
            } else {
                output.writeFixed32(fieldNumber, ((Integer) this.objects[i]).intValue());
            }
        }
    }

    public int getSerializedSize() {
        int computeFixed32Size;
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i = 0; i < this.count; i++) {
            int tag = this.tags[i];
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            int tagWireType = WireFormat.getTagWireType(tag);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        computeFixed32Size = CodedOutputStream.computeUInt64Size(fieldNumber, ((Long) this.objects[i]).longValue());
                        continue;
                    case 1:
                        computeFixed32Size = CodedOutputStream.computeFixed64Size(fieldNumber, ((Long) this.objects[i]).longValue());
                        continue;
                    case 2:
                        computeFixed32Size = CodedOutputStream.computeBytesSize(fieldNumber, (ByteString) this.objects[i]);
                        continue;
                    case 3:
                        computeFixed32Size = (CodedOutputStream.computeTagSize(fieldNumber) * 2) + ((UnknownFieldSetLite) this.objects[i]).getSerializedSize();
                        continue;
                    default:
                        throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                }
            } else {
                computeFixed32Size = CodedOutputStream.computeFixed32Size(fieldNumber, ((Integer) this.objects[i]).intValue());
            }
            size2 += computeFixed32Size;
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
        if (this.count == other.count && Arrays.equals(this.tags, other.tags) && Arrays.deepEquals(this.objects, other.objects)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (17 * 31) + this.count;
        return (((hashCode * 31) + Arrays.hashCode(this.tags)) * 31) + Arrays.deepHashCode(this.objects);
    }

    final void printWithIndent(StringBuilder buffer, int indent) {
        for (int i = 0; i < this.count; i++) {
            int fieldNumber = WireFormat.getTagFieldNumber(this.tags[i]);
            MessageLiteToString.printField(buffer, indent, String.valueOf(fieldNumber), this.objects[i]);
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
            int increment = this.count < 4 ? 8 : this.count >> 1;
            int newLength = this.count + increment;
            this.tags = Arrays.copyOf(this.tags, newLength);
            this.objects = Arrays.copyOf(this.objects, newLength);
        }
    }

    boolean mergeFieldFrom(int tag, CodedInputStream input) throws IOException {
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

    UnknownFieldSetLite mergeVarintField(int fieldNumber, int value) {
        checkMutable();
        if (fieldNumber == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        storeField(WireFormat.makeTag(fieldNumber, 0), Long.valueOf(value));
        return this;
    }

    UnknownFieldSetLite mergeLengthDelimitedField(int fieldNumber, ByteString value) {
        checkMutable();
        if (fieldNumber == 0) {
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }
        storeField(WireFormat.makeTag(fieldNumber, 2), value);
        return this;
    }

    private UnknownFieldSetLite mergeFrom(CodedInputStream input) throws IOException {
        int tag;
        do {
            tag = input.readTag();
            if (tag == 0) {
                break;
            }
        } while (mergeFieldFrom(tag, input));
        return this;
    }
}
