package de.schildbach.pte.dto.parcelable;

import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.dto.*;

import java.io.Serializable;
import java.util.*;

public class ParcelableHelper {
	public static <T extends Enum> List<T> listToEnum(List<String> stringList, Class<T> tClass) {
		List<T> list = new ArrayList<T>();
		for (String name : stringList) {
			list.add((T)Enum.valueOf(tClass, name));
		}
		return list;
	}

	public static <T extends Serializable> List<T> listToSerializable(
			List<? extends ParcelableSerializable<T>> parcelableList) {
		List<T> list = new ArrayList<T>();
		for (ParcelableSerializable<T> parcelable : parcelableList) {
			list.add(toSerializable(parcelable));
		}
		return list;
	}

	public static Set<NetworkProvider.Option> toOptionSet(List<String> options) {
		Set<NetworkProvider.Option> optionSet = new HashSet<NetworkProvider.Option>();
		for (String option : options) {
			optionSet.add(NetworkProvider.Option.valueOf(option));
		}
		return optionSet;
	}

	public static List<LocationParcelable> toParcelable(List<Location> locations) {
		List<LocationParcelable> parcelableList = new ArrayList<LocationParcelable>();
		for (Location location : locations) {
			parcelableList.add(toParcelable(location));
		}
		return parcelableList;
	}

	public static <T extends Serializable> ParcelableSerializable<T> toParcelable(T t) {
		return new ParcelableSerializable<T>(t);
	}

	public static LocationParcelable toParcelable(Location location) {
		return new LocationParcelable(location);
	}

	public static NearbyStationsResultParcelable toParcelable(NearbyStationsResult nearbyStationsResult) {
		return new NearbyStationsResultParcelable(nearbyStationsResult);
	}

	public static QueryTripsResultParcelable toParcelable(QueryTripsResult queryTripsResult) {
		return new QueryTripsResultParcelable(queryTripsResult);
	}

	public static QueryDeparturesResultParcelable toParcelable(QueryDeparturesResult queryDeparturesResult) {
		return new QueryDeparturesResultParcelable(queryDeparturesResult);
	}

	public static PointParcelable[] toParcelable(Point[] area) {
		if (area == null) return new PointParcelable[0];
		PointParcelable[] parcelables = new PointParcelable[area.length];
		for (int i = 0; i < area.length; i++) {
			parcelables[i] = toParcelable(area[i]);
		}
		return parcelables;
	}

	private static PointParcelable toParcelable(Point point) {
		return new PointParcelable(point);
	}

	public static StyleParcelable toParcelable(Style style) {
		return new StyleParcelable(style);
	}

	public static String[] toParcelable(NetworkProvider.Capability[] capabilities) {
		String[] strings = new String[capabilities.length];
		for (int i = 0; i < capabilities.length; i++) {
			strings[i] = capabilities[i].name();
		}
		return strings;
	}

	public static <T extends Enum> List<String> toParcelable(Collection<T> collection) {
		List<String> stringList = new ArrayList<String>(collection.size());
		for (T t : collection) {
			stringList.add(t.name());
		}
		return stringList;
	}

	public static <T extends Serializable> List<ParcelableSerializable<T>> toParcelableList(List<T> list) {
		List<ParcelableSerializable<T>> parcelableList = new ArrayList<ParcelableSerializable<T>>();
		for (T t : list) {
			parcelableList.add(toParcelable(t));
		}
		return parcelableList;
	}

	public static <T extends Serializable> T toSerializable(ParcelableSerializable<T> parcelable) {
		return parcelable.get();
	}

	public static NetworkProvider.WalkSpeed toWalkSpeed(String walkSpeed) {
		return NetworkProvider.WalkSpeed.valueOf(walkSpeed);
	}
}
