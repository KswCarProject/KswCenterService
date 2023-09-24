package com.ibm.icu.text;

import com.ibm.icu.text.DictionaryBreakEngine;
import java.text.CharacterIterator;

/* loaded from: classes5.dex */
interface LanguageBreakEngine {
    int findBreaks(CharacterIterator characterIterator, int i, int i2, DictionaryBreakEngine.DequeI dequeI);

    boolean handles(int i);
}
