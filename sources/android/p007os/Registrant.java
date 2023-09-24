package android.p007os;

import android.annotation.UnsupportedAppUsage;
import java.lang.ref.WeakReference;

/* renamed from: android.os.Registrant */
/* loaded from: classes3.dex */
public class Registrant {
    WeakReference refH;
    Object userObj;
    int what;

    @UnsupportedAppUsage
    public Registrant(Handler h, int what, Object obj) {
        this.refH = new WeakReference(h);
        this.what = what;
        this.userObj = obj;
    }

    @UnsupportedAppUsage
    public void clear() {
        this.refH = null;
        this.userObj = null;
    }

    @UnsupportedAppUsage
    public void notifyRegistrant() {
        internalNotifyRegistrant(null, null);
    }

    @UnsupportedAppUsage
    public void notifyResult(Object result) {
        internalNotifyRegistrant(result, null);
    }

    public void notifyException(Throwable exception) {
        internalNotifyRegistrant(null, exception);
    }

    @UnsupportedAppUsage
    public void notifyRegistrant(AsyncResult ar) {
        internalNotifyRegistrant(ar.result, ar.exception);
    }

    void internalNotifyRegistrant(Object result, Throwable exception) {
        Handler h = getHandler();
        if (h == null) {
            clear();
            return;
        }
        Message msg = Message.obtain();
        msg.what = this.what;
        msg.obj = new AsyncResult(this.userObj, result, exception);
        h.sendMessage(msg);
    }

    @UnsupportedAppUsage
    public Message messageForRegistrant() {
        Handler h = getHandler();
        if (h == null) {
            clear();
            return null;
        }
        Message msg = h.obtainMessage();
        msg.what = this.what;
        msg.obj = this.userObj;
        return msg;
    }

    public Handler getHandler() {
        if (this.refH == null) {
            return null;
        }
        return (Handler) this.refH.get();
    }
}
