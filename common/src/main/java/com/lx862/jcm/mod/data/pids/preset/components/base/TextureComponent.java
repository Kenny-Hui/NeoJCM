package com.lx862.jcm.mod.data.pids.preset.components.base;

import com.lx862.jcm.mod.data.KVPair;
import com.lx862.jcm.mod.render.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public abstract class TextureComponent extends PIDSComponent {
    public TextureComponent(double x, double y, double width, double height, KVPair additionalParam) {
        super(x, y, width, height);
    }

    protected void drawTexture(PoseStack poseStack, MultiBufferSource bufferSource, Direction facing, ResourceLocation identifier, double offsetX, double offsetY, double width, double height, int color) {
        double finalX = this.x + offsetX;
        double finalY = this.y + offsetY;
        poseStack.pushPose();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.text(identifier));
        RenderHelper.drawTexture(poseStack, vertexConsumer, (float)(finalX), (float)(finalY), 0, (float)width, (float)height, facing, color, RenderHelper.MAX_RENDER_LIGHT);
        poseStack.popPose();
    }
}
