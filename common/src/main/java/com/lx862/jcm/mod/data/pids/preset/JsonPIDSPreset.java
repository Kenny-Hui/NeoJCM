package com.lx862.jcm.mod.data.pids.preset;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lx862.jcm.mod.Constants;
import com.lx862.jcm.mod.JCMClient;
import com.lx862.jcm.mod.block.entity.PIDSBlockEntity;
import com.lx862.jcm.mod.data.KVPair;
import com.lx862.jcm.mod.data.pids.preset.components.*;
import com.lx862.jcm.mod.data.pids.preset.components.base.PIDSComponent;
import com.lx862.jcm.mod.data.pids.preset.components.base.TextComponent;
import com.lx862.jcm.mod.render.RenderHelper;
import com.lx862.jcm.mod.render.text.TextAlignment;
import com.lx862.jcm.mod.render.text.TextOverflowMode;
import com.lx862.jcm.mod.render.text.TextTranslationMode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mtr.data.IGui;
import mtr.data.ScheduleEntry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class JsonPIDSPreset extends PIDSPresetBase {
    private static final int PIDS_MARGIN = 7;
    private static final float ARRIVAL_TEXT_SCALE = 1.225F;
    private static final int HEADER_HEIGHT = 9;
    private static final String ICON_WEATHER_SUNNY = Constants.MOD_ID + ":textures/block/pids/weather_sunny.png";
    private static final String ICON_WEATHER_RAINY = Constants.MOD_ID + ":textures/block/pids/weather_rainy.png";
    private static final String ICON_WEATHER_THUNDER = Constants.MOD_ID + ":textures/block/pids/weather_thunder.png";
    private static final String TEXTURE_PLATFORM_CIRCLE = Constants.MOD_ID + ":textures/block/pids/plat_circle.png";
    private final ResourceLocation background;
    private final String fontId;
    private final TextOverflowMode textOverflowMode;
    private final boolean showClock;
    private final boolean showWeather;
    private final boolean topPadding;
    private final boolean hidePlatform;
    private final int textColor;
    private final boolean[] rowHidden;

    public JsonPIDSPreset(String id, String name, ResourceLocation thumbnail, List<String> blacklist, ResourceLocation background, String fontId, TextOverflowMode textOverflowMode, boolean[] rowHidden, boolean showClock, boolean showWeather, boolean topPadding, boolean builtin, boolean hidePlatform, int textColor) {
        super(id, name, thumbnail, blacklist, builtin);
        this.background = background;
        this.showClock = showClock;
        this.showWeather = showWeather;
        this.textColor = textColor;
        this.fontId = fontId == null ? "mtr:mtr" : fontId;
        this.rowHidden = rowHidden;
        this.topPadding = topPadding;
        this.textOverflowMode = textOverflowMode;
        this.hidePlatform = hidePlatform;
    }

    public static JsonPIDSPreset parse(JsonObject rootJsonObject) {
        final String id = rootJsonObject.get("id").getAsString();
        final String name = rootJsonObject.has("name") ? rootJsonObject.get("name").getAsString() : id;
        final ResourceLocation background = rootJsonObject.has("background") ? ResourceLocation.parse(rootJsonObject.get("background").getAsString()) : null;
        final ResourceLocation thumbnail = rootJsonObject.has("thumbnail") ? ResourceLocation.parse(rootJsonObject.get("thumbnail").getAsString()) : background;
        final String font = rootJsonObject.has("fonts") ? rootJsonObject.get("fonts").getAsString() : null;
        final TextOverflowMode textOverflowMode = rootJsonObject.has("textOverflowMode") ? TextOverflowMode.valueOf(rootJsonObject.get("textOverflowMode").getAsString()) : TextOverflowMode.STRETCH;
        final boolean builtin = rootJsonObject.has("builtin") && rootJsonObject.get("builtin").getAsBoolean();
        final boolean showWeather = rootJsonObject.has("showWeather") && rootJsonObject.get("showWeather").getAsBoolean();
        final boolean showClock = rootJsonObject.has("showClock") && rootJsonObject.get("showClock").getAsBoolean();
        final boolean topPadding = !rootJsonObject.has("topPadding") ? true : rootJsonObject.get("topPadding").getAsBoolean();
        final boolean hidePlatform = rootJsonObject.has("hidePlatform") && rootJsonObject.get("hidePlatform").getAsBoolean();
        final int textColor = rootJsonObject.has("color") ? (int)Long.parseLong(rootJsonObject.get("color").getAsString(), 16) : ARGB_BLACK;

        if(background == null) throw new IllegalArgumentException("A JSON PIDS Preset must have background set!");
        JCMClient.getMcMetaManager().load(background);

        final boolean[] rowHidden;
        List<String> blackList = new ArrayList<>();

        if(rootJsonObject.has("hideRow")) {
            JsonArray arr = rootJsonObject.getAsJsonArray("hideRow");
            rowHidden = new boolean[arr.size()];
            for(int i = 0; i < arr.size(); i++) {
                rowHidden[i] = arr.get(i).getAsBoolean();
            }
        } else {
            rowHidden = new boolean[]{};
        }

        if(rootJsonObject.has("blacklist")) {
            JsonArray arr = rootJsonObject.getAsJsonArray("blacklist");
            for(int i = 0; i < arr.size(); i++) {
                blackList.add(arr.get(i).getAsString());
            }
        }

        return new JsonPIDSPreset(id, name, thumbnail, blackList, background, font, textOverflowMode, rowHidden, showClock, showWeather, topPadding, builtin, hidePlatform, textColor);
    }

    public List<PIDSComponent> getComponents(List<ScheduleEntry> arrivals, String[] customMessages, boolean[] rowHidden, int x, int y, int screenWidth, int screenHeight, int rows, boolean hidePlatform) {
        List<PIDSComponent> components = new ArrayList<>();

        if(showClock) {
            components.add(new ClockComponent(x + screenWidth, y + 2, screenWidth, 10, TextComponent.of(TextAlignment.RIGHT, TextOverflowMode.STRETCH, fontId, ARGB_WHITE, 0.9)));
        }

        if(showWeather) {
            components.add(new WeatherIconComponent(x, y, 9, 9, new KVPair()
                    .with("weatherIconSunny", ICON_WEATHER_SUNNY)
                    .with("weatherIconRainy", ICON_WEATHER_RAINY)
                    .with("weatherIconThunder", ICON_WEATHER_THUNDER)));
        }

        boolean platformShown = !hidePlatform && !this.hidePlatform;
        boolean showCar = false;
        int tmpCar = -1;
        for(ScheduleEntry arrival : arrivals) {
            if(tmpCar == -1) {
                tmpCar = arrival.trainCars;
                continue;
            }

            if(tmpCar != arrival.trainCars) {
                showCar = true;
            }
        }

        /* Arrivals */
        int arrivalIndex = 0;
        double rowY = y + (topPadding ? HEADER_HEIGHT : 0) + 4;
        for(int i = 0; i < rows; i++) {
            double totalWidth = screenWidth;
            totalWidth -= PIDS_MARGIN;
            totalWidth -= PIDS_MARGIN;
            totalWidth -= (30 * ARRIVAL_TEXT_SCALE);
            if(platformShown) {
                totalWidth -= (14 * ARRIVAL_TEXT_SCALE);
            }

            boolean hasCustomMessage = customMessages[i] != null && !customMessages[i].isEmpty();
            boolean canShowArrival = arrivalIndex < arrivals.size() && !rowHidden[i];

            if(hasCustomMessage) {
                components.add(new CustomTextComponent(x, rowY, screenWidth - (PIDS_MARGIN * 3), 10, TextComponent.of(TextAlignment.LEFT, textOverflowMode, fontId, getTextColor(), ARRIVAL_TEXT_SCALE).with("text", customMessages[i])));
            } else if(canShowArrival) {
                ArrivalDestinationComponent arrivalDestinationComponent = new ArrivalDestinationComponent(x, rowY, totalWidth, 10, TextComponent.of(TextAlignment.LEFT, textOverflowMode, fontId, textColor, ARRIVAL_TEXT_SCALE).with("arrivalIndex", arrivalIndex));
                components.add(arrivalDestinationComponent);

                if (platformShown) {
                    components.add(new ArrivalTextureComponent(screenWidth - (40 * ARRIVAL_TEXT_SCALE), rowY, 10, 10, new KVPair().with("textureId", TEXTURE_PLATFORM_CIRCLE).with("arrivalIndex", arrivalIndex)));
                    components.add(new PlatformComponent(screenWidth - (40 * ARRIVAL_TEXT_SCALE), rowY, 8, 8, fontId, RenderHelper.ARGB_WHITE, 0.85, new KVPair().with("arrivalIndex", arrivalIndex)));
                }

                // ETA text cycle should follow destination
                ScheduleEntry thisArrival = arrivals.get(arrivalIndex);
                boolean destinationIsCjk = IGui.isCjk(arrivalDestinationComponent.getDestinationString(thisArrival));
                TextTranslationMode mode = destinationIsCjk ? TextTranslationMode.CJK : TextTranslationMode.NON_CJK;

                ArrivalETAComponent eta = new ArrivalETAComponent(screenWidth, rowY, 22 * ARRIVAL_TEXT_SCALE, 20, TextComponent.of(TextAlignment.RIGHT, TextOverflowMode.STRETCH, fontId, textColor, ARRIVAL_TEXT_SCALE)
                        .with("arrivalIndex", arrivalIndex)
                        .with("textTranslationMode", mode.name())
                );

                ArrivalCarComponent car = new ArrivalCarComponent(screenWidth, rowY, 22 * ARRIVAL_TEXT_SCALE, 20, TextComponent.of(TextAlignment.RIGHT, TextOverflowMode.STRETCH, fontId, textColor, ARRIVAL_TEXT_SCALE)
                        .with("arrivalIndex", arrivalIndex)
                        .with("textTranslationMode", mode.name())
                );

                if(showCar) {
                    components.add(new CycleComponent(new KVPair().with("cycleTime", 80), eta, car));
                } else {
                    components.add(eta);
                }
                arrivalIndex++;
            }

            rowY += (screenHeight / 4.7) * ARRIVAL_TEXT_SCALE;
        }
        return components;
    }

    public boolean hasTopPadding() {
        return topPadding;
    }

    @Override
    public int getTextColor() {
        return RenderHelper.MAX_ALPHA | textColor;
    }

    @Override
    public boolean isRowHidden(int row) {
        return rowHidden.length - 1 < row ? false : rowHidden[row];
    }

    @Override
    public void render(PIDSBlockEntity be, PoseStack poseStack, MultiBufferSource bufferSource, Level world, BlockPos pos, Direction facing, List<ScheduleEntry> arrivals, boolean[] rowHidden, float tickDelta, int x, int y, int width, int height) {
        int headerHeight = topPadding ? HEADER_HEIGHT : 0;
        int startX = PIDS_MARGIN;
        int contentWidth = width - (PIDS_MARGIN * 2);
        int contentHeight = height - headerHeight - 3;

        // Draw Background
        VertexConsumer backgroundConsumer = bufferSource.getBuffer(RenderType.text(background));
        RenderHelper.drawTexture(poseStack, backgroundConsumer, background, x, y, 0, width, height, facing, ARGB_WHITE, MAX_RENDER_LIGHT);

        poseStack.translate(startX, 0, -0.05);

        List<PIDSComponent> components = getComponents(arrivals, be.getCustomMessages(), rowHidden, x, y, contentWidth, contentHeight, be.getRowAmount(), be.platformNumberHidden());
        PIDSContext pidsContext = new PIDSContext(world, pos, be.getCustomMessages(), arrivals, tickDelta);

        // Texture
        poseStack.pushPose();
        for(PIDSComponent component : components) {
            poseStack.translate(0, 0, -0.02);
            poseStack.pushPose();
            component.render(poseStack, bufferSource, facing, pidsContext);
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
