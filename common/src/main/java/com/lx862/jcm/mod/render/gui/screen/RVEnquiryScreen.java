package com.lx862.jcm.mod.render.gui.screen;

import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.data.TransactionEntry;
import com.lx862.jcm.mod.render.gui.GuiHelper;
import com.lx862.jcm.mod.render.gui.screen.base.AnimatedScreen;
import com.lx862.jcm.mod.util.TextCategory;
import com.lx862.jcm.mod.util.TextUtil;
import mtr.MTR;
import mtr.data.IGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.text.SimpleDateFormat;
import java.util.List;

public class RVEnquiryScreen extends AnimatedScreen {
    private static final ResourceLocation cardScreenTexture = Constants.id("textures/enquiry/card.png");
    private static final ResourceLocation balanceTexture = Constants.id("textures/enquiry/transactions.png");
    private static final ResourceLocation octopusCardTexture = Constants.id("textures/enquiry/octopus_card.png");
    private static final ResourceLocation fontId = MTR.id("mtr");
    private final List<TransactionEntry> entries;
    private final BlockPos pos;
    private final long remainingBalance;
    private boolean showBalance;

    public RVEnquiryScreen(BlockPos pos, List<TransactionEntry> entries, int remainingBalance) {
        super(null, false);
        this.pos = pos;
        this.entries = entries;
        this.remainingBalance = remainingBalance;
        this.showBalance = true; // Simplify mechanics
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        super.renderBackground(guiGraphics, mouseX, mouseY, tickDelta);

        int screenWidth = Minecraft.getInstance().getWindow().getScreenWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getScreenHeight();

        int baseWidth = 427;
        double scaledWidth = width;
        double scaledHeight = height;
        int startX = 20;
        int startY = 70;

        int rectX = (screenWidth - 150) / 2;
        int rectY = ((screenHeight - 70) / 2) + 65;
        int rectWidth = 150;
        int rectHeight = 70;

        boolean cursorWithinRectangle = mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
        if(cursorWithinRectangle) {
            showBalance = true;
        }

        if (!showBalance) {
            GuiHelper.drawTexture(guiGraphics, cardScreenTexture, (screenWidth - (int) scaledWidth) / 2.0, (screenHeight - (int) scaledHeight) / 2.0, (int) scaledWidth, (int) scaledHeight);
            GuiHelper.drawTexture(guiGraphics, octopusCardTexture, mouseX, mouseY, 140, 86);
        } else {
            GuiHelper.drawTexture(guiGraphics, balanceTexture, (screenWidth - (int) scaledWidth) / 2.0, (screenHeight - (int) scaledHeight) / 2.0, (int) scaledWidth, (int) scaledHeight);

            int renderY = startY;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale((float)(double)width / baseWidth, (float)(double)width / baseWidth, (float)(double)width / baseWidth);

            guiGraphics.drawString(font, TextUtil.withFont(TextUtil.translatable(TextCategory.GUI, "rvenquiry_screen.balance"), fontId), startX, 20, 0, false);
            guiGraphics.drawString(font, TextUtil.withFont(TextUtil.literal("Octopus"), fontId), startX + 305, 75, 0, false);
            guiGraphics.drawString(font, TextUtil.withFont(TextUtil.translatable(TextCategory.GUI, "rvenquiry_screen.processor"), fontId), startX + 305, 85, 0, false);

            String processorId = String.format("%06d", pos.asLong() % 1000000);
            guiGraphics.drawString(font, TextUtil.withFont(TextUtil.literal(processorId), fontId), startX + 305, 95, 0, false);

            for (int i = 0; i < 10; i++) {
                MutableComponent renderText = getEntryText(i);
                if(renderText == null) continue;

                guiGraphics.drawString(font, TextUtil.withFont(renderText, fontId), startX, renderY, 0, false);
                renderY += 10;
            }

            if (!entries.isEmpty()) {
                if (remainingBalance >= 0) {
                    guiGraphics.drawString(font, TextUtil.withFont(TextUtil.literal("$" + remainingBalance), fontId), startX + 270, 20, 0x000000, false);
                } else {
                    guiGraphics.drawString(font, TextUtil.withFont(TextUtil.literal("-$" + Math.abs(remainingBalance)), fontId), startX + 270, 20, 0x000000, false);
                }
            }

            long lastDate = 0;

            for (TransactionEntry transactionEntry : entries) {
                if (transactionEntry.amount > 0) {
                    lastDate = transactionEntry.time;
                }
            }

            if (lastDate != 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedDate = formatter.format(lastDate);

                guiGraphics.drawString(font, TextUtil.withFont(TextUtil.translatable(TextCategory.GUI, "rvenquiry_screen.last.a"), fontId), startX + 305, 115, 0, false);
                guiGraphics.drawString(font, TextUtil.withFont(TextUtil.translatable(TextCategory.GUI, "rvenquiry_screen.last.b"), fontId), startX + 305, 125, 0, false);
                guiGraphics.drawString(font, TextUtil.withFont(TextUtil.translatable(TextCategory.GUI, "rvenquiry_screen.last.c"), fontId), startX + 305, 135, 0, false);
                guiGraphics.drawString(font, TextUtil.withFont(TextUtil.literal(formattedDate), fontId), startX + 305, 145, 0x000000, false);
            }

            guiGraphics.pose().popPose();
        }
    }

    private MutableComponent getEntryText(int i) {
        if(i >= entries.size()) return null;
        TransactionEntry transactionEntry = entries.get(i);
        String renderTextString;
        if (transactionEntry.amount < 0) {
            renderTextString = String.format("%s     %s     -$%.2f", transactionEntry.getFormattedDate(), IGui.formatStationName(transactionEntry.source), Math.abs((double) transactionEntry.amount));
        } else if (transactionEntry.amount > 0) {
            renderTextString = String.format("%s     %s     +$%.2f", transactionEntry.getFormattedDate(), IGui.formatStationName(transactionEntry.source), (double) transactionEntry.amount);
        } else {
            renderTextString = String.format("%s     %s     $%.2f", transactionEntry.getFormattedDate(), IGui.formatStationName(transactionEntry.source), (double) transactionEntry.amount);
        }
        return TextUtil.literal(renderTextString);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
