package com.lx862.jcm;

import com.lx862.jcm.mod.JCMClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuConfig implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return JCMClient::getClientConfigScreen;
    }
}
