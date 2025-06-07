package com.lx862.jcm.mod.render.gui.screen;

import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.JCM;
import com.lx862.jcm.mod.JCMClient;
import com.lx862.jcm.mod.render.gui.GuiHelper;
import com.lx862.jcm.mod.render.gui.screen.base.TitledScreen;
import com.lx862.jcm.mod.render.gui.widget.ListViewWidget;
import com.lx862.jcm.mod.render.gui.widget.WidgetSet;
import com.lx862.jcm.mod.util.JCMLogger;
import com.lx862.jcm.mod.util.TextCategory;
import com.lx862.jcm.mod.util.TextUtil;
import mtr.mappings.UtilitiesClient;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ClientConfigScreen extends TitledScreen implements GuiHelper {
    private static final ResourceLocation TEXTURE_BACKGROUND = Constants.id("textures/gui/config_screen/bg.png");
    private static final ResourceLocation TEXTURE_STAR = Constants.id("textures/gui/config_screen/stars.png");
    private static final ResourceLocation TEXTURE_TERRAIN = Constants.id("textures/gui/config_screen/terrain.png");
    private static final int STAR_ROTATION_LENGTH = 260 * 1000;

    private final WidgetSet bottomRowWidget;
    private final ListViewWidget listViewWidget;
    private final Checkbox disableRenderingButton;
    private final Checkbox debugModeButton;

    private boolean discardConfig = false;

    public ClientConfigScreen() {
        super(TextUtil.translatable(TextCategory.GUI, "brand"), true);
        bottomRowWidget = new WidgetSet(20);
        listViewWidget = new ListViewWidget();

        this.disableRenderingButton = Checkbox.builder(Component.empty(), Minecraft.getInstance().font).selected(JCMClient.getConfig().disableRendering).pos(0, 0).onValueChange((cb, bl) -> {
            JCMClient.getConfig().disableRendering = bl;
        }).build();

        this.debugModeButton = Checkbox.builder(Component.empty(), Minecraft.getInstance().font).pos(0, 0).selected(JCM.getConfig().debug).onValueChange((cb, bl) -> {
            JCM.getConfig().debug = bl;
        }).build();
    }

    @Override
    public Component getScreenSubtitle() {
        return TextUtil.translatable(TextCategory.GUI, "config.version", Constants.MOD_VERSION);
    }

    @Override
    protected void init() {
        super.init();
        listViewWidget.reset();
        bottomRowWidget.reset();

        int contentWidth = (int)Math.min((width * 0.75), MAX_CONTENT_WIDTH);
        int listViewHeight = (int)((height - 60) * 0.75);
        int startX = (width - contentWidth) / 2;
        int startY = TEXT_PADDING * 5;

        int bottomEntryHeight = (height - startY - listViewHeight - (BOTTOM_ROW_MARGIN * 2));

        addConfigEntries();
        addBottomButtons();
        addRenderableWidget(listViewWidget);
        addRenderableWidget(bottomRowWidget);
        listViewWidget.setXYSize(startX, startY, contentWidth, listViewHeight);
        bottomRowWidget.setXYSize(startX, startY + listViewHeight + BOTTOM_ROW_MARGIN, contentWidth, bottomEntryHeight);
    }

    private void addConfigEntries() {
        // General
        listViewWidget.addCategory(TextUtil.translatable(TextCategory.GUI, "config.listview.category.general"));

        listViewWidget.add(TextUtil.translatable(TextCategory.GUI, "config.listview.title.disable_rendering"), disableRenderingButton);
        addWidget(disableRenderingButton);

        // Debug
        listViewWidget.addCategory(TextUtil.translatable(TextCategory.GUI, "config.listview.category.debug"));
        listViewWidget.add(TextUtil.translatable(TextCategory.GUI, "config.listview.title.debug_mode"), debugModeButton);
        addWidget(debugModeButton);
    }


    private void addBottomButtons() {
        Button latestLogButton = Button.builder(TextUtil.translatable(TextCategory.GUI, "config.latest_log"), (btn) -> {
            openLatestLog();
        }).size(0, 20).build();

        Button crashLogButton = Button.builder(TextUtil.translatable(TextCategory.GUI, "config.crash_log"), (btn) -> {
            openLatestCrashReport();
        }).size(0, 20).build();

        Button saveButton = Button.builder(TextUtil.translatable(TextCategory.GUI, "config.save"), (btn) -> {
            onClose();
        }).size(0, 20).build();

        Button discardButton = Button.builder(TextUtil.translatable(TextCategory.GUI, "config.discard"), (btn) -> {
            discardConfig = true;
            onClose();
        }).size(0, 20).build();

        Button resetButton = Button.builder(TextUtil.translatable(TextCategory.GUI, "config.reset"), (btn) -> {
            JCMClient.getConfig().reset();
            //setEntryStateFromClientConfig();
        }).size(0, 20).build();

        addRenderableWidget(latestLogButton);
        addRenderableWidget(crashLogButton);

        addRenderableWidget(saveButton);
        addRenderableWidget(discardButton);
        addRenderableWidget(resetButton);

        bottomRowWidget.addWidget(latestLogButton);
        bottomRowWidget.addWidget(crashLogButton);
        bottomRowWidget.insertRow();
        bottomRowWidget.addWidget(saveButton);
        bottomRowWidget.addWidget(discardButton);
        bottomRowWidget.addWidget(resetButton);
    }

    @Override
    public void drawCustomBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        double terrainHeight = (width / 3.75);
        double starSize = Math.max(width, height) * 4;
        float starUVSize = (float) (starSize / 384F);
        double translateY = height * (1 - animationProgress);
        GuiHelper.drawTexture(guiGraphics, TEXTURE_BACKGROUND, 0, 0, width, height);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, translateY * 0.2f, 0);
        guiGraphics.pose().translate(width / 2.0, height / 2.0, 0);

        float starRot = (System.currentTimeMillis() % STAR_ROTATION_LENGTH) / (float)STAR_ROTATION_LENGTH;
        UtilitiesClient.rotateZDegrees(guiGraphics.pose(), starRot * 360);
        guiGraphics.pose().translate(-width / 2.0, -height / 2.0, 0);
        GuiHelper.drawTexture(guiGraphics, TEXTURE_STAR, 0, 0, starSize, starSize, 0, 0, starUVSize, starUVSize);
        guiGraphics.pose().popPose();

        GuiHelper.drawTexture(guiGraphics, TEXTURE_TERRAIN, 0, translateY + height - terrainHeight, width, terrainHeight);
    }

    @Override
    public void onClose() {
        if(!closing) {
            if (!discardConfig) {
                JCMClient.getConfig().write();
                JCMClient.getConfig().write();
            } else {
                // Don't save our change to disk, and re-read it from disk, effectively discarding the config
                JCMClient.getConfig().read();
                JCMClient.getConfig().read();
            }
        }

        super.onClose();
    }

    public static void openLatestLog() {
        File latestLog = Paths.get(Minecraft.getInstance().gameDirectory.toString(), "logs", "latest.log").toFile();
        if(latestLog.exists()) {
            Util.getPlatform().openFile(latestLog);
        }
    }

    public static void openLatestCrashReport() {
        File crashReportDir = Paths.get(Minecraft.getInstance().gameDirectory.toString(), "crash-reports").toFile();

        if(crashReportDir.exists()) {
            File[] crashReports = crashReportDir.listFiles();
            if(crashReports != null && crashReportDir.length() > 0) {
                SimpleDateFormat crashReportFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

                Arrays.sort(crashReports, (o1, o2) -> {
                    String filename1 = o1.getName().replace("crash-", "").replace("-client", "");
                    String filename2 = o2.getName().replace("crash-", "").replace("-client", "");
                    if(o1 == o2) return 0;

                    try {
                        long ts1 = crashReportFormat.parse(filename1).getTime();
                        long ts2 = crashReportFormat.parse(filename2).getTime();
                        return ts1 > ts2 ? -1 : 1;
                    } catch (ParseException e) {
                        JCMLogger.debug("Cannot compare crash report file name " + filename1 + " <-> " + filename2);
                    }
                    return 1;
                });

                JCMLogger.debug("Latest crash report is: " + crashReports[0].getName());
                Util.getPlatform().openFile(crashReports[0]);
            }
        }
    }
}
