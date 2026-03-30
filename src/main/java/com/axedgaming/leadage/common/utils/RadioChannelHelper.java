package com.axedgaming.leadage.common.utils;

public final class RadioChannelHelper {
    private RadioChannelHelper() {}

    public static int clampFrequency(int frequency) {
        return Math.max(RadioConstants.MIN_FREQUENCY, Math.min(RadioConstants.MAX_FREQUENCY, frequency));
    }

    public static boolean isValidFrequency(int frequency) {
        return frequency >= RadioConstants.MIN_FREQUENCY && frequency <= RadioConstants.MAX_FREQUENCY;
    }

    public static String formatFrequency(int frequency) {
        return clampFrequency(frequency) + " MHz";
    }
}
