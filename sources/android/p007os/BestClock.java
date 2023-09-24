package android.p007os;

import android.util.Log;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Arrays;

/* renamed from: android.os.BestClock */
/* loaded from: classes3.dex */
public class BestClock extends SimpleClock {
    private static final String TAG = "BestClock";
    private final Clock[] clocks;

    public BestClock(ZoneId zone, Clock... clocks) {
        super(zone);
        this.clocks = clocks;
    }

    @Override // android.p007os.SimpleClock, java.time.Clock
    public long millis() {
        Clock[] clockArr;
        for (Clock clock : this.clocks) {
            try {
                return clock.millis();
            } catch (DateTimeException e) {
                Log.m64w(TAG, e.toString());
            }
        }
        throw new DateTimeException("No clocks in " + Arrays.toString(this.clocks) + " were able to provide time");
    }
}
