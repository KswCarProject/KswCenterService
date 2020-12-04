package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class RcsGroupThread extends RcsThread {
    public RcsGroupThread(RcsControllerCall rcsControllerCall, int threadId) {
        super(rcsControllerCall, threadId);
    }

    public boolean isGroup() {
        return true;
    }

    public String getGroupName() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getGroupThreadName(RcsGroupThread.this.mThreadId, str);
            }
        });
    }

    public void setGroupName(String groupName) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(groupName) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadName(RcsGroupThread.this.mThreadId, this.f$1, str);
            }
        });
    }

    public Uri getGroupIcon() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getGroupThreadIcon(RcsGroupThread.this.mThreadId, str);
            }
        });
    }

    public void setGroupIcon(Uri groupIcon) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(groupIcon) {
            private final /* synthetic */ Uri f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadIcon(RcsGroupThread.this.mThreadId, this.f$1, str);
            }
        });
    }

    public RcsParticipant getOwner() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return Integer.valueOf(iRcs.getGroupThreadOwner(RcsGroupThread.this.mThreadId, str));
            }
        })).intValue());
    }

    public void setOwner(RcsParticipant participant) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(participant) {
            private final /* synthetic */ RcsParticipant f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadOwner(RcsGroupThread.this.mThreadId, this.f$1.getId(), str);
            }
        });
    }

    public void addParticipant(RcsParticipant participant) throws RcsMessageStoreException {
        if (participant != null) {
            this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(participant) {
                private final /* synthetic */ RcsParticipant f$1;

                {
                    this.f$1 = r2;
                }

                public final void methodOnIRcs(IRcs iRcs, String str) {
                    iRcs.addParticipantToGroupThread(RcsGroupThread.this.mThreadId, this.f$1.getId(), str);
                }
            });
        }
    }

    public void removeParticipant(RcsParticipant participant) throws RcsMessageStoreException {
        if (participant != null) {
            this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(participant) {
                private final /* synthetic */ RcsParticipant f$1;

                {
                    this.f$1 = r2;
                }

                public final void methodOnIRcs(IRcs iRcs, String str) {
                    iRcs.removeParticipantFromGroupThread(RcsGroupThread.this.mThreadId, this.f$1.getId(), str);
                }
            });
        }
    }

    public Set<RcsParticipant> getParticipants() throws RcsMessageStoreException {
        return Collections.unmodifiableSet(new LinkedHashSet<>(new RcsParticipantQueryResult(this.mRcsControllerCall, (RcsParticipantQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getParticipants(RcsParticipantQueryParams.this, str);
            }
        })).getParticipants()));
    }

    public Uri getConferenceUri() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() {
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return iRcs.getGroupThreadConferenceUri(RcsGroupThread.this.mThreadId, str);
            }
        });
    }

    public void setConferenceUri(Uri conferenceUri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn(conferenceUri) {
            private final /* synthetic */ Uri f$1;

            {
                this.f$1 = r2;
            }

            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadConferenceUri(RcsGroupThread.this.mThreadId, this.f$1, str);
            }
        });
    }
}
