package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.block.entity.StationNameStandingBlockEntity;
import com.lx862.jcm.mod.data.BlockProperties;
import mtr.block.IBlock;
import mtr.client.ClientData;
import mtr.client.IDrawing;
import mtr.render.RenderTrains;
import mtr.render.RenderStationNameBase;
import mtr.render.StoredMatrixTransformations;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class StationNameStandingRenderer extends RenderStationNameBase<StationNameStandingBlockEntity> {

    private static final float WIDTH = 0.6875F;
    private static final float HEIGHT = 1;
    private static final float OFFSET_Y = 0.125F;

    public StationNameStandingRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void drawStationName(BlockGetter world, BlockPos pos, BlockState state, Direction facing, StoredMatrixTransformations storedMatrixTransformations, MultiBufferSource vertexConsumers, String stationName, int stationColor, int color, int light) {
        if (IBlock.getStatePropertySafe(state, BlockProperties.VERTICAL_PART_3) == IBlock.EnumThird.MIDDLE) {
            RenderTrains.scheduleRender(ClientData.DATA_CACHE.getTallStationName(color, stationName, stationColor, WIDTH / HEIGHT).resourceLocation, false, RenderTrains.QueuedRenderLayer.EXTERIOR, (poseStack, vertexConsumer) -> {
                storedMatrixTransformations.transform(poseStack);
                IDrawing.drawTexture(poseStack, vertexConsumer, -WIDTH / 2, -HEIGHT / 2 - OFFSET_Y, WIDTH, HEIGHT, 0, 0, 1, 1, facing, ARGB_WHITE, light);
                poseStack.popPose();
            });
        }
    }
}
