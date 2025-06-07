package com.lx862.jcm;

import com.lx862.jcm.loader.neoforge.JCMRegistryImpl;
import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.JCM;
import com.lx862.jcm.mod.JCMClient;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import com.lx862.jcm.loader.neoforge.ClientProxy;
import com.lx862.jcm.loader.neoforge.ForgeUtilities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class NeoJCMForge {
	static {
		JCM.init();
	}

	public NeoJCMForge(IEventBus eventBus) {
		JCMRegistryImpl.registerAllDeferred(eventBus);
		eventBus.register(NeoJCMEventBus.class);
		eventBus.register(ForgeUtilities.RegisterCreativeTabs.class);
		if (FMLEnvironment.dist.isClient()) {
			ClientProxy.registerConfigScreen();
			NeoForge.EVENT_BUS.register(ForgeUtilities.Events.class);
			eventBus.register(ForgeUtilities.ClientsideEvents.class);
		}
	}

	private static class NeoJCMEventBus {
		@SubscribeEvent
		public static void onClientSetupEvent(FMLClientSetupEvent event) {
			JCMClient.initializeClient();
		}

		@SubscribeEvent
		public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
			PayloadRegistrar registrar = event.registrar("1");
			JCMRegistryImpl.PACKET_REGISTRY.commit(registrar);
		}
	}
}
