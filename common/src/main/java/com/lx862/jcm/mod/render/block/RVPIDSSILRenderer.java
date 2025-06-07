package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.PIDSBlockEntity;
import com.lx862.jcm.mod.data.pids.preset.PIDSPresetBase;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.data.ScheduleEntry;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RVPIDSSILRenderer<T extends PIDSBlockEntity> extends PIDSRenderer<T> {
    public RVPIDSSILRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderPIDS(T blockEntity, PIDSPresetBase pidsPreset, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, Direction facing, List<ScheduleEntry> arrivals, float tickDelta, boolean[] rowHidden) {
        poseStack.translate(-0.21, -0.155, -0.650);
        UtilitiesClient.rotateXDegrees(poseStack, 22.5f);
        poseStack.scale(1/96F, 1/96F, 1/96F);
        pidsPreset.render(blockEntity, poseStack, bufferSource, world, blockEntity.getBlockPos(), facing, arrivals, rowHidden, tickDelta, 0, 0, 136, 76);
    }
}
