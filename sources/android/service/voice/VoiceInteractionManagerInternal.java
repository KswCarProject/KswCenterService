package android.service.voice;

import android.p007os.Bundle;
import android.p007os.IBinder;

/* loaded from: classes3.dex */
public abstract class VoiceInteractionManagerInternal {
    public abstract void startLocalVoiceInteraction(IBinder iBinder, Bundle bundle);

    public abstract void stopLocalVoiceInteraction(IBinder iBinder);

    public abstract boolean supportsLocalVoiceInteraction();
}
