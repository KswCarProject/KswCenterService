package com.wits.pms.mcu.custom.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wits.pms.R;
import com.wits.pms.mcu.custom.utils.UpdateHelper;
import java.io.File;

public class ForceMcuUpdate {
    public static boolean NEEDFIX;
    public static final String TAG = ForceMcuUpdate.class.getName();
    public static boolean isFixing;
    public static File mcuFile;

    public static void check(Context context, String path) {
        File file = new File(path);
        if (file.listFiles() != null && file.listFiles().length != 0) {
            for (File subFile : file.listFiles()) {
                if (subFile.getName().toLowerCase().equals("ksw_mcu.bin")) {
                    Log.i(TAG, "file checked: " + subFile.getAbsolutePath());
                    mcuFile = subFile;
                    if (NEEDFIX) {
                        fix(context);
                    }
                }
            }
        }
    }

    public static void fix(Context context) {
        if (!isFixing) {
            Log.i(TAG, "fixing mcu");
            isFixing = true;
            Handler handler = new Handler(context.getMainLooper());
            ProgressBar progressBar = new ProgressBar(context, (AttributeSet) null, 16842872);
            progressBar.setPadding(20, 20, 20, 20);
            handler.post(new Runnable(progressBar) {
                private final /* synthetic */ ProgressBar f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ForceMcuUpdate.lambda$fix$0(Context.this, this.f$1);
                }
            });
        }
    }

    static /* synthetic */ void lambda$fix$0(final Context context, final ProgressBar progressBar) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom).setCancelable(false).setTitle((int) R.string.auto_update_mcu).setView((View) progressBar).create();
        alertDialog.getWindow().setType(2003);
        alertDialog.show();
        UpdateHelper.getInstance().setListener(new UpdateHelper.McuUpdateListener() {
            public void success() {
                alertDialog.setTitle((int) R.string.auto_update_mcu_success);
                alertDialog.show();
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
            UpdateHelper.getInstance().sendUpdateMessage(mcuFile);
        } catch (Exception e) {
            Log.e(TAG, "Error fix Mcu", e);
            alertDialog.dismiss();
        }
    }
}
