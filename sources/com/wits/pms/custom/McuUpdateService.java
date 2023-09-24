package com.wits.pms.custom;

import android.p007os.RemoteException;
import android.util.Log;
import com.wits.pms.ksw.IMcuUpdate;
import com.wits.pms.ksw.OnMcuUpdateProgressListener;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.File;

/* loaded from: classes2.dex */
public class McuUpdateService extends IMcuUpdate.Stub {
    @Override // com.wits.pms.ksw.IMcuUpdate
    public void mcuUpdate(String path) throws RemoteException {
        try {
            UpdateHelper.getInstance().sendUpdateMessage(new File(path));
        } catch (Exception e) {
            Log.m69e(McuUpdateService.class.getName(), "Mcu Update Error", e);
        }
    }

    @Override // com.wits.pms.ksw.IMcuUpdate
    public void setOnMcuUpdateProgressListener(final OnMcuUpdateProgressListener listener) throws RemoteException {
        UpdateHelper.getInstance().setListener(new UpdateHelper.McuUpdateListener() { // from class: com.wits.pms.custom.McuUpdateService.1
            @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
            public void success() {
                try {
                    Log.m72d("McuUpdateService", "mcuUpdate  success");
                    WitsCommand.sendCommand(1, 201, "");
                    listener.success();
                } catch (RemoteException e) {
                }
            }

            @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
            public void failed(int errorCode) {
                try {
                    listener.failed(errorCode);
                } catch (RemoteException e) {
                }
            }

            @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
            public void progress(float pg) {
                try {
                    listener.progress((int) pg);
                } catch (RemoteException e) {
                }
            }
        });
    }
}
