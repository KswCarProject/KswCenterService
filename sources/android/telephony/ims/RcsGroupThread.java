package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.aidl.IRcs;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class RcsGroupThread extends RcsThread {
    public RcsGroupThread(RcsControllerCall rcsControllerCall, int threadId) {
        super(rcsControllerCall, threadId);
    }

    @Override // android.telephony.ims.RcsThread
    public boolean isGroup() {
        return true;
    }

    public String getGroupName() throws RcsMessageStoreException {
        return (String) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$cwnjgWxIgjmTCKAe7pcICt4Voo0
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                String groupThreadName;
                groupThreadName = iRcs.getGroupThreadName(RcsGroupThread.this.mThreadId, str);
                return groupThreadName;
            }
        });
    }

    public void setGroupName(final String groupName) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$ZorE2WcUPTtLCwMm_x5CnWwa7YI
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadName(RcsGroupThread.this.mThreadId, groupName, str);
            }
        });
    }

    public Uri getGroupIcon() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$4K1iTAEPwdeTAbDd4wTsX1Jl4S4
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Uri groupThreadIcon;
                groupThreadIcon = iRcs.getGroupThreadIcon(RcsGroupThread.this.mThreadId, str);
                return groupThreadIcon;
            }
        });
    }

    public void setGroupIcon(final Uri groupIcon) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$2-3X4NWEVE7qw298P70JdcMW6oM
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadIcon(RcsGroupThread.this.mThreadId, groupIcon, str);
            }
        });
    }

    public RcsParticipant getOwner() throws RcsMessageStoreException {
        return new RcsParticipant(this.mRcsControllerCall, ((Integer) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$OMEGtapvlm86Yn7pLPBR5He4UoQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Integer valueOf;
                valueOf = Integer.valueOf(iRcs.getGroupThreadOwner(RcsGroupThread.this.mThreadId, str));
                return valueOf;
            }
        })).intValue());
    }

    public void setOwner(final RcsParticipant participant) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$9QKuv_xqJEallZ-aE2sSumu3POo
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadOwner(RcsGroupThread.this.mThreadId, participant.getId(), str);
            }
        });
    }

    public void addParticipant(final RcsParticipant participant) throws RcsMessageStoreException {
        if (participant == null) {
            return;
        }
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$HaJSnZuef49b66N8v9ayzVaOQxQ
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.addParticipantToGroupThread(RcsGroupThread.this.mThreadId, participant.getId(), str);
            }
        });
    }

    public void removeParticipant(final RcsParticipant participant) throws RcsMessageStoreException {
        if (participant == null) {
            return;
        }
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$xvETBJ_gzJJ5zvelRSNsYZBdXKw
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.removeParticipantFromGroupThread(RcsGroupThread.this.mThreadId, participant.getId(), str);
            }
        });
    }

    public Set<RcsParticipant> getParticipants() throws RcsMessageStoreException {
        final RcsParticipantQueryParams queryParameters = new RcsParticipantQueryParams.Builder().setThread(this).build();
        RcsParticipantQueryResult queryResult = new RcsParticipantQueryResult(this.mRcsControllerCall, (RcsParticipantQueryResultParcelable) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$X2eY_CkF7PfEGF8QwmaD6Cv0PhI
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                RcsParticipantQueryResultParcelable participants;
                participants = iRcs.getParticipants(RcsParticipantQueryParams.this, str);
                return participants;
            }
        }));
        List<RcsParticipant> participantList = queryResult.getParticipants();
        Set<RcsParticipant> participantSet = new LinkedHashSet<>(participantList);
        return Collections.unmodifiableSet(participantSet);
    }

    public Uri getConferenceUri() throws RcsMessageStoreException {
        return (Uri) this.mRcsControllerCall.call(new RcsControllerCall.RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$hYpkX2Z60Pf5FiSb6pvoBpmHfXA
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                Uri groupThreadConferenceUri;
                groupThreadConferenceUri = iRcs.getGroupThreadConferenceUri(RcsGroupThread.this.mThreadId, str);
                return groupThreadConferenceUri;
            }
        });
    }

    public void setConferenceUri(final Uri conferenceUri) throws RcsMessageStoreException {
        this.mRcsControllerCall.callWithNoReturn(new RcsControllerCall.RcsServiceCallWithNoReturn() { // from class: android.telephony.ims.-$$Lambda$RcsGroupThread$LhWdWS6noezEn0xijClZdbKHOas
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCallWithNoReturn
            public final void methodOnIRcs(IRcs iRcs, String str) {
                iRcs.setGroupThreadConferenceUri(RcsGroupThread.this.mThreadId, conferenceUri, str);
            }
        });
    }
}
