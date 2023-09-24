package com.wits.pms.mcu.custom;

import android.p007os.Handler;
import android.p007os.Message;
import android.util.Log;
import com.wits.pms.mcu.McuSender;

/* loaded from: classes2.dex */
public class KswMcuSender {
    private static final String TAG = "KswMcuSender";
    private static volatile KswMcuSender kswMcuSender;
    private Handler mHandler = new KswMcuMsgHandler(this);
    private final McuSender mcuSender;

    /* loaded from: classes2.dex */
    static class KswMcuMsgHandler extends Handler {
        private final KswMcuSender mcuSender;

        public KswMcuMsgHandler(KswMcuSender sender) {
            this.mcuSender = sender;
        }

        @Override // android.p007os.Handler
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

    public synchronized void sendMessage(KswMessage msg) {
        if (this.mcuSender != null && KswMcuLogic.handleSendMsg(msg)) {
            this.mcuSender.send(msg);
            return;
        }
        Log.m72d(TAG, "return directly");
    }

    public synchronized void sendUpdateMessage(KswMessage msg) {
        if (this.mcuSender != null && KswMcuLogic.handleSendMsg(msg)) {
            this.mcuSender.send(msg);
        }
    }

    public static void init(McuSender sender) {
        kswMcuSender = new KswMcuSender(sender);
    }

    public static KswMcuSender getSender() {
        if (kswMcuSender == null) {
            synchronized (KswMcuSender.class) {
                if (kswMcuSender == null) {
                    kswMcuSender = new KswMcuSender(null);
                }
            }
        }
        return kswMcuSender;
    }
}
