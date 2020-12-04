package android.net;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.util.ArrayUtils;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import libcore.util.EmptyArray;

public class NetworkStats implements Parcelable {
    private static final String CLATD_INTERFACE_PREFIX = "v4-";
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkStats> CREATOR = new Parcelable.Creator<NetworkStats>() {
        public NetworkStats createFromParcel(Parcel in) {
            return new NetworkStats(in);
        }

        public NetworkStats[] newArray(int size) {
            return new NetworkStats[size];
        }
    };
    public static final int DEFAULT_NETWORK_ALL = -1;
    public static final int DEFAULT_NETWORK_NO = 0;
    public static final int DEFAULT_NETWORK_YES = 1;
    public static final String IFACE_ALL = null;
    public static final String[] INTERFACES_ALL = null;
    private static final int IPV4V6_HEADER_DELTA = 20;
    public static final int METERED_ALL = -1;
    public static final int METERED_NO = 0;
    public static final int METERED_YES = 1;
    public static final int ROAMING_ALL = -1;
    public static final int ROAMING_NO = 0;
    public static final int ROAMING_YES = 1;
    public static final int SET_ALL = -1;
    public static final int SET_DBG_VPN_IN = 1001;
    public static final int SET_DBG_VPN_OUT = 1002;
    public static final int SET_DEBUG_START = 1000;
    public static final int SET_DEFAULT = 0;
    public static final int SET_FOREGROUND = 1;
    public static final int STATS_PER_IFACE = 0;
    public static final int STATS_PER_UID = 1;
    private static final String TAG = "NetworkStats";
    public static final int TAG_ALL = -1;
    public static final int TAG_NONE = 0;
    public static final int UID_ALL = -1;
    @UnsupportedAppUsage
    private int capacity;
    @UnsupportedAppUsage
    private int[] defaultNetwork;
    private long elapsedRealtime;
    @UnsupportedAppUsage
    private String[] iface;
    @UnsupportedAppUsage
    private int[] metered;
    @UnsupportedAppUsage
    private long[] operations;
    @UnsupportedAppUsage
    private int[] roaming;
    @UnsupportedAppUsage
    private long[] rxBytes;
    @UnsupportedAppUsage
    private long[] rxPackets;
    @UnsupportedAppUsage
    private int[] set;
    @UnsupportedAppUsage
    private int size;
    @UnsupportedAppUsage
    private int[] tag;
    @UnsupportedAppUsage
    private long[] txBytes;
    @UnsupportedAppUsage
    private long[] txPackets;
    @UnsupportedAppUsage
    private int[] uid;

    public interface NonMonotonicObserver<C> {
        void foundNonMonotonic(NetworkStats networkStats, int i, NetworkStats networkStats2, int i2, C c);

        void foundNonMonotonic(NetworkStats networkStats, int i, C c);
    }

    public static class Entry {
        public int defaultNetwork;
        @UnsupportedAppUsage
        public String iface;
        public int metered;
        public long operations;
        public int roaming;
        @UnsupportedAppUsage
        public long rxBytes;
        @UnsupportedAppUsage
        public long rxPackets;
        @UnsupportedAppUsage
        public int set;
        @UnsupportedAppUsage
        public int tag;
        @UnsupportedAppUsage
        public long txBytes;
        @UnsupportedAppUsage
        public long txPackets;
        @UnsupportedAppUsage
        public int uid;

        @UnsupportedAppUsage
        public Entry() {
            this(NetworkStats.IFACE_ALL, -1, 0, 0, 0, 0, 0, 0, 0);
        }

        public Entry(long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
            this(NetworkStats.IFACE_ALL, -1, 0, 0, rxBytes2, rxPackets2, txBytes2, txPackets2, operations2);
        }

        public Entry(String iface2, int uid2, int set2, int tag2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
            this(iface2, uid2, set2, tag2, 0, 0, 0, rxBytes2, rxPackets2, txBytes2, txPackets2, operations2);
        }

        public Entry(String iface2, int uid2, int set2, int tag2, int metered2, int roaming2, int defaultNetwork2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
            this.iface = iface2;
            this.uid = uid2;
            this.set = set2;
            this.tag = tag2;
            this.metered = metered2;
            this.roaming = roaming2;
            this.defaultNetwork = defaultNetwork2;
            this.rxBytes = rxBytes2;
            this.rxPackets = rxPackets2;
            this.txBytes = txBytes2;
            this.txPackets = txPackets2;
            this.operations = operations2;
        }

        public boolean isNegative() {
            return this.rxBytes < 0 || this.rxPackets < 0 || this.txBytes < 0 || this.txPackets < 0 || this.operations < 0;
        }

        public boolean isEmpty() {
            return this.rxBytes == 0 && this.rxPackets == 0 && this.txBytes == 0 && this.txPackets == 0 && this.operations == 0;
        }

        public void add(Entry another) {
            this.rxBytes += another.rxBytes;
            this.rxPackets += another.rxPackets;
            this.txBytes += another.txBytes;
            this.txPackets += another.txPackets;
            this.operations += another.operations;
        }

        public String toString() {
            return "iface=" + this.iface + " uid=" + this.uid + " set=" + NetworkStats.setToString(this.set) + " tag=" + NetworkStats.tagToString(this.tag) + " metered=" + NetworkStats.meteredToString(this.metered) + " roaming=" + NetworkStats.roamingToString(this.roaming) + " defaultNetwork=" + NetworkStats.defaultNetworkToString(this.defaultNetwork) + " rxBytes=" + this.rxBytes + " rxPackets=" + this.rxPackets + " txBytes=" + this.txBytes + " txPackets=" + this.txPackets + " operations=" + this.operations;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry e = (Entry) o;
            if (this.uid == e.uid && this.set == e.set && this.tag == e.tag && this.metered == e.metered && this.roaming == e.roaming && this.defaultNetwork == e.defaultNetwork && this.rxBytes == e.rxBytes && this.rxPackets == e.rxPackets && this.txBytes == e.txBytes && this.txPackets == e.txPackets && this.operations == e.operations && this.iface.equals(e.iface)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Integer.valueOf(this.uid), Integer.valueOf(this.set), Integer.valueOf(this.tag), Integer.valueOf(this.metered), Integer.valueOf(this.roaming), Integer.valueOf(this.defaultNetwork), this.iface});
        }
    }

    @UnsupportedAppUsage
    public NetworkStats(long elapsedRealtime2, int initialSize) {
        this.elapsedRealtime = elapsedRealtime2;
        this.size = 0;
        if (initialSize > 0) {
            this.capacity = initialSize;
            this.iface = new String[initialSize];
            this.uid = new int[initialSize];
            this.set = new int[initialSize];
            this.tag = new int[initialSize];
            this.metered = new int[initialSize];
            this.roaming = new int[initialSize];
            this.defaultNetwork = new int[initialSize];
            this.rxBytes = new long[initialSize];
            this.rxPackets = new long[initialSize];
            this.txBytes = new long[initialSize];
            this.txPackets = new long[initialSize];
            this.operations = new long[initialSize];
            return;
        }
        clear();
    }

    @UnsupportedAppUsage
    public NetworkStats(Parcel parcel) {
        this.elapsedRealtime = parcel.readLong();
        this.size = parcel.readInt();
        this.capacity = parcel.readInt();
        this.iface = parcel.createStringArray();
        this.uid = parcel.createIntArray();
        this.set = parcel.createIntArray();
        this.tag = parcel.createIntArray();
        this.metered = parcel.createIntArray();
        this.roaming = parcel.createIntArray();
        this.defaultNetwork = parcel.createIntArray();
        this.rxBytes = parcel.createLongArray();
        this.rxPackets = parcel.createLongArray();
        this.txBytes = parcel.createLongArray();
        this.txPackets = parcel.createLongArray();
        this.operations = parcel.createLongArray();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.elapsedRealtime);
        dest.writeInt(this.size);
        dest.writeInt(this.capacity);
        dest.writeStringArray(this.iface);
        dest.writeIntArray(this.uid);
        dest.writeIntArray(this.set);
        dest.writeIntArray(this.tag);
        dest.writeIntArray(this.metered);
        dest.writeIntArray(this.roaming);
        dest.writeIntArray(this.defaultNetwork);
        dest.writeLongArray(this.rxBytes);
        dest.writeLongArray(this.rxPackets);
        dest.writeLongArray(this.txBytes);
        dest.writeLongArray(this.txPackets);
        dest.writeLongArray(this.operations);
    }

    public NetworkStats clone() {
        NetworkStats clone = new NetworkStats(this.elapsedRealtime, this.size);
        Entry entry = null;
        for (int i = 0; i < this.size; i++) {
            entry = getValues(i, entry);
            clone.addValues(entry);
        }
        return clone;
    }

    public void clear() {
        this.capacity = 0;
        this.iface = EmptyArray.STRING;
        this.uid = EmptyArray.INT;
        this.set = EmptyArray.INT;
        this.tag = EmptyArray.INT;
        this.metered = EmptyArray.INT;
        this.roaming = EmptyArray.INT;
        this.defaultNetwork = EmptyArray.INT;
        this.rxBytes = EmptyArray.LONG;
        this.rxPackets = EmptyArray.LONG;
        this.txBytes = EmptyArray.LONG;
        this.txPackets = EmptyArray.LONG;
        this.operations = EmptyArray.LONG;
    }

    @VisibleForTesting
    public NetworkStats addIfaceValues(String iface2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2) {
        return addValues(iface2, -1, 0, 0, rxBytes2, rxPackets2, txBytes2, txPackets2, 0);
    }

    @VisibleForTesting
    public NetworkStats addValues(String iface2, int uid2, int set2, int tag2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
        return addValues(new Entry(iface2, uid2, set2, tag2, rxBytes2, rxPackets2, txBytes2, txPackets2, operations2));
    }

    @VisibleForTesting
    public NetworkStats addValues(String iface2, int uid2, int set2, int tag2, int metered2, int roaming2, int defaultNetwork2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
        Entry entry = r0;
        Entry entry2 = new Entry(iface2, uid2, set2, tag2, metered2, roaming2, defaultNetwork2, rxBytes2, rxPackets2, txBytes2, txPackets2, operations2);
        return addValues(entry);
    }

    public NetworkStats addValues(Entry entry) {
        if (this.size >= this.capacity) {
            int newLength = (Math.max(this.size, 10) * 3) / 2;
            this.iface = (String[]) Arrays.copyOf(this.iface, newLength);
            this.uid = Arrays.copyOf(this.uid, newLength);
            this.set = Arrays.copyOf(this.set, newLength);
            this.tag = Arrays.copyOf(this.tag, newLength);
            this.metered = Arrays.copyOf(this.metered, newLength);
            this.roaming = Arrays.copyOf(this.roaming, newLength);
            this.defaultNetwork = Arrays.copyOf(this.defaultNetwork, newLength);
            this.rxBytes = Arrays.copyOf(this.rxBytes, newLength);
            this.rxPackets = Arrays.copyOf(this.rxPackets, newLength);
            this.txBytes = Arrays.copyOf(this.txBytes, newLength);
            this.txPackets = Arrays.copyOf(this.txPackets, newLength);
            this.operations = Arrays.copyOf(this.operations, newLength);
            this.capacity = newLength;
        }
        setValues(this.size, entry);
        this.size++;
        return this;
    }

    private void setValues(int i, Entry entry) {
        this.iface[i] = entry.iface;
        this.uid[i] = entry.uid;
        this.set[i] = entry.set;
        this.tag[i] = entry.tag;
        this.metered[i] = entry.metered;
        this.roaming[i] = entry.roaming;
        this.defaultNetwork[i] = entry.defaultNetwork;
        this.rxBytes[i] = entry.rxBytes;
        this.rxPackets[i] = entry.rxPackets;
        this.txBytes[i] = entry.txBytes;
        this.txPackets[i] = entry.txPackets;
        this.operations[i] = entry.operations;
    }

    @UnsupportedAppUsage
    public Entry getValues(int i, Entry recycle) {
        Entry entry = recycle != null ? recycle : new Entry();
        entry.iface = this.iface[i];
        entry.uid = this.uid[i];
        entry.set = this.set[i];
        entry.tag = this.tag[i];
        entry.metered = this.metered[i];
        entry.roaming = this.roaming[i];
        entry.defaultNetwork = this.defaultNetwork[i];
        entry.rxBytes = this.rxBytes[i];
        entry.rxPackets = this.rxPackets[i];
        entry.txBytes = this.txBytes[i];
        entry.txPackets = this.txPackets[i];
        entry.operations = this.operations[i];
        return entry;
    }

    private void maybeCopyEntry(int dest, int src) {
        if (dest != src) {
            this.iface[dest] = this.iface[src];
            this.uid[dest] = this.uid[src];
            this.set[dest] = this.set[src];
            this.tag[dest] = this.tag[src];
            this.metered[dest] = this.metered[src];
            this.roaming[dest] = this.roaming[src];
            this.defaultNetwork[dest] = this.defaultNetwork[src];
            this.rxBytes[dest] = this.rxBytes[src];
            this.rxPackets[dest] = this.rxPackets[src];
            this.txBytes[dest] = this.txBytes[src];
            this.txPackets[dest] = this.txPackets[src];
            this.operations[dest] = this.operations[src];
        }
    }

    public long getElapsedRealtime() {
        return this.elapsedRealtime;
    }

    public void setElapsedRealtime(long time) {
        this.elapsedRealtime = time;
    }

    public long getElapsedRealtimeAge() {
        return SystemClock.elapsedRealtime() - this.elapsedRealtime;
    }

    @UnsupportedAppUsage
    public int size() {
        return this.size;
    }

    @VisibleForTesting
    public int internalSize() {
        return this.capacity;
    }

    @Deprecated
    public NetworkStats combineValues(String iface2, int uid2, int tag2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
        return combineValues(iface2, uid2, 0, tag2, rxBytes2, rxPackets2, txBytes2, txPackets2, operations2);
    }

    public NetworkStats combineValues(String iface2, int uid2, int set2, int tag2, long rxBytes2, long rxPackets2, long txBytes2, long txPackets2, long operations2) {
        return combineValues(new Entry(iface2, uid2, set2, tag2, rxBytes2, rxPackets2, txBytes2, txPackets2, operations2));
    }

    @UnsupportedAppUsage
    public NetworkStats combineValues(Entry entry) {
        int i = findIndex(entry.iface, entry.uid, entry.set, entry.tag, entry.metered, entry.roaming, entry.defaultNetwork);
        if (i == -1) {
            addValues(entry);
        } else {
            long[] jArr = this.rxBytes;
            jArr[i] = jArr[i] + entry.rxBytes;
            long[] jArr2 = this.rxPackets;
            jArr2[i] = jArr2[i] + entry.rxPackets;
            long[] jArr3 = this.txBytes;
            jArr3[i] = jArr3[i] + entry.txBytes;
            long[] jArr4 = this.txPackets;
            jArr4[i] = jArr4[i] + entry.txPackets;
            long[] jArr5 = this.operations;
            jArr5[i] = jArr5[i] + entry.operations;
        }
        return this;
    }

    @UnsupportedAppUsage
    public void combineAllValues(NetworkStats another) {
        Entry entry = null;
        for (int i = 0; i < another.size; i++) {
            entry = another.getValues(i, entry);
            combineValues(entry);
        }
    }

    public int findIndex(String iface2, int uid2, int set2, int tag2, int metered2, int roaming2, int defaultNetwork2) {
        for (int i = 0; i < this.size; i++) {
            if (uid2 == this.uid[i] && set2 == this.set[i] && tag2 == this.tag[i] && metered2 == this.metered[i] && roaming2 == this.roaming[i] && defaultNetwork2 == this.defaultNetwork[i] && Objects.equals(iface2, this.iface[i])) {
                return i;
            }
        }
        return -1;
    }

    @VisibleForTesting
    public int findIndexHinted(String iface2, int uid2, int set2, int tag2, int metered2, int roaming2, int defaultNetwork2, int hintIndex) {
        int i;
        for (int offset = 0; offset < this.size; offset++) {
            int halfOffset = offset / 2;
            if (offset % 2 == 0) {
                i = (hintIndex + halfOffset) % this.size;
            } else {
                i = (((this.size + hintIndex) - halfOffset) - 1) % this.size;
            }
            if (uid2 == this.uid[i] && set2 == this.set[i] && tag2 == this.tag[i] && metered2 == this.metered[i] && roaming2 == this.roaming[i] && defaultNetwork2 == this.defaultNetwork[i] && Objects.equals(iface2, this.iface[i])) {
                return i;
            }
        }
        return -1;
    }

    public void spliceOperationsFrom(NetworkStats stats) {
        for (int i = 0; i < this.size; i++) {
            int j = stats.findIndex(this.iface[i], this.uid[i], this.set[i], this.tag[i], this.metered[i], this.roaming[i], this.defaultNetwork[i]);
            if (j == -1) {
                this.operations[i] = 0;
            } else {
                this.operations[i] = stats.operations[j];
            }
        }
    }

    public String[] getUniqueIfaces() {
        HashSet<String> ifaces = new HashSet<>();
        for (String iface2 : this.iface) {
            if (iface2 != IFACE_ALL) {
                ifaces.add(iface2);
            }
        }
        return (String[]) ifaces.toArray(new String[ifaces.size()]);
    }

    @UnsupportedAppUsage
    public int[] getUniqueUids() {
        SparseBooleanArray uids = new SparseBooleanArray();
        for (int uid2 : this.uid) {
            uids.put(uid2, true);
        }
        int size2 = uids.size();
        int[] result = new int[size2];
        for (int i = 0; i < size2; i++) {
            result[i] = uids.keyAt(i);
        }
        return result;
    }

    @UnsupportedAppUsage
    public long getTotalBytes() {
        Entry entry = getTotal((Entry) null);
        return entry.rxBytes + entry.txBytes;
    }

    @UnsupportedAppUsage
    public Entry getTotal(Entry recycle) {
        return getTotal(recycle, (HashSet<String>) null, -1, false);
    }

    @UnsupportedAppUsage
    public Entry getTotal(Entry recycle, int limitUid) {
        return getTotal(recycle, (HashSet<String>) null, limitUid, false);
    }

    public Entry getTotal(Entry recycle, HashSet<String> limitIface) {
        return getTotal(recycle, limitIface, -1, false);
    }

    @UnsupportedAppUsage
    public Entry getTotalIncludingTags(Entry recycle) {
        return getTotal(recycle, (HashSet<String>) null, -1, true);
    }

    private Entry getTotal(Entry recycle, HashSet<String> limitIface, int limitUid, boolean includeTags) {
        Entry entry = recycle != null ? recycle : new Entry();
        entry.iface = IFACE_ALL;
        entry.uid = limitUid;
        entry.set = -1;
        entry.tag = 0;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        entry.rxBytes = 0;
        entry.rxPackets = 0;
        entry.txBytes = 0;
        entry.txPackets = 0;
        entry.operations = 0;
        for (int i = 0; i < this.size; i++) {
            boolean matchesIface = true;
            boolean matchesUid = limitUid == -1 || limitUid == this.uid[i];
            if (limitIface != null && !limitIface.contains(this.iface[i])) {
                matchesIface = false;
            }
            if (matchesUid && matchesIface && (this.tag[i] == 0 || includeTags)) {
                entry.rxBytes += this.rxBytes[i];
                entry.rxPackets += this.rxPackets[i];
                entry.txBytes += this.txBytes[i];
                entry.txPackets += this.txPackets[i];
                entry.operations += this.operations[i];
            }
        }
        return entry;
    }

    public long getTotalPackets() {
        long total = 0;
        for (int i = this.size - 1; i >= 0; i--) {
            total += this.rxPackets[i] + this.txPackets[i];
        }
        return total;
    }

    public NetworkStats subtract(NetworkStats right) {
        return subtract(this, right, (NonMonotonicObserver) null, (Object) null);
    }

    public static <C> NetworkStats subtract(NetworkStats left, NetworkStats right, NonMonotonicObserver<C> observer, C cookie) {
        return subtract(left, right, observer, cookie, (NetworkStats) null);
    }

    public static <C> NetworkStats subtract(NetworkStats left, NetworkStats right, NonMonotonicObserver<C> observer, C cookie, NetworkStats recycle) {
        NetworkStats result;
        long deltaRealtime;
        Entry entry;
        NetworkStats result2;
        int i;
        long j;
        NetworkStats networkStats = left;
        NetworkStats networkStats2 = right;
        NetworkStats networkStats3 = recycle;
        long deltaRealtime2 = networkStats.elapsedRealtime - networkStats2.elapsedRealtime;
        if (deltaRealtime2 < 0) {
            if (observer != null) {
                observer.foundNonMonotonic(left, -1, right, -1, cookie);
            }
            deltaRealtime2 = 0;
        }
        long deltaRealtime3 = deltaRealtime2;
        Entry entry2 = new Entry();
        int i2 = 0;
        if (networkStats3 == null || networkStats3.capacity < networkStats.size) {
            result = new NetworkStats(deltaRealtime3, networkStats.size);
        } else {
            result = recycle;
            result.size = 0;
            result.elapsedRealtime = deltaRealtime3;
        }
        NetworkStats result3 = result;
        while (true) {
            int i3 = i2;
            if (i3 < networkStats.size) {
                entry2.iface = networkStats.iface[i3];
                entry2.uid = networkStats.uid[i3];
                entry2.set = networkStats.set[i3];
                entry2.tag = networkStats.tag[i3];
                entry2.metered = networkStats.metered[i3];
                entry2.roaming = networkStats.roaming[i3];
                entry2.defaultNetwork = networkStats.defaultNetwork[i3];
                entry2.rxBytes = networkStats.rxBytes[i3];
                entry2.rxPackets = networkStats.rxPackets[i3];
                entry2.txBytes = networkStats.txBytes[i3];
                entry2.txPackets = networkStats.txPackets[i3];
                entry2.operations = networkStats.operations[i3];
                NetworkStats result4 = result3;
                NetworkStats networkStats4 = networkStats2;
                int j2 = right.findIndexHinted(entry2.iface, entry2.uid, entry2.set, entry2.tag, entry2.metered, entry2.roaming, entry2.defaultNetwork, i3);
                if (j2 != -1) {
                    entry2.rxBytes -= networkStats4.rxBytes[j2];
                    entry2.rxPackets -= networkStats4.rxPackets[j2];
                    entry2.txBytes -= networkStats4.txBytes[j2];
                    entry2.txPackets -= networkStats4.txPackets[j2];
                    entry2.operations -= networkStats4.operations[j2];
                }
                if (entry2.isNegative()) {
                    if (observer != null) {
                        i = i3;
                        result2 = result4;
                        entry = entry2;
                        deltaRealtime = deltaRealtime3;
                        observer.foundNonMonotonic(left, i, right, j2, cookie);
                    } else {
                        i = i3;
                        entry = entry2;
                        deltaRealtime = deltaRealtime3;
                        result2 = result4;
                    }
                    j = 0;
                    entry.rxBytes = Math.max(entry.rxBytes, 0);
                    entry.rxPackets = Math.max(entry.rxPackets, 0);
                    entry.txBytes = Math.max(entry.txBytes, 0);
                    entry.txPackets = Math.max(entry.txPackets, 0);
                    entry.operations = Math.max(entry.operations, 0);
                } else {
                    i = i3;
                    entry = entry2;
                    deltaRealtime = deltaRealtime3;
                    result2 = result4;
                    j = 0;
                }
                result2.addValues(entry);
                i2 = i + 1;
                networkStats2 = right;
                NetworkStats networkStats5 = recycle;
                deltaRealtime3 = deltaRealtime;
                long j3 = j;
                result3 = result2;
                entry2 = entry;
            } else {
                Entry entry3 = entry2;
                long j4 = deltaRealtime3;
                return result3;
            }
        }
    }

    public static void apply464xlatAdjustments(NetworkStats baseTraffic, NetworkStats stackedTraffic, Map<String, String> stackedIfaces, boolean useBpfStats) {
        NetworkStats networkStats = baseTraffic;
        NetworkStats networkStats2 = stackedTraffic;
        NetworkStats adjustments = new NetworkStats(0, stackedIfaces.size());
        Entry adjust = new Entry(IFACE_ALL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Entry entry = null;
        for (int i = 0; i < networkStats2.size; i++) {
            entry = networkStats2.getValues(i, entry);
            if (entry.iface == null || !entry.iface.startsWith(CLATD_INTERFACE_PREFIX)) {
                Map<String, String> map = stackedIfaces;
            } else {
                String baseIface = stackedIfaces.get(entry.iface);
                if (baseIface != null) {
                    adjust.iface = baseIface;
                    if (!useBpfStats) {
                        adjust.rxBytes = -(entry.rxBytes + (entry.rxPackets * 20));
                        adjust.rxPackets = -entry.rxPackets;
                    }
                    adjustments.combineValues(adjust);
                    entry.rxBytes += entry.rxPackets * 20;
                    entry.txBytes += entry.txPackets * 20;
                    networkStats2.setValues(i, entry);
                }
            }
        }
        Map<String, String> map2 = stackedIfaces;
        networkStats.removeUids(new int[]{1029});
        networkStats.combineAllValues(adjustments);
    }

    public void apply464xlatAdjustments(Map<String, String> stackedIfaces, boolean useBpfStats) {
        apply464xlatAdjustments(this, this, stackedIfaces, useBpfStats);
    }

    public NetworkStats groupedByIface() {
        NetworkStats stats = new NetworkStats(this.elapsedRealtime, 10);
        Entry entry = new Entry();
        entry.uid = -1;
        entry.set = -1;
        int i = 0;
        entry.tag = 0;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        entry.operations = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.size) {
                return stats;
            }
            if (this.tag[i2] == 0) {
                entry.iface = this.iface[i2];
                entry.rxBytes = this.rxBytes[i2];
                entry.rxPackets = this.rxPackets[i2];
                entry.txBytes = this.txBytes[i2];
                entry.txPackets = this.txPackets[i2];
                stats.combineValues(entry);
            }
            i = i2 + 1;
        }
    }

    public NetworkStats groupedByUid() {
        NetworkStats stats = new NetworkStats(this.elapsedRealtime, 10);
        Entry entry = new Entry();
        entry.iface = IFACE_ALL;
        entry.set = -1;
        int i = 0;
        entry.tag = 0;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        while (true) {
            int i2 = i;
            if (i2 >= this.size) {
                return stats;
            }
            if (this.tag[i2] == 0) {
                entry.uid = this.uid[i2];
                entry.rxBytes = this.rxBytes[i2];
                entry.rxPackets = this.rxPackets[i2];
                entry.txBytes = this.txBytes[i2];
                entry.txPackets = this.txPackets[i2];
                entry.operations = this.operations[i2];
                stats.combineValues(entry);
            }
            i = i2 + 1;
        }
    }

    public void removeUids(int[] uids) {
        int nextOutputEntry = 0;
        for (int i = 0; i < this.size; i++) {
            if (!ArrayUtils.contains(uids, this.uid[i])) {
                maybeCopyEntry(nextOutputEntry, i);
                nextOutputEntry++;
            }
        }
        this.size = nextOutputEntry;
    }

    public void filter(int limitUid, String[] limitIfaces, int limitTag) {
        if (limitUid != -1 || limitTag != -1 || limitIfaces != INTERFACES_ALL) {
            int nextOutputEntry = 0;
            Entry entry = new Entry();
            for (int i = 0; i < this.size; i++) {
                entry = getValues(i, entry);
                if ((limitUid == -1 || limitUid == entry.uid) && (limitTag == -1 || limitTag == entry.tag) && (limitIfaces == INTERFACES_ALL || ArrayUtils.contains((T[]) limitIfaces, entry.iface))) {
                    setValues(nextOutputEntry, entry);
                    nextOutputEntry++;
                }
            }
            this.size = nextOutputEntry;
        }
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.print(prefix);
        pw.print("NetworkStats: elapsedRealtime=");
        pw.println(this.elapsedRealtime);
        for (int i = 0; i < this.size; i++) {
            pw.print(prefix);
            pw.print("  [");
            pw.print(i);
            pw.print("]");
            pw.print(" iface=");
            pw.print(this.iface[i]);
            pw.print(" uid=");
            pw.print(this.uid[i]);
            pw.print(" set=");
            pw.print(setToString(this.set[i]));
            pw.print(" tag=");
            pw.print(tagToString(this.tag[i]));
            pw.print(" metered=");
            pw.print(meteredToString(this.metered[i]));
            pw.print(" roaming=");
            pw.print(roamingToString(this.roaming[i]));
            pw.print(" defaultNetwork=");
            pw.print(defaultNetworkToString(this.defaultNetwork[i]));
            pw.print(" rxBytes=");
            pw.print(this.rxBytes[i]);
            pw.print(" rxPackets=");
            pw.print(this.rxPackets[i]);
            pw.print(" txBytes=");
            pw.print(this.txBytes[i]);
            pw.print(" txPackets=");
            pw.print(this.txPackets[i]);
            pw.print(" operations=");
            pw.println(this.operations[i]);
        }
    }

    public static String setToString(int set2) {
        switch (set2) {
            case -1:
                return "ALL";
            case 0:
                return "DEFAULT";
            case 1:
                return "FOREGROUND";
            default:
                switch (set2) {
                    case 1001:
                        return "DBG_VPN_IN";
                    case 1002:
                        return "DBG_VPN_OUT";
                    default:
                        return IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
                }
        }
    }

    public static String setToCheckinString(int set2) {
        switch (set2) {
            case -1:
                return "all";
            case 0:
                return "def";
            case 1:
                return "fg";
            default:
                switch (set2) {
                    case 1001:
                        return "vpnin";
                    case 1002:
                        return "vpnout";
                    default:
                        return "unk";
                }
        }
    }

    public static boolean setMatches(int querySet, int dataSet) {
        if (querySet == dataSet) {
            return true;
        }
        if (querySet != -1 || dataSet >= 1000) {
            return false;
        }
        return true;
    }

    public static String tagToString(int tag2) {
        return "0x" + Integer.toHexString(tag2);
    }

    public static String meteredToString(int metered2) {
        switch (metered2) {
            case -1:
                return "ALL";
            case 0:
                return "NO";
            case 1:
                return "YES";
            default:
                return IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
        }
    }

    public static String roamingToString(int roaming2) {
        switch (roaming2) {
            case -1:
                return "ALL";
            case 0:
                return "NO";
            case 1:
                return "YES";
            default:
                return IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
        }
    }

    public static String defaultNetworkToString(int defaultNetwork2) {
        switch (defaultNetwork2) {
            case -1:
                return "ALL";
            case 0:
                return "NO";
            case 1:
                return "YES";
            default:
                return IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
        }
    }

    public String toString() {
        CharArrayWriter writer = new CharArrayWriter();
        dump("", new PrintWriter(writer));
        return writer.toString();
    }

    public int describeContents() {
        return 0;
    }

    public boolean migrateTun(int tunUid, String tunIface, String underlyingIface) {
        Entry tunIfaceTotal = new Entry();
        Entry underlyingIfaceTotal = new Entry();
        tunAdjustmentInit(tunUid, tunIface, underlyingIface, tunIfaceTotal, underlyingIfaceTotal);
        Entry pool = tunGetPool(tunIfaceTotal, underlyingIfaceTotal);
        if (pool.isEmpty()) {
            return true;
        }
        Entry moved = addTrafficToApplications(tunUid, tunIface, underlyingIface, tunIfaceTotal, pool);
        deductTrafficFromVpnApp(tunUid, underlyingIface, moved);
        if (moved.isEmpty()) {
            return true;
        }
        Slog.wtf(TAG, "Failed to deduct underlying network traffic from VPN package. Moved=" + moved);
        return false;
    }

    private void tunAdjustmentInit(int tunUid, String tunIface, String underlyingIface, Entry tunIfaceTotal, Entry underlyingIfaceTotal) {
        Entry recycle = new Entry();
        int i = 0;
        while (i < this.size) {
            getValues(i, recycle);
            if (recycle.uid == -1) {
                throw new IllegalStateException("Cannot adjust VPN accounting on an iface aggregated NetworkStats.");
            } else if (recycle.set == 1001 || recycle.set == 1002) {
                throw new IllegalStateException("Cannot adjust VPN accounting on a NetworkStats containing SET_DBG_VPN_*");
            } else {
                if (recycle.uid == tunUid && recycle.tag == 0 && Objects.equals(underlyingIface, recycle.iface)) {
                    underlyingIfaceTotal.add(recycle);
                }
                if (recycle.uid != tunUid && recycle.tag == 0 && Objects.equals(tunIface, recycle.iface)) {
                    tunIfaceTotal.add(recycle);
                }
                i++;
            }
        }
    }

    private static Entry tunGetPool(Entry tunIfaceTotal, Entry underlyingIfaceTotal) {
        Entry pool = new Entry();
        pool.rxBytes = Math.min(tunIfaceTotal.rxBytes, underlyingIfaceTotal.rxBytes);
        pool.rxPackets = Math.min(tunIfaceTotal.rxPackets, underlyingIfaceTotal.rxPackets);
        pool.txBytes = Math.min(tunIfaceTotal.txBytes, underlyingIfaceTotal.txBytes);
        pool.txPackets = Math.min(tunIfaceTotal.txPackets, underlyingIfaceTotal.txPackets);
        pool.operations = Math.min(tunIfaceTotal.operations, underlyingIfaceTotal.operations);
        return pool;
    }

    private Entry addTrafficToApplications(int tunUid, String tunIface, String underlyingIface, Entry tunIfaceTotal, Entry pool) {
        Entry moved = new Entry();
        Entry tmpEntry = new Entry();
        tmpEntry.iface = underlyingIface;
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(this.iface[i], tunIface) && this.uid[i] != tunUid) {
                if (tunIfaceTotal.rxBytes > 0) {
                    tmpEntry.rxBytes = (pool.rxBytes * this.rxBytes[i]) / tunIfaceTotal.rxBytes;
                } else {
                    tmpEntry.rxBytes = 0;
                }
                if (tunIfaceTotal.rxPackets > 0) {
                    tmpEntry.rxPackets = (pool.rxPackets * this.rxPackets[i]) / tunIfaceTotal.rxPackets;
                } else {
                    tmpEntry.rxPackets = 0;
                }
                if (tunIfaceTotal.txBytes > 0) {
                    tmpEntry.txBytes = (pool.txBytes * this.txBytes[i]) / tunIfaceTotal.txBytes;
                } else {
                    tmpEntry.txBytes = 0;
                }
                if (tunIfaceTotal.txPackets > 0) {
                    tmpEntry.txPackets = (pool.txPackets * this.txPackets[i]) / tunIfaceTotal.txPackets;
                } else {
                    tmpEntry.txPackets = 0;
                }
                if (tunIfaceTotal.operations > 0) {
                    tmpEntry.operations = (pool.operations * this.operations[i]) / tunIfaceTotal.operations;
                } else {
                    tmpEntry.operations = 0;
                }
                tmpEntry.uid = this.uid[i];
                tmpEntry.tag = this.tag[i];
                tmpEntry.set = this.set[i];
                tmpEntry.metered = this.metered[i];
                tmpEntry.roaming = this.roaming[i];
                tmpEntry.defaultNetwork = this.defaultNetwork[i];
                combineValues(tmpEntry);
                if (this.tag[i] == 0) {
                    moved.add(tmpEntry);
                    tmpEntry.set = 1001;
                    combineValues(tmpEntry);
                }
            }
        }
        return moved;
    }

    private void deductTrafficFromVpnApp(int tunUid, String underlyingIface, Entry moved) {
        moved.uid = tunUid;
        moved.set = 1002;
        moved.tag = 0;
        moved.iface = underlyingIface;
        moved.metered = -1;
        moved.roaming = -1;
        moved.defaultNetwork = -1;
        combineValues(moved);
        int idxVpnBackground = findIndex(underlyingIface, tunUid, 0, 0, 0, 0, 0);
        if (idxVpnBackground != -1) {
            tunSubtract(idxVpnBackground, this, moved);
        }
        int idxVpnForeground = findIndex(underlyingIface, tunUid, 1, 0, 0, 0, 0);
        if (idxVpnForeground != -1) {
            tunSubtract(idxVpnForeground, this, moved);
        }
    }

    private static void tunSubtract(int i, NetworkStats left, Entry right) {
        long rxBytes2 = Math.min(left.rxBytes[i], right.rxBytes);
        long[] jArr = left.rxBytes;
        jArr[i] = jArr[i] - rxBytes2;
        right.rxBytes -= rxBytes2;
        long rxPackets2 = Math.min(left.rxPackets[i], right.rxPackets);
        long[] jArr2 = left.rxPackets;
        jArr2[i] = jArr2[i] - rxPackets2;
        right.rxPackets -= rxPackets2;
        long txBytes2 = Math.min(left.txBytes[i], right.txBytes);
        long[] jArr3 = left.txBytes;
        jArr3[i] = jArr3[i] - txBytes2;
        right.txBytes -= txBytes2;
        long txPackets2 = Math.min(left.txPackets[i], right.txPackets);
        long[] jArr4 = left.txPackets;
        jArr4[i] = jArr4[i] - txPackets2;
        right.txPackets -= txPackets2;
    }
}
