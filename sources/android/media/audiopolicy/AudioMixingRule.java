package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@SystemApi
public class AudioMixingRule {
    public static final int RULE_EXCLUDE_ATTRIBUTE_CAPTURE_PRESET = 32770;
    public static final int RULE_EXCLUDE_ATTRIBUTE_USAGE = 32769;
    public static final int RULE_EXCLUDE_UID = 32772;
    private static final int RULE_EXCLUSION_MASK = 32768;
    public static final int RULE_MATCH_ATTRIBUTE_CAPTURE_PRESET = 2;
    public static final int RULE_MATCH_ATTRIBUTE_USAGE = 1;
    public static final int RULE_MATCH_UID = 4;
    @UnsupportedAppUsage
    private boolean mAllowPrivilegedPlaybackCapture;
    @UnsupportedAppUsage
    private final ArrayList<AudioMixMatchCriterion> mCriteria;
    private final int mTargetMixType;

    private AudioMixingRule(int mixType, ArrayList<AudioMixMatchCriterion> criteria, boolean allowPrivilegedPlaybackCapture) {
        this.mAllowPrivilegedPlaybackCapture = false;
        this.mCriteria = criteria;
        this.mTargetMixType = mixType;
        this.mAllowPrivilegedPlaybackCapture = allowPrivilegedPlaybackCapture;
    }

    public static final class AudioMixMatchCriterion {
        @UnsupportedAppUsage
        final AudioAttributes mAttr;
        @UnsupportedAppUsage
        final int mIntProp;
        @UnsupportedAppUsage
        final int mRule;

        AudioMixMatchCriterion(AudioAttributes attributes, int rule) {
            this.mAttr = attributes;
            this.mIntProp = Integer.MIN_VALUE;
            this.mRule = rule;
        }

        AudioMixMatchCriterion(Integer intProp, int rule) {
            this.mAttr = null;
            this.mIntProp = intProp.intValue();
            this.mRule = rule;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.mAttr, Integer.valueOf(this.mIntProp), Integer.valueOf(this.mRule)});
        }

        /* access modifiers changed from: package-private */
        public void writeToParcel(Parcel dest) {
            dest.writeInt(this.mRule);
            int match_rule = this.mRule & -32769;
            if (match_rule != 4) {
                switch (match_rule) {
                    case 1:
                        dest.writeInt(this.mAttr.getUsage());
                        return;
                    case 2:
                        dest.writeInt(this.mAttr.getCapturePreset());
                        return;
                    default:
                        Log.e("AudioMixMatchCriterion", "Unknown match rule" + match_rule + " when writing to Parcel");
                        dest.writeInt(-1);
                        return;
                }
            } else {
                dest.writeInt(this.mIntProp);
            }
        }

        public AudioAttributes getAudioAttributes() {
            return this.mAttr;
        }

        public int getIntProp() {
            return this.mIntProp;
        }

        public int getRule() {
            return this.mRule;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAffectingUsage(int usage) {
        Iterator<AudioMixMatchCriterion> it = this.mCriteria.iterator();
        while (it.hasNext()) {
            AudioMixMatchCriterion criterion = it.next();
            if ((criterion.mRule & 1) != 0 && criterion.mAttr != null && criterion.mAttr.getUsage() == usage) {
                return true;
            }
        }
        return false;
    }

    private static boolean areCriteriaEquivalent(ArrayList<AudioMixMatchCriterion> cr1, ArrayList<AudioMixMatchCriterion> cr2) {
        if (cr1 == null || cr2 == null) {
            return false;
        }
        if (cr1 == cr2) {
            return true;
        }
        if (cr1.size() == cr2.size() && cr1.hashCode() == cr2.hashCode()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int getTargetMixType() {
        return this.mTargetMixType;
    }

    public ArrayList<AudioMixMatchCriterion> getCriteria() {
        return this.mCriteria;
    }

    public boolean allowPrivilegedPlaybackCapture() {
        return this.mAllowPrivilegedPlaybackCapture;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioMixingRule that = (AudioMixingRule) o;
        if (this.mTargetMixType == that.mTargetMixType && areCriteriaEquivalent(this.mCriteria, that.mCriteria) && this.mAllowPrivilegedPlaybackCapture == that.mAllowPrivilegedPlaybackCapture) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(this.mTargetMixType), this.mCriteria, Boolean.valueOf(this.mAllowPrivilegedPlaybackCapture)});
    }

    /* access modifiers changed from: private */
    public static boolean isValidSystemApiRule(int rule) {
        if (rule == 4) {
            return true;
        }
        switch (rule) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isValidAttributesSystemApiRule(int rule) {
        switch (rule) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isValidRule(int rule) {
        int match_rule = -32769 & rule;
        if (match_rule == 4) {
            return true;
        }
        switch (match_rule) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isPlayerRule(int rule) {
        int match_rule = -32769 & rule;
        if (match_rule == 1 || match_rule == 4) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean isAudioAttributeRule(int match_rule) {
        switch (match_rule) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static class Builder {
        private boolean mAllowPrivilegedPlaybackCapture = false;
        private ArrayList<AudioMixMatchCriterion> mCriteria = new ArrayList<>();
        private int mTargetMixType = -1;

        public Builder addRule(AudioAttributes attrToMatch, int rule) throws IllegalArgumentException {
            if (AudioMixingRule.isValidAttributesSystemApiRule(rule)) {
                return checkAddRuleObjInternal(rule, attrToMatch);
            }
            throw new IllegalArgumentException("Illegal rule value " + rule);
        }

        public Builder excludeRule(AudioAttributes attrToMatch, int rule) throws IllegalArgumentException {
            if (AudioMixingRule.isValidAttributesSystemApiRule(rule)) {
                return checkAddRuleObjInternal(32768 | rule, attrToMatch);
            }
            throw new IllegalArgumentException("Illegal rule value " + rule);
        }

        public Builder addMixRule(int rule, Object property) throws IllegalArgumentException {
            if (AudioMixingRule.isValidSystemApiRule(rule)) {
                return checkAddRuleObjInternal(rule, property);
            }
            throw new IllegalArgumentException("Illegal rule value " + rule);
        }

        public Builder excludeMixRule(int rule, Object property) throws IllegalArgumentException {
            if (AudioMixingRule.isValidSystemApiRule(rule)) {
                return checkAddRuleObjInternal(32768 | rule, property);
            }
            throw new IllegalArgumentException("Illegal rule value " + rule);
        }

        public Builder allowPrivilegedPlaybackCapture(boolean allow) {
            this.mAllowPrivilegedPlaybackCapture = allow;
            return this;
        }

        private Builder checkAddRuleObjInternal(int rule, Object property) throws IllegalArgumentException {
            if (property == null) {
                throw new IllegalArgumentException("Illegal null argument for mixing rule");
            } else if (!AudioMixingRule.isValidRule(rule)) {
                throw new IllegalArgumentException("Illegal rule value " + rule);
            } else if (AudioMixingRule.isAudioAttributeRule(-32769 & rule)) {
                if (property instanceof AudioAttributes) {
                    return addRuleInternal((AudioAttributes) property, (Integer) null, rule);
                }
                throw new IllegalArgumentException("Invalid AudioAttributes argument");
            } else if (property instanceof Integer) {
                return addRuleInternal((AudioAttributes) null, (Integer) property, rule);
            } else {
                throw new IllegalArgumentException("Invalid Integer argument");
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f5, code lost:
            return r7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private android.media.audiopolicy.AudioMixingRule.Builder addRuleInternal(android.media.AudioAttributes r8, java.lang.Integer r9, int r10) throws java.lang.IllegalArgumentException {
            /*
                r7 = this;
                int r0 = r7.mTargetMixType
                r1 = 1
                r2 = -1
                if (r0 != r2) goto L_0x0013
                boolean r0 = android.media.audiopolicy.AudioMixingRule.isPlayerRule(r10)
                if (r0 == 0) goto L_0x0010
                r0 = 0
                r7.mTargetMixType = r0
                goto L_0x0030
            L_0x0010:
                r7.mTargetMixType = r1
                goto L_0x0030
            L_0x0013:
                int r0 = r7.mTargetMixType
                if (r0 != 0) goto L_0x001d
                boolean r0 = android.media.audiopolicy.AudioMixingRule.isPlayerRule(r10)
                if (r0 == 0) goto L_0x0028
            L_0x001d:
                int r0 = r7.mTargetMixType
                if (r0 != r1) goto L_0x0030
                boolean r0 = android.media.audiopolicy.AudioMixingRule.isPlayerRule(r10)
                if (r0 != 0) goto L_0x0028
                goto L_0x0030
            L_0x0028:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.String r1 = "Incompatible rule for mix"
                r0.<init>(r1)
                throw r0
            L_0x0030:
                java.util.ArrayList<android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion> r0 = r7.mCriteria
                monitor-enter(r0)
                java.util.ArrayList<android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion> r1 = r7.mCriteria     // Catch:{ all -> 0x00f6 }
                java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x00f6 }
                r2 = -32769(0xffffffffffff7fff, float:NaN)
                r3 = r10 & r2
            L_0x003e:
                boolean r4 = r1.hasNext()     // Catch:{ all -> 0x00f6 }
                r5 = 4
                if (r4 == 0) goto L_0x00d1
                java.lang.Object r4 = r1.next()     // Catch:{ all -> 0x00f6 }
                android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion r4 = (android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) r4     // Catch:{ all -> 0x00f6 }
                int r6 = r4.mRule     // Catch:{ all -> 0x00f6 }
                r6 = r6 & r2
                if (r6 == r3) goto L_0x0051
                goto L_0x003e
            L_0x0051:
                if (r3 == r5) goto L_0x00aa
                switch(r3) {
                    case 1: goto L_0x0081;
                    case 2: goto L_0x0058;
                    default: goto L_0x0056;
                }     // Catch:{ all -> 0x00f6 }
            L_0x0056:
                goto L_0x00cf
            L_0x0058:
                android.media.AudioAttributes r5 = r4.mAttr     // Catch:{ all -> 0x00f6 }
                int r5 = r5.getCapturePreset()     // Catch:{ all -> 0x00f6 }
                int r6 = r8.getCapturePreset()     // Catch:{ all -> 0x00f6 }
                if (r5 != r6) goto L_0x00cf
                int r2 = r4.mRule     // Catch:{ all -> 0x00f6 }
                if (r2 != r10) goto L_0x006a
                monitor-exit(r0)     // Catch:{ all -> 0x00f6 }
                return r7
            L_0x006a:
                java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00f6 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f6 }
                r5.<init>()     // Catch:{ all -> 0x00f6 }
                java.lang.String r6 = "Contradictory rule exists for "
                r5.append(r6)     // Catch:{ all -> 0x00f6 }
                r5.append(r8)     // Catch:{ all -> 0x00f6 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00f6 }
                r2.<init>(r5)     // Catch:{ all -> 0x00f6 }
                throw r2     // Catch:{ all -> 0x00f6 }
            L_0x0081:
                android.media.AudioAttributes r5 = r4.mAttr     // Catch:{ all -> 0x00f6 }
                int r5 = r5.getUsage()     // Catch:{ all -> 0x00f6 }
                int r6 = r8.getUsage()     // Catch:{ all -> 0x00f6 }
                if (r5 != r6) goto L_0x00cf
                int r2 = r4.mRule     // Catch:{ all -> 0x00f6 }
                if (r2 != r10) goto L_0x0093
                monitor-exit(r0)     // Catch:{ all -> 0x00f6 }
                return r7
            L_0x0093:
                java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00f6 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f6 }
                r5.<init>()     // Catch:{ all -> 0x00f6 }
                java.lang.String r6 = "Contradictory rule exists for "
                r5.append(r6)     // Catch:{ all -> 0x00f6 }
                r5.append(r8)     // Catch:{ all -> 0x00f6 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00f6 }
                r2.<init>(r5)     // Catch:{ all -> 0x00f6 }
                throw r2     // Catch:{ all -> 0x00f6 }
            L_0x00aa:
                int r5 = r4.mIntProp     // Catch:{ all -> 0x00f6 }
                int r6 = r9.intValue()     // Catch:{ all -> 0x00f6 }
                if (r5 != r6) goto L_0x00cf
                int r2 = r4.mRule     // Catch:{ all -> 0x00f6 }
                if (r2 != r10) goto L_0x00b8
                monitor-exit(r0)     // Catch:{ all -> 0x00f6 }
                return r7
            L_0x00b8:
                java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00f6 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f6 }
                r5.<init>()     // Catch:{ all -> 0x00f6 }
                java.lang.String r6 = "Contradictory rule exists for UID "
                r5.append(r6)     // Catch:{ all -> 0x00f6 }
                r5.append(r9)     // Catch:{ all -> 0x00f6 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00f6 }
                r2.<init>(r5)     // Catch:{ all -> 0x00f6 }
                throw r2     // Catch:{ all -> 0x00f6 }
            L_0x00cf:
                goto L_0x003e
            L_0x00d1:
                if (r3 == r5) goto L_0x00e9
                switch(r3) {
                    case 1: goto L_0x00de;
                    case 2: goto L_0x00de;
                    default: goto L_0x00d6;
                }     // Catch:{ all -> 0x00f6 }
            L_0x00d6:
                java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00f6 }
                java.lang.String r4 = "Unreachable code in addRuleInternal()"
                r2.<init>(r4)     // Catch:{ all -> 0x00f6 }
                throw r2     // Catch:{ all -> 0x00f6 }
            L_0x00de:
                java.util.ArrayList<android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion> r2 = r7.mCriteria     // Catch:{ all -> 0x00f6 }
                android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion r4 = new android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion     // Catch:{ all -> 0x00f6 }
                r4.<init>((android.media.AudioAttributes) r8, (int) r10)     // Catch:{ all -> 0x00f6 }
                r2.add(r4)     // Catch:{ all -> 0x00f6 }
                goto L_0x00f4
            L_0x00e9:
                java.util.ArrayList<android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion> r2 = r7.mCriteria     // Catch:{ all -> 0x00f6 }
                android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion r4 = new android.media.audiopolicy.AudioMixingRule$AudioMixMatchCriterion     // Catch:{ all -> 0x00f6 }
                r4.<init>((java.lang.Integer) r9, (int) r10)     // Catch:{ all -> 0x00f6 }
                r2.add(r4)     // Catch:{ all -> 0x00f6 }
            L_0x00f4:
                monitor-exit(r0)     // Catch:{ all -> 0x00f6 }
                return r7
            L_0x00f6:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00f6 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.audiopolicy.AudioMixingRule.Builder.addRuleInternal(android.media.AudioAttributes, java.lang.Integer, int):android.media.audiopolicy.AudioMixingRule$Builder");
        }

        /* access modifiers changed from: package-private */
        public Builder addRuleFromParcel(Parcel in) throws IllegalArgumentException {
            int rule = in.readInt();
            int match_rule = -32769 & rule;
            AudioAttributes attr = null;
            Integer intProp = null;
            if (match_rule != 4) {
                switch (match_rule) {
                    case 1:
                        attr = new AudioAttributes.Builder().setUsage(in.readInt()).build();
                        break;
                    case 2:
                        attr = new AudioAttributes.Builder().setInternalCapturePreset(in.readInt()).build();
                        break;
                    default:
                        in.readInt();
                        throw new IllegalArgumentException("Illegal rule value " + rule + " in parcel");
                }
            } else {
                intProp = new Integer(in.readInt());
            }
            return addRuleInternal(attr, intProp, rule);
        }

        public AudioMixingRule build() {
            return new AudioMixingRule(this.mTargetMixType, this.mCriteria, this.mAllowPrivilegedPlaybackCapture);
        }
    }
}
