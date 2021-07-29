package com.wits.pms.mcu.custom;

import android.os.Handler;
import android.os.Message;
import com.wits.pms.mcu.McuMessage;
import com.wits.pms.mcu.McuSender;

public class KswMcuSender {
    private static KswMcuSender kswMcuSender;
    private Handler mHandler = new KswMcuMsgHandler(this);
    private final McuSender mcuSender;

    static class KswMcuMsgHandler extends Handler {
        private final KswMcuSender mcuSender;

        public KswMcuMsgHandler(KswMcuSender sender) {
            this.mcuSender = sender;
        }

        public void handleMessage(Message msg) {
            this.mcuSender.sendMessage(KswMessage.obtain(msg.what, (byte[]) msg.obj));
        }
    }

    public KswMcuSender(McuSender sender) {
        this.mcuSender = sender;
    }

    public void sendMessage(int cmdType, byte[] data) {
        sendMessage(KswMessage.obtain(cmdType, data));
    }

    public void sendMessageDelay(int cmdType, byte[] data, long delay) {
        this.mHandler.removeMessages(cmdType);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(cmdType, data), delay);
    }

    public void sendMessage(KswMessage msg) {
        if (this.mcuSender != null && KswMcuLogic.handleSendMsg(msg)) {
            this.mcuSender.send((McuMessage) msg);
        }
    }

    public void sendUpdateMessage(KswMessage msg) {
        if (this.mcuSender != null && KswMcuLogic.handleSendMsg(msg)) {
            this.mcuSender.send((McuMessage) msg);
        }
    }

    public static void init(McuSender sender) {
        kswMcuSender = new KswMcuSender(sender);
    }

    public static KswMcuSender getSender() {
        if (kswMcuSender == null) {
            kswMcuSender = new KswMcuSender((McuSender) null);
        }
        return kswMcuSender;
    }
}
