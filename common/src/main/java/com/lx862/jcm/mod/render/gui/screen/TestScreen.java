package com.lx862.jcm.mod.render.gui.screen;

import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.render.gui.GuiHelper;
import com.lx862.jcm.mod.render.gui.screen.base.TitledScreen;
import com.lx862.jcm.mod.util.TextUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.mappings.UtilitiesClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TestScreen extends TitledScreen implements GuiHelper {
    private static final ResourceLocation TEXTURE_BACKGROUND = Constants.id("textures/gui/config_screen/bg.png");
    private static final ResourceLocation TEXTURE_TERRAIN = Constants.id("textures/gui/config_screen/terrain.png");

    public TestScreen() {
        super(TextUtil.literal("Demo Screen"), true);
    }

    @Override
    public Component getScreenSubtitle() {
        return TextUtil.literal("Hello World :3");
    }

    @Override
    public void drawCustomBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        double terrainHeight = (width / 3.75);
        GuiHelper.drawTexture(guiGraphics, TEXTURE_BACKGROUND, 0, 0, width, height);

        double translateY = height * (1 - animationProgress);
        GuiHelper.drawTexture(guiGraphics, TEXTURE_TERRAIN, 0, translateY + height - terrainHeight, width, terrainHeight);
        drawPride(guiGraphics);
        drawSpinningText(guiGraphics);
    }

    private void drawSpinningText(GuiGraphics guiGraphics) {
        final PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(width / 2.0, height / 2.0, 0);

        int times = 10;
        for(int i = 0; i < times; i++) {
            double radius = 80 * animationProgress;
            double angle = ((i / (double)times) * (Math.PI * 2)) - (elapsed / 10);
            double x = Math.sin(angle) * radius;
            double y = Math.cos(angle) * radius;
            poseStack.pushPose();
            poseStack.translate(x, y, 0);
            guiGraphics.drawCenteredString(font, "Test", 0, 0, 0xFFFFFFFF);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    private void drawPride(GuiGraphics guiGraphics) {
        final PoseStack poseStack = guiGraphics.pose();
        double halfWidth = width / 2.0;

        poseStack.pushPose();
        UtilitiesClient.rotateYDegrees(poseStack,5);
        poseStack.scale((float) animationProgress, 1, 1);

        GuiHelper.drawRectangle(guiGraphics, halfWidth - (halfWidth * animationProgress), 72, width * animationProgress, 8, 0xFFDF6277);
        GuiHelper.drawRectangle(guiGraphics, halfWidth - (halfWidth * animationProgress), 72 + 8, width * animationProgress, 8, 0xFFFB9168);
        GuiHelper.drawRectangle(guiGraphics, halfWidth - (halfWidth * animationProgress), 72 + 16, width * animationProgress, 8, 0xFFF3DB6C);
        GuiHelper.drawRectangle(guiGraphics, halfWidth - (halfWidth * animationProgress), 72 + 24, width * animationProgress, 8, 0xFF7AB392);
        GuiHelper.drawRectangle(guiGraphics, halfWidth - (halfWidth * animationProgress), 72 + 32, width * animationProgress, 8, 0xFF4B7CBC);
        GuiHelper.drawRectangle(guiGraphics, halfWidth - (halfWidth * animationProgress), 72 + 40, width * animationProgress, 8, 0xFF6F488C);
        poseStack.popPose();
    }
}
