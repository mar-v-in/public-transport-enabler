Clients will need the following packages(P), classes (C) and AIDL file (A):

    A de.schildbach.pte.android.INetworkProvider
    P de.schildbach.pte.android.client
    P de.schildbach.pte.dto
    P de.schildbach.pte.dto.parcelable
    C de.schildbach.pte.NetworkId
    C de.schildbach.pte.NetworkProvider

A `NetworkProvider` is than created using
`de.schildbach.pte.android.client.NetworkProviderFactory.provider(Context, NetworkId, String...)`
eg. using `NetworkProviderFactory.provider(this, NetworkId.DB);`

The resulting NetworkProvider works as usual, but note that all methods are RPC methods, so even `NetworkProvider.id()` should only be used asynchronous (using `AsyncTask` or a `Thread`)

Sample code:
```java
package de.schildbach.pte.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import de.schildbach.pte.NetworkId;
import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.android.client.NetworkProviderFactory;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.LocationType;

import java.io.IOException;

public class TestActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... voids) {
				test();
				return null;
			}
		};
		asyncTask.execute();
	}

	private void test() {
		Log.d("TEST", "before provider");
		NetworkProvider provider = NetworkProviderFactory.provider(this, NetworkId.DB);
		Log.d("TEST", "after provider");
		Log.d("TEST", "ID: " + provider.id());
		Location location = new Location(LocationType.ANY, 52519564, 13402526); // Berlin
		try {
			Log.d("TEST", "Next station:" + provider.queryNearbyStations(location, 5000, 100).stations.get(0).name);
		} catch (IOException e) {
			Log.w("TEST", e);
		}
	}
}
```
