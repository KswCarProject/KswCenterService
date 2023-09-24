package com.wits.pms;

import android.content.Intent;
import android.p007os.Bundle;
import android.support.annotation.Nullable;
import android.support.p014v7.app.AppCompatActivity;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mcu.McuService;

/* loaded from: classes2.dex */
public class TestActivity extends AppCompatActivity {
    @Override // android.support.p014v7.app.AppCompatActivity, android.support.p011v4.app.FragmentActivity, android.support.p011v4.app.SupportActivity, android.app.Activity
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, McuService.class));
        startService(new Intent(this, PowerManagerAppService.class));
    }
}
