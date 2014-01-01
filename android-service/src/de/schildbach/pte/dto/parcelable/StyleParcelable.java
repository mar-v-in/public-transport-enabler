package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import de.schildbach.pte.dto.Style;

public class StyleParcelable extends ParcelableSerializable<Style> {
	public static Creator<StyleParcelable> CREATOR = new Creator<StyleParcelable>() {
		@Override
		public StyleParcelable createFromParcel(Parcel parcel) {
			return new StyleParcelable((Style) parcel.readSerializable());
		}

		@Override
		public StyleParcelable[] newArray(int i) {
			return new StyleParcelable[i];
		}
	};

	public StyleParcelable(Style serializable) {
		super(serializable);
	}
}
