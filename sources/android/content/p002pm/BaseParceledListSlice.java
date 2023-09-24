package android.content.p002pm;

import android.annotation.UnsupportedAppUsage;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import android.util.Log;
import com.ibm.icu.text.PluralRules;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.content.pm.BaseParceledListSlice */
/* loaded from: classes.dex */
abstract class BaseParceledListSlice<T> implements Parcelable {
    private static final int MAX_IPC_SIZE = 65536;
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private final List<T> mList;
    private static String TAG = "ParceledListSlice";
    private static boolean DEBUG = false;

    protected abstract Parcelable.Creator<?> readParcelableCreator(Parcel parcel, ClassLoader classLoader);

    protected abstract void writeElement(T t, Parcel parcel, int i);

    @UnsupportedAppUsage
    protected abstract void writeParcelableCreator(T t, Parcel parcel);

    public BaseParceledListSlice(List<T> list) {
        this.mList = list;
    }

    BaseParceledListSlice(Parcel p, ClassLoader loader) {
        int N = p.readInt();
        this.mList = new ArrayList(N);
        if (DEBUG) {
            Log.m72d(TAG, "Retrieving " + N + " items");
        }
        if (N <= 0) {
            return;
        }
        Parcelable.Creator<?> creator = readParcelableCreator(p, loader);
        int i = 0;
        Class<?> listElementClass = null;
        int i2 = 0;
        while (i2 < N && p.readInt() != 0) {
            T parcelable = readCreator(creator, p, loader);
            if (listElementClass == null) {
                listElementClass = parcelable.getClass();
            } else {
                verifySameType(listElementClass, parcelable.getClass());
            }
            this.mList.add(parcelable);
            if (DEBUG) {
                Log.m72d(TAG, "Read inline #" + i2 + PluralRules.KEYWORD_RULE_SEPARATOR + this.mList.get(this.mList.size() - 1));
            }
            i2++;
        }
        if (i2 >= N) {
            return;
        }
        IBinder retriever = p.readStrongBinder();
        int i3 = i2;
        while (i3 < N) {
            if (DEBUG) {
                Log.m72d(TAG, "Reading more @" + i3 + " of " + N + ": retriever=" + retriever);
            }
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInt(i3);
            try {
                retriever.transact(1, data, reply, i);
                while (i3 < N && reply.readInt() != 0) {
                    T parcelable2 = readCreator(creator, reply, loader);
                    verifySameType(listElementClass, parcelable2.getClass());
                    this.mList.add(parcelable2);
                    if (DEBUG) {
                        Log.m72d(TAG, "Read extra #" + i3 + PluralRules.KEYWORD_RULE_SEPARATOR + this.mList.get(this.mList.size() - 1));
                    }
                    i3++;
                }
                reply.recycle();
                data.recycle();
                i = 0;
            } catch (RemoteException e) {
                Log.m63w(TAG, "Failure retrieving array; only received " + i3 + " of " + N, e);
                return;
            }
        }
    }

    private T readCreator(Parcelable.Creator<?> creator, Parcel p, ClassLoader loader) {
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            Parcelable.ClassLoaderCreator<?> classLoaderCreator = (Parcelable.ClassLoaderCreator) creator;
            return (T) classLoaderCreator.createFromParcel(p, loader);
        }
        return (T) creator.createFromParcel(p);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void verifySameType(Class<?> expected, Class<?> actual) {
        if (!actual.equals(expected)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can't unparcel type ");
            sb.append(actual == null ? null : actual.getName());
            sb.append(" in list of type ");
            sb.append(expected != null ? expected.getName() : null);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @UnsupportedAppUsage
    public List<T> getList() {
        return this.mList;
    }

    public void setInlineCountLimit(int maxCount) {
        this.mInlineCountLimit = maxCount;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x008f, code lost:
        r10.writeInt(0);
        r3 = new android.content.p002pm.BaseParceledListSlice.BinderC05751(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0099, code lost:
        if (android.content.p002pm.BaseParceledListSlice.DEBUG == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x009b, code lost:
        android.util.Log.m72d(android.content.p002pm.BaseParceledListSlice.TAG, "Breaking @" + r4 + " of " + r0 + ": retriever=" + r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00c1, code lost:
        r10.writeStrongBinder(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00c4, code lost:
        return;
     */
    @Override // android.p007os.Parcelable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeToParcel(Parcel dest, final int flags) {
        final int N = this.mList.size();
        dest.writeInt(N);
        if (DEBUG) {
            Log.m72d(TAG, "Writing " + N + " items");
        }
        if (N > 0) {
            final Class<?> listElementClass = this.mList.get(0).getClass();
            writeParcelableCreator(this.mList.get(0), dest);
            int i = 0;
            while (i < N && i < this.mInlineCountLimit && dest.dataSize() < 65536) {
                dest.writeInt(1);
                T parcelable = this.mList.get(i);
                verifySameType(listElementClass, parcelable.getClass());
                writeElement(parcelable, dest, flags);
                if (DEBUG) {
                    Log.m72d(TAG, "Wrote inline #" + i + PluralRules.KEYWORD_RULE_SEPARATOR + this.mList.get(i));
                }
                i++;
            }
        }
    }
}
