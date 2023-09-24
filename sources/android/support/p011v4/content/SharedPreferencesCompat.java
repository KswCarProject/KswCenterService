package android.support.p011v4.content;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/* renamed from: android.support.v4.content.SharedPreferencesCompat */
/* loaded from: classes3.dex */
public final class SharedPreferencesCompat {

    /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat */
    /* loaded from: classes3.dex */
    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        /* renamed from: android.support.v4.content.SharedPreferencesCompat$EditorCompat$Helper */
        /* loaded from: classes3.dex */
        private static class Helper {
            Helper() {
            }

            public void apply(@NonNull SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError e) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
        }

        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        public void apply(@NonNull SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
