package com.wits.pms.utils;

import android.content.Context;
import android.support.p014v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import com.wits.pms.C3580R;

/* loaded from: classes2.dex */
public class DialogUtils {
    public static AlertDialog createAlert(Context context, View view, String title, String content) {
        return createAlert(context, view, title, content, C3580R.C3583style.AlertDialogCustom);
    }

    public static AlertDialog createAlert(Context context, View view, String title, String content, int style) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, style);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setMessage(content);
        }
        if (view != null) {
            builder.setView(view);
        }
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setType(2003);
        }
        return dialog;
    }
}
