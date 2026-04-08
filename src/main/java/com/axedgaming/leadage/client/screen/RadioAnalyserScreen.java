package com.axedgaming.leadage.client.screen;

import com.axedgaming.leadage.common.menu.RadioAnalyserMenu;
import com.axedgaming.leadage.common.network.NetworkHandler;
import com.axedgaming.leadage.common.network.SetRadioFrequencyPacket;
import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RadioAnalyserScreen extends AbstractContainerScreen<RadioAnalyserMenu> {

    private static final ResourceLocation CONTAINER_TEXTURE =
            new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");

    private FrequencySlider slider;
    private Button validateButton;
    private Button resetButton;

    public RadioAnalyserScreen(RadioAnalyserMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = 72;
    }

    @Override
    protected void init() {
        super.init();

        int x = leftPos;
        int y = topPos;

        slider = new FrequencySlider(x + 18, y + 38, 140, 20, menu.getBlockEntity().getFrequency());
        addRenderableWidget(slider);

        validateButton = addRenderableWidget(Button.builder(
                Component.translatable("ui.leadage.radio.validate"),
                button -> NetworkHandler.sendToServer(
                        new SetRadioFrequencyPacket(true, menu.getBlockEntity().getBlockPos(), slider.getFrequency())
                )
        ).bounds(x + 18, y + 62, 68, 20).build());

        resetButton = addRenderableWidget(Button.builder(
                Component.translatable("ui.leadage.radio.reset"),
                button -> slider.setFrequency(RadioConstants.DEFAULT_FREQUENCY)
        ).bounds(x + 90, y + 62, 68, 20).build());
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        graphics.blit(CONTAINER_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        graphics.fill(x + 7, y + 16, x + 169, y + 84, 0x66000000);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);

        int x = leftPos;
        int y = topPos;

        graphics.drawCenteredString(font, Component.translatable("block.leadage.radio_analyser"), x + 88, y + 6, 0x404040);
        graphics.drawString(font, Component.literal("Ticket"), x + 18, y + 20, 0xFFFFFF, false);
        graphics.drawCenteredString(font, Component.translatable("ui.leadage.radio.tuning"), x + 88, y + 28, 0xFFFFFF);
        graphics.drawCenteredString(font, Component.literal(RadioChannelHelper.formatFrequency(slider.getFrequency())), x + 88, y + 90, 0xFFD37F);
    }

    private static class FrequencySlider extends AbstractSliderButton {

        private int frequency;

        public FrequencySlider(int x, int y, int width, int height, int frequency) {
            super(x, y, width, height, Component.empty(), toSliderValue(frequency));
            this.frequency = RadioConstants.clampFrequency(frequency);
            this.value = toSliderValue(this.frequency);
            updateMessage();
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = RadioConstants.clampFrequency(frequency);
            this.value = toSliderValue(this.frequency);
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            setMessage(Component.literal(RadioChannelHelper.formatFrequency(frequency)));
        }

        @Override
        protected void applyValue() {
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
            updateFromMouse(mouseX);
            return true;
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            updateFromMouse(mouseX);
        }

        private void updateFromMouse(double mouseX) {
            double raw = (mouseX - (this.getX() + 4)) / (double) (this.width - 8);
            raw = Math.max(0.0D, Math.min(1.0D, raw));

            int rawFrequency = fromSliderValue(raw);
            int step = getCurrentStep();

            int snapped = snap(rawFrequency, step);
            this.frequency = RadioConstants.clampFrequency(snapped);
            this.value = toSliderValue(this.frequency);
            updateMessage();
        }

        private int getCurrentStep() {
            if (hasAltDown()) return RadioConstants.FAST_STEP;
            if (hasShiftDown()) return RadioConstants.FINE_STEP;
            return RadioConstants.NORMAL_STEP;
        }

        private static int snap(int value, int step) {
            int min = RadioConstants.MIN_FREQUENCY;
            int offset = value - min;
            int snappedOffset = Math.round(offset / (float) step) * step;
            return min + snappedOffset;
        }

        private static double toSliderValue(int frequency) {
            return (RadioConstants.clampFrequency(frequency) - RadioConstants.MIN_FREQUENCY)
                    / (double) (RadioConstants.MAX_FREQUENCY - RadioConstants.MIN_FREQUENCY);
        }

        private static int fromSliderValue(double value) {
            return RadioConstants.MIN_FREQUENCY
                    + (int) Math.round(value * (RadioConstants.MAX_FREQUENCY - RadioConstants.MIN_FREQUENCY));
        }
    }
}