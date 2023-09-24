package com.wits.pms.mcu.custom;

import com.android.internal.content.NativeLibraryHelper;
import com.wits.pms.mcu.McuMessage;

/* loaded from: classes2.dex */
public class KswMessage extends McuMessage {
    public static final int FRAMEHEAD = 242;
    public static final int NORMAL_DATATYPE = 0;
    public static final int REQUEST_AIR_DATA_CMD = 10;
    public static final int REQUEST_MEDIA_DATA_CMD = 11;
    public static final int UPDATE_DATATYPE = 160;
    private int cmdType;

    public KswMessage(int cmdType, byte[] data) {
        this(cmdType, data, false);
    }

    public KswMessage(int cmdType, byte[] data, boolean update) {
        this.cmdType = cmdType;
        this.data = data;
        byte[] bytes = new byte[data.length + 2];
        System.arraycopy(data, 0, bytes, 2, data.length);
        this.frameHead = 242;
        this.dataType = update ? 160 : 0;
        bytes[0] = (byte) cmdType;
        bytes[1] = (byte) data.length;
        obtain(bytes);
    }

    public static KswMessage obtain(int cmdType, byte[] data) {
        return new KswMessage(cmdType, data);
    }

    public static KswMessage parse(byte[] data) {
        byte[] realData = new byte[data[3]];
        System.arraycopy(data, 4, realData, 0, realData.length);
        KswMessage kswMessage = new KswMessage(data[2] & 255, realData, (data[1] & 255) != 0);
        return kswMessage;
    }

    @Override // com.wits.pms.mcu.McuMessage
    public String printHex(String method, McuMessage msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(method);
        sb.append("-----[ cmdType:");
        sb.append(Integer.toHexString(getCmdType() & 255).toUpperCase());
        sb.append(" - data:");
        for (int i = 0; i < msg.getRealData().length; i++) {
            String hex = Integer.toHexString(msg.getRealData()[i] & 255);
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

    public int getCmdType() {
        return this.cmdType;
    }

    public static KswMessage obtain(int cmdType, byte[] datas, boolean b) {
        return new KswMessage(cmdType, datas, b);
    }

    @Override // com.wits.pms.mcu.McuMessage
    public byte[] getData() {
        return getRealData();
    }

    public byte[] getSourceData() {
        return this.outData;
    }

    public static KswMessage obtainKswMcuMsg(int type) {
        switch (type) {
            case 10:
                return new KswMessage(104, new byte[]{10, 0});
            case 11:
                return new KswMessage(104, new byte[]{11, 0});
            default:
                return null;
        }
    }
}
