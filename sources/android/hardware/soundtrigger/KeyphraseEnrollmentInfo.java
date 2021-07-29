package android.hardware.soundtrigger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import com.ibm.icu.text.PluralRules;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KeyphraseEnrollmentInfo {
    public static final String ACTION_MANAGE_VOICE_KEYPHRASES = "com.android.intent.action.MANAGE_VOICE_KEYPHRASES";
    public static final String EXTRA_VOICE_KEYPHRASE_ACTION = "com.android.intent.extra.VOICE_KEYPHRASE_ACTION";
    public static final String EXTRA_VOICE_KEYPHRASE_HINT_TEXT = "com.android.intent.extra.VOICE_KEYPHRASE_HINT_TEXT";
    public static final String EXTRA_VOICE_KEYPHRASE_LOCALE = "com.android.intent.extra.VOICE_KEYPHRASE_LOCALE";
    private static final String TAG = "KeyphraseEnrollmentInfo";
    private static final String VOICE_KEYPHRASE_META_DATA = "android.voice_enrollment";
    private final Map<KeyphraseMetadata, String> mKeyphrasePackageMap;
    private final KeyphraseMetadata[] mKeyphrases;
    private String mParseError;

    public KeyphraseEnrollmentInfo(PackageManager pm) {
        List<ResolveInfo> ris = pm.queryIntentActivities(new Intent(ACTION_MANAGE_VOICE_KEYPHRASES), 65536);
        if (ris == null || ris.isEmpty()) {
            this.mParseError = "No enrollment applications found";
            this.mKeyphrasePackageMap = Collections.emptyMap();
            this.mKeyphrases = null;
            return;
        }
        List<String> parseErrors = new LinkedList<>();
        this.mKeyphrasePackageMap = new HashMap();
        for (ResolveInfo ri : ris) {
            try {
                ApplicationInfo ai = pm.getApplicationInfo(ri.activityInfo.packageName, 128);
                if ((ai.privateFlags & 8) == 0) {
                    Slog.w(TAG, ai.packageName + "is not a privileged system app");
                } else if (!Manifest.permission.MANAGE_VOICE_KEYPHRASES.equals(ai.permission)) {
                    Slog.w(TAG, ai.packageName + " does not require MANAGE_VOICE_KEYPHRASES");
                } else {
                    KeyphraseMetadata metadata = getKeyphraseMetadataFromApplicationInfo(pm, ai, parseErrors);
                    if (metadata != null) {
                        this.mKeyphrasePackageMap.put(metadata, ai.packageName);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                String error = "error parsing voice enrollment meta-data for " + ri.activityInfo.packageName;
                parseErrors.add(error + PluralRules.KEYWORD_RULE_SEPARATOR + e);
                Slog.w(TAG, error, e);
            }
        }
        if (this.mKeyphrasePackageMap.isEmpty()) {
            parseErrors.add("No suitable enrollment application found");
            Slog.w(TAG, "No suitable enrollment application found");
            this.mKeyphrases = null;
        } else {
            this.mKeyphrases = (KeyphraseMetadata[]) this.mKeyphrasePackageMap.keySet().toArray(new KeyphraseMetadata[this.mKeyphrasePackageMap.size()]);
        }
        if (!parseErrors.isEmpty()) {
            this.mParseError = TextUtils.join((CharSequence) "\n", (Iterable) parseErrors);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007d, code lost:
        if (r0 != null) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0117, code lost:
        if (r0 == null) goto L_0x011b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.hardware.soundtrigger.KeyphraseMetadata getKeyphraseMetadataFromApplicationInfo(android.content.pm.PackageManager r11, android.content.pm.ApplicationInfo r12, java.util.List<java.lang.String> r13) {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r1 = r12.packageName
            r2 = 0
            r3 = r2
            java.lang.String r4 = "android.voice_enrollment"
            android.content.res.XmlResourceParser r4 = r12.loadXmlMetaData(r11, r4)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r0 = r4
            if (r0 != 0) goto L_0x002e
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r4.<init>()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r5 = "No android.voice_enrollment meta-data for "
            r4.append(r5)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r4.append(r1)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r4 = r4.toString()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r13.add(r4)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r5 = "KeyphraseEnrollmentInfo"
            android.util.Slog.w((java.lang.String) r5, (java.lang.String) r4)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            if (r0 == 0) goto L_0x002d
            r0.close()
        L_0x002d:
            return r2
        L_0x002e:
            android.content.res.Resources r4 = r11.getResourcesForApplication((android.content.pm.ApplicationInfo) r12)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            android.util.AttributeSet r5 = android.util.Xml.asAttributeSet(r0)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
        L_0x0036:
            int r6 = r0.next()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r7 = r6
            r8 = 1
            if (r6 == r8) goto L_0x0042
            r6 = 2
            if (r7 == r6) goto L_0x0042
            goto L_0x0036
        L_0x0042:
            java.lang.String r6 = r0.getName()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r8 = "voice-enrollment-application"
            boolean r8 = r8.equals(r6)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            if (r8 != 0) goto L_0x006f
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r8.<init>()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r9 = "Meta-data does not start with voice-enrollment-application tag for "
            r8.append(r9)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r8.append(r1)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r8 = r8.toString()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r13.add(r8)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            java.lang.String r9 = "KeyphraseEnrollmentInfo"
            android.util.Slog.w((java.lang.String) r9, (java.lang.String) r8)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            if (r0 == 0) goto L_0x006e
            r0.close()
        L_0x006e:
            return r2
        L_0x006f:
            int[] r2 = com.android.internal.R.styleable.VoiceEnrollmentApplication     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            android.content.res.TypedArray r2 = r4.obtainAttributes(r5, r2)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            android.hardware.soundtrigger.KeyphraseMetadata r8 = r10.getKeyphraseFromTypedArray(r2, r1, r13)     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            r3 = r8
            r2.recycle()     // Catch:{ XmlPullParserException -> 0x00e9, IOException -> 0x00b8, NameNotFoundException -> 0x0087 }
            if (r0 == 0) goto L_0x011b
        L_0x007f:
            r0.close()
            goto L_0x011b
        L_0x0084:
            r2 = move-exception
            goto L_0x011c
        L_0x0087:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r4.<init>()     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = "Error parsing keyphrase enrollment meta-data for "
            r4.append(r5)     // Catch:{ all -> 0x0084 }
            r4.append(r1)     // Catch:{ all -> 0x0084 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r5.<init>()     // Catch:{ all -> 0x0084 }
            r5.append(r4)     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = ": "
            r5.append(r6)     // Catch:{ all -> 0x0084 }
            r5.append(r2)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0084 }
            r13.add(r5)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = "KeyphraseEnrollmentInfo"
            android.util.Slog.w(r5, r4, r2)     // Catch:{ all -> 0x0084 }
            if (r0 == 0) goto L_0x011b
            goto L_0x007f
        L_0x00b8:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r4.<init>()     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = "Error parsing keyphrase enrollment meta-data for "
            r4.append(r5)     // Catch:{ all -> 0x0084 }
            r4.append(r1)     // Catch:{ all -> 0x0084 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r5.<init>()     // Catch:{ all -> 0x0084 }
            r5.append(r4)     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = ": "
            r5.append(r6)     // Catch:{ all -> 0x0084 }
            r5.append(r2)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0084 }
            r13.add(r5)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = "KeyphraseEnrollmentInfo"
            android.util.Slog.w(r5, r4, r2)     // Catch:{ all -> 0x0084 }
            if (r0 == 0) goto L_0x011b
            goto L_0x007f
        L_0x00e9:
            r2 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r4.<init>()     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = "Error parsing keyphrase enrollment meta-data for "
            r4.append(r5)     // Catch:{ all -> 0x0084 }
            r4.append(r1)     // Catch:{ all -> 0x0084 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r5.<init>()     // Catch:{ all -> 0x0084 }
            r5.append(r4)     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = ": "
            r5.append(r6)     // Catch:{ all -> 0x0084 }
            r5.append(r2)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0084 }
            r13.add(r5)     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = "KeyphraseEnrollmentInfo"
            android.util.Slog.w(r5, r4, r2)     // Catch:{ all -> 0x0084 }
            if (r0 == 0) goto L_0x011b
            goto L_0x007f
        L_0x011b:
            return r3
        L_0x011c:
            if (r0 == 0) goto L_0x0121
            r0.close()
        L_0x0121:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.soundtrigger.KeyphraseEnrollmentInfo.getKeyphraseMetadataFromApplicationInfo(android.content.pm.PackageManager, android.content.pm.ApplicationInfo, java.util.List):android.hardware.soundtrigger.KeyphraseMetadata");
    }

    private KeyphraseMetadata getKeyphraseFromTypedArray(TypedArray array, String packageName, List<String> parseErrors) {
        int searchKeyphraseId = array.getInt(0, -1);
        if (searchKeyphraseId <= 0) {
            String error = "No valid searchKeyphraseId specified in meta-data for " + packageName;
            parseErrors.add(error);
            Slog.w(TAG, error);
            return null;
        }
        String searchKeyphrase = array.getString(1);
        if (searchKeyphrase == null) {
            String error2 = "No valid searchKeyphrase specified in meta-data for " + packageName;
            parseErrors.add(error2);
            Slog.w(TAG, error2);
            return null;
        }
        String searchKeyphraseSupportedLocales = array.getString(2);
        if (searchKeyphraseSupportedLocales == null) {
            String error3 = "No valid searchKeyphraseSupportedLocales specified in meta-data for " + packageName;
            parseErrors.add(error3);
            Slog.w(TAG, error3);
            return null;
        }
        ArraySet<Locale> locales = new ArraySet<>();
        if (!TextUtils.isEmpty(searchKeyphraseSupportedLocales)) {
            try {
                String[] supportedLocalesDelimited = searchKeyphraseSupportedLocales.split(SmsManager.REGEX_PREFIX_DELIMITER);
                for (String forLanguageTag : supportedLocalesDelimited) {
                    locales.add(Locale.forLanguageTag(forLanguageTag));
                }
            } catch (Exception e) {
                String error4 = "Error reading searchKeyphraseSupportedLocales from meta-data for " + packageName;
                parseErrors.add(error4);
                Slog.w(TAG, error4);
                return null;
            }
        }
        int recognitionModes = array.getInt(3, -1);
        if (recognitionModes >= 0) {
            return new KeyphraseMetadata(searchKeyphraseId, searchKeyphrase, locales, recognitionModes);
        }
        String error5 = "No valid searchKeyphraseRecognitionFlags specified in meta-data for " + packageName;
        parseErrors.add(error5);
        Slog.w(TAG, error5);
        return null;
    }

    public String getParseError() {
        return this.mParseError;
    }

    public KeyphraseMetadata[] listKeyphraseMetadata() {
        return this.mKeyphrases;
    }

    public Intent getManageKeyphraseIntent(int action, String keyphrase, Locale locale) {
        if (this.mKeyphrasePackageMap == null || this.mKeyphrasePackageMap.isEmpty()) {
            Slog.w(TAG, "No enrollment application exists");
            return null;
        }
        KeyphraseMetadata keyphraseMetadata = getKeyphraseMetadata(keyphrase, locale);
        if (keyphraseMetadata != null) {
            return new Intent(ACTION_MANAGE_VOICE_KEYPHRASES).setPackage(this.mKeyphrasePackageMap.get(keyphraseMetadata)).putExtra(EXTRA_VOICE_KEYPHRASE_HINT_TEXT, keyphrase).putExtra(EXTRA_VOICE_KEYPHRASE_LOCALE, locale.toLanguageTag()).putExtra(EXTRA_VOICE_KEYPHRASE_ACTION, action);
        }
        return null;
    }

    public KeyphraseMetadata getKeyphraseMetadata(String keyphrase, Locale locale) {
        if (this.mKeyphrases != null && this.mKeyphrases.length > 0) {
            for (KeyphraseMetadata keyphraseMetadata : this.mKeyphrases) {
                if (keyphraseMetadata.supportsPhrase(keyphrase) && keyphraseMetadata.supportsLocale(locale)) {
                    return keyphraseMetadata;
                }
            }
        }
        Slog.w(TAG, "No enrollment application supports the given keyphrase/locale: '" + keyphrase + "'/" + locale);
        return null;
    }

    public String toString() {
        return "KeyphraseEnrollmentInfo [Keyphrases=" + this.mKeyphrasePackageMap.toString() + ", ParseError=" + this.mParseError + "]";
    }
}
