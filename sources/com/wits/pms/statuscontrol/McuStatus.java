package com.wits.pms.statuscontrol;

import android.telephony.SmsManager;
import com.google.gson.Gson;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.wits.pms.mcu.custom.KswMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.mozilla.universalchardet.UniversalDetector;

/* loaded from: classes2.dex */
public class McuStatus {
    public static final int ANDROID_MODE = 1;
    public static final int CAR_MODE = 2;
    public static final int TYPE_MCU_STATUS = 5;
    public String mcuVerison;
    public int systemMode;
    public CarData carData = new CarData();
    public ACData acData = new ACData();
    public BenzData benzData = new BenzData();
    public MediaStringInfo mediaStringInfo = new MediaStringInfo();
    public MediaPlayStatus mediaPlayStatus = new MediaPlayStatus();
    public CarBluetoothStatus bluetoothStatus = new CarBluetoothStatus();
    public MediaData mediaData = new MediaData();
    public DiscStatus discStatus = new DiscStatus();
    public EqData eqData = new EqData();

    /* loaded from: classes2.dex */
    public static final class MediaType {
        public static final int SRC_ALL_APP = 13;
        public static final int SRC_AUX = 6;
        public static final int SRC_BT = 3;
        public static final int SRC_BT_MUSIC = 4;
        public static final int SRC_CAR = 0;
        public static final int SRC_CAR_FM = 14;
        public static final int SRC_DTV = 9;
        public static final int SRC_DVD = 8;
        public static final int SRC_DVD_YUV = 12;
        public static final int SRC_DVR = 5;
        public static final int SRC_F_CAM = 11;
        public static final int SRC_MUSIC = 1;
        public static final int SRC_PHONELINK = 7;
        public static final int SRC_RADIO = 10;
        public static final int SRC_VIDEO = 2;
    }

    public McuStatus() {
    }

    public McuStatus(int systemMode, String mcuVerison) {
        this.systemMode = systemMode;
        this.mcuVerison = mcuVerison;
    }

    public static McuStatus getStatusFromJson(String jsonArg) {
        return (McuStatus) new Gson().fromJson(jsonArg, (Class<Object>) McuStatus.class);
    }

    public List<String> compare(McuStatus mcuStatus) {
        List<String> keys = new ArrayList<>();
        if (this.systemMode != mcuStatus.systemMode) {
            keys.add("systemMode");
        }
        if (this.carData.safetyBelt != mcuStatus.carData.safetyBelt) {
            keys.add("safetyBelt");
        }
        if (this.carData.handbrake != mcuStatus.carData.handbrake) {
            keys.add("handbrake");
        }
        if (this.carData.carGear != mcuStatus.carData.carGear) {
            keys.add("carGear");
        }
        if (this.carData.signalRight != mcuStatus.carData.signalRight) {
            keys.add("signalRight");
        }
        if (this.carData.signalLeft != mcuStatus.carData.signalLeft) {
            keys.add("signalLeft");
        }
        if (this.carData.signalDouble != mcuStatus.carData.signalDouble) {
            keys.add("signalDouble");
        }
        if (this.carData.oilUnitType != mcuStatus.carData.oilUnitType) {
            keys.add("oilUnitType");
        }
        if (this.carData.distanceUnitType != mcuStatus.carData.distanceUnitType) {
            keys.add("distanceUnitType");
        }
        if (this.carData.airTemperature != mcuStatus.carData.airTemperature) {
            keys.add("airTemperature");
        }
        if (this.carData.oilSum != mcuStatus.carData.oilSum) {
            keys.add("oilSum");
        }
        if (this.carData.engineTurnS != mcuStatus.carData.engineTurnS) {
            keys.add("engineTurnS");
        }
        if (this.carData.speed != mcuStatus.carData.speed) {
            keys.add("speed");
        }
        if (this.carData.averSpeed != mcuStatus.carData.averSpeed) {
            keys.add("averSpeed");
        }
        if (this.carData.oilWear != mcuStatus.carData.oilWear) {
            keys.add("oilWear");
        }
        if (this.carData.mileage != mcuStatus.carData.mileage) {
            keys.add("mileage");
        }
        if (this.carData.carDoor != mcuStatus.carData.carDoor) {
            keys.add("carDoor");
        }
        if (!this.acData.getJson().equals(mcuStatus.acData.getJson())) {
            keys.add("acData");
        }
        if (!this.benzData.getJson().equals(mcuStatus.benzData.getJson())) {
            keys.add("benzData");
        }
        return keys;
    }

    /* loaded from: classes2.dex */
    public static final class KswMcuMsg {
        public int cmdType;
        public byte[] data;

        public KswMcuMsg(int cmdType, byte... data) {
            this.cmdType = cmdType;
            this.data = data;
        }

        public static KswMcuMsg getMsgFormJson(String jsonArg) {
            return (KswMcuMsg) new Gson().fromJson(jsonArg, (Class<Object>) KswMcuMsg.class);
        }
    }

    /* loaded from: classes2.dex */
    public static class ACData {
        public static final int LEFT_ABOVE = 128;
        public static final int LEFT_AUTO = 16;
        public static final int LEFT_BELOW = 32;
        public static final int LEFT_FRONT = 64;
        public static final int RIGHT_ABOVE = 8;
        public static final int RIGHT_AUTO = 1;
        public static final int RIGHT_BELOW = 2;
        public static final int RIGHT_FRONT = 4;

        /* renamed from: i */
        public static int f2576i = 0;
        public boolean AC_Switch;
        public boolean autoSwitch;
        public boolean backMistSwitch;
        public boolean eco;
        public boolean frontMistSwitch;
        public boolean isOpen;
        public float leftTmp;
        public int loop;
        public int mode;
        public float rightTmp;
        public float speed;
        public boolean sync;

        private float getTmp(int dataTmp) {
            if (dataTmp == -1 || dataTmp == 0) {
                float realTmp = dataTmp;
                return realTmp;
            }
            float realTmp2 = ((dataTmp - 1) * 0.5f) + 16.0f;
            return realTmp2;
        }

        public void setRightTmp(int rightTmp) {
            this.rightTmp = getTmp(rightTmp);
        }

        public void setLeftTmp(int leftTmp) {
            this.leftTmp = getTmp(leftTmp);
        }

        public boolean isOpen(int type) {
            return (this.mode & type) != 0;
        }

        public boolean isEco() {
            return this.eco;
        }

        public void setEco(boolean eco) {
            this.eco = eco;
        }

        public boolean isOpen() {
            return this.isOpen;
        }

        public void setOpen(boolean open) {
            this.isOpen = open;
        }

        public boolean isAC_Switch() {
            return this.AC_Switch;
        }

        public void setAC_Switch(boolean AC_Switch) {
            this.AC_Switch = AC_Switch;
        }

        public int getLoop() {
            return this.loop;
        }

        public void setLoop(int loop) {
            this.loop = loop;
        }

        public boolean isFrontMistSwitch() {
            return this.frontMistSwitch;
        }

        public void setFrontMistSwitch(boolean frontMistSwitch) {
            this.frontMistSwitch = frontMistSwitch;
        }

        public boolean isBackMistSwitch() {
            return this.backMistSwitch;
        }

        public void setBackMistSwitch(boolean backMistSwitch) {
            this.backMistSwitch = backMistSwitch;
        }

        public boolean isSync() {
            return this.sync;
        }

        public void setSync(boolean sync) {
            this.sync = sync;
        }

        public int getMode() {
            return this.mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public float getLeftTmp() {
            return this.leftTmp;
        }

        public void setLeftTmp(float leftTmp) {
            this.leftTmp = leftTmp;
        }

        public float getRightTmp() {
            return this.rightTmp;
        }

        public void setRightTmp(float rightTmp) {
            this.rightTmp = rightTmp;
        }

        public boolean isAutoSwitch() {
            return this.autoSwitch;
        }

        public void setAutoSwitch(boolean autoSwitch) {
            this.autoSwitch = autoSwitch;
        }

        public float getSpeed() {
            return this.speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public static KswMessage getTestMsg() {
            f2576i = f2576i == 0 ? 1 : 0;
            byte d0 = (byte) (f2576i * 255);
            byte d1 = (byte) ((255 - (f2576i << 4)) - f2576i);
            byte d2 = (byte) (28 - (f2576i * 5));
            byte d3 = (byte) (6 - (f2576i * 5));
            byte d4 = (byte) ((f2576i * 12) + (f2576i << 4));
            return KswMessage.obtain(161, new byte[]{28, d0, d1, d2, d3, d4});
        }

        public static KswMessage getTestMsg2() {
            f2576i = f2576i == 0 ? 1 : 2;
            byte b = (byte) (f2576i * 255);
            byte b2 = (byte) ((255 - (f2576i << 4)) - f2576i);
            byte b3 = (byte) (28 - (f2576i * 5));
            byte b4 = (byte) (6 - (f2576i * 5));
            byte b5 = (byte) ((f2576i * 12) + (f2576i << 4));
            return KswMessage.obtain(161, new byte[]{28, -64, 68, 7, 7, 4});
        }

        public static ACData getStatusFromJson(String jsonArg) {
            return (ACData) new Gson().fromJson(jsonArg, (Class<Object>) ACData.class);
        }

        public String getJson() {
            return new Gson().toJson(this);
        }
    }

    /* loaded from: classes2.dex */
    public static class AirControl {

        /* loaded from: classes2.dex */
        public enum AcKeyType {
            dual,
            backMistSwitch,
            frontMistSwitch,
            loop,
            AC_Switch,
            isOpen,
            NULL2,
            NULL1,
            speedAdd,
            speedReduce,
            windDirect,
            leftTmpAdd,
            leftTmpReduce,
            rightTmpAdd,
            rightTmpReduce,
            autoSwitch
        }

        public static void pressKey(AcKeyType type) {
            byte data1 = 0;
            byte data2 = 0;
            int index = type.ordinal();
            if (index < 8) {
                data1 = (byte) ((1 << index) + 0);
            } else {
                data2 = (byte) ((1 << (index - 8)) + 0);
            }
            WitsCommand.sendCommand(1, 612, ((int) data1) + SmsManager.REGEX_PREFIX_DELIMITER + ((int) data2));
        }
    }

    /* loaded from: classes2.dex */
    public static class MediaStringInfo {
        public String album;
        public String artist;
        public String folderName;
        public int min;
        public String name;
        public int sec;
        public int times;

        public static MediaStringInfo parseInfoFromJson(String json) {
            return (MediaStringInfo) new Gson().fromJson(json, (Class<Object>) MediaStringInfo.class);
        }
    }

    public static void main(String... arg) throws UnsupportedEncodingException {
        byte[] data = {2, 121, 65, -106, -122};
        try {
            byte[] checkStringBytes = new byte[(data.length - 1) * 20];
            byte[] stringBytes = new byte[data.length - 1];
            for (int i = 0; i < 20; i++) {
                System.arraycopy(data, 1, checkStringBytes, (data.length - 1) * i, stringBytes.length);
            }
            System.arraycopy(data, 1, stringBytes, 0, stringBytes.length);
            CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(checkStringBytes);
            CharsetMatch charsetMatch = charsetDetector.detect();
            String charSet = charsetMatch.getName();
            UniversalDetector detector = new UniversalDetector(null);
            detector.handleData(checkStringBytes, 0, checkStringBytes.length);
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            if (encoding != null && encoding.contains("GB")) {
                new String(stringBytes, encoding);
                return;
            }
            if (!charSet.contains("windows") && !charSet.equals("UTF-16LE") && charsetMatch.getConfidence() >= 10) {
                if (charsetMatch.getName().equals("Big5") && charsetMatch.getConfidence() >= 10) {
                    String checkString = new String(stringBytes, charSet);
                    String uniString = new String(stringBytes, "Unicode");
                    if (uniString.length() < checkString.length()) {
                        charSet = "Unicode";
                    }
                }
                new String(stringBytes, charSet);
            }
            charSet = "Unicode";
            new String(stringBytes, charSet);
        } catch (Exception e) {
        }
    }

    /* loaded from: classes2.dex */
    public static class CarBluetoothStatus {
        public int batteryStatus;
        public int callSignal;
        public boolean isCalling;
        public int min;
        public String name;
        public boolean playingMusic;
        public int sec;
        public String settingsInfo;
        public int times;

        public static CarBluetoothStatus parseInfoFromJson(String json) {
            return (CarBluetoothStatus) new Gson().fromJson(json, (Class<Object>) CarBluetoothStatus.class);
        }
    }

    /* loaded from: classes2.dex */
    public static class MediaPlayStatus {
        public static final int TYPE_AM = 1;
        public static final int TYPE_AUX = 20;
        public static final int TYPE_BT_MUSIC = 21;
        public static final int TYPE_DISC = 16;
        public static final int TYPE_FM = 0;
        public static final int TYPE_MP3 = 18;
        public static final int TYPE_USB = 17;
        public boolean ALS;
        public boolean RAND;
        public boolean RPT;
        public boolean SCAN;

        /* renamed from: ST */
        public boolean f2580ST;
        public String status;
        public int times;
        public int type;

        public static MediaPlayStatus parseInfoFromJson(String json) {
            return (MediaPlayStatus) new Gson().fromJson(json, (Class<Object>) MediaPlayStatus.class);
        }
    }

    /* loaded from: classes2.dex */
    public static class DiscStatus {
        public boolean[] discInsert;
        public int range;
        public String status;
        public int times;

        public static DiscStatus parseInfoFromJson(String json) {
            return (DiscStatus) new Gson().fromJson(json, (Class<Object>) DiscStatus.class);
        }
    }

    /* loaded from: classes2.dex */
    public static class MediaData {
        public static final int TYPE_AUX = 20;
        public static final int TYPE_BT = 21;
        public static final int TYPE_DISC = 16;
        public static final int TYPE_FM = 1;
        public static final int TYPE_MODE = 64;
        public static final int TYPE_MP3 = 18;
        public static final int TYPE_USB = 17;
        public boolean ALS;
        public boolean RAND;
        public boolean RPT;
        public boolean SCAN;

        /* renamed from: ST */
        public boolean f2577ST;
        public String status;
        public int times;
        public int type;

        /* renamed from: fm */
        public C3670Fm f2578fm = new C3670Fm();
        public Disc disc = new Disc();
        public Usb usb = new Usb();
        public DVD dvd = new DVD();
        public MODE mode = new MODE();
        public MP3 mp3 = new MP3();

        /* loaded from: classes2.dex */
        public static class DVD extends BaseMediaInfo {
            public int chapterNumber;
            public int min;
            public int sec;
            public int totalChapter;
        }

        /* loaded from: classes2.dex */
        public static class Disc extends BaseMediaInfo {
            public int min;
            public int number;
            public int sec;
            public int track;
        }

        /* renamed from: com.wits.pms.statuscontrol.McuStatus$MediaData$Fm */
        /* loaded from: classes2.dex */
        public static class C3670Fm {
            public String freq;
            public String name;
            public int preFreq;
        }

        /* loaded from: classes2.dex */
        public static class MODE extends BaseMediaInfo {
            public boolean ASL;
            public boolean PAUSE;
            public boolean RAND;
            public boolean RPT;
            public boolean SCAN;

            /* renamed from: ST */
            public boolean f2579ST;
        }

        /* loaded from: classes2.dex */
        public static class MP3 extends BaseMediaInfo {
            public int fileNumber;
            public int folderNumber;
            public int min;
            public int sec;
        }

        /* loaded from: classes2.dex */
        public static class Usb extends BaseMediaInfo {
            public int fileNumber;
            public int folderNumber;
            public int min;
            public int sec;
        }

        public BaseMediaInfo getCurrentMediaInfo() {
            int i;
            if (this.type != 0 && (i = this.type) != 1) {
                if (i != 64) {
                    switch (i) {
                        case 16:
                            return this.disc;
                        case 17:
                            return this.usb;
                        case 18:
                            return this.mp3;
                        default:
                            switch (i) {
                                case 20:
                                case 21:
                                    break;
                                default:
                                    return null;
                            }
                    }
                } else {
                    return this.mode;
                }
            }
            return null;
        }

        /* loaded from: classes2.dex */
        public static class BaseMediaInfo {
            public String name = "";
            public String artist = "";
            public String album = "";
            public String folderName = "";

            public void reset() {
                this.name = "";
                this.artist = "";
                this.album = "";
            }
        }

        public static MediaData parseDataFromJson(String json) {
            return (MediaData) new Gson().fromJson(json, (Class<Object>) MediaData.class);
        }
    }

    /* loaded from: classes2.dex */
    public static class EqData {
        public int BAL;
        public int BAS;
        public int FAD;
        public int MID;
        public int TRE;
        public String changeVol;
        public int times;
        public int volume;

        public static EqData parseDataFromJson(String jsonArg) {
            return (EqData) new Gson().fromJson(jsonArg, (Class<Object>) EqData.class);
        }
    }

    /* loaded from: classes2.dex */
    public static class BenzData {
        public static final int AIR_MATIC_STATUS = 2;
        public static final int AUXILIARY_RADAR = 3;
        public static final int HIGH_CHASSIS_SWITCH = 1;
        public boolean airBagSystem;
        public int airMaticStatus;
        public boolean auxiliaryRadar;
        public boolean highChassisSwitch;
        public int key3;
        public int light1 = 0;
        public int light2 = 0;
        public int pressButton;

        public void pressButton(int which) {
            this.pressButton = which;
            WitsCommand.sendCommand(1, 801, getJson());
            this.pressButton = 0;
        }

        public static BenzData getStatusFromJson(String jsonArg) {
            return (BenzData) new Gson().fromJson(jsonArg, (Class<Object>) BenzData.class);
        }

        public String getJson() {
            return new Gson().toJson(this);
        }
    }

    /* loaded from: classes2.dex */
    public static class CarData {
        public static final int AHEAD_COVER = 8;
        public static final int BACK_COVER = 4;
        public static final int GEAR_D = 4;
        public static final int GEAR_N = 2;
        public static final int GEAR_P = 0;
        public static final int GEAR_R = 6;
        public static final int LEFT_AHEAD = 16;
        public static final int LEFT_BACK = 64;
        public static final int LLIGHT_OFF = 0;
        public static final int LLIGHT_ON = 8;
        public static final int RIGHT_AHEAD = 32;
        public static final int RIGHT_BACK = 128;
        public static final int RLIGHT_OFF = 0;
        public static final int RLIGHT_ON = 16;
        public float airTemperature;
        public float averSpeed;
        public int carDoor;
        public int carGear;
        public int distanceUnitType;
        public int engineTurnS;
        public boolean handbrake;
        public int mileage;
        public int oilSum;
        public int oilUnitType;
        public float oilWear;
        public boolean safetyBelt;
        public int signalDouble;
        public int signalLeft;
        public int signalRight;
        public int speed;
        public int temperatureUnitType;

        public boolean isOpen(int type) {
            return (this.carDoor & type) != 0;
        }

        public int getSignalLeft() {
            return this.signalLeft;
        }

        public void setSignalLeft(int signalLeft) {
            this.signalLeft = signalLeft;
        }

        public int getSignalDouble() {
            return this.signalDouble;
        }

        public void setSignalDouble(int signalDouble) {
            this.signalDouble = signalDouble;
        }

        public int getSignalRight() {
            return this.signalRight;
        }

        public void setSignalRight(int signalRight) {
            this.signalRight = signalRight;
        }

        public int getCarGear() {
            return this.carGear;
        }

        public void setCarGear(int carGear) {
            this.carGear = carGear;
        }

        public int getCarDoor() {
            return this.carDoor;
        }

        public void setCarDoor(int carDoor) {
            this.carDoor = carDoor;
        }

        public boolean isHandbrake() {
            return this.handbrake;
        }

        public void setHandbrake(boolean handbrake) {
            this.handbrake = handbrake;
        }

        public boolean isSafetyBelt() {
            return this.safetyBelt;
        }

        public void setSafetyBelt(boolean safetyBelt) {
            this.safetyBelt = safetyBelt;
        }

        public int getMileage() {
            return this.mileage;
        }

        public void setMileage(int mileage) {
            this.mileage = mileage;
        }

        public float getOilWear() {
            return this.oilWear;
        }

        public void setOilWear(float oilWear) {
            this.oilWear = oilWear;
        }

        public int getOilSum() {
            return this.oilSum;
        }

        public void setOilSum(int oilSum) {
            this.oilSum = oilSum;
        }

        public float getAverSpeed() {
            return this.averSpeed;
        }

        public void setAverSpeed(float averSpeed) {
            this.averSpeed = averSpeed;
        }

        public int getSpeed() {
            return this.speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getEngineTurnS() {
            return this.engineTurnS;
        }

        public void setEngineTurnS(int engineTurnS) {
            this.engineTurnS = engineTurnS;
        }

        public float getAirTemperature() {
            return this.airTemperature;
        }

        public void setAirTemperature(float airTemperature) {
            this.airTemperature = airTemperature;
        }

        public int getDistanceUnitType() {
            return this.distanceUnitType;
        }

        public void setDistanceUnitType(int distanceUnitType) {
            this.distanceUnitType = distanceUnitType;
        }

        public int getTemperatureUnitType() {
            return this.temperatureUnitType;
        }

        public void setTemperatureUnitType(int temperatureUnitType) {
            this.temperatureUnitType = temperatureUnitType;
        }

        public int getOilUnitType() {
            return this.oilUnitType;
        }

        public void setOilUnitType(int oilUnitType) {
            this.oilUnitType = oilUnitType;
        }

        public static CarData getStatusFromJson(String jsonArg) {
            return (CarData) new Gson().fromJson(jsonArg, (Class<Object>) CarData.class);
        }

        public String getJson() {
            return new Gson().toJson(this);
        }
    }

    public int getSystemMode() {
        return this.systemMode;
    }

    public void setSystemMode(int systemMode) {
        this.systemMode = systemMode;
    }

    public String getMcuVerison() {
        return this.mcuVerison;
    }

    public void setMcuVerison(String mcuVerison) {
        this.mcuVerison = mcuVerison;
    }

    public CarData getCarData() {
        return this.carData;
    }

    public void setCarData(CarData carData) {
        this.carData = carData;
    }

    public ACData getAcData() {
        return this.acData;
    }

    public void setAcData(ACData acData) {
        this.acData = acData;
    }
}
