package com.lx862.jcm;

import com.lx862.jcm.loader.fabric.JCMRegistryImpl;
import com.lx862.jcm.mod.JCM;
import net.fabricmc.api.ModInitializer;

public class JCMFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        JCM.init();
        JCMRegistryImpl.PACKET_REGISTRY.commitCommon();
    }
}
