package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.NetworkStats;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.WorkSource;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telephony.CarrierRestrictionRules;
import android.telephony.CellInfo;
import android.telephony.ClientRequestStats;
import android.telephony.ICellInfoCallback;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.NeighboringCellInfo;
import android.telephony.NetworkScanRequest;
import android.telephony.PhoneNumberRange;
import android.telephony.RadioAccessFamily;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyHistogram;
import android.telephony.UiccCardInfo;
import android.telephony.UiccSlotInfo;
import android.telephony.VisualVoicemailSmsFilterSettings;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import com.android.ims.internal.IImsServiceFeatureCallback;
import java.util.List;
import java.util.Map;

public interface ITelephony extends IInterface {
    void cacheMmTelCapabilityProvisioning(int i, int i2, int i3, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void call(String str, String str2) throws RemoteException;

    boolean canChangeDtmfToneLength(int i, String str) throws RemoteException;

    void carrierActionReportDefaultNetworkStatus(int i, boolean z) throws RemoteException;

    void carrierActionResetAll(int i) throws RemoteException;

    void carrierActionSetMeteredApnsEnabled(int i, boolean z) throws RemoteException;

    void carrierActionSetRadioEnabled(int i, boolean z) throws RemoteException;

    int checkCarrierPrivilegesForPackage(int i, String str) throws RemoteException;

    int checkCarrierPrivilegesForPackageAnyPhone(String str) throws RemoteException;

    @UnsupportedAppUsage
    void dial(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean disableDataConnectivity() throws RemoteException;

    void disableIms(int i) throws RemoteException;

    @UnsupportedAppUsage
    void disableLocationUpdates() throws RemoteException;

    void disableLocationUpdatesForSubscriber(int i) throws RemoteException;

    void disableVisualVoicemailSmsFilter(String str, int i) throws RemoteException;

    boolean doesSwitchMultiSimConfigTriggerReboot(int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean enableDataConnectivity() throws RemoteException;

    void enableIms(int i) throws RemoteException;

    @UnsupportedAppUsage
    void enableLocationUpdates() throws RemoteException;

    void enableLocationUpdatesForSubscriber(int i) throws RemoteException;

    boolean enableModemForSlot(int i, boolean z) throws RemoteException;

    void enableVideoCalling(boolean z) throws RemoteException;

    void enableVisualVoicemailSmsFilter(String str, int i, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) throws RemoteException;

    void enqueueSmsPickResult(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void factoryReset(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getActivePhoneType() throws RemoteException;

    int getActivePhoneTypeForSlot(int i) throws RemoteException;

    VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) throws RemoteException;

    String getAidForAppType(int i, int i2) throws RemoteException;

    List<CellInfo> getAllCellInfo(String str) throws RemoteException;

    CarrierRestrictionRules getAllowedCarriers() throws RemoteException;

    int getCalculatedPreferredNetworkType(String str) throws RemoteException;

    @UnsupportedAppUsage
    int getCallState() throws RemoteException;

    int getCallStateForSlot(int i) throws RemoteException;

    int getCardIdForDefaultEuicc(int i, String str) throws RemoteException;

    int getCarrierIdFromMccMnc(int i, String str, boolean z) throws RemoteException;

    int getCarrierIdListVersion(int i) throws RemoteException;

    List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int i) throws RemoteException;

    int getCarrierPrivilegeStatus(int i) throws RemoteException;

    int getCarrierPrivilegeStatusForUid(int i, int i2) throws RemoteException;

    int getCdmaEriIconIndex(String str) throws RemoteException;

    int getCdmaEriIconIndexForSubscriber(int i, String str) throws RemoteException;

    int getCdmaEriIconMode(String str) throws RemoteException;

    int getCdmaEriIconModeForSubscriber(int i, String str) throws RemoteException;

    String getCdmaEriText(String str) throws RemoteException;

    String getCdmaEriTextForSubscriber(int i, String str) throws RemoteException;

    String getCdmaMdn(int i) throws RemoteException;

    String getCdmaMin(int i) throws RemoteException;

    String getCdmaPrlVersion(int i) throws RemoteException;

    int getCdmaRoamingMode(int i) throws RemoteException;

    Bundle getCellLocation(String str) throws RemoteException;

    CellNetworkScanResult getCellNetworkScanResults(int i, String str) throws RemoteException;

    List<String> getCertsFromCarrierPrivilegeAccessRules(int i) throws RemoteException;

    List<ClientRequestStats> getClientRequestStats(String str, int i) throws RemoteException;

    int getDataActivationState(int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    int getDataActivity() throws RemoteException;

    @UnsupportedAppUsage
    boolean getDataEnabled(int i) throws RemoteException;

    int getDataNetworkType(String str) throws RemoteException;

    int getDataNetworkTypeForSubscriber(int i, String str) throws RemoteException;

    @UnsupportedAppUsage
    int getDataState() throws RemoteException;

    String getDefaultSmsApp(int i) throws RemoteException;

    String getDeviceId(String str) throws RemoteException;

    String getDeviceSoftwareVersionForSlot(int i, String str) throws RemoteException;

    boolean getEmergencyCallbackMode(int i) throws RemoteException;

    Map getEmergencyNumberList(String str) throws RemoteException;

    List<String> getEmergencyNumberListTestMode() throws RemoteException;

    String getEsn(int i) throws RemoteException;

    String[] getForbiddenPlmns(int i, int i2, String str) throws RemoteException;

    String getImeiForSlot(int i, String str) throws RemoteException;

    IImsConfig getImsConfig(int i, int i2) throws RemoteException;

    int getImsProvisioningInt(int i, int i2) throws RemoteException;

    boolean getImsProvisioningStatusForCapability(int i, int i2, int i3) throws RemoteException;

    String getImsProvisioningString(int i, int i2) throws RemoteException;

    int getImsRegTechnologyForMmTel(int i) throws RemoteException;

    IImsRegistration getImsRegistration(int i, int i2) throws RemoteException;

    String getImsService(int i, boolean z) throws RemoteException;

    String getLine1AlphaTagForDisplay(int i, String str) throws RemoteException;

    String getLine1NumberForDisplay(int i, String str) throws RemoteException;

    int getLteOnCdmaMode(String str) throws RemoteException;

    int getLteOnCdmaModeForSubscriber(int i, String str) throws RemoteException;

    String getManufacturerCodeForSlot(int i) throws RemoteException;

    String getMeidForSlot(int i, String str) throws RemoteException;

    String[] getMergedSubscriberIds(int i, String str) throws RemoteException;

    IImsMmTelFeature getMmTelFeatureAndListen(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException;

    String getMmsUAProfUrl(int i) throws RemoteException;

    String getMmsUserAgent(int i) throws RemoteException;

    List<NeighboringCellInfo> getNeighboringCellInfo(String str) throws RemoteException;

    String getNetworkCountryIsoForPhone(int i) throws RemoteException;

    int getNetworkSelectionMode(int i) throws RemoteException;

    int getNetworkTypeForSubscriber(int i, String str) throws RemoteException;

    int getNumberOfModemsWithSimultaneousDataConnections(int i, String str) throws RemoteException;

    List<String> getPackagesWithCarrierPrivileges(int i) throws RemoteException;

    List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException;

    String[] getPcscfAddress(String str, String str2) throws RemoteException;

    PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) throws RemoteException;

    int getPreferredNetworkType(int i) throws RemoteException;

    int getRadioAccessFamily(int i, String str) throws RemoteException;

    int getRadioHalVersion() throws RemoteException;

    int getRadioPowerState(int i, String str) throws RemoteException;

    IImsRcsFeature getRcsFeatureAndListen(int i, IImsServiceFeatureCallback iImsServiceFeatureCallback) throws RemoteException;

    ServiceState getServiceStateForSubscriber(int i, String str) throws RemoteException;

    SignalStrength getSignalStrength(int i) throws RemoteException;

    String getSimLocaleForSubscriber(int i) throws RemoteException;

    int[] getSlotsMapping() throws RemoteException;

    String[] getSmsApps(int i) throws RemoteException;

    int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException;

    int getSubscriptionCarrierId(int i) throws RemoteException;

    String getSubscriptionCarrierName(int i) throws RemoteException;

    int getSubscriptionSpecificCarrierId(int i) throws RemoteException;

    String getSubscriptionSpecificCarrierName(int i) throws RemoteException;

    List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException;

    boolean getTetherApnRequiredForSubscriber(int i) throws RemoteException;

    String getTypeAllocationCodeForSlot(int i) throws RemoteException;

    List<UiccCardInfo> getUiccCardsInfo(String str) throws RemoteException;

    UiccSlotInfo[] getUiccSlotsInfo() throws RemoteException;

    String getVisualVoicemailPackageName(String str, int i) throws RemoteException;

    Bundle getVisualVoicemailSettings(String str, int i) throws RemoteException;

    VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String str, int i) throws RemoteException;

    int getVoWiFiModeSetting(int i) throws RemoteException;

    int getVoWiFiRoamingModeSetting(int i) throws RemoteException;

    int getVoiceActivationState(int i, String str) throws RemoteException;

    int getVoiceMessageCountForSubscriber(int i, String str) throws RemoteException;

    int getVoiceNetworkTypeForSubscriber(int i, String str) throws RemoteException;

    Uri getVoicemailRingtoneUri(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    NetworkStats getVtDataUsage(int i, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    boolean handlePinMmi(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean handlePinMmiForSubscriber(int i, String str) throws RemoteException;

    void handleUssdRequest(int i, String str, ResultReceiver resultReceiver) throws RemoteException;

    @UnsupportedAppUsage
    boolean hasIccCard() throws RemoteException;

    boolean hasIccCardUsingSlotIndex(int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean iccCloseLogicalChannel(int i, int i2) throws RemoteException;

    boolean iccCloseLogicalChannelBySlot(int i, int i2) throws RemoteException;

    byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException;

    IccOpenLogicalChannelResponse iccOpenLogicalChannel(int i, String str, String str2, int i2) throws RemoteException;

    IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int i, String str, String str2, int i2) throws RemoteException;

    String iccTransmitApduBasicChannel(int i, String str, int i2, int i3, int i4, int i5, int i6, String str2) throws RemoteException;

    String iccTransmitApduBasicChannelBySlot(int i, String str, int i2, int i3, int i4, int i5, int i6, String str2) throws RemoteException;

    @UnsupportedAppUsage
    String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) throws RemoteException;

    String iccTransmitApduLogicalChannelBySlot(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) throws RemoteException;

    int invokeOemRilRequestRaw(byte[] bArr, byte[] bArr2) throws RemoteException;

    boolean isAdvancedCallingSettingEnabled(int i) throws RemoteException;

    boolean isApnMetered(int i, int i2) throws RemoteException;

    boolean isAvailable(int i, int i2, int i3) throws RemoteException;

    boolean isCapable(int i, int i2, int i3) throws RemoteException;

    boolean isConcurrentVoiceAndDataAllowed(int i) throws RemoteException;

    boolean isDataAllowedInVoiceCall(int i) throws RemoteException;

    boolean isDataConnectivityPossible(int i) throws RemoteException;

    boolean isDataEnabled(int i) throws RemoteException;

    boolean isDataEnabledForApn(int i, int i2, String str) throws RemoteException;

    boolean isDataRoamingEnabled(int i) throws RemoteException;

    boolean isEmergencyNumber(String str, boolean z) throws RemoteException;

    boolean isHearingAidCompatibilitySupported() throws RemoteException;

    boolean isImsRegistered(int i) throws RemoteException;

    boolean isInEmergencySmsMode() throws RemoteException;

    boolean isManualNetworkSelectionAllowed(int i) throws RemoteException;

    boolean isMmTelCapabilityProvisionedInCache(int i, int i2, int i3) throws RemoteException;

    boolean isModemEnabledForSlot(int i, String str) throws RemoteException;

    int isMultiSimSupported(String str) throws RemoteException;

    boolean isRadioOn(String str) throws RemoteException;

    @UnsupportedAppUsage
    boolean isRadioOnForSubscriber(int i, String str) throws RemoteException;

    boolean isRttSupported(int i) throws RemoteException;

    boolean isTtyModeSupported() throws RemoteException;

    boolean isTtyOverVolteEnabled(int i) throws RemoteException;

    boolean isUserDataEnabled(int i) throws RemoteException;

    boolean isVideoCallingEnabled(String str) throws RemoteException;

    boolean isVideoTelephonyAvailable(int i) throws RemoteException;

    boolean isVoWiFiRoamingSettingEnabled(int i) throws RemoteException;

    boolean isVoWiFiSettingEnabled(int i) throws RemoteException;

    boolean isVoicemailVibrationEnabled(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    boolean isVtSettingEnabled(int i) throws RemoteException;

    boolean isWifiCallingAvailable(int i) throws RemoteException;

    boolean isWorldPhone(int i, String str) throws RemoteException;

    boolean needMobileRadioShutdown() throws RemoteException;

    boolean needsOtaServiceProvisioning() throws RemoteException;

    String nvReadItem(int i) throws RemoteException;

    boolean nvWriteCdmaPrl(byte[] bArr) throws RemoteException;

    boolean nvWriteItem(int i, String str) throws RemoteException;

    boolean rebootModem(int i) throws RemoteException;

    void refreshUiccProfile(int i) throws RemoteException;

    void registerImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void registerImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void registerMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void requestCellInfoUpdate(int i, ICellInfoCallback iCellInfoCallback, String str) throws RemoteException;

    void requestCellInfoUpdateWithWorkSource(int i, ICellInfoCallback iCellInfoCallback, String str, WorkSource workSource) throws RemoteException;

    void requestModemActivityInfo(ResultReceiver resultReceiver) throws RemoteException;

    int requestNetworkScan(int i, NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, String str) throws RemoteException;

    void requestNumberVerification(PhoneNumberRange phoneNumberRange, long j, INumberVerificationCallback iNumberVerificationCallback, String str) throws RemoteException;

    boolean resetModemConfig(int i) throws RemoteException;

    void sendDialerSpecialCode(String str, String str2) throws RemoteException;

    String sendEnvelopeWithStatus(int i, String str) throws RemoteException;

    void sendVisualVoicemailSmsForSubscriber(String str, int i, String str2, int i2, String str3, PendingIntent pendingIntent) throws RemoteException;

    void setAdvancedCallingSettingEnabled(int i, boolean z) throws RemoteException;

    int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException;

    void setCarrierTestOverride(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException;

    boolean setCdmaRoamingMode(int i, int i2) throws RemoteException;

    boolean setCdmaSubscriptionMode(int i, int i2) throws RemoteException;

    void setCellInfoListRate(int i) throws RemoteException;

    void setDataActivationState(int i, int i2) throws RemoteException;

    boolean setDataAllowedDuringVoiceCall(int i, boolean z) throws RemoteException;

    void setDataRoamingEnabled(int i, boolean z) throws RemoteException;

    void setDefaultSmsApp(int i, String str) throws RemoteException;

    int setImsProvisioningInt(int i, int i2, int i3) throws RemoteException;

    void setImsProvisioningStatusForCapability(int i, int i2, int i3, boolean z) throws RemoteException;

    int setImsProvisioningString(int i, int i2, String str) throws RemoteException;

    void setImsRegistrationState(boolean z) throws RemoteException;

    boolean setImsService(int i, boolean z, String str) throws RemoteException;

    boolean setLine1NumberForDisplayForSubscriber(int i, String str, String str2) throws RemoteException;

    void setMultiSimCarrierRestriction(boolean z) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    boolean setNetworkSelectionModeManual(int i, OperatorInfo operatorInfo, boolean z) throws RemoteException;

    boolean setOperatorBrandOverride(int i, String str) throws RemoteException;

    void setPolicyDataEnabled(boolean z, int i) throws RemoteException;

    boolean setPreferredNetworkType(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    boolean setRadio(boolean z) throws RemoteException;

    void setRadioCapability(RadioAccessFamily[] radioAccessFamilyArr) throws RemoteException;

    boolean setRadioForSubscriber(int i, boolean z) throws RemoteException;

    void setRadioIndicationUpdateMode(int i, int i2, int i3) throws RemoteException;

    boolean setRadioPower(boolean z) throws RemoteException;

    boolean setRoamingOverride(int i, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException;

    void setRttCapabilitySetting(int i, boolean z) throws RemoteException;

    void setSimPowerStateForSlot(int i, int i2) throws RemoteException;

    void setUserDataEnabled(int i, boolean z) throws RemoteException;

    void setVoWiFiModeSetting(int i, int i2) throws RemoteException;

    void setVoWiFiNonPersistent(int i, boolean z, int i2) throws RemoteException;

    void setVoWiFiRoamingModeSetting(int i, int i2) throws RemoteException;

    void setVoWiFiRoamingSettingEnabled(int i, boolean z) throws RemoteException;

    void setVoWiFiSettingEnabled(int i, boolean z) throws RemoteException;

    void setVoiceActivationState(int i, int i2) throws RemoteException;

    boolean setVoiceMailNumber(int i, String str, String str2) throws RemoteException;

    void setVoicemailRingtoneUri(String str, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException;

    void setVoicemailVibrationEnabled(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    void setVtSettingEnabled(int i, boolean z) throws RemoteException;

    void shutdownMobileRadios() throws RemoteException;

    void stopNetworkScan(int i, int i2) throws RemoteException;

    @UnsupportedAppUsage
    boolean supplyPin(String str) throws RemoteException;

    boolean supplyPinForSubscriber(int i, String str) throws RemoteException;

    int[] supplyPinReportResult(String str) throws RemoteException;

    int[] supplyPinReportResultForSubscriber(int i, String str) throws RemoteException;

    boolean supplyPuk(String str, String str2) throws RemoteException;

    boolean supplyPukForSubscriber(int i, String str, String str2) throws RemoteException;

    int[] supplyPukReportResult(String str, String str2) throws RemoteException;

    int[] supplyPukReportResultForSubscriber(int i, String str, String str2) throws RemoteException;

    void switchMultiSimConfig(int i) throws RemoteException;

    boolean switchSlots(int[] iArr) throws RemoteException;

    @UnsupportedAppUsage
    void toggleRadioOnOff() throws RemoteException;

    void toggleRadioOnOffForSubscriber(int i) throws RemoteException;

    void unregisterImsProvisioningChangedCallback(int i, IImsConfigCallback iImsConfigCallback) throws RemoteException;

    void unregisterImsRegistrationCallback(int i, IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void unregisterMmTelCapabilityCallback(int i, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void updateEmergencyNumberListTestMode(int i, EmergencyNumber emergencyNumber) throws RemoteException;

    @UnsupportedAppUsage
    void updateServiceLocation() throws RemoteException;

    void updateServiceLocationForSubscriber(int i) throws RemoteException;

    public static class Default implements ITelephony {
        public void dial(String number) throws RemoteException {
        }

        public void call(String callingPackage, String number) throws RemoteException {
        }

        public boolean isRadioOn(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isRadioOnForSubscriber(int subId, String callingPackage) throws RemoteException {
            return false;
        }

        public boolean supplyPin(String pin) throws RemoteException {
            return false;
        }

        public boolean supplyPinForSubscriber(int subId, String pin) throws RemoteException {
            return false;
        }

        public boolean supplyPuk(String puk, String pin) throws RemoteException {
            return false;
        }

        public boolean supplyPukForSubscriber(int subId, String puk, String pin) throws RemoteException {
            return false;
        }

        public int[] supplyPinReportResult(String pin) throws RemoteException {
            return null;
        }

        public int[] supplyPinReportResultForSubscriber(int subId, String pin) throws RemoteException {
            return null;
        }

        public int[] supplyPukReportResult(String puk, String pin) throws RemoteException {
            return null;
        }

        public int[] supplyPukReportResultForSubscriber(int subId, String puk, String pin) throws RemoteException {
            return null;
        }

        public boolean handlePinMmi(String dialString) throws RemoteException {
            return false;
        }

        public void handleUssdRequest(int subId, String ussdRequest, ResultReceiver wrappedCallback) throws RemoteException {
        }

        public boolean handlePinMmiForSubscriber(int subId, String dialString) throws RemoteException {
            return false;
        }

        public void toggleRadioOnOff() throws RemoteException {
        }

        public void toggleRadioOnOffForSubscriber(int subId) throws RemoteException {
        }

        public boolean setRadio(boolean turnOn) throws RemoteException {
            return false;
        }

        public boolean setRadioForSubscriber(int subId, boolean turnOn) throws RemoteException {
            return false;
        }

        public boolean setRadioPower(boolean turnOn) throws RemoteException {
            return false;
        }

        public void updateServiceLocation() throws RemoteException {
        }

        public void updateServiceLocationForSubscriber(int subId) throws RemoteException {
        }

        public void enableLocationUpdates() throws RemoteException {
        }

        public void enableLocationUpdatesForSubscriber(int subId) throws RemoteException {
        }

        public void disableLocationUpdates() throws RemoteException {
        }

        public void disableLocationUpdatesForSubscriber(int subId) throws RemoteException {
        }

        public boolean enableDataConnectivity() throws RemoteException {
            return false;
        }

        public boolean disableDataConnectivity() throws RemoteException {
            return false;
        }

        public boolean isDataConnectivityPossible(int subId) throws RemoteException {
            return false;
        }

        public Bundle getCellLocation(String callingPkg) throws RemoteException {
            return null;
        }

        public String getNetworkCountryIsoForPhone(int phoneId) throws RemoteException {
            return null;
        }

        public List<NeighboringCellInfo> getNeighboringCellInfo(String callingPkg) throws RemoteException {
            return null;
        }

        public int getCallState() throws RemoteException {
            return 0;
        }

        public int getCallStateForSlot(int slotIndex) throws RemoteException {
            return 0;
        }

        public int getDataActivity() throws RemoteException {
            return 0;
        }

        public int getDataState() throws RemoteException {
            return 0;
        }

        public int getActivePhoneType() throws RemoteException {
            return 0;
        }

        public int getActivePhoneTypeForSlot(int slotIndex) throws RemoteException {
            return 0;
        }

        public int getCdmaEriIconIndex(String callingPackage) throws RemoteException {
            return 0;
        }

        public int getCdmaEriIconIndexForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int getCdmaEriIconMode(String callingPackage) throws RemoteException {
            return 0;
        }

        public int getCdmaEriIconModeForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public String getCdmaEriText(String callingPackage) throws RemoteException {
            return null;
        }

        public String getCdmaEriTextForSubscriber(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public boolean needsOtaServiceProvisioning() throws RemoteException {
            return false;
        }

        public boolean setVoiceMailNumber(int subId, String alphaTag, String number) throws RemoteException {
            return false;
        }

        public void setVoiceActivationState(int subId, int activationState) throws RemoteException {
        }

        public void setDataActivationState(int subId, int activationState) throws RemoteException {
        }

        public int getVoiceActivationState(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int getDataActivationState(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int getVoiceMessageCountForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public boolean isConcurrentVoiceAndDataAllowed(int subId) throws RemoteException {
            return false;
        }

        public Bundle getVisualVoicemailSettings(String callingPackage, int subId) throws RemoteException {
            return null;
        }

        public String getVisualVoicemailPackageName(String callingPackage, int subId) throws RemoteException {
            return null;
        }

        public void enableVisualVoicemailSmsFilter(String callingPackage, int subId, VisualVoicemailSmsFilterSettings settings) throws RemoteException {
        }

        public void disableVisualVoicemailSmsFilter(String callingPackage, int subId) throws RemoteException {
        }

        public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String callingPackage, int subId) throws RemoteException {
            return null;
        }

        public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int subId) throws RemoteException {
            return null;
        }

        public void sendVisualVoicemailSmsForSubscriber(String callingPackage, int subId, String number, int port, String text, PendingIntent sentIntent) throws RemoteException {
        }

        public void sendDialerSpecialCode(String callingPackageName, String inputCode) throws RemoteException {
        }

        public int getNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int getDataNetworkType(String callingPackage) throws RemoteException {
            return 0;
        }

        public int getDataNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int getVoiceNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public boolean hasIccCard() throws RemoteException {
            return false;
        }

        public boolean hasIccCardUsingSlotIndex(int slotIndex) throws RemoteException {
            return false;
        }

        public int getLteOnCdmaMode(String callingPackage) throws RemoteException {
            return 0;
        }

        public int getLteOnCdmaModeForSubscriber(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public List<CellInfo> getAllCellInfo(String callingPkg) throws RemoteException {
            return null;
        }

        public void requestCellInfoUpdate(int subId, ICellInfoCallback cb, String callingPkg) throws RemoteException {
        }

        public void requestCellInfoUpdateWithWorkSource(int subId, ICellInfoCallback cb, String callingPkg, WorkSource ws) throws RemoteException {
        }

        public void setCellInfoListRate(int rateInMillis) throws RemoteException {
        }

        public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int slotIndex, String callingPackage, String AID, int p2) throws RemoteException {
            return null;
        }

        public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int subId, String callingPackage, String AID, int p2) throws RemoteException {
            return null;
        }

        public boolean iccCloseLogicalChannelBySlot(int slotIndex, int channel) throws RemoteException {
            return false;
        }

        public boolean iccCloseLogicalChannel(int subId, int channel) throws RemoteException {
            return false;
        }

        public String iccTransmitApduLogicalChannelBySlot(int slotIndex, int channel, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
            return null;
        }

        public String iccTransmitApduLogicalChannel(int subId, int channel, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
            return null;
        }

        public String iccTransmitApduBasicChannelBySlot(int slotIndex, String callingPackage, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
            return null;
        }

        public String iccTransmitApduBasicChannel(int subId, String callingPackage, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
            return null;
        }

        public byte[] iccExchangeSimIO(int subId, int fileID, int command, int p1, int p2, int p3, String filePath) throws RemoteException {
            return null;
        }

        public String sendEnvelopeWithStatus(int subId, String content) throws RemoteException {
            return null;
        }

        public String nvReadItem(int itemID) throws RemoteException {
            return null;
        }

        public boolean nvWriteItem(int itemID, String itemValue) throws RemoteException {
            return false;
        }

        public boolean nvWriteCdmaPrl(byte[] preferredRoamingList) throws RemoteException {
            return false;
        }

        public boolean resetModemConfig(int slotIndex) throws RemoteException {
            return false;
        }

        public boolean rebootModem(int slotIndex) throws RemoteException {
            return false;
        }

        public int getCalculatedPreferredNetworkType(String callingPackage) throws RemoteException {
            return 0;
        }

        public int getPreferredNetworkType(int subId) throws RemoteException {
            return 0;
        }

        public boolean getTetherApnRequiredForSubscriber(int subId) throws RemoteException {
            return false;
        }

        public void enableIms(int slotId) throws RemoteException {
        }

        public void disableIms(int slotId) throws RemoteException {
        }

        public IImsMmTelFeature getMmTelFeatureAndListen(int slotId, IImsServiceFeatureCallback callback) throws RemoteException {
            return null;
        }

        public IImsRcsFeature getRcsFeatureAndListen(int slotId, IImsServiceFeatureCallback callback) throws RemoteException {
            return null;
        }

        public IImsRegistration getImsRegistration(int slotId, int feature) throws RemoteException {
            return null;
        }

        public IImsConfig getImsConfig(int slotId, int feature) throws RemoteException {
            return null;
        }

        public boolean setImsService(int slotId, boolean isCarrierImsService, String packageName) throws RemoteException {
            return false;
        }

        public String getImsService(int slotId, boolean isCarrierImsService) throws RemoteException {
            return null;
        }

        public void setNetworkSelectionModeAutomatic(int subId) throws RemoteException {
        }

        public CellNetworkScanResult getCellNetworkScanResults(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public int requestNetworkScan(int subId, NetworkScanRequest request, Messenger messenger, IBinder binder, String callingPackage) throws RemoteException {
            return 0;
        }

        public void stopNetworkScan(int subId, int scanId) throws RemoteException {
        }

        public boolean setNetworkSelectionModeManual(int subId, OperatorInfo operatorInfo, boolean persisSelection) throws RemoteException {
            return false;
        }

        public boolean setPreferredNetworkType(int subId, int networkType) throws RemoteException {
            return false;
        }

        public void setUserDataEnabled(int subId, boolean enable) throws RemoteException {
        }

        public boolean getDataEnabled(int subId) throws RemoteException {
            return false;
        }

        public boolean isUserDataEnabled(int subId) throws RemoteException {
            return false;
        }

        public boolean isDataEnabled(int subId) throws RemoteException {
            return false;
        }

        public boolean isManualNetworkSelectionAllowed(int subId) throws RemoteException {
            return false;
        }

        public String[] getPcscfAddress(String apnType, String callingPackage) throws RemoteException {
            return null;
        }

        public void setImsRegistrationState(boolean registered) throws RemoteException {
        }

        public String getCdmaMdn(int subId) throws RemoteException {
            return null;
        }

        public String getCdmaMin(int subId) throws RemoteException {
            return null;
        }

        public void requestNumberVerification(PhoneNumberRange range, long timeoutMillis, INumberVerificationCallback callback, String callingPackage) throws RemoteException {
        }

        public int getCarrierPrivilegeStatus(int subId) throws RemoteException {
            return 0;
        }

        public int getCarrierPrivilegeStatusForUid(int subId, int uid) throws RemoteException {
            return 0;
        }

        public int checkCarrierPrivilegesForPackage(int subId, String pkgName) throws RemoteException {
            return 0;
        }

        public int checkCarrierPrivilegesForPackageAnyPhone(String pkgName) throws RemoteException {
            return 0;
        }

        public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int phoneId) throws RemoteException {
            return null;
        }

        public boolean setLine1NumberForDisplayForSubscriber(int subId, String alphaTag, String number) throws RemoteException {
            return false;
        }

        public String getLine1NumberForDisplay(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public String getLine1AlphaTagForDisplay(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public String[] getMergedSubscriberIds(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public boolean setOperatorBrandOverride(int subId, String brand) throws RemoteException {
            return false;
        }

        public boolean setRoamingOverride(int subId, List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException {
            return false;
        }

        public int invokeOemRilRequestRaw(byte[] oemReq, byte[] oemResp) throws RemoteException {
            return 0;
        }

        public boolean needMobileRadioShutdown() throws RemoteException {
            return false;
        }

        public void shutdownMobileRadios() throws RemoteException {
        }

        public void setRadioCapability(RadioAccessFamily[] rafs) throws RemoteException {
        }

        public int getRadioAccessFamily(int phoneId, String callingPackage) throws RemoteException {
            return 0;
        }

        public void enableVideoCalling(boolean enable) throws RemoteException {
        }

        public boolean isVideoCallingEnabled(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean canChangeDtmfToneLength(int subId, String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isWorldPhone(int subId, String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isTtyModeSupported() throws RemoteException {
            return false;
        }

        public boolean isRttSupported(int subscriptionId) throws RemoteException {
            return false;
        }

        public boolean isHearingAidCompatibilitySupported() throws RemoteException {
            return false;
        }

        public boolean isImsRegistered(int subId) throws RemoteException {
            return false;
        }

        public boolean isWifiCallingAvailable(int subId) throws RemoteException {
            return false;
        }

        public boolean isVideoTelephonyAvailable(int subId) throws RemoteException {
            return false;
        }

        public int getImsRegTechnologyForMmTel(int subId) throws RemoteException {
            return 0;
        }

        public String getDeviceId(String callingPackage) throws RemoteException {
            return null;
        }

        public String getImeiForSlot(int slotIndex, String callingPackage) throws RemoteException {
            return null;
        }

        public String getTypeAllocationCodeForSlot(int slotIndex) throws RemoteException {
            return null;
        }

        public String getMeidForSlot(int slotIndex, String callingPackage) throws RemoteException {
            return null;
        }

        public String getManufacturerCodeForSlot(int slotIndex) throws RemoteException {
            return null;
        }

        public String getDeviceSoftwareVersionForSlot(int slotIndex, String callingPackage) throws RemoteException {
            return null;
        }

        public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
            return 0;
        }

        public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int subscriptionId) throws RemoteException {
            return null;
        }

        public void factoryReset(int subId) throws RemoteException {
        }

        public String getSimLocaleForSubscriber(int subId) throws RemoteException {
            return null;
        }

        public void requestModemActivityInfo(ResultReceiver result) throws RemoteException {
        }

        public ServiceState getServiceStateForSubscriber(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public Uri getVoicemailRingtoneUri(PhoneAccountHandle accountHandle) throws RemoteException {
            return null;
        }

        public void setVoicemailRingtoneUri(String callingPackage, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException {
        }

        public boolean isVoicemailVibrationEnabled(PhoneAccountHandle accountHandle) throws RemoteException {
            return false;
        }

        public void setVoicemailVibrationEnabled(String callingPackage, PhoneAccountHandle phoneAccountHandle, boolean enabled) throws RemoteException {
        }

        public List<String> getPackagesWithCarrierPrivileges(int phoneId) throws RemoteException {
            return null;
        }

        public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException {
            return null;
        }

        public String getAidForAppType(int subId, int appType) throws RemoteException {
            return null;
        }

        public String getEsn(int subId) throws RemoteException {
            return null;
        }

        public String getCdmaPrlVersion(int subId) throws RemoteException {
            return null;
        }

        public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException {
            return null;
        }

        public int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException {
            return 0;
        }

        public CarrierRestrictionRules getAllowedCarriers() throws RemoteException {
            return null;
        }

        public int getSubscriptionCarrierId(int subId) throws RemoteException {
            return 0;
        }

        public String getSubscriptionCarrierName(int subId) throws RemoteException {
            return null;
        }

        public int getSubscriptionSpecificCarrierId(int subId) throws RemoteException {
            return 0;
        }

        public String getSubscriptionSpecificCarrierName(int subId) throws RemoteException {
            return null;
        }

        public int getCarrierIdFromMccMnc(int slotIndex, String mccmnc, boolean isSubscriptionMccMnc) throws RemoteException {
            return 0;
        }

        public void carrierActionSetMeteredApnsEnabled(int subId, boolean visible) throws RemoteException {
        }

        public void carrierActionSetRadioEnabled(int subId, boolean enabled) throws RemoteException {
        }

        public void carrierActionReportDefaultNetworkStatus(int subId, boolean report) throws RemoteException {
        }

        public void carrierActionResetAll(int subId) throws RemoteException {
        }

        public NetworkStats getVtDataUsage(int subId, boolean perUidStats) throws RemoteException {
            return null;
        }

        public void setPolicyDataEnabled(boolean enabled, int subId) throws RemoteException {
        }

        public List<ClientRequestStats> getClientRequestStats(String callingPackage, int subid) throws RemoteException {
            return null;
        }

        public void setSimPowerStateForSlot(int slotIndex, int state) throws RemoteException {
        }

        public String[] getForbiddenPlmns(int subId, int appType, String callingPackage) throws RemoteException {
            return null;
        }

        public boolean getEmergencyCallbackMode(int subId) throws RemoteException {
            return false;
        }

        public SignalStrength getSignalStrength(int subId) throws RemoteException {
            return null;
        }

        public int getCardIdForDefaultEuicc(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public List<UiccCardInfo> getUiccCardsInfo(String callingPackage) throws RemoteException {
            return null;
        }

        public UiccSlotInfo[] getUiccSlotsInfo() throws RemoteException {
            return null;
        }

        public boolean switchSlots(int[] physicalSlots) throws RemoteException {
            return false;
        }

        public void setRadioIndicationUpdateMode(int subId, int filters, int mode) throws RemoteException {
        }

        public boolean isDataRoamingEnabled(int subId) throws RemoteException {
            return false;
        }

        public void setDataRoamingEnabled(int subId, boolean isEnabled) throws RemoteException {
        }

        public int getCdmaRoamingMode(int subId) throws RemoteException {
            return 0;
        }

        public boolean setCdmaRoamingMode(int subId, int mode) throws RemoteException {
            return false;
        }

        public boolean setCdmaSubscriptionMode(int subId, int mode) throws RemoteException {
            return false;
        }

        public void setCarrierTestOverride(int subId, String mccmnc, String imsi, String iccid, String gid1, String gid2, String plmn, String spn, String carrierPrivilegeRules, String apn) throws RemoteException {
        }

        public int getCarrierIdListVersion(int subId) throws RemoteException {
            return 0;
        }

        public void refreshUiccProfile(int subId) throws RemoteException {
        }

        public int getNumberOfModemsWithSimultaneousDataConnections(int subId, String callingPackage) throws RemoteException {
            return 0;
        }

        public int getNetworkSelectionMode(int subId) throws RemoteException {
            return 0;
        }

        public boolean isInEmergencySmsMode() throws RemoteException {
            return false;
        }

        public String[] getSmsApps(int userId) throws RemoteException {
            return null;
        }

        public String getDefaultSmsApp(int userId) throws RemoteException {
            return null;
        }

        public void setDefaultSmsApp(int userId, String packageName) throws RemoteException {
        }

        public int getRadioPowerState(int slotIndex, String callingPackage) throws RemoteException {
            return 0;
        }

        public void registerImsRegistrationCallback(int subId, IImsRegistrationCallback c) throws RemoteException {
        }

        public void unregisterImsRegistrationCallback(int subId, IImsRegistrationCallback c) throws RemoteException {
        }

        public void registerMmTelCapabilityCallback(int subId, IImsCapabilityCallback c) throws RemoteException {
        }

        public void unregisterMmTelCapabilityCallback(int subId, IImsCapabilityCallback c) throws RemoteException {
        }

        public boolean isCapable(int subId, int capability, int regTech) throws RemoteException {
            return false;
        }

        public boolean isAvailable(int subId, int capability, int regTech) throws RemoteException {
            return false;
        }

        public boolean isAdvancedCallingSettingEnabled(int subId) throws RemoteException {
            return false;
        }

        public void setAdvancedCallingSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
        }

        public boolean isVtSettingEnabled(int subId) throws RemoteException {
            return false;
        }

        public void setVtSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
        }

        public boolean isVoWiFiSettingEnabled(int subId) throws RemoteException {
            return false;
        }

        public void setVoWiFiSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
        }

        public boolean isVoWiFiRoamingSettingEnabled(int subId) throws RemoteException {
            return false;
        }

        public void setVoWiFiRoamingSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
        }

        public void setVoWiFiNonPersistent(int subId, boolean isCapable, int mode) throws RemoteException {
        }

        public int getVoWiFiModeSetting(int subId) throws RemoteException {
            return 0;
        }

        public void setVoWiFiModeSetting(int subId, int mode) throws RemoteException {
        }

        public int getVoWiFiRoamingModeSetting(int subId) throws RemoteException {
            return 0;
        }

        public void setVoWiFiRoamingModeSetting(int subId, int mode) throws RemoteException {
        }

        public void setRttCapabilitySetting(int subId, boolean isEnabled) throws RemoteException {
        }

        public boolean isTtyOverVolteEnabled(int subId) throws RemoteException {
            return false;
        }

        public Map getEmergencyNumberList(String callingPackage) throws RemoteException {
            return null;
        }

        public boolean isEmergencyNumber(String number, boolean exactMatch) throws RemoteException {
            return false;
        }

        public List<String> getCertsFromCarrierPrivilegeAccessRules(int subId) throws RemoteException {
            return null;
        }

        public void registerImsProvisioningChangedCallback(int subId, IImsConfigCallback callback) throws RemoteException {
        }

        public void unregisterImsProvisioningChangedCallback(int subId, IImsConfigCallback callback) throws RemoteException {
        }

        public void setImsProvisioningStatusForCapability(int subId, int capability, int tech, boolean isProvisioned) throws RemoteException {
        }

        public boolean getImsProvisioningStatusForCapability(int subId, int capability, int tech) throws RemoteException {
            return false;
        }

        public boolean isMmTelCapabilityProvisionedInCache(int subId, int capability, int tech) throws RemoteException {
            return false;
        }

        public void cacheMmTelCapabilityProvisioning(int subId, int capability, int tech, boolean isProvisioned) throws RemoteException {
        }

        public int getImsProvisioningInt(int subId, int key) throws RemoteException {
            return 0;
        }

        public String getImsProvisioningString(int subId, int key) throws RemoteException {
            return null;
        }

        public int setImsProvisioningInt(int subId, int key, int value) throws RemoteException {
            return 0;
        }

        public int setImsProvisioningString(int subId, int key, String value) throws RemoteException {
            return 0;
        }

        public void updateEmergencyNumberListTestMode(int action, EmergencyNumber num) throws RemoteException {
        }

        public List<String> getEmergencyNumberListTestMode() throws RemoteException {
            return null;
        }

        public boolean enableModemForSlot(int slotIndex, boolean enable) throws RemoteException {
            return false;
        }

        public void setMultiSimCarrierRestriction(boolean isMultiSimCarrierRestricted) throws RemoteException {
        }

        public int isMultiSimSupported(String callingPackage) throws RemoteException {
            return 0;
        }

        public void switchMultiSimConfig(int numOfSims) throws RemoteException {
        }

        public boolean doesSwitchMultiSimConfigTriggerReboot(int subId, String callingPackage) throws RemoteException {
            return false;
        }

        public int[] getSlotsMapping() throws RemoteException {
            return null;
        }

        public int getRadioHalVersion() throws RemoteException {
            return 0;
        }

        public boolean isModemEnabledForSlot(int slotIndex, String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isDataEnabledForApn(int apnType, int subId, String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isApnMetered(int apnType, int subId) throws RemoteException {
            return false;
        }

        public void enqueueSmsPickResult(String callingPackage, IIntegerConsumer subIdResult) throws RemoteException {
        }

        public String getMmsUserAgent(int subId) throws RemoteException {
            return null;
        }

        public String getMmsUAProfUrl(int subId) throws RemoteException {
            return null;
        }

        public boolean setDataAllowedDuringVoiceCall(int subId, boolean allow) throws RemoteException {
            return false;
        }

        public boolean isDataAllowedInVoiceCall(int subId) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITelephony {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ITelephony";
        static final int TRANSACTION_cacheMmTelCapabilityProvisioning = 231;
        static final int TRANSACTION_call = 2;
        static final int TRANSACTION_canChangeDtmfToneLength = 133;
        static final int TRANSACTION_carrierActionReportDefaultNetworkStatus = 173;
        static final int TRANSACTION_carrierActionResetAll = 174;
        static final int TRANSACTION_carrierActionSetMeteredApnsEnabled = 171;
        static final int TRANSACTION_carrierActionSetRadioEnabled = 172;
        static final int TRANSACTION_checkCarrierPrivilegesForPackage = 117;
        static final int TRANSACTION_checkCarrierPrivilegesForPackageAnyPhone = 118;
        static final int TRANSACTION_dial = 1;
        static final int TRANSACTION_disableDataConnectivity = 28;
        static final int TRANSACTION_disableIms = 92;
        static final int TRANSACTION_disableLocationUpdates = 25;
        static final int TRANSACTION_disableLocationUpdatesForSubscriber = 26;
        static final int TRANSACTION_disableVisualVoicemailSmsFilter = 56;
        static final int TRANSACTION_doesSwitchMultiSimConfigTriggerReboot = 242;
        static final int TRANSACTION_enableDataConnectivity = 27;
        static final int TRANSACTION_enableIms = 91;
        static final int TRANSACTION_enableLocationUpdates = 23;
        static final int TRANSACTION_enableLocationUpdatesForSubscriber = 24;
        static final int TRANSACTION_enableModemForSlot = 238;
        static final int TRANSACTION_enableVideoCalling = 131;
        static final int TRANSACTION_enableVisualVoicemailSmsFilter = 55;
        static final int TRANSACTION_enqueueSmsPickResult = 248;
        static final int TRANSACTION_factoryReset = 150;
        static final int TRANSACTION_getActivePhoneType = 37;
        static final int TRANSACTION_getActivePhoneTypeForSlot = 38;
        static final int TRANSACTION_getActiveVisualVoicemailSmsFilterSettings = 58;
        static final int TRANSACTION_getAidForAppType = 160;
        static final int TRANSACTION_getAllCellInfo = 69;
        static final int TRANSACTION_getAllowedCarriers = 165;
        static final int TRANSACTION_getCalculatedPreferredNetworkType = 88;
        static final int TRANSACTION_getCallState = 33;
        static final int TRANSACTION_getCallStateForSlot = 34;
        static final int TRANSACTION_getCardIdForDefaultEuicc = 182;
        static final int TRANSACTION_getCarrierIdFromMccMnc = 170;
        static final int TRANSACTION_getCarrierIdListVersion = 193;
        static final int TRANSACTION_getCarrierPackageNamesForIntentAndPhone = 119;
        static final int TRANSACTION_getCarrierPrivilegeStatus = 115;
        static final int TRANSACTION_getCarrierPrivilegeStatusForUid = 116;
        static final int TRANSACTION_getCdmaEriIconIndex = 39;
        static final int TRANSACTION_getCdmaEriIconIndexForSubscriber = 40;
        static final int TRANSACTION_getCdmaEriIconMode = 41;
        static final int TRANSACTION_getCdmaEriIconModeForSubscriber = 42;
        static final int TRANSACTION_getCdmaEriText = 43;
        static final int TRANSACTION_getCdmaEriTextForSubscriber = 44;
        static final int TRANSACTION_getCdmaMdn = 112;
        static final int TRANSACTION_getCdmaMin = 113;
        static final int TRANSACTION_getCdmaPrlVersion = 162;
        static final int TRANSACTION_getCdmaRoamingMode = 189;
        static final int TRANSACTION_getCellLocation = 30;
        static final int TRANSACTION_getCellNetworkScanResults = 100;
        static final int TRANSACTION_getCertsFromCarrierPrivilegeAccessRules = 225;
        static final int TRANSACTION_getClientRequestStats = 177;
        static final int TRANSACTION_getDataActivationState = 50;
        static final int TRANSACTION_getDataActivity = 35;
        static final int TRANSACTION_getDataEnabled = 106;
        static final int TRANSACTION_getDataNetworkType = 62;
        static final int TRANSACTION_getDataNetworkTypeForSubscriber = 63;
        static final int TRANSACTION_getDataState = 36;
        static final int TRANSACTION_getDefaultSmsApp = 199;
        static final int TRANSACTION_getDeviceId = 142;
        static final int TRANSACTION_getDeviceSoftwareVersionForSlot = 147;
        static final int TRANSACTION_getEmergencyCallbackMode = 180;
        static final int TRANSACTION_getEmergencyNumberList = 223;
        static final int TRANSACTION_getEmergencyNumberListTestMode = 237;
        static final int TRANSACTION_getEsn = 161;
        static final int TRANSACTION_getForbiddenPlmns = 179;
        static final int TRANSACTION_getImeiForSlot = 143;
        static final int TRANSACTION_getImsConfig = 96;
        static final int TRANSACTION_getImsProvisioningInt = 232;
        static final int TRANSACTION_getImsProvisioningStatusForCapability = 229;
        static final int TRANSACTION_getImsProvisioningString = 233;
        static final int TRANSACTION_getImsRegTechnologyForMmTel = 141;
        static final int TRANSACTION_getImsRegistration = 95;
        static final int TRANSACTION_getImsService = 98;
        static final int TRANSACTION_getLine1AlphaTagForDisplay = 122;
        static final int TRANSACTION_getLine1NumberForDisplay = 121;
        static final int TRANSACTION_getLteOnCdmaMode = 67;
        static final int TRANSACTION_getLteOnCdmaModeForSubscriber = 68;
        static final int TRANSACTION_getManufacturerCodeForSlot = 146;
        static final int TRANSACTION_getMeidForSlot = 145;
        static final int TRANSACTION_getMergedSubscriberIds = 123;
        static final int TRANSACTION_getMmTelFeatureAndListen = 93;
        static final int TRANSACTION_getMmsUAProfUrl = 250;
        static final int TRANSACTION_getMmsUserAgent = 249;
        static final int TRANSACTION_getNeighboringCellInfo = 32;
        static final int TRANSACTION_getNetworkCountryIsoForPhone = 31;
        static final int TRANSACTION_getNetworkSelectionMode = 196;
        static final int TRANSACTION_getNetworkTypeForSubscriber = 61;
        static final int TRANSACTION_getNumberOfModemsWithSimultaneousDataConnections = 195;
        static final int TRANSACTION_getPackagesWithCarrierPrivileges = 158;
        static final int TRANSACTION_getPackagesWithCarrierPrivilegesForAllPhones = 159;
        static final int TRANSACTION_getPcscfAddress = 110;
        static final int TRANSACTION_getPhoneAccountHandleForSubscriptionId = 149;
        static final int TRANSACTION_getPreferredNetworkType = 89;
        static final int TRANSACTION_getRadioAccessFamily = 130;
        static final int TRANSACTION_getRadioHalVersion = 244;
        static final int TRANSACTION_getRadioPowerState = 201;
        static final int TRANSACTION_getRcsFeatureAndListen = 94;
        static final int TRANSACTION_getServiceStateForSubscriber = 153;
        static final int TRANSACTION_getSignalStrength = 181;
        static final int TRANSACTION_getSimLocaleForSubscriber = 151;
        static final int TRANSACTION_getSlotsMapping = 243;
        static final int TRANSACTION_getSmsApps = 198;
        static final int TRANSACTION_getSubIdForPhoneAccount = 148;
        static final int TRANSACTION_getSubscriptionCarrierId = 166;
        static final int TRANSACTION_getSubscriptionCarrierName = 167;
        static final int TRANSACTION_getSubscriptionSpecificCarrierId = 168;
        static final int TRANSACTION_getSubscriptionSpecificCarrierName = 169;
        static final int TRANSACTION_getTelephonyHistograms = 163;
        static final int TRANSACTION_getTetherApnRequiredForSubscriber = 90;
        static final int TRANSACTION_getTypeAllocationCodeForSlot = 144;
        static final int TRANSACTION_getUiccCardsInfo = 183;
        static final int TRANSACTION_getUiccSlotsInfo = 184;
        static final int TRANSACTION_getVisualVoicemailPackageName = 54;
        static final int TRANSACTION_getVisualVoicemailSettings = 53;
        static final int TRANSACTION_getVisualVoicemailSmsFilterSettings = 57;
        static final int TRANSACTION_getVoWiFiModeSetting = 217;
        static final int TRANSACTION_getVoWiFiRoamingModeSetting = 219;
        static final int TRANSACTION_getVoiceActivationState = 49;
        static final int TRANSACTION_getVoiceMessageCountForSubscriber = 51;
        static final int TRANSACTION_getVoiceNetworkTypeForSubscriber = 64;
        static final int TRANSACTION_getVoicemailRingtoneUri = 154;
        static final int TRANSACTION_getVtDataUsage = 175;
        static final int TRANSACTION_handlePinMmi = 13;
        static final int TRANSACTION_handlePinMmiForSubscriber = 15;
        static final int TRANSACTION_handleUssdRequest = 14;
        static final int TRANSACTION_hasIccCard = 65;
        static final int TRANSACTION_hasIccCardUsingSlotIndex = 66;
        static final int TRANSACTION_iccCloseLogicalChannel = 76;
        static final int TRANSACTION_iccCloseLogicalChannelBySlot = 75;
        static final int TRANSACTION_iccExchangeSimIO = 81;
        static final int TRANSACTION_iccOpenLogicalChannel = 74;
        static final int TRANSACTION_iccOpenLogicalChannelBySlot = 73;
        static final int TRANSACTION_iccTransmitApduBasicChannel = 80;
        static final int TRANSACTION_iccTransmitApduBasicChannelBySlot = 79;
        static final int TRANSACTION_iccTransmitApduLogicalChannel = 78;
        static final int TRANSACTION_iccTransmitApduLogicalChannelBySlot = 77;
        static final int TRANSACTION_invokeOemRilRequestRaw = 126;
        static final int TRANSACTION_isAdvancedCallingSettingEnabled = 208;
        static final int TRANSACTION_isApnMetered = 247;
        static final int TRANSACTION_isAvailable = 207;
        static final int TRANSACTION_isCapable = 206;
        static final int TRANSACTION_isConcurrentVoiceAndDataAllowed = 52;
        static final int TRANSACTION_isDataAllowedInVoiceCall = 252;
        static final int TRANSACTION_isDataConnectivityPossible = 29;
        static final int TRANSACTION_isDataEnabled = 108;
        static final int TRANSACTION_isDataEnabledForApn = 246;
        static final int TRANSACTION_isDataRoamingEnabled = 187;
        static final int TRANSACTION_isEmergencyNumber = 224;
        static final int TRANSACTION_isHearingAidCompatibilitySupported = 137;
        static final int TRANSACTION_isImsRegistered = 138;
        static final int TRANSACTION_isInEmergencySmsMode = 197;
        static final int TRANSACTION_isManualNetworkSelectionAllowed = 109;
        static final int TRANSACTION_isMmTelCapabilityProvisionedInCache = 230;
        static final int TRANSACTION_isModemEnabledForSlot = 245;
        static final int TRANSACTION_isMultiSimSupported = 240;
        static final int TRANSACTION_isRadioOn = 3;
        static final int TRANSACTION_isRadioOnForSubscriber = 4;
        static final int TRANSACTION_isRttSupported = 136;
        static final int TRANSACTION_isTtyModeSupported = 135;
        static final int TRANSACTION_isTtyOverVolteEnabled = 222;
        static final int TRANSACTION_isUserDataEnabled = 107;
        static final int TRANSACTION_isVideoCallingEnabled = 132;
        static final int TRANSACTION_isVideoTelephonyAvailable = 140;
        static final int TRANSACTION_isVoWiFiRoamingSettingEnabled = 214;
        static final int TRANSACTION_isVoWiFiSettingEnabled = 212;
        static final int TRANSACTION_isVoicemailVibrationEnabled = 156;
        static final int TRANSACTION_isVtSettingEnabled = 210;
        static final int TRANSACTION_isWifiCallingAvailable = 139;
        static final int TRANSACTION_isWorldPhone = 134;
        static final int TRANSACTION_needMobileRadioShutdown = 127;
        static final int TRANSACTION_needsOtaServiceProvisioning = 45;
        static final int TRANSACTION_nvReadItem = 83;
        static final int TRANSACTION_nvWriteCdmaPrl = 85;
        static final int TRANSACTION_nvWriteItem = 84;
        static final int TRANSACTION_rebootModem = 87;
        static final int TRANSACTION_refreshUiccProfile = 194;
        static final int TRANSACTION_registerImsProvisioningChangedCallback = 226;
        static final int TRANSACTION_registerImsRegistrationCallback = 202;
        static final int TRANSACTION_registerMmTelCapabilityCallback = 204;
        static final int TRANSACTION_requestCellInfoUpdate = 70;
        static final int TRANSACTION_requestCellInfoUpdateWithWorkSource = 71;
        static final int TRANSACTION_requestModemActivityInfo = 152;
        static final int TRANSACTION_requestNetworkScan = 101;
        static final int TRANSACTION_requestNumberVerification = 114;
        static final int TRANSACTION_resetModemConfig = 86;
        static final int TRANSACTION_sendDialerSpecialCode = 60;
        static final int TRANSACTION_sendEnvelopeWithStatus = 82;
        static final int TRANSACTION_sendVisualVoicemailSmsForSubscriber = 59;
        static final int TRANSACTION_setAdvancedCallingSettingEnabled = 209;
        static final int TRANSACTION_setAllowedCarriers = 164;
        static final int TRANSACTION_setCarrierTestOverride = 192;
        static final int TRANSACTION_setCdmaRoamingMode = 190;
        static final int TRANSACTION_setCdmaSubscriptionMode = 191;
        static final int TRANSACTION_setCellInfoListRate = 72;
        static final int TRANSACTION_setDataActivationState = 48;
        static final int TRANSACTION_setDataAllowedDuringVoiceCall = 251;
        static final int TRANSACTION_setDataRoamingEnabled = 188;
        static final int TRANSACTION_setDefaultSmsApp = 200;
        static final int TRANSACTION_setImsProvisioningInt = 234;
        static final int TRANSACTION_setImsProvisioningStatusForCapability = 228;
        static final int TRANSACTION_setImsProvisioningString = 235;
        static final int TRANSACTION_setImsRegistrationState = 111;
        static final int TRANSACTION_setImsService = 97;
        static final int TRANSACTION_setLine1NumberForDisplayForSubscriber = 120;
        static final int TRANSACTION_setMultiSimCarrierRestriction = 239;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 99;
        static final int TRANSACTION_setNetworkSelectionModeManual = 103;
        static final int TRANSACTION_setOperatorBrandOverride = 124;
        static final int TRANSACTION_setPolicyDataEnabled = 176;
        static final int TRANSACTION_setPreferredNetworkType = 104;
        static final int TRANSACTION_setRadio = 18;
        static final int TRANSACTION_setRadioCapability = 129;
        static final int TRANSACTION_setRadioForSubscriber = 19;
        static final int TRANSACTION_setRadioIndicationUpdateMode = 186;
        static final int TRANSACTION_setRadioPower = 20;
        static final int TRANSACTION_setRoamingOverride = 125;
        static final int TRANSACTION_setRttCapabilitySetting = 221;
        static final int TRANSACTION_setSimPowerStateForSlot = 178;
        static final int TRANSACTION_setUserDataEnabled = 105;
        static final int TRANSACTION_setVoWiFiModeSetting = 218;
        static final int TRANSACTION_setVoWiFiNonPersistent = 216;
        static final int TRANSACTION_setVoWiFiRoamingModeSetting = 220;
        static final int TRANSACTION_setVoWiFiRoamingSettingEnabled = 215;
        static final int TRANSACTION_setVoWiFiSettingEnabled = 213;
        static final int TRANSACTION_setVoiceActivationState = 47;
        static final int TRANSACTION_setVoiceMailNumber = 46;
        static final int TRANSACTION_setVoicemailRingtoneUri = 155;
        static final int TRANSACTION_setVoicemailVibrationEnabled = 157;
        static final int TRANSACTION_setVtSettingEnabled = 211;
        static final int TRANSACTION_shutdownMobileRadios = 128;
        static final int TRANSACTION_stopNetworkScan = 102;
        static final int TRANSACTION_supplyPin = 5;
        static final int TRANSACTION_supplyPinForSubscriber = 6;
        static final int TRANSACTION_supplyPinReportResult = 9;
        static final int TRANSACTION_supplyPinReportResultForSubscriber = 10;
        static final int TRANSACTION_supplyPuk = 7;
        static final int TRANSACTION_supplyPukForSubscriber = 8;
        static final int TRANSACTION_supplyPukReportResult = 11;
        static final int TRANSACTION_supplyPukReportResultForSubscriber = 12;
        static final int TRANSACTION_switchMultiSimConfig = 241;
        static final int TRANSACTION_switchSlots = 185;
        static final int TRANSACTION_toggleRadioOnOff = 16;
        static final int TRANSACTION_toggleRadioOnOffForSubscriber = 17;
        static final int TRANSACTION_unregisterImsProvisioningChangedCallback = 227;
        static final int TRANSACTION_unregisterImsRegistrationCallback = 203;
        static final int TRANSACTION_unregisterMmTelCapabilityCallback = 205;
        static final int TRANSACTION_updateEmergencyNumberListTestMode = 236;
        static final int TRANSACTION_updateServiceLocation = 21;
        static final int TRANSACTION_updateServiceLocationForSubscriber = 22;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelephony asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITelephony)) {
                return new Proxy(obj);
            }
            return (ITelephony) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "dial";
                case 2:
                    return "call";
                case 3:
                    return "isRadioOn";
                case 4:
                    return "isRadioOnForSubscriber";
                case 5:
                    return "supplyPin";
                case 6:
                    return "supplyPinForSubscriber";
                case 7:
                    return "supplyPuk";
                case 8:
                    return "supplyPukForSubscriber";
                case 9:
                    return "supplyPinReportResult";
                case 10:
                    return "supplyPinReportResultForSubscriber";
                case 11:
                    return "supplyPukReportResult";
                case 12:
                    return "supplyPukReportResultForSubscriber";
                case 13:
                    return "handlePinMmi";
                case 14:
                    return "handleUssdRequest";
                case 15:
                    return "handlePinMmiForSubscriber";
                case 16:
                    return "toggleRadioOnOff";
                case 17:
                    return "toggleRadioOnOffForSubscriber";
                case 18:
                    return "setRadio";
                case 19:
                    return "setRadioForSubscriber";
                case 20:
                    return "setRadioPower";
                case 21:
                    return "updateServiceLocation";
                case 22:
                    return "updateServiceLocationForSubscriber";
                case 23:
                    return "enableLocationUpdates";
                case 24:
                    return "enableLocationUpdatesForSubscriber";
                case 25:
                    return "disableLocationUpdates";
                case 26:
                    return "disableLocationUpdatesForSubscriber";
                case 27:
                    return "enableDataConnectivity";
                case 28:
                    return "disableDataConnectivity";
                case 29:
                    return "isDataConnectivityPossible";
                case 30:
                    return "getCellLocation";
                case 31:
                    return "getNetworkCountryIsoForPhone";
                case 32:
                    return "getNeighboringCellInfo";
                case 33:
                    return "getCallState";
                case 34:
                    return "getCallStateForSlot";
                case 35:
                    return "getDataActivity";
                case 36:
                    return "getDataState";
                case 37:
                    return "getActivePhoneType";
                case 38:
                    return "getActivePhoneTypeForSlot";
                case 39:
                    return "getCdmaEriIconIndex";
                case 40:
                    return "getCdmaEriIconIndexForSubscriber";
                case 41:
                    return "getCdmaEriIconMode";
                case 42:
                    return "getCdmaEriIconModeForSubscriber";
                case 43:
                    return "getCdmaEriText";
                case 44:
                    return "getCdmaEriTextForSubscriber";
                case 45:
                    return "needsOtaServiceProvisioning";
                case 46:
                    return "setVoiceMailNumber";
                case 47:
                    return "setVoiceActivationState";
                case 48:
                    return "setDataActivationState";
                case 49:
                    return "getVoiceActivationState";
                case 50:
                    return "getDataActivationState";
                case 51:
                    return "getVoiceMessageCountForSubscriber";
                case 52:
                    return "isConcurrentVoiceAndDataAllowed";
                case 53:
                    return "getVisualVoicemailSettings";
                case 54:
                    return "getVisualVoicemailPackageName";
                case 55:
                    return "enableVisualVoicemailSmsFilter";
                case 56:
                    return "disableVisualVoicemailSmsFilter";
                case 57:
                    return "getVisualVoicemailSmsFilterSettings";
                case 58:
                    return "getActiveVisualVoicemailSmsFilterSettings";
                case 59:
                    return "sendVisualVoicemailSmsForSubscriber";
                case 60:
                    return "sendDialerSpecialCode";
                case 61:
                    return "getNetworkTypeForSubscriber";
                case 62:
                    return "getDataNetworkType";
                case 63:
                    return "getDataNetworkTypeForSubscriber";
                case 64:
                    return "getVoiceNetworkTypeForSubscriber";
                case 65:
                    return "hasIccCard";
                case 66:
                    return "hasIccCardUsingSlotIndex";
                case 67:
                    return "getLteOnCdmaMode";
                case 68:
                    return "getLteOnCdmaModeForSubscriber";
                case 69:
                    return "getAllCellInfo";
                case 70:
                    return "requestCellInfoUpdate";
                case 71:
                    return "requestCellInfoUpdateWithWorkSource";
                case 72:
                    return "setCellInfoListRate";
                case 73:
                    return "iccOpenLogicalChannelBySlot";
                case 74:
                    return "iccOpenLogicalChannel";
                case 75:
                    return "iccCloseLogicalChannelBySlot";
                case 76:
                    return "iccCloseLogicalChannel";
                case 77:
                    return "iccTransmitApduLogicalChannelBySlot";
                case 78:
                    return "iccTransmitApduLogicalChannel";
                case 79:
                    return "iccTransmitApduBasicChannelBySlot";
                case 80:
                    return "iccTransmitApduBasicChannel";
                case 81:
                    return "iccExchangeSimIO";
                case 82:
                    return "sendEnvelopeWithStatus";
                case 83:
                    return "nvReadItem";
                case 84:
                    return "nvWriteItem";
                case 85:
                    return "nvWriteCdmaPrl";
                case 86:
                    return "resetModemConfig";
                case 87:
                    return "rebootModem";
                case 88:
                    return "getCalculatedPreferredNetworkType";
                case 89:
                    return "getPreferredNetworkType";
                case 90:
                    return "getTetherApnRequiredForSubscriber";
                case 91:
                    return "enableIms";
                case 92:
                    return "disableIms";
                case 93:
                    return "getMmTelFeatureAndListen";
                case 94:
                    return "getRcsFeatureAndListen";
                case 95:
                    return "getImsRegistration";
                case 96:
                    return "getImsConfig";
                case 97:
                    return "setImsService";
                case 98:
                    return "getImsService";
                case 99:
                    return "setNetworkSelectionModeAutomatic";
                case 100:
                    return "getCellNetworkScanResults";
                case 101:
                    return "requestNetworkScan";
                case 102:
                    return "stopNetworkScan";
                case 103:
                    return "setNetworkSelectionModeManual";
                case 104:
                    return "setPreferredNetworkType";
                case 105:
                    return "setUserDataEnabled";
                case 106:
                    return "getDataEnabled";
                case 107:
                    return "isUserDataEnabled";
                case 108:
                    return "isDataEnabled";
                case 109:
                    return "isManualNetworkSelectionAllowed";
                case 110:
                    return "getPcscfAddress";
                case 111:
                    return "setImsRegistrationState";
                case 112:
                    return "getCdmaMdn";
                case 113:
                    return "getCdmaMin";
                case 114:
                    return "requestNumberVerification";
                case 115:
                    return "getCarrierPrivilegeStatus";
                case 116:
                    return "getCarrierPrivilegeStatusForUid";
                case 117:
                    return "checkCarrierPrivilegesForPackage";
                case 118:
                    return "checkCarrierPrivilegesForPackageAnyPhone";
                case 119:
                    return "getCarrierPackageNamesForIntentAndPhone";
                case 120:
                    return "setLine1NumberForDisplayForSubscriber";
                case 121:
                    return "getLine1NumberForDisplay";
                case 122:
                    return "getLine1AlphaTagForDisplay";
                case 123:
                    return "getMergedSubscriberIds";
                case 124:
                    return "setOperatorBrandOverride";
                case 125:
                    return "setRoamingOverride";
                case 126:
                    return "invokeOemRilRequestRaw";
                case 127:
                    return "needMobileRadioShutdown";
                case 128:
                    return "shutdownMobileRadios";
                case 129:
                    return "setRadioCapability";
                case 130:
                    return "getRadioAccessFamily";
                case 131:
                    return "enableVideoCalling";
                case 132:
                    return "isVideoCallingEnabled";
                case 133:
                    return "canChangeDtmfToneLength";
                case 134:
                    return "isWorldPhone";
                case 135:
                    return "isTtyModeSupported";
                case 136:
                    return "isRttSupported";
                case 137:
                    return "isHearingAidCompatibilitySupported";
                case 138:
                    return "isImsRegistered";
                case 139:
                    return "isWifiCallingAvailable";
                case 140:
                    return "isVideoTelephonyAvailable";
                case 141:
                    return "getImsRegTechnologyForMmTel";
                case 142:
                    return "getDeviceId";
                case 143:
                    return "getImeiForSlot";
                case 144:
                    return "getTypeAllocationCodeForSlot";
                case 145:
                    return "getMeidForSlot";
                case 146:
                    return "getManufacturerCodeForSlot";
                case 147:
                    return "getDeviceSoftwareVersionForSlot";
                case 148:
                    return "getSubIdForPhoneAccount";
                case 149:
                    return "getPhoneAccountHandleForSubscriptionId";
                case 150:
                    return "factoryReset";
                case 151:
                    return "getSimLocaleForSubscriber";
                case 152:
                    return "requestModemActivityInfo";
                case 153:
                    return "getServiceStateForSubscriber";
                case 154:
                    return "getVoicemailRingtoneUri";
                case 155:
                    return "setVoicemailRingtoneUri";
                case 156:
                    return "isVoicemailVibrationEnabled";
                case 157:
                    return "setVoicemailVibrationEnabled";
                case 158:
                    return "getPackagesWithCarrierPrivileges";
                case 159:
                    return "getPackagesWithCarrierPrivilegesForAllPhones";
                case 160:
                    return "getAidForAppType";
                case 161:
                    return "getEsn";
                case 162:
                    return "getCdmaPrlVersion";
                case 163:
                    return "getTelephonyHistograms";
                case 164:
                    return "setAllowedCarriers";
                case 165:
                    return "getAllowedCarriers";
                case 166:
                    return "getSubscriptionCarrierId";
                case 167:
                    return "getSubscriptionCarrierName";
                case 168:
                    return "getSubscriptionSpecificCarrierId";
                case 169:
                    return "getSubscriptionSpecificCarrierName";
                case 170:
                    return "getCarrierIdFromMccMnc";
                case 171:
                    return "carrierActionSetMeteredApnsEnabled";
                case 172:
                    return "carrierActionSetRadioEnabled";
                case 173:
                    return "carrierActionReportDefaultNetworkStatus";
                case 174:
                    return "carrierActionResetAll";
                case 175:
                    return "getVtDataUsage";
                case 176:
                    return "setPolicyDataEnabled";
                case 177:
                    return "getClientRequestStats";
                case 178:
                    return "setSimPowerStateForSlot";
                case 179:
                    return "getForbiddenPlmns";
                case 180:
                    return "getEmergencyCallbackMode";
                case 181:
                    return "getSignalStrength";
                case 182:
                    return "getCardIdForDefaultEuicc";
                case 183:
                    return "getUiccCardsInfo";
                case 184:
                    return "getUiccSlotsInfo";
                case 185:
                    return "switchSlots";
                case 186:
                    return "setRadioIndicationUpdateMode";
                case 187:
                    return "isDataRoamingEnabled";
                case 188:
                    return "setDataRoamingEnabled";
                case 189:
                    return "getCdmaRoamingMode";
                case 190:
                    return "setCdmaRoamingMode";
                case 191:
                    return "setCdmaSubscriptionMode";
                case 192:
                    return "setCarrierTestOverride";
                case 193:
                    return "getCarrierIdListVersion";
                case 194:
                    return "refreshUiccProfile";
                case 195:
                    return "getNumberOfModemsWithSimultaneousDataConnections";
                case 196:
                    return "getNetworkSelectionMode";
                case 197:
                    return "isInEmergencySmsMode";
                case 198:
                    return "getSmsApps";
                case 199:
                    return "getDefaultSmsApp";
                case 200:
                    return "setDefaultSmsApp";
                case 201:
                    return "getRadioPowerState";
                case 202:
                    return "registerImsRegistrationCallback";
                case 203:
                    return "unregisterImsRegistrationCallback";
                case 204:
                    return "registerMmTelCapabilityCallback";
                case 205:
                    return "unregisterMmTelCapabilityCallback";
                case 206:
                    return "isCapable";
                case 207:
                    return "isAvailable";
                case 208:
                    return "isAdvancedCallingSettingEnabled";
                case 209:
                    return "setAdvancedCallingSettingEnabled";
                case 210:
                    return "isVtSettingEnabled";
                case 211:
                    return "setVtSettingEnabled";
                case 212:
                    return "isVoWiFiSettingEnabled";
                case 213:
                    return "setVoWiFiSettingEnabled";
                case 214:
                    return "isVoWiFiRoamingSettingEnabled";
                case 215:
                    return "setVoWiFiRoamingSettingEnabled";
                case 216:
                    return "setVoWiFiNonPersistent";
                case 217:
                    return "getVoWiFiModeSetting";
                case 218:
                    return "setVoWiFiModeSetting";
                case 219:
                    return "getVoWiFiRoamingModeSetting";
                case 220:
                    return "setVoWiFiRoamingModeSetting";
                case 221:
                    return "setRttCapabilitySetting";
                case 222:
                    return "isTtyOverVolteEnabled";
                case 223:
                    return "getEmergencyNumberList";
                case 224:
                    return "isEmergencyNumber";
                case 225:
                    return "getCertsFromCarrierPrivilegeAccessRules";
                case 226:
                    return "registerImsProvisioningChangedCallback";
                case 227:
                    return "unregisterImsProvisioningChangedCallback";
                case 228:
                    return "setImsProvisioningStatusForCapability";
                case 229:
                    return "getImsProvisioningStatusForCapability";
                case 230:
                    return "isMmTelCapabilityProvisionedInCache";
                case 231:
                    return "cacheMmTelCapabilityProvisioning";
                case 232:
                    return "getImsProvisioningInt";
                case 233:
                    return "getImsProvisioningString";
                case 234:
                    return "setImsProvisioningInt";
                case 235:
                    return "setImsProvisioningString";
                case 236:
                    return "updateEmergencyNumberListTestMode";
                case 237:
                    return "getEmergencyNumberListTestMode";
                case 238:
                    return "enableModemForSlot";
                case 239:
                    return "setMultiSimCarrierRestriction";
                case 240:
                    return "isMultiSimSupported";
                case 241:
                    return "switchMultiSimConfig";
                case 242:
                    return "doesSwitchMultiSimConfigTriggerReboot";
                case 243:
                    return "getSlotsMapping";
                case 244:
                    return "getRadioHalVersion";
                case 245:
                    return "isModemEnabledForSlot";
                case 246:
                    return "isDataEnabledForApn";
                case 247:
                    return "isApnMetered";
                case 248:
                    return "enqueueSmsPickResult";
                case 249:
                    return "getMmsUserAgent";
                case 250:
                    return "getMmsUAProfUrl";
                case 251:
                    return "setDataAllowedDuringVoiceCall";
                case 252:
                    return "isDataAllowedInVoiceCall";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v57, resolved type: android.telephony.VisualVoicemailSmsFilterSettings} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v80, resolved type: android.os.WorkSource} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v129, resolved type: com.android.internal.telephony.OperatorInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v189, resolved type: android.telecom.PhoneAccount} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v201, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v209, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v213, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v223, resolved type: android.telephony.CarrierRestrictionRules} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v299, resolved type: android.telephony.emergency.EmergencyNumber} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v15, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v64 */
        /* JADX WARNING: type inference failed for: r0v110, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v112, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v114, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v116, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v122 */
        /* JADX WARNING: type inference failed for: r0v144 */
        /* JADX WARNING: type inference failed for: r0v154, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r0v196, types: [android.os.ResultReceiver] */
        /* JADX WARNING: type inference failed for: r0v205, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v321 */
        /* JADX WARNING: type inference failed for: r0v322 */
        /* JADX WARNING: type inference failed for: r0v323 */
        /* JADX WARNING: type inference failed for: r0v324 */
        /* JADX WARNING: type inference failed for: r0v325 */
        /* JADX WARNING: type inference failed for: r0v326 */
        /* JADX WARNING: type inference failed for: r0v327 */
        /* JADX WARNING: type inference failed for: r0v328 */
        /* JADX WARNING: type inference failed for: r0v329 */
        /* JADX WARNING: type inference failed for: r0v330 */
        /* JADX WARNING: type inference failed for: r0v331 */
        /* JADX WARNING: type inference failed for: r0v332 */
        /* JADX WARNING: type inference failed for: r0v333 */
        /* JADX WARNING: type inference failed for: r0v334 */
        /* JADX WARNING: type inference failed for: r0v335 */
        /* JADX WARNING: type inference failed for: r0v336 */
        /* JADX WARNING: type inference failed for: r0v337 */
        /* JADX WARNING: type inference failed for: r0v338 */
        /* JADX WARNING: type inference failed for: r0v339 */
        /* JADX WARNING: type inference failed for: r0v340 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r27, android.os.Parcel r28, android.os.Parcel r29, int r30) throws android.os.RemoteException {
            /*
                r26 = this;
                r11 = r26
                r12 = r27
                r13 = r28
                r14 = r29
                java.lang.String r15 = "com.android.internal.telephony.ITelephony"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r12 == r0) goto L_0x170e
                r0 = 0
                r1 = 0
                switch(r12) {
                    case 1: goto L_0x16ff;
                    case 2: goto L_0x16ec;
                    case 3: goto L_0x16d9;
                    case 4: goto L_0x16c2;
                    case 5: goto L_0x16af;
                    case 6: goto L_0x1698;
                    case 7: goto L_0x1681;
                    case 8: goto L_0x1666;
                    case 9: goto L_0x1653;
                    case 10: goto L_0x163c;
                    case 11: goto L_0x1625;
                    case 12: goto L_0x160a;
                    case 13: goto L_0x15f7;
                    case 14: goto L_0x15d4;
                    case 15: goto L_0x15bd;
                    case 16: goto L_0x15b2;
                    case 17: goto L_0x15a3;
                    case 18: goto L_0x158b;
                    case 19: goto L_0x1570;
                    case 20: goto L_0x1558;
                    case 21: goto L_0x154d;
                    case 22: goto L_0x153e;
                    case 23: goto L_0x1533;
                    case 24: goto L_0x1524;
                    case 25: goto L_0x1519;
                    case 26: goto L_0x150a;
                    case 27: goto L_0x14fb;
                    case 28: goto L_0x14ec;
                    case 29: goto L_0x14d9;
                    case 30: goto L_0x14bd;
                    case 31: goto L_0x14aa;
                    case 32: goto L_0x1497;
                    case 33: goto L_0x1488;
                    case 34: goto L_0x1475;
                    case 35: goto L_0x1466;
                    case 36: goto L_0x1457;
                    case 37: goto L_0x1448;
                    case 38: goto L_0x1435;
                    case 39: goto L_0x1422;
                    case 40: goto L_0x140b;
                    case 41: goto L_0x13f8;
                    case 42: goto L_0x13e1;
                    case 43: goto L_0x13ce;
                    case 44: goto L_0x13b7;
                    case 45: goto L_0x13a8;
                    case 46: goto L_0x138d;
                    case 47: goto L_0x137a;
                    case 48: goto L_0x1367;
                    case 49: goto L_0x1350;
                    case 50: goto L_0x1339;
                    case 51: goto L_0x1322;
                    case 52: goto L_0x130f;
                    case 53: goto L_0x12ef;
                    case 54: goto L_0x12d8;
                    case 55: goto L_0x12b5;
                    case 56: goto L_0x12a5;
                    case 57: goto L_0x1285;
                    case 58: goto L_0x1269;
                    case 59: goto L_0x1231;
                    case 60: goto L_0x121e;
                    case 61: goto L_0x1207;
                    case 62: goto L_0x11f4;
                    case 63: goto L_0x11dd;
                    case 64: goto L_0x11c6;
                    case 65: goto L_0x11b7;
                    case 66: goto L_0x11a4;
                    case 67: goto L_0x1191;
                    case 68: goto L_0x117a;
                    case 69: goto L_0x1167;
                    case 70: goto L_0x114c;
                    case 71: goto L_0x1121;
                    case 72: goto L_0x1112;
                    case 73: goto L_0x10ea;
                    case 74: goto L_0x10c2;
                    case 75: goto L_0x10ab;
                    case 76: goto L_0x1094;
                    case 77: goto L_0x1055;
                    case 78: goto L_0x1016;
                    case 79: goto L_0x0fd7;
                    case 80: goto L_0x0f98;
                    case 81: goto L_0x0f60;
                    case 82: goto L_0x0f49;
                    case 83: goto L_0x0f36;
                    case 84: goto L_0x0f1f;
                    case 85: goto L_0x0f0c;
                    case 86: goto L_0x0ef9;
                    case 87: goto L_0x0ee6;
                    case 88: goto L_0x0ed3;
                    case 89: goto L_0x0ec0;
                    case 90: goto L_0x0ead;
                    case 91: goto L_0x0e9e;
                    case 92: goto L_0x0e8f;
                    case 93: goto L_0x0e6d;
                    case 94: goto L_0x0e4b;
                    case 95: goto L_0x0e2d;
                    case 96: goto L_0x0e0f;
                    case 97: goto L_0x0df0;
                    case 98: goto L_0x0dd5;
                    case 99: goto L_0x0dc6;
                    case 100: goto L_0x0da6;
                    case 101: goto L_0x0d64;
                    case 102: goto L_0x0d51;
                    case 103: goto L_0x0d26;
                    case 104: goto L_0x0d0f;
                    case 105: goto L_0x0cf8;
                    case 106: goto L_0x0ce5;
                    case 107: goto L_0x0cd2;
                    case 108: goto L_0x0cbf;
                    case 109: goto L_0x0cac;
                    case 110: goto L_0x0c95;
                    case 111: goto L_0x0c81;
                    case 112: goto L_0x0c6e;
                    case 113: goto L_0x0c5b;
                    case 114: goto L_0x0c2a;
                    case 115: goto L_0x0c17;
                    case 116: goto L_0x0c00;
                    case 117: goto L_0x0be9;
                    case 118: goto L_0x0bd6;
                    case 119: goto L_0x0bb3;
                    case 120: goto L_0x0b98;
                    case 121: goto L_0x0b81;
                    case 122: goto L_0x0b6a;
                    case 123: goto L_0x0b53;
                    case 124: goto L_0x0b3c;
                    case 125: goto L_0x0b12;
                    case 126: goto L_0x0af2;
                    case 127: goto L_0x0ae3;
                    case 128: goto L_0x0ad8;
                    case 129: goto L_0x0ac5;
                    case 130: goto L_0x0aae;
                    case 131: goto L_0x0a9a;
                    case 132: goto L_0x0a87;
                    case 133: goto L_0x0a70;
                    case 134: goto L_0x0a59;
                    case 135: goto L_0x0a4a;
                    case 136: goto L_0x0a37;
                    case 137: goto L_0x0a28;
                    case 138: goto L_0x0a15;
                    case 139: goto L_0x0a02;
                    case 140: goto L_0x09ef;
                    case 141: goto L_0x09dc;
                    case 142: goto L_0x09c9;
                    case 143: goto L_0x09b2;
                    case 144: goto L_0x099f;
                    case 145: goto L_0x0988;
                    case 146: goto L_0x0975;
                    case 147: goto L_0x095e;
                    case 148: goto L_0x093f;
                    case 149: goto L_0x0923;
                    case 150: goto L_0x0914;
                    case 151: goto L_0x0901;
                    case 152: goto L_0x08e9;
                    case 153: goto L_0x08c9;
                    case 154: goto L_0x08a1;
                    case 155: goto L_0x0872;
                    case 156: goto L_0x0853;
                    case 157: goto L_0x082c;
                    case 158: goto L_0x0819;
                    case 159: goto L_0x080a;
                    case 160: goto L_0x07f3;
                    case 161: goto L_0x07e0;
                    case 162: goto L_0x07cd;
                    case 163: goto L_0x07be;
                    case 164: goto L_0x079f;
                    case 165: goto L_0x0787;
                    case 166: goto L_0x0774;
                    case 167: goto L_0x0761;
                    case 168: goto L_0x074e;
                    case 169: goto L_0x073b;
                    case 170: goto L_0x071c;
                    case 171: goto L_0x0705;
                    case 172: goto L_0x06ee;
                    case 173: goto L_0x06d7;
                    case 174: goto L_0x06c8;
                    case 175: goto L_0x06a3;
                    case 176: goto L_0x068b;
                    case 177: goto L_0x0674;
                    case 178: goto L_0x0661;
                    case 179: goto L_0x0646;
                    case 180: goto L_0x0633;
                    case 181: goto L_0x0617;
                    case 182: goto L_0x0600;
                    case 183: goto L_0x05ed;
                    case 184: goto L_0x05de;
                    case 185: goto L_0x05cb;
                    case 186: goto L_0x05b4;
                    case 187: goto L_0x05a1;
                    case 188: goto L_0x058a;
                    case 189: goto L_0x0577;
                    case 190: goto L_0x0560;
                    case 191: goto L_0x0549;
                    case 192: goto L_0x0500;
                    case 193: goto L_0x04ee;
                    case 194: goto L_0x04e0;
                    case 195: goto L_0x04ca;
                    case 196: goto L_0x04b8;
                    case 197: goto L_0x04aa;
                    case 198: goto L_0x0498;
                    case 199: goto L_0x0486;
                    case 200: goto L_0x0474;
                    case 201: goto L_0x045e;
                    case 202: goto L_0x0448;
                    case 203: goto L_0x0432;
                    case 204: goto L_0x041c;
                    case 205: goto L_0x0406;
                    case 206: goto L_0x03ec;
                    case 207: goto L_0x03d2;
                    case 208: goto L_0x03c0;
                    case 209: goto L_0x03aa;
                    case 210: goto L_0x0398;
                    case 211: goto L_0x0382;
                    case 212: goto L_0x0370;
                    case 213: goto L_0x035a;
                    case 214: goto L_0x0348;
                    case 215: goto L_0x0332;
                    case 216: goto L_0x0318;
                    case 217: goto L_0x0306;
                    case 218: goto L_0x02f4;
                    case 219: goto L_0x02e2;
                    case 220: goto L_0x02d0;
                    case 221: goto L_0x02ba;
                    case 222: goto L_0x02a8;
                    case 223: goto L_0x0296;
                    case 224: goto L_0x027c;
                    case 225: goto L_0x026a;
                    case 226: goto L_0x0254;
                    case 227: goto L_0x023e;
                    case 228: goto L_0x0220;
                    case 229: goto L_0x0206;
                    case 230: goto L_0x01ec;
                    case 231: goto L_0x01ce;
                    case 232: goto L_0x01b8;
                    case 233: goto L_0x01a2;
                    case 234: goto L_0x0188;
                    case 235: goto L_0x016e;
                    case 236: goto L_0x0150;
                    case 237: goto L_0x0142;
                    case 238: goto L_0x0128;
                    case 239: goto L_0x0115;
                    case 240: goto L_0x0103;
                    case 241: goto L_0x00f5;
                    case 242: goto L_0x00df;
                    case 243: goto L_0x00d1;
                    case 244: goto L_0x00c3;
                    case 245: goto L_0x00ad;
                    case 246: goto L_0x0093;
                    case 247: goto L_0x007d;
                    case 248: goto L_0x006a;
                    case 249: goto L_0x0058;
                    case 250: goto L_0x0046;
                    case 251: goto L_0x002c;
                    case 252: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r27, r28, r29, r30)
                return r0
            L_0x001a:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isDataAllowedInVoiceCall(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x002c:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x003b
                r1 = r10
            L_0x003b:
                boolean r2 = r11.setDataAllowedDuringVoiceCall(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x0046:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getMmsUAProfUrl(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r10
            L_0x0058:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getMmsUserAgent(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r10
            L_0x006a:
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                android.os.IBinder r1 = r28.readStrongBinder()
                com.android.internal.telephony.IIntegerConsumer r1 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(r1)
                r11.enqueueSmsPickResult(r0, r1)
                return r10
            L_0x007d:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                boolean r2 = r11.isApnMetered(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x0093:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                java.lang.String r2 = r28.readString()
                boolean r3 = r11.isDataEnabledForApn(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x00ad:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.isModemEnabledForSlot(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x00c3:
                r13.enforceInterface(r15)
                int r0 = r26.getRadioHalVersion()
                r29.writeNoException()
                r14.writeInt(r0)
                return r10
            L_0x00d1:
                r13.enforceInterface(r15)
                int[] r0 = r26.getSlotsMapping()
                r29.writeNoException()
                r14.writeIntArray(r0)
                return r10
            L_0x00df:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.doesSwitchMultiSimConfigTriggerReboot(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x00f5:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.switchMultiSimConfig(r0)
                r29.writeNoException()
                return r10
            L_0x0103:
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.isMultiSimSupported(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x0115:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0120
                r1 = r10
            L_0x0120:
                r0 = r1
                r11.setMultiSimCarrierRestriction(r0)
                r29.writeNoException()
                return r10
            L_0x0128:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0137
                r1 = r10
            L_0x0137:
                boolean r2 = r11.enableModemForSlot(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x0142:
                r13.enforceInterface(r15)
                java.util.List r0 = r26.getEmergencyNumberListTestMode()
                r29.writeNoException()
                r14.writeStringList(r0)
                return r10
            L_0x0150:
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0166
                android.os.Parcelable$Creator<android.telephony.emergency.EmergencyNumber> r0 = android.telephony.emergency.EmergencyNumber.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.emergency.EmergencyNumber r0 = (android.telephony.emergency.EmergencyNumber) r0
                goto L_0x0167
            L_0x0166:
            L_0x0167:
                r11.updateEmergencyNumberListTestMode(r1, r0)
                r29.writeNoException()
                return r10
            L_0x016e:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                java.lang.String r2 = r28.readString()
                int r3 = r11.setImsProvisioningString(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x0188:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                int r3 = r11.setImsProvisioningInt(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x01a2:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                java.lang.String r2 = r11.getImsProvisioningString(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r10
            L_0x01b8:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r11.getImsProvisioningInt(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x01ce:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x01e5
                r1 = r10
            L_0x01e5:
                r11.cacheMmTelCapabilityProvisioning(r0, r2, r3, r1)
                r29.writeNoException()
                return r10
            L_0x01ec:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                boolean r3 = r11.isMmTelCapabilityProvisionedInCache(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x0206:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                boolean r3 = r11.getImsProvisioningStatusForCapability(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x0220:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x0237
                r1 = r10
            L_0x0237:
                r11.setImsProvisioningStatusForCapability(r0, r2, r3, r1)
                r29.writeNoException()
                return r10
            L_0x023e:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ims.aidl.IImsConfigCallback r1 = android.telephony.ims.aidl.IImsConfigCallback.Stub.asInterface(r1)
                r11.unregisterImsProvisioningChangedCallback(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0254:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ims.aidl.IImsConfigCallback r1 = android.telephony.ims.aidl.IImsConfigCallback.Stub.asInterface(r1)
                r11.registerImsProvisioningChangedCallback(r0, r1)
                r29.writeNoException()
                return r10
            L_0x026a:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.util.List r1 = r11.getCertsFromCarrierPrivilegeAccessRules(r0)
                r29.writeNoException()
                r14.writeStringList(r1)
                return r10
            L_0x027c:
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x028b
                r1 = r10
            L_0x028b:
                boolean r2 = r11.isEmergencyNumber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x0296:
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.util.Map r1 = r11.getEmergencyNumberList(r0)
                r29.writeNoException()
                r14.writeMap(r1)
                return r10
            L_0x02a8:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isTtyOverVolteEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x02ba:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x02c9
                r1 = r10
            L_0x02c9:
                r11.setRttCapabilitySetting(r0, r1)
                r29.writeNoException()
                return r10
            L_0x02d0:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r11.setVoWiFiRoamingModeSetting(r0, r1)
                r29.writeNoException()
                return r10
            L_0x02e2:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getVoWiFiRoamingModeSetting(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x02f4:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r11.setVoWiFiModeSetting(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0306:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getVoWiFiModeSetting(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x0318:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0327
                r1 = r10
            L_0x0327:
                int r2 = r28.readInt()
                r11.setVoWiFiNonPersistent(r0, r1, r2)
                r29.writeNoException()
                return r10
            L_0x0332:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0341
                r1 = r10
            L_0x0341:
                r11.setVoWiFiRoamingSettingEnabled(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0348:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isVoWiFiRoamingSettingEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x035a:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0369
                r1 = r10
            L_0x0369:
                r11.setVoWiFiSettingEnabled(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0370:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isVoWiFiSettingEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x0382:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0391
                r1 = r10
            L_0x0391:
                r11.setVtSettingEnabled(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0398:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isVtSettingEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x03aa:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x03b9
                r1 = r10
            L_0x03b9:
                r11.setAdvancedCallingSettingEnabled(r0, r1)
                r29.writeNoException()
                return r10
            L_0x03c0:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isAdvancedCallingSettingEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x03d2:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                boolean r3 = r11.isAvailable(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x03ec:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                boolean r3 = r11.isCapable(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r10
            L_0x0406:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ims.aidl.IImsCapabilityCallback r1 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(r1)
                r11.unregisterMmTelCapabilityCallback(r0, r1)
                r29.writeNoException()
                return r10
            L_0x041c:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ims.aidl.IImsCapabilityCallback r1 = android.telephony.ims.aidl.IImsCapabilityCallback.Stub.asInterface(r1)
                r11.registerMmTelCapabilityCallback(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0432:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ims.aidl.IImsRegistrationCallback r1 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(r1)
                r11.unregisterImsRegistrationCallback(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0448:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ims.aidl.IImsRegistrationCallback r1 = android.telephony.ims.aidl.IImsRegistrationCallback.Stub.asInterface(r1)
                r11.registerImsRegistrationCallback(r0, r1)
                r29.writeNoException()
                return r10
            L_0x045e:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getRadioPowerState(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x0474:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                r11.setDefaultSmsApp(r0, r1)
                r29.writeNoException()
                return r10
            L_0x0486:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getDefaultSmsApp(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r10
            L_0x0498:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String[] r1 = r11.getSmsApps(r0)
                r29.writeNoException()
                r14.writeStringArray(r1)
                return r10
            L_0x04aa:
                r13.enforceInterface(r15)
                boolean r0 = r26.isInEmergencySmsMode()
                r29.writeNoException()
                r14.writeInt(r0)
                return r10
            L_0x04b8:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getNetworkSelectionMode(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x04ca:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getNumberOfModemsWithSimultaneousDataConnections(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r10
            L_0x04e0:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.refreshUiccProfile(r0)
                r29.writeNoException()
                return r10
            L_0x04ee:
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getCarrierIdListVersion(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r10
            L_0x0500:
                r13.enforceInterface(r15)
                int r16 = r28.readInt()
                java.lang.String r17 = r28.readString()
                java.lang.String r18 = r28.readString()
                java.lang.String r19 = r28.readString()
                java.lang.String r20 = r28.readString()
                java.lang.String r21 = r28.readString()
                java.lang.String r22 = r28.readString()
                java.lang.String r23 = r28.readString()
                java.lang.String r24 = r28.readString()
                java.lang.String r25 = r28.readString()
                r0 = r26
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r5 = r20
                r6 = r21
                r7 = r22
                r8 = r23
                r9 = r24
                r12 = r10
                r10 = r25
                r0.setCarrierTestOverride(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r29.writeNoException()
                return r12
            L_0x0549:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                boolean r2 = r11.setCdmaSubscriptionMode(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0560:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                boolean r2 = r11.setCdmaRoamingMode(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0577:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getCdmaRoamingMode(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x058a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x059a
                r1 = r12
            L_0x059a:
                r11.setDataRoamingEnabled(r0, r1)
                r29.writeNoException()
                return r12
            L_0x05a1:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isDataRoamingEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x05b4:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                r11.setRadioIndicationUpdateMode(r0, r1, r2)
                r29.writeNoException()
                return r12
            L_0x05cb:
                r12 = r10
                r13.enforceInterface(r15)
                int[] r0 = r28.createIntArray()
                boolean r1 = r11.switchSlots(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x05de:
                r12 = r10
                r13.enforceInterface(r15)
                android.telephony.UiccSlotInfo[] r0 = r26.getUiccSlotsInfo()
                r29.writeNoException()
                r14.writeTypedArray(r0, r12)
                return r12
            L_0x05ed:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.util.List r1 = r11.getUiccCardsInfo(r0)
                r29.writeNoException()
                r14.writeTypedList(r1)
                return r12
            L_0x0600:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getCardIdForDefaultEuicc(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0617:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.telephony.SignalStrength r2 = r11.getSignalStrength(r0)
                r29.writeNoException()
                if (r2 == 0) goto L_0x062f
                r14.writeInt(r12)
                r2.writeToParcel(r14, r12)
                goto L_0x0632
            L_0x062f:
                r14.writeInt(r1)
            L_0x0632:
                return r12
            L_0x0633:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.getEmergencyCallbackMode(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0646:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                java.lang.String r2 = r28.readString()
                java.lang.String[] r3 = r11.getForbiddenPlmns(r0, r1, r2)
                r29.writeNoException()
                r14.writeStringArray(r3)
                return r12
            L_0x0661:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r11.setSimPowerStateForSlot(r0, r1)
                r29.writeNoException()
                return r12
            L_0x0674:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                java.util.List r2 = r11.getClientRequestStats(r0, r1)
                r29.writeNoException()
                r14.writeTypedList(r2)
                return r12
            L_0x068b:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0697
                r1 = r12
            L_0x0697:
                r0 = r1
                int r1 = r28.readInt()
                r11.setPolicyDataEnabled(r0, r1)
                r29.writeNoException()
                return r12
            L_0x06a3:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x06b3
                r2 = r12
                goto L_0x06b4
            L_0x06b3:
                r2 = r1
            L_0x06b4:
                android.net.NetworkStats r3 = r11.getVtDataUsage(r0, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x06c4
                r14.writeInt(r12)
                r3.writeToParcel(r14, r12)
                goto L_0x06c7
            L_0x06c4:
                r14.writeInt(r1)
            L_0x06c7:
                return r12
            L_0x06c8:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.carrierActionResetAll(r0)
                r29.writeNoException()
                return r12
            L_0x06d7:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x06e7
                r1 = r12
            L_0x06e7:
                r11.carrierActionReportDefaultNetworkStatus(r0, r1)
                r29.writeNoException()
                return r12
            L_0x06ee:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x06fe
                r1 = r12
            L_0x06fe:
                r11.carrierActionSetRadioEnabled(r0, r1)
                r29.writeNoException()
                return r12
            L_0x0705:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0715
                r1 = r12
            L_0x0715:
                r11.carrierActionSetMeteredApnsEnabled(r0, r1)
                r29.writeNoException()
                return r12
            L_0x071c:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0730
                r1 = r12
            L_0x0730:
                int r3 = r11.getCarrierIdFromMccMnc(r0, r2, r1)
                r29.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x073b:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getSubscriptionSpecificCarrierName(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x074e:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getSubscriptionSpecificCarrierId(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0761:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getSubscriptionCarrierName(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x0774:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getSubscriptionCarrierId(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0787:
                r12 = r10
                r13.enforceInterface(r15)
                android.telephony.CarrierRestrictionRules r0 = r26.getAllowedCarriers()
                r29.writeNoException()
                if (r0 == 0) goto L_0x079b
                r14.writeInt(r12)
                r0.writeToParcel(r14, r12)
                goto L_0x079e
            L_0x079b:
                r14.writeInt(r1)
            L_0x079e:
                return r12
            L_0x079f:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x07b2
                android.os.Parcelable$Creator<android.telephony.CarrierRestrictionRules> r0 = android.telephony.CarrierRestrictionRules.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.CarrierRestrictionRules r0 = (android.telephony.CarrierRestrictionRules) r0
                goto L_0x07b3
            L_0x07b2:
            L_0x07b3:
                int r1 = r11.setAllowedCarriers(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x07be:
                r12 = r10
                r13.enforceInterface(r15)
                java.util.List r0 = r26.getTelephonyHistograms()
                r29.writeNoException()
                r14.writeTypedList(r0)
                return r12
            L_0x07cd:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getCdmaPrlVersion(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x07e0:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getEsn(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x07f3:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                java.lang.String r2 = r11.getAidForAppType(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x080a:
                r12 = r10
                r13.enforceInterface(r15)
                java.util.List r0 = r26.getPackagesWithCarrierPrivilegesForAllPhones()
                r29.writeNoException()
                r14.writeStringList(r0)
                return r12
            L_0x0819:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.util.List r1 = r11.getPackagesWithCarrierPrivileges(r0)
                r29.writeNoException()
                r14.writeStringList(r1)
                return r12
            L_0x082c:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r2 = r28.readString()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0843
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r0 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telecom.PhoneAccountHandle r0 = (android.telecom.PhoneAccountHandle) r0
                goto L_0x0844
            L_0x0843:
            L_0x0844:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x084c
                r1 = r12
            L_0x084c:
                r11.setVoicemailVibrationEnabled(r2, r0, r1)
                r29.writeNoException()
                return r12
            L_0x0853:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0866
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r0 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telecom.PhoneAccountHandle r0 = (android.telecom.PhoneAccountHandle) r0
                goto L_0x0867
            L_0x0866:
            L_0x0867:
                boolean r1 = r11.isVoicemailVibrationEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0872:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r1 = r28.readString()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0889
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r2 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r13)
                android.telecom.PhoneAccountHandle r2 = (android.telecom.PhoneAccountHandle) r2
                goto L_0x088a
            L_0x0889:
                r2 = r0
            L_0x088a:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0899
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x089a
            L_0x0899:
            L_0x089a:
                r11.setVoicemailRingtoneUri(r1, r2, r0)
                r29.writeNoException()
                return r12
            L_0x08a1:
                r12 = r10
                r13.enforceInterface(r15)
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x08b4
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r0 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telecom.PhoneAccountHandle r0 = (android.telecom.PhoneAccountHandle) r0
                goto L_0x08b5
            L_0x08b4:
            L_0x08b5:
                android.net.Uri r2 = r11.getVoicemailRingtoneUri(r0)
                r29.writeNoException()
                if (r2 == 0) goto L_0x08c5
                r14.writeInt(r12)
                r2.writeToParcel(r14, r12)
                goto L_0x08c8
            L_0x08c5:
                r14.writeInt(r1)
            L_0x08c8:
                return r12
            L_0x08c9:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                android.telephony.ServiceState r3 = r11.getServiceStateForSubscriber(r0, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x08e5
                r14.writeInt(r12)
                r3.writeToParcel(r14, r12)
                goto L_0x08e8
            L_0x08e5:
                r14.writeInt(r1)
            L_0x08e8:
                return r12
            L_0x08e9:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x08fc
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x08fd
            L_0x08fc:
            L_0x08fd:
                r11.requestModemActivityInfo(r0)
                return r12
            L_0x0901:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getSimLocaleForSubscriber(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x0914:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.factoryReset(r0)
                r29.writeNoException()
                return r12
            L_0x0923:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.telecom.PhoneAccountHandle r2 = r11.getPhoneAccountHandleForSubscriptionId(r0)
                r29.writeNoException()
                if (r2 == 0) goto L_0x093b
                r14.writeInt(r12)
                r2.writeToParcel(r14, r12)
                goto L_0x093e
            L_0x093b:
                r14.writeInt(r1)
            L_0x093e:
                return r12
            L_0x093f:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0952
                android.os.Parcelable$Creator<android.telecom.PhoneAccount> r0 = android.telecom.PhoneAccount.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telecom.PhoneAccount r0 = (android.telecom.PhoneAccount) r0
                goto L_0x0953
            L_0x0952:
            L_0x0953:
                int r1 = r11.getSubIdForPhoneAccount(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x095e:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.getDeviceSoftwareVersionForSlot(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x0975:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getManufacturerCodeForSlot(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x0988:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.getMeidForSlot(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x099f:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getTypeAllocationCodeForSlot(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x09b2:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.getImeiForSlot(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x09c9:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r11.getDeviceId(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x09dc:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getImsRegTechnologyForMmTel(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x09ef:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isVideoTelephonyAvailable(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0a02:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isWifiCallingAvailable(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0a15:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isImsRegistered(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0a28:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.isHearingAidCompatibilitySupported()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x0a37:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isRttSupported(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0a4a:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.isTtyModeSupported()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x0a59:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.isWorldPhone(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0a70:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.canChangeDtmfToneLength(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0a87:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                boolean r1 = r11.isVideoCallingEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0a9a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0aa6
                r1 = r12
            L_0x0aa6:
                r0 = r1
                r11.enableVideoCalling(r0)
                r29.writeNoException()
                return r12
            L_0x0aae:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getRadioAccessFamily(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0ac5:
                r12 = r10
                r13.enforceInterface(r15)
                android.os.Parcelable$Creator<android.telephony.RadioAccessFamily> r0 = android.telephony.RadioAccessFamily.CREATOR
                java.lang.Object[] r0 = r13.createTypedArray(r0)
                android.telephony.RadioAccessFamily[] r0 = (android.telephony.RadioAccessFamily[]) r0
                r11.setRadioCapability(r0)
                r29.writeNoException()
                return r12
            L_0x0ad8:
                r12 = r10
                r13.enforceInterface(r15)
                r26.shutdownMobileRadios()
                r29.writeNoException()
                return r12
            L_0x0ae3:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.needMobileRadioShutdown()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x0af2:
                r12 = r10
                r13.enforceInterface(r15)
                byte[] r0 = r28.createByteArray()
                int r1 = r28.readInt()
                if (r1 >= 0) goto L_0x0b02
                r2 = 0
                goto L_0x0b04
            L_0x0b02:
                byte[] r2 = new byte[r1]
            L_0x0b04:
                int r3 = r11.invokeOemRilRequestRaw(r0, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                r14.writeByteArray(r2)
                return r12
            L_0x0b12:
                r12 = r10
                r13.enforceInterface(r15)
                int r6 = r28.readInt()
                java.util.ArrayList r7 = r28.createStringArrayList()
                java.util.ArrayList r8 = r28.createStringArrayList()
                java.util.ArrayList r9 = r28.createStringArrayList()
                java.util.ArrayList r10 = r28.createStringArrayList()
                r0 = r26
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                boolean r0 = r0.setRoamingOverride(r1, r2, r3, r4, r5)
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x0b3c:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.setOperatorBrandOverride(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0b53:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String[] r2 = r11.getMergedSubscriberIds(r0, r1)
                r29.writeNoException()
                r14.writeStringArray(r2)
                return r12
            L_0x0b6a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.getLine1AlphaTagForDisplay(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x0b81:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.getLine1NumberForDisplay(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x0b98:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r28.readString()
                boolean r3 = r11.setLine1NumberForDisplayForSubscriber(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x0bb3:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0bc6
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0bc7
            L_0x0bc6:
            L_0x0bc7:
                int r1 = r28.readInt()
                java.util.List r2 = r11.getCarrierPackageNamesForIntentAndPhone(r0, r1)
                r29.writeNoException()
                r14.writeStringList(r2)
                return r12
            L_0x0bd6:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.checkCarrierPrivilegesForPackageAnyPhone(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0be9:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.checkCarrierPrivilegesForPackage(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0c00:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r11.getCarrierPrivilegeStatusForUid(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0c17:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getCarrierPrivilegeStatus(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0c2a:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0c3e
                android.os.Parcelable$Creator<android.telephony.PhoneNumberRange> r0 = android.telephony.PhoneNumberRange.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.PhoneNumberRange r0 = (android.telephony.PhoneNumberRange) r0
            L_0x0c3c:
                r1 = r0
                goto L_0x0c3f
            L_0x0c3e:
                goto L_0x0c3c
            L_0x0c3f:
                long r6 = r28.readLong()
                android.os.IBinder r0 = r28.readStrongBinder()
                com.android.internal.telephony.INumberVerificationCallback r8 = com.android.internal.telephony.INumberVerificationCallback.Stub.asInterface(r0)
                java.lang.String r9 = r28.readString()
                r0 = r26
                r2 = r6
                r4 = r8
                r5 = r9
                r0.requestNumberVerification(r1, r2, r4, r5)
                r29.writeNoException()
                return r12
            L_0x0c5b:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getCdmaMin(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x0c6e:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getCdmaMdn(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x0c81:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0c8d
                r1 = r12
            L_0x0c8d:
                r0 = r1
                r11.setImsRegistrationState(r0)
                r29.writeNoException()
                return r12
            L_0x0c95:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r28.readString()
                java.lang.String[] r2 = r11.getPcscfAddress(r0, r1)
                r29.writeNoException()
                r14.writeStringArray(r2)
                return r12
            L_0x0cac:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isManualNetworkSelectionAllowed(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0cbf:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isDataEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0cd2:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isUserDataEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0ce5:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.getDataEnabled(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0cf8:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0d08
                r1 = r12
            L_0x0d08:
                r11.setUserDataEnabled(r0, r1)
                r29.writeNoException()
                return r12
            L_0x0d0f:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                boolean r2 = r11.setPreferredNetworkType(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0d26:
                r12 = r10
                r13.enforceInterface(r15)
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0d3d
                android.os.Parcelable$Creator<com.android.internal.telephony.OperatorInfo> r0 = com.android.internal.telephony.OperatorInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                com.android.internal.telephony.OperatorInfo r0 = (com.android.internal.telephony.OperatorInfo) r0
                goto L_0x0d3e
            L_0x0d3d:
            L_0x0d3e:
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0d46
                r1 = r12
            L_0x0d46:
                boolean r3 = r11.setNetworkSelectionModeManual(r2, r0, r1)
                r29.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x0d51:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r11.stopNetworkScan(r0, r1)
                r29.writeNoException()
                return r12
            L_0x0d64:
                r12 = r10
                r13.enforceInterface(r15)
                int r6 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0d7c
                android.os.Parcelable$Creator<android.telephony.NetworkScanRequest> r1 = android.telephony.NetworkScanRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.telephony.NetworkScanRequest r1 = (android.telephony.NetworkScanRequest) r1
                r2 = r1
                goto L_0x0d7d
            L_0x0d7c:
                r2 = r0
            L_0x0d7d:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0d8d
                android.os.Parcelable$Creator<android.os.Messenger> r0 = android.os.Messenger.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Messenger r0 = (android.os.Messenger) r0
            L_0x0d8b:
                r3 = r0
                goto L_0x0d8e
            L_0x0d8d:
                goto L_0x0d8b
            L_0x0d8e:
                android.os.IBinder r7 = r28.readStrongBinder()
                java.lang.String r8 = r28.readString()
                r0 = r26
                r1 = r6
                r4 = r7
                r5 = r8
                int r0 = r0.requestNetworkScan(r1, r2, r3, r4, r5)
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x0da6:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                com.android.internal.telephony.CellNetworkScanResult r3 = r11.getCellNetworkScanResults(r0, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x0dc2
                r14.writeInt(r12)
                r3.writeToParcel(r14, r12)
                goto L_0x0dc5
            L_0x0dc2:
                r14.writeInt(r1)
            L_0x0dc5:
                return r12
            L_0x0dc6:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.setNetworkSelectionModeAutomatic(r0)
                r29.writeNoException()
                return r12
            L_0x0dd5:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0de5
                r1 = r12
            L_0x0de5:
                java.lang.String r2 = r11.getImsService(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x0df0:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x0e00
                r1 = r12
            L_0x0e00:
                java.lang.String r2 = r28.readString()
                boolean r3 = r11.setImsService(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x0e0f:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                android.telephony.ims.aidl.IImsConfig r3 = r11.getImsConfig(r1, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x0e29
                android.os.IBinder r0 = r3.asBinder()
            L_0x0e29:
                r14.writeStrongBinder(r0)
                return r12
            L_0x0e2d:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                android.telephony.ims.aidl.IImsRegistration r3 = r11.getImsRegistration(r1, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x0e47
                android.os.IBinder r0 = r3.asBinder()
            L_0x0e47:
                r14.writeStrongBinder(r0)
                return r12
            L_0x0e4b:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                android.os.IBinder r2 = r28.readStrongBinder()
                com.android.ims.internal.IImsServiceFeatureCallback r2 = com.android.ims.internal.IImsServiceFeatureCallback.Stub.asInterface(r2)
                android.telephony.ims.aidl.IImsRcsFeature r3 = r11.getRcsFeatureAndListen(r1, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x0e69
                android.os.IBinder r0 = r3.asBinder()
            L_0x0e69:
                r14.writeStrongBinder(r0)
                return r12
            L_0x0e6d:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                android.os.IBinder r2 = r28.readStrongBinder()
                com.android.ims.internal.IImsServiceFeatureCallback r2 = com.android.ims.internal.IImsServiceFeatureCallback.Stub.asInterface(r2)
                android.telephony.ims.aidl.IImsMmTelFeature r3 = r11.getMmTelFeatureAndListen(r1, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x0e8b
                android.os.IBinder r0 = r3.asBinder()
            L_0x0e8b:
                r14.writeStrongBinder(r0)
                return r12
            L_0x0e8f:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.disableIms(r0)
                r29.writeNoException()
                return r12
            L_0x0e9e:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.enableIms(r0)
                r29.writeNoException()
                return r12
            L_0x0ead:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.getTetherApnRequiredForSubscriber(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0ec0:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getPreferredNetworkType(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0ed3:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.getCalculatedPreferredNetworkType(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0ee6:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.rebootModem(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0ef9:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.resetModemConfig(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0f0c:
                r12 = r10
                r13.enforceInterface(r15)
                byte[] r0 = r28.createByteArray()
                boolean r1 = r11.nvWriteCdmaPrl(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x0f1f:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.nvWriteItem(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0f36:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.nvReadItem(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x0f49:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.sendEnvelopeWithStatus(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x0f60:
                r12 = r10
                r13.enforceInterface(r15)
                int r8 = r28.readInt()
                int r9 = r28.readInt()
                int r10 = r28.readInt()
                int r16 = r28.readInt()
                int r17 = r28.readInt()
                int r18 = r28.readInt()
                java.lang.String r19 = r28.readString()
                r0 = r26
                r1 = r8
                r2 = r9
                r3 = r10
                r4 = r16
                r5 = r17
                r6 = r18
                r7 = r19
                byte[] r0 = r0.iccExchangeSimIO(r1, r2, r3, r4, r5, r6, r7)
                r29.writeNoException()
                r14.writeByteArray(r0)
                return r12
            L_0x0f98:
                r12 = r10
                r13.enforceInterface(r15)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                int r16 = r28.readInt()
                int r17 = r28.readInt()
                int r18 = r28.readInt()
                int r19 = r28.readInt()
                int r20 = r28.readInt()
                java.lang.String r21 = r28.readString()
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r16
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r8 = r21
                java.lang.String r0 = r0.iccTransmitApduBasicChannel(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                r14.writeString(r0)
                return r12
            L_0x0fd7:
                r12 = r10
                r13.enforceInterface(r15)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                int r16 = r28.readInt()
                int r17 = r28.readInt()
                int r18 = r28.readInt()
                int r19 = r28.readInt()
                int r20 = r28.readInt()
                java.lang.String r21 = r28.readString()
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r16
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r8 = r21
                java.lang.String r0 = r0.iccTransmitApduBasicChannelBySlot(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                r14.writeString(r0)
                return r12
            L_0x1016:
                r12 = r10
                r13.enforceInterface(r15)
                int r9 = r28.readInt()
                int r10 = r28.readInt()
                int r16 = r28.readInt()
                int r17 = r28.readInt()
                int r18 = r28.readInt()
                int r19 = r28.readInt()
                int r20 = r28.readInt()
                java.lang.String r21 = r28.readString()
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r16
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r8 = r21
                java.lang.String r0 = r0.iccTransmitApduLogicalChannel(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                r14.writeString(r0)
                return r12
            L_0x1055:
                r12 = r10
                r13.enforceInterface(r15)
                int r9 = r28.readInt()
                int r10 = r28.readInt()
                int r16 = r28.readInt()
                int r17 = r28.readInt()
                int r18 = r28.readInt()
                int r19 = r28.readInt()
                int r20 = r28.readInt()
                java.lang.String r21 = r28.readString()
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r16
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r8 = r21
                java.lang.String r0 = r0.iccTransmitApduLogicalChannelBySlot(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                r14.writeString(r0)
                return r12
            L_0x1094:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                boolean r2 = r11.iccCloseLogicalChannel(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x10ab:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                boolean r2 = r11.iccCloseLogicalChannelBySlot(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x10c2:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                java.lang.String r3 = r28.readString()
                int r4 = r28.readInt()
                android.telephony.IccOpenLogicalChannelResponse r5 = r11.iccOpenLogicalChannel(r0, r2, r3, r4)
                r29.writeNoException()
                if (r5 == 0) goto L_0x10e6
                r14.writeInt(r12)
                r5.writeToParcel(r14, r12)
                goto L_0x10e9
            L_0x10e6:
                r14.writeInt(r1)
            L_0x10e9:
                return r12
            L_0x10ea:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                java.lang.String r3 = r28.readString()
                int r4 = r28.readInt()
                android.telephony.IccOpenLogicalChannelResponse r5 = r11.iccOpenLogicalChannelBySlot(r0, r2, r3, r4)
                r29.writeNoException()
                if (r5 == 0) goto L_0x110e
                r14.writeInt(r12)
                r5.writeToParcel(r14, r12)
                goto L_0x1111
            L_0x110e:
                r14.writeInt(r1)
            L_0x1111:
                return r12
            L_0x1112:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.setCellInfoListRate(r0)
                r29.writeNoException()
                return r12
            L_0x1121:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                android.os.IBinder r2 = r28.readStrongBinder()
                android.telephony.ICellInfoCallback r2 = android.telephony.ICellInfoCallback.Stub.asInterface(r2)
                java.lang.String r3 = r28.readString()
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x1144
                android.os.Parcelable$Creator<android.os.WorkSource> r0 = android.os.WorkSource.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.WorkSource r0 = (android.os.WorkSource) r0
                goto L_0x1145
            L_0x1144:
            L_0x1145:
                r11.requestCellInfoUpdateWithWorkSource(r1, r2, r3, r0)
                r29.writeNoException()
                return r12
            L_0x114c:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.os.IBinder r1 = r28.readStrongBinder()
                android.telephony.ICellInfoCallback r1 = android.telephony.ICellInfoCallback.Stub.asInterface(r1)
                java.lang.String r2 = r28.readString()
                r11.requestCellInfoUpdate(r0, r1, r2)
                r29.writeNoException()
                return r12
            L_0x1167:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.util.List r1 = r11.getAllCellInfo(r0)
                r29.writeNoException()
                r14.writeTypedList(r1)
                return r12
            L_0x117a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getLteOnCdmaModeForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x1191:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.getLteOnCdmaMode(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x11a4:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.hasIccCardUsingSlotIndex(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x11b7:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.hasIccCard()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x11c6:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getVoiceNetworkTypeForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x11dd:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getDataNetworkTypeForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x11f4:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.getDataNetworkType(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x1207:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getNetworkTypeForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x121e:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r28.readString()
                r11.sendDialerSpecialCode(r0, r1)
                r29.writeNoException()
                return r12
            L_0x1231:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r7 = r28.readString()
                int r8 = r28.readInt()
                java.lang.String r9 = r28.readString()
                int r10 = r28.readInt()
                java.lang.String r16 = r28.readString()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x1259
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x1257:
                r6 = r0
                goto L_0x125a
            L_0x1259:
                goto L_0x1257
            L_0x125a:
                r0 = r26
                r1 = r7
                r2 = r8
                r3 = r9
                r4 = r10
                r5 = r16
                r0.sendVisualVoicemailSmsForSubscriber(r1, r2, r3, r4, r5, r6)
                r29.writeNoException()
                return r12
            L_0x1269:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                android.telephony.VisualVoicemailSmsFilterSettings r2 = r11.getActiveVisualVoicemailSmsFilterSettings(r0)
                r29.writeNoException()
                if (r2 == 0) goto L_0x1281
                r14.writeInt(r12)
                r2.writeToParcel(r14, r12)
                goto L_0x1284
            L_0x1281:
                r14.writeInt(r1)
            L_0x1284:
                return r12
            L_0x1285:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r2 = r28.readInt()
                android.telephony.VisualVoicemailSmsFilterSettings r3 = r11.getVisualVoicemailSmsFilterSettings(r0, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x12a1
                r14.writeInt(r12)
                r3.writeToParcel(r14, r12)
                goto L_0x12a4
            L_0x12a1:
                r14.writeInt(r1)
            L_0x12a4:
                return r12
            L_0x12a5:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                r11.disableVisualVoicemailSmsFilter(r0, r1)
                return r12
            L_0x12b5:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r1 = r28.readString()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x12d0
                android.os.Parcelable$Creator<android.telephony.VisualVoicemailSmsFilterSettings> r0 = android.telephony.VisualVoicemailSmsFilterSettings.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.telephony.VisualVoicemailSmsFilterSettings r0 = (android.telephony.VisualVoicemailSmsFilterSettings) r0
                goto L_0x12d1
            L_0x12d0:
            L_0x12d1:
                r11.enableVisualVoicemailSmsFilter(r1, r2, r0)
                r29.writeNoException()
                return r12
            L_0x12d8:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                java.lang.String r2 = r11.getVisualVoicemailPackageName(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x12ef:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r2 = r28.readInt()
                android.os.Bundle r3 = r11.getVisualVoicemailSettings(r0, r2)
                r29.writeNoException()
                if (r3 == 0) goto L_0x130b
                r14.writeInt(r12)
                r3.writeToParcel(r14, r12)
                goto L_0x130e
            L_0x130b:
                r14.writeInt(r1)
            L_0x130e:
                return r12
            L_0x130f:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isConcurrentVoiceAndDataAllowed(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x1322:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getVoiceMessageCountForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x1339:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getDataActivationState(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x1350:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getVoiceActivationState(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x1367:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r11.setDataActivationState(r0, r1)
                r29.writeNoException()
                return r12
            L_0x137a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                r11.setVoiceActivationState(r0, r1)
                r29.writeNoException()
                return r12
            L_0x138d:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r28.readString()
                boolean r3 = r11.setVoiceMailNumber(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x13a8:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.needsOtaServiceProvisioning()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x13b7:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r11.getCdmaEriTextForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeString(r2)
                return r12
            L_0x13ce:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r11.getCdmaEriText(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x13e1:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getCdmaEriIconModeForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x13f8:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.getCdmaEriIconMode(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x140b:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r11.getCdmaEriIconIndexForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x1422:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int r1 = r11.getCdmaEriIconIndex(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x1435:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getActivePhoneTypeForSlot(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x1448:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r26.getActivePhoneType()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x1457:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r26.getDataState()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x1466:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r26.getDataActivity()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x1475:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r1 = r11.getCallStateForSlot(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x1488:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r26.getCallState()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x1497:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.util.List r1 = r11.getNeighboringCellInfo(r0)
                r29.writeNoException()
                r14.writeTypedList(r1)
                return r12
            L_0x14aa:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r11.getNetworkCountryIsoForPhone(r0)
                r29.writeNoException()
                r14.writeString(r1)
                return r12
            L_0x14bd:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                android.os.Bundle r2 = r11.getCellLocation(r0)
                r29.writeNoException()
                if (r2 == 0) goto L_0x14d5
                r14.writeInt(r12)
                r2.writeToParcel(r14, r12)
                goto L_0x14d8
            L_0x14d5:
                r14.writeInt(r1)
            L_0x14d8:
                return r12
            L_0x14d9:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                boolean r1 = r11.isDataConnectivityPossible(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x14ec:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.disableDataConnectivity()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x14fb:
                r12 = r10
                r13.enforceInterface(r15)
                boolean r0 = r26.enableDataConnectivity()
                r29.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x150a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.disableLocationUpdatesForSubscriber(r0)
                r29.writeNoException()
                return r12
            L_0x1519:
                r12 = r10
                r13.enforceInterface(r15)
                r26.disableLocationUpdates()
                r29.writeNoException()
                return r12
            L_0x1524:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.enableLocationUpdatesForSubscriber(r0)
                r29.writeNoException()
                return r12
            L_0x1533:
                r12 = r10
                r13.enforceInterface(r15)
                r26.enableLocationUpdates()
                r29.writeNoException()
                return r12
            L_0x153e:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.updateServiceLocationForSubscriber(r0)
                r29.writeNoException()
                return r12
            L_0x154d:
                r12 = r10
                r13.enforceInterface(r15)
                r26.updateServiceLocation()
                r29.writeNoException()
                return r12
            L_0x1558:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x1564
                r1 = r12
            L_0x1564:
                r0 = r1
                boolean r1 = r11.setRadioPower(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x1570:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x1580
                r1 = r12
            L_0x1580:
                boolean r2 = r11.setRadioForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x158b:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x1597
                r1 = r12
            L_0x1597:
                r0 = r1
                boolean r1 = r11.setRadio(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x15a3:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                r11.toggleRadioOnOffForSubscriber(r0)
                r29.writeNoException()
                return r12
            L_0x15b2:
                r12 = r10
                r13.enforceInterface(r15)
                r26.toggleRadioOnOff()
                r29.writeNoException()
                return r12
            L_0x15bd:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.handlePinMmiForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x15d4:
                r12 = r10
                r13.enforceInterface(r15)
                int r1 = r28.readInt()
                java.lang.String r2 = r28.readString()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x15ef
                android.os.Parcelable$Creator<android.os.ResultReceiver> r0 = android.os.ResultReceiver.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                goto L_0x15f0
            L_0x15ef:
            L_0x15f0:
                r11.handleUssdRequest(r1, r2, r0)
                r29.writeNoException()
                return r12
            L_0x15f7:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                boolean r1 = r11.handlePinMmi(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x160a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r28.readString()
                int[] r3 = r11.supplyPukReportResultForSubscriber(r0, r1, r2)
                r29.writeNoException()
                r14.writeIntArray(r3)
                return r12
            L_0x1625:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r28.readString()
                int[] r2 = r11.supplyPukReportResult(r0, r1)
                r29.writeNoException()
                r14.writeIntArray(r2)
                return r12
            L_0x163c:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int[] r2 = r11.supplyPinReportResultForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeIntArray(r2)
                return r12
            L_0x1653:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                int[] r1 = r11.supplyPinReportResult(r0)
                r29.writeNoException()
                r14.writeIntArray(r1)
                return r12
            L_0x1666:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r28.readString()
                boolean r3 = r11.supplyPukForSubscriber(r0, r1, r2)
                r29.writeNoException()
                r14.writeInt(r3)
                return r12
            L_0x1681:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.supplyPuk(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x1698:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.supplyPinForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x16af:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                boolean r1 = r11.supplyPin(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x16c2:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                boolean r2 = r11.isRadioOnForSubscriber(r0, r1)
                r29.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x16d9:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                boolean r1 = r11.isRadioOn(r0)
                r29.writeNoException()
                r14.writeInt(r1)
                return r12
            L_0x16ec:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                java.lang.String r1 = r28.readString()
                r11.call(r0, r1)
                r29.writeNoException()
                return r12
            L_0x16ff:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r28.readString()
                r11.dial(r0)
                r29.writeNoException()
                return r12
            L_0x170e:
                r12 = r10
                r14.writeString(r15)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.ITelephony.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITelephony {
            public static ITelephony sDefaultImpl;
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

            public void dial(String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dial(number);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void call(String callingPackage, String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(number);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().call(callingPackage, number);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRadioOn(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRadioOn(callingPackage);
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

            public boolean isRadioOnForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRadioOnForSubscriber(subId, callingPackage);
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

            public boolean supplyPin(String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pin);
                    boolean z = false;
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPin(pin);
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

            public boolean supplyPinForSubscriber(int subId, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(pin);
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPinForSubscriber(subId, pin);
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

            public boolean supplyPuk(String puk, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPuk(puk, pin);
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

            public boolean supplyPukForSubscriber(int subId, String puk, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPukForSubscriber(subId, puk, pin);
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

            public int[] supplyPinReportResult(String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pin);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPinReportResult(pin);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPinReportResultForSubscriber(int subId, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(pin);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPinReportResultForSubscriber(subId, pin);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPukReportResult(String puk, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPukReportResult(puk, pin);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPukReportResultForSubscriber(int subId, String puk, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().supplyPukReportResultForSubscriber(subId, puk, pin);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean handlePinMmi(String dialString) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dialString);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().handlePinMmi(dialString);
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

            public void handleUssdRequest(int subId, String ussdRequest, ResultReceiver wrappedCallback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(ussdRequest);
                    if (wrappedCallback != null) {
                        _data.writeInt(1);
                        wrappedCallback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleUssdRequest(subId, ussdRequest, wrappedCallback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean handlePinMmiForSubscriber(int subId, String dialString) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(dialString);
                    boolean z = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().handlePinMmiForSubscriber(subId, dialString);
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

            public void toggleRadioOnOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().toggleRadioOnOff();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void toggleRadioOnOffForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().toggleRadioOnOffForSubscriber(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setRadio(boolean turnOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(turnOn);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRadio(turnOn);
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

            public boolean setRadioForSubscriber(int subId, boolean turnOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(turnOn);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRadioForSubscriber(subId, turnOn);
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

            public boolean setRadioPower(boolean turnOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(turnOn);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setRadioPower(turnOn);
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

            public void updateServiceLocation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateServiceLocation();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateServiceLocationForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateServiceLocationForSubscriber(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableLocationUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableLocationUpdates();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableLocationUpdatesForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableLocationUpdatesForSubscriber(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableLocationUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableLocationUpdates();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableLocationUpdatesForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableLocationUpdatesForSubscriber(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean enableDataConnectivity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableDataConnectivity();
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

            public boolean disableDataConnectivity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableDataConnectivity();
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

            public boolean isDataConnectivityPossible(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDataConnectivityPossible(subId);
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

            public Bundle getCellLocation(String callingPkg) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCellLocation(callingPkg);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getNetworkCountryIsoForPhone(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkCountryIsoForPhone(phoneId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<NeighboringCellInfo> getNeighboringCellInfo(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNeighboringCellInfo(callingPkg);
                    }
                    _reply.readException();
                    List<NeighboringCellInfo> _result = _reply.createTypedArrayList(NeighboringCellInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCallState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCallStateForSlot(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallStateForSlot(slotIndex);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataActivity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataActivity();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataState();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getActivePhoneType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivePhoneType();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getActivePhoneTypeForSlot(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivePhoneTypeForSlot(slotIndex);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconIndex(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(39, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaEriIconIndex(callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconIndexForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaEriIconIndexForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconMode(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaEriIconMode(callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconModeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaEriIconModeForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaEriText(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaEriText(callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaEriTextForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaEriTextForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean needsOtaServiceProvisioning() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().needsOtaServiceProvisioning();
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

            public boolean setVoiceMailNumber(int subId, String alphaTag, String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(alphaTag);
                    _data.writeString(number);
                    boolean z = false;
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setVoiceMailNumber(subId, alphaTag, number);
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

            public void setVoiceActivationState(int subId, int activationState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(activationState);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoiceActivationState(subId, activationState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDataActivationState(int subId, int activationState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(activationState);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDataActivationState(subId, activationState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoiceActivationState(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoiceActivationState(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataActivationState(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataActivationState(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoiceMessageCountForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoiceMessageCountForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConcurrentVoiceAndDataAllowed(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConcurrentVoiceAndDataAllowed(subId);
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

            public Bundle getVisualVoicemailSettings(String callingPackage, int subId) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVisualVoicemailSettings(callingPackage, subId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getVisualVoicemailPackageName(String callingPackage, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVisualVoicemailPackageName(callingPackage, subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableVisualVoicemailSmsFilter(String callingPackage, int subId, VisualVoicemailSmsFilterSettings settings) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(subId);
                    if (settings != null) {
                        _data.writeInt(1);
                        settings.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableVisualVoicemailSmsFilter(callingPackage, subId, settings);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableVisualVoicemailSmsFilter(String callingPackage, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(56, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disableVisualVoicemailSmsFilter(callingPackage, subId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(String callingPackage, int subId) throws RemoteException {
                VisualVoicemailSmsFilterSettings _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVisualVoicemailSmsFilterSettings(callingPackage, subId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VisualVoicemailSmsFilterSettings.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    VisualVoicemailSmsFilterSettings _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int subId) throws RemoteException {
                VisualVoicemailSmsFilterSettings _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveVisualVoicemailSmsFilterSettings(subId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = VisualVoicemailSmsFilterSettings.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    VisualVoicemailSmsFilterSettings _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendVisualVoicemailSmsForSubscriber(String callingPackage, int subId, String number, int port, String text, PendingIntent sentIntent) throws RemoteException {
                PendingIntent pendingIntent = sentIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPackage);
                        try {
                            _data.writeInt(subId);
                        } catch (Throwable th) {
                            th = th;
                            String str = number;
                            int i = port;
                            String str2 = text;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(number);
                        } catch (Throwable th2) {
                            th = th2;
                            int i2 = port;
                            String str22 = text;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i3 = subId;
                        String str3 = number;
                        int i22 = port;
                        String str222 = text;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(port);
                        try {
                            _data.writeString(text);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendVisualVoicemailSmsForSubscriber(callingPackage, subId, number, port, text, sentIntent);
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
                        String str2222 = text;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    String str4 = callingPackage;
                    int i32 = subId;
                    String str32 = number;
                    int i222 = port;
                    String str22222 = text;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendDialerSpecialCode(String callingPackageName, String inputCode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackageName);
                    _data.writeString(inputCode);
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendDialerSpecialCode(callingPackageName, inputCode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(61, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkTypeForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataNetworkType(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataNetworkType(callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(63, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataNetworkTypeForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoiceNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(64, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoiceNetworkTypeForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasIccCard() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(65, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasIccCard();
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

            public boolean hasIccCardUsingSlotIndex(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    boolean z = false;
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasIccCardUsingSlotIndex(slotIndex);
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

            public int getLteOnCdmaMode(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLteOnCdmaMode(callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLteOnCdmaModeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(68, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLteOnCdmaModeForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<CellInfo> getAllCellInfo(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllCellInfo(callingPkg);
                    }
                    _reply.readException();
                    List<CellInfo> _result = _reply.createTypedArrayList(CellInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestCellInfoUpdate(int subId, ICellInfoCallback cb, String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    _data.writeString(callingPkg);
                    if (this.mRemote.transact(70, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestCellInfoUpdate(subId, cb, callingPkg);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestCellInfoUpdateWithWorkSource(int subId, ICellInfoCallback cb, String callingPkg, WorkSource ws) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    _data.writeString(callingPkg);
                    if (ws != null) {
                        _data.writeInt(1);
                        ws.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestCellInfoUpdateWithWorkSource(subId, cb, callingPkg, ws);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCellInfoListRate(int rateInMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rateInMillis);
                    if (this.mRemote.transact(72, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setCellInfoListRate(rateInMillis);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int slotIndex, String callingPackage, String AID, int p2) throws RemoteException {
                IccOpenLogicalChannelResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(callingPackage);
                    _data.writeString(AID);
                    _data.writeInt(p2);
                    if (!this.mRemote.transact(73, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().iccOpenLogicalChannelBySlot(slotIndex, callingPackage, AID, p2);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IccOpenLogicalChannelResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IccOpenLogicalChannelResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IccOpenLogicalChannelResponse iccOpenLogicalChannel(int subId, String callingPackage, String AID, int p2) throws RemoteException {
                IccOpenLogicalChannelResponse _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    _data.writeString(AID);
                    _data.writeInt(p2);
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().iccOpenLogicalChannel(subId, callingPackage, AID, p2);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IccOpenLogicalChannelResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IccOpenLogicalChannelResponse _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean iccCloseLogicalChannelBySlot(int slotIndex, int channel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeInt(channel);
                    boolean z = false;
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().iccCloseLogicalChannelBySlot(slotIndex, channel);
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

            public boolean iccCloseLogicalChannel(int subId, int channel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(channel);
                    boolean z = false;
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().iccCloseLogicalChannel(subId, channel);
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

            public String iccTransmitApduLogicalChannelBySlot(int slotIndex, int channel, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(slotIndex);
                        try {
                            _data.writeInt(channel);
                        } catch (Throwable th) {
                            th = th;
                            int i = cla;
                            int i2 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(cla);
                            try {
                                _data.writeInt(instruction);
                                _data.writeInt(p1);
                                _data.writeInt(p2);
                                _data.writeInt(p3);
                                _data.writeString(data);
                                if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    String _result = _reply.readString();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                String iccTransmitApduLogicalChannelBySlot = Stub.getDefaultImpl().iccTransmitApduLogicalChannelBySlot(slotIndex, channel, cla, instruction, p1, p2, p3, data);
                                _reply.recycle();
                                _data.recycle();
                                return iccTransmitApduLogicalChannelBySlot;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i3 = channel;
                        int i4 = cla;
                        int i222 = instruction;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i5 = slotIndex;
                    int i32 = channel;
                    int i42 = cla;
                    int i2222 = instruction;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String iccTransmitApduLogicalChannel(int subId, int channel, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeInt(channel);
                        } catch (Throwable th) {
                            th = th;
                            int i = cla;
                            int i2 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(cla);
                            try {
                                _data.writeInt(instruction);
                                _data.writeInt(p1);
                                _data.writeInt(p2);
                                _data.writeInt(p3);
                                _data.writeString(data);
                                if (this.mRemote.transact(78, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    String _result = _reply.readString();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                String iccTransmitApduLogicalChannel = Stub.getDefaultImpl().iccTransmitApduLogicalChannel(subId, channel, cla, instruction, p1, p2, p3, data);
                                _reply.recycle();
                                _data.recycle();
                                return iccTransmitApduLogicalChannel;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i3 = channel;
                        int i4 = cla;
                        int i222 = instruction;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i5 = subId;
                    int i32 = channel;
                    int i42 = cla;
                    int i2222 = instruction;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String iccTransmitApduBasicChannelBySlot(int slotIndex, String callingPackage, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(slotIndex);
                        try {
                            _data.writeString(callingPackage);
                        } catch (Throwable th) {
                            th = th;
                            int i = cla;
                            int i2 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(cla);
                            try {
                                _data.writeInt(instruction);
                                _data.writeInt(p1);
                                _data.writeInt(p2);
                                _data.writeInt(p3);
                                _data.writeString(data);
                                if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    String _result = _reply.readString();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                String iccTransmitApduBasicChannelBySlot = Stub.getDefaultImpl().iccTransmitApduBasicChannelBySlot(slotIndex, callingPackage, cla, instruction, p1, p2, p3, data);
                                _reply.recycle();
                                _data.recycle();
                                return iccTransmitApduBasicChannelBySlot;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str = callingPackage;
                        int i3 = cla;
                        int i222 = instruction;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i4 = slotIndex;
                    String str2 = callingPackage;
                    int i32 = cla;
                    int i2222 = instruction;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String iccTransmitApduBasicChannel(int subId, String callingPackage, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPackage);
                        } catch (Throwable th) {
                            th = th;
                            int i = cla;
                            int i2 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(cla);
                            try {
                                _data.writeInt(instruction);
                                _data.writeInt(p1);
                                _data.writeInt(p2);
                                _data.writeInt(p3);
                                _data.writeString(data);
                                if (this.mRemote.transact(80, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    String _result = _reply.readString();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                String iccTransmitApduBasicChannel = Stub.getDefaultImpl().iccTransmitApduBasicChannel(subId, callingPackage, cla, instruction, p1, p2, p3, data);
                                _reply.recycle();
                                _data.recycle();
                                return iccTransmitApduBasicChannel;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i22 = instruction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str = callingPackage;
                        int i3 = cla;
                        int i222 = instruction;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i4 = subId;
                    String str2 = callingPackage;
                    int i32 = cla;
                    int i2222 = instruction;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public byte[] iccExchangeSimIO(int subId, int fileID, int command, int p1, int p2, int p3, String filePath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeInt(fileID);
                        } catch (Throwable th) {
                            th = th;
                            int i = command;
                            int i2 = p1;
                            int i3 = p2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(command);
                            try {
                                _data.writeInt(p1);
                            } catch (Throwable th2) {
                                th = th2;
                                int i32 = p2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeInt(p2);
                                _data.writeInt(p3);
                                _data.writeString(filePath);
                                if (this.mRemote.transact(81, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    byte[] _result = _reply.createByteArray();
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                byte[] iccExchangeSimIO = Stub.getDefaultImpl().iccExchangeSimIO(subId, fileID, command, p1, p2, p3, filePath);
                                _reply.recycle();
                                _data.recycle();
                                return iccExchangeSimIO;
                            } catch (Throwable th3) {
                                th = th3;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            int i22 = p1;
                            int i322 = p2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i4 = fileID;
                        int i5 = command;
                        int i222 = p1;
                        int i3222 = p2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i6 = subId;
                    int i42 = fileID;
                    int i52 = command;
                    int i2222 = p1;
                    int i32222 = p2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String sendEnvelopeWithStatus(int subId, String content) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(content);
                    if (!this.mRemote.transact(82, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().sendEnvelopeWithStatus(subId, content);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String nvReadItem(int itemID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(itemID);
                    if (!this.mRemote.transact(83, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().nvReadItem(itemID);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean nvWriteItem(int itemID, String itemValue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(itemID);
                    _data.writeString(itemValue);
                    boolean z = false;
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().nvWriteItem(itemID, itemValue);
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

            public boolean nvWriteCdmaPrl(byte[] preferredRoamingList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(preferredRoamingList);
                    boolean z = false;
                    if (!this.mRemote.transact(85, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().nvWriteCdmaPrl(preferredRoamingList);
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

            public boolean resetModemConfig(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    boolean z = false;
                    if (!this.mRemote.transact(86, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetModemConfig(slotIndex);
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

            public boolean rebootModem(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    boolean z = false;
                    if (!this.mRemote.transact(87, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().rebootModem(slotIndex);
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

            public int getCalculatedPreferredNetworkType(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(88, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCalculatedPreferredNetworkType(callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPreferredNetworkType(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(89, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreferredNetworkType(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getTetherApnRequiredForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(90, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTetherApnRequiredForSubscriber(subId);
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

            public void enableIms(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(91, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableIms(slotId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableIms(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableIms(slotId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsMmTelFeature getMmTelFeatureAndListen(int slotId, IImsServiceFeatureCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(93, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMmTelFeatureAndListen(slotId, callback);
                    }
                    _reply.readException();
                    IImsMmTelFeature _result = IImsMmTelFeature.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsRcsFeature getRcsFeatureAndListen(int slotId, IImsServiceFeatureCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(94, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRcsFeatureAndListen(slotId, callback);
                    }
                    _reply.readException();
                    IImsRcsFeature _result = IImsRcsFeature.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsRegistration getImsRegistration(int slotId, int feature) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(feature);
                    if (!this.mRemote.transact(95, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsRegistration(slotId, feature);
                    }
                    _reply.readException();
                    IImsRegistration _result = IImsRegistration.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IImsConfig getImsConfig(int slotId, int feature) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(feature);
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsConfig(slotId, feature);
                    }
                    _reply.readException();
                    IImsConfig _result = IImsConfig.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setImsService(int slotId, boolean isCarrierImsService, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(isCarrierImsService);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setImsService(slotId, isCarrierImsService, packageName);
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

            public String getImsService(int slotId, boolean isCarrierImsService) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(isCarrierImsService);
                    if (!this.mRemote.transact(98, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsService(slotId, isCarrierImsService);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNetworkSelectionModeAutomatic(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(99, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNetworkSelectionModeAutomatic(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CellNetworkScanResult getCellNetworkScanResults(int subId, String callingPackage) throws RemoteException {
                CellNetworkScanResult _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(100, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCellNetworkScanResults(subId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = CellNetworkScanResult.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CellNetworkScanResult _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int requestNetworkScan(int subId, NetworkScanRequest request, Messenger messenger, IBinder binder, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (messenger != null) {
                        _data.writeInt(1);
                        messenger.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(binder);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(101, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestNetworkScan(subId, request, messenger, binder, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopNetworkScan(int subId, int scanId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(scanId);
                    if (this.mRemote.transact(102, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopNetworkScan(subId, scanId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setNetworkSelectionModeManual(int subId, OperatorInfo operatorInfo, boolean persisSelection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean _result = true;
                    if (operatorInfo != null) {
                        _data.writeInt(1);
                        operatorInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(persisSelection);
                    if (!this.mRemote.transact(103, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setNetworkSelectionModeManual(subId, operatorInfo, persisSelection);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPreferredNetworkType(int subId, int networkType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(networkType);
                    boolean z = false;
                    if (!this.mRemote.transact(104, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPreferredNetworkType(subId, networkType);
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

            public void setUserDataEnabled(int subId, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(105, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserDataEnabled(subId, enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getDataEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(106, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDataEnabled(subId);
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

            public boolean isUserDataEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(107, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserDataEnabled(subId);
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

            public boolean isDataEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(108, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDataEnabled(subId);
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

            public boolean isManualNetworkSelectionAllowed(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(109, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isManualNetworkSelectionAllowed(subId);
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

            public String[] getPcscfAddress(String apnType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(apnType);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(110, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPcscfAddress(apnType, callingPackage);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setImsRegistrationState(boolean registered) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(registered);
                    if (this.mRemote.transact(111, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setImsRegistrationState(registered);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaMdn(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(112, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaMdn(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaMin(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(113, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaMin(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestNumberVerification(PhoneNumberRange range, long timeoutMillis, INumberVerificationCallback callback, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (range != null) {
                        _data.writeInt(1);
                        range.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(timeoutMillis);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(114, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestNumberVerification(range, timeoutMillis, callback, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCarrierPrivilegeStatus(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(115, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCarrierPrivilegeStatus(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCarrierPrivilegeStatusForUid(int subId, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(116, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCarrierPrivilegeStatusForUid(subId, uid);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkCarrierPrivilegesForPackage(int subId, String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(pkgName);
                    if (!this.mRemote.transact(117, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkCarrierPrivilegesForPackage(subId, pkgName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkCarrierPrivilegesForPackageAnyPhone(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    if (!this.mRemote.transact(118, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkCarrierPrivilegesForPackageAnyPhone(pkgName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(phoneId);
                    if (!this.mRemote.transact(119, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCarrierPackageNamesForIntentAndPhone(intent, phoneId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setLine1NumberForDisplayForSubscriber(int subId, String alphaTag, String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(alphaTag);
                    _data.writeString(number);
                    boolean z = false;
                    if (!this.mRemote.transact(120, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setLine1NumberForDisplayForSubscriber(subId, alphaTag, number);
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

            public String getLine1NumberForDisplay(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(121, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLine1NumberForDisplay(subId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getLine1AlphaTagForDisplay(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(122, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLine1AlphaTagForDisplay(subId, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getMergedSubscriberIds(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(123, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMergedSubscriberIds(subId, callingPackage);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setOperatorBrandOverride(int subId, String brand) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(brand);
                    boolean z = false;
                    if (!this.mRemote.transact(124, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setOperatorBrandOverride(subId, brand);
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

            public boolean setRoamingOverride(int subId, List<String> gsmRoamingList, List<String> gsmNonRoamingList, List<String> cdmaRoamingList, List<String> cdmaNonRoamingList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeStringList(gsmRoamingList);
                            try {
                                _data.writeStringList(gsmNonRoamingList);
                                try {
                                    _data.writeStringList(cdmaRoamingList);
                                } catch (Throwable th) {
                                    th = th;
                                    List<String> list = cdmaNonRoamingList;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                List<String> list2 = cdmaRoamingList;
                                List<String> list3 = cdmaNonRoamingList;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            List<String> list4 = gsmNonRoamingList;
                            List<String> list22 = cdmaRoamingList;
                            List<String> list32 = cdmaNonRoamingList;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeStringList(cdmaNonRoamingList);
                            try {
                                boolean z = false;
                                if (this.mRemote.transact(125, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        z = true;
                                    }
                                    boolean _result = z;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean roamingOverride = Stub.getDefaultImpl().setRoamingOverride(subId, gsmRoamingList, gsmNonRoamingList, cdmaRoamingList, cdmaNonRoamingList);
                                _reply.recycle();
                                _data.recycle();
                                return roamingOverride;
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        List<String> list5 = gsmRoamingList;
                        List<String> list42 = gsmNonRoamingList;
                        List<String> list222 = cdmaRoamingList;
                        List<String> list322 = cdmaNonRoamingList;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i = subId;
                    List<String> list52 = gsmRoamingList;
                    List<String> list422 = gsmNonRoamingList;
                    List<String> list2222 = cdmaRoamingList;
                    List<String> list3222 = cdmaNonRoamingList;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int invokeOemRilRequestRaw(byte[] oemReq, byte[] oemResp) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(oemReq);
                    if (oemResp == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(oemResp.length);
                    }
                    if (!this.mRemote.transact(126, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().invokeOemRilRequestRaw(oemReq, oemResp);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(oemResp);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean needMobileRadioShutdown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(127, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().needMobileRadioShutdown();
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

            public void shutdownMobileRadios() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(128, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().shutdownMobileRadios();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRadioCapability(RadioAccessFamily[] rafs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(rafs, 0);
                    if (this.mRemote.transact(129, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRadioCapability(rafs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRadioAccessFamily(int phoneId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(130, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRadioAccessFamily(phoneId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableVideoCalling(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    if (this.mRemote.transact(131, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableVideoCalling(enable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVideoCallingEnabled(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(132, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVideoCallingEnabled(callingPackage);
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

            public boolean canChangeDtmfToneLength(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(133, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canChangeDtmfToneLength(subId, callingPackage);
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

            public boolean isWorldPhone(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(134, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWorldPhone(subId, callingPackage);
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

            public boolean isTtyModeSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(135, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTtyModeSupported();
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

            public boolean isRttSupported(int subscriptionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subscriptionId);
                    boolean z = false;
                    if (!this.mRemote.transact(136, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRttSupported(subscriptionId);
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

            public boolean isHearingAidCompatibilitySupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(137, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHearingAidCompatibilitySupported();
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

            public boolean isImsRegistered(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(138, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isImsRegistered(subId);
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

            public boolean isWifiCallingAvailable(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(139, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isWifiCallingAvailable(subId);
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

            public boolean isVideoTelephonyAvailable(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(140, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVideoTelephonyAvailable(subId);
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

            public int getImsRegTechnologyForMmTel(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(141, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsRegTechnologyForMmTel(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDeviceId(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(142, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceId(callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getImeiForSlot(int slotIndex, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(143, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImeiForSlot(slotIndex, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getTypeAllocationCodeForSlot(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    if (!this.mRemote.transact(144, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTypeAllocationCodeForSlot(slotIndex);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getMeidForSlot(int slotIndex, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(145, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMeidForSlot(slotIndex, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getManufacturerCodeForSlot(int slotIndex) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    if (!this.mRemote.transact(146, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getManufacturerCodeForSlot(slotIndex);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDeviceSoftwareVersionForSlot(int slotIndex, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(147, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDeviceSoftwareVersionForSlot(slotIndex, callingPackage);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccount != null) {
                        _data.writeInt(1);
                        phoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(148, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubIdForPhoneAccount(phoneAccount);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int subscriptionId) throws RemoteException {
                PhoneAccountHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subscriptionId);
                    if (!this.mRemote.transact(149, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPhoneAccountHandleForSubscriptionId(subscriptionId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PhoneAccountHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PhoneAccountHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void factoryReset(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(150, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().factoryReset(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getSimLocaleForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(151, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSimLocaleForSubscriber(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestModemActivityInfo(ResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(152, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestModemActivityInfo(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public ServiceState getServiceStateForSubscriber(int subId, String callingPackage) throws RemoteException {
                ServiceState _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(153, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServiceStateForSubscriber(subId, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ServiceState.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ServiceState _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri getVoicemailRingtoneUri(PhoneAccountHandle accountHandle) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(154, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoicemailRingtoneUri(accountHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVoicemailRingtoneUri(String callingPackage, PhoneAccountHandle phoneAccountHandle, Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (phoneAccountHandle != null) {
                        _data.writeInt(1);
                        phoneAccountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(155, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoicemailRingtoneUri(callingPackage, phoneAccountHandle, uri);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVoicemailVibrationEnabled(PhoneAccountHandle accountHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(156, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVoicemailVibrationEnabled(accountHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVoicemailVibrationEnabled(String callingPackage, PhoneAccountHandle phoneAccountHandle, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (phoneAccountHandle != null) {
                        _data.writeInt(1);
                        phoneAccountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(157, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoicemailVibrationEnabled(callingPackage, phoneAccountHandle, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getPackagesWithCarrierPrivileges(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    if (!this.mRemote.transact(158, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesWithCarrierPrivileges(phoneId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getPackagesWithCarrierPrivilegesForAllPhones() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(159, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesWithCarrierPrivilegesForAllPhones();
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getAidForAppType(int subId, int appType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(appType);
                    if (!this.mRemote.transact(160, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAidForAppType(subId, appType);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getEsn(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(161, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEsn(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaPrlVersion(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(162, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaPrlVersion(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<TelephonyHistogram> getTelephonyHistograms() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(163, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTelephonyHistograms();
                    }
                    _reply.readException();
                    List<TelephonyHistogram> _result = _reply.createTypedArrayList(TelephonyHistogram.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setAllowedCarriers(CarrierRestrictionRules carrierRestrictionRules) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (carrierRestrictionRules != null) {
                        _data.writeInt(1);
                        carrierRestrictionRules.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(164, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAllowedCarriers(carrierRestrictionRules);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CarrierRestrictionRules getAllowedCarriers() throws RemoteException {
                CarrierRestrictionRules _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(165, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllowedCarriers();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = CarrierRestrictionRules.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    CarrierRestrictionRules _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSubscriptionCarrierId(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(166, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubscriptionCarrierId(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getSubscriptionCarrierName(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(167, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubscriptionCarrierName(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSubscriptionSpecificCarrierId(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(168, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubscriptionSpecificCarrierId(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getSubscriptionSpecificCarrierName(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(169, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubscriptionSpecificCarrierName(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCarrierIdFromMccMnc(int slotIndex, String mccmnc, boolean isSubscriptionMccMnc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(mccmnc);
                    _data.writeInt(isSubscriptionMccMnc);
                    if (!this.mRemote.transact(170, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCarrierIdFromMccMnc(slotIndex, mccmnc, isSubscriptionMccMnc);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void carrierActionSetMeteredApnsEnabled(int subId, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(171, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().carrierActionSetMeteredApnsEnabled(subId, visible);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void carrierActionSetRadioEnabled(int subId, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(172, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().carrierActionSetRadioEnabled(subId, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void carrierActionReportDefaultNetworkStatus(int subId, boolean report) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(report);
                    if (this.mRemote.transact(173, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().carrierActionReportDefaultNetworkStatus(subId, report);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void carrierActionResetAll(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(174, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().carrierActionResetAll(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkStats getVtDataUsage(int subId, boolean perUidStats) throws RemoteException {
                NetworkStats _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(perUidStats);
                    if (!this.mRemote.transact(175, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVtDataUsage(subId, perUidStats);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkStats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkStats _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPolicyDataEnabled(boolean enabled, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(176, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPolicyDataEnabled(enabled, subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<ClientRequestStats> getClientRequestStats(String callingPackage, int subid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(subid);
                    if (!this.mRemote.transact(177, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getClientRequestStats(callingPackage, subid);
                    }
                    _reply.readException();
                    List<ClientRequestStats> _result = _reply.createTypedArrayList(ClientRequestStats.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSimPowerStateForSlot(int slotIndex, int state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeInt(state);
                    if (this.mRemote.transact(178, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSimPowerStateForSlot(slotIndex, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getForbiddenPlmns(int subId, int appType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(appType);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(179, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getForbiddenPlmns(subId, appType, callingPackage);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getEmergencyCallbackMode(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(180, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEmergencyCallbackMode(subId);
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

            public SignalStrength getSignalStrength(int subId) throws RemoteException {
                SignalStrength _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(181, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSignalStrength(subId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SignalStrength.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SignalStrength _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCardIdForDefaultEuicc(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(182, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCardIdForDefaultEuicc(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<UiccCardInfo> getUiccCardsInfo(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(183, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUiccCardsInfo(callingPackage);
                    }
                    _reply.readException();
                    List<UiccCardInfo> _result = _reply.createTypedArrayList(UiccCardInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UiccSlotInfo[] getUiccSlotsInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(184, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUiccSlotsInfo();
                    }
                    _reply.readException();
                    UiccSlotInfo[] _result = (UiccSlotInfo[]) _reply.createTypedArray(UiccSlotInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean switchSlots(int[] physicalSlots) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(physicalSlots);
                    boolean z = false;
                    if (!this.mRemote.transact(185, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().switchSlots(physicalSlots);
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

            public void setRadioIndicationUpdateMode(int subId, int filters, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(filters);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(186, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRadioIndicationUpdateMode(subId, filters, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isDataRoamingEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(187, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDataRoamingEnabled(subId);
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

            public void setDataRoamingEnabled(int subId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(188, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDataRoamingEnabled(subId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaRoamingMode(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(189, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCdmaRoamingMode(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setCdmaRoamingMode(int subId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(mode);
                    boolean z = false;
                    if (!this.mRemote.transact(190, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setCdmaRoamingMode(subId, mode);
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

            public boolean setCdmaSubscriptionMode(int subId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(mode);
                    boolean z = false;
                    if (!this.mRemote.transact(191, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setCdmaSubscriptionMode(subId, mode);
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

            public void setCarrierTestOverride(int subId, String mccmnc, String imsi, String iccid, String gid1, String gid2, String plmn, String spn, String carrierPrivilegeRules, String apn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(mccmnc);
                            _data.writeString(imsi);
                            _data.writeString(iccid);
                            _data.writeString(gid1);
                            _data.writeString(gid2);
                            _data.writeString(plmn);
                            _data.writeString(spn);
                            _data.writeString(carrierPrivilegeRules);
                            _data.writeString(apn);
                            if (this.mRemote.transact(192, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setCarrierTestOverride(subId, mccmnc, imsi, iccid, gid1, gid2, plmn, spn, carrierPrivilegeRules, apn);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = mccmnc;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = subId;
                    String str2 = mccmnc;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int getCarrierIdListVersion(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(193, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCarrierIdListVersion(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void refreshUiccProfile(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (this.mRemote.transact(194, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().refreshUiccProfile(subId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNumberOfModemsWithSimultaneousDataConnections(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(195, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNumberOfModemsWithSimultaneousDataConnections(subId, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNetworkSelectionMode(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(196, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkSelectionMode(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInEmergencySmsMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(197, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInEmergencySmsMode();
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

            public String[] getSmsApps(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(198, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSmsApps(userId);
                    }
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDefaultSmsApp(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(199, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultSmsApp(userId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDefaultSmsApp(int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(200, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDefaultSmsApp(userId, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRadioPowerState(int slotIndex, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(201, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRadioPowerState(slotIndex, callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerImsRegistrationCallback(int subId, IImsRegistrationCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(202, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerImsRegistrationCallback(subId, c);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterImsRegistrationCallback(int subId, IImsRegistrationCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(203, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterImsRegistrationCallback(subId, c);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerMmTelCapabilityCallback(int subId, IImsCapabilityCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(204, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerMmTelCapabilityCallback(subId, c);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterMmTelCapabilityCallback(int subId, IImsCapabilityCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (this.mRemote.transact(205, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterMmTelCapabilityCallback(subId, c);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isCapable(int subId, int capability, int regTech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(capability);
                    _data.writeInt(regTech);
                    boolean z = false;
                    if (!this.mRemote.transact(206, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCapable(subId, capability, regTech);
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

            public boolean isAvailable(int subId, int capability, int regTech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(capability);
                    _data.writeInt(regTech);
                    boolean z = false;
                    if (!this.mRemote.transact(207, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAvailable(subId, capability, regTech);
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

            public boolean isAdvancedCallingSettingEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(208, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAdvancedCallingSettingEnabled(subId);
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

            public void setAdvancedCallingSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(209, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAdvancedCallingSettingEnabled(subId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVtSettingEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(210, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVtSettingEnabled(subId);
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

            public void setVtSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(211, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVtSettingEnabled(subId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVoWiFiSettingEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(212, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVoWiFiSettingEnabled(subId);
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

            public void setVoWiFiSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(213, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoWiFiSettingEnabled(subId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVoWiFiRoamingSettingEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(214, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVoWiFiRoamingSettingEnabled(subId);
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

            public void setVoWiFiRoamingSettingEnabled(int subId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(215, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoWiFiRoamingSettingEnabled(subId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVoWiFiNonPersistent(int subId, boolean isCapable, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isCapable);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(216, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoWiFiNonPersistent(subId, isCapable, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoWiFiModeSetting(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(217, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoWiFiModeSetting(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVoWiFiModeSetting(int subId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(218, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoWiFiModeSetting(subId, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoWiFiRoamingModeSetting(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(219, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoWiFiRoamingModeSetting(subId);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVoWiFiRoamingModeSetting(int subId, int mode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(mode);
                    if (this.mRemote.transact(220, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVoWiFiRoamingModeSetting(subId, mode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRttCapabilitySetting(int subId, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(isEnabled);
                    if (this.mRemote.transact(221, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRttCapabilitySetting(subId, isEnabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTtyOverVolteEnabled(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(222, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTtyOverVolteEnabled(subId);
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

            public Map getEmergencyNumberList(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(223, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEmergencyNumberList(callingPackage);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isEmergencyNumber(String number, boolean exactMatch) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    _data.writeInt(exactMatch);
                    boolean z = false;
                    if (!this.mRemote.transact(224, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isEmergencyNumber(number, exactMatch);
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

            public List<String> getCertsFromCarrierPrivilegeAccessRules(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(225, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCertsFromCarrierPrivilegeAccessRules(subId);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerImsProvisioningChangedCallback(int subId, IImsConfigCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(226, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerImsProvisioningChangedCallback(subId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterImsProvisioningChangedCallback(int subId, IImsConfigCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(227, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterImsProvisioningChangedCallback(subId, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setImsProvisioningStatusForCapability(int subId, int capability, int tech, boolean isProvisioned) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(capability);
                    _data.writeInt(tech);
                    _data.writeInt(isProvisioned);
                    if (this.mRemote.transact(228, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setImsProvisioningStatusForCapability(subId, capability, tech, isProvisioned);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getImsProvisioningStatusForCapability(int subId, int capability, int tech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(capability);
                    _data.writeInt(tech);
                    boolean z = false;
                    if (!this.mRemote.transact(229, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsProvisioningStatusForCapability(subId, capability, tech);
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

            public boolean isMmTelCapabilityProvisionedInCache(int subId, int capability, int tech) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(capability);
                    _data.writeInt(tech);
                    boolean z = false;
                    if (!this.mRemote.transact(230, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMmTelCapabilityProvisionedInCache(subId, capability, tech);
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

            public void cacheMmTelCapabilityProvisioning(int subId, int capability, int tech, boolean isProvisioned) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(capability);
                    _data.writeInt(tech);
                    _data.writeInt(isProvisioned);
                    if (this.mRemote.transact(231, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cacheMmTelCapabilityProvisioning(subId, capability, tech, isProvisioned);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getImsProvisioningInt(int subId, int key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(key);
                    if (!this.mRemote.transact(232, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsProvisioningInt(subId, key);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getImsProvisioningString(int subId, int key) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(key);
                    if (!this.mRemote.transact(233, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsProvisioningString(subId, key);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setImsProvisioningInt(int subId, int key, int value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(key);
                    _data.writeInt(value);
                    if (!this.mRemote.transact(234, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setImsProvisioningInt(subId, key, value);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setImsProvisioningString(int subId, int key, String value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(key);
                    _data.writeString(value);
                    if (!this.mRemote.transact(235, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setImsProvisioningString(subId, key, value);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateEmergencyNumberListTestMode(int action, EmergencyNumber num) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(action);
                    if (num != null) {
                        _data.writeInt(1);
                        num.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(236, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateEmergencyNumberListTestMode(action, num);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getEmergencyNumberListTestMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(237, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEmergencyNumberListTestMode();
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean enableModemForSlot(int slotIndex, boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeInt(enable);
                    boolean z = false;
                    if (!this.mRemote.transact(238, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableModemForSlot(slotIndex, enable);
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

            public void setMultiSimCarrierRestriction(boolean isMultiSimCarrierRestricted) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isMultiSimCarrierRestricted);
                    if (this.mRemote.transact(239, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMultiSimCarrierRestriction(isMultiSimCarrierRestricted);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int isMultiSimSupported(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(240, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMultiSimSupported(callingPackage);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void switchMultiSimConfig(int numOfSims) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(numOfSims);
                    if (this.mRemote.transact(241, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().switchMultiSimConfig(numOfSims);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean doesSwitchMultiSimConfigTriggerReboot(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(242, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().doesSwitchMultiSimConfigTriggerReboot(subId, callingPackage);
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

            public int[] getSlotsMapping() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(243, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSlotsMapping();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRadioHalVersion() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(244, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRadioHalVersion();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isModemEnabledForSlot(int slotIndex, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(245, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isModemEnabledForSlot(slotIndex, callingPackage);
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

            public boolean isDataEnabledForApn(int apnType, int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(apnType);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(246, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDataEnabledForApn(apnType, subId, callingPackage);
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

            public boolean isApnMetered(int apnType, int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(apnType);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(247, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isApnMetered(apnType, subId);
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

            public void enqueueSmsPickResult(String callingPackage, IIntegerConsumer subIdResult) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(subIdResult != null ? subIdResult.asBinder() : null);
                    if (this.mRemote.transact(248, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().enqueueSmsPickResult(callingPackage, subIdResult);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public String getMmsUserAgent(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(249, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMmsUserAgent(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getMmsUAProfUrl(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(250, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMmsUAProfUrl(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setDataAllowedDuringVoiceCall(int subId, boolean allow) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(allow);
                    boolean z = false;
                    if (!this.mRemote.transact(251, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDataAllowedDuringVoiceCall(subId, allow);
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

            public boolean isDataAllowedInVoiceCall(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(252, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDataAllowedInVoiceCall(subId);
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

        public static boolean setDefaultImpl(ITelephony impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITelephony getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
