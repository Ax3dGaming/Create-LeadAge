package com.axedgaming.leadage.client;

import com.axedgaming.leadage.client.screen.RadioFrequencyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;

public class RadioClientHooks {

    public static void openItemFrequencyScreen(int currentFrequency) {
        Minecraft.getInstance().setScreen(new RadioFrequencyScreen(currentFrequency));
    }

    public static void openBlockFrequencyScreen(BlockPos pos, int currentFrequency){
        Minecraft.getInstance().setScreen(new RadioFrequencyScreen(pos, currentFrequency));
    }
}