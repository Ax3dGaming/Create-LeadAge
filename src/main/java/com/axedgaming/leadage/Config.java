package com.axedgaming.leadage;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LeadAge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    public static final ForgeConfigSpec COMMON_SPEC;

    public static ForgeConfigSpec.IntValue MAX_DIVING_WEIGHT;

    public static ForgeConfigSpec.DoubleValue MIN_JUMP_VELOCITY;

    public static ForgeConfigSpec.BooleanValue DIVING_WEIGHT_IGNORE_CREATIVE;

    public static ForgeConfigSpec.IntValue CERUSE_DURATION;

    public static ForgeConfigSpec.IntValue CERUSE_COOLDOWN;

    public static ForgeConfigSpec.IntValue CERUSE_AGGRO_RANGE;

    public static ForgeConfigSpec.IntValue SALT_RADIUS;

    public static ForgeConfigSpec.IntValue SALT_PERCENTAGE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("diving_weight");

        MAX_DIVING_WEIGHT = builder
                .comment("Maximum number of diving weights taken into account")
                .defineInRange("maxWeight", 128, 1, 1024);

        MIN_JUMP_VELOCITY = builder
                .comment("Minimum jump Y velocity when fully weighted (0.42 = normal jump)")
                .defineInRange("minJumpVelocity", 0.18D, 0.0D, 1.0D);

        DIVING_WEIGHT_IGNORE_CREATIVE = builder
                .comment("Does the diving weight ignore creative mode")
                .define("divingWeightIgnoreCreative", true);

        builder.pop();

        builder.push("ceruse");

        CERUSE_DURATION = builder
                .comment("Duration of the effect given by the ceruse item (in s)")
                .defineInRange("ceruseDuration", 60, 1, 240);

        CERUSE_COOLDOWN = builder
                .comment("Cooldown of the ceruse item duration (in s)")
                .defineInRange("ceruseCooldown", 5, 1, 30);

        CERUSE_AGGRO_RANGE = builder
                .comment("The range where the mobs aggro you when ceruse is applied (in blocks)")
                .defineInRange("ceruseAggroRange", 8, 1, 20);

        builder.pop();

        builder.push("salt");

        SALT_RADIUS = builder
                .comment("The radius where the salt transforms blocks")
                .defineInRange("saltRadius", 3, 1, 6);

        SALT_PERCENTAGE = builder
                .comment("The percentage of blocks transformed by salt")
                .defineInRange("saltPercentage", 50, 10, 100);

        builder.pop();

        COMMON_SPEC = builder.build();
    }
}