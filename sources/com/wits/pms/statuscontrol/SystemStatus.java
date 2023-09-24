package com.wits.pms.statuscontrol;

import android.view.KeyEvent;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SystemStatus {
    public static final int TYPE_SYSTEM_STATUS = 1;
    public int ccd;
    public boolean dormant;
    public int epb;
    public KeyEvent event;
    public int ill;
    public int lastMode;
    public int rlight;
    public String topApp;
    public String topClass = "";
    public int acc = 2;
    public int screenSwitch = 2;

    /* loaded from: classes2.dex */
    public static final class ACC {
        public static final int NORMAL = 2;
        public static final int OFF = 0;

        /* renamed from: ON */
        public static final int f2581ON = 1;
    }

    /* loaded from: classes2.dex */
    public static final class CCD {
        public static final int NORMAL = 0;
        public static final int REVER = 1;
    }

    /* loaded from: classes2.dex */
    public static final class EPB {
        public static final int TURN_OFF = 0;
        public static final int TURN_ON = 1;
    }

    /* loaded from: classes2.dex */
    public static final class ILL {
        public static final int TURN_OFF = 0;
        public static final int TURN_ON = 1;
    }

    /* loaded from: classes2.dex */
    public static final class MODE {
        public static final int AUX = 2;

        /* renamed from: FM */
        public static final int f2582FM = 0;
        public static final int MEDIA = 1;
        public static final int NAVI = 3;
        public static final int OTHER = 4;
    }

    /* loaded from: classes2.dex */
    public static final class RLIGHT {
        public static final int NORMAL = 0;
        public static final int OPEN = 1;
    }

    /* loaded from: classes2.dex */
    public static final class SCREEN {
        public static final int NORMAL = 2;
        public static final int OFF = 0;

        /* renamed from: ON */
        public static final int f2583ON = 1;
    }

    public SystemStatus() {
        this.topApp = "";
        this.topApp = "";
    }

    public List<String> compare(SystemStatus systemStatus) {
        List<String> keys = new ArrayList<>();
        if (this.acc != systemStatus.acc) {
            keys.add("acc");
        }
        if (this.rlight != systemStatus.rlight) {
            keys.add("rlight");
        }
        if (this.screenSwitch != systemStatus.screenSwitch) {
            keys.add("screenSwitch");
        }
        if (this.ccd != systemStatus.ccd) {
            keys.add("ccd");
        }
        if (this.ill != systemStatus.ill) {
            keys.add("ill");
        }
        if (this.epb != systemStatus.epb) {
            keys.add("epb");
        }
        if (this.dormant != systemStatus.dormant) {
            keys.add("dormant");
        }
        if (this.lastMode != systemStatus.lastMode) {
            keys.add("lastMode");
        }
        if (systemStatus.topApp != null && !systemStatus.topApp.equals(this.topApp)) {
            keys.add("topApp");
        }
        return keys;
    }

    public int getAcc() {
        return this.acc;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public KeyEvent getEvent() {
        return this.event;
    }

    public void setEvent(KeyEvent event) {
        this.event = event;
    }

    public String getTopApp() {
        return this.topApp;
    }

    public void setTopApp(String topApp) {
        this.topApp = topApp;
    }

    public boolean isDormant() {
        return this.dormant;
    }

    public void setDormant(boolean dormant) {
        this.dormant = dormant;
    }

    public int getLastMode() {
        return this.lastMode;
    }

    public void setLastMode(int lastMode) {
        this.lastMode = lastMode;
    }

    public int getScreenSwitch() {
        return this.screenSwitch;
    }

    public void setScreenSwitch(int screenSwitch) {
        this.screenSwitch = screenSwitch;
    }

    public static SystemStatus getStatusFormJson(String jsonArg) {
        return (SystemStatus) new Gson().fromJson(jsonArg, (Class<Object>) SystemStatus.class);
    }

    public int getRlight() {
        return this.rlight;
    }

    public void setRlight(int rlight) {
        this.rlight = rlight;
    }

    public int getCcd() {
        return this.ccd;
    }

    public void setCcd(int ccd) {
        this.ccd = ccd;
    }

    public int getIll() {
        return this.ill;
    }

    public void setIll(int ill) {
        this.ill = ill;
    }

    public int getEpb() {
        return this.epb;
    }

    public void setEpb(int epb) {
        this.epb = epb;
    }
}
