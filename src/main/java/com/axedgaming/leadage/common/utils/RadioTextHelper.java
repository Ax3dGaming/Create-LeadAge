package com.axedgaming.leadage.common.utils;

import net.minecraft.network.chat.Component;

public class RadioTextHelper {

    private RadioTextHelper() {}

    public static Component formatRadioMessage(int frequency, String playerName, String message) {
        return Component.literal(RadioChannelHelper.formatFrequencyBracket(frequency) + " : <" + playerName + "> - " + message);
    }

    public static String normalizeForComparison(String text) {
        if (text == null) {
            return "";
        }

        return text.trim().toLowerCase();
    }
}