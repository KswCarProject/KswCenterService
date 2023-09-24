package com.ibm.icu.text;

/* loaded from: classes5.dex */
class CharsetRecog_UTF8 extends CharsetRecognizer {
    CharsetRecog_UTF8() {
    }

    @Override // com.ibm.icu.text.CharsetRecognizer
    String getName() {
        return "UTF-8";
    }

    @Override // com.ibm.icu.text.CharsetRecognizer
    CharsetMatch match(CharsetDetector det) {
        int trailBytes;
        boolean hasBOM = false;
        int numValid = 0;
        int numInvalid = 0;
        byte[] input = det.fRawInput;
        int b = 0;
        if (det.fRawLength >= 3 && (input[0] & 255) == 239 && (input[1] & 255) == 187 && (input[2] & 255) == 191) {
            hasBOM = true;
        }
        while (true) {
            int i = b;
            if (i >= det.fRawLength) {
                break;
            }
            int b2 = input[i];
            if ((b2 & 128) != 0) {
                if ((b2 & 224) == 192) {
                    trailBytes = 1;
                } else if ((b2 & 240) == 224) {
                    trailBytes = 2;
                } else if ((b2 & 248) == 240) {
                    trailBytes = 3;
                } else {
                    numInvalid++;
                }
                while (true) {
                    i++;
                    if (i < det.fRawLength) {
                        int b3 = input[i];
                        if ((b3 & 192) != 128) {
                            numInvalid++;
                            break;
                        }
                        trailBytes--;
                        if (trailBytes == 0) {
                            numValid++;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            b = i + 1;
        }
        int confidence = 0;
        if (hasBOM && numInvalid == 0) {
            confidence = 100;
        } else if (hasBOM && numValid > numInvalid * 10) {
            confidence = 80;
        } else if (numValid > 3 && numInvalid == 0) {
            confidence = 100;
        } else if (numValid > 0 && numInvalid == 0) {
            confidence = 80;
        } else if (numValid == 0 && numInvalid == 0) {
            confidence = 15;
        } else if (numValid > numInvalid * 10) {
            confidence = 25;
        }
        if (confidence == 0) {
            return null;
        }
        return new CharsetMatch(det, this, confidence);
    }
}
