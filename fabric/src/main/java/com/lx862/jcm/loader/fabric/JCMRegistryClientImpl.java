package com.lx862.jcm.loader.fabric;

import mtr.MTRClient;
import mtr.mappings.BlockEntityMapper;
import mtr.mappings.BlockEntityRendererMapper;
import mtr.mappings.EntityRendererMapper;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Consumer;
import java.util.function.Function;

public class JCMRegistryClientImpl {

	public static void registerBlockRenderType(RenderType type, Block block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, type);
	}

	public static void registerItemModelPredicate(String id, Item item, String tag) {
		FabricModelPredicateProviderRegistry.register(item, ResourceLocation.parse(id), (itemStack, clientWorld, livingEntity, i) -> itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).contains(tag) ? 1 : 0);
	}

	public static <T extends BlockEntityMapper> void registerTileEntityRenderer(BlockEntityType<T> type, Function<BlockEntityRenderDispatcher, BlockEntityRendererMapper<T>> function) {
		BlockEntityRendererRegistry.register(type, context -> function.apply(null));
	}

	public static <T extends Entity> void registerEntityRenderer(EntityType<T> type, Function<Object, EntityRendererMapper<T>> function) {
		EntityRendererRegistry.register(type, function::apply);
	}

	public static void registerKeyBinding(KeyMapping keyMapping) {
		KeyBindingHelper.registerKeyBinding(keyMapping);
	}

	public static void registerBlockColors(Block block) {
		ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> MTRClient.getStationColor(pos), block);
	}

	public static void registerNetworkReceiver(ResourceLocation resourceLocation, Consumer<FriendlyByteBuf> consumer) {
		JCMRegistryImpl.PACKET_REGISTRY.registerNetworkReceiverS2C(resourceLocation, consumer);
	}

	public static void registerPlayerJoinEvent(Consumer<LocalPlayer> consumer) {
		ClientEntityEvents.ENTITY_LOAD.register((entity, clientWorld) -> {
			if (entity == Minecraft.getInstance().player) {
				consumer.accept((LocalPlayer) entity);
			}
		});
	}

	public static void registerTickEvent(Consumer<Minecraft> consumer) {
		ClientTickEvents.START_CLIENT_TICK.register(consumer::accept);
	}

	public static void sendToServer(ResourceLocation id, FriendlyByteBuf packet) {
		JCMRegistryImpl.PACKET_REGISTRY.sendC2S(id, packet);
	}
}
