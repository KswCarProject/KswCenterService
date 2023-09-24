package android.hardware.soundtrigger;

import android.util.ArraySet;
import java.util.Locale;

/* loaded from: classes.dex */
public class KeyphraseMetadata {

    /* renamed from: id */
    public final int f104id;
    public final String keyphrase;
    public final int recognitionModeFlags;
    public final ArraySet<Locale> supportedLocales;

    public KeyphraseMetadata(int id, String keyphrase, ArraySet<Locale> supportedLocales, int recognitionModeFlags) {
        this.f104id = id;
        this.keyphrase = keyphrase;
        this.supportedLocales = supportedLocales;
        this.recognitionModeFlags = recognitionModeFlags;
    }

    public String toString() {
        return "id=" + this.f104id + ", keyphrase=" + this.keyphrase + ", supported-locales=" + this.supportedLocales + ", recognition-modes=" + this.recognitionModeFlags;
    }

    public boolean supportsPhrase(String phrase) {
        return this.keyphrase.isEmpty() || this.keyphrase.equalsIgnoreCase(phrase);
    }

    public boolean supportsLocale(Locale locale) {
        return this.supportedLocales.isEmpty() || this.supportedLocales.contains(locale);
    }
}
