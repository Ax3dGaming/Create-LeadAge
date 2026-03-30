package com.axedgaming.leadage.common.utils;

public final class RadioTextHelper {
    private RadioTextHelper() {}

    public static boolean isRadioMessage(String rawMessage) {
        return rawMessage != null && rawMessage.startsWith("!") && rawMessage.length() > 1;
    }

    public static String stripRadioPrefix(String rawMessage) {
        if (rawMessage == null || rawMessage.isBlank()) {
            return "";
        }

        if (rawMessage.startsWith("!")) {
            return rawMessage.substring(1).trim();
        }

        return rawMessage.trim();
    }

    public static String normalizeForComparison(String input) {
        return input == null ? "" : input.trim().toLowerCase();
    }
}
