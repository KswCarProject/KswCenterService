package android.inputmethodservice;

import android.graphics.Rect;
import android.inputmethodservice.MultiClientInputMethodServiceDelegate;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IMultiClientInputMethodSession;
import com.android.internal.os.SomeArgs;
import com.android.internal.view.IInputMethodSession;
import com.android.internal.view.InputConnectionWrapper;
import java.util.concurrent.atomic.AtomicBoolean;

final class MultiClientInputMethodClientCallbackAdaptor {
    static final boolean DEBUG = false;
    static final String TAG = MultiClientInputMethodClientCallbackAdaptor.class.getSimpleName();
    @GuardedBy({"mSessionLock"})
    CallbackImpl mCallbackImpl;
    @GuardedBy({"mSessionLock"})
    KeyEvent.DispatcherState mDispatcherState;
    private final AtomicBoolean mFinished = new AtomicBoolean(false);
    @GuardedBy({"mSessionLock"})
    Handler mHandler;
    @GuardedBy({"mSessionLock"})
    InputEventReceiver mInputEventReceiver;
    @GuardedBy({"mSessionLock"})
    InputChannel mReadChannel;
    /* access modifiers changed from: private */
    public final Object mSessionLock = new Object();

    /* access modifiers changed from: package-private */
    public IInputMethodSession.Stub createIInputMethodSession() {
        InputMethodSessionImpl inputMethodSessionImpl;
        synchronized (this.mSessionLock) {
            inputMethodSessionImpl = new InputMethodSessionImpl(this.mSessionLock, this.mCallbackImpl, this.mHandler, this.mFinished);
        }
        return inputMethodSessionImpl;
    }

    /* access modifiers changed from: package-private */
    public IMultiClientInputMethodSession.Stub createIMultiClientInputMethodSession() {
        MultiClientInputMethodSessionImpl multiClientInputMethodSessionImpl;
        synchronized (this.mSessionLock) {
            multiClientInputMethodSessionImpl = new MultiClientInputMethodSessionImpl(this.mSessionLock, this.mCallbackImpl, this.mHandler, this.mFinished);
        }
        return multiClientInputMethodSessionImpl;
    }

    MultiClientInputMethodClientCallbackAdaptor(MultiClientInputMethodServiceDelegate.ClientCallback clientCallback, Looper looper, KeyEvent.DispatcherState dispatcherState, InputChannel readChannel) {
        synchronized (this.mSessionLock) {
            this.mCallbackImpl = new CallbackImpl(this, clientCallback);
            this.mDispatcherState = dispatcherState;
            this.mHandler = new Handler(looper, (Handler.Callback) null, true);
            this.mReadChannel = readChannel;
            this.mInputEventReceiver = new ImeInputEventReceiver(this.mReadChannel, this.mHandler.getLooper(), this.mFinished, this.mDispatcherState, this.mCallbackImpl.mOriginalCallback);
        }
    }

    private static final class KeyEventCallbackAdaptor implements KeyEvent.Callback {
        private final MultiClientInputMethodServiceDelegate.ClientCallback mLocalCallback;

        KeyEventCallbackAdaptor(MultiClientInputMethodServiceDelegate.ClientCallback callback) {
            this.mLocalCallback = callback;
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return this.mLocalCallback.onKeyDown(keyCode, event);
        }

        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
            return this.mLocalCallback.onKeyLongPress(keyCode, event);
        }

        public boolean onKeyUp(int keyCode, KeyEvent event) {
            return this.mLocalCallback.onKeyUp(keyCode, event);
        }

        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
            return this.mLocalCallback.onKeyMultiple(keyCode, event);
        }
    }

    private static final class ImeInputEventReceiver extends InputEventReceiver {
        private final MultiClientInputMethodServiceDelegate.ClientCallback mClientCallback;
        private final KeyEvent.DispatcherState mDispatcherState;
        private final AtomicBoolean mFinished;
        private final KeyEventCallbackAdaptor mKeyEventCallbackAdaptor;

        ImeInputEventReceiver(InputChannel readChannel, Looper looper, AtomicBoolean finished, KeyEvent.DispatcherState dispatcherState, MultiClientInputMethodServiceDelegate.ClientCallback callback) {
            super(readChannel, looper);
            this.mFinished = finished;
            this.mDispatcherState = dispatcherState;
            this.mClientCallback = callback;
            this.mKeyEventCallbackAdaptor = new KeyEventCallbackAdaptor(callback);
        }

        public void onInputEvent(InputEvent event) {
            boolean handled;
            if (this.mFinished.get()) {
                finishInputEvent(event, false);
                return;
            }
            boolean handled2 = false;
            try {
                if (event instanceof KeyEvent) {
                    handled = ((KeyEvent) event).dispatch(this.mKeyEventCallbackAdaptor, this.mDispatcherState, this.mKeyEventCallbackAdaptor);
                } else {
                    MotionEvent motionEvent = (MotionEvent) event;
                    if (motionEvent.isFromSource(4)) {
                        handled = this.mClientCallback.onTrackballEvent(motionEvent);
                    } else {
                        handled = this.mClientCallback.onGenericMotionEvent(motionEvent);
                    }
                }
            } finally {
                finishInputEvent(event, handled2);
            }
        }
    }

    private static final class InputMethodSessionImpl extends IInputMethodSession.Stub {
        @GuardedBy({"mSessionLock"})
        private CallbackImpl mCallbackImpl;
        @GuardedBy({"mSessionLock"})
        private Handler mHandler;
        private final AtomicBoolean mSessionFinished;
        private final Object mSessionLock;

        InputMethodSessionImpl(Object lock, CallbackImpl callback, Handler handler, AtomicBoolean sessionFinished) {
            this.mSessionLock = lock;
            this.mCallbackImpl = callback;
            this.mHandler = handler;
            this.mSessionFinished = sessionFinished;
        }

        public void updateExtractedText(int token, ExtractedText text) {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void updateSelection(int r6, int r7, int r8, int r9, int r10, int r11) {
            /*
                r5 = this;
                java.lang.Object r0 = r5.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r5.mCallbackImpl     // Catch:{ all -> 0x002d }
                if (r1 == 0) goto L_0x002b
                android.os.Handler r1 = r5.mHandler     // Catch:{ all -> 0x002d }
                if (r1 != 0) goto L_0x000c
                goto L_0x002b
            L_0x000c:
                com.android.internal.os.SomeArgs r1 = com.android.internal.os.SomeArgs.obtain()     // Catch:{ all -> 0x002d }
                r1.argi1 = r6     // Catch:{ all -> 0x002d }
                r1.argi2 = r7     // Catch:{ all -> 0x002d }
                r1.argi3 = r8     // Catch:{ all -> 0x002d }
                r1.argi4 = r9     // Catch:{ all -> 0x002d }
                r1.argi5 = r10     // Catch:{ all -> 0x002d }
                r1.argi6 = r11     // Catch:{ all -> 0x002d }
                android.os.Handler r2 = r5.mHandler     // Catch:{ all -> 0x002d }
                android.inputmethodservice.-$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc r3 = android.inputmethodservice.$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc.INSTANCE     // Catch:{ all -> 0x002d }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r4 = r5.mCallbackImpl     // Catch:{ all -> 0x002d }
                android.os.Message r3 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r3, r4, r1)     // Catch:{ all -> 0x002d }
                r2.sendMessage(r3)     // Catch:{ all -> 0x002d }
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                return
            L_0x002b:
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                return
            L_0x002d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.InputMethodSessionImpl.updateSelection(int, int, int, int, int, int):void");
        }

        public void viewClicked(boolean focusChanged) {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }

        public void updateCursor(Rect newCursor) {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void displayCompletions(android.view.inputmethod.CompletionInfo[] r5) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r4.mCallbackImpl     // Catch:{ all -> 0x001d }
                if (r1 == 0) goto L_0x001b
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x001d }
                if (r1 != 0) goto L_0x000c
                goto L_0x001b
            L_0x000c:
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x001d }
                android.inputmethodservice.-$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w r2 = android.inputmethodservice.$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w.INSTANCE     // Catch:{ all -> 0x001d }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r4.mCallbackImpl     // Catch:{ all -> 0x001d }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3, r5)     // Catch:{ all -> 0x001d }
                r1.sendMessage(r2)     // Catch:{ all -> 0x001d }
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x001b:
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x001d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.InputMethodSessionImpl.displayCompletions(android.view.inputmethod.CompletionInfo[]):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void appPrivateCommand(java.lang.String r5, android.os.Bundle r6) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r4.mCallbackImpl     // Catch:{ all -> 0x001d }
                if (r1 == 0) goto L_0x001b
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x001d }
                if (r1 != 0) goto L_0x000c
                goto L_0x001b
            L_0x000c:
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x001d }
                android.inputmethodservice.-$$Lambda$nzQNVb4Z0e33hB95nNP1BM-A3r4 r2 = android.inputmethodservice.$$Lambda$nzQNVb4Z0e33hB95nNP1BMA3r4.INSTANCE     // Catch:{ all -> 0x001d }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r4.mCallbackImpl     // Catch:{ all -> 0x001d }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3, r5, r6)     // Catch:{ all -> 0x001d }
                r1.sendMessage(r2)     // Catch:{ all -> 0x001d }
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x001b:
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x001d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.InputMethodSessionImpl.appPrivateCommand(java.lang.String, android.os.Bundle):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void toggleSoftInput(int r7, int r8) {
            /*
                r6 = this;
                java.lang.Object r0 = r6.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r6.mCallbackImpl     // Catch:{ all -> 0x0025 }
                if (r1 == 0) goto L_0x0023
                android.os.Handler r1 = r6.mHandler     // Catch:{ all -> 0x0025 }
                if (r1 != 0) goto L_0x000c
                goto L_0x0023
            L_0x000c:
                android.os.Handler r1 = r6.mHandler     // Catch:{ all -> 0x0025 }
                android.inputmethodservice.-$$Lambda$GapYa6Lyify6RwP-rgkklzmDV8I r2 = android.inputmethodservice.$$Lambda$GapYa6Lyify6RwPrgkklzmDV8I.INSTANCE     // Catch:{ all -> 0x0025 }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r6.mCallbackImpl     // Catch:{ all -> 0x0025 }
                java.lang.Integer r4 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0025 }
                java.lang.Integer r5 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0025 }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3, r4, r5)     // Catch:{ all -> 0x0025 }
                r1.sendMessage(r2)     // Catch:{ all -> 0x0025 }
                monitor-exit(r0)     // Catch:{ all -> 0x0025 }
                return
            L_0x0023:
                monitor-exit(r0)     // Catch:{ all -> 0x0025 }
                return
            L_0x0025:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0025 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.InputMethodSessionImpl.toggleSoftInput(int, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0027, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void finishSession() {
            /*
                r4 = this;
                java.lang.Object r0 = r4.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r4.mCallbackImpl     // Catch:{ all -> 0x0028 }
                if (r1 == 0) goto L_0x0026
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x0028 }
                if (r1 != 0) goto L_0x000c
                goto L_0x0026
            L_0x000c:
                java.util.concurrent.atomic.AtomicBoolean r1 = r4.mSessionFinished     // Catch:{ all -> 0x0028 }
                r2 = 1
                r1.set(r2)     // Catch:{ all -> 0x0028 }
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x0028 }
                android.inputmethodservice.-$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU r2 = android.inputmethodservice.$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU.INSTANCE     // Catch:{ all -> 0x0028 }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r4.mCallbackImpl     // Catch:{ all -> 0x0028 }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3)     // Catch:{ all -> 0x0028 }
                r1.sendMessage(r2)     // Catch:{ all -> 0x0028 }
                r1 = 0
                r4.mCallbackImpl = r1     // Catch:{ all -> 0x0028 }
                r4.mHandler = r1     // Catch:{ all -> 0x0028 }
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                return
            L_0x0026:
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                return
            L_0x0028:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.InputMethodSessionImpl.finishSession():void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo r5) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r4.mCallbackImpl     // Catch:{ all -> 0x001d }
                if (r1 == 0) goto L_0x001b
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x001d }
                if (r1 != 0) goto L_0x000c
                goto L_0x001b
            L_0x000c:
                android.os.Handler r1 = r4.mHandler     // Catch:{ all -> 0x001d }
                android.inputmethodservice.-$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE r2 = android.inputmethodservice.$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE.INSTANCE     // Catch:{ all -> 0x001d }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r4.mCallbackImpl     // Catch:{ all -> 0x001d }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3, r5)     // Catch:{ all -> 0x001d }
                r1.sendMessage(r2)     // Catch:{ all -> 0x001d }
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x001b:
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x001d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.InputMethodSessionImpl.updateCursorAnchorInfo(android.view.inputmethod.CursorAnchorInfo):void");
        }

        public final void notifyImeHidden() {
            MultiClientInputMethodClientCallbackAdaptor.reportNotSupported();
        }
    }

    private static final class MultiClientInputMethodSessionImpl extends IMultiClientInputMethodSession.Stub {
        @GuardedBy({"mSessionLock"})
        private CallbackImpl mCallbackImpl;
        @GuardedBy({"mSessionLock"})
        private Handler mHandler;
        private final AtomicBoolean mSessionFinished;
        private final Object mSessionLock;

        MultiClientInputMethodSessionImpl(Object lock, CallbackImpl callback, Handler handler, AtomicBoolean sessionFinished) {
            this.mSessionLock = lock;
            this.mCallbackImpl = callback;
            this.mHandler = handler;
            this.mSessionFinished = sessionFinished;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x003a, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void startInputOrWindowGainedFocus(com.android.internal.view.IInputContext r7, int r8, android.view.inputmethod.EditorInfo r9, int r10, int r11, int r12) {
            /*
                r6 = this;
                java.lang.Object r0 = r6.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r6.mCallbackImpl     // Catch:{ all -> 0x003b }
                if (r1 == 0) goto L_0x0039
                android.os.Handler r1 = r6.mHandler     // Catch:{ all -> 0x003b }
                if (r1 != 0) goto L_0x000c
                goto L_0x0039
            L_0x000c:
                com.android.internal.os.SomeArgs r1 = com.android.internal.os.SomeArgs.obtain()     // Catch:{ all -> 0x003b }
                java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x003b }
                r3 = 0
                r2.<init>(r3)     // Catch:{ all -> 0x003b }
                if (r7 != 0) goto L_0x0019
                goto L_0x0020
            L_0x0019:
                com.android.internal.view.InputConnectionWrapper r3 = new com.android.internal.view.InputConnectionWrapper     // Catch:{ all -> 0x003b }
                java.util.concurrent.atomic.AtomicBoolean r4 = r6.mSessionFinished     // Catch:{ all -> 0x003b }
                r3.<init>(r2, r7, r8, r4)     // Catch:{ all -> 0x003b }
            L_0x0020:
                r1.arg1 = r3     // Catch:{ all -> 0x003b }
                r1.arg2 = r9     // Catch:{ all -> 0x003b }
                r1.argi1 = r10     // Catch:{ all -> 0x003b }
                r1.argi2 = r11     // Catch:{ all -> 0x003b }
                r1.argi3 = r12     // Catch:{ all -> 0x003b }
                android.os.Handler r3 = r6.mHandler     // Catch:{ all -> 0x003b }
                android.inputmethodservice.-$$Lambda$Xt9K6cDxkSefTfR7zi9ni-dRFZ8 r4 = android.inputmethodservice.$$Lambda$Xt9K6cDxkSefTfR7zi9nidRFZ8.INSTANCE     // Catch:{ all -> 0x003b }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r5 = r6.mCallbackImpl     // Catch:{ all -> 0x003b }
                android.os.Message r4 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r4, r5, r1)     // Catch:{ all -> 0x003b }
                r3.sendMessage(r4)     // Catch:{ all -> 0x003b }
                monitor-exit(r0)     // Catch:{ all -> 0x003b }
                return
            L_0x0039:
                monitor-exit(r0)     // Catch:{ all -> 0x003b }
                return
            L_0x003b:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x003b }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.MultiClientInputMethodSessionImpl.startInputOrWindowGainedFocus(com.android.internal.view.IInputContext, int, android.view.inputmethod.EditorInfo, int, int, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void showSoftInput(int r6, android.os.ResultReceiver r7) {
            /*
                r5 = this;
                java.lang.Object r0 = r5.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r5.mCallbackImpl     // Catch:{ all -> 0x0021 }
                if (r1 == 0) goto L_0x001f
                android.os.Handler r1 = r5.mHandler     // Catch:{ all -> 0x0021 }
                if (r1 != 0) goto L_0x000c
                goto L_0x001f
            L_0x000c:
                android.os.Handler r1 = r5.mHandler     // Catch:{ all -> 0x0021 }
                android.inputmethodservice.-$$Lambda$m1uOlwS-mRsg9KSUY6vV9l9ksWc r2 = android.inputmethodservice.$$Lambda$m1uOlwSmRsg9KSUY6vV9l9ksWc.INSTANCE     // Catch:{ all -> 0x0021 }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r5.mCallbackImpl     // Catch:{ all -> 0x0021 }
                java.lang.Integer r4 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0021 }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3, r4, r7)     // Catch:{ all -> 0x0021 }
                r1.sendMessage(r2)     // Catch:{ all -> 0x0021 }
                monitor-exit(r0)     // Catch:{ all -> 0x0021 }
                return
            L_0x001f:
                monitor-exit(r0)     // Catch:{ all -> 0x0021 }
                return
            L_0x0021:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0021 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.MultiClientInputMethodSessionImpl.showSoftInput(int, android.os.ResultReceiver):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void hideSoftInput(int r6, android.os.ResultReceiver r7) {
            /*
                r5 = this;
                java.lang.Object r0 = r5.mSessionLock
                monitor-enter(r0)
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r1 = r5.mCallbackImpl     // Catch:{ all -> 0x0021 }
                if (r1 == 0) goto L_0x001f
                android.os.Handler r1 = r5.mHandler     // Catch:{ all -> 0x0021 }
                if (r1 != 0) goto L_0x000c
                goto L_0x001f
            L_0x000c:
                android.os.Handler r1 = r5.mHandler     // Catch:{ all -> 0x0021 }
                android.inputmethodservice.-$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0 r2 = android.inputmethodservice.$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0.INSTANCE     // Catch:{ all -> 0x0021 }
                android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor$CallbackImpl r3 = r5.mCallbackImpl     // Catch:{ all -> 0x0021 }
                java.lang.Integer r4 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x0021 }
                android.os.Message r2 = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(r2, r3, r4, r7)     // Catch:{ all -> 0x0021 }
                r1.sendMessage(r2)     // Catch:{ all -> 0x0021 }
                monitor-exit(r0)     // Catch:{ all -> 0x0021 }
                return
            L_0x001f:
                monitor-exit(r0)     // Catch:{ all -> 0x0021 }
                return
            L_0x0021:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0021 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor.MultiClientInputMethodSessionImpl.hideSoftInput(int, android.os.ResultReceiver):void");
        }
    }

    private static final class CallbackImpl {
        private final MultiClientInputMethodClientCallbackAdaptor mCallbackAdaptor;
        private boolean mFinished = false;
        /* access modifiers changed from: private */
        public final MultiClientInputMethodServiceDelegate.ClientCallback mOriginalCallback;

        CallbackImpl(MultiClientInputMethodClientCallbackAdaptor callbackAdaptor, MultiClientInputMethodServiceDelegate.ClientCallback callback) {
            this.mCallbackAdaptor = callbackAdaptor;
            this.mOriginalCallback = callback;
        }

        /* access modifiers changed from: package-private */
        public void updateSelection(SomeArgs args) {
            try {
                if (!this.mFinished) {
                    this.mOriginalCallback.onUpdateSelection(args.argi1, args.argi2, args.argi3, args.argi4, args.argi5, args.argi6);
                    args.recycle();
                }
            } finally {
                args.recycle();
            }
        }

        /* access modifiers changed from: package-private */
        public void displayCompletions(CompletionInfo[] completions) {
            if (!this.mFinished) {
                this.mOriginalCallback.onDisplayCompletions(completions);
            }
        }

        /* access modifiers changed from: package-private */
        public void appPrivateCommand(String action, Bundle data) {
            if (!this.mFinished) {
                this.mOriginalCallback.onAppPrivateCommand(action, data);
            }
        }

        /* access modifiers changed from: package-private */
        public void toggleSoftInput(int showFlags, int hideFlags) {
            if (!this.mFinished) {
                this.mOriginalCallback.onToggleSoftInput(showFlags, hideFlags);
            }
        }

        /* access modifiers changed from: package-private */
        public void finishSession() {
            if (!this.mFinished) {
                this.mFinished = true;
                this.mOriginalCallback.onFinishSession();
                synchronized (this.mCallbackAdaptor.mSessionLock) {
                    this.mCallbackAdaptor.mDispatcherState = null;
                    if (this.mCallbackAdaptor.mReadChannel != null) {
                        this.mCallbackAdaptor.mReadChannel.dispose();
                        this.mCallbackAdaptor.mReadChannel = null;
                    }
                    this.mCallbackAdaptor.mInputEventReceiver = null;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void updateCursorAnchorInfo(CursorAnchorInfo info) {
            if (!this.mFinished) {
                this.mOriginalCallback.onUpdateCursorAnchorInfo(info);
            }
        }

        /* access modifiers changed from: package-private */
        public void startInputOrWindowGainedFocus(SomeArgs args) {
            try {
                if (!this.mFinished) {
                    int startInputFlags = args.argi1;
                    int softInputMode = args.argi2;
                    int windowHandle = args.argi3;
                    this.mOriginalCallback.onStartInputOrWindowGainedFocus((InputConnectionWrapper) args.arg1, (EditorInfo) args.arg2, startInputFlags, softInputMode, windowHandle);
                    args.recycle();
                }
            } finally {
                args.recycle();
            }
        }

        /* access modifiers changed from: package-private */
        public void showSoftInput(int flags, ResultReceiver resultReceiver) {
            if (!this.mFinished) {
                this.mOriginalCallback.onShowSoftInput(flags, resultReceiver);
            }
        }

        /* access modifiers changed from: package-private */
        public void hideSoftInput(int flags, ResultReceiver resultReceiver) {
            if (!this.mFinished) {
                this.mOriginalCallback.onHideSoftInput(flags, resultReceiver);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void reportNotSupported() {
    }
}
