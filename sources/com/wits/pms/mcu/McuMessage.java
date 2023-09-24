package com.wits.pms.mcu;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes2.dex */
public class McuMessage {
    public byte[] data;
    public int dataType;
    public int frameHead;
    private int len;
    public byte[] outData;

    public McuMessage(int frameHead, int dataType, byte[] data) {
        this.frameHead = frameHead;
        this.dataType = dataType;
        this.data = data;
        obtain(data);
    }

    public void obtain(byte[] data) {
        this.len = data.length + 2;
        byte[] bytes = new byte[this.len + 1];
        bytes[0] = (byte) this.frameHead;
        bytes[1] = (byte) this.dataType;
        System.arraycopy(data, 0, bytes, 2, data.length);
        byte checkSum = (byte) sumCheck(bytes);
        bytes[this.len] = checkSum;
        this.outData = bytes;
    }

    public McuMessage() {
    }

    public static McuMessage parse(byte[] data) {
        if (check(data)) {
            McuMessage mcuMessage = new McuMessage();
            mcuMessage.frameHead = data[0];
            mcuMessage.dataType = data[1];
            mcuMessage.len = data[3];
            mcuMessage.outData = data;
            mcuMessage.data = new byte[data[3] + 5];
            System.arraycopy(data, 2, mcuMessage.data, 0, mcuMessage.data.length);
            return mcuMessage;
        }
        return null;
    }

    public static int sumCheck(byte[] b) {
        int sum = 0;
        for (int sum2 = 1; sum2 < b.length - 1; sum2++) {
            sum += b[sum2] & 255;
        }
        int i = ~sum;
        return i;
    }

    public static boolean check(byte[] b) {
        int sum = 0;
        for (int sum2 = 1; sum2 < b.length - 1; sum2++) {
            sum += b[sum2] & 255;
        }
        int i = b.length;
        int result = b[i - 1] + sum;
        return result == 255;
    }

    public String toString() {
        return printHex("Mcu toString", this);
    }

    public String printHex(String method, McuMessage msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(method);
        sb.append("-----[");
        for (int i = 0; i < msg.getData().length; i++) {
            String hex = Integer.toHexString(msg.getData()[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
            sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        }
        int i2 = sb.length();
        sb.replace(i2 - 1, sb.length(), "");
        sb.append(" ]\n");
        return sb.toString();
    }

    public int getFrameHead() {
        return this.frameHead;
    }

    public byte[] getRealData() {
        return this.data;
    }

    public byte[] getData() {
        return this.outData;
    }

    public int getDataType() {
        return this.dataType;
    }
}
