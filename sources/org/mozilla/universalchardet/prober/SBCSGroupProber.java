package org.mozilla.universalchardet.prober;

import java.nio.ByteBuffer;
import org.mozilla.universalchardet.prober.CharsetProber;
import org.mozilla.universalchardet.prober.sequence.HebrewModel;
import org.mozilla.universalchardet.prober.sequence.Ibm855Model;
import org.mozilla.universalchardet.prober.sequence.Ibm866Model;
import org.mozilla.universalchardet.prober.sequence.Koi8rModel;
import org.mozilla.universalchardet.prober.sequence.Latin5BulgarianModel;
import org.mozilla.universalchardet.prober.sequence.Latin5Model;
import org.mozilla.universalchardet.prober.sequence.Latin7Model;
import org.mozilla.universalchardet.prober.sequence.MacCyrillicModel;
import org.mozilla.universalchardet.prober.sequence.SequenceModel;
import org.mozilla.universalchardet.prober.sequence.Win1251BulgarianModel;
import org.mozilla.universalchardet.prober.sequence.Win1251Model;
import org.mozilla.universalchardet.prober.sequence.Win1253Model;

/* loaded from: classes5.dex */
public class SBCSGroupProber extends CharsetProber {
    private int activeNum;
    private int bestGuess;
    private CharsetProber.ProbingState state;
    private static final SequenceModel win1251Model = new Win1251Model();
    private static final SequenceModel koi8rModel = new Koi8rModel();
    private static final SequenceModel latin5Model = new Latin5Model();
    private static final SequenceModel macCyrillicModel = new MacCyrillicModel();
    private static final SequenceModel ibm866Model = new Ibm866Model();
    private static final SequenceModel ibm855Model = new Ibm855Model();
    private static final SequenceModel latin7Model = new Latin7Model();
    private static final SequenceModel win1253Model = new Win1253Model();
    private static final SequenceModel latin5BulgarianModel = new Latin5BulgarianModel();
    private static final SequenceModel win1251BulgarianModel = new Win1251BulgarianModel();
    private static final SequenceModel hebrewModel = new HebrewModel();
    private CharsetProber[] probers = new CharsetProber[13];
    private boolean[] isActive = new boolean[13];

    public SBCSGroupProber() {
        this.probers[0] = new SingleByteCharsetProber(win1251Model);
        this.probers[1] = new SingleByteCharsetProber(koi8rModel);
        this.probers[2] = new SingleByteCharsetProber(latin5Model);
        this.probers[3] = new SingleByteCharsetProber(macCyrillicModel);
        this.probers[4] = new SingleByteCharsetProber(ibm866Model);
        this.probers[5] = new SingleByteCharsetProber(ibm855Model);
        this.probers[6] = new SingleByteCharsetProber(latin7Model);
        this.probers[7] = new SingleByteCharsetProber(win1253Model);
        this.probers[8] = new SingleByteCharsetProber(latin5BulgarianModel);
        this.probers[9] = new SingleByteCharsetProber(win1251BulgarianModel);
        HebrewProber hebrewProber = new HebrewProber();
        this.probers[10] = hebrewProber;
        this.probers[11] = new SingleByteCharsetProber(hebrewModel, false, hebrewProber);
        this.probers[12] = new SingleByteCharsetProber(hebrewModel, true, hebrewProber);
        hebrewProber.setModalProbers(this.probers[11], this.probers[12]);
        reset();
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public String getCharSetName() {
        if (this.bestGuess == -1) {
            getConfidence();
            if (this.bestGuess == -1) {
                this.bestGuess = 0;
            }
        }
        return this.probers[this.bestGuess].getCharSetName();
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public float getConfidence() {
        if (this.state == CharsetProber.ProbingState.FOUND_IT) {
            return 0.99f;
        }
        if (this.state == CharsetProber.ProbingState.NOT_ME) {
            return 0.01f;
        }
        float f = 0.0f;
        for (int i = 0; i < this.probers.length; i++) {
            if (this.isActive[i]) {
                float confidence = this.probers[i].getConfidence();
                if (f < confidence) {
                    this.bestGuess = i;
                    f = confidence;
                }
            }
        }
        return f;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public CharsetProber.ProbingState getState() {
        return this.state;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public CharsetProber.ProbingState handleData(byte[] bArr, int i, int i2) {
        CharsetProber.ProbingState probingState;
        ByteBuffer filterWithoutEnglishLetters = filterWithoutEnglishLetters(bArr, i, i2);
        if (filterWithoutEnglishLetters.position() != 0) {
            for (int i3 = 0; i3 < this.probers.length; i3++) {
                if (this.isActive[i3]) {
                    CharsetProber.ProbingState handleData = this.probers[i3].handleData(filterWithoutEnglishLetters.array(), 0, filterWithoutEnglishLetters.position());
                    if (handleData == CharsetProber.ProbingState.FOUND_IT) {
                        this.bestGuess = i3;
                        probingState = CharsetProber.ProbingState.FOUND_IT;
                    } else if (handleData == CharsetProber.ProbingState.NOT_ME) {
                        this.isActive[i3] = false;
                        this.activeNum--;
                        if (this.activeNum <= 0) {
                            probingState = CharsetProber.ProbingState.NOT_ME;
                        }
                    } else {
                        continue;
                    }
                    this.state = probingState;
                    break;
                }
            }
        }
        return this.state;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public void reset() {
        this.activeNum = 0;
        for (int i = 0; i < this.probers.length; i++) {
            this.probers[i].reset();
            this.isActive[i] = true;
            this.activeNum++;
        }
        this.bestGuess = -1;
        this.state = CharsetProber.ProbingState.DETECTING;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public void setOption() {
    }
}
