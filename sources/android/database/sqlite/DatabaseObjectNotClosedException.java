package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;

/* loaded from: classes.dex */
public class DatabaseObjectNotClosedException extends RuntimeException {

    /* renamed from: s */
    private static final String f31s = "Application did not close the cursor or database object that was opened here";

    @UnsupportedAppUsage
    public DatabaseObjectNotClosedException() {
        super(f31s);
    }
}
