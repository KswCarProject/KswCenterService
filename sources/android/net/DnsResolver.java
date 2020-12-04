package android.net;

import android.net.DnsPacket;
import android.net.DnsResolver;
import android.net.util.DnsUtils;
import android.os.CancellationSignal;
import android.os.Looper;
import android.os.MessageQueue;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.util.Log;
import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class DnsResolver {
    public static final int CLASS_IN = 1;
    public static final int ERROR_PARSE = 0;
    public static final int ERROR_SYSTEM = 1;
    private static final int FD_EVENTS = 5;
    public static final int FLAG_EMPTY = 0;
    public static final int FLAG_NO_CACHE_LOOKUP = 4;
    public static final int FLAG_NO_CACHE_STORE = 2;
    public static final int FLAG_NO_RETRY = 1;
    private static final int MAXPACKET = 8192;
    private static final int NETID_UNSET = 0;
    private static final int SLEEP_TIME_MS = 2;
    private static final String TAG = "DnsResolver";
    public static final int TYPE_A = 1;
    public static final int TYPE_AAAA = 28;
    private static final DnsResolver sInstance = new DnsResolver();

    public interface Callback<T> {
        void onAnswer(T t, int i);

        void onError(DnsException dnsException);
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface DnsError {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface QueryClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface QueryFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface QueryType {
    }

    public static DnsResolver getInstance() {
        return sInstance;
    }

    private DnsResolver() {
    }

    public static class DnsException extends Exception {
        public final int code;

        DnsException(int code2, Throwable cause) {
            super(cause);
            this.code = code2;
        }
    }

    public void rawQuery(Network network, byte[] query, int flags, Executor executor, CancellationSignal cancellationSignal, Callback<? super byte[]> callback) {
        int i;
        if (cancellationSignal == null || !cancellationSignal.isCanceled()) {
            Object lock = new Object();
            if (network != null) {
                try {
                    i = network.getNetIdForResolv();
                } catch (ErrnoException e) {
                    executor.execute(new Runnable(e) {
                        private final /* synthetic */ ErrnoException f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                        }
                    });
                    return;
                }
            } else {
                i = 0;
            }
            FileDescriptor queryfd = NetworkUtils.resNetworkSend(i, query, query.length, flags);
            synchronized (lock) {
                registerFDListener(executor, queryfd, callback, cancellationSignal, lock);
                if (cancellationSignal != null) {
                    addCancellationSignal(cancellationSignal, queryfd, lock);
                }
            }
        }
    }

    public void rawQuery(Network network, String domain, int nsClass, int nsType, int flags, Executor executor, CancellationSignal cancellationSignal, Callback<? super byte[]> callback) {
        int i;
        CancellationSignal cancellationSignal2 = cancellationSignal;
        if (cancellationSignal2 == null || !cancellationSignal.isCanceled()) {
            Object lock = new Object();
            if (network != null) {
                try {
                    i = network.getNetIdForResolv();
                } catch (ErrnoException e) {
                    e = e;
                    String str = domain;
                    int i2 = nsClass;
                    int i3 = nsType;
                    int i4 = flags;
                    executor.execute(new Runnable(e) {
                        private final /* synthetic */ ErrnoException f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                        }
                    });
                }
            } else {
                i = 0;
            }
            try {
                FileDescriptor queryfd = NetworkUtils.resNetworkQuery(i, domain, nsClass, nsType, flags);
                synchronized (lock) {
                    try {
                        registerFDListener(executor, queryfd, callback, cancellationSignal, lock);
                        if (cancellationSignal2 != null) {
                            addCancellationSignal(cancellationSignal2, queryfd, lock);
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (ErrnoException e2) {
                e = e2;
                executor.execute(new Runnable(e) {
                    private final /* synthetic */ ErrnoException f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                    }
                });
            }
        }
    }

    private class InetAddressAnswerAccumulator implements Callback<byte[]> {
        private final List<InetAddress> mAllAnswers;
        private DnsException mDnsException;
        private final Network mNetwork;
        private int mRcode;
        private int mReceivedAnswerCount = 0;
        private final int mTargetAnswerCount;
        private final Callback<? super List<InetAddress>> mUserCallback;

        InetAddressAnswerAccumulator(Network network, int size, Callback<? super List<InetAddress>> callback) {
            this.mNetwork = network;
            this.mTargetAnswerCount = size;
            this.mAllAnswers = new ArrayList();
            this.mUserCallback = callback;
        }

        private boolean maybeReportError() {
            if (this.mRcode != 0) {
                this.mUserCallback.onAnswer(this.mAllAnswers, this.mRcode);
                return true;
            } else if (this.mDnsException == null) {
                return false;
            } else {
                this.mUserCallback.onError(this.mDnsException);
                return true;
            }
        }

        private void maybeReportAnswer() {
            int i = this.mReceivedAnswerCount + 1;
            this.mReceivedAnswerCount = i;
            if (i == this.mTargetAnswerCount) {
                if (!this.mAllAnswers.isEmpty() || !maybeReportError()) {
                    this.mUserCallback.onAnswer(DnsUtils.rfc6724Sort(this.mNetwork, this.mAllAnswers), this.mRcode);
                }
            }
        }

        public void onAnswer(byte[] answer, int rcode) {
            if (this.mReceivedAnswerCount == 0 || rcode == 0) {
                this.mRcode = rcode;
            }
            try {
                this.mAllAnswers.addAll(new DnsAddressAnswer(answer).getAddresses());
            } catch (ParseException e) {
                this.mDnsException = new DnsException(0, e);
            }
            maybeReportAnswer();
        }

        public void onError(DnsException error) {
            this.mDnsException = error;
            maybeReportAnswer();
        }
    }

    public void query(Network network, String domain, int flags, Executor executor, CancellationSignal cancellationSignal, Callback<? super List<InetAddress>> callback) {
        Network network2;
        FileDescriptor v6fd;
        FileDescriptor v4fd;
        int queryCount;
        CancellationSignal cancellationSignal2;
        String str = domain;
        int i = flags;
        Executor executor2 = executor;
        CancellationSignal cancellationSignal3 = cancellationSignal;
        Callback<? super List<InetAddress>> callback2 = callback;
        if (cancellationSignal3 == null || !cancellationSignal.isCanceled()) {
            Object lock = new Object();
            if (network != null) {
                network2 = network;
            } else {
                try {
                    network2 = NetworkUtils.getDnsNetwork();
                } catch (ErrnoException e) {
                    CancellationSignal cancellationSignal4 = cancellationSignal3;
                    executor2.execute(new Runnable(e) {
                        private final /* synthetic */ ErrnoException f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                        }
                    });
                    return;
                }
            }
            Network queryNetwork = network2;
            boolean queryIpv6 = DnsUtils.haveIpv6(queryNetwork);
            boolean queryIpv4 = DnsUtils.haveIpv4(queryNetwork);
            if (queryIpv6 || queryIpv4) {
                int queryCount2 = 0;
                if (queryIpv6) {
                    try {
                        queryCount2 = 0 + 1;
                        v6fd = NetworkUtils.resNetworkQuery(queryNetwork.getNetIdForResolv(), str, 1, 28, i);
                    } catch (ErrnoException e2) {
                        executor2.execute(new Runnable(e2) {
                            private final /* synthetic */ ErrnoException f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                            }
                        });
                        return;
                    }
                } else {
                    v6fd = null;
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e3) {
                    InterruptedException interruptedException = e3;
                    Thread.currentThread().interrupt();
                }
                if (queryIpv4) {
                    try {
                        v4fd = NetworkUtils.resNetworkQuery(queryNetwork.getNetIdForResolv(), str, 1, 1, i);
                        queryCount = queryCount2 + 1;
                    } catch (ErrnoException e4) {
                        if (queryIpv6) {
                            NetworkUtils.resNetworkCancel(v6fd);
                        }
                        executor2.execute(new Runnable(e4) {
                            private final /* synthetic */ ErrnoException f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                            }
                        });
                        return;
                    }
                } else {
                    queryCount = queryCount2;
                    v4fd = null;
                }
                InetAddressAnswerAccumulator accumulator = new InetAddressAnswerAccumulator(queryNetwork, queryCount, callback2);
                synchronized (lock) {
                    if (queryIpv6) {
                        try {
                            registerFDListener(executor, v6fd, accumulator, cancellationSignal, lock);
                        } catch (Throwable th) {
                            th = th;
                            int i2 = queryCount;
                            Network network3 = queryNetwork;
                            Callback<? super List<InetAddress>> callback3 = callback2;
                            CancellationSignal cancellationSignal5 = cancellationSignal3;
                        }
                    }
                    if (queryIpv4) {
                        int i3 = queryCount;
                        Network network4 = queryNetwork;
                        Callback<? super List<InetAddress>> callback4 = callback2;
                        cancellationSignal2 = cancellationSignal3;
                        try {
                            registerFDListener(executor, v4fd, accumulator, cancellationSignal, lock);
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    } else {
                        Network network5 = queryNetwork;
                        Callback<? super List<InetAddress>> callback5 = callback2;
                        cancellationSignal2 = cancellationSignal3;
                    }
                    if (cancellationSignal2 != null) {
                        cancellationSignal2.setOnCancelListener(new CancellationSignal.OnCancelListener(lock, queryIpv4, v4fd, queryIpv6, v6fd) {
                            private final /* synthetic */ Object f$1;
                            private final /* synthetic */ boolean f$2;
                            private final /* synthetic */ FileDescriptor f$3;
                            private final /* synthetic */ boolean f$4;
                            private final /* synthetic */ FileDescriptor f$5;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                                this.f$3 = r4;
                                this.f$4 = r5;
                                this.f$5 = r6;
                            }

                            public final void onCancel() {
                                DnsResolver.lambda$query$6(DnsResolver.this, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
                            }
                        });
                        return;
                    }
                    return;
                }
            }
            executor2.execute(new Runnable() {
                public final void run() {
                    DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, new ErrnoException("resNetworkQuery", OsConstants.ENONET)));
                }
            });
        }
    }

    public static /* synthetic */ void lambda$query$6(DnsResolver dnsResolver, Object lock, boolean queryIpv4, FileDescriptor v4fd, boolean queryIpv6, FileDescriptor v6fd) {
        synchronized (lock) {
            if (queryIpv4) {
                try {
                    dnsResolver.cancelQuery(v4fd);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (queryIpv6) {
                dnsResolver.cancelQuery(v6fd);
            }
        }
    }

    public void query(Network network, String domain, int nsType, int flags, Executor executor, CancellationSignal cancellationSignal, Callback<? super List<InetAddress>> callback) {
        Network network2;
        CancellationSignal cancellationSignal2 = cancellationSignal;
        Callback<? super List<InetAddress>> callback2 = callback;
        if (cancellationSignal2 == null || !cancellationSignal.isCanceled()) {
            Object lock = new Object();
            if (network != null) {
                network2 = network;
            } else {
                try {
                    network2 = NetworkUtils.getDnsNetwork();
                } catch (ErrnoException e) {
                    e = e;
                    String str = domain;
                    int i = nsType;
                    int i2 = flags;
                    executor.execute(new Runnable(e) {
                        private final /* synthetic */ ErrnoException f$1;

                        {
                            this.f$1 = r2;
                        }

                        public final void run() {
                            DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                        }
                    });
                }
            }
            Network queryNetwork = network2;
            try {
                FileDescriptor queryfd = NetworkUtils.resNetworkQuery(queryNetwork.getNetIdForResolv(), domain, 1, nsType, flags);
                InetAddressAnswerAccumulator accumulator = new InetAddressAnswerAccumulator(queryNetwork, 1, callback2);
                synchronized (lock) {
                    FileDescriptor queryfd2 = queryfd;
                    registerFDListener(executor, queryfd, accumulator, cancellationSignal, lock);
                    if (cancellationSignal2 != null) {
                        addCancellationSignal(cancellationSignal2, queryfd2, lock);
                    }
                }
            } catch (ErrnoException e2) {
                e = e2;
                executor.execute(new Runnable(e) {
                    private final /* synthetic */ ErrnoException f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        DnsResolver.Callback.this.onError(new DnsResolver.DnsException(1, this.f$1));
                    }
                });
            }
        }
    }

    public static final class DnsResponse {
        public final byte[] answerbuf;
        public final int rcode;

        public DnsResponse(byte[] answerbuf2, int rcode2) {
            this.answerbuf = answerbuf2;
            this.rcode = rcode2;
        }
    }

    private void registerFDListener(Executor executor, FileDescriptor queryfd, Callback<? super byte[]> answerCallback, CancellationSignal cancellationSignal, Object lock) {
        MessageQueue mainThreadMessageQueue = Looper.getMainLooper().getQueue();
        mainThreadMessageQueue.addOnFileDescriptorEventListener(queryfd, 5, new MessageQueue.OnFileDescriptorEventListener(executor, lock, cancellationSignal, answerCallback) {
            private final /* synthetic */ Executor f$1;
            private final /* synthetic */ Object f$2;
            private final /* synthetic */ CancellationSignal f$3;
            private final /* synthetic */ DnsResolver.Callback f$4;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i) {
                return DnsResolver.lambda$registerFDListener$9(MessageQueue.this, this.f$1, this.f$2, this.f$3, this.f$4, fileDescriptor, i);
            }
        });
    }

    static /* synthetic */ int lambda$registerFDListener$9(MessageQueue mainThreadMessageQueue, Executor executor, Object lock, CancellationSignal cancellationSignal, Callback answerCallback, FileDescriptor fd, int events) {
        mainThreadMessageQueue.removeOnFileDescriptorEventListener(fd);
        executor.execute(new Runnable(lock, cancellationSignal, fd, answerCallback) {
            private final /* synthetic */ Object f$0;
            private final /* synthetic */ CancellationSignal f$1;
            private final /* synthetic */ FileDescriptor f$2;
            private final /* synthetic */ DnsResolver.Callback f$3;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                DnsResolver.lambda$registerFDListener$8(this.f$0, this.f$1, this.f$2, this.f$3);
            }
        });
        return 0;
    }

    static /* synthetic */ void lambda$registerFDListener$8(Object lock, CancellationSignal cancellationSignal, FileDescriptor fd, Callback answerCallback) {
        DnsResponse resp = null;
        ErrnoException exception = null;
        synchronized (lock) {
            if (cancellationSignal != null) {
                if (cancellationSignal.isCanceled()) {
                    return;
                }
            }
            try {
                resp = NetworkUtils.resNetworkResult(fd);
            } catch (ErrnoException e) {
                Log.e(TAG, "resNetworkResult:" + e.toString());
                exception = e;
            }
        }
        if (exception != null) {
            answerCallback.onError(new DnsException(1, exception));
        } else {
            answerCallback.onAnswer(resp.answerbuf, resp.rcode);
        }
    }

    private void cancelQuery(FileDescriptor queryfd) {
        if (queryfd.valid()) {
            Looper.getMainLooper().getQueue().removeOnFileDescriptorEventListener(queryfd);
            NetworkUtils.resNetworkCancel(queryfd);
        }
    }

    private void addCancellationSignal(CancellationSignal cancellationSignal, FileDescriptor queryfd, Object lock) {
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(lock, queryfd) {
            private final /* synthetic */ Object f$1;
            private final /* synthetic */ FileDescriptor f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void onCancel() {
                DnsResolver.lambda$addCancellationSignal$10(DnsResolver.this, this.f$1, this.f$2);
            }
        });
    }

    public static /* synthetic */ void lambda$addCancellationSignal$10(DnsResolver dnsResolver, Object lock, FileDescriptor queryfd) {
        synchronized (lock) {
            dnsResolver.cancelQuery(queryfd);
        }
    }

    private static class DnsAddressAnswer extends DnsPacket {
        private static final boolean DBG = false;
        private static final String TAG = "DnsResolver.DnsAddressAnswer";
        private final int mQueryType;

        DnsAddressAnswer(byte[] data) throws ParseException {
            super(data);
            if ((this.mHeader.flags & 32768) == 0) {
                throw new ParseException("Not an answer packet");
            } else if (this.mHeader.getRecordCount(0) != 0) {
                this.mQueryType = ((DnsPacket.DnsRecord) this.mRecords[0].get(0)).nsType;
            } else {
                throw new ParseException("No question found");
            }
        }

        public List<InetAddress> getAddresses() {
            List<InetAddress> results = new ArrayList<>();
            if (this.mHeader.getRecordCount(1) == 0) {
                return results;
            }
            for (DnsPacket.DnsRecord ansSec : this.mRecords[1]) {
                int nsType = ansSec.nsType;
                if (nsType == this.mQueryType && (nsType == 1 || nsType == 28)) {
                    try {
                        results.add(InetAddress.getByAddress(ansSec.getRR()));
                    } catch (UnknownHostException e) {
                    }
                }
            }
            return results;
        }
    }
}
