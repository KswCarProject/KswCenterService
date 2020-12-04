package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.util.ArrayList;
import java.util.List;

public class RcsOutgoingMessage extends RcsMessage {
    RcsOutgoingMessage(RcsControllerCall rcsControllerCall, int id) {
        super(rcsControllerCall, id);
    }

    public List<RcsOutgoingMessageDelivery> getOutgoingDeliveries() throws RcsMessageStoreException {
        List<RcsOutgoingMessageDelivery> messageDeliveries = new ArrayList<>();
        int[] deliveryParticipants = (int[]) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getMessageRecipients(RcsOutgoingMessage.this.mId, str);
            }
        });
        if (deliveryParticipants != null) {
            for (int valueOf : deliveryParticipants) {
                messageDeliveries.add(new RcsOutgoingMessageDelivery(this.mRcsControllerCall, Integer.valueOf(valueOf).intValue(), this.mId));
            }
        }
        return messageDeliveries;
    }

    public boolean isIncoming() {
        return false;
    }
}
