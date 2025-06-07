package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.block.entity.PIDSProjectorBlockEntity;
import com.lx862.jcm.mod.data.pids.preset.PIDSPresetBase;
import com.lx862.jcm.mod.render.RenderHelper;
import com.lx862.jcm.mod.util.JCMUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.data.ScheduleEntry;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PIDSProjectorRenderer extends PIDSRenderer<PIDSProjectorBlockEntity> {
    public PIDSProjectorRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderPIDS(PIDSProjectorBlockEntity blockEntity, PIDSPresetBase pidsPreset, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, Direction facing, List<ScheduleEntry> arrivals, float tickDelta, boolean[] rowHidden) {
        UtilitiesClient.rotateYDegrees(poseStack, 90);
        float scale = (float)blockEntity.getScale();
        boolean showOutline = JCMUtil.playerHoldingBrush(Minecraft.getInstance().player);
        poseStack.translate(-0.5 + blockEntity.getOffsetX(), -0.5 - blockEntity.getOffsetY(), 0.5 + blockEntity.getOffsetZ());

        UtilitiesClient.rotateXDegrees(poseStack, (float)blockEntity.getRotateX());
        UtilitiesClient.rotateYDegrees(poseStack, (float)blockEntity.getRotateY());
        UtilitiesClient.rotateZDegrees(poseStack, (float)blockEntity.getRotateZ());

        // Draw projection effect
        if(showOutline && blockEntity.getRotateX() == 0 && blockEntity.getRotateY() == 0 && blockEntity.getRotateZ() == 0) {
            poseStack.pushPose();
            VertexConsumer lineConsumer = bufferSource.getBuffer(RenderType.lines());

            float offsetX = (float)(0.5 - blockEntity.getOffsetX());
            float offsetY = (float)(0.5 + blockEntity.getOffsetY());
            float offsetZ = (float)(-0.5 - blockEntity.getOffsetZ());

            RenderHelper.drawLine(poseStack, lineConsumer, offsetX, offsetY, offsetZ, 0, 0, 0, 0xFFFF0000);
            RenderHelper.drawLine(poseStack, lineConsumer, offsetX, offsetY, offsetZ, 0 + (1.785f * scale), 0, 0, 0xFFFF0000);
            RenderHelper.drawLine(poseStack, lineConsumer, offsetX, offsetY, offsetZ, 0, 0 + (1 * scale), 0, 0xFFFF0000);
            RenderHelper.drawLine(poseStack, lineConsumer, offsetX, offsetY, offsetZ, 0 + (1.785f * scale), 0 + (1 * scale), 0, 0xFFFF0000);
            poseStack.popPose();
        }

        poseStack.scale(1/76F, 1/76F, 1/76F);
        poseStack.scale(scale, scale, scale);
        pidsPreset.render(blockEntity, poseStack, bufferSource, world, blockEntity.getBlockPos(), facing, arrivals, rowHidden, tickDelta, 0, 0, 136, 76);

        // Border
        if(showOutline) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.beaconBeam(Constants.id("textures/block/light_1.png"), false));
            RenderHelper.drawTexture(poseStack, vertexConsumer, -8, -1, 0.1f, 138, 78, facing, 0xFFFF0000, MAX_RENDER_LIGHT);
        }
    }
}