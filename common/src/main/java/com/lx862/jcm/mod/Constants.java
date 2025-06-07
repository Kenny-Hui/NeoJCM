package com.lx862.jcm.mod;

import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final String MOD_NAME = "NeoJCM";
    public static final String MOD_ID = "jsblock";
    public static final String LOGGING_PREFIX = "[NeoJCM] ";
    public static final String MOD_VERSION = "2.0.0-prerelease.2";
    public static final int MC_TICK_PER_SECOND = 20;

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }
}
