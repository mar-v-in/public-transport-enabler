package de.schildbach.pte.android.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.schildbach.pte.NetworkId;
import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.android.INetworkProvider;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.EnumMap;
import java.util.Map;

public final class NetworkProviderFactory {
	private static Map<NetworkId, Reference<NetworkProvider>> providerRef =
			new EnumMap<NetworkId, Reference<NetworkProvider>>(NetworkId.class);

	private static void createAsync(Context context, final NetworkId networkId, String... args) {
		Intent intent = new Intent("de.schildbach.pte.android.NetworkProviderService.GET_PROVIDER");
		intent.putExtra("networkId", networkId.name());
		intent.putExtra("networkProviderParam", args);
		context.bindService(intent, new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
				synchronized (providerRef) {
					providerRef.put(networkId, new SoftReference<NetworkProvider>(new BoundNetworkProvider((INetworkProvider) iBinder)));
					providerRef.notifyAll();
				}
			}

			@Override
			public void onServiceDisconnected(ComponentName componentName) {
				synchronized (providerRef) {
					providerRef.put(networkId, null);
					providerRef.notifyAll();
				}
			}
		}, Context.BIND_AUTO_CREATE);
	}

	public static NetworkProvider provider(Context context, final NetworkId networkId, String... args) {
		NetworkProvider provider = null;
		if (providerRef.containsKey(networkId)) {
			Reference<NetworkProvider> networkProviderReference = providerRef.get(networkId);
			if (networkProviderReference != null) {
				provider = networkProviderReference.get();
				if (provider != null) {
					return provider;
				}
			}
		}
		createAsync(context, networkId, args);
		synchronized (providerRef) {
			try {
				while (provider == null) {
					providerRef.wait();
					if (providerRef.containsKey(networkId)) {
						Reference<NetworkProvider> networkProviderReference = providerRef.get(networkId);
						if (networkProviderReference != null) {
							provider = networkProviderReference.get();
						}
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		return provider;
	}
}