package de.schildbach.pte.android;

import android.os.RemoteException;
import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.dto.*;
import de.schildbach.pte.dto.parcelable.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

class NetworkProviderBinder extends INetworkProvider.Stub {

	private final NetworkProvider networkProvider;

	public NetworkProviderBinder(NetworkProvider networkProvider) {
		this.networkProvider = networkProvider;
	}

	@Override
	public List<LocationParcelable> autocompleteStations(CharSequence constraint) throws RemoteException {
		try {
			List<Location> locations = networkProvider.autocompleteStations(constraint);
			return ParcelableHelper.toParcelable(locations);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> defaultProducts() throws RemoteException {
		return ParcelableHelper.toParcelable(networkProvider.defaultProducts());
	}

	@Override
	public PointParcelable[] getArea() throws RemoteException {
		return ParcelableHelper.toParcelable(networkProvider.getArea());
	}

	@Override
	public boolean hasCapabilities(String[] capabilities) throws RemoteException {
		return hasCapabilities(capabilities);
	}

	@Override
	public String id() throws RemoteException {
		return networkProvider.id().name();
	}

	@Override
	public StyleParcelable lineStyle(String network, String line) throws RemoteException {
		return ParcelableHelper.toParcelable(networkProvider.lineStyle(network, line));
	}

	@Override
	public QueryDeparturesResultParcelable queryDepartures(int stationId, int maxDepartures, boolean equivs)
			throws RemoteException {
		try {
			QueryDeparturesResult queryDeparturesResult =
					networkProvider.queryDepartures(stationId, maxDepartures, equivs);
			return ParcelableHelper.toParcelable(queryDeparturesResult);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public QueryTripsResultParcelable queryMoreTrips(QueryTripsContextParcelable context, boolean later, int numTrips)
			throws RemoteException {
		try {
			return ParcelableHelper
					.toParcelable(networkProvider.queryMoreTrips(context.get(), later, numTrips));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public NearbyStationsResultParcelable queryNearbyStations(LocationParcelable location, int maxDistance,
															  int maxStations) throws RemoteException {
		try {
			NearbyStationsResult nearbyStationsResult =
					networkProvider.queryNearbyStations(location.get(), maxDistance, maxStations);
			return ParcelableHelper.toParcelable(nearbyStationsResult);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public QueryTripsResultParcelable queryTrips(LocationParcelable from, LocationParcelable via, LocationParcelable to,
												 long date, boolean dep, int numTrips, List<String> products,
												 String walkSpeed, String accessibility, List<String> options)
			throws RemoteException {
		try {
			QueryTripsResult queryTripsResult = networkProvider
					.queryTrips(from.get(), via.get(), to.get(), new Date(date),
								dep, numTrips, ParcelableHelper.listToEnum(products, Product.class),
								ParcelableHelper.toWalkSpeed(walkSpeed),
								NetworkProvider.Accessibility.valueOf(accessibility),
								ParcelableHelper.toOptionSet(options));
			return ParcelableHelper.toParcelable(queryTripsResult);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
