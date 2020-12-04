package android.service.textclassifier;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.textclassifier.ITextClassifierService;
import android.service.textclassifier.TextClassifierService;
import android.text.TextUtils;
import android.util.Slog;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SystemApi
public abstract class TextClassifierService extends Service {
    private static final String KEY_RESULT = "key_result";
    private static final String LOG_TAG = "TextClassifierService";
    public static final String SERVICE_INTERFACE = "android.service.textclassifier.TextClassifierService";
    private final ITextClassifierService.Stub mBinder = new ITextClassifierService.Stub() {
        private final CancellationSignal mCancellationSignal = new CancellationSignal();

        public void onSuggestSelection(TextClassificationSessionId sessionId, TextSelection.Request request, ITextClassifierCallback callback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(callback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, request, callback) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ TextSelection.Request f$2;
                private final /* synthetic */ ITextClassifierCallback f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    TextClassifierService.this.onSuggestSelection(this.f$1, this.f$2, TextClassifierService.AnonymousClass1.this.mCancellationSignal, new TextClassifierService.ProxyCallback(this.f$3));
                }
            });
        }

        public void onClassifyText(TextClassificationSessionId sessionId, TextClassification.Request request, ITextClassifierCallback callback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(callback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, request, callback) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ TextClassification.Request f$2;
                private final /* synthetic */ ITextClassifierCallback f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    TextClassifierService.this.onClassifyText(this.f$1, this.f$2, TextClassifierService.AnonymousClass1.this.mCancellationSignal, new TextClassifierService.ProxyCallback(this.f$3));
                }
            });
        }

        public void onGenerateLinks(TextClassificationSessionId sessionId, TextLinks.Request request, ITextClassifierCallback callback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(callback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, request, callback) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ TextLinks.Request f$2;
                private final /* synthetic */ ITextClassifierCallback f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    TextClassifierService.this.onGenerateLinks(this.f$1, this.f$2, TextClassifierService.AnonymousClass1.this.mCancellationSignal, new TextClassifierService.ProxyCallback(this.f$3));
                }
            });
        }

        public void onSelectionEvent(TextClassificationSessionId sessionId, SelectionEvent event) {
            Preconditions.checkNotNull(event);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, event) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ SelectionEvent f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    TextClassifierService.this.onSelectionEvent(this.f$1, this.f$2);
                }
            });
        }

        public void onTextClassifierEvent(TextClassificationSessionId sessionId, TextClassifierEvent event) {
            Preconditions.checkNotNull(event);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, event) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ TextClassifierEvent f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    TextClassifierService.this.onTextClassifierEvent(this.f$1, this.f$2);
                }
            });
        }

        public void onDetectLanguage(TextClassificationSessionId sessionId, TextLanguage.Request request, ITextClassifierCallback callback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(callback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, request, callback) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ TextLanguage.Request f$2;
                private final /* synthetic */ ITextClassifierCallback f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    TextClassifierService.this.onDetectLanguage(this.f$1, this.f$2, TextClassifierService.AnonymousClass1.this.mCancellationSignal, new TextClassifierService.ProxyCallback(this.f$3));
                }
            });
        }

        public void onSuggestConversationActions(TextClassificationSessionId sessionId, ConversationActions.Request request, ITextClassifierCallback callback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(callback);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId, request, callback) {
                private final /* synthetic */ TextClassificationSessionId f$1;
                private final /* synthetic */ ConversationActions.Request f$2;
                private final /* synthetic */ ITextClassifierCallback f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    TextClassifierService.this.onSuggestConversationActions(this.f$1, this.f$2, TextClassifierService.AnonymousClass1.this.mCancellationSignal, new TextClassifierService.ProxyCallback(this.f$3));
                }
            });
        }

        public void onCreateTextClassificationSession(TextClassificationContext context, TextClassificationSessionId sessionId) {
            Preconditions.checkNotNull(context);
            Preconditions.checkNotNull(sessionId);
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(context, sessionId) {
                private final /* synthetic */ TextClassificationContext f$1;
                private final /* synthetic */ TextClassificationSessionId f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    TextClassifierService.this.onCreateTextClassificationSession(this.f$1, this.f$2);
                }
            });
        }

        public void onDestroyTextClassificationSession(TextClassificationSessionId sessionId) {
            TextClassifierService.this.mMainThreadHandler.post(new Runnable(sessionId) {
                private final /* synthetic */ TextClassificationSessionId f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    TextClassifierService.this.onDestroyTextClassificationSession(this.f$1);
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), (Handler.Callback) null, true);
    private final ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();

    public interface Callback<T> {
        void onFailure(CharSequence charSequence);

        void onSuccess(T t);
    }

    public abstract void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, CancellationSignal cancellationSignal, Callback<TextClassification> callback);

    public abstract void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, CancellationSignal cancellationSignal, Callback<TextLinks> callback);

    public abstract void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, CancellationSignal cancellationSignal, Callback<TextSelection> callback);

    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    public void onDetectLanguage(TextClassificationSessionId sessionId, TextLanguage.Request request, CancellationSignal cancellationSignal, Callback<TextLanguage> callback) {
        this.mSingleThreadExecutor.submit(new Runnable(callback, request) {
            private final /* synthetic */ TextClassifierService.Callback f$1;
            private final /* synthetic */ TextLanguage.Request f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                this.f$1.onSuccess(TextClassifierService.this.getLocalTextClassifier().detectLanguage(this.f$2));
            }
        });
    }

    public void onSuggestConversationActions(TextClassificationSessionId sessionId, ConversationActions.Request request, CancellationSignal cancellationSignal, Callback<ConversationActions> callback) {
        this.mSingleThreadExecutor.submit(new Runnable(callback, request) {
            private final /* synthetic */ TextClassifierService.Callback f$1;
            private final /* synthetic */ ConversationActions.Request f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                this.f$1.onSuccess(TextClassifierService.this.getLocalTextClassifier().suggestConversationActions(this.f$2));
            }
        });
    }

    @Deprecated
    public void onSelectionEvent(TextClassificationSessionId sessionId, SelectionEvent event) {
    }

    public void onTextClassifierEvent(TextClassificationSessionId sessionId, TextClassifierEvent event) {
    }

    public void onCreateTextClassificationSession(TextClassificationContext context, TextClassificationSessionId sessionId) {
    }

    public void onDestroyTextClassificationSession(TextClassificationSessionId sessionId) {
    }

    @Deprecated
    public final TextClassifier getLocalTextClassifier() {
        return getDefaultTextClassifierImplementation(this);
    }

    public static TextClassifier getDefaultTextClassifierImplementation(Context context) {
        TextClassificationManager tcm = (TextClassificationManager) context.getSystemService(TextClassificationManager.class);
        if (tcm != null) {
            return tcm.getTextClassifier(0);
        }
        return TextClassifier.NO_OP;
    }

    public static <T extends Parcelable> T getResponse(Bundle bundle) {
        return bundle.getParcelable(KEY_RESULT);
    }

    public static ComponentName getServiceComponentName(Context context) {
        String packageName = context.getPackageManager().getSystemTextClassifierPackageName();
        if (TextUtils.isEmpty(packageName)) {
            Slog.d(LOG_TAG, "No configured system TextClassifierService");
            return null;
        }
        ResolveInfo ri = context.getPackageManager().resolveService(new Intent(SERVICE_INTERFACE).setPackage(packageName), 1048576);
        if (ri == null || ri.serviceInfo == null) {
            Slog.w(LOG_TAG, String.format("Package or service not found in package %s for user %d", new Object[]{packageName, Integer.valueOf(context.getUserId())}));
            return null;
        }
        ServiceInfo si = ri.serviceInfo;
        if (Manifest.permission.BIND_TEXTCLASSIFIER_SERVICE.equals(si.permission)) {
            return si.getComponentName();
        }
        Slog.w(LOG_TAG, String.format("Service %s should require %s permission. Found %s permission", new Object[]{si.getComponentName(), Manifest.permission.BIND_TEXTCLASSIFIER_SERVICE, si.permission}));
        return null;
    }

    private static final class ProxyCallback<T extends Parcelable> implements Callback<T> {
        private WeakReference<ITextClassifierCallback> mTextClassifierCallback;

        private ProxyCallback(ITextClassifierCallback textClassifierCallback) {
            this.mTextClassifierCallback = new WeakReference<>((ITextClassifierCallback) Preconditions.checkNotNull(textClassifierCallback));
        }

        public void onSuccess(T result) {
            ITextClassifierCallback callback = (ITextClassifierCallback) this.mTextClassifierCallback.get();
            if (callback != null) {
                try {
                    Bundle bundle = new Bundle(1);
                    bundle.putParcelable(TextClassifierService.KEY_RESULT, result);
                    callback.onSuccess(bundle);
                } catch (RemoteException e) {
                    Slog.d(TextClassifierService.LOG_TAG, "Error calling callback");
                }
            }
        }

        public void onFailure(CharSequence error) {
            ITextClassifierCallback callback = (ITextClassifierCallback) this.mTextClassifierCallback.get();
            if (callback != null) {
                try {
                    callback.onFailure();
                } catch (RemoteException e) {
                    Slog.d(TextClassifierService.LOG_TAG, "Error calling callback");
                }
            }
        }
    }
}
