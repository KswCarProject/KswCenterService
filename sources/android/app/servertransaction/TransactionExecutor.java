package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.content.Intent;
import android.os.IBinder;
import android.util.IntArray;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;

public class TransactionExecutor {
    private static final boolean DEBUG_RESOLVER = false;
    private static final String TAG = "TransactionExecutor";
    private TransactionExecutorHelper mHelper = new TransactionExecutorHelper();
    private PendingTransactionActions mPendingActions = new PendingTransactionActions();
    private ClientTransactionHandler mTransactionHandler;

    public TransactionExecutor(ClientTransactionHandler clientTransactionHandler) {
        this.mTransactionHandler = clientTransactionHandler;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r1 = r6.mTransactionHandler.getActivitiesToBeDestroyed();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute(android.app.servertransaction.ClientTransaction r7) {
        /*
            r6 = this;
            android.os.IBinder r0 = r7.getActivityToken()
            if (r0 == 0) goto L_0x0049
            android.app.ClientTransactionHandler r1 = r6.mTransactionHandler
            java.util.Map r1 = r1.getActivitiesToBeDestroyed()
            java.lang.Object r2 = r1.get(r0)
            android.app.servertransaction.ClientTransactionItem r2 = (android.app.servertransaction.ClientTransactionItem) r2
            if (r2 == 0) goto L_0x0049
            android.app.servertransaction.ActivityLifecycleItem r3 = r7.getLifecycleStateRequest()
            if (r3 != r2) goto L_0x001d
            r1.remove(r0)
        L_0x001d:
            android.app.ClientTransactionHandler r3 = r6.mTransactionHandler
            android.app.ActivityThread$ActivityClientRecord r3 = r3.getActivityClient(r0)
            if (r3 != 0) goto L_0x0049
            java.lang.String r3 = "TransactionExecutor"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = android.app.servertransaction.TransactionExecutorHelper.tId(r7)
            r4.append(r5)
            java.lang.String r5 = "Skip pre-destroyed transaction:\n"
            r4.append(r5)
            android.app.ClientTransactionHandler r5 = r6.mTransactionHandler
            java.lang.String r5 = android.app.servertransaction.TransactionExecutorHelper.transactionToString(r7, r5)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Slog.w((java.lang.String) r3, (java.lang.String) r4)
            return
        L_0x0049:
            r6.executeCallbacks(r7)
            r6.executeLifecycleState(r7)
            android.app.servertransaction.PendingTransactionActions r1 = r6.mPendingActions
            r1.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.servertransaction.TransactionExecutor.execute(android.app.servertransaction.ClientTransaction):void");
    }

    @VisibleForTesting
    public void executeCallbacks(ClientTransaction transaction) {
        int finalState;
        ClientTransaction clientTransaction = transaction;
        List<ClientTransactionItem> callbacks = transaction.getCallbacks();
        if (callbacks != null && !callbacks.isEmpty()) {
            IBinder token = transaction.getActivityToken();
            ActivityThread.ActivityClientRecord r = this.mTransactionHandler.getActivityClient(token);
            ActivityLifecycleItem finalStateRequest = transaction.getLifecycleStateRequest();
            if (finalStateRequest != null) {
                finalState = finalStateRequest.getTargetState();
            } else {
                finalState = -1;
            }
            int lastCallbackRequestingState = TransactionExecutorHelper.lastCallbackRequestingState(transaction);
            int size = callbacks.size();
            ActivityThread.ActivityClientRecord r2 = r;
            int i = 0;
            while (i < size) {
                ClientTransactionItem item = callbacks.get(i);
                int postExecutionState = item.getPostExecutionState();
                int closestPreExecutionState = this.mHelper.getClosestPreExecutionState(r2, item.getPostExecutionState());
                if (closestPreExecutionState != -1) {
                    cycleToPath(r2, closestPreExecutionState, clientTransaction);
                }
                item.execute(this.mTransactionHandler, token, this.mPendingActions);
                item.postExecute(this.mTransactionHandler, token, this.mPendingActions);
                if (r2 == null) {
                    r2 = this.mTransactionHandler.getActivityClient(token);
                }
                if (!(postExecutionState == -1 || r2 == null)) {
                    cycleToPath(r2, postExecutionState, i == lastCallbackRequestingState && finalState == postExecutionState, clientTransaction);
                }
                i++;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0007, code lost:
        r1 = r6.getActivityToken();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void executeLifecycleState(android.app.servertransaction.ClientTransaction r6) {
        /*
            r5 = this;
            android.app.servertransaction.ActivityLifecycleItem r0 = r6.getLifecycleStateRequest()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            android.os.IBinder r1 = r6.getActivityToken()
            android.app.ClientTransactionHandler r2 = r5.mTransactionHandler
            android.app.ActivityThread$ActivityClientRecord r2 = r2.getActivityClient(r1)
            if (r2 != 0) goto L_0x0014
            return
        L_0x0014:
            int r3 = r0.getTargetState()
            r4 = 1
            r5.cycleToPath(r2, r3, r4, r6)
            android.app.ClientTransactionHandler r3 = r5.mTransactionHandler
            android.app.servertransaction.PendingTransactionActions r4 = r5.mPendingActions
            r0.execute(r3, r1, r4)
            android.app.ClientTransactionHandler r3 = r5.mTransactionHandler
            android.app.servertransaction.PendingTransactionActions r4 = r5.mPendingActions
            r0.postExecute(r3, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.servertransaction.TransactionExecutor.executeLifecycleState(android.app.servertransaction.ClientTransaction):void");
    }

    @VisibleForTesting
    public void cycleToPath(ActivityThread.ActivityClientRecord r, int finish, ClientTransaction transaction) {
        cycleToPath(r, finish, false, transaction);
    }

    private void cycleToPath(ActivityThread.ActivityClientRecord r, int finish, boolean excludeLastState, ClientTransaction transaction) {
        performLifecycleSequence(r, this.mHelper.getLifecyclePath(r.getLifecycleState(), finish, excludeLastState), transaction);
    }

    private void performLifecycleSequence(ActivityThread.ActivityClientRecord r, IntArray path, ClientTransaction transaction) {
        ActivityThread.ActivityClientRecord activityClientRecord = r;
        IntArray intArray = path;
        int size = path.size();
        for (int i = 0; i < size; i++) {
            int state = intArray.get(i);
            switch (state) {
                case 1:
                    this.mTransactionHandler.handleLaunchActivity(activityClientRecord, this.mPendingActions, (Intent) null);
                    break;
                case 2:
                    this.mTransactionHandler.handleStartActivity(activityClientRecord, this.mPendingActions);
                    break;
                case 3:
                    this.mTransactionHandler.handleResumeActivity(activityClientRecord.token, false, activityClientRecord.isForward, "LIFECYCLER_RESUME_ACTIVITY");
                    break;
                case 4:
                    this.mTransactionHandler.handlePauseActivity(activityClientRecord.token, false, false, 0, this.mPendingActions, "LIFECYCLER_PAUSE_ACTIVITY");
                    break;
                case 5:
                    this.mTransactionHandler.handleStopActivity(activityClientRecord.token, false, 0, this.mPendingActions, false, "LIFECYCLER_STOP_ACTIVITY");
                    break;
                case 6:
                    this.mTransactionHandler.handleDestroyActivity(activityClientRecord.token, false, 0, false, "performLifecycleSequence. cycling to:" + intArray.get(size - 1));
                    break;
                case 7:
                    this.mTransactionHandler.performRestartActivity(activityClientRecord.token, false);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected lifecycle state: " + state);
            }
        }
    }
}
