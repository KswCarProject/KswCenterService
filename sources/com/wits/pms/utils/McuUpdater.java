package com.wits.pms.utils;

import android.content.Context;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.RemoteException;
import android.support.p014v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.ksw.IMcuUpdate;
import com.wits.pms.ksw.OnMcuUpdateProgressListener;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.mirror.ServiceManager;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.File;

/* loaded from: classes2.dex */
public class McuUpdater {
    private static final String TAG = McuUpdater.class.getSimpleName();

    private static IMcuUpdate getMcuUpdater() {
        IBinder mcu_update = ServiceManager.getService("mcu_update");
        return IMcuUpdate.Stub.asInterface(mcu_update);
    }

    public static void registerMcuUpdateListener(OnMcuUpdateProgressListener listener) throws RemoteException {
        getMcuUpdater().setOnMcuUpdateProgressListener(listener);
    }

    public static void mcuUpdate(String path) throws RemoteException {
        getMcuUpdater().mcuUpdate(path);
    }

    public static void check(Context context, String path) {
        File[] listFiles;
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
        File ufile = new File(path + "/KSW_MCU_AUTO.bin");
        if (ufile.exists()) {
            prepareMcuUpdate(context, file2);
        }
    }

    private static void prepareMcuUpdate(final Context context, final File file) {
        Handler handler = new Handler(context.getMainLooper());
        final ProgressBar progressBar = new ProgressBar(context, null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        handler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$McuUpdater$N-Wxvn_MI58rNLp6ECokxU4s7G4
            @Override // java.lang.Runnable
            public final void run() {
                McuUpdater.lambda$prepareMcuUpdate$0(Context.this, progressBar, file);
            }
        });
    }

    static /* synthetic */ void lambda$prepareMcuUpdate$0(Context context, ProgressBar progressBar, File file) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(false).setTitle(C3580R.string.update_mcu).setView(progressBar).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
        UpdateHelper.getInstance().setListener(new C36781(alertDialog, context, progressBar));
        try {
            UpdateHelper.getInstance().sendUpdateMessage(file);
        } catch (Exception e) {
            Log.m69e(TAG, "Error fix Mcu", e);
            alertDialog.dismiss();
        }
    }

    /* renamed from: com.wits.pms.utils.McuUpdater$1 */
    /* loaded from: classes2.dex */
    static class C36781 implements UpdateHelper.McuUpdateListener {
        final /* synthetic */ AlertDialog val$alertDialog;
        final /* synthetic */ Context val$context;
        final /* synthetic */ ProgressBar val$progressBar;

        C36781(AlertDialog alertDialog, Context context, ProgressBar progressBar) {
            this.val$alertDialog = alertDialog;
            this.val$context = context;
            this.val$progressBar = progressBar;
        }

        @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
        public void success() {
            Log.m72d(McuUpdater.TAG, "success , to SHUTDOWN ");
            WitsCommand.sendCommand(1, 201, "");
        }

        @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
        public void failed(final int errorCode) {
            this.val$alertDialog.dismiss();
            Handler handler = new Handler(Looper.getMainLooper());
            final Context context = this.val$context;
            handler.post(new Runnable() { // from class: com.wits.pms.utils.-$$Lambda$McuUpdater$1$3eOhtC49zLiUZswCWXtqoFy9LT4
                @Override // java.lang.Runnable
                public final void run() {
                    Toast.makeText(Context.this, "Mcu Error Code:" + errorCode, 0).show();
                }
            });
        }

        @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
        public void progress(float pg) {
            this.val$progressBar.setProgress((int) pg);
        }
    }
}
