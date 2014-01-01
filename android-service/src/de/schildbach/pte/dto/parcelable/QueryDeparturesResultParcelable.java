package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.QueryDeparturesResult;

public class QueryDeparturesResultParcelable extends ParcelableSerializable<QueryDeparturesResult> {

	public static Creator<QueryDeparturesResultParcelable> CREATOR = new Creator<QueryDeparturesResultParcelable>() {
		@Override
		public QueryDeparturesResultParcelable createFromParcel(Parcel parcel) {
			return new QueryDeparturesResultParcelable((QueryDeparturesResult) parcel.readSerializable());
		}

		@Override
		public QueryDeparturesResultParcelable[] newArray(int i) {
			return new QueryDeparturesResultParcelable[i];
		}
	};

	public QueryDeparturesResultParcelable(QueryDeparturesResult serializable) {
		super(serializable);
	}
}
