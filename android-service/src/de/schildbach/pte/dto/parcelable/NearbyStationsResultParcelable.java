package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.NearbyStationsResult;

public class NearbyStationsResultParcelable extends ParcelableSerializable<NearbyStationsResult> {
	public static Creator<NearbyStationsResultParcelable> CREATOR = new Creator<NearbyStationsResultParcelable>() {
		@Override
		public NearbyStationsResultParcelable createFromParcel(Parcel parcel) {
			return new NearbyStationsResultParcelable((NearbyStationsResult) parcel.readSerializable());
		}

		@Override
		public NearbyStationsResultParcelable[] newArray(int i) {
			return new NearbyStationsResultParcelable[i];
		}
	};

	public NearbyStationsResultParcelable(NearbyStationsResult serializable) {
		super(serializable);
	}
}
