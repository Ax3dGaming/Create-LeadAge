package com.axedgaming.leadage.common;

import net.minecraft.world.level.GameRules;

public class ModGameRules {
    public static GameRules.Key<GameRules.BooleanValue> SATURNISM_DATA_WHEN_WAKING_UP;

    public static void register() {
        SATURNISM_DATA_WHEN_WAKING_UP = GameRules.register(
                "SaturnismDataWhenWakingUp",
                GameRules.Category.PLAYER,
                GameRules.BooleanValue.create(false)
        );
    }
}