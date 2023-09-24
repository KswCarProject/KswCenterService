package android.view.textclassifier.intent;

import android.content.Context;
import android.content.Intent;
import com.android.internal.C3132R;
import com.google.android.textclassifier.AnnotatorModel;
import java.time.Instant;
import java.util.List;

/* loaded from: classes4.dex */
public interface ClassificationIntentFactory {
    List<LabeledIntent> create(Context context, String str, boolean z, Instant instant, AnnotatorModel.ClassificationResult classificationResult);

    static void insertTranslateAction(List<LabeledIntent> actions, Context context, String text) {
        actions.add(new LabeledIntent(context.getString(C3132R.string.translate), null, context.getString(C3132R.string.translate_desc), null, new Intent(Intent.ACTION_TRANSLATE).putExtra(Intent.EXTRA_TEXT, text), text.hashCode()));
    }
}
