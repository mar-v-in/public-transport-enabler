package de.schildbach.pte.dto.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ParcelableSerializable<T extends Serializable> implements Parcelable {
	public static Creator<ParcelableSerializable> CREATOR = new Creator<ParcelableSerializable>() {
		@Override
		public ParcelableSerializable createFromParcel(Parcel parcel) {
			return new ParcelableSerializable(parcel.readSerializable());
		}

		@Override
		public ParcelableSerializable[] newArray(int i) {
			return new ParcelableSerializable[i];
		}
	};
	private final T serializable;

	public ParcelableSerializable(T serializable) {
		this.serializable = serializable;
	}

	@Override
	public int describeContents() {
		return serializable.hashCode();
	}

	public T get() {
		return serializable;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeSerializable(serializable);
	}
}
