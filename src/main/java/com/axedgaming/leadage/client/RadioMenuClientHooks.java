package com.axedgaming.leadage.client;

import com.axedgaming.leadage.client.screen.RadioAnalyserScreen;
import com.axedgaming.leadage.common.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;

public class RadioMenuClientHooks {

    public static void registerScreens() {
        MenuScreens.register(ModMenus.RADIO_ANALYSER_MENU.get(), RadioAnalyserScreen::new);
    }
}