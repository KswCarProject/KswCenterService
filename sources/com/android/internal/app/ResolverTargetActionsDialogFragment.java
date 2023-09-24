package com.android.internal.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.p007os.Bundle;
import android.provider.Settings;
import com.android.internal.C3132R;

/* loaded from: classes4.dex */
public class ResolverTargetActionsDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private static final int APP_INFO_INDEX = 0;
    private static final String NAME_KEY = "componentName";
    private static final String TITLE_KEY = "title";

    public ResolverTargetActionsDialogFragment() {
    }

    public ResolverTargetActionsDialogFragment(CharSequence title, ComponentName name) {
        Bundle args = new Bundle();
        args.putCharSequence("title", title);
        args.putParcelable(NAME_KEY, name);
        setArguments(args);
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        return new AlertDialog.Builder(getContext()).setCancelable(true).setItems(C3132R.array.resolver_target_actions, this).setTitle(args.getCharSequence("title")).create();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        Bundle args = getArguments();
        ComponentName name = (ComponentName) args.getParcelable(NAME_KEY);
        if (which == 0) {
            Intent in = new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", name.getPackageName(), null)).addFlags(524288);
            startActivity(in);
        }
        dismiss();
    }
}
