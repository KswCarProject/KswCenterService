package com.android.internal.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.p007os.Bundle;
import android.p007os.UserHandle;
import android.p007os.UserManager;
import android.util.Log;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public class UnlaunchableAppActivity extends Activity implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener {
    private static final String EXTRA_UNLAUNCHABLE_REASON = "unlaunchable_reason";
    private static final String TAG = "UnlaunchableAppActivity";
    private static final int UNLAUNCHABLE_REASON_QUIET_MODE = 1;
    private int mReason;
    private IntentSender mTarget;
    private int mUserId;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        Intent intent = getIntent();
        this.mReason = intent.getIntExtra(EXTRA_UNLAUNCHABLE_REASON, -1);
        this.mUserId = intent.getIntExtra(Intent.EXTRA_USER_HANDLE, -10000);
        this.mTarget = (IntentSender) intent.getParcelableExtra(Intent.EXTRA_INTENT);
        if (this.mUserId != -10000) {
            if (this.mReason == 1) {
                String dialogTitle = getResources().getString(C3132R.string.work_mode_off_title);
                String dialogMessage = getResources().getString(C3132R.string.work_mode_off_message);
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(dialogTitle).setMessage(dialogMessage).setOnDismissListener(this);
                if (this.mReason == 1) {
                    builder.setPositiveButton(C3132R.string.work_mode_turn_on, this).setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
                } else {
                    builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
                }
                builder.show();
                return;
            }
            Log.wtf(TAG, "Invalid unlaunchable type: " + this.mReason);
            finish();
            return;
        }
        Log.wtf(TAG, "Invalid user id: " + this.mUserId + ". Stopping.");
        finish();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        if (this.mReason == 1 && which == -1) {
            UserManager.get(this).requestQuietModeEnabled(false, UserHandle.m110of(this.mUserId), this.mTarget);
        }
    }

    private static final Intent createBaseIntent() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("android", UnlaunchableAppActivity.class.getName()));
        intent.setFlags(276824064);
        return intent;
    }

    public static Intent createInQuietModeDialogIntent(int userId) {
        Intent intent = createBaseIntent();
        intent.putExtra(EXTRA_UNLAUNCHABLE_REASON, 1);
        intent.putExtra(Intent.EXTRA_USER_HANDLE, userId);
        return intent;
    }

    public static Intent createInQuietModeDialogIntent(int userId, IntentSender target) {
        Intent intent = createInQuietModeDialogIntent(userId);
        intent.putExtra(Intent.EXTRA_INTENT, target);
        return intent;
    }
}
