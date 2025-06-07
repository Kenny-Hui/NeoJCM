package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.FareSaverBlockEntity;
import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.render.RenderHelper;
import com.lx862.jcm.mod.render.text.TextAlignment;
import com.lx862.jcm.mod.render.text.TextInfo;
import com.lx862.jcm.mod.render.text.TextRenderingManager;
import com.lx862.jcm.mod.util.TextUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.block.IBlock;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FareSaverRenderer extends JCMBlockEntityRenderer<FareSaverBlockEntity> {
    public static final int TEXT_TILT_ANGLE = -10;

    public FareSaverRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderCurated(FareSaverBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, float partialTick, int light, int packedOverlay) {
        if(IBlock.getStatePropertySafe(state, BlockProperties.VERTICAL_PART_3) != IBlock.EnumThird.UPPER) return;

        MutableComponent discountText = TextUtil.literal(blockEntity.getPrefix() + blockEntity.getDiscount());

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(0.011F, 0.011F, 0.011F);
        rotateToBlockDirection(poseStack, blockEntity);
        UtilitiesClient.rotateZDegrees(poseStack,180);
        UtilitiesClient.rotateZDegrees(poseStack,TEXT_TILT_ANGLE);
        poseStack.translate(5.8, 14, -9.15);

        poseStack.pushPose();
        TextInfo textInfo = new TextInfo(discountText)
                .withFont("mtr:mtr")
                .withColor(ARGB_WHITE)
                .withTextAlignment(TextAlignment.CENTER);
        RenderHelper.scaleToFit(poseStack, TextRenderingManager.getTextWidth(textInfo), 12, true, 12);
        TextRenderingManager.draw(poseStack, bufferSource, textInfo, 0, 0);
        poseStack.popPose();
        poseStack.popPose();
    }
}
