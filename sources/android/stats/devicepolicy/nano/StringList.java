package android.stats.devicepolicy.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class StringList extends MessageNano {
    private static volatile StringList[] _emptyArray;
    public String[] stringValue;

    public static StringList[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new StringList[0];
                }
            }
        }
        return _emptyArray;
    }

    public StringList() {
        clear();
    }

    public StringList clear() {
        this.stringValue = WireFormatNano.EMPTY_STRING_ARRAY;
        this.cachedSize = -1;
        return this;
    }

    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.stringValue != null && this.stringValue.length > 0) {
            for (String element : this.stringValue) {
                if (element != null) {
                    output.writeString(1, element);
                }
            }
        }
        super.writeTo(output);
    }

    /* access modifiers changed from: protected */
    public int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.stringValue == null || this.stringValue.length <= 0) {
            return size;
        }
        int dataCount = 0;
        int dataSize = 0;
        for (String element : this.stringValue) {
            if (element != null) {
                dataCount++;
                dataSize += CodedOutputByteBufferNano.computeStringSizeNoTag(element);
            }
        }
        return size + dataSize + (dataCount * 1);
    }

    public StringList mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            if (tag == 0) {
                return this;
            }
            if (tag == 10) {
                int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                int i = this.stringValue == null ? 0 : this.stringValue.length;
                String[] newArray = new String[(i + arrayLength)];
                if (i != 0) {
                    System.arraycopy(this.stringValue, 0, newArray, 0, i);
                }
                while (i < newArray.length - 1) {
                    newArray[i] = input.readString();
                    input.readTag();
                    i++;
                }
                newArray[i] = input.readString();
                this.stringValue = newArray;
            } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                return this;
            }
        }
    }

    public static StringList parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (StringList) MessageNano.mergeFrom(new StringList(), data);
    }

    public static StringList parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new StringList().mergeFrom(input);
    }
}
