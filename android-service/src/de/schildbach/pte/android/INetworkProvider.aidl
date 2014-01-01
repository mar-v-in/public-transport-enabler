package de.schildbach.pte.android;

import de.schildbach.pte.dto.parcelable.NearbyStationsResultParcelable;
import de.schildbach.pte.dto.parcelable.LocationParcelable;
import de.schildbach.pte.dto.parcelable.QueryDeparturesResultParcelable;
import de.schildbach.pte.dto.parcelable.QueryTripsResultParcelable;
import de.schildbach.pte.dto.parcelable.QueryTripsContextParcelable;
import de.schildbach.pte.dto.parcelable.StyleParcelable;
import de.schildbach.pte.dto.parcelable.PointParcelable;

interface INetworkProvider {
    String id();
    boolean hasCapabilities(in String[] capabilities);
    NearbyStationsResultParcelable queryNearbyStations(in LocationParcelable location, int maxDistance, int maxStations);
    QueryDeparturesResultParcelable queryDepartures(int stationId, int maxDepartures, boolean equivs);
    List<LocationParcelable> autocompleteStations(CharSequence constraint);
    List<String> defaultProducts();
    QueryTripsResultParcelable queryTrips(in LocationParcelable from, in LocationParcelable via, in LocationParcelable to,
        long date, boolean dep, int numTrips, in List<String> products,	String walkSpeed, String accessibility,
        in List<String> options);
    QueryTripsResultParcelable queryMoreTrips(in QueryTripsContextParcelable context, boolean later, int numTrips);
    StyleParcelable lineStyle(String network, String line);
    PointParcelable[] getArea();
}
