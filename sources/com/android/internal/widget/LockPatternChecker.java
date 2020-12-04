package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncTask;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LockPatternChecker {

    public interface OnVerifyCallback {
        void onVerified(byte[] bArr, int i);
    }

    public interface OnCheckCallback {
        void onChecked(boolean z, int i);

        void onEarlyMatched() {
        }

        void onCancelled() {
        }
    }

    public static AsyncTask<?, ?, ?> verifyPattern(LockPatternUtils utils, List<LockPatternView.Cell> pattern, long challenge, int userId, OnVerifyCallback callback) {
        final List<LockPatternView.Cell> list = pattern;
        final LockPatternUtils lockPatternUtils = utils;
        final long j = challenge;
        final int i = userId;
        final OnVerifyCallback onVerifyCallback = callback;
        AsyncTask<Void, Void, byte[]> task = new AsyncTask<Void, Void, byte[]>() {
            private int mThrottleTimeout;
            private List<LockPatternView.Cell> patternCopy;

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                this.patternCopy = new ArrayList(list);
            }

            /* access modifiers changed from: protected */
            public byte[] doInBackground(Void... args) {
                try {
                    return lockPatternUtils.verifyPattern(this.patternCopy, j, i);
                } catch (LockPatternUtils.RequestThrottledException ex) {
                    this.mThrottleTimeout = ex.getTimeoutMs();
                    return null;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(byte[] result) {
                onVerifyCallback.onVerified(result, this.mThrottleTimeout);
            }
        };
        task.execute((Params[]) new Void[0]);
        return task;
    }

    public static AsyncTask<?, ?, ?> checkPattern(final LockPatternUtils utils, final List<LockPatternView.Cell> pattern, final int userId, final OnCheckCallback callback) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            private int mThrottleTimeout;
            private List<LockPatternView.Cell> patternCopy;

            /* access modifiers changed from: protected */
            public void onPreExecute() {
                this.patternCopy = new ArrayList(pattern);
            }

            /* access modifiers changed from: protected */
            public Boolean doInBackground(Void... args) {
                try {
                    LockPatternUtils lockPatternUtils = utils;
                    List<LockPatternView.Cell> list = this.patternCopy;
                    int i = userId;
                    OnCheckCallback onCheckCallback = callback;
                    Objects.requireNonNull(onCheckCallback);
                    return Boolean.valueOf(lockPatternUtils.checkPattern(list, i, new LockPatternUtils.CheckCredentialProgressCallback() {
                        public final void onEarlyMatched() {
                            LockPatternChecker.OnCheckCallback.this.onEarlyMatched();
                        }
                    }));
                } catch (LockPatternUtils.RequestThrottledException ex) {
                    this.mThrottleTimeout = ex.getTimeoutMs();
                    return false;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Boolean result) {
                callback.onChecked(result.booleanValue(), this.mThrottleTimeout);
            }

            /* access modifiers changed from: protected */
            public void onCancelled() {
                callback.onCancelled();
            }
        };
        task.execute((Params[]) new Void[0]);
        return task;
    }

    @Deprecated
    public static AsyncTask<?, ?, ?> verifyPassword(LockPatternUtils utils, String password, long challenge, int userId, OnVerifyCallback callback) {
        return verifyPassword(utils, password != null ? password.getBytes() : null, challenge, userId, callback);
    }

    public static AsyncTask<?, ?, ?> verifyPassword(LockPatternUtils utils, byte[] password, long challenge, int userId, OnVerifyCallback callback) {
        final LockPatternUtils lockPatternUtils = utils;
        final byte[] bArr = password;
        final long j = challenge;
        final int i = userId;
        final OnVerifyCallback onVerifyCallback = callback;
        AsyncTask<Void, Void, byte[]> task = new AsyncTask<Void, Void, byte[]>() {
            private int mThrottleTimeout;

            /* access modifiers changed from: protected */
            public byte[] doInBackground(Void... args) {
                try {
                    return LockPatternUtils.this.verifyPassword(bArr, j, i);
                } catch (LockPatternUtils.RequestThrottledException ex) {
                    this.mThrottleTimeout = ex.getTimeoutMs();
                    return null;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(byte[] result) {
                onVerifyCallback.onVerified(result, this.mThrottleTimeout);
            }
        };
        task.execute((Params[]) new Void[0]);
        return task;
    }

    public static AsyncTask<?, ?, ?> verifyTiedProfileChallenge(LockPatternUtils utils, byte[] password, boolean isPattern, long challenge, int userId, OnVerifyCallback callback) {
        final LockPatternUtils lockPatternUtils = utils;
        final byte[] bArr = password;
        final boolean z = isPattern;
        final long j = challenge;
        final int i = userId;
        final OnVerifyCallback onVerifyCallback = callback;
        AsyncTask<Void, Void, byte[]> task = new AsyncTask<Void, Void, byte[]>() {
            private int mThrottleTimeout;

            /* access modifiers changed from: protected */
            public byte[] doInBackground(Void... args) {
                try {
                    return LockPatternUtils.this.verifyTiedProfileChallenge(bArr, z, j, i);
                } catch (LockPatternUtils.RequestThrottledException ex) {
                    this.mThrottleTimeout = ex.getTimeoutMs();
                    return null;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(byte[] result) {
                onVerifyCallback.onVerified(result, this.mThrottleTimeout);
            }
        };
        task.execute((Params[]) new Void[0]);
        return task;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static AsyncTask<?, ?, ?> checkPassword(LockPatternUtils utils, String password, int userId, OnCheckCallback callback) {
        return checkPassword(utils, password != null ? password.getBytes() : null, userId, callback);
    }

    public static AsyncTask<?, ?, ?> checkPassword(final LockPatternUtils utils, final byte[] passwordBytes, final int userId, final OnCheckCallback callback) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            private int mThrottleTimeout;

            /* access modifiers changed from: protected */
            public Boolean doInBackground(Void... args) {
                try {
                    LockPatternUtils lockPatternUtils = LockPatternUtils.this;
                    byte[] bArr = passwordBytes;
                    int i = userId;
                    OnCheckCallback onCheckCallback = callback;
                    Objects.requireNonNull(onCheckCallback);
                    return Boolean.valueOf(lockPatternUtils.checkPassword(bArr, i, (LockPatternUtils.CheckCredentialProgressCallback) new LockPatternUtils.CheckCredentialProgressCallback() {
                        public final void onEarlyMatched() {
                            LockPatternChecker.OnCheckCallback.this.onEarlyMatched();
                        }
                    }));
                } catch (LockPatternUtils.RequestThrottledException ex) {
                    this.mThrottleTimeout = ex.getTimeoutMs();
                    return false;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Boolean result) {
                callback.onChecked(result.booleanValue(), this.mThrottleTimeout);
            }

            /* access modifiers changed from: protected */
            public void onCancelled() {
                callback.onCancelled();
            }
        };
        task.execute((Params[]) new Void[0]);
        return task;
    }
}
