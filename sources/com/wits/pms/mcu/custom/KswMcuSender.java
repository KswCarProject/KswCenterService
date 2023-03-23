package com.wits.pms.mcu.custom;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.wits.pms.mcu.McuMessage;
import com.wits.pms.mcu.McuSender;

public class KswMcuSender {
    private static final String TAG = "KswMcuSender";
    private static volatile KswMcuSender kswMcuSender;
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

    public synchronized void sendMessage(KswMessage msg) {
        if (this.mcuSender != null) {
            if (KswMcuLogic.handleSendMsg(msg)) {
                this.mcuSender.send((McuMessage) msg);
                return;
            }
        }
        Log.d(TAG, "return directly");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void sendUpdateMessage(com.wits.pms.mcu.custom.KswMessage r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            com.wits.pms.mcu.McuSender r0 = r1.mcuSender     // Catch:{ all -> 0x0015 }
            if (r0 == 0) goto L_0x0013
            boolean r0 = com.wits.pms.mcu.custom.KswMcuLogic.handleSendMsg(r2)     // Catch:{ all -> 0x0015 }
            if (r0 != 0) goto L_0x000c
            goto L_0x0013
        L_0x000c:
            com.wits.pms.mcu.McuSender r0 = r1.mcuSender     // Catch:{ all -> 0x0015 }
            r0.send((com.wits.pms.mcu.McuMessage) r2)     // Catch:{ all -> 0x0015 }
            monitor-exit(r1)
            return
        L_0x0013:
            monitor-exit(r1)
            return
        L_0x0015:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.mcu.custom.KswMcuSender.sendUpdateMessage(com.wits.pms.mcu.custom.KswMessage):void");
    }

    public static void init(McuSender sender) {
        kswMcuSender = new KswMcuSender(sender);
    }

    public static KswMcuSender getSender() {
        if (kswMcuSender == null) {
            synchronized (KswMcuSender.class) {
                if (kswMcuSender == null) {
                    kswMcuSender = new KswMcuSender((McuSender) null);
                }
            }
        }
        return kswMcuSender;
    }
}
