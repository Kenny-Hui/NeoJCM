package com.lx862.jcm.mod.mixin.compat;

import com.lx862.jcm.mod.util.JCMUtil;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Block renamed in JCM 2.0.0, unfortunately there's no easy way to migrate ids.
 * This class helps migrate these ids
 * See <a href="https://github.com/Syst3ms/fabric/blob/cc14f2db5affaca2a9db499e711c0f163d64fef0/fabric-registry-sync-v0/src/main/java/net/fabricmc/fabric/mixin/registry/sync/SimpleRegistryMixin.java">here</a>
 */
@Mixin(MappedRegistry.class)
public class MappedRegistryMixin {
    @ModifyVariable(at = @At("HEAD"), method = {
            "get(Lnet/minecraft/resources/ResourceLocation;)Ljava/lang/Object;",
            "getHolder(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;",
            "containsKey(Lnet/minecraft/resources/ResourceLocation;)Z"
    }, argsOnly = true)
    ResourceLocation dataFixerRegistry(ResourceLocation id) {
        if(id == null) return null;
        if((MappedRegistry)(Object)this instanceof DefaultedMappedRegistry<?>) {
            return JCMUtil.getMigrationId(id);
        }
        return id;
    }
}