package android.view.textservice;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.textservice.ISpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesSessionListener;
import dalvik.system.CloseGuard;
import java.util.LinkedList;
import java.util.Queue;

public class SpellCheckerSession {
    private static final boolean DBG = false;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE = 1;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE_FOR_SENTENCE = 2;
    public static final String SERVICE_META_DATA = "android.view.textservice.scs";
    /* access modifiers changed from: private */
    public static final String TAG = SpellCheckerSession.class.getSimpleName();
    private final CloseGuard mGuard = CloseGuard.get();
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SpellCheckerSession.this.handleOnGetSuggestionsMultiple((SuggestionsInfo[]) msg.obj);
                    return;
                case 2:
                    SpellCheckerSession.this.handleOnGetSentenceSuggestionsMultiple((SentenceSuggestionsInfo[]) msg.obj);
                    return;
                default:
                    return;
            }
        }
    };
    private final InternalListener mInternalListener;
    private final SpellCheckerInfo mSpellCheckerInfo;
    @UnsupportedAppUsage
    private final SpellCheckerSessionListener mSpellCheckerSessionListener;
    private final SpellCheckerSessionListenerImpl mSpellCheckerSessionListenerImpl;
    private final TextServicesManager mTextServicesManager;

    public interface SpellCheckerSessionListener {
        void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr);

        void onGetSuggestions(SuggestionsInfo[] suggestionsInfoArr);
    }

    public SpellCheckerSession(SpellCheckerInfo info, TextServicesManager tsm, SpellCheckerSessionListener listener) {
        if (info == null || listener == null || tsm == null) {
            throw new NullPointerException();
        }
        this.mSpellCheckerInfo = info;
        this.mSpellCheckerSessionListenerImpl = new SpellCheckerSessionListenerImpl(this.mHandler);
        this.mInternalListener = new InternalListener(this.mSpellCheckerSessionListenerImpl);
        this.mTextServicesManager = tsm;
        this.mSpellCheckerSessionListener = listener;
        this.mGuard.open("finishSession");
    }

    public boolean isSessionDisconnected() {
        return this.mSpellCheckerSessionListenerImpl.isDisconnected();
    }

    public SpellCheckerInfo getSpellChecker() {
        return this.mSpellCheckerInfo;
    }

    public void cancel() {
        this.mSpellCheckerSessionListenerImpl.cancel();
    }

    public void close() {
        this.mGuard.close();
        this.mSpellCheckerSessionListenerImpl.close();
        this.mTextServicesManager.finishSpellCheckerService(this.mSpellCheckerSessionListenerImpl);
    }

    public void getSentenceSuggestions(TextInfo[] textInfos, int suggestionsLimit) {
        this.mSpellCheckerSessionListenerImpl.getSentenceSuggestionsMultiple(textInfos, suggestionsLimit);
    }

    @Deprecated
    public void getSuggestions(TextInfo textInfo, int suggestionsLimit) {
        getSuggestions(new TextInfo[]{textInfo}, suggestionsLimit, false);
    }

    @Deprecated
    public void getSuggestions(TextInfo[] textInfos, int suggestionsLimit, boolean sequentialWords) {
        this.mSpellCheckerSessionListenerImpl.getSuggestionsMultiple(textInfos, suggestionsLimit, sequentialWords);
    }

    /* access modifiers changed from: private */
    public void handleOnGetSuggestionsMultiple(SuggestionsInfo[] suggestionInfos) {
        this.mSpellCheckerSessionListener.onGetSuggestions(suggestionInfos);
    }

    /* access modifiers changed from: private */
    public void handleOnGetSentenceSuggestionsMultiple(SentenceSuggestionsInfo[] suggestionInfos) {
        this.mSpellCheckerSessionListener.onGetSentenceSuggestions(suggestionInfos);
    }

    private static final class SpellCheckerSessionListenerImpl extends ISpellCheckerSessionListener.Stub {
        private static final int STATE_CLOSED_AFTER_CONNECTION = 2;
        private static final int STATE_CLOSED_BEFORE_CONNECTION = 3;
        private static final int STATE_CONNECTED = 1;
        private static final int STATE_WAIT_CONNECTION = 0;
        private static final int TASK_CANCEL = 1;
        private static final int TASK_CLOSE = 3;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE = 2;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE = 4;
        private Handler mAsyncHandler;
        private Handler mHandler;
        private ISpellCheckerSession mISpellCheckerSession;
        private final Queue<SpellCheckerParams> mPendingTasks = new LinkedList();
        private int mState = 0;
        private HandlerThread mThread;

        private static String taskToString(int task) {
            switch (task) {
                case 1:
                    return "TASK_CANCEL";
                case 2:
                    return "TASK_GET_SUGGESTIONS_MULTIPLE";
                case 3:
                    return "TASK_CLOSE";
                case 4:
                    return "TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE";
                default:
                    return "Unexpected task=" + task;
            }
        }

        private static String stateToString(int state) {
            switch (state) {
                case 0:
                    return "STATE_WAIT_CONNECTION";
                case 1:
                    return "STATE_CONNECTED";
                case 2:
                    return "STATE_CLOSED_AFTER_CONNECTION";
                case 3:
                    return "STATE_CLOSED_BEFORE_CONNECTION";
                default:
                    return "Unexpected state=" + state;
            }
        }

        public SpellCheckerSessionListenerImpl(Handler handler) {
            this.mHandler = handler;
        }

        private static class SpellCheckerParams {
            public final boolean mSequentialWords;
            public ISpellCheckerSession mSession;
            public final int mSuggestionsLimit;
            public final TextInfo[] mTextInfos;
            public final int mWhat;

            public SpellCheckerParams(int what, TextInfo[] textInfos, int suggestionsLimit, boolean sequentialWords) {
                this.mWhat = what;
                this.mTextInfos = textInfos;
                this.mSuggestionsLimit = suggestionsLimit;
                this.mSequentialWords = sequentialWords;
            }
        }

        /* access modifiers changed from: private */
        public void processTask(ISpellCheckerSession session, SpellCheckerParams scp, boolean async) {
            if (async || this.mAsyncHandler == null) {
                switch (scp.mWhat) {
                    case 1:
                        try {
                            session.onCancel();
                            break;
                        } catch (RemoteException e) {
                            String access$200 = SpellCheckerSession.TAG;
                            Log.e(access$200, "Failed to cancel " + e);
                            break;
                        }
                    case 2:
                        try {
                            session.onGetSuggestionsMultiple(scp.mTextInfos, scp.mSuggestionsLimit, scp.mSequentialWords);
                            break;
                        } catch (RemoteException e2) {
                            String access$2002 = SpellCheckerSession.TAG;
                            Log.e(access$2002, "Failed to get suggestions " + e2);
                            break;
                        }
                    case 3:
                        try {
                            session.onClose();
                            break;
                        } catch (RemoteException e3) {
                            String access$2003 = SpellCheckerSession.TAG;
                            Log.e(access$2003, "Failed to close " + e3);
                            break;
                        }
                    case 4:
                        try {
                            session.onGetSentenceSuggestionsMultiple(scp.mTextInfos, scp.mSuggestionsLimit);
                            break;
                        } catch (RemoteException e4) {
                            String access$2004 = SpellCheckerSession.TAG;
                            Log.e(access$2004, "Failed to get suggestions " + e4);
                            break;
                        }
                }
            } else {
                scp.mSession = session;
                this.mAsyncHandler.sendMessage(Message.obtain(this.mAsyncHandler, 1, scp));
            }
            if (scp.mWhat == 3) {
                synchronized (this) {
                    processCloseLocked();
                }
            }
        }

        private void processCloseLocked() {
            this.mISpellCheckerSession = null;
            if (this.mThread != null) {
                this.mThread.quit();
            }
            this.mHandler = null;
            this.mPendingTasks.clear();
            this.mThread = null;
            this.mAsyncHandler = null;
            switch (this.mState) {
                case 0:
                    this.mState = 3;
                    return;
                case 1:
                    this.mState = 2;
                    return;
                default:
                    String access$200 = SpellCheckerSession.TAG;
                    Log.e(access$200, "processCloseLocked is called unexpectedly. mState=" + stateToString(this.mState));
                    return;
            }
        }

        public void onServiceConnected(ISpellCheckerSession session) {
            synchronized (this) {
                int i = this.mState;
                if (i != 0) {
                    if (i != 3) {
                        String access$200 = SpellCheckerSession.TAG;
                        Log.e(access$200, "ignoring onServiceConnected due to unexpected mState=" + stateToString(this.mState));
                    }
                } else if (session == null) {
                    Log.e(SpellCheckerSession.TAG, "ignoring onServiceConnected due to session=null");
                } else {
                    this.mISpellCheckerSession = session;
                    if ((session.asBinder() instanceof Binder) && this.mThread == null) {
                        this.mThread = new HandlerThread("SpellCheckerSession", 10);
                        this.mThread.start();
                        this.mAsyncHandler = new Handler(this.mThread.getLooper()) {
                            public void handleMessage(Message msg) {
                                SpellCheckerParams scp = (SpellCheckerParams) msg.obj;
                                SpellCheckerSessionListenerImpl.this.processTask(scp.mSession, scp, true);
                            }
                        };
                    }
                    this.mState = 1;
                    while (!this.mPendingTasks.isEmpty()) {
                        processTask(session, this.mPendingTasks.poll(), false);
                    }
                }
            }
        }

        public void cancel() {
            processOrEnqueueTask(new SpellCheckerParams(1, (TextInfo[]) null, 0, false));
        }

        public void getSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit, boolean sequentialWords) {
            processOrEnqueueTask(new SpellCheckerParams(2, textInfos, suggestionsLimit, sequentialWords));
        }

        public void getSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) {
            processOrEnqueueTask(new SpellCheckerParams(4, textInfos, suggestionsLimit, false));
        }

        public void close() {
            processOrEnqueueTask(new SpellCheckerParams(3, (TextInfo[]) null, 0, false));
        }

        public boolean isDisconnected() {
            boolean z;
            synchronized (this) {
                z = true;
                if (this.mState == 1) {
                    z = false;
                }
            }
            return z;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:36:0x007d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void processOrEnqueueTask(android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams r5) {
            /*
                r4 = this;
                monitor-enter(r4)
                int r0 = r5.mWhat     // Catch:{ all -> 0x0086 }
                r1 = 3
                if (r0 != r1) goto L_0x0011
                int r0 = r4.mState     // Catch:{ all -> 0x0086 }
                r2 = 2
                if (r0 == r2) goto L_0x000f
                int r0 = r4.mState     // Catch:{ all -> 0x0086 }
                if (r0 != r1) goto L_0x0011
            L_0x000f:
                monitor-exit(r4)     // Catch:{ all -> 0x0086 }
                return
            L_0x0011:
                int r0 = r4.mState     // Catch:{ all -> 0x0086 }
                r2 = 1
                if (r0 == 0) goto L_0x0048
                int r0 = r4.mState     // Catch:{ all -> 0x0086 }
                if (r0 == r2) goto L_0x0048
                java.lang.String r0 = android.view.textservice.SpellCheckerSession.TAG     // Catch:{ all -> 0x0086 }
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0086 }
                r1.<init>()     // Catch:{ all -> 0x0086 }
                java.lang.String r2 = "ignoring processOrEnqueueTask due to unexpected mState="
                r1.append(r2)     // Catch:{ all -> 0x0086 }
                int r2 = r4.mState     // Catch:{ all -> 0x0086 }
                java.lang.String r2 = stateToString(r2)     // Catch:{ all -> 0x0086 }
                r1.append(r2)     // Catch:{ all -> 0x0086 }
                java.lang.String r2 = " scp.mWhat="
                r1.append(r2)     // Catch:{ all -> 0x0086 }
                int r2 = r5.mWhat     // Catch:{ all -> 0x0086 }
                java.lang.String r2 = taskToString(r2)     // Catch:{ all -> 0x0086 }
                r1.append(r2)     // Catch:{ all -> 0x0086 }
                java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0086 }
                android.util.Log.e(r0, r1)     // Catch:{ all -> 0x0086 }
                monitor-exit(r4)     // Catch:{ all -> 0x0086 }
                return
            L_0x0048:
                int r0 = r4.mState     // Catch:{ all -> 0x0086 }
                if (r0 != 0) goto L_0x007e
                int r0 = r5.mWhat     // Catch:{ all -> 0x0086 }
                if (r0 != r1) goto L_0x0055
                r4.processCloseLocked()     // Catch:{ all -> 0x0086 }
                monitor-exit(r4)     // Catch:{ all -> 0x0086 }
                return
            L_0x0055:
                r0 = 0
                int r3 = r5.mWhat     // Catch:{ all -> 0x0086 }
                if (r3 != r2) goto L_0x0070
            L_0x005a:
                java.util.Queue<android.view.textservice.SpellCheckerSession$SpellCheckerSessionListenerImpl$SpellCheckerParams> r2 = r4.mPendingTasks     // Catch:{ all -> 0x0086 }
                boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0086 }
                if (r2 != 0) goto L_0x0070
                java.util.Queue<android.view.textservice.SpellCheckerSession$SpellCheckerSessionListenerImpl$SpellCheckerParams> r2 = r4.mPendingTasks     // Catch:{ all -> 0x0086 }
                java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0086 }
                android.view.textservice.SpellCheckerSession$SpellCheckerSessionListenerImpl$SpellCheckerParams r2 = (android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams) r2     // Catch:{ all -> 0x0086 }
                int r3 = r2.mWhat     // Catch:{ all -> 0x0086 }
                if (r3 != r1) goto L_0x006f
                r0 = r2
            L_0x006f:
                goto L_0x005a
            L_0x0070:
                java.util.Queue<android.view.textservice.SpellCheckerSession$SpellCheckerSessionListenerImpl$SpellCheckerParams> r1 = r4.mPendingTasks     // Catch:{ all -> 0x0086 }
                r1.offer(r5)     // Catch:{ all -> 0x0086 }
                if (r0 == 0) goto L_0x007c
                java.util.Queue<android.view.textservice.SpellCheckerSession$SpellCheckerSessionListenerImpl$SpellCheckerParams> r1 = r4.mPendingTasks     // Catch:{ all -> 0x0086 }
                r1.offer(r0)     // Catch:{ all -> 0x0086 }
            L_0x007c:
                monitor-exit(r4)     // Catch:{ all -> 0x0086 }
                return
            L_0x007e:
                com.android.internal.textservice.ISpellCheckerSession r0 = r4.mISpellCheckerSession     // Catch:{ all -> 0x0086 }
                monitor-exit(r4)     // Catch:{ all -> 0x0086 }
                r1 = 0
                r4.processTask(r0, r5, r1)
                return
            L_0x0086:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0086 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.processOrEnqueueTask(android.view.textservice.SpellCheckerSession$SpellCheckerSessionListenerImpl$SpellCheckerParams):void");
        }

        public void onGetSuggestions(SuggestionsInfo[] results) {
            synchronized (this) {
                if (this.mHandler != null) {
                    this.mHandler.sendMessage(Message.obtain(this.mHandler, 1, results));
                }
            }
        }

        public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
            synchronized (this) {
                if (this.mHandler != null) {
                    this.mHandler.sendMessage(Message.obtain(this.mHandler, 2, results));
                }
            }
        }
    }

    private static final class InternalListener extends ITextServicesSessionListener.Stub {
        private final SpellCheckerSessionListenerImpl mParentSpellCheckerSessionListenerImpl;

        public InternalListener(SpellCheckerSessionListenerImpl spellCheckerSessionListenerImpl) {
            this.mParentSpellCheckerSessionListenerImpl = spellCheckerSessionListenerImpl;
        }

        public void onServiceConnected(ISpellCheckerSession session) {
            this.mParentSpellCheckerSessionListenerImpl.onServiceConnected(session);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public ITextServicesSessionListener getTextServicesSessionListener() {
        return this.mInternalListener;
    }

    public ISpellCheckerSessionListener getSpellCheckerSessionListener() {
        return this.mSpellCheckerSessionListenerImpl;
    }
}
