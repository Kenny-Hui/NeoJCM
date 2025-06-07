package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.JCMClient;
import com.lx862.jcm.mod.data.BlockProperties;
import com.lx862.jcm.mod.render.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.block.IBlock;
import mtr.mappings.BlockEntityMapper;
import mtr.mappings.BlockEntityRendererMapper;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class JCMBlockEntityRenderer<T extends BlockEntityMapper> extends BlockEntityRendererMapper<T> implements RenderHelper {
    public JCMBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        if(JCMClient.getConfig().disableRendering || level == null) return;

        if(level.getBlockState(pos).isAir()) return;
        BlockState state = level.getBlockState(pos);

        renderCurated(blockEntity, poseStack, bufferSource, level, state, pos, partialTick, packedLight, packedOverlay);
    }

    /**
     * Same as the default block entity render method, but only called in safe condition and when rendering are not disabled
     */
    public abstract void renderCurated(T blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockState state, BlockPos pos, float partialTick, int light, int packedOverlay);

    public void rotateToBlockDirection(PoseStack poseStack, T blockEntity) {
        Level world = blockEntity.getLevel();
        if(world != null) {
            BlockState state = blockEntity.getBlockState();
            Direction facing = IBlock.getStatePropertySafe(state, BlockProperties.FACING);
            UtilitiesClient.rotateYDegrees(poseStack, -facing.toYRot());
        }
    }
}
