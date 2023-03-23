package com.wits.pms.bean;

public class APKInfo {
    String apkPath;
    String pkgName;
    long versionCode;
    String versionName;

    public APKInfo() {
    }

    public APKInfo(String pkgName2, String versionName2, String apkPath2, long versionCode2) {
        this.apkPath = apkPath2;
        this.pkgName = pkgName2;
        this.versionName = versionName2;
        this.versionCode = versionCode2;
    }

    public void setPkgName(String pkgName2) {
        this.pkgName = pkgName2;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setVersionName(String versionName2) {
        this.versionName = versionName2;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionCode(long versionCode2) {
        this.versionCode = versionCode2;
    }

    public long getVersionCode() {
        return this.versionCode;
    }

    public void setApkPath(String apkPath2) {
        this.apkPath = apkPath2;
    }

    public String getApkPath() {
        return this.apkPath;
    }
}
