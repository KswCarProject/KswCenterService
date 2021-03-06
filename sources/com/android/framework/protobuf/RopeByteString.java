package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

final class RopeByteString extends ByteString {
    /* access modifiers changed from: private */
    public static final int[] minLengthByDepth;
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final ByteString left;
    private final int leftLength;
    /* access modifiers changed from: private */
    public final ByteString right;
    private final int totalLength;
    private final int treeDepth;

    static {
        List<Integer> numbers = new ArrayList<>();
        int f1 = 1;
        int f2 = 1;
        while (f2 > 0) {
            numbers.add(Integer.valueOf(f2));
            int temp = f1 + f2;
            f1 = f2;
            f2 = temp;
        }
        numbers.add(Integer.MAX_VALUE);
        minLengthByDepth = new int[numbers.size()];
        for (int i = 0; i < minLengthByDepth.length; i++) {
            minLengthByDepth[i] = numbers.get(i).intValue();
        }
    }

    private RopeByteString(ByteString left2, ByteString right2) {
        this.left = left2;
        this.right = right2;
        this.leftLength = left2.size();
        this.totalLength = this.leftLength + right2.size();
        this.treeDepth = Math.max(left2.getTreeDepth(), right2.getTreeDepth()) + 1;
    }

    static ByteString concatenate(ByteString left2, ByteString right2) {
        if (right2.size() == 0) {
            return left2;
        }
        if (left2.size() == 0) {
            return right2;
        }
        int newLength = left2.size() + right2.size();
        if (newLength < 128) {
            return concatenateBytes(left2, right2);
        }
        if (left2 instanceof RopeByteString) {
            RopeByteString leftRope = (RopeByteString) left2;
            if (leftRope.right.size() + right2.size() < 128) {
                return new RopeByteString(leftRope.left, concatenateBytes(leftRope.right, right2));
            } else if (leftRope.left.getTreeDepth() > leftRope.right.getTreeDepth() && leftRope.getTreeDepth() > right2.getTreeDepth()) {
                return new RopeByteString(leftRope.left, new RopeByteString(leftRope.right, right2));
            }
        }
        if (newLength >= minLengthByDepth[Math.max(left2.getTreeDepth(), right2.getTreeDepth()) + 1]) {
            return new RopeByteString(left2, right2);
        }
        return new Balancer().balance(left2, right2);
    }

    private static ByteString concatenateBytes(ByteString left2, ByteString right2) {
        int leftSize = left2.size();
        int rightSize = right2.size();
        byte[] bytes = new byte[(leftSize + rightSize)];
        left2.copyTo(bytes, 0, 0, leftSize);
        right2.copyTo(bytes, 0, leftSize, rightSize);
        return ByteString.wrap(bytes);
    }

    static RopeByteString newInstanceForTest(ByteString left2, ByteString right2) {
        return new RopeByteString(left2, right2);
    }

    public byte byteAt(int index) {
        checkIndex(index, this.totalLength);
        if (index < this.leftLength) {
            return this.left.byteAt(index);
        }
        return this.right.byteAt(index - this.leftLength);
    }

    public int size() {
        return this.totalLength;
    }

    /* access modifiers changed from: protected */
    public int getTreeDepth() {
        return this.treeDepth;
    }

    /* access modifiers changed from: protected */
    public boolean isBalanced() {
        return this.totalLength >= minLengthByDepth[this.treeDepth];
    }

    public ByteString substring(int beginIndex, int endIndex) {
        int length = checkRange(beginIndex, endIndex, this.totalLength);
        if (length == 0) {
            return ByteString.EMPTY;
        }
        if (length == this.totalLength) {
            return this;
        }
        if (endIndex <= this.leftLength) {
            return this.left.substring(beginIndex, endIndex);
        }
        if (beginIndex >= this.leftLength) {
            return this.right.substring(beginIndex - this.leftLength, endIndex - this.leftLength);
        }
        return new RopeByteString(this.left.substring(beginIndex), this.right.substring(0, endIndex - this.leftLength));
    }

    /* access modifiers changed from: protected */
    public void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        if (sourceOffset + numberToCopy <= this.leftLength) {
            this.left.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        } else if (sourceOffset >= this.leftLength) {
            this.right.copyToInternal(target, sourceOffset - this.leftLength, targetOffset, numberToCopy);
        } else {
            int leftLength2 = this.leftLength - sourceOffset;
            this.left.copyToInternal(target, sourceOffset, targetOffset, leftLength2);
            this.right.copyToInternal(target, 0, targetOffset + leftLength2, numberToCopy - leftLength2);
        }
    }

    public void copyTo(ByteBuffer target) {
        this.left.copyTo(target);
        this.right.copyTo(target);
    }

    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    public List<ByteBuffer> asReadOnlyByteBufferList() {
        List<ByteBuffer> result = new ArrayList<>();
        PieceIterator pieces = new PieceIterator(this);
        while (pieces.hasNext()) {
            result.add(pieces.next().asReadOnlyByteBuffer());
        }
        return result;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    /* access modifiers changed from: package-private */
    public void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        if (sourceOffset + numberToWrite <= this.leftLength) {
            this.left.writeToInternal(out, sourceOffset, numberToWrite);
        } else if (sourceOffset >= this.leftLength) {
            this.right.writeToInternal(out, sourceOffset - this.leftLength, numberToWrite);
        } else {
            int numberToWriteInLeft = this.leftLength - sourceOffset;
            this.left.writeToInternal(out, sourceOffset, numberToWriteInLeft);
            this.right.writeToInternal(out, 0, numberToWrite - numberToWriteInLeft);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeTo(ByteOutput output) throws IOException {
        this.left.writeTo(output);
        this.right.writeTo(output);
    }

    /* access modifiers changed from: protected */
    public String toStringInternal(Charset charset) {
        return new String(toByteArray(), charset);
    }

    public boolean isValidUtf8() {
        if (this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(0, 0, this.leftLength), 0, this.right.size()) == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int partialIsValidUtf8(int state, int offset, int length) {
        if (offset + length <= this.leftLength) {
            return this.left.partialIsValidUtf8(state, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialIsValidUtf8(state, offset - this.leftLength, length);
        }
        int leftLength2 = this.leftLength - offset;
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(state, offset, leftLength2), 0, length - leftLength2);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        ByteString otherByteString = (ByteString) other;
        if (this.totalLength != otherByteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int thisHash = peekCachedHashCode();
        int thatHash = otherByteString.peekCachedHashCode();
        if (thisHash == 0 || thatHash == 0 || thisHash == thatHash) {
            return equalsFragments(otherByteString);
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: com.android.framework.protobuf.ByteString$LeafByteString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: com.android.framework.protobuf.ByteString$LeafByteString} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean equalsFragments(com.android.framework.protobuf.ByteString r14) {
        /*
            r13 = this;
            r0 = 0
            com.android.framework.protobuf.RopeByteString$PieceIterator r1 = new com.android.framework.protobuf.RopeByteString$PieceIterator
            r2 = 0
            r1.<init>(r13)
            java.lang.Object r3 = r1.next()
            com.android.framework.protobuf.ByteString$LeafByteString r3 = (com.android.framework.protobuf.ByteString.LeafByteString) r3
            r4 = 0
            com.android.framework.protobuf.RopeByteString$PieceIterator r5 = new com.android.framework.protobuf.RopeByteString$PieceIterator
            r5.<init>(r14)
            r2 = r5
            java.lang.Object r5 = r2.next()
            com.android.framework.protobuf.ByteString$LeafByteString r5 = (com.android.framework.protobuf.ByteString.LeafByteString) r5
            r6 = 0
            r7 = r4
            r4 = r0
            r0 = r6
        L_0x001e:
            int r8 = r3.size()
            int r8 = r8 - r4
            int r9 = r5.size()
            int r9 = r9 - r7
            int r10 = java.lang.Math.min(r8, r9)
            if (r4 != 0) goto L_0x0033
            boolean r11 = r3.equalsRange(r5, r7, r10)
            goto L_0x0037
        L_0x0033:
            boolean r11 = r5.equalsRange(r3, r4, r10)
        L_0x0037:
            if (r11 != 0) goto L_0x003a
            return r6
        L_0x003a:
            int r0 = r0 + r10
            int r12 = r13.totalLength
            if (r0 < r12) goto L_0x004b
            int r6 = r13.totalLength
            if (r0 != r6) goto L_0x0045
            r6 = 1
            return r6
        L_0x0045:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r6.<init>()
            throw r6
        L_0x004b:
            if (r10 != r8) goto L_0x0056
            r4 = 0
            java.lang.Object r12 = r1.next()
            r3 = r12
            com.android.framework.protobuf.ByteString$LeafByteString r3 = (com.android.framework.protobuf.ByteString.LeafByteString) r3
            goto L_0x0057
        L_0x0056:
            int r4 = r4 + r10
        L_0x0057:
            if (r10 != r9) goto L_0x0062
            r7 = 0
            java.lang.Object r12 = r2.next()
            r5 = r12
            com.android.framework.protobuf.ByteString$LeafByteString r5 = (com.android.framework.protobuf.ByteString.LeafByteString) r5
            goto L_0x0063
        L_0x0062:
            int r7 = r7 + r10
        L_0x0063:
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.RopeByteString.equalsFragments(com.android.framework.protobuf.ByteString):boolean");
    }

    /* access modifiers changed from: protected */
    public int partialHash(int h, int offset, int length) {
        if (offset + length <= this.leftLength) {
            return this.left.partialHash(h, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialHash(h, offset - this.leftLength, length);
        }
        int leftLength2 = this.leftLength - offset;
        return this.right.partialHash(this.left.partialHash(h, offset, leftLength2), 0, length - leftLength2);
    }

    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance((InputStream) new RopeInputStream());
    }

    public InputStream newInput() {
        return new RopeInputStream();
    }

    private static class Balancer {
        private final Stack<ByteString> prefixesStack;

        private Balancer() {
            this.prefixesStack = new Stack<>();
        }

        /* access modifiers changed from: private */
        public ByteString balance(ByteString left, ByteString right) {
            doBalance(left);
            doBalance(right);
            ByteString partialString = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                partialString = new RopeByteString(this.prefixesStack.pop(), partialString);
            }
            return partialString;
        }

        private void doBalance(ByteString root) {
            if (root.isBalanced()) {
                insert(root);
            } else if (root instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) root;
                doBalance(rbs.left);
                doBalance(rbs.right);
            } else {
                throw new IllegalArgumentException("Has a new type of ByteString been created? Found " + root.getClass());
            }
        }

        private void insert(ByteString byteString) {
            int depthBin = getDepthBinForLength(byteString.size());
            int binEnd = RopeByteString.minLengthByDepth[depthBin + 1];
            if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= binEnd) {
                this.prefixesStack.push(byteString);
                return;
            }
            int binStart = RopeByteString.minLengthByDepth[depthBin];
            ByteString newTree = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < binStart) {
                newTree = new RopeByteString(this.prefixesStack.pop(), newTree);
            }
            ByteString newTree2 = new RopeByteString(newTree, byteString);
            while (!this.prefixesStack.isEmpty()) {
                if (this.prefixesStack.peek().size() >= RopeByteString.minLengthByDepth[getDepthBinForLength(newTree2.size()) + 1]) {
                    break;
                }
                newTree2 = new RopeByteString(this.prefixesStack.pop(), newTree2);
            }
            this.prefixesStack.push(newTree2);
        }

        private int getDepthBinForLength(int length) {
            int depth = Arrays.binarySearch(RopeByteString.minLengthByDepth, length);
            if (depth < 0) {
                return (-(depth + 1)) - 1;
            }
            return depth;
        }
    }

    private static class PieceIterator implements Iterator<ByteString.LeafByteString> {
        private final Stack<RopeByteString> breadCrumbs;
        private ByteString.LeafByteString next;

        private PieceIterator(ByteString root) {
            this.breadCrumbs = new Stack<>();
            this.next = getLeafByLeft(root);
        }

        private ByteString.LeafByteString getLeafByLeft(ByteString root) {
            ByteString pos = root;
            while (pos instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) pos;
                this.breadCrumbs.push(rbs);
                pos = rbs.left;
            }
            return (ByteString.LeafByteString) pos;
        }

        private ByteString.LeafByteString getNextNonEmptyLeaf() {
            while (!this.breadCrumbs.isEmpty()) {
                ByteString.LeafByteString result = getLeafByLeft(this.breadCrumbs.pop().right);
                if (!result.isEmpty()) {
                    return result;
                }
            }
            return null;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public ByteString.LeafByteString next() {
            if (this.next != null) {
                ByteString.LeafByteString result = this.next;
                this.next = getNextNonEmptyLeaf();
                return result;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return ByteString.wrap(toByteArray());
    }

    private void readObject(ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }

    private class RopeInputStream extends InputStream {
        private ByteString.LeafByteString currentPiece;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int currentPieceSize;
        private int mark;
        private PieceIterator pieceIterator;

        public RopeInputStream() {
            initialize();
        }

        public int read(byte[] b, int offset, int length) {
            if (b == null) {
                throw new NullPointerException();
            } else if (offset >= 0 && length >= 0 && length <= b.length - offset) {
                return readSkipInternal(b, offset, length);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public long skip(long length) {
            if (length >= 0) {
                if (length > 2147483647L) {
                    length = 2147483647L;
                }
                return (long) readSkipInternal((byte[]) null, 0, (int) length);
            }
            throw new IndexOutOfBoundsException();
        }

        private int readSkipInternal(byte[] b, int offset, int length) {
            int offset2 = offset;
            int bytesRemaining = length;
            while (true) {
                if (bytesRemaining <= 0) {
                    break;
                }
                advanceIfCurrentPieceFullyRead();
                if (this.currentPiece != null) {
                    int count = Math.min(this.currentPieceSize - this.currentPieceIndex, bytesRemaining);
                    if (b != null) {
                        this.currentPiece.copyTo(b, this.currentPieceIndex, offset2, count);
                        offset2 += count;
                    }
                    this.currentPieceIndex += count;
                    bytesRemaining -= count;
                } else if (bytesRemaining == length) {
                    return -1;
                }
            }
            return length - bytesRemaining;
        }

        public int read() throws IOException {
            advanceIfCurrentPieceFullyRead();
            if (this.currentPiece == null) {
                return -1;
            }
            ByteString.LeafByteString leafByteString = this.currentPiece;
            int i = this.currentPieceIndex;
            this.currentPieceIndex = i + 1;
            return leafByteString.byteAt(i) & 255;
        }

        public int available() throws IOException {
            return RopeByteString.this.size() - (this.currentPieceOffsetInRope + this.currentPieceIndex);
        }

        public boolean markSupported() {
            return true;
        }

        public void mark(int readAheadLimit) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        public synchronized void reset() {
            initialize();
            readSkipInternal((byte[]) null, 0, this.mark);
        }

        private void initialize() {
            this.pieceIterator = new PieceIterator(RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private void advanceIfCurrentPieceFullyRead() {
            if (this.currentPiece != null && this.currentPieceIndex == this.currentPieceSize) {
                this.currentPieceOffsetInRope += this.currentPieceSize;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                    return;
                }
                this.currentPiece = null;
                this.currentPieceSize = 0;
            }
        }
    }
}
