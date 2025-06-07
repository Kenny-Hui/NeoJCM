package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.KCRStationNameSignBlockEntity;
import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.render.IDrawingJoban;
import com.lx862.jcm.mod.render.RenderHelper;
import com.lx862.jcm.mod.util.TextUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.client.ClientData;
import mtr.data.IGui;
import mtr.data.RailwayData;
import mtr.data.Station;
import mtr.mappings.UtilitiesClient;
import mtr.block.IBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class KCRStationNameSignRenderer extends JCMBlockEntityRenderer<KCRStationNameSignBlockEntity> {
    private static final ResourceLocation KCR_FONT = ResourceLocation.parse("jsblock:kcr_sign");

    public KCRStationNameSignRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void renderCurated(KCRStationNameSignBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, float partialTick, int light, int packedOverlay) {
        poseStack.translate(0.5, 0.5, 0.5);
        rotateToBlockDirection(poseStack, blockEntity);
        UtilitiesClient.rotateZDegrees(poseStack,180);
        UtilitiesClient.rotateYDegrees(poseStack,180);

        final Station station = RailwayData.getStation(ClientData.STATIONS, ClientData.DATA_CACHE, blockEntity.getBlockPos());
        final String stationName = station == null ? TextUtil.translatable("gui.mtr.untitled").getString() : station.name;
        double offsetForExitDirection = IBlock.getStatePropertySafe(state, BlockProperties.EXIT_ON_LEFT) ? -0.225 : 0.225;

        // Draw both sides
        for(int i = 0; i < 2; i++) {
            poseStack.pushPose();
            poseStack.translate(offsetForExitDirection, 0, 0);
            if(i == 1) UtilitiesClient.rotateYDegrees(poseStack, 180); // Other side
            poseStack.translate(0, -0.05, -0.175);
            poseStack.scale(0.021F, 0.021F, 0.021F);
            IDrawingJoban.drawStringWithFont(poseStack, Minecraft.getInstance().font, Minecraft.getInstance().renderBuffers().bufferSource(), stationName, KCR_FONT, IGui.HorizontalAlignment.CENTER, IGui.VerticalAlignment.CENTER, 0, 0, 60, 32, 1, 0xEEEEEE, false, RenderHelper.MAX_RENDER_LIGHT, null);
            poseStack.popPose();
        }
    }
}
