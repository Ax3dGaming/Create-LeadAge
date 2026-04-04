package com.axedgaming.leadage.common.utils;

import com.axedgaming.leadage.Config;

public class RadioConstants {



    public static final int MIN_FREQUENCY = 760; // 76.0 MHz
    public static final int MAX_FREQUENCY = 1080; // 108.0 MHz
    public static final int DEFAULT_FREQUENCY = 920; // 92.0 MHz

    public static final int NORMAL_STEP = 10; // 1.0 MHz
    public static final int FINE_STEP = 1;    // 0.1 MHz
    public static final int FAST_STEP = 50;   // 5.0 MHz


    public static final int RADIO_BLOCK_RANGE = Config.RADIO_RANGE.get();
    public static final int ANALYSER_PULSE_TICKS = 20;

    public static final String NBT_FREQUENCY = "Frequency";
    public static final String NBT_TARGET_TEXT = "TargetText";
    public static final String NBT_PULSE_TICKS = "PulseTicks";

    private RadioConstants() {}

    public static int clampFrequency(int frequency) {
        return Math.max(MIN_FREQUENCY, Math.min(MAX_FREQUENCY, frequency));
    }
}