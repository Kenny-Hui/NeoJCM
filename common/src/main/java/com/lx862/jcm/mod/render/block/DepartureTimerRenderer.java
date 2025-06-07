package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.DepartureTimerBlockEntity;
import com.lx862.jcm.mod.render.text.TextInfo;
import com.lx862.jcm.mod.render.text.TextRenderingManager;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.client.ClientData;
import mtr.data.Platform;
import mtr.data.RailwayData;
import mtr.data.ScheduleEntry;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DepartureTimerRenderer extends JCMBlockEntityRenderer<DepartureTimerBlockEntity> {
    public DepartureTimerRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderCurated(DepartureTimerBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, Level world, net.minecraft.world.level.block.state.BlockState state, net.minecraft.core.BlockPos pos, float partialTick, int light, int packedOverlay) {
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.018F, 0.018F, 0.018F);
        rotateToBlockDirection(poseStack, blockEntity);
        UtilitiesClient.rotateZDegrees(poseStack, 180);
        poseStack.translate(-12.5, -2, -4.1);

        final long platformId = RailwayData.getClosePlatformId(ClientData.PLATFORMS, ClientData.DATA_CACHE, pos, 5, 3, 3);
        if (platformId == 0) {
            return;
        }

        final Set<ScheduleEntry> schedules = ClientData.SCHEDULES_FOR_PLATFORM.get(platformId);
        if (schedules == null || schedules.isEmpty()) {
            return;
        }
        final List<ScheduleEntry> scheduleList = new ArrayList<>(schedules);
        Collections.sort(scheduleList);
        final ScheduleEntry firstArrival = scheduleList.getFirst();

        boolean arrived = firstArrival.arrivalMillis <= System.currentTimeMillis();
        if (!arrived) return;

        int remainingSecond = (int) (firstArrival.arrivalMillis - System.currentTimeMillis()) / 1000;
        final Platform platform = ClientData.DATA_CACHE.platformIdMap.get(platformId);
        long dwellLeft = (platform == null ? 0 : Math.abs((platform.getDwellTime() / 2) - Math.abs(remainingSecond))) * 1000L;

        long seconds = dwellLeft / 1000;
        long mins = seconds / 60;

        TextRenderingManager.draw(poseStack, bufferSource, new TextInfo(String.format("%d:%02d", mins % 10, seconds % 60)).withColor(0xFFEE2233).withFont("jsblock:deptimer"), 0, 0);
    }
}
