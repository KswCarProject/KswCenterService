package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

public class RcsParticipant {
    private final int mId;
    private final RcsControllerCall mRcsControllerCall;

    public RcsParticipant(RcsControllerCall rcsControllerCall, int id) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mId = id;
    }

    public String getCanonicalAddress() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getRcsParticipantCanonicalAddress(RcsParticipant.this.mId, str);
            }
        });
    }

    public String getAlias() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getRcsParticipantAlias(RcsParticipant.this.mId, str);
            }
        });
    }

    public void setAlias(String alias) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(alias) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setRcsParticipantAlias(RcsParticipant.this.mId, this.f$1, str);
            }
        });
    }

    public String getContactId() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getRcsParticipantContactId(RcsParticipant.this.mId, str);
            }
        });
    }

    public void setContactId(String contactId) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(contactId) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setRcsParticipantContactId(RcsParticipant.this.mId, this.f$1, str);
            }
        });
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RcsParticipant)) {
            return false;
        }
        if (this.mId == ((RcsParticipant) obj).mId) {
            return true;
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
