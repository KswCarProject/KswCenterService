package com.android.internal.app.procstats;

import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.app.procstats.SparseMappingTable;

public class PssTable extends SparseMappingTable.Table {
    public PssTable(SparseMappingTable tableData) {
        super(tableData);
    }

    public void mergeStats(PssTable that) {
        int N = that.getKeyCount();
        for (int i = 0; i < N; i++) {
            int thatKey = that.getKeyAt(i);
            int key = getOrAddKey((byte) SparseMappingTable.getIdFromKey(thatKey), 10);
            mergeStats(getArrayForKey(key), SparseMappingTable.getIndexFromKey(key), that.getArrayForKey(thatKey), SparseMappingTable.getIndexFromKey(thatKey));
        }
    }

    public void mergeStats(int state, int inCount, long minPss, long avgPss, long maxPss, long minUss, long avgUss, long maxUss, long minRss, long avgRss, long maxRss) {
        int key = getOrAddKey((byte) state, 10);
        int i = key;
        mergeStats(getArrayForKey(key), SparseMappingTable.getIndexFromKey(key), inCount, minPss, avgPss, maxPss, minUss, avgUss, maxUss, minRss, avgRss, maxRss);
    }

    public static void mergeStats(long[] stats, int statsIndex, long[] thatStats, int thatStatsIndex) {
        mergeStats(stats, statsIndex, (int) thatStats[thatStatsIndex + 0], thatStats[thatStatsIndex + 1], thatStats[thatStatsIndex + 2], thatStats[thatStatsIndex + 3], thatStats[thatStatsIndex + 4], thatStats[thatStatsIndex + 5], thatStats[thatStatsIndex + 6], thatStats[thatStatsIndex + 7], thatStats[thatStatsIndex + 8], thatStats[thatStatsIndex + 9]);
    }

    public static void mergeStats(long[] stats, int statsIndex, int inCount, long minPss, long avgPss, long maxPss, long minUss, long avgUss, long maxUss, long minRss, long avgRss, long maxRss) {
        int i = inCount;
        long j = avgPss;
        long j2 = avgUss;
        long count = stats[statsIndex + 0];
        if (count == 0) {
            stats[statsIndex + 0] = (long) i;
            stats[statsIndex + 1] = minPss;
            stats[statsIndex + 2] = j;
            stats[statsIndex + 3] = maxPss;
            stats[statsIndex + 4] = minUss;
            stats[statsIndex + 5] = j2;
            stats[statsIndex + 6] = maxUss;
            stats[statsIndex + 7] = minRss;
            stats[statsIndex + 8] = avgRss;
            stats[statsIndex + 9] = maxRss;
            long j3 = count;
            long count2 = avgUss;
            return;
        }
        long count3 = count;
        long j4 = avgRss;
        stats[statsIndex + 0] = ((long) i) + count3;
        if (stats[statsIndex + 1] > minPss) {
            stats[statsIndex + 1] = minPss;
        }
        stats[statsIndex + 2] = (long) (((((double) stats[statsIndex + 2]) * ((double) count3)) + (((double) j) * ((double) i))) / ((double) (((long) i) + count3)));
        if (stats[statsIndex + 3] < maxPss) {
            stats[statsIndex + 3] = maxPss;
        }
        if (stats[statsIndex + 4] > minUss) {
            stats[statsIndex + 4] = minUss;
        }
        long count4 = count3;
        stats[statsIndex + 5] = (long) (((((double) stats[statsIndex + 5]) * ((double) count3)) + (((double) avgUss) * ((double) i))) / ((double) (((long) i) + count4)));
        if (stats[statsIndex + 6] < maxUss) {
            stats[statsIndex + 6] = maxUss;
        }
        long j5 = avgRss;
        if (stats[statsIndex + 7] > minRss) {
            stats[statsIndex + 7] = minRss;
        }
        stats[statsIndex + 8] = (long) (((((double) stats[statsIndex + 8]) * ((double) count4)) + (((double) j5) * ((double) i))) / ((double) (((long) i) + count4)));
        if (stats[statsIndex + 9] < maxRss) {
            stats[statsIndex + 9] = maxRss;
        }
    }

    public void writeStatsToProtoForKey(ProtoOutputStream proto, int key) {
        writeStatsToProto(proto, getArrayForKey(key), SparseMappingTable.getIndexFromKey(key));
    }

    public static void writeStatsToProto(ProtoOutputStream proto, long[] stats, int statsIndex) {
        proto.write(1120986464261L, stats[statsIndex + 0]);
        ProtoOutputStream protoOutputStream = proto;
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268038L, stats[statsIndex + 1], stats[statsIndex + 2], stats[statsIndex + 3]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268039L, stats[statsIndex + 4], stats[statsIndex + 5], stats[statsIndex + 6]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268040L, stats[statsIndex + 7], stats[statsIndex + 8], stats[statsIndex + 9]);
    }
}
