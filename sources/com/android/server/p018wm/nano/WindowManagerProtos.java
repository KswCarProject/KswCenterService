package com.android.server.p018wm.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* renamed from: com.android.server.wm.nano.WindowManagerProtos */
/* loaded from: classes4.dex */
public interface WindowManagerProtos {

    /* renamed from: com.android.server.wm.nano.WindowManagerProtos$TaskSnapshotProto */
    /* loaded from: classes4.dex */
    public static final class TaskSnapshotProto extends MessageNano {
        private static volatile TaskSnapshotProto[] _emptyArray;
        public int insetBottom;
        public int insetLeft;
        public int insetRight;
        public int insetTop;
        public boolean isRealSnapshot;
        public boolean isTranslucent;
        public int orientation;
        public float scale;
        public int systemUiVisibility;
        public String topActivityComponent;
        public int windowingMode;

        public static TaskSnapshotProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new TaskSnapshotProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public TaskSnapshotProto() {
            clear();
        }

        public TaskSnapshotProto clear() {
            this.orientation = 0;
            this.insetLeft = 0;
            this.insetTop = 0;
            this.insetRight = 0;
            this.insetBottom = 0;
            this.isRealSnapshot = false;
            this.windowingMode = 0;
            this.systemUiVisibility = 0;
            this.isTranslucent = false;
            this.topActivityComponent = "";
            this.scale = 0.0f;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.orientation != 0) {
                output.writeInt32(1, this.orientation);
            }
            if (this.insetLeft != 0) {
                output.writeInt32(2, this.insetLeft);
            }
            if (this.insetTop != 0) {
                output.writeInt32(3, this.insetTop);
            }
            if (this.insetRight != 0) {
                output.writeInt32(4, this.insetRight);
            }
            if (this.insetBottom != 0) {
                output.writeInt32(5, this.insetBottom);
            }
            if (this.isRealSnapshot) {
                output.writeBool(6, this.isRealSnapshot);
            }
            if (this.windowingMode != 0) {
                output.writeInt32(7, this.windowingMode);
            }
            if (this.systemUiVisibility != 0) {
                output.writeInt32(8, this.systemUiVisibility);
            }
            if (this.isTranslucent) {
                output.writeBool(9, this.isTranslucent);
            }
            if (!this.topActivityComponent.equals("")) {
                output.writeString(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.scale) != Float.floatToIntBits(0.0f)) {
                output.writeFloat(11, this.scale);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.orientation != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(1, this.orientation);
            }
            if (this.insetLeft != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(2, this.insetLeft);
            }
            if (this.insetTop != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.insetTop);
            }
            if (this.insetRight != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(4, this.insetRight);
            }
            if (this.insetBottom != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(5, this.insetBottom);
            }
            if (this.isRealSnapshot) {
                size += CodedOutputByteBufferNano.computeBoolSize(6, this.isRealSnapshot);
            }
            if (this.windowingMode != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(7, this.windowingMode);
            }
            if (this.systemUiVisibility != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(8, this.systemUiVisibility);
            }
            if (this.isTranslucent) {
                size += CodedOutputByteBufferNano.computeBoolSize(9, this.isTranslucent);
            }
            if (!this.topActivityComponent.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.scale) != Float.floatToIntBits(0.0f)) {
                return size + CodedOutputByteBufferNano.computeFloatSize(11, this.scale);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public TaskSnapshotProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 8:
                        this.orientation = input.readInt32();
                        break;
                    case 16:
                        this.insetLeft = input.readInt32();
                        break;
                    case 24:
                        this.insetTop = input.readInt32();
                        break;
                    case 32:
                        this.insetRight = input.readInt32();
                        break;
                    case 40:
                        this.insetBottom = input.readInt32();
                        break;
                    case 48:
                        this.isRealSnapshot = input.readBool();
                        break;
                    case 56:
                        this.windowingMode = input.readInt32();
                        break;
                    case 64:
                        this.systemUiVisibility = input.readInt32();
                        break;
                    case 72:
                        this.isTranslucent = input.readBool();
                        break;
                    case 82:
                        this.topActivityComponent = input.readString();
                        break;
                    case 93:
                        this.scale = input.readFloat();
                        break;
                    default:
                        if (WireFormatNano.parseUnknownField(input, tag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        public static TaskSnapshotProto parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (TaskSnapshotProto) MessageNano.mergeFrom(new TaskSnapshotProto(), data);
        }

        public static TaskSnapshotProto parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new TaskSnapshotProto().mergeFrom(input);
        }
    }
}
