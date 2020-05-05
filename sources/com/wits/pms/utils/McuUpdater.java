package com.wits.pms.utils;

import android.content.Context;
import android.os.RemoteException;
import com.wits.pms.ksw.IMcuUpdate;
import com.wits.pms.ksw.OnMcuUpdateProgressListener;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import java.io.File;

public class McuUpdater {
    private static IMcuUpdate getMcuUpdater() {
        return IMcuUpdate.Stub.asInterface(ServiceManager.getService("mcu_update"));
    }

    public static void registerMcuUpdateListener(OnMcuUpdateProgressListener listener) throws RemoteException {
        getMcuUpdater().setOnMcuUpdateProgressListener(listener);
    }

    public static void mcuUpdate(String path) throws RemoteException {
        getMcuUpdater().mcuUpdate(path);
    }

    public static void check(Context context, String path) {
        File file = new File(path + "/ksw_mcu_auto.bin");
        if (file.exists()) {
            UpdateHelper.getInstance().sendUpdateMessage(file);
        }
    }
}
