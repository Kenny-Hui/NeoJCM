package com.lx862.jcm.mappings;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class ArchitecturyUtilities {
    public static void registerCommand(Consumer<CommandDispatcher<CommandSourceStack>> callback) {
        CommandRegistrationEvent.EVENT.register((dispatcher, dedicated, commandSelection) -> callback.accept(dispatcher));
    }

    public static void registerPlayerJoinEvent(Consumer<ServerPlayer> consumer) {
        PlayerEvent.PLAYER_JOIN.register(consumer::accept);
    }

    public static void registerPlayerQuitEvent(Consumer<ServerPlayer> consumer) {
        PlayerEvent.PLAYER_QUIT.register(consumer::accept);
    }

    public static void registerPlayerChangeDimensionEvent(Consumer<ServerPlayer> consumer) {
        PlayerEvent.CHANGE_DIMENSION.register(((player, oldWorld, newWorld) -> consumer.accept(player)));
    }

    public static void registerServerStartingEvent(Consumer<MinecraftServer> consumer) {
        LifecycleEvent.SERVER_STARTING.register(consumer::accept);
    }

    public static void registerServerStoppingEvent(Consumer<MinecraftServer> consumer) {
        LifecycleEvent.SERVER_STOPPING.register(consumer::accept);
    }

    public static void registerTickEvent(Consumer<MinecraftServer> consumer) {
        TickEvent.SERVER_PRE.register(consumer::accept);
    }
}
