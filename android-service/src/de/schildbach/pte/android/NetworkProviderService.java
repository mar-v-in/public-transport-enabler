package de.schildbach.pte.android;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.schildbach.pte.NetworkId;
import de.schildbach.pte.NetworkProvider;

public class NetworkProviderService extends Service {

	public IBinder onBind(Intent intent) {
		if ("de.schildbach.pte.android.NetworkProviderService.GET_PROVIDER".equals(intent.getAction())) {
			if (!intent.hasExtra("networkId")) {
				// TODO: Alternatively use a default provider here
				return null;
			}
			NetworkId networkId = NetworkId.valueOf(intent.getStringExtra("networkId"));
			NetworkProvider networkProvider;
			if (intent.hasExtra("networkProviderParam")) {
				networkProvider =
						NetworkProviderFactory.create(networkId, intent.getStringArrayExtra("networkProviderParam"));
			} else {
				networkProvider = NetworkProviderFactory.create(networkId);
			}
			if (networkProvider == null) return null;
			return new NetworkProviderBinder(networkProvider);
		} else if ("de.schildbach.pte.android.NetworkProviderService.LIST_PROVIDERS".equals(intent.getAction())) {
			// TODO: some method to retrieve a list of known providers...
			return null;
		} else {
			return null;
		}
	}
}
