package android.hardware.radio.V1_0;

import android.p007os.HidlSupport;
import android.p007os.HwBlob;
import android.p007os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public final class SimApdu {
    public int cla;
    public String data = new String();
    public int instruction;

    /* renamed from: p1 */
    public int f95p1;

    /* renamed from: p2 */
    public int f96p2;

    /* renamed from: p3 */
    public int f97p3;
    public int sessionId;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SimApdu.class) {
            return false;
        }
        SimApdu other = (SimApdu) otherObject;
        if (this.sessionId == other.sessionId && this.cla == other.cla && this.instruction == other.instruction && this.f95p1 == other.f95p1 && this.f96p2 == other.f96p2 && this.f97p3 == other.f97p3 && HidlSupport.deepEquals(this.data, other.data)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sessionId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cla))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.instruction))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.f95p1))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.f96p2))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.f97p3))), Integer.valueOf(HidlSupport.deepHashCode(this.data)));
    }

    public final String toString() {
        return "{.sessionId = " + this.sessionId + ", .cla = " + this.cla + ", .instruction = " + this.instruction + ", .p1 = " + this.f95p1 + ", .p2 = " + this.f96p2 + ", .p3 = " + this.f97p3 + ", .data = " + this.data + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(40L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<SimApdu> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SimApdu> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 40, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SimApdu _hidl_vec_element = new SimApdu();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 40);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.sessionId = _hidl_blob.getInt32(_hidl_offset + 0);
        this.cla = _hidl_blob.getInt32(_hidl_offset + 4);
        this.instruction = _hidl_blob.getInt32(_hidl_offset + 8);
        this.f95p1 = _hidl_blob.getInt32(_hidl_offset + 12);
        this.f96p2 = _hidl_blob.getInt32(_hidl_offset + 16);
        this.f97p3 = _hidl_blob.getInt32(_hidl_offset + 20);
        this.data = _hidl_blob.getString(_hidl_offset + 24);
        parcel.readEmbeddedBuffer(this.data.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 24 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(40);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SimApdu> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 40);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 40);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.sessionId);
        _hidl_blob.putInt32(4 + _hidl_offset, this.cla);
        _hidl_blob.putInt32(8 + _hidl_offset, this.instruction);
        _hidl_blob.putInt32(12 + _hidl_offset, this.f95p1);
        _hidl_blob.putInt32(16 + _hidl_offset, this.f96p2);
        _hidl_blob.putInt32(20 + _hidl_offset, this.f97p3);
        _hidl_blob.putString(24 + _hidl_offset, this.data);
    }
}
