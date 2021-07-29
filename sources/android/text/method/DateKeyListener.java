package android.text.method;

import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ArrayUtils;
import com.ibm.icu.text.DateFormat;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

public class DateKeyListener extends NumberKeyListener {
    @Deprecated
    public static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', '-', '.'};
    private static final String[] SKELETONS = {DateFormat.YEAR_NUM_MONTH_DAY, DateFormat.YEAR_NUM_MONTH, DateFormat.NUM_MONTH_DAY};
    private static final String SYMBOLS_TO_IGNORE = "yMLd";
    @GuardedBy({"sLock"})
    private static final HashMap<Locale, DateKeyListener> sInstanceCache = new HashMap<>();
    private static final Object sLock = new Object();
    private final char[] mCharacters;
    private final boolean mNeedsAdvancedInput;

    public int getInputType() {
        if (this.mNeedsAdvancedInput) {
            return 1;
        }
        return 20;
    }

    /* access modifiers changed from: protected */
    public char[] getAcceptedChars() {
        return this.mCharacters;
    }

    @Deprecated
    public DateKeyListener() {
        this((Locale) null);
    }

    public DateKeyListener(Locale locale) {
        LinkedHashSet<Character> chars = new LinkedHashSet<>();
        if (NumberKeyListener.addDigits(chars, locale) && NumberKeyListener.addFormatCharsFromSkeletons(chars, locale, SKELETONS, SYMBOLS_TO_IGNORE)) {
            this.mCharacters = NumberKeyListener.collectionToArray(chars);
            this.mNeedsAdvancedInput = true ^ ArrayUtils.containsAll(CHARACTERS, this.mCharacters);
            return;
        }
        this.mCharacters = CHARACTERS;
        this.mNeedsAdvancedInput = false;
    }

    @Deprecated
    public static DateKeyListener getInstance() {
        return getInstance((Locale) null);
    }

    public static DateKeyListener getInstance(Locale locale) {
        DateKeyListener instance;
        synchronized (sLock) {
            instance = sInstanceCache.get(locale);
            if (instance == null) {
                instance = new DateKeyListener(locale);
                sInstanceCache.put(locale, instance);
            }
        }
        return instance;
    }
}
