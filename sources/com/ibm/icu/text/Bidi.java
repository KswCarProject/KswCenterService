package com.ibm.icu.text;

import android.bluetooth.BluetoothHidDevice;
import android.mtp.MtpConstants;
import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.lang.UCharacter;
import java.awt.font.NumericShaper;
import java.awt.font.TextAttribute;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import org.mozilla.universalchardet.prober.HebrewProber;

public class Bidi {
    static final byte AL = 13;
    static final byte AN = 5;
    static final byte B = 7;
    static final byte BN = 18;
    @Deprecated
    public static final int CLASS_DEFAULT = 23;
    private static final char CR = '\r';
    static final byte CS = 6;
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = 126;
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = 127;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final short DO_MIRRORING = 2;
    static final int[] DirPropFlagE = {DirPropFlag(LRE), DirPropFlag((byte) 14)};
    static final int[] DirPropFlagLR = {DirPropFlag((byte) 0), DirPropFlag((byte) 1)};
    static final int DirPropFlagMultiRuns = DirPropFlag((byte) 31);
    static final int[] DirPropFlagO = {DirPropFlag(LRO), DirPropFlag((byte) 15)};
    static final byte EN = 2;
    static final byte ENL = 23;
    static final byte ENR = 24;
    static final byte ES = 3;
    static final byte ET = 4;
    static final int FIRSTALLOC = 10;
    static final byte FOUND_L = ((byte) DirPropFlag((byte) 0));
    static final byte FOUND_R = ((byte) DirPropFlag((byte) 1));
    static final byte FSI = 19;
    private static final int IMPTABLEVELS_COLUMNS = 8;
    private static final int IMPTABLEVELS_RES = 7;
    private static final int IMPTABPROPS_COLUMNS = 16;
    private static final int IMPTABPROPS_RES = 15;
    public static final short INSERT_LRM_FOR_NUMERIC = 4;
    static final int ISOLATE = 256;
    public static final short KEEP_BASE_COMBINING = 1;
    static final byte L = 0;
    public static final byte LEVEL_DEFAULT_LTR = 126;
    public static final byte LEVEL_DEFAULT_RTL = Byte.MAX_VALUE;
    public static final byte LEVEL_OVERRIDE = Byte.MIN_VALUE;
    private static final char LF = '\n';
    static final int LOOKING_FOR_PDI = 3;
    static final byte LRE = 11;
    static final byte LRI = 20;
    static final int LRM_AFTER = 2;
    static final int LRM_BEFORE = 1;
    static final byte LRO = 12;
    public static final byte LTR = 0;
    public static final int MAP_NOWHERE = -1;
    static final int MASK_BN_EXPLICIT = (DirPropFlag(BN) | MASK_EXPLICIT);
    static final int MASK_B_S = (DirPropFlag((byte) 7) | DirPropFlag((byte) 8));
    static final int MASK_EMBEDDING = (DirPropFlag(NSM) | MASK_POSSIBLE_N);
    static final int MASK_EXPLICIT = ((((DirPropFlag(LRE) | DirPropFlag(LRO)) | DirPropFlag((byte) 14)) | DirPropFlag((byte) 15)) | DirPropFlag((byte) 16));
    static final int MASK_ISO = (((DirPropFlag(LRI) | DirPropFlag(RLI)) | DirPropFlag(FSI)) | DirPropFlag(PDI));
    static final int MASK_LTR = (((((((DirPropFlag((byte) 0) | DirPropFlag((byte) 2)) | DirPropFlag(ENL)) | DirPropFlag(ENR)) | DirPropFlag((byte) 5)) | DirPropFlag(LRE)) | DirPropFlag(LRO)) | DirPropFlag(LRI));
    static final int MASK_POSSIBLE_N = ((((DirPropFlag(ON) | DirPropFlag((byte) 6)) | DirPropFlag((byte) 3)) | DirPropFlag((byte) 4)) | MASK_WS);
    static final int MASK_RTL = ((((DirPropFlag((byte) 1) | DirPropFlag(AL)) | DirPropFlag((byte) 14)) | DirPropFlag((byte) 15)) | DirPropFlag(RLI));
    static final int MASK_R_AL = (DirPropFlag((byte) 1) | DirPropFlag(AL));
    static final int MASK_STRONG_EN_AN = ((((DirPropFlag((byte) 0) | DirPropFlag((byte) 1)) | DirPropFlag(AL)) | DirPropFlag((byte) 2)) | DirPropFlag((byte) 5));
    static final int MASK_WS = (((MASK_B_S | DirPropFlag(WS)) | MASK_BN_EXPLICIT) | MASK_ISO);
    public static final byte MAX_EXPLICIT_LEVEL = 125;
    public static final byte MIXED = 2;
    public static final byte NEUTRAL = 3;
    static final int NOT_SEEKING_STRONG = 0;
    static final byte NSM = 17;
    static final byte ON = 10;
    public static final int OPTION_DEFAULT = 0;
    public static final int OPTION_INSERT_MARKS = 1;
    public static final int OPTION_REMOVE_CONTROLS = 2;
    public static final int OPTION_STREAMING = 4;
    public static final short OUTPUT_REVERSE = 16;
    static final byte PDF = 16;
    static final byte PDI = 22;
    static final byte R = 1;
    public static final short REMOVE_BIDI_CONTROLS = 8;
    static final short REORDER_COUNT = 7;
    public static final short REORDER_DEFAULT = 0;
    public static final short REORDER_GROUP_NUMBERS_WITH_R = 2;
    public static final short REORDER_INVERSE_FOR_NUMBERS_SPECIAL = 6;
    public static final short REORDER_INVERSE_LIKE_DIRECT = 5;
    public static final short REORDER_INVERSE_NUMBERS_AS_L = 4;
    static final short REORDER_LAST_LOGICAL_TO_VISUAL = 1;
    public static final short REORDER_NUMBERS_SPECIAL = 1;
    public static final short REORDER_RUNS_ONLY = 3;
    static final byte RLE = 14;
    static final byte RLI = 21;
    static final int RLM_AFTER = 8;
    static final int RLM_BEFORE = 4;
    static final byte RLO = 15;
    public static final byte RTL = 1;
    static final byte S = 8;
    static final int SEEKING_STRONG_FOR_FSI = 2;
    static final int SEEKING_STRONG_FOR_PARA = 1;
    static final int SIMPLE_OPENINGS_COUNT = 20;
    static final int SIMPLE_PARAS_COUNT = 10;
    static final byte WS = 9;
    private static final short _AN = 3;
    private static final short _B = 6;
    private static final short _EN = 2;
    private static final short _L = 0;
    private static final short _ON = 4;
    private static final short _R = 1;
    private static final short _S = 5;
    private static final short[] groupProp = {0, 1, 2, 7, 8, 3, 9, 6, 5, 4, 4, 10, 10, 12, 10, 10, 10, 11, 10, 4, 4, 4, 4, 13, 14};
    private static final short[] impAct0 = {0, 1, 2, 3, 4};
    private static final short[] impAct1 = {0, 1, 13, 14};
    private static final short[] impAct2 = {0, 1, 2, 5, 6, 7, 8};
    private static final short[] impAct3 = {0, 1, 9, 10, 11, 12};
    private static final byte[][] impTabL_DEFAULT = {new byte[]{0, 1, 0, 2, 0, 0, 0, 0}, new byte[]{0, 1, 3, 3, LRI, LRI, 0, 1}, new byte[]{0, 1, 0, 2, RLI, RLI, 0, 2}, new byte[]{0, 1, 3, 3, LRI, LRI, 0, 2}, new byte[]{0, 33, 51, 51, 4, 4, 0, 0}, new byte[]{0, 33, 0, 50, 5, 5, 0, 0}};
    private static final byte[][] impTabL_GROUP_NUMBERS_WITH_R = {new byte[]{0, 3, NSM, NSM, 0, 0, 0, 0}, new byte[]{HebrewProber.SPACE, 3, 1, 1, 2, HebrewProber.SPACE, HebrewProber.SPACE, 2}, new byte[]{HebrewProber.SPACE, 3, 1, 1, 2, HebrewProber.SPACE, HebrewProber.SPACE, 1}, new byte[]{0, 3, 5, 5, LRI, 0, 0, 1}, new byte[]{HebrewProber.SPACE, 3, 5, 5, 4, HebrewProber.SPACE, HebrewProber.SPACE, 1}, new byte[]{0, 3, 5, 5, LRI, 0, 0, 2}};
    private static final byte[][] impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = {new byte[]{0, 98, 1, 1, 0, 0, 0, 0}, new byte[]{0, 98, 1, 1, 0, 48, 0, 4}, new byte[]{0, 98, 84, 84, FSI, 48, 0, 3}, new byte[]{48, 66, 84, 84, 3, 48, 48, 3}, new byte[]{48, 66, 4, 4, FSI, 48, 48, 4}};
    private static final byte[][] impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS = {new byte[]{0, 99, 0, 1, 0, 0, 0, 0}, new byte[]{0, 99, 0, 1, BN, 48, 0, 4}, new byte[]{HebrewProber.SPACE, 99, HebrewProber.SPACE, 1, 2, 48, HebrewProber.SPACE, 3}, new byte[]{0, 99, 85, 86, LRI, 48, 0, 3}, new byte[]{48, 67, 85, 86, 4, 48, 48, 3}, new byte[]{48, 67, 5, 86, LRI, 48, 48, 4}, new byte[]{48, 67, 85, 6, LRI, 48, 48, 4}};
    private static final byte[][] impTabL_INVERSE_NUMBERS_AS_L = {new byte[]{0, 1, 0, 0, 0, 0, 0, 0}, new byte[]{0, 1, 0, 0, LRI, LRI, 0, 1}, new byte[]{0, 1, 0, 0, RLI, RLI, 0, 2}, new byte[]{0, 1, 0, 0, LRI, LRI, 0, 2}, new byte[]{HebrewProber.SPACE, 1, HebrewProber.SPACE, HebrewProber.SPACE, 4, 4, HebrewProber.SPACE, 1}, new byte[]{HebrewProber.SPACE, 1, HebrewProber.SPACE, HebrewProber.SPACE, 5, 5, HebrewProber.SPACE, 1}};
    private static final byte[][] impTabL_NUMBERS_SPECIAL = {new byte[]{0, 2, NSM, NSM, 0, 0, 0, 0}, new byte[]{0, 66, 1, 1, 0, 0, 0, 0}, new byte[]{0, 2, 4, 4, FSI, FSI, 0, 1}, new byte[]{0, 34, 52, 52, 3, 3, 0, 0}, new byte[]{0, 2, 4, 4, FSI, FSI, 0, 2}};
    private static final short[][] impTabProps = {new short[]{1, 2, 4, 5, 7, 15, 17, 7, 9, 7, 0, 7, 3, 18, 21, 4}, new short[]{1, 34, 36, 37, 39, 47, 49, 39, 41, 39, 1, 1, 35, 50, 53, 0}, new short[]{33, 2, 36, 37, 39, 47, 49, 39, 41, 39, 2, 2, 35, 50, 53, 1}, new short[]{33, 34, 38, 38, 40, 48, 49, 40, 40, 40, 3, 3, 3, 50, 53, 1}, new short[]{33, 34, 4, 37, 39, 47, 49, 74, 11, 74, 4, 4, 35, 18, 21, 2}, new short[]{33, 34, 36, 5, 39, 47, 49, 39, 41, 76, 5, 5, 35, 50, 53, 3}, new short[]{33, 34, 6, 6, 40, 48, 49, 40, 40, 77, 6, 6, 35, 18, 21, 3}, new short[]{33, 34, 36, 37, 7, 47, 49, 7, 78, 7, 7, 7, 35, 50, 53, 4}, new short[]{33, 34, 38, 38, 8, 48, 49, 8, 8, 8, 8, 8, 35, 50, 53, 4}, new short[]{33, 34, 4, 37, 7, 47, 49, 7, 9, 7, 9, 9, 35, 18, 21, 4}, new short[]{97, 98, 4, 101, 135, 111, 113, 135, 142, 135, 10, 135, 99, 18, 21, 2}, new short[]{33, 34, 4, 37, 39, 47, 49, 39, 11, 39, 11, 11, 35, 18, 21, 2}, new short[]{97, 98, 100, 5, 135, 111, 113, 135, 142, 135, 12, 135, 99, 114, 117, 3}, new short[]{97, 98, 6, 6, 136, 112, 113, 136, 136, 136, 13, 136, 99, 18, 21, 3}, new short[]{33, 34, 132, 37, 7, 47, 49, 7, 14, 7, 14, 14, 35, 146, 149, 4}, new short[]{33, 34, 36, 37, 39, 15, 49, 39, 41, 39, 15, 39, 35, 50, 53, 5}, new short[]{33, 34, 38, 38, 40, 16, 49, 40, 40, 40, 16, 40, 35, 50, 53, 5}, new short[]{33, 34, 36, 37, 39, 47, 17, 39, 41, 39, 17, 39, 35, 50, 53, 6}, new short[]{33, 34, 18, 37, 39, 47, 49, 83, 20, 83, 18, 18, 35, 18, 21, 0}, new short[]{97, 98, 18, 101, 135, 111, 113, 135, 142, 135, 19, 135, 99, 18, 21, 0}, new short[]{33, 34, 18, 37, 39, 47, 49, 39, 20, 39, 20, 20, 35, 18, 21, 0}, new short[]{33, 34, 21, 37, 39, 47, 49, 86, 23, 86, 21, 21, 35, 18, 21, 3}, new short[]{97, 98, 21, 101, 135, 111, 113, 135, 142, 135, 22, 135, 99, 18, 21, 3}, new short[]{33, 34, 21, 37, 39, 47, 49, 39, 23, 39, 23, 23, 35, 18, 21, 3}};
    private static final byte[][] impTabR_DEFAULT = {new byte[]{1, 0, 2, 2, 0, 0, 0, 0}, new byte[]{1, 0, 1, 3, LRI, LRI, 0, 1}, new byte[]{1, 0, 2, 2, 0, 0, 0, 1}, new byte[]{1, 0, 1, 3, 5, 5, 0, 1}, new byte[]{33, 0, 33, 3, 4, 4, 0, 0}, new byte[]{1, 0, 1, 3, 5, 5, 0, 0}};
    private static final byte[][] impTabR_GROUP_NUMBERS_WITH_R = {new byte[]{2, 0, 1, 1, 0, 0, 0, 0}, new byte[]{2, 0, 1, 1, 0, 0, 0, 1}, new byte[]{2, 0, LRI, LRI, FSI, 0, 0, 1}, new byte[]{34, 0, 4, 4, 3, 0, 0, 0}, new byte[]{34, 0, 4, 4, 3, 0, 0, 1}};
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT = {new byte[]{1, 0, 2, 2, 0, 0, 0, 0}, new byte[]{1, 0, 1, 2, FSI, FSI, 0, 1}, new byte[]{1, 0, 2, 2, 0, 0, 0, 1}, new byte[]{33, 48, 6, 4, 3, 3, 48, 0}, new byte[]{33, 48, 6, 4, 5, 5, 48, 3}, new byte[]{33, 48, 6, 4, 5, 5, 48, 2}, new byte[]{33, 48, 6, 4, 3, 3, 48, 1}};
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS = {new byte[]{FSI, 0, 1, 1, 0, 0, 0, 0}, new byte[]{35, 0, 1, 1, 2, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 0, 1}, new byte[]{35, 0, 1, 1, 2, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 0, 0}, new byte[]{3, 0, 3, 54, LRI, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 0, 1}, new byte[]{83, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 5, 54, 4, BluetoothHidDevice.SUBCLASS1_KEYBOARD, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 0}, new byte[]{83, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 5, 54, 4, BluetoothHidDevice.SUBCLASS1_KEYBOARD, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 1}, new byte[]{83, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 6, 6, 4, BluetoothHidDevice.SUBCLASS1_KEYBOARD, BluetoothHidDevice.SUBCLASS1_KEYBOARD, 3}};
    private static final byte[][] impTabR_INVERSE_NUMBERS_AS_L = {new byte[]{1, 0, 1, 1, 0, 0, 0, 0}, new byte[]{1, 0, 1, 1, LRI, LRI, 0, 1}, new byte[]{1, 0, 1, 1, 0, 0, 0, 1}, new byte[]{1, 0, 1, 1, 5, 5, 0, 1}, new byte[]{33, 0, 33, 33, 4, 4, 0, 0}, new byte[]{1, 0, 1, 1, 5, 5, 0, 0}};
    private static final ImpTabPair impTab_DEFAULT = new ImpTabPair(impTabL_DEFAULT, impTabR_DEFAULT, impAct0, impAct0);
    private static final ImpTabPair impTab_GROUP_NUMBERS_WITH_R = new ImpTabPair(impTabL_GROUP_NUMBERS_WITH_R, impTabR_GROUP_NUMBERS_WITH_R, impAct0, impAct0);
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT = new ImpTabPair(impTabL_DEFAULT, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
    private static final ImpTabPair impTab_INVERSE_NUMBERS_AS_L = new ImpTabPair(impTabL_INVERSE_NUMBERS_AS_L, impTabR_INVERSE_NUMBERS_AS_L, impAct0, impAct0);
    private static final ImpTabPair impTab_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_DEFAULT, impAct0, impAct0);
    final UBiDiProps bdp;
    int controlCount;
    BidiClassifier customClassifier;
    byte defaultParaLevel;
    byte[] dirProps;
    byte[] dirPropsMemory;
    byte direction;
    String epilogue;
    int flags;
    ImpTabPair impTabPair;
    InsertPoints insertPoints;
    boolean isGoodLogicalToVisualRunsMap;
    boolean isInverse;
    int isolateCount;
    Isolate[] isolates;
    int lastArabicPos;
    int length;
    byte[] levels;
    byte[] levelsMemory;
    int[] logicalToVisualRunsMap;
    boolean mayAllocateRuns;
    boolean mayAllocateText;
    boolean orderParagraphsLTR;
    int originalLength;
    Bidi paraBidi;
    int paraCount;
    byte paraLevel;
    byte[] paras_level;
    int[] paras_limit;
    String prologue;
    int reorderingMode;
    int reorderingOptions;
    int resultLength;
    int runCount;
    BidiRun[] runs;
    BidiRun[] runsMemory;
    BidiRun[] simpleRuns;
    char[] text;
    int trailingWSStart;

    static class Point {
        int flag;
        int pos;

        Point() {
        }
    }

    static class InsertPoints {
        int confirmed;
        Point[] points = new Point[0];
        int size;

        InsertPoints() {
        }
    }

    static class Opening {
        byte contextDir;
        int contextPos;
        short flags;
        int match;
        int position;

        Opening() {
        }
    }

    static class IsoRun {
        byte contextDir;
        int contextPos;
        byte lastBase;
        byte lastStrong;
        byte level;
        short limit;
        short start;

        IsoRun() {
        }
    }

    static class BracketData {
        boolean isNumbersSpecial;
        int isoRunLast;
        IsoRun[] isoRuns = new IsoRun[127];
        Opening[] openings = new Opening[20];

        BracketData() {
        }
    }

    static class Isolate {
        int start1;
        int startON;
        short state;
        short stateImp;

        Isolate() {
        }
    }

    static int DirPropFlag(byte dir) {
        return 1 << dir;
    }

    /* access modifiers changed from: package-private */
    public boolean testDirPropFlagAt(int flag, int index) {
        return (DirPropFlag(this.dirProps[index]) & flag) != 0;
    }

    static final int DirPropFlagLR(byte level) {
        return DirPropFlagLR[level & 1];
    }

    static final int DirPropFlagE(byte level) {
        return DirPropFlagE[level & 1];
    }

    static final int DirPropFlagO(byte level) {
        return DirPropFlagO[level & 1];
    }

    static final byte DirFromStrong(byte strong) {
        return strong == 0 ? (byte) 0 : 1;
    }

    static final byte NoOverride(byte level) {
        return (byte) (level & LEVEL_DEFAULT_RTL);
    }

    static byte GetLRFromLevel(byte level) {
        return (byte) (level & 1);
    }

    static boolean IsDefaultLevel(byte level) {
        return (level & LEVEL_DEFAULT_LTR) == 126;
    }

    static boolean IsBidiControlChar(int c) {
        return (c & -4) == 8204 || (c >= 8234 && c <= 8238) || (c >= 8294 && c <= 8297);
    }

    /* access modifiers changed from: package-private */
    public void verifyValidPara() {
        if (this != this.paraBidi) {
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: package-private */
    public void verifyValidParaOrLine() {
        Bidi para = this.paraBidi;
        if (this != para) {
            if (para == null || para != para.paraBidi) {
                throw new IllegalStateException();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void verifyRange(int index, int start, int limit) {
        if (index < start || index >= limit) {
            throw new IllegalArgumentException("Value " + index + " is out of range " + start + " to " + limit);
        }
    }

    public Bidi() {
        this(0, 0);
    }

    public Bidi(int maxLength, int maxRunCount) {
        this.dirPropsMemory = new byte[1];
        this.levelsMemory = new byte[1];
        this.paras_limit = new int[10];
        this.paras_level = new byte[10];
        this.runsMemory = new BidiRun[0];
        this.simpleRuns = new BidiRun[]{new BidiRun()};
        this.customClassifier = null;
        this.insertPoints = new InsertPoints();
        if (maxLength < 0 || maxRunCount < 0) {
            throw new IllegalArgumentException();
        }
        this.bdp = UBiDiProps.INSTANCE;
        if (maxLength > 0) {
            getInitialDirPropsMemory(maxLength);
            getInitialLevelsMemory(maxLength);
        } else {
            this.mayAllocateText = true;
        }
        if (maxRunCount <= 0) {
            this.mayAllocateRuns = true;
        } else if (maxRunCount > 1) {
            getInitialRunsMemory(maxRunCount);
        }
    }

    private Object getMemory(String label, Object array, Class<?> arrayClass, boolean mayAllocate, int sizeNeeded) {
        int len = Array.getLength(array);
        if (sizeNeeded == len) {
            return array;
        }
        if (mayAllocate) {
            try {
                return Array.newInstance(arrayClass, sizeNeeded);
            } catch (Exception e) {
                throw new OutOfMemoryError("Failed to allocate memory for " + label);
            }
        } else if (sizeNeeded <= len) {
            return array;
        } else {
            throw new OutOfMemoryError("Failed to allocate memory for " + label);
        }
    }

    private void getDirPropsMemory(boolean mayAllocate, int len) {
        this.dirPropsMemory = (byte[]) getMemory("DirProps", this.dirPropsMemory, Byte.TYPE, mayAllocate, len);
    }

    /* access modifiers changed from: package-private */
    public void getDirPropsMemory(int len) {
        getDirPropsMemory(this.mayAllocateText, len);
    }

    private void getLevelsMemory(boolean mayAllocate, int len) {
        this.levelsMemory = (byte[]) getMemory("Levels", this.levelsMemory, Byte.TYPE, mayAllocate, len);
    }

    /* access modifiers changed from: package-private */
    public void getLevelsMemory(int len) {
        getLevelsMemory(this.mayAllocateText, len);
    }

    private void getRunsMemory(boolean mayAllocate, int len) {
        this.runsMemory = (BidiRun[]) getMemory("Runs", this.runsMemory, BidiRun.class, mayAllocate, len);
    }

    /* access modifiers changed from: package-private */
    public void getRunsMemory(int len) {
        getRunsMemory(this.mayAllocateRuns, len);
    }

    private void getInitialDirPropsMemory(int len) {
        getDirPropsMemory(true, len);
    }

    private void getInitialLevelsMemory(int len) {
        getLevelsMemory(true, len);
    }

    private void getInitialRunsMemory(int len) {
        getRunsMemory(true, len);
    }

    public void setInverse(boolean isInverse2) {
        this.isInverse = isInverse2;
        this.reorderingMode = isInverse2 ? 4 : 0;
    }

    public boolean isInverse() {
        return this.isInverse;
    }

    public void setReorderingMode(int reorderingMode2) {
        if (reorderingMode2 >= 0 && reorderingMode2 < 7) {
            this.reorderingMode = reorderingMode2;
            this.isInverse = reorderingMode2 == 4;
        }
    }

    public int getReorderingMode() {
        return this.reorderingMode;
    }

    public void setReorderingOptions(int options) {
        if ((options & 2) != 0) {
            this.reorderingOptions = options & -2;
        } else {
            this.reorderingOptions = options;
        }
    }

    public int getReorderingOptions() {
        return this.reorderingOptions;
    }

    public static byte getBaseDirection(CharSequence paragraph) {
        if (paragraph == null || paragraph.length() == 0) {
            return 3;
        }
        int length2 = paragraph.length();
        int i = 0;
        while (i < length2) {
            byte direction2 = UCharacter.getDirectionality(UCharacter.codePointAt(paragraph, i));
            if (direction2 == 0) {
                return 0;
            }
            if (direction2 == 1 || direction2 == 13) {
                return 1;
            }
            i = UCharacter.offsetByCodePoints(paragraph, i, 1);
        }
        return 3;
    }

    private byte firstL_R_AL() {
        byte result = ON;
        int i = 0;
        while (i < this.prologue.length()) {
            int uchar = this.prologue.codePointAt(i);
            i += Character.charCount(uchar);
            byte dirProp = (byte) getCustomizedClass(uchar);
            if (result == 10) {
                if (dirProp == 0 || dirProp == 1 || dirProp == 13) {
                    result = dirProp;
                }
            } else if (dirProp == 7) {
                result = ON;
            }
        }
        return result;
    }

    private void checkParaCount() {
        int count = this.paraCount;
        if (count > this.paras_level.length) {
            int oldLength = this.paras_level.length;
            int[] saveLimits = this.paras_limit;
            byte[] saveLevels = this.paras_level;
            try {
                this.paras_limit = new int[(count * 2)];
                this.paras_level = new byte[(count * 2)];
                System.arraycopy(saveLimits, 0, this.paras_limit, 0, oldLength);
                System.arraycopy(saveLevels, 0, this.paras_level, 0, oldLength);
            } catch (Exception e) {
                throw new OutOfMemoryError("Failed to allocate memory for paras");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:111:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x00e7 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getDirProps() {
        /*
            r23 = this;
            r0 = r23
            r1 = 0
            r2 = 0
            r0.flags = r2
            r3 = 0
            byte r4 = r0.paraLevel
            boolean r4 = IsDefaultLevel(r4)
            r5 = 1
            if (r4 == 0) goto L_0x001c
            int r6 = r0.reorderingMode
            r7 = 5
            if (r6 == r7) goto L_0x001a
            int r6 = r0.reorderingMode
            r7 = 6
            if (r6 != r7) goto L_0x001c
        L_0x001a:
            r6 = r5
            goto L_0x001d
        L_0x001c:
            r6 = r2
        L_0x001d:
            r7 = -1
            r0.lastArabicPos = r7
            r8 = 0
            int r9 = r0.reorderingOptions
            r10 = 2
            r9 = r9 & r10
            if (r9 == 0) goto L_0x0029
            r9 = r5
            goto L_0x002a
        L_0x0029:
            r9 = r2
        L_0x002a:
            r11 = 10
            r12 = 126(0x7e, float:1.77E-43)
            int[] r13 = new int[r12]
            byte[] r12 = new byte[r12]
            r14 = -1
            int r15 = r0.reorderingOptions
            r15 = r15 & 4
            if (r15 == 0) goto L_0x003b
            r0.length = r2
        L_0x003b:
            byte r15 = r0.paraLevel
            r15 = r15 & r5
            byte r3 = (byte) r15
            r15 = 10
            if (r4 == 0) goto L_0x0063
            byte[] r10 = r0.paras_level
            r10[r2] = r3
            r11 = r3
            java.lang.String r10 = r0.prologue
            if (r10 == 0) goto L_0x0061
            byte r10 = r23.firstL_R_AL()
            r16 = r10
            if (r10 == r15) goto L_0x0061
            if (r16 != 0) goto L_0x005b
            byte[] r10 = r0.paras_level
            r10[r2] = r2
            goto L_0x005f
        L_0x005b:
            byte[] r10 = r0.paras_level
            r10[r2] = r5
        L_0x005f:
            r10 = 0
            goto L_0x006a
        L_0x0061:
            r10 = 1
            goto L_0x006a
        L_0x0063:
            byte[] r10 = r0.paras_level
            byte r15 = r0.paraLevel
            r10[r2] = r15
            r10 = r2
        L_0x006a:
            r1 = 0
        L_0x006b:
            int r15 = r0.originalLength
            r16 = 20
            if (r1 >= r15) goto L_0x01c9
            r15 = r1
            char[] r5 = r0.text
            int r7 = r0.originalLength
            int r5 = com.ibm.icu.text.UTF16.charAt(r5, r2, r7, r1)
            int r7 = com.ibm.icu.text.UTF16.getCharCount(r5)
            int r1 = r1 + r7
            int r7 = r1 + -1
            int r2 = r0.getCustomizedClass(r5)
            byte r2 = (byte) r2
            r18 = r3
            int r3 = r0.flags
            int r19 = DirPropFlag(r2)
            r3 = r3 | r19
            r0.flags = r3
            byte[] r3 = r0.dirProps
            r3[r7] = r2
            if (r7 <= r15) goto L_0x00af
            int r3 = r0.flags
            r19 = 18
            int r20 = DirPropFlag(r19)
            r3 = r3 | r20
            r0.flags = r3
        L_0x00a4:
            byte[] r3 = r0.dirProps
            r17 = -1
            int r7 = r7 + -1
            r3[r7] = r19
            if (r7 > r15) goto L_0x00a4
            goto L_0x00b1
        L_0x00af:
            r17 = -1
        L_0x00b1:
            if (r9 == 0) goto L_0x00bb
            boolean r3 = IsBidiControlChar(r5)
            if (r3 == 0) goto L_0x00bb
            int r8 = r8 + 1
        L_0x00bb:
            if (r2 != 0) goto L_0x00f1
            r3 = 1
            if (r10 != r3) goto L_0x00cf
            r21 = r7
            byte[] r7 = r0.paras_level
            r22 = r9
            int r9 = r0.paraCount
            int r9 = r9 - r3
            r3 = 0
            r7[r9] = r3
            r3 = 0
        L_0x00cd:
            r10 = r3
            goto L_0x00e5
        L_0x00cf:
            r21 = r7
            r22 = r9
            r3 = 2
            if (r10 != r3) goto L_0x00e5
            r3 = 125(0x7d, float:1.75E-43)
            if (r14 > r3) goto L_0x00e3
            int r3 = r0.flags
            int r7 = DirPropFlag(r16)
            r3 = r3 | r7
            r0.flags = r3
        L_0x00e3:
            r3 = 3
            goto L_0x00cd
        L_0x00e5:
            r11 = 0
        L_0x00e7:
            r7 = r17
            r3 = r18
            r9 = r22
            r2 = 0
            r5 = 1
            goto L_0x006b
        L_0x00f1:
            r21 = r7
            r22 = r9
            r3 = 21
            r7 = 13
            r9 = 1
            if (r2 == r9) goto L_0x0199
            if (r2 != r7) goto L_0x0100
            goto L_0x0199
        L_0x0100:
            r9 = 19
            if (r2 < r9) goto L_0x011e
            if (r2 > r3) goto L_0x011e
            int r14 = r14 + 1
            r3 = 125(0x7d, float:1.75E-43)
            if (r14 > r3) goto L_0x0112
            int r3 = r1 + -1
            r13[r14] = r3
            r12[r14] = r10
        L_0x0112:
            if (r2 != r9) goto L_0x011c
            byte[] r3 = r0.dirProps
            int r7 = r1 + -1
            r3[r7] = r16
            r10 = 2
            goto L_0x00e7
        L_0x011c:
            r10 = 3
            goto L_0x00e7
        L_0x011e:
            r3 = 22
            if (r2 != r3) goto L_0x013e
            r3 = 2
            if (r10 != r3) goto L_0x0132
            r3 = 125(0x7d, float:1.75E-43)
            if (r14 > r3) goto L_0x0132
            int r3 = r0.flags
            int r7 = DirPropFlag(r16)
            r3 = r3 | r7
            r0.flags = r3
        L_0x0132:
            if (r14 < 0) goto L_0x00e7
            r3 = 125(0x7d, float:1.75E-43)
            if (r14 > r3) goto L_0x013b
            byte r3 = r12[r14]
            r10 = r3
        L_0x013b:
            int r14 = r14 + -1
            goto L_0x00e7
        L_0x013e:
            r3 = 7
            if (r2 != r3) goto L_0x00e7
            int r3 = r0.originalLength
            if (r1 >= r3) goto L_0x0150
            if (r5 != r7) goto L_0x0150
            char[] r3 = r0.text
            char r3 = r3[r1]
            r9 = 10
            if (r3 != r9) goto L_0x0152
            goto L_0x00e7
        L_0x0150:
            r9 = 10
        L_0x0152:
            int[] r3 = r0.paras_limit
            int r7 = r0.paraCount
            r9 = 1
            int r7 = r7 - r9
            r3[r7] = r1
            if (r6 == 0) goto L_0x0165
            if (r11 != r9) goto L_0x0165
            byte[] r3 = r0.paras_level
            int r7 = r0.paraCount
            int r7 = r7 - r9
            r3[r7] = r9
        L_0x0165:
            int r3 = r0.reorderingOptions
            r3 = r3 & 4
            if (r3 == 0) goto L_0x016f
            r0.length = r1
            r0.controlCount = r8
        L_0x016f:
            int r3 = r0.originalLength
            if (r1 >= r3) goto L_0x00e7
            int r3 = r0.paraCount
            r7 = 1
            int r3 = r3 + r7
            r0.paraCount = r3
            r23.checkParaCount()
            if (r4 == 0) goto L_0x018b
            byte[] r3 = r0.paras_level
            int r9 = r0.paraCount
            int r9 = r9 - r7
            r3[r9] = r18
            r3 = 1
            r9 = r18
            r10 = r3
            r11 = r9
            goto L_0x0196
        L_0x018b:
            byte[] r3 = r0.paras_level
            int r9 = r0.paraCount
            int r9 = r9 - r7
            byte r7 = r0.paraLevel
            r3[r9] = r7
            r3 = 0
            r10 = r3
        L_0x0196:
            r14 = -1
            goto L_0x00e7
        L_0x0199:
            r9 = 1
            if (r10 != r9) goto L_0x01a6
            byte[] r3 = r0.paras_level
            int r7 = r0.paraCount
            int r7 = r7 - r9
            r3[r7] = r9
            r3 = 0
        L_0x01a4:
            r10 = r3
            goto L_0x01be
        L_0x01a6:
            r7 = 2
            if (r10 != r7) goto L_0x01be
            r7 = 125(0x7d, float:1.75E-43)
            if (r14 > r7) goto L_0x01bc
            byte[] r7 = r0.dirProps
            r9 = r13[r14]
            r7[r9] = r3
            int r7 = r0.flags
            int r3 = DirPropFlag(r3)
            r3 = r3 | r7
            r0.flags = r3
        L_0x01bc:
            r3 = 3
            goto L_0x01a4
        L_0x01be:
            r11 = 1
            r3 = 13
            if (r2 != r3) goto L_0x00e7
            int r3 = r1 + -1
            r0.lastArabicPos = r3
            goto L_0x00e7
        L_0x01c9:
            r18 = r3
            r22 = r9
            r2 = 125(0x7d, float:1.75E-43)
            if (r14 <= r2) goto L_0x01d4
            r14 = 125(0x7d, float:1.75E-43)
            r10 = 2
        L_0x01d4:
            if (r14 < 0) goto L_0x01e8
            r2 = 2
            if (r10 != r2) goto L_0x01e3
            int r2 = r0.flags
            int r3 = DirPropFlag(r16)
            r2 = r2 | r3
            r0.flags = r2
            goto L_0x01e8
        L_0x01e3:
            byte r10 = r12[r14]
            int r14 = r14 + -1
            goto L_0x01d4
        L_0x01e8:
            int r2 = r0.reorderingOptions
            r2 = r2 & 4
            if (r2 == 0) goto L_0x01fd
            int r2 = r0.length
            int r3 = r0.originalLength
            if (r2 >= r3) goto L_0x01fb
            int r2 = r0.paraCount
            r3 = 1
            int r2 = r2 - r3
            r0.paraCount = r2
            goto L_0x0209
        L_0x01fb:
            r3 = 1
            goto L_0x0209
        L_0x01fd:
            r3 = 1
            int[] r2 = r0.paras_limit
            int r5 = r0.paraCount
            int r5 = r5 - r3
            int r7 = r0.originalLength
            r2[r5] = r7
            r0.controlCount = r8
        L_0x0209:
            if (r6 == 0) goto L_0x0214
            if (r11 != r3) goto L_0x0214
            byte[] r2 = r0.paras_level
            int r5 = r0.paraCount
            int r5 = r5 - r3
            r2[r5] = r3
        L_0x0214:
            if (r4 == 0) goto L_0x021d
            byte[] r2 = r0.paras_level
            r3 = 0
            byte r2 = r2[r3]
            r0.paraLevel = r2
        L_0x021d:
            r1 = 0
        L_0x021e:
            int r2 = r0.paraCount
            if (r1 >= r2) goto L_0x0232
            int r2 = r0.flags
            byte[] r3 = r0.paras_level
            byte r3 = r3[r1]
            int r3 = DirPropFlagLR(r3)
            r2 = r2 | r3
            r0.flags = r2
            int r1 = r1 + 1
            goto L_0x021e
        L_0x0232:
            boolean r2 = r0.orderParagraphsLTR
            if (r2 == 0) goto L_0x024a
            int r2 = r0.flags
            r3 = 7
            int r3 = DirPropFlag(r3)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x024a
            int r2 = r0.flags
            r3 = 0
            int r3 = DirPropFlag(r3)
            r2 = r2 | r3
            r0.flags = r2
        L_0x024a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.getDirProps():void");
    }

    /* access modifiers changed from: package-private */
    public byte GetParaLevelAt(int pindex) {
        if (this.defaultParaLevel == 0 || pindex < this.paras_limit[0]) {
            return this.paraLevel;
        }
        int i = 1;
        while (i < this.paraCount && pindex >= this.paras_limit[i]) {
            i++;
        }
        if (i >= this.paraCount) {
            i = this.paraCount - 1;
        }
        return this.paras_level[i];
    }

    private void bracketInit(BracketData bd) {
        boolean z = false;
        bd.isoRunLast = 0;
        bd.isoRuns[0] = new IsoRun();
        bd.isoRuns[0].start = 0;
        bd.isoRuns[0].limit = 0;
        bd.isoRuns[0].level = GetParaLevelAt(0);
        IsoRun isoRun = bd.isoRuns[0];
        IsoRun isoRun2 = bd.isoRuns[0];
        IsoRun isoRun3 = bd.isoRuns[0];
        byte GetParaLevelAt = (byte) (GetParaLevelAt(0) & 1);
        isoRun3.contextDir = GetParaLevelAt;
        isoRun2.lastBase = GetParaLevelAt;
        isoRun.lastStrong = GetParaLevelAt;
        bd.isoRuns[0].contextPos = 0;
        bd.openings = new Opening[20];
        if (this.reorderingMode == 1 || this.reorderingMode == 6) {
            z = true;
        }
        bd.isNumbersSpecial = z;
    }

    private void bracketProcessB(BracketData bd, byte level) {
        bd.isoRunLast = 0;
        bd.isoRuns[0].limit = 0;
        bd.isoRuns[0].level = level;
        IsoRun isoRun = bd.isoRuns[0];
        IsoRun isoRun2 = bd.isoRuns[0];
        byte b = (byte) (level & 1);
        bd.isoRuns[0].contextDir = b;
        isoRun2.lastBase = b;
        isoRun.lastStrong = b;
        bd.isoRuns[0].contextPos = 0;
    }

    private void bracketProcessBoundary(BracketData bd, int lastCcPos, byte contextLevel, byte embeddingLevel) {
        IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
        if ((DirPropFlag(this.dirProps[lastCcPos]) & MASK_ISO) == 0) {
            if (NoOverride(embeddingLevel) > NoOverride(contextLevel)) {
                contextLevel = embeddingLevel;
            }
            pLastIsoRun.limit = pLastIsoRun.start;
            pLastIsoRun.level = embeddingLevel;
            byte b = (byte) (contextLevel & 1);
            pLastIsoRun.contextDir = b;
            pLastIsoRun.lastBase = b;
            pLastIsoRun.lastStrong = b;
            pLastIsoRun.contextPos = lastCcPos;
        }
    }

    private void bracketProcessLRI_RLI(BracketData bd, byte level) {
        IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
        pLastIsoRun.lastBase = ON;
        short lastLimit = pLastIsoRun.limit;
        bd.isoRunLast++;
        IsoRun pLastIsoRun2 = bd.isoRuns[bd.isoRunLast];
        if (pLastIsoRun2 == null) {
            IsoRun[] isoRunArr = bd.isoRuns;
            int i = bd.isoRunLast;
            IsoRun isoRun = new IsoRun();
            isoRunArr[i] = isoRun;
            pLastIsoRun2 = isoRun;
        }
        pLastIsoRun2.limit = lastLimit;
        pLastIsoRun2.start = lastLimit;
        pLastIsoRun2.level = level;
        byte b = (byte) (level & 1);
        pLastIsoRun2.contextDir = b;
        pLastIsoRun2.lastBase = b;
        pLastIsoRun2.lastStrong = b;
        pLastIsoRun2.contextPos = 0;
    }

    private void bracketProcessPDI(BracketData bd) {
        bd.isoRunLast--;
        bd.isoRuns[bd.isoRunLast].lastBase = ON;
    }

    private void bracketAddOpening(BracketData bd, char match, int position) {
        IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
        if (pLastIsoRun.limit >= bd.openings.length) {
            Opening[] saveOpenings = bd.openings;
            try {
                int count = bd.openings.length;
                bd.openings = new Opening[(count * 2)];
                System.arraycopy(saveOpenings, 0, bd.openings, 0, count);
            } catch (Exception e) {
                throw new OutOfMemoryError("Failed to allocate memory for openings");
            }
        }
        Opening pOpening = bd.openings[pLastIsoRun.limit];
        if (pOpening == null) {
            Opening[] openingArr = bd.openings;
            short s = pLastIsoRun.limit;
            Opening opening = new Opening();
            openingArr[s] = opening;
            pOpening = opening;
        }
        pOpening.position = position;
        pOpening.match = match;
        pOpening.contextDir = pLastIsoRun.contextDir;
        pOpening.contextPos = pLastIsoRun.contextPos;
        pOpening.flags = 0;
        pLastIsoRun.limit = (short) (pLastIsoRun.limit + 1);
    }

    private void fixN0c(BracketData bd, int openingIndex, int newPropPosition, byte newProp) {
        IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
        for (int k = openingIndex + 1; k < pLastIsoRun.limit; k++) {
            Opening qOpening = bd.openings[k];
            if (qOpening.match < 0) {
                if (newPropPosition >= qOpening.contextPos) {
                    if (newPropPosition >= qOpening.position) {
                        continue;
                    } else if (newProp != qOpening.contextDir) {
                        int openingPosition = qOpening.position;
                        this.dirProps[openingPosition] = newProp;
                        int closingPosition = -qOpening.match;
                        this.dirProps[closingPosition] = newProp;
                        qOpening.match = 0;
                        fixN0c(bd, k, openingPosition, newProp);
                        fixN0c(bd, k, closingPosition, newProp);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    private byte bracketProcessClosing(BracketData bd, int openIdx, int position) {
        byte newProp;
        IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
        Opening pOpening = bd.openings[openIdx];
        byte direction2 = (byte) (pLastIsoRun.level & 1);
        boolean stable = true;
        if ((direction2 == 0 && (pOpening.flags & FOUND_L) > 0) || (direction2 == 1 && (pOpening.flags & FOUND_R) > 0)) {
            newProp = direction2;
        } else if ((pOpening.flags & (FOUND_L | FOUND_R)) != 0) {
            stable = openIdx == pLastIsoRun.start;
            if (direction2 != pOpening.contextDir) {
                newProp = pOpening.contextDir;
            } else {
                newProp = direction2;
            }
        } else {
            pLastIsoRun.limit = (short) openIdx;
            return ON;
        }
        this.dirProps[pOpening.position] = newProp;
        this.dirProps[position] = newProp;
        fixN0c(bd, openIdx, pOpening.position, newProp);
        if (stable) {
            pLastIsoRun.limit = (short) openIdx;
            while (pLastIsoRun.limit > pLastIsoRun.start && bd.openings[pLastIsoRun.limit - 1].position == pOpening.position) {
                pLastIsoRun.limit = (short) (pLastIsoRun.limit - 1);
            }
        } else {
            pOpening.match = -position;
            int k = openIdx - 1;
            while (k >= pLastIsoRun.start && bd.openings[k].position == pOpening.position) {
                bd.openings[k].match = 0;
                k--;
            }
            for (int k2 = openIdx + 1; k2 < pLastIsoRun.limit; k2++) {
                Opening qOpening = bd.openings[k2];
                if (qOpening.position >= position) {
                    break;
                }
                if (qOpening.match > 0) {
                    qOpening.match = 0;
                }
            }
        }
        return newProp;
    }

    private void bracketProcessChar(BracketData bd, int position) {
        byte newProp;
        char match;
        IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
        byte dirProp = this.dirProps[position];
        if (dirProp == 10) {
            char c = this.text[position];
            int idx = pLastIsoRun.limit - 1;
            while (true) {
                if (idx < pLastIsoRun.start) {
                    break;
                } else if (bd.openings[idx].match != c) {
                    idx--;
                } else {
                    byte newProp2 = bracketProcessClosing(bd, idx, position);
                    if (newProp2 == 10) {
                        c = 0;
                    } else {
                        pLastIsoRun.lastBase = ON;
                        pLastIsoRun.contextDir = newProp2;
                        pLastIsoRun.contextPos = position;
                        byte level = this.levels[position];
                        if ((level & Byte.MIN_VALUE) != 0) {
                            byte newProp3 = (byte) (level & 1);
                            pLastIsoRun.lastStrong = newProp3;
                            short flag = (short) DirPropFlag(newProp3);
                            for (int i = pLastIsoRun.start; i < idx; i++) {
                                Opening opening = bd.openings[i];
                                opening.flags = (short) (opening.flags | flag);
                            }
                            byte[] bArr = this.levels;
                            bArr[position] = (byte) (bArr[position] & LEVEL_DEFAULT_RTL);
                        }
                        byte[] bArr2 = this.levels;
                        int i2 = bd.openings[idx].position;
                        bArr2[i2] = (byte) (bArr2[i2] & LEVEL_DEFAULT_RTL);
                        return;
                    }
                }
            }
            if (c != 0) {
                match = (char) UCharacter.getBidiPairedBracket(c);
            } else {
                match = 0;
            }
            if (match != c && UCharacter.getIntPropertyValue(c, MtpConstants.OPERATION_GET_DEVICE_PROP_VALUE) == 1) {
                if (match == 9002) {
                    bracketAddOpening(bd, 12297, position);
                } else if (match == 12297) {
                    bracketAddOpening(bd, 9002, position);
                }
                bracketAddOpening(bd, match, position);
            }
        }
        byte level2 = this.levels[position];
        if ((level2 & Byte.MIN_VALUE) != 0) {
            newProp = (byte) (level2 & 1);
            if (!(dirProp == 8 || dirProp == 9 || dirProp == 10)) {
                this.dirProps[position] = newProp;
            }
            pLastIsoRun.lastBase = newProp;
            pLastIsoRun.lastStrong = newProp;
            pLastIsoRun.contextDir = newProp;
            pLastIsoRun.contextPos = position;
        } else if (dirProp <= 1 || dirProp == 13) {
            newProp = DirFromStrong(dirProp);
            pLastIsoRun.lastBase = dirProp;
            pLastIsoRun.lastStrong = dirProp;
            pLastIsoRun.contextDir = newProp;
            pLastIsoRun.contextPos = position;
        } else if (dirProp == 2) {
            pLastIsoRun.lastBase = 2;
            if (pLastIsoRun.lastStrong == 0) {
                if (!bd.isNumbersSpecial) {
                    this.dirProps[position] = ENL;
                }
                pLastIsoRun.contextDir = 0;
                pLastIsoRun.contextPos = position;
                newProp = 0;
            } else {
                newProp = 1;
                if (pLastIsoRun.lastStrong == 13) {
                    this.dirProps[position] = 5;
                } else {
                    this.dirProps[position] = ENR;
                }
                pLastIsoRun.contextDir = 1;
                pLastIsoRun.contextPos = position;
            }
        } else if (dirProp == 5) {
            newProp = 1;
            pLastIsoRun.lastBase = 5;
            pLastIsoRun.contextDir = 1;
            pLastIsoRun.contextPos = position;
        } else if (dirProp == 17) {
            newProp = pLastIsoRun.lastBase;
            if (newProp == 10) {
                this.dirProps[position] = newProp;
            }
        } else {
            newProp = dirProp;
            pLastIsoRun.lastBase = dirProp;
        }
        if (newProp <= 1 || newProp == 13) {
            short flag2 = (short) DirPropFlag(DirFromStrong(newProp));
            for (int i3 = pLastIsoRun.start; i3 < pLastIsoRun.limit; i3++) {
                if (position > bd.openings[i3].position) {
                    Opening opening2 = bd.openings[i3];
                    opening2.flags = (short) (opening2.flags | flag2);
                }
            }
        }
    }

    private byte directionFromFlags() {
        if ((this.flags & MASK_RTL) == 0 && ((this.flags & DirPropFlag((byte) 5)) == 0 || (this.flags & MASK_POSSIBLE_N) == 0)) {
            return 0;
        }
        if ((this.flags & MASK_LTR) == 0) {
            return 1;
        }
        return 2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte resolveExplicitLevels() {
        /*
            r20 = this;
            r0 = r20
            r1 = 0
            r2 = 0
            byte r3 = r0.GetParaLevelAt(r2)
            r0.isolateCount = r2
            byte r4 = r20.directionFromFlags()
            r5 = 2
            if (r4 == r5) goto L_0x0012
            return r4
        L_0x0012:
            int r5 = r0.reorderingMode
            r6 = 1
            if (r5 <= r6) goto L_0x003c
        L_0x0018:
            int r5 = r0.paraCount
            if (r2 >= r5) goto L_0x003b
            if (r2 != 0) goto L_0x0020
            r5 = 0
            goto L_0x0026
        L_0x0020:
            int[] r5 = r0.paras_limit
            int r6 = r2 + -1
            r5 = r5[r6]
        L_0x0026:
            int[] r6 = r0.paras_limit
            r6 = r6[r2]
            byte[] r7 = r0.paras_level
            byte r3 = r7[r2]
            r1 = r5
        L_0x002f:
            if (r1 >= r6) goto L_0x0038
            byte[] r7 = r0.levels
            r7[r1] = r3
            int r1 = r1 + 1
            goto L_0x002f
        L_0x0038:
            int r2 = r2 + 1
            goto L_0x0018
        L_0x003b:
            return r4
        L_0x003c:
            int r5 = r0.flags
            int r7 = MASK_EXPLICIT
            int r8 = MASK_ISO
            r7 = r7 | r8
            r5 = r5 & r7
            r7 = 13
            r8 = 7
            r9 = 10
            r10 = 18
            if (r5 != 0) goto L_0x009f
            com.ibm.icu.text.Bidi$BracketData r5 = new com.ibm.icu.text.Bidi$BracketData
            r5.<init>()
            r0.bracketInit(r5)
        L_0x0056:
            int r6 = r0.paraCount
            if (r2 >= r6) goto L_0x009e
            if (r2 != 0) goto L_0x005e
            r6 = 0
            goto L_0x0064
        L_0x005e:
            int[] r6 = r0.paras_limit
            int r11 = r2 + -1
            r6 = r6[r11]
        L_0x0064:
            int[] r11 = r0.paras_limit
            r11 = r11[r2]
            byte[] r12 = r0.paras_level
            byte r3 = r12[r2]
            r1 = r6
        L_0x006d:
            if (r1 >= r11) goto L_0x009b
            byte[] r12 = r0.levels
            r12[r1] = r3
            byte[] r12 = r0.dirProps
            byte r12 = r12[r1]
            if (r12 != r10) goto L_0x007a
            goto L_0x0098
        L_0x007a:
            if (r12 != r8) goto L_0x0095
            int r13 = r1 + 1
            int r14 = r0.length
            if (r13 >= r14) goto L_0x0098
            char[] r13 = r0.text
            char r13 = r13[r1]
            if (r13 != r7) goto L_0x0091
            char[] r13 = r0.text
            int r14 = r1 + 1
            char r13 = r13[r14]
            if (r13 != r9) goto L_0x0091
            goto L_0x0098
        L_0x0091:
            r0.bracketProcessB(r5, r3)
            goto L_0x0098
        L_0x0095:
            r0.bracketProcessChar(r5, r1)
        L_0x0098:
            int r1 = r1 + 1
            goto L_0x006d
        L_0x009b:
            int r2 = r2 + 1
            goto L_0x0056
        L_0x009e:
            return r4
        L_0x009f:
            r5 = r3
            r11 = r3
            r12 = 0
            r13 = 127(0x7f, float:1.78E-43)
            short[] r13 = new short[r13]
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            com.ibm.icu.text.Bidi$BracketData r18 = new com.ibm.icu.text.Bidi$BracketData
            r18.<init>()
            r19 = r18
            r7 = r19
            r0.bracketInit(r7)
            short r8 = (short) r3
            r13[r2] = r8
            r0.flags = r2
            r1 = 0
        L_0x00be:
            int r8 = r0.length
            if (r1 >= r8) goto L_0x02ba
            byte[] r8 = r0.dirProps
            byte r8 = r8[r1]
            r2 = 125(0x7d, float:1.75E-43)
            r10 = 256(0x100, float:3.59E-43)
            r18 = 9
            switch(r8) {
                case 7: goto L_0x024c;
                case 8: goto L_0x00cf;
                case 9: goto L_0x00cf;
                case 10: goto L_0x00cf;
                case 11: goto L_0x0200;
                case 12: goto L_0x0200;
                case 13: goto L_0x00cf;
                case 14: goto L_0x0200;
                case 15: goto L_0x0200;
                case 16: goto L_0x01d6;
                case 17: goto L_0x00cf;
                case 18: goto L_0x01c6;
                case 19: goto L_0x00cf;
                case 20: goto L_0x0154;
                case 21: goto L_0x0154;
                case 22: goto L_0x00f4;
                default: goto L_0x00cf;
            }
        L_0x00cf:
            r18 = r6
            byte r2 = NoOverride(r5)
            byte r6 = NoOverride(r11)
            if (r2 == r6) goto L_0x029b
            r0.bracketProcessBoundary(r7, r12, r11, r5)
            int r2 = r0.flags
            int r6 = DirPropFlagMultiRuns
            r2 = r2 | r6
            r0.flags = r2
            r2 = r5 & -128(0xffffffffffffff80, float:NaN)
            if (r2 == 0) goto L_0x0292
            int r2 = r0.flags
            int r6 = DirPropFlagO(r5)
            r2 = r2 | r6
            r0.flags = r2
            goto L_0x029b
        L_0x00f4:
            byte r2 = NoOverride(r5)
            byte r6 = NoOverride(r11)
            if (r2 == r6) goto L_0x0108
            r0.bracketProcessBoundary(r7, r12, r11, r5)
            int r2 = r0.flags
            int r6 = DirPropFlagMultiRuns
            r2 = r2 | r6
            r0.flags = r2
        L_0x0108:
            if (r15 <= 0) goto L_0x0111
            int r15 = r15 + -1
            byte[] r2 = r0.dirProps
            r2[r1] = r18
            goto L_0x0134
        L_0x0111:
            if (r17 <= 0) goto L_0x0130
            int r2 = r0.flags
            r6 = 22
            int r6 = DirPropFlag(r6)
            r2 = r2 | r6
            r0.flags = r2
            r12 = r1
            r16 = 0
        L_0x0121:
            short r2 = r13[r14]
            if (r2 >= r10) goto L_0x0128
            int r14 = r14 + -1
            goto L_0x0121
        L_0x0128:
            int r14 = r14 + -1
            int r17 = r17 + -1
            r0.bracketProcessPDI(r7)
            goto L_0x0134
        L_0x0130:
            byte[] r2 = r0.dirProps
            r2[r1] = r18
        L_0x0134:
            short r2 = r13[r14]
            r2 = r2 & -257(0xfffffffffffffeff, float:NaN)
            byte r2 = (byte) r2
            int r5 = r0.flags
            int r6 = DirPropFlag(r9)
            int r10 = DirPropFlagLR(r2)
            r6 = r6 | r10
            r5 = r5 | r6
            r0.flags = r5
            r5 = r2
            byte[] r6 = r0.levels
            byte r10 = NoOverride(r2)
            r6[r1] = r10
            r11 = r5
            goto L_0x01ee
        L_0x0154:
            int r6 = r0.flags
            int r10 = DirPropFlag(r9)
            int r19 = DirPropFlagLR(r5)
            r10 = r10 | r19
            r6 = r6 | r10
            r0.flags = r6
            byte[] r6 = r0.levels
            byte r10 = NoOverride(r5)
            r6[r1] = r10
            byte r6 = NoOverride(r5)
            byte r10 = NoOverride(r11)
            if (r6 == r10) goto L_0x017f
            r0.bracketProcessBoundary(r7, r12, r11, r5)
            int r6 = r0.flags
            int r10 = DirPropFlagMultiRuns
            r6 = r6 | r10
            r0.flags = r6
        L_0x017f:
            r6 = r5
            r10 = 20
            if (r8 != r10) goto L_0x018a
            int r10 = r5 + 2
            r10 = r10 & 126(0x7e, float:1.77E-43)
            byte r10 = (byte) r10
            goto L_0x0192
        L_0x018a:
            byte r10 = NoOverride(r5)
            r11 = 1
            int r10 = r10 + r11
            r10 = r10 | r11
            byte r10 = (byte) r10
        L_0x0192:
            if (r10 > r2) goto L_0x01bd
            if (r15 != 0) goto L_0x01bd
            if (r16 != 0) goto L_0x01bd
            int r2 = r0.flags
            int r11 = DirPropFlag(r8)
            r2 = r2 | r11
            r0.flags = r2
            r2 = r1
            int r11 = r17 + 1
            int r12 = r0.isolateCount
            if (r11 <= r12) goto L_0x01aa
            r0.isolateCount = r11
        L_0x01aa:
            r5 = r10
            int r14 = r14 + 1
            int r12 = r5 + 256
            short r12 = (short) r12
            r13[r14] = r12
            r0.bracketProcessLRI_RLI(r7, r5)
            r12 = r2
            r17 = r11
            r18 = 1
            r11 = r6
            goto L_0x02b1
        L_0x01bd:
            byte[] r2 = r0.dirProps
            r2[r1] = r18
            int r15 = r15 + 1
            r11 = r6
            goto L_0x01ee
        L_0x01c6:
            byte[] r2 = r0.levels
            r2[r1] = r11
            int r2 = r0.flags
            r6 = 18
            int r10 = DirPropFlag(r6)
            r2 = r2 | r10
            r0.flags = r2
            goto L_0x01ee
        L_0x01d6:
            r6 = 18
            int r2 = r0.flags
            int r18 = DirPropFlag(r6)
            r2 = r2 | r18
            r0.flags = r2
            byte[] r2 = r0.levels
            r2[r1] = r11
            if (r15 <= 0) goto L_0x01e9
            goto L_0x01ee
        L_0x01e9:
            if (r16 <= 0) goto L_0x01f2
            int r16 = r16 + -1
        L_0x01ee:
            r18 = 1
            goto L_0x02b1
        L_0x01f2:
            if (r14 <= 0) goto L_0x01ee
            short r2 = r13[r14]
            if (r2 >= r10) goto L_0x01ee
            r2 = r1
            int r14 = r14 + -1
            short r6 = r13[r14]
            byte r5 = (byte) r6
            r12 = r2
            goto L_0x01ee
        L_0x0200:
            int r6 = r0.flags
            r10 = 18
            int r18 = DirPropFlag(r10)
            r6 = r6 | r18
            r0.flags = r6
            byte[] r6 = r0.levels
            r6[r1] = r11
            r6 = 11
            r10 = 12
            if (r8 == r6) goto L_0x0225
            if (r8 != r10) goto L_0x0219
            goto L_0x0225
        L_0x0219:
            byte r6 = NoOverride(r5)
            r18 = 1
            int r6 = r6 + 1
            r6 = r6 | 1
            byte r6 = (byte) r6
            goto L_0x022c
        L_0x0225:
            r18 = 1
            int r6 = r5 + 2
            r6 = r6 & 126(0x7e, float:1.77E-43)
            byte r6 = (byte) r6
        L_0x022c:
            if (r6 > r2) goto L_0x0246
            if (r15 != 0) goto L_0x0246
            if (r16 != 0) goto L_0x0246
            r2 = r1
            r5 = r6
            if (r8 == r10) goto L_0x023b
            r10 = 15
            if (r8 != r10) goto L_0x023e
        L_0x023b:
            r10 = r5 | -128(0xffffffffffffff80, float:NaN)
            byte r5 = (byte) r10
        L_0x023e:
            int r14 = r14 + 1
            short r10 = (short) r5
            r13[r14] = r10
            r12 = r2
            goto L_0x02b1
        L_0x0246:
            if (r15 != 0) goto L_0x02b1
            int r16 = r16 + 1
            goto L_0x02b1
        L_0x024c:
            r18 = r6
            int r2 = r0.flags
            r6 = 7
            int r10 = DirPropFlag(r6)
            r2 = r2 | r10
            r0.flags = r2
            byte[] r2 = r0.levels
            byte r6 = r0.GetParaLevelAt(r1)
            r2[r1] = r6
            int r2 = r1 + 1
            int r6 = r0.length
            if (r2 >= r6) goto L_0x02b1
            char[] r2 = r0.text
            char r2 = r2[r1]
            r6 = 13
            if (r2 != r6) goto L_0x0277
            char[] r2 = r0.text
            int r10 = r1 + 1
            char r2 = r2[r10]
            if (r2 != r9) goto L_0x0277
            goto L_0x02b1
        L_0x0277:
            r2 = 0
            r10 = r2
            r15 = r2
            r2 = 0
            r14 = 0
            int r6 = r1 + 1
            byte r6 = r0.GetParaLevelAt(r6)
            r5 = r6
            short r11 = (short) r5
            r16 = 0
            r13[r16] = r11
            r0.bracketProcessB(r7, r5)
            r17 = r2
            r11 = r6
            r16 = r15
            r15 = r10
            goto L_0x02b1
        L_0x0292:
            int r2 = r0.flags
            int r6 = DirPropFlagE(r5)
            r2 = r2 | r6
            r0.flags = r2
        L_0x029b:
            r2 = r5
            byte[] r6 = r0.levels
            r6[r1] = r5
            r0.bracketProcessChar(r7, r1)
            int r6 = r0.flags
            byte[] r10 = r0.dirProps
            byte r10 = r10[r1]
            int r10 = DirPropFlag(r10)
            r6 = r6 | r10
            r0.flags = r6
            r11 = r2
        L_0x02b1:
            int r1 = r1 + 1
            r6 = r18
            r2 = 0
            r10 = 18
            goto L_0x00be
        L_0x02ba:
            int r2 = r0.flags
            int r6 = MASK_EMBEDDING
            r2 = r2 & r6
            if (r2 == 0) goto L_0x02cc
            int r2 = r0.flags
            byte r6 = r0.paraLevel
            int r6 = DirPropFlagLR(r6)
            r2 = r2 | r6
            r0.flags = r2
        L_0x02cc:
            boolean r2 = r0.orderParagraphsLTR
            if (r2 == 0) goto L_0x02e4
            int r2 = r0.flags
            r6 = 7
            int r6 = DirPropFlag(r6)
            r2 = r2 & r6
            if (r2 == 0) goto L_0x02e4
            int r2 = r0.flags
            r6 = 0
            int r6 = DirPropFlag(r6)
            r2 = r2 | r6
            r0.flags = r2
        L_0x02e4:
            byte r2 = r20.directionFromFlags()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.resolveExplicitLevels():byte");
    }

    private byte checkExplicitLevels() {
        int isolateCount2 = 0;
        this.flags = 0;
        this.isolateCount = 0;
        int currentParaIndex = 0;
        int currentParaLimit = this.paras_limit[0];
        byte currentParaLevel = this.paraLevel;
        for (int i = 0; i < this.length; i++) {
            byte level = this.levels[i];
            byte dirProp = this.dirProps[i];
            if (dirProp == 20 || dirProp == 21) {
                isolateCount2++;
                if (isolateCount2 > this.isolateCount) {
                    this.isolateCount = isolateCount2;
                }
            } else if (dirProp == 22) {
                isolateCount2--;
            } else if (dirProp == 7) {
                isolateCount2 = 0;
            }
            if (this.defaultParaLevel != 0 && i == currentParaLimit && currentParaIndex + 1 < this.paraCount) {
                currentParaIndex++;
                currentParaLevel = this.paras_level[currentParaIndex];
                currentParaLimit = this.paras_limit[currentParaIndex];
            }
            int overrideFlag = level & -128;
            byte level2 = (byte) (level & LEVEL_DEFAULT_RTL);
            if (level2 < currentParaLevel || 125 < level2) {
                if (level2 != 0) {
                    throw new IllegalArgumentException("level " + level2 + " out of bounds at " + i);
                } else if (dirProp != 7) {
                    level2 = currentParaLevel;
                    this.levels[i] = (byte) (level2 | overrideFlag);
                }
            }
            if (overrideFlag != 0) {
                this.flags |= DirPropFlagO(level2);
            } else {
                this.flags |= DirPropFlagE(level2) | DirPropFlag(dirProp);
            }
        }
        if ((this.flags & MASK_EMBEDDING) != 0) {
            this.flags |= DirPropFlagLR(this.paraLevel);
        }
        return directionFromFlags();
    }

    private static short GetStateProps(short cell) {
        return (short) (cell & 31);
    }

    private static short GetActionProps(short cell) {
        return (short) (cell >> 5);
    }

    private static short GetState(byte cell) {
        return (short) (cell & 15);
    }

    private static short GetAction(byte cell) {
        return (short) (cell >> 4);
    }

    private static class ImpTabPair {
        short[][] impact;
        byte[][][] imptab;

        ImpTabPair(byte[][] table1, byte[][] table2, short[] act1, short[] act2) {
            this.imptab = new byte[][][]{table1, table2};
            this.impact = new short[][]{act1, act2};
        }
    }

    private static class LevState {
        short[] impAct;
        byte[][] impTab;
        int lastStrongRTL;
        byte runLevel;
        int runStart;
        int startL2EN;
        int startON;
        short state;

        private LevState() {
        }
    }

    private void addPoint(int pos, int flag) {
        Point point = new Point();
        int len = this.insertPoints.points.length;
        if (len == 0) {
            this.insertPoints.points = new Point[10];
            len = 10;
        }
        if (this.insertPoints.size >= len) {
            Point[] savePoints = this.insertPoints.points;
            this.insertPoints.points = new Point[(len * 2)];
            System.arraycopy(savePoints, 0, this.insertPoints.points, 0, len);
        }
        point.pos = pos;
        point.flag = flag;
        this.insertPoints.points[this.insertPoints.size] = point;
        this.insertPoints.size++;
    }

    private void setLevelsOutsideIsolates(int start, int limit, byte level) {
        int isolateCount2 = 0;
        for (int k = start; k < limit; k++) {
            byte dirProp = this.dirProps[k];
            if (dirProp == 22) {
                isolateCount2--;
            }
            if (isolateCount2 == 0) {
                this.levels[k] = level;
            }
            if (dirProp == 20 || dirProp == 21) {
                isolateCount2++;
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processPropertySeq(com.ibm.icu.text.Bidi.LevState r19, short r20, int r21, int r22) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r22
            byte[][] r4 = r1.impTab
            short[] r5 = r1.impAct
            r6 = r21
            short r7 = r1.state
            r8 = r4[r7]
            byte r8 = r8[r2]
            short r9 = GetState(r8)
            r1.state = r9
            short r9 = GetAction(r8)
            short r9 = r5[r9]
            short r10 = r1.state
            r10 = r4[r10]
            r11 = 7
            byte r10 = r10[r11]
            if (r9 == 0) goto L_0x01ff
            r13 = 4
            r14 = 5
            r15 = 2
            r11 = -1
            r12 = 1
            switch(r9) {
                case 1: goto L_0x01fa;
                case 2: goto L_0x01f3;
                case 3: goto L_0x01e6;
                case 4: goto L_0x01da;
                case 5: goto L_0x0168;
                case 6: goto L_0x014d;
                case 7: goto L_0x011a;
                case 8: goto L_0x0110;
                case 9: goto L_0x00ef;
                case 10: goto L_0x00e5;
                case 11: goto L_0x00cc;
                case 12: goto L_0x00a9;
                case 13: goto L_0x005a;
                case 14: goto L_0x003b;
                default: goto L_0x0031;
            }
        L_0x0031:
            r17 = r5
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r11 = "Internal ICU error in processPropertySeq"
            r5.<init>(r11)
            throw r5
        L_0x003b:
            byte r11 = r1.runLevel
            int r11 = r11 + r12
            byte r11 = (byte) r11
            int r12 = r6 + -1
        L_0x0041:
            int r13 = r1.startON
            if (r12 < r13) goto L_0x0056
            byte[] r13 = r0.levels
            byte r13 = r13[r12]
            if (r13 <= r11) goto L_0x0053
            byte[] r13 = r0.levels
            byte r14 = r13[r12]
            int r14 = r14 - r15
            byte r14 = (byte) r14
            r13[r12] = r14
        L_0x0053:
            int r12 = r12 + -1
            goto L_0x0041
        L_0x0056:
            r17 = r5
            goto L_0x0201
        L_0x005a:
            byte r11 = r1.runLevel
            int r12 = r6 + -1
        L_0x005e:
            int r13 = r1.startON
            if (r12 < r13) goto L_0x01ff
            byte[] r13 = r0.levels
            byte r13 = r13[r12]
            int r14 = r11 + 3
            if (r13 != r14) goto L_0x008e
        L_0x006a:
            byte[] r13 = r0.levels
            byte r13 = r13[r12]
            int r14 = r11 + 3
            if (r13 != r14) goto L_0x0083
            byte[] r13 = r0.levels
            int r14 = r12 + -1
            byte r16 = r13[r12]
            r17 = r5
            int r5 = r16 + -2
            byte r5 = (byte) r5
            r13[r12] = r5
            r12 = r14
            r5 = r17
            goto L_0x006a
        L_0x0083:
            r17 = r5
        L_0x0085:
            byte[] r5 = r0.levels
            byte r5 = r5[r12]
            if (r5 != r11) goto L_0x0090
            int r12 = r12 + -1
            goto L_0x0085
        L_0x008e:
            r17 = r5
        L_0x0090:
            byte[] r5 = r0.levels
            byte r5 = r5[r12]
            int r13 = r11 + 2
            if (r5 != r13) goto L_0x009d
            byte[] r5 = r0.levels
            r5[r12] = r11
            goto L_0x00a4
        L_0x009d:
            byte[] r5 = r0.levels
            int r13 = r11 + 1
            byte r13 = (byte) r13
            r5[r12] = r13
        L_0x00a4:
            int r12 = r12 + -1
            r5 = r17
            goto L_0x005e
        L_0x00a9:
            r17 = r5
            byte r5 = r1.runLevel
            int r5 = r5 + r10
            byte r5 = (byte) r5
            int r11 = r1.startON
        L_0x00b1:
            if (r11 >= r6) goto L_0x00c0
            byte[] r12 = r0.levels
            byte r12 = r12[r11]
            if (r12 >= r5) goto L_0x00bd
            byte[] r12 = r0.levels
            r12[r11] = r5
        L_0x00bd:
            int r11 = r11 + 1
            goto L_0x00b1
        L_0x00c0:
            com.ibm.icu.text.Bidi$InsertPoints r12 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r13 = r0.insertPoints
            int r13 = r13.size
            r12.confirmed = r13
            r1.startON = r6
            goto L_0x0201
        L_0x00cc:
            r17 = r5
            com.ibm.icu.text.Bidi$InsertPoints r5 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r11 = r0.insertPoints
            int r11 = r11.confirmed
            r5.size = r11
            if (r2 != r14) goto L_0x0201
            r0.addPoint(r6, r13)
            com.ibm.icu.text.Bidi$InsertPoints r5 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r11 = r0.insertPoints
            int r11 = r11.size
            r5.confirmed = r11
            goto L_0x0201
        L_0x00e5:
            r17 = r5
            r0.addPoint(r6, r12)
            r0.addPoint(r6, r15)
            goto L_0x0201
        L_0x00ef:
            r17 = r5
            int r5 = r6 + -1
        L_0x00f3:
            if (r5 < 0) goto L_0x00ff
            byte[] r11 = r0.levels
            byte r11 = r11[r5]
            r11 = r11 & r12
            if (r11 != 0) goto L_0x00ff
            int r5 = r5 + -1
            goto L_0x00f3
        L_0x00ff:
            if (r5 < 0) goto L_0x010c
            r0.addPoint(r5, r13)
            com.ibm.icu.text.Bidi$InsertPoints r11 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r12 = r0.insertPoints
            int r12 = r12.size
            r11.confirmed = r12
        L_0x010c:
            r1.startON = r6
            goto L_0x0201
        L_0x0110:
            r17 = r5
            int r5 = r3 + -1
            r1.lastStrongRTL = r5
            r1.startON = r11
            goto L_0x0201
        L_0x011a:
            r17 = r5
            r5 = 3
            if (r2 != r5) goto L_0x0145
            byte[] r5 = r0.dirProps
            byte r5 = r5[r6]
            if (r5 != r14) goto L_0x0145
            int r5 = r0.reorderingMode
            r13 = 6
            if (r5 == r13) goto L_0x0145
            int r5 = r1.startL2EN
            if (r5 != r11) goto L_0x0134
            int r5 = r3 + -1
            r1.lastStrongRTL = r5
            goto L_0x0201
        L_0x0134:
            int r5 = r1.startL2EN
            if (r5 < 0) goto L_0x0140
            int r5 = r1.startL2EN
            r0.addPoint(r5, r12)
            r5 = -2
            r1.startL2EN = r5
        L_0x0140:
            r0.addPoint(r6, r12)
            goto L_0x0201
        L_0x0145:
            int r5 = r1.startL2EN
            if (r5 != r11) goto L_0x0201
            r1.startL2EN = r6
            goto L_0x0201
        L_0x014d:
            r17 = r5
            com.ibm.icu.text.Bidi$InsertPoints r5 = r0.insertPoints
            com.ibm.icu.text.Bidi$Point[] r5 = r5.points
            int r5 = r5.length
            if (r5 <= 0) goto L_0x015e
            com.ibm.icu.text.Bidi$InsertPoints r5 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r12 = r0.insertPoints
            int r12 = r12.confirmed
            r5.size = r12
        L_0x015e:
            r1.startON = r11
            r1.startL2EN = r11
            int r5 = r3 + -1
            r1.lastStrongRTL = r5
            goto L_0x0201
        L_0x0168:
            r17 = r5
            int r5 = r1.startL2EN
            if (r5 < 0) goto L_0x0173
            int r5 = r1.startL2EN
            r0.addPoint(r5, r12)
        L_0x0173:
            r1.startL2EN = r11
            com.ibm.icu.text.Bidi$InsertPoints r5 = r0.insertPoints
            com.ibm.icu.text.Bidi$Point[] r5 = r5.points
            int r5 = r5.length
            if (r5 == 0) goto L_0x01b7
            com.ibm.icu.text.Bidi$InsertPoints r5 = r0.insertPoints
            int r5 = r5.size
            com.ibm.icu.text.Bidi$InsertPoints r13 = r0.insertPoints
            int r13 = r13.confirmed
            if (r5 > r13) goto L_0x0187
            goto L_0x01b7
        L_0x0187:
            int r5 = r1.lastStrongRTL
            int r5 = r5 + r12
        L_0x018a:
            if (r5 >= r6) goto L_0x019e
            byte[] r13 = r0.levels
            byte[] r12 = r0.levels
            byte r12 = r12[r5]
            int r12 = r12 - r15
            r16 = -2
            r12 = r12 & -2
            byte r12 = (byte) r12
            r13[r5] = r12
            int r5 = r5 + 1
            r12 = 1
            goto L_0x018a
        L_0x019e:
            com.ibm.icu.text.Bidi$InsertPoints r12 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r13 = r0.insertPoints
            int r13 = r13.size
            r12.confirmed = r13
            r1.lastStrongRTL = r11
            if (r2 != r14) goto L_0x0201
            r11 = 1
            r0.addPoint(r6, r11)
            com.ibm.icu.text.Bidi$InsertPoints r11 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r12 = r0.insertPoints
            int r12 = r12.size
            r11.confirmed = r12
            goto L_0x0201
        L_0x01b7:
            r1.lastStrongRTL = r11
            r5 = r4[r7]
            r11 = 7
            byte r5 = r5[r11]
            r11 = r5 & 1
            if (r11 == 0) goto L_0x01c9
            int r11 = r1.startON
            if (r11 <= 0) goto L_0x01c9
            int r11 = r1.startON
            goto L_0x01cb
        L_0x01c9:
            r11 = r21
        L_0x01cb:
            if (r2 != r14) goto L_0x0203
            r12 = 1
            r0.addPoint(r6, r12)
            com.ibm.icu.text.Bidi$InsertPoints r12 = r0.insertPoints
            com.ibm.icu.text.Bidi$InsertPoints r13 = r0.insertPoints
            int r13 = r13.size
            r12.confirmed = r13
            goto L_0x0203
        L_0x01da:
            r17 = r5
            byte r5 = r1.runLevel
            int r5 = r5 + r15
            byte r5 = (byte) r5
            int r11 = r1.startON
            r0.setLevelsOutsideIsolates(r11, r6, r5)
            goto L_0x0201
        L_0x01e6:
            r17 = r5
            byte r5 = r1.runLevel
            r11 = 1
            int r5 = r5 + r11
            byte r5 = (byte) r5
            int r11 = r1.startON
            r0.setLevelsOutsideIsolates(r11, r6, r5)
            goto L_0x0201
        L_0x01f3:
            r17 = r5
            int r5 = r1.startON
            r11 = r5
            goto L_0x0203
        L_0x01fa:
            r17 = r5
            r1.startON = r6
            goto L_0x0201
        L_0x01ff:
            r17 = r5
        L_0x0201:
            r11 = r21
        L_0x0203:
            if (r10 != 0) goto L_0x0207
            if (r11 >= r6) goto L_0x021c
        L_0x0207:
            byte r5 = r1.runLevel
            int r5 = r5 + r10
            byte r5 = (byte) r5
            int r12 = r1.runStart
            if (r11 < r12) goto L_0x0219
            r12 = r11
        L_0x0210:
            if (r12 >= r3) goto L_0x021c
            byte[] r13 = r0.levels
            r13[r12] = r5
            int r12 = r12 + 1
            goto L_0x0210
        L_0x0219:
            r0.setLevelsOutsideIsolates(r11, r3, r5)
        L_0x021c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.processPropertySeq(com.ibm.icu.text.Bidi$LevState, short, int, int):void");
    }

    private byte lastL_R_AL() {
        int i = this.prologue.length();
        while (i > 0) {
            int uchar = this.prologue.codePointBefore(i);
            i -= Character.charCount(uchar);
            byte dirProp = (byte) getCustomizedClass(uchar);
            if (dirProp == 0) {
                return 0;
            }
            if (dirProp == 1 || dirProp == 13) {
                return 1;
            }
            if (dirProp == 7) {
                return 4;
            }
        }
        return 4;
    }

    private byte firstL_R_AL_EN_AN() {
        int i = 0;
        while (i < this.epilogue.length()) {
            int uchar = this.epilogue.codePointAt(i);
            i += Character.charCount(uchar);
            byte dirProp = (byte) getCustomizedClass(uchar);
            if (dirProp == 0) {
                return 0;
            }
            if (dirProp == 1 || dirProp == 13) {
                return 1;
            }
            if (dirProp == 2) {
                return 2;
            }
            if (dirProp == 5) {
                return 3;
            }
        }
        return 4;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=short, code=int, for r8v13, types: [short] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void resolveImplicitLevels(int r22, int r23, short r24, short r25) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r23
            com.ibm.icu.text.Bidi$LevState r3 = new com.ibm.icu.text.Bidi$LevState
            r4 = 0
            r3.<init>()
            r4 = 1
            r5 = -1
            int r6 = r0.lastArabicPos
            r7 = 0
            r8 = 1
            if (r1 >= r6) goto L_0x0027
            byte r6 = r21.GetParaLevelAt(r22)
            r6 = r6 & r8
            if (r6 <= 0) goto L_0x0027
            int r6 = r0.reorderingMode
            r9 = 5
            if (r6 == r9) goto L_0x0025
            int r6 = r0.reorderingMode
            r9 = 6
            if (r6 != r9) goto L_0x0027
        L_0x0025:
            r6 = r8
            goto L_0x0028
        L_0x0027:
            r6 = r7
        L_0x0028:
            r9 = -1
            r3.startL2EN = r9
            r3.lastStrongRTL = r9
            r3.runStart = r1
            byte[] r10 = r0.levels
            byte r10 = r10[r1]
            r3.runLevel = r10
            com.ibm.icu.text.Bidi$ImpTabPair r10 = r0.impTabPair
            byte[][][] r10 = r10.imptab
            byte r11 = r3.runLevel
            r11 = r11 & r8
            r10 = r10[r11]
            r3.impTab = r10
            com.ibm.icu.text.Bidi$ImpTabPair r10 = r0.impTabPair
            short[][] r10 = r10.impact
            byte r11 = r3.runLevel
            r11 = r11 & r8
            r10 = r10[r11]
            r3.impAct = r10
            r10 = 4
            if (r1 != 0) goto L_0x005a
            java.lang.String r11 = r0.prologue
            if (r11 == 0) goto L_0x005a
            byte r11 = r21.lastL_R_AL()
            if (r11 == r10) goto L_0x005a
            short r11 = (short) r11
            goto L_0x005c
        L_0x005a:
            r11 = r24
        L_0x005c:
            byte[] r12 = r0.dirProps
            byte r12 = r12[r1]
            r13 = 22
            if (r12 != r13) goto L_0x008e
            com.ibm.icu.text.Bidi$Isolate[] r7 = r0.isolates
            int r12 = r0.isolateCount
            r7 = r7[r12]
            int r7 = r7.startON
            r3.startON = r7
            com.ibm.icu.text.Bidi$Isolate[] r7 = r0.isolates
            int r12 = r0.isolateCount
            r7 = r7[r12]
            int r7 = r7.start1
            com.ibm.icu.text.Bidi$Isolate[] r12 = r0.isolates
            int r13 = r0.isolateCount
            r12 = r12[r13]
            short r12 = r12.stateImp
            com.ibm.icu.text.Bidi$Isolate[] r13 = r0.isolates
            int r14 = r0.isolateCount
            r13 = r13[r14]
            short r13 = r13.state
            r3.state = r13
            int r13 = r0.isolateCount
            int r13 = r13 - r8
            r0.isolateCount = r13
            goto L_0x00a6
        L_0x008e:
            r3.startON = r9
            r12 = r22
            byte[] r13 = r0.dirProps
            byte r13 = r13[r1]
            r14 = 17
            if (r13 != r14) goto L_0x009e
            int r13 = r11 + 1
            short r13 = (short) r13
            goto L_0x009f
        L_0x009e:
            r13 = r7
        L_0x009f:
            r3.state = r7
            r0.processPropertySeq(r3, r11, r1, r1)
            r7 = r12
            r12 = r13
        L_0x00a6:
            r13 = r22
            r14 = r13
            r13 = r7
            r7 = r4
            r4 = r1
        L_0x00ac:
            r15 = 21
            r10 = 20
            if (r4 > r2) goto L_0x0180
            if (r4 < r2) goto L_0x00df
            int r16 = r2 + -1
        L_0x00b6:
            r17 = r16
            r8 = r17
            if (r8 <= r1) goto L_0x00cf
            byte[] r9 = r0.dirProps
            byte r9 = r9[r8]
            int r9 = DirPropFlag(r9)
            int r16 = MASK_BN_EXPLICIT
            r9 = r9 & r16
            if (r9 == 0) goto L_0x00cf
            int r16 = r8 + -1
            r8 = 1
            r9 = -1
            goto L_0x00b6
        L_0x00cf:
            byte[] r9 = r0.dirProps
            byte r9 = r9[r8]
            if (r9 == r10) goto L_0x00db
            if (r9 != r15) goto L_0x00d8
            goto L_0x00db
        L_0x00d8:
            r8 = r25
            goto L_0x011f
        L_0x00db:
            r19 = r6
            goto L_0x0182
        L_0x00df:
            byte[] r8 = r0.dirProps
            byte r8 = r8[r4]
            r9 = 7
            if (r8 != r9) goto L_0x00ea
            r9 = -1
            r0.isolateCount = r9
            goto L_0x00eb
        L_0x00ea:
            r9 = -1
        L_0x00eb:
            if (r6 == 0) goto L_0x011b
            r10 = 13
            if (r8 != r10) goto L_0x00f3
            r8 = 1
            goto L_0x011b
        L_0x00f3:
            r15 = 2
            if (r8 != r15) goto L_0x011b
            if (r5 > r4) goto L_0x0116
            r7 = 1
            r5 = r23
            int r15 = r4 + 1
        L_0x00fd:
            if (r15 >= r2) goto L_0x0116
            byte[] r9 = r0.dirProps
            byte r9 = r9[r15]
            if (r9 == 0) goto L_0x0113
            r10 = 1
            if (r9 == r10) goto L_0x0113
            r10 = 13
            if (r9 != r10) goto L_0x010d
            goto L_0x0113
        L_0x010d:
            int r15 = r15 + 1
            r9 = -1
            r10 = 13
            goto L_0x00fd
        L_0x0113:
            short r7 = (short) r9
            r5 = r15
        L_0x0116:
            r9 = 13
            if (r7 != r9) goto L_0x011b
            r8 = 5
        L_0x011b:
            short[] r9 = groupProp
            short r8 = r9[r8]
        L_0x011f:
            r9 = r12
            short[][] r10 = impTabProps
            r10 = r10[r9]
            short r10 = r10[r8]
            short r12 = GetStateProps(r10)
            short r15 = GetActionProps(r10)
            if (r4 != r2) goto L_0x0133
            if (r15 != 0) goto L_0x0133
            r15 = 1
        L_0x0133:
            if (r15 == 0) goto L_0x0171
            short[][] r16 = impTabProps
            r16 = r16[r9]
            r17 = 15
            r18 = r5
            short r5 = r16[r17]
            switch(r15) {
                case 1: goto L_0x0168;
                case 2: goto L_0x0162;
                case 3: goto L_0x0157;
                case 4: goto L_0x014e;
                default: goto L_0x0142;
            }
        L_0x0142:
            r19 = r6
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r20 = r5
            java.lang.String r5 = "Internal ICU error in resolveImplicitLevels"
            r6.<init>(r5)
            throw r6
        L_0x014e:
            r0.processPropertySeq(r3, r5, r13, r14)
            r13 = r14
            r14 = r4
            r19 = r6
            goto L_0x0175
        L_0x0157:
            r0.processPropertySeq(r3, r5, r13, r14)
            r19 = r6
            r6 = 4
            r0.processPropertySeq(r3, r6, r14, r4)
            r6 = r4
            goto L_0x016f
        L_0x0162:
            r19 = r6
            r6 = r4
            r14 = r6
            goto L_0x0175
        L_0x0168:
            r19 = r6
            r0.processPropertySeq(r3, r5, r13, r4)
            r6 = r4
        L_0x016f:
            r13 = r6
            goto L_0x0175
        L_0x0171:
            r18 = r5
            r19 = r6
        L_0x0175:
            int r4 = r4 + 1
            r5 = r18
            r6 = r19
            r8 = 1
            r9 = -1
            r10 = 4
            goto L_0x00ac
        L_0x0180:
            r19 = r6
        L_0x0182:
            int r6 = r0.length
            if (r2 != r6) goto L_0x0193
            java.lang.String r6 = r0.epilogue
            if (r6 == 0) goto L_0x0193
            byte r6 = r21.firstL_R_AL_EN_AN()
            r8 = 4
            if (r6 == r8) goto L_0x0193
            short r6 = (short) r6
            goto L_0x0195
        L_0x0193:
            r6 = r25
        L_0x0195:
            int r4 = r2 + -1
        L_0x0197:
            if (r4 <= r1) goto L_0x01a9
            byte[] r8 = r0.dirProps
            byte r8 = r8[r4]
            int r8 = DirPropFlag(r8)
            int r9 = MASK_BN_EXPLICIT
            r8 = r8 & r9
            if (r8 == 0) goto L_0x01a9
            int r4 = r4 + -1
            goto L_0x0197
        L_0x01a9:
            byte[] r8 = r0.dirProps
            byte r8 = r8[r4]
            if (r8 == r10) goto L_0x01b1
            if (r8 != r15) goto L_0x01f3
        L_0x01b1:
            int r9 = r0.length
            if (r2 >= r9) goto L_0x01f3
            int r9 = r0.isolateCount
            r10 = 1
            int r9 = r9 + r10
            r0.isolateCount = r9
            com.ibm.icu.text.Bidi$Isolate[] r9 = r0.isolates
            int r10 = r0.isolateCount
            r9 = r9[r10]
            if (r9 != 0) goto L_0x01ce
            com.ibm.icu.text.Bidi$Isolate[] r9 = r0.isolates
            int r10 = r0.isolateCount
            com.ibm.icu.text.Bidi$Isolate r15 = new com.ibm.icu.text.Bidi$Isolate
            r15.<init>()
            r9[r10] = r15
        L_0x01ce:
            com.ibm.icu.text.Bidi$Isolate[] r9 = r0.isolates
            int r10 = r0.isolateCount
            r9 = r9[r10]
            r9.stateImp = r12
            com.ibm.icu.text.Bidi$Isolate[] r9 = r0.isolates
            int r10 = r0.isolateCount
            r9 = r9[r10]
            short r10 = r3.state
            r9.state = r10
            com.ibm.icu.text.Bidi$Isolate[] r9 = r0.isolates
            int r10 = r0.isolateCount
            r9 = r9[r10]
            r9.start1 = r13
            com.ibm.icu.text.Bidi$Isolate[] r9 = r0.isolates
            int r10 = r0.isolateCount
            r9 = r9[r10]
            int r10 = r3.startON
            r9.startON = r10
            goto L_0x01f6
        L_0x01f3:
            r0.processPropertySeq(r3, r6, r2, r2)
        L_0x01f6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.resolveImplicitLevels(int, int, short, short):void");
    }

    private void adjustWSLevels() {
        if ((this.flags & MASK_WS) != 0) {
            int i = this.trailingWSStart;
            while (i > 0) {
                while (i > 0) {
                    i--;
                    int DirPropFlag = DirPropFlag(this.dirProps[i]);
                    int flag = DirPropFlag;
                    if ((DirPropFlag & MASK_WS) == 0) {
                        break;
                    } else if (!this.orderParagraphsLTR || (DirPropFlag((byte) 7) & flag) == 0) {
                        this.levels[i] = GetParaLevelAt(i);
                    } else {
                        this.levels[i] = 0;
                    }
                }
                while (true) {
                    if (i <= 0) {
                        break;
                    }
                    i--;
                    int flag2 = DirPropFlag(this.dirProps[i]);
                    if ((MASK_BN_EXPLICIT & flag2) == 0) {
                        if (this.orderParagraphsLTR && (DirPropFlag((byte) 7) & flag2) != 0) {
                            this.levels[i] = 0;
                            break;
                        } else if ((MASK_B_S & flag2) != 0) {
                            this.levels[i] = GetParaLevelAt(i);
                            break;
                        }
                    } else {
                        this.levels[i] = this.levels[i + 1];
                    }
                }
            }
        }
    }

    public void setContext(String prologue2, String epilogue2) {
        String str = null;
        this.prologue = (prologue2 == null || prologue2.length() <= 0) ? null : prologue2;
        if (epilogue2 != null && epilogue2.length() > 0) {
            str = epilogue2;
        }
        this.epilogue = str;
    }

    private void setParaSuccess() {
        this.prologue = null;
        this.epilogue = null;
        this.paraBidi = this;
    }

    /* access modifiers changed from: package-private */
    public int Bidi_Min(int x, int y) {
        return x < y ? x : y;
    }

    /* access modifiers changed from: package-private */
    public int Bidi_Abs(int x) {
        return x >= 0 ? x : -x;
    }

    /* access modifiers changed from: package-private */
    public void setParaRunsOnly(char[] parmText, byte parmParaLevel) {
        int saveTrailingWSStart;
        int oldRunCount;
        String visualText;
        int step;
        int limit;
        int start;
        int saveTrailingWSStart2;
        byte parmParaLevel2;
        char[] cArr = parmText;
        byte b = parmParaLevel;
        this.reorderingMode = 0;
        int parmLength = cArr.length;
        if (parmLength == 0) {
            setPara(cArr, b, (byte[]) null);
            this.reorderingMode = 3;
            return;
        }
        int saveOptions = this.reorderingOptions;
        int i = 2;
        if ((saveOptions & 1) > 0) {
            this.reorderingOptions &= -2;
            this.reorderingOptions |= 2;
        }
        byte parmParaLevel3 = (byte) (b & 1);
        setPara(cArr, parmParaLevel3, (byte[]) null);
        byte[] saveLevels = new byte[this.length];
        System.arraycopy(getLevels(), 0, saveLevels, 0, this.length);
        int saveTrailingWSStart3 = this.trailingWSStart;
        String visualText2 = writeReordered(2);
        int[] visualMap = getVisualMap();
        this.reorderingOptions = saveOptions;
        int saveLength = this.length;
        byte saveDirection = this.direction;
        this.reorderingMode = 5;
        byte parmParaLevel4 = (byte) (parmParaLevel3 ^ 1);
        setPara(visualText2, parmParaLevel4, (byte[]) null);
        BidiLine.getRuns(this);
        int oldRunCount2 = this.runCount;
        int visualStart = 0;
        int addedRuns = 0;
        int i2 = 0;
        while (i2 < oldRunCount2) {
            int runLength = this.runs[i2].limit - visualStart;
            if (runLength >= i) {
                int logicalStart = this.runs[i2].start;
                int index1 = logicalStart + 1;
                while (true) {
                    parmParaLevel2 = parmParaLevel4;
                    int j = index1;
                    if (j >= logicalStart + runLength) {
                        break;
                    }
                    int index = visualMap[j];
                    int index12 = visualMap[j - 1];
                    int saveOptions2 = saveOptions;
                    int logicalStart2 = logicalStart;
                    if (Bidi_Abs(index - index12) != 1 || saveLevels[index] != saveLevels[index12]) {
                        addedRuns++;
                    }
                    index1 = j + 1;
                    parmParaLevel4 = parmParaLevel2;
                    saveOptions = saveOptions2;
                    logicalStart = logicalStart2;
                }
            } else {
                parmParaLevel2 = parmParaLevel4;
            }
            i2++;
            visualStart += runLength;
            parmParaLevel4 = parmParaLevel2;
            saveOptions = saveOptions;
            i = 2;
        }
        int i3 = saveOptions;
        if (addedRuns > 0) {
            getRunsMemory(oldRunCount2 + addedRuns);
            if (this.runCount == 1) {
                this.runsMemory[0] = this.runs[0];
            } else {
                System.arraycopy(this.runs, 0, this.runsMemory, 0, this.runCount);
            }
            this.runs = this.runsMemory;
            this.runCount += addedRuns;
            for (int i4 = oldRunCount2; i4 < this.runCount; i4++) {
                if (this.runs[i4] == null) {
                    this.runs[i4] = new BidiRun(0, 0, (byte) 0);
                }
            }
        }
        int i5 = oldRunCount2 - 1;
        while (i5 >= 0) {
            int newI = i5 + addedRuns;
            int runLength2 = i5 == 0 ? this.runs[0].limit : this.runs[i5].limit - this.runs[i5 - 1].limit;
            int logicalStart3 = this.runs[i5].start;
            int indexOddBit = this.runs[i5].level & 1;
            if (runLength2 < 2) {
                if (addedRuns > 0) {
                    visualText = visualText2;
                    this.runs[newI].copyFrom(this.runs[i5]);
                } else {
                    visualText = visualText2;
                }
                int logicalPos = visualMap[logicalStart3];
                this.runs[newI].start = logicalPos;
                int i6 = newI;
                this.runs[newI].level = (byte) (saveLevels[logicalPos] ^ indexOddBit);
                int i7 = runLength2;
                int runLength3 = logicalPos;
                int i8 = logicalStart3;
                saveTrailingWSStart = saveTrailingWSStart3;
                oldRunCount = oldRunCount2;
            } else {
                int newI2 = newI;
                visualText = visualText2;
                if (indexOddBit > 0) {
                    start = logicalStart3;
                    limit = (logicalStart3 + runLength2) - 1;
                    step = 1;
                } else {
                    start = (logicalStart3 + runLength2) - 1;
                    limit = logicalStart3;
                    step = -1;
                }
                int step2 = step;
                int start2 = start;
                while (start != limit) {
                    int runLength4 = runLength2;
                    int runLength5 = visualMap[start];
                    int index13 = visualMap[start + step2];
                    int logicalStart4 = logicalStart3;
                    int oldRunCount3 = oldRunCount2;
                    if (Bidi_Abs(runLength5 - index13) == 1 && saveLevels[runLength5] == saveLevels[index13]) {
                        int i9 = runLength5;
                        saveTrailingWSStart2 = saveTrailingWSStart3;
                    } else {
                        int logicalPos2 = Bidi_Min(visualMap[start2], runLength5);
                        this.runs[newI2].start = logicalPos2;
                        int i10 = runLength5;
                        this.runs[newI2].level = (byte) (saveLevels[logicalPos2] ^ indexOddBit);
                        this.runs[newI2].limit = this.runs[i5].limit;
                        int i11 = logicalPos2;
                        this.runs[i5].limit -= Bidi_Abs(start - start2) + 1;
                        int insertRemove = this.runs[i5].insertRemove & 10;
                        this.runs[newI2].insertRemove = insertRemove;
                        BidiRun bidiRun = this.runs[i5];
                        saveTrailingWSStart2 = saveTrailingWSStart3;
                        bidiRun.insertRemove = (~insertRemove) & bidiRun.insertRemove;
                        addedRuns--;
                        newI2--;
                        start2 = start + step2;
                    }
                    start += step2;
                    runLength2 = runLength4;
                    logicalStart3 = logicalStart4;
                    oldRunCount2 = oldRunCount3;
                    saveTrailingWSStart3 = saveTrailingWSStart2;
                }
                int i12 = logicalStart3;
                saveTrailingWSStart = saveTrailingWSStart3;
                oldRunCount = oldRunCount2;
                if (addedRuns > 0) {
                    this.runs[newI2].copyFrom(this.runs[i5]);
                }
                int logicalPos3 = Bidi_Min(visualMap[start2], visualMap[limit]);
                this.runs[newI2].start = logicalPos3;
                this.runs[newI2].level = (byte) (saveLevels[logicalPos3] ^ indexOddBit);
            }
            i5--;
            visualText2 = visualText;
            oldRunCount2 = oldRunCount;
            saveTrailingWSStart3 = saveTrailingWSStart;
        }
        String str = visualText2;
        int i13 = oldRunCount2;
        this.paraLevel = (byte) (this.paraLevel ^ 1);
        this.text = cArr;
        this.length = saveLength;
        this.originalLength = parmLength;
        this.direction = saveDirection;
        this.levels = saveLevels;
        this.trailingWSStart = saveTrailingWSStart3;
        if (this.runCount > 1) {
            this.direction = 2;
        }
        this.reorderingMode = 3;
    }

    public void setPara(String text2, byte paraLevel2, byte[] embeddingLevels) {
        if (text2 == null) {
            setPara(new char[0], paraLevel2, embeddingLevels);
        } else {
            setPara(text2.toCharArray(), paraLevel2, embeddingLevels);
        }
    }

    public void setPara(char[] chars, byte paraLevel2, byte[] embeddingLevels) {
        short eor;
        short sor;
        short eor2;
        if (paraLevel2 < 126) {
            verifyRange(paraLevel2, 0, 126);
        }
        if (chars == null) {
            chars = new char[0];
        }
        if (this.reorderingMode == 3) {
            setParaRunsOnly(chars, paraLevel2);
            return;
        }
        this.paraBidi = null;
        this.text = chars;
        int length2 = this.text.length;
        this.resultLength = length2;
        this.originalLength = length2;
        this.length = length2;
        this.paraLevel = paraLevel2;
        this.direction = (byte) (paraLevel2 & 1);
        this.paraCount = 1;
        this.dirProps = new byte[0];
        this.levels = new byte[0];
        this.runs = new BidiRun[0];
        this.isGoodLogicalToVisualRunsMap = false;
        this.insertPoints.size = 0;
        this.insertPoints.confirmed = 0;
        this.defaultParaLevel = IsDefaultLevel(paraLevel2) ? paraLevel2 : 0;
        if (this.length == 0) {
            if (IsDefaultLevel(paraLevel2)) {
                this.paraLevel = (byte) (1 & this.paraLevel);
                this.defaultParaLevel = 0;
            }
            this.flags = DirPropFlagLR(paraLevel2);
            this.runCount = 0;
            this.paraCount = 0;
            setParaSuccess();
            return;
        }
        this.runCount = -1;
        getDirPropsMemory(this.length);
        this.dirProps = this.dirPropsMemory;
        getDirProps();
        this.trailingWSStart = this.length;
        if (embeddingLevels == null) {
            getLevelsMemory(this.length);
            this.levels = this.levelsMemory;
            this.direction = resolveExplicitLevels();
        } else {
            this.levels = embeddingLevels;
            this.direction = checkExplicitLevels();
        }
        if (this.isolateCount > 0 && (this.isolates == null || this.isolates.length < this.isolateCount)) {
            this.isolates = new Isolate[(this.isolateCount + 3)];
        }
        this.isolateCount = -1;
        switch (this.direction) {
            case 0:
                this.trailingWSStart = 0;
                break;
            case 1:
                this.trailingWSStart = 0;
                break;
            default:
                switch (this.reorderingMode) {
                    case 0:
                        this.impTabPair = impTab_DEFAULT;
                        break;
                    case 1:
                        this.impTabPair = impTab_NUMBERS_SPECIAL;
                        break;
                    case 2:
                        this.impTabPair = impTab_GROUP_NUMBERS_WITH_R;
                        break;
                    case 3:
                        throw new InternalError("Internal ICU error in setPara");
                    case 4:
                        this.impTabPair = impTab_INVERSE_NUMBERS_AS_L;
                        break;
                    case 5:
                        if ((this.reorderingOptions & 1) == 0) {
                            this.impTabPair = impTab_INVERSE_LIKE_DIRECT;
                            break;
                        } else {
                            this.impTabPair = impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
                            break;
                        }
                    case 6:
                        if ((this.reorderingOptions & 1) == 0) {
                            this.impTabPair = impTab_INVERSE_FOR_NUMBERS_SPECIAL;
                            break;
                        } else {
                            this.impTabPair = impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
                            break;
                        }
                }
                if (embeddingLevels == null && this.paraCount <= 1 && (this.flags & DirPropFlagMultiRuns) == 0) {
                    resolveImplicitLevels(0, this.length, (short) GetLRFromLevel(GetParaLevelAt(0)), (short) GetLRFromLevel(GetParaLevelAt(this.length - 1)));
                } else {
                    int limit = 0;
                    byte level = GetParaLevelAt(0);
                    byte nextLevel = this.levels[0];
                    if (level < nextLevel) {
                        eor = (short) GetLRFromLevel(nextLevel);
                    } else {
                        eor = (short) GetLRFromLevel(level);
                    }
                    do {
                        int start = limit;
                        byte level2 = nextLevel;
                        if (start <= 0 || this.dirProps[start - 1] != 7) {
                            sor = eor;
                        } else {
                            sor = (short) GetLRFromLevel(GetParaLevelAt(start));
                        }
                        while (true) {
                            limit++;
                            if (limit >= this.length || (this.levels[limit] != level2 && (DirPropFlag(this.dirProps[limit]) & MASK_BN_EXPLICIT) == 0)) {
                            }
                        }
                        if (limit < this.length) {
                            nextLevel = this.levels[limit];
                        } else {
                            nextLevel = GetParaLevelAt(this.length - 1);
                        }
                        if (NoOverride(level2) < NoOverride(nextLevel)) {
                            eor2 = (short) GetLRFromLevel(nextLevel);
                        } else {
                            eor2 = (short) GetLRFromLevel(level2);
                        }
                        if ((level2 & Byte.MIN_VALUE) == 0) {
                            resolveImplicitLevels(start, limit, sor, eor);
                        } else {
                            while (true) {
                                byte[] bArr = this.levels;
                                int start2 = start + 1;
                                bArr[start] = (byte) (bArr[start] & LEVEL_DEFAULT_RTL);
                                if (start2 >= limit) {
                                    int i = start2;
                                } else {
                                    start = start2;
                                }
                            }
                        }
                    } while (limit < this.length);
                }
                adjustWSLevels();
                break;
        }
        if (this.defaultParaLevel > 0 && (this.reorderingOptions & 1) != 0 && (this.reorderingMode == 5 || this.reorderingMode == 6)) {
            int i2 = 0;
            while (i2 < this.paraCount) {
                int last = this.paras_limit[i2] - 1;
                if (this.paras_level[i2] != 0) {
                    int start3 = i2 == 0 ? 0 : this.paras_limit[i2 - 1];
                    int j = last;
                    while (true) {
                        if (j >= start3) {
                            byte dirProp = this.dirProps[j];
                            if (dirProp == 0) {
                                if (j < last) {
                                    while (this.dirProps[last] == 7) {
                                        last--;
                                    }
                                }
                                addPoint(last, 4);
                            } else if ((DirPropFlag(dirProp) & MASK_R_AL) == 0) {
                                j--;
                            }
                        }
                    }
                }
                i2++;
            }
        }
        if ((this.reorderingOptions & 2) != 0) {
            this.resultLength -= this.controlCount;
        } else {
            this.resultLength += this.insertPoints.size;
        }
        setParaSuccess();
    }

    public void setPara(AttributedCharacterIterator paragraph) {
        byte paraLvl;
        byte level;
        Boolean runDirection = (Boolean) paragraph.getAttribute(TextAttribute.RUN_DIRECTION);
        if (runDirection == null) {
            paraLvl = LEVEL_DEFAULT_LTR;
        } else {
            paraLvl = !runDirection.equals(TextAttribute.RUN_DIRECTION_LTR);
        }
        byte[] lvls = null;
        int len = paragraph.getEndIndex() - paragraph.getBeginIndex();
        byte[] embeddingLevels = new byte[len];
        char[] txt = new char[len];
        int i = 0;
        char ch = paragraph.first();
        while (ch != 65535) {
            txt[i] = ch;
            Integer embedding = (Integer) paragraph.getAttribute(TextAttribute.BIDI_EMBEDDING);
            if (!(embedding == null || (level = embedding.byteValue()) == 0)) {
                if (level < 0) {
                    lvls = embeddingLevels;
                    embeddingLevels[i] = (byte) ((0 - level) | -128);
                } else {
                    lvls = embeddingLevels;
                    embeddingLevels[i] = level;
                }
            }
            ch = paragraph.next();
            i++;
        }
        NumericShaper shaper = (NumericShaper) paragraph.getAttribute(TextAttribute.NUMERIC_SHAPING);
        if (shaper != null) {
            shaper.shape(txt, 0, len);
        }
        setPara(txt, paraLvl, lvls);
    }

    public void orderParagraphsLTR(boolean ordarParaLTR) {
        this.orderParagraphsLTR = ordarParaLTR;
    }

    public boolean isOrderParagraphsLTR() {
        return this.orderParagraphsLTR;
    }

    public byte getDirection() {
        verifyValidParaOrLine();
        return this.direction;
    }

    public String getTextAsString() {
        verifyValidParaOrLine();
        return new String(this.text);
    }

    public char[] getText() {
        verifyValidParaOrLine();
        return this.text;
    }

    public int getLength() {
        verifyValidParaOrLine();
        return this.originalLength;
    }

    public int getProcessedLength() {
        verifyValidParaOrLine();
        return this.length;
    }

    public int getResultLength() {
        verifyValidParaOrLine();
        return this.resultLength;
    }

    public byte getParaLevel() {
        verifyValidParaOrLine();
        return this.paraLevel;
    }

    public int countParagraphs() {
        verifyValidParaOrLine();
        return this.paraCount;
    }

    public BidiRun getParagraphByIndex(int paraIndex) {
        int paraStart;
        verifyValidParaOrLine();
        verifyRange(paraIndex, 0, this.paraCount);
        Bidi bidi = this.paraBidi;
        if (paraIndex == 0) {
            paraStart = 0;
        } else {
            paraStart = bidi.paras_limit[paraIndex - 1];
        }
        BidiRun bidiRun = new BidiRun();
        bidiRun.start = paraStart;
        bidiRun.limit = bidi.paras_limit[paraIndex];
        bidiRun.level = GetParaLevelAt(paraStart);
        return bidiRun;
    }

    public BidiRun getParagraph(int charIndex) {
        verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        int paraIndex = 0;
        verifyRange(charIndex, 0, bidi.length);
        while (true) {
            int paraIndex2 = paraIndex;
            if (charIndex < bidi.paras_limit[paraIndex2]) {
                return getParagraphByIndex(paraIndex2);
            }
            paraIndex = paraIndex2 + 1;
        }
    }

    public int getParagraphIndex(int charIndex) {
        verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        int paraIndex = 0;
        verifyRange(charIndex, 0, bidi.length);
        while (true) {
            int paraIndex2 = paraIndex;
            if (charIndex < bidi.paras_limit[paraIndex2]) {
                return paraIndex2;
            }
            paraIndex = paraIndex2 + 1;
        }
    }

    public void setCustomClassifier(BidiClassifier classifier) {
        this.customClassifier = classifier;
    }

    public BidiClassifier getCustomClassifier() {
        return this.customClassifier;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000d, code lost:
        if (r0 == 23) goto L_0x000f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getCustomizedClass(int r4) {
        /*
            r3 = this;
            com.ibm.icu.text.BidiClassifier r0 = r3.customClassifier
            r1 = 23
            if (r0 == 0) goto L_0x000f
            com.ibm.icu.text.BidiClassifier r0 = r3.customClassifier
            int r0 = r0.classify(r4)
            r2 = r0
            if (r0 != r1) goto L_0x0015
        L_0x000f:
            com.ibm.icu.impl.UBiDiProps r0 = r3.bdp
            int r2 = r0.getClass(r4)
        L_0x0015:
            r0 = r2
            if (r0 < r1) goto L_0x001a
            r0 = 10
        L_0x001a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ibm.icu.text.Bidi.getCustomizedClass(int):int");
    }

    public Bidi setLine(int start, int limit) {
        verifyValidPara();
        verifyRange(start, 0, limit);
        verifyRange(limit, 0, this.length + 1);
        if (getParagraphIndex(start) == getParagraphIndex(limit - 1)) {
            return BidiLine.setLine(this, start, limit);
        }
        throw new IllegalArgumentException();
    }

    public byte getLevelAt(int charIndex) {
        verifyValidParaOrLine();
        verifyRange(charIndex, 0, this.length);
        return BidiLine.getLevelAt(this, charIndex);
    }

    public byte[] getLevels() {
        verifyValidParaOrLine();
        if (this.length <= 0) {
            return new byte[0];
        }
        return BidiLine.getLevels(this);
    }

    public BidiRun getLogicalRun(int logicalPosition) {
        verifyValidParaOrLine();
        verifyRange(logicalPosition, 0, this.length);
        return BidiLine.getLogicalRun(this, logicalPosition);
    }

    public int countRuns() {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        return this.runCount;
    }

    public BidiRun getVisualRun(int runIndex) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(runIndex, 0, this.runCount);
        return BidiLine.getVisualRun(this, runIndex);
    }

    public int getVisualIndex(int logicalIndex) {
        verifyValidParaOrLine();
        verifyRange(logicalIndex, 0, this.length);
        return BidiLine.getVisualIndex(this, logicalIndex);
    }

    public int getLogicalIndex(int visualIndex) {
        verifyValidParaOrLine();
        verifyRange(visualIndex, 0, this.resultLength);
        if (this.insertPoints.size == 0 && this.controlCount == 0) {
            if (this.direction == 0) {
                return visualIndex;
            }
            if (this.direction == 1) {
                return (this.length - visualIndex) - 1;
            }
        }
        BidiLine.getRuns(this);
        return BidiLine.getLogicalIndex(this, visualIndex);
    }

    public int[] getLogicalMap() {
        countRuns();
        if (this.length <= 0) {
            return new int[0];
        }
        return BidiLine.getLogicalMap(this);
    }

    public int[] getVisualMap() {
        countRuns();
        if (this.resultLength <= 0) {
            return new int[0];
        }
        return BidiLine.getVisualMap(this);
    }

    public static int[] reorderLogical(byte[] levels2) {
        return BidiLine.reorderLogical(levels2);
    }

    public static int[] reorderVisual(byte[] levels2) {
        return BidiLine.reorderVisual(levels2);
    }

    public static int[] invertMap(int[] srcMap) {
        if (srcMap == null) {
            return null;
        }
        return BidiLine.invertMap(srcMap);
    }

    public Bidi(String paragraph, int flags2) {
        this(paragraph.toCharArray(), 0, (byte[]) null, 0, paragraph.length(), flags2);
    }

    public Bidi(AttributedCharacterIterator paragraph) {
        this();
        setPara(paragraph);
    }

    public Bidi(char[] text2, int textStart, byte[] embeddings, int embStart, int paragraphLength, int flags2) {
        this();
        byte paraLvl;
        byte[] paraEmbeddings;
        if (flags2 != 1) {
            switch (flags2) {
                case 126:
                    paraLvl = LEVEL_DEFAULT_LTR;
                    break;
                case 127:
                    paraLvl = LEVEL_DEFAULT_RTL;
                    break;
                default:
                    paraLvl = 0;
                    break;
            }
        } else {
            paraLvl = 1;
        }
        if (embeddings == null) {
            paraEmbeddings = null;
        } else {
            paraEmbeddings = new byte[paragraphLength];
            for (int i = 0; i < paragraphLength; i++) {
                byte lev = embeddings[i + embStart];
                if (lev < 0) {
                    lev = (byte) ((-lev) | -128);
                }
                paraEmbeddings[i] = lev;
            }
        }
        if (textStart == 0 && paragraphLength == text2.length) {
            setPara(text2, paraLvl, paraEmbeddings);
            return;
        }
        char[] paraText = new char[paragraphLength];
        System.arraycopy(text2, textStart, paraText, 0, paragraphLength);
        setPara(paraText, paraLvl, paraEmbeddings);
    }

    public Bidi createLineBidi(int lineStart, int lineLimit) {
        return setLine(lineStart, lineLimit);
    }

    public boolean isMixed() {
        return !isLeftToRight() && !isRightToLeft();
    }

    public boolean isLeftToRight() {
        return getDirection() == 0 && (this.paraLevel & 1) == 0;
    }

    public boolean isRightToLeft() {
        return getDirection() == 1 && (this.paraLevel & 1) == 1;
    }

    public boolean baseIsLeftToRight() {
        return getParaLevel() == 0;
    }

    public int getBaseLevel() {
        return getParaLevel();
    }

    public int getRunCount() {
        return countRuns();
    }

    /* access modifiers changed from: package-private */
    public void getLogicalToVisualRunsMap() {
        if (!this.isGoodLogicalToVisualRunsMap) {
            int count = countRuns();
            if (this.logicalToVisualRunsMap == null || this.logicalToVisualRunsMap.length < count) {
                this.logicalToVisualRunsMap = new int[count];
            }
            long[] keys = new long[count];
            for (int i = 0; i < count; i++) {
                keys[i] = (((long) this.runs[i].start) << 32) + ((long) i);
            }
            Arrays.sort(keys);
            for (int i2 = 0; i2 < count; i2++) {
                this.logicalToVisualRunsMap[i2] = (int) (keys[i2] & -1);
            }
            this.isGoodLogicalToVisualRunsMap = true;
        }
    }

    public int getRunLevel(int run) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(run, 0, this.runCount);
        getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[run]].level;
    }

    public int getRunStart(int run) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(run, 0, this.runCount);
        getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[run]].start;
    }

    public int getRunLimit(int run) {
        verifyValidParaOrLine();
        BidiLine.getRuns(this);
        verifyRange(run, 0, this.runCount);
        getLogicalToVisualRunsMap();
        int idx = this.logicalToVisualRunsMap[run];
        return this.runs[idx].start + (idx == 0 ? this.runs[idx].limit : this.runs[idx].limit - this.runs[idx - 1].limit);
    }

    public static boolean requiresBidi(char[] text2, int start, int limit) {
        for (int i = start; i < limit; i++) {
            if (((1 << UCharacter.getDirection(text2[i])) & 57378) != 0) {
                return true;
            }
        }
        return false;
    }

    public static void reorderVisually(byte[] levels2, int levelStart, Object[] objects, int objectStart, int count) {
        byte[] reorderLevels = new byte[count];
        System.arraycopy(levels2, levelStart, reorderLevels, 0, count);
        int[] indexMap = reorderVisual(reorderLevels);
        Object[] temp = new Object[count];
        System.arraycopy(objects, objectStart, temp, 0, count);
        for (int i = 0; i < count; i++) {
            objects[objectStart + i] = temp[indexMap[i]];
        }
    }

    public String writeReordered(int options) {
        verifyValidParaOrLine();
        if (this.length == 0) {
            return "";
        }
        return BidiWriter.writeReordered(this, options);
    }

    public static String writeReverse(String src, int options) {
        if (src == null) {
            throw new IllegalArgumentException();
        } else if (src.length() > 0) {
            return BidiWriter.writeReverse(src, options);
        } else {
            return "";
        }
    }
}
