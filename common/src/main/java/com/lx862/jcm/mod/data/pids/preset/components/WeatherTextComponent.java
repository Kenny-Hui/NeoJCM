package com.lx862.jcm.mod.data.pids.preset.components;

import com.google.gson.JsonObject;
import com.lx862.jcm.mod.data.KVPair;
import com.lx862.jcm.mod.data.pids.preset.PIDSContext;
import com.lx862.jcm.mod.data.pids.preset.components.base.PIDSComponent;
import com.lx862.jcm.mod.data.pids.preset.components.base.TextComponent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;

public class WeatherTextComponent extends TextComponent {
    private final String sunnyText;
    private final String rainyText;
    private final String thunderstormText;

    public WeatherTextComponent(double x, double y, double width, double height, KVPair additionalParam) {
        super(x, y, width, height, additionalParam);
        this.sunnyText = additionalParam.get("sunnyText", "Sunny");
        this.rainyText = additionalParam.get("rainyText", "Rainy");
        this.thunderstormText = additionalParam.get("thunderstormText", "Thunderstorm");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, Direction facing, PIDSContext context) {
        drawText(poseStack, bufferSource, context.level.isRaining() ? rainyText : context.level.isThundering() ? thunderstormText : sunnyText);
    }

    public static PIDSComponent parseComponent(double x, double y, double width, double height, JsonObject jsonObject) {
        return new WeatherTextComponent(x, y, width, height, new KVPair(jsonObject));
    }
}
