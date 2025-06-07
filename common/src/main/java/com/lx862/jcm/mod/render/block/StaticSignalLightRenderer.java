package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.render.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.MTR;
import mtr.block.BlockSignalLightBase;
import mtr.client.IDrawing;
import mtr.mappings.BlockEntityMapper;
import mtr.block.IBlock;
import mtr.mappings.UtilitiesClient;
import mtr.render.MoreRenderLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class StaticSignalLightRenderer<T extends BlockEntityMapper> extends JCMBlockEntityRenderer<T> {
    private final boolean drawOnTop;
    private final int color;

    public StaticSignalLightRenderer(BlockEntityRenderDispatcher dispatcher, int color, boolean drawOnTop) {
        super(dispatcher);
        this.color = color;
        this.drawOnTop = drawOnTop;
    }

    @Override
    public void renderCurated(T blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, float partialTick, int light, int packedOverlay) {
        final Direction facing = IBlock.getStatePropertySafe(state, BlockProperties.FACING);
        final float angle = facing.toYRot();

        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);

        poseStack.pushPose();
        UtilitiesClient.rotateYDegrees(poseStack, -angle);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(MoreRenderLayers.getLight(MTR.id("textures/block/white.png"), false));
        drawSignalLight(poseStack, vertexConsumer, blockEntity, partialTick, facing);
        poseStack.popPose();

        poseStack.popPose();
    }

    private void drawSignalLight(PoseStack poseStack, VertexConsumer vertexConsumer, T blockEntity, float partialTick, Direction facing) {
        final float y = drawOnTop ? 0.4375F : 0.0625F;
        IDrawing.drawTexture(poseStack, vertexConsumer, -0.125F, y, -0.19375F, 0.125F, y + 0.25F, -0.19375F, facing.getOpposite(), color, RenderHelper.MAX_RENDER_LIGHT);
    }
}
