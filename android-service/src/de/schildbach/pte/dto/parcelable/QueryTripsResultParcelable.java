package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.QueryTripsResult;

public class QueryTripsResultParcelable extends ParcelableSerializable<QueryTripsResult> {
	public static Creator<QueryTripsResultParcelable> CREATOR = new Creator<QueryTripsResultParcelable>() {
		@Override
		public QueryTripsResultParcelable createFromParcel(Parcel parcel) {
			return new QueryTripsResultParcelable((QueryTripsResult) parcel.readSerializable());
		}

		@Override
		public QueryTripsResultParcelable[] newArray(int i) {
			return new QueryTripsResultParcelable[i];
		}
	};

	public QueryTripsResultParcelable(QueryTripsResult serializable) {
		super(serializable);
	}
}
