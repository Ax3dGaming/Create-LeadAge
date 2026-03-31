package com.axedgaming.leadage.client;

import com.axedgaming.leadage.client.screen.RadioFrequencyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

public class RadioClientHooks {

    public static void openFrequencyScreen(InteractionHand hand, int currentFrequency) {
        Minecraft.getInstance().setScreen(new RadioFrequencyScreen(hand, currentFrequency));
    }
}