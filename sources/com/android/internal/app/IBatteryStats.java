package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.net.wifi.WifiActivityEnergyInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.WorkSource;
import android.os.connectivity.CellularBatteryStats;
import android.os.connectivity.GpsBatteryStats;
import android.os.connectivity.WifiBatteryStats;
import android.os.health.HealthStatsParceler;
import android.telephony.ModemActivityInfo;
import android.telephony.SignalStrength;

public interface IBatteryStats extends IInterface {
    long computeBatteryTimeRemaining() throws RemoteException;

    @UnsupportedAppUsage
    long computeChargeTimeRemaining() throws RemoteException;

    @UnsupportedAppUsage
    long getAwakeTimeBattery() throws RemoteException;

    long getAwakeTimePlugged() throws RemoteException;

    CellularBatteryStats getCellularBatteryStats() throws RemoteException;

    GpsBatteryStats getGpsBatteryStats() throws RemoteException;

    @UnsupportedAppUsage
    byte[] getStatistics() throws RemoteException;

    ParcelFileDescriptor getStatisticsStream() throws RemoteException;

    WifiBatteryStats getWifiBatteryStats() throws RemoteException;

    @UnsupportedAppUsage
    boolean isCharging() throws RemoteException;

    void noteBleScanResults(WorkSource workSource, int i) throws RemoteException;

    void noteBleScanStarted(WorkSource workSource, boolean z) throws RemoteException;

    void noteBleScanStopped(WorkSource workSource, boolean z) throws RemoteException;

    void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) throws RemoteException;

    void noteChangeWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, WorkSource workSource2, int i3, String str3, String str4, int i4, boolean z) throws RemoteException;

    void noteConnectivityChanged(int i, String str) throws RemoteException;

    void noteDeviceIdleMode(int i, String str, int i2) throws RemoteException;

    void noteEvent(int i, String str, int i2) throws RemoteException;

    void noteFlashlightOff(int i) throws RemoteException;

    void noteFlashlightOn(int i) throws RemoteException;

    void noteFullWifiLockAcquired(int i) throws RemoteException;

    void noteFullWifiLockAcquiredFromSource(WorkSource workSource) throws RemoteException;

    void noteFullWifiLockReleased(int i) throws RemoteException;

    void noteFullWifiLockReleasedFromSource(WorkSource workSource) throws RemoteException;

    void noteGpsChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException;

    void noteGpsSignalQuality(int i) throws RemoteException;

    void noteInteractive(boolean z) throws RemoteException;

    void noteJobFinish(String str, int i, int i2, int i3, int i4) throws RemoteException;

    void noteJobStart(String str, int i, int i2, int i3) throws RemoteException;

    void noteLongPartialWakelockFinish(String str, String str2, int i) throws RemoteException;

    void noteLongPartialWakelockFinishFromSource(String str, String str2, WorkSource workSource) throws RemoteException;

    void noteLongPartialWakelockStart(String str, String str2, int i) throws RemoteException;

    void noteLongPartialWakelockStartFromSource(String str, String str2, WorkSource workSource) throws RemoteException;

    void noteMobileRadioPowerState(int i, long j, int i2) throws RemoteException;

    void noteModemControllerActivity(ModemActivityInfo modemActivityInfo) throws RemoteException;

    void noteNetworkInterfaceType(String str, int i) throws RemoteException;

    void noteNetworkStatsEnabled() throws RemoteException;

    void notePhoneDataConnectionState(int i, boolean z) throws RemoteException;

    void notePhoneOff() throws RemoteException;

    void notePhoneOn() throws RemoteException;

    void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException;

    void notePhoneState(int i) throws RemoteException;

    void noteResetAudio() throws RemoteException;

    void noteResetBleScan() throws RemoteException;

    void noteResetCamera() throws RemoteException;

    void noteResetFlashlight() throws RemoteException;

    void noteResetVideo() throws RemoteException;

    void noteScreenBrightness(int i) throws RemoteException;

    void noteScreenState(int i) throws RemoteException;

    void noteStartAudio(int i) throws RemoteException;

    void noteStartCamera(int i) throws RemoteException;

    void noteStartSensor(int i, int i2) throws RemoteException;

    void noteStartVideo(int i) throws RemoteException;

    void noteStartWakelock(int i, int i2, String str, String str2, int i3, boolean z) throws RemoteException;

    void noteStartWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2, boolean z) throws RemoteException;

    void noteStopAudio(int i) throws RemoteException;

    void noteStopCamera(int i) throws RemoteException;

    void noteStopSensor(int i, int i2) throws RemoteException;

    void noteStopVideo(int i) throws RemoteException;

    void noteStopWakelock(int i, int i2, String str, String str2, int i3) throws RemoteException;

    void noteStopWakelockFromSource(WorkSource workSource, int i, String str, String str2, int i2) throws RemoteException;

    void noteSyncFinish(String str, int i) throws RemoteException;

    void noteSyncStart(String str, int i) throws RemoteException;

    void noteUserActivity(int i, int i2) throws RemoteException;

    void noteVibratorOff(int i) throws RemoteException;

    void noteVibratorOn(int i, long j) throws RemoteException;

    void noteWakeUp(String str, int i) throws RemoteException;

    void noteWifiBatchedScanStartedFromSource(WorkSource workSource, int i) throws RemoteException;

    void noteWifiBatchedScanStoppedFromSource(WorkSource workSource) throws RemoteException;

    void noteWifiControllerActivity(WifiActivityEnergyInfo wifiActivityEnergyInfo) throws RemoteException;

    void noteWifiMulticastDisabled(int i) throws RemoteException;

    void noteWifiMulticastEnabled(int i) throws RemoteException;

    void noteWifiOff() throws RemoteException;

    void noteWifiOn() throws RemoteException;

    void noteWifiRadioPowerState(int i, long j, int i2) throws RemoteException;

    void noteWifiRssiChanged(int i) throws RemoteException;

    void noteWifiRunning(WorkSource workSource) throws RemoteException;

    void noteWifiRunningChanged(WorkSource workSource, WorkSource workSource2) throws RemoteException;

    void noteWifiScanStarted(int i) throws RemoteException;

    void noteWifiScanStartedFromSource(WorkSource workSource) throws RemoteException;

    void noteWifiScanStopped(int i) throws RemoteException;

    void noteWifiScanStoppedFromSource(WorkSource workSource) throws RemoteException;

    void noteWifiState(int i, String str) throws RemoteException;

    void noteWifiStopped(WorkSource workSource) throws RemoteException;

    void noteWifiSupplicantStateChanged(int i, boolean z) throws RemoteException;

    void setBatteryState(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException;

    boolean setChargingStateUpdateDelayMillis(int i) throws RemoteException;

    HealthStatsParceler takeUidSnapshot(int i) throws RemoteException;

    HealthStatsParceler[] takeUidSnapshots(int[] iArr) throws RemoteException;

    public static class Default implements IBatteryStats {
        public void noteStartSensor(int uid, int sensor) throws RemoteException {
        }

        public void noteStopSensor(int uid, int sensor) throws RemoteException {
        }

        public void noteStartVideo(int uid) throws RemoteException {
        }

        public void noteStopVideo(int uid) throws RemoteException {
        }

        public void noteStartAudio(int uid) throws RemoteException {
        }

        public void noteStopAudio(int uid) throws RemoteException {
        }

        public void noteResetVideo() throws RemoteException {
        }

        public void noteResetAudio() throws RemoteException {
        }

        public void noteFlashlightOn(int uid) throws RemoteException {
        }

        public void noteFlashlightOff(int uid) throws RemoteException {
        }

        public void noteStartCamera(int uid) throws RemoteException {
        }

        public void noteStopCamera(int uid) throws RemoteException {
        }

        public void noteResetCamera() throws RemoteException {
        }

        public void noteResetFlashlight() throws RemoteException {
        }

        public byte[] getStatistics() throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor getStatisticsStream() throws RemoteException {
            return null;
        }

        public boolean isCharging() throws RemoteException {
            return false;
        }

        public long computeBatteryTimeRemaining() throws RemoteException {
            return 0;
        }

        public long computeChargeTimeRemaining() throws RemoteException {
            return 0;
        }

        public void noteEvent(int code, String name, int uid) throws RemoteException {
        }

        public void noteSyncStart(String name, int uid) throws RemoteException {
        }

        public void noteSyncFinish(String name, int uid) throws RemoteException {
        }

        public void noteJobStart(String name, int uid, int standbyBucket, int jobid) throws RemoteException {
        }

        public void noteJobFinish(String name, int uid, int stopReason, int standbyBucket, int jobid) throws RemoteException {
        }

        public void noteStartWakelock(int uid, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
        }

        public void noteStopWakelock(int uid, int pid, String name, String historyName, int type) throws RemoteException {
        }

        public void noteStartWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
        }

        public void noteChangeWakelockFromSource(WorkSource ws, int pid, String name, String histyoryName, int type, WorkSource newWs, int newPid, String newName, String newHistoryName, int newType, boolean newUnimportantForLogging) throws RemoteException {
        }

        public void noteStopWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type) throws RemoteException {
        }

        public void noteLongPartialWakelockStart(String name, String historyName, int uid) throws RemoteException {
        }

        public void noteLongPartialWakelockStartFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
        }

        public void noteLongPartialWakelockFinish(String name, String historyName, int uid) throws RemoteException {
        }

        public void noteLongPartialWakelockFinishFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
        }

        public void noteVibratorOn(int uid, long durationMillis) throws RemoteException {
        }

        public void noteVibratorOff(int uid) throws RemoteException {
        }

        public void noteGpsChanged(WorkSource oldSource, WorkSource newSource) throws RemoteException {
        }

        public void noteGpsSignalQuality(int signalLevel) throws RemoteException {
        }

        public void noteScreenState(int state) throws RemoteException {
        }

        public void noteScreenBrightness(int brightness) throws RemoteException {
        }

        public void noteUserActivity(int uid, int event) throws RemoteException {
        }

        public void noteWakeUp(String reason, int reasonUid) throws RemoteException {
        }

        public void noteInteractive(boolean interactive) throws RemoteException {
        }

        public void noteConnectivityChanged(int type, String extra) throws RemoteException {
        }

        public void noteMobileRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
        }

        public void notePhoneOn() throws RemoteException {
        }

        public void notePhoneOff() throws RemoteException {
        }

        public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
        }

        public void notePhoneDataConnectionState(int dataType, boolean hasData) throws RemoteException {
        }

        public void notePhoneState(int phoneState) throws RemoteException {
        }

        public void noteWifiOn() throws RemoteException {
        }

        public void noteWifiOff() throws RemoteException {
        }

        public void noteWifiRunning(WorkSource ws) throws RemoteException {
        }

        public void noteWifiRunningChanged(WorkSource oldWs, WorkSource newWs) throws RemoteException {
        }

        public void noteWifiStopped(WorkSource ws) throws RemoteException {
        }

        public void noteWifiState(int wifiState, String accessPoint) throws RemoteException {
        }

        public void noteWifiSupplicantStateChanged(int supplState, boolean failedAuth) throws RemoteException {
        }

        public void noteWifiRssiChanged(int newRssi) throws RemoteException {
        }

        public void noteFullWifiLockAcquired(int uid) throws RemoteException {
        }

        public void noteFullWifiLockReleased(int uid) throws RemoteException {
        }

        public void noteWifiScanStarted(int uid) throws RemoteException {
        }

        public void noteWifiScanStopped(int uid) throws RemoteException {
        }

        public void noteWifiMulticastEnabled(int uid) throws RemoteException {
        }

        public void noteWifiMulticastDisabled(int uid) throws RemoteException {
        }

        public void noteFullWifiLockAcquiredFromSource(WorkSource ws) throws RemoteException {
        }

        public void noteFullWifiLockReleasedFromSource(WorkSource ws) throws RemoteException {
        }

        public void noteWifiScanStartedFromSource(WorkSource ws) throws RemoteException {
        }

        public void noteWifiScanStoppedFromSource(WorkSource ws) throws RemoteException {
        }

        public void noteWifiBatchedScanStartedFromSource(WorkSource ws, int csph) throws RemoteException {
        }

        public void noteWifiBatchedScanStoppedFromSource(WorkSource ws) throws RemoteException {
        }

        public void noteWifiRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
        }

        public void noteNetworkInterfaceType(String iface, int type) throws RemoteException {
        }

        public void noteNetworkStatsEnabled() throws RemoteException {
        }

        public void noteDeviceIdleMode(int mode, String activeReason, int activeUid) throws RemoteException {
        }

        public void setBatteryState(int status, int health, int plugType, int level, int temp, int volt, int chargeUAh, int chargeFullUAh) throws RemoteException {
        }

        public long getAwakeTimeBattery() throws RemoteException {
            return 0;
        }

        public long getAwakeTimePlugged() throws RemoteException {
            return 0;
        }

        public void noteBleScanStarted(WorkSource ws, boolean isUnoptimized) throws RemoteException {
        }

        public void noteBleScanStopped(WorkSource ws, boolean isUnoptimized) throws RemoteException {
        }

        public void noteResetBleScan() throws RemoteException {
        }

        public void noteBleScanResults(WorkSource ws, int numNewResults) throws RemoteException {
        }

        public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
            return null;
        }

        public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
            return null;
        }

        public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
            return null;
        }

        public HealthStatsParceler takeUidSnapshot(int uid) throws RemoteException {
            return null;
        }

        public HealthStatsParceler[] takeUidSnapshots(int[] uid) throws RemoteException {
            return null;
        }

        public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo info) throws RemoteException {
        }

        public void noteModemControllerActivity(ModemActivityInfo info) throws RemoteException {
        }

        public void noteWifiControllerActivity(WifiActivityEnergyInfo info) throws RemoteException {
        }

        public boolean setChargingStateUpdateDelayMillis(int delay) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBatteryStats {
        private static final String DESCRIPTOR = "com.android.internal.app.IBatteryStats";
        static final int TRANSACTION_computeBatteryTimeRemaining = 18;
        static final int TRANSACTION_computeChargeTimeRemaining = 19;
        static final int TRANSACTION_getAwakeTimeBattery = 75;
        static final int TRANSACTION_getAwakeTimePlugged = 76;
        static final int TRANSACTION_getCellularBatteryStats = 81;
        static final int TRANSACTION_getGpsBatteryStats = 83;
        static final int TRANSACTION_getStatistics = 15;
        static final int TRANSACTION_getStatisticsStream = 16;
        static final int TRANSACTION_getWifiBatteryStats = 82;
        static final int TRANSACTION_isCharging = 17;
        static final int TRANSACTION_noteBleScanResults = 80;
        static final int TRANSACTION_noteBleScanStarted = 77;
        static final int TRANSACTION_noteBleScanStopped = 78;
        static final int TRANSACTION_noteBluetoothControllerActivity = 86;
        static final int TRANSACTION_noteChangeWakelockFromSource = 28;
        static final int TRANSACTION_noteConnectivityChanged = 43;
        static final int TRANSACTION_noteDeviceIdleMode = 73;
        static final int TRANSACTION_noteEvent = 20;
        static final int TRANSACTION_noteFlashlightOff = 10;
        static final int TRANSACTION_noteFlashlightOn = 9;
        static final int TRANSACTION_noteFullWifiLockAcquired = 58;
        static final int TRANSACTION_noteFullWifiLockAcquiredFromSource = 64;
        static final int TRANSACTION_noteFullWifiLockReleased = 59;
        static final int TRANSACTION_noteFullWifiLockReleasedFromSource = 65;
        static final int TRANSACTION_noteGpsChanged = 36;
        static final int TRANSACTION_noteGpsSignalQuality = 37;
        static final int TRANSACTION_noteInteractive = 42;
        static final int TRANSACTION_noteJobFinish = 24;
        static final int TRANSACTION_noteJobStart = 23;
        static final int TRANSACTION_noteLongPartialWakelockFinish = 32;
        static final int TRANSACTION_noteLongPartialWakelockFinishFromSource = 33;
        static final int TRANSACTION_noteLongPartialWakelockStart = 30;
        static final int TRANSACTION_noteLongPartialWakelockStartFromSource = 31;
        static final int TRANSACTION_noteMobileRadioPowerState = 44;
        static final int TRANSACTION_noteModemControllerActivity = 87;
        static final int TRANSACTION_noteNetworkInterfaceType = 71;
        static final int TRANSACTION_noteNetworkStatsEnabled = 72;
        static final int TRANSACTION_notePhoneDataConnectionState = 48;
        static final int TRANSACTION_notePhoneOff = 46;
        static final int TRANSACTION_notePhoneOn = 45;
        static final int TRANSACTION_notePhoneSignalStrength = 47;
        static final int TRANSACTION_notePhoneState = 49;
        static final int TRANSACTION_noteResetAudio = 8;
        static final int TRANSACTION_noteResetBleScan = 79;
        static final int TRANSACTION_noteResetCamera = 13;
        static final int TRANSACTION_noteResetFlashlight = 14;
        static final int TRANSACTION_noteResetVideo = 7;
        static final int TRANSACTION_noteScreenBrightness = 39;
        static final int TRANSACTION_noteScreenState = 38;
        static final int TRANSACTION_noteStartAudio = 5;
        static final int TRANSACTION_noteStartCamera = 11;
        static final int TRANSACTION_noteStartSensor = 1;
        static final int TRANSACTION_noteStartVideo = 3;
        static final int TRANSACTION_noteStartWakelock = 25;
        static final int TRANSACTION_noteStartWakelockFromSource = 27;
        static final int TRANSACTION_noteStopAudio = 6;
        static final int TRANSACTION_noteStopCamera = 12;
        static final int TRANSACTION_noteStopSensor = 2;
        static final int TRANSACTION_noteStopVideo = 4;
        static final int TRANSACTION_noteStopWakelock = 26;
        static final int TRANSACTION_noteStopWakelockFromSource = 29;
        static final int TRANSACTION_noteSyncFinish = 22;
        static final int TRANSACTION_noteSyncStart = 21;
        static final int TRANSACTION_noteUserActivity = 40;
        static final int TRANSACTION_noteVibratorOff = 35;
        static final int TRANSACTION_noteVibratorOn = 34;
        static final int TRANSACTION_noteWakeUp = 41;
        static final int TRANSACTION_noteWifiBatchedScanStartedFromSource = 68;
        static final int TRANSACTION_noteWifiBatchedScanStoppedFromSource = 69;
        static final int TRANSACTION_noteWifiControllerActivity = 88;
        static final int TRANSACTION_noteWifiMulticastDisabled = 63;
        static final int TRANSACTION_noteWifiMulticastEnabled = 62;
        static final int TRANSACTION_noteWifiOff = 51;
        static final int TRANSACTION_noteWifiOn = 50;
        static final int TRANSACTION_noteWifiRadioPowerState = 70;
        static final int TRANSACTION_noteWifiRssiChanged = 57;
        static final int TRANSACTION_noteWifiRunning = 52;
        static final int TRANSACTION_noteWifiRunningChanged = 53;
        static final int TRANSACTION_noteWifiScanStarted = 60;
        static final int TRANSACTION_noteWifiScanStartedFromSource = 66;
        static final int TRANSACTION_noteWifiScanStopped = 61;
        static final int TRANSACTION_noteWifiScanStoppedFromSource = 67;
        static final int TRANSACTION_noteWifiState = 55;
        static final int TRANSACTION_noteWifiStopped = 54;
        static final int TRANSACTION_noteWifiSupplicantStateChanged = 56;
        static final int TRANSACTION_setBatteryState = 74;
        static final int TRANSACTION_setChargingStateUpdateDelayMillis = 89;
        static final int TRANSACTION_takeUidSnapshot = 84;
        static final int TRANSACTION_takeUidSnapshots = 85;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBatteryStats asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBatteryStats)) {
                return new Proxy(obj);
            }
            return (IBatteryStats) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "noteStartSensor";
                case 2:
                    return "noteStopSensor";
                case 3:
                    return "noteStartVideo";
                case 4:
                    return "noteStopVideo";
                case 5:
                    return "noteStartAudio";
                case 6:
                    return "noteStopAudio";
                case 7:
                    return "noteResetVideo";
                case 8:
                    return "noteResetAudio";
                case 9:
                    return "noteFlashlightOn";
                case 10:
                    return "noteFlashlightOff";
                case 11:
                    return "noteStartCamera";
                case 12:
                    return "noteStopCamera";
                case 13:
                    return "noteResetCamera";
                case 14:
                    return "noteResetFlashlight";
                case 15:
                    return "getStatistics";
                case 16:
                    return "getStatisticsStream";
                case 17:
                    return "isCharging";
                case 18:
                    return "computeBatteryTimeRemaining";
                case 19:
                    return "computeChargeTimeRemaining";
                case 20:
                    return "noteEvent";
                case 21:
                    return "noteSyncStart";
                case 22:
                    return "noteSyncFinish";
                case 23:
                    return "noteJobStart";
                case 24:
                    return "noteJobFinish";
                case 25:
                    return "noteStartWakelock";
                case 26:
                    return "noteStopWakelock";
                case 27:
                    return "noteStartWakelockFromSource";
                case 28:
                    return "noteChangeWakelockFromSource";
                case 29:
                    return "noteStopWakelockFromSource";
                case 30:
                    return "noteLongPartialWakelockStart";
                case 31:
                    return "noteLongPartialWakelockStartFromSource";
                case 32:
                    return "noteLongPartialWakelockFinish";
                case 33:
                    return "noteLongPartialWakelockFinishFromSource";
                case 34:
                    return "noteVibratorOn";
                case 35:
                    return "noteVibratorOff";
                case 36:
                    return "noteGpsChanged";
                case 37:
                    return "noteGpsSignalQuality";
                case 38:
                    return "noteScreenState";
                case 39:
                    return "noteScreenBrightness";
                case 40:
                    return "noteUserActivity";
                case 41:
                    return "noteWakeUp";
                case 42:
                    return "noteInteractive";
                case 43:
                    return "noteConnectivityChanged";
                case 44:
                    return "noteMobileRadioPowerState";
                case 45:
                    return "notePhoneOn";
                case 46:
                    return "notePhoneOff";
                case 47:
                    return "notePhoneSignalStrength";
                case 48:
                    return "notePhoneDataConnectionState";
                case 49:
                    return "notePhoneState";
                case 50:
                    return "noteWifiOn";
                case 51:
                    return "noteWifiOff";
                case 52:
                    return "noteWifiRunning";
                case 53:
                    return "noteWifiRunningChanged";
                case 54:
                    return "noteWifiStopped";
                case 55:
                    return "noteWifiState";
                case 56:
                    return "noteWifiSupplicantStateChanged";
                case 57:
                    return "noteWifiRssiChanged";
                case 58:
                    return "noteFullWifiLockAcquired";
                case 59:
                    return "noteFullWifiLockReleased";
                case 60:
                    return "noteWifiScanStarted";
                case 61:
                    return "noteWifiScanStopped";
                case 62:
                    return "noteWifiMulticastEnabled";
                case 63:
                    return "noteWifiMulticastDisabled";
                case 64:
                    return "noteFullWifiLockAcquiredFromSource";
                case 65:
                    return "noteFullWifiLockReleasedFromSource";
                case 66:
                    return "noteWifiScanStartedFromSource";
                case 67:
                    return "noteWifiScanStoppedFromSource";
                case 68:
                    return "noteWifiBatchedScanStartedFromSource";
                case 69:
                    return "noteWifiBatchedScanStoppedFromSource";
                case 70:
                    return "noteWifiRadioPowerState";
                case 71:
                    return "noteNetworkInterfaceType";
                case 72:
                    return "noteNetworkStatsEnabled";
                case 73:
                    return "noteDeviceIdleMode";
                case 74:
                    return "setBatteryState";
                case 75:
                    return "getAwakeTimeBattery";
                case 76:
                    return "getAwakeTimePlugged";
                case 77:
                    return "noteBleScanStarted";
                case 78:
                    return "noteBleScanStopped";
                case 79:
                    return "noteResetBleScan";
                case 80:
                    return "noteBleScanResults";
                case 81:
                    return "getCellularBatteryStats";
                case 82:
                    return "getWifiBatteryStats";
                case 83:
                    return "getGpsBatteryStats";
                case 84:
                    return "takeUidSnapshot";
                case 85:
                    return "takeUidSnapshots";
                case 86:
                    return "noteBluetoothControllerActivity";
                case 87:
                    return "noteModemControllerActivity";
                case 88:
                    return "noteWifiControllerActivity";
                case 89:
                    return "setChargingStateUpdateDelayMillis";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v21, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v25, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v28, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v52, resolved type: android.telephony.SignalStrength} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v32, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v35, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v38, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v42, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v45, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v48, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v51, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v54, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v57, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v62, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v66, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v70, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v74, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v122, resolved type: android.bluetooth.BluetoothActivityEnergyInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v77, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v126, resolved type: android.telephony.ModemActivityInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v80, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v130, resolved type: android.net.wifi.WifiActivityEnergyInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v83, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v84, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v85, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v86, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v87, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v88, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v89, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v90, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v91, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v92, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v93, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v94, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v95, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v96, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v97, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v98, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v99, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v100, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v101, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v102, resolved type: android.os.WorkSource} */
        /* JADX WARNING: type inference failed for: r2v30, types: [android.telephony.SignalStrength] */
        /* JADX WARNING: type inference failed for: r2v76, types: [android.bluetooth.BluetoothActivityEnergyInfo] */
        /* JADX WARNING: type inference failed for: r2v79, types: [android.telephony.ModemActivityInfo] */
        /* JADX WARNING: type inference failed for: r2v82, types: [android.net.wifi.WifiActivityEnergyInfo] */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r26, android.os.Parcel r27, android.os.Parcel r28, int r29) throws android.os.RemoteException {
            /*
                r25 = this;
                r12 = r25
                r13 = r26
                r14 = r27
                r15 = r28
                java.lang.String r10 = "com.android.internal.app.IBatteryStats"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r9 = 1
                if (r13 == r0) goto L_0x0824
                r0 = 0
                r2 = 0
                switch(r13) {
                    case 1: goto L_0x0810;
                    case 2: goto L_0x07fc;
                    case 3: goto L_0x07ec;
                    case 4: goto L_0x07dc;
                    case 5: goto L_0x07cc;
                    case 6: goto L_0x07bc;
                    case 7: goto L_0x07b0;
                    case 8: goto L_0x07a4;
                    case 9: goto L_0x0794;
                    case 10: goto L_0x0784;
                    case 11: goto L_0x0774;
                    case 12: goto L_0x0764;
                    case 13: goto L_0x0758;
                    case 14: goto L_0x074c;
                    case 15: goto L_0x073c;
                    case 16: goto L_0x0723;
                    case 17: goto L_0x0713;
                    case 18: goto L_0x0703;
                    case 19: goto L_0x06f3;
                    case 20: goto L_0x06db;
                    case 21: goto L_0x06c7;
                    case 22: goto L_0x06b3;
                    case 23: goto L_0x0697;
                    case 24: goto L_0x0670;
                    case 25: goto L_0x063f;
                    case 26: goto L_0x0618;
                    case 27: goto L_0x05da;
                    case 28: goto L_0x0571;
                    case 29: goto L_0x0540;
                    case 30: goto L_0x052a;
                    case 31: goto L_0x0508;
                    case 32: goto L_0x04f2;
                    case 33: goto L_0x04d0;
                    case 34: goto L_0x04be;
                    case 35: goto L_0x04b0;
                    case 36: goto L_0x0484;
                    case 37: goto L_0x0476;
                    case 38: goto L_0x0468;
                    case 39: goto L_0x045a;
                    case 40: goto L_0x0448;
                    case 41: goto L_0x0436;
                    case 42: goto L_0x0424;
                    case 43: goto L_0x0412;
                    case 44: goto L_0x03fc;
                    case 45: goto L_0x03f2;
                    case 46: goto L_0x03e8;
                    case 47: goto L_0x03cc;
                    case 48: goto L_0x03b6;
                    case 49: goto L_0x03a8;
                    case 50: goto L_0x039e;
                    case 51: goto L_0x0394;
                    case 52: goto L_0x0378;
                    case 53: goto L_0x034c;
                    case 54: goto L_0x0330;
                    case 55: goto L_0x031e;
                    case 56: goto L_0x0308;
                    case 57: goto L_0x02fa;
                    case 58: goto L_0x02ec;
                    case 59: goto L_0x02de;
                    case 60: goto L_0x02d0;
                    case 61: goto L_0x02c2;
                    case 62: goto L_0x02b4;
                    case 63: goto L_0x02a6;
                    case 64: goto L_0x028a;
                    case 65: goto L_0x026e;
                    case 66: goto L_0x0252;
                    case 67: goto L_0x0236;
                    case 68: goto L_0x0216;
                    case 69: goto L_0x01fa;
                    case 70: goto L_0x01e4;
                    case 71: goto L_0x01d2;
                    case 72: goto L_0x01c8;
                    case 73: goto L_0x01b2;
                    case 74: goto L_0x0177;
                    case 75: goto L_0x0169;
                    case 76: goto L_0x015b;
                    case 77: goto L_0x0137;
                    case 78: goto L_0x0113;
                    case 79: goto L_0x0109;
                    case 80: goto L_0x00e9;
                    case 81: goto L_0x00d2;
                    case 82: goto L_0x00bb;
                    case 83: goto L_0x00a4;
                    case 84: goto L_0x0089;
                    case 85: goto L_0x0077;
                    case 86: goto L_0x005e;
                    case 87: goto L_0x0045;
                    case 88: goto L_0x002c;
                    case 89: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r26, r27, r28, r29)
                return r0
            L_0x001a:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                boolean r1 = r12.setChargingStateUpdateDelayMillis(r0)
                r28.writeNoException()
                r15.writeInt(r1)
                return r9
            L_0x002c:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x003f
                android.os.Parcelable$Creator<android.net.wifi.WifiActivityEnergyInfo> r0 = android.net.wifi.WifiActivityEnergyInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.net.wifi.WifiActivityEnergyInfo r2 = (android.net.wifi.WifiActivityEnergyInfo) r2
                goto L_0x0040
            L_0x003f:
            L_0x0040:
                r0 = r2
                r12.noteWifiControllerActivity(r0)
                return r9
            L_0x0045:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0058
                android.os.Parcelable$Creator<android.telephony.ModemActivityInfo> r0 = android.telephony.ModemActivityInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.telephony.ModemActivityInfo r2 = (android.telephony.ModemActivityInfo) r2
                goto L_0x0059
            L_0x0058:
            L_0x0059:
                r0 = r2
                r12.noteModemControllerActivity(r0)
                return r9
            L_0x005e:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0071
                android.os.Parcelable$Creator<android.bluetooth.BluetoothActivityEnergyInfo> r0 = android.bluetooth.BluetoothActivityEnergyInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.bluetooth.BluetoothActivityEnergyInfo r2 = (android.bluetooth.BluetoothActivityEnergyInfo) r2
                goto L_0x0072
            L_0x0071:
            L_0x0072:
                r0 = r2
                r12.noteBluetoothControllerActivity(r0)
                return r9
            L_0x0077:
                r14.enforceInterface(r10)
                int[] r0 = r27.createIntArray()
                android.os.health.HealthStatsParceler[] r1 = r12.takeUidSnapshots(r0)
                r28.writeNoException()
                r15.writeTypedArray(r1, r9)
                return r9
            L_0x0089:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                android.os.health.HealthStatsParceler r2 = r12.takeUidSnapshot(r1)
                r28.writeNoException()
                if (r2 == 0) goto L_0x00a0
                r15.writeInt(r9)
                r2.writeToParcel(r15, r9)
                goto L_0x00a3
            L_0x00a0:
                r15.writeInt(r0)
            L_0x00a3:
                return r9
            L_0x00a4:
                r14.enforceInterface(r10)
                android.os.connectivity.GpsBatteryStats r1 = r25.getGpsBatteryStats()
                r28.writeNoException()
                if (r1 == 0) goto L_0x00b7
                r15.writeInt(r9)
                r1.writeToParcel(r15, r9)
                goto L_0x00ba
            L_0x00b7:
                r15.writeInt(r0)
            L_0x00ba:
                return r9
            L_0x00bb:
                r14.enforceInterface(r10)
                android.os.connectivity.WifiBatteryStats r1 = r25.getWifiBatteryStats()
                r28.writeNoException()
                if (r1 == 0) goto L_0x00ce
                r15.writeInt(r9)
                r1.writeToParcel(r15, r9)
                goto L_0x00d1
            L_0x00ce:
                r15.writeInt(r0)
            L_0x00d1:
                return r9
            L_0x00d2:
                r14.enforceInterface(r10)
                android.os.connectivity.CellularBatteryStats r1 = r25.getCellularBatteryStats()
                r28.writeNoException()
                if (r1 == 0) goto L_0x00e5
                r15.writeInt(r9)
                r1.writeToParcel(r15, r9)
                goto L_0x00e8
            L_0x00e5:
                r15.writeInt(r0)
            L_0x00e8:
                return r9
            L_0x00e9:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x00fc
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x00fd
            L_0x00fc:
            L_0x00fd:
                r0 = r2
                int r1 = r27.readInt()
                r12.noteBleScanResults(r0, r1)
                r28.writeNoException()
                return r9
            L_0x0109:
                r14.enforceInterface(r10)
                r25.noteResetBleScan()
                r28.writeNoException()
                return r9
            L_0x0113:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x0126
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                r2 = r1
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x0127
            L_0x0126:
            L_0x0127:
                r1 = r2
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x0130
                r0 = r9
            L_0x0130:
                r12.noteBleScanStopped(r1, r0)
                r28.writeNoException()
                return r9
            L_0x0137:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x014a
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                r2 = r1
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x014b
            L_0x014a:
            L_0x014b:
                r1 = r2
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x0154
                r0 = r9
            L_0x0154:
                r12.noteBleScanStarted(r1, r0)
                r28.writeNoException()
                return r9
            L_0x015b:
                r14.enforceInterface(r10)
                long r0 = r25.getAwakeTimePlugged()
                r28.writeNoException()
                r15.writeLong(r0)
                return r9
            L_0x0169:
                r14.enforceInterface(r10)
                long r0 = r25.getAwakeTimeBattery()
                r28.writeNoException()
                r15.writeLong(r0)
                return r9
            L_0x0177:
                r14.enforceInterface(r10)
                int r11 = r27.readInt()
                int r16 = r27.readInt()
                int r17 = r27.readInt()
                int r18 = r27.readInt()
                int r19 = r27.readInt()
                int r20 = r27.readInt()
                int r21 = r27.readInt()
                int r22 = r27.readInt()
                r0 = r25
                r1 = r11
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r0.setBatteryState(r1, r2, r3, r4, r5, r6, r7, r8)
                r28.writeNoException()
                return r9
            L_0x01b2:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                java.lang.String r1 = r27.readString()
                int r2 = r27.readInt()
                r12.noteDeviceIdleMode(r0, r1, r2)
                r28.writeNoException()
                return r9
            L_0x01c8:
                r14.enforceInterface(r10)
                r25.noteNetworkStatsEnabled()
                r28.writeNoException()
                return r9
            L_0x01d2:
                r14.enforceInterface(r10)
                java.lang.String r0 = r27.readString()
                int r1 = r27.readInt()
                r12.noteNetworkInterfaceType(r0, r1)
                r28.writeNoException()
                return r9
            L_0x01e4:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                long r1 = r27.readLong()
                int r3 = r27.readInt()
                r12.noteWifiRadioPowerState(r0, r1, r3)
                r28.writeNoException()
                return r9
            L_0x01fa:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x020d
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x020e
            L_0x020d:
            L_0x020e:
                r0 = r2
                r12.noteWifiBatchedScanStoppedFromSource(r0)
                r28.writeNoException()
                return r9
            L_0x0216:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0229
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x022a
            L_0x0229:
            L_0x022a:
                r0 = r2
                int r1 = r27.readInt()
                r12.noteWifiBatchedScanStartedFromSource(r0, r1)
                r28.writeNoException()
                return r9
            L_0x0236:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0249
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x024a
            L_0x0249:
            L_0x024a:
                r0 = r2
                r12.noteWifiScanStoppedFromSource(r0)
                r28.writeNoException()
                return r9
            L_0x0252:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0265
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x0266
            L_0x0265:
            L_0x0266:
                r0 = r2
                r12.noteWifiScanStartedFromSource(r0)
                r28.writeNoException()
                return r9
            L_0x026e:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0281
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x0282
            L_0x0281:
            L_0x0282:
                r0 = r2
                r12.noteFullWifiLockReleasedFromSource(r0)
                r28.writeNoException()
                return r9
            L_0x028a:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x029d
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x029e
            L_0x029d:
            L_0x029e:
                r0 = r2
                r12.noteFullWifiLockAcquiredFromSource(r0)
                r28.writeNoException()
                return r9
            L_0x02a6:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteWifiMulticastDisabled(r0)
                r28.writeNoException()
                return r9
            L_0x02b4:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteWifiMulticastEnabled(r0)
                r28.writeNoException()
                return r9
            L_0x02c2:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteWifiScanStopped(r0)
                r28.writeNoException()
                return r9
            L_0x02d0:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteWifiScanStarted(r0)
                r28.writeNoException()
                return r9
            L_0x02de:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteFullWifiLockReleased(r0)
                r28.writeNoException()
                return r9
            L_0x02ec:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteFullWifiLockAcquired(r0)
                r28.writeNoException()
                return r9
            L_0x02fa:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteWifiRssiChanged(r0)
                r28.writeNoException()
                return r9
            L_0x0308:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x0317
                r0 = r9
            L_0x0317:
                r12.noteWifiSupplicantStateChanged(r1, r0)
                r28.writeNoException()
                return r9
            L_0x031e:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                java.lang.String r1 = r27.readString()
                r12.noteWifiState(r0, r1)
                r28.writeNoException()
                return r9
            L_0x0330:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0343
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x0344
            L_0x0343:
            L_0x0344:
                r0 = r2
                r12.noteWifiStopped(r0)
                r28.writeNoException()
                return r9
            L_0x034c:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x035e
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                goto L_0x035f
            L_0x035e:
                r0 = r2
            L_0x035f:
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x036f
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                r2 = r1
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x0370
            L_0x036f:
            L_0x0370:
                r1 = r2
                r12.noteWifiRunningChanged(r0, r1)
                r28.writeNoException()
                return r9
            L_0x0378:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x038b
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x038c
            L_0x038b:
            L_0x038c:
                r0 = r2
                r12.noteWifiRunning(r0)
                r28.writeNoException()
                return r9
            L_0x0394:
                r14.enforceInterface(r10)
                r25.noteWifiOff()
                r28.writeNoException()
                return r9
            L_0x039e:
                r14.enforceInterface(r10)
                r25.noteWifiOn()
                r28.writeNoException()
                return r9
            L_0x03a8:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.notePhoneState(r0)
                r28.writeNoException()
                return r9
            L_0x03b6:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x03c5
                r0 = r9
            L_0x03c5:
                r12.notePhoneDataConnectionState(r1, r0)
                r28.writeNoException()
                return r9
            L_0x03cc:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x03df
                android.os.Parcelable$Creator<android.telephony.SignalStrength> r0 = android.telephony.SignalStrength.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r2 = r0
                android.telephony.SignalStrength r2 = (android.telephony.SignalStrength) r2
                goto L_0x03e0
            L_0x03df:
            L_0x03e0:
                r0 = r2
                r12.notePhoneSignalStrength(r0)
                r28.writeNoException()
                return r9
            L_0x03e8:
                r14.enforceInterface(r10)
                r25.notePhoneOff()
                r28.writeNoException()
                return r9
            L_0x03f2:
                r14.enforceInterface(r10)
                r25.notePhoneOn()
                r28.writeNoException()
                return r9
            L_0x03fc:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                long r1 = r27.readLong()
                int r3 = r27.readInt()
                r12.noteMobileRadioPowerState(r0, r1, r3)
                r28.writeNoException()
                return r9
            L_0x0412:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                java.lang.String r1 = r27.readString()
                r12.noteConnectivityChanged(r0, r1)
                r28.writeNoException()
                return r9
            L_0x0424:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x042f
                r0 = r9
            L_0x042f:
                r12.noteInteractive(r0)
                r28.writeNoException()
                return r9
            L_0x0436:
                r14.enforceInterface(r10)
                java.lang.String r0 = r27.readString()
                int r1 = r27.readInt()
                r12.noteWakeUp(r0, r1)
                r28.writeNoException()
                return r9
            L_0x0448:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                int r1 = r27.readInt()
                r12.noteUserActivity(r0, r1)
                r28.writeNoException()
                return r9
            L_0x045a:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteScreenBrightness(r0)
                r28.writeNoException()
                return r9
            L_0x0468:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteScreenState(r0)
                r28.writeNoException()
                return r9
            L_0x0476:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteGpsSignalQuality(r0)
                r28.writeNoException()
                return r9
            L_0x0484:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0496
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                goto L_0x0497
            L_0x0496:
                r0 = r2
            L_0x0497:
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x04a7
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                r2 = r1
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x04a8
            L_0x04a7:
            L_0x04a8:
                r1 = r2
                r12.noteGpsChanged(r0, r1)
                r28.writeNoException()
                return r9
            L_0x04b0:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                r12.noteVibratorOff(r0)
                r28.writeNoException()
                return r9
            L_0x04be:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                long r1 = r27.readLong()
                r12.noteVibratorOn(r0, r1)
                r28.writeNoException()
                return r9
            L_0x04d0:
                r14.enforceInterface(r10)
                java.lang.String r0 = r27.readString()
                java.lang.String r1 = r27.readString()
                int r3 = r27.readInt()
                if (r3 == 0) goto L_0x04ea
                android.os.Parcelable$Creator<android.os.WorkSource> r2 = android.os.WorkSource.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x04eb
            L_0x04ea:
            L_0x04eb:
                r12.noteLongPartialWakelockFinishFromSource(r0, r1, r2)
                r28.writeNoException()
                return r9
            L_0x04f2:
                r14.enforceInterface(r10)
                java.lang.String r0 = r27.readString()
                java.lang.String r1 = r27.readString()
                int r2 = r27.readInt()
                r12.noteLongPartialWakelockFinish(r0, r1, r2)
                r28.writeNoException()
                return r9
            L_0x0508:
                r14.enforceInterface(r10)
                java.lang.String r0 = r27.readString()
                java.lang.String r1 = r27.readString()
                int r3 = r27.readInt()
                if (r3 == 0) goto L_0x0522
                android.os.Parcelable$Creator<android.os.WorkSource> r2 = android.os.WorkSource.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.os.WorkSource r2 = (android.os.WorkSource) r2
                goto L_0x0523
            L_0x0522:
            L_0x0523:
                r12.noteLongPartialWakelockStartFromSource(r0, r1, r2)
                r28.writeNoException()
                return r9
            L_0x052a:
                r14.enforceInterface(r10)
                java.lang.String r0 = r27.readString()
                java.lang.String r1 = r27.readString()
                int r2 = r27.readInt()
                r12.noteLongPartialWakelockStart(r0, r1, r2)
                r28.writeNoException()
                return r9
            L_0x0540:
                r14.enforceInterface(r10)
                int r0 = r27.readInt()
                if (r0 == 0) goto L_0x0553
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                r1 = r0
                goto L_0x0554
            L_0x0553:
                r1 = r2
            L_0x0554:
                int r6 = r27.readInt()
                java.lang.String r7 = r27.readString()
                java.lang.String r8 = r27.readString()
                int r11 = r27.readInt()
                r0 = r25
                r2 = r6
                r3 = r7
                r4 = r8
                r5 = r11
                r0.noteStopWakelockFromSource(r1, r2, r3, r4, r5)
                r28.writeNoException()
                return r9
            L_0x0571:
                r14.enforceInterface(r10)
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x0583
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.os.WorkSource r1 = (android.os.WorkSource) r1
                goto L_0x0584
            L_0x0583:
                r1 = r2
            L_0x0584:
                int r16 = r27.readInt()
                java.lang.String r17 = r27.readString()
                java.lang.String r18 = r27.readString()
                int r19 = r27.readInt()
                int r3 = r27.readInt()
                if (r3 == 0) goto L_0x05a4
                android.os.Parcelable$Creator<android.os.WorkSource> r2 = android.os.WorkSource.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.os.WorkSource r2 = (android.os.WorkSource) r2
            L_0x05a2:
                r6 = r2
                goto L_0x05a5
            L_0x05a4:
                goto L_0x05a2
            L_0x05a5:
                int r20 = r27.readInt()
                java.lang.String r21 = r27.readString()
                java.lang.String r22 = r27.readString()
                int r23 = r27.readInt()
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x05bd
                r11 = r9
                goto L_0x05be
            L_0x05bd:
                r11 = r0
            L_0x05be:
                r0 = r25
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                r7 = r20
                r8 = r21
                r13 = r9
                r9 = r22
                r24 = r10
                r10 = r23
                r0.noteChangeWakelockFromSource(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r28.writeNoException()
                return r13
            L_0x05da:
                r13 = r9
                r24 = r10
                r7 = r24
                r14.enforceInterface(r7)
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x05f1
                android.os.Parcelable$Creator<android.os.WorkSource> r1 = android.os.WorkSource.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.os.WorkSource r1 = (android.os.WorkSource) r1
                goto L_0x05f2
            L_0x05f1:
                r1 = r2
            L_0x05f2:
                int r8 = r27.readInt()
                java.lang.String r9 = r27.readString()
                java.lang.String r10 = r27.readString()
                int r11 = r27.readInt()
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x060a
                r6 = r13
                goto L_0x060b
            L_0x060a:
                r6 = r0
            L_0x060b:
                r0 = r25
                r2 = r8
                r3 = r9
                r4 = r10
                r5 = r11
                r0.noteStartWakelockFromSource(r1, r2, r3, r4, r5, r6)
                r28.writeNoException()
                return r13
            L_0x0618:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r6 = r27.readInt()
                int r8 = r27.readInt()
                java.lang.String r9 = r27.readString()
                java.lang.String r10 = r27.readString()
                int r11 = r27.readInt()
                r0 = r25
                r1 = r6
                r2 = r8
                r3 = r9
                r4 = r10
                r5 = r11
                r0.noteStopWakelock(r1, r2, r3, r4, r5)
                r28.writeNoException()
                return r13
            L_0x063f:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r8 = r27.readInt()
                int r9 = r27.readInt()
                java.lang.String r10 = r27.readString()
                java.lang.String r11 = r27.readString()
                int r16 = r27.readInt()
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x0660
                r6 = r13
                goto L_0x0661
            L_0x0660:
                r6 = r0
            L_0x0661:
                r0 = r25
                r1 = r8
                r2 = r9
                r3 = r10
                r4 = r11
                r5 = r16
                r0.noteStartWakelock(r1, r2, r3, r4, r5, r6)
                r28.writeNoException()
                return r13
            L_0x0670:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                java.lang.String r6 = r27.readString()
                int r8 = r27.readInt()
                int r9 = r27.readInt()
                int r10 = r27.readInt()
                int r11 = r27.readInt()
                r0 = r25
                r1 = r6
                r2 = r8
                r3 = r9
                r4 = r10
                r5 = r11
                r0.noteJobFinish(r1, r2, r3, r4, r5)
                r28.writeNoException()
                return r13
            L_0x0697:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                java.lang.String r0 = r27.readString()
                int r1 = r27.readInt()
                int r2 = r27.readInt()
                int r3 = r27.readInt()
                r12.noteJobStart(r0, r1, r2, r3)
                r28.writeNoException()
                return r13
            L_0x06b3:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                java.lang.String r0 = r27.readString()
                int r1 = r27.readInt()
                r12.noteSyncFinish(r0, r1)
                r28.writeNoException()
                return r13
            L_0x06c7:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                java.lang.String r0 = r27.readString()
                int r1 = r27.readInt()
                r12.noteSyncStart(r0, r1)
                r28.writeNoException()
                return r13
            L_0x06db:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                java.lang.String r1 = r27.readString()
                int r2 = r27.readInt()
                r12.noteEvent(r0, r1, r2)
                r28.writeNoException()
                return r13
            L_0x06f3:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                long r0 = r25.computeChargeTimeRemaining()
                r28.writeNoException()
                r15.writeLong(r0)
                return r13
            L_0x0703:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                long r0 = r25.computeBatteryTimeRemaining()
                r28.writeNoException()
                r15.writeLong(r0)
                return r13
            L_0x0713:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                boolean r0 = r25.isCharging()
                r28.writeNoException()
                r15.writeInt(r0)
                return r13
            L_0x0723:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                android.os.ParcelFileDescriptor r1 = r25.getStatisticsStream()
                r28.writeNoException()
                if (r1 == 0) goto L_0x0738
                r15.writeInt(r13)
                r1.writeToParcel(r15, r13)
                goto L_0x073b
            L_0x0738:
                r15.writeInt(r0)
            L_0x073b:
                return r13
            L_0x073c:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                byte[] r0 = r25.getStatistics()
                r28.writeNoException()
                r15.writeByteArray(r0)
                return r13
            L_0x074c:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                r25.noteResetFlashlight()
                r28.writeNoException()
                return r13
            L_0x0758:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                r25.noteResetCamera()
                r28.writeNoException()
                return r13
            L_0x0764:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteStopCamera(r0)
                r28.writeNoException()
                return r13
            L_0x0774:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteStartCamera(r0)
                r28.writeNoException()
                return r13
            L_0x0784:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteFlashlightOff(r0)
                r28.writeNoException()
                return r13
            L_0x0794:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteFlashlightOn(r0)
                r28.writeNoException()
                return r13
            L_0x07a4:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                r25.noteResetAudio()
                r28.writeNoException()
                return r13
            L_0x07b0:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                r25.noteResetVideo()
                r28.writeNoException()
                return r13
            L_0x07bc:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteStopAudio(r0)
                r28.writeNoException()
                return r13
            L_0x07cc:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteStartAudio(r0)
                r28.writeNoException()
                return r13
            L_0x07dc:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteStopVideo(r0)
                r28.writeNoException()
                return r13
            L_0x07ec:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                r12.noteStartVideo(r0)
                r28.writeNoException()
                return r13
            L_0x07fc:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                int r1 = r27.readInt()
                r12.noteStopSensor(r0, r1)
                r28.writeNoException()
                return r13
            L_0x0810:
                r13 = r9
                r7 = r10
                r14.enforceInterface(r7)
                int r0 = r27.readInt()
                int r1 = r27.readInt()
                r12.noteStartSensor(r0, r1)
                r28.writeNoException()
                return r13
            L_0x0824:
                r13 = r9
                r7 = r10
                r15.writeString(r7)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.IBatteryStats.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IBatteryStats {
            public static IBatteryStats sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void noteStartSensor(int uid, int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(sensor);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStartSensor(uid, sensor);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStopSensor(int uid, int sensor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(sensor);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStopSensor(uid, sensor);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStartVideo(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStartVideo(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStopVideo(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStopVideo(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStartAudio(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStartAudio(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStopAudio(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStopAudio(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteResetVideo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteResetVideo();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteResetAudio() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteResetAudio();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteFlashlightOn(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteFlashlightOn(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteFlashlightOff(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteFlashlightOff(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStartCamera(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStartCamera(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStopCamera(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStopCamera(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteResetCamera() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteResetCamera();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteResetFlashlight() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteResetFlashlight();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getStatistics() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatistics();
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getStatisticsStream() throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStatisticsStream();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isCharging() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCharging();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long computeBatteryTimeRemaining() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().computeBatteryTimeRemaining();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long computeChargeTimeRemaining() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().computeChargeTimeRemaining();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteEvent(int code, String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(code);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteEvent(code, name, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteSyncStart(String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteSyncStart(name, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteSyncFinish(String name, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteSyncFinish(name, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteJobStart(String name, int uid, int standbyBucket, int jobid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    _data.writeInt(standbyBucket);
                    _data.writeInt(jobid);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteJobStart(name, uid, standbyBucket, jobid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteJobFinish(String name, int uid, int stopReason, int standbyBucket, int jobid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(uid);
                    _data.writeInt(stopReason);
                    _data.writeInt(standbyBucket);
                    _data.writeInt(jobid);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteJobFinish(name, uid, stopReason, standbyBucket, jobid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStartWakelock(int uid, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(uid);
                        try {
                            _data.writeInt(pid);
                            try {
                                _data.writeString(name);
                                try {
                                    _data.writeString(historyName);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = type;
                                    boolean z = unimportantForLogging;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                String str = historyName;
                                int i2 = type;
                                boolean z2 = unimportantForLogging;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = name;
                            String str3 = historyName;
                            int i22 = type;
                            boolean z22 = unimportantForLogging;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(type);
                            try {
                                _data.writeInt(unimportantForLogging ? 1 : 0);
                                if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().noteStartWakelock(uid, pid, name, historyName, type, unimportantForLogging);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            boolean z222 = unimportantForLogging;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        int i3 = pid;
                        String str22 = name;
                        String str32 = historyName;
                        int i222 = type;
                        boolean z2222 = unimportantForLogging;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i4 = uid;
                    int i32 = pid;
                    String str222 = name;
                    String str322 = historyName;
                    int i2222 = type;
                    boolean z22222 = unimportantForLogging;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void noteStopWakelock(int uid, int pid, String name, String historyName, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(type);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStopWakelock(uid, pid, name, historyName, type);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteStartWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type, boolean unimportantForLogging) throws RemoteException {
                WorkSource workSource = ws;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(pid);
                        try {
                            _data.writeString(name);
                        } catch (Throwable th) {
                            th = th;
                            String str = historyName;
                            int i = type;
                            boolean z = unimportantForLogging;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(historyName);
                            try {
                                _data.writeInt(type);
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z2 = unimportantForLogging;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeInt(unimportantForLogging ? 1 : 0);
                                if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().noteStartWakelockFromSource(ws, pid, name, historyName, type, unimportantForLogging);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i2 = type;
                            boolean z22 = unimportantForLogging;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        String str2 = name;
                        String str3 = historyName;
                        int i22 = type;
                        boolean z222 = unimportantForLogging;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i3 = pid;
                    String str22 = name;
                    String str32 = historyName;
                    int i222 = type;
                    boolean z2222 = unimportantForLogging;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void noteChangeWakelockFromSource(WorkSource ws, int pid, String name, String histyoryName, int type, WorkSource newWs, int newPid, String newName, String newHistoryName, int newType, boolean newUnimportantForLogging) throws RemoteException {
                Parcel _reply;
                WorkSource workSource = ws;
                WorkSource workSource2 = newWs;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (workSource != null) {
                        try {
                            _data.writeInt(1);
                            workSource.writeToParcel(_data, 0);
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                        }
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(histyoryName);
                    _data.writeInt(type);
                    if (workSource2 != null) {
                        _data.writeInt(1);
                        workSource2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(newPid);
                    _data.writeString(newName);
                    _data.writeString(newHistoryName);
                    _data.writeInt(newType);
                    _data.writeInt(newUnimportantForLogging ? 1 : 0);
                    if (this.mRemote.transact(28, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply = _reply2;
                    try {
                        Stub.getDefaultImpl().noteChangeWakelockFromSource(ws, pid, name, histyoryName, type, newWs, newPid, newName, newHistoryName, newType, newUnimportantForLogging);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void noteStopWakelockFromSource(WorkSource ws, int pid, String name, String historyName, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(pid);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(type);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteStopWakelockFromSource(ws, pid, name, historyName, type);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteLongPartialWakelockStart(String name, String historyName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteLongPartialWakelockStart(name, historyName, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteLongPartialWakelockStartFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteLongPartialWakelockStartFromSource(name, historyName, workSource);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteLongPartialWakelockFinish(String name, String historyName, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteLongPartialWakelockFinish(name, historyName, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteLongPartialWakelockFinishFromSource(String name, String historyName, WorkSource workSource) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(historyName);
                    if (workSource != null) {
                        _data.writeInt(1);
                        workSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteLongPartialWakelockFinishFromSource(name, historyName, workSource);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteVibratorOn(int uid, long durationMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeLong(durationMillis);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteVibratorOn(uid, durationMillis);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteVibratorOff(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteVibratorOff(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteGpsChanged(WorkSource oldSource, WorkSource newSource) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (oldSource != null) {
                        _data.writeInt(1);
                        oldSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (newSource != null) {
                        _data.writeInt(1);
                        newSource.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteGpsChanged(oldSource, newSource);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteGpsSignalQuality(int signalLevel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(signalLevel);
                    if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteGpsSignalQuality(signalLevel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteScreenState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(state);
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteScreenState(state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteScreenBrightness(int brightness) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(brightness);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteScreenBrightness(brightness);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteUserActivity(int uid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(event);
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteUserActivity(uid, event);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWakeUp(String reason, int reasonUid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    _data.writeInt(reasonUid);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWakeUp(reason, reasonUid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteInteractive(boolean interactive) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(interactive);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteInteractive(interactive);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteConnectivityChanged(int type, String extra) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(extra);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteConnectivityChanged(type, extra);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteMobileRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(powerState);
                    _data.writeLong(timestampNs);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteMobileRadioPowerState(powerState, timestampNs, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notePhoneOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(45, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notePhoneOn();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notePhoneOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notePhoneOff();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notePhoneSignalStrength(SignalStrength signalStrength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (signalStrength != null) {
                        _data.writeInt(1);
                        signalStrength.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notePhoneSignalStrength(signalStrength);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notePhoneDataConnectionState(int dataType, boolean hasData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(dataType);
                    _data.writeInt(hasData);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notePhoneDataConnectionState(dataType, hasData);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notePhoneState(int phoneState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneState);
                    if (this.mRemote.transact(49, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notePhoneState(phoneState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiOn();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiOff();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiRunning(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiRunning(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiRunningChanged(WorkSource oldWs, WorkSource newWs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (oldWs != null) {
                        _data.writeInt(1);
                        oldWs.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (newWs != null) {
                        _data.writeInt(1);
                        newWs.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiRunningChanged(oldWs, newWs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiStopped(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiStopped(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiState(int wifiState, String accessPoint) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(wifiState);
                    _data.writeString(accessPoint);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiState(wifiState, accessPoint);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiSupplicantStateChanged(int supplState, boolean failedAuth) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(supplState);
                    _data.writeInt(failedAuth);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiSupplicantStateChanged(supplState, failedAuth);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiRssiChanged(int newRssi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newRssi);
                    if (this.mRemote.transact(57, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiRssiChanged(newRssi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteFullWifiLockAcquired(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(58, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteFullWifiLockAcquired(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteFullWifiLockReleased(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteFullWifiLockReleased(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiScanStarted(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiScanStarted(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiScanStopped(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiScanStopped(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiMulticastEnabled(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(62, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiMulticastEnabled(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiMulticastDisabled(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(63, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiMulticastDisabled(uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteFullWifiLockAcquiredFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteFullWifiLockAcquiredFromSource(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteFullWifiLockReleasedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteFullWifiLockReleasedFromSource(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiScanStartedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(66, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiScanStartedFromSource(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiScanStoppedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(67, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiScanStoppedFromSource(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiBatchedScanStartedFromSource(WorkSource ws, int csph) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(csph);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiBatchedScanStartedFromSource(ws, csph);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiBatchedScanStoppedFromSource(WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(69, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiBatchedScanStoppedFromSource(ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteWifiRadioPowerState(int powerState, long timestampNs, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(powerState);
                    _data.writeLong(timestampNs);
                    _data.writeInt(uid);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteWifiRadioPowerState(powerState, timestampNs, uid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteNetworkInterfaceType(String iface, int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(type);
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteNetworkInterfaceType(iface, type);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteNetworkStatsEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(72, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteNetworkStatsEnabled();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteDeviceIdleMode(int mode, String activeReason, int activeUid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(activeReason);
                    _data.writeInt(activeUid);
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteDeviceIdleMode(mode, activeReason, activeUid);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBatteryState(int status, int health, int plugType, int level, int temp, int volt, int chargeUAh, int chargeFullUAh) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(status);
                        try {
                            _data.writeInt(health);
                        } catch (Throwable th) {
                            th = th;
                            int i = plugType;
                            int i2 = level;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(plugType);
                            try {
                                _data.writeInt(level);
                                _data.writeInt(temp);
                                _data.writeInt(volt);
                                _data.writeInt(chargeUAh);
                                _data.writeInt(chargeFullUAh);
                                if (this.mRemote.transact(74, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().setBatteryState(status, health, plugType, level, temp, volt, chargeUAh, chargeFullUAh);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = level;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i3 = health;
                        int i4 = plugType;
                        int i222 = level;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i5 = status;
                    int i32 = health;
                    int i42 = plugType;
                    int i2222 = level;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public long getAwakeTimeBattery() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAwakeTimeBattery();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getAwakeTimePlugged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAwakeTimePlugged();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteBleScanStarted(WorkSource ws, boolean isUnoptimized) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isUnoptimized);
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteBleScanStarted(ws, isUnoptimized);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteBleScanStopped(WorkSource ws, boolean isUnoptimized) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isUnoptimized);
                    if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteBleScanStopped(ws, isUnoptimized);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteResetBleScan() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteResetBleScan();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteBleScanResults(WorkSource ws, int numNewResults) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(numNewResults);
                    if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().noteBleScanResults(ws, numNewResults);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CellularBatteryStats getCellularBatteryStats() throws RemoteException {
                CellularBatteryStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(81, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCellularBatteryStats();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = CellularBatteryStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CellularBatteryStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public WifiBatteryStats getWifiBatteryStats() throws RemoteException {
                WifiBatteryStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiBatteryStats();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiBatteryStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WifiBatteryStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public GpsBatteryStats getGpsBatteryStats() throws RemoteException {
                GpsBatteryStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getGpsBatteryStats();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = GpsBatteryStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    GpsBatteryStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public HealthStatsParceler takeUidSnapshot(int uid) throws RemoteException {
                HealthStatsParceler _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().takeUidSnapshot(uid);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = HealthStatsParceler.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    HealthStatsParceler _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public HealthStatsParceler[] takeUidSnapshots(int[] uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(uid);
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().takeUidSnapshots(uid);
                    }
                    _reply.readException();
                    HealthStatsParceler[] _result = (HealthStatsParceler[]) _reply.createTypedArray(HealthStatsParceler.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void noteBluetoothControllerActivity(BluetoothActivityEnergyInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(86, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().noteBluetoothControllerActivity(info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void noteModemControllerActivity(ModemActivityInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(87, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().noteModemControllerActivity(info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void noteWifiControllerActivity(WifiActivityEnergyInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(88, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().noteWifiControllerActivity(info);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean setChargingStateUpdateDelayMillis(int delay) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(delay);
                    boolean z = false;
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setChargingStateUpdateDelayMillis(delay);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IBatteryStats impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IBatteryStats getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
