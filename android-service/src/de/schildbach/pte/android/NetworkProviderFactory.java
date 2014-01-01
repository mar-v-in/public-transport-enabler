package de.schildbach.pte.android;

import android.util.Log;
import de.schildbach.pte.*;

import java.util.EnumMap;
import java.util.Map;

class NetworkProviderFactory {

	private static final String TAG = "NetworkProviderService";
	private static Map<NetworkId, Class<? extends NetworkProvider>> map =
			new EnumMap<NetworkId, Class<? extends NetworkProvider>>(NetworkId.class);

	public static void addClass(Class<? extends NetworkProvider> clazz) {
		try {
			map.put((NetworkId) clazz.getField("NETWORK_ID").get(null), clazz);
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	static {
		addClass(RtProvider.class);
		addClass(BahnProvider.class);
		addClass(BvgProvider.class);
		addClass(VbbProvider.class);
		addClass(NvvProvider.class);
		addClass(BayernProvider.class);
		addClass(MvvProvider.class);
		addClass(InvgProvider.class);
		addClass(AvvProvider.class);
		addClass(VgnProvider.class);
		addClass(VvmProvider.class);
		addClass(VmvProvider.class);
		addClass(ShProvider.class);
		addClass(GvhProvider.class);
		addClass(BsvagProvider.class);
		addClass(BsagProvider.class);
		addClass(VbnProvider.class);
		addClass(NasaProvider.class);
		addClass(VvoProvider.class);
		addClass(VmsProvider.class);
		addClass(VrrProvider.class);
		addClass(MvgProvider.class);
		addClass(VrnProvider.class);
		addClass(VvsProvider.class);
		addClass(NaldoProvider.class);
		addClass(DingProvider.class);
		addClass(KvvProvider.class);
		addClass(VagfrProvider.class);
		addClass(NvbwProvider.class);
		addClass(VvvProvider.class);
		addClass(OebbProvider.class);
		addClass(VorProvider.class);
		addClass(LinzProvider.class);
		addClass(SvvProvider.class);
		addClass(VvtProvider.class);
		addClass(VmobilProvider.class);
		addClass(IvbProvider.class);
		addClass(StvProvider.class);
		addClass(SbbProvider.class);
		addClass(BvbProvider.class);
		addClass(VblProvider.class);
		addClass(ZvvProvider.class);
		addClass(SncbProvider.class);
		addClass(LuProvider.class);
		addClass(NsProvider.class);
		addClass(DsbProvider.class);
		addClass(SeProvider.class);
		addClass(StockholmProvider.class);
		addClass(NriProvider.class);
		addClass(TflProvider.class);
		addClass(TlemProvider.class);
		addClass(TlwmProvider.class);
		addClass(TlswProvider.class);
		addClass(TfiProvider.class);
		addClass(MariborProvider.class);
		addClass(PlProvider.class);
		addClass(AtcProvider.class);
		addClass(DubProvider.class);
		addClass(SfProvider.class);
		addClass(SeptaProvider.class);
		addClass(SydneyProvider.class);
		addClass(MetProvider.class);
		addClass(VgsProvider.class);
		addClass(SadProvider.class);
		addClass(WienProvider.class);
		addClass(VrtProvider.class);
	}

	public static NetworkProvider create(final NetworkId networkId, String... args) {
		if (map.containsKey(networkId)) {
			Class<? extends NetworkProvider> clazz = map.get(networkId);
			if (clazz != null) {
				Class[] argClasses = new Class[args.length];
				for (int i = 0; i < argClasses.length; ++i) {
					argClasses[i] = String.class;
				}
				try {
					return clazz.getConstructor(argClasses).newInstance(args);
				} catch (Exception e) {
					Log.w(TAG, e);
				}
			}
		}
		return null;
	}
}
