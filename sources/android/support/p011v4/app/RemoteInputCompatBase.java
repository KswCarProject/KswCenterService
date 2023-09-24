package android.support.p011v4.app;

import android.p007os.Bundle;
import java.util.Set;

@Deprecated
/* renamed from: android.support.v4.app.RemoteInputCompatBase */
/* loaded from: classes3.dex */
class RemoteInputCompatBase {

    /* renamed from: android.support.v4.app.RemoteInputCompatBase$RemoteInput */
    /* loaded from: classes3.dex */
    public static abstract class RemoteInput {

        /* renamed from: android.support.v4.app.RemoteInputCompatBase$RemoteInput$Factory */
        /* loaded from: classes3.dex */
        public interface Factory {
            RemoteInput build(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set);

            RemoteInput[] newArray(int i);
        }

        protected abstract boolean getAllowFreeFormInput();

        protected abstract Set<String> getAllowedDataTypes();

        protected abstract CharSequence[] getChoices();

        protected abstract Bundle getExtras();

        protected abstract CharSequence getLabel();

        protected abstract String getResultKey();
    }

    RemoteInputCompatBase() {
    }
}
