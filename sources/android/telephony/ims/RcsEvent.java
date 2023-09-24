package android.telephony.ims;

/* loaded from: classes4.dex */
public abstract class RcsEvent {
    private final long mTimestamp;

    abstract void persist(RcsControllerCall rcsControllerCall) throws RcsMessageStoreException;

    protected RcsEvent(long timestamp) {
        this.mTimestamp = timestamp;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }
}
