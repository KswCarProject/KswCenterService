package android.content.p001om;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.p001om.IOverlayManager;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.p007os.UserHandle;
import java.util.List;

@SystemApi
/* renamed from: android.content.om.OverlayManager */
/* loaded from: classes.dex */
public class OverlayManager {
    private final Context mContext;
    private final IOverlayManager mService;

    public OverlayManager(Context context, IOverlayManager service) {
        this.mContext = context;
        this.mService = service;
    }

    public OverlayManager(Context context) {
        this(context, IOverlayManager.Stub.asInterface(ServiceManager.getService(Context.OVERLAY_SERVICE)));
    }

    @SystemApi
    public void setEnabledExclusiveInCategory(String packageName, UserHandle user) {
        try {
            if (!this.mService.setEnabledExclusiveInCategory(packageName, user.getIdentifier())) {
                throw new IllegalStateException("setEnabledExclusiveInCategory failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setEnabled(String packageName, boolean enable, UserHandle user) {
        try {
            if (!this.mService.setEnabled(packageName, enable, user.getIdentifier())) {
                throw new IllegalStateException("setEnabled failed");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public OverlayInfo getOverlayInfo(String packageName, UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfo(packageName, userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<OverlayInfo> getOverlayInfosForTarget(String targetPackageName, UserHandle user) {
        try {
            return this.mService.getOverlayInfosForTarget(targetPackageName, user.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
