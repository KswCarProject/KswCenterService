package org.mozilla.universalchardet;

import java.io.FileInputStream;
import java.io.PrintStream;
import org.mozilla.universalchardet.prober.CharsetProber;
import org.mozilla.universalchardet.prober.EscCharsetProber;
import org.mozilla.universalchardet.prober.Latin1Prober;
import org.mozilla.universalchardet.prober.MBCSGroupProber;
import org.mozilla.universalchardet.prober.SBCSGroupProber;

/* loaded from: classes5.dex */
public class UniversalDetector {
    public static final float MINIMUM_THRESHOLD = 0.2f;
    public static final float SHORTCUT_THRESHOLD = 0.95f;
    private String detectedCharset;
    private boolean done;
    private boolean gotData;
    private InputState inputState;
    private byte lastChar;
    private CharsetListener listener;
    private boolean start;
    private CharsetProber escCharsetProber = null;
    private CharsetProber[] probers = new CharsetProber[3];

    /* loaded from: classes5.dex */
    public enum InputState {
        PURE_ASCII,
        ESC_ASCII,
        HIGHBYTE
    }

    public UniversalDetector(CharsetListener charsetListener) {
        this.listener = charsetListener;
        for (int i = 0; i < this.probers.length; i++) {
            this.probers[i] = null;
        }
        reset();
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length != 1) {
            System.out.println("USAGE: java UniversalDetector filename");
            return;
        }
        UniversalDetector universalDetector = new UniversalDetector(new CharsetListener() { // from class: org.mozilla.universalchardet.UniversalDetector.1
            @Override // org.mozilla.universalchardet.CharsetListener
            public void report(String str) {
                PrintStream printStream = System.out;
                printStream.println("charset = " + str);
            }
        });
        byte[] bArr = new byte[4096];
        FileInputStream fileInputStream = new FileInputStream(strArr[0]);
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read <= 0 || universalDetector.isDone()) {
                break;
            }
            universalDetector.handleData(bArr, 0, read);
        }
        universalDetector.dataEnd();
    }

    public void dataEnd() {
        if (this.gotData) {
            if (this.detectedCharset != null) {
                this.done = true;
                if (this.listener != null) {
                    this.listener.report(this.detectedCharset);
                }
            } else if (this.inputState != InputState.HIGHBYTE) {
                InputState inputState = this.inputState;
                InputState inputState2 = InputState.ESC_ASCII;
            } else {
                float f = 0.0f;
                int i = 0;
                for (int i2 = 0; i2 < this.probers.length; i2++) {
                    float confidence = this.probers[i2].getConfidence();
                    if (confidence > f) {
                        i = i2;
                        f = confidence;
                    }
                }
                if (f > 0.2f) {
                    this.detectedCharset = this.probers[i].getCharSetName();
                    if (this.listener != null) {
                        this.listener.report(this.detectedCharset);
                    }
                }
            }
        }
    }

    public String getDetectedCharset() {
        return this.detectedCharset;
    }

    public CharsetListener getListener() {
        return this.listener;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleData(byte[] bArr, int i, int i2) {
        String str;
        if (this.done) {
            return;
        }
        if (i2 > 0) {
            this.gotData = true;
        }
        if (this.start) {
            this.start = false;
            if (i2 > 3) {
                int i3 = bArr[i] & 255;
                int i4 = bArr[i + 1] & 255;
                int i5 = bArr[i + 2] & 255;
                int i6 = bArr[i + 3] & 255;
                if (i3 == 0) {
                    if (i4 == 0 && i5 == 254 && i6 == 255) {
                        str = Constants.CHARSET_UTF_32BE;
                    } else {
                        if (i4 == 0 && i5 == 255 && i6 == 254) {
                            str = Constants.CHARSET_X_ISO_10646_UCS_4_2143;
                        }
                        if (this.detectedCharset != null) {
                        }
                    }
                    this.detectedCharset = str;
                    if (this.detectedCharset != null) {
                    }
                } else if (i3 != 239) {
                    switch (i3) {
                        case 254:
                            if (i4 == 255 && i5 == 0 && i6 == 0) {
                                str = Constants.CHARSET_X_ISO_10646_UCS_4_3412;
                            } else if (i4 == 255) {
                                str = Constants.CHARSET_UTF_16BE;
                            }
                            this.detectedCharset = str;
                            break;
                        case 255:
                            if (i4 == 254 && i5 == 0 && i6 == 0) {
                                str = Constants.CHARSET_UTF_32LE;
                            } else if (i4 == 254) {
                                str = Constants.CHARSET_UTF_16LE;
                            }
                            this.detectedCharset = str;
                            break;
                    }
                    if (this.detectedCharset != null) {
                        this.done = true;
                        return;
                    }
                } else {
                    if (i4 == 187 && i5 == 191) {
                        str = Constants.CHARSET_UTF_8;
                        this.detectedCharset = str;
                    }
                    if (this.detectedCharset != null) {
                    }
                }
            }
        }
        int i7 = i + i2;
        for (int i8 = i; i8 < i7; i8++) {
            int i9 = bArr[i8] & 255;
            if ((i9 & 128) == 0 || i9 == 160) {
                if (this.inputState == InputState.PURE_ASCII && (i9 == 27 || (i9 == 123 && this.lastChar == 126))) {
                    this.inputState = InputState.ESC_ASCII;
                }
                this.lastChar = bArr[i8];
            } else if (this.inputState != InputState.HIGHBYTE) {
                this.inputState = InputState.HIGHBYTE;
                if (this.escCharsetProber != null) {
                    this.escCharsetProber = null;
                }
                if (this.probers[0] == null) {
                    this.probers[0] = new MBCSGroupProber();
                }
                if (this.probers[1] == null) {
                    this.probers[1] = new SBCSGroupProber();
                }
                if (this.probers[2] == null) {
                    this.probers[2] = new Latin1Prober();
                }
            }
        }
        if (this.inputState == InputState.ESC_ASCII) {
            if (this.escCharsetProber == null) {
                this.escCharsetProber = new EscCharsetProber();
            }
            if (this.escCharsetProber.handleData(bArr, i, i2) == CharsetProber.ProbingState.FOUND_IT) {
                this.done = true;
                this.detectedCharset = this.escCharsetProber.getCharSetName();
            }
        } else if (this.inputState == InputState.HIGHBYTE) {
            for (int i10 = 0; i10 < this.probers.length; i10++) {
                if (this.probers[i10].handleData(bArr, i, i2) == CharsetProber.ProbingState.FOUND_IT) {
                    this.done = true;
                    this.detectedCharset = this.probers[i10].getCharSetName();
                    return;
                }
            }
        }
    }

    public boolean isDone() {
        return this.done;
    }

    public void reset() {
        this.done = false;
        this.start = true;
        this.detectedCharset = null;
        this.gotData = false;
        this.inputState = InputState.PURE_ASCII;
        this.lastChar = (byte) 0;
        if (this.escCharsetProber != null) {
            this.escCharsetProber.reset();
        }
        for (int i = 0; i < this.probers.length; i++) {
            if (this.probers[i] != null) {
                this.probers[i].reset();
            }
        }
    }

    public void setListener(CharsetListener charsetListener) {
        this.listener = charsetListener;
    }
}
