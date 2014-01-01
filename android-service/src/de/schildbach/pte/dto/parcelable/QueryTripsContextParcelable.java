package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.QueryTripsContext;

public class QueryTripsContextParcelable extends ParcelableSerializable<QueryTripsContext> {
	public static Creator<QueryTripsContextParcelable> CREATOR = new Creator<QueryTripsContextParcelable>() {
		@Override
		public QueryTripsContextParcelable createFromParcel(Parcel parcel) {
			return new QueryTripsContextParcelable((QueryTripsContext) parcel.readSerializable());
		}

		@Override
		public QueryTripsContextParcelable[] newArray(int i) {
			return new QueryTripsContextParcelable[i];
		}
	};

	public QueryTripsContextParcelable(QueryTripsContext serializable) {
		super(serializable);
	}
}
