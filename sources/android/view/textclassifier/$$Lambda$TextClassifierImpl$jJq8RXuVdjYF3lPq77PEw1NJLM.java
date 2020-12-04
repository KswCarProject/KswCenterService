package android.view.textclassifier;

import com.google.android.textclassifier.AnnotatorModel;
import java.util.function.Function;

/* renamed from: android.view.textclassifier.-$$Lambda$TextClassifierImpl$jJq8RXuVdjYF3lPq-77PEw1NJLM  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$TextClassifierImpl$jJq8RXuVdjYF3lPq77PEw1NJLM implements Function {
    public static final /* synthetic */ $$Lambda$TextClassifierImpl$jJq8RXuVdjYF3lPq77PEw1NJLM INSTANCE = new $$Lambda$TextClassifierImpl$jJq8RXuVdjYF3lPq77PEw1NJLM();

    private /* synthetic */ $$Lambda$TextClassifierImpl$jJq8RXuVdjYF3lPq77PEw1NJLM() {
    }

    public final Object apply(Object obj) {
        return Integer.valueOf(AnnotatorModel.getVersion(((Integer) obj).intValue()));
    }
}
