package com.wits.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mcu.McuService;

public class TestActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, McuService.class));
        startService(new Intent(this, PowerManagerAppService.class));
    }
}
