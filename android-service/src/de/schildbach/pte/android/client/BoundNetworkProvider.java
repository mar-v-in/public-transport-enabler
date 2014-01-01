package de.schildbach.pte.android.client;

import android.os.RemoteException;
import de.schildbach.pte.NetworkId;
import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.android.INetworkProvider;
import de.schildbach.pte.dto.*;
import de.schildbach.pte.dto.parcelable.ParcelableHelper;
import de.schildbach.pte.dto.parcelable.QueryTripsContextParcelable;

import java.io.IOException;
import java.util.*;

class BoundNetworkProvider implements NetworkProvider {
	private final INetworkProvider networkProvider;

	public BoundNetworkProvider(INetworkProvider networkProvider) {
		this.networkProvider = networkProvider;
	}

	@Override
	public List<Location> autocompleteStations(CharSequence constraint) throws IOException {
		try {
			return ParcelableHelper.listToSerializable(networkProvider.autocompleteStations(constraint));
		} catch (RemoteException e) {
			throw new IOException(e);
		}
	}

	@Override
	public Collection<Product> defaultProducts() {
		try {
			return ParcelableHelper.listToEnum(networkProvider.defaultProducts(), Product.class);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Point[] getArea() {
		try {
			List<Point> points = ParcelableHelper.listToSerializable(Arrays.asList(networkProvider.getArea()));
			return points.toArray(new Point[points.size()]);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasCapabilities(Capability... capabilities) {
		try {
			return networkProvider.hasCapabilities(ParcelableHelper.toParcelable(capabilities));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public NetworkId id() {
		try {
			return NetworkId.valueOf(networkProvider.id());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Style lineStyle(String network, String line) {
		try {
			return networkProvider.lineStyle(network, line).get();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public QueryDeparturesResult queryDepartures(int stationId, int maxDepartures, boolean equivs) throws IOException {
		try {
			return networkProvider.queryDepartures(stationId, maxDepartures, equivs).get();
		} catch (RemoteException e) {
			throw new IOException(e);
		}
	}

	@Override
	public QueryTripsResult queryMoreTrips(QueryTripsContext context, boolean later, int numTrips) throws IOException {
		try {
			return networkProvider.queryMoreTrips(new QueryTripsContextParcelable(context), later, numTrips)
								  .get();
		} catch (RemoteException e) {
			throw new IOException(e);
		}
	}

	@Override
	public NearbyStationsResult queryNearbyStations(Location location, int maxDistance, int maxStations)
			throws IOException {
		try {
			return networkProvider
					.queryNearbyStations(ParcelableHelper.toParcelable(location), maxDistance, maxStations)
					.get();
		} catch (RemoteException e) {
			throw new IOException(e);
		}
	}

	@Override
	public QueryTripsResult queryTrips(Location from, Location via, Location to, Date date, boolean dep, int numTrips,
									   Collection<Product> products, WalkSpeed walkSpeed, Accessibility accessibility,
									   Set<Option> options) throws IOException {
		try {
			return networkProvider.queryTrips(ParcelableHelper.toParcelable(from), ParcelableHelper.toParcelable(via),
											  ParcelableHelper.toParcelable(to), date.getTime(), dep, numTrips,
											  ParcelableHelper.toParcelable(products), walkSpeed.name(),
											  accessibility.name(), ParcelableHelper.toParcelable(options))
								  .get();
		} catch (RemoteException e) {
			throw new IOException(e);
		}
	}
}
