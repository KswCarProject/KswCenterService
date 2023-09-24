package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
public class RcsParticipant {
    private final int mId;
    private final RcsControllerCall mRcsControllerCall;

    public RcsParticipant(RcsControllerCall rcsControllerCall, int id) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = id;
    }

    public String getCanonicalAddress() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsParticipant$T35onLZnU-uRTl7zQ7ZWRFtFvx4
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String rcsParticipantCanonicalAddress;
                rcsParticipantCanonicalAddress = iRcs.getRcsParticipantCanonicalAddress(RcsParticipant.this.mId, str);
                return rcsParticipantCanonicalAddress;
            }
        });
    }

    public String getAlias() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsParticipant$MNtRFbM6h-ycH3bPEUZgB5f56zs
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String rcsParticipantAlias;
                rcsParticipantAlias = iRcs.getRcsParticipantAlias(RcsParticipant.this.mId, str);
                return rcsParticipantAlias;
            }
        });
    }

    public void setAlias(final String alias) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsParticipant$xir-e-NE3auWDac4dOx89mKtRKU
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setRcsParticipantAlias(RcsParticipant.this.mId, alias, str);
            }
        });
    }

    public String getContactId() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsParticipant$up5zUlvCkFUru1_1NfgXrzNmBic
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String rcsParticipantContactId;
                rcsParticipantContactId = iRcs.getRcsParticipantContactId(RcsParticipant.this.mId, str);
                return rcsParticipantContactId;
            }
        });
    }

    public void setContactId(final String contactId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsParticipant$HgHlMU15W2RReyvhk-UQ-432pfA
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setRcsParticipantContactId(RcsParticipant.this.mId, contactId, str);
            }
        });
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RcsParticipant) {
            RcsParticipant other = (RcsParticipant) obj;
            return this.mId == other.mId;
        }
        return false;
    }

    public int hashCode() {
        return this.mId;
    }

    public int getId() {
        return this.mId;
    }
}
