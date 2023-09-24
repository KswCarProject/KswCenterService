package android.accounts;

import android.app.Activity;
import android.p007os.Bundle;
import android.view.View;
import com.android.internal.C3132R;

/* loaded from: classes.dex */
public class CantAddAccountActivity extends Activity {
    public static final String EXTRA_ERROR_CODE = "android.accounts.extra.ERROR_CODE";

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C3132R.layout.app_not_authorized);
    }

    public void onCancelButtonClicked(View view) {
        onBackPressed();
    }
}
