package com.axedgaming.leadage;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec COMMON_SPEC;

    public static ForgeConfigSpec.IntValue MAX_DIVING_WEIGHT;

    public static ForgeConfigSpec.DoubleValue MIN_JUMP_VELOCITY;

    public static ForgeConfigSpec.IntValue CERUSE_DURATION;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("diving_weight");

        MAX_DIVING_WEIGHT = builder
                .comment("Maximum number of diving weights taken into account")
                .defineInRange("maxWeight", 128, 1, 1024);

        MIN_JUMP_VELOCITY = builder
                .comment("Minimum jump Y velocity when fully weighted (0.42 = normal jump)")
                .defineInRange("minJumpVelocity", 0.18D, 0.0D, 1.0D);

        builder.pop();

        builder.push("ceruse");

        CERUSE_DURATION = builder
                .comment("Duration of the effect given by the ceruse item (in s)")
                .defineInRange("ceruseDuration", 60, 1, 240);

        builder.pop();

        COMMON_SPEC = builder.build();
    }
}