package android.gesture;

import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class GesturePoint {
    public final long timestamp;

    /* renamed from: x */
    public final float f41x;

    /* renamed from: y */
    public final float f42y;

    public GesturePoint(float x, float y, long t) {
        this.f41x = x;
        this.f42y = y;
        this.timestamp = t;
    }

    static GesturePoint deserialize(DataInputStream in) throws IOException {
        float x = in.readFloat();
        float y = in.readFloat();
        long timeStamp = in.readLong();
        return new GesturePoint(x, y, timeStamp);
    }

    public Object clone() {
        return new GesturePoint(this.f41x, this.f42y, this.timestamp);
    }
}
