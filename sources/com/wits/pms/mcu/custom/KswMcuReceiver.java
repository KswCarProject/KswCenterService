package com.wits.pms.mcu.custom;

import android.util.Log;
import com.wits.pms.BuildConfig;
import com.wits.pms.mcu.McuService;

public abstract class KswMcuReceiver implements McuService.OnReceiveData {
    private static byte[] bytes;
    private byte[] currentPack;
    private int dataIndex;
    private byte[] realPack;
    private boolean recvHead;
    private int recvHeadIndex;

    public abstract void onMcuMessage(byte[] bArr);

    public static void main(String... a) {
        StringBuilder sb = new StringBuilder("pass");
        sb.replace(0, 1, ("pass".charAt(0) + BuildConfig.FLAVOR).toUpperCase());
    }

    public void reset() {
        this.dataIndex = 0;
        this.currentPack = null;
        this.realPack = null;
        this.recvHead = false;
    }

    public void onReceiveMcu(byte[] packs) {
        int i = 0;
        while (i < packs.length) {
            try {
                int data = packs[i] & 255;
                if (this.recvHead) {
                    if (this.currentPack != null) {
                        this.currentPack[this.dataIndex] = (byte) data;
                        if (this.dataIndex == 3) {
                            this.realPack = new byte[(packs[i] + 5)];
                        }
                        if (this.realPack != null && this.dataIndex == this.realPack.length - 1) {
                            System.arraycopy(this.currentPack, 0, this.realPack, 0, this.realPack.length);
                            onMcuMessage(this.realPack);
                            this.currentPack = null;
                            this.realPack = null;
                            this.recvHead = false;
                        }
                    }
                } else if (!this.recvHead && data == 242) {
                    this.dataIndex = 0;
                    this.recvHead = true;
                    this.currentPack = new byte[128];
                    this.currentPack[0] = -14;
                }
                this.dataIndex++;
                i++;
            } catch (Exception e) {
                reset();
                printHex("onDataReceived McuException", packs);
                Log.e("McuService", "McuException", e);
                return;
            }
        }
    }

    public static void printHex(String method, byte[] data) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("-----[");
        for (byte b : data) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append("0x");
            sb.append(hex.toUpperCase());
            sb.append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), BuildConfig.FLAVOR);
        sb.append("]\n");
        Log.v("KswMcuMessage", sb.toString());
    }
}
