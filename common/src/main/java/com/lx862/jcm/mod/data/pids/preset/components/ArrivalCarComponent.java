package com.lx862.jcm.mod.data.pids.preset.components;

import com.google.gson.JsonObject;
import com.lx862.jcm.mod.data.KVPair;
import com.lx862.jcm.mod.data.pids.preset.PIDSContext;
import com.lx862.jcm.mod.data.pids.preset.components.base.PIDSComponent;
import com.lx862.jcm.mod.data.pids.preset.components.base.TextComponent;
import com.lx862.jcm.mod.render.text.TextInfo;
import com.lx862.jcm.mod.util.TextUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.data.ScheduleEntry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class ArrivalCarComponent extends TextComponent {
    private final int arrivalIndex;
    public ArrivalCarComponent(double x, double y, double width, double height, KVPair additionalParam) {
        super(x, y, width, height, additionalParam);
        this.arrivalIndex = additionalParam.getInt("arrivalIndex", 0);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, Direction facing, PIDSContext context) {
        List<ScheduleEntry> arrivals = context.arrivals;
        if(arrivalIndex >= arrivals.size()) return;

        ScheduleEntry arrival = arrivals.get(arrivalIndex);
        final MutableComponent text = TextUtil.literal(arrival.trainCars + cycleString("卡", arrival.trainCars > 1 ? "-cars" : "-car"));

        drawText(poseStack, bufferSource, new TextInfo(text));
    }

    public static PIDSComponent parseComponent(double x, double y, double width, double height, JsonObject jsonObject) {
        return new ArrivalCarComponent(x, y, width, height, new KVPair(jsonObject));
    }
}
