package com.android.internal.os;

import android.os.Process;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProcTimeInStateReader {
    private static final String TAG = "ProcTimeInStateReader";
    private static final List<Integer> TIME_IN_STATE_HEADER_LINE_FORMAT = Collections.singletonList(10);
    private static final List<Integer> TIME_IN_STATE_LINE_FREQUENCY_FORMAT = Arrays.asList(new Integer[]{8224, 10});
    private static final List<Integer> TIME_IN_STATE_LINE_TIME_FORMAT = Arrays.asList(new Integer[]{32, 8202});
    private long[] mFrequenciesKhz;
    private int[] mTimeInStateTimeFormat;

    public ProcTimeInStateReader(Path initialTimeInStateFile) throws IOException {
        initializeTimeInStateFormat(initialTimeInStateFile);
    }

    public long[] getUsageTimesMillis(Path timeInStatePath) {
        long[] readLongs = new long[this.mFrequenciesKhz.length];
        if (!Process.readProcFile(timeInStatePath.toString(), this.mTimeInStateTimeFormat, (String[]) null, readLongs, (float[]) null)) {
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
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        int numFrequencies = 0;
        while (true) {
            int i2 = i;
            if (i2 >= timeInStateBytes.length) {
                break;
            }
            if (!Character.isDigit(timeInStateBytes[i2])) {
                arrayList.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
                arrayList2.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
            } else {
                arrayList.addAll(TIME_IN_STATE_LINE_FREQUENCY_FORMAT);
                arrayList2.addAll(TIME_IN_STATE_LINE_TIME_FORMAT);
                numFrequencies++;
            }
            while (i2 < timeInStateBytes.length && timeInStateBytes[i2] != 10) {
                i2++;
            }
            i = i2 + 1;
        }
        if (numFrequencies != 0) {
            long[] readLongs = new long[numFrequencies];
            if (Process.parseProcLine(timeInStateBytes, 0, timeInStateBytes.length, ArrayUtils.convertToIntArray(arrayList), (String[]) null, readLongs, (float[]) null)) {
                this.mTimeInStateTimeFormat = ArrayUtils.convertToIntArray(arrayList2);
                this.mFrequenciesKhz = readLongs;
                return;
            }
            throw new IOException("Failed to parse time_in_state file");
        }
        throw new IOException("Empty time_in_state file");
    }
}
