package android.hardware.radio.V1_2;

import android.p007os.HidlSupport;
import android.p007os.HwBlob;
import android.p007os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public final class CellIdentityOperatorNames {
    public String alphaLong = new String();
    public String alphaShort = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != CellIdentityOperatorNames.class) {
            return false;
        }
        CellIdentityOperatorNames other = (CellIdentityOperatorNames) otherObject;
        if (HidlSupport.deepEquals(this.alphaLong, other.alphaLong) && HidlSupport.deepEquals(this.alphaShort, other.alphaShort)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.alphaLong)), Integer.valueOf(HidlSupport.deepHashCode(this.alphaShort)));
    }

    public final String toString() {
        return "{.alphaLong = " + this.alphaLong + ", .alphaShort = " + this.alphaShort + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        HwBlob blob = parcel.readBuffer(32L);
        readEmbeddedFromParcel(parcel, blob, 0L);
    }

    public static final ArrayList<CellIdentityOperatorNames> readVectorFromParcel(HwParcel parcel) {
        ArrayList<CellIdentityOperatorNames> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16L);
        int _hidl_vec_size = _hidl_blob.getInt32(8L);
        HwBlob childBlob = parcel.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellIdentityOperatorNames _hidl_vec_element = new CellIdentityOperatorNames();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, _hidl_index_0 * 32);
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.alphaLong = _hidl_blob.getString(_hidl_offset + 0);
        parcel.readEmbeddedBuffer(this.alphaLong.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 0 + 0, false);
        this.alphaShort = _hidl_blob.getString(_hidl_offset + 16);
        parcel.readEmbeddedBuffer(this.alphaShort.getBytes().length + 1, _hidl_blob.handle(), _hidl_offset + 16 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(32);
        writeEmbeddedToBlob(_hidl_blob, 0L);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<CellIdentityOperatorNames> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8L, _hidl_vec_size);
        _hidl_blob.putBool(12L, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, _hidl_index_0 * 32);
        }
        _hidl_blob.putBlob(0L, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putString(0 + _hidl_offset, this.alphaLong);
        _hidl_blob.putString(16 + _hidl_offset, this.alphaShort);
    }
}
