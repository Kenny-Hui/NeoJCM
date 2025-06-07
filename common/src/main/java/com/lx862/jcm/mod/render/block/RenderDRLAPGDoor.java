package com.lx862.jcm.mod.render.block;

import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.block.entity.APGDoorDRLBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.MTR;
import mtr.MTRClient;
import mtr.block.BlockAPGGlass;
import mtr.block.BlockAPGGlassEnd;
import mtr.block.BlockPSDAPGDoorBase;
import mtr.block.IBlock;
import mtr.data.IGui;
import mtr.mappings.BlockEntityRendererMapper;
import mtr.mappings.ModelDataWrapper;
import mtr.mappings.ModelMapper;
import mtr.mappings.UtilitiesClient;
import mtr.render.MoreRenderLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

/**
 * Copied from MTR, PSD/APG is a pain
 */
public class RenderDRLAPGDoor<T extends APGDoorDRLBlockEntity> extends BlockEntityRendererMapper<T> implements IGui, IBlock {
    private final int type;
    private static final ModelSingleCube MODEL_APG_TOP = new ModelSingleCube(34, 9, 0, 15, 1, 16, 1, 1);
    private static final ModelAPGDoorBottom MODEL_APG_BOTTOM = new ModelAPGDoorBottom();
    private static final ModelAPGDoorLight MODEL_APG_LIGHT = new ModelAPGDoorLight();
    private static final ModelSingleCube MODEL_APG_DOOR_LOCKED = new ModelSingleCube(6, 6, 5, 17, 1, 6, 6, 0);
    public RenderDRLAPGDoor(BlockEntityRenderDispatcher dispatcher, int type) {
        super(dispatcher);
        this.type = type;
    }

    @Override
    public boolean shouldRenderOffScreen(T blockEntity) {
        return true;
    }

    @Override
    public void render(T entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        final Level level = entity.getLevel();
        if (level == null) {
            return;
        }

        final BlockPos blockPos = entity.getBlockPos();
        final Direction facing = IBlock.getStatePropertySafe(level, blockPos, BlockPSDAPGDoorBase.FACING);
        final boolean side = IBlock.getStatePropertySafe(level, blockPos, BlockPSDAPGDoorBase.SIDE) == EnumSide.RIGHT;
        final boolean half = IBlock.getStatePropertySafe(level, blockPos, BlockPSDAPGDoorBase.HALF) == DoubleBlockHalf.UPPER;
        final boolean end = IBlock.getStatePropertySafe(level, blockPos, BlockPSDAPGDoorBase.END);
        final boolean unlocked = IBlock.getStatePropertySafe(level, blockPos, BlockPSDAPGDoorBase.UNLOCKED);
        final double open = Math.min(entity.getOpen(MTRClient.getLastFrameDuration()), type >= 3 ? 0.75F : 1);

        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        UtilitiesClient.rotateYDegrees(poseStack, -facing.toYRot());
        UtilitiesClient.rotateXDegrees(poseStack, 180);

        switch (type) {
            case 2:
                if (half) {
                    final Block block = level.getBlockState(blockPos.relative(side ? facing.getClockWise() : facing.getCounterClockWise())).getBlock();
                    if (block instanceof BlockAPGGlass || block instanceof BlockAPGGlassEnd) {
                        poseStack.pushPose();
                        poseStack.translate(side ? -0.515625 : 0.515625, 0, 0);
                        poseStack.scale(0.5F, 1, 1);
                        final ResourceLocation lightLocation = MTR.id(String.format("textures/block/apg_door_light_%s.png", open > 0 ? "on" : "off"));
                        final VertexConsumer vertexConsumerLight = bufferSource.getBuffer(open > 0 ? MoreRenderLayers.getLight(lightLocation, true) : MoreRenderLayers.getExterior(lightLocation));
                        MODEL_APG_LIGHT.renderToBuffer(poseStack, vertexConsumerLight, packedLight, packedOverlay, 0xFFFFFFFF);
                        poseStack.popPose();
                    }
                }
                break;
        }

        poseStack.translate(open * (side ? -1 : 1), 0, 0);

        switch (type) {
            case 2:
                final VertexConsumer vertexConsumerAPGDoor = bufferSource.getBuffer(MoreRenderLayers.getExterior(Constants.id(String.format("textures/entity/drl_apg_door/apg_door_%s_%s.png", half ? "top" : "bottom", side ? "right" : "left"))));
                (half ? MODEL_APG_TOP : MODEL_APG_BOTTOM).renderToBuffer(poseStack, vertexConsumerAPGDoor, packedLight, packedOverlay, 0xFFFFFFFF);
                if (half && !unlocked) {
                    final VertexConsumer vertexConsumerDoorLocked = bufferSource.getBuffer(MoreRenderLayers.getExterior(MTR.id("textures/block/sign/door_not_in_use.png")));
                    MODEL_APG_DOOR_LOCKED.renderToBuffer(poseStack, vertexConsumerDoorLocked, packedLight, packedOverlay, 0xFFFFFFFF);
                }
                break;
        }
        poseStack.popPose();
    }

    private static class ModelSingleCube extends EntityModel<Entity> {

        private final ModelMapper cube;

        private ModelSingleCube(int textureWidth, int textureHeight, int x, int y, int z, int length, int height, int depth) {
            super();
            final ModelDataWrapper modelDataWrapper = new ModelDataWrapper(this, textureWidth, textureHeight);
            cube = new ModelMapper(modelDataWrapper);
            cube.texOffs(0, 0).addBox(x - 8, y - 16, z - 8, length, height, depth, 0, false);
            modelDataWrapper.setModelPart(textureWidth, textureHeight);
            cube.setModelPart();
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            cube.render(poseStack, buffer, 0, 0, 0, packedLight, packedOverlay);
        }

        @Override
        public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        }
    }

    private static class ModelAPGDoorBottom extends EntityModel<Entity> {

        private final ModelMapper bone;

        private ModelAPGDoorBottom() {
            final int textureWidth = 34;
            final int textureHeight = 27;

            final ModelDataWrapper modelDataWrapper = new ModelDataWrapper(this, textureWidth, textureHeight);

            bone = new ModelMapper(modelDataWrapper);
            bone.texOffs(0, 0).addBox(-8, -16, -7, 16, 16, 1, 0, false);
            bone.texOffs(0, 17).addBox(-8, -6, -8, 16, 6, 1, 0, false);

            final ModelMapper cube_r1 = new ModelMapper(modelDataWrapper);
            cube_r1.setPos(0, -6, -8);
            cube_r1.setRotationAngle(-0.7854F, 0, 0);
            cube_r1.texOffs(0, 24).addBox(-8, -2, 0, 16, 2, 1, 0, false);
            modelDataWrapper.setModelPart(textureWidth, textureHeight);
            bone.setModelPart();
        }


        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            bone.render(poseStack, buffer, 0, 0, 0, packedLight, packedOverlay);
        }

        @Override
        public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        }
    }

    private static class ModelAPGDoorLight extends EntityModel<Entity> {

        private final ModelMapper bone;

        private ModelAPGDoorLight() {
            final ModelDataWrapper modelDataWrapper = new ModelDataWrapper(this, 8, 8);

            bone = new ModelMapper(modelDataWrapper);
            bone.texOffs(0, 4).addBox(-0.5F, -2, -7, 1, 1, 3, 0.05F, false);

            final ModelMapper cube_r1 = new ModelMapper(modelDataWrapper);
            cube_r1.setPos(0, -2.05F, -4.95F);
            cube_r1.setRotationAngle(0.3927F, 0, 0);
            cube_r1.texOffs(0, 0).addBox(-0.5F, 0.05F, -3.05F, 1, 1, 3, 0.05F, false);

            modelDataWrapper.setModelPart(8, 8);
            bone.setModelPart();
        }

        @Override
        public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            bone.render(poseStack, buffer, 0, 0, 0, packedLight, packedOverlay);
        }
    }
}