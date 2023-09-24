package android.nfc.cardemulation;

import android.app.Service;
import android.content.Intent;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Message;
import android.p007os.Messenger;
import android.p007os.RemoteException;
import android.util.Log;

/* loaded from: classes3.dex */
public abstract class HostNfcFService extends Service {
    public static final int DEACTIVATION_LINK_LOSS = 0;
    public static final String KEY_DATA = "data";
    public static final String KEY_MESSENGER = "messenger";
    public static final int MSG_COMMAND_PACKET = 0;
    public static final int MSG_DEACTIVATED = 2;
    public static final int MSG_RESPONSE_PACKET = 1;
    public static final String SERVICE_INTERFACE = "android.nfc.cardemulation.action.HOST_NFCF_SERVICE";
    public static final String SERVICE_META_DATA = "android.nfc.cardemulation.host_nfcf_service";
    static final String TAG = "NfcFService";
    Messenger mNfcService = null;
    final Messenger mMessenger = new Messenger(new MsgHandler());

    public abstract void onDeactivated(int i);

    public abstract byte[] processNfcFPacket(byte[] bArr, Bundle bundle);

    /* loaded from: classes3.dex */
    final class MsgHandler extends Handler {
        MsgHandler() {
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle dataBundle = msg.getData();
                    if (dataBundle == null) {
                        return;
                    }
                    if (HostNfcFService.this.mNfcService == null) {
                        HostNfcFService.this.mNfcService = msg.replyTo;
                    }
                    byte[] packet = dataBundle.getByteArray("data");
                    if (packet != null) {
                        byte[] responsePacket = HostNfcFService.this.processNfcFPacket(packet, null);
                        if (responsePacket != null) {
                            if (HostNfcFService.this.mNfcService == null) {
                                Log.m70e(HostNfcFService.TAG, "Response not sent; service was deactivated.");
                                return;
                            }
                            Message responseMsg = Message.obtain((Handler) null, 1);
                            Bundle responseBundle = new Bundle();
                            responseBundle.putByteArray("data", responsePacket);
                            responseMsg.setData(responseBundle);
                            responseMsg.replyTo = HostNfcFService.this.mMessenger;
                            try {
                                HostNfcFService.this.mNfcService.send(responseMsg);
                                return;
                            } catch (RemoteException e) {
                                Log.m70e("TAG", "Response not sent; RemoteException calling into NfcService.");
                                return;
                            }
                        }
                        return;
                    }
                    Log.m70e(HostNfcFService.TAG, "Received MSG_COMMAND_PACKET without data.");
                    return;
                case 1:
                    if (HostNfcFService.this.mNfcService == null) {
                        Log.m70e(HostNfcFService.TAG, "Response not sent; service was deactivated.");
                        return;
                    }
                    try {
                        msg.replyTo = HostNfcFService.this.mMessenger;
                        HostNfcFService.this.mNfcService.send(msg);
                        return;
                    } catch (RemoteException e2) {
                        Log.m70e(HostNfcFService.TAG, "RemoteException calling into NfcService.");
                        return;
                    }
                case 2:
                    HostNfcFService.this.mNfcService = null;
                    HostNfcFService.this.onDeactivated(msg.arg1);
                    return;
                default:
                    super.handleMessage(msg);
                    return;
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }

    public final void sendResponsePacket(byte[] responsePacket) {
        Message responseMsg = Message.obtain((Handler) null, 1);
        Bundle dataBundle = new Bundle();
        dataBundle.putByteArray("data", responsePacket);
        responseMsg.setData(dataBundle);
        try {
            this.mMessenger.send(responseMsg);
        } catch (RemoteException e) {
            Log.m70e("TAG", "Local messenger has died.");
        }
    }
}
