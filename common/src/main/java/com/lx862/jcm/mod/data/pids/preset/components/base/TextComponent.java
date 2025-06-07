package com.lx862.jcm.mod.data.pids.preset.components.base;

import com.lx862.jcm.mod.data.JCMServerStats;
import com.lx862.jcm.mod.data.KVPair;
import com.lx862.jcm.mod.render.RenderHelper;
import com.lx862.jcm.mod.render.text.*;
import com.mojang.blaze3d.vertex.PoseStack;
import mtr.data.IGui;
import net.minecraft.client.renderer.MultiBufferSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TextComponent extends PIDSComponent {
    public static final int SWITCH_LANG_DURATION = 60;
    protected final TextOverflowMode textOverflowMode;
    protected final TextAlignment textAlignment;
    protected final TextTranslationMode textTranslationMode;
    protected final String font;
    protected final int textColor;
    protected final double scale;

    public TextComponent(double x, double y, double width, double height, KVPair additionalParam) {
        super(x, y, width, height);
        this.font = additionalParam.get("font", "");
        this.textAlignment = TextAlignment.valueOf(additionalParam.get("textAlignment", "LEFT"));
        this.textOverflowMode = TextOverflowMode.valueOf(additionalParam.get("textOverflowMode", "STRETCH"));
        this.textTranslationMode = TextTranslationMode.valueOf(additionalParam.get("textTranslationMode", "CYCLE"));
        this.scale = additionalParam.getDouble("scale", 1.0);
        this.textColor = additionalParam.getColor("color", 0);
    }

    public static KVPair of(TextAlignment textAlignment, TextOverflowMode textOverflowMode, String font, int textColor, double scale) {
        return new KVPair()
                .with("textAlignment", textAlignment.name())
                .with("textOverflowMode", textOverflowMode.name())
                .with("font", font)
                .with("color", textColor)
                .with("scale", scale);
    }

    protected void drawText(PoseStack poseStack, MultiBufferSource bufferSource, String text) {
        drawText(poseStack, bufferSource, new TextInfo(cycleString(text)));
    }

    protected void drawText(PoseStack poseStack, MultiBufferSource bufferSource, TextInfo text) {
        final PoseStack pose = poseStack;
        TextInfo finalText = text.withColor(textColor + ARGB_BLACK).withFont(font).withTextAlignment(textAlignment);
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale((float)scale, (float)scale, (float)scale);
        double textWidth = TextRenderingManager.getTextWidth(finalText);
        if(textOverflowMode == TextOverflowMode.MARQUEE && textWidth > width) {
            finalText = finalText.withScrollingText().withMaxWidth((float)width);
        } else {
            RenderHelper.scaleToFit(poseStack, textWidth, width, textOverflowMode == TextOverflowMode.SCALE, 14);
        }

        TextRenderingManager.draw(poseStack, bufferSource, finalText, 0, 0);

        poseStack.popPose();
    }

    protected String cycleString(String mtrString, boolean isForTranslation) {
        if(textTranslationMode == TextTranslationMode.MERGE) {
            return mtrString.replace("|", " ");
        }

        List<String> split = new ArrayList<>(Arrays.asList(mtrString.split("\\|")));

        if(textTranslationMode != TextTranslationMode.CYCLE) {
            for(String lang : new ArrayList<>(split)) {
                if(textTranslationMode == TextTranslationMode.CJK && !isCjk(lang, isForTranslation)) {
                    split.remove(lang);
                }
                if(textTranslationMode == TextTranslationMode.NON_CJK && isCjk(lang, isForTranslation)) {
                    split.remove(lang);
                }
            }
        }

        if(split.isEmpty()) return "";

        // TODO: Client tick!
        return split.get(((int) JCMServerStats.getGameTick() / SWITCH_LANG_DURATION) % split.size());
    }

    protected boolean isCjk(String str, boolean isTranslation) {
        if(isTranslation) return str.endsWith("_cjk");
        return IGui.isCjk(str);
    }

    protected String cycleString(String... string) {
        return cycleString(String.join("|", string), false);
    }

    protected String cycleStringTranslation(String... string) {
        return cycleString(String.join("|", string), true);
    }
}
