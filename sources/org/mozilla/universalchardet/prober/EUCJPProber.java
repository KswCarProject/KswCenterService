package org.mozilla.universalchardet.prober;

import java.util.Arrays;
import org.mozilla.universalchardet.Constants;
import org.mozilla.universalchardet.prober.CharsetProber;
import org.mozilla.universalchardet.prober.contextanalysis.EUCJPContextAnalysis;
import org.mozilla.universalchardet.prober.distributionanalysis.EUCJPDistributionAnalysis;
import org.mozilla.universalchardet.prober.statemachine.CodingStateMachine;
import org.mozilla.universalchardet.prober.statemachine.EUCJPSMModel;
import org.mozilla.universalchardet.prober.statemachine.SMModel;

/* loaded from: classes5.dex */
public class EUCJPProber extends CharsetProber {
    private static final SMModel smModel = new EUCJPSMModel();
    private CodingStateMachine codingSM = new CodingStateMachine(smModel);
    private EUCJPContextAnalysis contextAnalyzer = new EUCJPContextAnalysis();
    private EUCJPDistributionAnalysis distributionAnalyzer = new EUCJPDistributionAnalysis();
    private byte[] lastChar = new byte[2];
    private CharsetProber.ProbingState state;

    public EUCJPProber() {
        reset();
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public String getCharSetName() {
        return Constants.CHARSET_EUC_JP;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public float getConfidence() {
        return Math.max(this.contextAnalyzer.getConfidence(), this.distributionAnalyzer.getConfidence());
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public CharsetProber.ProbingState getState() {
        return this.state;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public CharsetProber.ProbingState handleData(byte[] bArr, int i, int i2) {
        CharsetProber.ProbingState probingState;
        int i3 = i2 + i;
        for (int i4 = i; i4 < i3; i4++) {
            int nextState = this.codingSM.nextState(bArr[i4]);
            if (nextState == 1) {
                probingState = CharsetProber.ProbingState.NOT_ME;
            } else if (nextState == 2) {
                probingState = CharsetProber.ProbingState.FOUND_IT;
            } else {
                if (nextState == 0) {
                    int currentCharLen = this.codingSM.getCurrentCharLen();
                    if (i4 == i) {
                        this.lastChar[1] = bArr[i];
                        this.contextAnalyzer.handleOneChar(this.lastChar, 0, currentCharLen);
                        this.distributionAnalyzer.handleOneChar(this.lastChar, 0, currentCharLen);
                    } else {
                        int i5 = i4 - 1;
                        this.contextAnalyzer.handleOneChar(bArr, i5, currentCharLen);
                        this.distributionAnalyzer.handleOneChar(bArr, i5, currentCharLen);
                    }
                }
            }
            this.state = probingState;
        }
        this.lastChar[0] = bArr[i3 - 1];
        if (this.state == CharsetProber.ProbingState.DETECTING && this.contextAnalyzer.gotEnoughData() && getConfidence() > 0.95f) {
            this.state = CharsetProber.ProbingState.FOUND_IT;
        }
        return this.state;
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public void reset() {
        this.codingSM.reset();
        this.state = CharsetProber.ProbingState.DETECTING;
        this.contextAnalyzer.reset();
        this.distributionAnalyzer.reset();
        Arrays.fill(this.lastChar, (byte) 0);
    }

    @Override // org.mozilla.universalchardet.prober.CharsetProber
    public void setOption() {
    }
}
