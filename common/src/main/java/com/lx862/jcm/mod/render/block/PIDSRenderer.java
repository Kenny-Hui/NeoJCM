package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.PIDSBlockEntity;
import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.data.pids.PIDSManager;
import com.lx862.jcm.mod.data.pids.preset.PIDSPresetBase;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.longs.LongImmutableList;
import mtr.client.ClientData;
import mtr.data.RailwayData;
import mtr.data.ScheduleEntry;
import mtr.mappings.UtilitiesClient;
import mtr.block.IBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public abstract class PIDSRenderer<T extends PIDSBlockEntity> extends JCMBlockEntityRenderer<T> {
    public PIDSRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderCurated(T blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, float tickDelta, int light, int packedLightOverlay) {
        PIDSPresetBase pidsPreset = getPreset(blockEntity);
        if(pidsPreset == null) return;

        Direction facing = IBlock.getStatePropertySafe(state, BlockProperties.FACING);

        boolean[] rowHidden = new boolean[blockEntity.getRowAmount()];
        boolean[] beRowHidden = blockEntity.getRowHidden();
        for(int i = 0; i < rowHidden.length; i++) {
            rowHidden[i] = pidsPreset.isRowHidden(i) || beRowHidden[i];
        }

        LongImmutableList platforms;
        if(!blockEntity.getPlatformIds().isEmpty()) {
            platforms = new LongImmutableList(blockEntity.getPlatformIds());
        } else {
            final long platformId = RailwayData.getClosePlatformId(ClientData.PLATFORMS, ClientData.DATA_CACHE, pos, 5, 4, 4);
            if (platformId != 0) {
                platforms = new LongImmutableList(new long[]{platformId});
            } else {
                platforms = new LongImmutableList(new long[]{});
            }
        }

        List<ScheduleEntry> arrivals = new ArrayList<>();
        for(long platformId : platforms) {
            arrivals.addAll(ClientData.SCHEDULES_FOR_PLATFORM.getOrDefault(platformId, new HashSet<>()));
        }
        Collections.sort(arrivals);

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        UtilitiesClient.rotateYDegrees(poseStack, 90 - facing.toYRot());
        UtilitiesClient.rotateZDegrees(poseStack, 180);
        renderPIDS(blockEntity, pidsPreset, poseStack, bufferSource, world, state, pos, facing, arrivals, tickDelta, rowHidden);
        poseStack.popPose();
    }

    public abstract void renderPIDS(T blockEntity, PIDSPresetBase pidsPreset, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, Direction facing, List<ScheduleEntry> arrivals, float tickDelta, boolean[] rowHidden);

    private PIDSPresetBase getPreset(PIDSBlockEntity blockEntity) {
        return PIDSManager.getPreset(blockEntity.getPresetId(), PIDSManager.getPreset(blockEntity.getDefaultPresetId()));
    }
}
