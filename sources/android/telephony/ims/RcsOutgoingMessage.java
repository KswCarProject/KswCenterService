package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RcsOutgoingMessage extends RcsMessage {
    RcsOutgoingMessage(RcsControllerCall rcsControllerCall, int id) {
        super(rcsControllerCall, id);
    }

    public List<RcsOutgoingMessageDelivery> getOutgoingDeliveries() throws RcsMessageStoreException {
        List<RcsOutgoingMessageDelivery> messageDeliveries = new ArrayList<>();
        int[] deliveryParticipants = (int[]) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsOutgoingMessage$uP-7yJmMalJRjXgq_qS_YvAUKuo
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                int[] messageRecipients;
                messageRecipients = iRcs.getMessageRecipients(RcsOutgoingMessage.this.mId, str);
                return messageRecipients;
            }
        });
        if (deliveryParticipants != null) {
            for (int i : deliveryParticipants) {
                Integer deliveryParticipant = Integer.valueOf(i);
                messageDeliveries.add(new RcsOutgoingMessageDelivery(this.mRcsControllerCall, deliveryParticipant.intValue(), this.mId));
            }
        }
        return messageDeliveries;
    }

    @Override // android.telephony.ims.RcsMessage
    public boolean isIncoming() {
        return false;
    }
}
