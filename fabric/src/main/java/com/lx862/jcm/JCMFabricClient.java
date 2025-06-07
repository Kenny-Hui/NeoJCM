package com.lx862.jcm;

import com.lx862.jcm.loader.fabric.JCMRegistryImpl;
import com.lx862.jcm.mod.JCMClient;
import net.fabricmc.api.ClientModInitializer;

public class JCMFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        JCMClient.initializeClient();
        JCMRegistryImpl.PACKET_REGISTRY.commitClient();
    }
}
