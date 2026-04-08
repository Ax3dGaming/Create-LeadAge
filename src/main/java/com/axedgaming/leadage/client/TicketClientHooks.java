package com.axedgaming.leadage.client;

import com.axedgaming.leadage.client.screen.TicketEditScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public class TicketClientHooks {

    public static void openTicketScreen(InteractionHand hand, String currentMessage) {
        Minecraft.getInstance().setScreen(new TicketEditScreen(hand, currentMessage));
    }
}