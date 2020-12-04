package com.android.internal.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DebugUtils;
import android.util.Slog;
import com.android.internal.R;

public class DumpHeapActivity extends Activity {
    public static final String ACTION_DELETE_DUMPHEAP = "com.android.server.am.DELETE_DUMPHEAP";
    public static final String EXTRA_DELAY_DELETE = "delay_delete";
    public static final Uri JAVA_URI = Uri.parse("content://com.android.server.heapdump/java");
    public static final String KEY_DIRECT_LAUNCH = "direct_launch";
    public static final String KEY_IS_SYSTEM_PROCESS = "is_system_process";
    public static final String KEY_IS_USER_INITIATED = "is_user_initiated";
    public static final String KEY_PROCESS = "process";
    public static final String KEY_SIZE = "size";
    AlertDialog mDialog;
    boolean mHandled = false;
    String mProcess;
    long mSize;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int messageId;
        super.onCreate(savedInstanceState);
        this.mProcess = getIntent().getStringExtra(KEY_PROCESS);
        this.mSize = getIntent().getLongExtra(KEY_SIZE, 0);
        boolean isUserInitiated = getIntent().getBooleanExtra(KEY_IS_USER_INITIATED, false);
        boolean isSystemProcess = getIntent().getBooleanExtra(KEY_IS_SYSTEM_PROCESS, false);
        String directLaunch = getIntent().getStringExtra(KEY_DIRECT_LAUNCH);
        if (directLaunch != null) {
            Intent intent = new Intent(ActivityManager.ACTION_REPORT_HEAP_LIMIT);
            intent.setPackage(directLaunch);
            ClipData clip = ClipData.newUri(getContentResolver(), "Heap Dump", JAVA_URI);
            intent.setClipData(clip);
            intent.addFlags(1);
            intent.setType(clip.getDescription().getMimeType(0));
            intent.putExtra(Intent.EXTRA_STREAM, (Parcelable) JAVA_URI);
            try {
                startActivity(intent);
                scheduleDelete();
                this.mHandled = true;
                finish();
                return;
            } catch (ActivityNotFoundException e) {
                Slog.i("DumpHeapActivity", "Unable to direct launch to " + directLaunch + ": " + e.getMessage());
            }
        }
        if (isUserInitiated) {
            messageId = R.string.dump_heap_ready_text;
        } else if (isSystemProcess) {
            messageId = R.string.dump_heap_system_text;
        } else {
            messageId = R.string.dump_heap_text;
        }
        AlertDialog.Builder b = new AlertDialog.Builder(this, 16974394);
        b.setTitle((int) R.string.dump_heap_title);
        b.setMessage((CharSequence) getString(messageId, this.mProcess, DebugUtils.sizeValueToString(this.mSize, (StringBuilder) null)));
        b.setNegativeButton(17039360, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DumpHeapActivity.this.mHandled = true;
                DumpHeapActivity.this.sendBroadcast(new Intent(DumpHeapActivity.ACTION_DELETE_DUMPHEAP));
                DumpHeapActivity.this.finish();
            }
        });
        b.setPositiveButton(17039370, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DumpHeapActivity.this.mHandled = true;
                DumpHeapActivity.this.scheduleDelete();
                Intent intent = new Intent(Intent.ACTION_SEND);
                ClipData clip = ClipData.newUri(DumpHeapActivity.this.getContentResolver(), "Heap Dump", DumpHeapActivity.JAVA_URI);
                intent.setClipData(clip);
                intent.addFlags(1);
                intent.setType(clip.getDescription().getMimeType(0));
                intent.putExtra(Intent.EXTRA_STREAM, (Parcelable) DumpHeapActivity.JAVA_URI);
                DumpHeapActivity.this.startActivity(Intent.createChooser(intent, DumpHeapActivity.this.getText(R.string.dump_heap_title)));
                DumpHeapActivity.this.finish();
            }
        });
        this.mDialog = b.show();
    }

    /* access modifiers changed from: package-private */
    public void scheduleDelete() {
        Intent broadcast = new Intent(ACTION_DELETE_DUMPHEAP);
        broadcast.putExtra(EXTRA_DELAY_DELETE, true);
        sendBroadcast(broadcast);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (!isChangingConfigurations() && !this.mHandled) {
            sendBroadcast(new Intent(ACTION_DELETE_DUMPHEAP));
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mDialog.dismiss();
    }
}
