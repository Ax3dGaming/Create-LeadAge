package com.axedgaming.leadage.common.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;

public class RadioChannelHelper {

    private static final String NBT_FREQUENCY = "Frequency";

    private RadioChannelHelper() {}

    public static int getFrequency(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return RadioConstants.DEFAULT_FREQUENCY;
        }

        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(NBT_FREQUENCY)) {
            return RadioConstants.DEFAULT_FREQUENCY;
        }

        return RadioConstants.clampFrequency(tag.getInt(NBT_FREQUENCY));
    }

    public static void setFrequency(ItemStack stack, int frequency) {
        if (stack == null || stack.isEmpty()) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(NBT_FREQUENCY, RadioConstants.clampFrequency(frequency));
    }

    public static String formatFrequency(int frequency) {
        return String.format(Locale.ROOT, "%.1f MHz", frequency / 10.0D);
    }

    public static String formatFrequencyBracket(int frequency) {
        return "[" + formatFrequency(frequency) + "]";
    }
}