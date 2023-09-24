package android.view.contentcapture;

import android.view.autofill.AutofillId;
import android.view.contentcapture.ViewNode;

/* loaded from: classes4.dex */
final class ChildContentCaptureSession extends ContentCaptureSession {
    private final ContentCaptureSession mParent;

    protected ChildContentCaptureSession(ContentCaptureSession parent, ContentCaptureContext clientContext) {
        super(clientContext);
        this.mParent = parent;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    MainContentCaptureSession getMainCaptureSession() {
        if (this.mParent instanceof MainContentCaptureSession) {
            return (MainContentCaptureSession) this.mParent;
        }
        return this.mParent.getMainCaptureSession();
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    ContentCaptureSession newChild(ContentCaptureContext clientContext) {
        ContentCaptureSession child = new ChildContentCaptureSession(this, clientContext);
        getMainCaptureSession().notifyChildSessionStarted(this.mId, child.mId, clientContext);
        return child;
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void flush(int reason) {
        this.mParent.flush(reason);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void updateContentCaptureContext(ContentCaptureContext context) {
        getMainCaptureSession().notifyContextUpdated(this.mId, context);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void onDestroy() {
        getMainCaptureSession().notifyChildSessionFinished(this.mParent.mId, this.mId);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewAppeared(ViewNode.ViewStructureImpl node) {
        getMainCaptureSession().notifyViewAppeared(this.mId, node);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewDisappeared(AutofillId id) {
        getMainCaptureSession().notifyViewDisappeared(this.mId, id);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    void internalNotifyViewTextChanged(AutofillId id, CharSequence text) {
        getMainCaptureSession().notifyViewTextChanged(this.mId, id, text);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    public void internalNotifyViewTreeEvent(boolean started) {
        getMainCaptureSession().notifyViewTreeEvent(this.mId, started);
    }

    @Override // android.view.contentcapture.ContentCaptureSession
    boolean isContentCaptureEnabled() {
        return getMainCaptureSession().isContentCaptureEnabled();
    }
}
