package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.Point;

public class PointParcelable extends ParcelableSerializable<Point> {

	public static Creator<PointParcelable> CREATOR = new Creator<PointParcelable>() {
		@Override
		public PointParcelable createFromParcel(Parcel parcel) {
			return new PointParcelable((Point) parcel.readSerializable());
		}

		@Override
		public PointParcelable[] newArray(int i) {
			return new PointParcelable[i];
		}
	};

	public PointParcelable(Point serializable) {
		super(serializable);
	}
}
