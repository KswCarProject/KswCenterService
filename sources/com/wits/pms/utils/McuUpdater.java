package com.wits.pms.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.ksw.IMcuUpdate;
import com.wits.pms.ksw.OnMcuUpdateProgressListener;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.mirror.ServiceManager;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.File;

public class McuUpdater {
    /* access modifiers changed from: private */
    public static final String TAG = McuUpdater.class.getSimpleName();

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
        for (File file : new File(path).listFiles()) {
            if (file.getName().toLowerCase().contains("ksw_mcu_auto.bin")) {
                prepareMcuUpdate(context, file);
                return;
            }
        }
        File file2 = new File(path + "/ksw_mcu_auto.bin");
        if (file2.exists()) {
            prepareMcuUpdate(context, file2);
            return;
        }
        if (new File(path + "/KSW_MCU_AUTO.bin").exists()) {
            prepareMcuUpdate(context, file2);
        }
    }

    private static void prepareMcuUpdate(Context context, File file) {
        Handler handler = new Handler(context.getMainLooper());
        ProgressBar progressBar = new ProgressBar(context, (AttributeSet) null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        handler.post(new Runnable(progressBar, file) {
            private final /* synthetic */ ProgressBar f$1;
            private final /* synthetic */ File f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                McuUpdater.lambda$prepareMcuUpdate$0(Context.this, this.f$1, this.f$2);
            }
        });
    }

    static /* synthetic */ void lambda$prepareMcuUpdate$0(final Context context, final ProgressBar progressBar, File file) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(false).setTitle((int) R.string.update_mcu).setView((View) progressBar).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
        UpdateHelper.getInstance().setListener(new UpdateHelper.McuUpdateListener() {
            public void success() {
                Log.d(McuUpdater.TAG, "success , to SHUTDOWN ");
                WitsCommand.sendCommand(1, 201, "");
            }

            public void failed(int errorCode) {
                alertDialog.dismiss();
                new Handler(Looper.getMainLooper()).post(new Runnable(errorCode) {
                    private final /* synthetic */ int f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        Toast.makeText(Context.this, (CharSequence) "Mcu Error Code:" + this.f$1, 0).show();
                    }
                });
            }

            public void progress(float pg) {
                progressBar.setProgress((int) pg);
            }
        });
        try {
            UpdateHelper.getInstance().sendUpdateMessage(file);
        } catch (Exception e) {
            Log.e(TAG, "Error fix Mcu", e);
            alertDialog.dismiss();
        }
    }
}
