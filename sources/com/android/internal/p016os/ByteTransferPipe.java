package com.android.internal.p016os;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.android.internal.os.ByteTransferPipe */
/* loaded from: classes4.dex */
public class ByteTransferPipe extends TransferPipe {
    static final String TAG = "ByteTransferPipe";
    private ByteArrayOutputStream mOutputStream;

    public ByteTransferPipe() throws IOException {
    }

    public ByteTransferPipe(String bufferPrefix) throws IOException {
        super(bufferPrefix, TAG);
    }

    @Override // com.android.internal.p016os.TransferPipe
    protected OutputStream getNewOutputStream() {
        this.mOutputStream = new ByteArrayOutputStream();
        return this.mOutputStream;
    }

    public byte[] get() throws IOException {
        m31go(null);
        return this.mOutputStream.toByteArray();
    }
}
