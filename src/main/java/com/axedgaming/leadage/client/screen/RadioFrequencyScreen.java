package com.axedgaming.leadage.client.screen;

import com.axedgaming.leadage.common.network.NetworkHandler;
import com.axedgaming.leadage.common.network.SetHeldRadioFrequencyPacket;
import com.axedgaming.leadage.common.utils.RadioChannelHelper;
import com.axedgaming.leadage.common.utils.RadioConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;

public class RadioFrequencyScreen extends Screen {

    private final InteractionHand hand;
    private final int initialFrequency;
    private FrequencySlider slider;

    public RadioFrequencyScreen(InteractionHand hand, int currentFrequency) {
        super(Component.literal("Radio Frequency"));
        this.hand = hand;
        this.initialFrequency = RadioConstants.clampFrequency(currentFrequency);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        slider = new FrequencySlider(centerX - 100, centerY - 10, 200, 20, initialFrequency);
        addRenderableWidget(slider);

        addRenderableWidget(Button.builder(Component.literal("Valider"), button -> {
            NetworkHandler.sendToServer(new SetHeldRadioFrequencyPacket(hand, slider.getFrequency()));
            onClose();
        }).bounds(centerX - 102, centerY + 20, 98, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Reset"), button -> {
            slider.setFrequency(RadioConstants.DEFAULT_FREQUENCY);
        }).bounds(centerX + 4, centerY + 20, 98, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.drawCenteredString(this.font, "Réglage de la radio", centerX, centerY - 42, 0xFFFFFF);
        graphics.drawCenteredString(this.font, RadioChannelHelper.formatFrequency(slider.getFrequency()), centerX, centerY - 28, 0xFFD37F);
        graphics.drawCenteredString(this.font, "Normal: 1.0  |  Shift: 0.1  |  Alt: 5.0", centerX, centerY + 48, 0xA0A0A0);
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
            // géré manuellement dans updateFromMouse pour supporter les steps variables
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
            if (Screen.hasAltDown()) {
                return RadioConstants.FAST_STEP;
            }

            if (Screen.hasShiftDown()) {
                return RadioConstants.FINE_STEP;
            }

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