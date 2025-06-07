package com.lx862.jcm.mod.registry;

import com.lx862.jcm.loader.JCMRegistryClient;
import com.lx862.jcm.mod.render.block.*;
import com.lx862.jcm.mod.util.JCMLogger;

public final class BlockEntityRenderers {
    public static void registerClient() {
        JCMLogger.debug("Registering Block Entity Renderer...");
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.APG_DOOR_DRL.get(), (dispatcher) -> new RenderDRLAPGDoor<>(dispatcher, 2));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.BUTTERFLY_LIGHT.get(), ButterflyLightRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.DEPARTURE_TIMER.get(), DepartureTimerRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.FARE_SAVER.get(), FareSaverRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.KCR_STATION_NAME_SIGN.get(), KCRStationNameSignRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.KCR_STATION_NAME_SIGN_STATION_COLOR.get(), KCRStationNameSignRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.SIGNAL_LIGHT_INVERTED_RED_ABOVE.get(), (dispatcher) -> new SignalBlockInvertedRenderer<>(dispatcher, 0xFF0000FF, true));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.SIGNAL_LIGHT_INVERTED_RED_BELOW.get(), (dispatcher) -> new SignalBlockInvertedRenderer<>(dispatcher, 0xFF00FF00, false));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.SIGNAL_LIGHT_RED_BELOW.get(), (dispatcher) -> new StaticSignalLightRenderer<>(dispatcher, 0xFFFF0000, false));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.SIGNAL_LIGHT_RED_TOP.get(), (dispatcher) -> new StaticSignalLightRenderer<>(dispatcher, 0xFFFF0000, true));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.SIGNAL_LIGHT_BLUE.get(), (dispatcher) -> new StaticSignalLightRenderer<>(dispatcher, 0xFF0000FF, true));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.SIGNAL_LIGHT_GREEN.get(), (dispatcher) -> new StaticSignalLightRenderer<>(dispatcher, 0xFF00FF00, false));
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.PIDS_1A.get(), PIDS1ARenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.PIDS_PROJECTOR.get(), PIDSProjectorRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.LCD_PIDS.get(), LCDPIDSRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.RV_PIDS.get(), RVPIDSRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.RV_PIDS_SIL_1.get(), RVPIDSSILRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.RV_PIDS_SIL_2.get(), RVPIDSSILRenderer::new);
        JCMRegistryClient.registerTileEntityRenderer(BlockEntities.STATION_NAME_STANDING.get(), StationNameStandingRenderer::new);
    }
}
