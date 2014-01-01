package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.Location;

public class LocationParcelable extends ParcelableSerializable<Location> {
	public static Creator<LocationParcelable> CREATOR = new Creator<LocationParcelable>() {
		@Override
		public LocationParcelable createFromParcel(Parcel parcel) {
			return new LocationParcelable((Location) parcel.readSerializable());
		}

		@Override
		public LocationParcelable[] newArray(int i) {
			return new LocationParcelable[i];
		}
	};

	public LocationParcelable(Location serializable) {
		super(serializable);
	}
}
