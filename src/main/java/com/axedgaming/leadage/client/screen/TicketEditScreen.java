package com.axedgaming.leadage.client.screen;

import com.axedgaming.leadage.common.network.NetworkHandler;
import com.axedgaming.leadage.common.network.SetTicketMessagePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;

public class TicketEditScreen extends Screen {

    private final InteractionHand hand;
    private final String initialMessage;
    private EditBox editBox;

    public TicketEditScreen(InteractionHand hand, String initialMessage) {
        super(Component.translatable("ui.leadage.ticket.edit"));
        this.hand = hand;
        this.initialMessage = initialMessage == null ? "" : initialMessage;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        editBox = new EditBox(this.font, centerX - 100, centerY - 10, 200, 20, Component.translatable("ui.leadage.ticket.input"));
        editBox.setMaxLength(120);
        editBox.setValue(initialMessage);
        editBox.setFocused(true);
        this.addRenderableWidget(editBox);
        this.setInitialFocus(editBox);

        this.addRenderableWidget(Button.builder(Component.translatable("ui.leadage.validate"), button -> {
            NetworkHandler.sendToServer(new SetTicketMessagePacket(hand, editBox.getValue()));
            onClose();
        }).bounds(centerX - 100, centerY + 20, 98, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("ui.leadage.reset"), button -> {
            editBox.setValue("");
        }).bounds(centerX + 2, centerY + 20, 98, 20).build());
    }

    @Override
    public void resize(net.minecraft.client.Minecraft minecraft, int width, int height) {
        String current = editBox != null ? editBox.getValue() : "";
        super.resize(minecraft, width, height);
        if (editBox != null) {
            editBox.setValue(current);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.drawCenteredString(this.font, this.title, centerX, centerY - 35, 0xFFFFFF);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (editBox != null && editBox.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }

        if (keyCode == 257 || keyCode == 335) { // Enter
            NetworkHandler.sendToServer(new SetTicketMessagePacket(hand, editBox.getValue()));
            onClose();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (editBox != null && editBox.charTyped(codePoint, modifiers)) {
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }
}