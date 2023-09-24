package com.android.internal.p016os;

import android.p007os.Process;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: com.android.internal.os.ProcTimeInStateReader */
/* loaded from: classes4.dex */
public class ProcTimeInStateReader {
    private static final String TAG = "ProcTimeInStateReader";
    private long[] mFrequenciesKhz;
    private int[] mTimeInStateTimeFormat;
    private static final List<Integer> TIME_IN_STATE_LINE_FREQUENCY_FORMAT = Arrays.asList(8224, 10);
    private static final List<Integer> TIME_IN_STATE_LINE_TIME_FORMAT = Arrays.asList(32, 8202);
    private static final List<Integer> TIME_IN_STATE_HEADER_LINE_FORMAT = Collections.singletonList(10);

    public ProcTimeInStateReader(Path initialTimeInStateFile) throws IOException {
        initializeTimeInStateFormat(initialTimeInStateFile);
    }

    public long[] getUsageTimesMillis(Path timeInStatePath) {
        long[] readLongs = new long[this.mFrequenciesKhz.length];
        boolean readSuccess = Process.readProcFile(timeInStatePath.toString(), this.mTimeInStateTimeFormat, null, readLongs, null);
        if (!readSuccess) {
            return null;
        }
        for (int i = 0; i < readLongs.length; i++) {
            readLongs[i] = readLongs[i] * 10;
        }
        return readLongs;
    }

    public long[] getFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    private void initializeTimeInStateFormat(Path timeInStatePath) throws IOException {
        byte[] timeInStateBytes = Files.readAllBytes(timeInStatePath);
        ArrayList<Integer> timeInStateFrequencyFormat = new ArrayList<>();
        ArrayList<Integer> timeInStateTimeFormat = new ArrayList<>();
        int i = 0;
        int numFrequencies = 0;
        while (true) {
            int i2 = i;
            int i3 = timeInStateBytes.length;
            if (i2 >= i3) {
                break;
            }
            if (!Character.isDigit(timeInStateBytes[i2])) {
                timeInStateFrequencyFormat.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
                timeInStateTimeFormat.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
            } else {
                timeInStateFrequencyFormat.addAll(TIME_IN_STATE_LINE_FREQUENCY_FORMAT);
                timeInStateTimeFormat.addAll(TIME_IN_STATE_LINE_TIME_FORMAT);
                numFrequencies++;
            }
            while (i2 < timeInStateBytes.length && timeInStateBytes[i2] != 10) {
                i2++;
            }
            i = i2 + 1;
        }
        if (numFrequencies == 0) {
            throw new IOException("Empty time_in_state file");
        }
        long[] readLongs = new long[numFrequencies];
        boolean readSuccess = Process.parseProcLine(timeInStateBytes, 0, timeInStateBytes.length, ArrayUtils.convertToIntArray(timeInStateFrequencyFormat), null, readLongs, null);
        if (!readSuccess) {
            throw new IOException("Failed to parse time_in_state file");
        }
        this.mTimeInStateTimeFormat = ArrayUtils.convertToIntArray(timeInStateTimeFormat);
        this.mFrequenciesKhz = readLongs;
    }
}
