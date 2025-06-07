package com.lx862.jcm.mod.render.gui.widget;

import com.lx862.jcm.mod.render.gui.GuiHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import static com.lx862.jcm.mod.render.RenderHelper.ARGB_WHITE;

public abstract class AbstractScrollViewWidget extends AbstractWidget {
    public static final int SCROLLBAR_WIDTH = 6;
    protected double currentScroll = 0;
    private boolean scrollbarDragging = false;

    public AbstractScrollViewWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 0 && isOverScrollbar(mouseX, mouseY)) {
            scrollbarDragging = true;
        } else {
            scrollbarDragging = false;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button == 0) {
            scrollbarDragging = false;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (visible && isFocused() && scrollbarDragging) {
            double remainingHeight = getContentHeight() - getHeight();

            int s1 = (getHeight() * getHeight()) / getContentHeight();
            double scrollScale = remainingHeight / ((double)getHeight() - s1);
            setScroll(currentScroll + (deltaY * scrollScale));
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        scrollY *= 15;
        double oldScroll = currentScroll;
        setScroll(currentScroll - scrollY);
        return oldScroll != currentScroll;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean bl = keyCode == GLFW.GLFW_KEY_UP;
        boolean bl2 = keyCode == GLFW.GLFW_KEY_DOWN;
        if (bl || bl2) {
            double oldScroll = currentScroll;
            if(bl)
                setScroll(currentScroll - 15);
            else
                setScroll(currentScroll + 15);
            if(oldScroll != currentScroll) return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected boolean contentOverflowed() {
        return getContentHeight() > getHeight();
    }

    protected int getScrollbarOffset() {
        return contentOverflowed() ? SCROLLBAR_WIDTH : 0;
    }

    public void setScroll(double scroll) {
        if(scroll < 0 || !contentOverflowed()) {
            currentScroll = 0;
        } else if(scroll > getContentHeight() - getHeight()) {
            currentScroll = getContentHeight() - getHeight();
        } else {
            currentScroll = scroll;
        }
    }

    private void renderScrollbar(GuiGraphics guiGraphics, boolean isMouseOverScrollbar) {
        if(!contentOverflowed()) return;

        int fullHeight = getContentHeight();
        int visibleHeight = getHeight();
        double scrollbarHeight = visibleHeight * ((double)visibleHeight / fullHeight);
        double bottomOffset = currentScroll / (fullHeight - visibleHeight);
        double yOffset = bottomOffset * (visibleHeight - scrollbarHeight);
        GuiHelper.drawRectangle(guiGraphics, getX() + getWidth() - SCROLLBAR_WIDTH, getY() + yOffset, SCROLLBAR_WIDTH, scrollbarHeight, isMouseOverScrollbar ? ARGB_WHITE : 0xFFC0C0C0);

        // Border
        GuiHelper.drawRectangle(guiGraphics, getX() + getWidth() - 1, getY() + yOffset, 1, scrollbarHeight, 0xFF808080);
        GuiHelper.drawRectangle(guiGraphics, getX() + getWidth() - SCROLLBAR_WIDTH, getY() + yOffset + scrollbarHeight - 1, SCROLLBAR_WIDTH, 1, 0xFF808080);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        guiGraphics.enableScissor(getX(), getY(), getX() + getWidth(), getY() + getHeight());
        renderScrollbar(guiGraphics, isOverScrollbar(mouseX, mouseY));
        guiGraphics.disableScissor();

        guiGraphics.enableScissor(getX(), getY(), getX() + (getWidth() - getScrollbarOffset()), getY() + getHeight());
        renderContent(guiGraphics, mouseX, mouseY, tickDelta);
        guiGraphics.disableScissor();
    }

    private boolean isOverScrollbar(double mouseX, double mouseY) {
        int scrollbarEndX = getX() + getWidth();
        int scrollbarStartX = scrollbarEndX - getScrollbarOffset();
        return mouseX >= scrollbarStartX && mouseX <= scrollbarEndX && mouseY >= getY() && mouseY <= getY() + getHeight();
    }

    public abstract void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta);
    protected abstract int getContentHeight();
}
