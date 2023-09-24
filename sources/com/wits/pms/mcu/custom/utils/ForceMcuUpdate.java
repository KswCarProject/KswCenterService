package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.p007os.Handler;
import android.p007os.Looper;
import android.support.p014v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.C3580R;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import com.wits.pms.statuscontrol.WitsCommand;
import java.io.File;

/* loaded from: classes2.dex */
public class ForceMcuUpdate {
    public static boolean NEEDFIX;
    public static final String TAG = ForceMcuUpdate.class.getName();
    public static boolean isFixing;
    public static File mcuFile;

    public static void check(Context context, String path) {
        File[] listFiles;
        File file = new File(path);
        if (file.listFiles() == null || file.listFiles().length == 0) {
            return;
        }
        for (File subFile : file.listFiles()) {
            if (subFile.getName().toLowerCase().equals("ksw_mcu.bin")) {
                Log.m68i(TAG, "file checked: " + subFile.getAbsolutePath());
                mcuFile = subFile;
                if (NEEDFIX) {
                    fix(context);
                }
            }
        }
    }

    public static void fix(final Context context) {
        if (isFixing) {
            return;
        }
        Log.m68i(TAG, "fixing mcu");
        isFixing = true;
        Handler handler = new Handler(context.getMainLooper());
        final ProgressBar progressBar = new ProgressBar(context, null, 16842872);
        progressBar.setPadding(20, 20, 20, 20);
        handler.post(new Runnable() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ForceMcuUpdate$ZAmeeqtq5NBnP7TJ-E1vJSyoi9I
            @Override // java.lang.Runnable
            public final void run() {
                ForceMcuUpdate.lambda$fix$0(Context.this, progressBar);
            }
        });
    }

    static /* synthetic */ void lambda$fix$0(Context context, ProgressBar progressBar) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, C3580R.C3583style.AlertDialogCustom).setCancelable(false).setTitle(C3580R.string.auto_update_mcu).setView(progressBar).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
        UpdateHelper.getInstance().setListener(new C36521(alertDialog, context, progressBar));
        try {
            UpdateHelper.getInstance().sendUpdateMessage(mcuFile);
        } catch (Exception e) {
            Log.m69e(TAG, "Error fix Mcu", e);
            alertDialog.dismiss();
        }
    }

    /* renamed from: com.wits.pms.mcu.custom.utils.ForceMcuUpdate$1 */
    /* loaded from: classes2.dex */
    static class C36521 implements UpdateHelper.McuUpdateListener {
        final /* synthetic */ AlertDialog val$alertDialog;
        final /* synthetic */ Context val$context;
        final /* synthetic */ ProgressBar val$progressBar;

        C36521(AlertDialog alertDialog, Context context, ProgressBar progressBar) {
            this.val$alertDialog = alertDialog;
            this.val$context = context;
            this.val$progressBar = progressBar;
        }

        @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
        public void success() {
            WitsCommand.sendCommand(1, 201, "");
        }

        @Override // com.wits.pms.mcu.custom.utils.UpdateHelper.McuUpdateListener
        public void failed(final int errorCode) {
            this.val$alertDialog.dismiss();
            Handler handler = new Handler(Looper.getMainLooper());
            final Context context = this.val$context;
            handler.post(new Runnable() { // from class: com.wits.pms.mcu.custom.utils.-$$Lambda$ForceMcuUpdate$1$iJAYtZpWQJzXpF_to9EQsAZr0Ec
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
