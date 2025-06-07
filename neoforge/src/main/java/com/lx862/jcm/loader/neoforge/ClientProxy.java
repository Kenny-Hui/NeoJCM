package com.lx862.jcm.loader.neoforge;

import com.lx862.jcm.mod.render.gui.screen.ClientConfigScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ClientProxy {
    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ((modContainer, arg) -> new ClientConfigScreen().withPreviousScreen(arg)));
    }
}
