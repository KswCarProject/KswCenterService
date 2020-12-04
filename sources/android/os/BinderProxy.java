package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.BinderInternal;
import java.io.FileDescriptor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import libcore.util.NativeAllocationRegistry;

public final class BinderProxy implements IBinder {
    private static final int NATIVE_ALLOCATION_SIZE = 1000;
    @GuardedBy({"sProxyMap"})
    private static final ProxyMap sProxyMap = new ProxyMap();
    private static volatile Binder.ProxyTransactListener sTransactListener = null;
    private final long mNativeData;
    volatile boolean mWarnOnBlocking = Binder.sWarnOnBlocking;

    /* access modifiers changed from: private */
    public static native long getNativeFinalizer();

    public native String getInterfaceDescriptor() throws RemoteException;

    public native boolean isBinderAlive();

    public native void linkToDeath(IBinder.DeathRecipient deathRecipient, int i) throws RemoteException;

    public native boolean pingBinder();

    public native boolean transactNative(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException;

    public native boolean unlinkToDeath(IBinder.DeathRecipient deathRecipient, int i);

    public static void setTransactListener(Binder.ProxyTransactListener listener) {
        sTransactListener = listener;
    }

    private static final class ProxyMap {
        private static final int CRASH_AT_SIZE = 20000;
        private static final int LOG_MAIN_INDEX_SIZE = 8;
        private static final int MAIN_INDEX_MASK = 255;
        private static final int MAIN_INDEX_SIZE = 256;
        static final int MAX_NUM_INTERFACES_TO_DUMP = 10;
        private static final int WARN_INCREMENT = 10;
        private final Long[][] mMainIndexKeys;
        private final ArrayList<WeakReference<BinderProxy>>[] mMainIndexValues;
        private int mRandom;
        private int mWarnBucketSize;

        private ProxyMap() {
            this.mWarnBucketSize = 20;
            this.mMainIndexKeys = new Long[256][];
            this.mMainIndexValues = new ArrayList[256];
        }

        private static int hash(long arg) {
            return ((int) ((arg >> 2) ^ (arg >> 10))) & 255;
        }

        /* access modifiers changed from: private */
        public int size() {
            int size = 0;
            for (ArrayList<WeakReference<BinderProxy>> a : this.mMainIndexValues) {
                if (a != null) {
                    size += a.size();
                }
            }
            return size;
        }

        private int unclearedSize() {
            int size = 0;
            for (ArrayList<WeakReference<BinderProxy>> a : this.mMainIndexValues) {
                if (a != null) {
                    Iterator<WeakReference<BinderProxy>> it = a.iterator();
                    while (it.hasNext()) {
                        if (it.next().get() != null) {
                            size++;
                        }
                    }
                }
            }
            return size;
        }

        private void remove(int hash, int index) {
            Long[] keyArray = this.mMainIndexKeys[hash];
            ArrayList<WeakReference<BinderProxy>> valueArray = this.mMainIndexValues[hash];
            int size = valueArray.size();
            if (index != size - 1) {
                keyArray[index] = keyArray[size - 1];
                valueArray.set(index, valueArray.get(size - 1));
            }
            valueArray.remove(size - 1);
        }

        /* access modifiers changed from: package-private */
        public BinderProxy get(long key) {
            int myHash = hash(key);
            Long[] keyArray = this.mMainIndexKeys[myHash];
            if (keyArray == null) {
                return null;
            }
            ArrayList<WeakReference<BinderProxy>> valueArray = this.mMainIndexValues[myHash];
            int bucketSize = valueArray.size();
            for (int i = 0; i < bucketSize; i++) {
                if (key == keyArray[i].longValue()) {
                    BinderProxy bp = (BinderProxy) valueArray.get(i).get();
                    if (bp != null) {
                        return bp;
                    }
                    remove(myHash, i);
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void set(long key, BinderProxy value) {
            int myHash = hash(key);
            ArrayList<WeakReference<BinderProxy>> valueArray = this.mMainIndexValues[myHash];
            if (valueArray == null) {
                ArrayList<WeakReference<BinderProxy>>[] arrayListArr = this.mMainIndexValues;
                ArrayList<WeakReference<BinderProxy>> arrayList = new ArrayList<>();
                arrayListArr[myHash] = arrayList;
                valueArray = arrayList;
                this.mMainIndexKeys[myHash] = new Long[1];
            }
            int size = valueArray.size();
            WeakReference<BinderProxy> newWr = new WeakReference<>(value);
            for (int i = 0; i < size; i++) {
                if (valueArray.get(i).get() == null) {
                    valueArray.set(i, newWr);
                    this.mMainIndexKeys[myHash][i] = Long.valueOf(key);
                    if (i < size - 1) {
                        int i2 = this.mRandom + 1;
                        this.mRandom = i2;
                        int rnd = Math.floorMod(i2, size - (i + 1));
                        if (valueArray.get(i + 1 + rnd).get() == null) {
                            remove(myHash, i + 1 + rnd);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            valueArray.add(size, newWr);
            Long[] keyArray = this.mMainIndexKeys[myHash];
            if (keyArray.length == size) {
                Long[] newArray = new Long[((size / 2) + size + 2)];
                System.arraycopy(keyArray, 0, newArray, 0, size);
                newArray[size] = Long.valueOf(key);
                this.mMainIndexKeys[myHash] = newArray;
            } else {
                keyArray[size] = Long.valueOf(key);
            }
            if (size >= this.mWarnBucketSize) {
                int totalSize = size();
                Log.v("Binder", "BinderProxy map growth! bucket size = " + size + " total = " + totalSize);
                this.mWarnBucketSize = this.mWarnBucketSize + 10;
                if (Build.IS_DEBUGGABLE && totalSize >= 20000) {
                    int totalUnclearedSize = unclearedSize();
                    if (totalUnclearedSize >= 20000) {
                        dumpProxyInterfaceCounts();
                        dumpPerUidProxyCounts();
                        Runtime.getRuntime().gc();
                        throw new AssertionError("Binder ProxyMap has too many entries: " + totalSize + " (total), " + totalUnclearedSize + " (uncleared), " + unclearedSize() + " (uncleared after GC). BinderProxy leak?");
                    } else if (totalSize > (totalUnclearedSize * 3) / 2) {
                        Log.v("Binder", "BinderProxy map has many cleared entries: " + (totalSize - totalUnclearedSize) + " of " + totalSize + " are cleared");
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        public InterfaceCount[] getSortedInterfaceCounts(int maxToReturn) {
            String key;
            if (maxToReturn >= 0) {
                Map<String, Integer> counts = new HashMap<>();
                for (ArrayList<WeakReference<BinderProxy>> a : this.mMainIndexValues) {
                    if (a != null) {
                        Iterator<WeakReference<BinderProxy>> it = a.iterator();
                        while (it.hasNext()) {
                            BinderProxy bp = (BinderProxy) it.next().get();
                            if (bp == null) {
                                key = "<cleared weak-ref>";
                            } else {
                                try {
                                    key = bp.getInterfaceDescriptor();
                                    if ((key == null || key.isEmpty()) && !bp.isBinderAlive()) {
                                        key = "<proxy to dead node>";
                                    }
                                } catch (Throwable th) {
                                    key = "<exception during getDescriptor>";
                                }
                            }
                            Integer i = counts.get(key);
                            if (i == null) {
                                counts.put(key, 1);
                            } else {
                                counts.put(key, Integer.valueOf(i.intValue() + 1));
                            }
                        }
                    }
                }
                Map.Entry<String, Integer>[] sorted = (Map.Entry[]) counts.entrySet().toArray(new Map.Entry[counts.size()]);
                Arrays.sort(sorted, $$Lambda$BinderProxy$ProxyMap$aKNUVKkR8bNu2XRFxaO2PW1AFBA.INSTANCE);
                int returnCount = Math.min(maxToReturn, sorted.length);
                InterfaceCount[] ifaceCounts = new InterfaceCount[returnCount];
                for (int i2 = 0; i2 < returnCount; i2++) {
                    ifaceCounts[i2] = new InterfaceCount(sorted[i2].getKey(), sorted[i2].getValue().intValue());
                }
                return ifaceCounts;
            }
            throw new IllegalArgumentException("negative interface count");
        }

        /* access modifiers changed from: private */
        public void dumpProxyInterfaceCounts() {
            InterfaceCount[] sorted = getSortedInterfaceCounts(10);
            Log.v("Binder", "BinderProxy descriptor histogram (top " + Integer.toString(10) + "):");
            for (int i = 0; i < sorted.length; i++) {
                Log.v("Binder", " #" + (i + 1) + ": " + sorted[i]);
            }
        }

        /* access modifiers changed from: private */
        public void dumpPerUidProxyCounts() {
            SparseIntArray counts = BinderInternal.nGetBinderProxyPerUidCounts();
            if (counts.size() != 0) {
                Log.d("Binder", "Per Uid Binder Proxy Counts:");
                for (int i = 0; i < counts.size(); i++) {
                    int uid = counts.keyAt(i);
                    int binderCount = counts.valueAt(i);
                    Log.d("Binder", "UID : " + uid + "  count = " + binderCount);
                }
            }
        }
    }

    public static final class InterfaceCount {
        private final int mCount;
        private final String mInterfaceName;

        InterfaceCount(String interfaceName, int count) {
            this.mInterfaceName = interfaceName;
            this.mCount = count;
        }

        public String toString() {
            return this.mInterfaceName + " x" + Integer.toString(this.mCount);
        }
    }

    public static InterfaceCount[] getSortedInterfaceCounts(int num) {
        InterfaceCount[] access$100;
        synchronized (sProxyMap) {
            access$100 = sProxyMap.getSortedInterfaceCounts(num);
        }
        return access$100;
    }

    public static int getProxyCount() {
        int access$200;
        synchronized (sProxyMap) {
            access$200 = sProxyMap.size();
        }
        return access$200;
    }

    public static void dumpProxyDebugInfo() {
        if (Build.IS_DEBUGGABLE) {
            synchronized (sProxyMap) {
                sProxyMap.dumpProxyInterfaceCounts();
                sProxyMap.dumpPerUidProxyCounts();
            }
        }
    }

    private static BinderProxy getInstance(long nativeData, long iBinder) {
        synchronized (sProxyMap) {
            try {
                BinderProxy result = sProxyMap.get(iBinder);
                if (result != null) {
                    return result;
                }
                BinderProxy result2 = new BinderProxy(nativeData);
                NoImagePreloadHolder.sRegistry.registerNativeAllocation(result2, nativeData);
                sProxyMap.set(iBinder, result2);
                return result2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private BinderProxy(long nativeData) {
        this.mNativeData = nativeData;
    }

    private static class NoImagePreloadHolder {
        public static final long sNativeFinalizer = BinderProxy.getNativeFinalizer();
        public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(BinderProxy.class.getClassLoader(), sNativeFinalizer, 1000);

        private NoImagePreloadHolder() {
        }
    }

    public IInterface queryLocalInterface(String descriptor) {
        return null;
    }

    public boolean transact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        Binder.checkParcel(this, code, data, "Unreasonably large binder buffer");
        if (this.mWarnOnBlocking && (flags & 1) == 0) {
            this.mWarnOnBlocking = false;
            Log.w("Binder", "Outgoing transactions from this process must be FLAG_ONEWAY", new Throwable());
        }
        boolean tracingEnabled = Binder.isTracingEnabled();
        if (tracingEnabled) {
            Throwable tr = new Throwable();
            Binder.getTransactionTracker().addTrace(tr);
            StackTraceElement stackTraceElement = tr.getStackTrace()[1];
            Trace.traceBegin(1, stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName());
        }
        Binder.ProxyTransactListener transactListener = sTransactListener;
        Object session = null;
        if (transactListener != null) {
            int origWorkSourceUid = Binder.getCallingWorkSourceUid();
            session = transactListener.onTransactStarted(this, code);
            int updatedWorkSourceUid = Binder.getCallingWorkSourceUid();
            if (origWorkSourceUid != updatedWorkSourceUid) {
                data.replaceCallingWorkSourceUid(updatedWorkSourceUid);
            }
        }
        try {
            return transactNative(code, data, reply, flags);
        } finally {
            if (transactListener != null) {
                transactListener.onTransactEnded(session);
            }
            if (tracingEnabled) {
                Trace.traceEnd(1);
            }
        }
    }

    public void dump(FileDescriptor fd, String[] args) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeFileDescriptor(fd);
        data.writeStringArray(args);
        try {
            transact(IBinder.DUMP_TRANSACTION, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void dumpAsync(FileDescriptor fd, String[] args) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeFileDescriptor(fd);
        data.writeStringArray(args);
        try {
            transact(IBinder.DUMP_TRANSACTION, data, reply, 1);
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    public void shellCommand(FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, ResultReceiver resultReceiver) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeFileDescriptor(in);
        data.writeFileDescriptor(out);
        data.writeFileDescriptor(err);
        data.writeStringArray(args);
        ShellCallback.writeToParcel(callback, data);
        resultReceiver.writeToParcel(data, 0);
        try {
            transact(IBinder.SHELL_COMMAND_TRANSACTION, data, reply, 0);
            reply.readException();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    private static void sendDeathNotice(IBinder.DeathRecipient recipient) {
        try {
            recipient.binderDied();
        } catch (RuntimeException exc) {
            Log.w("BinderNative", "Uncaught exception from death notification", exc);
        }
    }
}
