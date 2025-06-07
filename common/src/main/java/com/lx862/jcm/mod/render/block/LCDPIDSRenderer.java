package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.LCDPIDSBlockEntity;
import com.lx862.jcm.mod.data.pids.preset.PIDSPresetBase;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mtr.data.ScheduleEntry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LCDPIDSRenderer extends PIDSRenderer<LCDPIDSBlockEntity> {
    public LCDPIDSRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderPIDS(LCDPIDSBlockEntity blockEntity, PIDSPresetBase pidsPreset, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, Direction facing, List<ScheduleEntry> arrivals, float tickDelta, boolean[] rowHidden) {
        poseStack.translate(-0.19, -0.125, -0.130);
        poseStack.scale(1/96F, 1/96F, 1/96F);

        pidsPreset.render(blockEntity, poseStack, bufferSource, world, blockEntity.getBlockPos(), facing, arrivals, rowHidden, tickDelta, 0, 0, 133, 72);
    }
}
