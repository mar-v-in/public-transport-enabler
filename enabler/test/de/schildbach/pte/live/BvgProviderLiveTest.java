/*
 * Copyright 2010-2013 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.pte.live;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.schildbach.pte.BvgProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.NearbyStationsResult;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryDeparturesResult;
import de.schildbach.pte.dto.QueryTripsResult;

/**
 * @author Andreas Schildbach
 */
public class BvgProviderLiveTest extends AbstractProviderLiveTest
{
	public BvgProviderLiveTest()
	{
		super(new BvgProvider(null));
	}

	@Test
	public void nearbyStations() throws Exception
	{
		final NearbyStationsResult result = provider.queryNearbyStations(new Location(LocationType.STATION, 9220302), 0, 0);
		assertEquals(NearbyStationsResult.Status.OK, result.status);
		print(result);
	}

	@Test
	public void nearbyStationsByCoordinate() throws Exception
	{
		final NearbyStationsResult result = provider.queryNearbyStations(new Location(LocationType.ADDRESS, 52486400, 13350744), 0, 0);

		print(result);
	}

	@Test
	public void nearbyStationsInvalidStation() throws Exception
	{
		final NearbyStationsResult result = provider.queryNearbyStations(new Location(LocationType.STATION, 2449475), 0, 0);
		assertEquals(NearbyStationsResult.Status.INVALID_STATION, result.status);
	}

	@Test
	public void queryDepartures() throws Exception
	{
		final QueryDeparturesResult resultLive = provider.queryDepartures(309557, 0, false);
		assertEquals(QueryDeparturesResult.Status.OK, resultLive.status);
		System.out.println(resultLive.stationDepartures);

		final QueryDeparturesResult resultPlan = provider.queryDepartures(9100003, 0, false);
		assertEquals(QueryDeparturesResult.Status.OK, resultPlan.status);
		System.out.println(resultPlan.stationDepartures);
	}

	@Test
	public void queryDeparturesInvalidStation() throws Exception
	{
		final QueryDeparturesResult resultLive = provider.queryDepartures(111111, 0, false);
		assertEquals(QueryDeparturesResult.Status.INVALID_STATION, resultLive.status);

		final QueryDeparturesResult resultPlan = provider.queryDepartures(2449475, 0, false);
		assertEquals(QueryDeparturesResult.Status.INVALID_STATION, resultPlan.status);
	}

	@Test
	public void autocompleteUmlaut() throws Exception
	{
		final List<Location> autocompletes = provider.autocompleteStations("Güntzelstr.");

		print(autocompletes);

		Assert.assertEquals("Güntzelstr. (U)", autocompletes.get(0).name);
	}

	@Test
	public void autocompleteAddress() throws Exception
	{
		final List<Location> autocompletes = provider.autocompleteStations("Sophienstr. 24");

		print(autocompletes);

		Assert.assertEquals("Sophienstr. 24", autocompletes.get(0).name);
	}

	@Test
	public void autocompleteIncomplete() throws Exception
	{
		final List<Location> autocompletes = provider.autocompleteStations("nol");

		print(autocompletes);
	}

	@Test
	public void shortTrip() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.STATION, 9056102, "Berlin", "Nollendorfplatz"), null, new Location(
				LocationType.STATION, 9013103, "Berlin", "Prinzenstraße"), new Date(), true, Product.ALL, WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
		final QueryTripsResult later2Result = queryMoreTrips(laterResult.context, true);
		System.out.println(later2Result);
		final QueryTripsResult earlierResult = queryMoreTrips(later2Result.context, false);
		System.out.println(earlierResult);
		final QueryTripsResult later3Result = queryMoreTrips(earlierResult.context, true);
		System.out.println(later3Result);
	}

	@Test
	public void shortViaTrip() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.STATION, 9056102, "Berlin", "Nollendorfplatz"), new Location(
				LocationType.STATION, 9044202, "Berlin", "Bundesplatz"), new Location(LocationType.STATION, 9013103, "Berlin", "Prinzenstraße"),
				new Date(), true, Product.ALL, WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
	}

	@Test
	public void tripBetweenCoordinates() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.ADDRESS, 0, 52501507, 13357026, null, null), null, new Location(
				LocationType.ADDRESS, 0, 52513639, 13568648, null, null), new Date(), true, Product.ALL, WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
	}

	@Test
	public void tripBetweenCoordinatesAndAddresses() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.ADDRESS, 0, 52536099, 13426309, null,
				"Christburger Straße 1, 10405 Berlin, Deutschland"), null, new Location(LocationType.ADDRESS, 0, 52486400, 13350744, null,
				"Eisenacher Straße 70, 10823 Berlin, Deutschland"), new Date(), true, Product.ALL, WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
	}

	@Test
	public void viaTripBetweenCoordinates() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.ADDRESS, 0, 52501507, 13357026, null, null), new Location(
				LocationType.ADDRESS, 0, 52479868, 13324247, null, null), new Location(LocationType.ADDRESS, 0, 52513639, 13568648, null, null),
				new Date(), true, Product.ALL, WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
	}

	@Test
	public void tripBetweenAddresses() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.ADDRESS, 0, 52479663, 13324278, "10715 Berlin-Wilmersdorf",
				"Weimarische Str. 7"), null, new Location(LocationType.ADDRESS, 0, 52541536, 13421290, "10437 Berlin-Prenzlauer Berg",
				"Göhrener Str. 5"), new Date(), true, Product.ALL, WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
	}

	@Test
	public void viaTripBetweenAddresses() throws Exception
	{
		final QueryTripsResult result = queryTrips(new Location(LocationType.ADDRESS, 0, 52479663, 13324278, "10715 Berlin-Wilmersdorf",
				"Weimarische Str. 7"), new Location(LocationType.ADDRESS, 0, 52527872, 13381657, "10115 Berlin-Mitte", "Hannoversche Str. 20"),
				new Location(LocationType.ADDRESS, 0, 52526029, 13399878, "10178 Berlin-Mitte", "Sophienstr. 24"), new Date(), true, Product.ALL,
				WalkSpeed.NORMAL, Accessibility.NEUTRAL);
		System.out.println(result);
		final QueryTripsResult laterResult = queryMoreTrips(result.context, true);
		System.out.println(laterResult);
	}

	@Test
	public void testStationIdReverse() throws Exception
	{
		Assert.assertEquals(BvgProvider.migrateStationIdReverse(101000316), 100316);
		Assert.assertEquals(BvgProvider.migrateStationIdReverse(301000316), 300316);

		// no conversions
		Assert.assertEquals(BvgProvider.migrateStationIdReverse(102000316), 102000316);
		Assert.assertEquals(BvgProvider.migrateStationIdReverse(1101000316), 1101000316);
		Assert.assertEquals(BvgProvider.migrateStationIdReverse(11000316), 11000316);
	}
}
