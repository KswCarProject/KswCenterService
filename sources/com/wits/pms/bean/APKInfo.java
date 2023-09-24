package com.wits.pms.bean;

/* loaded from: classes2.dex */
public class APKInfo {
    String apkPath;
    String pkgName;
    long versionCode;
    String versionName;

    public APKInfo() {
    }

    public APKInfo(String pkgName, String versionName, String apkPath, long versionCode) {
        this.apkPath = apkPath;
        this.pkgName = pkgName;
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public long getVersionCode() {
        return this.versionCode;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getApkPath() {
        return this.apkPath;
    }
}
